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
  String po_no = (String) request.getAttribute("po_no");
%>
<%
    PreOrderService preorderSvc = new PreOrderService();
    List<PreOrderVO> list = preorderSvc.getAllByMemid(memVO.getMem_id());
    pageContext.setAttribute("list",list);
    
//     PreOrderDetailService preorderdetailSvc = new PreOrderDetailService();
//     List<PreOrderDetailVO> list1 = preorderdetailSvc.getAllByPo_no(po_no);
//     pageContext.setAttribute("list1",list1);


    
    WelRecordService welRecordSvc = new WelRecordService();
    List<WelRecordVO> set = welRecordSvc.getWelRecordByMemID(memVO.getMem_id());
    pageContext.setAttribute("set",set);
    
    
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
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
			</c:if></strong></span>
          
        </div>
        <div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">您的訂單明細</h6>
                </div>
                <div class="card-body">
                  <div class="table-responsive">
                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                      <thead>
                        <tr>
                          <th>訂單編號</th>
                          <th>訂單成立時間</th>
                          <th>收件人</th>
                          <th>收件地址</th>
                          <th>訂單狀態</th>
                          <th>數量</th>
                          <th>單價</th>

                        </tr>
                      </thead>
                      <tfoot>
                        <tr>
                          <th>訂單編號</th>
                          <th>訂單成立時間</th>
                          <th>收件人</th>
                          <th>收件地址</th>
                          <th>訂單狀態</th>
                          <th>數量</th>
                          <th>單價</th>
                        </tr>
                      </tfoot>
                      
                      <tbody>
                       <c:forEach var="preorderdetailVO" items="${list1}">
                          <tr>
                              <td>${preorderVO.po_no}</td>
                              <td>${preorderVO.po_time}</td>
                              <td>${preorderVO.po_name}</td>
                              <td>${preorderVO.po_addr}</td>
                              <td>${preorderVO.po_status}</td>
                              
                              <td>${preorderdetailVO.po_qty}</td>
                              <td>${preorderdetailVO.po_price}</td>
                              
                            <td>
                              <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/preproduct/preproduct.do" style="margin-bottom: 0px;">
                                 <input type="submit" value="修改">
                                 <input type="hidden" name="po_no"  value="${preorderVO.po_no}">
                                 <input type="hidden" name="action" value="getOne_For_Update"></FORM>
                            </td>
                            <td>
                              <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/preproduct/preproduct.do" style="margin-bottom: 0px;">
                                 <input type="submit" value="刪除">
                                 <input type="hidden" name="po_no"  value="${preorderVO.po_no}">
                                 <input type="hidden" name="action" value="delete"></FORM>
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


