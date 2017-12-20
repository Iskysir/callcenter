<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>回访满意度</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="hlSatisfiedDegreeController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${hlSatisfiedDegreePage.id }">
					<input id="createName" name="createName" type="hidden" value="${hlSatisfiedDegreePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${hlSatisfiedDegreePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${hlSatisfiedDegreePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${hlSatisfiedDegreePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${hlSatisfiedDegreePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${hlSatisfiedDegreePage.updateDate }">
					<input id="versionNum" name="versionNum" type="hidden" value="${hlSatisfiedDegreePage.versionNum }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${hlSatisfiedDegreePage.sysOrgCode }">
					<input id="delflag" name="delflag" type="hidden" value="${hlSatisfiedDegreePage.delflag }">
					<input id="busType" name="busType" type="hidden" value="${hlSatisfiedDegreePage.busType }">
					<input id="busId" name="busId" type="hidden" value="${hlSatisfiedDegreePage.busId }">
					<input id="bza" name="bza" type="hidden" value="${hlSatisfiedDegreePage.bza }">
					<input id="bzb" name="bzb" type="hidden" value="${hlSatisfiedDegreePage.bzb }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							满意度:
						</label>
					</td>
					<td class="value">
					     	 <input id="degeree" name="degeree" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">满意度</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							回访状态:
						</label>
					</td>
					<td class="value">
					     	 <input id="stState" name="stState" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">回访状态</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							有效工作量:
						</label>
					</td>
					<td class="value">
					     	 <input id="yxgzl" name="yxgzl" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">有效工作量</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							无效工作量:
						</label>
					</td>
					<td class="value">
					     	 <input id="wxgzl" name="wxgzl" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">无效工作量</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							失效工作量:
						</label>
					</td>
					<td class="value">
					     	 <input id="sxgzl" name="sxgzl" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">失效工作量</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							回访人:
						</label>
					</td>
					<td class="value">
					     	 <input id="visitPerson" name="visitPerson" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">回访人</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							回访时间:
						</label>
					</td>
					<td class="value">
							   <input id="visitTime" name="visitTime" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker()"
								                
								               >    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">回访时间</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							客户录入:
						</label>
					</td>
					<td class="value">
					     	 <input id="visitInput" name="visitInput" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">客户录入</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							录入时间:
						</label>
					</td>
					<td class="value">
							   <input id="inputTime" name="inputTime" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker()"
								                
								               >    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">录入时间</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							回访意见:
						</label>
					</td>
					<td class="value">
						  	 <textarea style="width:600px;" class="inputxt" rows="6" id="visitOption" name="visitOption"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">回访意见</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/chrhc/project/hl/visit/hlSatisfiedDegree.js"></script>		