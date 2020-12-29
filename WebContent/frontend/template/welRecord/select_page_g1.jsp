<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.wel_record.model.*"%>
<%@ page import="com.mem.model.*"%>
<%
  WelRecordVO welRecordVO = (WelRecordVO) request.getAttribute("welRecordVO");

     MemVO memVO = (MemVO) session.getAttribute("memVO");

%>

<html>
<head>
<title>Caroline</title>

<style>
  table#table-1 {
	width: 100%;
	background-color: #ACD6FF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>���]��������d��</h3><h4>( MVC )</h4></td></tr>
</table>


<h3>��Ƭd��:</h3>
	
<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
<tr>
	
		<td>�|���b��:</td>
		<td> <c:if test="${not empty sessionScope.usrid}">
			 ${sessionScope.usrid}
		</c:if> </td>
	</tr>
	<tr>
		<td>���]�l�B</td>
		<td>NTD 79999</td>
	</tr>
</table>

<h1>�d��</h1><br>

   <FORM METHOD="post" ACTION="<%= request.getContextPath()%>/welRecord/welRecord.do" >
       <b>��ܷ|���s��:</b>
       <select size="1" name="mem_id">
         <c:forEach var="memVO" items="${memSvc.all}" > 
          <option value="${memVO.mem_id}">${memVO.mem_id}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getAllbyMem_For_Display">
       <input type="submit" value="�e�X">
     </FORM>
  	
 <h1>�s�W</h1><br>
 
  <a href='deposit.jsp'><button type="button"value="�ڭn�x��"></button></a>


</body>
</html>