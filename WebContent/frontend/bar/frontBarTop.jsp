<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
	<head>

		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<!-- favicon -->
		<link rel="icon" href="<%=request.getContextPath()%>/frontend/template/images/favicon.png">
		
		<!-- bootstrap css -->
		<link href="<%=request.getContextPath()%>/frontend/template/css/bootstrap.min.css" rel="stylesheet">
		
		<!-- css -->
		<link href="<%=request.getContextPath()%>/frontend/template/css/style.css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/frontend/template/css/animate.css" rel="stylesheet">
		
		<!-- fonts -->
		<link href="https://fonts.googleapis.com/css?family=Rubik:400,500,700,900" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Lily+Script+One" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/frontend/template/css/font-awesome.min.css" rel="stylesheet" type="text/css">
		<link href='<%=request.getContextPath()%>/frontend/template/fonts/FontAwesome.otf' rel='stylesheet' type='text/css'>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/template/css/linear-icons.css">
		
		<!-- other css -->
		<style>
			.ycl-topBar-second{
				display:inline-block;
			}
			
			.ycl-topBar-cartCntText{
				margin-left:1em !important;
				font-size:10px !important;
			}
		</style>
		
				
		<!-- page title -->
</head>
	<body>
	
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
                <a class="navbar-brand" href="index.html"><img src="images/logo.png" alt="logo"></a>
            </div><!-- / navbar-header -->
            	
            <div class="secondary-nav" style="display:inline-block;">

            	<!-- 購物車 -->
            	<li class="dropdown ycl-topBar-second">
                    <a href="#" class="shopping-cart space-right dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-shopping-cart"></i><span class="cart-badge">5</span></a>
                    <ul class="dropdown-menu animated zoomIn fast">
                        <li><a href="<%=request.getContextPath()%>/frontend/shop/Cart.jsp"><span>一般商品</span><span class="text-primary ycl-topBar-cartCntText" id="cartCnt1">30</span></a></li>
                        <li><a href="<%=request.getContextPath()%>/frontend/preproduct/shoppingCart.jsp"><span>預購商品</span><span class="text-primary ycl-topBar-cartCntText" id="cartCnt2">2</span></a></li>
                    </ul>
                </li>

            	<!-- 會員 -->
            	<li class="dropdown ycl-topBar-second">
                    <a href="#" class="my-account space-right dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-user"></i></a>
                    <ul class="dropdown-menu animated zoomIn fast">
                    	<c:choose>
                    		<c:when test="${empty sessionScope.memVO}">
                    			<li><a href="<%=request.getContextPath()%>/frontend/members/memLogin.jsp"><span>登入</span></a></li>
                    		</c:when>
                    		<c:otherwise>
                    			<li><a href="<%=request.getContextPath()%>/frontend/members/memArea.jsp"><span>設定</span></a></li>
                        		<li><a href="<%=request.getContextPath()%>/frontend/members/memLoginHandler.do?action=logout"><span>登出</span></a></li>
                        	</c:otherwise>
                    	</c:choose>

                    </ul>
                </li>
                
            </div>
            
            <div class="navbar-collapse collapse text-center">
                <ul class="nav navbar-nav">
                	
                    <li class=""><a href="index.html"><span>首頁</span></a></li>
                    
                    <!-- 社群 -->
                    <li><a href="<%=request.getContextPath()%>/frontend/painter/listAllPainterFollow.jsp"><span>社群</span></a></li>
                    
                    <!-- 一般購買 -->
                    <li><a href="<%=request.getContextPath()%>/frontend/shop/EShop.jsp"><span>商城</span></a></li>
                    
                    <!-- 創意主題競賽 -->
                    <li><a href="<%=request.getContextPath()%>/frontend/event_p/event_homePage.jsp"><span>主題競賽</span></a></li>

                    <!-- 預購 -->
                    <li><a href="<%=request.getContextPath()%>/frontend/preproduct/frontindex.jsp"><span>預購</span></a></li>

                    <!-- 競標 -->
                    <li><a href="<%=request.getContextPath()%>/frontend/biddingFront/biddingPage.jsp"><span>競標</span></a></li>

                    <!-- 見面會 -->
                    <li><a href="<%=request.getContextPath()%>/frontend/biddingFront/biddingPage.jsp"><span>見面會</span></a></li>


                    <!-- ------------------------------------------------------------------ -->

<!--                     備用 -->
<!--                     <li class="dropdown"> -->
<!-- 	                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span>預購</span> <span class="dropdown-icon"></span></a> -->
<!-- 	                    <ul class="dropdown-menu animated zoomIn fast"> -->
<!-- 	                        <li><a href="blog.html"><span>備用</span></a></li> -->
<!-- 	                        <li><a href="single-post.html"><span>備用</span></a></li> -->
<!-- 	                    </ul> -->
<!--                     </li> -->
                    
                    
                </ul>
            </div><!--/ nav-collapse -->
        </div><!-- / container -->
    </nav>
    <!-- / nav -->
    
    <!-- header-banner -->
    <div id="header-banner" style="margin-top:60px;"></div>
    <!-- / header-banner -->
	
	</body>
</html>