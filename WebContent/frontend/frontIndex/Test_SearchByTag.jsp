<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        img{
            max-width: 60%; 
        }
        #result>ul>li{
            cursor: pointer;
        }
    </style>
</head>

<body>
    <form action="<%=request.getContextPath()%>/painter/TagSearch" name="addForm">
        <div>
            <input type="hidden" name="action" value="addTag">
            <input id="inputA" name="input" type="text" >
            <input type="submit" value="addTag">
        </div>
    </form>
    <form action="<%=request.getContextPath()%>/painter/TagSearch" name="searchForm">
        <div>
            <input type="hidden" name="action" value="searchByTag">
            <input id="inputB" type="text" name="input" >
            <input type="submit" value="searchByTag">
        </div>
    </form>
    
    <div id="result">
        <ul>
            <li></li>
            <li></li>
            <li></li>    
        </ul>
    </div>

<script>
//=================ajax搜尋輸入模糊查詢返回推薦搜尋結果 ajax =====================
 $('#inputB').on("change",function(){
    var srtag=$(this).val();
 
    // alert($(this).val());
    // var url ="/controller/Search";
    $.ajax({
        method: "post",
        url:"TagSearch",
        data:{srtag:srtag},
        success: function(dataReceived){ //dataReceived=> out.println("<li> #"+result+"</li>")
    //  alert(dataReceived);
            $("#searchList> div").html(dataReceived)
        },
        error: function(){
            alert("failed");
        }
    });
});
    //====================== 點選推薦搜尋結果輸入搜尋=============================
    $('#result').click(
        function(e){
         // console.log($('#inputB'));
           $('#inputB').val(e.target.innerHTML);
           
        }
    );
    



</script>



</body>

</html>