package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import bean.ClassList;



public class KswDao{
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private RowMapper<ClassList> classListRowMapper = new RowMapper<ClassList>() {
		
		@Override
		public ClassList mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassList classList = new ClassList();
			return classList;
		}
	};

	public int count() {
		Integer count = jdbcTemplate.queryForObject(
				"select count(*) from board ", Integer.class);
		return count;
	}

	public List<ClassList> getAllClass(){
		List<ClassList> results = jdbcTemplate.query(
				"SELECT * FROM SUBJECTS ", 
				classListRowMapper);	
				/*
				"SELECT SUBJECT_NAME, SUBJECT_START, SUBJECT_END, SUBJECT_STATE, SUBJECT_STATE FROM SUBJECTS ", 
				new RowMapper<ClassList>(){

					@Override
					public ClassList mapRow(ResultSet rs, int rowNum) throws SQLException {
						ClassList classList = new ClassList(
								rs.getString("className"),
								rs.getDate("classStartDate"),
								rs.getDate("classEndDate"),
								rs.getString("classTeacher"),
								rs.getString("classStat"));
						return classList;
					}
					
				});	
				*/	
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
	public List<ClassList> selectPage(String srch, 
			int startPage, int limit) {
		List<ClassList> results;
		if(srch == null || srch.equals("")){
			results = jdbcTemplate.query(
				"select num, (select name from member where id = writer)"
				+ "writer, subject, content, file, re_ref, re_lev, "
				+ "re_seq, readcount, regdate from board limit ?,? ",
				classListRowMapper, startPage, limit);
		} else {
			results = jdbcTemplate.query(
				"select num, (select name from member where id = writer)"
				+ "writer, subject, content, file, re_ref, re_lev, "
				+ "re_seq, readcount, regdate from board where( "
				+ "subject like '%?%' or "
				+ "content like '%?%' or "
				+ "writer like '%?%' ) limit ?,?", 
				classListRowMapper, srch, srch, srch, startPage, limit);
		}
		return results;
	}
	public void boardDelete(ClassList classList) {
		jdbcTemplate.update(
				"delete from board where num = ?", 
				classList.getNum());
	}	
}
