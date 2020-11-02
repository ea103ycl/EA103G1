<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.meeting.model.*"%>

<%
	MeetingVO meetingVO = (MeetingVO) request.getAttribute("meetingVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>員工資料修改 - update_meeting_input.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
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

<style>
  table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>見面會資料修改 - update_meeting_input.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/frontend/meeting/listAllMeeting_front.jsp">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料修改:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM action="<%=request.getContextPath()%>/frontend/meeting/meeting.do"  method="post" enctype="multipart/form-data">
<table>
    <tr>
		<td>見面會編號:<font color=red><b>*</b></font></td>
		<td><%=meetingVO.getMt_no()%></td>
		
	</tr>


	<tr>
		<td>會員ID:<font color=red><b>*</b></font></td>	
		<td><%=meetingVO.getMem_id()%></td>	
		</tr>
	
	<tr>
		<td>人數上限:<font color=red><b>*</b></font></td>
		<td><%=meetingVO.getMax_num()%></td>
			
	</tr>

	<tr>
		<td>人數下限:<font color=red><b>*</b></font></td>		
	    <td><%=meetingVO.getMin_num()%></td>
	    
	</tr>
	
    <tr>
		<td>地點:<font color=red><b>*</b></font></td>	
		<td><input type="TEXT" name="mt_place" size="45" value="<%=meetingVO.getMt_place()%>" /></td>	 
	</tr>
	
	<tr>
		<td>報名費:<font color=red><b>*</b></font></td>
		<td><%=meetingVO.getRi_fee()%></td>	
		
	</tr>
	
	<tr>
		<td>活動簡介:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="mt_detail" size="45" value="<%=meetingVO.getMt_detail()%>" /></td>	 
	</tr>
	
	<tr>
		<td>報名開始時間:<font color=red><b>*</b></font></td>
		<td><%=meetingVO.getMt_start_time()%></td>	 
	    
		
	</tr>
	
	<tr>
		<td>報名截止時間:<font color=red><b>*</b></font></td>
		<td><%=meetingVO.getMt_end_time()%></td>
	   	
	</tr>
	
	<tr>
		<td>活動時間:<font color=red><b>*</b></font></td>
		<td><%=meetingVO.getMt_time()%></td>
					
		 
	</tr>
	
	<tr>
		<td>活動名稱:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="mt_id" value="<%=meetingVO.getMt_id()%>" /></td>	 
	</tr>
	



</table>
<br>

<input type="hidden" name="action" value="update">
<input type="hidden" name="mt_no" value="<%=meetingVO.getMt_no()%>">
<input type="hidden" name="mem_id" value="<%=meetingVO.getMem_id()%>">
<input type="hidden" name="max_num" value="<%=meetingVO.getMax_num()%>">
<input type="hidden" name="min_num" value="<%=meetingVO.getMin_num()%>">	
<input type="hidden" name="ri_fee" value="<%=meetingVO.getRi_fee()%>"> 
<input type="hidden" name="mt_start_time" value="<%=meetingVO.getMt_start_time()%>">	
<input type="hidden" name="mt_end_time" value="<%=meetingVO.getMt_end_time()%>">
<input type="hidden" name="mt_time" value="<%=meetingVO.getMt_time()%>">
<input type="hidden" name="mt_pic" value="<%=meetingVO.getMt_pic()%>">

<input type="submit" value="送出修改"></FORM>
</body>

</html>