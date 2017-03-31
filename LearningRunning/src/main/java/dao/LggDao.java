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
import bean.ClassJoinMem;
import bean.Classes;
import bean.PersonSubList;
import bean.TempAttendance;
import command.AttendanceInsertCommand;
import command.AttendancePersonCommand;
import command.MemberSearchCommand;
import command.ClassesSearchCommand;



public class LggDao{
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper<Attendance> attendanceRowMapper = new RowMapper<Attendance>() {
		@Override
		public Attendance mapRow(ResultSet rs, int rowNum) throws SQLException {
			Attendance beanAttendance = new Attendance(
					rs.getInt("class_id"),
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
	private RowMapper<TempAttendance> tempAttendanceRowMapper = new RowMapper<TempAttendance>() {
		@Override
		public TempAttendance mapRow(ResultSet rs, int rowNum) throws SQLException {
			TempAttendance beanAttendance = new TempAttendance(
					rs.getInt("TEMP_ID"),
					rs.getInt("ClASS_ID"),
					rs.getInt("M_ID"),
					rs.getDate("CHECK_TIME"),
					rs.getString("STATUS")
					
			);
			return beanAttendance;
		}		
	};

	
	
	public List<TempAttendance> tempAttendanceList(int class_id) {
		String sql = "select * from (select * from TEMP_ATTENDANCE where CLASS_ID = ? ) "
				+ "natural join MEMBER  ";
		List<TempAttendance> result = jdbcTemplate.query(sql,tempAttendanceRowMapper,class_id);
		return result;
	}
	
	private RowMapper<Classes> classRowMapper = new RowMapper<Classes>() {
		@Override
		public Classes mapRow(ResultSet rs, int rowNum) throws SQLException {
			Classes beanClasses = new Classes(
					rs.getInt("CLASS_ID"),
					rs.getString("CLASS_NAME"),
					rs.getDate("CLASS_START"),
					rs.getDate("CLASS_END"),
					rs.getString("CLASS_STATE"),
					rs.getString("CLASS_COMMENT"),
					rs.getInt("STUDENT_COUNT")
				);
			return beanClasses;
		}		
	};
	public List<Classes> teachersClasses(int teacherId) {
		String sql = "select * "
				+ "from (select CLASS_ID from MEMBER_CLASS where M_ID = ?) "
				+ "natural join CLASSES "
				+ "left outer join (select CLASS_ID,count(*) as STUDENT_COUNT from MEMBER_CLASS where AUTH_ENAME='student' GROUP BY AUTH_ENAME,CLASS_ID) using(CLASS_ID)";
		List<Classes> result = jdbcTemplate.query(sql,classRowMapper,teacherId);
		return result;
	}

	public int attendInsert(AttendanceInsertCommand command,int class_id) {
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
		String sql = "update TEMP_ATTENDANCE set " + cName + " = ? where CLASS_ID = ? and M_ID IN ("+inSql+") and "+cName+" IS NULL ";
		return jdbcTemplate.update(sql, command.getTime(),class_id);
	}
	public List<Classes> classList(int m_id) {
		String sql = "select * "
				+ "from  MEMBER_CLASS where M_ID=? "
				+ "natural join CLASSES ";
		List<Classes> result = jdbcTemplate.query(sql,classRowMapper,m_id);
		return result;

	}
	
	
	public List<Classes> classList(ClassesSearchCommand command) {
		String whereSql ="";
		int tmp=0;
		if(command.getSearchText()!=null){
			whereSql += (tmp==0)?" where ":" and ";
			tmp++;
			whereSql += " CLASS_NAME like '%"+command.getSearchText()+"%' ";
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
			whereSql += " CLASS_STATE in ("+inSql+") ";
		}
		
		String sql = "select * "
				+ "from  CLASSES "
				+ "left outer join (select CLASS_ID,count(*) as STUDENT_COUNT from MEMBER_CLASS where AUTH_ENAME='student' group by CLASS_ID ) USING(CLASS_ID)  "
				+ whereSql;
		List<Classes> result = jdbcTemplate.query(sql,classRowMapper);
		return result;
	}

	public int studentCountByClasses(int class_id) {
		List<Integer> result = jdbcTemplate.query("SELECT count(*) FROM STUDENT_CLASS where CLASS_ID = ? ",
				new RowMapper<Integer>(){
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						Integer cnt = rs.getInt(1);
						return cnt;
					}
				},class_id);
		return result.isEmpty()?0:result.get(0);
	}
	@Transactional
	public int classDelete(int class_id) {
		jdbcTemplate.update("delete from MEMBER_CLASS where CLASS_ID = ? ",class_id);
		int cnt1 = jdbcTemplate.update("delete from CLASSES where CLASS_ID = ? ",class_id);
		return cnt1;
	}

	public int classEdit(Classes command) {
		return jdbcTemplate.update(" update CLASSES set "
						+ "CLASS_NAME=?,"
						+ "CLASS_START=?,"
						+ "CLASS_END=?,"
						+ "CLASS_STATE=?,"
						+ "CLASS_COMMENT=?"
						+ " where CLASS_ID = ? ",
						command.getClass_name(),
						command.getClass_start(),
						command.getClass_end(),
						command.getClass_state(),
						command.getClass_comment(),
						command.getClass_id()
			);
	}

	
	
	
	public int classInsert(Classes command) {
		return jdbcTemplate.update(" INSERT INTO CLASSES "
				+ "(CLASS_ID,CLASS_NAME,CLASS_START,CLASS_END,CLASS_STATE,CLASS_COMMENT) "
				+ " VALUES(SEQUENCE_CLASS.NEXTVAL,?,?,?,?,?) ",
				command.getClass_name(),
				command.getClass_start(),
				command.getClass_end(),
				command.getClass_state(),
				command.getClass_comment()
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

	public List<Classes> simpleClassList() {
		String whereSql = "";
		String sql = "select * "
				+ "from  CLASSES "
				+ "left outer join (select CLASS_ID,count(*) as STUDENT_COUNT from MEMBER_CLASS where AUTH_ENAME='student' GROUP BY M_ID,CLASS_ID)  USING(CLASS_ID) "
				+ whereSql;
		List<Classes> result = jdbcTemplate.query(sql,classRowMapper);
		return result;
	}

	private RowMapper<ClassJoinMem> classJoinMemRowMapper = new RowMapper<ClassJoinMem>() {
		@Override
		public ClassJoinMem mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassJoinMem beanClassJoinMem = new ClassJoinMem(
					rs.getInt("M_ID"),
					rs.getInt("CLASS_ID"),
					rs.getString("M_NAME"),
					rs.getString("CLASS_NAME")
				);
			return beanClassJoinMem;
		}		
	};
	
	public List<ClassJoinMem> classJoinMemList(Integer class_id, String auth_ename) {
		String tableName = "";
		if(auth_ename.equals("") || auth_ename==null){
			return null;
		}
		if(class_id==null){
			return null;
		}
			
		
			
		String sql = " select * from  MEMBER "
				+ " natural join (select * from member_auth where auth_ename='"+auth_ename+"')"
				+ " left outer join (select * from MEMBER_CLASS where class_id="+class_id+" and AUTH_ENAME='"+auth_ename+"'  ) USING(M_ID) "
				+ " left outer join classes USING(CLASS_ID) ";
		List<ClassJoinMem> result = jdbcTemplate.query(sql,classJoinMemRowMapper);
		return result;
		
	}

	public int classJoinMemInsert(List<Integer> m_ids, String class_id, String auth_ename) {
		String intoSql = "";
		String tableName = "";
		String dateName = "";
		if(auth_ename.equals("") || auth_ename==null){
			return 0;
		}
		
		for (int i = 0;i<m_ids.size() ;i++) {
			intoSql += "into MEMBER_CLASS (M_ID,CLASS_ID,AUTH_ENAME,END_DATE) values("+m_ids.get(i)+","+class_id+",'"+auth_ename+"',SYSDATE) ";
		}
		int result = jdbcTemplate.update("insert all "+intoSql+" SELECT * FROM DUAL");
		return result;
	}

	public int classJoinMemDelete(List<Integer> m_ids, String class_id, String auth_ename) {
		String tableName = "";
		if(auth_ename.equals("") || auth_ename==null){
			return 0;
		}
		String inSql = "";
		for (int i = 0; i < m_ids.size(); i++) {
			if (i!=0) inSql += ",";
			inSql += m_ids.get(i).toString();
		}
		int result = jdbcTemplate.update("delete from MEMBER_CLASS where M_ID in ("+inSql+") and CLASS_ID = ? and AUTH_ENAME = ? ",class_id,auth_ename);
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
			whereSql += " CLASS_NAME like '%"+command.getSearchText()+"%' ";
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
//			whereSql += " CLASS_STATE in ("+inSql+") ";
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
				+ " where M_ID = ? ",
				command.getM_email(),
				command.getM_name(),
				command.getM_pass(),
				command.getM_id()
	);
	}
	
	@Transactional
	public int memberDelete(int m_id) {
		jdbcTemplate.update("DELETE FROM MEMBER_CLASS WHERE M_ID = ? ",m_id);
		return jdbcTemplate.update("DELETE FROM MEMBER WHERE M_ID = ? ",m_id);
	}

	public AuthMember memberByEmailAndPass(String email, String password) {
		String sql = "select * from  MEMBER  "
				+ "where m_email = ? and m_pass = ?";
		List<AuthMember> result = jdbcTemplate.query(sql,member2RowMapper,email,password);
		return result.isEmpty()?null:result.get(0);

	}
	
	public List<String> memberAuthList(Integer m_id) {
		String sql = "select * from  MEMBER_auth  "
				+ "where m_id = ? ";
		List<String> result = jdbcTemplate.query(sql,new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String auth = rs.getString(1);
				return auth;
			}
		},m_id);
		return result;
	}


	
	
