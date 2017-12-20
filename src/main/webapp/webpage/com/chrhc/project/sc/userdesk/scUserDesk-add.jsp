<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>自定义桌面</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  
    <script type="text/javascript" src="plug-in/accordion/js/leftmenu.js"></script>
 
	<link rel="stylesheet" href="plug-in/accordion/css/icons.css" type="text/css"></link>
	<link rel="stylesheet" href="plug-in/accordion/css/accordion.css" type="text/css"></link>
	 <link type="text/css" rel="stylesheet" href="plug-in/ztree/css/zTreeStyle/zTreeStyle.css" >
	<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.excheck-3.5.js"></script> 

 
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="scUserDeskController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${scUserDeskPage.id }">
					<input id="createName" name="createName" type="hidden" value="${scUserDeskPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${scUserDeskPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${scUserDeskPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${scUserDeskPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${scUserDeskPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${scUserDeskPage.updateDate }">
					<input id="versionNum" name="versionNum" type="hidden" value="${scUserDeskPage.versionNum }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${scUserDeskPage.sysOrgCode }">
					<input id="delflag" name="delflag" type="hidden" value="${scUserDeskPage.delflag }">
					<input id="menuId" name="menuId" type="hidden" value="${scUserDeskPage.menuId }">
					<input id="deskIcon" name="deskIcon" type="hidden" value="${scUserDeskPage.deskIcon }">
					<input id="userId" name="userId" type="hidden" value="${scUserDeskPage.userId }">
		<table style="width: 220px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr style="width: 25px">
				<div>
				<ul id="menutree" class="ztree" style="float: left;width:220px"></ul>
				</div>
		</tr>
			</table>
		</t:formvalid>
 <script type="text/javascript">
 var zTreeObj;
 var ztreemenu = '${ztreemenu}';
 //alert(ztreemenu);
 var setting = {
    async: {
				enable: true,
				url: "loginController.do?getTree",
				autoParam: ["id", "name"]
			},
			callback: {
				onClick: zTreeOnClick
			}
   
 };
 
 function zTreeOnClick(event, treeId, treeNode) {
	if(!treeNode.isParent){
		$("#menuId").val(treeNode.id);
		$("#menuName").val(treeNode.name);
		
		
		$.post(
			"scUserDeskController.do?doAdd&menuId="+$("#menuId").attr("value")+"&menuName="+treeNode.name,
			 "",
			function(data){
				var eValue=eval("("+data+")");
				if(eValue.success == true){
					
					var random = Math.random();
					var urls = "scUserDeskController.do?scUserDesk&random="+random;
					windowapi.opener.location.reload(urls);
					windowapi.close();
				}else{
					$.dialog.alert("删除出现错误！");
				}
				
			});
		
	}
	else
	{
	 if(treeNode.open)
	{
		 zTreeObj.expandNode (treeNode, false, false, false, false);	 
	}
	 else
	{
		 zTreeObj.expandNode (treeNode, true, false, true, false);	 
	}
	 
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
  <script src = "webpage/com/chrhc/project/sc/userdesk/scUserDesk.js"></script>		