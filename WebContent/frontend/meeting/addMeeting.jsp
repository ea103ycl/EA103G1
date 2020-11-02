<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.meeting.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.wel_record.model.*"%>
<%@ page import="java.util.*"%>

<%
	MeetingVO meetingVO = (MeetingVO) request.getAttribute("meetingVO");
%>
<%= meetingVO==null %>

<%
	WelRecordVO welRecordVO = (WelRecordVO) request.getAttribute("welRecordVO");

	MemVO memVO = (MemVO) session.getAttribute("memVO");

%>

<!DOCTYPE html>
<html>
<!-- page title -->
<title>舉辦見面會 - addAllMeeting.jsp</title>
<head>
<meta charset="UTF-8">
<style>
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

<style>
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

</style>


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
                    <h1>AddMeeting</h1>
                </div><!-- / banner-info -->
            </div><!-- / banner-border -->
        </div><!-- / banner-content -->
    </div>
    <!-- / header-banner -->
	</header>
	<!-- / header -->
<table id="table-1">
	<tr><td>
		 <h3>舉辦見面會 - addMeeting.jsp</h3>
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
		<td><input type="TEXT" name="max_num" size="3"
			 value="<%= (meetingVO==null)? "" : meetingVO.getMax_num()%>" />人</td>
	</tr>

	<tr>
		<td>人數下限:</td>
		<td><input type="TEXT" name="min_num" size="3"
			 value="<%= (meetingVO==null)? "" : meetingVO.getMin_num()%>" />人</td>
	</tr>
	
    <tr>
		<td>地點:</td>
		<td><input type="TEXT" name="mt_place" size="30"
			 value="<%= (meetingVO==null)? "" : meetingVO.getMt_place()%>" /></td>
	</tr>
	
	<tr>
		<td>報名費:</td>
		<td><input type="TEXT" name="ri_fee" size="3"
			 value="<%= (meetingVO==null)? "" : meetingVO.getRi_fee()%>" />元</td>
	</tr>
	
	<tr>
		<td>活動簡介:</td>
		<td><textarea name="mt_detail"  size="30"
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
<div style="text-align:center;"><input type="submit" value="送出新增"></div></FORM>

<!-- footer -->

	<%@include file="/frontend/bar/frontBarFooter.jsp"%>

	<!-- / footer -->
</body>

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


</html>