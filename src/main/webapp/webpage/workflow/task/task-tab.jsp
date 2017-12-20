<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
 <link href="plug-in/media/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="plug-in/media/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/style.css" rel="stylesheet" type="text/css"/>
 	<link href="plug-in/media/css/style-add.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>
   	<link href="plug-in/media/jquery.easyui/themes/gray/easyui.css" rel="stylesheet" type="text/css" >
<t:tabs  id="tt" iframe="false" tabPosition="top">
	<t:tab  href="taskController.do?goTaskForm&taskId=${taskId }" icon="icon-search" title="附加页面" id="formPage"></t:tab>
	<t:tab  href="taskController.do?goTaskOperate&taskId=${taskId }" icon="icon-search" title="任务处理" id="taskOperate"></t:tab>
	<t:tab  href="activitiController.do?viewProcessInstanceHistory&processInstanceId=${processInstanceId }" icon="icon-search" title="流程图" id="processPicture"></t:tab>
</t:tabs>