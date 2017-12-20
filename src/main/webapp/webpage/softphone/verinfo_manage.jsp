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
	function save(){
	$("#saveBtn").attr("disabled","disabled");
		 $.post( 
			"${ctx}/app/save",
			$("#form1").serialize(),
			function(data){
				art.dialog({title: '提示',icon: 'succeed',lock: true,content: '保存成功，2秒后会自动关闭……',time:2});
				//alert(data);
				//uiBase.closeCurrentTab();
			}
		);
	}

/**
 * 附件上传事件初始化
 */
 $(document).ready(function (){
		 $("#infile").uploadify({
				'swf' : basePath +'/resources/general/common/uploadify/uploadify.swf?ver=' + Math.random(),
				'uploader'  : basePath +'/uploadAction/upload?attachtype=app/apk',
				'cancelImg' : basePath +'/resources/general/common/uploadify/uploadify-cancel.png',
				'fileObjName'   : 'infile',
				'expressInstall':'expressInstall.swf',
				//在浏览窗口底部的文件类型下拉菜单中显示的文本
				'fileTypeDesc':'支持的格式：apk',
				//允许上传的文件后缀
				'fileTypeExts':'*.apk',
				//上传文件的大小限制
				'fileSizeLimit':'20MB',
				'hideButton':true,
				'auto' : true,
				'multi' : false,
				'height' : "30",
				'width' : "90",
	      		onUploadSuccess : function(file,data,response) {
	      			$("#saveBtn").attr("disabled",false);
	      			var fileDOM = "<tr id='file_"+data+"'><td width='400' clospan="+3+">"+file.name+"</td>"+
	      							"<td width='100'>" +
	      							'<input name="resouceUrl" type="hidden" value="' + data + '">'+
	      							"<a id='"+data+"' name='deletefile' href='javascript:return false'>删除</a>&nbsp;&nbsp;&nbsp;"+
	      							"</td></tr>";
	      							
	      			$("#resouceUrl").val(data);
	      			$("#uploadfileList").empty();
	      			//添加文件列表信息
	      			$("#uploadfileList").append(fileDOM);
	      			//绑定删除事件
	      			$("#uploadfileList a[name='deletefile']").unbind();
	      			$("#uploadfileList a[name='deletefile']").bind("click", function(){
	      				//实时获取附件id，并发送ajax请求删除附件
	      				var attachId = $(this).attr("id") ;
	      				$.ajax({
	      					type:"get",
	      					url:synergy.path+"/uploadAction/deleteById/"+attachId,
	      					async:false,
	      					success:function(data){
	      						$("#uploadfileList #file_"+attachId).remove();
	      					}
	      				});
	      			});	
	      				      				      			
				}.bind(this),
				'buttonText' : '选择apk'
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
						
						
						<div class="information-form">
							<div class="col-md-4">
								<label class="field-title">版本号：</label> 
								<input type="text" name="ver" value="${ver}"  readonly="readonly"/>
							</div>
						
							<div class="col-md-12">
								<input type='file' id='infile' name='infile' /> 
								<table id="uploadfileList" width="500" border="0" align="left"></table>
							</div>								
							
						</div>
						
						</form>
					</div>
					
					<div class="col-md-12 footer-btn padding-25 m-t-40">
						<button id="saveBtn" type="button" class="btn btn-primary" onclick="save();" disabled="disabled">保存</button>
						<button type="button" class="btn btn-default" onclick="uiBase.closeCurrentTab();" >关闭</button>
					</div>
					
				</div>
			</div>
		</div>
		
	</div>
	
	
<div>

		       
</div>
</body>
</html>