<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>二维码比较日志</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="scEwmLogController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${scEwmLogPage.id }">
					<input id="createName" name="createName" type="hidden" value="${scEwmLogPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${scEwmLogPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${scEwmLogPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${scEwmLogPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${scEwmLogPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${scEwmLogPage.updateDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							业务id:
						</label>
					</td>
					<td class="value">
					     	 <input id=" ywId" name=" ywId" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">业务id</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							业务类型:
						</label>
					</td>
					<td class="value">
					     	 <input id="ywType" name="ywType" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">业务类型</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							业务表单:
						</label>
					</td>
					<td class="value">
					     	 <input id="ywTable" name="ywTable" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">业务表单</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							比较次数:
						</label>
					</td>
					<td class="value">
					     	 <input id="operTimes" name="operTimes" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">比较次数</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							当事人姓名:
						</label>
					</td>
					<td class="value">
					     	 <input id="name" name="name" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">当事人姓名</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							当事人证件号码:
						</label>
					</td>
					<td class="value">
					     	 <input id="cardid" name="cardid" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">当事人证件号码</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/chrhc/project/sc/ewmtest/scEwmLog.js"></script>		