<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="en" class="ie8 no-js">
<head>
<meta charset="utf-8"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="ie-stand">
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>sencondaryMenu</title>
<link rel="stylesheet"  href="plug-in/scbi/css/font-awesome.min.css" type="text/css">
<!--[if IE 7]>
<link rel="stylesheet" type="text/css" href="css/font-awesome-ie7.min.css">
<![endif]-->
<link rel="stylesheet" href="plug-in/scbi/css/Library.css" type="text/css">
<link rel="stylesheet" href="plug-in/scbi/css/style.css" type="text/css">
<script type="text/javascript" src="plug-in/scbi/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="plug-in/scbi/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="plug-in/scbi/js/bimenu.js"></script>
<!--[if lte IE 9]>
<script type="text/javascript" src="plug-in/scbi/js/html5.js"></script>
<script type="text/javascript" src="plug-in/scbi/js/PIE.js"></script>
<script type="text/javascript" src="plug-in/scbi/js/pieuser.js"></script>
<![endif]-->
<script type="text/javascript">
function subMenuClick(obj)
{
	//adaptiveH();
	var url=$(obj).attr("url");
	var pid=$(obj).attr("id");
	var menuName=$(obj).attr("title");
	var html=window.top.$("#current_position").html();
	html=html.replace("SPAN","A");
	html=html.replace("SPAN","A");
	html=html.replace("span","a");
	html=html.replace("span","a");
	html=html+"<span url='"+url+"' title='"+menuName+"' id='"+pid+"' onclick='topSubMenuClick(this);'>&nbsp;&nbsp;>&nbsp;&nbsp;"+menuName+"</span>";
	window.top.$("#current_position").html(html);
	window.location.href = encodeURI(url+"&_scbi_parentId="+pid+"&_x="+Math.random());
}
function initSubMenu() {

	//alert("loginController.do?getTree&id=${parentMenuId}"+"&_x="+Math.random());
	$.getJSON("loginController.do?getTree&id=${parentMenuId}"+"&_x="+Math.random(),{},function(data){
         var menuStr = 	'';
		  $.each(data, function(i, menu){
			  //var urlDesk=getUrlbyType(menu.path,"desk");
			  var urlDesk=menu.path;
			  //alert(menu.functionurl);
		       menuStr += ('<a title="'+menu.name+'" class="menu_item" id="'+menu.id+'" url="'+menu.functionurl+'" onclick="subMenuClick(this)" href="javascript:;" style="background: url('+urlDesk+') no-repeat center top;">'+menu.name+'</a>');
		    });
		$("#menu_list").append(menuStr);
		
	});
}
$(function(){
	initSubMenu();
});
</script>
</head>

<body>
    <div class="menu_list" id="menu_list">
        <!--  <a class="menu_item" href="javascript:;" style="background: url(plug-in/scbi/images/icons/zhpj/i44.png) no-repeat center top;">各社区受理量TOP10排名</a>
        <a class="menu_item" href="javascript:;" style="background: url(plug-in/scbi/images/icons/zhpj/i45.png) no-repeat center top;">各区县受理量排名</a>
        <a class="menu_item" href="javascript:;" style="background: url(plug-in/scbi/images/icons/zhpj/i46.png) no-repeat center top;">各业务部门受理量排名</a>
        <a class="menu_item" href="javascript:;" onclick="changePage('qssll.html')" style="background: url(plug-in/scbi/images/icons/zhpj/i47.png) no-repeat center top;">各市受理量排名</a>
        <a class="menu_item" href="javascript:;" style="background: url(plug-in/scbi/images/icons/zhpj/i48.png) no-repeat center top;">民政对象按社区统计</a>
        <a class="menu_item" href="javascript:;" style="background: url(plug-in/scbi/images/icons/zhpj/i49.png) no-repeat center top;">独生子女费发放按社区统计</a>
        <a class="menu_item" href="javascript:;" style="background: url(plug-in/scbi/images/icons/zhpj/i50.png) no-repeat center top;">社会保险统计</a>
        <a class="menu_item" href="javascript:;" style="background: url(plug-in/scbi/images/icons/zhpj/i51.png) no-repeat center top;">开具证明按社区统计</a>
        <a class="menu_item" href="javascript:;" onclick="changePage('rkxxtj.html')" style="background: url(plug-in/scbi/images/icons/zhpj/i52.png) no-repeat center top;">人口信息统计</a>
        <a class="menu_item" href="javascript:;" style="background: url(plug-in/scbi/images/icons/zhpj/i53.png) no-repeat center top;">即办件统计</a>
        <a class="menu_item" href="javascript:;" style="background: url(plug-in/scbi/images/icons/zhpj/i54.png) no-repeat center top;">消息发布统计</a>
        <a class="menu_item" href="javascript:;" style="background: url(plug-in/scbi/images/icons/zhpj/i55.png) no-repeat center top;">党组信息统计</a>
        <a class="menu_item" href="javascript:;" style="background: url(plug-in/scbi/images/icons/zhpj/i56.png) no-repeat center top;">志愿者信息统计</a>
        <a class="menu_item" href="javascript:;" style="background: url(plug-in/scbi/images/icons/zhpj/i57.png) no-repeat center top;">人口密度统计</a>-->
    </div>

    <script type="text/javascript">
        function changePage(uurl){
            //$(".menu_list").addClass("donghua");
            window.location.href = uurl;
        }
    </script>

</body>



</html>

