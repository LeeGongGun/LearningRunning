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
}
