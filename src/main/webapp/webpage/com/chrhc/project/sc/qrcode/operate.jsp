<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
	<div>
	<label>${buscode}</label>
		<a href="#" class="easyui-linkbutton" onclick="openwindow('生成二维码','qrcodeController.do?operatenew','二维码图片',1000,600)">查看二维码</a>
	</div>
    
</body>
</html>