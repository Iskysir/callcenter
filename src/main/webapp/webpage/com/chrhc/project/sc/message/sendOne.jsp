<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html>
<html>
 <head>
  <title>消息</title>
  <t:base type="ckeditor,jquery,easyui,tools,DatePicker"></t:base>
  
      <script type="text/javascript" src="plug-in/accordion/js/leftmenu.js"></script>
 	<link rel="stylesheet"  href="plug-in/media/css/font-awesome.min.css" type="text/css">
 	<link href="plug-in/media/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
 	<link href="plug-in/media/css/style.css" rel="stylesheet" type="text/css"/>
 	
	<link rel="stylesheet" href="plug-in/accordion/css/icons.css" type="text/css"></link>
	<link rel="stylesheet" href="plug-in/accordion/css/accordion.css" type="text/css"></link>
	 <link type="text/css" rel="stylesheet" href="plug-in/ztree/css/zTreeStyle/zTreeStyle.css" >
	<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.excheck-3.5.js"></script> 
	<link href="plug-in/media/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>
	<link rel="stylesheet" type="text/css" href="plug-in/media/jquery.easyui/themes/gray/easyui.css">
	
	<link href="plug-in/chrhc/bootstrap.css" rel="stylesheet">
	<link type="text/css" rel="stylesheet" href="plug-in/chrhc/style.css" >
	 <script src="plug-in/media/layer/layer.min.js" type="text/javascript"></script>
  <script type="text/javascript">
 	function subForm(){
 	 
 	}

 	function beforeSubmit_(){

 		$("#content_text").val(ckeditor_content.getData());
 //		var ids = parent.getSelDept();

 		var panduan = $("input[type='checkbox']").is(':checked');
			if(!panduan){
				$.dialog.alert("信息类型至少选择一个！");
				return false;
			}
 		
 		var a = $("select[name='modelType']").find("option:selected").text();
		var b = $("select[name='modelType']").find("option:selected").val();
		if(b=='' || b==undefined || b==null){
			$("#modelName").attr("value","");
		}else{
			$("#modelName").attr("value",a);
		}
		

		var aa = $.trim($("#title").val()).length;
		    if(aa>50){
		    	$.dialog.alert("邮件标题不能超过50个字符");
		    	return false;
		    }
		if($("input[value='email']").is(':checked')){
	 		var aa = $.trim($("#title").val()).length;
	 		if(aa == 0){
				$.dialog.alert("邮件标题不能为空");
				return false;
	 	 	}
	 	 }
 		
 			
 //			$("#receiver").val(ids);
 			return true ;
 	}
	function uploadFile(a){
			$("input[name='mType']").attr("checked",false);
			$("#title").val("");
			
//			alert(ckeditor_content.getData());
			var cc = $(".cke_wysiwyg_frame cke_reset").val();
			$("#modelType").find("option[selected='selected']").attr("selected",true);
			$("body[contenteditable='true']").empty();
//			var ddd = $(window.frames["iframe[allowtransparency='true']"].document).find(":text");
			
			if(a != 1){
 				tip('已加入发送队列');
			} 	
 			
	}
	$(document).ready(function(){ 
		$("select[name='modelType']").change(function(){
			ckeditor_content.setData($(this).val());
		}); 
		
	});
  </script>
 </head>
<body class="page_body">
<section id="mainbox"> 


<div class="Information_div">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="scMessageController.do?addOne" tiptype="4" beforeSubmit="beforeSubmit_" refresh="true" >
  			<input type="hidden" id="modelName" name="modelName">
			<input id="receiver" name="receiver" type="hidden" value="${receiver }"> 
			<div class="Information_div_rt" >
			<div class=" bj_div" >
			<span>邮件标题</span>
			<input id="title" name="title" type="text" class="search-input" />
			<span>信息模版</span>
               	 <t:dictSelect field="modelType" type="list" id="modelType"
									typeGroupCode="id" defaultVal="${scMessagePage.modelType.value}" hasLabel="false"  
									title="模板类型" dictTable="sc_msg_template" dictText="model_name" dictField="content" ></t:dictSelect>  
			<div class="lt_div">
                		<label><i class="fa  fa-mobile-phone"></i>短信<input name="mType" type="checkbox" value="sms" /></label>
                		<label><i class="fa  fa-envelope"></i>邮件<input name="mType" type="checkbox" value="email" /></label>
                		<!-- <label><i class="fa  fa-weixin"></i>微信<input name="mType" type="checkbox" value="wechat" /></label> -->
                		<label><i class=""></i>移动端推送<input name="mType" type="checkbox" value="baidu" /></label>
                        <button type="submit" id="btn_sub" class="btn btn-cont"  >发送</button>
                        <div class="btn btn-cont" type="button" onclick="uploadFile(1)">重置</div>
                </div>
            </div>
            
            <t:ckeditor name="content" isfinder="false"
								type="height:570" value="${scMessagePage.content}"></t:ckeditor>
             </div>
    </t:formvalid>        
</div>            
            
            
</section>
 </body>
  <script src = "webpage/com/chrhc/project/sc/message/scMessage.js"></script>		