<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.preorder.model.*"%>
<%
	PreOrderService preorderSvc = new PreOrderService();
    List<PreOrderVO> list = preorderSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<%
	PreOrderVO preorderVO = (PreOrderVO) request.getAttribute("preorderVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
	
		<title>ArtsBlock預購商品首頁</title>
		
	</head>
	<body id="page-top">
		<div id="wrapper">
			<%@include file="/backend/bar/backBarSide.jsp"%>
			<div id="content-wrapper" class="d-flex flex-column">		
				<div id="content">
					<!-- Topbar -->
					<%@include file="/backend/bar/backBarTop.jsp"%>
					<div class="container-fluid">				
					<!--=====自定義內容start ================================================== -->
				  	<!-- Page Heading -->
			          <h1 class="h3 mb-2 text-gray-800">預購商品訂單總表</h1>
			          <p class="mb-4">這裡存放著關係公司命脈的重要客戶訂單，不要亂搞，不然公司會倒! <a target="_blank" href="https://datatables.net">這超連結要幹嘛我真的不知道</a>.</p>
					  <div>
				          <c:if test="${not empty errorMsgs}">
							  <font style="color:red">請修正以下錯誤:</font>
							  <ul>
								  <c:forEach var="message" items="${errorMsgs}">
									  <li style="color:red">${message}</li>
								  </c:forEach>
							  </ul>
						  </c:if>
			          </div>
			          <!-- DataTales Example -->
			          <div class="card shadow mb-4">
			            <div class="card-header py-3">
			              <h6 class="m-0 font-weight-bold text-primary">主訂單列表</h6>
			            </div>
			            <div class="card-body">
			              <div class="table-responsive">
			                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
			                  <thead>
			                    <tr>
			                      <th>訂單編號</th>
			                      <th>會員編號</th>
			                      <th>訂單成立時間</th>
			                      <th>郵遞區號</th>
			                      <th>收件人</th>
			                      <th>連絡電話</th>
			                      <th>寄件地址</th>
			                      <th>訂單狀態</th>
			                      <th>訂單總額</th>
			                      <th>買方備註</th>
			                    </tr>
			                  </thead>
			                  <tfoot>
			                    <tr>
			                      <th>訂單編號</th>
			                      <th>會員編號</th>
			                      <th>訂單成立時間</th>
			                      <th>郵遞區號</th>
			                      <th>收件人</th>
			                      <th>連絡電話</th>
			                      <th>寄件地址</th>
			                      <th>訂單狀態</th>
			                      <th>訂單總額</th>
			                      <th>買方備註</th>
			                    </tr>
			                  </tfoot>
			                  <%@ include file="pages/page4_forOrder.file" %>
			                  <tbody>
				                  <c:forEach var="preorderVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				                    <tr>
				                      <td>${preorderVO.po_no}</td>
				                      <td>${preorderVO.mem_id}</td>
				                      <td>${preorderVO.po_time}</td>
				                      <td>${preorderVO.po_zip}</td>
				                      <td>${preorderVO.po_name}</td>
				                      <td>0${preorderVO.po_phone}</td>
				                      <td>${preorderVO.po_addr}</td>
				                      <td>${preorderVO.po_status}</td>
				                      <td>${preorderVO.po_total}</td>
				                      <td>${preorderVO.po_note}</td>
				                    </tr>
				                  </c:forEach>
			                  </tbody>
			                </table>
			                
			              </div>
			            </div>
			          </div>

					<!--===== 自定義內容end ================================================== -->
					</div> <!--END OF container-fluid-->
					<%@include file="/backend/bar/footer.jsp"%>
				</div> <!--END OF content-->
			</div><!--END OF content-wrapper -->
		</div><!--END OF wrapper -->
		<script>
		<!--JavaScript內容-->
		</script>				
	</body>
	
	
</html>