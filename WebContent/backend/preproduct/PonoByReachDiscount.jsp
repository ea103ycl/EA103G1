<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.preorder.model.*"%>
<%@ page import="com.preorderdetail.model.*"%>
<%@ page import="com.discount.model.*"%>
<%@ page import="com.wel_record.model.*"%>
<%@ page import="com.mem.model.*"%>
<%
	PreOrderDetailService preorderdetailSvc = new PreOrderDetailService();
	List<PreOrderDetailVO> list = preorderdetailSvc.getAll_OrderQty();
	pageContext.setAttribute("list",list);
	
	DiscountSettingService discountSvc = new DiscountSettingService();
	List<DiscountSettingVO> list_dis = discountSvc.getAll();
	pageContext.setAttribute("list_dis",list_dis);
	
	List<PreOrderVO> preorderlist = (List<PreOrderVO>) session.getAttribute("preorderlist");
%>
<%/*----------顯示錢包----------*/
	MemService memSvc = new MemService();
	List<MemVO> list_mem = memSvc.getAll();
	pageContext.setAttribute("list_mem",list_mem);
%>
<% /*----------阻擋重複提交(F5)----------*/
	Random ran = new Random();
	String formhash = String.valueOf(ran.nextInt());
	Set<String> formhashSession = (Set<String>) session.getAttribute("formhashSession");
	if(formhashSession == null){
		formhashSession = new HashSet<String>();
	}
	while(formhashSession.contains(formhash)){
		formhash = String.valueOf(ran.nextInt());
	}
	formhashSession.add(formhash);
	session.setAttribute("formhashSession",formhashSession);
%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>ArtsBlock預購商品銷售量</title>
		<style>
			#addcartbtn{display: none;}
		</style>
	</head>
	<body id="page-top">
	<%
		if (preorderlist != null && (preorderlist.size() > 0)) {
	%>
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
								<script type="text/javascript">
							        alert("${errorMsgs}");
							    </script>
							</c:if>
						</div>
						<!-- DataTales Example -->
						<div class="card shadow mb-4">
							<div class="card-header py-3" id="add_bottom_area">
								<h6 class="m-0 font-weight-bold text-primary">預購商品清單</h6>
								<div class="card-body  mw-100">
									<div class="float-right "></div>
									<div class="float-left align-bottom">
										<h4>預購商品編號 :${preorderlist[0].po_prod_no}</h4>
									</div>
								</div>
							</div>
							<div class="card-body">
								<div class="table-responsive">
									<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
										<thead>
											<tr>
												<th>訂單編號</th>
												<th>會員編號 / 客戶姓名</th>
							                    <th>購買數量</th>
							                    <th>商品價格</th>
							                    <th>會員錢包</th>
							                    <th>折讓金</th>
							                    <th>發送折價金</th>               
											</tr>                    
										</thead>
										<tfoot>
						                    <tr>
						                    	<th>訂單編號</th>
												<th>會員編號 / 客戶姓名</th>
							                    <th>購買數量</th>
							                    <th>商品價格</th>
							                    <th>會員錢包</th>
							                    <th>折讓金</th>
							                    <th>發送折價金</th>          
						                    </tr>
										</tfoot>
										<tbody>
										<%@ include file="pages/page1.file" %>
					                    <c:forEach var="preorderVO" items="${preorderlist}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" varStatus="status">
					                   
											<tr>
												<td>${preorderVO.po_no}</td>
												<td>${preorderVO.mem_id} / ${preorderVO.po_name}</td>
												<td>${preorderVO.po_qty}</td>
												<td>$ ${(preorderVO.po_price*0.9).intValue()}</td>
												<td>
													<c:forEach var="memVO" items="${list_mem}">
														<c:if test="${preorderVO.mem_id==memVO.mem_id}">
															$ ${memVO.balance}
														</c:if>
													</c:forEach>
												</td>
												<td>
													<c:forEach var="disVO" items="${list_dis}">
														<c:if test="${preorderVO.po_prod_no==disVO.po_prod_no}">
															$ ${((preorderVO.po_price*0.9)*preorderVO.po_qty*(100-disVO.reach_discount)/100).intValue()}
														</c:if>
													</c:forEach>
												</td>
												<td>
													<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/preproduct/preorder.do" style="margin-bottom: 0px;">
														<input type="submit"  name="submit" value="發送折讓金" >
														<input type="hidden" name="po_no"  value="${preorderVO.po_no}">
														<input type="hidden" name="po_status"  value="${preorderVO.po_status}">
														<input type="hidden" name="mem_id"  value="${preorderVO.mem_id}">
														<input id="addcartbtn" type="text" name="formhash"  value="<%=formhash %>">
															<c:forEach var="disVO" items="${list_dis}">
																<c:if test="${preorderVO.po_prod_no==disVO.po_prod_no}">
																	<input type="hidden" name="return_discount"  value="${((preorderVO.po_price*0.9)*preorderVO.po_qty*(100-disVO.reach_discount)/100).intValue()}">
																</c:if>
															</c:forEach>
														<input type="hidden" name="action" value="returnDiscount">
													</FORM>
													<c:if test="${preorderVO.po_status==1}">處理中</c:if>
													<c:if test="${preorderVO.po_status==2}">出貨中</c:if>
													<c:if test="${preorderVO.po_status==3}">已到貨</c:if>
													<c:if test="${preorderVO.po_status==4}">已結案</c:if>
													<c:if test="${preorderVO.po_status==5}">結案(已折讓)</c:if>
												</td>
											</tr>
											</c:forEach>
					                   </tbody>
					                </table>
								</div>
							</div>
						</div>
						<%}else{%>
							<script type="text/javascript">
						        alert("本商品尚未符合折扣標準，故無法顯示清單");
						    </script>
						    <a href="<%=request.getContextPath()%>/backend/preproduct/quantityOfSale.jsp">回銷售總表</a>
						<%}%>
					<!--===== 自定義內容end ================================================== -->
					</div> <!--END OF container-fluid-->
					<%@include file="/backend/bar/footer.jsp"%>
				</div> <!--END OF content-->
			</div><!--END OF content-wrapper -->
		</div><!--END OF wrapper -->
		<script type="text/javascript">
			
		</script>
	</body>
</html>
