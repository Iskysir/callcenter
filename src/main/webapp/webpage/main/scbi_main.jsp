<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="en" class="ie8 no-js">
<head>
<meta charset="utf-8"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>决策支持平台</title>
<link rel="stylesheet"  href="plug-in/scbi/css/font-awesome.min.css" type="text/css">
<!--[if IE 7]>
<link rel="stylesheet" type="text/css" href="css/font-awesome-ie7.min.css">
<![endif]-->
<link rel="stylesheet" href="plug-in/scbi/css/Library.css" type="text/css">
<link rel="stylesheet" href="plug-in/scbi/css/main.css" type="text/css">
<script type="text/javascript" src="plug-in/scbi/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="plug-in/scbi/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="plug-in/scbi/js/main.js"></script>
<script type="text/javascript" src="plug-in/scbi/js/inputmail.js"></script>
<script type="text/javascript" src="plug-in/scbi/js/bimenu.js"></script>
<t:base type="tools"></t:base>
<!--[if lte IE 9]>
<script type="text/javascript" src="plug-in/scbi/js/html5.js"></script>
<script type="text/javascript" src="plug-in/scbi/js/PIE.js"></script>
<script type="text/javascript" src="plug-in/scbi/js/pieuser.js"></script>
<![endif]-->
<script type="text/javascript">
lhgDialog =$.dialog;
dialogStr = "userList,roleList,departList,functionList,typeGridTree,dbSourceList,iconList,mutiLangList,territoryList,categoryList,scCerTemplateList,cgformButtonList,typeValueList,processnodeList,busbaseList,myTaskList,processproList,listenerList,cgreportConfigHeadList,operationList,roleUserList,scCerTemplateList,scCerTemplateList,departUserList";
serverFullPath="${webRoot}";
var mainGisFrameWindow,mainFrameWindow;
function leftMenuClick(obj)
{
	var  win = $(window).height();
	var  hea = $('.header').height();
	var  foot = $('.footer').height();
	var mai = win - hea - foot-5;
	var  c_p = $('.current_position').height()-5;
	
	$('.mainFrame').height(mai-c_p);
	
	var url=$(obj).attr("url");
	var pid=$(obj).attr("id");
	var menuName=$(obj).attr("title");
	if("scGislayerController.do?goView"==url)
	{
		
		$("#mainGISFrame").show();
		$("#mainGISFrame").attr("src",encodeURI(url));
		$("#mainFrame").hide();
		$("#current_position").hide();
	}
	else
	{
		if(!url)
		{
			url="scbiMenuController.do?subMenu";	
		}
		
		$("#mainFrame").show();
		$("#current_position").show();
		$("#mainGISFrame").hide();
		$("#mainFrame").attr("src",encodeURI(url+"&_scbi_parentId="+pid+"&_x="+Math.random()));
		adaptiveH();
		$("#current_position").html("<span url='"+url+"' title='"+menuName+"' id='"+pid+"' onclick='leftMenuClick(this)'>"+menuName+"</span>");
	
		
		//alert(url+"&_scbi_parentId="+pid+"&_x="+Math.random());
	}
}

function topSubMenuClick(obj)
{
	var url=$(obj).attr("url");
	var pid=$(obj).attr("id");
	var menuName=$(obj).attr("title");
	var prevNode=$(obj).prev();
	prevNode.nextAll().remove();
	prevNode.after("<span url='"+url+"' title='"+menuName+"' id='"+pid+"' onclick='topSubMenuClick(this)' >&nbsp;&nbsp;>&nbsp;&nbsp;"+menuName+"</span>");
	$("#mainFrame").attr("src",encodeURI(url+"&_scbi_parentId="+pid+"&_x="+Math.random()));
}

function initBIMenu() {
	$.getJSON("loginController.do?getTree",{},function(data){
         var menuStr = 	'';
         var func ;
		  $.each(data, function(i, menu){
			  var urlHide=getUrlbyType(menu.path,"hide");
			  var urlShow=getUrlbyType(menu.path,"show");
		       menuStr += ('<li  title="'+menu.name+'" href="#" id="'+menu.id+'" url="'+menu.functionurl+'" onclick="leftMenuClick(this)"><b class="menu_ico_img" url=""><img class="img_01" src="'+urlShow+'"/><img class="img_02" src="'+urlHide+'"/></b><a>'+menu.name+'</a></li>');
		    });
		$("#menuPage").append(menuStr);
		
	});
}

function showTopMenu(obj)
{
	
	var url=$(obj).attr("url");
	var menuName=$(obj).attr("title");
	art.dialog.open(url+'&_x='+Math.random(),{title: menuName,width: '750px',id:menuName});
}

$(function(){
	initBIMenu();
});

</script>
</head>

<body>
<!--头部-->
<header class="header">
  <div class="logo_div"><img src="plug-in/scbi/images/logo.png"/></div>
  <div class="logo_title_div"><a>决策支持平台</a></div>
  <div class="shrink shrink_div"><i class="fa fa-bars "></i></div>
  <div class="Personal_div rt">
  	<div class="user_div">
    	<table>
        	<tr>
            	<td class="user_img_td tct"></td>
                <td class="user_span_td"><span>${currentOrgName}</span><br/><span style="margin-top:4px;">${realName}</span></td>
                <td class="user_i_td"><i class='fa fa-caret-right'></i></td>
            </tr>
        </table>
    </div>
    <div class="user_menu_div">
    	<ul class="menu_ul">
        	<li onclick="showTopMenu(this)" url="${ctx}/userController.do?userinfo" title="用户信息"><a href="#"><i class='fa  fa-user'></i>用户信息</a></li>
            <li onclick="showTopMenu(this)" url="${ctx}/userController.do?changepassword" title="修改密码"><a href="#"><i class='fa fa-lock'></i>修改密码</a></li>
            <li onclick="exit('loginController.do?logout','<t:mutiLang langKey="common.exit.confirm"/>')"><a href="#"><i class='fa  fa-sign-out'></i>退出</a></li>
        </ul>
    </div>
  </div>
</header>
<!--头部end-->
<!--内容mainbox-->
<section class="mainbox">
    <div class="menu_div ">
    	<!--菜单搜索UL-->
        <form class="">
        	<div class="menu_Search_div">
            	<div class="menu_Search_input_div">
                	<input type="text" maxlength="128" name="menu_Search_name" id="menu_Search_name" placeholder="点击搜索功能.." />
                    <input type="button" class="menu_S_i" value="">
                </div>
                <ul class="on_changes"></ul>
             </div>
         </form>
    	<!--菜单搜索UL end-->
        <!--一级菜单UL-->
    	<ul class="menu_ul menu_no" id="menuPage">
        	
        </ul>
        <!--一级菜单UL end-->
    </div>
    <div class="menu_div_page">
		<iframe id="mainGISFrame" name="mainGISFrame" width=100% height=99% frameborder=0 scrolling=no src="scGislayerController.do?goView"></iframe>
		<div class="current_position" style="display:none" id="current_position">
            <a href="javascript:;">综合评价</a><span>>人口信息统计</span>
        </div>
        <iframe id="mainFrame" class="mainFrame" name="mainFrame" width=100%  frameborder=0 scrolling=yes src="" style="display:none"></iframe>        	   
    </div>
</section>
<!--内容mainbox end--> 
<!--底部mainbox end--> 
<footer class="footer">
	<div class="footer_div"><span>&copy;华戎信息产业有限公司2015-2020   V1.0</span></div>
</footer>
<script type="text/javascript" src="plug-in/artDiglog/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="plug-in/artDiglog/iframeTool.js"></script>
<script type="text/javascript">

</script>
</body>


</html>

