package com.prod.controller;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import com.prod.model.ProdService;
import com.prod.model.ProdVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)



public class ShoppingServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		
		
   

	
	//�ҽk�d��
			if ("Fuzzy_Search".equals(action)) { // �Ӧ�select_page.jsp���ШD
				
		    	List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);
				
				HttpSession session = req.getSession();

				try {
					/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
					String prod_name = req.getParameter("prod_name");
					/***************************2.�}�l�d�߸��*****************************************/
					ProdService prodSvc = new ProdService();
					List<ProdVO> fuzzy_list = new ArrayList<ProdVO>();
					fuzzy_list = prodSvc.FuzzySearch(prod_name);
					
					if (fuzzy_list.size()==0) {
						errorMsgs.add("�d�L�����ӫ~!!");
						session.setAttribute("fuzzy_list",fuzzy_list);
					}
					
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/frontend/shop/FuzzySearch.jsp");
						failureView.forward(req, res);
						return;//�{�����_
					}
					
					/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
					session.setAttribute("fuzzy_list", fuzzy_list); 
					RequestDispatcher successView = req.getRequestDispatcher("/frontend/shop/FuzzySearch.jsp"); 
					successView.forward(req, res);

					/***************************��L�i�઺���~�B�z*************************************/
				} catch (Exception e) {
					errorMsgs.add("�d�ߥ���!!:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/shop/FuzzySearch.jsp");
					failureView.forward(req, res);
			
				}
			}
	
			
	//���O�d��
			if ("Ma_Search".equals(action)) { // �Ӧ�select_page.jsp���ШD
				
		    	List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);
				
				HttpSession session = req.getSession();

				try {
					/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
					String ma_no = req.getParameter("ma_no");
					
					/***************************2.�}�l�d�߸��*****************************************/
					ProdService prodSvc = new ProdService();
					List<ProdVO> ma_list = new ArrayList<ProdVO>();
					ma_list = prodSvc.MaSearch(ma_no);
					
					if (ma_list.size()==0) {
						errorMsgs.add("�d�L�����ӫ~!!");
						session.setAttribute("ma_list",ma_list);
					}
					
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/frontend/shop/MaSearch.jsp");
						failureView.forward(req, res);
						return;//�{�����_
					}
					
					/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
					session.setAttribute("ma_list", ma_list); 
					RequestDispatcher successView = req.getRequestDispatcher("/frontend/shop/MaSearch.jsp"); 
					successView.forward(req, res);

					/***************************��L�i�઺���~�B�z*************************************/
				} catch (Exception e) {
					errorMsgs.add("�d�ߥ���!!:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/shop/MaSearch.jsp");
					failureView.forward(req, res);
			
				}
			}
			
			
			
			
			//�̷ӧ@�~�d��
			if ("Ptr_Search".equals(action)) { // �Ӧ�select_page.jsp���ШD
				
		    	List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);
				
				HttpSession session = req.getSession();

				try {
					/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
					Integer ptr_no = new Integer(req.getParameter("ptr_no").trim());
					
					/***************************2.�}�l�d�߸��*****************************************/
					ProdService prodSvc = new ProdService();
					List<ProdVO> ptr_list = new ArrayList<ProdVO>();
					ptr_list = prodSvc.PtrSearch(ptr_no);
					
					if (ptr_list.size()==0) {
						errorMsgs.add("�d�L�����ӫ~!!");
						session.setAttribute("ptr_list",ptr_list);
					}
					
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/frontend/shop/PtrSearch.jsp");
						failureView.forward(req, res);
						return;//�{�����_
					}
					
					/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
					session.setAttribute("ptr_list", ptr_list); 
					RequestDispatcher successView = req.getRequestDispatcher("/frontend/shop/PtrSearch.jsp"); 
					successView.forward(req, res);

					/***************************��L�i�઺���~�B�z*************************************/
				} catch (Exception e) {
					errorMsgs.add("�d�ߥ���!!:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/shop/PtrSearch.jsp");
					failureView.forward(req, res);
			
				}
			}
			
			
			//�P�@�~�@�~���P�����d��
			if ("Other_Ma_Search".equals(action)) { // �Ӧ�select_page.jsp���ШD
				
		    	List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);
				
				

				try {
					/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
					Integer ptr_no = new Integer(req.getParameter("ptr_no").trim());
					String ma_no = req.getParameter("ma_no").trim();
					Integer prod_no = new Integer(req.getParameter("prod_no"));//�p�G�S����L�������ܥγo��
					/***************************2.�}�l�d�߸��*****************************************/
					
					
					ProdService prodSvc = new ProdService();
					ProdVO prodVO = prodSvc.getOneOtherMa(ptr_no, ma_no);
					
				
					
					if (prodVO==null) {
						errorMsgs.add("�٨S�������ӫ~~����^.^");
						ProdVO prodVO2 = prodSvc.getOneProd(prod_no);
						req.setAttribute("prodVO", prodVO2); //�t����J�榡���~������,�]�s�Jreq
						RequestDispatcher failureView = req.getRequestDispatcher("/frontend/shop/ProdDetail.jsp");
						failureView.forward(req, res);
						return; //�{�����_
					}
					
					
					
					/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
					req.setAttribute("prodVO", prodVO); 
					RequestDispatcher successView = req.getRequestDispatcher("/frontend/shop/ProdDetail.jsp"); 
					successView.forward(req, res);
					/***************************��L�i�઺���~�B�z*************************************/
				} catch (Exception e) {
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/shop/ProdDetail.jsp");
					failureView.forward(req, res);
				}
			}
					
					
					
					
			
			
			
			
			
			
	//�d�ݰӫ~�Ա�
			if ("getOne_For_Detail".equals(action)) { 
				try {
					/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
					Integer prod_no = new Integer(req.getParameter("prod_no"));
					/***************************2.�}�l�d�߸��*****************************************/
					ProdService prodSvc = new ProdService();
					ProdVO prodVO = prodSvc.getOneProd(prod_no);
					/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
					req.setAttribute("prodVO", prodVO); 
					RequestDispatcher successView = req.getRequestDispatcher("/frontend/shop/ProdDetail.jsp"); 
					successView.forward(req, res);
					/***************************��L�i�઺���~�B�z*************************************/
				} catch (Exception e) {
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/shop/EShop.jsp");
					failureView.forward(req, res);
				}
			}
			
			
			//�M���ʪ���
			if ("Clear_Cart".equals(action)) { 
				try {
					
					HttpSession session = req.getSession();
					Vector<ProdVO> buylist = (Vector<ProdVO>) session.getAttribute("shoppingcart");
					session.removeAttribute("shoppingcart");//�M���ʪ���
					
					RequestDispatcher successView = req.getRequestDispatcher("/frontend/shop/Cart.jsp"); 
					successView.forward(req, res);
					/***************************��L�i�઺���~�B�z*************************************/
				} catch (Exception e) {
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/shop/Cart.jsp");
					failureView.forward(req, res);
				}
			}
			
			
			
			
			
	}
}

