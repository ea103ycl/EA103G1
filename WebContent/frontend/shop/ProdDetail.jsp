<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,java.io.*"%>
<%@ page import="com.prod.model.*"%>
<%@ page import="com.eval.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
ProdVO prodVO = (ProdVO) request.getAttribute("prodVO"); 
%>

<%	
EvalService evalSvc = new EvalService();
List<EvalVO> list_eval = evalSvc.getAllByProd_no(prodVO.getProd_no());
pageContext.setAttribute("list_eval", list_eval);


%>


<!DOCTYPE html>
<html lang="en">

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

<!-- shop single-product -->
<section id="shop">
    <div class="container space-top-30">
        <div class="row">

            <!-- product content area -->
            <div class="col-sm-6 col-md-7 product-content-area">
                <div class="product-content-area">
                    <div id="product-slider" class="carousel slide" data-ride="carousel">
                        <!-- wrapper for slides -->
                        <div class="carousel-inner" role="listbox">
                            <div class="item active">
                                <img src="<%=request.getContextPath()%>/frontend/shop/prod.pic?action=getpic&prod_no=${prodVO.prod_no}" alt="">
                            </div>
                            <div class="item">
                                 <img src="<%=request.getContextPath()%>/frontend/shop/prod.pic?action=getpic&prod_no=${prodVO.prod_no}" alt="">
                            </div>
                            <div class="item">
                                 <img src="<%=request.getContextPath()%>/frontend/shop/prod.pic?action=getpic&prod_no=${prodVO.prod_no}" alt="">
                            </div>
                        </div>
                        <!-- / wrapper for slides -->

                        <!-- controls -->
                        <a class="left carousel-control" href="#product-slider" role="button" data-slide="prev">
                            <span class="lnr lnr-chevron-left" aria-hidden="true"></span>
                        </a>
                        <a class="right carousel-control" href="#product-slider" role="button" data-slide="next">
                            <span class="lnr lnr-chevron-right" aria-hidden="true"></span>
                        </a>
                        <!-- / controls -->
                    </div><!-- / product-slider -->

                    <ul class="nav nav-tabs" role="tablist">
                        <li class="active"><a href="#description" role="tab" data-toggle="tab" aria-expanded="true">商品描述</a></li>
                        
                        <li class=""><a href="#reviews" role="tab" data-toggle="tab" aria-expanded="false">商品評價</a></li>
                    </ul>
                    <!-- / nav-tabs -->
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane animated fadeIn active" id="description">
                            <p></p>
                            <p>${prodVO.prod_detail}</p>
                        </div>
                        <!-- / description-tab -->

                        
                       
                      <!---------------------------  reviews-tab ----------換---------------------------------->
                        <div role="tabpanel" class="tab-pane animated fadeIn" id="reviews">
                            <div class="reviews">
                            
                            
