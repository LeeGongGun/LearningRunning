package dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import bean.Attendance;
import bean.AuthMember;
import bean.ClassAttend;
import bean.ClassJoinMem;
import bean.Classes;
import bean.TempAttendance;
import command.AttendancePersonCommand;
import command.PersonSearch;



public class LggDao{
	private JdbcTemplate jdbcTemplate;
		@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}



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
	public List<ClassAttend> memberClassAttendList(int m_id) {
		String sql = "select * "
				+ "from (SELECT * FROM MEMBER_CLASS where M_ID="+m_id+") "
				+ "natural join CLASSES "
				+ "left outer join (select CLASS_ID,M_ID,count(*) as 출석 from ATTENDANCE where M_ID="+m_id+" and ATTEND_STATUS='출석' GROUP BY M_ID,CLASS_ID,ATTEND_STATUS) using(M_ID,CLASS_ID) "
				+ "left outer join (select CLASS_ID,M_ID,count(*) as 결석 from ATTENDANCE where M_ID="+m_id+" and ATTEND_STATUS='결석' GROUP BY M_ID,CLASS_ID,ATTEND_STATUS) using(M_ID,CLASS_ID) "
				+ "left outer join (select CLASS_ID,M_ID,count(*) as 조퇴 from ATTENDANCE where M_ID="+m_id+" and ATTEND_STATUS='조퇴' GROUP BY M_ID,CLASS_ID,ATTEND_STATUS) using(M_ID,CLASS_ID) "
				+ "left outer join (select CLASS_ID,M_ID,count(*) as 외출 from ATTENDANCE where M_ID="+m_id+" and ATTEND_STATUS='외출' GROUP BY M_ID,CLASS_ID,ATTEND_STATUS) using(M_ID,CLASS_ID) "
				+ "left outer join (select CLASS_ID,M_ID,count(*) as 지각 from ATTENDANCE where M_ID="+m_id+" and ATTEND_STATUS='지각' GROUP BY M_ID,CLASS_ID,ATTEND_STATUS) using(M_ID,CLASS_ID) ";
		List<ClassAttend> result = jdbcTemplate.query(sql,classAttendRowMapper);
		return result;

	}
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
						0
					);
				return beanClasses;				
			}
			
		},class_id);
		return result.isEmpty()?null:result.get(0);
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


	
	
	//jsh
	
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
	
	
	public List<Attendance> memberAttendList(PersonSearch command) {
		String sql = "select * from ATTENDANCE where M_ID = ? and CLASS_ID=? and ATTEND_DATE between ? and ? order by ATTEND_DATE desc";
		List<Attendance> results = 
				jdbcTemplate.query(sql, attendPersonRowMapper, command.getM_id(), command.getClass_id(), command.getFrom(), command.getTo());
		return results;
	} 
	

	
	
	

}
