<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
    <head>
       	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=Edge">
        <meta http-equiv="Cache-Control" content="no-store" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
        <title>热线服务平台--登录</title>
        <style type="text/css">
			.kCode{background:none;color:#F00000;font-size:18px;font-weight:bold;height:29px;line-height:29px;width:75px; float:left; margin-left:5px;margin-top:3px;}
			.error{color:#FF0000; line-height:24px;clear: both;}
        </style>
        
        <link type="text/css" rel="stylesheet" href="${ctx}/resources/themes/hotline/common/css/login.css">
        <link type="text/css" rel="stylesheet" href="${ctx}/resources/themes/hotline/common/css/square/blue.css">
       	<script src="${ctx}/resources/themes/hotline/common/jquery/jquery-1.9.1.js" type="text/javascript"></script>
       	<script src="${ctx}/resources/themes/hotline/common/jquery/jquery.cookie.js" type="text/javascript"></script>
       	<script src="${ctx}/resources/themes/hotline/common/placeholder.js" type="text/javascript"></script>
       	<script src="${ctx}/resources/themes/hotline/common/jquery/jquery.icheck.js" type="text/javascript"></script>
       	<script src="${ctx}/resources/themes/hotline/common/dialog/artDialog.js?skin=default" type="text/javascript"></script>
       
        <script type="text/javascript">
			/*$(document).ready(function(){
				<c:if test="${not empty message}">
					alert("${message}");
				</c:if>	
			});*/
			if(parent.findData){
				parent.art.dialog({title: '提示',icon: 'succeed',lock: true,content: '登录超时，3秒后跳转登录页面',time:3});
				setTimeout('parent.location.href = "${ctx}/login"',3000);
				//uiBase.closeCurrentTab();
			} 

			function checkNull(){
				
				if($('#username').val().trim().length == 0){
					$('#error').text("用户名不能为空！");
					return false;
				}
				if($('#password').val().trim().length == 0){
					$('#error').html("密码不能为空！");
					return false;
				}
				
				if($('#ivcode').val().trim().length == 0){
					$('#error').text("验证码不能为空！");
					$('#password').val("");
					return false;
				}

				var flag = $('#remember-name').is(':checked');
				if(flag == true){
			  	  setCookie("nameFlag",flag);
			  	  setCookie("displayname",$('#username').val());
			    }
			}

			function changePic()
			{
				var urlhref="${ctx}/servlet/ivcode?"+Math.random();
				document.getElementById("gvcode").src=urlhref;
			}
		</script>
    </head>
    <body onload="changePic()">
        <div class="logo-f"> 
        	<span class="logo"></span> 
        	<span class="logo-font">热线服务平台</span>
        </div>
        <div class="login_box" style="width:710px">
        
        <div class="login">
            <div class="login-header">
                <span class="user-photo-f">
                    <img class="user-photo" src="${ctx}/resources/themes/hotline/common/images/user-photo.png">
                </span>
                <span class="users-login"></span>
            </div>
            <form id="loginForm" action="${ctx}/login" method="post">
            <div class="login-body">
                <div class="login-body-left">
                    <ul class="username">
                        <li>
                            <span class="username-p"></span>   
                            <input class="userlab" type="text" id="username" name="username" value="${displayname}" placeholder="输入用户名">
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
                            <input class="captchalab" type="text" id="ivcode" name="ivcode" onblur="" placeholder="请输入验证码">
                        </li>
                        <li class="captcha-p">
                            <img id="gvcode" onclick="changePic()" src="${ctx}/randCodeImage?a=<%=System.currentTimeMillis()%>>" class="kCode"/>
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
				       </li>
                    </ul>
                    <input type="submit" class="log-btn" onclick="return checkNull()" value="登 录" />
                </div>
                <div class="login-body-right">
                    <ul class="message">
                        <li class="message-one">
                            <span>12345</span>
                            <span>市民热线</span>
                        </li>
                        <li class="message-two">
                            <span>服务找政府</span>
                        </li>
                        <li class="message-three">
                            <span>听民声&nbsp;&nbsp;解民忧</span>
                        </li>
                    </ul>
                </div>
            </div>
            
            </form>
            
              <div class="login_down">
               <div class="login_down_l"></div>
               <div class="login_down_c">
                 <ul>
                   <li class="downbtn"><a href="${ctx}/resources/meet/MeetingDesktop.zip"><i class="shipinpc"></i><span>视频会议电脑版</span></a></li>
                   <li class="downline"></li>
                   <li class="downbtn"><a href="${ctx}/resources/meet/MSRXmeeting.apk"><i class="shipinmobil"></i><span>视频会议手机版</span></a></li>
                   <li class="downline"></li>
                   <li class="downbtn"><a href="${ctx}/resources/meet/HotlineMobile.apk"><i class="minsmobil"></i><span>民生热线移动端</span></a></li>
                 </ul>
               </div>
               <div class="login_down_r"></div>
  </div>
  
  
            <div class="login-footer"></div>
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
<script>
    $(document).ready(function(){
	      
	        if($("#error").length==0){
					$(".remember").before(' <ul ><li><p class="error" id="error"></p></li></ul>');
	        }	
        
			jQuery('input[placeholder]').not("#username").placeholder();
		      $('input').iCheck({
		        checkboxClass: 'icheckbox_square-blue',
		        radioClass: 'iradio_square-blue'
		      });


      if(getCookie("nameFlag")){
    	  if(getCookie("displayname") != null && getCookie("displayname") !=''){
	  			$('#username').val(getCookie("displayname"));
	  			$("input").iCheck('check');
  		   } 
      }
    });

    function setCookie(name, value) {
    	 $.cookie(name, value);
     }
    
    function getCookie(name) {
        return  $.cookie(name);
    }
    

</script>
    </body>
</html>
