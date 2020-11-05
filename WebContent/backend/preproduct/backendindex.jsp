<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.preproduct.model.*"%>
<%@ page import="com.discount.model.*" %>
<%@ page import="com.preorderdetail.model.*" %>
<%
    PreProductService preproductSvc = new PreProductService();
    List<PreProductVO> list = preproductSvc.getAll();
    pageContext.setAttribute("list",list);
    
    DiscountSettingService discountSvc = new DiscountSettingService();
	List<DiscountSettingVO> list_dis = discountSvc.getAll();
	pageContext.setAttribute("list_dis",list_dis);
%>
<%
	PreProductVO preproductVO = (PreProductVO) request.getAttribute("preproductVO");
	DiscountSettingVO discountsettingVO = (DiscountSettingVO) request.getAttribute("discountsettingVO");
%>
<jsp:useBean id="disSvc" scope="page" class="com.discount.model.DiscountSettingService" />
<jsp:useBean id="preorderdetailSvc" scope="page" class="com.preorderdetail.model.PreOrderDetailService" />
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>ArtsBlock預購商品首頁</title>
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
						<h1 class="h3 mb-2 text-gray-800" id="text_h3"><br>本期得獎作品前10名</h1>
						<br>
						<div class="card shadow mb-4">
					        <%@include file="/backend/preproduct/tools/preproduct_cardparts.jsp"%>
						</div>
						<h1 class="h3 mb-2 text-gray-800" id="text_h3"><br>預購商品</h1>
						<div>
					        <c:if test="${not empty errorMsgs}">
								
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
									<div class="alert alert-danger" role="alert">
										${message}</div>
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
							                    <th>投稿作品編號</th>
							                    <th>素材編號</th>
							                    <th>預購開始時間</th>
							                    <th>預購結束時間</th>
							                    <th>原價</th>
							                    <th>狀態</th>
							                    <th>切換</th>
							                    <th>修改</th>
							                    <th>刪除</th>                      
											</tr>                    
										</thead>
										<tfoot>
						                    <tr>
							                    <th>預購商品編號</th>
							                    <th>投稿作品編號</th>
							                    <th>素材編號</th>
							                    <th>預購開始時間</th>
							                    <th>預購結束時間</th>
							                    <th>原價</th>
							                    
							                    <th>狀態</th>
							                    <th>切換</th>
							                    <th>修改</th>
							                    <th>刪除</th>                      
						                    </tr>
										</tfoot>
										<tbody>
										<%@ include file="pages/page1.file" %>
					                    <c:forEach var="preproductVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
											<tr>
												<td>${preproductVO.po_prod_no}</td>
												<td><img class="img01"; src="tools/eventP_show.jsp?EVENT_P_NO=${preproductVO.event_p_no}">
												<td><img class="img01"; src="tools/materialP_show.jsp?MA_NO=${preproductVO.ma_no}"></td>
												<td id="po_dateS"><fmt:formatDate value="${preproductVO.po_start}" pattern="yyyy/MM/dd"/></td>
												<td id="po_dateE"><fmt:formatDate value="${preproductVO.po_end}" pattern="yyyy/MM/dd"/></td>
												<td>${preproductVO.po_price}</td>
												
												
												<td>
													<c:forEach var="disVO" items="${list_dis}">
													
														<c:if test="${preproductVO.po_prod_no==disVO.po_prod_no}">
														
															${(disVO.reach_discount/10).intValue()}折/${disVO.reach_number}人<br>

														</c:if>
														
													
													</c:forEach>
												</td>
												
												<td id="swich_btn">
													<FORM id="formName" name="formName" METHOD="post" ACTION="<%=request.getContextPath()%>/backend/preproduct/preproduct.do" style="margin-bottom: 0px;">
														<input type="submit" name="swich" value="上/下架">
														<input type="hidden" name="po_prod_no"  value="${preproductVO.po_prod_no}">
														<input type="hidden" name="po_start" value="${preproductVO.getPo_end()}">
														<input type="hidden" name="po_end" value="${preproductVO.getPo_start()}">
														<input type="hidden" name="action" value="switchPreProduct">
														
													</FORM>
												</td>
												    
												<td>
													<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/preproduct/preproduct.do" style="margin-bottom: 0px;">
														<input type="submit" value="修改">
														<input type="hidden" name="po_prod_no"  value="${preproductVO.po_prod_no}">
														<input type="hidden" name="action" value="getOne_For_Update">
													</FORM>
												</td>
												
												<td>
													<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/preproduct/preproduct.do" style="margin-bottom: 0px;">
														<input type="submit" value="刪除">
														<c:forEach var="preorderdetailVO" items="${preorderdetailSvc.getAll()}">
															<c:if test="${preproductVO.po_prod_no==preorderdetailVO.po_prod_no}">
												                <input type="hidden" name="po_prod_detail"  value="${preproductVO.po_prod_no}">
											                </c:if>
										                </c:forEach>
														<input type="hidden" name="po_prod_no"  value="${preproductVO.po_prod_no}">
														<c:forEach var="disVO" items="${disSvc.all}">
										                    <c:if test="${preproductVO.po_prod_no==disVO.po_prod_no}">
											                    <input type="hidden" name="dis_no"  value="${disVO.dis_no}">
										                    </c:if>
										                </c:forEach>
														<input type="hidden" name="action" value="delete">
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