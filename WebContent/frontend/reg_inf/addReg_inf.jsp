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

table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
    text-align: center;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
  h4 {
  padding: 5px;
    text-align: center;
  }
  body {
    color: #666666;
    background: #fefefe;
    font-family: "Rubik", sans-serif;
    font-weight: 400;
    font-size: 14px;
    line-height: 20px;
    letter-spacing: 0.05em;
}
table#table-1 {
	background-color: #C4E1E1;
    border: 2px #ECF5FF;
    text-align: center;
    
  }
  table#table-1 h4 {
    color: #613030;
    display: block;
    margin-bottom: 10px;
  }
  
  h4 {
    color: blue;
    display: inline;
  }
  h3 {
  color: blue;
    display: inline;
  }
</style>
    </head>
    <body onload="checkoutAmount()">
</head>

<body>
<!-- header -->

	<header>

		<%@include file="/frontend/bar/frontBarTop.jsp"%>

		    <!-- header-banner -->
    <div id="header-banner">
        <div class="banner-content single-page text-center">
            <div class="banner-border">
                <div class="banner-info" id="banner-info">
                    <h1>Meeting</h1>
                </div><!-- / banner-info -->
            </div><!-- / banner-border -->
        </div><!-- / banner-content -->
    </div>
    <!-- / header-banner -->
	</header>
	<!-- / header -->
<table id="table-1">
	<tr><td>
		 <h3>報名見面會_addReg_inf.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/frontend/meeting/listAllMeeting_front.jsp">回首頁</a></h4>
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
              <td>總計金額:</td>
              <td><span id="amount"></td>
              
    </tr>
    <tr>
              <td>剩餘名額</td>
             <td><span id="amount4"></td>
   </tr>   
</table>

<br>
<input type="hidden" name="mt_no" value="${meetingVO.mt_no}">
<input type="hidden" name="ri_fee" id="ri_fee" value="${meetingVO.ri_fee}" />
<input type="hidden" name="max_num" id="max_num" value="${meetingVO.max_num}" />
<input type="hidden" name="mt_num" id="mt_num" value="${meetingVO.mt_num}">
<input type="hidden" name="totalAmount" id="amount2" >
<input type="hidden" name="check_num" id="amount3" >
<input type="hidden" name="action" value="checkout_reg">
<div style="text-align:center;"><input type="submit" value="送出新增"></div>
</FORM>

	<table id="table-1">
<td></td>
</table>
</body>
</html>