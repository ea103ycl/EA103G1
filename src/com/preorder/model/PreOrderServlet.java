package com.preorder.model;

import java.io.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.*;
import javax.servlet.http.*;

import com.preorderdetail.model.PreOrderDetailService;
import com.preorderdetail.model.PreOrderDetailVO;
import com.preproduct.model.PreProductService;
import com.preproduct.model.PreProductVO;
import com.wel_record.model.WelRecordService;

import tools.MoneyTool;
public class PreOrderServlet extends HttpServlet{
	public void doGet(HttpServletRequest req,HttpServletResponse res)
			throws ServletException,IOException {
		doPost(req,res);
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res)
			throws ServletException,IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String action = req.getParameter("action");
		System.out.println("------------------進入Servlet-----------------------");
		System.out.println("PreOrder - Servlet的action被觸發!");
		
		if("getOne_For_Display".equals(action)) {
			System.out.println("PreOrder - getOne_For_Display被觸發!");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String po_no = req.getParameter("po_no");
				System.out.println("po_no = "+po_no);

				/*****************開始查詢*********************/
				PreOrderDetailService preorderdetailSvc = new PreOrderDetailService();
				List<PreOrderDetailVO> preorderdetaillist = preorderdetailSvc.getAllByPo_no(po_no);
				System.out.println("preorderdetailVO = "+preorderdetaillist);
				
				
				if(preorderdetaillist == null) {
					errorMsgs.add("查沒有這筆資料阿...");
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/preproduct/order_Success_List.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*****************查詢成功準備轉交*********************/
				req.setAttribute("preorderVO",preorderdetaillist);
				req.setAttribute("po_no",po_no);
				String url = "/frontend/preproduct/order_Detail_Page.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料，愛你唷"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/preproduct/order_Success_List.jsp");
				failureView.forward(req,res);
			}
		}
		
		if("getOne_For_Display2".equals(action)) {
			System.out.println("PreOrder - getOne_For_Display被觸發!");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String po_no = req.getParameter("po_no");
				System.out.println("po_no = "+po_no);

				/*****************開始查詢*********************/
				PreOrderDetailService preorderdetailSvc = new PreOrderDetailService();
				List<PreOrderDetailVO> preorderdetaillist = preorderdetailSvc.getAllByPo_no(po_no);
				System.out.println("preorderdetailVO = "+preorderdetaillist);
				
				
				if(preorderdetaillist == null) {
					errorMsgs.add("查沒有這筆資料阿...");
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/preproduct/preOrder.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*****************查詢成功準備轉交*********************/
				req.setAttribute("preorderdetailVO",preorderdetaillist);
				req.setAttribute("po_no",po_no);
				String url = "/backend/preproduct/preOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料，愛你唷"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/preproduct/preOrder.jsp");
				failureView.forward(req,res);
			}
		}
		
		if("cancel_order".equals(action)) {
			System.out.println("PreOrder - Servlet(取消訂單)被觸發!");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String formhash = req.getParameter("formhash");
				System.out.println("拿到表單的formhash = "+formhash);
				Set<String> formhashSession = (Set<String>) session.getAttribute("formhashSession");
				System.out.println("拿到session里面的集合 = "+formhashSession);
				
				if(formhashSession == null || !formhashSession.contains(formhash)) {
//					formhashSession.remove(formhash);
					session.setAttribute("formhashSession", formhashSession);
					RequestDispatcher failureView = req.getRequestDispatcher
							("/frontend/preproduct/order_Success_List.jsp");
					failureView.forward(req, res);
					return;
				}else {
					System.out.println("一切正常");
				}
				
				String po_no = req.getParameter("po_no");
				System.out.println("要取消的訂單編號為po_no = "+po_no);
				Integer po_total = (new Integer(req.getParameter("po_total")));
				System.out.println("要退款的金額為po_total = "+po_total);
				
				PreOrderVO preorderVO = new PreOrderVO();
				preorderVO.setPo_no(po_no);
				preorderVO.setPo_total(po_total);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("preorderVO", preorderVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/preproduct/order_Success_List.jsp");
					failureView.forward(req, res);
					errorMsgs.clear();
					System.out.println("執行errorMsgs.clear()");
					return;
				}
				/*************************** 2.開始退款及刪除資料 ***************************************/
				System.out.println("準備使用MoneyTool工具");
				Boolean ifCheckOutSucess = MoneyTool.checkOut(session, 36, po_no, po_total);
				System.out.println("使用完MoneyTool工具");
				
				
				System.out.println("進入刪除訂單階段");
				PreOrderService preorderSvc = new PreOrderService();
				preorderSvc.deletePreOrder(po_no);
				
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/
				System.out.println("操作成功，從Session裡把formhash刪除");
				formhashSession.remove(formhash);
				session.setAttribute("formhashSession", formhashSession);
				session.removeAttribute("formhashSession");

				System.out.println("刪除完成,準備轉交url");
				String url = "/frontend/preproduct/order_Success_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				

			} catch (Exception e) {
				errorMsgs.add("無法取得資料，愛你唷"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/preproduct/order_Success_List.jsp");
				failureView.forward(req,res);
			}
			
			
		}
		if("look_discount_pono".equals(action)) {
			System.out.println("PreOrder - Servlet(look_discount_pono)被觸發!");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String po_prod_no = req.getParameter("po_prod_no");
				System.out.println("取得po_prod_no = "+po_prod_no);
				Integer reach_number = new Integer(req.getParameter("reach_number"));
				System.out.println("取得reach_number = "+reach_number);
				
				/*****************開始查詢*********************/
				
				PreOrderService preorderSvc = new PreOrderService();
				List<PreOrderVO> preorderlist = preorderSvc.look_discount_pono(reach_number,po_prod_no);
				System.out.println("preorderdetailVO = "+preorderlist);
				System.out.println("從DAO離開 回到Servlet，準備成功轉交");
				
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/preproduct/PonoByReachDiscount.jsp");
					failureView.forward(req, res);
					return;
				}
				/*****************查詢成功準備轉交*********************/
				HttpSession ordersession = req.getSession();
				ordersession.setAttribute("preorderlist",preorderlist);
				System.out.println("ordersession = "+ordersession);

