package com.follow.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.lv.model.LvService;
import com.painter.model.PainterVO;

public class FollowDAO implements FollowDAO_interface{
	
	private static String INSERT_STMT = "INSERT INTO FOLLOW VALUES(?, ?)";
	private static String DELETE_STMT = "DELETE FROM FOLLOW WHERE MEM_ID = ? AND F_MEM_ID = ? ";
	private static String GET_CNT_STMT = "SELECT COUNT(1) FROM FOLLOW WHERE MEM_ID = ? AND F_MEM_ID = ? ";
	private static String GET_ONE_STMT = "SELECT * FROM FOLLOW WHERE MEM_ID = ? AND F_MEM_ID = ? ";
	private static String GET_ALL_FOLLOW_STMT = "SELECT * FROM FOLLOW WHERE MEM_ID = ? ";
	private static String GET_ALL_FANS_STMT = "SELECT * FROM FOLLOW WHERE F_MEM_ID = ? ";
	private static String GET_FOLLOW_CNT_STMT = "SELECT COUNT(1) AS CNT FROM FOLLOW WHERE MEM_ID = ? ";
	private static String GET_FANS_CNT_STMT = "SELECT COUNT(1) AS CNT FROM FOLLOW WHERE F_MEM_ID = ? ";
	private static String UPDATE_MEMBERS_LV_STMT = "UPDATE MEMBERS SET LV = ? WHERE MEM_ID = ? ";
	
	// �@�����ε{����,�w��@�Ӹ�Ʈw ,�@�Τ@��DataSource�Y�i
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

	@Override
	public void update(FollowVO followVO) {
		
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rs = null;
		
		try {

//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			//1.���P�_�O�_���ۦP��Ʀs�b
			pstmt1 = con.prepareStatement(GET_CNT_STMT);
			pstmt1.setString(1, followVO.getMem_id());
			pstmt1.setString(2, followVO.getF_mem_id());
			rs = pstmt1.executeQuery();
			
			int cnt = 0;
			while (rs.next()) {
				cnt = rs.getInt(1);
			}
			System.out.println("�|�� =" + followVO.getMem_id() + " �l�� " + followVO.getF_mem_id() + " ����Ƶ��� = " + cnt);
			
			//2.�Y�L�ۦP��Ʀs�b�h�s�W���,�_�h�R�����
			String executeSQL = (cnt == 0 ? INSERT_STMT : DELETE_STMT);
			System.out.println("���檺SQL�� = " + executeSQL);
			
			//3.�]���|�s�ʷ|����ơA�ҥH���ק�commit�Ҧ�
			con.setAutoCommit(false);
			
			//4.���ʰl����
			pstmt2 = con.prepareStatement(executeSQL);
			pstmt2.setString(1, followVO.getMem_id());
			pstmt2.setString(2, followVO.getF_mem_id());
			pstmt2.executeUpdate();
			System.out.println("���ʰl���ɧ���");
			
			//5.���o�l�ܼ�
			pstmt3 = con.prepareStatement(GET_FANS_CNT_STMT);
			pstmt3.setString(1, followVO.getF_mem_id());
			rs = pstmt3.executeQuery();
			int newFansCnt = 0;
			while (rs.next()) {
				newFansCnt = rs.getInt(1);
			}
			System.out.println( followVO.getF_mem_id() + "���̷s�����Ƶ��� = " + newFansCnt);
			
			//6.���o�s�l�ܼƬ۹悔�{�O����
			LvService lvService = new LvService();
			Integer newLv = lvService.getLvByFansCnt(newFansCnt).getLv();
			System.out.println( followVO.getF_mem_id() + "���̷s���{�O���� = " + newLv);
			
			//7.���ʷ|���ɤW�����{�O����
			pstmt4 = con.prepareStatement(UPDATE_MEMBERS_LV_STMT);
			pstmt4.setInt(1, newLv);
			pstmt4.setString(2, followVO.getF_mem_id());
			pstmt4.executeUpdate();
			System.out.println( "����" + followVO.getF_mem_id() + "�����{�O����");
			
			//8.�N���ʸ��commit�ë�_autoCommit
			con.commit();
			con.setAutoCommit(true);
			System.out.println( "commit��Ƨ���");

		} catch (SQLException se) {
			//9.�����h����rollback
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
		} finally {
			
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			
			if (pstmt4 != null) {
				try {
					pstmt4.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if (pstmt3 != null) {
				try {
					pstmt3.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if (pstmt2 != null) {
				try {
					pstmt2.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if (pstmt1 != null) {
				try {
					pstmt1.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
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
		
	}

	@Override
	public Integer getCnt(String mem_id, String f_mem_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer cnt = 0;
		
		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_CNT_STMT);
			pstmt.setString(1, mem_id);
			pstmt.setString(2, f_mem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
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

		return cnt;
		
	}
	
	@Override
	public FollowVO getOne(String mem_id, String f_mem_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FollowVO followVO = null;
		
		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, mem_id);
			pstmt.setString(2, f_mem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				followVO = new FollowVO();
				followVO.setMem_id(rs.getString("mem_id"));
				followVO.setF_mem_id(rs.getString("f_mem_id"));
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
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

		return followVO;
		
	}
	

	@Override
	public List<FollowVO> getFollowList(String mem_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FollowVO> list = new ArrayList<FollowVO>();
		FollowVO followVO = null;
		
		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ALL_FOLLOW_STMT);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				followVO = new FollowVO();
				followVO.setMem_id(rs.getString("mem_id"));
				followVO.setF_mem_id(rs.getString("f_mem_id"));
				list.add(followVO);
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
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

		return list;
	}

	@Override
	public List<FollowVO> getFansList(String f_mem_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FollowVO> list = new ArrayList<FollowVO>();
		FollowVO followVO = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ALL_FANS_STMT);
			pstmt.setString(1, f_mem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				followVO = new FollowVO();
				followVO.setMem_id(rs.getString("mem_id"));
				followVO.setF_mem_id(rs.getString("f_mem_id"));
				list.add(followVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
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

		return list;
	}

	@Override
	public Integer getFollowCnt(String mem_id) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer cnt = 0;

		try {

//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_FOLLOW_CNT_STMT);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cnt = rs.getInt("cnt");
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
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

		return cnt;
		
	}

	@Override
	public Integer getFansCnt(String mem_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer cnt = 0;

		try {

//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_FANS_CNT_STMT);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cnt = rs.getInt("cnt");
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
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

		return cnt;
	}

	
//	public static void main(String args[]) {
//		FollowDAO dao = new FollowDAO();
//		String testMem_id = "M000003";
//		List<FollowVO> list = new ArrayList<FollowVO>();
//		FollowVO followVO= null;
//		
//		
//		System.out.println("�l�ܹ�H�H��:" + dao.getFollowCnt(testMem_id));
//		list = dao.getFollowList(testMem_id);
//		for(FollowVO vo : list) {
//			System.out.println(vo.getF_mem_id());
//		}
//		
//		System.out.println("�����H��:" + dao.getFansCnt(testMem_id));		
//		list = dao.getFansList(testMem_id);
//		for(FollowVO vo : list) {
//			System.out.println(vo.getMem_id());
//		}
//		
//		System.out.println("======================����UPDATE FOLLOW");
//		followVO = new FollowVO();
//		followVO.setMem_id("M000003");
//		followVO.setF_mem_id("M000003");
//		dao.update(followVO);
//		
//	}
	
	
}
