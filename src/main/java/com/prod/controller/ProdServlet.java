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
		
		
		
 //�d�߬Y��
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String prod = req.getParameter("prod_no").trim();
				if (prod == null || prod.length() == 0) {
					errorMsgs.add("�ӫ~�s�����i����!!");
				}
				
				
				Integer prod_no = null;
				try {
					prod_no = new Integer(prod);
				} catch (NumberFormatException e) {
					errorMsgs.add("�ӫ~�s���ж�Ʀr!!");
				}
			
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/prod/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				ProdService prodSvc = new ProdService();
				ProdVO prodVO = prodSvc.getOneProd(prod_no);
				if (prodVO == null) {
					errorMsgs.add("�d�L�������");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/prod/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("prodVO", prodVO); 
				RequestDispatcher successView = req.getRequestDispatcher("/backend/prod/listOneProd.jsp"); 
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/prod/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
		
		
		
		
 //�ק�Y�@��
		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				Integer prod_no = new Integer (req.getParameter("prod_no").trim());
				
				/***************************2.�}�l�d�߸��****************************************/
				ProdService prodSvc = new ProdService();
				ProdVO prodVO = prodSvc.getOneProd(prod_no);
				
				
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("prodVO", prodVO); 
				RequestDispatcher successView = req.getRequestDispatcher("/backend/prod/update_prod_input.jsp");
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/prod/listAllProd.jsp");
				failureView.forward(req, res);
			}
		}
		
		
  //��s�ӫ~
		if ("update".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/      				
				
				Integer prod_no = new Integer(req.getParameter("prod_no").trim());
                 
				String prod_name = req.getParameter("prod_name").trim();
				if (prod_name == null || prod_name.length() == 0) {
					errorMsgs.add("�ӫ~�W�٤��i�ť�!!");
				} 
				
				
				Integer ptr_no = new Integer(req.getParameter("ptr_no").trim());
				
				String ma_no = req.getParameter("ma_no").trim();
				
				
				String price = req.getParameter("prod_price").trim();
				if ( price == null || price.length() == 0 ) {
					errorMsgs.add("�������i���ũ�0��!!");
				
				}
				Integer prod_price = null;
				try {
					prod_price = new Integer(price);
					if(prod_price <= 0){
						errorMsgs.add("�������i��0���έt��!!");
					}
				} catch (NumberFormatException e) {
					errorMsgs.add("�����ж�Ʀr!");
				}
				
				
				String prod_detail = req.getParameter("prod_detail").trim();
				if (prod_detail == null || prod_detail.length() == 0) {
					errorMsgs.add("�ӫ~�y�z���i����!!");
	            }
				
				
				Integer prod_status = null;
				try {
					prod_status = new Integer(req.getParameter("prod_status").trim());
					System.out.println(prod_status);
				} catch (Exception e) {
					errorMsgs.add("�п�ӫ~���A!!");
				}
				
				
				Part part  =req.getPart("prod_pic");
				byte[] prod_pic = null;
				String form = part.getContentType().toLowerCase();
				if (form.contains("application/octet-stream")) {
						ProdService prodSvc = new ProdService();
						ProdVO prodVO = prodSvc.getOneProd(prod_no);
						prod_pic =	prodVO.getProd_pic();
				}else if(!form.contains("image")) {
					errorMsgs.add("�ȱ������ɮ榡�W��");
				}
				else if(part.getSize()==0) {
					errorMsgs.add("�ФW�ǹϤ�!!");
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
					req.setAttribute("prodVO", prodVO); //�t����J�榡���~������,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/prod/update_prod_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				ProdService prodSvc = new ProdService();
				prodVO = prodSvc.updateProd(prod_no, ptr_no, ma_no, prod_name, prod_price, prod_detail,  prod_status, prod_pic);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("prodVO", prodVO); 
				RequestDispatcher successView = req.getRequestDispatcher("/backend/prod/listOneProd.jsp"); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			 } catch (Exception e) {
					errorMsgs.add("����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/prod/update_prod_input.jsp");
				failureView.forward(req, res);
			}
		}


		
