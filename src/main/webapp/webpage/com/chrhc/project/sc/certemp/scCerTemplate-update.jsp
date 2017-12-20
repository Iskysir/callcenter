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
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="scCerTemplateController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${scCerTemplatePage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" nowrap="nowrap">
							<label class="Validform_label" >
								模板名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="tempName" name="tempName" type="text" style="width: 150px" class="inputxt"  datatype="*1-50" 
									               
									                 value='${scCerTemplatePage.tempName}'>
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
									<t:dictSelect field="tempType" type="list"
										typeGroupCode="template_type" defaultVal="${scCerTemplatePage.tempType}" hasLabel="false"  title="模板类型"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">模板类型</label>
						</td>
					</tr>
					
				<tr>
					<td align="right">
						<label class="Validform_label">
							表名:
						</label>
					</td>
					<td class="value">
							 <input id="tableName" name="tableName" type="text" style="width: 150px" class="inputxt"  value='${scCerTemplatePage.tableName}' datatype="*1-200" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">表名</label>
							<span style="color: red;">*</span>
						</td>
				</tr>
				
				<tr>
						<td align="right">
							<label class="Validform_label">
								二维码模板:
							</label>
						</td>
						<td class="value">
									  
									<t:dictSelect field="qrcode" dictTable="sc_ewmnrxpz" dictField="ywbh" dictText="ywmc" defaultVal="${scCerTemplatePage.qrcode}" hasLabel="false"  title="二维码模板"></t:dictSelect>  
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">二维码模板</label>
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
								type="width:1100,height:570" value="${scCerTemplatePage.template}"></t:ckeditor>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/chrhc/project/sc/certemp/scCerTemplate.js"></script>		