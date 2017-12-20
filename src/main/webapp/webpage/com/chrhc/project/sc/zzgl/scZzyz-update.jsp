<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%
String path_java = request.getContextPath();
String basePath_java = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path_java+"/";
%>
<!DOCTYPE html>
<html>
<head>
<title>证照验真结果</title>
 <t:base type="jquery,easyui,tools,DatePicker"></t:base>
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
<script type="text/javascript" src="plug-in/tools/Map.js"></script>

<script src="plug-in/icheck/icheck.js"></script>
<script src="plug-in/chrhc/jquery.tabs.js"></script>
<script src="plug-in/chrhc/jquery.lazyload.js"></script>
<script src="plug-in/Validform/js/Validform_v5.3.1.js" type="text/javascript"></script>

<script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript"
	src="webpage/com/chrhc/project/sc/docfile/gpy.js"></script>
<script src="plug-in/chrhc/bootstrap.min.js"></script>
<script src="plug-in/chrhc/ie8/html5.js"></script>
<script src="plug-in/chrhc/ie8/respond.min.js"></script>
	<script type="text/javascript"
	src="plug-in/artDiglog/jquery.artDialog.js?skin=default"></script>
<%--  validform --%>	
<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css"/>
<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css"/>
<script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>

<script type="text/javascript">
	//增加时的拍照次数   用于记录每条附件数据的序号，以及图片生成序号（）
	var photoindext = 1;
	var picpathall ="";
	var fileid="";
	$(document).ready(function() {
		$("#formobj").attr("class","form-inline valideForm"); 
		$(":input[name!=bz]").attr("disabled",true);
		$(":input[name=bz]").attr("readonly",true);
	
	});
	
	
</script>
<!-- ServerReply event   上传完成事件 -->
<SCRIPT LANGUAGE=javascript FOR=CuteCtrl1 EVENT="ServerReply(filename)">
<!--
	CuteCtrl_ItemSelected(filename)
//-->
</SCRIPT>
<SCRIPT LANGUAGE=javascript FOR=CuteCtrl1 EVENT="Error(filename)">
<!--
	erro_upload(filename)
//-->
</SCRIPT>
<style type="text/css">
.aui_close{
display:none;
}
</style>
</head>
<body style="overflow-x:hidden;">

	<%--引入的demo画面 --%>
	<t:formvalid layout="table" tiptype="4"  action="scZzyzController.do?doAdd"  formid="formobj" dialog="true">
	
		<div class="container form-card">
		
					<input id="id" name="id" type="hidden" value="${scZzyzPage.id }">
					<input id="yzjg" name="yzjg" type="hidden" value="${scZzyzPage.yzjg }">
					<input id="yzsj" name="yzsj" type="hidden" value="${scZzyzPage.yzsj }">
					<input id="scbj" name="scbj" type="hidden" value="${scZzyzPage.scbj }">
					<input id="versionNum" name="versionNum" type="hidden" value="${scZzyzPage.versionNum }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${scZzyzPage.sysOrgCode }">
					<input id="zzdyid" name="zzdyid" type="hidden" value="${scZzyzPage.zzdyid }">
					<input id="createDate" name="createDate" type="hidden" value="${scZzyzPage.createDate }">
		<div class="row">
		    <div class="col-md-12">
		    	<div class="group-title">
					<label>验证结果信息</label>
				</div>
		    </div>
		</div>	
			
		
		<div class="row">
				<div class="col-md-6">
					<label class="control-label" for="zzbh">证件编号</label>
					<input id="zzbh" name="zzbh" type="text" style="width: 150px" class="form-control w260" readonly="readonly" value='${scZzyzPage.zzbh}'/>
				</div>
				<div class="col-md-6">
					<label class="control-label" for="yzjg">验证结果</label>
					<input  type="text" class="form-control w260" id="yzjg" name="yzjg"  value='${scZzyzPage.yzjg}'/><%--validType="sc_doc_war,idcardnum,id" --%>
				</div>
		</div>
		<div class="row">
				
				<div class="col-md-6">
					<label class="control-label" for="bz">备注</label>
					<%--<input  type="text" class="form-control w260" id="bz"  value='${scZzyzPage.bz}'/>validType="sc_doc_war,idcardnum,id" --%>
					<textarea rows="1" cols="20" class="form-control w260" id="bz" name="bz" >${scZzyzPage.bz}</textarea>
					
				</div>
		</div>
	
	</div>
	
	</t:formvalid>
	

</body>
<script src="webpage/com/chrhc/project/sc/docfile/scDocWar.js"></script>

</html>