//�s�W�ӫ~		
		 if ("insert".equals(action)) {
				
				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);
				
				
	     try {
				String prod_name = req.getParameter("prod_name").trim();
				if (prod_name == null || prod_name.length() == 0) {
					errorMsgs.add("�ӫ~�W�٤��i�ť�!!");
				} 
				
				
				
                Integer ptr_no = new Integer(req.getParameter("ptr_no").trim());
				
               
				String ma_no = req.getParameter("ma_no").trim();
					
				
				String price = req.getParameter("prod_price").trim();
				if (price == null || price.length() == 0 ) {
					errorMsgs.add("�������i�H����!!");
				}
				Integer prod_price = null;
				try {
					prod_price = new Integer(price);
					if(prod_price <= 0){
						errorMsgs.add("�������i��0���έt��!!");
					}
				} catch (NumberFormatException e) {
					errorMsgs.add("�����ж�Ʀr!!");
				}
				
				
				
				String prod_detail = req.getParameter("prod_detail").trim();
				if (prod_detail == null || prod_detail.length() == 0) {
					errorMsgs.add("�ӫ~�y�z���i�ť�!!");
	            }
				
				
				
				Integer prod_status = null;
				try {
					prod_status = new Integer(req.getParameter("prod_status").trim());
				} catch (Exception e) {
					errorMsgs.add("�п�ӫ~���A!!");
				}
				
				
				
				
				byte[] prod_pic =null;
				Part part = req.getPart("prod_pic");
				
				if (part.getSize() == 0) {
					errorMsgs.add("�ФW�ǷӤ�!!");
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
			 
			 

			/***************************2.�}�l�s�W���***************************************/
			ProdService prodSvc = new ProdService();
			prodVO = prodSvc.addProd( ptr_no,  ma_no,  prod_name, prod_price,  prod_detail,  prod_status,  prod_pic);

			/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
			RequestDispatcher successView = req.getRequestDispatcher("/backend/prod/listAllProd.jsp"); 
			successView.forward(req, res);//���\���ܧ�����浹listALLProd
			/***************************��L�i�઺���~�B�z*************************************/
            } catch (Exception e) {
			errorMsgs.add("�s�W��ƥ���:"+e.getMessage());
          	RequestDispatcher failureView = req.getRequestDispatcher("/backend/prod/addProd.jsp");
			failureView.forward(req, res);
		}
	}
	
			 
			 
			 
		
   //�R���ӫ~
		if ("delete".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				Integer prod_no = new Integer(req.getParameter("prod_no").trim());
				
				/***************************2.�}�l�R�����***************************************/
				ProdService prodSvc = new ProdService();
				prodSvc.deleteProd(prod_no);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				String url = "/backend/prod/listAllProd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/prod/listAllProd.jsp");
				failureView.forward(req, res);
			}
		}
		
		
 //�W�U�[�ӫ~
		if ("getOne_For_Status".equals(action)) { // �Ӧ�listAllEmp.jsp

			
				Integer prod_no = new Integer(req.getParameter("prod_no").trim()); 
				
				String status = req.getParameter("prod_status").trim();
				
				
				Integer prod_status = null;
				
					  if (status.equals("1")) {
						prod_status = new Integer("0");
					} else if(status.equals("0")) {
						prod_status = new Integer("1");
					}
			
				
	
				ProdVO prodVO = new ProdVO();
				prodVO.setProd_no(prod_no);
				prodVO.setProd_status(prod_status);
				
	
				

				/***************************2.�}�l�ק���*****************************************/
				ProdService prodSvc = new ProdService();
				prodVO = prodSvc.ChangeStatus(prod_no, prod_status);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("prodVO", prodVO); 
				String url = "/backend/prod/listAllProd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
		}
		
	
		
	}
}
