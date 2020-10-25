package com.prod.model;


	import java.io.InputStream;
import java.sql.*;
	import java.util.*;

	import javax.naming.Context;
	import javax.naming.InitialContext;
	import javax.naming.NamingException;
	import javax.sql.DataSource;

	public class ProdJNDIDAO  implements ProdDAO_interface {
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

		private static final String INSERT_STMT = "INSERT INTO PRODUCT(PROD_NO, PTR_NO, MA_NO, PROD_NAME, PROD_PRICE, PROD_DETAIL ,PROD_STATUS, PROD_PIC )   VALUES(PROD_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?)";
		private static final String UPDATE = "UPDATE PRODUCT SET  PTR_NO=?, MA_NO=?, PROD_NAME=?, PROD_PRICE=?, PROD_DETAIL=?, PROD_STATUS=?, PROD_PIC=? WHERE PROD_NO=?";
		private static final String GET_ALL_STMT = "SELECT * FROM PRODUCT ORDER BY PROD_NO";
		private static final String GET_ONE_STMT = "SELECT * FROM PRODUCT WHERE PROD_NO = ?";
		private static final String DELETE = "DELETE FROM PRODUCT WHERE PROD_NO = ?";
				

		@Override
		public void insert(ProdVO prodVO) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setInt(1,    prodVO.getPtr_no());
				pstmt.setString(2,    prodVO.getMa_no());
				pstmt.setString(3, prodVO.getProd_name());
				pstmt.setInt(4,    prodVO.getProd_price());
				pstmt.setString(5, prodVO.getProd_detail());
				pstmt.setInt(6, prodVO.getProd_status());
				pstmt.setBytes(7,  prodVO.getProd_pic());
				
				pstmt.executeUpdate();

				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
				// Clean up JDBC resources
			} finally {
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
		}

		@Override
		public void update(ProdVO prodVO) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setInt(1,    prodVO.getPtr_no());
				pstmt.setString(2,    prodVO.getMa_no());
				pstmt.setString(3, prodVO.getProd_name());
				pstmt.setInt(4,    prodVO.getProd_price());
				pstmt.setString(5, prodVO.getProd_detail());
				pstmt.setInt(6,    prodVO.getProd_status());
				pstmt.setBytes(7,  prodVO.getProd_pic());
				pstmt.setInt(8,    prodVO.getProd_no());

				pstmt.executeUpdate();

			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
				// Clean up JDBC resources
			} finally {
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

		}

		@Override
		public void delete(Integer prod_no) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(DELETE);

				pstmt.setInt(1, prod_no);

				pstmt.executeUpdate();

				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
				// Clean up JDBC resources
			} finally {
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

		}

		@Override
		public ProdVO findByPrimaryKey(Integer prod_no) {
			ProdVO prodVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setInt(1, prod_no);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					prodVO = new ProdVO();
					prodVO.setProd_no(rs.getInt("prod_no"));
					prodVO.setPtr_no(rs.getInt("ptr_no"));
					prodVO.setMa_no(rs.getString("ma_no"));
					prodVO.setProd_name(rs.getString("prod_name"));
					prodVO.setProd_price(rs.getInt("prod_price"));
					prodVO.setProd_detail(rs.getString("prod_detail"));
					prodVO.setProd_status(rs.getInt("prod_status"));
					prodVO.setProd_pic(rs.getBytes("prod_pic"));
					
				}

				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
				// Clean up JDBC resources
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
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
			return prodVO;
		}

		@Override
		public List<ProdVO> getAll() {
			List<ProdVO> list = new ArrayList<ProdVO>();
			ProdVO prodVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					prodVO = new ProdVO();
					prodVO.setProd_no(rs.getInt("prod_no"));
					prodVO.setPtr_no(rs.getInt("ptr_no"));
					prodVO.setMa_no(rs.getString("ma_no"));
					prodVO.setProd_name(rs.getString("prod_name"));
					prodVO.setProd_price(rs.getInt("prod_price"));
					prodVO.setProd_detail(rs.getString("prod_detail"));
					prodVO.setProd_status(rs.getInt("prod_status"));
					prodVO.setProd_pic(rs.getBytes("prod_pic"));
					list.add(prodVO);
				}
				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
				// Clean up JDBC resources
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
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
		public List<ProdVO> FuzzySearch(String prod_name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void changeStatus(ProdVO prodVO) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public List<ProdVO> getAll_status() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ProdVO> MaSearch(String ma_no) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ProdVO> PtrSearch(Integer ptr_no) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ProdVO OthrMaSearch(Integer ptr_no, String ma_no) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ProdVO> getAllptr() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ProdVO> getAllma() {
			// TODO Auto-generated method stub
			return null;
		}

	

		
		



		
		

	
			
		
}
