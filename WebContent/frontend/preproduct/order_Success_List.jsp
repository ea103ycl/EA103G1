<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%@ page import="com.wel_record.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.preorder.model.*"%>
<%@ page import="com.preorderdetail.model.*"%>
<%/*----------顯示錢包----------*/
  WelRecordVO welRecordVO = (WelRecordVO) request.getAttribute("welRecordVO");
  MemVO memVO = (MemVO) session.getAttribute("memVO");
  PreOrderVO preorderVO = (PreOrderVO) request.getAttribute("preorderVO");
  PreOrderDetailVO preorderdetailVO = (PreOrderDetailVO) request.getAttribute("PreOrderDetailVO");
%>
<%/*----------顯示訂單LIST----------*/
    PreOrderService preorderSvc = new PreOrderService();
    List<PreOrderVO> list = preorderSvc.getAllByMemid(memVO.getMem_id());
    pageContext.setAttribute("list",list);
    

    WelRecordService welRecordSvc = new WelRecordService();
    List<WelRecordVO> set = welRecordSvc.getWelRecordByMemID(memVO.getMem_id());
    pageContext.setAttribute("set",set);

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
<%@ page import="com.preproduct.model.*"%>
<%
    PreProductService preproductSvc = new PreProductService();
    List<PreProductVO> list_for_countdown = preproductSvc.getAll();
    pageContext.setAttribute("list_for_countdown",list_for_countdown);
%>


<!DOCTYPE html>
<html lang="en">
<head>
  <%@include file="/frontend/bar/frontBarTop.jsp" %>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"> 
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
  <META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
  <title>SB Admin 2 - Blank</title>
  
  <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">
  <script type="text/javascript" src="<%=request.getContextPath()%>/frontend/preproduct/css/index_parts/jquery-3.5.1.js"></script>
  <style type="text/css" media="screen">
    #disappear,
    #disappear01,
    #disappear02,
    #addcartbtn{
      display: none;
    }
    .img01{
      width:250px; height:95%;
      margin-right: 30px;
            margin-left: 10px;
      float: left;
    }
    .count_conutdown h3{
      margin: 10px 20px;
      color:#c39d6d;
    }
    .count_contentBx .count_conutdown{

      display: flex;
      line-height: 30px !important;
    }
    .count_contentBx .count_conutdown .count_time{
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      background: #fff;
      margin-right: 20px;
      font-size: 48px;
      min-width: 110px;
      font-weight: 700;
      padding: 20px 5px 10px;
      border-radius: 4px;
      color: gray; 
    }
    
    header #header-banner{
    /*  background-image: url(css/img/index_css_imgs/banner_bg.jpg) !important; */
      background-image: url("https://mdbootstrap.com/img/Photos/Horizontal/Nature/full page/img(12).jpg");
      background-repeat: no-repeat;
      background-position:center;
      background-size: cover;
    }
    .banner-info h1{
      font-size: 48px;
      font-weight: 600;
      margin-bottom:20px !important;
    }
    .banner-border {
      width: 80% !important;
      height:200px !important;
    }
    .banner-info{
      width: 100% !important;
      background: rgba(255, 255, 255, 0.9)!important;
      padding: 20px 0 !important;
    }
    .count_conutdown{
      justify-content: center;
      align-items: center;
      
    }
    .btn-lg{
      width: 100% !important;
      font-size: 28px;
      margin:8px;
      padding: 12px 0px;
      font-weight: 600;
      justify-content: center;
      align-items: center;
      box-sizing: border-box;
    }
    .divcss5{
     border:1px groove #000; width:300px; height:300px; 
     } 
    .divcss5 img{width:auto; height:100%;} 
  </style>
</head>
<body>
<!-- preloader -->
<div id="preloader">
    <div class="spinner spinner-round"></div>
</div>
<!-- / preloader -->

