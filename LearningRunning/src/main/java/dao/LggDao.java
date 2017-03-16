package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import bean.Attendance;



public class LggDao{
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	private RowMapper<Attendance> attendanceRowMapper = new RowMapper<Attendance>() {
		@Override
		public Attendance mapRow(ResultSet rs, int rowNum) throws SQLException {
			Attendance beanAttendance = new Attendance(
					rs.getInt("subject_id"),
					rs.getInt("m_id"),
					rs.getDate("start_time"),
					rs.getDate("end_time"),
					rs.getDate("stop_time"),
					rs.getDate("restart"),
					rs.getString("a_status")
			);
			return beanAttendance;
		}		
	};
	
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public int testConn() {
		
		List<Integer> result = jdbcTemplate.query("SELECT count(*) FROM member ",
				new RowMapper<Integer>(){
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						Integer cnt = rs.getInt(1);
						return cnt;
					}
				});
		return result.isEmpty()?0:result.get(0);
	}
	public List<Attendance> tempAttendanceList(int subject_id) {
		String sql = "select * from temp_attendance where SUBJECT_ID = ? ";
		List<Attendance> result = jdbcTemplate.query(sql,attendanceRowMapper,subject_id);
		return result;
	}

}
