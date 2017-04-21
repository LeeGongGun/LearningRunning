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
import bean.ClassAttend;
import bean.ClassJoinMem;
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
import command.PersonSearch;
import command.examCommand;

public class TeacherDao {
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	//AuthMember
	private RowMapper<AuthMember> memberRowMapper = new RowMapper<AuthMember>() {
		@Override
		public AuthMember mapRow(ResultSet rs, int rowNum) throws SQLException {
			AuthMember beanMember = new AuthMember(
					rs.getInt("M_ID"),
					rs.getString("M_EMAIL"),
					rs.getString("M_NAME"),
					rs.getString("M_PASS"),
					rs.getString("AUTH_ENAME"),
					rs.getString("M_IMAGE"),
					rs.getString("M_APP_U_NO")
				);
			return beanMember;
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
	
	public List<AuthMember> authOnlyList(String auth_ename) {
		String sql = "select * from  MEMBER natural join (select * from MEMBER_AUTH where auth_ename=?)  ";
		List<AuthMember> result = jdbcTemplate.query(sql,member2RowMapper,auth_ename);
		return result;
	}

	public List<AuthMember> authList(String auth_ename) {
		String sql = "select * from  MEMBER left outer join (select * from MEMBER_AUTH where auth_ename=?) USING(M_ID)  ";
		List<AuthMember> result = jdbcTemplate.query(sql,memberRowMapper,auth_ename);
		return result;
	}

	public List<AuthMember> studentList(Integer class_id) {
		String whereSql="";
		if(class_id!=null){
			whereSql = " and class_id = "+class_id;
		}
		String sql = "select * from (select * from member_class where auth_ename='student' "+whereSql+") natural join member  ";
		List<AuthMember> result = jdbcTemplate.query(sql,memberRowMapper);
		return result;
	}

	public AuthMember selectMember(int m_id) {
		String sql ="select * from MEMBER where M_ID = ?";
		List<AuthMember> result = jdbcTemplate.query(sql,member2RowMapper,m_id);
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

	//Curriculum
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
	
	//Classes
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
	
	public List<Classes> simpleClassList() {
		String whereSql = "";
		String sql = "select * "
				+ "from  CLASSES "
				+ "left outer join (select CLASS_ID,count(*) as STUDENT_COUNT from MEMBER_CLASS where AUTH_ENAME='student' GROUP BY CLASS_ID)  USING(CLASS_ID) "
				+ whereSql;
		List<Classes> result = jdbcTemplate.query(sql,classRowMapper);
		return result;
	}

	public Classes selectClasses(int class_id) {
		String sql = "select * from CLASSES where class_id = ? ";
		List<Classes> result = jdbcTemplate.query(sql,new RowMapper<Classes>(){
	
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
						0
					);
				return beanClasses;				
			}
			
		},class_id);
		return result.isEmpty()?null:result.get(0);
	}
	
	public List<Classes> teacherClassList(int teacher_id) {
		String whereSql = "";
		String sql = "(select * from  MEMBER_CLASS where m_id= "+teacher_id+" and auth_ename='teacher')"
				+ "natural join CLASSES "
				+ "left outer join (select CLASS_ID,count(*) as STUDENT_COUNT from MEMBER_CLASS where AUTH_ENAME='student' GROUP BY CLASS_ID)  USING(CLASS_ID) ";
		List<Classes> result = jdbcTemplate.query(sql,classRowMapper);
		return result;
	}

	public List<Classes> studentClassList(int student_id) {
		String whereSql = "";
		String sql = "(select * from  MEMBER_CLASS where m_id= "+student_id+" and auth_ename='student')"
				+ "natural join CLASSES "
				+ "left outer join (select CLASS_ID,count(*) as STUDENT_COUNT from MEMBER_CLASS where AUTH_ENAME='student' GROUP BY CLASS_ID)  USING(CLASS_ID) ";
		List<Classes> result = jdbcTemplate.query(sql,classRowMapper);
		return result;
	}
	
	//score
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

	//ExamJoinSubject
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

	//ClassAttend
	private RowMapper<ClassAttend> classAttendRowMapper = new RowMapper<ClassAttend>() {
		@Override
		public ClassAttend mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassAttend beanClassAttend = new ClassAttend(
					rs.getInt("CLASS_ID"),
					rs.getInt("M_ID"),
					rs.getString("CLASS_NAME"),
					rs.getString("CLASS_STATE"),
					rs.getString("출석"),
					rs.getString("결석"),
					rs.getString("조퇴"),
					rs.getString("외출"),
					rs.getString("지각")
				);
			return beanClassAttend;
		}		
	};
	
