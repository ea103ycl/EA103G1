<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.reg_inf.model.*"%>
<%@ page import="com.meeting.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.wel_record.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>
<%
    String mt_no = (String) request.getParameter("mt_no"); 

    Reg_infService reg_infSvc = new Reg_infService();
    List<Reg_infVO> list = reg_infSvc.getReg_inf_mt_no(mt_no);
    pageContext.setAttribute("list",list);   

    
    
    MeetingService meetingSvc = new MeetingService();
    MeetingVO meetingVO = meetingSvc.getOneMeeting("mt_no");
    pageContext.setAttribute("meetingSvc",meetingSvc);

%>


<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
	  <meta name="description" content="">
	  <meta name="author" content="">
<title>報名表清單 -listAllReg_inf_mt_no.jsp</title>
<!-- favicon -->
		<link rel="icon" href="<%=request.getContextPath()%>/backend/template/img/favicon.png">


</head>
<body onload="checkoutAmount();" id="page-top">
<div id="wrapper">
	
			<%@include file="/backend/bar/backBarSide.jsp"%>
	
			<div id="content-wrapper" class="d-flex flex-column">		
	
				<div id="content">
	
					<!-- Topbar -->
					<%@include file="/backend/bar/backBarTop.jsp"%>
	
					<div class="container-fluid">				
					<!--=====自定義內容start ================================================== -->
						
						<%-- 頁面標題 --%>
						<h1 class="h3 mb-2 text-gray-800">取消的見面會</h1>
							  <h4><a href="<%=request.getContextPath()%>/backend/meeting/listAllMeeting_back.jsp">回首頁</a></h4>
						
                        <%-- 錯誤表列 --%>
						<c:if test="${not empty errorMsgs}">
							<font style="color: red">請修正以下錯誤:</font>
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color: red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>
<div class="table table-hover">
<table class="table table-bordered text-center"
       id="dataTableNoSearchChange" width="100%" cellspacing="0">
       <thead>
	<tr class="bg-gray-400">

	<tr>
		<th>狀態</th>
		<th>報名表ID</th>
		<th>見面會名稱</th>
		<th>報名人數</th>
		<th>費用</th>
		<th>退費</th>		
	</tr>
	</thead>
	
	<tbody>
	<%@ include file="page1.file" %> 
	<c:forEach var="reg_infVO" items="${list}"   begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">

		<tr>
		<td class="align-middle">	    
<c:set var="ri_status" scope="session" value="${reg_infVO.ri_status}"/>
<c:choose>
    <c:when test="${ri_status == 1}">
            報名成功
    </c:when>
    <c:when test="${ri_status == 2}">
            已退費
    </c:when>
    <c:when test="${ri_status == 3}">
            已取消，待退費
    </c:when>
    <c:otherwise>
           報名成功
    </c:otherwise>
</c:choose>   
</td>		    
			<td>${reg_infVO.ri_id}</td>
			<td>${meetingSvc.getOneMeeting(reg_infVO.mt_no).mt_id}</td>
		
			<td>${reg_infVO.ri_qty}</td>
			<td>${meetingSvc.getOneMeeting(reg_infVO.mt_no).ri_fee}</td>
			
			
	<td class="align-middle">
	<c:if test="${reg_infVO.ri_status == 1}">
	 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/reg_inf/reg_inf.do" style="margin-bottom: 0px;">
			     
			     <input type="hidden" name="ri_id"  value="${reg_infVO.ri_id}">
			     <input type="hidden" name="mt_no"  value="${reg_infVO.mt_no}">
			     <input type="hidden" name="ri_qty" id="ri_qty" value="${reg_infVO.ri_qty}">
			     <input type="hidden" name="ri_fee" id="ri_fee" value="${meetingSvc.getOneMeeting(reg_infVO.mt_no).ri_fee}" >
			     <input type="hidden" name="totalAmount" id="amount2" >
			     <input type="hidden" name="mem_id"  value="${reg_infVO.mem_id}">
			     <input type="hidden" name="action"	value="cancel_back">
			     <input type="submit" value="退費">
			     </FORM>
		</c:if>
		</td>
		</tr>
	</c:forEach>
				 
	
</table>
<div style="text-align:center;">
<%@ include file="page2.file" %>
                          </div>
					</div>								
				</div>
			</div>
	<!--===== 自定義內容end ================================================== -->
		</div> <!--END OF container-fluid-->
	</div>	
			
		<%@include file="/backend/bar/footer.jsp"%>
</div>
</body>
<script>


function checkoutAmount()
{
/*計算總價格*/

var ri_qty=document.getElementById("ri_qty").value;
var ri_fee=document.getElementById("ri_fee").value;
console.log("======================")
document.getElementById("amount2").value=ri_qty*ri_fee;
}
</script>
</html>