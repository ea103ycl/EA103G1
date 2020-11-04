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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>見面會_後台 - listAllMeeting_back.jsp</title>
		<!-- favicon -->
		<link rel="icon" href="<%=request.getContextPath()%>/backend/template/img/favicon.png">
</head>
<body id="page-top">
	
		<div id="wrapper">
	
			<%@include file="/backend/bar/backBarSide.jsp"%>
	
			<div id="content-wrapper" class="d-flex flex-column">		
	
				<div id="content">
	
					<!-- Topbar -->
					<%@include file="/backend/bar/backBarTop.jsp"%>
	
					<div class="container-fluid">				
						<!--=====自定義內容start ================================================== -->
			
<%-- 頁面標題 --%>
		 <h1 class="h3 mb-2 text-gray-800">見面會_後台</h1>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<div class="table table-hover">
<table class="table table-bordered text-center"
       id="dataTableNoSearchChange" width="100%" cellspacing="0">
	<thead>
	<tr class="bg-gray-400">
	    
	    <th>狀態</th>
	    <th>活動名稱</th>
	    <th>舉辦人ID</th>
	    <th>日期</th>
		<th>報名期間</th>
        <th>人數</th>
        <th>查詢</th>
	</tr>
	</thead>
	
	<tbody>
	<%@ include file="page1.file" %> 
  <h4 align="right">  <a href='<%=request.getContextPath()%>/backend/meeting/cancel_meeting_search.jsp'>「取消的」</a> 見面會</h4>
	
	<c:forEach var="meetingVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>	  
		<td class="align-middle">	    
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
            <td class="align-middle">${meetingVO.mt_id}</td> 
		    <td class="align-middle">${meetingVO.mem_id}</td>	    
	    		         
		    <td class="align-middle"><fmt:formatDate value="${meetingVO.mt_time}" pattern="yyyy-MM-dd" /></td> 
		     
		    <td class="align-middle"><fmt:formatDate value="${meetingVO.mt_start_time}" pattern="yyyy-MM-dd" /> ~ <fmt:formatDate value="${meetingVO.mt_end_time}" pattern="yyyy-MM-dd" /></td>		 

		    <td class="align-middle">${meetingVO.mt_num}</td> 
		
<td class="align-middle">
         <form action="<%=request.getContextPath()%>/frontend/reg_inf/reg_inf.do" " method="post"><!-- action值填你的api url -->
		 <input type="hidden" name="mt_no" value="${meetingVO.mt_no}" />
		 <input type="submit" value="查詢" />
		 <input type="hidden" name="action" value="getReg_inf_mt_no">
		  </form>
		 </td>
		</tr>
	</c:forEach>
</table>
<div style="text-align:center;">
<%@ include file="page2.file" %>
                          </div>
					</div>								
				</div>
			</div>

<!--===== 自定義內容end ================================================== -->
					</div> <!--END OF container-fluid-->
					
				</div> <!--END OF content-->
				
				<%@include file="/backend/bar/footer.jsp"%>
				
			</div><!--END OF content-wrapper -->




</body>
</html>