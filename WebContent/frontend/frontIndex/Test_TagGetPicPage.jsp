<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<style>
img {
	max-width: 40%;
}

#result>ul>li {
	cursor: pointer;
}
</style>
</head>

<body>
	<jsp:useBean id="painterSvc" scope="page" class="com.painter.model.PainterService" />
	<jsp:useBean id="painterTagSvc" scope="page" class="com.painter_tag.model.PainterTagService" />
	<jsp:useBean id="painterTagMapSvc" scope="page" class="com.painter_tag_map.model.PainterTagMapService" />

	<form method="post" action="TagGetPic" name="searchForm">
		<div>
			<input type="hidden" name="action" value="tagGetPic"> 
			<input id="inputC" type="text" name="searchBar1"> 
			<input id="submit" type="submit" value="search">
			<input type="hidden" name="requestUrl" value="<%=request.getServletPath()%>">
		</div>
	</form>

		<c:forEach var="ptrno" items="${ptrnoList}">
			<div>
<%-- 				<img src="<%=request.getContextPath()%>/painter/ShowImage?ptr_no=${ptrno}"> --%>
				<img src="<%=request.getContextPath()%>/painter/painter.do?action=showPic&ptr_no=${ptrno}">
			</div>
		</c:forEach>








	<script>
		//=================ajax搜尋輸入模糊查詢返回推薦搜尋結果 ajax =====================
		$('#inputB').on("change", function() {
			var srtag = $(this).val();
			$.ajax({
				method : "post",
				url : "TagGetPic",
				data : {
					srtag : srtag
				},
				success : function(dataReceived) {
					$("#result> ul").html(dataReceived)
				},
				error : function() {
					alert("failed");
				}
			});
		});
		//====================== 點選推薦搜尋結果輸入搜尋=============================
		$('#result').click(function(e) {
			// console.log($('#inputB'));
			if ($('inputB').val !== null) {
				$('#inputB').val(e.target.innerHTML);
			}
		});
		//==========================
	</script>



</body>

</html>