<!--                             / description-tab -->
                        
                   
                    <c:if test="${list_eval.size()==0}">
                                        <font size="5px" color="deeppink">還沒有人評價此商品唷^^"</font>
                      </c:if>
                       <c:forEach var="evalVO" items="${list_eval}" >
                       
                      

                            
                                <div class="review-author pull-left">
                                  <img src="images/author1.jpg" alt="">
                                </div>
                         <!---------------------------------------------------------------------  review-content --------------------------------------------------------->
                                <div class="review-content" style="margin: 0 0 20px 0;">
                                    
                                    <div class="review-stars">
                                    
                                        <span class="product-rating">
                                        <c:if test="${evalVO.eval_rang==1}">
                                         <i class="fa fa-star"></i>
                                         </c:if>
                                        <c:if test="${evalVO.eval_rang==2}">
                                        <i class="fa fa-star"></i><i class="fa fa-star"></i>
                                         </c:if>
                                          <c:if test="${evalVO.eval_rang==3}">       
                                        <i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i>
                                        </c:if>
                                        <c:if test="${evalVO.eval_rang==4}"> 
                                        <i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i>
                                        </c:if>
                                        <c:if test="${evalVO.eval_rang==5}"> 
                                            <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i>
                                       </c:if>
                                        </span>
                                    </div>
                                    <h6 class="review-title no-margin">${evalVO.eval_review}</h6>
                                    <cite> ${evalVO.mem_id} 評價於:</cite>
                                    <cite><fmt:formatDate value="${evalVO.eval_time}" pattern="yyyy-MM-dd HH:mm:ss"/></cite>
                                </div>
                          
                                
                                </c:forEach>
                          <!-------------------------------------------------------- / review-content -------------------------------------------------------------->
                               
                                
                          <!----------------------------------  review-content -------------------------------------------->
                                
                             <!----------------------------- / review-content ---------------------------------------------->

                              <!----------------------------------- add review --------------------------------------------->
                                
                             <!-------------------------------------- / add review ----------------------------------------->
                            </div><!-- / reviews -->
                        </div>
                        <!--------------------------- / reviews-tab -------------------------------------------->
                    </div>
                    <!-- / tab-content -->
                </div><!-- / product-content-area -->

            </div>
            <!-------------------------------------- / product-content-area --------------------------------->


            <!-- product sidebar area -->
            <div class="col-sm-6 col-md-5 product-sidebar">
                <div class="product-sidebar-details">
                    <h4>${prodVO.prod_name}</h4>
                    
                    <div class="product-info">
                        <div class="info">
                            <p><i class="lnr lnr-tag"></i><span>$ ${prodVO.prod_price}</span></p>
                        </div>
                        
                        
                        <div class="info">
                            <p><i class="lnr lnr-star"></i>
                            <span>商品評價
                            
                            <%  if (list_eval.size()==0){ %>
                            <font size="1px" color="deeppink">還沒有人評價過此商品</font>
                             <% } %>
                             
                             
                             <%if (list_eval.size()!=0){
                            	 
                            	 Integer star_total = 0; 
                                 for(EvalVO evalVO2 : list_eval) {
                                 star_total +=  evalVO2.getEval_rang(); 
                                }
                                Integer star_value = star_total / list_eval.size(); 
                            	%> 
                      
                             
                            <%  if ( star_value >0 && star_value <=0.5){ %> 
                            <i class="fa fa-star-half-o"></i>
                            <% } %>
                            <%  if (star_value>0.5 && star_value<=1){ %>
                             <i class="fa fa-star"></i>
                            <% } %>
                            <%  if (star_value >1 && star_value <=1.5){ %>
                             <i class="fa fa-star"></i><i class="fa fa-star-half-o"></i>
                            <% } %>
                            <%  if (star_value >1.5 && star_value<=2){ %>
                            <i class="fa fa-star"></i><i class="fa fa-star"></i>
                            <% } %>
                            <%  if (star_value >2 && star_value <=2.5){ %>
                             <i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star-half-o"></i>
                            <% } %>
                            <%  if (star_value >2.5 && star_value<=3){ %>
                            <i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i>
                            <% } %>
                            <%  if (star_value >3 && star_value <=3.5){ %>
                             <i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star-half-o"></i>
                            <% } %>
                            <%  if (star_value >3.5 && star_value<=4){ %>
                             <i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i>
                            <% } %>
                            <%  if (star_value >4 && star_value <=4.5){ %>
                              <i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star-half-o"></i>
                            <% } %>
                             <%  if (star_value >4.5 && star_value <=5){ %>
                              <i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i>
                            <% } %>
                            <% } %>
                            </span>
                            
                            </p>
                        </div>
                    </div><!-- / product-info -->

<!-------------------------------------- / 下拉 --------------------------------->
                    
 <div>                  
<form  action="<%=request.getContextPath()%>/frontend/shop/shopping" method="POST" enctype="multipart/form-data"> 
 <jsp:useBean id="prodSvc2" scope="page" class="com.prod.model.ProdService"  />
<b>看看其他素材:</b>
<select  name="ma_no" style="width:100px;font-size:15px; "onchange="submit();" >
<option value="">選擇素材</option>  
   <c:forEach var="prodVO2" items="${prodSvc2.allma}"> 
   <option value="${prodVO2.ma_no}"> ${prodVO2.ma_name}</option>
   </c:forEach>
   </select>
        <input type="hidden" name="prod_no" value="${prodVO.prod_no}">
        <input type="hidden" name="ptr_no" value="${prodVO.ptr_no}">
        <input type="hidden" name="action" value="Other_Ma_Search">
</form>	
</div>                  
                    
<form name="shoppingForm" action="<%=request.getContextPath()%>/frontend/shop/cart" method="POST" enctype="multipart/form-data">                  
        
        <div class="buy-product">
        <div class="options">
        <b>選擇要買的數量:</b>
        <input name="prod_qty" type="number" step="1" min="0"  value="1" title="Qty" class="input-text qty text" size="1">    
         </div>
		 <!-- / options -->
		
		   <div class="space-25">&nbsp;</div>
		