	//jsh
	
	private RowMapper<AttendancePersonCommand> attendPersonRowMapper = new RowMapper<AttendancePersonCommand>() {
		@Override
		public AttendancePersonCommand mapRow(ResultSet rs, int rowNum) 
				throws SQLException {
			AttendancePersonCommand attendancePersonCommand = new AttendancePersonCommand(
					rs.getInt("m_id"), 
					rs.getInt("subject_id"),
					rs.getInt("attend_id"),
					rs.getDate("start_time"),	
					rs.getDate("end_time"),	
					rs.getDate("stop_time"),	
					rs.getDate("restart_time"),	
					rs.getString("attend_status"),	
					rs.getString("subject_name"),
					rs.getDate("subject_start"), 
					rs.getDate("subject_end"),
					rs.getString("subject_state"),
					rs.getString("subject_comment"), 
					rs.getString("m_email"),
					rs.getString("m_name"),
					rs.getInt("m_pass"),
					rs.getString("m_app_u_no"));
			return attendancePersonCommand;
		}
	};	
	
	private RowMapper<PersonSubList> personSubListRowMapper = new RowMapper<PersonSubList>() {
		@Override
		public PersonSubList mapRow(ResultSet rs, int rowNum) 
				throws SQLException {
			PersonSubList personSubList = new PersonSubList(
					rs.getInt("m_id"),
					rs.getInt("subject_id"),
					rs.getString("subject_name"),
					rs.getString("subject_state"),
					rs.getString("m_email"),
					rs.getString("m_name")
					);
			return personSubList;
		}
	};	
	
