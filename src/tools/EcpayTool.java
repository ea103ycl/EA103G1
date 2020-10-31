package tools;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

public class EcpayTool
{
	public static AllInOne all;

	public static int orderId4Ecpay = 0;

	private static void initial()
	{
		all = new AllInOne("");
	}

	public static String genAioCheckOutALL(int amount, String orderNo, HttpServletRequest req)
	{

		String amountStr = Integer.toString(amount);

		String returnUrl = req.getContextPath() + "/frontend/members/memArea.jsp";

		AioCheckOutALL obj = new AioCheckOutALL();

		String date = DateTool.timestamp2String4Ecpay(new Timestamp(System.currentTimeMillis()));
		obj.setMerchantTradeNo(orderNo);
		obj.setMerchantTradeDate(date);
		obj.setTotalAmount(amountStr);
		obj.setTradeDesc("test Description");
		obj.setItemName("ArtsBlock-Àx­È");
		obj.setOrderResultURL(returnUrl);

		obj.setNeedExtraPaidInfo("N");
		String form = all.aioCheckOut(obj, null);
		return form;
	}

//	public static void main(String[] args)
//	{
//		// TODO Auto-generated method stub
//		initial();
//		System.out.println("aioCheckOutALL: " + genAioCheckOutALL(10000, "testartblock111"));
//	}

}
