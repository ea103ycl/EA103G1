<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<!DOCTYPE html>
<html lang="en">
<%
	MemVO memVO = (MemVO) request.getAttribute("memVO");
%>

<head>

		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
	
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
    
    <%@include file="/frontend/bar/frontBarTop.jsp"%>
      
    </header>
    <!-- / header -->




<!-- content -->

<div id="page-content" class="container" style="height:90%">
    <div class="pnf-content text-center" style="margin-top:100px">
        <h2>WAIT...THE LAST STEP</h2>
        <p class="lead"><strong>請至您的電子信箱收取驗證信, 即可開始探索ArtsBlock！</strong></p>
        
       
        <p><a href="<%=request.getContextPath()%>/frontend/members/mem.do?action=sendVerifyAgain" class="btn btn-lg btn-primary-filled btn-rounded"><span>收不到驗證信? 點此重新發送</span></a></p>
        <h6>OR</h6>
        <p><a href="<%=request.getContextPath()%>/frontend/front_index.jsp" class="btn btn-lg btn-default-filled btn-rounded"><i class="fa fa-long-arrow-right"></i><span>先逛逛首頁</span></a></p>
    </div>
</div><!-- / container -->

<!-- / content -->

<!-- scroll to top -->
<a href="#top" class="scroll-to-top page-scroll is-hidden" data-nav-status="toggle"><i class="fa fa-angle-up"></i></a>
<!-- / scroll to top -->
   
    
    
    
    
    
    
    
    <!-- / content -->
  
    <!-- footer -->
    <footer class="light-footer">
        <div class="widget-area">
            <div class="container">
                <div class="row">
                    <div class="col-md-4 widget">
                        <div class="about-widget">
                            <div class="widget-title-image">
                                <img src="images/logo2.png" alt="">
                            </div>
                            <p>Vivamus consequat lacus quam, nec egestas quam egestas sit amet. Suspendisse et risus gravida tellus aliquam ullamcorper. Pellentesque elit dolor, ornare ut lorem nec, convallis nibh accumsan lacus morbi leo lipsum.</p>
                        </div><!-- / about-widget -->
                    </div><!-- / widget -->
                    <!-- / first widget -->
                    <div class="col-md-2 widget">
                        <div class="widget-title">
                            <h4>BRANDS</h4>
                        </div>
                        <div class="link-widget">
                            <div class="info">
                                <a href="#x">Brand 1</a>
                            </div>
                            <div class="info">
                                <a href="#x">Brand 2</a>
                            </div>
                            <div class="info">
                                <a href="#x">Brand 3</a>
                            </div>
                            <div class="info">
                                <a href="#x">Brand 4</a>
                            </div>
                        </div>
                    </div><!-- / widget -->
                    <!-- / second widget -->
                    <div class="col-md-2 widget">
                        <div class="widget-title">
                            <h4>SUPPORT</h4>
                        </div>
                        <div class="link-widget">
                            <div class="info">
                                <a href="#x">Terms  Conditions</a>
                            </div>
                            <div class="info">
                                <a href="#x">Shipping  Return</a>
                            </div>
                            <div class="info">
                                <a href="faq.html">F.A.Q</a>
                            </div>
                            <div class="info">
                                <a href="contact.html">Contact</a>
                            </div>
                        </div>
                    </div><!-- / widget -->
                    <!-- / third widget -->
                    <div class="col-md-4 widget">
                        <div class="widget-title">
                            <h4>CONTACT</h4>
                        </div>
                        <div class="contact-widget">
                            <div class="info">
                                <p><i class="lnr lnr-map-marker"></i><span>Miami, S Miami Ave, SW 20th, Store No.1</span></p>
                            </div>
                            <div class="info">
                                <a href="tel:+0123456789"><i class="lnr lnr-phone-handset"></i><span>+0123 456 789</span></a>
                            </div>
                            <div class="info">
                                <a href="mailto:hello@yoursite.com"><i class="lnr lnr-envelope"></i><span>office@yoursite.com</span></a>
                            </div>
                            <div class="info">
                                <i class="lnr lnr-thumbs-up"></i>
                                <span class="social text-left">
                                    <a class="no-margin" href="#"><i class="fa fa-facebook"></i></a>
                                    <a href="#"><i class="fa fa-twitter"></i></a>
                                    <a href="#"><i class="fa fa-google-plus"></i></a>
                                    <a href="#"><i class="fa fa-linkedin"></i></a>
                                    <a href="#"><i class="fa fa-pinterest"></i></a>
                                </span>
                            </div>
                        </div><!-- / contact-widget -->
                    </div><!-- / widget -->
                    <!-- / fourth widget -->
                </div><!-- / row -->
            </div><!-- / container -->
        </div><!-- / widget-area -->
        <div class="footer-info">
            <div class="container">
                <div class="pull-left copyright">
                    <p><strong>© MS - MINIMAL SHOP THEME</strong></p>
                </div>
                <span class="pull-right">
                    <img src="images/visa.png" alt="">
                    <img src="images/mastercard.png" alt="">
                    <img src="images/discover.png" alt="">
                    <img src="images/paypal.png" alt="">
                </span>
            </div><!-- / container -->
        </div><!-- / footer-info -->
    </footer>
    <!-- / footer -->
	<%@include file="/frontend/bar/frontBarFooter.jsp"%>

<script>

$("#submitBtn").click(function(){

	
	$("#submitBtn").text("更改中...");
	$("#submitBtn").prop("disabled",true);
	
	$.ajax({
		url: "<%=request.getContextPath()%>/frontend/members/memLoginHandler.do",
								type : "POST",
								data : {
									action : "changePwdfromEmail",
									newPassword1 : $("#password1").val(),
									newPassword2 : $("#password2").val(),
									
			
									
								},
								
								success : function(data) {
									
									console.log(data);

									if (data === 'true'){
										$("#submitBtn").text("提交");
										$("#submitBtn").prop("disabled",false);
										$('#alertWarn').css("display", "none");
									$('#alertSuccess').css("display", "block");
								
									
									}
									
									else{
										$("#submitBtn").text("提交");
										$("#submitBtn").prop("disabled",false);
										$('#alertSuccess').css("display", "none");
										$('#alertWarn').css("display", "block");
									}
										
									}
								
							});
				});


</script>

</body>

</html>