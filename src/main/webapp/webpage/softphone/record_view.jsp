<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title></title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinecss.jsp"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejs.jsp"%>

<script	src="${ctx}/resources/general/common/uploadify/jquery.uploadify-3.1.min.js"	type="text/javascript"></script>
<script	src="${ctx}/resources/general/common/jquery/jquery.fileDownload.js"	type="text/javascript"></script>
<script src="${ctx}/resources/general/common/flexpaper/flexpaper_flash.js" type="text/javascript"></script>
<script src="${ctx}/resources/general/common/uploadify/swfobject.js" type="text/javascript"></script>

<script type="text/javascript">
<!--


	function toReply(id,type){
		var title = ""; 
		if('ChannelTypeMail' == type){
			title = '邮件';
		}else if('ChannelTypeSinaweibo' == type){
			title = '微博';
		}else if('ChannelTypeSms' == type){
			title = '短信';
		}
		
		
	parent.$(".tabs-selected .tabs-title ").text(title + '回复');
	//	uiBase.addOneTab(title + '回复',"${ctx}/channelrecord/toReply/" + id);
	//var mainTabs = window.parent.$('#maintabs');
	//var parentTab=mainTabs.tabs("getSelected");
	location.href = "${ctx}/channelrecord/toReply/" + id ;
	}

	function addOrder(id){
		uiBase.addOneTab('工单新增',"${ctx}/channelrecord/addOrder/" + id + "?type=${record.type}" );	
	}

	function viewOrder(id){
		uiBase.addOneTab('工单查看',"${ctx}/business/accept/modify/" +id  + '?pageTag=viewPage');	
	}

	function toRelate(id){	
			//uiBase.addOneTab('关联工单信息','${ctx }/channelrecord/relateOrder/'+id + "?search_relaState=");	
			uiBase.addOneTab('关联工单信息','${ctx}/business/accept/toRelate/'+id + '?search_pageTag=channel');		
	}

	/**
	 * 下载附件文件
	 */
	function downloadAtta(id) {
		//alert(id);
		$.fileDownload("${ctx}/uploadAction/downloadById/" + id, {
			httpMethod : "GET",
		}).done(function() {
			alert('文件下载成功!');
		}).fail(function() {
			alert('文件不存在!');
		});
	}
	/**
	 * 删除文件
	 */
	function deleteAtta(id) {
		$.ajax({
			type : "get",
			url : "${ctx}/uploadAction/deleteById/" + id,
			async : false,
			success : function(data) {
				alert("文件删除成功！");
				$("#att_" + id  ).remove();
			}
		});
	}
//-->
</script>
</head>
<body>

	<div class="col-md-12">
	
		<div class="row">
			<div class="grid simple">
				<div class="index-grid-body">
					
						<div class="col-md-10 m-t-30">
							<form  method="post" id="form1"   >
						
							<div class="information-form">
								<c:if test="${record.type=='ChannelTypeMail'}">
									<div class="col-md-12">
										<label class="field-title">标题：</label> 
										${record.subject }
									</div>
								</c:if>
								
								<%-- <c:if test="${record.send }"> --%>
									<div class="col-md-12">
										<label class="field-title">接收人：</label> 
										<c:out value="${empty record.bccAddress ? record.toAddress : record.bccAddress  }" escapeXml="true"/> 
									</div>
								<%-- </c:if> --%>
								
								<div class="col-md-12">
									<label class="field-title">发件人：</label>
									
									<c:out value="${empty record.obligateB ? record.fromAddress : record.obligateB  }" escapeXml="true"/> 
								</div>
								
								<div class="col-md-12">
									<label class="field-title">内容：</label> 
		
									<c:if test="${ record.subject != 'image' }">
								 		${record.content}
								 	</c:if> 
									<c:if test="${ record.subject == 'image' }">
								 		 <img width="310" src="${ctx}/image/showImage/${record.id}">
								 	</c:if>		

								</div>
								
								<c:if test="${record.type=='ChannelTypeMail'}" >

									<c:if test="${not empty attachmentList}">
										<span id="attachmentSpan" style="">附件：</span>
										<div id="attachmentListDiv">
										<c:forEach var="attachment" items="${attachmentList}">
										<div class="attachment_box">
										<img id="att_${attachment.id}" class="file-img" src="${ctx}/resources/themes/hotline/common/images/download.jpg" title="点击下载文件" onclick="downloadAtta('${attachment.id}') " style="cursor:pointer; ">
												<a  id="att_down_${attachment.id}" onclick="downloadAtta('${attachment.id}') " style="cursor:pointer; " title="点击下载文件">${attachment.fileName}</a>
												</div>
										</c:forEach>
									</div>
									
									</c:if>
								</c:if>
									 										
							</div>
							
							</form>
						</div>
					
					<div class="col-md-12 footer-btn padding-25 m-t-40">
						<c:if test="${!record.send }">
							<c:choose>
				        		<c:when test="${empty record.obligateE}">
									<button type="button" class="btn btn-primary" onclick="addOrder('${record.id}');">新建工单</button>
									<%-- <button type="button" class="btn btn-primary" onclick="toRelate('${record.id}');">关联工单</button> --%>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn btn-primary" onclick="viewOrder('${record.obligateC}');">查看工单</button>
								</c:otherwise>
							</c:choose>
								
							<c:if test="${record.type=='ChannelTypeMail' || record.type=='ChannelTypeSinaweibo'|| record.type=='ChannelTypeSms'}">
								<button type="button" class="btn btn-default" onclick="toReply('${record.id}','${record.type}');" >回复</button>
							</c:if>
						</c:if>
						<button type="button" class="btn btn-default" onclick="uiBase.closeCurrentTab();" >返回</button>
					</div>
					
				</div>
			</div>
		</div>
		
	</div>
	

			
</body>
</html>