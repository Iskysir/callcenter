<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title></title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejs.jsp"%>
<%@ include file="/WEB-INF/views/hotline/common/chatinclude.jsp"%>
<link type="text/css" rel="stylesheet" href="${ctx}/resources/themes/hotline/common/css/font-awesome-4.1.0/css/font-awesome.css" />
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
                  <a class="" href="javascript:void(0);"></a>
                  <a class="" href="javascript:void(0);"></a>
                  <a class="min_btn" href="javascript:;"></a>
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
                        <a class="chat02_title_btn ctb01" href="javascript:;" id="biaoqing"></a>
                        
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
                            <li style="left: 20px; top: 10px;">民生热线，24小时在线为您服务！</li>
                            <li style="right: 5px; top: 5px;">
                            	<a href="javascript:;"><img src="${ctx}/resources/themes/hotline/chat/img/send_btn.jpg" onclick="sendMsg();"></a>
                            </li>
                             <!--  <span >(ctrl+enter)</span> -->
                        </ul>
                        
                    </div>
                </div>
            </div>
            <div class="chatRight">
               <h3>民生热线  业务咨询</h3>
               <h4><a href="javascript:addOrder();"><i class="fa fa-edit green"></i><span>新建工单</span></a></h4>
               <%-- <h4><a href="javascript:toRelate(parent.ch_record.id);"><i class="fa fa-arrows-h green"></i><span>关联工单</span></a></h4> --%>
               <h4><a href="javascript:toChatEnd();"><i class="fa fa-trash-o green"></i><span>结束对话</span></a></h4>
			
			<!-- <input type="button" value="工单号" onclick="alert(orderId)"> -->
            </div>
            <div style="clear: both;">
            </div>
        </div>
</body>


	
<script type="text/javascript" >
      	var orderId ;
      	var uploading = 0 ;
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
			
			var msg = $('#editor').val();
			if(!$.trim(msg)){
				$.dialog({
		            title: '消息提示',
		            icon: 'succeed',
		            content: "不能发送空消息"
		        });	
				return ;
			}
			var msg = delMsg(msg);
			$.post(
				"${ctx}/MessageServlet", 
				jQuery.extend({},parent.ch_record,{"subject":'message',"content":msg,"fromAddress":'${userInfo.userName}',id:"","obligateD":"" }),
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

		function setMsg(msg,flag_){
			var content = '<div class="message">'
	 			+	'<div class="wrap-text">';
	 			if(flag_){
	 				content += '<h5 class="ask-name">来自我</h5>';
	 			}else {
	 				content += '<h5 class="ask-name">来自' + (msg.obligateB ? msg.obligateB : '客户')  + '</h5>';
	 			}
	 			content = content 
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
		}
		
		function delMsg(g) { 
			if(- 1 != g.indexOf("*#emo_")){
				g = g.replace("*#", "<img src='" + basePath +"/resources/themes/hotline/chat/img/").replace("#*", ".gif'/>");
				return delMsg(g);
			}
			return g;
		}	
	
		
	function toChatEnd(){
		if(!orderId){
			$.dialog({
	            title: '消息提示',
	            icon: 'question',
	            lock: true,
	            content: '还没有新建工单，是否要结束吗？',
	            ok: function(){
	            	chatEnd();      
	            },
	            cancel: function () {
	            	 
	            }
	        });				
		}else {
			chatEnd();
		}
	}	
		
	function chatEnd(){
		//alert(parent.ch_record.obligateA);
		$.post("${ctx}/webchat/end/" + parent.ch_record.obligateA, 
			{"orderId" : orderId},
			function(data){
				orderId = "";
				parent.document.getElementById('chatFrame').src = '';
				$("#iframebox",window.parent.document).css("display","none");
				$("#chat-min-window",window.parent.document).css("display","none");
		});
	}

	function toRelate(id){	
		if(!id){
			$.dialog({
	            title: '消息提示',
	            icon: 'question',
	            lock: true,
	            content: '没有聊天记录无法新建工单'
	        });	
			return ;
		}
		
		//uiBase.addOneTab('关联工单信息','${ctx }/channelrecord/relateOrder/'+id + "?search_relaState=");		
		uiBase.addOneTab('关联工单信息','${ctx}/business/accept/toRelate/'+id + '?search_pageTag=channel');		
	}
	
		function addOrder(){
			if(!parent.ch_record.id){
				$.dialog({
		            title: '消息提示',
		            icon: 'question',
		            lock: true,
		            content: '没有聊天记录无法新建工单'
		        });	
				return ;
			}
			//uiBase.addOneTab('工单新增',"${ctx}/channelrecord/addOrder/" + id + "?type= " );	
			uiBase.addOneTab('工单新增',"${ctx}/channelrecord/addOrder2?infoSource=" + parent.ch_record.type );	
		}
	  
		 $("#infile").uploadify({
				'swf' : basePath + '/resources/general/common/uploadify/uploadify.swf?ver=' + Math.random(),
				'uploader'  : basePath + '/uploadAction/upload?attachtype=ChannelRecord/weixin',
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
	      			uploading --  ;
	      			if("ChannelTypeWeixin" == parent.ch_record.type){
	      				$.post(
	      					"${ctx}/MessageServlet", 
	      					{"obligateA":parent.ch_record.obligateA,"subject":'message',"content":"image","fromAddress":parent.user,"obligateD":data},
	      					function(data_){
	      						var msg = 	parent.ch_record;
	      						msg.sendTime_ = new Date().format('yyyy-MM-dd hh:mm');
	      						msg.content = '<img width="310" src="${ctx}/image/show/' + data + '">';
	      						setMsg(msg,true);
	      					}
	      				);
	      			} else if("ChannelTypeWebchat" == parent.ch_record.type){
	      				$("#editor").val($("#editor").val() + '<img  width="310" src="${ctx}/image/show/' + data + '">');
	      			}
	      				      				      			
				}.bind(this),
				onSelect:function(){
					uploading ++  ;
				}.bind(this),
	            'buttonText' : ' '
	           	//'buttonImage' : basePath + '/resources/themes/hotline/chat/img/emo_50.gif'
	        });	 
		 
		 
		chrhc_shortcut.add("ctrl+enter",function(){
			 sendMsg();
		}); 
		
		
		if('ChannelTypeWeixin' == parent.ch_record.type){
			$("#biaoqing").remove();
		}
		
		
</script>

</html>