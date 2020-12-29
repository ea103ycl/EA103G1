package com.emp.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.emp.model.*;

@MultipartConfig
public class EmpServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

//		if ("login".equals(action)) {
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try { // �]�w�ŭȬ��F�w�������rEX:empoo
//				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
//				String str1 = req.getParameter("emp_no"); // return String
//				String emp_no = str1.trim();
//				String emp_no_Reg = "^E[0-9]{3}[0-9]{1}$";
//				
//				if (emp_no == null || emp_no.length() == 0) {
//					errorMsgs.add("�п�J���u�s���C");
//				}else if (!emp_no.matches(emp_no_Reg)) {
//					errorMsgs.add("�п�J���T�榡�A���u�s����E�}�Y��4��Ʀr�C");
//				}
//
//				String emp_pwd = req.getParameter("emp_pwd");
//				String emp_pwd_Reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,8}$";
//				
//				String str2 = req.getParameter("emp_pwd"); // return String
//				if (str2 == null || (str2.trim()).length() == 0) {
//					errorMsgs.add("�п�J���u�K�X�C");
//				} else if (!emp_pwd.trim().matches(emp_pwd_Reg)) {
//					errorMsgs.add("�K�X�u��O�ѼƦr�M�r���զ��A�åB�n�P�ɧt���Ʀr�M�r���A�B���׭n�b4-8�줧���C");
//				}				
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
//					failureView.forward(req, res);
//					return;// �{�����_
//				}
//
//				Emp_Account_Service empSvc = new Emp_Account_Service();
//				Emp_Account_VO emp_Account_VO = empSvc.getOneEmp(emp_no);
//				if (emp_Account_VO == null) {
//					errorMsgs.add("�d�L��ơC");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
//					failureView.forward(req, res);
//					return;// �{�����_
//				}
//
//				if (!emp_Account_VO.getEmpPwd().equals(emp_pwd)) {
//					errorMsgs.add("�K�X���~�C");
//				}else {
//						RequestDispatcher successView = req.getRequestDispatcher("/backend/emp/index.jsp");
//						successView.forward(req, res);					
//					}
//	
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
//					failureView.forward(req, res);
//					return;// �{�����_
//				}
//
//			} catch (Exception e) {
//				errorMsgs.add("�L�k���o���:" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
//				failureView.forward(req, res);
//			}
//		}
		if ("logout".equals(action)) {
			HttpSession session = req.getSession();
			session.removeAttribute("emp_no");
			session.removeAttribute("funcList");
			RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
			failureView.forward(req, res);
		}
			
		if("updatePwd".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try {
				String str = req.getParameter("emp_no"); // return String
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�b���C");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/resetPwd.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				String emp_no = str.trim();
				String emp_no_Reg = "^E[0-9]{3}[0-9]{1}$";

				if (!emp_no.matches(emp_no_Reg)) {
					errorMsgs.add("�п�J���T�榡�A�b����E�}�Y��4��Ʀr�C");
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/resetPwd.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				
				Emp_Account_Service empSvc = new Emp_Account_Service();
				
				Emp_Account_VO emp_Account_VO = empSvc.getOneEmp(emp_no);
				String dbpwd = emp_Account_VO.getEmpPwd();
				
				String old_pwd = req.getParameter("old_pwd");
				if(old_pwd == null || old_pwd.length() == 0) {
					errorMsgs.add("�п�J�K�X�C");
				}else if(!dbpwd.equals(old_pwd)) {
					errorMsgs.add("�K�X���~�C");
				}	
				
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/resetPwd.jsp");
					failureView.forward(req, res);
					return;
				}
				
				String emp_pwd = req.getParameter("emp_pwd");
				String emp_pwdc = req.getParameter("emp_pwdc");
				String emp_pwd_Reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,8}$";
				
			if (emp_pwd == null || emp_pwd.trim().length() == 0) {
				errorMsgs.add("�s�K�X�ФŪťաC");
			} else if (!emp_pwd.trim().matches(emp_pwd_Reg)) {
				errorMsgs.add("�s�K�X�u��O�ѼƦr�M�r���զ��A�åB�n�P�ɧt���Ʀr�M�r���A�B���׭n�b4-8�줧���C");
			} else if(!emp_pwd.equals(emp_pwdc)) {	
				errorMsgs.add("���ˬd�s�K�X�P���ƿ�J���O�_�۲šC");
			}
			
			if(!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/resetPwd.jsp");
				failureView.forward(req, res);
				return;
			}
		
			empSvc.resetPwd(emp_no, emp_pwd);
			
			RequestDispatcher successView = req.getRequestDispatcher("/backend/emp/login.jsp");
			successView.forward(req, res);
										
			} catch(Exception e) {
				errorMsgs.add("�L�k��s���"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/resetPwd.jsp");
				failureView.forward(req,res);
			}
		}
		
		
		if("newEmp".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try {
				String str = req.getParameter("emp_no"); // return String
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�b���C");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/newEmp.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				String emp_no = str.trim();
				String emp_no_Reg = "^E[0-9]{3}[0-9]{1}$";

				if (!emp_no.matches(emp_no_Reg)) {
					errorMsgs.add("�п�J���T�榡�A�b����E�}�Y��4��Ʀr�C");
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/newEmp.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				
				Emp_Account_Service empSvc = new Emp_Account_Service();
				
				Emp_Account_VO emp_Account_VO = empSvc.getOneEmp(emp_no);
				String dbpwd = emp_Account_VO.getEmpPwd();
				
				String old_pwd = req.getParameter("old_pwd");
				if(old_pwd == null || old_pwd.length() == 0) {
					errorMsgs.add("�п�J�K�X�C");
				}else if(!dbpwd.equals(old_pwd)) {
					errorMsgs.add("�K�X���~�C");
				}	
				
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/newEmp.jsp");
					failureView.forward(req, res);
					return;
				}
				
				String emp_pwd = req.getParameter("emp_pwd");
				String emp_pwdc = req.getParameter("emp_pwdc");
				String emp_pwd_Reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,8}$";
				
			if (emp_pwd == null || emp_pwd.trim().length() == 0) {
				errorMsgs.add("�s�K�X�ФŪťաC");
			} else if (!emp_pwd.trim().matches(emp_pwd_Reg)) {
				errorMsgs.add("�s�K�X�u��O�ѼƦr�M�r���զ��A�åB�n�P�ɧt���Ʀr�M�r���A�B���׭n�b4-8�줧���C");
			} else if(!emp_pwd.equals(emp_pwdc)) {	
				errorMsgs.add("���ˬd�s�K�X�P���ƿ�J���O�_�۲šC");
			}
			
			if(!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/newEmp.jsp");
				failureView.forward(req, res);
				return;
			}
		
			empSvc.resetPwd(emp_no, emp_pwd);
			
			RequestDispatcher successView = req.getRequestDispatcher("/backend/emp/login.jsp");
			successView.forward(req, res);
										
			} catch(Exception e) {
				errorMsgs.add("�L�k��s���"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/newEmp.jsp");
				failureView.forward(req,res);
			}
		}	
	
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			// Object Value
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try { // �]�w�ŭȬ��F�w�������rEX:empoo
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				
				String str1 = (req.getParameter("emp_no1")).trim(); // return String
				String str2 = req.getParameter("emp_no2");
				String str3 = req.getParameter("emp_no3");
				String emp_no = null;				
					
				if((str1 == null||str1.length()==0) && (str2 == null||str2.length()==0)) {
					emp_no = str3;
				} else if((str1 == null||str1.length()==0) && (str3 == null||str3.length()==0)) {
					emp_no = str2;
				} else if((str2 == null||str2.length()==0) && (str3 == null||str3.length()==0)) {
					emp_no = str1;
				}
				
				if(((str1 != null && str1.length()!=0) && (str2 != null && str2.length()!=0)) ||
				   ((str1 != null && str1.length()!=0) && (str3 != null && str3.length()!=0)) ||
				   ((str2 != null && str2.length()!=0) && (str3 != null && str3.length()!=0)) ||
				   ((str1 != null && str1.length()!=0) && (str2 != null && str2.length()!=0) && (str3 != null && str3.length()!=0))) {
					errorMsgs.add("�п�J��@�d�߸��");
				}else {					
					if(emp_no == null || emp_no.length()==0) {
						errorMsgs.add("�п�J���u��ơC");					
					}
				}
																
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/listAllEmp.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				String emp_no_Reg = "^E[0-9]{3}[0-9]{1}$";
				if (!emp_no.matches(emp_no_Reg)) {
					errorMsgs.add("�п�J���T�榡�A���u�s����E�}�Y��4��Ʀr�C");
				}
			
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/listAllEmp.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				
				/*************************** 2.�}�l�d�߸�� *****************************************/
				Emp_Account_Service empSvc = new Emp_Account_Service();
				Emp_Account_VO emp_Account_VO = empSvc.getOneEmp(emp_no);
				if (emp_Account_VO == null) {
					errorMsgs.add("�d�L��ơC");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/listAllEmp.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
				req.setAttribute("emp_Account_VO", emp_Account_VO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/backend/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}

		// first, get data of one emp
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				String emp_no = req.getParameter("emp_no");

				/*************************** 2.�}�l�d�߸�� ****************************************/
				Emp_Account_Service empSvc = new Emp_Account_Service();
				Emp_Account_VO emp_Account_VO = empSvc.getOneEmp(emp_no);

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("emp_Account_VO", emp_Account_VO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/backend/emp/update_emp_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // �Ӧ�update_emp_input.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				String emp_no = req.getParameter("emp_no");

				String emp_name = req.getParameter("emp_name");
				String emp_name_Reg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9-)]{2,10}$";
				if (emp_name == null || emp_name.trim().length() == 0) {
					errorMsgs.add("���u�m�W: �ФŪťաC");
				} else if (!emp_name.trim().matches(emp_name_Reg)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("���u�m�W: �u��O���B�^��r���B�Ʀr�M- , �B���ץ��ݦb2��10�����C");
				}

				
				Emp_Account_Service empSvc = new Emp_Account_Service();
				Emp_Account_VO emp_Account_VO = empSvc.getOneEmp(emp_no);
				String emp_pwd = emp_Account_VO.getEmpPwd();
	
				String emp_pos = req.getParameter("emp_pos");
				if (emp_pos == null || emp_pos.trim().length() == 0) {
					errorMsgs.add("¾��ФŪťաC");
				}

				String emp_mail = req.getParameter("emp_mail");
				String emp_mail_Reg = "\\p{Alpha}\\w{2,15}[@][a-z0-9]{3,}[.]\\p{Lower}{2,}";
				if (!emp_mail.trim().matches(emp_mail_Reg)) {
					errorMsgs.add("�п�J���T��email�榡�C");
				}

				byte[] emp_photo = null;
				Part part = req.getPart("emp_photo");

				InputStream in = part.getInputStream();
				if (in.available() == 0) {
					empSvc = new Emp_Account_Service();
					emp_Account_VO = empSvc.getOneEmp(emp_no);
					emp_photo = emp_Account_VO.getEmpPhoto();
				} else {
					byte[] buf = new byte[in.available()];
					in.read(buf);
					emp_photo = buf;
				}

				Integer emp_status = new Integer(req.getParameter("emp_status"));

				String[] func_Arr = req.getParameterValues("func_id");

				if (func_Arr == null || func_Arr.length == 0) {
					errorMsgs.add("�п�J�v��");
				}

				emp_Account_VO = new Emp_Account_VO();
				Available_Func_VO available_Func_VO = new Available_Func_VO();

				emp_Account_VO.setEmpNo(emp_no);
				emp_Account_VO.setEmpName(emp_name);
				emp_Account_VO.setEmpPwd(emp_pwd);
				emp_Account_VO.setEmpPos(emp_pos);
				emp_Account_VO.setEmpMail(emp_mail);
				emp_Account_VO.setEmpPhoto(emp_photo);
				emp_Account_VO.setEmpStatus(emp_status);

				if (func_Arr != null && func_Arr.length != 0) {
					for (int i = 0; i < func_Arr.length; i++) {
						available_Func_VO.setFuncId(func_Arr[i]);
					}
				}

				available_Func_VO.setEmpNo(emp_no);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) { // ���J���~�����e�]�A�e�^�����W
					req.setAttribute("emp_Account_VO", emp_Account_VO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/update_emp_input.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}

				/*************************** 2.�}�l�ק��� *****************************************/
				empSvc = new Emp_Account_Service(); // �N�W�����U�ӰѼƸg��updateEmp�]�w�i�h
				emp_Account_VO = empSvc.updateEmp(emp_no, emp_name, emp_pwd, emp_pos, emp_mail, emp_photo, emp_status);

				Available_Func_Service funcSvc = new Available_Func_Service();
				funcSvc.deleteEmpFunc(emp_no);

				for (int i = 0; i < func_Arr.length; i++) {
					available_Func_VO = funcSvc.addEmpFunc(emp_no, func_Arr[i]);
				}

				/*************************** 3.�ק粒��,�ǳ����(Send the Success view) *************/
				req.setAttribute("emp_Account_VO", emp_Account_VO); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
				String url = "/backend/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/update_emp_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
				Emp_Account_Service empSvc = new Emp_Account_Service();

				String emp_name = req.getParameter("emp_name");
				String emp_name_Reg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9-)]{2,10}$";
				if (emp_name == null || emp_name.trim().length() == 0) {
					errorMsgs.add("���u�m�W: �ФŪťաC");
				} else if (!emp_name.trim().matches(emp_name_Reg)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("���u�m�W: �u��O���B�^��r���B�Ʀr�M- , �B���ץ��ݦb2��10�����C");
				}