<div id="top"></div>
<header>

    <!-- nav -->
    <nav class="navbar navbar-default nav-sec navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="frontindex.jsp"><img src="<%=request.getContextPath()%>/frontend/template/images/favicon.png" alt="logo"></a>
            </div><!-- / navbar-header -->
            <div class="secondary-nav">
                <a href="login-register.html" class="my-account space-right"><i class="fa fa-user"></i></a>
                <a href="shoppingCart.jsp" class="shopping-cart"><i class="fa fa-shopping-cart"></i> <span id="countcart" class="cart-badge">0</span></a>
            </div>
            <div class="navbar-collapse collapse text-center">
                <ul class="nav navbar-nav">
                    <li><a href="index.html"><span>首頁</span></a></li>
                    <li><a href="about.html"><span>ABOUT</span></a></li>
                    <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span>BLOG</span> <span class="dropdown-icon"></span></a>
                    <ul class="dropdown-menu animated zoomIn fast">
                        <li><a href="blog.html"><span>BLOG FULLWIDTH</span></a></li>
                        <li><a href="blog-masonry.html"><span>BLOG MASONRY</span></a></li>
                        <li><a href="blog-sidebar.html"><span>BLOG SIDEBAR</span></a></li>
                        <li><a href="single-post-full.html"><span>POST FULLWIDTH</span></a></li>
                        <li><a href="single-post.html"><span>POST SIDEBAR</span></a></li>
                    </ul>
                    </li>
                    <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span>SHOP</span> <span class="dropdown-icon"></span></a>
                    <ul class="dropdown-menu animated zoomIn fast">
                        <li><a href="shop.html"><span>FULL WIDTH</span></a></li>
                        <li><a href="shop-right.html"><span>RIGHT SIDEBAR</span></a></li>
                        <li><a href="shop-left.html"><span>LEFT SIDEBAR</span></a></li>
                        <li class="active"><a href="shop-masonry.html"><span>MASONRY</span></a></li>
                        <li><a href="single-product.html"><span>SINGLE PRODUCT</span></a></li>
                        <li><a href="single-product2.html"><span>SINGLE PRODUCT 2</span></a></li>
                        <li><a href="single-product3.html"><span>SINGLE PRODUCT 3</span></a></li>
                    </ul>
                    </li>
                    <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span>PAGES</span> <span class="dropdown-icon"></span></a>
                    <ul class="dropdown-menu animated zoomIn fast">
                        <li><a href="faq.html"><span>FAQ</span></a></li>
                        <li><a href="shopping-cart.html"><span>SHOPPING CART</span></a></li>
                        <li><a href="login-register.html"><span>LOGIN / REGISTER</span></a></li>
                        <li><a href="my-account.html"><span>MY ACCOUNT</span></a></li>
                        <li><a href="checkout.html"><span>CHECKOUT</span></a></li>
                        <li><a href="404.html"><span>404 PAGE</span></a></li>
                        <li><a href="components.html"><span>COMPONENTS</span></a></li>
                    </ul>
                    </li>
                    <li><a href="contact.html"><span>CONTACT</span></a></li>
                </ul>
            </div><!--/ nav-collapse -->
        </div><!-- / container -->
    </nav>
    <!-- / nav -->
    <!-- header-banner 本期預購活動倒數-->
    <div id="header-banner">
        <div class="banner-content single-page text-center">
            <div class="banner-border">
            <img class="img01" src="<%=request.getContextPath()%>/frontend/preproduct/css/index_parts/css_imgs/no1.jpg">
                <div class="banner-info">
                
                    <div class="count_contentBx">
                    
                  <div class="count_conutdown">
                    <h3>預購倒數</h3>
                    <button type="button" class="btn btn-lg btn-primary" role="button"><div id="count_day">0</div><span>Days</span></button>
                    <button type="button" class="btn btn-lg btn-primary" role="button"><div id="count_hour">0</div><span>Hours</span></button>
                    <button type="button" class="btn btn-lg btn-primary" role="button"><div id="count_minute">0</div><span>Mins</span></button>
                    <button type="button" class="btn btn-lg btn-primary" role="button"><div id="count_second">0</div><span>Secs</span></button>
                    <div id="disappear">${list_for_countdown[0].getPo_end()}</div>
                  </div>
                </div>
                </div><!-- / banner-info -->
            </div><!-- / banner-border -->
        </div><!-- / banner-content -->
    </div><!-- / header-banner -->
