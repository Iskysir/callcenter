<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户信息</title>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
<link id="easyuiTheme" rel="stylesheet"
	href="plug-in/easyui/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="plug-in/easyui/themes/icon.css"
	type="text/css"></link>
<link rel="stylesheet" type="text/css"
	href="plug-in/accordion/css/accordion.css">
<link href="plug-in/chrhc/bootstrap.css" rel="stylesheet">
<link href="plug-in/chrhc/style.css" rel="stylesheet">
<link href="plug-in/icheck/skin/square/blue.css" rel="stylesheet">
<script type="text/javascript"
	src="plug-in/easyui/jquery.easyui.min.1.3.2.js"></script>
<script type="text/javascript" src="plug-in/easyui/locale/zh-cn.js"></script>
<script type="text/javascript" src="plug-in/tools/syUtil.js"></script>
<script type="text/javascript"
	src="plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
<script type="text/javascript" src="plug-in/tools/curdtools_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/tools/easyuiextend.js"></script>
<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css"
	type="text/css"></link>
	<link rel="stylesheet" href="plug-in/chrhc/currentPosition.css" type="text/css"></link>
<script type="text/javascript"
	src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<script src="plug-in/chrhc/bootstrap.min.js"></script>
<script src="plug-in/chrhc/ie8/html5.js"></script>
<script src="plug-in/chrhc/ie8/respond.min.js"></script>
<script src="plug-in/icheck/icheck.js"></script>
<script src="plug-in/chrhc/jquery.tabs.js"></script>
<script src="plug-in/chrhc/jquery.lazyload.js"></script>
<script src="plug-in/Validform/js/Validform_v5.3.1.js" type="text/javascript"></script>
</head>

<body>
<div class="Current_position">
    	<img src="plug-in/media/image/dingwei.png"/><span>当前所在的位置：</span>
		<a href="javascript:;">用户信息</a>
    </div>
<t:formvalid formid="formobj" dialog="true" layout="table" action="userController.do?saveUserForControl">
	<div class="container form-card">
		<input id="id" name="id" type="hidden" value="${user.id }">
		<input id="roleid" name="roleid" type="hidden" value="${user.roleid }">
		<input id="orgIds" name="orgIds" type="hidden" value="${user.orgIds }">
		<input id="status" name="status" type="hidden" value="${user.status }">
		<input id="activitiSync" name="activitiSync" type="hidden" value="${user.activitiSync }">
		<div class="row">
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label" for="userName"><t:mutiLang langKey="common.username"/>:</label> 
						${user.userName }
					</div>
				</div>
		</div>
		<div class="row">
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label" for="realName"><t:mutiLang langKey="common.real.name"/>:</label>
						<input id="realName" class="form-control w260" name="realName" value="${user.realName }" datatype="s2-10">
						<span class="need">*</span>
                        <span class="Validform_checktip"><t:mutiLang langKey="fill.realname"/></span>
					</div>
				</div>
		</div>
		<div class="row">
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label" for="mobilePhone"><t:mutiLang langKey="common.phone"/>:</label> 
						 <input class="form-control w260" name="mobilePhone" value="${user.mobilePhone}" datatype="m" errormsg="手机号码不正确" ignore="ignore">
                			<span class="Validform_checktip"></span>					
					</div>
				</div>
		</div>
		<div class="row">
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label" for="officePhone"><t:mutiLang langKey="common.tel"/>:</label>
						<input class="form-control w260" name="officePhone" value="${user.officePhone}" datatype="tel" errormsg="办公室电话不正确" ignore="ignore">
                        <span class="Validform_checktip"></span>			          	
					</div>
				</div>				
		</div>
	
		<div class="row">
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label" for="email"><t:mutiLang langKey="common.common.mail"/>:</label> 
						<input class="form-control w260" name="email" value="${user.email}" datatype="e,*5-30" nullmsg="请输入电子信箱!" ignore="ignore">
                		<span class="Validform_checktip"></span>										
					</div>
				</div>
		</div>

			<div class="row" id="buttonPanel">
				<div class="col-md-8 center">
					<button type="submit" class="btn btn-sure">确定</button>
          			<button type="button" class="btn btn-cancel" onclick="closeCurrentTab()">关闭</button>
				</div>
			
			</div>
	</div>

</t:formvalid>


<script type="text/javascript">
		$(document).ready(function() {
			$('input').iCheck({
				checkboxClass : 'icheckbox_square-blue',
				radioClass : 'iradio_square-blue',
				increaseArea : '20%' // optional
			});
			// 全选
			$('.table input#selectAll').on('ifChecked', function(event) {
				$('.table input').iCheck('check');
			});
			// 取消全选
			$('.table input#selectAll').on('ifUnchecked', function(event) {
				$('.table input').iCheck('uncheck');
			});
			$('.demo').Tabs({
				event : 'click'
			});
			//********************************表单验证 开始******************************************
		    $(".valideForm").Validform({
		      tiptype:2
		    });
		    //********************************表单验证 结束******************************************

		    $("#formobj").attr({"class":"form-inline valideForm"});

		  //zxy 2015年10月27日 scbi 无tab页关闭 begin
			var mainTabs = window.parent.$('#maintabs');
			if(!mainTabs.length>0)
			{
				//alert("undefined");
				$(".Current_position").hide();
				closeCurrentTab=function(){
					//window.top.art.dialog({ title: '提示', content: '用户信息修改成功'});
					window.top.art.dialog.list['用户信息'].close();
					}
			}
			//zxy 2015年10月27日 scbi 无tab页关闭 end		    
		});

		function uploadFile(data){
			//alert(data.msg);
			//tip(data.msg);
				closeCurrentTab();
			// debugger;		
		}
</script>

</body>
</html>