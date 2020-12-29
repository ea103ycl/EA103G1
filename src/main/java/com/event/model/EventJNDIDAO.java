package com.event.model;

import java.util.*;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;



public class EventJNDIDAO implements EventDAO_interface{
	private static  Connection con=null;
	private static  DataSource ds=null;
	PreparedStatement pstmt=null;
	static{
		try {
			Context ctx=new InitialContext();
			ds=(DataSource)ctx.lookup("java:comp/env/jdbc/G1DB");//"java:comp/env/jdbc/TestDB"
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static final String INSERT=
			"INSERT INTO EVENT(event_no,event_name,event_start,event_end,event_ul_start,event_ul_end,event_vote_start,event_vote_end,event_stat)"
			+ "VALUES(event_seq.nextval,?,?,?,?,?,?,?,?)";
	private static final String DELETE=
			"DELETE FROM EVENT WHERE EVENT_NO=?";
	private static final String FindByEventNO=
			"SELECT * FROM EVENT WHERE EVENT_NO=?";
	private static final String UPDATE=
			"UPDATE EVENT SET event_name=?,event_start=?,event_end=?,event_ul_start=?,event_ul_end=?,event_vote_start=?,event_vote_end=?,event_stat=?"
			+ "WHERE event_no=?";
	private static final String FindAllEvent=
			"SELECT * FROM EVENT";
	private static final String FINDWITHOUTEND="SELECT*FROM EVENT WHERE EVENT_STAT!=3";
	
	public String insert(EventVO eventVO) {
		try {
			
			con=ds.getConnection();
			pstmt=con.prepareStatement(INSERT);
			pstmt.setString(1,eventVO.getEvent_name());			
			pstmt.setTimestamp(2, eventVO.getEvent_start());
			pstmt.setTimestamp(3, eventVO.getEvent_end());
			pstmt.setTimestamp(4, eventVO.getEvent_ul_start());
			pstmt.setTimestamp(5, eventVO.getEvent_ul_end());
			pstmt.setTimestamp(6, eventVO.getEvent_vote_start());
			pstmt.setTimestamp(7, eventVO.getEvent_vote_end());
			pstmt.setInt(8,eventVO.getEvent_stat());
			pstmt.executeUpdate();
		}catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con!=null) {
				try {
				con.close();
				}
			catch(SQLException e) {
				e.printStackTrace();
				}
			}
		}
		return "";
	}
	
