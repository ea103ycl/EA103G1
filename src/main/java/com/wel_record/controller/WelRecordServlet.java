package com.wel_record.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.wel_record.model.WelRecordService;
import com.wel_record.model.WelRecordVO;

import ecpay.example.EcpayAllInOne;
import tools.MoneyTool;

//@WebServlet("/welRecord/welRecord.do") //mark by YCL

public class WelRecordServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final int WITHDRAW_LIMIT = 300000; // ��鴣��W���T�Q�U

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();

		WelRecordService welRecordSvc = new WelRecordService();

		// �O�_����ɨ�d���\����^
		req.setAttribute("EcpaySuccess", false);
		// �O�_�����ڦ��\
		req.setAttribute("withdrawSuccess", false);

		if ("deposit".equals(action)) {

			// ���o��U�n�J���|��VO
			MemVO memVO = new MemVO();
			HttpSession session = req.getSession();
			memVO = (MemVO) session.getAttribute("memVO");

			System.out.println("memVO.accno:" + memVO.getM_accno());

			List<String> errorMsgsForMoney = new LinkedList<String>();

			req.setAttribute("errorMsgsForMoney", errorMsgsForMoney);

			try {

				// ********************1.����ШD�Ѽƨè̿�J�榡�����~�B�z******************

				Integer amount = new Integer(req.getParameter("depositAmount").trim());

				if (amount <= 0) {
					errorMsgsForMoney.add("���B���o�p�󵥩�s");

				}
				if (!errorMsgsForMoney.isEmpty()) {
					System.out.println("���B�p��s, �ɦ^�쭶��");
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");
					failureView.forward(req, res);
					return;
				}

//				********************2.�}�l���b******************

				Boolean ifCheckOutSucess = MoneyTool.checkOut(session, 10, null, amount);// �x�ȶǤJ����

				if (ifCheckOutSucess) {

					// ��ɳ]�w�ɸ��|
					String paymentConDirectory = "/WEB-INF/payment_conf.xml";
					String paymentConfPath = getServletContext().getRealPath(paymentConDirectory);

					// ��ɪ�^�|���M�Ϥ�url
					StringBuffer url = req.getRequestURL();
					url.append("?action=transfer2memArea&memid=");
					url.append(memVO.getMem_id());
					System.out.println(url.toString());

					// ���ͺ��form�æs�Jrequest��
					String form = EcpayAllInOne.getForm(amount, paymentConfPath, url.toString());
					req.setAttribute("form", form);

					// ���b���\���������ɭ���
					System.out.println("�ǳ���ܺ��");
					String returnUrl = "/frontend/members/memforward2Ecpay.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(returnUrl);
					successView.forward(req, res);

				} else {
					System.out.println("���b����");
					errorMsgsForMoney.add("�x�ȥ���,���ˬd�榡�O�_���T"); // ���b���ѫ᪺�B�z
				}

			} catch (Exception e) {
				errorMsgsForMoney.add("�нT�{��J�榡�O�_���T�I");
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");
				failureView.forward(req, res);
			}

		}

		// �q��ɨ�d��������^�|���M��
		if ("transfer2memArea".equals(action)) {

			// ��ssession��memVO

			String mem_id = req.getParameter("memid");

			MemService memSrc = new MemService();

			MemVO updatedMemVO = memSrc.findByPrimaryKey(mem_id);

			HttpSession session = req.getSession();

			session.setAttribute("memVO", updatedMemVO);

			String url = "/frontend/members/memArea.jsp";
			RequestDispatcher forwardView = req.getRequestDispatcher(url);

			req.setAttribute("EcpaySuccess", true);

			forwardView.forward(req, res);

		}

		if ("withdraw".equals(action)) {

			// ���o��U�n�J���|��VO
			MemVO memVO = new MemVO();
			HttpSession session = req.getSession();
			memVO = (MemVO) session.getAttribute("memVO");

			System.out.println("memVO.accno:" + memVO.getM_accno());

			List<String> errorMsgsForMoney = new LinkedList<String>();

			req.setAttribute("errorMsgsForMoney", errorMsgsForMoney);

			try {

				// ********************1.����ШD�Ѽƨè̿�J�榡�����~�B�z******************

				Integer amount = new Integer(req.getParameter("withdrawAmount").trim());

				if (amount > WITHDRAW_LIMIT) {
					errorMsgsForMoney.add("��鴣��W�����s�x��30�U��");
				}

				if (amount <= 0) {
					errorMsgsForMoney.add("���B���o�p�󵥩�s");

				}

				if (!errorMsgsForMoney.isEmpty()) {

					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");

					failureView.forward(req, res);
					return;
				}

//				********************2.�}�l���b******************

				Boolean ifCheckOutSucess = MoneyTool.checkOut(session, 20, null, -amount);// ���ڽжǤJ�t��

				if (ifCheckOutSucess) {

					String returnUrl = "/frontend/members/memArea.jsp";// ���b���\�����^��|������
					RequestDispatcher successView = req.getRequestDispatcher(returnUrl);
					req.setAttribute("withdrawSuccess", true);
					successView.forward(req, res);

				} else {
					errorMsgsForMoney.add("���⥢��,���ˬd�l�B�O�_�R���ή榡���T"); // ���b���ѫ᪺�B�z

				}

				if (!errorMsgsForMoney.isEmpty()) {

					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");

					failureView.forward(req, res);
					return;
				}

			} catch (Exception e) {
				errorMsgsForMoney.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");
				failureView.forward(req, res);
			}

		}

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>(); // �Ыؤ@��list�ñN���~�T���s�J��list��, �A�N��list�s���req cope��
			req.setAttribute("errorMsgs", errorMsgs);

			try {
//				********************1.����ШD�Ѽƨè̿�J�榡�����~�B�z******************
				String str = req.getParameter("tns_id");
				if (str == null || (str.trim().length() == 0)) // ����J�ο�J�ť�
				{
					errorMsgs.add("�п�J����y����");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/welRecord/select_page_g1.jsp");

					failureView.forward(req, res);
					return;
				}

				Integer tns_id = null;
				try {
					tns_id = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("�y���s���榡�����T");
				}
//				********************2.�}�l�d�߸��******************

				WelRecordVO welRecordVO = welRecordSvc.getOneWelRecord(tns_id);

				if (welRecordVO == null) {
					errorMsgs.add("�d�L���");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/welRecord/select_page_g1.jsp");

					failureView.forward(req, res);
					return;
				}

//				********************3.�d�ߧ���,�ǳ����******************
				req.setAttribute("welRecordVO", welRecordVO);
				String url = "/backend/welRecord/listOneWelRecord.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

//				********************��L�i����~�B�z******************
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/emp/select_page.jsp");
				failureView.forward(req, res);
			}
		}

