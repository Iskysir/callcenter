
 
 <%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html>
<html>
 <head>
  <title>消息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 
    <script type="text/javascript" src="plug-in/accordion/js/leftmenu.js"></script>
	<link rel="stylesheet" href="plug-in/accordion/css/icons.css" type="text/css"></link>
	<link rel="stylesheet" href="plug-in/accordion/css/accordion.css" type="text/css"></link>
	<link type="text/css" rel="stylesheet" href="plug-in/ztree/css/zTreeStyle/zTreeStyle.css" >
	<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.excheck-3.5.js"></script> 
	
	<link rel="stylesheet"  href="plug-in/media/css/font-awesome.min.css" type="text/css">
	<link href="plug-in/media/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>
    <link rel="stylesheet" type="text/css" href="plug-in/media/jquery.easyui/themes/gray/easyui.css">
	
	
	
	
	<script type="text/javascript">
	//编写自定义JS代码
	var zTreeObj;
	var ztreemenu = '${ztreemenu}';
	var setting = {
		data: {
			simpleData: {
				enable: true
			}
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
	 
	};

 
	function getSelDept(){
		var ids = "" ;
		var nodes = zTreeObj.getCheckedNodes(true);
		 $.each(nodes,function(i,item){
			//if(!item.isParent){
				ids += (item.orgcode + ",");
			//}
		 });
		 return ids;
	}

	
	$(document).ready(function(){ 
		$.getJSON("systemController.do?getDeptTree",{},function(data){
		 	zTreeObj= $.fn.zTree.init($("#menutree"), setting,data);
		});
	});
 
  </script>
 </head>
 <body style="background-color:#fff !important">
 
 <section id="mainbox"> 
 
	<div class="tab-content tab-box datagrid">
	  	<div class="Current_position">
	    	<img src="plug-in/media/image/dingwei.png"/><span style="font-size:14px; color:#666666;">当前所在的位置：</span>
			<script type="text/javascript">
				if(parent.url_title_map[ location.href + "#"]){
					document.write(parent.url_title_map[ location.href + "#"]);
				}
			</script>
	    </div>
	</div>

    <div class="Information_div" style="height: 754px;">
    	<div class="Information_div_lt" style="height: 85%;" >
    		<ul>请选择接收对象：</ul>
            <ul id="menutree" class="ztree"></ul>
        </div>
        <div class="Information_div_rt">
       		<div class="" >
       			<iframe src="scMessageController.do?right"  scrolling="no" frameborder="0" height="600px" width="100%" ></iframe>
       		</div>
        </div>
    </div>
</section>
 
 </body>
 