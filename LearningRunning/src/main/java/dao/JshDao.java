package dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import bean.PersonSubList;
import command.AttendancePersonCommand;



public class JshDao{
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);	
	}
	
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
	public List<PersonSubList> selectSubPerson(int studentId) {
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