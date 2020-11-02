<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.reg_inf.model.*"%>
<%@ page import="com.meeting.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.wel_record.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
    Reg_infVO reg_infVO = (Reg_infVO) request.getAttribute("reg_infVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>
<%
	WelRecordVO welRecordVO = (WelRecordVO) request.getAttribute("welRecordVO");

	MemVO memVO = (MemVO) session.getAttribute("memVO");

%>

<html>
<head>
<title>報名資料 - listOneReg_inf.jsp</title>

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
	width: 600px;
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

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-2">
	<tr><td>
		 <h3>見面會資料 - ListOneReg_inf.jsp</h3>
		 <h4><a href="/EA103G1/frontend/meeting/listAllMeeting_front.jsp">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
	    <th>狀態</th>
		<th>見面會ID</th>
		<th>會員ID</th>
		<th>人數上限</th>
		<th>人數下限</th>
		<th>地點</th>
	     
	</tr>
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
            已取消
    </c:when>
    <c:otherwise>
           報名成功
    </c:otherwise>
</c:choose>   
</td>	
			<td>${reg_infVO.ri_id}</td>
			<td>${reg_infVO.mt_no}</td>
			<td>${reg_infVO.mem_id}</td>
			<td>${reg_infVO.ri_qty}</td>
			<td>${reg_infVO.ri_note}</td>
			
			
	</tr>
	<c:if test="${reg_infVO.ri_status == 2}">			
	    <td><FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/reg_inf/reg_inf.do" style="margin-bottom: 0px;"> 
			     <input type="hidden" name="ri_id"  value="${reg_infVO.ri_id}">
			     <input type="hidden" name="ri_status"  value="2">
			     <input type="hidden" name="action"	value="cancel">
			     <input type="submit" value="取消報名">
	 </FORM></td></c:if>
	
	
	
	
</table>

</body>
</html>