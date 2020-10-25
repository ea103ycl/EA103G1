<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.event_p.model.*" %>
<%@ page import="java.util.*" %>
<%
	Event_PVO event_pVO=(Event_PVO)request.getAttribute("event_pVO");
	List<String> errMsgs=(List<String>)request.getAttribute("errMsgs");
%>
<c:if test="<%=event_pVO!=null %>">
	<%
	  byte[] imgBytes=event_pVO.getEvent_p_img();
	  final Base64.Encoder encoder = Base64.getEncoder();
	  String data=encoder.encodeToString(imgBytes);
	  request.setAttribute("data", data);
	%>	
</c:if>
	<c:if test="${not empty errMsgs }">
	<c:forEach var="err" items="${errMsgs}">
		<ul>
			<li>${err}</li>
		</ul>		
		
	</c:forEach>
	</c:if>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/backend/template/css/bootstrap.min.css">
<title>event_p update</title>
</head>
<body>

<a href="select_page.jsp">回首頁</a><br>
<div class="container">
	<form action="<%=request.getContextPath() %>/backend/event_p/Event_pServlet" method="post" enctype="multipart/form-data">
			<div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">投稿作品編號</label>
                <div class="col-sm-3">
					<input type="text" class="form-control" name="event_p_no" value="${event_pVO.event_p_no }" readonly="readonly">                			
                </div>
            </div>
			
			 <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">會員編號</label>
                <div class="col-sm-3">
					<input type="text" class="form-control" name="mem_id" value="${event_pVO.mem_id }" readonly="readonly">                			
                </div>
            </div>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">活動編號</label>
                <div class="col-sm-3">
					<input type="text" class="form-control" name="event_no" value="${event_pVO.event_no }" readonly="readonly">
                </div>
            </div>
             <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">投稿作品名稱</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control " name="event_p_name" value=<%=(event_pVO==null)?"":event_pVO.getEvent_p_name().toString() %> >
                </div>
            </div>
            
             <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">投稿作品投票數</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="event_vote_num" value=<%=(event_pVO==null)?"":event_pVO.getEvent_vote_num().toString() %> >
                </div>
            </div>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">投稿作品排行</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control " name="vote_rank" value=<%=(event_pVO==null)?"":event_pVO.getVote_rank().toString() %> >
                </div>
            </div>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">投稿作品狀態</label>
                <div class="col-sm-3">
                	<select class="form-control" name="event_p_stat">
                		<option value="0" <%=event_pVO==null?"":event_pVO.getEvent_p_stat()==0?"selected":"" %>>未被檢舉</option>
                		<option value="1" <%=event_pVO==null?"":event_pVO.getEvent_p_stat()==1?"selected":"" %>>待審核</option>
                		<option value="2" <%=event_pVO==null?"":event_pVO.getEvent_p_stat()==2?"selected":"" %>>通過</option>
                		<option value="3" <%=event_pVO==null?"":event_pVO.getEvent_p_stat()==3?"selected":"" %> >不通過</option>
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
     
            
			
			<input type="hidden" name="action" value="update">
			<input type="submit" value="送出" >
	</form>
	
	</div>
	<script	type="text/javascript">
	var img2=document.getElementById("demo2");
	var myFile;

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

		
		
	}
	</script>	
</body>
</html>