	public ClassAttend memberClassAttendList(int class_id,int m_id) {
		String sql = "select * "
				+ "from (SELECT * FROM MEMBER_CLASS where M_ID="+m_id+" and CLASS_ID="+class_id+") "
				+ "natural join CLASSES "
				+ "left outer join (select CLASS_ID,M_ID,count(*) as 출석 from ATTENDANCE where M_ID="+m_id+" and CLASS_ID="+class_id+" and ATTEND_STATUS='출석' GROUP BY M_ID,CLASS_ID,ATTEND_STATUS) using(M_ID,CLASS_ID) "
				+ "left outer join (select CLASS_ID,M_ID,count(*) as 결석 from ATTENDANCE where M_ID="+m_id+" and CLASS_ID="+class_id+" and ATTEND_STATUS='결석' GROUP BY M_ID,CLASS_ID,ATTEND_STATUS) using(M_ID,CLASS_ID) "
				+ "left outer join (select CLASS_ID,M_ID,count(*) as 조퇴 from ATTENDANCE where M_ID="+m_id+" and CLASS_ID="+class_id+" and ATTEND_STATUS='조퇴' GROUP BY M_ID,CLASS_ID,ATTEND_STATUS) using(M_ID,CLASS_ID) "
				+ "left outer join (select CLASS_ID,M_ID,count(*) as 외출 from ATTENDANCE where M_ID="+m_id+" and CLASS_ID="+class_id+" and ATTEND_STATUS='외출' GROUP BY M_ID,CLASS_ID,ATTEND_STATUS) using(M_ID,CLASS_ID) "
				+ "left outer join (select CLASS_ID,M_ID,count(*) as 지각 from ATTENDANCE where M_ID="+m_id+" and CLASS_ID="+class_id+" and ATTEND_STATUS='지각' GROUP BY M_ID,CLASS_ID,ATTEND_STATUS) using(M_ID,CLASS_ID) ";
		List<ClassAttend> result = jdbcTemplate.query(sql,classAttendRowMapper);
		return result.isEmpty()?null:result.get(0);
	
	}
	
	//ClassJoinMem
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

	//Attendance
	private RowMapper<Attendance> attendPersonRowMapper = new RowMapper<Attendance>() {
		@Override
		public Attendance mapRow(ResultSet rs, int rowNum) 
				throws SQLException {
			Attendance attendancePersonCommand = new Attendance(
					rs.getInt("attend_id"),
					rs.getInt("class_id"),
					rs.getInt("m_id"),
					rs.getDate("attend_date"),
					rs.getString("attend_status"),
					rs.getString("start_time"),
					rs.getString("end_time"),
					rs.getString("stop_time"),
					rs.getString("restart_time")
				);
			return attendancePersonCommand;
		}
	};	

	public List<Attendance> memberAttendList(PersonSearch command) {
		String sql = "select * from ATTENDANCE where M_ID = ? and CLASS_ID=? and ATTEND_DATE between ? and ? order by ATTEND_DATE desc";
		List<Attendance> results = 
				jdbcTemplate.query(sql, attendPersonRowMapper, command.getM_id(), command.getClass_id(), command.getFrom(), command.getTo());
		return results;
	} 
	
}
