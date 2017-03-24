package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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
		
	public int count(int studentId ) {
		Integer count = jdbcTemplate.queryForObject(
				"select count(*) from attendance where m_id = ? ", Integer.class, studentId);
		System.out.println(count);
		return count;
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
				"select distinct subject_name from ATTENDANCE natural join subjects where m_id = ? ", 
				String.class, studentId);
		return result;
	}
	
	public double getAttendRate(int studentId){
		Integer studentAttend = jdbcTemplate.queryForObject(
				"select count(*) from (select * from attendance where m_id = ? and "
				+ "start_time between to_date((select  trunc(sysdate,'MM') from dual), 'YY/MM/DD') and "
				+ "to_date((select last_day(sysdate) from dual), 'YY/MM/DD')) "
				+ "where attend_status = '출석' or attend_status = '지각' or "
				+ "ATTEND_STATUS='외출' ",
				Integer.class, studentId);
		
		Integer mustAttend = jdbcTemplate.queryForObject("select count(*) from "
				+ "(select * from attendance where m_id = ?) where start_time "
				+ "between to_date((select  trunc(sysdate,'MM') from dual), 'YY/MM/DD') and "
				+ "to_date((select last_day(sysdate) from dual), 'YY/MM/DD') ",
				Integer.class, studentId);
		
		double result = ((double) studentAttend / (double) mustAttend) * 100;
		return result;
	}
	
	public List<AttendancePersonCommand> searchPersonPeriod (int studentId) {
		String sql = "select * from attendance natural join subjects natural join member where m_id = ? ";
		List<AttendancePersonCommand> results = jdbcTemplate.query(sql, attendPersonRowMapper, studentId);
		return results;
	} 
}


//select * from (select * from attendance where attend_status = '출석' or attend_status = '지각' or
//ATTEND_STATUS='외출') natural join subjects natural join member where m_id = 5 and start_time 
//between to_date('2017/03/01', 'YY/MM/DD') and 
//to_date('2017/03/31', 'YY/MM/DD');