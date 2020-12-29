package com.reg_inf_controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.reg_inf.model.Reg_infVO;

import tools.MoneyTool;

import com.mem.model.MemVO;
import com.reg_inf.model.*;

public class Reg_infServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		MemVO memVO = new MemVO();

		HttpSession session = req.getSession();
		memVO = (MemVO) session.getAttribute("memVO");
		

		if ("getOne_For_Display".equals(action)) { // �Ӧ�listAllReg_inf���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("ri_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J���W��s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/reg_inf/listAllReg_inf.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				String ri_id = null;
				try {
					ri_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("���W��s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/reg_inf/listAllReg_inf.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				Reg_infService reg_infSvc = new Reg_infService();
				Reg_infVO reg_infVO = reg_infSvc.getOneReg_inf(ri_id);
				if (reg_infVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/reg_inf/listAllReg_inf.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("reg_infVO", reg_infVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/frontend/reg_inf/listOneReg_inf.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/reg_inf/listAllReg_inf.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getReg_inf_mt_no".equals(action)) { // �Ӧ�listAllMeeting_back.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("mt_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J���W��s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/reg_inf/listAllReg_inf_back.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				String mt_no = null;
				try {
					mt_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("���W��s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/reg_inf/listAllReg_inf_back.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				

				
				/***************************2.�}�l�d�߸��*****************************************/
				Reg_infService reg_infSvc = new Reg_infService();
				List<Reg_infVO> reg_infVO = reg_infSvc.getReg_inf_mt_no(mt_no);
				System.out.println("getReg_inf_mt_no" + mt_no);

				if (reg_infVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/reg_inf/listAllReg_inf_back.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("reg_infVO", reg_infVO); // ��Ʈw���X��empVO����,�s�Jreq
//				HttpSession session = request.getSession("reg_infVO", reg_infVO);
				String url = "/backend/reg_inf/listAllReg_inf_mt_no.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/reg_inf/listAllReg_inf_back.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Display_back".equals(action)) { // �Ӧ�listAllReg_inf_back.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("ri_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J���W��s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/reg_inf/listAllReg_inf_back.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				String ri_id = null;
				try {
					ri_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("���W��s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/reg_inf/listAllReg_inf_back.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				Reg_infService reg_infSvc = new Reg_infService();
				Reg_infVO reg_infVO = reg_infSvc.getOneReg_inf(ri_id);
				if (reg_infVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/reg_inf/listAllReg_inf_back.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("reg_infVO", reg_infVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/backend/reg_inf/listOneReg_inf_back.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/reg_inf/listAllReg_inf_back.jsp");
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
				String ri_id = new String(req.getParameter("ri_id"));
				
				/***************************2.�}�l�d�߸��****************************************/
				Reg_infService reg_infSvc = new Reg_infService();
				Reg_infVO reg_infVO = reg_infSvc.getOneReg_inf(ri_id);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("reg_infVO", reg_infVO);         // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/frontend/reg_inf/update_reg_inf_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/reg_inf/listAllReg_inf.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // �Ӧ�update_reg_inf_input.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String ri_id = new String(req.getParameter("ri_id").trim());
				
				String mem_id = req.getParameter("mem_id");
				String mem_idReg = "^[(a-zA-Z0-9_)]{2,10}$";
				if (mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.add("�|��ID: �ФŪť�");
				} else if(!mem_id.trim().matches(mem_idReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�|��ID: �u��O�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��10����");
	            }
			
				String mt_no = new String(req.getParameter("mt_no").trim());
				
				Integer ri_qty = null;
				try {
					ri_qty = new Integer(req.getParameter("ri_qty").trim());
				} catch (NumberFormatException e) {
					ri_qty = 0;
					errorMsgs.add("�H�ƽж�Ʀr.");
				}
				
				String ri_note = null;
				try {
					ri_note = new String(req.getParameter("ri_note").trim());
				} catch (NumberFormatException e) {
					ri_note = "";
					errorMsgs.add("�ФŪť�.");
				}
				
			
				Reg_infVO reg_infVO = new Reg_infVO();
				reg_infVO.setMt_no(mt_no);
				reg_infVO.setMem_id(mem_id);
				reg_infVO.setRi_qty(ri_qty);
				reg_infVO.setRi_note(ri_note);
				reg_infVO.setRi_id(ri_id);

				System.out.println(ri_note);
	
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("reg_infVO", reg_infVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/reg_inf/update_reg_inf_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				Reg_infService reg_infSvc = new Reg_infService();
				reg_infVO = reg_infSvc.updateReg_inf(mt_no,mem_id,ri_qty,ri_note,ri_id);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("reg_infVO", reg_infVO); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
				String url = "/frontend/reg_inf/listAllReg_inf.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/reg_inf/update_reg_inf_input.jsp");
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
			
				String mt_no = new String(req.getParameter("mt_no").trim());
				
				Integer ri_qty = null;
				try {
					ri_qty = new Integer(req.getParameter("ri_qty").trim());
				} catch (NumberFormatException e) {
					ri_qty = 0;
					errorMsgs.add("�H�ƽж�Ʀr.");
				}
				
				String ri_note = null;
				try {
					ri_note = new String(req.getParameter("ri_note").trim());
				} catch (NumberFormatException e) {
					ri_note = "";
					errorMsgs.add("�ФŪť�.");
				}
//				String test = req.getParameter("ri_status");
//				if(test !=null) xxx;
				
				Integer ri_status = 1;
				Reg_infVO reg_infVO = new Reg_infVO();
				
				reg_infVO.setMt_no(mt_no);
				reg_infVO.setMem_id(mem_id);
				reg_infVO.setRi_status(ri_status);
				reg_infVO.setRi_qty(ri_qty);
				reg_infVO.setRi_note(ri_note);
				
	
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("reg_infVO", reg_infVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/listAllMeeting_front.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				

				
				/***************************2.�}�l�s�W���***************************************/
//	1.random->123
//	2.email->123
//	3.123->456->DB
				Reg_infService reg_infSvc = new Reg_infService();
				reg_infVO = reg_infSvc.addReg_inf(mt_no,mem_id,ri_qty,ri_status,ri_note);
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/frontend/reg_inf/listAllReg_inf.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/reg_inf/addReg_inf.jsp");
				failureView.forward(req, res);
			}
		}
        
        if ("checkout_reg".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>(); // �Ыؤ@��list�ñN���~�T���s�J��list��, �A�N��list�s���req cope��
			req.setAttribute("errorMsgs", errorMsgs);

			HttpSession session1 = req.getSession();
			try {
				
				
				// ********************1.����ШD�Ѽƨè̿�J�榡�����~�B�z******************
				String ri_id = null;
				Integer amount = (new Integer(req.getParameter("totalAmount").trim()));
				String mem_id = req.getParameter("mem_id");
				System.out.println(mem_id);
				String mem_idReg = "^[(a-zA-Z0-9_)]{2,10}$";
				if (mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.add("�|��ID: �ФŪť�");
				} else if(!mem_id.trim().matches(mem_idReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�|��ID: �u��O�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��10����");
	            }
			
				String mt_no = new String(req.getParameter("mt_no").trim());
				Integer check_remaining_people = (new Integer(req.getParameter("check_num").trim()));
				
				Integer ri_qty = (new Integer(req.getParameter("ri_qty").trim()));
				if(ri_qty <=0) {
					errorMsgs.add("�H�ƥ����j��0");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/listAllMeeting_front.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				else if(ri_qty > check_remaining_people ) {
					errorMsgs.add("�H�ƶW�L�o! ");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/listAllMeeting_front.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
	
				String ri_note = null;
				try {
					ri_note = new String(req.getParameter("ri_note").trim());
				} catch (NumberFormatException e) {
					ri_note = "";
					errorMsgs.add("�ФŪť�.");
				}
				
				Integer ri_status = 1;
				Reg_infVO reg_infVO = new Reg_infVO();
		

				reg_infVO.setMt_no(mt_no);
				reg_infVO.setMem_id(mem_id);
				reg_infVO.setRi_status(ri_status);
				reg_infVO.setRi_qty(ri_qty);
				reg_infVO.setRi_note(ri_note);
				
				
//				********************2.�}�l���b******************

				Reg_infService reg_infSvc = new Reg_infService();
				reg_infVO = reg_infSvc.addReg_inf(mt_no,mem_id,ri_qty,ri_status,ri_note);
		       String ri_id2 = reg_infVO.getRi_id();
		
		     
				Boolean ifCheckOutSucess = MoneyTool.checkOut(session1, 43, ri_id2, -amount);// ���ڽжǤJ�t��

				if (ifCheckOutSucess) {
					System.out.println("test");
					reg_infSvc.check_mt_numReg_inf();
				String url = "/frontend/meeting/listAllMeeting_front.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // ���b���\�����^�쭶���|������
					successView.forward(req, res);
					  

				} else {
					reg_infSvc.deleteReg_inf(ri_id2);
					reg_infSvc.check_mt_numReg_inf();
					errorMsgs.add("���W����,���ˬd�l�B�O�_�R���ή榡���T"); // ���b���ѫ᪺�B�z
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/meeting/listAllMeeting_front.jsp");

					failureView.forward(req, res);
					return;
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");

					failureView.forward(req, res);
					return;
				}

			} catch (Exception e) {		
				System.out.println("test2");
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");
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
				String ri_id = new String(req.getParameter("ri_id"));
				
				/***************************2.�}�l�R�����***************************************/
				Reg_infService reg_infSvc = new Reg_infService();
				reg_infSvc.deleteReg_inf(ri_id);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				String url = "/frontend/reg_inf/listAllReg_inf.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/reg_inf/listAllReg_inf.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
            if ("cancel".equals(action)) { // �Ӧ�update_reg_inf_input.jsp���ШD
			
            	List<String> errorMsgs = new LinkedList<String>(); 
    			req.setAttribute("errorMsgs", errorMsgs);

    			HttpSession session2 = req.getSession();

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String ri_id = new String(req.getParameter("ri_id").trim());
				System.out.println("ri_id.length: "+ri_id.length());
				if(ri_id.length()==0) {
					System.out.println("ri_id is null");
				}
				
		        Integer ri_status = 2;	
		        Integer ri_qty = 0;
				

		        Integer amount = (new Integer(req.getParameter("totalAmount").trim()));
		        
				Reg_infVO reg_infVO = new Reg_infVO();
				reg_infVO.setRi_status(ri_status);
				reg_infVO.setRi_qty(ri_qty);

				reg_infVO.setRi_id(ri_id);
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("reg_infVO", reg_infVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/reg_inf/listAllReg_inf.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				/***************************2.�}�l�ק���*****************************************/
				Reg_infService reg_infSvc = new Reg_infService();
				reg_infVO = reg_infSvc.cancelReg_inf(ri_status,ri_qty,ri_id);
			    reg_infSvc.check_mt_numReg_inf();


				Boolean ifCheckOutSucess = MoneyTool.checkOut(session2, 38, ri_id, amount);// ���ڽжǤJ�t��
				
                
				if (ifCheckOutSucess) {
					
				String url = "/frontend/reg_inf/listAllReg_inf.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // ���b���\�����^�쭶���|������
					successView.forward(req, res);
					  

				} else {
					errorMsgs.add("���W����,���ˬd�l�B�O�_�R���ή榡���T"); // ���b���ѫ᪺�B�z
				}

				if (!errorMsgs.isEmpty()) {

					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");

					failureView.forward(req, res);
					return;
				}

			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");
				failureView.forward(req, res);
			}

		}
            if ("cancel_back".equals(action)) { // �Ӧ�update_reg_inf_input.jsp���ШD
    			
            	List<String> errorMsgs = new LinkedList<String>(); 
    			req.setAttribute("errorMsgs", errorMsgs);

    			HttpSession session2 = req.getSession();

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String ri_id = new String(req.getParameter("ri_id").trim());
				System.out.println("ri_id.length: "+ri_id.length());
				if(ri_id.length()==0) {
					System.out.println("ri_id is null");
				}
				String mem_id = new String(req.getParameter("mem_id").trim());

		        Integer ri_status = 2;	
		        Integer ri_qty = 0;
				

		        Integer amount = (new Integer(req.getParameter("totalAmount").trim()));
		        
				Reg_infVO reg_infVO = new Reg_infVO();
				reg_infVO.setRi_status(ri_status);
				reg_infVO.setRi_qty(ri_qty);
				reg_infVO.setRi_id(mem_id);

				reg_infVO.setRi_id(ri_id);
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("reg_infVO", reg_infVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/reg_inf/listAllReg_inf.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				/***************************2.�}�l�ק���*****************************************/
				Reg_infService reg_infSvc = new Reg_infService();
				reg_infVO = reg_infSvc.cancelReg_inf(ri_status,ri_qty,ri_id);
			    


				Boolean ifCheckOutSucess = MoneyTool.checkOut_back(mem_id, 38, ri_id, amount);// ���ڽжǤJ�t��
				
                
				if (ifCheckOutSucess) {
					reg_infSvc.check_mt_numReg_inf();
				String url = "/backend/reg_inf/listAllReg_inf_mt_no.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // ���b���\�����^�쭶���|������
					successView.forward(req, res);
					  

				} else {
					errorMsgs.add("���W����,���ˬd�l�B�O�_�R���ή榡���T"); // ���b���ѫ᪺�B�z
				}

				if (!errorMsgs.isEmpty()) {

					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");

					failureView.forward(req, res);
					return;
				}

			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");
				failureView.forward(req, res);
			}

		}
          
            
    }

	
	}
