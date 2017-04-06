package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import bean.AuthMember;
import bean.ClassJoinMem;
import bean.ClassJoinSubject;
import bean.Classes;
import bean.CurriJoinSubject;
import bean.Curriculum;
import command.ClassesSearchCommand;
import command.MemberSearchCommand;

public class AdminDao {
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
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
	@Transactional
	public int classDelete(int class_id) {
		jdbcTemplate.update("delete from MEMBER_CLASS where CLASS_ID = ? ",class_id);
		int cnt1 = jdbcTemplate.update("delete from CLASSES where CLASS_ID = ? ",class_id);
		return cnt1;
	}
	public List<Classes> simpleClassList() {
		String whereSql = "";
		String sql = "select * "
				+ "from  CLASSES "
				+ "left outer join (select CLASS_ID,count(*) as STUDENT_COUNT from MEMBER_CLASS where AUTH_ENAME='student' GROUP BY CLASS_ID)  USING(CLASS_ID) "
				+ whereSql;
		List<Classes> result = jdbcTemplate.query(sql,classRowMapper);
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
	public List<AuthMember> authList(String auth_ename) {
		String sql = "select * from  MEMBER left outer join (select * from MEMBER_AUTH where auth_ename=?) USING(M_ID)  ";
		List<AuthMember> result = jdbcTemplate.query(sql,memberRowMapper,auth_ename);
		return result;
	}
	public List<ClassJoinMem> classJoinMemList(Integer class_id, String auth_ename) {
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
	public List<AuthMember> memberList(MemberSearchCommand command) {
		String whereSql ="";
		int tmp=0;
		if(command.getSearchText()!=null){
			whereSql += (tmp==0)?" where ":" and ";
			tmp++;
			whereSql += " CLASS_NAME like '%"+command.getSearchText()+"%' ";
		}

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
	
	
	//Curriculum
	private RowMapper<Curriculum> CurriRowMapper = new RowMapper<Curriculum>() {
		@Override
		public Curriculum mapRow(ResultSet rs, int rowNum) throws SQLException {
			Curriculum beanCurri = new Curriculum(
					rs.getInt("CUR_ID"),
					rs.getString("CUR_NAME")
				);
			return beanCurri;
		}		
	};
	
	public List<Curriculum> curriList() {
		String sql = "select * from CURRICULUM ";
		List<Curriculum> result = jdbcTemplate.query(sql,CurriRowMapper);
		return result;
	}
	public int curriInsert(String cur_name) {
		return jdbcTemplate.update(" INSERT INTO CURRICULUM "
				+ "(CUR_ID,CUR_NAME) "
				+ " VALUES(SEQUENCE_CURRI.NEXTVAL,?) ",
				cur_name
				);
	}
	public int curriEdit(String cur_name,int cur_id) {
		return jdbcTemplate.update(" update CURRICULUM set "
				+ "CUR_NAME=?"
				+ " where CUR_ID = ? ",
				cur_name,
				cur_id
				);
	}
	@Transactional
	public int curriDelete(int m_id) {
		jdbcTemplate.update("DELETE FROM CURRICULUM_SUBJECT WHERE CUR_ID = ? ",m_id);
		return jdbcTemplate.update("DELETE FROM CURRICULUM WHERE CUR_ID = ? ",m_id);
	}

	private RowMapper<CurriJoinSubject> curriJoinSubRowMapper = new RowMapper<CurriJoinSubject>() {
		@Override
		public CurriJoinSubject mapRow(ResultSet rs, int rowNum) throws SQLException {
			CurriJoinSubject beanClasses = new CurriJoinSubject(
					rs.getInt("cur_id"),
					rs.getInt("subject_id"),
					rs.getString("subject_title")
				);
			return beanClasses;
		}		
	};
	public List<CurriJoinSubject> curriSubjectList(int cur_id) {
		String sql = "select * from SUBJECTS "
				+ "left outer join (select * from CURRICULUM_SUBJECT where cur_id=?) using(subject_id) ";
		List<CurriJoinSubject> result = jdbcTemplate.query(sql,curriJoinSubRowMapper,cur_id);
		return result;
	}
	public int curriJoinSubInsert(List<Integer> subject_ids, String cur_id) {
		String intoSql = "";
		
		for (int i = 0;i<subject_ids.size() ;i++) {
			intoSql += "into CURRICULUM_SUBJECT (CUR_ID,SUBJECT_ID) values("+cur_id+","+subject_ids.get(i)+") ";
		}
		int result = jdbcTemplate.update("insert all "+intoSql+" SELECT * FROM DUAL");
		return result;
	}
	public int curriJoinSubDelete(List<Integer> subject_ids, String cur_id) {
		String inSql = "";
		for (int i = 0; i < subject_ids.size(); i++) {
			if (i!=0) inSql += ",";
			inSql += subject_ids.get(i).toString();
		}
		int result = jdbcTemplate.update("DELETE FROM CURRICULUM_SUBJECT WHERE SUBJECT_ID in ("+inSql+") and CUR_ID = ? ",cur_id);
		return result;
	}

//////
	
	//classSubject
	private RowMapper<ClassJoinSubject> classJoinSubRowMapper = new RowMapper<ClassJoinSubject>() {
		@Override
		public ClassJoinSubject mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassJoinSubject beanClasses = new ClassJoinSubject(
					rs.getInt("class_id"),
					rs.getInt("subject_id"),
					rs.getString("subject_title")
				);
			return beanClasses;
		}		
	};
	public List<ClassJoinSubject> classSubjectList(int class_id) {
		String sql = "select * from SUBJECTS "
				+ "left outer join (select * from CURRICULUM_SUBJECT where class_id=?) using(subject_id) ";
		List<ClassJoinSubject> result = jdbcTemplate.query(sql,classJoinSubRowMapper,class_id);
		return result;
	}
	public int classJoinSubInsert(List<Integer> subject_ids, String class_id) {
		String intoSql = "";
		
		for (int i = 0;i<subject_ids.size() ;i++) {
			intoSql += "into CURRICULUM_SUBJECT (CUR_ID,SUBJECT_ID) values("+class_id+","+subject_ids.get(i)+") ";
		}
		int result = jdbcTemplate.update("insert all "+intoSql+" SELECT * FROM DUAL");
		return result;
	}
	public int classJoinSubDelete(List<Integer> subject_ids, String class_id) {
		String inSql = "";
		for (int i = 0; i < subject_ids.size(); i++) {
			if (i!=0) inSql += ",";
			inSql += subject_ids.get(i).toString();
		}
		int result = jdbcTemplate.update("DELETE FROM CURRICULUM_SUBJECT WHERE SUBJECT_ID in ("+inSql+") and CUR_ID = ? ",class_id);
		return result;
	}
	
	
	
	
}
