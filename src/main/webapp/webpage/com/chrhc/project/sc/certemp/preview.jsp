<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<link href="plug-in/chrhc/bootstrap.css" rel="stylesheet">
<link href="plug-in/chrhc/style.css" rel="stylesheet">

<%
	double number=Math.random()*100000;
%>

<img align="right" width="300px"  height="300px" style="float: right !important;" alt="二维码" src="qrcodeController.do?qrcode&keycode=${qrcode} &id=${id}&<%=number%>"></img>

${content}
<div class = "col-md-12 center">
<div style="width: 500px; margin:0 auto;">
	<input class='noprint btn-sure' id='btnPrint'  type='button' value='打印' onclick='javascript:statusAndNum();'   style="float:left;"/> 
	<input class='noprint btn-cancel' type="button" value='关闭' onclick="closeCurrentTab();"  style="float:left;"/>
</div>

</div>
<style type='text/css' media=print>
 .noprint{display : none }
 </style> 
 <script type="text/javascript">
 function statusAndNum(){
	
	 $.ajax({
			url:"scCerTemplateController.do?printStatusAndNum&id=${id}&tableName=${tableName}",
			async:true,
			cache:false,
			type:'GET',
			success:function(data){
				window.print();
			},
			error:function(){}
		});
 }
 
</script>
