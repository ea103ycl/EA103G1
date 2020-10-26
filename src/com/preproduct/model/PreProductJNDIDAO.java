//package com.preproduct.model;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.*;
//
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.sql.DataSource;
//
//import com.eventp.model.EventPVO;
//import com.material.model.*;
//
//public class PreProductJNDIDAO implements PreProductDAO_interface{
//
//	private static DataSource ds = null;
//	static {
//		try {
//			Context ctx = new InitialContext();
//			ds=(DataSource)ctx.lookup("java:comp/env/jdbc/G1DB");
//		}catch(NamingException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private static final String INSERT_STMT = "INSERT INTO PRE_PRODUCT (PO_PROD_NO,EVENT_P_NO,MA_NO,PO_START,PO_END,PRICE,DETAIL) "
//			+ "VALUES (PRE_PRODUCT_SEQ.NEXTVAL,?,?,?,?,?,?)";
//	private static final String GET_ALL_STMT = "SELECT * FROM PRE_PRODUCT";
//	private static final String GET_ONE_STMT = "SELECT * FROM PRE_PRODUCT WHERE PO_PROD_NO = ?";
//	private static final String GET_Eventno_ByPreproduct_STMT1 ="SELECT * FROM EVENT_P WHERE event_p_no=?";
//	private static final String GET_Eventno_ByPreproduct_STMT = "SELECT event_p_no,mem_id,event_no,event_p_name,event_p_date,Event_vote_num,vote_rank,event_p_stat FROM EVENT_P WHERE VOTE_RANK = ? AND EVENT_NO = (SELECT MAX(EVENT_NO) FROM EVENT WHERE EVENT_STAT = 3)";
//	private static final String GET_EventIMG_ByEvent_STMT = "SELECT EVENT_P_IMG FROM EVENT_P WHERE VOTE_RANK = ? "
//			+ "AND EVENT_NO = (SELECT MAX(EVENT_NO) FROM EVENT WHERE EVENT_STAT = 3)";
//	private static final String UPDATE = "UPDATE PRE_PRODUCT SET event_p_no=?, ma_no=?, po_start=?, po_end=?, price=?, detail=? WHERE po_prod_no=?";
//	private static final String DELETE = "DELETE FROM PRE_PRODUCT WHERE po_prod_no=?";
//	
//
//	@Override
//	public void insert(PreProductVO pre_productVO) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(INSERT_STMT);
////			pstmt = con.prepareStatement(GET_EventIMG_ByEvent_STMT);
//			pstmt.setInt(1, pre_productVO.getEvent_p_no());
//			pstmt.setString(2, pre_productVO.getMa_no());
//			pstmt.setDate(3, pre_productVO.getPo_start());
//			pstmt.setDate(4, pre_productVO.getPo_end());
//			pstmt.setInt(5, pre_productVO.getPo_price());
//			pstmt.setString(6, pre_productVO.getPo_detail());
//			
//			pstmt.executeUpdate();
//			
//
//		}catch (SQLException e) {
//			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
//		} finally {
//			if(pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//	}
//
//	@Override
//	public void update(PreProductVO pre_productVO) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(UPDATE);
//			
//			pstmt.setInt(1, pre_productVO.getEvent_p_no());
//			pstmt.setString(2, pre_productVO.getMa_no());
//			pstmt.setDate(3, pre_productVO.getPo_start());
//			pstmt.setDate(4, pre_productVO.getPo_end());
//			pstmt.setInt(5, pre_productVO.getPo_price());
//			pstmt.setString(6, pre_productVO.getPo_detail());
//			pstmt.setString(7, pre_productVO.getPo_prod_no());
//			
//			pstmt.executeUpdate();
//			
//			
//		}catch (SQLException e) {
//			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
//		} finally {
//			if(pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//	}
//
//	@Override
//	public void delete(String po_prod_no) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(DELETE);
//			
//			pstmt.setString(1, po_prod_no);
//			
//			pstmt.executeUpdate();
//			
//		} catch (SQLException e) {
//			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
//		} finally {
//			if(pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//	}
//
//	@Override
//	public PreProductVO findByPrimaryKey(String po_prod_no) {
//		PreProductVO preproductVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_ONE_STMT);
//			
//			pstmt.setString(1, po_prod_no);
//			
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				preproductVO = new PreProductVO();
//				preproductVO.setPo_prod_no(rs.getString("po_prod_no"));
//				preproductVO.setEvent_p_no(rs.getInt("event_p_no"));
//				preproductVO.setMa_no(rs.getString("ma_no"));
//				preproductVO.setPo_start(rs.getDate("po_start"));
//				preproductVO.setPo_end(rs.getDate("po_end"));
//				preproductVO.setPo_price(rs.getInt("po_price"));
//				preproductVO.setPo_detail(rs.getString("po_detail"));
//				
//			}
//
//		} catch (SQLException e) {
//			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
//		} finally {
//			if(rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return preproductVO;
//	}
//
//	@Override
//	public List<PreProductVO> getALL() {
//		List<PreProductVO> list = new ArrayList<PreProductVO>();
//		PreProductVO preproductVO = null;
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_ALL_STMT);
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				preproductVO = new PreProductVO();
//				preproductVO.setPo_prod_no(rs.getString("po_prod_no"));
//				preproductVO.setEvent_p_no(rs.getInt("event_p_no"));
//				preproductVO.setMa_no(rs.getString("ma_no"));
//				preproductVO.setPo_start(rs.getDate("po_start"));
//				preproductVO.setPo_end(rs.getDate("po_end"));
//				preproductVO.setPo_price(rs.getInt("po_price"));
//				preproductVO.setPo_detail(rs.getString("po_detail"));
//				list.add(preproductVO);
//			}
//
//		} catch (SQLException e) {
//			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
//		} finally {
//			if(rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return list;
//	}
//
//	@Override
//	public Set<EventPVO> getEventpnoByPreproduct(Integer event_p_no) {
//		Set<EventPVO> set = new LinkedHashSet<EventPVO>();
//		EventPVO eventpVO = null;
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//				con = ds.getConnection();
//				pstmt = con.prepareStatement(GET_Eventno_ByPreproduct_STMT1);
//				pstmt.setInt(1, event_p_no);
//				rs = pstmt.executeQuery();
//				
//				while(rs.next()) {
//					eventpVO = new EventPVO();
//					eventpVO.setEvent_p_no(rs.getInt("event_p_no"));
//					eventpVO.setMem_id(rs.getString("mem_id"));
//					eventpVO.setEvent_no(rs.getString("event_no"));
//					eventpVO.setEvent_p_name(rs.getString("event_p_name"));
//					eventpVO.setEvent_p_date(rs.getTimestamp("event_p_date"));
//					eventpVO.setEvent_vote_num(rs.getInt("event_vote_num"));
//					eventpVO.setVote_rank(rs.getInt("vote_rank"));
//					eventpVO.setEvent_p_stat(rs.getInt("event_p_stat"));
//					set.add(eventpVO);	
//				}
//
//			} catch (SQLException e) {
//				throw new RuntimeException("a database error occured.love you!"+e.getMessage());
//			} finally {
//				if(rs != null) {
//					try {
//						rs.close();
//					} catch (SQLException e) {
//						e.printStackTrace(System.err);
//					}
//				}
//				if(pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException e) {
//						e.printStackTrace(System.err);
//					}
//				}
//				if(con != null) {
//					try {
//						con.close();
//					} catch (SQLException e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//		return set;
//	}
//
//	@Override
//	public Set<MaterialDataVO> getManoByPreproduct(String ma_no) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_EventIMG_ByEvent_STMT);
//			
//			// 清空裡面參數，重覆使用已取得的PreparedStatement物件
//			for(int i=1;i<=10;i++) {
//				pstmt.clearParameters();
//				pstmt.setInt(1, i);
//				ResultSet rs2 = pstmt.executeQuery();
//				rs2.next();
//				byte[] pic = rs2.getBytes(1);
//				
//				FileOutputStream fos = new FileOutputStream("Output/No"+i+".png");
//				fos.write(pic);
//				fos.flush();
//				fos.close();
//				
////				readPicture(pic);
//
//				rs2.close();}
//
//			
//			
//
//		} catch (SQLException se) {
//			System.out.println(se);
//		} catch (IOException ie) {
//			System.out.println(ie);
//		} finally {
//			// 依建立順序關閉資源 (越晚建立越早關閉)
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					System.out.println(se);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException se) {
//					System.out.println(se);
//				}
//			}
//		}
//		return null;
//	}
//	// Handle with byte array datajl
//	public static void readPicture(byte[] bytes) throws IOException {
//		
//			FileOutputStream fos = new FileOutputStream("Output/No1.png");
//			fos.write(bytes);
//			fos.flush();
//			fos.close();
//
//	}
//}
