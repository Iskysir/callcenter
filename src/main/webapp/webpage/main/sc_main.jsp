<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="en" class="ie8 no-js">
<head>
	<meta charset="utf-8" />
	<title>德州社区公共服务综合信息平台--管理平台</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="ie-stand">
	<meta http-equiv="Cache-Control" content="no-store" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<!-- BEGIN GLOBAL MANDATORY STYLES -->
    <link rel="stylesheet"  href="plug-in/scmedia/css/font-awesome.min.css" type="text/css">
	<link href="plug-in/scmedia/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/scmedia/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/scmedia/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/scmedia/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/scmedia/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>
    <link rel="stylesheet" type="text/css" href="plug-in/scmedia/jquery.easyui/themes/gray/easyui.css">
    <link rel="stylesheet" href="plug-in/scmedia/jquery.mCustomScrollbar/css/jquery.mCustomScrollbar.css" type="text/css">
    <script type="text/javascript"	src="webpage/com/chrhc/project/sc/docfile/gpy.js"></script>
        <!--[if IE 7]>
        <link rel="stylesheet" type="text/css" href="plug-in/scmedia/css/font-awesome-ie7.min.css">
        <![endif]-->
        <!--[if IE 8]>
        <link rel="stylesheet" type="text/css" href="plug-in/chrhc/ie8/ie8.css">
        <![endif]-->
            <!-- END GLOBAL MANDATORY STYLES -->
            <!-- zwt  start -->
      
     	<!-- zwt  end -->
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
				<a class="brand" href="loginController.do?login">
				<img class="logoimg" src="plug-in/scmedia/image/logo.png" alt="logo"/>
                <span>德州社区公共服务综合信息平台--管理平台</span>
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
					
					<!--  <li class="dropdown" id="header_notification_bar">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"  title="金民系统">
									<i class="fa fa-leaf fa-3x"></i>
						</a>
						<ul class="dropdown-menu extended notification">
							<li class="external">
								<a href="#"><i class=" fa fa-codepen   fa-1x"></i>婚姻登记</a>
							</li>	
							<li class="external">
								<a href="#"><i class="fa fa-umbrella  fa-1x"></i>低保户登记</a>
							</li>	
							<li class="external">
								<a href="#"><i class="fa fa-ioxhost   fa-1x"></i>养老保险登记</a>
							</li>
						</ul>
					</li>
					<li class="dropdown" id="header_notification_bar">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"  title="部门联动">
									<i class="fa fa-windows fa-3x"></i>
						</a>
						<ul class="dropdown-menu extended notification">
							<li class="external">
								<a href="#"><i class="fa fa-shield   fa-1x"></i>公安局</a>
							</li>	
							<li class="external">	
								<a href="#"><i class="fa fa-ils   fa-1x"></i>财政局</a>
							</li>	
							<li class="external">	
								<a href="#"><i class="fa  fa-university   fa-1x"></i>房管局</a>
							</li>
						</ul>
					</li>-->
					<!-- BEGIN NOTIFICATION DROPDOWN -->   
					<li class="dropdown" id="header_notification_bar" style="display:none">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-bell fa-3x"></i>
						<span class="badge">1</span>
						</a>
						<ul class="dropdown-menu extended notification">
							<li>
								<p>您有1条提醒</p>
							</li>
							<li>
								<a href="#">
								<span class="label label-success"><i class="icon-plus"></i></span>
								有新用户注册 
								<span class="time">2015-9-1 09:55:30</span>
								</a>
							</li>
							<li class="external">
								<a href="#">查看更多<i class="m-icon-swapright"></i></a>
							</li>
						</ul>
					</li>
					<!-- END NOTIFICATION DROPDOWN -->
					<!-- BEGIN INBOX DROPDOWN -->
					
					<li class="dropdown" id="header_inbox_bar" style="display:none">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<i class="fa  fa-envelope fa-3x"></i>
						<span class="badge">2</span>
						</a>
						<ul class="dropdown-menu extended inbox">
							<li>
								<p>您有两条消息</p>
							</li>
							<li>
								<a href="inbox.html?a=view">
								<span class="photo"><img src="plug-in/scmedia/image/5019d66eef7ed_200x200_3.jpg" alt="" /></span>
								<span class="subject">
								<span class="from">王珂</span>
								<span class="time">2015-9-1</span>
								</span>
								<span class="message">
								hi,好久不见，最近在忙什么?
								</span>  
								</a>
							</li>
							<li>
								<a href="inbox.html?a=view">
								<span class="photo"><img src="plug-in/scmedia/image/5019d66eef7ed_200x200_3.jpg" alt="" /></span>
								<span class="subject">
								<span class="from">李总</span>
								<span class="time">2015年9月2日</span>
								</span>
								<span class="message">
								会议暂定2015年9月2日 下午3点
								</span>  
								</a>
							</li>
							
							<li class="external">
								<a href="inbox.html">查看更多<i class="m-icon-swapright"></i></a>
							</li>
						</ul>
					</li>
					<!-- END INBOX DROPDOWN -->
					<!-- BEGIN TODO DROPDOWN -->
					<li class="dropdown" id="header_task_bar">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<i class="fa  fa-pencil fa-3x"></i>
						<span id="taskCount2" class="badge"></span>
						</a>
						<ul class="dropdown-menu extended tasks" >
							<li id="mytaskLi">
								<p>您有&nbsp;<span id="taskCount" style="color: red;font-weight:bold;">0</span>&nbsp;项任务需要办理</p>
							</li>
							<!--<li>
								<a href="#">
								<span class="task">
								<span class="desc">社区入户普查2015年9月1日-2015年9月10日</span>
							 <span class="percent">30%</span>
								</span>
								<span class="progress progress-success ">
								<span style="width: 30%;" class="bar"></span>
								</span>
								</a>
							</li> -->
											 
							<li class="external" onclick="addTab('我的任务','taskController.do?goTaskListTab');">
								<a href="javascript:void(0);">查看更多 <i class="m-icon-swapright"></i></a>
							</li>
						</ul>
					</li>
					<!-- END TODO DROPDOWN -->
					<!-- BEGIN USER LOGIN DROPDOWN -->
					<li class="dropdown user">
						
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<span class="username">${currentOrgName}</span><br/>
                        <span class="username">${realName}</span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="javascript:addTab('用户信息','userController.do?userinfo&_x='+Math.random(),'')"><i class="icon-user"></i>用户信息</a></li>
							<li><a href="javascript:addTab('修改密码','http://10.61.160.20:8080/uiap-web/editpwd','')"><i class="icon-calendar"></i>修改密码</a></li>
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
        	<ul class="page-sidebar-menu" >
				 <li class="icon_li">
                 	<div class=" fa_a" href="javascript:;">
                 		<c:forEach items="${mostMenuList}" var="one"  varStatus="status">
                 		<c:if test="${status.index==0}">
                       <div class="icon_div" onclick="addTab('${one.functionName}','${one.functionUrl}')" title="${one.functionName}"><a class="img_url" href="javascript:;"><img src="<t:icon url='${one.TSIconDesk.iconPath}' type="most"/>" onerror="this.src='plug-in/accordion/images/05默认图标_most.png'"/></a></div>
                        <span class="title"></c:if>
                        <c:if test="${ status.index!=0}"> <div class="icon_div xianshi" onclick="addTab('${one.functionName}','${one.functionUrl}')" title="${one.functionName}"><a class="img_url" href="javascript:;"><img src="<t:icon url='${one.TSIconDesk.iconPath}' type="most"/>"/></a></div></c:if>
                        </c:forEach>
                        </span>
					</div>
				</li>
         	</ul> 
            <div class="collapse_div" id="collapse_div"> 
            		<!-- BEGIN SIDEBAR MENU -->        
			<ul class="page-sidebar-menu sidebar-menu_1" id="menuTree">
                
			</ul>
			<!-- END SIDEBAR MENU -->     
			</div>
		</div>
		<!-- END SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
        	<div id="maintabs" class="easyui-tabs" data-options="tools:'#tab-tools',fit:'true'" style="width:100%;">
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
            <img src="plug-in/scmedia/image/favicon.png"/>
            &copy; &nbsp; V3.2
			</span>
		</div>
	</div>
	

	<!-- END FOOTER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<script src="plug-in/jquery/jquery-1.8.3.js" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="plug-in/scmedia/js/bootstrap.min.js" type="text/javascript"></script>
	<!--[if lt IE 9]>
	<script src="plug-in/scmedia/js/excanvas.min.js"></script>
	<![endif]-->   

	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="plug-in/scmedia/js/app.js" type="text/javascript"></script>
	<script src="plug-in/scmedia/js/index.js" type="text/javascript"></script>  
    <script src="plug-in/scmedia/jquery.easyui/jquery.easyui.min.js"></script>  
    <script src="plug-in/scmedia/jquery.mCustomScrollbar/js/jquery.mousewheel.min.js" type="text/javascript"></script>
	<script src="plug-in/scmedia/jquery.mCustomScrollbar/js/jquery.mCustomScrollbar.concat.min.js" type="text/javascript"></script>
	<script src="plug-in/scmedia/login_menu.js" type="text/javascript"></script>
	
	<t:base type="tools"></t:base>
	
	<div id="mm" class="easyui-menu" style="width: 150px;">
	    <%-- <div id="mm-tabupdate"><t:mutiLang langKey="common.refresh"/></div> --%>
	    <div id="mm-tabclose"><t:mutiLang langKey="common.close"/></div>
	    <div id="mm-tabcloseall"><t:mutiLang langKey="common.close.all"/></div>
	   <%--  <div id="mm-tabcloseother"><t:mutiLang langKey="common.close.all.but.this"/></div> --%>
	  <%--   <div class="menu-sep"></div>
	    <div id="mm-tabcloseright"><t:mutiLang langKey="common.close.all.right"/></div>
	    <div id="mm-tabcloseleft"><t:mutiLang langKey="common.close.all.left"/></div> --%>
	</div>
	
	<!-- END PAGE LEVEL SCRIPTS -->  

	<script type="text/javascript">
	function adjust(obj){  
	 	var ha;
		if($.browser.msie) { ha = 95;} //IE
		else if($.browser.safari){ ha = 95;} //谷歌
		else if($.browser.mozilla) { ha = 95;} //火狐
		else if($.browser.opera) { ha = 95;} 
		
				
		var height = $(window).height(); //浏览器当前窗口文档的高度
		$(".page-content").height(height-ha);
		$(".collapse_div").height(height-ha-60);
		$(".sidebar-menu_1").height(height-ha-60);

		$(".easyui-tabs").height(height-ha);	
	}  
	window.onload=function(){  
	  window.onresize = adjust;  
	  adjust(); 
	  
	}  
	var dialog_Loading=false;
	function showLoadingDialog()
	{
		/* alert(1);
		alert(dialog_Loading); */
		if(dialog_Loading)
		{
			dialog_Loading.show();
		}
		else
		{
			
			dialog_Loading = window.top.art.dialog({
		         title:false,
		         esc: false,
		         cancel:false,
		         content:'<img class="js_loading" src="plug-in/chrhc/images/logo_load.gif"/><span style="white-space:nowrap; margin-left:25px;"> 数据加载中，请稍后</span>'
		        });
			$(".js_loading").parent("div.aui_content").css("cssText","padding:15px 38px!important;border:5px solid #75b2d7").parent(".aui_main").css("cssText","padding-top:0px!important; border:1px solid #187ebc;");
			$(".aui_inner").css("cssText","border:none!important;");
		}
	}
	function closeLoadingDialog()
	{
		dialog_Loading.hide();
	}
		 jQuery(document).ready(function() {
			
			//弹出loading遮罩
	        /* var dialogLoading = art.dialog({
	         title:false,
	         esc: false,
	         cancel:false,
	         lock:true,
	         content:'<img src="plug-in/chrhc/images/logo_load.gif"/><span class="m-l-30" style="white-space:nowrap;"> 数据加载中…</span>',
	         time:3
	        }); */
	        //dialogLoading.close();//关闭loading
			 
			 initFirstMenu(); 
			 initMyTask();
		 	 App.init(); 
			 $('#jkxx a').click(function (e) {
 				 e.preventDefault();
  				//$(this).tab('show');
			}); 

			 $('#maintabs').tabs({
				onSelect:function (title,index){
					try{
					var w = $("iframe:visible").width();
					 $("iframe:visible").css("width",w+1 );
					}catch(e){}
				}
			});
			 //zwt
			 //gpyinit();
			 tabClose();
			 tabCloseEven();
			 
			$("#Takepic").click(function() {
				var zzyz = $("#zzyzflag").val();
				var mainTabs = window.parent.$('#maintabs');
				var currentTab=mainTabs.tabs("getSelected");
				var gpywin = currentTab.find('iframe')[0].contentWindow;
				if(zzyz=="1"){
					gpywin.zzyzTakepic();
				}else{
					 var indFlag=gpywin.scanImg();
					 if(indFlag){
						
						gpywin.Takepic(parseInt(indFlag));	
					 }else{
						//alert(gpywin.getindext());
						gpywin.addline(gpywin.photoindext);
						gpywin.Takepic(gpywin.photoindext);
						gpywin.photoindext = gpywin.photoindext + 1;
					 }	
				}
			
		});
		$("#freadcard").click(function() {
			var mainTabs = window.parent.$('#maintabs');
			var currentTab=mainTabs.tabs("getSelected");
			var gpywin = currentTab.find('iframe')[0].contentWindow;
			var indFlag=gpywin.scanImg();
			debugger;
			if(indFlag){
					
				gpywin.ReadIDCARDInfo(parseInt(indFlag));	
			}else{
				gpywin.addline(gpywin.photoindext,true);
				gpywin.ReadIDCARDInfo(gpywin.photoindext);
				gpywin.photoindext = gpywin.photoindext + 1;
			}
		});
			 
			 
		 });
     </script>
	<!-- END JAVASCRIPTS -->
    <script type="text/javascript">

    lhgDialog =$.dialog;
	dialogStr = "functionList,typeGridTree,dbSourceList,iconList,mutiLangList,territoryList,categoryList,scCerTemplateList,cgformButtonList,typeValueList,processnodeList,busbaseList,myTaskList,processproList,listenerList,cgreportConfigHeadList,operationList,roleUserList,scCerTemplateList,scCerTemplateList,departUserList";
	serverFullPath="${webRoot}";

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
    
		$(document).on('click','.add', function(){
			var pahei = $(".page-content").height()-60;
				var	index = $(this).text();
				var sr = 'index.html';
					if( index == 'tab页'){sr = 'index_2.html'}
					if( index == '信息发布页'){sr = 'index_3.html'}
					if( index == '快捷业务1'){sr = 'saomiao/saomiao.html'}
					if( index == '快捷业务2'){sr = 'saomiao/kuaijie.html'}
					
				
		});

		
	
		var userAgent = navigator.userAgent.toLowerCase(); 
			// Figure out what browser is being used 
			jQuery.browser = { 
				version: (userAgent.match( /.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/ ) || [])[1], 
				safari: /webkit/.test( userAgent ), 
				opera: /opera/.test( userAgent ), 
				msie: /msie/.test( userAgent ) && !/opera/.test( userAgent ), 
				mozilla: /mozilla/.test( userAgent ) && !/(compatible|webkit)/.test( userAgent ) 
			}; 
	</script>
    <script>
    function adjust(obj){  
     	var ha=0;
    	if($.browser.msie) { ha = 95;} //IE
    	else if($.browser.safari){ ha = 95;} //谷歌
    	else if($.browser.mozilla) { ha = 95;} //火狐
    	else if($.browser.opera) { ha = 95;} 
    	else { alert("i don't konw!"); } 
    			
    	var height = $(window).height(); //浏览器当前窗口文档的高度
    	$(".page-content").height(height-ha);
    	$(".collapse_div").height(height-ha-60);
    	$(".sidebar-menu_1").height(height-ha-60);
    	//加滚动条，设置宽度
    	$(".sidebar-menu_1").width("226px");
    	$(".easyui-tabs").height(height-ha);	
    	
    	
    	$(".panel .panel-body div iframe").height(height-160);
    	$(".tabs-panels").height(height-141);	
    	$(".panel-body").height(height-141);
    }  
    window.onload=function(){  
      window.onresize = adjust;  
      adjust();  
    }

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
    <!-- 	<script type="text/javascript" src="plug-in/artDiglog/jquery.artDialog.js?skin=default"></script> -->
    <script type="text/javascript" src="plug-in/artDiglog/artDialog.js?skin=blue"></script>
	<!--[if lte IE 9]>
    <script src="plug-in/scmedia/js/html5.js" type="text/javascript"></script>
    <script src="plug-in/scmedia/js/PIE.js" type="text/javascript"></script>
    <script src="plug-in/scmedia/js/pieuser.js" type="text/javascript"></script>
    <link href="plug-in/scmedia/css/style_ie.css" rel="stylesheet" type="text/css"/>
    <![endif]-->
    
	<%--高拍仪画面集成区域 style="width:1px;height:1px" --%>
	<div  class="container gpy-card" id="gaopaiyi" style="position: absolute; top:0px; left:0px;">
		<div class="row">
			<div class="col-md-12" style="text-align:center">
					<object classid="clsid:6CA705D0-BB6E-46DF-BE44-64809B0B0E36"
						id=VIDEOCAP CODEBASE="*.cab#version=0,0,0,0" width=1 height=1>
					</object>
			</div>
		</div>
		<div class="row" id ='gpyrow1'style="display:none" >
			<div class="col-md-12 center" >
				
					<button class="btn btn-default" type="button"
						onClick="start_MainCampreview()">
						<i class="gpyicon gpyicon-zsxt" ></i>&nbsp;主摄像头
					</button>
					<button class="btn btn-default" type="button"
						onClick="start_SubCampreview()">
						<i class="gpyicon gpyicon-fsxt"></i>&nbsp;副摄像头
					</button>
					<button class="btn btn-default" type="button"
						onClick="RotateLeft()">
						<i class="gpyicon gpyicon-zx"></i>&nbsp;左旋&nbsp;
					</button>
					<button class="btn btn-default" type="button"
						onClick="RotateRight()">
						<i class="gpyicon gpyicon-yx"></i>&nbsp;右旋&nbsp;
					</button>
					<%--  <button class="btn btn-default" type="button" onClick="ZoomIn()" style="padding-left:3px">
						<i class="gpyicon gpyicon-fd"></i>&nbsp;放大
					</button>
					<button class="btn btn-default" type="button" onClick="ZoomOut()" style="padding-left:3px">
						<i class="gpyicon gpyicon-sx"></i>&nbsp;缩小
					</button>--%>
				
			</div>
		</div>
		<div class="row" id="gpyrow2"style="display:none" >
			<div class="col-md-12 center">
				<button type="button" class="btn btn-sure" id="freadcard">
					<i class="gpyicon gpyicon-read"></i>&nbsp;读取其他身份证
				</button>
				<button type="button" class="btn btn-blue" id="Takepic">
					<i class="gpyicon gpyicon-photograph"></i>&nbsp;拍照
				</button>
			</div>
		</div>
	</div>
    <input type="hidden" id ="zzyzflag" name="zzyzflag">
    
	<!-- 引入切换系统浮动框 -->
    <!-- 引入切换系统浮动框  -->
    <!--<script src="http://10.61.160.20:8080/uiap-web/resources/general/common/boarder/boarder.js?user=${userName }" type="text/javascript"></script>-->
	<!-- 引入证明报表url zwt 20151202 -->
	<script type="text/javascript">
	var PROVREPORTURL="http://localhost:9080/chrhcreportprov/ReportServer?reportlet=";//8100
	//取二维码地址IP与端口号
	var ZIP = "localhost:9080";//"10.61.160.20:8090";
	</script>
<script type="text/javascript">
function printprov(id){
	printstatus(id);
}
//改变打印状态
function printstatus(idvalue){

		//****************ajax 调用  更改打印状态  部署时更改成相应的ip****************//
		  var url="http://localhost:9080/sc/scCerTemplateController.do?printStatusAndNumbyID&id="+idvalue;//10.61.160.20:18090
		 // debugger;
		   $.ajax({
		     url:url,
		     dataType:'jsonp',
		     //processData: false, 
		     async:false,
		     type:'get',
		     jsonpCallback:"datas",
		     success:function(data){
		    	 //alert(data);
		     },
		     error:function(XMLHttpRequest, textStatus, errorThrown) {
		       //alert(XMLHttpRequest.status+"----"+XMLHttpRequest.readyState+"---"+textStatus);
		     }
		     });
}

</script>
</body>
<!-- END BODY -->
</html>