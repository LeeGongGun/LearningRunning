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
import bean.ClassJoinSubject;
import bean.Classes;
import bean.Counsel;
import bean.Curriculum;
import bean.Exam;
import bean.ExamJoinSubject;
import bean.Score;
import bean.TempAttendance;
import command.AttendanceInsertCommand;
import command.CounselSearchCommand;
import command.MemberSearchCommand;
import command.examCommand;

public class TeacherDao {
	private JdbcTemplate jdbcTemplate;
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
					rs.getInt("CUR_ID"),
					rs.getInt("STUDENT_COUNT")
				);
			return beanClasses;
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
					rs.getString("M_IMAGE"),
					rs.getString("M_APP_U_NO")
				);
			return beanMember;
		}		
	};
	public List<AuthMember> memberList(MemberSearchCommand command) {
		String whereSql ="";
		String joinSql ="";
		int tmp=0;
		if(command.getClass_id()>0){
			whereSql += (tmp==0)?" where ":" and ";
			tmp++;
			whereSql += " CLASS_ID = "+command.getClass_id()+" ";
			joinSql = " (select * from member_class "+whereSql+" ) natural join ";
		}
		String sql = "select * from "+joinSql+"  MEMBER  order by M_ID desc";
		List<AuthMember> result = jdbcTemplate.query(sql,member2RowMapper);
		return result;
	}
	
	public List<AuthMember> authList(String auth_ename) {
		String sql = "select * from  MEMBER left outer join (select * from MEMBER_AUTH where auth_ename=?) USING(M_ID)  ";
		List<AuthMember> result = jdbcTemplate.query(sql,member2RowMapper,auth_ename);
		return result;
	}
	public List<AuthMember> authOnlyList(String auth_ename) {
		String sql = "select * from  MEMBER natural join (select * from MEMBER_AUTH where auth_ename=?)  ";
		List<AuthMember> result = jdbcTemplate.query(sql,member2RowMapper,auth_ename);
		return result;
	}
	private RowMapper<Curriculum> curriRowMapper = new RowMapper<Curriculum>() {
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
		List<Curriculum> result = jdbcTemplate.query(sql,curriRowMapper);
		return result;
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
	@Transactional
	public int scoreInsert(List<Integer> exam_ids, List<Integer> m_ids, List<Integer> subject_ids,
			List<Integer> scores) {
		String intoSql = "";
		
		for (int i = 0;i<exam_ids.size() ;i++) {
			intoSql += "into EXAM_SCORE (EXAM_ID,SUBJECT_ID,M_ID,SCORE) "
					+ "values("+exam_ids.get(i)+","+subject_ids.get(i)+","+m_ids.get(i)+","+scores.get(i)+") ";
		}
		int result = jdbcTemplate.update("insert all "+intoSql+" SELECT * FROM DUAL");
		return result;

	}
	private RowMapper<Score> scoreRowMapper = new RowMapper<Score>() {
		@Override
		public Score mapRow(ResultSet rs, int rowNum) throws SQLException {
			Score beanscore = new Score(
					rs.getInt("exam_id"), 
					rs.getInt("subject_id"), 
					rs.getInt("m_id"), 
					rs.getInt("score")
					);
			return beanscore;
		}		
	};

	
	public List<Score> scoreListByExam(Integer exam_id) {
		String whereSql = "where exam_id="+exam_id;
		String sql = "select * "
				+ "from  exam_score "
				+ whereSql;
		List<Score> result = jdbcTemplate.query(sql,scoreRowMapper);
		return result;
		
	}
	public int scoreUpdate(List<Integer> exam_ids, List<Integer> m_ids, List<Integer> subject_ids,
			List<Integer> scores) {
		int updateOk = 0;
		for (int i = 0; i < scores.size(); i++) {
			String sql = "update exam_score set score="+scores.get(i)
			+" where exam_id="+exam_ids.get(i)
			+" and subject_id="+subject_ids.get(i)
			+" and m_id="+m_ids.get(i);
			updateOk += jdbcTemplate.update(sql);
		}
		return updateOk;
	}
	public int scoreDelete(Integer exam_id) {
		return jdbcTemplate.update("DELETE FROM EXAM_SCORE where EXAM_ID = ? ",exam_id);
	}

	public List<Score> scoreListByClass(Integer class_id) {
		String sql = "select * from  (select * from exam where class_id="+class_id+")"
				+ " natural join exam_score ";
		List<Score> result = jdbcTemplate.query(sql,scoreRowMapper);
		return result;
	}
	private RowMapper<ExamJoinSubject> ExamJoinSubjectRowMapper = new RowMapper<ExamJoinSubject>() {
		@Override
		public ExamJoinSubject mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExamJoinSubject beanClasses = new ExamJoinSubject(
					rs.getInt("exam_id"),
					rs.getInt("subject_id"),
					rs.getString("subject_title")
				);
			return beanClasses;
		}		
	};
	public List<ExamJoinSubject> memberExamSubjectList(int class_id) {
		String sql = "select * from "
				+ "(select * from exam_subject WHERE exam_id in "
				+ "(select EXAM_ID from exam where class_id=?)) "
				+ "natural join subjects";
		List<ExamJoinSubject> result = jdbcTemplate.query(sql,ExamJoinSubjectRowMapper,class_id);
		return result;
	}

	
	//counsel
	private RowMapper<Counsel> counselRowMapper = new RowMapper<Counsel>() {
		@Override
		public Counsel mapRow(ResultSet rs, int rowNum) throws SQLException {
			Counsel beanMember = new Counsel(
					rs.getInt("COUNSEL_ID"),
					rs.getString("COUNSEL_TITLE"),
					rs.getString("COUNSEL_CONDENT"),
					rs.getInt("COUNSELOR"),
					rs.getInt("M_ID"),
					rs.getDate("COUNSEL_DATE").toString()
				);
			return beanMember;
		}		
	};
	
	
	public List<Counsel> counselList(CounselSearchCommand command) {
		String whereSql ="";
		int tmp=0;
		if(command.getCounselor()>0){
			whereSql += (tmp==0)?" where ":" and ";
			tmp++;
			whereSql += " counselor = "+command.getCounselor()+" ";
		}
		if(command.getM_id()>0){
			whereSql += (tmp==0)?" where ":" and ";
			tmp++;
			whereSql += " m_id = "+command.getM_id()+" ";
		}

		String sql = "select * from  COUNSEL "
				+whereSql
				+ " order by COUNSEL_DATE desc ";
		List<Counsel> result = jdbcTemplate.query(sql,counselRowMapper);
		return result;
	}

	
	public int counselInsert(Counsel command) {
		return jdbcTemplate.update(" insert into COUNSEL "
				+ "(COUNSEL_ID,COUNSEL_TITLE,COUNSEL_CONDENT,COUNSELOR,M_ID,COUNSEL_DATE) "
				+ " values(SEQUENCE_COUNSEL.NEXTVAL,?,?,?,?,'"
				+command.getCounsel_date()+"') ",
				command.getCounsel_title(),command.getCounsel_condent(),command.getCounselor(),command.getM_id()
				);
	}
	public int counselEdit(Counsel command) {
		return jdbcTemplate.update(" update COUNSEL set "
				+ "COUNSEL_TITLE=?,"
				+ "COUNSEL_CONDENT=?,"
				+ "COUNSELOR=?,"
				+ "M_ID=?,"
				+ "COUNSEL_DATE='"+command.getCounsel_date()+"'"
				+ " where COUNSEL_ID = ? ",
				command.getCounsel_title(),
				command.getCounsel_condent(),
				command.getCounselor(),
				command.getM_id(),
				command.getCounsel_id()
				);
	}
	public int counselDelete(int counsel_id) {
		return jdbcTemplate.update("delete from COUNSEL where COUNSEL_ID = ? ",counsel_id);
	}

}
