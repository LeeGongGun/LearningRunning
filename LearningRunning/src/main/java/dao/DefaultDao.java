package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import bean.AuthMember;
import command.RegisterCommand;

public class DefaultDao {
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	private RowMapper<AuthMember> member2RowMapper = new RowMapper<AuthMember>() {
		@Override
		public AuthMember mapRow(ResultSet rs, int rowNum) throws SQLException {
			AuthMember beanMember = new AuthMember(
					rs.getInt("M_ID"),
					rs.getString("M_EMAIL"),
					rs.getString("M_NAME"),
					rs.getString("M_PASS"),
					rs.getString("M_IMAGE"),
					rs.getString("M_APP_U_NO")
				);
			return beanMember;
		}		
	};
	public AuthMember memberByEmailAndPass(String email, String password) {
		String sql = "select * from  MEMBER  "
				+ "where m_email = ? and m_pass = ?";
		List<AuthMember> result = jdbcTemplate.query(sql,member2RowMapper,email,password);
		return result.isEmpty()?null:result.get(0);

	}
	public List<String> memberAuth(int m_id) {
		String sql = "select AUTH_ENAME from  MEMBER_AUTH  "
				+ "where m_id = ? ";
		List<String> result = jdbcTemplate.query(sql,new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String beanMember = new String(
						rs.getString("AUTH_ENAME")
					);
				return beanMember;
			}		

		},m_id);
		return result;
		
	}
	public AuthMember selectMemberByEmail(String email) {
		String sql = "select * from  MEMBER  "
				+ "where m_email = ?";
		List<AuthMember> result = jdbcTemplate.query(sql,member2RowMapper,email);
		return result.isEmpty()?null:result.get(0);

	}
	
	public int memberRegister(RegisterCommand command) {
		return jdbcTemplate.update(" INSERT INTO MEMBER "
				+ "(M_ID,M_EMAIL,M_NAME,M_PASS) "
				+ " VALUES(SEQUENCE_MEMBER.NEXTVAL,?,?,?) ",
				command.getM_email(),
				command.getM_name(),
				command.getM_pass()
				);
	}

}
