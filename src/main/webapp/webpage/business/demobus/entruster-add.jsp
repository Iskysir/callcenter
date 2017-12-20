<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>委托人</title>
  <t:base type="jquery,tools,easyui,DatePicker"></t:base>	
 	<link href="plug-in/media/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="plug-in/media/css/bootstrap.min.css" rel="stylesheet" type="text/css"/> 
	<link href="plug-in/media/css/style.css" rel="stylesheet" type="text/css"/>
 	<link href="plug-in/media/css/style-add.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/default.css" rel="stylesheet" type="text/css"/>
   	<link href="plug-in/media/jquery.easyui/themes/gray/easyui.css" rel="stylesheet" type="text/css" >
	<!-- <script src="plug-in/media/js/jquery-1.10.1.min.js" type="text/javascript"></script> -->
	<script src="plug-in/media/js/bootstrap.min.js" type="text/javascript"></script>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  </script>
 </head>
<body class="page_body">
<section id="mainbox"> 
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" beforeSubmit="refreshPage()" layout="table" action="activitiController.do?doEntrust" tiptype="4">
		<input id="taskId" name="taskId" type="hidden" value="${taskId }">
		 <div class="chrhc_input_div">		
		 	<span style="float: left; font-size: 14px"> 委托人:</span>            
		        <div class="tabs_btn_div" style="float: left;">			        	        	
		        	<input type="text" name="last" id="last" readonly="readonly"> 
		    		<t:choose hiddenName="id" hiddenid="id" url="activitiController.do?goEntrust" name="entrusterList" icon="icon-search" title="用户列表" textname="last" isclear="true"></t:choose>
		    		<input id="id" name="id" readonly="readonly" type="hidden" class="inputxt">
		    	</div>
		    </div>
	</t:formvalid>
	</section>
 </body>
  <script src = "webpage/com/buss/book/tBBook.js"></script>	
  <script type="text/javascript">
  $(document).ready(function() {
		//处理选择操作人按钮样式
		$(".tabs_btn_div a[icon='icon-search']").text("");
		$(".tabs_btn_div a[icon='icon-search']").css("text-decoration","none");
		$(".tabs_btn_div a[icon='icon-search']").removeClass("easyui-linkbutton l-btn l-btn-plain");
		$(".tabs_btn_div a[icon='icon-search']").append("&nbsp;&nbsp;&nbsp;<img src=\"plug-in/media/image/searchImg.jpg\"/>&nbsp;&nbsp;");		
		$(".tabs_btn_div a[icon='icon-redo']").text("");
		$(".tabs_btn_div a[icon='icon-redo']").css("text-decoration","none");
		$(".tabs_btn_div a[icon='icon-redo']").removeClass("easyui-linkbutton l-btn l-btn-plain");
		$(".tabs_btn_div a[icon='icon-redo']").append("&nbsp;&nbsp;<img src=\"plug-in/media/image/redoImg.jpg\"/>&nbsp;&nbsp;");
	});	
  //提交后刷新数据列表
  function refreshPage(){
	  if(getParentWindow().refreshCurTab){
		  getParentWindow().refreshCurTab();
	 }
  }
  </script>
  