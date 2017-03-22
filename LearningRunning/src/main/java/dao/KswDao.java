package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import bean.ClassList;
import command.ClassListCommand;



public class KswDao{
	
	public KswDao() {
		super();
		// TODO Auto-generated constructor stub
	}

	private JdbcTemplate jdbcTemplate;
	
	public KswDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private RowMapper<ClassList> boardRowMapper = new RowMapper<ClassList>() {
		@Override
		public ClassList mapRow(ResultSet rs, int rowNum) 
				throws SQLException {
			ClassList list = new ClassList(
					rs.getInt("subject_id"),
					rs.getString("subject_name"),
					rs.getDate("subject_start"),
					rs.getDate("subject_end"),
					rs.getString("subject_state"),
					rs.getString("subject_comment"));
			return list;
		}
	};

	public int count() {
		Integer count = jdbcTemplate.queryForObject(
				"select count(*) from board ", Integer.class);
		return count;
	}

	public List<ClassList> getAllClass(){
		List<ClassList> results = jdbcTemplate.query(
				"select * from subjects ", boardRowMapper);
		return results;
	}

	public int countPage(String srch) {
		Integer count;
		if(srch == null || srch.equals("")){
			count = jdbcTemplate.queryForObject(
				"select count(*) from board ", 
				Integer.class);
		} else {
			count = jdbcTemplate.queryForObject(
				"select count(*) from board where( "
				+ "subject like '%?%' or "
				+ "content like '%?%' or "
				+ "writer like '%?%' ) ", 
				Integer.class, srch, srch, srch);
		}
		return count;
	}
	
}
