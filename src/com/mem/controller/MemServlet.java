package com.mem.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mem.model.MemService;
import com.mem.model.MemVO;

import tools.MailService;

//@WebServlet("/frontend/members/mem.do") //mark by YCL
public class MemServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{

		doPost(req, res);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		MemService memSvc = new MemService();
//會員註冊
		if ("InsertMem".equals(action))
			{

				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				try
					{
						/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
						String name = req.getParameter("name");
						String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
						if (name == null || name.trim().length() == 0)
							{
								errorMsgs.add("會員姓名請勿空白");
							} else if (!name.trim().matches(nameReg))
							{ // 以下練習正則(規)表示式(regular-expression)
								errorMsgs.add("會員姓名請輸入中、英文字母, 且長度必須在2到10之間");
							}
						System.out.println("通過會員姓名驗證");

						String gender = req.getParameter("gender");

						if (gender == null || gender.trim().length() == 0)
							{
								errorMsgs.add("請選擇性別");
							}
						System.out.println(gender);
						System.out.println("通過性別驗證");

						String mobile = req.getParameter("mobile").trim();
						String mobileReg = "\\d{10}";
						if (mobile == null || mobile.trim().length() == 0)
							{
								errorMsgs.add("手機請勿空白");
							} else if (!mobile.trim().matches(mobileReg))
							{ // 以下練習正則(規)表示式(regular-expression)
								errorMsgs.add("請輸入十碼數字如0912312312");
							}

						System.out.println("通過手機驗證");

						String email = req.getParameter("email").trim();
						String emailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
						if (email == null || email.trim().length() == 0)
							{
								errorMsgs.add("電子信箱請勿空白");
							} else if (!email.trim().matches(emailReg))
							{ // 以下練習正則(規)表示式(regular-expression)
								errorMsgs.add("請輸入正確的電子信箱格式");
							}

						System.out.println("通過電子信箱驗證");

						String zip = req.getParameter("zip");

						System.out.println(zip);

						String city = "";

						Integer zipcode = null;

						if (zip == null || zip.trim().length() == 0)
							{
								errorMsgs.add("請選擇郵遞區號");
							} else
							{

								String[] zips = zip.split(" ", 2);
								city = zips[1];
								zipcode = Integer.parseInt(zips[0]);

								System.out.println(zipcode);
								System.out.println("通過郵遞區號驗證");
							}
						String address = req.getParameter("address").trim();

						if (address == null || address.trim().length() == 0)
							{
								errorMsgs.add("地址請勿空白");

							}

						System.out.println("通過地址驗證");

						String usrid = req.getParameter("usrid").trim();
						String usridReg = "^[(a-zA-Z0-9)]{6,20}$";
						if (usrid == null || usrid.trim().length() == 0)
							{
								errorMsgs.add("使用者名稱請勿空白");
							} else if (!usrid.trim().matches(usridReg))
							{
								errorMsgs.add("使用者名稱只能是大小寫英文字母、數字, 且長度必須在6到20之間");
							}

						System.out.println("通過使用者名稱驗證");

						String psw = req.getParameter("psw").trim();
						String pswReg = "^[(a-zA-Z0-9)]{6,20}$";
						String confirmpsw = null;

						if (psw == null || psw.trim().length() == 0)
							{
								errorMsgs.add("密碼 請勿空白");
							} else if (!psw.trim().matches(pswReg))
							{
								System.out.println(psw);
								errorMsgs.add("密碼只能是大小寫英文字母、數字 , 且長度必需在6到20之間");
							} else
							{

								System.out.println("通過密碼驗證");
								confirmpsw = req.getParameter("confirmpsw").trim();
								String confirmpswReg = "^[(a-zA-Z0-9)]{6,20}$";
								System.out.println(confirmpsw);
								if (confirmpsw == null || confirmpsw.trim().length() == 0)
									{
										errorMsgs.add("確認密碼 請勿空白");

									} else if (!confirmpsw.trim().matches(psw))
									{
										errorMsgs.add("與第一次輸入密碼不相符,請重新輸入");
									} else if (!confirmpsw.trim().matches(confirmpswReg))
									{
										errorMsgs.add("密碼只能是英文字母、數字 , 且長度必需在6到20之間");
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
						if (!errorMsgs.isEmpty())
							{

								req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的empVO物件,也存入req
								RequestDispatcher failureView = req
										.getRequestDispatcher("/frontend/members/memRegister.jsp");
								failureView.forward(req, res);
								return;
							}

						/*************************** 2.開始新增資料 ***************************************/

						memSvc.addMem(1, usrid, confirmpsw, name, gender, null, null, mobile, zipcode, city, address,
								email, null, null, 1, 1, 1, 0, 0);

						MemVO registeredMemVO = memSvc.findByAccnoEmail(usrid);

						// 新增註冊但未驗證之會員VO於session內,方便重發確認信
						HttpSession session = req.getSession();
						session.setAttribute("registeredMemVO", registeredMemVO);

						// -----------------新增會員預設圖片---------------------------------------

						// 從此本地目錄流入預設圖並取得圖片byte[]
						String defaultPicDirectory = "";
						if (gender.equals("F"))
							{
								defaultPicDirectory = "/frontend/members/images/femaleDefault.png";
							} else
							{
								defaultPicDirectory = "/frontend/members/images/maleDefault.png";
							}

						String defaultPicPath = getServletContext().getRealPath(defaultPicDirectory);

						File file = new File(defaultPicPath);
						FileInputStream fis = new FileInputStream(file);
						ByteArrayOutputStream baos = new ByteArrayOutputStream();// 此資料流會把write的位元資料存到一個內建的byte[]
						byte[] buffer = new byte[8192];
						int i;
						while ((i = fis.read(buffer)) != -1)
							{
								baos.write(buffer, 0, i);
								baos.flush();
							}

						baos.close();
						fis.close();

						byte[] photo = baos.toByteArray();

						// 開始送進資料庫

						memSvc.addPhoto(registeredMemVO.getMem_id(), photo);

						System.out.println("預設照片資料庫成功");

						System.out.println("已呼叫MemService新增會員");

						/*************************** 3.新增完成,寄送驗證信 ********************/

						String directory = "/frontend/members/images/logobg2.png";
						String photoPath = getServletContext().getRealPath(directory);

						String subject = "ArtsBlock-註冊驗證信";

						MailService.sendEmail4Verify(email, subject, photoPath, req, registeredMemVO);

						/*************************** 4.準備轉交 ***********/
						String url = "/frontend/members/memRegisterSuccess.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url);

						System.out.println("準備forward");
						successView.forward(req, res);

						/*************************** 其他可能的錯誤處理 **********************************/
					} catch (Exception e)
					{
						errorMsgs.add(e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memRegister.jsp");
						failureView.forward(req, res);
					}
			}

		// 重發驗證信
		if ("sendVerifyAgain".equals(action))
			{
				HttpSession session = req.getSession();
				MemVO registeredMemVO = (MemVO) session.getAttribute("registeredMemVO");

				String directory = "/frontend/members/images/logobg2.png";
				String photoPath = getServletContext().getRealPath(directory);

				String subject = "ArtsBlock-註冊驗證信";

				String email = registeredMemVO.getM_email();
				MailService.sendEmail4Verify(email, subject, photoPath, req, registeredMemVO);

				String url = "/frontend/members/memRegisterSuccess.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}

		// 會員驗證信內的驗證鈕
		if ("verify".equals(action))
			{

				String mem_id = req.getParameter("memid");
				MemVO memVO = (MemVO) memSvc.findByPrimaryKey(mem_id);
				System.out.println("verify mem_id:" + mem_id);

				// 將會員vo存入session
				HttpSession session = req.getSession();
				session.setAttribute("memVO", memVO);

				// 創建session監聽器，將使用者名稱存放至上線列表onLineList
				session.setAttribute("onlineUser", new LoginSessionvBindingListener(mem_id));

				// 信箱驗證成功轉回首頁
				RequestDispatcher verifiedView = req.getRequestDispatcher("/frontend/front_index.jsp");
				verifiedView.forward(req, res);
			}

		// 修改會員資料 from memArea.jsp
		if ("UpdateMem".equals(action))
			{
				JSONObject jsonObject = new JSONObject();

				try
					{
						/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
						String name = req.getParameter("name");
						String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
						if (name == null || name.trim().length() == 0)
							{
								jsonObject.put("name", "姓名請勿空白");
							} else if (!name.trim().matches(nameReg))
							{ // 以下練習正則(規)表示式(regular-expression)
								jsonObject.put("name", "會員姓名請輸入中、英文字母, 且長度必須在2到10之間");
							}

						String gender = req.getParameter("gender");

						String birthday = req.getParameter("birthday");

						Date bday = java.sql.Date.valueOf(birthday);

						String mobile = req.getParameter("mobile").trim();

						String mobileReg = "\\d{10}";
						if (mobile == null || mobile.trim().length() == 0)
							{
								jsonObject.put("mobile", "手機請勿空白");
							} else if (!mobile.trim().matches(mobileReg))
							{ // 以下練習正則(規)表示式(regular-expression)
								jsonObject.put("mobile", "請輸入十碼數字如0912312312");
							}

						String phone = req.getParameter("phone").trim();
						String phoneReg = "[0-9]{10}";
						if (!phone.trim().matches(phoneReg))
							{
								jsonObject.put("phone", "請輸入格式如0212345678");
							}

						String email = req.getParameter("email").trim();
						String emailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
						if (email == null || email.trim().length() == 0)
							{
								jsonObject.put("email", "電子信箱請勿空白");
							} else if (!email.trim().matches(emailReg))
							{ // 以下練習正則(規)表示式(regular-expression)
								jsonObject.put("email", "請輸入正確的電子信箱格式");
							}

						String contactZip = req.getParameter("contactZip");

						String contactCity = "";
						Integer contactZipcode = null;
						if (contactZip == null || contactZip.trim().length() == 0)
							{
								jsonObject.put("contactZip", "請輸入郵遞區號");
							} else
							{
								String[] zips = contactZip.split(" ", 2);
								contactCity = zips[1];
								contactZipcode = Integer.parseInt(zips[0]);
							}
						String contactAddress = req.getParameter("contactAddr").trim();

						if (contactAddress == null || contactAddress.trim().length() == 0)
							{
								jsonObject.put("contactAddr", "地址請勿空白");
							}

						if (jsonObject.length() != 0)
							{
								out.print(jsonObject);
								return;
							}

						HttpSession session = req.getSession();
						MemVO memVO = (MemVO) session.getAttribute("memVO");
						String mem_id = memVO.getMem_id();

						memVO.setM_gender(gender);
						memVO.setM_name(name);
						memVO.setM_mobile(mobile);
						memVO.setM_city(contactCity);
						memVO.setM_zip(contactZipcode);
						memVO.setM_addr(contactAddress);
						memVO.setM_email(email);

						/*************************** 2.開始新增資料 ***************************************/
						memSvc = new MemService();
						memSvc.updateByUser(name, gender, bday, phone, mobile, contactZipcode, contactCity,
								contactAddress, email, mem_id);
						System.out.println("已更新會員資料");

						MemVO UpdatedmemVO = memSvc.findByPrimaryKey(mem_id);
						session.setAttribute("memVO", UpdatedmemVO);

						/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
						System.out.println("out.print:true");
						// ajax送回成功訊息
						out.print("true");

						/*************************** 其他可能的錯誤處理 **********************************/
					} catch (Exception e)
					{
						e.printStackTrace();
						out.print("exception");
						RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memRegister.jsp");
						failureView.forward(req, res);
					}
			}

		if ("checkAccount".equals(action))
			{
				System.out.println("checkingAccount");
				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				try
					{
						System.out.println(req.getParameter("usrid"));
						String usrid = req.getParameter("usrid").trim();
						if (usrid == null || usrid.trim().length() == 0)
							{
								errorMsgs.add("使用者名稱請勿空白");

							}

						/*************************** 2.開始搜尋資料,比對帳號是否重覆 ********************************/

						memSvc = new MemService();

						Set<String> usridSet = memSvc.getAllUsrId();

						System.out.println("usrid:" + usrid);
						if (usridSet.contains(usrid))
							{

								out.print(true);

							} else
							{

								out.print(false);
							}
						/*************************** 其他可能的錯誤處理 **********************************/
					} catch (Exception e)
					{
						errorMsgs.add(e.getMessage());
					}

			}

		if ("getAllMembers".equals(action))
			{

				memSvc = new MemService();
				List<MemVO> list = memSvc.getAll();

				JSONArray array = new JSONArray(list);
				out.print(array);

			}

		if ("getOneByIdNameAccout".equals(action))
			{

				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				try
					{
						/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

						String input = req.getParameter("input");

						System.out.println("input:" + input);
						String inputReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{2,20}$";
						if (input == null || input.trim().length() == 0)
							{
								errorMsgs.add("請輸入欲搜尋之會員帳號、名稱或編號");
							} else if (!input.matches(inputReg))
							{
								errorMsgs.add("輸入格式錯誤, 請重新輸入");
							}
						System.out.println("通過搜尋驗證");

						/*********************** 2.開始搜尋資料 *************************/

						memSvc = new MemService();
						List<MemVO> list = memSvc.findByPKNameAcc(input);

						for (MemVO memVO : list)
							{
								System.out.println("帳號:" + memVO.getM_accno());
								System.out.println("編號:" + memVO.getMem_id());
								System.out.println("名稱:" + memVO.getM_name());
							}

						JSONArray array = new JSONArray(list);

						out.print(array);

					} catch (Exception e)
					{
						errorMsgs.add(e.getMessage());
						System.out.println(e.getMessage());
					}

			}

		if ("getContactZip".equals(action))
			{

				HttpSession session = req.getSession();
				MemVO memVO = (MemVO) session.getAttribute("memVO");

				System.out.println("getContactZip");

				out.print(new JSONObject(memVO));

			}

	}
}
