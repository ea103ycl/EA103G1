package com.prod.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.prod.model.*;
import javax.servlet.annotation.MultipartConfig;
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)



public class ProdServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		
 //查詢某筆
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String prod = req.getParameter("prod_no");
				if (prod == null || (prod.trim()).length() == 0) {
					errorMsgs.add("商品編號不可為空!!");
				}
				
				
				Integer prod_no = null;
				try {
					prod_no = new Integer(prod);
				} catch (NumberFormatException e) {
					errorMsgs.add("商品編號請填數字!!");
				}
			
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/prod/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				ProdService prodSvc = new ProdService();
				ProdVO prodVO = prodSvc.getOneProd(prod_no);
				if (prodVO == null) {
					errorMsgs.add("查無相關資料");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/prod/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("prodVO", prodVO); 
				RequestDispatcher successView = req.getRequestDispatcher("/backend/prod/listOneProd.jsp"); 
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/prod/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
		
		
		
		
 //修改某一筆
		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer prod_no = new Integer(req.getParameter("prod_no"));
				
				/***************************2.開始查詢資料****************************************/
				ProdService prodSvc = new ProdService();
				ProdVO prodVO = prodSvc.getOneProd(prod_no);
				
				
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("prodVO", prodVO); 
				RequestDispatcher successView = req.getRequestDispatcher("/backend/prod/update_prod_input.jsp");
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/prod/listAllProd.jsp");
				failureView.forward(req, res);
			}
		}
		
		
  //更新商品
		if ("update".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/      				
				
				Integer prod_no = new Integer(req.getParameter("prod_no").trim());
                 
				String prod_name = req.getParameter("prod_name");
				String prod_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,20}$";
				if (prod_name == null || prod_name.trim().length() == 0) {
					errorMsgs.add("商品名稱不可空白!!");
				} else if(!prod_name.trim().matches(prod_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("商品名稱: 只能是中、英文字母、數字和_ ,且長度必需在1到20之間");
	          }
				
				
				Integer ptr_no = new Integer(req.getParameter("ptr_no"));
				
				String ma_no = req.getParameter("ma_no");
				
				
				String price = req.getParameter("prod_price");
				if (price == null || (price.trim()).length() == 0) {
					errorMsgs.add("價錢不可為空!!");
				}
				Integer prod_price = null;
				try {
					prod_price = new Integer(price);
				} catch (NumberFormatException e) {
					errorMsgs.add("價錢請填數字!");
				}
				
				
				String prod_detail = req.getParameter("prod_detail");
				if (prod_detail == null || prod_detail.trim().length() == 0) {
					errorMsgs.add("商品描述不可為空!!");
	            }
				
				
				Integer prod_status = null;
				try {
					prod_status = new Integer(req.getParameter("prod_status").trim());
				} catch (Exception e) {
					errorMsgs.add("請選商品狀態!!");
				}
				
				
				Part part  =req.getPart("prod_pic");
				byte[] prod_pic = null;
				String form = part.getContentType().toLowerCase();
				if (form.contains("application/octet-stream")) {
						ProdService prodSvc = new ProdService();
						ProdVO prodVO = prodSvc.getOneProd(prod_no);
						prod_pic =	prodVO.getProd_pic();
				}else if(!form.contains("image")) {
					errorMsgs.add("僅接受圖檔格式上傳");
				}
				else if(part.getSize()==0) {
					errorMsgs.add("請上傳圖片!!");
				}
				else {
					InputStream in = part.getInputStream();
					prod_pic = new byte[in.available()];
					in.read(prod_pic);
					in.close();
				}
				
					
				ProdVO prodVO = new ProdVO();
				prodVO.setProd_no(prod_no);
				prodVO.setPtr_no(ptr_no);
				prodVO.setMa_no(ma_no);
				prodVO.setProd_name(prod_name);
				prodVO.setProd_price(prod_price);
				prodVO.setProd_detail(prod_detail);
				prodVO.setProd_status(prod_status);
				prodVO.setProd_pic(prod_pic);
				
				
//				 System.out.println("prod_no= "+prod_no);
//				 System.out.println("ptr_no= "+ptr_no);
//				 System.out.println("ma_no= "+ma_no);
//				 System.out.println("prod_name= "+prod_name);
//				 System.out.println("prod_price= "+prod_price);
//				 System.out.println("prod_dtail= "+prod_detail);
//				 System.out.println("prod_status= "+prod_status);
//				 System.out.println("prod_pic= "+prod_pic);
				 
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("prodVO", prodVO); //含有輸入格式錯誤的物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/prod/update_prod_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ProdService prodSvc = new ProdService();
				prodVO = prodSvc.updateProd(prod_no, ptr_no, ma_no, prod_name, prod_price, prod_detail,  prod_status, prod_pic);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("prodVO", prodVO); 
				RequestDispatcher successView = req.getRequestDispatcher("/backend/prod/listOneProd.jsp"); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			 } catch (Exception e) {
					errorMsgs.add("更改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/prod/update_prod_input.jsp");
				failureView.forward(req, res);
			}
		}


		
