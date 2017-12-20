<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>亲属关系表</title>
  <link href="plug-in/chrhc/bootstrap.css" rel="stylesheet">
<link href="plug-in/chrhc/style.css" rel="stylesheet">
<link href="plug-in/icheck/skin/square/blue.css" rel="stylesheet">
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script src="plug-in/chrhc/bootstrap.min.js"></script>
<script src="plug-in/chrhc/ie8/html5.js"></script>
<script src="plug-in/chrhc/ie8/respond.min.js"></script>
<script src="plug-in/icheck/icheck.js"></script>
<script src="plug-in/chrhc/jquery.tabs.js"></script>
<script src="plug-in/chrhc/jquery.lazyload.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="kinshipController.do?doUpdate" tiptype="4" beforeSubmit="updateselect">
					 <div class="container form-card">
					<input id="id" name="id" type="hidden" value="${kinshipPage.id }">
					<input id="ryId" name="ryId" type="hidden" value="${kinshipPage.ryId }">
					<input id="delflag" name="delflag" type="hidden" value="${kinshipPage.delflag }">
					<input id="delDate" name="delDate" type="hidden" value="${kinshipPage.delDate }">
					<input id="obligatea" name="obligatea" type="hidden" value="${kinshipPage.obligatea }">
					<input id="obligateb" name="obligateb" type="hidden" value="${kinshipPage.obligateb }">
					<input id="obligatec" name="obligatec" type="hidden" value="${kinshipPage.obligatec }">
					<input id="obligated" name="obligated" type="hidden" value="${kinshipPage.obligated }">
					<input id="obligatee" name="obligatee" type="hidden" value="${kinshipPage.obligatee }">
					<input id="qsId" name="qsId" type="hidden" value="${kinshipPage.qsId }">
					<input id="ryName" name="ryName" type="hidden" value="${kinshipPage.ryName }">
					<input id="sex" name="sex" type="hidden">
			<div class="row">
				<div class="col-md-12">
					<div class="group-title">
						<label>基本信息</label>
					</div>
				</div>
			</div>
			<div class="row">
			<div class="col-md-12">
			<div class="form-group">
						<label class="control-label" for="gxlx">关系类型</label> 
						<t:dictSelect field="gxlx" type="list"
									typeGroupCode="qsgx" defaultVal="${kinshipPage.gxlx}" hasLabel="false"  title="关系类型" extendJson="{'disabled':'true'}"></t:dictSelect>     
							<span style="color: red;">*</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">关系类型</label>
							
						
					</div>
			</div>
			</div>
			<div class="row">
			<div class="col-md-12">
			<div class="form-group">
						<label class="control-label" for="name">姓名</label> 
							<input id="name" name="name" type="text" class="form-control w260"  value='${kinshipPage.name}'
							onclick="inputClick(this,'xm','sc_rkjbxxnew','name=xm,qsId=id,sex=xb','{&quot;singleSelect&quot;:true}') " datatype="*1-100" readonly="readonly">
							<span class="suoshu" onclick="inputClick(this,'xm','sc_rkjbxxnew','name=xm,qsId=id,sex=xb','{&quot;singleSelect&quot;:true}') "></span>	
							<span style="color: red;">*</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">姓名</label>
						
					</div>
			</div>
			</div>
			<div class="row">
			<div class="col-md-12">
			<div class="form-group">
						<label class="control-label" for="name">备注</label> 
							 <textarea class="form-control" rows="3" cols="80" id="bz" name="bz" datatype="*1-200" ignore="ignore">${kinshipPage.bz}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
					</div>
			</div>
			</div>

		<div class="row" id="buttonPanel">
				<div class="col-md-12 center">
					<button type="submit" class="btn btn-sure">确定</button>
          			<button type="button" class="btn btn-cancel" onclick="closeCurrentTab()">关闭</button>
				</div>
			</div>
		</div>
					
					
		<!--  <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								关系类型:<span style="color: red;">*</span>
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="gxlx" type="list"
										typeGroupCode="qsgx" defaultVal="${kinshipPage.gxlx}" hasLabel="false"  title="关系类型" extendJson="{'disabled':'true'}"></t:dictSelect>     
								
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">关系类型</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								姓名:<span style="color: red;">*</span>
							</label>
						</td>
						<td class="value">
						      		<input id="name" name="name" type="text" style="width: 150px" class="searchbox-inputtext"  onclick="inputClick(this,'xm','sc_rkjbxxnew','name=xm,qsId=id,sex=xb','{&quot;singleSelect&quot;:true}') " datatype="*1-100"
									                 value='${kinshipPage.name}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">姓名</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value">
						  	 	<textarea id="bz" style="width:600px;" class="inputxt" rows="6" name="bz">${kinshipPage.bz}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
				<td align="right">
					<label class="Validform_label">
					</label>
				</td>
				<td class="value">
				</td>
					</tr>
			</table>-->
			
			
			
			
			
			
			
		</t:formvalid>
 </body>
  <script src = "webpage/com/chrhc/project/sc/kinship/kinship.js"></script>		