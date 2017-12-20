<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>证明类型选择</title>
  <link href="plug-in/chrhc/bootstrap.css" rel="stylesheet">
<link href="plug-in/chrhc/style.css" rel="stylesheet">
<link href="plug-in/icheck/skin/square/blue.css" rel="stylesheet">
<style>
	.form-control {display: inline-block !important;}
</style>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script src="plug-in/chrhc/bootstrap.min.js"></script>
<script src="plug-in/chrhc/ie8/html5.js"></script>
<script src="plug-in/chrhc/ie8/respond.min.js"></script>
<script src="plug-in/icheck/icheck.js"></script>
<script src="plug-in/chrhc/jquery.tabs.js"></script>
<script src="plug-in/chrhc/jquery.lazyload.js"></script>
  
 </head>
<body>
<input type="text" id="id" value='${id }' style="display:none">
<input type="text" id="dtKey" value='${dtKey }' style="display:none">
<input type="text" id="tableName" value='${tableName }' style="display:none">

</body>
 <script type="text/javascript">
  $(function($){
  	$("#formobj").attr({"class":"form-inline valideForm"});
	$("select").attr({"class":"form-control w260"});
	aa();
	function aa(){
		$.post(
			      "scDataTransconfController.do?transData",
			      {
				  	"dtKey" : $("input[id='dtKey']").val(),
				  	"id" : $("input[id='id']").val()
				  },
			      function(data) {
						var obj = jQuery.parseJSON(data);
	//					  parent.parent.addTab("证明打印", obj.attributes.url);
						location.href=obj.attributes.url;
	 });
	}
  });
  </script>	
</html>