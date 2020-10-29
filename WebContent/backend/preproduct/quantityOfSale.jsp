<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.preorder.model.*"%>
<%@ page import="com.preorderdetail.model.*"%>
<%
	PreOrderDetailService preorderdetailSvc = new PreOrderDetailService();
	List<PreOrderDetailVO> list = preorderdetailSvc.getAll_OrderQty();
	pageContext.setAttribute("list",list);
%>
    

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>ArtsBlock預購商品銷售量</title>
		<style>

		</style>
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
							<div class="card-header py-3" id="add_bottom_area">
								<h6 class="m-0 font-weight-bold text-primary">預購商品清單</h6>
								<div class="card-body  mw-100">
									<div class="float-right ">
										<a href="<%=request.getContextPath()%>/backend/preproduct/addPreProduct.jsp" id="add_bottom" class="btn btn-primary btn-lg active ml-2" role="button" aria-pressed="true">新增商品</a>
										
										<a href="<%=request.getContextPath()%>/backend/preproduct/addPreProduct_ByMano.jsp?EVENT_NO=${list01[0].event_no}" id="add_bottom" class="btn btn-primary btn-lg active ml-2" role="button" aria-pressed="true">新增系列商品</a>
										
										<a href="<%=request.getContextPath()%>/backend/preproduct/addDiscount.jsp" id="add_bottom" class="btn btn-primary btn-lg active ml-2" role="button" aria-pressed="true">新增折扣</a>
									</div>
									<div class="float-left align-bottom">
										<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/preproduct/preproduct.do" >
											<b>搜尋預購商品編號 :</b>
											<input type="text" name="po_prod_no">
											<input type="hidden" name="action" value="getOne_For_Display">
											<input type="submit" value="送出">
										</FORM>
									</div>
								</div>
							</div>
							<div class="card-body">
								<div class="table-responsive">
									<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
										<thead>
											<tr>
												<th>預購商品編號</th>
							                    <th>銷售量</th>
							                    <th>功能1</th>
							                                         
											</tr>                    
										</thead>
										<tfoot>
						                    <tr>
							                    <th>預購商品編號</th>
							                    <th>銷售量</th>
							                    <th>功能1</th>
							                                     
						                    </tr>
										</tfoot>
										<tbody>
										<%@ include file="pages/page1.file" %>
					                    <c:forEach var="preorderdetailVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
											<tr>
												<td>${preorderdetailVO.po_prod_no}</td>
												
												<td>${preorderdetailVO.po_qty}</td>
												
												<td>
													<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/preproduct/preproduct.do" style="margin-bottom: 0px;">
														<input type="submit" value="折扣">
														
														<input type="hidden" name="action" value="switchDiscount">
													</FORM>
												</td>
											</tr>
					                    </c:forEach>
					                    <%@ include file="pages/page2.file" %>
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
		
	</body>
</html>