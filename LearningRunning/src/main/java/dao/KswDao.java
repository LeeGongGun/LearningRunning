package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import bean.ClassList;



public class KswDao{
	private JdbcTemplate jdbcTemplate;
	
	/*
	public KswDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	*/
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

	public ClassList selectByNum(Long num){
		List<ClassList> results = jdbcTemplate.query(
				"SELECT * FROM ATTENDANCE WHERE ATTEND_ID=? ", 
				classListRowMapper, num);
		return results.isEmpty()?null: results.get(0);
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
	public ClassList selectByWriter(Long id) {
		ClassList classList = new ClassList();
		return classList;
	}	
}
