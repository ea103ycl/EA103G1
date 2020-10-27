package com.mem.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.mem.model.MemService;
import com.mem.model.MemVO;

//@WebServlet("/headphotoHandler.do") //mark by YCL
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class HeadphotoHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

//		PrintWriter out = res.getWriter();
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("image/gif"); // 輸出二位元資料

//      取得session會員VO及會員ID
		HttpSession session = req.getSession();
		MemVO memVO = (MemVO) session.getAttribute("memVO");
		String mem_id = memVO.getMem_id();

		String action = req.getParameter("action");
// ---------------------------上傳圖片-------------------------------

		if ("uploadPhoto".equals(action)) {

//從請求參數取得part物件
			Part part = req.getPart("headPhoto");

// 檔案上傳本機的目的地目錄
			String saveDirectory = "/images_uploaded"; // 上傳檔案的目的地目錄;

			String realPath = getServletContext().getRealPath(saveDirectory);
			System.out.println("realPath=" + realPath); // 測試用
			// 自動建立目地目錄
			File fsaveDirectory = new File(realPath);
			if (!fsaveDirectory.exists())
				fsaveDirectory.mkdirs(); // 於 ContextPath 之下,自動建立目地目錄

			; // 取得來源檔案名稱
			File f = new File(fsaveDirectory, "photo" + mem_id);

			System.out.println(f.toString());
			part.write(f.toString());

//再從此本地目錄流入並取得byte[]

			File file = new File(f.toString());
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();// 此資料流會把write的位元資料存到一個內建的byte[]
			byte[] buffer = new byte[8192];
			int i;
			while ((i = fis.read(buffer)) != -1) {
				baos.write(buffer, 0, i);
				baos.flush();
			}

			baos.close();
			fis.close();

			byte[] photo = baos.toByteArray();

//開始送進資料庫

			MemService memSvc = new MemService();

			memSvc.addPhoto(mem_id, photo);

			System.out.println("新增至資料庫成功");

			RequestDispatcher successView = req.getRequestDispatcher("/frontend/members/memArea.jsp");
			successView.forward(req, res);
		}
		// ---------------------------從資料庫撈出圖片-------------------------------

		if ("getPic".equals(action)) {

			System.out.println("有進來getPic");

			MemService memSvc = new MemService();
			byte[] photofromDB = memSvc.getPhoto(mem_id);
			ServletOutputStream servletOut = res.getOutputStream();
			servletOut.write(photofromDB);

			System.out.println("length:" + photofromDB.length);
			System.out.println("已經write Pic");

		}
	}

}