				String url = "/backend/preproduct/PonoByReachDiscount.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				System.out.println("大結局");
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料，愛你唷"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/preproduct/quantityOfSale.jsp");
				failureView.forward(req,res);
			}
		}
		if ("update_status".equals(action)) {
			System.out.println("-----Servlet觸發update_status-----");
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String po_no = new String(req.getParameter("po_no"));
				System.out.println("取得po_prod_no = "+po_no);
				/*********************************************************************************/
				Integer po_status = new Integer(req.getParameter("po_status"));
				System.out.println("取得event_p_no = "+po_status);
				/*********************************************************************************/
				
				/*********************************************************************************/
				PreOrderVO preorderVO = new PreOrderVO();
				preorderVO.setPo_no(po_no);
				preorderVO.setPo_status(po_status);
				
				/*********************************************************************************/
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("preorderVO", preorderVO);
					System.out.println("判斷到errorMsgs.isEmpty()不是空的");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/preproduct/preOrder.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				/***************************2.開始修改資料*****************************************/
				PreOrderService preorderSvc = new PreOrderService();
				System.out.println("準備轉入Servic執行 = Svc.updateStatus");
				preorderVO = preorderSvc.updateStatus(po_status,po_no);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("preorderVO", preorderVO);
				String url = "/backend/preproduct/preOrder.jsp";
				System.out.println("-----Servlet修改成功.準備轉交-----");
				RequestDispatcher successView = req.getRequestDispatcher(url);
				System.out.println("***************************************************");
				successView.forward(req, res);
	
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/preproduct/preOrder.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("returnDiscount".equals(action)) {
			System.out.println("-----Servlet觸發returnDiscount-----");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
			/***************************0.阻擋重複傳值問題**********************/
				String formhash = req.getParameter("formhash");
				System.out.println("拿到表單的formhash = "+formhash);
				Set<String> formhashSession = (Set<String>) session.getAttribute("formhashSession");
				System.out.println("拿到session里面的集合 = "+formhashSession);
				
				if(formhashSession == null || !formhashSession.contains(formhash)) {
//					formhashSession.remove(formhash);
					session.setAttribute("formhashSession", formhashSession);
					RequestDispatcher failureView = req.getRequestDispatcher
							("/backend/preproduct/PonoByReachDiscount.jsp");
					failureView.forward(req, res);
					return;
				}else {
					System.out.println("一切正常");
				}
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			String po_no = req.getParameter("po_no");
			System.out.println("取值po_no = "+po_no);
			//--------------------------------------------------------------------------------
			Integer po_status = new Integer(req.getParameter("po_status"));
			if(po_status == 5) {
				errorMsgs.add("本訂單已結案不得發送折讓金!");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/preproduct/PonoByReachDiscount.jsp");
				failureView.forward(req, res);
				return;
			}else
			{
				po_status = 5;
			}
			po_status = 5;
			System.out.println("取值po_status = "+po_status);
			//--------------------------------------------------------------------------------
			String mem_id = req.getParameter("mem_id");
			System.out.println("取值mem_id = "+mem_id);
			//--------------------------------------------------------------------------------
			Integer return_discount = new Integer(req.getParameter("return_discount"));
			System.out.println("取值return_discount = "+return_discount);
			
			PreOrderVO preorderVO = new PreOrderVO();
			preorderVO.setPo_no(po_no);
			preorderVO.setPo_status(po_status);
			
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("preorderVO", preorderVO);
				System.out.println("判斷到errorMsgs.isEmpty()不是空的");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/preproduct/PonoByReachDiscount.jsp");
				failureView.forward(req, res);
				return; //程式中斷
			}
			/***************************2.開始修改資料*****************************************/
			PreOrderService preorderSvc = new PreOrderService();
			System.out.println("準備轉入Servic執行 = preorderSvc.updateStatus");
			preorderVO = preorderSvc.updateStatus(po_status,po_no);
			System.out.println("準備轉入Servic執行 = wrSrc.addWelRecord");
			WelRecordService wrSrc = new WelRecordService();
			wrSrc.addWelRecord(mem_id, 34, po_no, return_discount);
				
			/***************************3.修改完成,準備轉交(Send the Success view)*************/	
			req.setAttribute("preorderVO", preorderVO);
			
			formhashSession.remove(formhash);
			session.setAttribute("formhashSession", formhashSession);
			session.removeAttribute("formhashSession");
			
			String url = "/backend/preproduct/PonoByReachDiscount.jsp";
			System.out.println("-----Servlet發送折讓金成功.準備轉交-----");
			RequestDispatcher successView = req.getRequestDispatcher(url);
			System.out.println("***************************************************");
			successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/preproduct/PonoByReachDiscount.jsp");
				failureView.forward(req, res);
			}
		}

	}
}
