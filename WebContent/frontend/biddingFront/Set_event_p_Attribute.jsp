<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="BIG5">
	<title>Insert title here</title>
	<style>
		#wrapper {
			text-align: center;
			margin: auto;
			margin-top: 25%;
			height: 500px;
			width: 500px;
		}

		#input {
			text-align:center;
			height: 50%;
			width: 70%;
			font-size: 150%;
			padding-left: 3%;
		}

		#btn-div {
			margin: auto;
		}
		#btn{
			display:none;
		}

		#submit{
			margin-top: 5%;
			margin-left: -3px;
			text-decoration: none;
			color: #C76363;
			border: solid #C76363;
			border-radius: 15px;
			width: 100px;
			height: 50px;
			font-size: 20px;
			box-shadow: 3px 3px 1px  0px rgba(87, 87, 87, 0.3),inset 3px 3px 1px 0px  rgba(87, 87, 87, 0.3);
			text-shadow: 2.5px 2.5px 0px rgba(87, 87, 87, 0.3);
			background-color:transparent;
			outline: none;
			opacity: 0.7;
		}
		#submit:active{
			margin-top: 5.5%;
			margin-left: 0px;
			text-decoration: none;
			color: #C76363;
			border: solid #C76363;
			opacity: 0.8;
			border-radius: 15px;
			width: 100px;
			height: 50px;
			font-size: 20px;
			outline: none;
			box-shadow: none;
			text-shadow: none;
			background-color: transparent;
		}

		form {
			height: 20%;
		}
	</style>
</head>

<body>


	<div id="wrapper">
		<form action="<%=request.getContextPath()%>/biddingPage/BdPageServlet">
			<input type="hidden" name="action" value="setEventToContextscope">
			<input id="input" type="text" name="eventNo" value="${event_no}" required>
			<!-- 			<div id="btn-div">Submit</div> -->
			<div style="margin-top:5%">
				<button type="submit" id="submit" ondrop="dropDelHandler(event)">Submit</button>
			</div>

		</form>
	</div>
</body>

</html>