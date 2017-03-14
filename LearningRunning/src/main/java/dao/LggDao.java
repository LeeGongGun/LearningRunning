package dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;



public class LggDao{
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
