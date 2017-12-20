<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title></title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejs.jsp"%>
<%@ include file="/WEB-INF/views/hotline/common/chatinclude.jsp"%>
<script	src="${ctx}/resources/general/common/uploadify/jquery.uploadify-3.1.min.js"	type="text/javascript"></script>
<script	src="${ctx}/resources/general/common/jquery/jquery.fileDownload.js"	type="text/javascript"></script>
<script src="${ctx}/resources/general/common/flexpaper/flexpaper_flash.js" type="text/javascript"></script>
<script src="${ctx}/resources/general/common/uploadify/swfobject.js" type="text/javascript"></script>


<style type="text/css">
	.fileName {
		display: none;
	}
	.cancel {
		display: none;
	}
	.data {
		display: none;
	}	
</style>
<script type="text/javascript">
var dialogueId ,recordType;
var uploading = 0 ;

var user = "${user}";
var server =  '${ctx}/ChatComet?name=' + user + '&client=ChannelTypeWebchat';

//alert(server);

var comet = {
	connection   : false,
	iframediv    : false,

	initialize: function() {
		if (navigator.appVersion.indexOf("MSIE") != -1) {
			comet.connection = new ActiveXObject("htmlfile");
			comet.connection.open();
			comet.connection.write("<html>");
			comet.connection.write("<script>document.domain = '"+document.domain+"'");
			comet.connection.write("</html>");
			comet.connection.close();
			
			comet.iframediv = comet.connection.createElement("div");
			//修改支持ie9
			//comet.connection.appendChild(comet.iframediv);
			comet.connection.getElementsByTagName("script")[0].appendChild(comet.iframediv);
			comet.connection.parentWindow.comet = comet;
			
			comet.iframediv.innerHTML = "<iframe id='comet_iframe' src='"+server+"'></iframe>";

		} else if (navigator.appVersion.indexOf("KHTML") != -1) {
			comet.connection = document.createElement('iframe');
			comet.connection.setAttribute('id',     'comet_iframe');
			comet.connection.setAttribute('src',    server);
			with (comet.connection.style) {
				position   = "absolute";
				left       = top   = "-100px";
				height     = width = "1px";
				visibility = "hidden";
			}
		    document.body.appendChild(comet.connection);

		} else {
			comet.connection = document.createElement('iframe');
			comet.connection.setAttribute('id',     'comet_iframe');
			with (comet.connection.style) {
			    left       = top   = "-100px";
			    height     = width = "1px";
			    visibility = "hidden";
			    display    = 'none';
			}
			comet.iframediv = document.createElement('iframe');
			comet.iframediv.setAttribute('src', server);
			comet.connection.appendChild(comet.iframediv);
			document.body.appendChild(comet.connection);
		}
	},

	getMsg:function(msg){
		if('connect' == msg.subject){
			dialogueId = msg.obligateA;
			recordType = msg.type;
			dialog.close();
			if('success' == msg.content){
				uiBase.showDialog({
		            title: '消息提示',
		            icon: 'succeed',
		            content: "客服已接通"
		        });		
								
			}else {
				uiBase.showDialog({
		            title: '消息提示',
		            icon: 'warning',
		            lock: true,
		            content: "客服暂忙，请稍后"
		        });	
				//setTimeout("window.close();",3000); 
			}
		}else if('message' == msg.subject || 'image' == msg.subject ){
			var content = '<div class="message">'
	 			+	'<div class="wrap-text">'
	 			+		'<h5 class="ask-name">客服' + msg.obligateB + '</h5>'
	 			+ 	'</div>'
	 			+ 	'<div class="wrap-ri">'
	 			+ 		'<span>(' + msg.sendTime_ +')</span>'
	 			+ 	'</div>'
	 			+ 	'<div class="wrap-cont">'
	 			+   	msg.content
	 			+	'</div>'
	 			+ '</div>';
			$(".mes3").append(content);
			$(".chat01_content").scrollTop($(".mes3").height());				
		}else if('end' == msg.subject){
			$.dialog({
	            title: '消息提示',
	            icon: 'succeed',
	            content: "坐席已经断开"
			});
		}
	},

	//退出
	busy: function() {
		dialog.close();
		 uiBase.showDialog({
		    title: '消息提示',
		    icon: 'succeed',
		    lock: true,
		    content: "暂无空闲的坐席为你服务"
		});		
	} ,
	
	//退出
	onUnload: function() {
		 $.post("${ctx}/MessageServlet", 
			 {"obligateA":dialogueId,"subject":'end',"content":'end',"fromAddress":user},
			function(data){
			});
	} 
	
}//comet end
			
if (window.addEventListener) {
	window.addEventListener("load", comet.initialize, false);
	window.addEventListener("unload", comet.onUnload, false);
} else if (window.attachEvent) {
	window.attachEvent("onload", comet.initialize);
	window.attachEvent("onunload", comet.onUnload);
}



