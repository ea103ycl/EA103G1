<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import=" java.text.*,  java.util.*"  %>
<%@ page import="com.reg_inf.model.*"%>
<%@ page import="com.meeting.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.wel_record.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>
<%
    MemVO memVO = (MemVO) session.getAttribute("memVO");
    
    Reg_infService reg_infSvc = new Reg_infService();
    List<Reg_infVO> list = reg_infSvc.getMem_Reg_inf(memVO.getMem_id());
    pageContext.setAttribute("list",list);
    
    MeetingService meetingSvc = new MeetingService();
    MeetingVO meetingVO = meetingSvc.getOneMeeting("mt_no");
    pageContext.setAttribute("meetingSvc",meetingSvc);
 
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
<title>「我報名的」見面會-listAllReg_inf.jsp</title>
	
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
	        	<button type="button"  onclick="location.href='<%=request.getContextPath()%>/frontend/meeting/listAllMeeting_front.jsp'" class="btn btn-primary-filled">見面會-首頁</button>	        	        	                
		        <button type="button" onclick="location.href='<%=request.getContextPath()%>/frontend/meeting/addMeeting.jsp'" class="btn btn-primary-filled">舉辦見面會</button>
		        <button type="button" onclick="location.href='<%=request.getContextPath()%>/frontend/meeting/search_mem_meeting.jsp'" class="btn btn-primary-filled">我舉辦的見面會</button>
		        <button type="button" onclick="location.href='<%=request.getContextPath()%>/frontend/reg_inf/listAllReg_inf.jsp'" class="btn btn-lg btn-primary">我報名的見面會</button>
	        </div>
	       	
			<%-- 表格內容START --%>
			<%@ include file="page1.file" %> 

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
		<th>見面會名稱</th>
		<th>報名期間</th>
		<th>日期</th>
		<th>報名人數</th>
		<th>報名備註</th>
		<th>修改</th>	
		<th>取消</th>	
	</tr>
	
	<c:forEach var="reg_infVO" items="${list}"   begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">

		<tr>
		    <td>	    
<c:set var="ri_status" scope="session" value="${reg_infVO.ri_status}"/>

<c:choose>
    <c:when test="${ri_status == 1}">
            報名成功
    </c:when>
    <c:when test="${ri_status == 2}">
          已退費
    </c:when>
 
    <c:otherwise>
           報名成功
    </c:otherwise>
</c:choose>   
</td>		    
			<td>${reg_infVO.ri_id}</td>
			<td>${meetingSvc.getOneMeeting(reg_infVO.mt_no).mt_id}</td>
			<td><fmt:formatDate value="${meetingSvc.getOneMeeting(reg_infVO.mt_no).mt_start_time}" pattern="yyyy-MM-dd" />~<fmt:formatDate value="${meetingSvc.getOneMeeting(reg_infVO.mt_no).mt_end_time}" pattern="yyyy-MM-dd" /></td>
			<td><fmt:formatDate value="${meetingSvc.getOneMeeting(reg_infVO.mt_no).mt_time}" pattern="yyyy-MM-dd" /></td>
			<td>${reg_infVO.ri_qty}</td>
			<td>${reg_infVO.ri_note}</td>
	
	
	<td>	    
<%-- 以下測試時間 --%>
<fmt:formatDate value="<%=current %>" pattern="yyyy-MM-dd" var="now_time" />
<fmt:formatDate value="${meetingSvc.getOneMeeting(reg_infVO.mt_no).mt_start_time}" pattern="yyyy-MM-dd" var="mt_start_time" />
<fmt:formatDate value="${meetingSvc.getOneMeeting(reg_infVO.mt_no).mt_time}" pattern="yyyy-MM-dd" var="mt_time" />
<fmt:formatDate value="${meetingSvc.getOneMeeting(reg_infVO.mt_no).mt_end_time}" pattern="yyyy-MM-dd" var="mt_end_time" />
<c:set var="ri_status" scope="session" value="${reg_infVO.ri_status}"/>


<c:choose> 
  <c:when test="${ri_status == 2}">
              已退款
    </c:when>
    <c:when test="${now_time gt mt_time}">
             活動已結束
    </c:when>
     <c:when test="${now_time gt mt_end_time}">
    <font color=red>報名截止</font>
    </c:when>
    <c:when test="${mt_start_time gt now_time}">
           <font color=blue>   即將開放</font>
    </c:when>
    <c:otherwise>
        <FORM METHOD="get" ACTION="<%=request.getContextPath()%>/frontend/reg_inf/reg_inf.do" style="margin-bottom: 0px;">
	 	 <input type="hidden" name="ri_id"  value="${reg_infVO.ri_id}">
	 <input class="btn btn-primary btn-pill" type="submit" value="修改">
	<input type="hidden" name="action"	value="getOne_For_Update">
	
	</FORM>
    </c:otherwise>
</c:choose>   
</td>	 		      

<td>	    

<c:choose> 
  <c:when test="${ri_status == 2}">
            已退款
    </c:when>
    <c:when test="${now_time gt mt_time}">
             活動已結束
    </c:when>
     <c:when test="${now_time gt mt_end_time}">
    <font color=red>報名截止</font>
    </c:when>
    <c:when test="${mt_start_time gt now_time}">
     <font color=blue>即將開放</font>
    </c:when>
    <c:otherwise>
        <FORM METHOD="get" ACTION="<%=request.getContextPath()%>/frontend/reg_inf/reg_inf.do" style="margin-bottom: 0px;">
	 	 <input type="hidden" name="ri_id"  value="${reg_infVO.ri_id}">
	             <input class="btn btn-primary btn-pill" type="submit" value="取消"> 
	             <input type="hidden" name="action"	value="getOne_For_Display">
	</FORM>
    </c:otherwise>
</c:choose>   
</td>	 		   
		</tr>
	</c:forEach>

</table>
<%-- 表格內容END --%>

	<div class="row">
	    <div class="roger-mt">
	</div>
		<%@ include file="page2.file" %> 
	        
<!-------------------------------------------------------------------------------- 自定義內容END------------------------- -->

    </div><!-- / row -->
	</div> <!-- end container -->
	</section>

	<!-- footer -->
	<%@include file="/frontend/bar/frontBarFooter.jsp"%>

	<!-- / footer -->
</body>
</html>