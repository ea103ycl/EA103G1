<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.painter.model.*"%>
<%@ page import="com.painter_tag_map.model.*"%>
<%@ page import="com.painter_tag.model.*"%>
<%@ page import="com.painter_msg.model.*"%>
<%@ page import="com.follow.model.*"%>

<%@include file="/frontend/bar/testLogin.jsp"%>

<%
	MemVO loginMemVO = (MemVO)session.getAttribute("memVO");
	pageContext.setAttribute("loginMemVO", loginMemVO);
	
	MemService sMembersSvc = new MemService();
	pageContext.setAttribute("sMembersSvc", sMembersSvc);

	PainterService painterSvc = new PainterService();
	Integer ptr_no = Integer.valueOf(request.getParameter("ptr_no"));
	
	PainterVO painterVO = painterSvc.getOnePainter(ptr_no);
	pageContext.setAttribute("painterVO", painterVO);
	
	PainterMsgService painterMsgSvc = new PainterMsgService();
	List<PainterMsgVO> msgList = painterMsgSvc.getAll(ptr_no);
	pageContext.setAttribute("msgList", msgList);
	
	String src = (String)request.getParameter("src");
	pageContext.setAttribute("src", src);
	
	String spg = (String)request.getParameter("spg");
	pageContext.setAttribute("spg", spg);
	
	String sid = (String)request.getParameter("sid");
	pageContext.setAttribute("sid", sid);
	
	PainterTagMapService painterTagMapSvc = new PainterTagMapService();
	List<PainterTagMapVO> tagList = painterTagMapSvc.getAllByPtrNo(ptr_no);
	pageContext.setAttribute("tagList", tagList);
	
	PainterTagService painterTagSvc = new PainterTagService();
	pageContext.setAttribute("painterTagSvc", painterTagSvc);
	
	String tagString = "";
	for( PainterTagMapVO painterTagMapVo : tagList){
		tagString += "#" + painterTagSvc.getOneByTagNo( painterTagMapVo.getTag_no() ).getTag_desc();
	}
	pageContext.setAttribute("tagString", tagString);

	
%>

<!DOCTYPE html>
<html>
<head>
	
	<title>${painterVO.ptr_nm}</title>
	<link href="<%=request.getContextPath()%>/frontend/template/YCL/YCL.css" rel="stylesheet">
