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
    Integer mt_status = 2; 

	MeetingService meetingSvc = new MeetingService();
    List<MeetingVO> list = meetingSvc.getCancel_Meeting();
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
	  <title>取消的見面會 -cancel_meeting_search.jsp</title>
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
						<h1 class="h3 mb-2 text-gray-800">取消的見面會</h1>
							  <h4><a href="<%=request.getContextPath()%>/backend/meeting/listAllMeeting_back.jsp">回首頁</a></h4>
						
                        <%-- 錯誤表列 --%>
						<c:if test="${not empty errorMsgs}">
							<font style="color: red">請修正以下錯誤:</font>
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color: red">${message}</li>
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
       <th>待退費人數</th>
       <th>查詢</th>
	</tr>
	</thead>
	
	<tbody>
	<%@ include file="page1.file" %> 
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
   <c:when test="${meetingVO.mt_num ==0}">
             退費完畢
    </c:when>

    <c:otherwise>
          <font color=red>活動已取消，待退費</font>
    </c:otherwise>
</c:choose>   
</td>	
	
<td>${meetingVO.mt_id}</td> 
		    <td>${meetingVO.mem_id}</td>	    
	    		         
		    <td class="align-middle"><fmt:formatDate value="${meetingVO.mt_time}" pattern="yyyy-MM-dd" /></td> 
		     
		    <td class="align-middle"><fmt:formatDate value="${meetingVO.mt_start_time}" pattern="yyyy-MM-dd" /> ~ <fmt:formatDate value="${meetingVO.mt_end_time}" pattern="yyyy-MM-dd" /></td>		 

		   <td class="align-middle">${meetingVO.mt_num}</td> 
		
           <td class="align-middle">
             <form action="<%=request.getContextPath()%>/frontend/reg_inf/reg_inf.do" " method="get"><!-- action值填你的api url -->
		     <input type="hidden" name="mt_no" value="${meetingVO.mt_no}" />
		     <input type="submit" value="查詢" />
		     <input type="hidden" name="action" value="getReg_inf_mt_no">
		     </form>
		  </td>
		</tr>
	   </c:forEach>
    </tbody>
</table>

<div style="text-align:center;">
<%@ include file="page2.file" %>
                         </div>
					</div>								
				</div>
			</div>
	       <!--===== 自定義內容end ================================================== -->
		</div> <!--END OF container-fluid-->
	</div>	
			
		<%@include file="/backend/bar/footer.jsp"%>
</div>
</body>
</html>