<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>

  <t:base type="jquery,easyui,tools,DatePicker"></t:base>

 </head>
<!--<t:menu menuFun="${menuMap}"></t:menu>-->
  
<body>

<c:forEach items="${ mostMenuList}" var="one">
	<!--  <a href="javascript:addTab('${one.functionName}','${one.functionUrl}')" >${one.functionName}</a><br/>-->
</c:forEach> 

<div>
	<ul id="menutree" class="ztree " style="float: left;"></ul>
</div>
	
  <script type="text/javascript" src="plug-in/accordion/js/leftmenu.js"></script>
<!--<script type="text/javascript" src="plug-in/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="plug-in/ztree/jquery.ztree.excheck-3.5.js"></script>
<link type="text/css" rel="stylesheet" href="plug-in/ztree/zTreeStyle.css" >
-->
<link rel="stylesheet" href="plug-in/accordion/css/icons.css" type="text/css"></link>
<link rel="stylesheet" href="plug-in/accordion/css/accordion.css" type="text/css"></link>
 <link type="text/css" rel="stylesheet" href="plug-in/ztree/css/zTreeStyle/zTreeStyle.css" >
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.excheck-3.5.js"></script> 
 <script type="text/javascript">
 var zTreeObj;
 var ztreemenu = '${ztreemenu}';
 //alert(ztreemenu);
 var setting = {
    async: {
		type:"post",
		enable: true,
				url: "loginController.do?getTree",
				autoParam: ["ID=id"]
			},
			callback: {
				onClick: zTreeOnClick
			}
   
 };
 function zTreeOnClick(event, treeId, treeNode) {
	//alert(treeId);
	// alert(treeNode.isParent);
	 if((!treeNode.isParent)){

			var t = treeNode ;
			var potionName = t.name;
			
			/*while(t.getParentNode()){
				potionName = t.getParentNode().name + "-->" + potionName;
				t = t.getParentNode();
			}*/

			//updateMostMenu(treeNode.id);
		 	addTab(potionName,treeNode.FUNCTIONURL+"&clickFunctionId="+treeNode.id);
	 }
	 else
		{
			//alert(treeNode.open);
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
function updateMostMenu(menuId){
	$.post(
		"scMostMenuController.do?mostMenuList",
		{"menuId":menuId}
	);
}
	
$(document).ready(function(){ 
 zTreeObj= $.fn.zTree.init($("#menutree"), setting);
  
 });
 
 
$(document).ajaxComplete(function(event, xhr, settings) {
	/* 绑定事件 */
	//alert(xhr.responseText.indexOf('loginController.do?login'));
	//if(xhr.responseText.indexOf('loginController.do?login') != -1&&xhr.responseText.indexOf("$(document).ajaxComplete(function(event, xhr, settings)")==-1){
	    //判断如果当前页面不为主框架，则将主框架进行跳转
	    //alert(xhr.responseText.substring(xhr.responseText.indexOf('loginController.do?login'),xhr.responseText.length));
	//  	var tagert_URL = "<%=path%>/loginController.do?login";
	//    if(self==top){
	//    	window.location.href = tagert_URL;
	//    }else{
	//    	top.location.href = tagert_URL;
	//    }
	//}
});
  </script>
  </body>
  </html>