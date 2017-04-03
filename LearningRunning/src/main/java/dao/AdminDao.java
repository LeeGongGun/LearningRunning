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
import bean.Classes;
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

	
}
