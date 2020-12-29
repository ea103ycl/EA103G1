package com.discount.model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DiscountSettingServlet extends HttpServlet{

	public void doGet(HttpServletRequest req,HttpServletResponse res)
			throws ServletException,IOException {
		doPost(req,res);
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
			throws ServletException,IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("------------------�i�JDiscountServlet-----------------------");
		
		if("insertdiscount".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			System.out.println("Servlet -- Ĳ�oinsertdiscount");
			
			try {
				String event_no = req.getParameter("event_no");
				System.out.println("���� - event_no = " + event_no);
				Integer reach_number = null;
				try {
					reach_number =  new Integer(req.getParameter("reach_number"));
					System.out.println("���� - reach_number = " + reach_number);
				}catch (NumberFormatException e) {
					reach_number = 0;
					errorMsgs.add("�H�ƽж�Ʀr");
					System.out.println("�wadd�H�ƽж�Ʀr.!");
				}
				Integer reach_discount = null;
				try {
					reach_discount =  new Integer(req.getParameter("reach_discount"));
					System.out.println("���� - reach_discount = " + reach_discount);
				}catch (NumberFormatException e) {
					reach_discount = 0;
					errorMsgs.add("�ʤ���ж�Ʀr");
					System.out.println("�wadd�ʤ���ж�Ʀr.!");
				}

				DiscountSettingVO discountsettingVO = new DiscountSettingVO();
				System.out.println("�إ�discountsettingVO");
				discountsettingVO.setReach_discount(reach_discount);
				discountsettingVO.setReach_number(reach_number);
				discountsettingVO.setEvent_no(event_no);
				System.out.println("�N���쪺�ȩ�JdiscountsettingVO��");
				
				if (!errorMsgs.isEmpty()) {
					System.out.println("�w�P�_!errorMsgs.isEmpty()");
					req.setAttribute("discountsettingVO", discountsettingVO); // �t����J�榡���~��preproductVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/preproduct/addDiscount.jsp");
					System.out.println("�w�P�_failureView");
					failureView.forward(req, res);
					System.out.println("�wforward");
					return;
				}
				/***************************2.�}�l�s�W���***************************************/
				System.out.println("�Y�N�}�l�s�W���");
				DiscountSettingService discountsettingSvc = new DiscountSettingService();
				discountsettingVO = discountsettingSvc.addDiscountWithEvent(reach_discount, reach_number, event_no);
				System.out.println("����Svc =>addDiscountWithEvent");
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				System.out.println("�s�W����,�ǳ����url");
				String url = "/backend/preproduct/backendindex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);	
				
				
			}catch (Exception e) {
				errorMsgs.add("�L�k���o��ơA�R�A��"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/preproduct/addDiscount.jsp");
				failureView.forward(req,res);
			}
			
			
			
			
			
			
			
		}
		
		
		
		
		
		
	}

}
