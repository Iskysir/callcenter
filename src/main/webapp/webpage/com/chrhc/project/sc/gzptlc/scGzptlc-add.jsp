<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>网上办事</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="scGzptlcController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${scGzptlcPage.id }">
					<input id="createName" name="createName" type="hidden" value="${scGzptlcPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${scGzptlcPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${scGzptlcPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${scGzptlcPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${scGzptlcPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${scGzptlcPage.updateDate }">
					<input id="versionNum" name="versionNum" type="hidden" value="${scGzptlcPage.versionNum }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${scGzptlcPage.sysOrgCode }">
					<input id="delflag" name="delflag" type="hidden" value="${scGzptlcPage.delflag }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							姓名:
						</label>
					</td>
					<td class="value">
					     	 <input id="name" name="name" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">姓名</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							性别:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="xb" type="list"
									typeGroupCode="sex" defaultVal="${scGzptlcPage.xb}" hasLabel="false"  title="性别"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">性别</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							身份证号:
						</label>
					</td>
					<td class="value">
					     	 <input id="sfzh" name="sfzh" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">身份证号</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							出生年月:
						</label>
					</td>
					<td class="value">
					     	 <input id="csrq" name="csrq" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">出生年月</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							办理类型:
						</label>
					</td>
					<td class="value">
					     	 <input id="bizType" name="bizType" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">办理类型</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							办理状态:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="status" type="list"
									typeGroupCode="spflag" defaultVal="${scGzptlcPage.status}" hasLabel="false"  title="办理状态"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">办理状态</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							审批意见:
						</label>
					</td>
					<td class="value">
						  	 <textarea style="width:600px;" class="inputxt" rows="6" id="spyj" name="spyj"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">审批意见</label>
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
  <script src = "webpage/com/chrhc/project/sc/gzptlc/scGzptlc.js"></script>		