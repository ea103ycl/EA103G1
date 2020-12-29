package com.painter_act.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PainterActDAO implements PainterActDAO_interface {

	
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

	private static final String INSERT_ACT_STMT = "INSERT INTO PAINTER_ACT(PTR_NO, MEM_ID, ACT_TYPE) VALUES(?, ?, ?)";
	private static final String DELETE_ACT_STMT = "DELETE PAINTER_ACT WHERE PTR_NO = ? AND MEM_ID = ? AND ACT_TYPE = ?";
	private static final String GET_PAINTER_ACT_LIST_BY_ACT_TYPE_STMT = "SELECT * FROM PAINTER_ACT WHERE PTR_NO = ? AND ACT_TYPE = ? ORDER BY ROWID";
	private static final String GET_ONE_PAINTER_ACT_BY_ACT_TYPE_STMT = "SELECT * FROM PAINTER_ACT WHERE PTR_NO = ? AND ACT_TYPE = ?  AND MEM_ID = ?";

	// update painter
	private static final String UPDATE_PAINTER_lIKE_CNT_STMT = "UPDATE PAINTER SET LIKE_CNT = LIKE_CNT + ? WHERE PTR_NO = ?";
	private static final String UPDATE_PAINTER_COL_CNT_STMT = "UPDATE PAINTER SET COL_CNT = COL_CNT + ? WHERE PTR_NO = ?";
	
	// executeType -1:�R�� 1:�s�W
	@Override
	public void update(Integer executeType, PainterActVO painterActVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			//�B�J1/5:�����۰�commit
			con.setAutoCommit(false);
			
			//�B�J2/5:���g�줬�ʩ��Ӥ�
			String executeSQL1 = ( executeType == 1 ? INSERT_ACT_STMT : DELETE_ACT_STMT );
			pstmt = con.prepareStatement(executeSQL1);
			pstmt.setInt(1, painterActVO.getPtr_no());
			pstmt.setString(2, painterActVO.getMem_id());
			pstmt.setInt(3, painterActVO.getAct_type());
			int executeCnt = pstmt.executeUpdate();
			System.out.println("���ʪ���Ƶ��� = " + executeCnt);

			//�B�J3/5:���ʩ��Ӧ���s�ɦA���ʤH�Ʀ^�@�~�D��
			if(executeCnt == 1 ) {
				String executeSQL2 = ( painterActVO.getAct_type() == 1 ? UPDATE_PAINTER_lIKE_CNT_STMT : UPDATE_PAINTER_COL_CNT_STMT );
				pstmt2 = con.prepareStatement(executeSQL2);
				pstmt2.setInt(1, executeType);
				pstmt2.setInt(2, painterActVO.getPtr_no());
				pstmt2.executeUpdate();
			}
			
			//�B�J4/5:���S���D�~commit
			con.commit();
			con.setAutoCommit(true);

		} catch (SQLException e) {
			//�B�J5/5:�p�GSQL���榳���D�hrollback
			if(con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
			
			if (pstmt2 != null) {
				try {
					pstmt2.close();
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
	}


	@Override
	public List<PainterActVO> getAllByActType(Integer ptr_no, Integer act_type) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PainterActVO> list = new ArrayList<PainterActVO>();
		PainterActVO painterActVO = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_PAINTER_ACT_LIST_BY_ACT_TYPE_STMT);
			pstmt.setInt(1, ptr_no);
			pstmt.setInt(2, act_type);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				painterActVO = new PainterActVO();
				painterActVO.setPtr_no(rs.getInt("ptr_no"));
				painterActVO.setMem_id(rs.getString("mem_id"));
				painterActVO.setAct_type(rs.getInt("act_type"));
				list.add(painterActVO);
			}

		} catch (SQLException e) {
			e.printStackTrace(System.err);
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

		return list;
	}

	@Override
	public PainterActVO getOneByActType(Integer ptr_no, Integer act_type, String mem_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PainterActVO painterActVO = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ONE_PAINTER_ACT_BY_ACT_TYPE_STMT);
			pstmt.setInt(1, ptr_no);
			pstmt.setInt(2, act_type);
			pstmt.setString(3, mem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				painterActVO = new PainterActVO();
				painterActVO.setPtr_no(rs.getInt("ptr_no"));
				painterActVO.setMem_id(rs.getString("mem_id"));
				painterActVO.setAct_type(rs.getInt("act_type"));
			}

		} catch (SQLException e) {
			e.printStackTrace(System.err);
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

		return painterActVO;
	}


}
