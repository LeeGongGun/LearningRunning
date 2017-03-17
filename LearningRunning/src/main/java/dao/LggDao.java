package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import bean.Attendance;
import bean.Subject;



public class LggDao{
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	private RowMapper<Attendance> attendanceRowMapper = new RowMapper<Attendance>() {
		@Override
		public Attendance mapRow(ResultSet rs, int rowNum) throws SQLException {
			Attendance beanAttendance = new Attendance(
					rs.getInt("subject_id"),
					rs.getInt("m_id"),
					rs.getString("m_name"),
					rs.getDate("start_time"),
					rs.getDate("end_time"),
					rs.getDate("stop_time"),
					rs.getDate("restart_time"),
					rs.getString("attendance_status")
			);
			return beanAttendance;
		}		
	};
	private RowMapper<Subject> subjectRowMapper = new RowMapper<Subject>() {
		@Override
		public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
			Subject beanSubject = new Subject(
					rs.getInt("SUBJECT_ID"),
					rs.getString("SUBJECT_NAME"),
					rs.getDate("SUBJECT_START"),
					rs.getDate("SUBJECT_END"),
					rs.getString("SUBJECT_STATE"),
					rs.getString("SUBJECT_COMMENT"),
					rs.getInt("STUDENT_COUNT")
				);
			return beanSubject;
		}		
	};
	
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Attendance> tempAttendanceList(int subjectId) {
		String sql = "select * from TEMP_ATTENDANCE "
				+ "natural join MEMBER  where SUBJECT_ID = ? ";
		List<Attendance> result = jdbcTemplate.query(sql,attendanceRowMapper,subjectId);
		return result;
	}
	public List<Subject> teachersSubject(int teacherId) {
		String sql = "select * "
				+ "from (select SUBJECT_ID from TEACHER_SUBJECT where M_ID = ?) "
				+ "natural join SUBJECTS "
				+ "natural join (select SUBJECT_ID,count(*) as STUDENT_COUNT from STUDENT_SUBJECT GROUP BY SUBJECT_ID) ";
		List<Subject> result = jdbcTemplate.query(sql,subjectRowMapper,teacherId);
		return result;
	}

}
