<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>业务回访关联表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="hlBusVisitController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${hlBusVisitPage.id }">
					<input id="createName" name="createName" type="hidden" value="${hlBusVisitPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${hlBusVisitPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${hlBusVisitPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${hlBusVisitPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${hlBusVisitPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${hlBusVisitPage.updateDate }">
					<input id="versionNum" name="versionNum" type="hidden" value="${hlBusVisitPage.versionNum }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${hlBusVisitPage.sysOrgCode }">
					<input id="delflag" name="delflag" type="hidden" value="${hlBusVisitPage.delflag }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								业务主键:
							</label>
						</td>
						<td class="value">
						     	 <input id="busId" name="busId" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${hlBusVisitPage.busId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">业务主键</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								业务类别:
							</label>
						</td>
						<td class="value">
						     	 <input id="busType" name="busType" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${hlBusVisitPage.busType}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">业务类别</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								回访题目主键:
							</label>
						</td>
						<td class="value">
						     	 <input id="visitId" name="visitId" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${hlBusVisitPage.visitId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">回访题目主键</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								回访答案:
							</label>
						</td>
						<td class="value">
						     	 <input id="visitResult" name="visitResult" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${hlBusVisitPage.visitResult}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">回访答案</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								预留字段a:
							</label>
						</td>
						<td class="value">
						     	 <input id="bza" name="bza" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${hlBusVisitPage.bza}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">预留字段a</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								预留字段b:
							</label>
						</td>
						<td class="value">
						     	 <input id="bzb" name="bzb" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${hlBusVisitPage.bzb}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">预留字段b</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/chrhc/project/hl/visit/hlBusVisit.js"></script>		