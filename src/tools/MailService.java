package tools;

import java.util.Properties;

//import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import com.mem.model.MemVO;

public class MailService {

	// 設定傳送郵件:至收信人的Email信箱,Email主旨,欲傳送圖片之路徑,請求參數及收件人VO
	public static void sendMail(String to, String subject, String photoPath, HttpServletRequest req, MemVO memVO) {

		// -----------------------------------------寄Email圖片版-----------------------------------------------

		try {
			// Email內的重設密碼button之超連結
			StringBuffer url = req.getRequestURL();

			String reqStr = "?action=URLfromEmail&memid=";

			String accno = memVO.getM_accno();
			String mem_id = memVO.getMem_id();

			url.append(reqStr);
			url.append(mem_id);

			System.out.println(url);

			// 設定使用SSL連線至 Gmail smtp Server
			Properties props = new Properties();

			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			// ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)

			// ●須將myGmail的【安全性較低的應用程式存取權】打開

			final String myGmail = "ea103g1@gmail.com"; // 寄件人帳號

			final String myGmail_password = "!1qaz2wsx"; // 寄件人密碼

			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(myGmail, myGmail_password);
				}
			});

			Transport transport = session.getTransport();

			Message message = new MimeMessage(session);

			// 設定寄件人
			message.setFrom(new InternetAddress(myGmail));

			// 設定收件人
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// 設定信中的主旨
			message.setSubject(subject);

			// 設定信中的內容

			// 文字部份，注意 img src 部份要用 cid:接下面附檔的header
			MimeBodyPart textPart = new MimeBodyPart();
			StringBuffer html = new StringBuffer();

			html.append(
					"<div style=\"text-align:center\"><img style=\"width=100px;margin:0px auto\" src='cid:image'/><br>");
			html.append("<h2>Hi&ensp;" + accno + "</h2><br>");
			html.append("<h2>請點選以下按鈕重設您的密碼</h2><br>");
			// 導向重設密碼之按鈕
			html.append("<div><!--[if mso]>\r\n"
					+ "  <v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"\" style=\"height:40px;v-text-anchor:middle;width:200px;\" arcsize=\"10%\" strokecolor=\"#ffffff\" fillcolor=\"#a26d53\">\r\n"
					+ "    <w:anchorlock/>\r\n"
					+ "    <center style=\"color:#ffffff;font-family:sans-serif;font-size:13px;font-weight:bold;\">Show me the button!</center>\r\n"
					+ "  </v:roundrect>\r\n" + "<![endif]--><a href=\"" + url + "\"\r\n"
					+ "style=\"background-color:#C39D6D;border:1px solid #ffffff;border-radius:4px;color:#ffffff;display:inline-block;font-family:sans-serif;font-size:13px;font-weight:bold;line-height:40px;text-align:center;text-decoration:none;width:200px;-webkit-text-size-adjust:none;mso-hide:all;\">重設密碼</a></div></div>");

			textPart.setContent(html.toString(), "text/html; charset=UTF-8");

			// 圖檔部份，注意 html 用 cid:image，則header要設<image>
			MimeBodyPart picturePart = new MimeBodyPart();

//				System.out.println(photoPath);
			FileDataSource fds = new FileDataSource(photoPath);
			picturePart.setDataHandler(new DataHandler(fds));
			picturePart.setFileName(fds.getName());
			picturePart.setHeader("Content-ID", "<image>");

			Multipart email = new MimeMultipart();
			email.addBodyPart(textPart);
			email.addBodyPart(picturePart);

			message.setContent(email);

			transport.connect();
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();

			System.out.println("傳送成功!");
		} catch (MessagingException e) {
			System.out.println("傳送失敗!");
			e.printStackTrace();
		}

	}

	// 測試用

//	public static void main(String args[]) {
//
//		String path = "C:\\Users\\Big data\\Desktop\\15897945014338.jpg";
//
//		String to = "caroline20126862@gmail.com";
//
//		String subject = "密碼通知";
//
//		String ch_name = "peter1";
//		String passRandom = "111";
//		String messageText = "Hello! " + ch_name + " 請謹記此密碼: " + passRandom + "\n" + " (已經啟用)";
//
//		MailService.sendMail(to, subject, messageText, path);
//
//	}

}
//-----------------------------------------寄Email文字版-----------------------------------------------
//import java.util.Properties;
//
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//public class MailService
//{
//
//	// 設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容
//	public void sendMail(String to, String subject, String messageText)
//	{
//
//		try
//			{
//				// 設定使用SSL連線至 Gmail smtp Server
//				Properties props = new Properties();
//				props.put("mail.smtp.host", "smtp.gmail.com");
//				props.put("mail.smtp.socketFactory.port", "465");
//				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//				props.put("mail.smtp.auth", "true");
//				props.put("mail.smtp.port", "465");
//
//				// ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
//				// ●須將myGmail的【安全性較低的應用程式存取權】打開
//				final String myGmail = "caroline20126862@gmail.com";
//				final String myGmail_password = "caroline159357";
//				Session session = Session.getInstance(props, new Authenticator()
//				{
//					protected PasswordAuthentication getPasswordAuthentication()
//					{
//						return new PasswordAuthentication(myGmail, myGmail_password);
//					}
//				});
//
//				Message message = new MimeMessage(session);
//				message.setFrom(new InternetAddress(myGmail));
//				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//
//				// 設定信中的主旨
//				message.setSubject(subject);
//				// 設定信中的內容
//				message.setText(messageText);
//
//				Transport.send(message);
//				System.out.println("傳送成功!");
//			} catch (MessagingException e)
//			{
//				System.out.println("傳送失敗!");
//				e.printStackTrace();
//			}
//	}
//
//	public static void main(String args[])
//	{
//
//		String to = "ea103g1@gmail.com";
//
//		String subject = "密碼通知";
//
//		String ch_name = "peter1";
//		String passRandom = "111";
//		String messageText = "Hello! " + ch_name + " 請謹記此密碼: " + passRandom + "\n" + " (已經啟用)";
//
//		MailService mailService = new MailService();
//		mailService.sendMail(to, subject, messageText);
//
//	}
//
//}
