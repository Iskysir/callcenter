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

    <%--update-start--Author:zhangguoming  Date:20140825 for：添加组织机构combobox多选的处理方法--%>
    <script>
        function setOrgIds() {
            var orgIds = $("#orgSelect").combotree("getValues");
            $("#orgIds").val(orgIds);
			var b = true;
            b = checkOrg();
            return b;
        }
        //验证组织机构是否为空
        function checkOrg(){
        	var value = $("input[class='combo-text validatebox-text']").val();
        	var b = true;
        	if(value && value!=''){
        		$("#orgSelectSpan").attr("class","Validform_checktip Validform_right");
            	$("#orgSelectSpan").text("通过信息验证！");
        		b = true;
            }else{
            	$("#orgSelectSpan").attr("class","Validform_checktip Validform_wrong");
            	$("#orgSelectSpan").text("请选择组织机构！");
				b = false;
            }
            return b;
        }
        $(function() {
            $("#orgSelect").combotree({
                onChange: function(n, o) {
                    if($("#orgSelect").combotree("getValues") != "") {
                        $("#orgSelect option").eq(1).attr("selected", true);
                        $("#orgSelectSpan").attr("class","Validform_checktip Validform_right");
                    	$("#orgSelectSpan").text("通过信息验证！");
                    } else {
                        $("#orgSelect option").eq(1).attr("selected", false);

                        $("#orgSelectSpan").attr("class","Validform_checktip Validform_wrong");
                    	$("#orgSelectSpan").text("请选择组织机构！");
                    }
                }
            });
            <%--$("#orgSelect").combobox("setValues", ${orgIdList});--%>
            $("#orgSelect").combotree("setValues", ${orgIdList}); 
        });
    </script>
</head>

<body>
<div class="Current_position">
    	<img src="plug-in/media/image/dingwei.png"/><span>当前所在的位置：</span>
		<a href="javascript:;">系统管理</a>&nbsp;>
     	 <a href="javascript:;">用户管理</a>&nbsp;
