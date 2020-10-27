<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<title>front_index</title>

<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">
<!-- Vendor CSS Files -->
<link href="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/boxicons/css/boxicons.min.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/venobox/venobox.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/owl.carousel/assets/owl.carousel.min.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/aos/aos.css" rel="stylesheet">
<!-- Template Main CSS File -->
<link href="<%=request.getContextPath()%>/frontend/front_index/assets/css/style.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/frontend/front_index/css/masonry.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/frontend/front_index/css/index_front_css.css" rel="stylesheet">
<!-- =======================================================
  * Template Name: iPortfolio - v1.4.0
  * Template URL: https://bootstrapmade.com/iportfolio-bootstrap-portfolio-websites-template/
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->
</head>

<body>
	<jsp:useBean id="pSvc" scope="page"
		class="com.painter.model.PainterService" />
	<jsp:useBean id="ptSvc" scope="page"
		class="com.painter_tag.model.PainterTagService" />
	<jsp:useBean id="ptmSvc" scope="page"
		class="com.painter_tag_map.model.PainterTagMapService" />
	<!-- ======= Mobile nav toggle button ======= -->
	<button type="button" class="mobile-nav-toggle d-xl-none">
		<i class="icofont-navigation-menu"></i>
	</button>
	<!-- ======= Header ======= -->
	<header id="header">
		<div class="d-flex flex-column">
			<div class="profile">
				<img src="<%=request.getContextPath()%>/frontend/front_index/imgData/img (12).jpg" alt=""
					class="img-fluid rounded-circle">
				<h1 class="text-light">
					<a href="index.html">Alex Smith</a>
				</h1>
				<div class="social-links mt-3 text-center">
					<a href="#" class="twitter"><i class="bx bxl-twitter"></i></a> <a
						href="#" class="facebook"><i class="bx bxl-facebook"></i></a> <a
						href="#" class="instagram"><i class="bx bxl-instagram"></i></a> <a
						href="#" class="google-plus"><i class="bx bxl-skype"></i></a> <a
						href="#" class="linkedin"><i class="bx bxl-linkedin"></i></a>
				</div>
			</div>
			<nav class="nav-menu">
				<ul>
					<li><a href="index.html"><i class="bx bx-home"></i> <span>Home</span></a></li>
					<li><a href="#about"><i class="bx bx-user"></i> <span>About</span></a></li>
					<li><a href="#resume"><i class="bx bx-file-blank"></i> <span>Resume</span></a></li>
					<li><a href="#portfolio"><i class="bx bx-book-content"></i>
							Portfolio</a></li>
					<li><a href="#services"><i class="bx bx-server"></i>
							Services</a></li>
					<li><a href="#contact"><i class="bx bx-envelope"></i>
							Contact</a></li>
				</ul>
			</nav>
			<!-- .nav-menu -->
			<button type="button" class="mobile-nav-toggle ">
				<i class="icofont-navigation-menu"></i>
			</button>
		</div>
	</header>
	<!-- End Header -->
	<main id="main">
		<!-- ======= Breadcrumbs ======= -->
		<section class="breadcrumbs">
			<div class="container">
				<div class="d-flex justify-content-between align-items-center">
					<h2>Inner Page</h2>
					<ol>
						<li><a href="index.html">Home</a></li>
						<li>Inner Page</li>
					</ol>
				</div>
			</div>
		</section>
		<!-- End Breadcrumbs -->
		<section class="inner-page innerpage" style="padding-top: 30px;">
			<div id=topWrapper>
				<div id=fixedTop>
					<div id="logoBlock">
						<div id="leftHeader">
							<div class="LH-inner">
								<a href="">
									<div class="">創意主題競賽</div>
								</a>
							</div>
							<div class="LH-inner">
								<a href="">
									<div class="">競標</div>
								</a>
							</div>
							<div class="LH-inner">
								<a href="">
									<div class="">商城</div>
								</a>
							</div>
						</div>
						<div id="logo">
							<div id="title1">ARTS</div>
							<div id="title2">BLOCK</div>
						</div>
						<div id="rightHeader">
							<div class="RH-inner">
								<div id="SBA-svg-1">
									<span>page1</span>
								</div>
							</div>
							<div class="RH-inner">
								<div id="SBA-svg-1">
									<span>page2</span>
								</div>
							</div>
							<div class="RH-inner">
								<div id="SBA-svg-1">
									<span>page3</span>
								</div>
							</div>
						</div>
					</div>
					<!-- ====searchBlcok================== -->
					<div id="searchBlock">
						<div id="searchBlockMid">
							<form id="searchForm1" method="post"
								action="<%=request.getContextPath()%>/painter/TagGetPic" name="searchForm">
								<div>
									<input type="hidden" name="action" value="tagGetPic"> 
									<input id="searchBar1" type="text" name="searchBar1"
										placeholder="Search">
								</div>
							</form>
							<ul id="searchlistBox">
								<li class="searchList">
									<div>
										<div>#</div>
										<span> words here</span>
									</div>
								</li>
								<li class="searchList">
									<div>
										<div>#</div>
										<span> words here2</span>
									</div>
								</li>
							</ul>
						</div>
					</div>
					<!-- ====searchBlcok================== -->
				</div>
			</div>
			<!-- FixedTopEnd -->
			<div class=" container my-container">
				<!-- ================================= -->
				<!-- FixedTop -->
				<!-- ================================================================================================================ -->
				<div id="bubbleWrapper">
					<div class="bubble" id="bubble1">
						<span></span>
					</div>
					<div class="bubble" id="bubble2">
						<span></span>
					</div>
					<div class="bubble" id="bubble3">
						<span></span>
					</div>
					<div class="bubble" id="bubble4">
						<span></span>
					</div>
					<div class="bubble" id="bubble5">
						<span></span>
					</div>
					<div class="bubble" id="bubble6">
						<span></span>
					</div>
					<div class="bubble" id="bubble7">
						<span></span>
					</div>
					<div class="bubble" id="bubble8">
						<span></span>
					</div>
					<div class="bubble" id="bubble9">
						<span></span>
					</div>
					<div class="bubble" id="bubble10">
						<span></span>
					</div>
					<div class="bubble" id="bubble11">
						<span></span>
					</div>
					<div class="bubble" id="bubble12">
						<span></span>
					</div>
					<div id="BigWhiteDot"></div>
				</div>


				<%-- ============================================= --%>
				<c:if test="${not empty errorMsgs}">
					<br>
					<div class="errorMsgs" id="errorMsgs"><h2>${errorMsgs}</h2></div>
					<hr>
				</c:if>
				<c:choose>
					<c:when test="${not empty ptrnoList}">
					<div style="display:hidden" id="getPicResult"></div>
						<div class="gridWrapper">
							<div class="grid" id="grid">
								<c:forEach var="ptrno" items="${ptrnoList}">
									<div class="grid-item">
