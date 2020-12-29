package com.lv.controller;

import java.io.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.lv.model.LvService;
import com.lv.model.LvVO;

import tools.YclTools;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class LvServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String action = req.getParameter("action");
		
		if ("showPic".equals(action)) {
			res.setContentType("image/gif");
			ServletOutputStream out = res.getOutputStream();
			try {
				Integer lv = Integer.valueOf(req.getParameter("lv").trim());
				LvService lvSvc = new LvService();		
				YclTools.readByteArrayFromDB(req, res, lvSvc.getIconByLv(lv));
			}catch (Exception e) {
				InputStream in = getServletContext().getResourceAsStream("/backend/img/null.jpg");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
			}
		}else if ("showMemLvIcon".equals(action)) {
			res.setContentType("image/gif");
			ServletOutputStream out2 = res.getOutputStream();
			try {
				String mem_id = ((String)req.getParameter("mem_id")).trim();
				LvService lvSvc = new LvService();		
				YclTools.readByteArrayFromDB(req, res, lvSvc.getOneMemberLvIcon(mem_id));
			}catch (Exception e) {
				InputStream in = getServletContext().getResourceAsStream("/backend/img/null.jpg");
				byte[] b = new byte[in.available()];
				in.read(b);
				out2.write(b);
				in.close();
			}
		} else {
			doPost(req, res);
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		// ======================================================================
		// �s�W
		// ======================================================================
		if ("insert".contentEquals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				String lv_nm = req.getParameter("lv_nm");
				if (lv_nm == null || (lv_nm.trim()).length() == 0) {
					errorMsgs.add("�п�J���{�O�W��");
				}

				String f_cnt_Str = req.getParameter("f_cnt");
				boolean isFCntNull = (f_cnt_Str == null || (f_cnt_Str.trim()).length() == 0);
				Integer f_cnt = 0;
				if (isFCntNull) {
					errorMsgs.add("�п�J�һݯ�����");
				} else {
					f_cnt = Integer.valueOf(f_cnt_Str);
					if (f_cnt < 0) {
						errorMsgs.add("�һݯ����ƻ�>0");
					}

					LvService lvSrc = new LvService();
					LvVO lvVO = lvSrc.findByThresholdSetting(f_cnt);
					if (lvVO != null) {
						errorMsgs.add("�t�Τ��w�s�b�����e�����{�O����");
					}
				}

				Part filePart = req.getPart("imgPath");
				boolean isUploadFile = YclTools.isUploadFile(filePart);
				if (!isUploadFile) {
					errorMsgs.add("�ФW�Ǩ��{�O�ϥ�");
				}

				/*************************** 2.�}�l�s�W��� **********************/
				byte[] fileByteArray = YclTools.transPartToByteArray(filePart);

				LvVO lvVO = new LvVO();
				lvVO.setLv_nm(lv_nm);
				lvVO.setF_cnt(isFCntNull ? null : f_cnt);
				lvVO.setIcon(fileByteArray);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("lvVO", lvVO); // �N��J��ƾɦ^�s�W�����A�קK���s��J
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/addLv.jsp");
					failureView.forward(req, res);
					return;
				}

				LvService lvSvc = new LvService();
				lvSvc.insert(lvVO);

				/*************************** 3.�s�W�����ɦ^�d�߭� ***********/
				RequestDispatcher successView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp"); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				System.out.println("���~�T��:" + e.fillInStackTrace().getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/addLv.jsp");
				failureView.forward(req, res);
			}
		}
		
		// ======================================================================
		// ���o�n�ק諸���
		// ======================================================================
		if ("getOneForUpdate".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer lv = Integer.valueOf(req.getParameter("lv"));
				
				LvService lvSvc = new LvService();
				LvVO lvVO = lvSvc.findByPrimaryKey(lv);

				if (lvVO == null) {
					errorMsgs.add("�d�L���");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
				req.setAttribute("lvVO", lvVO); // ��Ʈw���X��lvVO����,�s�Jreq
				String url = "/backend/lv/updateLv.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				System.out.println("���~�T��:" + e.fillInStackTrace().getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp");
				failureView.forward(req, res);
			}
		}

		// ======================================================================
		// �x�s�ק���
		// ======================================================================
		if ("update".contentEquals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				Integer lv = Integer.valueOf(req.getParameter("lv"));
				
				String lv_nm = req.getParameter("lv_nm");
				if (lv_nm == null || (lv_nm.trim()).length() == 0) {
					errorMsgs.add("�п�J���{�O�W��");
				}

				String f_cnt_Str = req.getParameter("f_cnt");
				boolean isFCntNull = (f_cnt_Str == null || (f_cnt_Str.trim()).length() == 0);
				Integer f_cnt = 0;
				if (isFCntNull) {
					errorMsgs.add("�п�J�һݯ�����");
				} else {
					f_cnt = Integer.valueOf(f_cnt_Str);
					if (f_cnt < 0) {
						errorMsgs.add("�һݯ����ƻ�>0");
					}

					LvService lvSrc = new LvService();
					LvVO lvVO = lvSrc.findByThresholdSetting(f_cnt);
					if (lvVO != null && lvVO.getLv() != lv) {
						errorMsgs.add("�t�Τ��w�s�b�����e�����{�O����");
					}
				}

				Part filePart = req.getPart("imgPath");
				boolean isUploadFile = YclTools.isUploadFile(filePart);

				/*************************** 2.�}�l�ק��� **********************/
				byte[] fileByteArray = null;
				if (isUploadFile) {
					fileByteArray = YclTools.transPartToByteArray(filePart);
				}else {
					LvService lvSvc = new LvService();
					LvVO lvVO = lvSvc.findByPrimaryKey(lv);
					fileByteArray = lvVO.getIcon();
				}
				
				LvVO lvVO = new LvVO();
				lvVO.setLv(lv);
				lvVO.setLv_nm(lv_nm);
				lvVO.setF_cnt(isFCntNull ? null : f_cnt);
				lvVO.setIcon(fileByteArray);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("lvVO", lvVO); // �N��J��ƾɦ^�s�W�����A�קK���s��J
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/updateLv.jsp");
					failureView.forward(req, res);
					return;
				}

				LvService lvSvc = new LvService();
				lvSvc.update(lvVO);
				
				/*************************** 3.�s�W�����ɦ^�d�߭� ***********/
				RequestDispatcher successView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp"); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				System.out.println("���~�T��:" + e.fillInStackTrace().getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/updateLv.jsp");
				failureView.forward(req, res);
			}
		}

		// ======================================================================
		// �R��
		// ======================================================================
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				Integer lv = Integer.valueOf(req.getParameter("lv"));
				LvService lvSvc = new LvService();
				LvVO lvVO = lvSvc.findByPrimaryKey(lv);

				// ���o�R���̧C����(�����Ƭ�0)�����
				if (lvVO.getF_cnt() == 0) {
					errorMsgs.add("���o�R���̧C����(�����Ƭ�0)����ơA�ȥi�ק悔�{�O�W�١B���{�O�ϥ�");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.�}�l�R����� *****************************************/
				lvSvc.delete(lv);

				/*************************** 3.�R�������ɦ^�d�߭� ***********/
				RequestDispatcher successView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp"); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				System.out.println("���~�T��:" + e.fillInStackTrace().getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		// ======================================================================
		// �d�ߩҦ����
		// ======================================================================
		if ("getAll".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				LvService lvSvc = new LvService();
				List<LvVO> list = lvSvc.getAll();

				if (list == null) {
					errorMsgs.add("�d�L���");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
				req.setAttribute("list", list); // ��Ʈw���X��lvVO����,�s�Jreq
				String url = "/backend/lv/listAllLv.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				System.out.println("���~�T��:" + e.fillInStackTrace().getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp");
				failureView.forward(req, res);
			}
		}

		// ======================================================================
		// �����s�WOR�ק�
		// ======================================================================
		if ("cancel".equals(action)) {
			String url = "/backend/lv/listAllLv.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

	}
}
