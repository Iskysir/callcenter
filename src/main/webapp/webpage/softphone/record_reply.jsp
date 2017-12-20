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
	function reply(){
	
		if (!validator.form()) {
			return ;
		}
		
		$("#replyBtn").attr("disabled","disabled");
		$.post( 
			"${ctx}/channelrecord/reply",
			$("#form1").serialize(),
			function(data){
				art.dialog({title: '提示',icon: 'succeed',lock: true,content: '发送成功，2秒后会自动关闭……',time:2});
				uiBase.refreshParentTab();
				uiBase.closeCurrentTab();
			}
		);
	}
	
	var validator ;
	$(function() {
	    	validator = $("#form1").validate({
	        errorPlacement: function(error, element) {
	            	$( element ).closest( "form" ).find( "label[for='" + element.attr( "id" ) + "']" ).append( error );
	        },
	        errorElement: "span"
	    });
	});
/**
 * 附件上传事件初始化
 */
 $(document).ready(function (){
		 $("#infile").uploadify({
				'swf' : basePath +'/resources/general/common/uploadify/uploadify.swf?ver=' + Math.random(),
				'uploader'  : basePath +'/uploadAction/upload?attachtype=ChannelRecord/mail',
				'cancelImg' : basePath +'/resources/general/common/uploadify/uploadify-cancel.png',
				'fileObjName'   : 'infile',
				'expressInstall':'expressInstall.swf',
				//在浏览窗口底部的文件类型下拉菜单中显示的文本
				'fileTypeDesc':'支持的格式：doc,pdf,xls,txt',
				//允许上传的文件后缀
				'fileTypeExts':'*.doc;*.docx;*.xls;*.xlsx;*.pdf;*.txt;*.jpg;*.png;',
				//上传文件的大小限制
				'fileSizeLimit':'20MB',
				'hideButton':true,
				'auto' : true,
				'multi' : false,
				'height' : "30",
				'width' : "90",
	      		onUploadSuccess : function(file,data,response) {
	      			var fileDOM = "<tr id='file_"+data+"'><td width='400' clospan="+3+">"+file.name+"</td>"+
	      							"<td width='100'>" +
	      							'<input name="attachids" type="hidden" value="' + data + '">'+
	      							"<a id='"+data+"' name='deletefile' href='javascript:return false'><i class='fa fa-trash-o green'></i><span>删除</span></a>&nbsp;&nbsp;&nbsp;"+
	      							"</td></tr>";
	      			//添加文件列表信息
	      			$("#uploadfileList").append(fileDOM);
	      			//绑定删除事件
	      			$("#uploadfileList a[name='deletefile']").unbind();
	      			$("#uploadfileList a[name='deletefile']").bind("click", function(){
	      				//实时获取附件id，并发送ajax请求删除附件
	      				var attachId = $(this).attr("id") ;
	      				$.ajax({
	      					type:"get",
	      					url:"${ctx}/uploadAction/deleteById/"+attachId,
	      					async:false,
	      					success:function(data){
	      						$("#uploadfileList #file_"+attachId).remove();
	      					}
	      				});
	      			});	
	      				      				      			
				}.bind(this),
				'buttonText' : '选择附件'
	            //'buttonImage' : basePath + '/resources/themes/hotline/common/images/call-pic.png'
	        });	 	
});

 /**
  * 在关闭页面前，执行该方法，清除uploadify加载时falsh，页面提示：“__flash__removeCallback未定义”的bug	
  */
 function removeChart(){  
	try{  
	    $("#infile").empty();  
	   }catch(e){  
	}  
}
//-->
</script>
</head>

<body onunload="removeChart()">


	<div class="col-md-12">
	
		<div class="row">
			<div class="grid simple">
				<div class="index-grid-body">
					<div class="col-md-10 m-t-30">
						<form  method="post" id="form1"   >
						
						<input type="hidden" name="obligateA" value="${record.obligateA}" />
						<input type="hidden" name="obligateB" value="${record.obligateB}" />	
						<input type="hidden" name="obligateC" value="${record.obligateC}" />
						<input type="hidden" name="obligateE" value="${record.obligateE}" />		
						
						<c:if test="${record.type=='ChannelTypeSms'}">
							<input type="hidden" name="fromAddress" value="${smsSenderNum}" />	
						</c:if>
						
						<input name="type" type="hidden" value="${record.type}">
						
						<div class="information-form">
							<c:if test="${record.type=='ChannelTypeMail'}">
								<div class="col-md-4">
									<label class="field-title">标题：</label> 
									<input type="text" name="subject"  />
								</div>
							</c:if>
						
							<div class="col-md-4">
								<label class="field-title">收件人：</label> 
								<input type="text"  name="toAddress" value="${record.fromAddress}" readonly="readonly"/>
							</div>								
							
							<div class="col-md-9">
								<label class="field-title"  for="content">内容：</label>
								<textarea name="content" cols="40" rows="5" id="content" required 
								<c:if test="${record.type=='ChannelTypeSinaweibo'}">
									maxlength="140"
								</c:if>
								<c:if test="${record.type=='ChannelTypeSms'}">
									maxlength="70"
								</c:if>		
								></textarea>
							</div>
							<c:if test="${record.type=='ChannelTypeMail'}">
							
								<div class="col-md-6 ie8correct attachment">
										<input type='file' id='infile' name='infile' />
								</div>
								
								<div id="fileTableDiv" class="row">
									<div class="col-md-8 ie8correct">
										<table id="uploadfileList" class="table table-bordered table-hover table-fixed-layout">
										</table>
									</div>
								</div>
								
							</c:if>							
						</div>
						
						</form>
					</div>
					
					<div class="col-md-12 footer-btn padding-25 m-t-40">
						<button type="button" class="btn btn-primary" onclick="reply();" id="replyBtn">发送</button>
						<button type="button" class="btn btn-default" onclick="uiBase.closeCurrentTab();" >返回</button>
					</div>
					
				</div>
			</div>
		</div>
		
	</div>
	
	
<div>

		       
</div>
</body>
</html>