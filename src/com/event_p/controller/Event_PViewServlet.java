package com.event_p.controller;

import java.io.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.event_p.model.*;

@MultipartConfig
@WebServlet("/Event_PViewServlet")
public class Event_PViewServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/jepg");
		
		ServletOutputStream out=res.getOutputStream();
		String event_p_no= req.getParameter("event_p_no");//從網頁取得event_p_no
		byte[] img=null;
//		if(event_p_no==null||event_p_no.trim().length()==0) {
//			//從本機取出圖片，null通常是新的活動競賽(暫時取logo2)
//			
//			String url= getServletContext().getResource("/frontend/template/images/logo2.png").toString();
//			System.out.println("logo2.png位置:"+url);
////			getServletContext().getResource("/frontend/template/images/logo2.png").toString();
//			File f=new File(url);
//			InputStream is= getServletContext().getResource("/frontend/template/images/logo2.png").openStream();
////			BufferedInputStream bis=new BufferedInputStream(fis);
//			ByteArrayOutputStream baos=new ByteArrayOutputStream();
//			int i=0;
//			byte[] b=new byte[8192];
//			System.out.println(is.toString());
//			if((is.read(b))!=-1) {
//				System.out.println("read");
//				baos.write(b);
//				
//			}
//			img=baos.toByteArray();
//			baos.flush();
//			baos.close();
//			is.close();
//			System.out.println("img.length(null): "+img.length);
//		}else {
			Event_PDAO dao=new Event_PDAO();//dao呼叫findByPrimaryKey
			Event_PVO event_pVO=dao.findByPrimaryKey(new Integer(event_p_no));
			
			img=event_pVO.getEvent_p_img(); //取得byte[]	
//			System.out.println("event_p_no: "+event_p_no);
//			System.out.println("img.length: "+img.length);
//		}
//		read
//		img.length(null): 8192
//		event_p_no: 3001
//		event_p_no: 3003
//		img.length: 0
//		img.length: 0
	
		
		out.write(img);
		out.close();
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
