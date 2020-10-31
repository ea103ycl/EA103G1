<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>IBM Emp: Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>IBM Emp: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for IBM Emp: Home</p>

<h3>¸ê®Æ¬d¸ß:</h3>
	


<ul>

  <li>
<form id="allPayAPIForm" action="https://payment-stage.ecPay.com.tw/Cashier/AioCheckOut/V5" method="post"><input type="hidden" name="NeedExtraPaidInfo" value="N"><input type="hidden" name="ReturnURL" value="http://211.23.128.214:5000"><input type="hidden" name="BidingCard" value=""><input type="hidden" name="CheckMacValue" value="C1387AC06FDBCD1F8AA127A85120CE3A3AE50266F3B903785725C04CCE7F2403"><input type="hidden" name="StoreExpireDate" value=""><input type="hidden" name="PeriodAmount" value=""><input type="hidden" name="PaymentInfoURL" value=""><input type="hidden" name="ClientRedirectURL" value=""><input type="hidden" name="StoreID" value=""><input type="hidden" name="EncryptType" value="1"><input type="hidden" name="IgnorePayment" value=""><input type="hidden" name="CreditInstallment" value=""><input type="hidden" name="PaymentType" value="aio"><input type="hidden" name="OrderResultURL" value=""><input type="hidden" name="PeriodReturnURL" value=""><input type="hidden" name="PlatformID" value=""><input type="hidden" name="MerchantMemberID" value=""><input type="hidden" name="Frequency" value=""><input type="hidden" name="ExpireDate" value=""><input type="hidden" name="ItemName" value="TestItem"><input type="hidden" name="InvoiceMark" value="N"><input type="hidden" name="ExecTimes" value=""><input type="hidden" name="ChoosePayment" value="ALL"><input type="hidden" name="MerchantID" value="2000132"><input type="hidden" name="TradeDesc" value="test Description"><input type="hidden" name="ClientBackURL" value=""><input type="hidden" name="PeriodType" value=""><input type="hidden" name="CustomField4" value=""><input type="hidden" name="Desc_4" value=""><input type="hidden" name="TotalAmount" value="50"><input type="hidden" name="CustomField3" value=""><input type="hidden" name="Desc_3" value=""><input type="hidden" name="CustomField2" value=""><input type="hidden" name="Desc_2" value=""><input type="hidden" name="MerchantTradeDate" value="2017/01/01 08:05:23"><input type="hidden" name="CustomField1" value=""><input type="hidden" name="Desc_1" value=""><input type="hidden" name="ChooseSubPayment" value=""><input type="hidden" name="UnionPay" value=""><input type="hidden" name="InstallmentAmount" value=""><input type="hidden" name="MerchantTradeNo" value="testCompany999999999"><input type="hidden" name="ItemURL" value=""><input type="hidden" name="Remark" value=""><input type="hidden" name="DeviceSource" value=""><input type="hidden" name="Language" value=""><input type="hidden" name="Redeem" value=""><script language="JavaScript">allPayAPIForm.submit()</script></form>


  </li>
  

</ul>


</body>
</html>