	//수업 일수 세기
	public int count(int studentId ) {
		Integer count = jdbcTemplate.queryForObject(
				"select count(*) from attendance where m_id = ? ", Integer.class, studentId);
		return count;
	}
	
	//총 출석
	public int attendCount(int studentId ) {
		Integer count = jdbcTemplate.queryForObject(
				"select count(*) from (select * from attendance where attend_status = '출석') "
				+ "where m_id = ? ", Integer.class, studentId);
		return count;
	}
		
	//지각
	public int lateCount(int studentId ) {
		Integer count = jdbcTemplate.queryForObject(
				"select count(*) from (select * from attendance where attend_status = '지각') "
				+ "where m_id = ? ", Integer.class, studentId);
		System.out.println(count);
		return count;
	}
	
	//외출
	public int goOutCount(int studentId ) {
		Integer count = jdbcTemplate.queryForObject(
			"select count(*) from (select * from attendance where attend_status = '외출') "
			+ "where m_id = ? ", Integer.class, studentId);
		return count;
	}
	
	//결석
	public int absentCount(int studentId ) {
		Integer count = jdbcTemplate.queryForObject(
			"select count(*) from (select * from attendance where attend_status = '결석') "
			+ "where m_id = ? ", Integer.class, studentId);
		return count;
	}
	
	//조퇴
	public int leaveEarlyCount(int studentId ) {
		Integer count = jdbcTemplate.queryForObject(
			"select count(*) from (select * from attendance where attend_status = '조퇴') "
			+ "where m_id = ? ", Integer.class, studentId);
		return count;
	}
	
