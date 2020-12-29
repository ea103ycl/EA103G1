package com.bidding.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.bidding.model.BdRedis;
import com.bidding.model.BiddingService;
import com.bidding.model.BiddingVO;

//@WebServlet("/bidding/BiddingServlet") //mark by YCL
public class BiddingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String memId = req.getParameter("memId");
				String memIdReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (memId == null || (memId.trim()).length() == 0) {
					errorMsgs.add("�|��id:���ର�ť�");
				} else if (!memId.trim().matches(memIdReg)) {
					errorMsgs.add("�|��id: �u��O���B�^��r���B�Ʀr�M_�A���ץ��ݦb2��10����");
				}

				Integer bdProdNo = null;
				try {
					bdProdNo = new Integer(req.getParameter("bdProdNo").trim());
				} catch (NumberFormatException e) {
					bdProdNo = 0;
					errorMsgs.add("�ӫ~�s���ж�Ʀr");
				}

				Integer bdPrice = null;
				try {
					bdPrice = new Integer(req.getParameter("bdPrice").trim());
				} catch (NumberFormatException e) {
					bdPrice = 0;
					errorMsgs.add("�o�Ъ��B�ж�Ʀr");
				}

				java.sql.Timestamp bdDateStr = null;
				try {
					bdDateStr = java.sql.Timestamp.valueOf(req.getParameter("bdDateStr").trim());
				} catch (IllegalArgumentException e) {
					bdDateStr = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("�п�J�}�l�ɶ�!");
				}

				java.sql.Timestamp bdDateEnd = null;
				try {
					bdDateEnd = java.sql.Timestamp.valueOf(req.getParameter("bdDateEnd").trim());
				} catch (IllegalArgumentException e) {
					bdDateEnd = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("�п�J�I�Юɶ�!");
				}

				Integer bdStatus = null;
				try {
					bdStatus = new Integer(req.getParameter("bdStatus"));
				} catch (NumberFormatException e) {
					bdStatus = 0;
					errorMsgs.add("�v�Ъ��A�u�ର0,1");
				}

				Integer bdOrdStatus = null;
				try {
					bdOrdStatus = new Integer(req.getParameter("bdOrdStatus"));
				} catch (NumberFormatException e) {
					bdOrdStatus = 0;
					errorMsgs.add("�v�Эq�檬�A�u�ର0-4");
				}

				Integer pmtStatus = null;
				try {
					pmtStatus = new Integer(req.getParameter("bdOrdStatus"));
				} catch (NumberFormatException e) {
					pmtStatus = 0;
					errorMsgs.add("�I�ڪ��A���ର�ŭ�");
				}

				Integer bdZip = null;
				try {
					bdZip = new Integer(req.getParameter("bdOrdStatus"));
				} catch (NumberFormatException e) {
					bdZip = 00000;
					errorMsgs.add("�l���ϸ����ର�ŭ�");
				}

				String bdName = req.getParameter("bdName");
				if (bdName == null || (bdName.trim()).length() == 0) {
					errorMsgs.add("�п�J����H�m�W");
				}
				String bdPhone = req.getParameter("bdPhone");
				if (bdPhone == null || (bdPhone.trim()).length() == 0) {
					errorMsgs.add("�п�J����H�q��");
				}
				String bdAddr = req.getParameter("bdAddr");
				if (bdAddr == null || (bdAddr.trim()).length() == 0) {
					errorMsgs.add("�п�J����a�}");
				}

				BiddingVO bVO = new BiddingVO();
				bVO.setMemId(memId);
				bVO.setBdProdNo(bdProdNo);
				bVO.setBdPrice(bdPrice);
				bVO.setBdDateStr(bdDateStr);
				bVO.setBdDateEnd(bdDateEnd);
				bVO.setBdStatus(bdStatus);
				bVO.setBdOrdStatus(bdOrdStatus);
				bVO.setPmtStatus(pmtStatus);
				bVO.setBdZip(bdZip);
				bVO.setBdName(bdName);
				bVO.setBdPhone(bdPhone);
				bVO.setBdAddr(bdAddr);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("bVO", bVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/bidding/biddingView.html");
					failureView.forward(req, res);
					return;
				}

				/************************* �}�l�s�W��� ***************************/

				BiddingService bdSvc = new BiddingService();
				bdSvc.insert(memIdReg, bdProdNo, bdPrice, bdDateStr, bdDateEnd, bdStatus, bdOrdStatus, pmtStatus, bdZip,
						bdName, bdPhone, bdAddr);

			} catch (Exception e) {
				errorMsgs.add("�s�W��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(req.getContextPath()+"/backend/bidding/biddingView.html");
				failureView.forward(req, res);

			}
		}

		// ============
		if ("getOne".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String bdNo = req.getParameter("bdNo");
				if (bdNo == null || (bdNo.trim()).length() == 0) {
					errorMsgs.add("�s�����~");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/bidding/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** �}�l�d�߸�� ***************************/
				BiddingService bdSvc = new BiddingService();
				BiddingVO bVO = bdSvc.getOne(bdNo);
				if (bVO == null) {
					errorMsgs.add("�d�L�����");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/bidding/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** �d�ߧ���,�ǳ���� *************************/

				req.setAttribute("bVO", bVO);
				String url ="/backend/bidding/listOne.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z ************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/bidding/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		// ============
		if ("getOneForUpdate".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String bdNo = req.getParameter("bdNo");

				BiddingService bdSvc = new BiddingService();
				BiddingVO bVO = bdSvc.getOne(bdNo);

				req.setAttribute("bVO", bVO);
				String url ="/backend/bidding/update_bidding_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���: " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/bidding/listAll.jsp");
				failureView.forward(req, res);
			}
		}

		// ============
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String bdNo = req.getParameter("bdNo");
				String bdNoReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (bdNo == null || (bdNo.trim().length() == 0)) {
					errorMsgs.add("�v�нs�����ର�ť�");
				} else if (!bdNo.trim().matches(bdNoReg)) {
					errorMsgs.add("�v�нs��: �u��O���B�^��r���B�Ʀr�M_�A���ץ��ݦb2��10����");
				}

				String memId = req.getParameter("memId");
				String memIdReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (memId == null || (memId.trim()).length() == 0) {
					errorMsgs.add("�|��id:���ର�ť�");
				} else if (!memId.trim().matches(memIdReg)) {
					errorMsgs.add("�|��id: �u��O���B�^��r���B�Ʀr�M_�A���ץ��ݦb2��10����");
				}

				Integer bdProdNo = null;
				try {
					bdProdNo = new Integer(req.getParameter("bdProdNo").trim());
				} catch (NumberFormatException e) {
					bdProdNo = 0;
					errorMsgs.add("�ӫ~�s���ж�Ʀr");
				}

				Integer bdPrice = null;
				try {
					bdPrice = new Integer(req.getParameter("bdPrice").trim());
				} catch (NumberFormatException e) {
					bdPrice = 0;
					errorMsgs.add("�o�Ъ��B�ж�Ʀr");
				}

				java.sql.Timestamp bdDateStr = null;
				try {
					bdDateStr = java.sql.Timestamp.valueOf(req.getParameter("bdDateStr").trim());
				} catch (IllegalArgumentException e) {
					bdDateStr = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("�п�J�}�l�ɶ�!");
				}

				java.sql.Timestamp bdDateEnd = null;
				try {
					bdDateEnd = java.sql.Timestamp.valueOf(req.getParameter("bdDateEnd").trim());
				} catch (IllegalArgumentException e) {
					bdDateEnd = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("�п�J�I�Юɶ�!");
				}

				Integer bdStatus = null;
				try {
					bdStatus = new Integer(req.getParameter("bdStatus"));
				} catch (NumberFormatException e) {
					bdStatus = 0;
					errorMsgs.add("�v�Ъ��A�u�ର0,1");
				}

				Integer bdOrdStatus = null;
				try {
					bdOrdStatus = new Integer(req.getParameter("bdOrdStatus"));
				} catch (NumberFormatException e) {
					bdOrdStatus = 0;
					errorMsgs.add("�v�Эq�檬�A�u�ର0-4");
				}

				Integer pmtStatus = null;
				try {
					pmtStatus = new Integer(req.getParameter("pmtStatus"));
				} catch (NumberFormatException e) {
					pmtStatus = 0;
					errorMsgs.add("�I�ڪ��A�ж�Ʀr");
				}

				Integer bdZip = null;
				try {
					bdZip = new Integer(req.getParameter("bdZip"));
				} catch (NumberFormatException e) {
					bdZip = 00000;
					errorMsgs.add("�l���ϸ��ж�Ʀr");
				}

				String bdName = req.getParameter("bdName");
				if (bdName == null || (bdName.trim()).length() == 0) {
					errorMsgs.add("�п�J����H�m�W");
				}

				String bdPhone = req.getParameter("bdPhone");
				String bdPhoneReg = "^[(0-9)]{10}$";
				if (bdPhone == null || (bdPhone.trim()).length() == 0) {
					errorMsgs.add("�п�J�q�ܸ��X");
				} else if (!bdPhone.trim().matches(bdPhoneReg)) {
					errorMsgs.add("�п�J���T�q�ܸ��X");
				}

				String bdAddr = req.getParameter("bdAddr");
				if (bdAddr == null || (bdAddr.trim()).length() == 0) {
					errorMsgs.add("�п�J����a�}");
				}

				BiddingVO bVO = new BiddingVO();
				bVO.setBdNo(bdNo);
				bVO.setMemId(memId);
				bVO.setBdProdNo(bdProdNo);
				bVO.setBdPrice(bdPrice);
				bVO.setBdDateStr(bdDateStr);
				bVO.setBdDateEnd(bdDateEnd);
				bVO.setBdStatus(bdStatus);
				bVO.setBdOrdStatus(bdOrdStatus);
				bVO.setPmtStatus(pmtStatus);
				bVO.setBdZip(bdZip);
				bVO.setBdName(bdName);
				bVO.setBdPhone(bdPhone);
				bVO.setBdAddr(bdAddr);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("bVO", bVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/bidding/update_bidding_input.jsp");
					failureView.forward(req, res);
					return;
				}
				BiddingService bdSvc = new BiddingService();
				bVO = bdSvc.update(bdNo, memId, bdProdNo, bdPrice, bdDateStr, bdDateEnd, bdStatus, bdOrdStatus,
						pmtStatus, bdZip, bdName, bdPhone, bdAddr);

				req.setAttribute("bVO", bVO);
				String url ="/backend/bidding/listOne.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�s�W��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/bidding/update_bidding_input.jsp");
				failureView.forward(req, res);
			}
		}


	}
}
