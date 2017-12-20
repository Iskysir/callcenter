<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/context/mytags.jsp"%>
<%String gisUrl= path+"/webpage/system/gis/gisdemo.jsp?config={enableMeasure:true,enableLayerControl:true,enableDataEdit:true,enableQuery:true,enableSpotClickForMain:true}";%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8" />
	<title>社区管理平台</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="ie-stand">
	<meta http-equiv="Cache-Control" content="no-store" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<!-- BEGIN GLOBAL MANDATORY STYLES -->
    <link rel="stylesheet"  href="plug-in/media/css/font-awesome.min.css" type="text/css">
	<link href="plug-in/media/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>
    <link rel="stylesheet" type="text/css" href="plug-in/media/jquery.easyui/themes/gray/easyui.css">
        <!--[if IE 7]>
        <link rel="stylesheet" type="text/css" href="css/font-awesome-ie7.min.css">
        <![endif]-->
            
 
 <script src="webapp/main/leftnew.jsp" type="text/javascript"></script>
 
 
	<t:base type="jquery,tools"></t:base>
	<script src="plug-in/media/js/bootstrap.min.js" type="text/javascript"></script>
	<!--[if lt IE 9]>
	<script src="media/js/excanvas.min.js"></script>
	<![endif]-->   
    <!--[if lte IE 9]>
    <script src="media/js/html5.js" type="text/javascript"></script>
    <script src="media/js/PIE.js" type="text/javascript"></script>
    <script src="media/js/pieuser.js" type="text/javascript"></script>
    <![endif]-->
	<script src="plug-in/media/js/app.js" type="text/javascript"></script>
	<script src="plug-in/media/js/index.js" type="text/javascript"></script>  
    <script type="text/javascript" src="plug-in/media/jquery.easyui/jquery.easyui.min.js"></script>      
	<script src="plug-in/media/login_new.js" type="text/javascript"></script>  
	<script type="text/javascript"
	src="plug-in/artDiglog/jquery.artDialog.js?skin=default"></script>
	<script type="text/javascript">
		lhgDialog =$.dialog;
		dialogStr = "functionList,typeGridTree,dbSourceList,iconList,mutiLangList,territoryList,categoryList,cgformButtonList,typeValueList,processnodeList,busbaseList,myTaskList,processproList,listenerList,cgreportConfigHeadList,operationList,roleUserLists,departUserList";
		serverFullPath="${webRoot}";
		gisUrl="<%=gisUrl%>";
		// sclient 客户端 文件下载
		function downloadSclient()
		{
			//alert(1);
			window.top.art.dialog({ 
				title:"客户端控件下载", 
				content:'请点击【下载】按钮安装客户端控件，安装完毕后重启IE浏览器、拔插设备后再登录。', 
				ok:function(){ 
					//$.download(serverFullPath+"/sclient/download.do", "","post"); 
					window.open(serverFullPath+"/sclient/download.do");
				}, 
				okVal:"下载", 
				cancel:function(){ 

				}, 
				cancelVal:"返回", 
				id: "dialog_sclientdownload", 
				lock:false

				});
		}
		 jQuery(document).ready(function() { 
		 	App.init();  

			var ha;
			if($.browser.msie) { ha = 95;} //IE
		 	else if($.browser.safari){ ha = 105;} //谷歌
			else if($.browser.mozilla) { ha = 105;} //火狐
			else if($.browser.opera) { ha = 105;} 
			else { alert("i don't konw!"); } 
			var height = $(window).height(); //浏览器当前窗口文档的高度
			$(".page-content").height(height-ha);
			$(".easyui-tabs").height(height-ha);
			$(".page-sidebar").height(height-ha-20);


			initFirstMenu();
 
			
		 });

		 /*$(document).live('click','li .add', function(){
			//alert(1);
			 var pahei = $(".page-content").height()-60;
			var	index = $(this).text();
			$('#tt').tabs('add',{
				title: 'Tab'+index,
				content: '<div style="padding:10px"><iframe frameborder="0" scrolling="no" src="scUserDeskController.do?scUserDesk" style="width:100%;height:'+pahei+'px" ></iframe></div>',
				closable: true
			});  
		});*/
	
		var userAgent = navigator.userAgent.toLowerCase(); 
			// Figure out what browser is being used 
			jQuery.browser = { 
				version: (userAgent.match( /.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/ ) || [])[1], 
				safari: /webkit/.test( userAgent ), 
				opera: /opera/.test( userAgent ), 
				msie: /msie/.test( userAgent ) && !/opera/.test( userAgent ), 
				mozilla: /mozilla/.test( userAgent ) && !/(compatible|webkit)/.test( userAgent ) 
			}; 
 
			/**
			 * 退出确认框
			 * 
			 * @param url
			 * @param content
			 * @param index
			 */
			function exit(url, content) {
				var dialog;
				if($.dialog.confirm)
				{
					dialog=$.dialog;
				}
				else
				{
					dialog=window.top.lhgDialog;
				}
				dialog.confirm(content, function(){
					window.location = url;
				}, function(){
				}).zindex();
			}
     </script>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<div class="header navbar navbar-inverse navbar-fixed-top">
		<!-- BEGIN TOP NAVIGATION BAR -->
		<div class="navbar-inner">
			<div class="container-fluid">
				<!-- BEGIN LOGO -->
				<a class="brand" >
				<img class="logoimg" src="plug-in/media/image/logo.png" alt="logo"/>
                <span  style="font-weight:500;">社区管理平台</span>
				</a>
				<!-- 收缩菜单按钮 -->
				<div class="sid"><i class="fa fa-bars "></i></div>
				<!-- 收缩菜单按钮 end-->
				<!-- END LOGO -->
				<!-- BEGIN RESPONSIVE MENU TOGGLER -->
				<a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse">

				<!-- END RESPONSIVE MENU TOGGLER -->            
				<!-- BEGIN TOP NAVIGATION MENU -->              
				<ul class="nav pull-right">
					<!-- BEGIN NOTIFICATION DROPDOWN -->   
					<li class="dropdown" id="header_notification_bar">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-bell fa-3x"></i>
						<span class="badge">6</span>
						</a>
						<ul class="dropdown-menu extended notification">
							<li>
								<p>You have 14 new notifications</p>
							</li>
							<li>
								<a href="#">
								<span class="label label-success"><i class="icon-plus"></i></span>
								New user registered. 
								<span class="time">Just now</span>
								</a>
							</li>
							<li>
								<a href="#">
								<span class="label label-important"><i class="icon-bolt"></i></span>
								Server #12 overloaded. 
								<span class="time">15 mins</span>
								</a>
							</li>
							
						</ul>
					</li>
					<!-- END NOTIFICATION DROPDOWN -->
					<!-- BEGIN INBOX DROPDOWN -->
					<li class="dropdown" id="header_inbox_bar">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<i class="fa  fa-envelope fa-3x"></i>
						<span class="badge">5</span>
						</a>
						<ul class="dropdown-menu extended inbox">
							<li>
								<p>You have 12 new messages</p>
							</li>
							<li>
								<a href="inbox.html?a=view">
								<span class="photo"><img src="plug-in/media/image/5019d66eef7ed_200x200_3.jpg" alt="" /></span>
								<span class="subject">
								<span class="from">Lisa Wong</span>
								<span class="time">Just Now</span>
								</span>
								<span class="message">
								Vivamus sed auctor nibh congue nibh. auctor nibh
								auctor nibh...
								</span>  
								</a>
							</li>
						</ul>
					</li>
					<!-- END INBOX DROPDOWN -->
					<!-- BEGIN TODO DROPDOWN -->
					<li class="dropdown" id="header_task_bar">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<i class="fa  fa-pencil fa-3x"></i>
						<span class="badge">5</span>
						</a>
						<ul class="dropdown-menu extended tasks">
							<li>
								<p>You have 12 pending tasks</p>
							</li>
							<li>
								<a href="#">
								<span class="task">
								<span class="desc">New release v1.2</span>
								<span class="percent">30%</span>
								</span>
								<span class="progress progress-success ">
								<span style="width: 30%;" class="bar"></span>
								</span>
								</a>
							</li>
							<li>
								<a href="#">
								<span class="task">
								<span class="desc">Application deployment</span>
								<span class="percent">65%</span>
								</span>
								<span class="progress progress-danger progress-striped active">
								<span style="width: 65%;" class="bar"></span>
								</span>
								</a>
							</li>
						</ul>
					</li>
					<!-- END TODO DROPDOWN -->
					<!-- BEGIN USER LOGIN DROPDOWN -->
					<li class="dropdown user">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						 <div class="icon_div"><img src="plug-in/media/image/5019d66eef7ed_200x200_3.jpg"/></div>
						<span class="username">${userName }</span>
						<i class="icon-angle-down"></i>
						</a>
						<ul class="dropdown-menu">
							<li><a href="javascript:addTab('用户信息','userController.do?userinfo&_x='+Math.random(),'')"><i class="icon-user"></i>用户信息</a></li>
							<li><a href="javascript:addTab('修改密码','userController.do?changepassword','')"><i class="icon-calendar"></i>修改密码</a></li>
							<li class="divider"></li>
							<li><a onclick="exit('loginController.do?logout','<t:mutiLang langKey="common.exit.confirm"/>')"><i class="icon-lock"></i>退出</a></li>
						</ul>
					</li>
					<!-- END USER LOGIN DROPDOWN -->
				</ul>
				<!-- END TOP NAVIGATION MENU --> 
			</div>
		</div>
		<!-- END TOP NAVIGATION BAR -->
	</div>
	<!-- END HEADER -->
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<div class="page-sidebar nav-collapse collapse">
			<!-- BEGIN SIDEBAR MENU -->        
			<ul class="page-sidebar-menu" id="menuTree">
				 <li class="icon_li">
                 	<a class=" fa_a" href="javascript:;">
	                 	<c:forEach items="${mostMenuList}" var="one">
							<div class="icon_div xianshi" onclick="addTab('${one.functionName}','${one.functionUrl}')" ><i class="fa fa-user  fa_coler1"></i></div>
							<div  class="layer_notice" style="display:none;" > ${one.functionName} </div>
						</c:forEach> 
						
					</a>
				</li>
			</ul>
			<!-- END SIDEBAR MENU -->
		</div>
		<!-- END SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
        	<div id="maintabs" class="easyui-tabs" data-options="tools:'#tab-tools'" style="width:99%;">
            </div>
            <div id="tab-tools">
            </div>
		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<div class="footer">
		<div class="footer-inner">
			<i class="fa fa-question-circle"></i> QA <i class="fa fa-phone"></i> 技术支持电话：0531-88888888
		</div>
		<div class="footer-tools">
			<span style="width:auto; white-space:nowrap;">
            <img src="plug-in/media/image/favicon.png"/>
            &copy;华戎信息产业有限公司2015-2020 &nbsp; V2.0
			</span>
		</div>
	</div>
	<!-- END FOOTER -->
	
<script type="text/javascript">
	$(document).on('mouseover', '.xianshi', function () {  
 		var a = $('.layer_notice').html();
  		layer.tips(a , this, {
  			style: ['background-color:#78BA32; color:#fff', '#78BA32'],
			maxWidth:185,
			time: 0,
				closeBtn:false,
			offset: ['100', '10'],
		});
  		alert(a);
		});
	$(document).on('mouseleave', '.xianshi', function () {            
  			layer.closeTips();
		});
</script>
	
</body>
<!-- END BODY -->
</html>