	//attendancepersonSubList 페이지용 Dao
	public  List<PersonSubList> selectSubPerson(int studentId) {
		String sql = "select distinct m_id, subject_id, subject_name, subject_state, m_email, m_name from "
				+ "attendance natural join subjects natural join member where m_id = ? ";
		List<PersonSubList> results = jdbcTemplate.query(sql, personSubListRowMapper, studentId);
		return results;
	}
	
	public List<AttendancePersonCommand> selectAllPerson(int studentId) {
		String sql = "select * from attendance natural join subjects natural join member where m_id = ? ";
		List<AttendancePersonCommand> results = jdbcTemplate.query(sql, attendPersonRowMapper, studentId);
		return results;
	}

	public String getStudentName(int studentId) {
		String result = jdbcTemplate.queryForObject(
				"select m_name from member where m_id = ? ", String.class, studentId);
		return result;
	}
	
	public String getStudentEmail(int studentId) {
		String result = jdbcTemplate.queryForObject(
				"select m_email from member where m_id = ? ", String.class, studentId);
		return result;
	}
	
	public String getSubjectName(int studentId) {
		String result = jdbcTemplate.queryForObject(
				"select distinct subject_name from attendance natural join subjects where m_id = ? ", 
				String.class, studentId);
		return result;
	}
	
	public String getMemberId(int studentId) {
		String result = jdbcTemplate.queryForObject(
				"select distinct m_id from attendance natural join subjects where m_id = ? ", 
				String.class, studentId);
		return result;
	}
	
	public double getAttendRate(int studentId){
		Integer studentAttend = jdbcTemplate.queryForObject(
				"select count(*) from (select * from attendance where m_id = ? and "
				+ "start_time between to_date((select  trunc(sysdate,'MM') from dual), 'YY/MM/DD') and "
				+ "to_date((select last_day(sysdate) from dual), 'YY/MM/DD')) "
				+ "where attend_status in ('출석','외출','지각','조퇴') ",
				Integer.class, studentId);
		
		Integer mustAttend = jdbcTemplate.queryForObject("select count(*) from "
				+ "(select * from attendance where m_id = ?) where start_time "
				+ "between to_date((select  trunc(sysdate,'MM') from dual), 'YY/MM/DD') and "
				+ "to_date((select last_day(sysdate) from dual), 'YY/MM/DD') ",
				Integer.class, studentId);
		
		double result = ((double) studentAttend / (double) mustAttend) * 100;
		return result;
	}
	
	
	//기간별 조회
	public List<AttendancePersonCommand> searchPersonPeriod (int studentId, String strFrom, String strTo) {
		String sql = "select * from attendance natural join subjects natural join member where m_id = ? and"
				+ " start_time between to_date(?, 'YY/MM/DD') and to_date(?, 'YY/MM/DD')";
		List<AttendancePersonCommand> results = 
				jdbcTemplate.query(sql, attendPersonRowMapper, studentId, strFrom, strTo);
		return results;
	} 
	
	//결석, 외출, 지각, 조퇴 조회
	//결석
	public List<AttendancePersonCommand> absent(int studentId){
		String sql = "select * from (select * from attendance natural join subjects natural join member"
				+ " where attend_status = '결석' ) where m_id = ? ";
		List<AttendancePersonCommand> results= jdbcTemplate.query(sql, attendPersonRowMapper, studentId);
		return results;
	}
	
	//외출
	public List<AttendancePersonCommand> goOut(int studentId){
		String sql = "select * from (select * from attendance natural join subjects natural join member"
				+ " where attend_status = '외출' ) where m_id = ? ";
		List<AttendancePersonCommand> results= jdbcTemplate.query(sql, attendPersonRowMapper, studentId);
		return results;
	}
	
	//지각
	public List<AttendancePersonCommand> beLate(int studentId){
		String sql = "select * from (select * from attendance natural join subjects natural join member "
				+ "where attend_status = '지각' ) where m_id = ? ";
		List<AttendancePersonCommand> results= jdbcTemplate.query(sql, attendPersonRowMapper, studentId);
		return results;
	}
	
	
	//조퇴
	public List<AttendancePersonCommand> leaveEarly(int studentId){
		String sql = "select * from (select * from attendance natural join subjects natural join member"
				+ " where attend_status = '조퇴' ) where m_id = ? ";
		List<AttendancePersonCommand> results= jdbcTemplate.query(sql, attendPersonRowMapper, studentId);
		return results;
	}
	
	
	
	

}
