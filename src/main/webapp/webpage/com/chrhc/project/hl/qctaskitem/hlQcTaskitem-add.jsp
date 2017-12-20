<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>质检考评</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="hlQcTaskitemController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${hlQcTaskitemPage.id }">
					<input id="createName" name="createName" type="hidden" value="${hlQcTaskitemPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${hlQcTaskitemPage.createBy }">
					<input id="updateName" name="updateName" type="hidden" value="${hlQcTaskitemPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${hlQcTaskitemPage.updateBy }">
					<input id="versionNum" name="versionNum" type="hidden" value="${hlQcTaskitemPage.versionNum }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${hlQcTaskitemPage.sysOrgCode }">
					<input id="delflag" name="delflag" type="hidden" value="${hlQcTaskitemPage.delflag }">
					<input id="createDate" name="createDate" type="hidden" value="${hlQcTaskitemPage.createDate }">
					<input id="updateDate" name="updateDate" type="hidden" value="${hlQcTaskitemPage.updateDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							业务主键:
						</label>
					</td>
					<td class="value">
					     	 <input id="orderid" name="orderid" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">业务主键</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							业务类型:
						</label>
					</td>
					<td class="value">
					     	 <input id="ordertype" name="ordertype" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">业务类型</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							质检任务主键:
						</label>
					</td>
					<td class="value">
					     	 <input id="taskid" name="taskid" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">质检任务主键</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							考评得分:
						</label>
					</td>
					<td class="value">
					     	 <input id="avsorce" name="avsorce" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">考评得分</label>
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
								               
								               >
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
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">预留字段b</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							预留字段c:
						</label>
					</td>
					<td class="value">
					     	 <input id="bzc" name="bzc" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">预留字段c</label>
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
  <script src = "webpage/com/chrhc/project/hl/qctaskitem/hlQcTaskitem.js"></script>		