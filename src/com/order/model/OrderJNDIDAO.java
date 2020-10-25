package com.order.model;


import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.detail.model.DetailVO;

import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;


public class OrderJNDIDAO implements OrderDAO_interface {
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

	private static final String INSERT_STMT = "INSERT INTO ORDERS(OR_NO, MEM_ID, OR_NAME, OR_PHONE, OR_ZIP, OR_ADDR, OR_NOTE, OR_TOTAL )   VALUES('S'||to_char(sysdate,'yyyymmdd')||'-'||LPAD(to_char(order_seq.NEXTVAL), 5, '0'),?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM ORDERS ORDER BY OR_NO";
	private static final String GET_ONE_STMT = "SELECT * FROM ORDERS WHERE OR_NO = ?";
	private static final String UPDATE = "UPDATE ORDERS SET OR_STATUS=? WHERE OR_NO=?";
	
	
	@Override
	public String insert(OrderVO orderVO, List<DetailVO> detailList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String ord_next_no = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1,    orderVO.getMem_id());
			pstmt.setString(2,    orderVO.getOr_name());
			pstmt.setString(3, orderVO.getOr_phone());
			pstmt.setInt(4,    orderVO.getOr_zip());
			pstmt.setString(5, orderVO.getOr_addr());
			pstmt.setString(6,    orderVO.getOr_note());
			pstmt.setInt(7,    orderVO.getOr_total());
			
			
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
		return  ord_next_no;
					}

	
	

	

	@Override
	public OrderVO findByPrimaryKey(String or_no) {
		OrderVO orderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, or_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				orderVO = new OrderVO();
				orderVO.setOr_no(rs.getString("or_no"));
				orderVO.setMem_id(rs.getString("mem_id"));
				orderVO.setOr_name(rs.getString("or_name"));
				orderVO.setOr_phone(rs.getString("or_phone"));
				orderVO.setOr_zip(rs.getInt("or_zip"));
				orderVO.setOr_addr(rs.getString("or_addr"));
				orderVO.setOr_note(rs.getString("or_note"));
				orderVO.setOr_total(rs.getInt("or_total"));
				orderVO.setOr_status(rs.getInt("or_status"));
				orderVO.setOr_time(rs.getTimestamp("or_time"));
				
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
						return orderVO;
					}

	@Override
	public List<OrderVO> getAll() {
		List<OrderVO> list = new ArrayList<OrderVO>();
		OrderVO orderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				orderVO = new OrderVO();
				orderVO.setOr_no(rs.getString("or_no"));
				orderVO.setMem_id(rs.getString("mem_id"));
				orderVO.setOr_name(rs.getString("or_name"));
				orderVO.setOr_phone(rs.getString("or_phone"));
				orderVO.setOr_zip(rs.getInt("or_zip"));
				orderVO.setOr_addr(rs.getString("or_addr"));
				orderVO.setOr_note(rs.getString("or_note"));
				orderVO.setOr_total(rs.getInt("or_total"));
				orderVO.setOr_status(rs.getInt("or_status"));
				orderVO.setOr_time(rs.getTimestamp("or_time"));
				list.add(orderVO);
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
	public void update(OrderVO orderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1,    orderVO.getOr_status());
			pstmt.setString(2,    orderVO.getOr_no());

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
	public List<OrderVO> orderSearch(String mem_id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
		
}
