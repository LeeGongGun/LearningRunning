package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import controller.Board;


public class BoardDao{
	private JdbcTemplate jdbcTemplate;
	private DataSource ds;
//	private RowMapper<Board> boardRowMapper = new RowMapper<Board>() {
//		@Override
//		public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
//			Board beanBoard = new Board(
//					rs.getInt("num"), 
//					rs.getString("writer"), 
//					rs.getString("subject"), 
//					rs.getString("content"), 
//					rs.getString("file"), 
//					rs.getInt("re_ref"), 
//					rs.getInt("re_lev"), 
//					rs.getInt("re_seq"), 
//					rs.getInt("readcount"), 
//					rs.getDate("regdate"), 
//					rs.getInt("owner"), 
//					rs.getString("ip")
//			);
//			return beanBoard;
//		}		
//	};
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public int getListCount(String searchText) {
		String whereSql = "";
		if(searchText!=null && searchText!=""){ 
			whereSql = "WHERE subject like '%"+searchText+"%' "
					+ "or content like '%"+searchText+"%' "
					+ "or writer like '%"+searchText+"%' ";
		}
		List<Integer> result = jdbcTemplate.query("SELECT count(*) FROM BOARD " + whereSql,
				new RowMapper<Integer>(){
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						Integer cnt = rs.getInt(1);
						return cnt;
					}
				});
		return result.isEmpty()?0:result.get(0);
	}
	public List<Board> getBoardList(String searchText,int page, int limit) {
		String whereSql = "";
		if(searchText!=null && searchText!=""){ 
			whereSql = "WHERE subject like '%"+searchText+"%' "
					+ "or content like '%"+searchText+"%' "
					+ "or writer like '%"+searchText+"%' ";
		}
		String sql = "SELECT * FROM BOARD "
				+ whereSql
				+ "ORDER BY BOARD.RE_REF DESC,BOARD.RE_SEQ "
				+ "LIMIT ?,? ";
		int startRow  = (page-1) * limit ;

		List<Board> result = jdbcTemplate.query(sql,boardRowMapper,startRow,limit);
		return result;

	}
	public Board getDetail(Long num) {
		String sql = "select * from board "
				+ "where num =? ";
		List<Board> result = jdbcTemplate.query(sql,boardRowMapper,num);
		return result.isEmpty()?null:result.get(0);

	}
	@Transactional
	public int insertBoard(final Board bb) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int result  = jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt =  con.prepareStatement(" INSERT INTO BOARD "
						+ "(WRITER,SUBJECT,CONTENT,FILE,OWNER,IP) "
						+ " VALUES(?,?,?,?,?,?) ",new String[]{"NUM"});
				pstmt.setString(1, bb.getWriter());
				pstmt.setString(2, bb.getSubject());
				pstmt.setString(3, bb.getContent());
				pstmt.setString(4, bb.getFileName());
				pstmt.setInt(5, bb.getOwner());
				pstmt.setString(6, bb.getIp());
				return pstmt;
			}
		},keyHolder);
		int keyValue = keyHolder.getKey().intValue();
		jdbcTemplate.update("UPDATE BOARD SET RE_REF=? WHERE NUM = ? ",keyValue,keyValue);
		bb.setNum(keyValue);
		return result;
	}
	@Transactional
	public int replyBoard(final Board bb) {
		jdbcTemplate.update("UPDATE BOARD SET RE_SEQ =  RE_SEQ+1 WHERE RE_REF=? AND RE_SEQ > ? ",
				bb.getRe_ref(),
				bb.getRe_seq()
		);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int result  = jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt =  con.prepareStatement(" INSERT INTO BOARD "
						+ "(WRITER,SUBJECT,CONTENT,FILE,RE_REF,RE_LEV,RE_SEQ,OWNER,IP) "
						+ " VALUES(?,?,?,?,?,?,?,?,?) ",new String[]{"NUM"});
						pstmt.setString(1, bb.getWriter());
						pstmt.setString(2, bb.getSubject());
						pstmt.setString(3, bb.getContent());
						pstmt.setString(4, bb.getFileName());
						pstmt.setInt(5, bb.getRe_ref());
						pstmt.setInt(6, bb.getRe_lev()+1);
						pstmt.setInt(7, bb.getRe_seq()+1);
						pstmt.setInt(8, bb.getOwner());
						pstmt.setString(9, bb.getIp());
						return pstmt;
				}
			},keyHolder);
		int keyValue = keyHolder.getKey().intValue();
		bb.setNum(keyValue);
		return keyValue;
	}
	
	public int deleteBoard(Long boardNum) {
		return jdbcTemplate.update("DELETE FROM BOARD WHERE NUM = ? ",boardNum);
	}
	public int updateBoard(Board bb) {
		return jdbcTemplate.update("UPDATE BOARD SET "
					+ "WRITER = ? ,"
					+ "SUBJECT = ? ,"
					+ "CONTENT = ? "
					+ "where NUM= ? ",
					bb.getWriter(),
					bb.getSubject(),
					bb.getContent(),
					bb.getNum()
				);
	}
	public int updateReadCount(Long num) {
		return jdbcTemplate.update("UPDATE BOARD SET READCOUNT = READCOUNT+1 WHERE NUM=? ",num);
	}	
	public boolean isBoardWrite(Long num,String requester) {
		String writer = jdbcTemplate.queryForObject("SELECT WRITER FROM BOARD WHERE NUM = ? ",String.class, num);
		return writer==requester;
	}

}