<!-- 		   <a href="shopping-cart.html" class="btn btn-primary-filled btn-rounded"><i class="lnr lnr-cart"></i><span> Add to Cart</span></a> -->
<!-- 		   <a href="checkout.html" class="btn btn-success-filled btn-rounded"><i class="lnr lnr-heart"></i><span> Buy Now</span></a> -->
                        
                        
                        
      <input type="hidden" name="prod_no" value="${prodVO.prod_no}">
      <input type="hidden" name="prod_name" value="${prodVO.prod_name}">
      <input type="hidden" name="prod_price" value="${prodVO.prod_price}">
      <input type="hidden" name="action" value="ADD">	
       <button type="submit" name="Submit" value="放入購物車" class="btn btn-primary-filled btn-rounded"><i class="lnr lnr-cart"></i><span> 加入購物車</span>>
        </div>
       </form>
       

    <c:if test="${not empty errorMsgs}">
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>              
 </div>
<!-------------------------------------- / 加入購物車 --------------------------------->
                </div><!-- product-details -->

            </div><!-- / col-sm-4 col-md-3 -->
            <!-- / product sidebar area -->

        </div><!-- / row -->
<!-------------------------------------- / 相關作品????? --------------------------------->
        <div id="related-products">
            <h4 class="space-top-30 space-bottom-30 space-left">你可能也感興趣的商品</h4>
            <ul class="row shop list-unstyled" id="grid">
                
<!----------------------------------------------- Related-product ------------------------------------------>
<%	
ProdService prodSvc3 = new ProdService();
List<ProdVO> list3 = prodSvc3.getRelatedProd(prodVO.getPtr_no());
pageContext.setAttribute("list_RelatedProd", list3);
%>
                <c:forEach var="prodVO3" items="${list_RelatedProd}" >
                <li class="col-xs-6 col-md-4 product m-product" data-groups='["mens"]'>
                    <div class="img-bg-color primary">
                        <h5 class="product-price">${prodVO3.prod_price}</h5>
                        <a href="single-product.html" class="product-link"></a>
                        <!-- / product-link -->
                        <img src="<%=request.getContextPath()%>/frontend/shop/prod.pic?action=getpic&prod_no=${prodVO3.prod_no}" width="200" height="300"  name="prod_pic">
                        <!-- / product-image -->
                        <!-- product-hover-tools -->
<div class="product-hover-tools">
                        
                        

 <div class="product-hover-tools">                     
<form  action="<%=request.getContextPath()%>/frontend/shop/shopping" method="POST" enctype="multipart/form-data">
  <input type="hidden" name="prod_no" value="${prodVO3.prod_no}">
   <input type="hidden" name="action" value="getOne_For_Detail">		
    <button type="submit" name="Submit" value="商品詳情"   class="view-btn" data-toggle="tooltip" title="View Product">
    <i class="lnr lnr-eye"></i>
    </button>   
</form>



<form name="shoppingForm" action="<%=request.getContextPath()%>/frontend/shop/cart" method="POST" enctype="multipart/form-data">
       <input type="hidden" name="prod_no" value="${prodVO3.prod_no}">
      <input type="hidden" name="prod_name" value="${prodVO3.prod_name}">
      <input type="hidden" name="prod_price" value="${prodVO3.prod_price}">
       <input type="hidden" name="prod_qty" value= 1 >
      <input type="hidden" name="action" value="ADD">	
        <button type="submit" name="Submit" value="放入購物車"   class="cart-btn" data-toggle="tooltip" title="Add to Cart">
        <i class="lnr lnr-cart"></i>
        </button>   
</form>
 </div>
                                                                   
                            
</div>
<!------------------------------------------- / product-hover-tools ----------------------------------------->
                        <!-- product-details -->
                        <div class="product-details">
                            <h5 class="product-title">${prodVO3.prod_name}</h5>
                            <p class="product-category"></p>
                        </div><!-- / product-details -->
                    </div><!-- / img-bg-color -->
                </li>
</c:forEach>
                <!---------------------------------------------- /Related-product ----------------------------------->

                

            </ul><!-- / row -->
        </div><!-- / related-products -->

    </div><!-- / container -->
</section>
<!-- / shop single-product -->

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