</head>
<body>

	<div id="page-content" class="container">
	
       	<!-- top bar -->
		<%@include file="/frontend/bar/frontBarTop.jsp"%>
		<%@include file="/frontend/template/YCL/YCL.css"%>

	    <!-- post full-width -->
	    <section id="blog">
	        <div class="row">
	            <!-- post content area -->
	            <div class="col-xs-12 col-sm-6">
	            	<!-- 作品圖片 -->
					<img id="ycl-painter-pic" src="<%=request.getContextPath()%>/painter/painter.do?action=showPic&ptr_no=${painterVO.ptr_no}" alt="">
	            </div>
	            <div class="col-xs-12  col-sm-6">
	                <div class="blog block post-content-area">
	                    <div class="post-info-box">
	                    
	                    	<c:if test="${loginMemVO.mem_id == painterVO.mem_id}">
	            				<button id="deletePtrBtn" class="pull-right">刪除</button>
	                    		<button class="pull-right" data-target="#updateModal" onclick="$('#updateModal').modal('show')">修改</button>
	            			</c:if>
	            			
	            			<!-- 作品名稱 -->
	            			<h3>${painterVO.ptr_nm}</h3>
	            			
	            			<!-- 作品說明區塊 -->           		
	            			<p class="post-meta">
	            			
	            				<!-- 創作者頭像&會員帳號 -->
	            				<a href="<%=request.getContextPath()%>/frontend/painter/listAllPainter.jsp?sid=${painterVO.mem_id}">
	            					<img class="ycl-creator-pic" src="<%=request.getContextPath()%>/members/headphotoHandler.do?action=getPic&mem_id=${painterVO.mem_id}">
	            					${sMembersSvc.findByPrimaryKey(painterVO.mem_id).getM_accno()}
	            				</a>
	            				
	            				<!-- 發布日期 -->
	            				<fmt:formatDate value="${painterVO.create_dt}" pattern="yyyy/MM/dd HH:mm"/></p>
						    	
						    	<!-- 作品說明 -->
						    	<div>
							    	<c:forTokens var="str" items="${painterVO.intro}" delims="&#13;&#10;">
							    		<p>${str}</p>
							    	</c:forTokens>
						    	</div>
						    	
						    	<!-- 作品tag -->
						   		<c:forEach var="painterTagMapVO" items="${tagList}">
						    		<a href="#" class="ycl-hashtag"># ${painterTagSvc.getOneByTagNo(painterTagMapVO.tag_no).tag_desc}&nbsp</a>
						    	</c:forEach>
	                    </div><!-- / post-info-box -->
	                </div><!-- / blog-block -->
	
	                <div class="comments">
	                    <h4>留言</h4>
	                    <ul class="media-list">
	                    	                    
	                    	<c:if test="${fn:length(msgList) == 0}">
	                    		無任何留言。
	                    	</c:if>
	                    	
	                    	<c:set var="count" value="0" scope="page" />
	                    	<c:forEach var="painterMsgVO" items="${msgList}">
	                    	
	                    		<!-- 每則留言內容 -->                  
		                    	<li class="media">
		                    		                        
		                        	<!-- 留言者頭像 -->
		                            <div class="media-left">
		                            
		                                <a href="<%=request.getContextPath()%>/frontend/painter/listAllPainter.jsp?sid=${painterMsgVO.mem_id}">
		                                    <img class="media-object" alt="" src="<%=request.getContextPath()%>/members/headphotoHandler.do?action=getPic&mem_id=${painterMsgVO.mem_id}">
		                                </a>
		                            </div>
		                            
		                            <!-- 留言內容 -->
		                            <div class="media-body">
		                            
		                                <div class="comment-info">		                                    
		                                    <div class="comment-author">
		                                    	<a href="<%=request.getContextPath()%>/frontend/painter/listAllPainter.jsp?sid=${painterMsgVO.mem_id}"> ${sMembersSvc.findByPrimaryKey(painterMsgVO.mem_id).getM_accno()}</a>
		                                    <span class="comment-date ycl-comment-date pull-right">
		                                    	<c:set var="count" value="${count + 1}" scope="page"/> ${count}樓
		                                    	<fmt:formatDate value="${painterMsgVO.msg_dt}" pattern="yyyy/MM/dd HH:mm"/>		                                    	
		                                    	<c:choose>
		                                    		
		                                    		<c:when test="${loginMemVO.mem_id == painterMsgVO.mem_id}">		                                    		
														<button class="ycl-msg-btn-del deleteByMsgOwner" value="${painterMsgVO.msg_no}"><i class="fa fa-window-close" aria-hidden="true"></i></button>
		                                    		</c:when>
		                                    		
		                                    		<c:when test="${loginMemVO.mem_id == painterVO.mem_id}">
															<button class="ycl-msg-btn-del deleteByPainterOwner" value="${painterMsgVO.msg_no}"><i class="fa fa-window-close" aria-hidden="true"></i></button>
		                                    		</c:when>
		                                    			                                    	
		                                    	</c:choose>
		                                    </span>		                                    
		                                    </div>
		                                    
		                                </div><!-- / comment-info -->
		                                
		                                <div class="comment">
		                                    <p>${painterMsgVO.msg}</p>
		                                </div><!-- / comment -->
		                            </div><!-- / parent media-body -->
		                        </li><!-- / media -->	                    
	                    
	                    	</c:forEach>
	                    </ul><!-- / media-list -->
	                    
	                    
  						<!-- comment form -->
	                    <div id="comment-form">
	                    
		                    <c:if test="${not empty errorMsgs}">
								<font style="color: red">請修正以下錯誤:</font>
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
										<li style="color: red">${message}</li>
									</c:forEach>
								</ul>
							</c:if>
	                    
	                        <form id="commentForm" data-toggle="validator" method="post" action="<%=request.getContextPath()%>/painter/painter_msg.do" enctype="multipart/form-data">
	                            <div class="row">                         
	                                <div class="col-xs-12">
	                                    <div class="form-group">
	                                        <textarea id="msg" name="msg" class="form-control" rows="5" placeholder="MESSAGE" required></textarea>
	                                    	<input style="display:none" name="ptr_no" value="${painterVO.ptr_no}"></input>
	                                    </div>
