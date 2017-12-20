<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title> </title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  
    <script type="text/javascript" src="plug-in/accordion/js/leftmenu.js"></script>
 
	<link rel="stylesheet" href="plug-in/accordion/css/icons.css" type="text/css"></link>
	<link rel="stylesheet" href="plug-in/accordion/css/accordion.css" type="text/css"></link>
	 <link type="text/css" rel="stylesheet" href="plug-in/ztree/css/zTreeStyle/zTreeStyle.css" >
	<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.excheck-3.5.js"></script> 

 
 </head>
 <body>
 
		
		
		
<div>
	<ul id="menutree" class="ztree" style="float: left;"></ul>
</div>


 <script type="text/javascript">
 var zTreeObj;
 var ztreemenu = '${ztreemenu}';
 //alert(ztreemenu);
 var setting = {
    async: {
				enable: true,
				url: "systemController.do?getDepUserTree",
				autoParam: ["id", "name"]
			},
			callback: {
				onClick: zTreeOnClick
			} ,
		check:{
			enable: true,
			chkboxType: { "Y" : "s", "N" : "s" }
		} 
   
 };
 
 function zTreeOnClick(event, treeId, treeNode) {
	if(!treeNode.isParent){
		$("#menuId").val(treeNode.id);
		$("#menuName").val(treeNode.name);
	}
	
};

 
	
	$(document).ready(function(){ 
	 	zTreeObj= $.fn.zTree.init($("#menutree"), setting);
	});
 
 
$(document).ajaxComplete(function(event, xhr, settings) {
	/* 绑定事件 */
	//alert(xhr.responseText.indexOf('loginController.do?login'));
	if(xhr.responseText.indexOf('loginController.do?login') != -1&&xhr.responseText.indexOf("$(document).ajaxComplete(function(event, xhr, settings)")==-1){
	    //判断如果当前页面不为主框架，则将主框架进行跳转
	    //alert(xhr.responseText.substring(xhr.responseText.indexOf('loginController.do?login'),xhr.responseText.length));
	  	var tagert_URL = "<%=path%>/loginController.do?login";
	    if(self==top){
	    	window.location.href = tagert_URL;
	    }else{
	    	top.location.href = tagert_URL;
	    }
	}
});
  </script>
 </body>
