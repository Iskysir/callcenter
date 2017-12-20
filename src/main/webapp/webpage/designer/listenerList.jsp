<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
	<head>
		<title>监听列表</title>
		<t:base type="jquery,easyui,tools"></t:base>
	</head>
	<body style="overflow-y: hidden" scroll="no">		
		<t:datagrid checkbox="true" name="listenerList" actionUrl="processController.do?listenerGrid&typeid=${typeid}&status=1"  idField="id" pagination="false">	 	
		<t:dgCol title="id" hidden="true" field="id"></t:dgCol>
		<t:dgCol title="名称" field="listenername" width="20"></t:dgCol>
		<t:dgCol title="事件" field="listenereven" width="40"></t:dgCol>
		<t:dgCol title="执行类型" field="listenertype" width="40"></t:dgCol>
		<t:dgCol title="执行内容" field="listenervalue" width="40"></t:dgCol>
	</t:datagrid>							
	</body>
</html>