package tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.sql.Clob;
import java.sql.SQLException;

public class YclTools {

	// ======================================================================
	// 將路徑裡的資料轉為InpuetSteam(JDBC的第三種範例)
	// 使用的是資料流，為了避免硬體方面管線上的限制，資料量大時用這種會比較好，將資料分流寫入DB而不是一次大量寫入(>=100mb時算資料量大)
	// ======================================================================
	public static InputStream getUploadFileStream(String path) throws IOException {
		System.out.println(path);
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		return fis;
	}

	// ======================================================================
	// 將路徑裡的資料轉為Byte陣列(JDBC的第二種範例)
	// 使用的是JDBC原生的方法，且byte[]從JAVA 1.0開始就有，不受各家DB廠商提供的driver影響
	// 比較適合用在資料量小時使用
	// ======================================================================
	public static byte[] getUploadFileByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file); // (L)低接管
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); // 此資料流會把write的位元資料存到一個內建的byte[]
		byte[] buffer = new byte[8192]; // 8kb
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
			baos.flush();
		}
		baos.close();
		fis.close();
		return baos.toByteArray(); // toByteArray()可以讓我們取得這個資料流內建的byte[]
	}

	// ======================================================================
	// 將取得的Part轉為byte陣列
	// ======================================================================
	public static byte[] transPartToByteArray(Part filePart) throws IOException {

		InputStream fileContent = filePart.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[fileContent.available()]; // 緩衝區大小8kb
		int i;
		while ((i = fileContent.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
			baos.flush();
		}
		baos.close();
		fileContent.close();

		return baos.toByteArray();

	}

	// ======================================================================
	// 判斷上傳檔是否為空
	// ======================================================================
	public static boolean isUploadFile(Part filePart) {
		String fileName = (filePart.getSubmittedFileName()).trim();
		if (fileName == null || fileName.length() == 0) {
			return false;
		} else {
			return true;
		}

	}

	// ========================================================================
	// 將db內讀出的byte[]轉為Stream並呈現於頁面
	// ========================================================================
	public static void readByteArrayFromDB(HttpServletRequest req, HttpServletResponse res, byte[] dbData)
			throws IOException {

		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dbData);
		BufferedInputStream in = new BufferedInputStream(byteArrayInputStream);
		byte[] buf = new byte[10 * 1024]; // 4K buffer
		int len;
		while ((len = in.read(buf)) != -1) {
			out.write(buf, 0, len);
		}
		in.close();
	}

	// ========================================================================
	// 將db內讀出的Clob轉為String，並加上textarea的換行符號&#13;&#10;
	// ========================================================================
	public static String readClobToString(Clob clob) throws SQLException, IOException {

		String result = "";

		if (clob != null) {

			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(clob.getCharacterStream());
			String str;

			while ((str = br.readLine()) != null) {
				sb.append(str);
				sb.append("&#13;&#10;"); //&#13;&#10;
//				sb.append("\n");
				// https://stackoverflow.com/questions/14534767/how-to-append-a-newline-to-stringbuilder
				// sb.append(System.getProperty("line.separator"));
			}

			br.close();
			result = sb.toString();
		}

		return result;

	}

	// ========================================================================
	// 將db內讀出的VARCHAR2轉為String，並將換行符號轉換為<br>
	// ========================================================================
	public static String readStringAppendBr(String text) {

		String result = "";
		if (text != null && text.length() != 0) {

			StringBuilder sb = new StringBuilder();
			String[] resultArr = text.split("\n");

			for (String str : resultArr) {
				sb.append(str);
				sb.append("<br>");
			}

			result = sb.toString();
		}

		return result;

	}

}