</header>

<!--=====自定義內容start ================================================== -->
        
        
       <div id="personal-info" class="account-info-content">
                        <h4>PERSONAL INFO <span class="pull-right"><a href="#x" class="btn btn-sm btn-primary btn-rounded no-margin"><i class="lnr lnr-pencil"></i><span>Edit</span></a></span></h4>
                        <div class="row">
                            <div class="col-xs-6 col-sm-4 col-md-2">
                                <img src="images/account-img.jpg" alt="">
                                <a href="#x" class="btn btn-primary btn-xs btn-rounded no-margin">Change Image</a>
                            </div>
                            <div class="col-xs-6 col-sm-8 col-md-10">
                                <p>Full Name: <span>John Doe</span></p>
                                <p>Country: <span>USA</span></p>
                                <p>Email: <span>me@mysite.com</span></p>
                                <p>Phone: <span>+0123 456 789</span></p>
                                <p>Date of Birth: <span>Aug. 13, 1991</span></p>
                            </div>
                        </div><!-- / row -->
                    </div><!-- / personal-info -->
                    <div id="shipping-info" class="account-info-content">
                        <h4>SHIPPING INFO <span class="pull-right"><a href="#x" class="btn btn-sm btn-primary btn-rounded no-margin"><i class="lnr lnr-pencil"></i><span>Edit</span></a></span></h4>
                        <p class="space-bottom"><span><strong>John Doe - me@mysite.com</strong></span></p>
                        <p>Country: <span>USA</span></p>
                        <p>State: <span>Florida</span></p>
                        <p>City: <span>Miami</span></p>
                        <p>Address Line: <span>S Miami Ave, SW 20th, Suite 3864</span></p>
                        <p>ZIP Code: <span>33222</span></p>
                        <div class="account-info-footer"><a href="#x" class="btn btn-sm btn-primary btn-rounded no-margin"><i class="fa fa-plus"></i><span>Add New Address</span></a></div>
                    </div><!-- / shipping-info -->
                    <div id="my-orders" class="account-info-content">
                        <h4>MY ORDERS <span class="pull-right"><a href="#x" class="btn btn-sm btn-primary btn-rounded no-margin"><i class="lnr lnr-pencil"></i><span>Edit</span></a></span></h4>
                        <p><a href="#x">Order #38726</a> <span>- Paid & Shipped</span> - Tracking No: <span>#TRCK182736</span></p>
                        <p><a href="#x">Order #34823</a> <span>- Completed on 25.10.2016</span></p>
                        <p><a href="#x">Order #23463</a> <span>- Completed on 16.08.2016</span></p>
                    </div><!-- / my-orders -->
        
        
        
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
<script type="text/javascript">
// MDB Lightbox Init
$(function () {
$("#mdb-lightbox-ui").load("mdb-addons/mdb-lightbox-ui.html");
});
</script>
 <script type="text/javascript">//倒數計時器

   var countDate = $("#disappear").text();/*這邊放list[0] po_end*/
   var someday = new Date(countDate);

  function newYear(){
   var now = new Date();
   console.log(typeof now);

       gap = someday - now;

       var second = 1000;
       var minute = second * 60;
       var hour = minute * 60;
       var day = hour * 24;

       var d = Math.floor(gap/(day));
       var h = Math.floor((gap%(day)) / (hour));
       var m = Math.floor((gap%(hour)) / (minute));
       var s = Math.floor((gap%(minute)) / second);

       document.getElementById('count_day').innerText = d;
       document.getElementById('count_hour').innerText = h;
       document.getElementById('count_minute').innerText = m;
       document.getElementById('count_second').innerText = s;
       }

       setInterval(function(){
        newYear();
       },1000)
 </script>
</body>

</html>


