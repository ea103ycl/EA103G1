<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import=" java.text.*,  java.util.*"  %>
<%@ page import="com.meeting.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.wel_record.model.*"%>

<%
	MeetingService meetingSvc = new MeetingService();
    List<MeetingVO> list = meetingSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<%
	WelRecordVO welRecordVO = (WelRecordVO) request.getAttribute("welRecordVO");

	MemVO memVO = (MemVO) session.getAttribute("memVO");
%>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 Date current = new Date();
%>

<!DOCTYPE html>
<html>
<head>
	<!-- top bar -->
	<%@include file="/frontend/bar/frontBarTop.jsp"%>
	<title>頁面名稱</title>
	
	<style>
		.roger-mt{
			margin-bottom:30px;
		}
	</style>
</head>

<body>

	<section class="space-top-30">
	<div id="page-content" class="container">
	
	    <div class="row">
	     <!-------------------------------------------------------------------------------- 自定義內容START------------------------- -->
	        <div class="roger-mt">
		        <button type="button" onclick="location.href='<%=request.getContextPath()%>/frontend/meeting/addMeeting.jsp'" class="btn btn-primary-filled">舉辦見面會</button>
		        <button type="button" onclick="location.href='<%=request.getContextPath()%>/frontend/meeting/search_mem_meeting.jsp'" class="btn btn-primary-filled">我舉辦的見面會</button>
		        <button type="button" onclick="location.href='<%=request.getContextPath()%>/frontend/reg_inf/listAllReg_inf.jsp'" class="btn btn-primary-filled">我報名的見面會</button>
	        </div>
	        
	        <%-- 表格內容START --%>
			<%@ include file="page1.file" %> 
	        
<!-- 	        <table id="table-1"> -->
<!-- 				<tr> -->
<!-- 					<td> -->
<%-- 						 <h4><a href='<%=request.getContextPath()%>/frontend/meeting/addMeeting.jsp'>「舉辦」</a> 見面會 --%>
<%-- 							 <a href='<%=request.getContextPath()%>/frontend/meeting/search_mem_meeting.jsp'>「我舉辦的」</a> 見面會 --%>
<%-- 				 			 <a href='<%=request.getContextPath()%>/frontend/reg_inf/listAllReg_inf.jsp'>「我報名的」</a> 見面會 --%>
<!-- 						</h4> -->
<!-- 					</td> -->
<!-- 			   </tr> -->
<!-- 			</table> -->
	          
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
				    <th>報名狀態</th>
				    <th>活動名稱</th>
				    <th>日期</th>
					<th>地點</th>
					<th>報名期間</th>
					<th>目前人數</th>
			        <th>查詢</th>
				</tr>
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
							    <font color=red>活動已取消</font>
							    </c:when>
							    <c:when test="${now_time gt mt_time}">
							     <font color=red> 活動已結束</font>
							    </c:when>
							     <c:when test="${now_time gt mt_end_time}">
							    <font color=red>報名截止</font>
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
					    <td><fmt:formatDate value="${meetingVO.mt_time}" pattern="yyyy-MM-dd" /></td> 
					    <td>${meetingVO.mt_place}</td>	    
					    <td><fmt:formatDate value="${meetingVO.mt_start_time}" pattern="yyyy-MM-dd" />~<fmt:formatDate value="${meetingVO.mt_end_time}" pattern="yyyy-MM-dd" /></td>		 
						<td>${meetingVO.mt_num}/${meetingVO.max_num}</td>    
						<td>
				           <form action="<%=request.getContextPath()%>/frontend/meeting/meeting.do" " method="get"><!-- action值填你的api url -->
								<input type="hidden" name="mt_no" value="${meetingVO.mt_no}" />
								<input class="btn btn-primary" type="submit" value="查詢" />
								<input type="hidden" name="action" value="getOne_For_Display">
						  </form>
						</td>		    
					</tr>
				</c:forEach>
				
			</table>
			<%-- 表格內容END --%>
		
		<!-------------------------------------------------------------------------------- 自定義內容END------------------------- -->
	    </div><!-- / row -->
	</div> <!-- end container -->
	</section>

	<!-- footer -->
	<%@include file="/frontend/bar/frontBarFooter.jsp"%>
	
	<script></script>

</body>
</html>