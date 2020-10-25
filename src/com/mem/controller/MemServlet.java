package com.mem.controller;
import tools.YclTools;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import java.io.InputStream;

import com.mem.model.MemService;
import com.mem.model.MemVO;


@WebServlet("/frontend/members/mem.do")
@MultipartConfig
public class MemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();
		try {
			String mem_id = (String)req.getParameter("mem_id").trim();
			MemService memberSvc = new MemService();
			YclTools.readByteArrayFromDB(req, res, memberSvc.getMemberPhoto(mem_id));
		} catch (Exception e) {
			InputStream in = getServletContext().getResourceAsStream("/backend/img/null.jpg");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();
		}
		
		
		doPost(req, res);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		res.setContentType("text/html; charset=UTF-8");
		

		if ("InsertMem".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String name = req.getParameter("name");
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
				if (name == null || name.trim().length() == 0) {
					errorMsgs.add("會員姓名請勿空白");
				} else if (!name.trim().matches(nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員姓名請輸入中、英文字母, 且長度必須在2到10之間");
				}
				System.out.println("通過會員姓名驗證");

				String gender = req.getParameter("gender");

				if (gender == null || gender.trim().length() == 0) {
					errorMsgs.add("請選擇性別");
				}
				System.out.println(gender);
				System.out.println("通過性別驗證");

				String mobile = req.getParameter("mobile").trim();
				String mobileReg = "\\d{10}";
				if (mobile == null || mobile.trim().length() == 0) {
					errorMsgs.add("手機請勿空白");
				} else if (!mobile.trim().matches(mobileReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("請輸入十碼數字如0912312312");
				}

				System.out.println("通過手機驗證");

				String email = req.getParameter("email").trim();
				String emailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
				if (email == null || email.trim().length() == 0) {
					errorMsgs.add("電子信箱請勿空白");
				} else if (!email.trim().matches(emailReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("請輸入正確的電子信箱格式");
				}

				System.out.println("通過電子信箱驗證");

				String zip = req.getParameter("zip");

				System.out.println(zip);

				String city = "";
				String district = "";
				Integer zipcode = null;

				if (zip == null || zip.trim().length() == 0) {
					errorMsgs.add("請選擇郵遞區號");
				} else {
					String[] zips = zip.split("\\s+");
					city = zips[1];
					district = zips[2];
					zipcode = Integer.parseInt(zips[0]);
					System.out.println(zipcode);

					System.out.println("通過郵遞區號驗證");
				}
				String address = req.getParameter("address").trim();

				if (address == null || address.trim().length() == 0) {
					errorMsgs.add("地址請勿空白");

				}

				String fullAddress = city + district + address;
				System.out.println(fullAddress);

				System.out.println("通過地址驗證");

				String usrid = req.getParameter("usrid").trim();
				String usridReg = "^[(a-zA-Z0-9)]{6,10}$";
				if (usrid == null || usrid.trim().length() == 0) {
					errorMsgs.add("使用者名稱請勿空白");
				} else if (!usrid.trim().matches(usridReg)) {
					errorMsgs.add("使用者名稱請輸入中、英文字母, 且長度必須在6到10之間");
				}

				System.out.println("通過使用者名稱驗證");

				String psw = req.getParameter("psw").trim();
				String pswReg = "^[(a-zA-Z0-9)]{6,10}$";
				String confirmpsw = null;

				if (psw == null || psw.trim().length() == 0) {
					errorMsgs.add("密碼 請勿空白");
				} else if (!psw.trim().matches(pswReg)) {
					System.out.println(psw);
					errorMsgs.add("密碼只能是大小寫英文字母、數字 , 且長度必需在6到10之間");
				} else {

					System.out.println("通過密碼驗證");
					confirmpsw = req.getParameter("confirmpsw").trim();
					String confirmpswReg = "^[(a-zA-Z0-9)]{6,10}$";
					System.out.println(confirmpsw);
					if (confirmpsw == null || confirmpsw.trim().length() == 0) {
						errorMsgs.add("確認密碼 請勿空白");

					} else if (!confirmpsw.trim().matches(psw)) {
						errorMsgs.add("與第一次輸入密碼不相符,請重新輸入");
					} else if (!confirmpsw.trim().matches(confirmpswReg)) {
						errorMsgs.add("密碼只能是英文字母、數字 , 且長度必需在6到10之間");
					}
					System.out.println("通過確認密碼驗證");
				}

				MemVO memVO = new MemVO();
				memVO.setM_accno(usrid);
				memVO.setM_psw(confirmpsw);
				memVO.setM_gender(gender);
				memVO.setM_name(name);
				memVO.setM_mobile(mobile);
				memVO.setM_city(city);
				memVO.setM_zip(zipcode);
				memVO.setM_addr(address);
				memVO.setM_email(email);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {

					req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memRegister.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				MemService memSvc = new MemService();

				memVO = memSvc.addMem(1, usrid, confirmpsw, name, gender, null, null, mobile, zipcode, city,
						fullAddress, email, null, null, 1, 1, 1, 0, 0);

				System.out.println("已呼叫MemService新增會員");
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/frontend/members/select_page_frontend.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				System.out.println("準備forward");
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memRegister.jsp");
				failureView.forward(req, res);
			}
		}

		if ("checkAccount".equals(action)) {
			System.out.println("checkingAccount");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter out = res.getWriter();
			
			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			try {
				System.out.println(req.getParameter("usrid"));
				String usrid = req.getParameter("usrid").trim();
				if (usrid == null || usrid.trim().length() == 0) {
					errorMsgs.add("使用者名稱請勿空白");

				}

				/*************************** 2.開始搜尋資料,比對帳號是否重覆 ********************************/

				MemService memSvc = new MemService();

				Set<String> usridSet = memSvc.getAllUsrId();

				System.out.println("usrid:" + usrid);
				if (usridSet.contains(usrid)) {

					out.print(true);

				} else {

					out.print(false);
				}
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
			}

		}

		if ("getAllMembers".equals(action)) {

			MemService memSvc = new MemService();
			List<MemVO> list = memSvc.getAll();
			PrintWriter out = res.getWriter();
			JSONArray array = new JSONArray(list);
			out.print(array);

		}

		if ("getOneByIdNameAccout".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter out = res.getWriter();
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				String input = req.getParameter("input");

				System.out.println("input:" + input);
				String inputReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{2,20}$";
				if (input == null || input.trim().length() == 0) {
					errorMsgs.add("請輸入欲搜尋之會員帳號、名稱或編號");
				} else if (!input.matches(inputReg)) {
					errorMsgs.add("輸入格式錯誤, 請重新輸入");
				}
				System.out.println("通過搜尋驗證");

				/*********************** 2.開始搜尋資料 *************************/

				MemService memSvc = new MemService();
				List<MemVO> list = memSvc.findByPKNameAcc(input);

				for (MemVO memVO : list) {
					System.out.println("帳號:" + memVO.getM_accno());
					System.out.println("編號:" + memVO.getMem_id());
					System.out.println("名稱:" + memVO.getM_name());
				}

				JSONArray array = new JSONArray(list);

				out.print(array);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println(e.getMessage());
			}

		}

	}
}




