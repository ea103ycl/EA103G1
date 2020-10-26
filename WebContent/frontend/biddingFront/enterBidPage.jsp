<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*"%>

<jsp:useBean id="bdr" scope="application" class="com.bidding.model.BdRedis" />
<%-- StartDate=${bdr.bdInfo.bdDateStr} --%>
<%-- EndDate=${bdr.bdInfo.bdDateEnd} --%>
<%-- bdNo=${bdr.bdInfo.bdNo} --%>
<html lang="en">

<head>
    <meta charset="Utf-8">
    <title>Insert title here</title>
</head>

<body>
${sqlBdNo}
    <form action="<%=request.getContextPath()%>/biddingPage/BdPageServlet">
        <input type="text" id="memId" name="memId" placeholder="memId" value="M000001" required><br>
        <input type="text" id="bdNo" name="bdNo" placeholder="bdNo" value="B000001" required><br>
        <input type="hidden" name="action" value="setBidInfo">
        <input type="submit" value="submit">
        
    </form>
    <script>
   		
    </script>
</body>

</html>