<!-- 	                                    <button type="submit" name="action" value="insert" id="form-submit" class="btn btn-md btn-primary-filled btn-form-submit"><strong>送出</strong></button> -->
	                                </div>
	                            </div><!-- / row -->
	                            
	                            <div class="row">
	                            	<c:choose>
	                            		<c:when test="${src==2}"><div class="col-xs-5"><a href="<%=request.getContextPath()%>/frontend/painter/listAllPainterCol.jsp?sid=${sid}&whichPageCol=${spg}" class="btn btn-md btn-primary btn-form-submit"><strong>回收藏列表</strong></a></div></c:when>
	                            		<c:when test="${src==3}"><div class="col-xs-5"><a href="<%=request.getContextPath()%>/frontend/painter/listAllPainterFollow.jsp?sid=${sid}&whichPage=${spg}" class="btn btn-md btn-primary btn-form-submit"><strong>回關注圈</strong></a></div></c:when>
	                            		<c:otherwise><div class="col-xs-5"><a href="<%=request.getContextPath()%>/frontend/painter/listAllPainter.jsp?sid=${painterVO.mem_id}&whichPage=${spg}" class="btn btn-md btn-primary btn-form-submit"><strong>回作品列表</strong></a></div></c:otherwise>
	                            	</c:choose>
	                            	<div class="col-xs-7"><button type="submit" name="action" value="insert" id="form-submit" class="btn btn-md btn-primary-filled btn-form-submit"><strong>送出</strong></button></div>
	                            </div>
	                            
	                        </form>
	                        
	                    </div>
	                    <!-- / comment form -->
	                </div><!-- / comments -->

	                
	            </div><!-- / col-sm-8 col-md-9 -->
	            <!-- / post content area -->
	
	            
	        </div><!-- / row -->
	    </section>
	    <!-- / post full-width -->
	    
	    	<!-- Modal 修改 -->
			<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="updateModalLabel">修改作品</h4>
			      </div>
			      <div class="modal-body">
			      
			        <form method="post" action="<%=request.getContextPath()%>/painter/painter.do" enctype="multipart/form-data">

			        	<div class="form-group" style="display:none;">
	                        <label>作品編號</label>
	                        <input type="text" class="form-control" id="ptr_no" name="ptr_no" value="${painterVO.ptr_no}">
	                    </div>

	                    <div class="form-group">
	                        <label>作品能見度</label>
	                        <select class="form-control" id="priv_stat" name="priv_stat">
	                        	<option value="1" <c:if test="${1==painterVO.priv_stat}">selected</c:if>>公開</option>
	                        	<option value="3" <c:if test="${3==painterVO.priv_stat}">selected</c:if>>僅限粉絲</option>
	                        	<option value="2" <c:if test="${2==painterVO.priv_stat}">selected</c:if>>僅限個人</option>
	                        </select>    
	                    </div>
	                    
			        	<div class="form-group">
	                        <label>作品名稱</label>
	                        <input type="text" class="form-control" id="ptr_nm" name="ptr_nm" placeholder="請輸入作品名稱" required="required" data-error="*請輸入作品名稱" 
	                        	   value="${painterVO.ptr_nm}">
	                    </div>

	                    
	                    <div class="form-group">
	                        <label>作品說明</label>
	                        <textarea class="form-control" id="intro" name="intro" placeholder="請輸入作品說明" rows="5" onkeyup="autogrow(this);" >${painterVO.intro}</textarea>
	                    </div>
						
			        	<div class="form-group">
	                        <label>HashTag</label>
	                        <input class="form-control" id="tag_desc" name="tag_desc" placeholder="請輸入作品tag" value="${tagString}">  
	                    </div>
	                    
	                    <div class="form-group">
	                        <label>選擇作品圖片</label>
	                        <input type="file" id="imgPath" name="imgPath" onchange="readURL(this);">
	                        <img class="img-fluid w-25 h-25" id="imgUpload"
	                        	 src="<%=request.getContextPath()%>/painter/painter.do?action=showPic&ptr_no=${painterVO.ptr_no}">	
                    	</div>
                    	
	                     <div class="modal-footer">
				        	<button type="submit" class="btn btn-primary btn-rounded" data-dismiss="modal">取消</button>
				        	<button type="submit" class="btn btn-primary-filled btn-rounded" name="action" value="update">修改</button>
				      	 </div>

			        </form>			      
			      </div>

			    </div>
			  </div>
			</div>
			<!-- Modal 修改 END -->
	    
	
	</div> <!-- end container -->

	<!-- footer -->
	<%@include file="/frontend/bar/frontBarFooter.jsp"%>

	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	
	<script>
		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {
					$('#imgUpload').attr('src', e.target.result);
				};					
					reader.readAsDataURL(input.files[0]);
			}
		}
	
	
		$("#deletePtrBtn").click(function() {
			swal({
				  title: "刪除作品",
				  text: "確定要從ArtsBlock永久移除這個作品嗎？",
				  icon: "warning",
				  buttons: ["取消", "確定"],
				  dangerMode: true,
				})
				.then((willDelete) => {
				  if (willDelete) {
					  location.href='<%=request.getContextPath()%>/painter/painter.do?action=delete&ptr_no=${painterVO.ptr_no}';
				  } else {
				   null;
				  }
				});
		});
		
		$('.deleteByPainterOwner').click(function() {
			let id= $(this).val();
			swal({
				  title: "刪除留言",
				  text: "確定要刪除留言嗎？",
				  icon: "warning",
				  buttons: ["取消", "確定"],
				  dangerMode: true,
				})
				.then((willDelete) => {
				  if (willDelete) {
					  location.href='<%=request.getContextPath()%>/painter/painter_msg.do?action=deleteByMsgOwner&msg_no=' + id + '&ptr_no=${painterVO.ptr_no}';
				  } else {
				   null;
				  }
				});
		});
		
		$('.deleteByMsgOwner').click(function() {
			let id= $(this).val();
			swal({
				  title: "刪除留言",
				  text: "確定要刪除的留言嗎？",
				  icon: "warning",
		          buttons: ["取消", "確定"],
				  dangerMode: true,
				})
				.then((willDelete) => {
				  if (willDelete) {
					  location.href='<%=request.getContextPath()%>/painter/painter_msg.do?action=deleteByPainterOwner&msg_no=' + id + '&ptr_no=${painterVO.ptr_no}';
				  } else {
				   null;
				  }
				});
		});
	
		function autogrow(textarea){
			let adjustedHeight=textarea.clientHeight;
			    adjustedHeight=Math.max(textarea.scrollHeight,adjustedHeight);
			    if (adjustedHeight>textarea.clientHeight){
			        textarea.style.height=adjustedHeight+'px';
			    }
		}

		
	</script>
	
</body>
</html>