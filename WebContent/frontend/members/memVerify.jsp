<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<!DOCTYPE html>
<html lang="en">


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
    <!-- header-banner -->
    <div id="header-banner">
        <div class="banner-content single-page text-center" style="padding-bottom:30px ;padding-top:50px">
            <div class="banner-border">
                <div class="banner-info" style="padding: 0px">
                    <div id="login-form" style="padding-bottom:0px">
                        <h3 class="log-title">設定密碼</h3>
                        <div style="margin-bottom:40px">

                            <img src="images/login.png" style="width: 100px">
                        </div>
                        
                        
                        <div class="form-group">
              
                           <p id="intro" style="margin-bottom:15px">請輸入您的新密碼</p>
                            <input type="text" class="form-control" id="password1" placeholder="新密碼" required>
                            <div class="help-block with-errors"></div>
                              <input type="text" class="form-control" id="password2" placeholder="再次輸入新密碼" required>
                            <div class="help-block with-errors"></div>
                              <button id="submitBtn" class="btn btn-md btn-primary-filled btn-log btn-rounded"
									type="button" style="width:100%">
								提交
								</button>
                      
                        </div>
    
                   
                    </div>
                                 
     
                </div><!-- / banner-info -->
                 
            </div><!-- / banner-border -->      
             </div><!-- / banner-content -->
               <div class="alert alert-success alert-dismissible" role="alert" id="alertSuccess" style="display: none;">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>密碼重設成功！</strong> 下次登入請使用新密碼或<a href="memLogin.jsp" style="color:white; text-decoration:underline;">重新登入</a>
                </div>
                  <div class="alert alert-warning alert-dismissible" role="alert" id="alertWarn" style="display: none;">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>密碼設定失敗</strong> 請檢查密碼格式是否正確或密碼是否一致
                </div>
               
                
  
    </div>
    
   
    
    
    
    
    
    
    
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