<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    
    <title>ArtsBlock 後台管理系統</title>
    
    <!-- Custom fonts for this template -->
    <link href="<%=request.getContextPath()%>/backend/template/css/sb-admin-2.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
    <!-- Custom styles for this page -->
    <link href="<%=request.getContextPath()%>/backend/template/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/backend/template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <style type="text/css">/*瑞龍:此處為手動改CSS的地方*/
        .dataTables_wrapper label {
            
        }
        .img01{
        height: 75px;
        }
        .pagination
        {
        	display: none;
        }
        div.dataTables_wrapper div.dataTables_length label
        {
        	display: none;
        }


    </style>
</head>

<body id="page-top" style=" background-image: linear-gradient(to bottom, #D0D0D0, #D0D0D0); background-position: top left;  background-size: 100%;  background-repeat: repeat;">
   
        <!-- =========================================== Sidebar START ===========================================  -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar" style=" background-image: linear-gradient(to bottom, rgba(0,0,0,0.2), #EA7500); background-position: top left;  background-size: 100%;  background-repeat: repeat;">
                
            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">
                <div class="sidebar-brand-icon rotate-n-15">
                    <i class="fas fa-palette"></i>
                </div>
                <div class="sidebar-brand-text mx-3">ArtsBlock<sup></sup></div>
            </a>
            
            <!-- Divider -->
            <hr class="sidebar-divider my-0">
            
            <!-- Nav Item - Dashboard -->
            <li class="nav-item active">
                <a class="nav-link" href="#">
                    <i class="fas fa-fw fa-tachometer-alt"></i>
                    <span>控制台</span></a>
            </li>
            
            <!-- Divider -->
            <hr class="sidebar-divider">
            
            <!-- Heading -->
            <div class="sidebar-heading"> 社群 Community </div>
            
            <!-- Nav Item - Pages Collapse Menu -->
            <!-- 瑞龍:注意collapseOne -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
                    <i class="fas fa-users"></i>
                    <span>會員管理</span>
                </a>
                <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">會員系統:</h6>
                        <a class="collapse-item" href="#">會員權限</a>
                        <a class="collapse-item" href="#">會員查詢</a>
                        <a class="collapse-item" href="#">里程碑</a>
                        <a class="collapse-item" href="#">交易紀錄</a>
                    </div>
                </div>
            </li>
            
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                    <i class="fas fa-flag-checkered"></i>
                    <span>創意主題競賽</span>
                </a>
                <div id="collapseOne" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">主題競賽系統:</h6>
                        <a class="collapse-item" href="#">創意主題管理</a>
                        <a class="collapse-item" href="#">參賽作品管理</a>
                    </div>
                </div>
            </li>
            
            <!-- Divider -->
            <hr class="sidebar-divider">
            
            <!-- Heading -->
            <div class="sidebar-heading"> 商城 Commerce </div>
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse3" aria-expanded="true" aria-controls="collapse3">
                    <i class="fas fa-gavel"></i>
                    <span>限時競標管理</span>
                </a>
                <div id="collapse3" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">限時競標系統:</h6>
                        <a class="collapse-item" href="#">競標商品管理</a>
                        <a class="collapse-item" href="#">競標案件查詢</a>
                    </div>
                </div>
            </li>
            
            <!-- Nav Item - Tables -->
            <li class="nav-item">
                <a class="nav-link" href="javascript:window.location.href='preproduct.jsp'"><!-- 瑞龍:我的預購JSP頁面跳轉 -->
                    <i class="fas fa-store"></i>
                    <span>預購商品管理</span></a>
            </li>
            
            <!-- Nav Item - Tables -->
            <li class="nav-item">
                <a class="nav-link" href="#"> 
                    <i class="fas fa-gifts"></i>
                    <span>商品管理</span></a>
            </li>
            
            <!-- Nav Item - Tables -->
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <i class="fas fa-tshirt"></i>
                    <span>素材管理</span></a>
            </li>
            
            <!-- Nav Item - Tables -->
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <i class="far fa-calendar-alt"></i>
                    <span>見面會管理</span></a>
            </li>
            
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse4" aria-expanded="true" aria-controls="collapse4">
                    <i class="fas fa-file-invoice-dollar"></i>
                    <span>訂單管理</span>
                </a>
                <div id="collapse4" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">所有訂單系統:</h6>
                        <a class="collapse-item" href="#">競標訂單管理</a>
                        <a class="collapse-item" href="#">預購訂單管理</a>
                        <a class="collapse-item" href="#">一般訂單管理</a>
                        <a class="collapse-item" href="#">見面會訂單管理</a>
                        <a class="collapse-item" href="#">訂單問題回覆</a>
                    </div>
                </div>
            </li>
            
            <!-- Divider -->
            <hr class="sidebar-divider">
            
            <!-- Heading -->
            <div class="sidebar-heading"> 系統 System </div>
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse5" aria-expanded="true" aria-controls="collapse5">
                    <i class="fas fa-ad"></i>
                    <span>廣告管理</span>
                </a>
                <div id="collapse5" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">廣告系統:</h6>
                        <a class="collapse-item" href="#">廣告管理</a>
                        <a class="collapse-item" href="#">社群分析</a>
                    </div>
                </div>
            </li>
            
            <!-- Nav Item - Tables -->
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <i class="fas fa-poo"></i>
                    <span>作品檢舉</span></a>
            </li>
            
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <i class="fas fa-user-shield"></i>
                    <span>員工管理</span></a>
            </li>
            
            <!-- Divider -->
            <hr class="sidebar-divider d-none d-md-block">
            
            <!-- Sidebar Toggler (Sidebar) -->
            <div class="text-center d-none d-md-inline">
                <button class="rounded-circle border-0" id="sidebarToggle"></button>
            </div>
       
        
         </ul>
        <!-- =========================================== Sidebar END   ===========================================  -->
        
        <!-- Bootstrap core JavaScript-->
        <script src="<%=request.getContextPath()%>/backend/template/vendor/jquery/jquery.min.js"></script>
        <script src="<%=request.getContextPath()%>/backend/template/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- Core plugin JavaScript-->
        <script src="<%=request.getContextPath()%>/backend/template/vendor/jquery-easing/jquery.easing.min.js"></script>
        <!-- Custom scripts for all pages-->
        <script src="<%=request.getContextPath()%>/backend/template/js/sb-admin-2.min.js"></script>
        <!-- Page level plugins -->
        <script src="<%=request.getContextPath()%>/backend/template/vendor/chart.js/Chart.min.js"></script>
        <!-- Page level custom scripts -->
        <script src="<%=request.getContextPath()%>/backend/template/js/demo/chart-area-demo.js"></script>
        <script src="<%=request.getContextPath()%>/backend/template/js/demo/chart-pie-demo.js"></script>

</body>

</html>