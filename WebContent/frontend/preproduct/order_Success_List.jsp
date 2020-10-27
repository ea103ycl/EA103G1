<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%@ page import="com.wel_record.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.preorder.model.*"%>
<%@ page import="com.preorderdetail.model.*"%>
<%
  WelRecordVO welRecordVO = (WelRecordVO) request.getAttribute("welRecordVO");
  MemVO memVO = (MemVO) session.getAttribute("memVO");
  PreOrderVO preorderVO = (PreOrderVO) request.getAttribute("preorderVO");
  PreOrderDetailVO preorderdetailVO = (PreOrderDetailVO) request.getAttribute("PreOrderDetailVO");
%>
<%
    PreOrderService preorderSvc = new PreOrderService();
    List<PreOrderVO> list = preorderSvc.getAllByMemid(memVO.getMem_id());
    pageContext.setAttribute("list",list);
    

    WelRecordService welRecordSvc = new WelRecordService();
    List<WelRecordVO> set = welRecordSvc.getWelRecordByMemID(memVO.getMem_id());
    pageContext.setAttribute("set",set);

%>

<% 
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
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"> 
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
  <META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
  <title>SB Admin 2 - Blank</title>
  <link rel="stylesheet" type="text/css" href="css/index.css">
  <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">
  <script type="text/javascript" src="<%=request.getContextPath()%>/frontend/preproduct/css/index_parts/jquery-3.5.1.js"></script>

</head>
<body>



<!--=====自定義內容start ================================================== -->
        <h3>訂購成功</h3>
        <div>
          <p>顯示session客戶編號</p>
          <span><strong><br>
            <c:if
				test="${not empty sessionScope.memVO.m_accno}">
				會員帳號:${sessionScope.memVO.m_accno}
				錢包餘額:${sessionScope.memVO.balance}
			</c:if></strong></span>
			<c:if test="${not empty errorMsgs}">
				              <font style="color:red">請修正以下錯誤:</font>
				              <ul>
				                <c:forEach var="message" items="${errorMsgs}">
				                  <li style="color:red">${message}</li>
				                </c:forEach>
				                </ul></c:if>
		  
        </div>
        <div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">您的訂單</h6>
                </div>
                <div class="card-body">
                  <div class="table-responsive">
                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                      <thead>
                        <tr>
                          <th>訂單編號</th>
                          <th>客戶編號</th>
                          <th>訂單成立時間</th>
                          <th>收件人</th>
                          <th>連絡電話</th>
                          <th>收件地址</th>
                          <th>訂單狀態</th>
                        </tr>
                      </thead>
                      <tfoot>
                        <tr>
                          <th>訂單編號</th>
                          <th>客戶編號</th>
                          <th>訂單成立時間</th>
                          <th>收件人</th>
                          <th>連絡電話</th>
                          <th>收件地址</th>
                          <th>訂單狀態</th>
                        </tr>
                      </tfoot>
                      <%@ include file="/backend/preproduct/pages/page1.file" %> 
                      <tbody>
                        <c:forEach var="preorderVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
    
                          <tr>
                              <td>${preorderVO.po_no}</td>
                                <td>${preorderVO.mem_id}</td>
                                <td>${preorderVO.po_time}</td>
                                <td>${preorderVO.po_name}</td>
                                <td>${preorderVO.po_phone}</td>
                                <td>${preorderVO.po_addr}</td>
                                <td>${preorderVO.po_status}</td>
                            <td>
                              <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/preproduct/preorder.do" style="margin-bottom: 0px;">
                                 <input type="submit" value="查明細">
                                 <input type="hidden" name="po_no"  value="${preorderVO.po_no}">
                                 <input type="hidden" name="action" value="getOne_For_Display"></FORM>
                            </td>
                            <td>
                              <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/preproduct/preorder.do" style="margin-bottom: 0px;">
                                 <input type="submit" value="取消訂單">
                                 <input type="hidden" name="po_no"  value="${preorderVO.po_no}">
                                 <input type="hidden" name="po_total"  value="${preorderVO.po_total}">
                                 
                                 <input type="text" name="formhash"  value="<%=formhash %>">
                                 
                                 <input type="hidden" name="action" value="cancel_order"></FORM>
                            </td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
                
              </div>

          <!--===== 自定義內容end ================================================== -->
          
<script src="https://unpkg.com/ionicons@5.2.3/dist/ionicons.js"></script>  
<%@include file="/frontend/bar/frontBarFooter.jsp" %>
<script src="<%=request.getContextPath()%>/frontend/preproduct/css/index_parts/jquery-3.5.1.js"></script>
</body>

</html>


