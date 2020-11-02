<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import=" java.text.*,  java.util.*"  %>
<%@ page import="com.meeting.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.wel_record.model.*"%>





<%
	MeetingService meetingSvc = new MeetingService();
    List<MeetingVO> list = meetingSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<%
	WelRecordVO welRecordVO = (WelRecordVO) request.getAttribute("welRecordVO");

	MemVO memVO = (MemVO) session.getAttribute("memVO");
%>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 Date current = new Date();
%>


<html>
<head>

<!-- page title -->
<title>�Ҧ����u��� - listAllMeeting_front.jsp</title>

<style>
  table#table-1 {
	background-color: #C4E1E1;
    border: 2px #ECF5FF;
    text-align: center;
    
  }
  table#table-1 h4 {
    color: #613030;
    display: block;
    margin-bottom: 10px;
  }
  
  h4 {
    color: blue;
    display: inline;
  }
  h3 {
  color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
    text-align: center;
  }
  th, td {
    padding: 5px;
    text-align: center;
       height:100px;
    font-size:20px;
  }
  h4 {
  padding: 5px;
    text-align: center;
  }
  body {
    color: #666666;
    background: #fefefe;
    font-family: "Rubik", sans-serif;
    font-weight: 400;
    font-size: 14px;
    line-height: 20px;
    letter-spacing: 0.05em;
}
</style>

</head>
<body>

	<!-- header -->

	<header>

		<%@include file="/frontend/bar/frontBarTop.jsp"%>

		    <!-- header-banner -->
    <div id="header-banner">
        <div class="banner-content single-page text-center">
            <div class="banner-border">
                <div class="banner-info" id="banner-info">
                    <h1>Meeting</h1>
                </div><!-- / banner-info -->
            </div><!-- / banner-border -->
        </div><!-- / banner-content -->
    </div>
    <!-- / header-banner -->
	</header>
	<!-- / header -->
<table id="table-1">
	<tr><td>
		 <h3>�Ҧ������|��� - listAllMeeting.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/frontend/meeting/listAllMeeting_front.jsp">�^����</a></h4>	 
	</td></tr>
</table>

<%-- ���~���C --%>
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
	    <th>���W���A</th>
	    <th>���ʦW��</th>
	    <th>���</th>
		<th>�a�I</th>
		<th>���W����</th>
		<th>�ثe�H��</th>
        <th>�d��</th>
        
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="meetingVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
		
		 
<td>	    
<%-- �H�U���ծɶ� --%>
<fmt:formatDate value="<%=current %>" pattern="yyyy-MM-dd" var="now_time" />
<fmt:formatDate value="${meetingVO.mt_start_time}" pattern="yyyy-MM-dd" var="mt_start_time" />
<fmt:formatDate value="${meetingVO.mt_time}" pattern="yyyy-MM-dd" var="mt_time" />
<fmt:formatDate value="${meetingVO.mt_end_time}" pattern="yyyy-MM-dd" var="mt_end_time" />
<c:set var="mt_status" scope="session" value="${meetingVO.mt_status}"/>


<c:choose> 
  <c:when test="${mt_status == 2}">
    <font color=red>���ʤw����</font>
    </c:when>
    <c:when test="${now_time gt mt_time}">
     <font color=red> ���ʤw����</font>
    </c:when>
     <c:when test="${now_time gt mt_end_time}">
    <font color=red>���W�I��</font>
    </c:when>
    <c:when test="${mt_start_time gt now_time}">
              �Y�N�}��
    </c:when>
    <c:otherwise>
          �}����W
    </c:otherwise>
</c:choose>   
</td>	 		      
<td>${meetingVO.mt_id}</td>    
		    <td><fmt:formatDate value="${meetingVO.mt_time}" pattern="yyyy-MM-dd" /></td> 
		    <td>${meetingVO.mt_place}</td>	    
		     
		    <td><fmt:formatDate value="${meetingVO.mt_start_time}" pattern="yyyy-MM-dd" />~<fmt:formatDate value="${meetingVO.mt_end_time}" pattern="yyyy-MM-dd" /></td>		 
<td>${meetingVO.mt_num}/${meetingVO.max_num}</td>    


<td>
           <form action="<%=request.getContextPath()%>/frontend/meeting/meeting.do" " method="get"><!-- action�ȶ�A��api url -->
		  <input type="hidden" name="mt_no" value="${meetingVO.mt_no}" />
		<input type="submit" value="�d��" />
		<input type="hidden" name="action" value="getOne_For_Display">
		  </form>
		    </td>		    
		</tr>
	</c:forEach>
</table>
<table id="table-1">
	<tr><td>
		 <h4>
<a href='<%=request.getContextPath()%>/frontend/meeting/addMeeting.jsp'>�u�|��v</a> �����|

  <a href='<%=request.getContextPath()%>/frontend/meeting/search_mem_meeting.jsp'>�u���|�쪺�v</a> �����|

 <a href='<%=request.getContextPath()%>/frontend/reg_inf/listAllReg_inf.jsp'>�u�ڳ��W���v</a> �����|
</h4>
	</td></tr>
</table>

<!-- footer -->

	<%@include file="/frontend/bar/frontBarFooter.jsp"%>

	<!-- / footer -->

</body>
</html>