</div>
<t:formvalid formid="formobj" dialog="true" layout="table" action="userController.do?saveUser" beforeSubmit="setOrgIds">
	<div class="container form-card">
		<div class="row">
				<div class="col-md-8">
					<div class="form-group">
					<input id="id" name="id" type="hidden" value="${user.id }">
						<label class="control-label" for="userName"><t:mutiLang langKey="common.username"/>:</label> 
						<c:if test="${user.id!=null }"> ${user.userName } </c:if>
                		<c:if test="${user.id==null }">
                   		 <input type="text" id="userName" class="form-control w260" name="userName" validType="t_s_base_user,userName,id" value="${user.userName }" datatype="*2-20" />
                    	<span class="need">*</span>
                    	<span class="Validform_checktip"> <t:mutiLang langKey="username.rang2to10"/></span>
                		</c:if>
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
		
		<c:if test="${user.id==null }">
		<div class="row">		       
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label" for="password"><t:mutiLang langKey="common.password"/>:</label> 
						<input type="password" class="form-control w260"  value="" id="password" name="password" plugin="passwordStrength" datatype="*6-18" errormsg="" />
                    	<span class="passwordStrength" style="display: none;">
                        	<span><t:mutiLang langKey="common.weak"/></span>
                        	<span><t:mutiLang langKey="common.middle"/></span>
                        <span class="last"><t:mutiLang langKey="common.strong"/></span>
                    	</span>
                    	<span class="need">*</span>
                    	<span class="Validform_checktip"> <t:mutiLang langKey="password.rang6to18"/></span>
					</div>
				</div>
		</div>
		<div class="row">
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label" for="repassword"><t:mutiLang langKey="common.repeat.password"/>:</label>
						<input id="repassword" class="form-control w260" type="password" value="${user.password}" recheck="password" datatype="*6-18" errormsg="两次输入的密码不一致！">
                    	<span class="need">*</span>                    	
                    	<span class="Validform_checktip"><t:mutiLang langKey="common.repeat.password"/></span>
					</div>
				</div>				
				
		</div>
		</c:if>	
		<div class="row">
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label" for="orgSelect"><t:mutiLang langKey="common.department"/>:</label> 						
                	<select class="easyui-combotree form-control w260" data-options="url:'departController.do?getOrgTree', multiple:true, cascadeCheck:false"
                        id="orgSelect" name="orgSelect" datatype="select1">
                	<%--update-end--Author:zhangguoming  Date:20140826 for：将combobox修改为combotree--%>
                    <c:forEach items="${departList}" var="depart">
                        <option value="${depart.id }">${depart.departname}</option>
                    </c:forEach>
                	</select>
                 		<span class="need">*</span>                	                	
                		<input id="orgIds" name="orgIds" type="hidden">
                		<span class="Validform_checktip" id="orgSelectSpan"><t:mutiLang langKey="please.select.department"/></span>     
         
					</div>
				</div>
		</div>
		<div class="row">
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label" for="roleName"><t:mutiLang langKey="common.role"/>:</label>
			          	<input id="roleid" name="roleid" type="hidden" value="${id}" id="roleid">            
                        <input id="roleName" name="roleName" type="text" class="form-control w260" value="${roleName }" onclick="choose_role()"  datatype="*"> 
                        <span class="need">*</span>
                		<span class="Validform_checktip"><t:mutiLang langKey="role.muti.select"/></span>
                        <span class="suoshu" onClick="choose_role()"></span>                       
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
		<div class="row">
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label" for="exampleInputName2">工作流引擎:</label>
						<label class="radio-inline"> <input type="radio" checked
							name="activitiSync" id="activitiSync" datatype="min" min="1" <c:if test="${user.activitiSync eq 1}">checked="true"</c:if> value="1">
							同步
						</label> 
						<label class="radio-inline"> <input type="radio"
							name="activitiSync" id="activitiSync" <c:if test="${user.activitiSync eq 0}">checked="true"</c:if> value="0" >
							  不同步
						</label>
						<span class="Validform_checktip">是否同步工作流引擎</span>
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
			//处理组织机构验证，第一次加载页面时，即使为空，无需显示提示
		    $("#orgSelectSpan").attr("class","Validform_checktip");
        	$("#orgSelectSpan").text(""); 

		});
		var neibuClickFlag = false;
		  function neibuClick() {
			  neibuClickFlag = true; 
			  $('#btn_sub').trigger('click');
		  }
		 function uploadFile(data){
			 debugger;
		  		if(!$("input[name='id']").val()){
		  			$("input[name='id']").val(data.obj.id);
		  		}
		  		if($(".uploadify-queue-item").length>0){
		  			upload();
		  		}else{
		  			if (neibuClickFlag){
		  				alert(data.msg);
		  				neibuClickFlag = false;
		  			}else {
		  			//getParentWindow();
		  				//alert(getParentWindow().document.body.innerHTML);
			  			var win = getParentWindow();
			  			if(win)
				  		{
			  				win.reloadTable(data);
							win.tip(data.msg);
				  		}
						
						if(frameElement.api)
						{
							frameElement.api.close();
						}
						else
						{
							closeCurrentTab();
						}
		  			}
		  		}
		  	}
		 var windowapi = frameElement.api, W = windowapi.opener;
		 function choose_role()
		 {
			var roleId = $("#roleid").val();
			 if(typeof(windowapi) == 'undefined')
		 	{
				 	$.dialog(
		 					{
			 					content: 'url:userController.do?roles&roleId='+roleId,
			 					zIndex: 2100,title: '角色列表',
			 					lock : true,
			 					width :400,
			 					height :500,
			 					left :'50%',
			 					top :'45%',
			 					opacity : 0.4,
			 					button : [ 
						 					{name : '确定',
							 					callback : clickcallback_role,
							 					focus : true}, 
							 					{name : '取消',
								 					callback : function() {}
							 					} 
							 			  ]
				 			  });
		 	}else{
			 	$.dialog(
					 	{
						 	content: 'url:userController.do?roles&roleId='+roleId,
						 	zIndex: 2100,
						 	title: '角色列表',
						 	lock : true,
						 	parent:windowapi,
						 	width :400,
						 	height :500,
						 	left :'50%',
						 	top :'45%',
		 					opacity : 0.4,
		 					button : [ 
		 			 					{name : '确定',
			 			 					callback : clickcallback_role,
			 			 					focus : true}, 
			 			 					{
				 			 					name : '取消',
				 			 					callback : function() {}
			 			 					} 
			 			 			]
 			 			});
		 		}
		 }
		 function clearAll_role()
		 {
			 if($('#roleName').length>=1)
				 {
				 	$('#roleName').val('');
				 	$('#roleName').blur();
				 }
		 	if($("input[name='roleName']").length>=1)
			 	{
			 		$("input[name='roleName']").val('');
			 		$("input[name='roleName']").blur();
			 	}
		 		$('#roleid').val("");
		 }
		 function clickcallback_role()
		 {
			 iframe = this.iframe.contentWindow;
			 var roleName=iframe.getroleListSelections('roleName');	
		 	 if($('#roleName').length>=1)
			 {
			 	$('#roleName').val(roleName);
			 	$('#roleName').blur();
			 }
			 if($("input[name='roleName']").length>=1)
			{
					$("input[name='roleName']").val(roleName);
		 			$("input[name='roleName']").blur();
		 	}
			var id =iframe.getroleListSelections('id');
			if (id!== undefined &&id!="")
			{
				$('#roleid').val(id);
			}
		}
		
</script>

</body>
</html>