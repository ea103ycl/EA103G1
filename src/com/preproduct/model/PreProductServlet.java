package com.preproduct.model;

import java.io.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.discount.model.DiscountSettingService;
import com.discount.model.DiscountSettingVO;
import com.preproduct.model.PreProductService;
import com.preproduct.model.PreProductVO;

public class PreProductServlet extends HttpServlet{

	public void doGet(HttpServletRequest req,HttpServletResponse res)
			throws ServletException,IOException {
		doPost(req,res);
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
			throws ServletException,IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("------------------進入Servlet-----------------------");
		System.out.println("Servlet的action被觸發!");
		
		if("getOne_For_Display".equals(action)) { //來自Select_Page.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*接收請求參數 輸入格式的錯誤處理*/
				String str = req.getParameter("po_prod_no");/*用預購商品編號搜尋*/
				if(str==null||(str.trim()).length()==0) {
					errorMsgs.add("阿你還沒輸入員工編號啊?! 你傻了嗎?");
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/preproduct/backendindex.jsp");
					failureView.forward(req, res);
					return;
				}
				String po_prod_no=null;
				try {
					po_prod_no = new  String(str);
				}catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/preproduct/backendindex.jsp");
					failureView.forward(req, res);
					return;
				}
				/*****************開始查詢*********************/
				PreProductService preproductSvc = new PreProductService();
				PreProductVO preproductVO = preproductSvc.getOnePreProduct(po_prod_no);
				if(preproductVO == null) {
					errorMsgs.add("查沒有這筆資料阿...");
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/preproduct/backendindex.jsp");
					failureView.forward(req, res);
					return;
				}
				/*****************查詢成功準備轉交*********************/
				req.setAttribute("preproductVO",preproductVO); /*listOnebackendindex.jsp會取用*/
				String url = "/backend/preproduct/listOnebackendindex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料，愛你唷"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/preproduct/backendindex.jsp");
				failureView.forward(req,res);
			}
		}
		if("getOne_For_Update".equals(action)) {
			System.out.println("Servlet - 觸發getOne_For_Update的action!");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				
				String po_prod_no = new String(req.getParameter("po_prod_no"));
				System.out.println("Servlet - 取得參數po_prod_no = "+po_prod_no);
				
				
				
				/***************************2.開始查詢資料****************************************/
				
				PreProductService preproductSvc = new PreProductService();
				System.out.println("Service - 建立preproductSvc開始查詢資料");
				PreProductVO preproductVO = preproductSvc.getOnePreProduct(po_prod_no);
				System.out.println("Service - 查詢完畢!");
				
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				
				req.setAttribute("preproductVO", preproductVO);         // 資料庫取出的preproductVO物件,存入req
				System.out.println("Servlet - 準備轉交去url!");
				String url = "/backend/preproduct/update_preproduct_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_preproduct_input.jsp
				System.out.println("Servlet - 完成轉交url!");
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			}catch (Exception e) {
				errorMsgs.add("阿就沒辦法取得要改的資料阿:" + e.getMessage());
				System.out.println("Servlet - 發生errorMsgs01");
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/preproduct/backendindex.jsp");
				failureView.forward(req, res);
			}
			
		}
		if("switchPreProduct".equals(action)){
			System.out.println("Servlet - 觸發switchPreProduct的action!");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs",errorMsgs);

			try{
				String po_prod_no = new String(req.getParameter("po_prod_no").trim());
				System.out.println("Servlet - 宣告String po_prod_no =(req.getParameter("+po_prod_no+")");

				java.sql.Timestamp po_start = java.sql.Timestamp.valueOf(req.getParameter("po_start").trim());
				
				Date currentdate = new Date(System.currentTimeMillis());
				SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String strToday = bartDateFormat.format(currentdate);
				Date today = java.sql.Date.valueOf(strToday.toString());
				System.out.println("Servlet - 宣告po_start =(req.getParameter("+po_start+")");
				java.sql.Timestamp po_end = java.sql.Timestamp.valueOf(req.getParameter("po_end").trim());
				System.out.println("Servlet - 宣告po_end =(req.getParameter("+po_end+")");
			    System.out.println("今天:"+today);
			    System.out.println("預購開始日期:"+po_start);
			    System.out.println("預購結束日期:"+po_end);
			    
			    System.out.println("today.after(po_start)= "+today.after(po_start));
			    System.out.println("today.after(po_end)= "+today.after(po_end));
			    System.out.println("最後判斷=  "+(!(today.after(po_start)||(today.after(po_end)))));
			    
				
				if(today.after(po_start)&&(today.after(po_end))){
					errorMsgs.add("本預購商品已過檔期，故無法上架^0^");
				}else if(today.before(po_start)&&(today.before(po_end))){
					errorMsgs.add("此預購商品尚未開始，故無法下架0w0");
				}

				PreProductVO preproductVO = new PreProductVO();
				preproductVO.setPo_prod_no(po_prod_no);
				preproductVO.setPo_start(po_start);
				preproductVO.setPo_end(po_end);
				System.out.println("Servlet - 將剛剛宣告的值set到preproductVO裡");
				try{
				if (!errorMsgs.isEmpty()) {
							req.setAttribute("preproductVO", preproductVO);// 含有輸入格式錯誤的empVO物件,也存入req
							RequestDispatcher failureView = req
									.getRequestDispatcher("/backend/preproduct/backendindex.jsp");
							failureView.forward(req, res);
							return; //程式中斷
						}
				}catch (Exception e) {
					errorMsgs.add("無法取得資料，愛你唷"+e.getMessage());
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/preproduct/backendindex.jsp");
					failureView.forward(req,res);
				}
				/*****************開始查詢*********************/
				PreProductService preproductSvc = new PreProductService();
				System.out.println("Servlet - 創立Service - preproductSvc");
				preproductVO = preproductSvc.switchPreProduct(po_prod_no,po_start,po_end);
				System.out.println("Servlet - 用Svc呼叫switchPreProduct(po_prod_no,po_start,po_end)");
				System.out.println("Servlet - 將Svc放進preproductVO");
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("preproductVO",preproductVO);
				String url = "/backend/preproduct/backendindex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				System.out.println("Servlet - 轉交url");
				successView.forward(req,res);
				/***************************其他可能的錯誤處理*************************************/	
				} catch (Exception e) {
						errorMsgs.add("修改資料失敗:"+e.getMessage());
						RequestDispatcher failureView = req
								.getRequestDispatcher("/backend/preproduct/backendindex.jsp");
						failureView.forward(req, res);
					}
		}
		
			if ("update".equals(action)) { // 來自update_preproduct_input.jsp的請求
				System.out.println("Servlet - 觸發update的action!");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String po_prod_no = new String(req.getParameter("po_prod_no").trim());
				System.out.println("取得po_prod_no = "+po_prod_no);
				Integer event_p_no = new Integer(req.getParameter("event_p_no").trim());
				System.out.println("取得event_p_no = "+event_p_no);
				String ma_no = new String(req.getParameter("ma_no").trim());
				System.out.println("取得ma_no = "+ma_no);
				java.sql.Timestamp po_start = null;
				try {
					po_start = java.sql.Timestamp.valueOf(req.getParameter("po_start").trim());
					System.out.println("取得po_start= "+po_start);
				
				} catch (IllegalArgumentException e) {
					po_start=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				java.sql.Timestamp po_end = null;
				try {
					po_end = java.sql.Timestamp.valueOf(req.getParameter("po_end").trim());
					System.out.println("取得po_end= "+po_end);
				} catch (IllegalArgumentException e) {
					po_end=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				Integer po_price = null;
				try {
					po_price = new Integer(req.getParameter("po_price").trim());
					System.out.println("取得po_price= "+po_price);
				} catch (NumberFormatException e) {
					po_price = 0;
					errorMsgs.add("價格請填數字.");
				}
				String po_detail = (req.getParameter("po_detail").trim());
				System.out.println("取得po_detail= "+po_detail);

				PreProductVO preproductVO = new PreProductVO();
				preproductVO.setPo_prod_no(po_prod_no);
				preproductVO.setEvent_p_no(event_p_no);
				preproductVO.setMa_no(ma_no);
				preproductVO.setPo_start(po_start);
				preproductVO.setPo_end(po_end);
				preproductVO.setPo_price(po_price);
				preproductVO.setPo_detail(po_detail);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("preproductVO", preproductVO);// 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/preproduct/update_preproduct_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				PreProductService preproductSvc = new PreProductService();
				preproductVO = preproductSvc.updatePreProduct(po_prod_no, event_p_no, ma_no, po_start, po_end,po_price, po_detail);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("preproductVO", preproductVO);// 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/backend/preproduct/backendindex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/preproduct/update_preproduct_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insert".equals(action)) { // 來自addbackendindex.jsp的請求  
			System.out.println("-----Servlet觸發insert-----");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			Integer event_p_no=null;
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
				String event_p_noStr=req.getParameter("event_p_no");
				System.out.println("取值 event_p_noStr = " + event_p_noStr);
				
				if(event_p_noStr == null || event_p_noStr.trim().length() == 0)
				{
					errorMsgs.add("請選擇作品編號!");
					System.out.println("已add請選擇作品編號!");
				}else {
					try {
					event_p_no = new Integer(req.getParameter("event_p_no").trim());
					}catch(NumberFormatException e) {
						errorMsgs.add("數字格式不對");
					}
				}

				String ma_no = req.getParameter("ma_no");
				if (ma_no == null || ma_no.trim().length() == 0) {
					errorMsgs.add("請選擇素材!");
					System.out.println("已add請選擇素材! ma_no = " + ma_no);
				}

				java.sql.Timestamp po_start = null;
				System.out.println(req.getParameter("po_start"));
				try {
					po_start = java.sql.Timestamp.valueOf(req.getParameter("po_start")+" 00:00:00");
					System.out.println("預購開始日期! po_start" + po_start);
				} catch (IllegalArgumentException e) {
					po_start=new java.sql.Timestamp(System.currentTimeMillis());
					System.out.println("預購開始日期跑到catch! po_start" + po_start);
					errorMsgs.add("請輸入預購開始日期!");
					
				}
				java.sql.Timestamp po_end = null;
				try {
					po_end = java.sql.Timestamp.valueOf(req.getParameter("po_end").trim()+" 00:00:00");
					System.out.println("預購結束日期! po_end" + po_end);
				} catch (IllegalArgumentException e) {
					
					po_end=new java.sql.Timestamp(System.currentTimeMillis());
					System.out.println("預購結束日期跑到catch! po_end" + po_end);
					errorMsgs.add("請輸入預購結束日期!");
					
				}
				Integer po_price = null;
				try {
					po_price = new Integer(req.getParameter("po_price").trim());
				} catch (NumberFormatException e) {
					po_price = 0;
					errorMsgs.add("價格請填數字");
					System.out.println("已add價格請填數字.!");
				}
				String po_detail = (req.getParameter("po_detail").trim());
		
				PreProductVO preproductVO = new PreProductVO();
				preproductVO.setEvent_p_no(event_p_no);
				preproductVO.setMa_no(ma_no);
				preproductVO.setPo_start(po_start);
				preproductVO.setPo_end(po_end);
				preproductVO.setPo_price(po_price);
				preproductVO.setPo_detail(po_detail);
		
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					System.out.println("已判斷!errorMsgs.isEmpty()");
					req.setAttribute("preproductVO", preproductVO); // 含有輸入格式錯誤的preproductVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/preproduct/addPreProduct.jsp");
					System.out.println("已判斷failureView");
					failureView.forward(req, res);
					System.out.println("已forward");
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				PreProductService preproductSvc = new PreProductService();
				preproductVO = preproductSvc.addPreProduct(event_p_no, ma_no, po_start, po_end, po_price, po_detail);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/backend/preproduct/backendindex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);			
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/preproduct/addbackendindex.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("insertByRanking".equals(action)) {
			System.out.println("-----Servlet觸發insertByRanking-----");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String ma_no = req.getParameter("ma_no");
				System.out.println("取得ma_no = "+ma_no);
				if(ma_no == null || ma_no.trim().length() == 0) {
					errorMsgs.add("請選擇素材");
				}
				java.sql.Timestamp po_start = null;
				try {
					po_start = java.sql.Timestamp.valueOf(req.getParameter("po_start").trim()+" 00:00:00");
					System.out.println("取得po_start = "+po_start);
				}catch (IllegalArgumentException e) {
					po_start=new java.sql.Timestamp(System.currentTimeMillis());
					System.out.println("po_start跑到catch來了");
					errorMsgs.add("請設定開始時間");
				}
				java.sql.Timestamp po_end = null;
				try {
					po_end = java.sql.Timestamp.valueOf(req.getParameter("po_end").trim()+" 00:00:00");
					System.out.println("取得po_end = "+po_end);
				}catch (IllegalArgumentException e) {
					po_end=new java.sql.Timestamp(System.currentTimeMillis());
					System.out.println("po_end跑到catch來了");
					errorMsgs.add("請設定開始時間");
				}
				Integer po_price = null;
				try {
					po_price = new Integer(req.getParameter("po_price").trim());
					System.out.println("取得po_price = "+po_price);
				}catch(NumberFormatException e) {
					po_price=0;
					errorMsgs.add("價格請填數字");
					System.out.println("已add價格請填數字");
				}
				String po_detail = (req.getParameter("po_detail").trim());
				System.out.println("取得po_detail = "+po_detail);
				
				PreProductVO preproductVO = new PreProductVO();
				preproductVO.setMa_no(ma_no);
				preproductVO.setPo_start(po_start);
				preproductVO.setPo_end(po_end);
				preproductVO.setPo_price(po_price);
				preproductVO.setPo_detail(po_detail);
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("preproductVO", preproductVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/preproduct/addPreProduct_ByMano.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************2.開始批量新增資料***************************************/
				PreProductService preproductScv = new PreProductService();
				preproductVO = preproductScv.addByRanking(ma_no, po_start, po_end, po_price, po_detail);
				/***************************3.批量新增完成,準備轉交(Send the Success view)***********/
				String url = "/backend/preproduct/backendindex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}catch(Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/preproduct/backendindex.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllbackendindex.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String po_prod_no = new String(req.getParameter("po_prod_no"));
				
				/***************************2.開始刪除資料***************************************/
				PreProductService preproductSvc = new PreProductService();
				preproductSvc.deletePreProduct(po_prod_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/backend/preproduct/backendindex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/preproduct/backendindex.jsp");
				failureView.forward(req, res);
			}
		}
		if("switchDiscount".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			System.out.println("Servlet 觸發switchDiscount ");
			try {
				/***************************1.接收請求參數***************************************/
				String po_prod_no = req.getParameter("po_prod_no");
				System.out.println("取得預購商品po_prod_no = " + po_prod_no);
				Integer event_p_no = new Integer(req.getParameter("event_p_no").trim());
				System.out.println("取得活動編號event_p_no = " + event_p_no);
				Integer po_price = new Integer(req.getParameter("po_price").trim());
				System.out.println("取得原價po_price = " + po_price);
				
				
				
				/***************************2.開始刪除資料***************************************/
				
				DiscountSettingService discountSvc = new DiscountSettingService();
//				List<DiscountSettingVO> discountList =  discountSvc. 10/20從這邊繼續
				
				/***************************3.完成,準備轉交(Send the Success view)***********/								
				String url = "/backend/preproduct/backendindex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/preproduct/backendindex.jsp");
				failureView.forward(req, res);
			}
			
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
