<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%String gisUrl= path+"/webpage/system/gis/gisdemo.jsp?config={enableMeasure:true,enableLayerControl:true,enableDataEdit:true,enableQuery:true,enableSpotClickForMain:true}";%>
<!DOCTYPE html >
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="ie-stand">
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />

<title><t:mutiLang langKey="jeect.platform" /></title>

<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<script type="text/javascript">
lhgDialog =$.dialog;
</script>
<script type="text/javascript"
	src="plug-in/artDiglog/jquery.artDialog.js?skin=default"></script>
<link rel="shortcut icon" href="images/favicon.ico">
<link rel="stylesheet" href="plug-in/chrhc/bootstrap.css"
	type="text/css"></link>
<link rel="stylesheet" href="plug-in/chrhc/mainFix.css" type="text/css"></link>
<style type="text/css">
a {
	color: Black;
	text-decoration: none;
}

a:hover {
	color: black;
	text-decoration: none;
}
/*update-start--Author:zhangguoming  Date:20140622 for：左侧树调整：加大宽度、更换节点图标、修改选中颜色*/
.tree-node-selected {
	background: #eaf2ff;
}
.tabs-scroller-right,.tabs-scroller-left{
	height:49px !important;
	margin-bottom:1px;
}
.tabs-scroller-left{
	margin-left:2px;
}
/*update-end--Author:zhangguoming  Date:20140622 for：左侧树调整：加大宽度、更换节点图标、修改选中颜色*/
</style>
<SCRIPT type="text/javascript">
	dialogStr = "functionList,typeGridTree,dbSourceList,iconList,mutiLangList,territoryList,categoryList,cgformButtonList,typeValueList,processnodeList,busbaseList,myTaskList,processproList,listenerList,cgreportConfigHeadList,operationList,roleUserList,departUserList";
	serverFullPath="${webRoot}";
	gisUrl="<%=gisUrl%>";
	// sclient 客户端 文件下载
	function downloadSclient()
	{
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
	//alert(serverFullPath);
	$(function() {
		
		$('#westPanel').panel({ 
			"onCollapse": function () {
				document.getElementById("myMap").contentWindow.cMap.reSetMapSize();
			}, 
			"onExpand": function () {
				//(document.getElementById("myMap").contentWindow.cMap.reSetMapSize());
				setTimeout("document.getElementById('myMap').contentWindow.cMap.reSetMapSize()",1000);
			}
			});
		//alert("<%=path%>/webpage/system/gis/gisdemo.jsp?config={enableMeasure:true,enableLayerControl:true,enableDataEdit:true,enableQuery:true,enableSpotClickForMain:true}");
			
		/* 
		$('#layout_jeecg_onlineDatagrid')
				.datagrid(
						{
							url : 'systemController.do?datagridOnline&field=ip,logindatetime,user.userName,',
							title : '',
							iconCls : '',
							fit : true,
							fitColumns : true,
							pagination : true,
							pageSize : 10,
							pageList : [ 10 ],
							nowarp : false,
							border : false,
							idField : 'id',
							sortName : 'logindatetime',
							sortOrder : 'desc',
							frozenColumns : [ [ {
								title : '<t:mutiLang langKey="common.code"/>',
								field : 'id',
								width : 150,
								hidden : true
							} ] ],
							columns : [ [
									{
										title : '<t:mutiLang langKey="common.login.name"/>',
										field : 'user.userName',
										width : 100,
										align : 'center',
										sortable : true,
										formatter : function(value, rowData,
												rowIndex) {
											return formatString(
													'<span title="{0}">{1}</span>',
													value, value);
										}
									},
									{
										title : 'IP',
										field : 'ip',
										width : 150,
										align : 'center',
										sortable : true,
										formatter : function(value, rowData,
												rowIndex) {
											return formatString(
													'<span title="{0}">{1}</span>',
													value, value);
										}
									},
									{
										title : '<t:mutiLang langKey="common.login.time"/>',
										field : 'logindatetime',
										width : 150,
										sortable : true,
										formatter : function(value, rowData,
												rowIndex) {
											return formatString(
													'<span title="{0}">{1}</span>',
													value, value);
										},
										hidden : true
									} ] ],
							onClickRow : function(rowIndex, rowData) {
							},
							onLoadSuccess : function(data) {
								$('#layout_jeecg_onlinePanel')
										.panel(
												'setTitle',
												'( '
														+ data.total
														+ ' )'
														+ ' <t:mutiLang langKey="lang.user.online"/>');
							},
							onLoadError : function(data) {
							}
						}).datagrid('getPager').pagination({
					showPageList : false,
					showRefresh : false,
					beforePageText : '',
					afterPageText : '/{pages}',
					displayMsg : ''
				});

		$('#layout_jeecg_onlinePanel').panel({
			tools : [ {
				iconCls : 'icon-reload',
				handler : function() {
					$('#layout_jeecg_onlineDatagrid').datagrid('load', {});
				}
			} ]
		});

		$('#layout_east_calendar').calendar({
			fit : true,
			current : new Date(),
			border : false,
			onSelect : function(date) {
				$(this).calendar('moveTo', new Date());
			}
		});
		$(".layout-expand")
				.click(
						function() {
							$('#layout_east_calendar').css("width", "auto");
							$('#layout_east_calendar').parent().css("width",
									"auto");
							$("#layout_jeecg_onlinePanel").find(
									".datagrid-view")
									.css("max-height", "200px");
							$(
									"#layout_jeecg_onlinePanel .datagrid-view .datagrid-view2 .datagrid-body")
									.css("max-height", "180px").css(
											"overflow-y", "auto");
						});
		
		*/
	}); 
	var onlineInterval;

	function easyPanelCollapase() {
		window.clearTimeout(onlineInterval);
	}
	function easyPanelExpand() {
		onlineInterval = window.setInterval(function() {
			$('#layout_jeecg_onlineDatagrid').datagrid('load', {});
		}, 1000 * 20);
	}

</SCRIPT>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">

	<!-- 顶部-->
	<div region="north" border="false"
		style="background: #E6E6FA; height: 70px !important; padding: 1px; overflow: hidden;">
		<div class="container-fluid header">
			<div class="col-md-12">
				<div class="logo">
					<div class="logoTitle">社区综合管理平台</div>
					<div class="logoSubTitle">Community Integrated Management System</div>
				</div>
				<!-- <div class="navv">
					<button class="changeSystem">
						<span class="glyphicon glyphicon-resize-vertical"></span>切换系统
					</button>
				</div>
				<div class="systemList" style="display: none;">
					<ul>
						<li class="current"><span class="glyphicon glyphicon-lock"></span><span
							class="sysName">统一身份认证管理平台</span></li>
						<li><span class="glyphicon glyphicon-user"></span><span
							class="sysName">人力资源系统</span></li>
						<li><span class="glyphicon glyphicon-time"></span><span
							class="sysName">协同办公系统</span></li>
					</ul>
				</div> -->
				<div class="globalBtn">
					<div style="float: right; width: 210px; padding-top:20px;">
						<a href="javascript:void(0);" class="easyui-menubutton"
							style="background-color: #1E5E9C; border:none !important;" menu="#layout_north_kzmbMenu">
							<span class="glyphicon glyphicon-cog"></span> <t:mutiLang
								langKey="common.control.panel" />
						</a> <a href="javascript:void(0);" class="easyui-menubutton"
							style="background-color: #1E5E9C; border:none !important;" onclick="exit('loginController.do?logout','<t:mutiLang langKey="common.exit.confirm"/>',1);">
							<span class="glyphicon glyphicon-off"></span> <t:mutiLang
								langKey="common.logout" />
						</a>
					</div>
					<div style="float: right; width: auto;">
						<ul>
							<li class="user_info"><t:mutiLang langKey="common.user" />：${userName }</li>
							<li class="user_info"><t:mutiLang langKey="current.org" />：${currentOrgName }</li>
							<li class="user_info"><t:mutiLang langKey="common.role" />：${roleName }</li>
							<!--<li><a href="javascript:void(0);" class="easyui-menubutton"
							menu="#layout_north_kzmbMenu"> <span
								class="glyphicon glyphicon-cog"></span> <t:mutiLang
									langKey="common.control.panel" />
						</a></li>
						  <li><a href="javascript:void(0);" class="easyui-menubutton"
							menu="#layout_north_zxMenu"> <span
								class="glyphicon glyphicon-off"></span> <t:mutiLang
									langKey="common.logout" />
						</a></li>
						<li><a href="javascript:void(0);"
							style="color: #FFFFFF; font-size: 16px;"
							onclick="exit('loginController.do?logout','<t:mutiLang langKey="common.exit.confirm"/>',1);">
								<span class="glyphicon glyphicon-off"></span> <t:mutiLang
									langKey="common.logout" />
						</a></li>-->
						</ul>
					</div>
					<div id="layout_north_kzmbMenu"
						style="width: 100px; display: none;">
						<div
							onclick="addTab('<t:mutiLang langKey="common.profile"/>','userController.do?userinfo&_x='+Math.random())">
							<t:mutiLang langKey="common.profile" />
						</div>
						<div class="menu-sep"></div>
						<div
							onclick="addTab('<t:mutiLang langKey="common.change.password"/>','userController.do?changepassword')">
							<t:mutiLang langKey="common.change.password" />
						</div>

						<%-- <div class="menu-sep"></div>
						<div
							onclick="add('<t:mutiLang langKey="common.change.style"/>','userController.do?changestyle')">
							<t:mutiLang langKey="common.my.style" />
						</div> --%>
					</div>
					<div id="layout_north_zxMenu" style="width: 100px; display: none;">
						<div class="menu-sep"></div>
						<div
							onclick="exit('loginController.do?logout','<t:mutiLang langKey="common.exit.confirm"/>',1);">
							<t:mutiLang langKey="common.exit" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 顶部-->
	<%-- <div region="north" border="false" title=" sc 1.0" style="BACKGROUND: #E6E6FA; height: 85px; padding: 1px; overflow: hidden;">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
		    <td align="left" style="vertical-align: text-bottom;"><!-- <img src="plug-in/login/images/head.png;"> <img src="plug-in/login/images/foot.png"> --></td>
		    <td align="right" nowrap>
		        <table>
		            <tr>
		                <td valign="top" height="50">
		                    <span style="color: #CC33FF"><t:mutiLang langKey="common.user"/>:</span>
		                    <span style="color: #666633">${userName }</span>
		                    <span style="color: #CC33FF"><t:mutiLang langKey="current.org"/>:</span>
		                    <span style="color: #666633">${currentOrgName }</span>
		                    <span style="color: #CC33FF"><t:mutiLang langKey="common.role"/>:</span>
		                    <span style="color: #666633">${roleName }</span>
		                </td>
		            </tr>
		            <tr>
		                <div style="position: absolute; right: 0px; bottom: 0px;">
		                    <a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_kzmbMenu" iconCls="icon-help">
		                        <t:mutiLang langKey="common.control.panel"/>
		                    </a>
		                    <a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_zxMenu" iconCls="icon-back">
		                        <t:mutiLang langKey="common.logout"/>
		                    </a>
		                </div>
		                <div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
		                    <div onclick="openwindow('<t:mutiLang langKey="common.profile"/>','userController.do?userinfo')">
		                        <t:mutiLang langKey="common.profile"/>
		                    </div>
		                    <div class="menu-sep"></div>
		                    <div onclick="add('<t:mutiLang langKey="common.change.password"/>','userController.do?changepassword')">
		                        <t:mutiLang langKey="common.change.password"/>
		                    </div>
		
		                    <div class="menu-sep"></div>
		                    <div onclick="add('<t:mutiLang langKey="common.change.style"/>','userController.do?changestyle')">
		                        <t:mutiLang langKey=""/>
		                    </div>
		                </div>
		                <div id="layout_north_zxMenu" style="width: 100px; display: none;">
		                    <div class="menu-sep"></div>
		                    <div onclick="exit('loginController.do?logout','<t:mutiLang langKey="common.exit.confirm"/>',1);"><t:mutiLang langKey="common.exit"/></div>
		                </div>
		            </tr>
		        </table>
		    </td>
		    <td align="right">&nbsp;&nbsp;&nbsp;</td>
		</tr>
		</table>
		</div> --%>
	<!-- 左侧-->
	<div region="west" split="true" href="loginController.do?leftnew"
		title="<t:mutiLang langKey="common.navegation" />" collapsed="false" id="westPanel" 
		style="width: 200px; padding: 1px;">
	</div>
	<!-- 中间-->
	<div id="mainPanle" region="center" style="overflow: hidden; ">
		<div id="maintabs" class="easyui-tabs" fit="true" border="false">
			 <div class="easyui-tab" title="社区<t:mutiLang langKey="common.map"/>"
				style="padding: 1px; overflow: hidden;" >
				<iframe name="myMap" id="myMap" scrolling="auto" frameborder="0"
					src="<%=path%>/webpage/system/gis/gisdemo.jsp?config={enableMeasure:true,enableLayerControl:true,enableDataEdit:true,enableQuery:true,enableSpotClickForMain:true}"
					style="width: 100%; height: 100%;"></iframe>
			</div> 
		</div>
	</div>
	<!-- 右侧 -->
	<!--  <div collapsed="false" region="east" iconCls="icon-reload"
		title="" split="false"
		style="width: 0px;display:none;"
		data-options="onCollapse:function(){easyPanelCollapase()},onExpand:function(){easyPanelExpand()}">
		<div id="tabs" class="easyui-tabs" border="false"
			style="height: 240px">
			<div title="<t:mutiLang langKey="common.calendar"/>"
				style="padding: 0px; overflow: hidden; color: red;">
				<div id="layout_east_calendar"></div>
			</div>
		</div>
		<div id="layout_jeecg_onlinePanel"
			data-options="fit:true,border:false"
			title=<t:mutiLang langKey="common.online.user"/>>
			<table id="layout_jeecg_onlineDatagrid"></table>
		</div>
	</div>-->
	<!-- 底部 -->
	<div region="south" border="false"
		style="height: 25px; overflow: hidden;">
		<div align="center" style="color: #999999;">
			&copy;
			<%-- <t:mutiLang langKey="common.copyright" /> --%>
			<span class="tip"> <%-- <t:mutiLang langKey="common.copyright" />: <a href="#" title=" JEECG Framework 3.4.3 GA">JEECG Framework 3.4.3 GA</a> --%>
				华戎信息产业有限公司&nbsp;版权所有&nbsp;1989-2015&nbsp;版本：V1.2.0
			</span>
		</div>
	</div>
	<!-- 底部 -->
	<%-- <div region="south" border="false"
		style="height: 25px; overflow: hidden;">
		<div align="center" style="color: #CC99FF; padding-top: 2px">
			&copy;
			<t:mutiLang langKey="common.copyright" />
			<span class="tip"> <a href="http://www.jeecg.org"
				title=" JEECG Framework 3.4.3 GA"> JEECG Framework 3.4.3 GA</a> <t:mutiLang
					langKey="common.copyright" />: <a href="#"
				title=" JEECG Framework 3.4.3 GA">JEECG Framework 3.4.3 GA</a>
			</span>
		</div>
	</div> --%>
	<div id="mm" class="easyui-menu" style="width: 150px;display:none">
		<div id="mm-tabupdate">
			<t:mutiLang langKey="common.refresh" />
		</div>
		<div id="mm-tabclose">
			<t:mutiLang langKey="common.close" />
		</div>
		<div id="mm-tabcloseall">
			<t:mutiLang langKey="common.close.all" />
		</div>
		<div id="mm-tabcloseother">
			<t:mutiLang langKey="common.close.all.but.this" />
		</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">
			<t:mutiLang langKey="common.close.all.right" />
		</div>
		<div id="mm-tabcloseleft">
			<t:mutiLang langKey="common.close.all.left" />
		</div>
	</div>
<script>
	
</script>
</body>
</html>