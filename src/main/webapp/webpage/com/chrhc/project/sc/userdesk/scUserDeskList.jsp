<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
 <head>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<%
	String[] colors = {"blue","green","cyan","purple","yellow","orange","red","royalblue"};
	
%>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
    <link rel="stylesheet"  href="plug-in/scmedia/css/font-awesome.min.css" type="text/css">
	<link href="plug-in/scmedia/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/scmedia/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/scmedia/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/scmedia/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/scmedia/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>


<script type="text/javascript">
<!--

serverFullPath="${webRoot}";


function choose_menu(){
	$.dialog({
		content: "url:scUserDeskController.do?goAdd",
		lock : true,
		title:"选择",
		width:250,
		height: 400,
		cache:false,
		parent:windowapi,
	   
	    cancelVal: '关闭',
	//    cancel: true /*为true等价于function(){}*/
	   cancel:function(){},
	}).zindex();
	
//	window.parent.location.reload(true);
}
function del_menu(id){
	delObjNew('确定删除吗?', function(r) {
	$.post(
		"scUserDeskController.do?doDel&id=" + id,
		"",
		function(data){
			var eValue=eval("("+data+")");
			if (eValue.success == true) {
				var random = Math.random();
				var urls = "scUserDeskController.do?scUserDesk&random="+random;
				window.location.reload(urls); 
			}else{
				tip("删除出现错误！");
			}
		}
	);

//	window.location.replace(urls); 
	});
}
//-->
$(document).ready(function(){
	var count = $("i");
	if(count.length > 8){
		$("#addTag").hide();
	}
	
});

function openPage(functionName , functionUrl , userDeskId){
	if(!parent.url_title_map[serverFullPath + "/" + functionUrl] ){
		parent.url_title_map[serverFullPath + "/" + functionUrl] = functionName;
		parent.url_title_map[serverFullPath + "/" + functionUrl + "#"] = functionName;
	}
	parent.addTab(functionName , functionUrl , userDeskId);
}

</script>
 </head>
 
<c:set var="colors_value" value="blue,green,cyan,purple,yellow,orange,red,royalblue" />
<c:set var="delim" value=","/> 
<c:set var="colors" value="${fn:split(colors_value, delim)}"/> 

<body class="syin_body">
	<div class="Current_position">
    	<img src="plug-in/media/image/dingwei.png"/><span>当前所在的位置：</span><a href="javascript:;" class="last">首页</a>
    </div>
<div class="inde_div">

<c:forEach items="${userDesk}" var="one" varStatus="status">

	 	<div class="sy_div ${colors[status.index] } ">
			<i class="fa fa-remove fa-2x" onclick=" del_menu('${one.userDeskEntity.id}')"></i>
			<img src="<t:icon iconId='${one.function.TSIconDesk.id}'  type='desk'/>" onerror="this.src='plug-in/accordion/images/05默认图标_desk.png'"  onclick="javascript:openPage('${one.function.functionName}','${one.function.functionUrl}','${one.userDeskEntity.menuId}')"/>
			<span class="sy_span">${one.function.functionName}</span>
		</div >
</c:forEach>
	<div id = "addTag" class="sy_div gray">
		<i class="fa  fa-2x gray"></i>
			<img id="addImg"  src="plug-in/media/image/add.png" onclick="choose_menu()">
			<span class="sy_span"></span>
	</div>
</div>

</body>
    <!--[if lte IE 9]>
    <script src="plug-in/scmedia/js/html5.js" type="text/javascript"></script>
    <script src="plug-in/scmedia/js/PIE.js" type="text/javascript"></script>
    <script src="plug-in/scmedia/js/pieuser.js" type="text/javascript"></script>
    <![endif]-->
</html>