<%-- 										<img src="<%=request.getContextPath()%>/painter/ShowImage?ptr_no=${ptrno}"> --%>
										<img src="<%=request.getContextPath()%>/painter/painter.do?action=showPic&ptr_no=${ptrno}">
									</div>
								</c:forEach>
							</div>
						</div>
					</c:when>
					<%-- ==============Top100Liked===================== --%>
					<c:otherwise>
						<div class="gridWrapper">
							<div class="grid" id="grid">
								<c:forEach var="pVO" items="${pSvc.getMostLiked(1,7)}">
									<div class="grid-item">
<%-- 										<img src="<%=request.getContextPath()%>/painter/ShowImage?ptr_no=${pVO.ptr_no}"> --%>
										<img src="<%=request.getContextPath()%>/painter/painter.do?action=showPic&ptr_no=${pVO.ptr_no}">
									</div>
								</c:forEach>
							</div>
						</div>
						<div id="lastDiv"></div>
					</c:otherwise>
				</c:choose>




				<!-- ================================= -->
			</div>
		</section>
	</main>
	<!-- End #main -->
	<a href="#" class="back-to-top"><i class="icofont-simple-up"></i></a>
	<!-- Vendor JS Files -->
	<script src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/jquery/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/jquery.easing/jquery.easing.min.js"></script>
	<script src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/php-email-form/validate.js"></script>
	<script src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/waypoints/jquery.waypoints.min.js"></script>
	<script src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/counterup/counterup.min.js"></script>
	<script src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/isotope-layout/isotope.pkgd.min.js"></script>
	<script src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/venobox/venobox.min.js"></script>
	<script src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/owl.carousel/owl.carousel.min.js"></script>
	<script src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/typed.js/typed.min.js"></script>
	<script src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/aos/aos.js"></script>
	<script src="<%=request.getContextPath()%>/frontend/front_index/js/masonry.js"></script>
	<!-- Template Main JS File -->
	<script src="<%=request.getContextPath()%>/frontend/front_index/assets/js/main.js"></script>
	<script src="<%=request.getContextPath()%>/frontend/front_index/js/index_front_js.js"></script>
</body>

</html>