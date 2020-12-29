package com.mem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mem.model.MemService;
import com.mem.model.MemVO;

import tools.MailService;

//@WebServlet("/frontend/members/memLoginHandler.do") //mark by YCL
public class MemLoginHandler extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();

		String action = req.getParameter("action");
		MemService memSvc = new MemService();

//-------------------------------------------�n�J--------------------------------------

		if ("login".equals(action))
			{

				MemVO memVO = new MemVO();

				System.out.println("checkingAccount");
				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/

				try
					{

						String usrid = req.getParameter("usrid");
						System.out.println(usrid);
						if (usrid == null || usrid.trim().length() == 0)
							{
								errorMsgs.add("�ϥΪ̦W�ٽФŪť�");

							}
						String password = req.getParameter("password");
						System.out.println(password);
						if (password == null || password.trim().length() == 0)
							{
								errorMsgs.add("�K�X�ФŪť�");

							}

						if (!errorMsgs.isEmpty())
							{

								memVO.setM_accno(usrid);
								memVO.setM_psw(password);
								req.setAttribute("memVO", memVO);// �t����J�榡���~��memVO����,�]�s�Jreq���^�n�J�e��
								RequestDispatcher failureView = req
										.getRequestDispatcher("/frontend/members/memLogin.jsp");
								failureView.forward(req, res);
								return;
							}

						/*************************** 2.�}�l�j�M���,���b���O�_���� ********************************/

						Map<String, String> map = new TreeMap<String, String>();
						map = memSvc.getAllAccounts();

						boolean hasUsrid = map.containsKey(usrid);

						System.out.println("hasUsrid" + hasUsrid);

						System.out.println(map.get(usrid));

						if (hasUsrid && (password.equals(map.get(usrid))))
							{

								try
									{ /************ 3.�Y�b���K�X���T, �N�|����Ʀs�Jsession����^����� ***************/
										System.out.println("Usrid Correct? " + true);

										memVO = memSvc.findByAccnoEmail(usrid);

										HttpSession session = req.getSession();
										session.setAttribute("memVO", memVO);

										// �Ы�session��ť���A�N�ϥΪ̦W�٦s��ܤW�u�C��onLineList
										session.setAttribute("onlineUser", new LoginSessionvBindingListener(usrid));

										String location = (String) session.getAttribute("location");// ���Xsession�O�_���s�ӷ�����
										if (location != null)
											{
												session.removeAttribute("location"); // �p�G���ӷ�����,���M��session��location�ݩ�,�A���ɦ^�ӷ�����
												res.sendRedirect(location); // location�s��req.getRequestURI,�p/frontdend/members/memLogin.jsp
												return;
											}

										else
											{
												// �Y�S���ӷ�����, �ɦ^����
												res.sendRedirect(req.getContextPath() + "/frontend/front_index.jsp");
											}

									} catch (Exception ignored)
									{
									}

							} else
							{
								errorMsgs.add("�b���K�X���~�άd�L���ϥΪ�");
								RequestDispatcher failureView = req
										.getRequestDispatcher("/frontend/members/memLogin.jsp");
								failureView.forward(req, res);
								return;
							}

						/*************************** ��L�i�઺���~�B�z **********************************/
					} catch (Exception e)
					{
						errorMsgs.add(e.getMessage());
						System.out.println(e.getMessage());
					}

			}

		// -------------------------------------------�n�X--------------------------------------

		if ("logout".equals(action))
			{
				HttpSession session = req.getSession();
				session.invalidate();

				res.sendRedirect(req.getContextPath() + "/frontend/front_index.jsp");
				return;

			}

		// -------------------------------------------�ܧ�K�X--------------------------------------

		if ("changePwd".equals(action))
			{

				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				try
					{

						String oldPassword = req.getParameter("oldPassword").trim();

						String newPassword1 = req.getParameter("newPassword1").trim();
						String newPassword2 = req.getParameter("newPassword2").trim();
						System.out.print(oldPassword);

						System.out.print(newPassword1);

						System.out.print(newPassword2);

						// �����X�ثe�n�J���ϥΪ̱b��
						HttpSession session = req.getSession();
						MemVO memVO = (MemVO) session.getAttribute("memVO");
						MemVO newMemVO = memSvc.findByPrimaryKey(memVO.getMem_id());
						String mem_id = newMemVO.getMem_id();

						// ���X��Ʈw�ӱb�����K�X
						String realPwd = newMemVO.getM_psw();

						String pswReg = "^[(a-zA-Z0-9)]{6,20}$";

						// ���~����
						if (oldPassword == null || newPassword1 == null || newPassword2 == null
								|| oldPassword.trim().length() == 0 || newPassword1.trim().length() == 0
								|| newPassword2.trim().length() == 0)
							{
									{
										errorMsgs.add("�K�X�ФŪť�");
									}
							} else if (!oldPassword.equals(realPwd))
							{

								errorMsgs.add("��K�X��J�����T");

							} else if (!newPassword1.trim().matches(pswReg) || !newPassword2.trim().matches(pswReg))
							{
								errorMsgs.add("�s�K�X��J�榡���~");
							}

						else if (!newPassword1.equals(newPassword2))
							{
								errorMsgs.add("�⦸�K�X��J�K�X���@�P,�Э��s��J");
							} else
							{

								System.out.println("�q�L�T�{�K�X����");

								// �q�L����,�}�l��s�K�X

								memSvc.updatePsw(mem_id, newPassword1);
								System.out.println("�K�X�w��s:" + mem_id + ",�s�K�X:" + newPassword1);
								// ��ssession���
								MemVO newSessionMemVO = memSvc.findByPrimaryKey(memVO.getMem_id());
								session.setAttribute("memVO", newSessionMemVO);

								// �^��ajax ���\�T��
								out.print("true");
								System.out.println("�w�ǰeajax");

							}

						// �p�����~���^��e��
						if (!errorMsgs.isEmpty())
							{
								out.print("�����~�T�����^��e��");
								RequestDispatcher failureView = req
										.getRequestDispatcher("/frontend/members/memArea.jsp");
								System.out.println(errorMsgs);
								failureView.forward(req, res);
								System.out.println("�����~�T�����^��e��");
								return;
							}

						/*************************** ��L�i�઺���~�B�z **********************************/
					} catch (Exception e)
					{
						out.print("Exception");
						errorMsgs.add(e.getMessage());

					}

			}

		// -------------------------------------------�ѰO�K�X�H�eemail--------------------------------------

		if ("forgetPsw".equals(action))
			{

				String directory = "/frontend/members/images/logobg2.png";
				String photoPath = getServletContext().getRealPath(directory);

				System.out.println(photoPath);

				String subject = "ArtsBlock-���]�z���K�X";
				String email = null;

				String input = req.getParameter("input").trim();

				String emailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
				String usridReg = "^[(a-zA-Z0-9)]{6,20}$";

				if (input == null || input.trim().length() == 0)
					{
						out.print(false);

					} else if (input.trim().matches(emailReg) || input.trim().matches(usridReg))
					{
						// ��J�q�l�H�c �� �ϥΪ̦W�ٲŦX�榡

						try
							{ // �}�l�d�߬O�_���ӨϥΪ̤αH�e�l�� �æ^��ture
								MemVO memVO = memSvc.findByAccnoEmail(input);

								email = memVO.getM_email();

								MailService.sendMail(email, subject, photoPath, req, memVO);

								out.print(true);

							} catch (Exception e)
							{ // �d�ߤ���h�^��false
								out.print(false);
								e.printStackTrace();
							}

					}

				System.out.println("�q�L�q�l�H�c����");

			}

		// ------------------------------------�qemailURL��ק�K�X����-------------------------------------------

		if ("URLfromEmail".equals(action))
			{

				System.out.println("URLfromEmail");
//			// ���o�ШD�ѼƤ���accno
				String mem_id = req.getParameter("memid");
				MemVO memVO = (MemVO) memSvc.findByPrimaryKey(mem_id);
				System.out.println("URLfromEmail mem_id:" + mem_id);

				// �N�|��vo�s�Jsession�����
				HttpSession session = req.getSession();
				session.setAttribute("memVO", memVO);

				RequestDispatcher forgotPswView = req.getRequestDispatcher("/frontend/members/memVerify.jsp");
				forgotPswView.forward(req, res);

			}

		// ------------------------------------�ק�K�X����-------------------------------------------

		if ("changePwdfromEmail".equals(action))
			{

				try
					{

						// ���~����
						String newPassword1 = req.getParameter("newPassword1").trim();
						String newPassword2 = req.getParameter("newPassword2").trim();
						System.out.println("newPassword1:" + newPassword1 + ",newPassword2:" + newPassword2);
						System.out.println(newPassword1.equals(newPassword2));
						if (newPassword1.equals(newPassword2))
							{

								System.out.println("newPassword1.equals(newPassword2):yessss");

								// ���Xsession�|��VO�Υثe�n�J���ϥΪ̱b��
								HttpSession session = req.getSession();
								MemVO memVO = (MemVO) session.getAttribute("memVO");
								String mem_id = memVO.getMem_id();

								System.out.println("changePwdfromEmail mem_id:" + mem_id);

								// �q�L����,�}�l��s�K�X

								memSvc.updatePsw(mem_id, newPassword1);
								System.out.println("�K�X�w��s:" + mem_id + ",�s�K�X:" + newPassword1);
								// ��ssession���
								MemVO newSessionMemVO = memSvc.findByPrimaryKey(mem_id);
								session.setAttribute("memVO", newSessionMemVO);

								// �^��ajax ���\�T��
								out.print("true");
								System.out.println("�w�ǰeajax");

							} else
							{

								out.print(false);
								return;
							}

						/*************************** ��L�i�઺���~�B�z **********************************/
					} catch (Exception e)
					{
						out.print(false);
						e.getMessage();
						return;

					}

			}

	}

}
