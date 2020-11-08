<!-------------------------------------- nav ------------------------------------------>
<%@include file="/frontend/bar/frontBarTop.jsp"%>
<!-------------------------------------- /nav ------------------------------------------>


<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*,java.io.*"%>
<%@ page import="com.order.model.*"%>
<%@ page import="com.eval.model.*"%>
<%@page import="com.mem.model.*"%>

<!DOCTYPE html>
<%
	//假資料

// 	String mem_id = "M000001";
// 	String m_name = "麥可";
// 	Integer m_zip = 421;
// 	String m_addr = "台中市豐原區";
// 	String m_phone = "0982-766248";

// 	MemVO memVO = new MemVO();
// 	memVO.setMem_id(mem_id);
// 	memVO.setM_name(m_name);
// 	memVO.setM_zip(m_zip);
// 	memVO.setM_addr(m_addr);
// 	memVO.setM_phone(m_phone);
// 	request.getSession().setAttribute("memVO", memVO);
%>










<%
	//session
	MemVO memVO = new MemVO();
	memVO = (MemVO) request.getSession().getAttribute("memVO");
%>



<%
List<OrderVO> order_list = (ArrayList<OrderVO>)session.getAttribute("or_status_list_buyer");
pageContext.setAttribute("order_list", order_list);
%>




<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>SB Admin 2 - Tables</title>
<!-- Custom fonts for this template -->
<!--     <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css"> -->
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
<!--     Custom styles for this template -->
<!-- <link href="css/sb-admin-2.min.css" rel="stylesheet"> -->
<!--     Custom styles for this page -->
<link href="vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">
<body>

	<!-- Begin Page Content -->
	<div class="container-fluid">
	

 
		<div class="card shadow mb-4">

			<div class="card-body">
				<div class="table-responsive" style="margin-top: 200px;">
				
				
<form  action="<%=request.getContextPath()%>/frontend/shop/order" method="POST" enctype="multipart/form-data">
   <select  name="or_status" style="width:130px;font-size:15px; "onchange="submit();" >
   
   <option value="">選擇訂單狀態</option>
   <option value="100">查看全部</option> 
   <option value="1">尚未出貨</option> 
   <option value="2">運送中</option> 
   <option value="3">我已收到貨</option>
   <option value="4">訂單完成</option>  
   </select>
   <input type="hidden" name="mem_id" value="<%=memVO.getMem_id()%>"> 
   <input type="hidden" name="action" value="Get_Order_By_Status_Buyer"> 
</form> 

<%------------------- 錯誤表列 --------------------------%>
						<c:if test="${not empty errorMsgs}">
						<ul>
						<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
						</c:forEach>
						</ul>
						</c:if>
<%------------------- 錯誤表列 --------------------------%>				
				
				
	<table class="table table-bordered" id="dataTable" width="100%"  cellspacing="0" style="margin: 20px 0 100px 0;">
						<thead>
							<tr>
								<th>訂單編號</th>
								<th>訂單成立時間</th>
								<th>消費金額</th>
								<th>備註</th>
								<th>訂單狀態</th>
								<th>訂單明細</th>
							</tr>
						</thead>
						<tfoot>
						</tfoot>
						<tbody>
							<c:forEach var="orderVO" items="${order_list}">
								<tr>
								    
									<td>${orderVO.or_no}</td>
									<td><fmt:formatDate value="${orderVO.or_time}"
											pattern="yyyy-MM-dd HH:mm" /></td>
									<td>$ ${orderVO.or_total}</td>
									<td>${orderVO.or_note}</td>
									<td><c:if test="${orderVO.or_status==1}"><font color="deeppink">處理中</font></c:if> <c:if
											test="${orderVO.or_status==2}"><font color="blue">出貨中</font></c:if> <c:if
											test="${orderVO.or_status==3}"><font color="deeppink">我已收到貨</font></c:if> <c:if
											test="${orderVO.or_status==4}"><font color="blue">訂單完成</font></c:if></td>
											
									<td>
										
											<input type="hidden" name="or_no" value="${orderVO.or_no}">
											<button type="button"class="btn btn-primary-filled btn-rounded" onclick='openWindows("${orderVO.or_no}")' >查看訂單詳情</button>
			<script>											
			function openWindows(or_no){
				window.open("<%=request.getContextPath()%>/frontend/shop/order/DetailSearch.jsp?or_no=" + or_no ,"Sample","fullscreen=no,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no, copyhistory=no,width=1000,height=430,left=750,top=150");
			}
	     </script>
										
									</td>
									
									
									
							
									
									
									
	
											
											
											
				<td>
				<c:if test="${orderVO.or_status==2}">
				<FORM METHOD="post"  action="<%=request.getContextPath()%>/frontend/shop/order"	enctype="multipart/form-data" style="margin-bottom: 0px;">
							<input   type="submit" value="我收到貨了" class="btn btn-primary-filled btn-rounded"> 
							<input type="hidden" name="or_status" value="${orderVO.or_status}">
							<input type="hidden" name="or_no" value="${orderVO.or_no}">
							<input type="hidden" name="action" value="Change_Order_Status_forBuyer">
				</FORM>
				 </c:if>
				 
				 <c:if test="${orderVO.or_status==3}">
				<FORM METHOD="post"  action="<%=request.getContextPath()%>/frontend/shop/order"	enctype="multipart/form-data" style="margin-bottom: 0px;">
							<input  class="btn btn-primary-filled btn-rounded" type="submit" value="完成訂單"> 
							<input type="hidden" name="or_status" value="${orderVO.or_status}">
							<input type="hidden" name="or_no" value="${orderVO.or_no}">
							<input type="hidden" name="action" value="Change_Order_Status_forBuyer">
				</FORM>
				 </c:if>
				 
				 
				

			 



<jsp:useBean id="evalSvc" scope="page" class="com.eval.model.EvalService" />

<c:if test="${orderVO.or_status==4 && pageScope.evalSvc.getAllByOr_no(orderVO.or_no).size()==0}">
				<FORM METHOD="post"  action="<%=request.getContextPath()%>/frontend/shop/order"	enctype="multipart/form-data" style="margin-bottom: 0px;">
							<font size="2px" ><input  class="btn btn-primary-filled btn-rounded" type="submit" value="還沒給評價唷"></font> 
							
							<input type="hidden" name="or_no" value="${orderVO.or_no}">
							<input type="hidden" name="action" value="Eval_Prod">
				</FORM>
				 </c:if>

				
				 
				 
				</td>	
				
				
					
											
											
									
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
	<!-- Bootstrap core JavaScript-->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<!-- Core plugin JavaScript-->
	<script src="vendor/jquery-easing/jquery.easing.min.js"></script>
	<!-- Custom scripts for all pages-->
	<script src="js/sb-admin-2.min.js"></script>
	<!-- Page level plugins -->
	<script src="vendor/datatables/jquery.dataTables.min.js"></script>
	<script src="vendor/datatables/dataTables.bootstrap4.min.js"></script>
	<!-- Page level custom scripts -->
	<script src="js/demo/datatables-demo.js"></script>

	<!--------------------------------------- footer --------------------------------------->
	<%@include file="/frontend/bar/frontBarFooter.jsp"%>
	<!--------------------------------------- /footer --------------------------------------->
	
	
</body>

</html>