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
			AttendancePersonCommand attendancePersonCommand = new AttendancePersonCommand(rs.getInt("m_id"), 
					rs.getString("m_name"), 
					rs.getString("subject_name"),
					rs.getDate("subject_start"), 
					rs.getDate("subject_end"),
					rs.getString("subject_comment"), 
					rs.getString("subject_status"),
					rs.getInt("attendRate"));
			return attendancePersonCommand;
		}
	};	
	
	public int count(AttendancePersonCommand attendancePersonCommand) {
		Integer count = jdbcTemplate.queryForObject(
				"select count(*) from attendance where m_id = '1' ", Integer.class);
		return count;
	}
	
	public AttendancePersonCommand selectAllPerson() {
		List<AttendancePersonCommand> results = jdbcTemplate.query(
				"select * from STUDENT_SUBJECT natural join subjects natural join member ", attendPersonRowMapper);
		return results.isEmpty()?null: results.get(0);
	}
}
