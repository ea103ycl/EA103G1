package com.painter_tag.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PainterTagDAO implements PainterTagDAO_interface {

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G1");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

//	String driver = "oracle.jdbc.driver.OracleDriver";
//	String url = "jdbc:oracle:thin:@localhost:1521:XE";
//	String userid = "G1";
//	String passwd = "123456";

	private static final String GET_PAINTER_TAG_NO_STMT = "SELECT TAG_NO FROM PAINTER_TAG WHERE TAG_DESC = ? ";
	private static final String INSERT_PAINTER_TAG_STMT = "INSERT INTO PAINTER_TAG(TAG_NO, TAG_DESC) VALUES (PAINTER_TAG_SEQ.NEXTVAL, ?　)　";
	private static final String GET_ONE_BY_TAG_NO_STMT = "SELECT * FROM PAINTER_TAG WHERE TAG_NO = ? ";

	private Integer tag_no; // Hashtag流水號
	private String tag_desc; // hashtag內容

	public Integer findTagNo(String tagDesc) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer tag_no = 0;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_PAINTER_TAG_NO_STMT);
			pstmt.setString(1, tagDesc);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tag_no = rs.getInt("tag_no");
			}

		} catch (SQLException e) {
			throw new RuntimeException("database error" + e.getMessage());
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return tag_no;
	}

	@Override
	public Integer insert(String tagDesc) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer tag_no = 0;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			tag_no = findTagNo(tagDesc);

			if (0 == tag_no) { // 已經存在的tag不重複儲存

				String[] cols = { "tag_no" }; // insert後要取回的欄位
				pstmt = con.prepareStatement(INSERT_PAINTER_TAG_STMT, cols);
				pstmt.setString(1, tagDesc);
				pstmt.executeUpdate();

				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					tag_no = rs.getInt(1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException("database error" + e.getMessage());
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return tag_no;
	}

	
	@Override
	public PainterTagVO getOneByTagNo(Integer tag_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PainterTagVO painterTagVO = null;
		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ONE_BY_TAG_NO_STMT);
			pstmt.setInt(1, tag_no);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				painterTagVO = new PainterTagVO();
				painterTagVO.setTag_no(rs.getInt("tag_no"));
				painterTagVO.setTag_desc(rs.getString("tag_desc"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException("database error" + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return painterTagVO;

	}
	
}
