package com.preorder.Controller;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.mem.model.MemService;
import com.preorder.model.PreOrderService;
import com.preorder.model.PreOrderVO;
import com.preorderdetail.model.PreOrderDetailService;
import com.preorderdetail.model.PreOrderDetailVO;
import com.preproduct.model.PreProductVO;

import tools.MoneyTool;


public class ShoppingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		
		@SuppressWarnings("unchecked")
		List<PreProductVO> buylist = (Vector<PreProductVO>) session.getAttribute("preshoppingcart");
		String action = req.getParameter("action");
		System.out.println("------------------�i�JCHECKOUT - Servlet-----------------------");
		System.out.println("Shopping - Servlet��action�QĲ�o!");
		
		if(!action.equals("CHECKOUT")) {
			System.out.println("Ĳ�o���b>>sevlet != CHECKOUT");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			if(action.equals("DELETE")) {
				System.out.println("Ĳ�o>>action = DELETE");
				String del = req.getParameter("del");
				System.out.println("���X�Ѽ�>>del = "+del);
				int d = Integer.parseInt(del);
				System.out.println("����>>buylist.remove(d);");
				buylist.remove(d);
				System.out.println("�ثe�ʪ����ƶq: "+buylist.size());
				session.setAttribute("shoppingcart", buylist);
				System.out.println("Servlet - �ǳ����hurl!");
				String url ="/frontend/preproduct/shoppingCart.jsp";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				System.out.println("Servlet - �������url!");
				rd.forward(req, res);
				return;
			}
			else if(action.equals("ADD")) {
				System.out.println("Ĳ�o>> action = ADD");
				PreProductVO preproductVO = getPreProductVO(req);
				
				if(buylist == null) {
					System.out.println("�P�_��>> buylist == null");
					buylist = new Vector<PreProductVO>();
					System.out.println("�Nnew Vector<PreProductVO>��Jbuylist��");
					buylist.add(preproductVO);
					System.out.println("����buylist.add(preproductVO);");
				}else {
					System.out.println("�P�_��>> buylist != null");
					if(buylist.contains(preproductVO)) {
						errorMsgs.add("���ӫ~�w�g�[�J�ʪ����o!");
					}else {
						buylist.add(preproductVO);
						System.out.println("����buylist.add(preproductVO);");
					}
				}
			}
			session.setAttribute("preshoppingcart", buylist);
			String url ="/frontend/preproduct/frontindex.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
			System.out.println("����rd.forward(req, res);");
		}
		else if (action.equals("CHECKOUT")) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
				String po_no = null;
				String mem_id = req.getParameter("mem_id");
				System.out.println("����-�|���s�� = " + mem_id);
				Integer po_zip = (new Integer(req.getParameter("po_zip").trim()));
				System.out.println("����-�l���� = " + po_zip);
				String po_name = req.getParameter("po_name");
				System.out.println("����-����H = " + po_name);
				String po_phone = req.getParameter("po_phone");
				System.out.println("����-�s���q�� = " + po_phone);
				String po_addr =  req.getParameter("po_addr");
				System.out.println("����-����a�} = " + po_addr);
				Integer po_total = (new Integer(req.getParameter("total_cart_amt").trim()));
				System.out.println("����-�`���B = " + po_total);
				String po_note = req.getParameter("po_note");
				System.out.println("����-�R��Ƶ� = " + po_note);
				String[] po_prod_no = req.getParameterValues("po_prod_no");
				System.out.println("����-�ӫ~�s�� = " + po_prod_no);
				String[] po_qty = req.getParameterValues("po_qty");
				System.out.println("����-�ӫ~�ƶq = " + po_qty);
				String[] po_price = req.getParameterValues("po_price");
				System.out.println("����-�ӫ~���� = " + po_price);
				
				PreOrderVO preorderVO = new PreOrderVO();
				preorderVO.setMem_id(mem_id);
				preorderVO.setPo_zip(po_zip);
				preorderVO.setPo_name(po_name);
				preorderVO.setPo_phone(po_phone);
				preorderVO.setPo_addr(po_addr);
				preorderVO.setPo_total(po_total);
				preorderVO.setPo_note(po_note);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("preorderVO", preorderVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/preproduct/shoppingCart.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.�}�l�s�W��� ***************************************/
				
				Boolean ifCheckOutSucess = MoneyTool.checkOut(session, 41, po_no, -(po_total));// ���ڽжǤJ�t��
				
				if(ifCheckOutSucess) {
					List<PreOrderDetailVO> list = new ArrayList<>();
					
					for(int i = 0;i<buylist.size();i++) {
						System.out.println("start insert ��"+(i+1)+"��ӫ~");
						Integer po_qty1 = new Integer(po_qty[i]);;
						System.out.println(po_qty1);
						Integer po_price1 = new Integer(po_price[i]);
						String po_prod_no1 = po_prod_no[i];
						System.out.println("�ӫ~�ƶq = "+po_qty1);
						System.out.println("�ӫ~���� = "+po_price1);
						System.out.println("�ӫ~�s�� = "+po_prod_no1);
						
						PreOrderDetailVO preorderdetailVO  = new PreOrderDetailVO();
						preorderdetailVO.setPo_prod_no(po_prod_no1);
						preorderdetailVO.setPo_qty(po_qty1);
						preorderdetailVO.setPo_price(po_price1);
						System.out.println("�w�g�bpreorderdetailVO��set����");
						list.add(preorderdetailVO);
					}
				
				
				PreOrderService preorderSvc = new PreOrderService();
				System.out.println("�ǳư���ipreorderSvc.addPreOrderWithDetail");
				preorderVO = preorderSvc.addPreOrderWithDetail(mem_id, po_zip, po_name, po_phone, po_addr, 1, po_total, po_note,list);
				
				}else {
					errorMsgs.add("�A�i��l�B���Ӱ���!!");
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/preproduct/shoppingCart.jsp");
					failureView.forward(req,res);
					return;
				}

				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = "/frontend/preproduct/order_Success_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				buylist.removeAll(buylist);
				System.out.println("�ǳ�forward");
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/	
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/preproduct/shoppingCart.jsp");
				failureView.forward(req, res);
			}
		}
	}

	private PreProductVO getPreProductVO(HttpServletRequest req) {
		System.out.println("--------------------------------");
		System.out.println("�i�J��>> PreProductVO getPreProductVO");
		 String po_prod_no = req.getParameter("po_prod_no");
		 String event_p_no = req.getParameter("event_p_no");
		 String ma_no = req.getParameter("ma_no"); 
		 String po_price = req.getParameter("po_price");
		 String po_detail = req.getParameter("po_detail");
		 
		 System.out.println("get��PreProductVO�Ҧ��ݩ�");
		 PreProductVO preproductVO = new PreProductVO();
		 
		 preproductVO.setPo_prod_no(po_prod_no);
		 preproductVO.setEvent_p_no(new Integer(event_p_no));
		 preproductVO.setMa_no(ma_no);
		 preproductVO.setPo_price(new Integer(po_price));
		 preproductVO.setPo_detail(po_detail);
		 System.out.println("set��preproductVO�Ҧ��ݩ�>>return");
		 return preproductVO;
		 
		 
	}
}
