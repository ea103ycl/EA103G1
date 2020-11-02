<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.reg_inf.model.*"%>
<%@ page import="com.meeting.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.wel_record.model.*"%>
<%@ page import="java.util.*"%>

<%
   Reg_infVO reg_infVO = (Reg_infVO) request.getAttribute("reg_infVO");

	WelRecordVO welRecordVO = (WelRecordVO) request.getAttribute("welRecordVO");

	MemVO memVO = (MemVO) session.getAttribute("memVO");
	

%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="UTF-8"/>
<script type="text/javascript" src="css/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">


<script type="text/javascript">


function checkoutAmount()
{
/*計算總價格*/

var max_num=document.getElementById("max_num").value;
var mt_num=document.getElementById("mt_num").value;
var ri_qty=document.getElementById("ri_qty").value;
var ri_fee=document.getElementById("ri_fee").value;
document.getElementById("amount").innerHTML=ri_qty*ri_fee;
document.getElementById("amount2").value=ri_qty*ri_fee;
document.getElementById("amount3").value=max_num-mt_num;
document.getElementById("amount4").innerHTML=max_num-mt_num;

}
</script>


<style type="text/css">
#imgtest {
    position: absolute;
    top: 100px;
    left: 400px;
    z-index: 1;
}

table {
    left: 100px;
    font-size: 20px;
}
</style>
    </head>
    <body onload="initialize()">
        <div id="imgtest"></div>
</head>

<body>

<table id="table-1">
	<tr><td>
		 <h3>報名見面會</h3></td><td>
		 <h4><a href="/EA103G1/frontend/meeting/listAllMeeting_front.jsp">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料新增:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<!-- enctype="multipart/form-data" -->
<FORM METHOD="get" ACTION="<%=request.getContextPath()%>/frontend/reg_inf/reg_inf.do" name="form2"  >
<table>
	
<tr>
		<td>見面會編號:</td>
		<td>
	     <input type="hidden" name="mt_no" value="<%=request.getParameter("mt_no")%>">
	         ${meetingVO.mt_no}
	         
	         <td>
	</tr>
	
	<tr>
		<td>會員ID:</td>
		<td>
	     <input type="hidden" name="mem_id" value="${sessionScope.memVO.mem_id}">
	     <c:if test="${not empty sessionScope.memVO.mem_id}">
					 ${sessionScope.memVO.mem_id}
							
		 </c:if>
		 </td>
	</tr>

	<tr>
		<td>報名人數:</td>
		<td><input type="TEXT" name="ri_qty" size="45" id="ri_qty" onblur="checkoutAmount();"
			 value="<%= (reg_infVO==null)? "" : reg_infVO.getRi_qty()%>" /></td>

			
	</tr>
	

	<tr>
	<td>報名費:</td>
	<td>${meetingVO.ri_fee}</td>
	
	</tr>
	
	
    <tr>
		<td>備註:</td>
		<td><input type="TEXT" name="ri_note" size="45"
			 value="<%= (reg_infVO==null)? "" : reg_infVO.getRi_note()%>" /></td>
	</tr>
	<tr>
              <td>總計:<span id="amount">元</td>
              </tr>
              <td>剩餘名額<span id="amount4"></td>
                       
</table>

<br>
<input type="hidden" name="mt_no" value="${meetingVO.mt_no}">
<input type="hidden" name="ri_fee" id="ri_fee" value="${meetingVO.ri_fee}" />
<input type="hidden" name="max_num" id="max_num" value="${meetingVO.max_num}" />
<input type="hidden" name="mt_num" id="mt_num" value="${meetingVO.mt_num}">
<input type="hidden" name="totalAmount" id="amount2" >
<input type="hidden" name="check_num" id="amount3" >
<input type="hidden" name="action" value="checkout_reg">
<input type="submit" value="送出新增">
</FORM>
</body>
</html>