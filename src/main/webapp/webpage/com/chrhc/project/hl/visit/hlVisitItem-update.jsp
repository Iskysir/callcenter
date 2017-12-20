<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>回访题库</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="hlVisitItemController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${hlVisitItemPage.id }">
					<input id="createName" name="createName" type="hidden" value="${hlVisitItemPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${hlVisitItemPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${hlVisitItemPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${hlVisitItemPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${hlVisitItemPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${hlVisitItemPage.updateDate }">
					<input id="versionNum" name="versionNum" type="hidden" value="${hlVisitItemPage.versionNum }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${hlVisitItemPage.sysOrgCode }">
					<input id="delflag" name="delflag" type="hidden" value="${hlVisitItemPage.delflag }">
					<input id="bza" name="bza" type="hidden" value="${hlVisitItemPage.bza }">
					<input id="bzb" name="bzb" type="hidden" value="${hlVisitItemPage.bzb }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								题目名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="itemName" name="itemName" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${hlVisitItemPage.itemName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">题目名称</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								所属类别:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="itemType" type="list"
										typeGroupCode="hl_bus_type" defaultVal="${hlVisitItemPage.itemType}" hasLabel="false"  title="所属类别"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">所属类别</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/chrhc/project/hl/visit/hlVisitItem.js"></script>		