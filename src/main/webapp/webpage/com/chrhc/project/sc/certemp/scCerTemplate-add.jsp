<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>证照模板</title>
  <t:base type="ckeditor,jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
  <script src="plug-in/chrhc/enhancejs/sc_rent_form.js" type="text/javascript"></script>
<script src="plug-in/chrhc/enhancejs/sc_rent_list.js" type="text/javascript"></script>




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
<script type="text/javascript">

function uploadFile(){
	$("#tempName").val("");
	$("#tempType").find("option[selected='selected']").attr("selected",true);
	$("#qrcode").find("option[selected='selected']").attr("selected",true);
	$("#tableName").val("");
	addOneTab("证照模板", "scCerTemplateController.do?scCerTemplate" );
	closeCurrentTab();
	location.href="scCerTemplateController.do?scCerTemplate";
	
//	$.post(
//			"scCerTemplateController.do?scCerTemplate",
//			"",
//			function(){
//				addOneTab("证照模板", "scCerTemplateController.do?scCerTemplate" );;}
//			);
//	closeCurrentTab();
}

</script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="scCerTemplateController.do?doAdd" callback="uploadFile" >
					<input id="id" name="id" type="hidden" value="${scCerTemplatePage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" nowrap="nowrap">
						<label class="Validform_label">
							模板名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="tempName" name="tempName" type="text" style="width: 150px" class="inputxt"     datatype="*1-50" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">模板名称</label>
							<span style="color: red;">*</span>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							模板类型:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="tempType" type="list" id = "tempType"
									typeGroupCode="template_type" defaultVal="${scCerTemplatePage.tempType}" hasLabel="false"  title="模板类型"></t:dictSelect>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">模板类型</label>
						</td>
				</tr>
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							二维码模板:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="qrcode" id="qrcode" dictTable="sc_ewmnrxpz" dictField="ywbh" dictText="ywmc" defaultVal="${scCerTemplatePage.qrcode}" hasLabel="false"  title="二维码模板"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">二维码模板</label>
						</td>
				</tr>
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							表名:
						</label>
					</td>
					<td class="value">
							 <input id="tableName" name="tableName" type="text" style="width: 150px" class="inputxt" datatype="*1-200" value='${scCerTemplatePage.tableName}' datatype="*1-200" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">表名</label>
							<span style="color: red;">*</span>
						</td>
				</tr>
				
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							文件模板:
						</label>
					</td>
					<td class="value">

							<t:ckeditor name="template" isfinder="false"
								type="width:1100,height:570" value="${template}"></t:ckeditor>
	
						</td>
				</tr>
				<tr>
					<td colspan="2">
					<div class="ui_buttons">
						<button id="btn_sub" type="submit"  class="btn btn-sure">确定</button>
						<button type="button" class="btn btn-cancel" onclick="closeCurrentTab()">关闭</button>
					</div>
					</td>
				</tr> 
			</table>
		</t:formvalid>
 </body>
 <script type="text/javascript">
function custom_close(){
	window.opener=null;
	window.close();
}

 </script>
  <script src = "webpage/com/chrhc/project/sc/certemp/scCerTemplate.js"></script>		