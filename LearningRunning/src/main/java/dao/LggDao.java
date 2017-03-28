package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import bean.Attendance;
import bean.AuthMember;
import bean.SubJoinMem;
import bean.Subject;
import command.AttendanceInsertCommand;
import command.MemberSearchCommand;
import command.SubjectSearchCommand;



public class LggDao{
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	private RowMapper<Attendance> attendanceRowMapper = new RowMapper<Attendance>() {
		@Override
		public Attendance mapRow(ResultSet rs, int rowNum) throws SQLException {
			Attendance beanAttendance = new Attendance(
					rs.getInt("subject_id"),
					rs.getInt("m_id"),
					rs.getString("m_name"),
					rs.getTimestamp("start_time"),
					rs.getTimestamp("end_time"),
					rs.getTimestamp("stop_time"),
					rs.getTimestamp("restart_time"),
					rs.getString("ATTEND_STATUS")
			);
			return beanAttendance;
		}		
	};

//	private RowMapper<Teacher> teacherRowMapper = new RowMapper<Teacher>() {
//		@Override
//		public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
//			Teacher beanTeacher = new Teacher(
//					rs.getInt("M_ID"),
//					rs.getInt("AUTH_MANAGER"),
//					rs.getDate("AUTH_END_DATE"),
//					rs.getString("AUTH_ENAME"),
//					rs.getString("AUTH_KNAME"),
//					rs.getString("M_EMAIL"),
//					rs.getString("M_NAME")
//				);
//			return beanTeacher;
//		}		
//	};
	

	
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Attendance> tempAttendanceList(int subjectId) {
		String sql = "select * from TEMP_ATTENDANCE "
				+ "natural join MEMBER  where SUBJECT_ID = ? ";
		List<Attendance> result = jdbcTemplate.query(sql,attendanceRowMapper,subjectId);
		return result;
	}
	
