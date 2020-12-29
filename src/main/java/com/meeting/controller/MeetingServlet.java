package com.meeting.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.meeting.model.MeetingVO;
import com.prod.model.ProdService;
import com.prod.model.ProdVO;

import tools.MoneyTool;

import com.meeting.model.*;
@MultipartConfig
public class MeetingServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("mt_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�����|�s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/listAllMeeting_front.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				String mt_no = null;
				try {
					mt_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("�����|�s�������T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				MeetingService meetingSvc = new MeetingService();
				MeetingVO meetingVO = meetingSvc.getOneMeeting(mt_no);
				if (meetingVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/listAllMeeting_front.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("meetingVO", meetingVO); // ��Ʈw���X��meetingVO����,�s�Jreq
				String url = "/frontend/meeting/listOneMeeting.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneMeeting.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/meeting/listAllMeeting_front.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				String mt_no = new String(req.getParameter("mt_no"));
				
				/***************************2.�}�l�d�߸��****************************************/
				MeetingService meetingSvc = new MeetingService();
				MeetingVO meetingVO = meetingSvc.getOneMeeting(mt_no);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("meetingVO", meetingVO);         // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/frontend/meeting/update_meeting_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/meeting/listAllMeeting.jsp");
				failureView.forward(req, res);
			}
		}
		
//		if ("getOne_For_Reg".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//			
//			try {
//				/***************************1.�����ШD�Ѽ�****************************************/
//				String mem_id = new String(req.getParameter("mem_id"));
//				
//				/***************************2.�}�l�d�߸��****************************************/
//				MeetingService meetingSvc = new MeetingService();
//				MeetingVO meetingVO = meetingSvc.getOneMeeting(mem_id);
//								
//				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
//				req.setAttribute("meetingVO", meetingVO);         // ��Ʈw���X��empVO����,�s�Jreq
//				String url = "/frontend/reg_inf/listAllReg_inf.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
//				successView.forward(req, res);
//
//				/***************************��L�i�઺���~�B�z**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/frontend/meeting/listAllMeeting.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
		
		if ("update".equals(action)) { // �Ӧ�update_emp_input.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String mt_no = new String(req.getParameter("mt_no").trim());
				
				String mem_id = req.getParameter("mem_id");
				String mem_idReg = "^[(a-zA-Z0-9_)]{2,10}$";
				if (mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.add("�|��ID: �ФŪť�");
				} else if(!mem_id.trim().matches(mem_idReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�|��ID: �u��O�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��10����");
	            }
				
				Integer max_num = null;
				try {
					max_num = new Integer(req.getParameter("max_num").trim());
				} catch (NumberFormatException e) {
					max_num = 0;
					errorMsgs.add("�H�ƽж�Ʀr.");
				}
				
				Integer min_num = null;
				try {
					min_num = new Integer(req.getParameter("min_num").trim());
				} catch (NumberFormatException e) {
					min_num = 0;
					errorMsgs.add("�H�ƽж�Ʀr.");
				}
				
				String mt_place = req.getParameter("mt_place");
				String mt_placeReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (mt_place == null || mt_place.trim().length() == 0) {
					errorMsgs.add("�a�I: �ФŪť�");
				} else if(!mt_place.trim().matches(mt_placeReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�a�I: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��20����");
	            }
				
				Integer ri_fee = null;
				try {
					ri_fee = new Integer(req.getParameter("ri_fee").trim());
				} catch (NumberFormatException e) {
					ri_fee = 0;
					errorMsgs.add("���W�O�ж�Ʀr.");
				}
				
				String mt_detail = req.getParameter("mt_detail");
				String mt_detailReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (mt_detail == null || mt_detail.trim().length() == 0) {
					errorMsgs.add("����²��: �ФŪť�");
				} else if(!mt_detail.trim().matches(mt_detailReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("����²��: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��20����");
	            }
//				
				java.sql.Timestamp mt_start_time = null;
				try {
					mt_start_time = java.sql.Timestamp.valueOf(req.getParameter("mt_start_time").trim());
				} catch (IllegalArgumentException e) {
					mt_start_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}
				
				java.sql.Timestamp mt_end_time = null;
				try {
					mt_end_time = java.sql.Timestamp.valueOf(req.getParameter("mt_end_time").trim());
				} catch (IllegalArgumentException e) {
					mt_end_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}
				
				java.sql.Timestamp mt_time = null;
				try {
					mt_time = java.sql.Timestamp.valueOf(req.getParameter("mt_time").trim());
				} catch (IllegalArgumentException e) {
					mt_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}
				
				String mt_id = req.getParameter("mt_id");
				String mt_idReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (mt_id == null || mt_id.trim().length() == 0) {
					errorMsgs.add("���ʦW��: �ФŪť�");
				} else if(!mt_id.trim().matches(mt_idReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("���ʦW��: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��20����");
	            }
				
		
				Part part = req.getPart("mt_pic");
				InputStream in = part.getInputStream();
				byte[] mt_pic = new byte[in.available()];
				in.read(mt_pic);
				in.close();

				MeetingVO meetingVO = new MeetingVO();
				meetingVO.setMt_no(mt_no);
				meetingVO.setMem_id(mem_id);
				meetingVO.setMax_num(max_num);
				meetingVO.setMin_num(min_num);
				meetingVO.setMt_place(mt_place);
				meetingVO.setRi_fee(ri_fee);
				meetingVO.setMt_detail(mt_detail);
				meetingVO.setMt_start_time(mt_start_time);
				meetingVO.setMt_end_time(mt_end_time);
				meetingVO.setMt_time(mt_time);
				meetingVO.setMt_id(mt_id);
				meetingVO.setMt_pic(mt_pic);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("meetingVO", meetingVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/update_meeting_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				MeetingService meetingSvc = new MeetingService();
				meetingVO = meetingSvc.updateMeeting(mt_no,mem_id,max_num, min_num, mt_place,ri_fee,mt_detail,mt_start_time,mt_end_time,mt_time,mt_id,mt_pic);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("meetingVO", meetingVO); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
				String url = "/frontend/meeting/listOneMeeting.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/meeting/update_meeting_input.jsp");
				failureView.forward(req, res);
		}
		}
	
		
		if ("getOne_For_Reg".equals(action)) { // �Ӧ�listoneMeeting.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				String mt_no = new String(req.getParameter("mt_no"));
				
				/***************************2.�}�l�d�߸��****************************************/
				MeetingService meetingSvc = new MeetingService();
				MeetingVO meetingVO = meetingSvc.getOneMeeting(mt_no);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("meetingVO", meetingVO);         // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/frontend/reg_inf/addReg_inf.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/meeting/listOneMeeting.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				
				String mem_id = req.getParameter("mem_id");
				String mem_idReg = "^[(a-zA-Z0-9_)]{2,10}$";
				if (mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.add("�|��ID: �ФŪť�");
				} else if(!mem_id.trim().matches(mem_idReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�|��ID: �u��O�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��10����");
	            }
				
				Integer max_num = null;
				try {
					max_num = new Integer(req.getParameter("max_num").trim());
				} catch (NumberFormatException e) {
					max_num = 0;
					errorMsgs.add("�H�Ƥ���0");
				} 
				
				
				Integer min_num = null;
				try {
					min_num = new Integer(req.getParameter("min_num").trim());
				} catch (NumberFormatException e) {
					min_num = 0;
					errorMsgs.add("�H�Ƥ���0");
				}
				
				if (min_num == null || min_num == 0) {
					errorMsgs.add("�̤j�H��: �ФŪť�");
				} else if (max_num == null || max_num == 0) {
					errorMsgs.add("�̤j�H��: �ФŪť�");
				} else if (max_num < min_num) {
					errorMsgs.add("�̤j�H�ƻݤj��̤֤H��");
				} 
				
				
				
				
				
				
				
				
				String mt_place = req.getParameter("mt_place");
				String mt_placeReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (mt_place == null || mt_place.trim().length() == 0) {
					errorMsgs.add("�a�I: �ФŪť�");
				} else if(!mt_place.trim().matches(mt_placeReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�a�I: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��20����");
	            }
				
				Integer ri_fee = null;
				try {
					ri_fee = new Integer(req.getParameter("ri_fee").trim());
				} catch (NumberFormatException e) {
					ri_fee = 0;
					errorMsgs.add("�~���ж�Ʀr.");
				}
				
				String mt_detail = req.getParameter("mt_detail");
				String mt_detailReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (mt_detail == null || mt_detail.trim().length() == 0) {
					errorMsgs.add("����²��: �ФŪť�");
				} else if(!mt_place.trim().matches(mt_detailReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("����²��: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��20����");
	            }
				
				java.sql.Timestamp mt_start_time = null;
				try {
					mt_start_time = java.sql.Timestamp.valueOf(req.getParameter("mt_start_time").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("�п�J�}�l���W���!");
				}
				
				java.sql.Timestamp mt_end_time = null;
				try {
					mt_end_time = java.sql.Timestamp.valueOf(req.getParameter("mt_end_time").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("�п�J���W�I����!");
				}
				
				java.sql.Timestamp mt_time = null;
				try {
					mt_time = java.sql.Timestamp.valueOf(req.getParameter("mt_time").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("�п�J���ʤ��!");
				}
			
				String mt_id = req.getParameter("mt_id");
				String mt_idReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (mt_detail == null || mt_id.trim().length() == 0) {
					errorMsgs.add("����²��: �ФŪť�");
				} else if(!mt_id.trim().matches(mt_idReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("����²��: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��20����");
	            }
				
				Integer mt_num = 0;

				Integer mt_status = 0;
				Part part = req.getPart("mt_pic");
				InputStream in = part.getInputStream();
				byte[] mt_pic = new byte[in.available()];
				in.read(mt_pic);
				in.close();

				MeetingVO meetingVO = new MeetingVO();
				
				meetingVO.setMem_id(mem_id);
				meetingVO.setMt_status(mt_status);
				meetingVO.setMax_num(max_num);
				meetingVO.setMin_num(min_num);
				meetingVO.setMt_place(mt_place);
				meetingVO.setRi_fee(ri_fee);
				meetingVO.setMt_detail(mt_detail);
				meetingVO.setMt_start_time(mt_start_time);
				meetingVO.setMt_end_time(mt_end_time);
				meetingVO.setMt_time(mt_time);
				meetingVO.setMt_id(mt_id);
				meetingVO.setMt_num(mt_num);
				meetingVO.setMt_pic(mt_pic);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("meetingVO", meetingVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/addMeeting.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				
				/***************************2.�}�l�s�W���***************************************/
//	1.random->123
//	2.email->123
//	3.123->456->DB
				
				
				MeetingService meetingSvc = new MeetingService();
				meetingVO = meetingSvc.addMeeting(mem_id, mt_status,max_num, min_num, mt_place,ri_fee, mt_detail,mt_start_time,mt_end_time,mt_time,mt_id,mt_num,mt_pic);
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/frontend/meeting/listAllMeeting_front.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/meeting/addMeeting.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				String mt_no = new String(req.getParameter("mt_no"));
				
				/***************************2.�}�l�R�����***************************************/
				MeetingService meetingSvc = new MeetingService();
				meetingSvc.deleteMeeting(mt_no);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				String url = "/frontend/meeting/listAllMeeting.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/meeting/listAllMeeting.jsp");
				failureView.forward(req, res);
			}
		}
			if ("cancel_meeting".equals(action)) { // �Ӧ�search_mem_meeting.jsp���ШD
				
				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);
				try {
					/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
					String mt_no = new String(req.getParameter("mt_no").trim());
					Integer mt_status = 2;

					MeetingVO meetingVO = new MeetingVO();
					
					meetingVO.setMt_status(mt_status);
					meetingVO.setMt_no(mt_no);

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("meetingVO", meetingVO); // �t����J�榡���~��empVO����,�]�s�Jreq
						RequestDispatcher failureView = req
								.getRequestDispatcher("/frontend/meeting/search_mem_meeting.jsp");
						failureView.forward(req, res);
						return; //�{�����_
					}
					
					/***************************2.�}�l�ק���*****************************************/
					MeetingService meetingSvc = new MeetingService();
					meetingVO = meetingSvc.cancelMeeting(mt_status,mt_no);
					System.out.println(mt_no);
					System.out.println(mt_status);
					/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
					req.setAttribute("meetingVO", meetingVO); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
					
					String url = "/frontend/meeting/search_mem_meeting.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
					successView.forward(req, res);

					/***************************��L�i�઺���~�B�z*************************************/
				} catch (Exception e) {
					errorMsgs.add("�ק��ƥ���:"+e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/search_mem_meeting.jsp");
					failureView.forward(req, res);
			}
			}
               if ("sharing_meeting".equals(action)) { // �Ӧ�search_mem_meeting.jsp���ШD
				
				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);
				HttpSession session1 = req.getSession();

				try {
					/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
					String mt_no = new String(req.getParameter("mt_no").trim());
					Integer mt_status = 5;
			        Integer amount = (new Integer(req.getParameter("sharingAmount").trim()));

					MeetingVO meetingVO = new MeetingVO();
					
					meetingVO.setMt_status(mt_status);
					meetingVO.setMt_no(mt_no);

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("meetingVO", meetingVO); // �t����J�榡���~��empVO����,�]�s�Jreq
						RequestDispatcher failureView = req
								.getRequestDispatcher("/frontend/meeting/search_mem_meeting.jsp");
						failureView.forward(req, res);
						return; //�{�����_
					}
					
					/***************************2.�}�l�ק���*****************************************/
					MeetingService meetingSvc = new MeetingService();
					meetingVO = meetingSvc.cancelMeeting(mt_status,mt_no);
					System.out.println(mt_no);
					System.out.println(mt_status);

					Boolean ifCheckOutSucess = MoneyTool.checkOut(session1, 33, mt_no, amount);// ���ڽжǤJ�t��
					if (ifCheckOutSucess) {
					
					
					

					/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
					req.setAttribute("meetingVO", meetingVO); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
					
					String url = "/frontend/meeting/search_mem_meeting.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // ���b���\�����^�쭶���|������
					successView.forward(req, res);} 
					else {
						errorMsgs.add("������,���ˬd�榡���T");
						RequestDispatcher failureView = req.getRequestDispatcher("/frontend/meeting/search_mem_meeting.jsp");

						failureView.forward(req, res);
						return;
					}

					if (!errorMsgs.isEmpty()) {

						RequestDispatcher failureView = req.getRequestDispatcher("/frontend/meeting/search_mem_meeting.jsp");

						failureView.forward(req, res);
						return;
					}

				} catch (Exception e) {
					errorMsgs.add("�L�k���o���:" + e.getMessage());
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/meeting/search_mem_meeting.jsp");
					failureView.forward(req, res);
				}
				
               }
               /***************************�H�U���j�M�\��*****************************************/
             //�ҽk�d��
   			if ("Fuzzy_Search".equals(action)) { // �Ӧ�select_page.jsp���ШD
   				
   		    	List<String> errorMsgs = new LinkedList<String>();
   				req.setAttribute("errorMsgs", errorMsgs);
   				
   				HttpSession session = req.getSession();

   				try {
   					/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
   					String mt_id = req.getParameter("mt_id");
   					/***************************2.�}�l�d�߸��*****************************************/
   					MeetingService meetingSvc = new MeetingService();
   					List<MeetingVO> fuzzy_list = new ArrayList<MeetingVO>();
   					fuzzy_list = meetingSvc.FuzzySearch(mt_id);
   					
   					if (fuzzy_list.size()==0) {
   						errorMsgs.add("�d�L���������|");
   						session.setAttribute("fuzzy_list",fuzzy_list);
   					}
   					
   					if (!errorMsgs.isEmpty()) {
   						RequestDispatcher failureView = req
   								.getRequestDispatcher("/frontend/meeting/meeting_fuzzy_search.jsp");
   						failureView.forward(req, res);
   						return;//�{�����_
   					}
   					
   					/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
   					session.setAttribute("fuzzy_list", fuzzy_list); 
   					RequestDispatcher successView = req.getRequestDispatcher("/frontend/meeting/meeting_fuzzy_search.jsp"); 
   					successView.forward(req, res);

   					/***************************��L�i�઺���~�B�z*************************************/
   				} catch (Exception e) {
   					errorMsgs.add("�d�ߥ���!!:" + e.getMessage());
   					RequestDispatcher failureView = req
   							.getRequestDispatcher("/frontend/meeting/meeting_fuzzy_search.jsp");
   					failureView.forward(req, res);
   			
   				}
   			}
               
               
               
               
               
               
          }
	}
	

