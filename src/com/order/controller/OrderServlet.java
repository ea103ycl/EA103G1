package com.order.controller;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.deal.model.DealService;
import com.deal.model.DealVO;
import com.detail.model.DetailService;
import com.detail.model.DetailVO;
import com.order.model.OrderService;
import com.order.model.OrderVO;
import com.prod.model.ProdVO;

import com.mem.model.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)




public class OrderServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		
	//新增訂單
		if ("insert".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			HttpSession session = req.getSession();
			Vector<ProdVO> buylist = (Vector<ProdVO>) session.getAttribute("shoppingcart");
			
			
			
      try {	
	
			String mem_id = req.getParameter("mem_id");
			String or_name = req.getParameter("or_name");
			
			DealService dealSvc = new DealService();
			Integer balance = dealSvc.getOneDeal(mem_id).getBalance();//從DB取餘額出來
			
			String total = req.getParameter("or_total");
			Integer or_total = new Integer(total);
			if(or_total <=0) {
				errorMsgs.add("結帳金額不正確!!");
			}
			else if(balance < or_total) {
				errorMsgs.add("餘額不足!!!你目前錢包金額是: " +"$"+balance);
			}else if(or_total > 300000){
				errorMsgs.add("單次交易最高總金額為30萬!!");
			}
			
			
			String or_note = req.getParameter("or_note");
			
			
			String zip = req.getParameter("or_zip");
			if (zip == null || (zip.trim()).length() == 0) {
				errorMsgs.add("郵遞區號不可為空!!");
			}
			Integer or_zip = null;
			try {
				or_zip = new Integer(zip);
			} catch (Exception e) {
				errorMsgs.add("郵遞區號格式不正確");
			}
				
			
			
			String or_phone = req.getParameter("or_phone");
			if (or_phone == null || or_phone.trim().length() == 0) {
				errorMsgs.add("電話不可為空!!");
		    }
			
			String or_addr = req.getParameter("or_addr");
			if (or_addr == null || or_addr.trim().length() == 0) {
				errorMsgs.add("收件地址不可為空!!");
		    }


					OrderVO orderVO = new OrderVO();
					orderVO.setMem_id(mem_id);
					orderVO.setOr_name(or_name);
					orderVO.setOr_phone(or_phone);
					orderVO.setOr_zip(or_zip);
					orderVO.setOr_addr(or_addr);
					orderVO.setOr_note(or_note);
					orderVO.setOr_total(or_total);
					
					
					System.out.println(mem_id);
					System.out.println(or_name);
					System.out.println(or_phone);
					System.out.println(or_zip);
					System.out.println(or_addr);
					System.out.println(or_note);
					System.out.println(or_total);
		
		
		 
		if (!errorMsgs.isEmpty()) {
			req.setAttribute("or_total", or_total);
			req.setAttribute("orderVO", orderVO); 
			RequestDispatcher failureView = req
					.getRequestDispatcher("/frontend/shop/Checkout.jsp");
			failureView.forward(req, res);
			return; //程式中斷
		}
			

			
			
			/***************************2.開始新增資料***************************************/
			List<DetailVO> detailList = new ArrayList<>();
			
				 for (int i = 0; i < buylist.size(); i++) {
					 
						ProdVO order = buylist.get(i);
						String prod_name = order.getProd_name();
						Integer prod_qty = order.getProd_qty();
						Integer prod_price = order.getProd_price();
						Integer prod_no = order.getProd_no();
						
						
//						System.out.println("商品名稱: "+prod_name);
//						System.out.println("商品數量: "+prod_qty);
//						System.out.println("商品價格: "+prod_price);
//						System.out.println("商品編號: "+prod_no);
						
						DetailVO detailVO = new DetailVO();
						detailVO.setProd_no(prod_no);
						detailVO.setOr_qty(prod_qty);
						detailVO.setOr_p_price(prod_price);
						detailList.add(detailVO);								
				 }
		 
				 OrderService orderSvc = new OrderService();
				 orderVO = orderSvc.addOrder(null, mem_id,  or_name,  or_phone, or_zip,  or_addr,  or_note,  or_total,  detailList);
					
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("orderVO", orderVO);
				
				
				session.removeAttribute("shoppingcart");//購物車清空
				
				
				String url = "/frontend/shop/Finished.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
				
		 } catch (Exception e) {
				errorMsgs.add("訂單產生失敗:"+e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/frontend/shop/Checkout.jsp");
			failureView.forward(req, res);
		}
	}
	 
		
		
	//明細查詢
		if ("Detail_Search".equals(action)) {
				
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String or_no = req.getParameter("or_no");
				
				/***************************2.開始查詢資料*****************************************/
				DetailService detailSvc = new DetailService();
				List<DetailVO> detail_list = new ArrayList<DetailVO>();
				detail_list = detailSvc.detailSearch(or_no);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("detail_list", detail_list); 
				String url = "/frontend/shop/order/DetailSearch.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
			}

	
	}
}

