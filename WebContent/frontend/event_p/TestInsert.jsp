<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.event_p.model.*" %>
<%@ page import="com.event.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Base64.*" %>

<%
  Event_PVO event_pVO = (Event_PVO) request.getAttribute("event_pVO");
  pageContext.setAttribute("event_pVO", event_pVO);  
  HttpSession sess=request.getSession();
 	
 
  Event_PService eventPSvc=new Event_PService();
  EventService 	eventSvc=new EventService();
  EventVO eventVO=eventSvc.findByPrimaryKey((String)sess.getAttribute("event_no"));
  
%>
<c:if test="<%=sess.getAttribute(\"mem_id\")==null %>">
	<%sess.setAttribute("mem_id","M000003"); %>
</c:if>
<c:if test="<%=eventPSvc.checkUploadByMemid((String)sess.getAttribute(\"mem_id\"),(String)sess.getAttribute(\"event_no\")) %>">
	會員<%=sess.getAttribute("mem_id") %>以投稿過
	forward到更新資訊頁面
</c:if>

<c:if test="<%=event_pVO!=null %>">
	<%
	  byte[] imgBytes=event_pVO.getEvent_p_img();
	  final Base64.Encoder encoder = Base64.getEncoder();
	  String data=encoder.encodeToString(imgBytes);
	  request.setAttribute("data", data);
	%>	
</c:if>
  

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
<%-- 	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/backend/template/css/bootstrap.min.css"> --%>
<!-- 	<link rel="stylesheet" type="text/css" -->
<%-- 	href="<%=request.getContextPath()%>/frontend/template/css/bootstrap.min.css"> --%>
<title>投稿作品頁面</title>
</head>
<body>
<%@include file="/frontend/event_p/frontBarTop.jsp" %>
${event_pVO==null }
<c:if test="${not empty errMsgs }">
	<c:forEach var="err" items="${errMsgs}">
		<ul>
			<li>${err}</li>
		</ul>		
		
	</c:forEach>
</c:if>
<a href="event_spec.jsp">回到主題競賽</a>


	<div class="container">
	<form action="Event_pServlet" method="post" enctype="multipart/form-data">
		
<!-- 			 <div class="form-group row"> -->
<!--                 <label for="inputEmail3" class="col-sm-2 col-form-label">會員編號</label> -->
<!--                 <div class="col-sm-3"> -->
<!--                 	<select class="form-control" name="mem_id"> -->
<%--                 		<c:forEach var="memId" items="${memIds}"> --%>
<%--                 			<c:choose> --%>
<%-- 	                			<c:when test="${event_pVO.mem_id==memId}"> --%>
<%-- 	                				<option value="${memId}" selected>${memId}</option> --%>
<%-- 	                			</c:when> --%>
<%-- 	                			<c:otherwise>                		 --%>
<%-- 	                				<option value="${memId}" >${memId}</option> --%>
<%-- 	                			</c:otherwise> --%>
<%--                 			</c:choose> --%>
<%--                 		</c:forEach> --%>
<!--                 	</select> -->
                			
<!--                 </div> -->
<!--             </div> -->
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">會員編號</label>
                <div class="col-sm-3">
                	<input type="text" class="form-control" name="mem_id" value="<%=sess.getAttribute("mem_id")%>" readonly="readonly">
                </div>
            </div>
<!--             <div class="form-group row"> -->
<!--                 <label for="inputEmail3" class="col-sm-2 col-form-label">活動編號</label> -->
<!--                 <div class="col-sm-3"> -->
<!--                 	<select class="form-control" name="event_no"> -->
<%--                 		<c:forEach var="event_no" items="${eventNos}"> --%>
<%--                 			<c:choose> --%>
<%-- 	                			<c:when test="${event_pVO.event_no==event_no}"> --%>
<%-- 	                				<option value="${event_no}" selected>${event_no}</option> --%>
<%-- 	                			</c:when> --%>
<%-- 	                			<c:otherwise>                		 --%>
<%-- 	                				<option value="${event_no}" >${event_no}</option> --%>
<%-- 	                			</c:otherwise> --%>
<%--                 			</c:choose> --%>
<%--                 		</c:forEach> --%>
<!--                 	</select> -->
<!--                 </div> -->
<!--             </div> -->
			<div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">活動編號</label>
                <div class="col-sm-3">
                	<input type="text" class="form-control" name="event_no" value="<%=sess.getAttribute("event_no") %>" readonly="readonly">
                </div>
            </div>
             <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">投稿作品名稱</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control " name="event_p_name" >
                </div>
            </div>
            
             <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">投稿作品投票數</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="event_vote_num" value="0" readonly >
                </div>
            </div>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">投稿作品排行</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control " name="vote_rank" value="0" readonly >
                </div>
            </div>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">投稿作品狀態</label>
                <div class="col-sm-3">
                	<select class="form-control" name="event_p_stat">
                		<option value="1" <%=event_pVO==null?"":event_pVO.getEvent_p_stat()==1?"selected":"" %>>可投票</option>
                		<option value="2" <%=event_pVO==null?"":event_pVO.getEvent_p_stat()==2?"selected":"" %>>已被檢舉</option>
<%--                 		<option value="3" <%=event_pVO==null?"":event_pVO.getEvent_p_stat()==3?"selected":"" %> >不通過</option> --%>
                	</select>
<%--                     <input type="text" class="form-control " name="event_p_stat" value=<%=(event_pVO==null)?"":event_pVO.getEvent_p_stat().toString() %> > --%>
                </div>
            </div>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">上傳圖片(待修)</label>
                <div class="col-sm-3">
<%--                    <input type="text" class="form-control " name="event_vote_end" value=<%=(event_pVO==null)?"":event_pVO.getEvent_vote_end().toString() %> > --%>
						<input type="file" name="pic01" id="myFile" onchange="upload()">
						<img id="demo" <%=event_pVO==null?"":"src=data:image/jepg;base64,"+request.getAttribute("data") %>>
						
                </div>
            </div>
     
            
			<input type="hidden" name="imgForm" value="">
			<input type="hidden" name="action" value="insert">
			<input type="submit" value="送出" >
	</form>
	<img id="demo2">
	</div>
</body>




<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/backend/event/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/backend/event/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/backend/event/datetimepicker/jquery.datetimepicker.full.js"></script>
<script type="text/javascript">
// 	function init(){
// 	}
	var img2=document.getElementById("demo2");
	var myFile;
// 	var url="";
	function upload(){		
		myFile=document.getElementById("myFile");
		var img=document.getElementById("demo");
		var files =myFile.files;//找出上傳檔案
		var file=files[0];//取第一個來源
		var reader =new FileReader;
		reader.addEventListener('load',function(){			
			img.setAttribute('src',reader.result);			
		})

		reader.readAsDataURL(file);
// 		url=getObjectURL(file);	
		
		
	}
// 	function getObjectURL(file) {  
// 	     var url = null;  
// 	     if (window.createObjcectURL != undefined) {  
// 	         url = window.createOjcectURL(file);  
// 	     } else if (window.URL != undefined) {  
// 	         url = window.URL.createObjectURL(file);  
// 	     } else if (window.webkitURL != undefined) {  
// 	         url = window.webkitURL.createObjectURL(file);  
// 	     }  
// 	     return url;  
// 	 }

		

// 	window.onload=init;
</script>
</html>