	private RowMapper<Subject> subjectRowMapper = new RowMapper<Subject>() {
		@Override
		public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
			Subject beanSubject = new Subject(
					rs.getInt("SUBJECT_ID"),
					rs.getString("SUBJECT_NAME"),
					rs.getDate("SUBJECT_START"),
					rs.getDate("SUBJECT_END"),
					rs.getString("SUBJECT_STATE"),
					rs.getString("SUBJECT_COMMENT"),
					rs.getInt("STUDENT_COUNT")
				);
			return beanSubject;
		}		
	};
	public List<Subject> teachersSubject(int teacherId) {
		String sql = "select * "
				+ "from (select SUBJECT_ID from TEACHER_SUBJECT where M_ID = ?) "
				+ "natural join SUBJECTS "
				+ "natural join (select SUBJECT_ID,count(*) as STUDENT_COUNT from STUDENT_SUBJECT GROUP BY SUBJECT_ID) ";
		List<Subject> result = jdbcTemplate.query(sql,subjectRowMapper,teacherId);
		return result;
	}

	public int attendInsert(AttendanceInsertCommand command,int subjectId) {
		String state = command.getState();
		String cName = "";
		if (state.equals("start")) {
			cName = "START_TIME";
		}else if (state.equals("stop")) {
			cName = "STOP_TIME";
		}else if (state.equals("restart")) {
			cName = "RESTART_TIME";
		}else if (state.equals("end")) {
			cName = "END_TIME";
		}else {
			return 0;
		}
		String inSql = "";
		String[] ids = command.getAttendanceCheck();
		for (int i = 0; i < ids.length; i++) {
			if (i!=0) inSql += ",";
			inSql += ids[i];
		}
		String sql = "update TEMP_ATTENDANCE set " + cName + " = ? where SUBJECT_ID = ? and M_ID IN ("+inSql+") and "+cName+" IS NULL ";
		return jdbcTemplate.update(sql, command.getTime(),subjectId);
	}

	public List<Subject> subjectList(SubjectSearchCommand command) {
		String whereSql ="";
		int tmp=0;
		if(command.getSearchText()!=null){
			whereSql += (tmp==0)?" where ":" and ";
			tmp++;
			whereSql += " SUBJECT_NAME like '%"+command.getSearchText()+"%' ";
		}
		if(command.getState()!=null && command.getState().length > 0){
			String[] ids = command.getState();
			String inSql="";
			for (int i = 0; i < ids.length; i++) {
				if (i!=0) inSql += ",";
				inSql += ids[i];
			}
			whereSql += (tmp==0)?" where ":" and ";
			tmp++;
			whereSql += " SUBJECT_STATE in ("+inSql+") ";
		}
		
		String sql = "select * "
				+ "from  SUBJECTS "
				+ "left outer join (select SUBJECT_ID,count(*) as STUDENT_COUNT from STUDENT_SUBJECT GROUP BY SUBJECT_ID) USING(SUBJECT_ID)  "
				+ whereSql;
		List<Subject> result = jdbcTemplate.query(sql,subjectRowMapper);
		return result;
	}

	public int studentCountBySubject(int subjectId) {
		List<Integer> result = jdbcTemplate.query("SELECT count(*) FROM STUDENT_SUBJECT where SUBJECT_ID = ? ",
				new RowMapper<Integer>(){
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						Integer cnt = rs.getInt(1);
						return cnt;
					}
				},subjectId);
		return result.isEmpty()?0:result.get(0);
	}
	@Transactional
	public int subjectDelete(int subjectId) {
		jdbcTemplate.update("DELETE FROM STUDENT_SUBJECT WHERE SUBJECT_ID = ? ",subjectId);
		int cnt1 = jdbcTemplate.update("DELETE FROM SUBJECTS WHERE SUBJECT_ID = ? ",subjectId);
		return cnt1;
	}

	public int subjectEdit(Subject command) {
		return jdbcTemplate.update(" update SUBJECTS set "
						+ "SUBJECT_NAME=?,"
						+ "SUBJECT_START=?,"
						+ "SUBJECT_END=?,"
						+ "SUBJECT_STATE=?,"
						+ "SUBJECT_COMMENT=?"
						+ " where SUBJECT_ID = ? ",
						command.getSubject_name(),
						command.getSubject_start(),
						command.getSubject_end(),
						command.getSubject_state(),
						command.getSubject_comment(),
						command.getSubject_id()
			);
	}

	
	
	
	public int subjectInsert(Subject command) {
		return jdbcTemplate.update(" INSERT INTO SUBJECTS "
				+ "(SUBJECT_ID,SUBJECT_NAME,SUBJECT_START,SUBJECT_END,SUBJECT_STATE,SUBJECT_COMMENT) "
				+ " VALUES(SEQUENCE_SUBJECT.NEXTVAL,?,?,?,?,?) ",
				command.getSubject_name(),
				command.getSubject_start(),
				command.getSubject_end(),
				command.getSubject_state(),
				command.getSubject_comment()
				);
	}

	public int authInsert(List<Integer> m_ids, String auth_ename,int auth_manager_id) {
		String intoSql = "";
		String auth_kname = "";
		if (auth_ename.equals("")) {
			return 0 ;			
		}else if (auth_ename.equals("teacher")) {
			auth_kname = "선생님";
		}else if (auth_ename.equals("student")) {
			auth_kname = "학생";
		}else if (auth_ename.equals("admin")) {
			auth_kname = "관리자";
		}else{
			return 0 ;
		}
		
		for (int i = 0;i<m_ids.size() ;i++) {
//			if (i!=0) intoSql += ",";
			intoSql += "into MEMBER_AUTH (M_ID,AUTH_ENAME,AUTH_KNAME,AUTH_MANAGER) values("+m_ids.get(i)+",'"+auth_ename+"','"+auth_kname+"',"+auth_manager_id+") ";
		}
		int result = jdbcTemplate.update("insert all "+intoSql+" SELECT * FROM DUAL");
		return result;
	}

	public int authDelete(List<Integer> m_ids, String auth_ename) {
		String inSql = "";
		for (int i = 0; i < m_ids.size(); i++) {
			if (i!=0) inSql += ",";
			inSql += m_ids.get(i).toString();
		}
		int result = jdbcTemplate.update("DELETE FROM MEMBER_AUTH WHERE m_id in ("+inSql+") and auth_ename = ? ",auth_ename);
		return result;
	}
	
	private RowMapper<AuthMember> memberRowMapper = new RowMapper<AuthMember>() {
		@Override
		public AuthMember mapRow(ResultSet rs, int rowNum) throws SQLException {
			AuthMember beanMember = new AuthMember(
					rs.getInt("M_ID"),
					rs.getString("M_EMAIL"),
					rs.getString("M_NAME"),
					rs.getString("M_PASS"),
					rs.getString("AUTH_ENAME"),
					rs.getString("M_APP_U_NO")
				);
			return beanMember;
		}		
	};

	public List<AuthMember> authList(String auth_ename) {
		String sql = "select * from  MEMBER left outer join (select * from MEMBER_AUTH where auth_ename=?) USING(M_ID)  ";
		List<AuthMember> result = jdbcTemplate.query(sql,memberRowMapper,auth_ename);
		return result;
	}

	public List<Subject> simpleSubjectList() {
		String whereSql = "";
		String sql = "select * "
				+ "from  SUBJECTS "
				+ "left outer join (select SUBJECT_ID,count(*) as STUDENT_COUNT from STUDENT_SUBJECT GROUP BY SUBJECT_ID)  USING(SUBJECT_ID) "
				+ whereSql;
		List<Subject> result = jdbcTemplate.query(sql,subjectRowMapper);
		return result;
	}

	private RowMapper<SubJoinMem> subJoinMemRowMapper = new RowMapper<SubJoinMem>() {
		@Override
		public SubJoinMem mapRow(ResultSet rs, int rowNum) throws SQLException {
			SubJoinMem beanSubJoinMem = new SubJoinMem(
					rs.getInt("M_ID"),
					rs.getInt("SUBJECT_ID"),
					rs.getString("M_NAME"),
					rs.getString("SUBJECT_NAME")
				);
			return beanSubJoinMem;
		}		
	};
	
	public List<SubJoinMem> subJoinMemList(Integer subject_id, String auth_ename) {
		String tableName = "";
		if(auth_ename.equals("student")){
			tableName = "STUDENT_SUBJECT";
		}else if (auth_ename.equals("teacher")) {
			tableName = "TEACHER_SUBJECT";
		}else{
			return null;
		}
		if(subject_id==null){
			return null;
		}
			
		
			
		String sql = " select * from  MEMBER "
				+ " natural join (select * from member_auth where auth_ename='"+auth_ename+"')"
				+ " left outer join (select * from "+tableName+" where subject_id="+subject_id+"  ) USING(M_ID) "
				+ " left outer join subjects USING(SUBJECT_ID) ";
		List<SubJoinMem> result = jdbcTemplate.query(sql,subJoinMemRowMapper);
		return result;
		
	}

	public int subJoinMemInsert(List<Integer> m_ids, String subject_id, String auth_ename) {
		String intoSql = "";
		String tableName = "";
		String dateName = "";
		if(auth_ename.equals("student")){
			tableName = "STUDENT_SUBJECT";
			dateName = "SS_DATE";
		}else if (auth_ename.equals("teacher")) {
			tableName = "TEACHER_SUBJECT";
			dateName = "TS_DATE";
		}else{
			return 0;
		}
		
		for (int i = 0;i<m_ids.size() ;i++) {
			intoSql += "into "+tableName+" (M_ID,SUBJECT_ID,"+dateName+") values("+m_ids.get(i)+","+subject_id+",SYSDATE) ";
		}
		int result = jdbcTemplate.update("insert all "+intoSql+" SELECT * FROM DUAL");
		return result;
	}

	public int subJoinMemDelete(List<Integer> m_ids, String subject_id, String auth_ename) {
		String tableName = "";
		if(auth_ename.equals("student")){
			tableName = "STUDENT_SUBJECT";
		}else if (auth_ename.equals("teacher")) {
			tableName = "TEACHER_SUBJECT";
		}else{
			return 0;
		}
		String inSql = "";
		for (int i = 0; i < m_ids.size(); i++) {
			if (i!=0) inSql += ",";
			inSql += m_ids.get(i).toString();
		}
		int result = jdbcTemplate.update("DELETE FROM "+tableName+" WHERE m_id in ("+inSql+") and subject_id = ? ",subject_id);
		return result;
	}

	
	private RowMapper<AuthMember> member2RowMapper = new RowMapper<AuthMember>() {
		@Override
		public AuthMember mapRow(ResultSet rs, int rowNum) throws SQLException {
			AuthMember beanMember = new AuthMember(
					rs.getInt("M_ID"),
					rs.getString("M_EMAIL"),
					rs.getString("M_NAME"),
					rs.getString("M_PASS"),
					rs.getString("M_APP_U_NO")
				);
			return beanMember;
		}		
	};

	public List<AuthMember> memberList(MemberSearchCommand command) {
		String whereSql ="";
		int tmp=0;
		if(command.getSearchText()!=null){
			whereSql += (tmp==0)?" where ":" and ";
			tmp++;
			whereSql += " SUBJECT_NAME like '%"+command.getSearchText()+"%' ";
		}
//		if(command.getState()!=null && command.getState().length > 0){
//			String[] ids = command.getState();
//			String inSql="";
//			for (int i = 0; i < ids.length; i++) {
//				if (i!=0) inSql += ",";
//				inSql += ids[i];
//			}
//			whereSql += (tmp==0)?" where ":" and ";
//			tmp++;
//			whereSql += " SUBJECT_STATE in ("+inSql+") ";
//		}

		String sql = "select * from  MEMBER  "
				+ whereSql;
		List<AuthMember> result = jdbcTemplate.query(sql,member2RowMapper);
		return result;
		}

	public int memberInsert(AuthMember command) {
		return jdbcTemplate.update(" INSERT INTO MEMBER "
				+ "(M_ID,M_EMAIL,M_NAME,M_PASS) "
				+ " VALUES(SEQUENCE_MEMBER.NEXTVAL,?,?,?) ",
				command.getM_email(),
				command.getM_name(),
				command.getM_pass()
				);
	}

	public int memberEdit(AuthMember command) {
		return jdbcTemplate.update(" update MEMBER set "
				+ "M_EMAIL=?,"
				+ "M_NAME=?,"
				+ "M_PASS=?"
				+ " where SUBJECT_ID = ? ",
				command.getM_email(),
				command.getM_name(),
				command.getM_pass(),
				command.getM_id()
	);
	}

	public int memberDelete(int m_id) {
//		jdbcTemplate.update("DELETE FROM STUDENT_SUBJECT WHERE SUBJECT_ID = ? ",subjectId);
		int cnt1 = jdbcTemplate.update("DELETE FROM MEMBER WHERE M_ID = ? ",m_id);
		return cnt1;
	}

	public AuthMember selectByEmailAndPass(String email, String password) {
		String sql = "select * from  MEMBER  "
				+ "where m_email = ? and m_pass = ?";
		List<AuthMember> result = jdbcTemplate.query(sql,member2RowMapper,email,password);
		return result.isEmpty()?null:result.get(0);

	}



}
