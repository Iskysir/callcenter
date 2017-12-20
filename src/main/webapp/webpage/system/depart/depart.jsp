<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>部门信息</title>
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
<link rel="stylesheet" href="plug-in/chrhc/currentPosition.css" type="text/css"></link>
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

<script type="text/javascript">
	$(function() {
		$('#cc').combotree({
			url : 'departController.do?setPFunction&selfId=${depart.id}',
            width: 155,
            onSelect : function(node) {
//                alert(node.text);
                changeOrgType();
            }
        });
        if(!$('#cc').val()) { // 第一级，只显示公司选择项
            var orgTypeSelect = $("#orgType");
            var companyOrgType = '<option value="1" <c:if test="${orgType=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.company"/></option>';
            orgTypeSelect.empty();
            orgTypeSelect.append(companyOrgType);
        } else { // 非第一级，不显示公司选择项
            $("#orgType option:first").remove();
        }
        if($("#id").val()) {
            $('#cc').combotree('disable');
        }
        if('${empty pid}' == 'false') { // 设置新增页面时的父级
            $('#cc').combotree('setValue', '${pid}');
        }
	});
    function changeOrgType() { // 处理组织类型，不显示公司选择项
        var orgTypeSelect = $("#orgType");
        var optionNum = orgTypeSelect.get(0).options.length;
        if(optionNum == 1) {
            $("#orgType option:first").remove();
            var bumen = '<option value="2" <c:if test="${orgType=='2'}">selected="selected"</c:if>><t:mutiLang langKey="common.department"/></option>';
            var gangwei = '<option value="3" <c:if test="${orgType=='3'}">selected="selected"</c:if>><t:mutiLang langKey="common.position"/></option>';
            orgTypeSelect.append(bumen).append(gangwei);
        }
    }
</script>
</head>
<body>
<div class="Current_position">
    	<img src="plug-in/media/image/dingwei.png"/><span>当前所在的位置：</span>
		<a href="javascript:;">系统管理</a>&nbsp;>
     	 <a href="javascript:;">组织机构管理</a>
</div>
<t:formvalid formid="formobj" layout="table" dialog="true" action="systemController.do?saveDepart">
	<div class="container form-card">
			<div class="row">
				<div class="col-md-8">
					<div class="form-group">
					<input id="id" name="id" type="hidden" value="${depart.id }">
						<label class="control-label" for="departname"><t:mutiLang langKey="common.department.name"/>:</label> 
						<input name="departname" class="form-control w260" value="${depart.departname }"  datatype="s1-20">
						<span class="need">*</span>
                    	<span class="Validform_checktip"><t:mutiLang langKey="departmentname.rang1to20"/></span>
					</div>
				</div>				
		</div>
		<div class="row">
			<div class="col-md-8">
					<div class="form-group">
						<label class="control-label" for="description"><t:mutiLang langKey="position.desc"/>:</label>
						<input name="description" class="form-control w260" value="${depart.description }" datatype="s1-60" ignore="ignore">
					</div>
				</div>
		</div>
		<div class="row">
			<div class="col-md-8">
					<div class="form-group">
						<label class="control-label"><t:mutiLang langKey="parent.depart"/>:</label>
						<input id="cc" class="form-control w260"  name="TSPDepart.id" value="${depart.TSPDepart.id}">
					</div>
				</div>
		</div>
		
		<div class="row">
			<div class="col-md-8">
					<div class="form-group">			
						<label class="control-label" for="orgType"><t:mutiLang langKey="common.org.type"/>:</label>
						<input type="hidden" name="orgCode" value="${depart.orgCode }" />
						<select class="form-control w260" name="orgType" id="orgType">
                			<option value="1" <c:if test="${depart.orgType=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.company"/></option>
                			<option value="2" <c:if test="${depart.orgType=='2'}">selected="selected"</c:if>><t:mutiLang langKey="common.department"/></option>
                			<option value="3" <c:if test="${depart.orgType=='3'}">selected="selected"</c:if>><t:mutiLang langKey="common.position"/></option>
           	 			</select>
					</div>
				</div>
		</div>
		<div class="row">
			<div class="col-md-8">
					<div class="form-group">			
						<label class="control-label" for="orgAttr">机构属性:</label>
						
						<t:dictSelect field="orgAttr" type="list"
									typeGroupCode="org_attr" defaultVal="${not empty depart.orgAttr ? depart.orgAttr : '01'}" hasLabel="false"  title="机构属性"  extendJson="{'class':'form-control w260'}"  ></t:dictSelect>
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
						win.reloadTable(data);
						win.tip(data.msg);
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
</script>
</body>
</html>