function sendMsg(){
	if(uploading > 0 ){
		uiBase.showDialog({
            title: '消息提示',
            icon: 'warning',
            lock: true,
            content: "附件正在上传不能发送消息"
        });	
		return ;
	}
	var msg = delMsg($('#editor').val());
	
	if(!$.trim(msg)){
		$.dialog({
            title: '消息提示',
            icon: 'succeed',
            content: "不能发送空消息"
        });	
		return ;
	}
	 $.post("${ctx}/MessageServlet", 
			 {"obligateA":dialogueId,"subject":'message',"content":msg,"fromAddress":user},
			function(data){
				var content = '<div class="message">'
				 			+	'<div class="wrap-text">'
				 			+		'<h5 class="question-name">我</h5>'
				 			+ 	'</div>'
				 			+ 	'<div class="wrap-ri">'
				 			+ 		'<span>(' + new Date().format('yyyy-MM-dd hh:mm') +')</span>'
				 			+ 	'</div>'
				 			+ 	'<div class="wrap-cont">'
				 			+   	msg
				 			+	'</div>'
				 			+ '</div>';
				$(".mes3").append(content);
				$('#editor').val("");
				$(".chat01_content").scrollTop($(".mes3").height());		
			});
}

function delMsg(g) { 
	if(- 1 != g.indexOf("*#emo_")){
		g = g.replace("*#", "<img  src='" + basePath +"/resources/themes/hotline/chat/img/").replace("#*", ".gif'/>");
		return delMsg(g);
	}
	return g;
}


</script>
</head>
<body>
        <div class="chatBox" id="chatBox-id">
            <div class="chatTop">
              <div class="chatTop_t">
               <div class="photobox"></div>
               <div class="chat-title">
                 <h1><b>民生热线</b>  <span></span></h1>
                 <h2>民生问题咨询解答.....</h2>
               </div>
               <div class="chat-window-btn">
               </div>
              </div>
               <!-- <div class="menue-list">
                 <ul>
                    <li class="mbtn-01"></li>
                    <li class="mbtn-02"></li>
                    <li class="mbtn-03"></li>
                    <li class="mbtn-04"></li>
                    <li class="mbtn-05"></li>
                    <li class="mbtn-06"></li>
                    <li class="mbtn-07"></li>
                 </ul>
               </div> -->
            </div>
            <div class="chatLeft">
                <div class="chat01">
                    <div class="chat01_content">
                        <div class="message_box mes1" style="border:red 1px solid;">
                        </div>
                        <div class="message_box mes2">
                        </div>
                        <div class="message_box mes3" style="display: block;">
                        </div>
                    </div>
                </div>
                <div class="chat02">
                    <div class="chat02_title">
						 <a class="chat02_title_btn ctb01" href="javascript:;"></a>
						 
                        <a class="chat02_title_btn ctb02"
                            href="javascript:;" title="选择文件">
                            <span id="infile"></span>
                        </a>
                        
                        <div class="wl_faces_box">
                            <div class="wl_faces_content">
                                <div class="title">
                                    <ul>
                                        <li class="title_name">常用表情</li><li class="wl_faces_close"><span>&nbsp;</span></li></ul>
                                </div>
								<%@include file="expression.jsp" %>
								
                            </div>
                            <div class="wlf_icon">
                            </div>
                        </div>
                    </div>
                    <div class="chat02_content">
                        <textarea id="editor"></textarea>
                    </div>
                    <div class="chat02_bar">
                        <ul>
                            <li style="left: 20px; top: 10px;">民生热线 ，24小时在线为您服务！</li>
                            <li style="right: 5px; top: 5px;"><a href="javascript:;"><img src="${ctx}/resources/themes/hotline/chat/img/send_btn.jpg" onclick="sendMsg();"></a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="chatRight">
               <h3>民生热线   业务咨询</h3>
            </div>
            <div style="clear: both;">
            </div>
        </div>
</body>

<script type="text/javascript">
$("#infile").uploadify({
	'swf' : basePath + '/resources/general/common/uploadify/uploadify.swf?ver=' + Math.random(),
	'uploader'  : basePath + '/uploadAction/upload?attachtype=ChannelRecord/mail',
	'cancelImg' :basePath + '/resources/general/common/uploadify/uploadify-cancel.png',
	'fileObjName'   : 'infile',
	'expressInstall':'expressInstall.swf',
	//在浏览窗口底部的文件类型下拉菜单中显示的文本
	'fileTypeDesc':'支持的格式：jpg,png',
	//允许上传的文件后缀
	'fileTypeExts':'*.jpg;*.png;',
	//上传文件的大小限制
	'fileSizeLimit':'20MB',
	'hideButton':true,
	'auto' : true,
	'multi' : false,
	'height' : "30",
	'width' : "30",
		onUploadSuccess : function(file,data,response) {
			uploading --;
			$("#editor").val($("#editor").val() + '<img  width="310"  src="${ctx}/image/show/' + data + '">');
				      				      			
	}.bind(this),
	onSelect:function(){
		uploading ++  ;
	}.bind(this),
	
    'buttonText' : ' '
   	//'buttonImage' : basePath + '/resources/themes/hotline/chat/img/emo_50.gif'
});	 

var dialog = uiBase.showDialog({
    title: '消息提示',
    icon: 'succeed',
    lock: true,
    content: "正在接通，请稍后"
});	

chrhc_shortcut.add("ctrl+enter",function(){
	 sendMsg();
}); 
</script>
</html>
