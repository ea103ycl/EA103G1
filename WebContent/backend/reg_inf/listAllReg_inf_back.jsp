<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.reg_inf.model.*"%>
<%@ page import="com.meeting.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.wel_record.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>
<%
    WelRecordVO welRecordVO = (WelRecordVO) request.getAttribute("welRecordVO");
MeetingVO meetingVO = (MeetingVO) request.getAttribute("meetingVO");


    Reg_infService reg_infSvc = new Reg_infService();
    List<Reg_infVO> list = reg_infSvc.getAll();
    pageContext.setAttribute("list",list);
    
   
%>


<html>
<head>
<title>全部報名表(會員) - listAllReg_inf.jsp</title>

<style>
  table#table-2 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-2 h4 {
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
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-2">
	<tr><td>
		 <h3>所有報名資料 - listAllReg_inf.jsp</h3>
		 <h4><a href="/EA103G1/frontend/meeting/listAllMeeting_front.jsp">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>狀態</th>
		<th>報名表ID</th>
		<th>見面會ID</th>
		
		<th>報名人數</th>
		<th>報名人數</th>
		<th>修改</th>
			
	</tr>
		
	<%@ include file="page1.file" %> 
	<c:forEach var="reg_infVO" items="${list}"   begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">

		<tr>
		    <td>	    
<c:set var="ri_status" scope="session" value="${reg_infVO.ri_status}"/>
<c:choose>
    <c:when test="${ri_status == 1}">
            報名成功
    </c:when>
    <c:when test="${ri_status == 2}">
            已取消，待退費
    </c:when>
    <c:when test="${ri_status == 3}">
           已退費
    </c:when>
    <c:otherwise>
           報名成功
    </c:otherwise>
</c:choose>   
</td>		    
			<td>${reg_infVO.ri_id}</td>
			<td>${reg_infVO.mt_no}</td>
			<td>${reg_infVO.mt_no}</td>
			<td>${reg_infVO.ri_qty}</td>
			<td>${reg_infVO.ri_note}</td>
	<td>
	 <FORM METHOD="get" ACTION="<%=request.getContextPath()%>/frontend/reg_inf/reg_inf.do" style="margin-bottom: 0px;">
	 	 <input type="hidden" name="ri_id"  value="${reg_infVO.ri_id}">
	 <input type="submit" value="查詢">
	<input type="hidden" name="action"	value="getOne_For_Display_back">
	
	</FORM>
    </td>
		

		
		
		
		
		
		
		
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>