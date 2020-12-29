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
import com.eval.model.EvalService;
import com.order.model.OrderService;
import com.order.model.OrderVO;
import com.prod.model.ProdService;
import com.prod.model.ProdVO;



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
		
		
		
	//�s�W�q��
		if ("insert".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			HttpSession session = req.getSession();
			Vector<ProdVO> buylist = (Vector<ProdVO>) session.getAttribute("shoppingcart");
			
			
			
      try {	
	
			String mem_id = req.getParameter("mem_id");
			String or_name = req.getParameter("or_name");
			
			DealService dealSvc = new DealService();
			Integer balance = dealSvc.getOneDeal(mem_id).getBalance();//�qDB���l�B�X��
			
			String total = req.getParameter("or_total");
			Integer or_total = new Integer(total);
			if(or_total <=0) {
				errorMsgs.add("���b���B�����T!!");
			}
			else if(balance < or_total) {
				errorMsgs.add("�l�B����!!!�A�ثe���]���B�O: " +"$"+balance);
			}else if(or_total > 300000){
				errorMsgs.add("�榸����̰��`���B��30�U!!");
			}
			
			
			String or_note = req.getParameter("or_note");
			
			
			String zip = req.getParameter("or_zip");
			if (zip == null || (zip.trim()).length() == 0) {
				errorMsgs.add("�l���ϸ����i����!!");
			}
			Integer or_zip = null;
			try {
				or_zip = new Integer(zip);
			} catch (Exception e) {
				errorMsgs.add("�l���ϸ��榡�����T");
			}
				
			
			
			String or_phone = req.getParameter("or_phone");
			if (or_phone == null || or_phone.trim().length() == 0) {
				errorMsgs.add("�q�ܤ��i����!!");
		    }
			
			String or_addr = req.getParameter("or_addr");
			if (or_addr == null || or_addr.trim().length() == 0) {
				errorMsgs.add("����a�}���i����!!");
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
			return; //�{�����_
		}
			

			
			
			/***************************2.�}�l�s�W���***************************************/
			List<DetailVO> detailList = new ArrayList<>();
			
				 for (int i = 0; i < buylist.size(); i++) {
					 
						ProdVO order = buylist.get(i);
						String prod_name = order.getProd_name();
						Integer prod_qty = order.getProd_qty();
						Integer prod_price = order.getProd_price();
						Integer prod_no = order.getProd_no();
						
						
//						System.out.println("�ӫ~�W��: "+prod_name);
//						System.out.println("�ӫ~�ƶq: "+prod_qty);
//						System.out.println("�ӫ~����: "+prod_price);
//						System.out.println("�ӫ~�s��: "+prod_no);
						
						DetailVO detailVO = new DetailVO();
						detailVO.setProd_no(prod_no);
						detailVO.setOr_qty(prod_qty);
						detailVO.setOr_p_price(prod_price);
						detailList.add(detailVO);								
				 }
		 
				 OrderService orderSvc = new OrderService();
				 orderVO = orderSvc.addOrder(null, mem_id,  or_name,  or_phone, or_zip,  or_addr,  or_note,  or_total,  detailList);
					
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("orderVO", orderVO);
				
				
				session.removeAttribute("shoppingcart");//�ʪ����M��
				
				
				String url = "/frontend/shop/Finished.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z*************************************/
				
		 } catch (Exception e) {
				errorMsgs.add("�q�沣�ͥ���:"+e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/frontend/shop/Checkout.jsp");
			failureView.forward(req, res);
		}
	}
	 
		
		
	//���Ӭd��
		if ("Detail_Search".equals(action)) {
				
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String or_no = req.getParameter("or_no");
				
				/***************************2.�}�l�d�߸��*****************************************/
				DetailService detailSvc = new DetailService();
				List<DetailVO> detail_list = new ArrayList<DetailVO>();
				detail_list = detailSvc.detailSearch(or_no);
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("detail_list", detail_list); 
				String url = "/frontend/shop/order/DetailSearch.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
			}
		
		
		//���q�檬�A_��x
				if ("Change_Order_Status".equals(action)) { 

					
						String or_no = req.getParameter("or_no").trim(); 
						
						String status = req.getParameter("or_status").trim();
						
						System.out.println(or_no);
						System.out.println(status);
						Integer or_status = null;
						
							  if (status.equals("1")) {
								or_status = new Integer("2");
							} else if(status.equals("2")) {
								or_status = new Integer("3");
							}else if(status.equals("3")) {
								or_status = new Integer("4");
							}
					
						
			
						OrderVO orderVO = new OrderVO();
						orderVO.setOr_no(or_no);
						orderVO.setOr_status(or_status);
						
			
						

						/***************************2.�}�l�ק���*****************************************/
						OrderService orderSvc = new OrderService();
						orderVO = orderSvc.ChangeStatus(or_no, or_status);
						
						/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
//						req.setAttribute("orderVO", orderVO); 
						String url = "/backend/prod/listAllOrder.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url); 
						successView.forward(req, res);
				}
				
				
				//���q�檬�A_�R�a
				if ("Change_Order_Status_forBuyer".equals(action)) { 

					
						String or_no = req.getParameter("or_no").trim(); 
						
						String status = req.getParameter("or_status").trim();
						
						System.out.println(or_no);
						System.out.println(status);
						Integer or_status = null;
						
							  if (status.equals("1")) {
								or_status = new Integer("2");
							} else if(status.equals("2")) {
								or_status = new Integer("3");
							}else if(status.equals("3")) {
								or_status = new Integer("4");
							}
					
						
			
						OrderVO orderVO = new OrderVO();
						orderVO.setOr_no(or_no);
						orderVO.setOr_status(or_status);
						
			
						

						/***************************2.�}�l�ק���*****************************************/
						OrderService orderSvc = new OrderService();
						orderVO = orderSvc.ChangeStatus(or_no, or_status);
						
						/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
//						req.setAttribute("orderVO", orderVO); 
						String url = "/frontend/shop/order/OrderSearch.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url); 
						successView.forward(req, res);
				}
				
				
				//�d�ӫ~��������
				if ("Eval_Prod".equals(action)) {
						
						/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
						String or_no = req.getParameter("or_no").trim();
						
						/***************************2.�}�l�d�߸��*****************************************/
						DetailService detailSvc = new DetailService();
						List<DetailVO> detail_list = new ArrayList<DetailVO>();
						detail_list = detailSvc.detailSearch(or_no);
						
						/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
						req.setAttribute("detail_list", detail_list); 
						String url = "/frontend/shop/EvalProd.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url); 
						successView.forward(req, res);
					}
				
				
				//�e�X����
				if ("Eval_Prod_ok".equals(action)) {
						
					
					List<String> errorMsgs = new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgs);
					
					try {
						/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
					Integer data_count = new Integer(req.getParameter("data_count").trim());
					
					for(int i =1; i<=data_count; i++) {
						
					    Integer prod_no = new Integer(req.getParameter("prod_no"+i).trim());
					    
					    Integer eval_rang =0;
					    String rang = req.getParameter("eval_rang"+i).trim();
					    System.out.println(rang);
					    if(rang==null || rang.length()==0) {
					    	errorMsgs.add("�ˡA�ֵ̤��@���P��!!");
					    }else {
					    	 eval_rang = new Integer(rang);
					    }
						String eval_review = req.getParameter("eval_review"+i).trim();
						String or_no = req.getParameter("or_no").trim();
						String mem_id = req.getParameter("mem_id").trim();
						
						 if (!errorMsgs.isEmpty()) {
								DetailService detailSvc = new DetailService();
								List<DetailVO> detail_list = new ArrayList<DetailVO>();
								detail_list = detailSvc.detailSearch(or_no);
								
								
								req.setAttribute("detail_list", detail_list); 
								String url = "/frontend/shop/EvalProd.jsp";
								RequestDispatcher NGView = req.getRequestDispatcher(url); 
								NGView.forward(req, res);
								return;
							
							}else{
								EvalService evalSvc = new EvalService();
								evalSvc.addEval(prod_no, or_no, mem_id, eval_rang, eval_review);
							}
						
						
//						System.out.println(prod_no);
//						System.out.println(or_no);
//						System.out.println(mem_id);
//						System.out.println(eval_rang);
//						System.out.println(eval_review);
						
						
						
					}
						
												
						
						/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
						
						String url = "/frontend/shop/order/OrderSearch.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url); 
						successView.forward(req, res);
					} catch (Exception e) {
						errorMsgs.add("�����ӫ~����:"+e.getMessage());
						e.getStackTrace();
						
						
						
						RequestDispatcher failureView = req.getRequestDispatcher("/frontend/shop/EvalProd.jsp");
						failureView.forward(req, res);
					}
				}
				
				
				
				
				
				//�q�檬�A�d��_��x
				if ("Get_Order_By_Status".equals(action)) { 
					
			    	List<String> errorMsgs = new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgs);
					HttpSession session = req.getSession();
					

					try {
						/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
						Integer or_status = new Integer(req.getParameter("or_status").trim());
						System.out.println(or_status);
						
						/***************************2.�}�l�d�߸��*****************************************/
						OrderService orderSvc = new OrderService();
						List<OrderVO> or_status_list = new ArrayList<OrderVO>();
						or_status_list = orderSvc.getOrderByStauts(or_status);
						
						
						
						
						
						
						
						 if(or_status==100) {
							RequestDispatcher allView = req.getRequestDispatcher("/backend/prod/listAllOrder.jsp"); 
							allView.forward(req, res);
							return;
						}
						 else if(or_status_list.size()==0) {
							errorMsgs.add("�d�L�����q��");
							session.setAttribute("or_status_list",or_status_list);
						}
						 
						 if (!errorMsgs.isEmpty()) {
								RequestDispatcher failureView = req
										.getRequestDispatcher("/backend/prod/OrderbyStatus.jsp");
								failureView.forward(req, res);
								return;//�{�����_
							}
						
						
						
						/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
						session.setAttribute("or_status_list", or_status_list); 
						RequestDispatcher successView = req.getRequestDispatcher("/backend/prod/OrderbyStatus.jsp"); 
						successView.forward(req, res);

						/***************************��L�i�઺���~�B�z*************************************/
					} catch (Exception e) {
						errorMsgs.add("�d�ߥ���!!:" + e.getMessage());
						RequestDispatcher failureView = req
								.getRequestDispatcher("/backend/prod/OrderbyStatus.jsp");
						failureView.forward(req, res);
				
					}
				}
				
				
				//�q�檬�A�d��_�e�x
				if ("Get_Order_By_Status_Buyer".equals(action)) { 
					
			    	List<String> errorMsgs = new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgs);
					HttpSession session = req.getSession();
					

					try {
						/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
						Integer or_status = new Integer(req.getParameter("or_status").trim());
						String mem_id = req.getParameter("mem_id").trim();
						
						/***************************2.�}�l�d�߸��*****************************************/
						OrderService orderSvc = new OrderService();
						List<OrderVO> or_status_list_buyer = new ArrayList<OrderVO>();
						or_status_list_buyer = orderSvc.getOrderByStauts_Buyer(or_status, mem_id);
						
						
						
						
						
						
						
						 if(or_status==100) {
							RequestDispatcher allView = req.getRequestDispatcher("/frontend/shop/order/OrderSearch.jsp"); 
							allView.forward(req, res);
							return;
						}
						 else if(or_status_list_buyer.size()==0) {
							errorMsgs.add("�d�L�����q��");
							session.setAttribute("or_status_list_buyer",or_status_list_buyer);
						}
						 
						 if (!errorMsgs.isEmpty()) {
								RequestDispatcher failureView = req
										.getRequestDispatcher("/frontend/shop/order/OrderSearchByStatus.jsp");
								failureView.forward(req, res);
								return;//�{�����_
							}
						
						
						
						/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
						session.setAttribute("or_status_list_buyer", or_status_list_buyer); 
						RequestDispatcher successView = req.getRequestDispatcher("/frontend/shop/order/OrderSearchByStatus.jsp"); 
						successView.forward(req, res);

						/***************************��L�i�઺���~�B�z*************************************/
					} catch (Exception e) {
						errorMsgs.add("�d�ߥ���!!:" + e.getMessage());
						RequestDispatcher failureView = req
								.getRequestDispatcher("/frontend/shop/order/OrderSearchByStatus.jsp");
						failureView.forward(req, res);
				
					}
				}
				
				

	
	}
}

