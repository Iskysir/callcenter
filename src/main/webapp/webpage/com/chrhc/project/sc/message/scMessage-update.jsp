<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>消息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="scMessageController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${scMessagePage.id }">
					<input id="createName" name="createName" type="hidden" value="${scMessagePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${scMessagePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${scMessagePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${scMessagePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${scMessagePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${scMessagePage.updateDate }">
					<input id="versionNum" name="versionNum" type="hidden" value="${scMessagePage.versionNum }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${scMessagePage.sysOrgCode }">
					<input id="delflag" name="delflag" type="hidden" value="${scMessagePage.delflag }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								信息类型:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="mType" type="checkbox"
										typeGroupCode="es_type" defaultVal="${scMessagePage.mType}" hasLabel="false"  title="信息类型"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">信息类型</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								内容:
							</label>
						</td>
						<td class="value">
						  	 	<textarea id="content" style="width:600px;" class="inputxt" rows="6" name="content">${scMessagePage.content}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">内容</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								消息标题:
							</label>
						</td>
						<td class="value">
						     	 <input id="title" name="title" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${scMessagePage.title}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">消息标题</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								发送人:
							</label>
						</td>
						<td class="value">
						     	 <input id="publisher" name="publisher" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${scMessagePage.publisher}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发送人</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								接收人:
							</label>
						</td>
						<td class="value">
						     	 <input id="receiver" name="receiver" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${scMessagePage.receiver}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">接收人</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								发送时间:
							</label>
						</td>
						<td class="value">
									  <input id="pTime" name="pTime" type="text" style="width: 150px" 
						      						 class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									                
						      						 value='<fmt:formatDate value='${scMessagePage.pTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发送时间</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								状态:
							</label>
						</td>
						<td class="value">
						     	 <input id="status" name="status" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${scMessagePage.status}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">状态</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value">
						  	 	<textarea id="remark" style="width:600px;" class="inputxt" rows="6" name="remark">${scMessagePage.remark}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								模板类型:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="modelType" type="list"
										typeGroupCode="" defaultVal="${scMessagePage.modelType}" hasLabel="false"  title="模板类型"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">模板类型</label>
						</td>
				<td align="right">
					<label class="Validform_label">
					</label>
				</td>
				<td class="value">
				</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/chrhc/project/sc/message/scMessage.js"></script>		