package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import bean.Classes;
import bean.TempAttendance;
import command.AttendanceInsertCommand;

public class TeacherDao {
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	private RowMapper<Classes> classRowMapper = new RowMapper<Classes>() {
		@Override
		public Classes mapRow(ResultSet rs, int rowNum) throws SQLException {
			Classes beanClasses = new Classes(
					rs.getInt("CLASS_ID"),
					rs.getString("CLASS_NAME"),
					rs.getDate("CLASS_START"),
					rs.getDate("CLASS_END"),
					rs.getString("CLASS_STATE"),
					rs.getString("CLASS_COMMENT"),
					rs.getInt("STUDENT_COUNT")
				);
			return beanClasses;
		}		
	};
	private RowMapper<TempAttendance> tempAttendanceRowMapper = new RowMapper<TempAttendance>() {
		@Override
		public TempAttendance mapRow(ResultSet rs, int rowNum) throws SQLException {
			TempAttendance beanAttendance = new TempAttendance(
					rs.getInt("TEMP_ID"),
					rs.getInt("ClASS_ID"),
					rs.getInt("M_ID"),
					rs.getDate("CHECK_TIME"),
					rs.getString("STATUS")
					
			);
			return beanAttendance;
		}		
	};

	
	public List<Classes> teachersClasses(int teacherId) {
		String sql = "select * "
				+ "from (select CLASS_ID from MEMBER_CLASS where M_ID = ?) "
				+ "natural join CLASSES "
				+ "left outer join (select CLASS_ID,count(*) as STUDENT_COUNT from MEMBER_CLASS where AUTH_ENAME='student' GROUP BY AUTH_ENAME,CLASS_ID) using(CLASS_ID)";
		List<Classes> result = jdbcTemplate.query(sql,classRowMapper,teacherId);
		return result;
	}
	public List<TempAttendance> tempAttendanceList(int class_id) {
		String sql = "select * from (select * from TEMP_ATTENDANCE where CLASS_ID = ? ) "
				+ "natural join MEMBER  ";
		List<TempAttendance> result = jdbcTemplate.query(sql,tempAttendanceRowMapper,class_id);
		return result;
	}
	public int attendInsert(AttendanceInsertCommand command,int class_id) {
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
		String sql = "update TEMP_ATTENDANCE set " + cName + " = ? where CLASS_ID = ? and M_ID IN ("+inSql+") and "+cName+" IS NULL ";
		return jdbcTemplate.update(sql, command.getTime(),class_id);
	}

}