//			---------------------------------�d�߿��]����by�|���s��----------------------------------

		if ("getAllbyMem_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
//				********************1.�����Ѽ�  �U�Ԧ����S�����~�B�z******************

				String mem_id = req.getParameter("mem_id");

//				********************2.�}�l�d�߸��******************

				List<WelRecordVO> list = welRecordSvc.getWelRecordByMemID(mem_id);

				if (list == null) {
					errorMsgs.add("�d�L���");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/welRecord/select_page_g1.jsp");

					failureView.forward(req, res);
					return;
				}

//				********************3.�d�ߧ���,�ǳ����******************
				req.setAttribute("set", list);
				String url = "/backend/welRecord/listAllWelRecordByMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

//				********************��L�i����~�B�z******************
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/emp/select_page.jsp");
				failureView.forward(req, res);
			}

		}

		// �d�ߥ������]����

		if ("getAllRecords".equals(action)) {

			List<WelRecordVO> list = welRecordSvc.getAll();

			JSONArray array = new JSONArray(list);
			out.print(array);

		}

		// �d�߳�@�|�����]����by�|���s���B�|���b���γ�@����y����

		if ("getOneById_Accno".equals(action)) {

			try {

				// �B�z�ШD�Ѽ�, ���o����ӷ��X
				String input = req.getParameter("input").trim();

				if (input.substring(0, 1).equals("M")) {

					System.out.println("input:" + input);
					List<WelRecordVO> list = welRecordSvc.getWelRecordByMemID(input);

					JSONArray array = new JSONArray(list);
					out.print(array);

				} else if (input.substring(0, 1).equals("4")) {

					int tnsid = Integer.parseInt(input);

					System.out.println("tnsid:" + tnsid);

					WelRecordVO welRecordVO = welRecordSvc.getOneWelRecord(tnsid);

					JSONObject obj = new JSONObject(welRecordVO);

					out.print(obj);

				} else {
					System.out.println(false);
					out.print(false);
				}

			} catch (Exception e) {
				e.getMessage();
				out.print(false);
			}
		}

		// �z�����ӷ�
		if ("filterRecords".equals(action)) {

			try {

				// �B�z�ШD�Ѽ�, ���o����ӷ��X
				String input = req.getParameter("filter").trim();

				int src = Integer.parseInt(input);

				// �}�l�d�߸��

				List<WelRecordVO> list = null;

				if (src == 10 || src == 20) { // �|���x�� or �|������

					list = welRecordSvc.getWelRecordBySrc(src);

				} else if (src == 77) { // ���x����-�q��

					list = welRecordSvc.getWelRecordAmongSrc(40, 43);

				} else if (src == 88) { // ���x����-����/�馩��

					list = welRecordSvc.getWelRecordAmongSrc(30, 34);

				} else { // ���x�h��-�q��

					list = welRecordSvc.getWelRecordAmongSrc(35, 38);

				}

				JSONArray array = new JSONArray(list);
				out.print(array);

			} catch (Exception e) {
				e.getMessage();

			}

		}

	}

}
