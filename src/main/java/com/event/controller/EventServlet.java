package com.event.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.event.model.EventDAO;
import com.event.model.EventService;
import com.event.model.EventVO;
import com.event_p.model.Event_PDAO;
import com.event_p.model.Event_PVO;
import com.event_tag.model.Event_TagService;
import com.event_tag.model.Event_TagVO;


@WebServlet("/backend/event/eventServlet")
public class EventServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	
		
}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html charset=UTF-8");
		String action=req.getParameter("action");
		//�������~�T���A��VIEW���
		
		
		
		if("insert".equals(action)) {
//			List<String> errMsgs=new ArrayList<String>();
			Map<String,String>errMsgs=new HashMap<String,String>();
			req.setAttribute("errMsgs", errMsgs);
			Event_TagService tagSvc=new Event_TagService();
			
			try {
				//���ʦW�٤���ť�
				String event_name=req.getParameter("event_name");//�qhashTag��X				
//System.out.println(event_name);
				if(event_name==null||event_name.trim().length()==0||"�S��HashTag�i�H���".equals(event_name)||"null".equals(event_name)) {
					errMsgs.put("event_name","�Х��s�W�Ω��HashTag");
					event_name="";
				}

				//�п�J���ʶ}�l�ɶ�
				String time=" 00:00:00";
				java.sql.Timestamp event_start=null;
				
				String tIn01= req.getParameter("event_start")+time;
				System.out.println(tIn01);
				try { 
					event_start=java.sql.Timestamp.valueOf(tIn01); 
				}catch(IllegalArgumentException e) {
					errMsgs.put("event_start","���T��J���ʶ}�l���");
					event_start=new Timestamp(System.currentTimeMillis());
				}
				
				//���ʵ���
				java.sql.Timestamp event_end=null;
				String tIn02= req.getParameter("event_end")+time;
				try { 
					event_end=java.sql.Timestamp.valueOf(tIn02); 
				}catch(IllegalArgumentException e) {
					errMsgs.put("event_end","���T��J���ʵ������");
					event_end=new Timestamp(System.currentTimeMillis());
				}
				
				//�}�l�x�D�@�~
				java.sql.Timestamp event_ul_start=null;
				String tIn03= req.getParameter("event_ul_start")+time;
				try { 
					event_ul_start=java.sql.Timestamp.valueOf(tIn03); 
				}catch(IllegalArgumentException e) {
					errMsgs.put("event_ul_start","���T��J�}�l�x�D�@�~���");
					event_ul_start=new Timestamp(System.currentTimeMillis());
				}
				
				//�����x�D�@�~
				java.sql.Timestamp event_ul_end=null;
				String tIn04= req.getParameter("event_ul_end")+time;
				try { 
					event_ul_end=java.sql.Timestamp.valueOf(tIn04); 
				}catch(IllegalArgumentException e) {
					errMsgs.put("event_ul_end","���T��J�����x�D�@�~���");
					event_ul_end=new Timestamp(System.currentTimeMillis());
				}
				
				//�}�l�벼
				java.sql.Timestamp event_vote_start=null;
				String tIn05= req.getParameter("event_vote_start")+time;
				try { 
					event_vote_start=java.sql.Timestamp.valueOf(tIn05); 
				}catch(IllegalArgumentException e) {
					errMsgs.put("event_vote_start","���T��J�}�l�벼���");
					event_vote_start=new Timestamp(System.currentTimeMillis());
				}
				
				//�����벼
				java.sql.Timestamp event_vote_end=null;
				String tIn06= req.getParameter("event_vote_end")+time;
				try { 
					event_vote_end=java.sql.Timestamp.valueOf(tIn06); 
				}catch(IllegalArgumentException e) {
					errMsgs.put("event_vote_end","���T��J�����벼���");
					event_vote_end=new Timestamp(System.currentTimeMillis());
				}
				
				String stat=req.getParameter("event_stat");
//				String statReg="^[0-3]$";//�P�_��0��3
//				if(stat==null||stat.trim().length()==0) {
//					errMsgs.add("���n�ť�");
//					stat="0";
//				}else if(!stat.trim().matches(statReg)) {
//					errMsgs.add("�п�J���T���A�X");
//					stat="3";
//				}
				
				//�P�_������ǩ�
//				if(event_start.getTime()>event_ul_start.getTime()||
//				   event_ul_start.getTime()>event_ul_end.getTime()||
//				   event_ul_end.getTime()>event_vote_start.getTime()||
//				   event_vote_start.getTime()>event_vote_end.getTime()||
//				   event_vote_end.getTime()!=event_end.getTime()
//				  ) {
//					errMsgs.add("�T�{��J����O�_�ŦX����");
//				}
				
				Integer event_stat=new Integer(stat);
				EventVO eventVO=new EventVO();
				eventVO.setEvent_name(event_name);
				eventVO.setEvent_start(event_start);
				eventVO.setEvent_end(event_end);
				eventVO.setEvent_ul_start(event_ul_start);
				eventVO.setEvent_ul_end(event_ul_end);
				eventVO.setEvent_vote_start(event_vote_start);
				eventVO.setEvent_vote_end(event_vote_end);
				eventVO.setEvent_stat(event_stat);


				
				
				
				EventService srv=new EventService();
				String event_no=srv.insert(event_name, event_start, event_end, event_ul_start, event_ul_end, event_vote_start, event_vote_end, event_stat);
				
				//��Q��L��HashTag���A,�ѦۼW�D��Ȩ��X���ʽs��
				
				String[] Tags=event_name.split(",");//����HahsTag
//				System.out.println(Tags[0]+","+Tags[1]+","+Tags[2]);
				
				
				if(!errMsgs.isEmpty()) {
					req.setAttribute("eventVO",eventVO);
					String url="/backend/event/TestInsert.jsp";
					RequestDispatcher err=req.getRequestDispatcher(url);
					err.forward(req, res);
					return;
				}
				
				for(int i=0;i<Tags.length;i++) {					
					List<Event_TagVO> tagVOs= tagSvc.findAllByTagName(Tags[i]);
					Iterator<Event_TagVO> iter=tagVOs.iterator();
					while(iter.hasNext()) {
						Event_TagVO tagVO=iter.next();
						tagSvc.update(tagVO.getMem_id(),event_no, tagVO.getEvent_tag_time(), tagVO.getEvent_tag_name(), tagVO.getEvent_tag_no());
						System.out.println("tag ���ʽs�����w��s");
					}
					//��tag name��X����hashTag
					//�A�� update��s���A

				}
				String url="/backend/event/TestListAll.jsp";
				RequestDispatcher ok=req.getRequestDispatcher(url);
				ok.forward(req, res);
				System.out.println("done");
			}catch(Exception e) {
				errMsgs.put("��L���~","���~�T��");
				e.printStackTrace();
				
				String url="/backend/event/TestInsert.jsp";
				RequestDispatcher fail=req.getRequestDispatcher(url);
				fail.forward(req, res);
			}
		}
		//�R���u��R�٨S�Q��Z������
		if("delete".equals(action)) {
			List<String> errMsgs=new ArrayList<String>();
			req.setAttribute("errMsgs",errMsgs );

			String event_no=null;
			try {
				event_no= req.getParameter("event_no");//name=event_no
				if(event_no==null||event_no.trim().length()==0) {
					errMsgs.add("�ФŪť�");
				}
				if(!errMsgs.isEmpty()) {
					String path="/TestListAll.jsp";
					RequestDispatcher fail=req.getRequestDispatcher(path);
					fail.forward(req, res);
				}

				EventService svc=new EventService();
				svc.delete(event_no);
				String path="/TestListAll.jsp";
				RequestDispatcher ok=req.getRequestDispatcher(path);
				ok.forward(req, res);
				
			}catch(Exception e) {
				errMsgs.add("���~�A�L�k�R��:");
				e.printStackTrace();
				String path="TestListAll.jsp";
				RequestDispatcher fail=req.getRequestDispatcher(path);
				fail.forward(req, res);
				
			}
		}
		//�ק�
		if("update".equals(action)) {
//			List<String> errMsgs=new ArrayList<String>();
			Map<String,String> errMsgs=new HashMap<String,String>(); 
			req.setAttribute("errMsgs",errMsgs);
			String event_no=req.getParameter("event_no");
			//���o�|���ק諸eventVO;
			EventService svc=new EventService();
			EventVO eventVOPass=svc.findByPrimaryKey(event_no);
			try {
				
			
			String event_name=req.getParameter("event_name");
			if(event_name==null||event_name.trim().length()==0) {
				errMsgs.put("event_name","�ФŪť�");
				event_name=eventVOPass.getEvent_name();
			}
			
		
			//�п�J���ʶ}�l�ɶ�
			String time=" 00:00:00";
			java.sql.Timestamp event_start=null;
			String tIn01= req.getParameter("event_start")+time;
			try { 
				event_start=java.sql.Timestamp.valueOf(tIn01); 
			}catch(IllegalArgumentException e) {
				errMsgs.put("event_start","���T��J���ʶ}�l���");
				event_start=eventVOPass.getEvent_start();
			}
			
			//���ʵ���
			java.sql.Timestamp event_end=null;
			String tIn02= req.getParameter("event_end")+time;
			try { 
				event_end=java.sql.Timestamp.valueOf(tIn02); 
			}catch(IllegalArgumentException e) {
				errMsgs.put("event_end","���T��J���ʵ������");
				event_end=eventVOPass.getEvent_end();
			}
			
			//�}�l�x�D�@�~
			java.sql.Timestamp event_ul_start=null;
			String tIn03= req.getParameter("event_ul_start")+time;
			try { 
				event_ul_start=java.sql.Timestamp.valueOf(tIn03); 
			}catch(IllegalArgumentException e) {
				errMsgs.put("event_ul_start","���T��J�}�l�x�D�@�~���");
				event_ul_start=eventVOPass.getEvent_ul_start();
			}
			
			//�����x�D�@�~
			java.sql.Timestamp event_ul_end=null;
			String tIn04= req.getParameter("event_ul_end")+time;
			try { 
				event_ul_end=java.sql.Timestamp.valueOf(tIn04); 
			}catch(IllegalArgumentException e) {
				errMsgs.put("event_ul_end","���T��J�����x�D�@�~���");
				event_ul_end=eventVOPass.getEvent_ul_end();
			}
			
			//�}�l�벼
			java.sql.Timestamp event_vote_start=null;
			String tIn05= req.getParameter("event_vote_start")+time;
			try { 
				event_vote_start=java.sql.Timestamp.valueOf(tIn05); 
			}catch(IllegalArgumentException e) {
				errMsgs.put("event_vote_start","���T��J�}�l�벼���");
				event_vote_start=eventVOPass.getEvent_vote_start();
			}
			
			//�����벼
			java.sql.Timestamp event_vote_end=null;
			String tIn06= req.getParameter("event_vote_end")+time;
			try { 
				event_vote_end=java.sql.Timestamp.valueOf(tIn06); 
			}catch(IllegalArgumentException e) {
				errMsgs.put("event_vote_end","���T��J�����벼���");
				event_vote_end=eventVOPass.getEvent_vote_end();
			}
			
			String event_statStr=req.getParameter("event_stat");
			String statReg="^[0-3]$";//�P�_��1��3
			if(event_statStr==null||event_statStr.trim().length()==0) {
				errMsgs.put("event_stat","���n�ť�");
				event_statStr="3";
			}else if(!event_statStr.trim().matches(statReg)) {
				errMsgs.put("event_stat","�п�J���T���A�X");
				event_statStr="0";
			}
			
			EventVO eventVO=new EventVO();
			Integer event_stat=new Integer(event_statStr);
			eventVO.setEvent_no(event_no);
			eventVO.setEvent_name(event_name);
			eventVO.setEvent_start(event_start);
			eventVO.setEvent_end(event_end);
			eventVO.setEvent_ul_start(event_ul_start);
			eventVO.setEvent_ul_end(event_ul_end);
			eventVO.setEvent_vote_start(event_vote_start);
			eventVO.setEvent_vote_end(event_vote_end);
			eventVO.setEvent_stat(event_stat);
			
			if(!errMsgs.isEmpty()) {
				req.setAttribute("eventVO", eventVO);
				String path="/backend/event/TestUpdate.jsp";
				RequestDispatcher fail=req.getRequestDispatcher(path);
				fail.forward(req, res);
				return;
			}

			
			
			
			//���A��3���ʵ����ɡA��s�ƦW
			if (event_stat.equals(new Integer(3))) {
				Event_PDAO dao = new Event_PDAO();
					List<Event_PVO> event_pVOs = dao.bigRankSort(event_no, 20);
					System.out.println("event_pVOs size : "+event_pVOs.size());
					Iterator<Event_PVO> iterRank = event_pVOs.iterator();
					System.out.println("start update rank");
					while (iterRank.hasNext()) {
						Event_PVO event_pVO = iterRank.next();
						dao.bigRankUpdate(event_pVO.getVote_rank(), event_pVO.getEvent_p_no());
					}
			}
			
			svc.update(event_no, event_name, event_start, event_end, event_ul_start, event_ul_end, event_vote_start, event_vote_end, event_stat);
			req.setAttribute("eventVO", eventVO);
			System.out.println(svc.findLastEndEvent());
			if (event_stat.equals(new Integer(3))) {
				String event_no_last = svc.findLastEndEvent();
				System.out.println(3);
				getServletContext().setAttribute("event_no", event_no_last);
				System.out.println(4);
			}
			
System.out.println(req.getParameter("requestURL"));
//			String path="/backend/event/TestListAll.jsp";
			RequestDispatcher ok=req.getRequestDispatcher("/backend/event/TestListAll.jsp");
//			req.getParameter("requestURI")
			ok.forward(req, res);
			}catch(Exception e) {
				errMsgs.put("��L���~","�X�{���~");
				
				e.printStackTrace();
				String path="/backend/event/TestListAll.jsp";
				RequestDispatcher fail=req.getRequestDispatcher(path);
				fail.forward(req, res);
			}
			
		}
		//ListAll�ק�ШD
		if("updateFromListAll".equals(action)) {
//			List<String> errMsgs=new ArrayList<String>();
			Map<String,String>errMsgs=new HashMap<String,String>();
			req.setAttribute("errMsgs", errMsgs);
			try {
				String event_no=req.getParameter("event_no");
				
				
				EventService svc=new EventService();
				EventVO eventVO=svc.findByPrimaryKey(event_no);
				req.setAttribute("eventVO", eventVO);
				String path="/backend/event/TestUpdate.jsp";
				RequestDispatcher successView=req.getRequestDispatcher(path);
				successView.forward(req, res);
			}catch(Exception e) {
				e.printStackTrace();
				errMsgs.put("���~","�������~");
				String path="/backend/event/TestListAll.jsp";
				RequestDispatcher fail=req.getRequestDispatcher(path);
				fail.forward(req, res);}
			
			
		}
		if("findByPrimaryKey".equals(action)) {
			List<String> errMsgs=new ArrayList<String>();
			req.setAttribute("errMsgs", errMsgs);
			try {
				EventService svc=new EventService();
				String event_no=req.getParameter("event_no");
				System.out.println(event_no);
				EventVO eventVO=svc.findByPrimaryKey(event_no);
				
				req.setAttribute("eventVO", eventVO);
				//����ơA�}�l��e
				String path="/backend/event/displayOne.jsp";
				RequestDispatcher displayOne=req.getRequestDispatcher(path);
				displayOne.forward(req, res);
			}catch(Exception e) {
				e.printStackTrace();
				errMsgs.add("�������~");
				String path="/backend/event/event_select_page.jsp";
				RequestDispatcher fail=req.getRequestDispatcher(path);
				fail.forward(req, res);				
			}
		}
	}
	
	

}