//				String emp_pwd = req.getParameter("emp_pwd");
//				String emp_pwd_Reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,8}$";
//				if (emp_pwd == null || emp_pwd.trim().length() == 0) {
//					errorMsgs.add("�K�X�ФŪťաC");
//				} else if (!emp_pwd.trim().matches(emp_pwd_Reg)) {
//					errorMsgs.add("�K�X�u��O�ѼƦr�M�r���զ��A�åB�n�P�ɧt���Ʀr�M�r���A�B���׭n�b4-8�줧���C");
//				}

				String emp_pwd = randomPwd();

				String emp_pos = req.getParameter("emp_pos");
				if (emp_pos == null || emp_pos.trim().length() == 0) {
					errorMsgs.add("¾��ФŪťաC");
				}

				String emp_mail = req.getParameter("emp_mail");
				String emp_mail_Reg = "\\p{Alpha}\\w{2,15}[@][a-z0-9]{3,}[.]\\p{Lower}{2,}";
				if (!emp_mail.trim().matches(emp_mail_Reg)) {
					errorMsgs.add("�п�J���T��email�榡�C");
				}

				byte[] emp_photo = null;
				Part part = req.getPart("emp_photo");

				InputStream in = part.getInputStream();
				if (in.available() == 0) {
					errorMsgs.add("�|����ܹϤ�");
				} else {
					byte[] buf = new byte[in.available()];
					in.read(buf);
					emp_photo = buf;
				}
				Integer emp_status = new Integer(req.getParameter("emp_status"));

				String[] func_Arr = req.getParameterValues("func_id");
				if (func_Arr == null || func_Arr.length == 0) {
					errorMsgs.add("�п�J�v��");
				}

				Emp_Account_VO emp_Account_VO = new Emp_Account_VO();
				Available_Func_VO available_Func_VO = new Available_Func_VO();

				emp_Account_VO.setEmpName(emp_name);
				emp_Account_VO.setEmpPwd(emp_pwd);
				emp_Account_VO.setEmpPos(emp_pos);
				emp_Account_VO.setEmpMail(emp_mail);
				emp_Account_VO.setEmpPhoto(emp_photo);
				emp_Account_VO.setEmpStatus(emp_status);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) { // ���J���~�����e�]�A�e�^�����W
					req.setAttribute("emp_Account_VO", emp_Account_VO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/addEmp.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.�}�l�s�W��� ***************************************/

				Emp_Account_VO emp_Account_VO2 = empSvc.addEmp(emp_name, emp_pwd, emp_pos, emp_mail, emp_photo,
						emp_status);

				String emp_no = emp_Account_VO2.getEmpNo();

				available_Func_VO.setEmpNo(emp_no);

				if (func_Arr != null && func_Arr.length != 0) {
					for (int i = 0; i < func_Arr.length; i++) {
						available_Func_VO.setFuncId(func_Arr[i]);
					}
				}

				Available_Func_Service funcSvc = new Available_Func_Service();

				for (int i = 0; i < func_Arr.length; i++) {
					available_Func_VO = funcSvc.addEmpFunc(emp_no, func_Arr[i]);
				}
				
				
				String resetUrl = "http://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/backend/emp/newEmp.jsp"
						 + "?emp_no1="+emp_no+"&emp_pwd1="+emp_pwd;
				
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
			    //�H�eE-Mail
			    String to = emp_mail;			      
			    String subject = "�K�X�q��";			      
			    String ch_name = emp_name;
			    String messageText = "Hello! " + ch_name + " �H�U�O�A���K�X���m�T��" + "\n" + 
			    					 "�b��:" + emp_no+ "\n" +
			    		             "�K�X:" + emp_pwd + "\n" + 
			    					 " (�w�g�ҥ�)" + "\n" + "���I��H�U�s�����]�K�X"+"\n"+
			    					 resetUrl;
			    
	
			       
			    MailService mailService = new MailService();
			    mailService.sendMail(to, subject, messageText);
			    
			    HttpSession session = req.getSession();
			    session.setAttribute("emp_no1",emp_no);
			    session.setAttribute("emp_pwd1",emp_pwd);
			    

			    
				String url = "/backend/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/addEmp.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ***************************************/
				String emp_no = req.getParameter("emp_no");

				/*************************** 2.�}�l�R����� ***************************************/
				Available_Func_Service funcSvc = new Available_Func_Service();
				funcSvc.deleteEmpFunc(emp_no);

				Emp_Account_Service empSvc = new Emp_Account_Service();
				empSvc.deleteEmp(emp_no);

				/*************************** 3.�R������,�ǳ����(Send the Success view) ***********/
				String url = "/backend/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}
	}

	private String randomPwd() {
		int r;
		StringBuilder ranPwd = new StringBuilder();

		ranPwd.append((int) ((Math.random() * 10)));

		for (int i = 0; i < 7; i++) {
			r = (int) (Math.random() * 3 + 1);
			if (r == 1) {
				ranPwd.append((int) ((Math.random() * 10)));
			} else if (r == 2) {
				ranPwd.append((char) (((Math.random() * 26) + 65)));
			} else {
				ranPwd.append(((char) ((Math.random() * 26) + 97)));
			}
		}
		return ranPwd.toString();
	}
}
