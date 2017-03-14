package dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;



public class KymDao{
	private JdbcTemplate jdbcTemplate;
	private DataSource ds;
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