//新增商品		
		 if ("insert".equals(action)) {
				
				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);
				
				
	     try {
				String prod_name = req.getParameter("prod_name");
				String prod_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,20}$";
				if (prod_name == null || prod_name.trim().length() == 0) {
					errorMsgs.add("商品名稱不可空白!!");
				} else if(!prod_name.trim().matches(prod_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("商品名稱: 只能是中、英文字母、數字和_ ,且長度必需在1到20之間");
	            }
				
				
				
                Integer ptr_no = new Integer(req.getParameter("ptr_no"));
				
               
				String ma_no = req.getParameter("ma_no");
					
				
				String price = req.getParameter("prod_price");
				if (price == null || (price.trim()).length() == 0) {
					errorMsgs.add("價錢不可以為空!!");
				}
				Integer prod_price = null;
				try {
					prod_price = new Integer(price);
				} catch (NumberFormatException e) {
					errorMsgs.add("價錢請填數字!!");
				}
				
				
				
				String prod_detail = req.getParameter("prod_detail");
				if (prod_detail == null || prod_detail.trim().length() == 0) {
					errorMsgs.add("商品描述不可空白!!");
	            }
				
				
				
				Integer prod_status = null;
				try {
					prod_status = new Integer(req.getParameter("prod_status").trim());
				} catch (Exception e) {
					errorMsgs.add("請選商品狀態!!");
				}
				
				
				
				
				byte[] prod_pic =null;
				Part part = req.getPart("prod_pic");
				
				if (part.getSize() == 0) {
					errorMsgs.add("請上傳照片!!");
	            }else{
	            	InputStream in = part.getInputStream();
					prod_pic = new byte[in.available()];
					in.read(prod_pic);
					in.close();
	            }
				
			
			
			
			
				ProdVO prodVO = new ProdVO();
				prodVO.setPtr_no(ptr_no);
				prodVO.setMa_no(ma_no);
				prodVO.setProd_name(prod_name);
				prodVO.setProd_price(prod_price);
				prodVO.setProd_detail(prod_detail);
				prodVO.setProd_status(prod_status);
				prodVO.setProd_pic(prod_pic);
			
	
//				 System.out.println("ptr_no= "+ptr_no);
//				 System.out.println("ma_no= "+ma_no);
//				 System.out.println("prod_name= "+prod_name);
//				 System.out.println("prod_price= "+prod_price);
//				 System.out.println("prod_dtail= "+prod_detail);
//				 System.out.println("prod_status= "+prod_status);
//				 System.out.println("prod_pic= "+prod_pic);
			
				
	
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("prodVO", prodVO); 
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/prod/addProd.jsp");
	                failureView.forward(req, res);
					return;
				}
			 
			 

			/***************************2.開始新增資料***************************************/
			ProdService prodSvc = new ProdService();
			prodVO = prodSvc.addProd( ptr_no,  ma_no,  prod_name, prod_price,  prod_detail,  prod_status,  prod_pic);

			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			RequestDispatcher successView = req.getRequestDispatcher("/backend/prod/listAllProd.jsp"); 
			successView.forward(req, res);//成功的話把資料轉交給listALLProd
			/***************************其他可能的錯誤處理*************************************/
            } catch (Exception e) {
			errorMsgs.add("新增資料失敗:"+e.getMessage());
          	RequestDispatcher failureView = req.getRequestDispatcher("/backend/prod/addProd.jsp");
			failureView.forward(req, res);
		}
	}
	
			 
			 
			 
		
   //刪除商品
		if ("delete".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				Integer prod_no = new Integer(req.getParameter("prod_no"));
				
				/***************************2.開始刪除資料***************************************/
				ProdService prodSvc = new ProdService();
				prodSvc.deleteProd(prod_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/backend/prod/listAllProd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/prod/listAllProd.jsp");
				failureView.forward(req, res);
			}
		}
		
		
 //上下架商品
		if ("getOne_For_Status".equals(action)) { // 來自listAllEmp.jsp

			
				Integer prod_no = new Integer(req.getParameter("prod_no")); 
				
				String status = req.getParameter("prod_status");
				
				
				Integer prod_status = null;
				
					  if (status.equals("1")) {
						prod_status = new Integer("0");
					} else if(status.equals("0")) {
						prod_status = new Integer("1");
					}
			
				
	
				ProdVO prodVO = new ProdVO();
				prodVO.setProd_no(prod_no);
				prodVO.setProd_status(prod_status);
				
	
				

				/***************************2.開始修改資料*****************************************/
				ProdService prodSvc = new ProdService();
				prodVO = prodSvc.ChangeStatus(prod_no, prod_status);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("prodVO", prodVO); 
				String url = "/backend/prod/listAllProd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
		}
		
	
		
	}
}
