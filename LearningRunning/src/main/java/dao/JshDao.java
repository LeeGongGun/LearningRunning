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
					rs.getInt("M_ID"), 
					rs.getInt("SUBJECT_id"),
					rs.getString("SS_STATUS"),
					rs.getDate("SS_DATE"),	
					rs.getString("SUBJECT_NAME"),
					rs.getDate("SUBJECT_START"), 
					rs.getDate("SUBJECT_END"),
					rs.getString("SUBJECT_STATE"),
					rs.getString("SUBJECT_COMMENT"), 
					rs.getString("M_EMAIL"),
					rs.getString("M_NAME"),
					rs.getInt("M_PASS"),
					rs.getString("M_APP_U_NO"));
			return attendancePersonCommand;
		}
	};	
	
	public int count(AttendancePersonCommand attendancePersonCommand) {
		Integer count = jdbcTemplate.queryForObject(
				"select count(*) from attendance where m_id = 1 ", Integer.class);
		System.out.println(count);
		return count;
	}
	
	public AttendancePersonCommand selectAllPerson() {
		List<AttendancePersonCommand> results = jdbcTemplate.query(
				"select * from student_subject natural join subjects natural join member ", attendPersonRowMapper);
		return results.isEmpty()?null: results.get(0);
	}
}