	public void delete(String event_no) {
		// TODO Auto-generated method stub
		try {
			
			con=ds.getConnection();//SQLException
			pstmt=con.prepareStatement(DELETE);
			pstmt.setString(1,event_no);
			pstmt.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				}catch(SQLException e) {
						e.printStackTrace();
				}
			}
			if(con!=null) {
				try {
				con.close();
				}
			catch(SQLException e) {
				e.printStackTrace();
				}
			}
		}
		
	}
	
	public void update(EventVO eventVO) {
		try {
			
			con=ds.getConnection();
			PreparedStatement pstmt=con.prepareStatement(UPDATE);
			
			
			pstmt.setString(1, eventVO.getEvent_name());
			pstmt.setTimestamp(2, eventVO.getEvent_start());
			pstmt.setTimestamp(3, eventVO.getEvent_end());
			pstmt.setTimestamp(4, eventVO.getEvent_ul_start());
			pstmt.setTimestamp(5, eventVO.getEvent_ul_end());
			pstmt.setTimestamp(6, eventVO.getEvent_vote_start());
			pstmt.setTimestamp(7, eventVO.getEvent_vote_end());
			pstmt.setInt(8, eventVO.getEvent_stat());
			pstmt.setString(9, eventVO.getEvent_no());//��@�ק����
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public EventVO findByPrimaryKey(String event_no) {
		// TODO Auto-generated method stub
		EventVO eventVO=new EventVO();
		try {
			
			con=ds.getConnection();
			PreparedStatement pstmt=con.prepareStatement(FindByEventNO);
			pstmt.setString(1,event_no);
			ResultSet rs= pstmt.executeQuery();
			if(rs.next()) {
				eventVO.setEvent_no(rs.getString(1));
				eventVO.setEvent_name(rs.getString(2));
				eventVO.setEvent_start(rs.getTimestamp(3));
				eventVO.setEvent_end(rs.getTimestamp(4));
				eventVO.setEvent_ul_start(rs.getTimestamp(5));
				eventVO.setEvent_ul_end(rs.getTimestamp(6));
				eventVO.setEvent_vote_start(rs.getTimestamp(7));
				eventVO.setEvent_vote_end(rs.getTimestamp(8));
				eventVO.setEvent_stat(rs.getInt(9));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return eventVO;
	}
	
	
	public List<EventVO> findAllEvent() {
		// TODO Auto-generated method stub
		List<EventVO> eventVOs=new ArrayList();//��try�~���~��^��
		try {
			
			con=ds.getConnection();
			PreparedStatement pstmt=con.prepareStatement(FindAllEvent);
			
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next()) {
				EventVO eventVO=new EventVO();
				eventVO.setEvent_no(rs.getString(1));
				eventVO.setEvent_name(rs.getString(2));
				eventVO.setEvent_start(rs.getTimestamp(3));
				eventVO.setEvent_end(rs.getTimestamp(4));
				eventVO.setEvent_ul_start(rs.getTimestamp(5));
				eventVO.setEvent_ul_end(rs.getTimestamp(6));
				eventVO.setEvent_vote_start(rs.getTimestamp(7));
				eventVO.setEvent_vote_end(rs.getTimestamp(8));
				eventVO.setEvent_stat(rs.getInt(9));
				eventVOs.add(eventVO);
			}
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return eventVOs;
		
	}
	
	
	public static void main(String[] args) {
		EventJNDIDAO dao=new EventJNDIDAO();
//	
//		
//		java.util.Date date=new java.util.Date();
//		Timestamp now=new Timestamp(date.getTime());
//		java.sql.Timestamp ts=java.sql.Timestamp.valueOf("2020-08-01 00:00:00".trim());
//		//�s�W
//		EventVO eventVO1=new EventVO();
//		eventVO1.setEvent_name("Neo");		
//		eventVO1.setEvent_start(ts);
//		eventVO1.setEvent_end(now);
//		eventVO1.setEvent_ul_start(now);
//		eventVO1.setEvent_ul_end(now);
//		eventVO1.setEvent_vote_start(now);
//		eventVO1.setEvent_vote_end(now);
//		eventVO1.setEvent_stat(new Integer(1));
//		dao.insert(eventVO1);
//		System.out.println("add  done");
		
		//�R��
//		dao.delete(10);
//		System.out.println("delete done");
		
		//�d�ߥHevent_no�d��
//		EventVO eventVO=dao.findByPrimaryKey(1001);
//		System.out.print(eventVO.getEvent_no());
		
		//�ק�
//		EventVO eventVO3=new EventVO();
//		eventVO3.setEvent_no(1001);
//		eventVO3.setEvent_name("123");
//        String dateTimeStr2 = "2020-11-01 00:00:00";
//		java.sql.Timestamp tsEstart = java.sql.Timestamp.valueOf(dateTimeStr2);//String��Timestamp		
//		Timestamp tsulstart=new Timestamp(tsEstart.getTime()+(86400*7)*1000);
//		Timestamp tsulend=new Timestamp(tsulstart.getTime()+(86400*14)*1000);
//		Timestamp tsEend=new Timestamp(tsulend.getTime()+(86400*7)*1000);//��tsulend��Z�I����+7��	
//		eventVO3.setEvent_start(tsEstart);				
//		eventVO3.setEvent_end(tsEend);		
//		eventVO3.setEvent_ul_start(tsulstart);//�[7�ѡA�}�l�x�@�~
//		eventVO3.setEvent_ul_end(tsulend);//�}�l�x�@�~��[14�ѡA�@�~�I�Z��A�}�l�벼��		
//		eventVO3.setEvent_vote_start(tsulend);//����x�Z�����ɶ�
//		eventVO3.setEvent_vote_end(tsEend);//�x�Z�����ɶ��[7�ѡA���ʵ���
//		eventVO3.setEvent_stat(3);
//		dao.update(eventVO3);
		
		//�d����
		List<EventVO> eventVOs=dao.findAllEvent();
		Iterator<EventVO> le= eventVOs.iterator();
		while(le.hasNext()) {
			EventVO eventVO=le.next();
			System.out.print(eventVO.getEvent_no()+",");
			System.out.print(eventVO.getEvent_name()+",");
			System.out.print(eventVO.getEvent_start()+",");
			System.out.print(eventVO.getEvent_end()+",");
			System.out.print(eventVO.getEvent_ul_start()+",");
			System.out.print(eventVO.getEvent_ul_end()+",");
			System.out.print(eventVO.getEvent_vote_start()+",");
			System.out.print(eventVO.getEvent_vote_end()+",");
			System.out.println(eventVO.getEvent_stat());
		}
	}

	@Override
	public List<EventVO> findWithoutEnd() {
		// TODO Auto-generated method stub
		Connection con=null;
		List<EventVO> eventVOs=new ArrayList<EventVO>();
		try {
			con=ds.getConnection();
			PreparedStatement pstmt=con.prepareStatement(FINDWITHOUTEND);
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next()) {
				EventVO eventVO=new EventVO();
				eventVO.setEvent_no(rs.getString(1));
				eventVO.setEvent_name(rs.getString(2));
				eventVO.setEvent_start(rs.getTimestamp(3));
				eventVO.setEvent_end(rs.getTimestamp(4));
				eventVO.setEvent_ul_start(rs.getTimestamp(5));
				eventVO.setEvent_ul_end(rs.getTimestamp(6));
				eventVO.setEvent_vote_start(rs.getTimestamp(7));
				eventVO.setEvent_vote_end(rs.getTimestamp(8));
				eventVO.setEvent_stat(rs.getInt(9));
				eventVOs.add(eventVO);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return eventVOs;
	}

	@Override
	public String findLastEndEvent() {
		// TODO Auto-generated method stub
		return null;
	}








}