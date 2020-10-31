<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="java.util.* , com.prod.model.ProdVO" %>

<!DOCTYPE html>
<html lang="en">

<%
  Integer or_total =0;
  Integer count=0;
  Vector<ProdVO> buylist = (Vector<ProdVO>) session.getAttribute("shoppingcart");
                if (buylist != null && (buylist.size() > 0)) {
				for (int index = 0; index < buylist.size(); index++) {
				count = (index+1);
				                     }
	                                 }
%>
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Minimal Shop Theme">
<meta name="keywords" content="responsive, retina ready, html5, css3, shop, market, onli store, bootstrap theme" />
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
    <!------------------------------------ / nav -------------------------------------------->
        

    <!-- header-banner -->
    <div id="header-banner">
        <div class="banner-content single-page text-center">
            <div class="banner-border">
                <div class="banner-info">
                    <h1>Shopping Cart</h1>
                </div><!-- / banner-info -->
            </div><!-- / banner-border -->
        </div><!-- / banner-content -->
    </div>
    <!-- / header-banner -->
</header>
<!-- / header -->

<!-- content -->

<!-- shopping-cart -->
<div id="shopping-cart">
    <div class="container">
    
    
       
        <!-------------------------------------- shopping cart table ------------------------------------>
   
        
        
<%
if (buylist != null && (buylist.size() > 0)) {
%> 
    


        <table class="shopping-cart">
            <thead>
                <tr>
                    <th class="image">&nbsp;</th>
                    <th>商品</th>
                    <th>商品價格</th>
                    <th>購買數量</th>
                    <th>總金額</th>
                    <th>&nbsp;</th>
                </tr>
            </thead>

<%
for (int index = 0; index < buylist.size(); index++) {
	ProdVO order = buylist.get(index);
    int prod_qty = order.getProd_qty();
	int prod_price = order.getProd_price();
	int prod_no = order.getProd_no();
	 or_total += prod_qty * prod_price;
	 
%>
            
            <tbody>
                <tr class="cart-item">
 <td class="image">
<form  action="<%=request.getContextPath()%>/frontend/shop/shopping" method="POST" enctype="multipart/form-data">
  <input type="hidden" name="prod_no" value="<%=order.getProd_no()%>">
  <input type="hidden" name="action" value="getOne_For_Detail">		
  <button type="submit" name="Submit" value="商品詳情"   class="view-btn" data-toggle="tooltip" title="View Product" style="width:60px;height:60px;">
  <img src="<%=request.getContextPath()%>/frontend/shop/prod.pic?action=getpic&prod_no=<%=order.getProd_no()%>"  alt="">
  </button>   
</form>             
 </td>
                    <td><%=order.getProd_name()%></a></td>
                    <td><%=order.getProd_price()%></td>
                    <td class="qty"><%=order.getProd_qty()%></td>
                    <td><%=order.getProd_price()*order.getProd_qty()%></td>
                    
                    
              <td>
              <form name="deleteForm" action="<%=request.getContextPath()%>/frontend/shop/cart" method="POST">
              <input type="hidden" name="action" value="DELETE">
              <input type="hidden" name="del" value="<%=index %>">
              <input type="submit" value="X" class="btn btn-danger-filled x-remove">
              </form>
              </td>
             
                </tr>
                 
<%}%> 

            </tbody>
        </table>
<%}%>  
    
    
        
        <!-- / shopping cart table -->

        <div class="row cart-footer">
            <div class="coupon col-sm-6">
            
            
<FORM METHOD="post" action="<%=request.getContextPath()%>/frontend/shop/shopping"  enctype="multipart/form-data"  > 
                <div class="input-group">
                    <input type="text" name="prod_name" class="form-control rounded" id="coupon-code" placeholder="輸入你想找的商品">
                    <span class="input-group-btn">
                    <input type="hidden" name="action" value="Fuzzy_Search">
                    <input type="submit" value="再找找你想要的商品"  class="btn btn-primary-filled btn-rounded" type="button"><i class="lnr lnr-tag"></i> 
                    <span>再找找想要的商品</span>>  
                    </span>
</FORM>	 
                </div>
            
       
        
        

                
                
                
            </div><!-- / input-group -->
            <div class="update-cart col-sm-6">
            
            <form  action="<%=request.getContextPath()%>/frontend/shop/shopping" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="action" value="Clear_Cart">
            <button  type="submit" name="Submit" value="清空購物車" class="btn btn-default-filled btn-rounded" type="button"><i class="lnr lnr-sync"></i> <span>清空購物車</span></button>
            </form>
            </div><!-- / update-cart -->



            <div class="col-sm-6 cart-total">
                <h4>此次消費總金額:</h4>
                <h3 style="color:deeppink;">NT$ <%=or_total%></h3>
              
            </div><!-- / cart-total -->

            <div class="col-sm-6 cart-checkout">
                <a href="EShop.jsp" class="btn btn-default-filled btn-rounded"><i class="lnr lnr-cart"></i> <span>再逛逛</span></a>
                <a href="Checkout.jsp" class="btn btn-primary-filled btn-rounded"><i class="lnr lnr-exit"></i> <span>結帳去</span></a>
            </div><!-- / cart-checkout -->


        </div><!-- / row -->
    </div><!-- / container -->
</div>
<!-- / shopping-cart -->

<!-- / content -->

<!-- scroll to top -->
<a href="#top" class="scroll-to-top page-scroll is-hidden" data-nav-status="toggle"><i class="fa fa-angle-up"></i></a>
<!-- / scroll to top -->

<!--------------------------------------- footer --------------------------------------->
<%@include file="/frontend/bar/frontBarFooter.jsp"%>
<!--------------------------------------- /footer --------------------------------------->

<!-- javascript -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<!-- sticky nav -->
<script src="js/jquery.easing.min.js"></script>
<script src="js/scrolling-nav-sticky.js"></script>
<!-- / sticky nav -->

<!-- hide nav -->
<script src="js/hide-nav.js"></script>
<!-- / hide nav -->

<!-- preloader -->
<script src="js/preloader.js"></script>
<!-- / preloader -->

<!-- / javascript -->
</body>

</html>