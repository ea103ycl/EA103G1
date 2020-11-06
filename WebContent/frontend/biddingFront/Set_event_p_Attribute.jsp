<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
</head>
<body>

	<jsp:useBean id="memSvc" scope="application"
		class="com.mem.model.MemService" />	
		
	<jsp:useBean id="epSvc" scope="page" class="com.event_p.model.Event_PService" />	

<%-- 	<c:set var="memVO" value="${memSvc.findByPrimaryKey(M000001)}" scope="session"/> --%>
	<c:set var="event_no" value="E001003" scope="application"/>
<%-- 	<span>memId=${memVO.mem_id}</span><br> --%>
	<span>event_no=${event_no}</span><br>
	
	<span>event_p_no= ${epSvc.findTopByEventNoWithoutReport(event_no).event_p_no}</span><br>
	
	${latestBd1}
	${latestBd2}
	${latestBd3}
</body>
</html>