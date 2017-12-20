<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html  xmlns="http://www.w3.org/1999/xhtml">
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8" />
	<title>智慧社区-首页</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<!-- BEGIN GLOBAL MANDATORY STYLES -->
    <link rel="stylesheet"  href="plug-in/scmedia/css/font-awesome.min.css" type="text/css">
	<link href="plug-in/scmedia/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/scmedia/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/scmedia/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/scmedia/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/scmedia/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>
    <link rel="stylesheet" type="text/css" href="plug-in/scmedia/jquery.easyui/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="plug-in/scmedia/jquery.mCustomScrollbar/css/jquery.mCustomScrollbar.css">
     
    <!--[if IE 7]>
       <link rel="stylesheet" type="text/css" href="media/css/font-awesome-ie7.min.css">
    <![endif]-->
    <!-- END GLOBAL MANDATORY STYLES -->

</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page_body">
<section id="mainbox"> 
  <div class="tab-content tab-box">
  	<div class="Current_position">
    	<img src="plug-in/media/image/dingwei.png"/><span>当前所在的位置：</span>
      <a href="javascript:;">信息通知</a>&nbsp;>
      <a href="javascript:;">信息状态</a>
    </div>
     <ul class="nav nav-tabs">
      	<li class="active"><a href="#jbxx" data-toggle="tab">基本信息</a></li>
    
	<div class="tabs_btn_div">
		<button class="btn btn_bag_3" id="back_btn" onclick="parent.$('#editFormDiv').hide();parent.$('#editForm').attr('src','');parent.$('.datagrid').show();"><i class="fa fa-reply"></i></button>
	</div>
	</ul>
	
	<input type="hidden" name="id" value="${ id}" >
	
    <div class="tab-pane active" id="jbxx">
    <table class=" tab_xq">
    	<tr>
        	<th>邮件标题</th>
            <td><input  type="text" value="${scMessageEntity.title }" disabled="disabled" /></td>
        </tr>
        <tr>
        	<th>发送日期</th>
            <td><input  type="text" value="${pTime}" disabled="disabled" /></td>
        </tr>
        <tr>
        	<th>发送方式</th>
            <td>
            	<div class="lt_div">
            		<label><i class="fa  fa-mobile-phone"></i>手机<input name="Fruit" type="checkbox" value="sms"  disabled="disabled" /></label>
                     <label><i class="fa  fa-envelope"></i>邮件<input name="Fruit" type="checkbox" value="email"  disabled="disabled" /></label>
                     <!-- <label><i class="fa  fa-weixin"></i>微信<input name="Fruit" type="checkbox" value="wechat"  disabled="disabled" /></label> -->
                     <label><i class="fa  fa-weixin"></i>手机端推送<input name="Fruit" type="checkbox" value="baidu" disabled="disabled" /></label>
                    <!-- <t:dictSelect field="mType" type="checkbox" typeGroupCode="es_type" defaultVal="${mTypes}" hasLabel="false"  title="信息类型" ></t:dictSelect> -->
                 </div>
            </td>
        </tr>
        <tr>
        	<th>发送内容</th>
            <td><textarea disabled="disabled" cols="200" rows="10">${content }</textarea></td>
        </tr>
        <tr>
        	<th>发送状态</th>
            <td><div class="zhuangtai">发送成功<span class="chenggong">${send }</span>条</div><div class="zhuangtai">发送失败<span class="shibai">${sendFail }</span>条</div></td>
        </tr>
    </table>
    
    <div  class="layer_notice" style="display:none;" > 
    	${receivers}
    </div>
    
 	<div  class="shibai2" style="display:none;" > 
    	${receiversFail}
    </div>
                       
     
      
    </div>

  </div>
  <a href="javascript:location.href = prve_url;"  class="dataprve" style="display:none;"></a>
  <a  href="javascript:location.href = next_url;"  class="datanext" style="display:none;"></a> 
</section>
<!--mainbox end-->
	<!-- END FOOTER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<script src="plug-in/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
	<script src="plug-in/scmedia/layer/layer.min.js" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="plug-in/media/js/bootstrap.min.js" type="text/javascript"></script>
	<!--[if lt IE 9]>
	<script src="media/js/excanvas.min.js"></script>
	<![endif]-->   
    <!--[if lte IE 9]>
    <script src="media/js/html5.js" type="text/javascript"></script>
    <script src="media/js/PIE.js" type="text/javascript"></script>
    <script src="media/js/pieuser_2.js" type="text/javascript"></script>
    <![endif]-->
	<script>

		var prve_url , next_url;
		
		 $(document).ready(function() { 

			$(".chenggong").mouseover(function(){
				if($(this).html() == '0') return  ;
				var a = $('.layer_notice').html();
				
		  		layer.tips(a, this, {
		  			style: ['background-color:#78BA32; color:#fff', '#78BA32'],
					maxWidth:185,
					time: 0,
						closeBtn:false,
					offset: ['100', '10'],
				});
			});

			$(".chenggong").mouseleave(function () {            
	  			layer.closeTips();
			});
			

			$(".shibai").mouseover(function () {  
		 		if($(this).html() == '0') return  ;
		 		var a = $('.shibai2').html();
		  		layer.tips(a , this, {
		  			style: ['background-color:#78BA32; color:#fff', '#78BA32'],
					maxWidth:185,
					time: 0,
						closeBtn:false,
					offset: ['100', '10'],
				});
			});
			$(".shibai").mouseleave(function () {            
	  			layer.closeTips();
			});
			 
			var types = $("input[type='checkbox']");
			var a = '${mTypes}';
			var names = a.split(",");
			for(var i=0;i<types.length;i++){
				for(var j=0;j<names.length;j++){
					if(types[i].value == names[j]){
						types[i].checked = true;
						break;
					}
				}
			};

			
			if(parent.nextRecord){
				var id = $("input[name='id']").val();
				prve_url = parent.nextRecord(id,-1);
				next_url = parent.nextRecord(id,1);	
				if(prve_url){
					$(".dataprve").show();
				}
		 		if(next_url){
					$(".datanext").show();	  
				}
			}
			
		 });
		 
			function nextRecord(stepNum){
				
				
				alert(parent.nextRecord(id,stepNum));
			}


			
     </script>
     <script type="text/javascript">

	
 </script>
 	<script src="plug-in/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="plug-in/media/js/bootstrap.min.js" type="text/javascript"></script>
 
 
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>