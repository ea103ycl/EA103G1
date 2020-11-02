<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.meeting.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.wel_record.model.*"%>
<%@ page import=" java.text.*,  java.util.*"  %>


<%-- 此頁練習採用 EL 的寫法取值 --%>

<%

	MeetingService meetingSvc = new MeetingService();
    List<MeetingVO> list = meetingSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 Date current = new Date();
%>


<html>
<head>
<title>見面會_後台 - listAllMeeting_back.jsp</title>

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
	width: 1600px;
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


<table id="table-1">
	<tr><td>
		 <h3>見面會_後台 - listAllMeeting_back.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/backend/meeting/listAllMeeting_back.jsp">回首頁</a></h4>
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
	    <th>活動名稱</th>
	    <th>舉辦人ID</th>
	    <th>日期</th>
		
		<th>報名期間</th>
       
       <th>人數</th>
       <th>查詢</th>
      
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="meetingVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
		
		  
		<td>	    
<%-- 以下測試時間 --%>
<fmt:formatDate value="<%=current %>" pattern="yyyy-MM-dd" var="now_time" />
<fmt:formatDate value="${meetingVO.mt_start_time}" pattern="yyyy-MM-dd" var="mt_start_time" />
<fmt:formatDate value="${meetingVO.mt_time}" pattern="yyyy-MM-dd" var="mt_time" />
<fmt:formatDate value="${meetingVO.mt_end_time}" pattern="yyyy-MM-dd" var="mt_end_time" />
<c:set var="mt_status" scope="session" value="${meetingVO.mt_status}"/>


<c:choose> 
  <c:when test="${mt_status == 2}">
           <font color=red> 活動已取消</font>
    </c:when>
    <c:when test="${now_time gt mt_time}">
             活動已結束
    </c:when>
   <c:when test="${now_time gt mt_end_time}">
         報名截止
    </c:when>
    <c:when test="${mt_start_time gt now_time}">
              即將開放
    </c:when>
    <c:otherwise>
          開放報名
    </c:otherwise>
</c:choose>   
</td>		
<td>${meetingVO.mt_id}</td> 
		    <td>${meetingVO.mem_id}</td>	    
	    		         
		    <td><fmt:formatDate value="${meetingVO.mt_time}" pattern="yyyy-MM-dd" /></td> 
		     
		    <td><fmt:formatDate value="${meetingVO.mt_start_time}" pattern="yyyy-MM-dd" /> ~ <fmt:formatDate value="${meetingVO.mt_end_time}" pattern="yyyy-MM-dd" /></td>		 

		    <td>${meetingVO.mt_num}</td> 
		
<td>
         <form action="<%=request.getContextPath()%>/frontend/reg_inf/reg_inf.do" " method="get"><!-- action值填你的api url -->
		 <input type="hidden" name="mt_no" value="${meetingVO.mt_no}" />
		 <input type="submit" value="查詢" />
		 <input type="hidden" name="action" value="getReg_inf_mt_no">
		  </form>
		 </td>
		</tr>
	</c:forEach>
</table>


<ul>
  <li><a href='<%=request.getContextPath()%>/backend/meeting/cancel_meeting_search.jsp'>「取消的」</a> 見面會</li>
</ul>



  


<%@ include file="page2.file" %>

</body>
</html>