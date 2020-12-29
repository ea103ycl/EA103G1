package com.emp.controller;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

import com.emp.model.Available_Func_Service;
import com.emp.model.Available_Func_VO;
import com.emp.model.Emp_Account_Service;
import com.emp.model.Emp_Account_VO;
import com.emp.model.MailService;

public class LoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String dbno;
	private String dbpwd;

	// �i�ˬd�ϥΪ̿�J���b��(account) �K�X(password)�O�_���ġj
	// �i��ڤW���ܸ�Ʈw�j�M���j
	protected boolean allowUser(String emp_no, String emp_pwd) {
		if (dbno.equals(emp_no) && dbpwd.equals(emp_pwd))
			return true;
		else
			return false;
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

	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		req.setCharacterEncoding("Big5");
		res.setContentType("text/html; charset=Big5");
		PrintWriter out = res.getWriter();
		String action = req.getParameter("action");

		if ("login".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				// �i���o�ϥΪ� �b��(account) �K�X(password)�j
				String str1 = req.getParameter("emp_no");
				String str2 = req.getParameter("emp_pwd");

				String emp_no = str1.trim();
				String emp_no_Reg = "^E[0-9]{3}[0-9]{1}$";

				if (emp_no == null || emp_no.length() == 0) {
					errorMsgs.add("�п�J���u�s���C");
				} else if (!emp_no.matches(emp_no_Reg)) {
					errorMsgs.add("�п�J���T�榡�A���u�s����E�}�Y��4��Ʀr�C");
				}

				String emp_pwd = req.getParameter("emp_pwd");
				String emp_pwd_Reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,8}$";

				if (str2 == null || (str2.trim()).length() == 0) {
					errorMsgs.add("�п�J���u�K�X�C");
				} else if (!emp_pwd.trim().matches(emp_pwd_Reg)) {
					errorMsgs.add("�K�X�u��O�ѼƦr�M�r���զ��A�åB�n�P�ɧt���Ʀr�M�r���A�B���׭n�b4-8�줧���C");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				Emp_Account_Service empSvc = new Emp_Account_Service();
				Emp_Account_VO emp_Account_VO = empSvc.getOneEmp(emp_no);

				if (emp_Account_VO == null) {
					errorMsgs.add("�d�L��ơC");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				dbno = emp_Account_VO.getEmpNo();
				dbpwd = emp_Account_VO.getEmpPwd();

				// �i�ˬd�ӱb�� , �K�X�O�_���ġj
				if (!allowUser(emp_no, emp_pwd)) { // �i�b�� , �K�X�L�Įɡj
					errorMsgs.add("�K�X���~�C");

					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				} else { // �i�b�� , �K�X���Į�, �~���H�U�u�@�j
					HttpSession session = req.getSession();
					session.setAttribute("emp_no", emp_no); // *�u�@1: �~�bsession�����w�g�n�J�L������

					Available_Func_Service AvaFuncSvc = new Available_Func_Service();
					List<Available_Func_VO> list = AvaFuncSvc.getOneEmpFunc(emp_no);

					StringBuffer sb = new StringBuffer();
					for (Available_Func_VO available_Func_VO : list) {
						String x = available_Func_VO.getFuncId();
						sb.append(x);
					}

					String funcList = sb.toString();

					session.setAttribute("funcList", funcList);

					try {
						String location = (String) session.getAttribute("location");
						if (location != null) {
							session.removeAttribute("location"); // *�u�@2: �ݬݦ��L�ӷ����� (-->�p���ӷ�����:�h���ɦܨӷ�����)
							res.sendRedirect(location);
							return;
						}
					} catch (Exception ignored) {
				}

					res.sendRedirect(req.getContextPath() + "/backend/back-index.jsp"); // *�u�@3:
																						// (-->�p�L�ӷ�����:�h���ɦ�login_success.jsp)
				}

			} catch (Exception e) {
				errorMsgs.add("�L�k��s���" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
				failureView.forward(req, res);

			}
		}
		
		if ("forget".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String str = req.getParameter("emp_no"); // return String
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�b���C");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
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
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				
				Emp_Account_Service empSvc = new Emp_Account_Service();
				Emp_Account_VO emp_Account_VO = empSvc.getOneEmp(emp_no);

				if (emp_Account_VO == null) {
					errorMsgs.add("�d�L��ơC");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
	
				empSvc = new Emp_Account_Service();
				emp_Account_VO = empSvc.getOneEmp(emp_no);
				String emp_mail = emp_Account_VO.getEmpMail();
				String emp_name = emp_Account_VO.getEmpName();
				String emp_pwd = randomPwd();
				empSvc.resetPwd(emp_no,emp_pwd);

				String resetUrl = "http://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/backend/emp/resetPwd.jsp"
						+ "?emp_no="+emp_no+"&emp_pwd="+emp_pwd;

				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				// �H�eE-Mail
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
				session.setAttribute("emp_no", emp_no);
				session.setAttribute("emp_pwd", emp_pwd);

				String url = "/backend/emp/moveMail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k��s���" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
				failureView.forward(req, res);
			}
		}
	}
}