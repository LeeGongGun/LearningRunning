package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import bean.Attendance;
import bean.Subject;
import command.AttendanceInsertCommand;
import command.SubjectSearchCommand;



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
					rs.getTimestamp("start_time"),
					rs.getTimestamp("end_time"),
					rs.getTimestamp("stop_time"),
					rs.getTimestamp("restart_time"),
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

	public int attendInsert(AttendanceInsertCommand command,int subjectId) {
		String state = command.getState();
		String cName = "";
		if (state.equals("start")) {
			cName = "START_TIME";
		}else if (state.equals("stop")) {
			cName = "STOP_TIME";
		}else if (state.equals("restart")) {
			cName = "RESTART_TIME";
		}else if (state.equals("end")) {
			cName = "END_TIME";
		}else {
			return 0;
		}
		String inSql = "";
		String[] ids = command.getAttendanceCheck();
		for (int i = 0; i < ids.length; i++) {
			if (i!=0) inSql += ",";
			inSql += ids[i];
		}
		String sql = "update TEMP_ATTENDANCE set " + cName + " = ? where SUBJECT_ID = ? and M_ID IN ("+inSql+") and "+cName+" IS NULL ";
		return jdbcTemplate.update(sql, command.getTime(),subjectId);
	}

	public List<Subject> subjectList(SubjectSearchCommand command) {
		String whereSql ="";
		int tmp=0;
		if(command.getSearchText()!=null){
			whereSql += (tmp==0)?" where ":" and ";
			tmp++;
			whereSql += " SUBJECT_NAME= ? ";
		}
		if(command.getState().length > 0){
			String[] ids = command.getState();
			String inSql="";
			for (int i = 0; i < ids.length; i++) {
				if (i!=0) inSql += ",";
				inSql += ids[i];
			}
			whereSql += (tmp==0)?" where ":" and ";
			tmp++;
			whereSql += " SUBJECT_STATE in ("+inSql+") ";
		}
		
		System.out.println(whereSql);
		String sql = "select * "
				+ "from  SUBJECTS "
				+ "natural join (select SUBJECT_ID,count(*) as STUDENT_COUNT from STUDENT_SUBJECT GROUP BY SUBJECT_ID) "
				+ whereSql;
		List<Subject> result = jdbcTemplate.query(sql,subjectRowMapper,command.getSearchText());
		return result;
	}

}
