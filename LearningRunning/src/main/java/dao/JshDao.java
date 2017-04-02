package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import bean.Attendance;
import bean.Classes;
import bean.CounselManager;
import bean.PersonSubList;
import command.AttendancePersonCommand;

public class JshDao {
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper<CounselManager> counselMngRowMapper = new RowMapper<CounselManager>() {
		@Override
		public CounselManager mapRow(ResultSet rs, int rowNum) throws SQLException {
			CounselManager counselManager = new CounselManager(
					rs.getInt("student_id"),
					rs.getInt("m_id"),
					rs.getString("class_name"),
					rs.getString("subject_title"),				  
					rs.getString("counsel_content"),
					rs.getString("manager"),
					rs.getString("student_name"),
					rs.getDate("counsel_date")
			);
			return counselManager;
		}		
	};
	
	public List<CounselManager> getCounselAll(int teacherId) {
		String sql = "select * from COUNSEL_STUDENT where m_id = ? ";
		List<CounselManager> results = 
				jdbcTemplate.query(sql, counselMngRowMapper, teacherId);
		return results;
	}
}