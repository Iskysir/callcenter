<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<%
String lang = org.jeecgframework.core.util.BrowserUtils.getBrowserLanguage(request);
//String langurl = "plug-in/mutiLang/" + lang +".js";

String langurl="plug-in/mutiLang/zh-cn.js";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="renderer" content="ie-stand">
        <meta http-equiv="X-UA-Compatible" content="IE=8">
        <meta http-equiv="Cache-Control" content="no-store" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />


<title>热线服务平台</title>
	<link rel="shortcut icon" href="resources/fc/images/icon/favicon.ico"/>
	<style type="text/css">
		.kCode{background:none;color:#F00000;font-size:18px;font-weight:bold;height:29px;line-height:29px;width:75px; float:left; margin-left:5px;margin-top:3px;}
		.error{color:#FF0000; line-height:24px;clear: both;}
	</style>

	<link type="text/css" rel="stylesheet" href="${ctx}/resources/themes/hotline/common/css/login.css">
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/themes/hotline/common/css/square/blue.css">
<script src=<%=langurl%> type="text/javascript"></script>
<!--[if lt IE 9]>
   <script src="plug-in/login/js/html5.js"></script>
  <![endif]-->
<!--[if lt IE 7]>
  <script src="plug-in/login/js/iepng.js" type="text/javascript"></script>
  <script type="text/javascript">
	EvPNG.fix('div, ul, img, li, input'); //EvPNG.fix('包含透明PNG图片的标签'); 多个标签之间用英文逗号隔开。
</script>
  <![endif]-->

</head>
<body>

    <div id="successLogin"></div>
    <div class="text_success" style="display:none;margin:auto;width:150px;"><img src="plug-in/login/images/loader_green.gif" alt="Please wait" /> <span style="color: white"><t:mutiLang langKey="common.login.success.wait"/></span></div>

		<!--<form name="formLogin" id="formLogin" action="loginController.do?login" check="loginController.do?checkuser" method="post">
			<input name="userKey" type="hidden" id="userKey" value="D1B5CC2FE46C4CC983C073BCA897935608D926CD32992B5900" />

					<input class="userName" name="userName" type="text" id="userName" title="" iscookie="true" value="" nullmsg="" />
					<input class="userPassword" name="password" type="password" id="password" title="" value="" nullmsg="" />

						<input type="text" class="vfCode" id="randCode" name="randCode" placeholder="验证码"/>
						<img id="randCodeImage" src="randCodeImage" class="kCode"/>

						 <input type="checkbox"  id="on_off" name="remember" />记住用户名

						<input type="button" class="loginSubmit" value="登录" name="but_login" id="but_login">
						<input type="reset" class="loginReset" value="重置" name="but_reset" id="but_reset" >



		</form>-->
	<div id="login">
	<div class="logo-f">
		<span class="logo"></span>
		<span class="logo-font"></span>
		<!--<span class="logo-font">博兴热线服务平台</span>-->
	</div>
	<div class="login_box" style="width:710px">

		<div class="login">
			<div class="login-header">
                <span class="user-photo-f">
                    <img class="user-photo" src="${ctx}/resources/themes/hotline/common/images/user-photo.png">
                </span>
				<span class="users-login"></span>
			</div>
			<form name="formLogin" id="formLogin" action="loginController.do?login" check="loginController.do?checkuser" method="post">
				<div class="login-body">
					<div class="login-body-left">
						<ul class="username" >
							<li>
								<span class="username-p"></span>
								<input name="userKey" type="hidden" id="userKey" value="D1B5CC2FE46C4CC983C073BCA897935608D926CD32992B5900" />
								<input class="userlab" style="width: 190px" type="text" id="userName" name="userName" value="${displayname}" placeholder="输入用户名"/>
								<input  style="padding-left:5px;width: 52px;height: 33px;line-height: 33px;" type="text" id="extension" name="extension" value="${displayname}" placeholder="分机号"/>
								<!--  <input name="mobilecode" value="">-->
							</li>
						</ul>
						<ul class="userpass">
							<li>
								<span class="userpass-p"></span>
								<input class="userlab" type="password"  id="password" name="password" value="" placeholder="输入密码">
							</li>
						</ul>
						<ul >
							<li class="captcha">
								<input class="captchalab" type="text" id="randCode" name="randCode" onblur="" placeholder="请输入验证码">
							</li>
							<li class="captcha-p">
								<img id="randCodeImage" onclick="changePic()" src="${ctx}/randCodeImage?a=<%=System.currentTimeMillis()%>>" class="kCode"/>
							</li>
						</ul>
						<c:if test="${not empty message}">
							<ul>
								<li>
									<p class="error" id="error">&nbsp;${message}</p>
								</li>
							</ul>
						</c:if>

						<ul class="remember">
							<li>
								<input class="remember-check" id="remember-name" type="checkbox">
							</li>
							<li>
								<label class="remember-font" for="remember-name">记住用户名</label>
								<div id="alertMessage"></div>
							</li>
						</ul>
						<input type="button" class="log-btn" id="but_login" value="登 录" />
					</div>
					<div class="login-body-right">
						<ul class="message">
							<li class="message-one">
								<span></span>
								<span></span>
							</li>
							<li class="message-two">
								<span>热线服务平台</span>
							</li>
							<li class="message-three">
								<span></span>
							</li>
						</ul>
					</div>
				</div>

			</form>

			<div class="login_down">
				<div class="login_down_l"></div>
				<div class="login_down_c">
					<ul>
						<li class="downbtn"><a href="javascript:void(0)"><i class="shipinpc"></i><span></span></a></li>
						<li class="downline"></li>
						<li class="downbtn"><a href="javascript:void(0)"><i class="shipinmobil"></i><span></span></a></li>
						<li class="downline"></li>
						<li class="downbtn"><a href="javascript:void(0)"><i class="minsmobil"></i><span></span></a></li>
					</ul>
				</div>
				<div class="login_down_r"></div>
			</div>


			<div class="login-footer" style="display: none"></div>
		</div>

		<!--<div class="saomiaobox">
   <div class="sbox1">
     <img src="${ctx}/resources/meet/code1.png" class=""/>
     <span>视频会议手机版</span>
   </div>
   <div class="sbox2">
     <img src="${ctx}/resources/meet/code2.png" class=""/>
     <span>民生热线移动版</span>
   </div>
   <div class="sbox3">
     <img src="${ctx}/resources/meet/cygzh.jpg" class=""/>
     <span>民生热线公众号</span>
   </div>
</div>-->

	</div>
	</div>
		<!--<div class="copyright">&copy; &nbsp;&nbsp;版本：V2.0</div>-->

    <!-- Link JScript-->
    <script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
    <script type="text/javascript" src="plug-in/login/js/jquery-jrumble.js"></script>
    <script type="text/javascript" src="plug-in/login/js/jquery.tipsy.js"></script>
    <script type="text/javascript" src="plug-in/login/js/iphone.check.js"></script>
    <script type="text/javascript" src="plug-in/login/js/login.js"></script>
    <script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
</body>
</html>