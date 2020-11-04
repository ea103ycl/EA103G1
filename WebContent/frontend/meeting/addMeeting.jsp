<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.meeting.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.wel_record.model.*"%>
<%@ page import="java.util.*"%>
<%
	MeetingVO meetingVO = (MeetingVO) request.getAttribute("meetingVO");
%>

<%-- <%= meetingVO==null %> --%>

<%
	WelRecordVO welRecordVO = (WelRecordVO) request.getAttribute("welRecordVO");
	MemVO memVO = (MemVO) session.getAttribute("memVO");
%>
<!DOCTYPE html>
<html>
<head>

	<!-- top bar -->
	<%@include file="/frontend/bar/frontBarTop.jsp"%>
	
	<!-- page title -->
	<title>舉辦見面會</title>
	
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
		        <button type="button" onclick="location.href='<%=request.getContextPath()%>/frontend/meeting/addMeeting.jsp'" class="btn btn-lg btn-primary">舉辦見面會</button>
		        <button type="button" onclick="location.href='<%=request.getContextPath()%>/frontend/meeting/search_mem_meeting.jsp'" class="btn btn-primary-filled">我舉辦的見面會</button>
		        <button type="button" onclick="location.href='<%=request.getContextPath()%>/frontend/reg_inf/listAllReg_inf.jsp'" class="btn btn-primary-filled">我報名的見面會</button>
	        </div>
	        
	        <%-- 表格內容START --%>

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

<FORM METHOD="post" ACTION="meeting.do" name="form1" enctype="multipart/form-data">
<table>

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
		<td>活動名稱:</td>
		<td><input type="TEXT" name="mt_id" size="30"
			 value="<%= (meetingVO==null)? "" : meetingVO.getMt_id()%>" /></td>
	</tr>

	<tr>
		<td>人數上限:</td>
		<td><input type="TEXT" name="max_num" size="3" oninput = "value=value.replace(/[^\d]/g,'')"
			 value="<%= (meetingVO==null)? "" : meetingVO.getMax_num()%>" />人</td>
	</tr>

	<tr>
		<td>人數下限:</td>
		<td><input type="TEXT" name="min_num" size="3" oninput = "value=value.replace(/[^\d]/g,'')"
			 value="<%= (meetingVO==null)? "" : meetingVO.getMin_num()%>" />人</td>
	</tr>
	
    <tr>
		<td>地點:</td>
		<td><input type="TEXT" name="mt_place" size="30"
			 value="<%= (meetingVO==null)? "" : meetingVO.getMt_place()%>" /></td>
	</tr>
	
	<tr>
		<td>報名費:</td>
		<td><input type="TEXT" name="ri_fee" size="3" oninput = "value=value.replace(/[^\d]/g,'')"
			 value="<%= (meetingVO==null)? "" : meetingVO.getRi_fee()%>" />元</td>
	</tr>
	
	<tr>
		<td>活動簡介:</td>
		<td><textarea name="mt_detail" rows="5" cols="20"
			 value="<%= (meetingVO==null)? "" : meetingVO.getMt_detail()%>" /></textarea></td>
	</tr>
	
	<tr>
		<td>報名開始時間:</td>
		<td><input type="text" name="mt_start_time" id="start_date" onfocus="this.blur()" size="30"
			 value="<%= (meetingVO==null)? "" : meetingVO.getMt_start_time()%>" /></td>
	</tr>
	
	<tr>
		<td>報名截止時間:</td>
		<td><input type="TEXT" name="mt_end_time" id="end_date" onfocus="this.blur()"size="30"
			 value="<%= (meetingVO==null)? "" : meetingVO.getMt_end_time()%>" /></td>
	</tr>
	
	<tr>
		<td>活動時間:</td>
		<td><input type="TEXT" name="mt_time" id="mt_date" onfocus="this.blur()"size="30"
			 value="<%= (meetingVO==null)? "" : meetingVO.getMt_time()%>" /></td>
	</tr>
	
	
	

    <tr>
		<td>圖片:</td>
		<td><input type="file" accept=".png, .jpg, .jpeg" name="mt_pic" size="45"size="30"
			 value="<%= (meetingVO==null)? "0" : meetingVO.getMt_pic()%>" /></td>
	</tr>
	

</table>

<input type="hidden" name="action" value="insert">
<div style="text-align:center;"><input class="btn btn-primary-filled btn-pill" type="submit" value="送出新增"></div></FORM>

<%-- 表格內容END --%>
		<!-------------------------------------------------------------------------------- 自定義內容END------------------------- -->
	    </div><!-- / row -->
	</div> <!-- end container -->
	</section>

	<!-- footer -->
	<%@include file="/frontend/bar/frontBarFooter.jsp"%>


<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<link   rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>
 <script>

$.datetimepicker.setLocale('zh'); // kr ko ja en
$(function(){
	 $('#start_date').datetimepicker({
	  format:'Y-m-d H:i:s',
	  step: 1,
	  
	  onShow:function(){
	   this.setOptions({
	    maxDate:$('#end_date').val()?$('#end_date').val():false
	   })
	  },
	  timepicker:true
	 });
	 
	 $('#end_date').datetimepicker({
	  format:'Y-m-d H:i:s',
	  step: 1,
	  onShow:function(){
	   this.setOptions({
	    minDate:$('#start_date').val()?$('#start_date').val():false
	   })
	  },
	  timepicker:true
	 });
	 
	 $('#mt_date').datetimepicker({
		  format:'Y-m-d H:i:s',
		  step: 1,
		  onShow:function(){
		   this.setOptions({
		    minDate:$('#end_date').val()?$('#end_date').val():false
		   })
		  },
		  timepicker:true
		 });
});
</script>

</body>
</html>