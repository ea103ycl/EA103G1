<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,java.io.*"%>
<%@ page import="com.prod.model.*"%>

<%
  List<ProdVO> list = (ArrayList<ProdVO>) session.getAttribute("fuzzy_list");
  pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Minimal Shop Theme">
    <meta name="keywords" conteAnt="responsive, retina ready, html5, css3, shop, market, onli store, bootstrap theme" />
    <meta name="author" content="KingStudio.ro">
    <!-- favicon -->
    <link rel="icon" href="images/favicon.png">
    <!-- page title -->
    <title>MS - Minimal Shop Theme</title>
    <!-- bootstrap css -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- css -->
    <link href="css/style.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <!-- fonts -->
    <link href="https://fonts.googleapis.com/css?family=Rubik:400,500,700,900" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Lily+Script+One" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href='fonts/FontAwesome.otf' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="css/linear-icons.css">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
</head>

<body>
    <!-- preloader -->
    <div id="preloader">
        <div class="spinner spinner-round"></div>
    </div>
    <!-- / preloader -->
    <div id="top"></div>
    <!-- header -->
    <header>
    
        <!-------------------------------------- nav ------------------------------------------>
        <%@include file="/frontend/bar/frontBarTop.jsp"%>
        <!-------------------------------------- /nav ------------------------------------------> 
        
        <!-- header-banner -->
        <div id="header-banner">
            <div class="banner-content single-page text-center">
                <div class="banner-border">
                    <div class="banner-info">
                         <h1>ArtsBlock Shop</h1>
                        <p>Welcome to you</p>
                    </div><!-- / banner-info -->
                </div><!-- / banner-border -->
            </div><!-- / banner-content -->
        </div>
        <!-- / header-banner -->
    </header>
    <!-- / header -->
    <!-- content -->
    <!-- shop section -->
    <section id="shop" class="space-top-30">
        <div class="container">
        
            <p class="shop-results space-left">

<FORM METHOD="post" action="<%=request.getContextPath()%>/frontend/shop/shopping"  enctype="multipart/form-data"  > 
        <b><font color="deeppink">關鍵字查詢:</font></b>
        <input type="text" name="prod_name">
        <input type="hidden" name="action" value="Fuzzy_Search">
        <input type="submit" value="送出">  
</FORM>	




<FORM METHOD="post" action="<%=request.getContextPath()%>/frontend/shop/shopping"  enctype="multipart/form-data"  >
<jsp:useBean id="prodSvc2" scope="page" class="com.prod.model.ProdService"  />
<b><font color="deeppink">選擇素材:</font></b>
<select  name="ma_no" style="width:100px;font-size:15px; "onchange="submit();" >
<option value="">選擇素材</option>   
   <c:forEach var="prodVO2" items="${prodSvc2.allma}">
   <option value="${prodVO2.ma_no}"> ${prodVO2.ma_name}</option>
   </c:forEach>
  </select>
   <input type="hidden" name="action" value="Ma_Search">     
</FORM>	


	<c:if test="${not empty errorMsgs}">
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>



<ul class="row shop list-unstyled" id="grid">
                <!---------------------------------------------- product ------------------------------------------->
<c:forEach var="prodVO" items="${list}" >
                <li class="col-xs-6 col-md-4 product m-product" data-groups='["mens"]'>
                    <div class="img-bg-color primary">
                        <h5 class="product-price">${prodVO.prod_price}</h5>
                        <a href="single-product.html" class="product-link"></a>
                        <!-- / product-link -->
                        <img src="<%=request.getContextPath()%>/frontend/shop/prod.pic?action=getpic&prod_no=${prodVO.prod_no}" width="200" height="300"  name="prod_pic">
                        <!-- / product-image -->
                        <!-- product-hover-tools -->
<div class="product-hover-tools">
                        
                        

 <div class="product-hover-tools">                     
<form  action="<%=request.getContextPath()%>/frontend/shop/shopping" method="POST" enctype="multipart/form-data">
  <input type="hidden" name="prod_no" value="${prodVO.prod_no}">
   <input type="hidden" name="action" value="getOne_For_Detail">		
    <button type="submit" name="Submit" value="商品詳情"   class="view-btn" data-toggle="tooltip" title="View Product">
    <i class="lnr lnr-eye"></i>
    </button>   
</form>



<form name="shoppingForm" action="<%=request.getContextPath()%>/frontend/shop/cart" method="POST" enctype="multipart/form-data">
       <input type="hidden" name="prod_no" value="${prodVO.prod_no}">
      <input type="hidden" name="prod_name" value="${prodVO.prod_name}">
      <input type="hidden" name="prod_price" value="${prodVO.prod_price}">
       <input type="hidden" name="prod_qty" value= 1 >
      <input type="hidden" name="action" value="ADD">	
        <button type="submit" name="Submit" value="放入購物車"   class="cart-btn" data-toggle="tooltip" title="Add to Cart">
        <i class="lnr lnr-cart"></i>
        </button>   
</form>
 </div>
                                                                   
                            
</div><!------------------------------ / product-hover-tools ------------------------------>
                        <!-- product-details -->
                        <div class="product-details">
                            <h5 class="product-title">${prodVO.prod_name}</h5>
                            <p class="product-category"></p>
                        </div><!-- / product-details -->
                    </div><!-- / img-bg-color -->
                </li>
</c:forEach>
                <!----------------------------------------- / product ----------------------------------------->
                <!-- sizer -->
                <li class="col-xs-6 col-md-4 shuffle_sizer"></li>
                <!-- / sizer -->
            </ul> <!-- / products -->
            <div class="text-center more-button space-top-30">
<!--                 <a href="#x" class="btn btn-default-filled"><i class="lnr lnr-sync"></i><span>LOAD MORE</span></a> -->
            </div>
        </div><!-- / container -->
    </section>
    <!-- / shop section -->
    <!-- / content -->
    <!-- scroll to top -->
    <a href="#top" class="scroll-to-top page-scroll is-hidden" data-nav-status="toggle"><i class="fa fa-angle-up"></i></a>
    <!-- / scroll to top -->
    
 <!--------------------------------------- footer --------------------------------------->
<%@include file="/frontend/bar/frontBarFooter.jsp"%>
<!--------------------------------------- /footer --------------------------------------->

    <!-- javascript -->
    <script src="js/jquery.min.js"></script>
    <!-- sticky nav -->
    <script src="js/jquery.easing.min.js"></script>
    <script src="js/scrolling-nav-sticky.js"></script>
    <!-- / sticky nav -->
    <!-- hide nav -->
    <script src="js/hide-nav.js"></script>
    <!-- / hide nav -->
    <script>
        $(document).ready(function() {
            $('[data-toggle="tooltip"]').tooltip();
        });
    </script>
    <!-- shuffle grid-resizer -->
    <script src="js/jquery.shuffle.min.js"></script>
    <script src="js/custom.js"></script>
    <!-- / shuffle grid-resizer -->
    <!-- preloader -->
    <script src="js/preloader.js"></script>
    <!-- / preloader -->
    <!-- / javascript -->
</body>

</html>