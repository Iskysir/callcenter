<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>二维码配置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>

<link href="plug-in/chrhc/bootstrap.css" rel="stylesheet">
<link href="plug-in/chrhc/style.css" rel="stylesheet">
<link href="plug-in/icheck/skin/square/blue.css" rel="stylesheet">
<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>

<script src="plug-in/chrhc/bootstrap.min.js"></script>
<script src="plug-in/chrhc/ie8/html5.js"></script>
<script src="plug-in/chrhc/ie8/respond.min.js"></script>
<script src="plug-in/icheck/icheck.js"></script>
<script src="plug-in/chrhc/jquery.tabs.js"></script>
<script src="plug-in/chrhc/jquery.lazyload.js"></script>
<script src="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.js" type="text/javascript"></script>
<script src="plug-in/Validform/js/Validform_v5.3.1.js" type="text/javascript"></script>
  <style>
  	.ac_results {
  		border:1px solid #CCCCCC !important;
		background-color: #FFFFFF !important;
	}
	.ac_results li:hover {
		color:#ffffff !important;
		background-color: #0A246A;
	}
	li{
		color:#000 !important;
	}
  </style>
 <script type="text/javascript">
  $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	$(".tabs-wrap").css('width','100%');

	$("#formobj").attr('class', 'form-inline valideForm');

	$("#sourcetable").attr('class', 'form-control w260').css('background-color','#ffffff');
	
	$("select").attr({"class":"form-control w260"});

	$("input[type=radio][name=sfyy][value=yes]").attr("checked",true); 
	$("input[type=radio][name=sfyy][value=yes]").parent().addClass("checked",true);  
	
  });

  function addLine(){
	  var tr =  $("#add_scEwmnrxpzsub_table_template tr").clone();
	 	$("#add_scEwmnrxpzsub_table").append(tr);
	 	 
	 	 resetTrNum('add_scEwmnrxpzsub_table');
	     getTableFieldX($("#sourcetable").val());
	 	 return false;
  }


  function delLine(){ 
		$("#add_scEwmnrxpzsub_table").find("input:checked").parent().parent().remove();   
		resetTrNum('add_scEwmnrxpzsub_table'); 
		return false;
  }


  

  function parse(data){
  	var parsed = [];
      	$.each(data.rows,function(index,row){
      		parsed.push({data:row,result:row,value:row.id});
      	});
				return parsed;
}
/**
* 选择后回调 
* 
* @param {Object} data
*/
function callBack(data) {
	var tablename = data.tableName;
	$("#sourcetable").val(data.tableName).attr('class', 'form-control w260');

	getTableField(tablename);
	
}

/**
* 每一个选择项显示的信息
* 
* @param {Object} data
*/
function formatItem(data) {
	return data.tableName;
}

 </script>
 </head>
 <body style="overflow-x: hidden;">

  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="4" action="scEwmnrxpzController.do?doAdd">
	<input id="id" name="id" type="hidden" value="${scEwmnrxpzPage.id }">
	<input id="createName" name="createName" type="hidden" value="${scEwmnrxpzPage.createName }">
	<input id="createBy" name="createBy" type="hidden" value="${scEwmnrxpzPage.createBy }">
	<input id="createDate" name="createDate" type="hidden" value="${scEwmnrxpzPage.createDate }">
	<input id="updateName" name="updateName" type="hidden" value="${scEwmnrxpzPage.updateName }">
	<input id="updateBy" name="updateBy" type="hidden" value="${scEwmnrxpzPage.updateBy }">
	<input id="updateDate" name="updateDate" type="hidden" value="${scEwmnrxpzPage.updateDate }">
	<input id="delflag" name="delflag" type="hidden" value="0">
	<input id="versionNum" name="versionNum" type="hidden" value="0">
	<div class="container form-card">
			<div class="row">
				<div class="col-md-12">
					<div class="group-title">
						<label>基本信息</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<label class="control-label">业务编号:</label>
					<input id="ywbh" name="ywbh" type="text" class="form-control w260" validtype="sc_ewmnrxpz,ywbh,id" datatype="w1,s2-30">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">业务编号</label>
					<span style="color:red;">*</span>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<label class="control-label">业务名称: </label>
					<input id="ywmc" name="ywmc" type="text" class="form-control w260" ignore="ignore" datatype="*1-30">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">业务名称</label>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<label class="control-label">前端传输条件:</label>
					<input id="frontfield" name="frontfield" type="text" class="form-control w260" datatype="*1-30">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">前端传输条件</label>
					<span style="color:red;">*</span>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<label class="control-label">数据来源表:</label>
					<t:autocomplete minLength="1" dataSource="commonController.do?getAutoList" closefun="close" valueField="id" searchField="tableName" 
		         		labelField="tableName" parse="parse" formatItem="formatItem" result="callBack"  name="sourcetable" entityName="CgFormHeadEntity" datatype="*1-32" maxRows="10" nullmsg="请输入关键字" errormsg="数据不存在,请重新输入">
		        	</t:autocomplete>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">数据来源表</label>
					<span style="color:red;">*</span>
				</div>
			</div>
			<div class="row">
		    	<div class="col-md-12">
					<label class="control-label" for="exampleInputName2">是否已经使用:</label>
					<t:dictSelect field="sfyy" type="radio" typeGroupCode="yesno"  hasLabel="false" title="是否已经使用"></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">是否已经使用</label>
					<span style="color:red;">*</span>    
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<label class="control-label">备注信息</label>
					<textarea id="remark" class="form-control" rows="3" cols="80" ignore="ignore" datatype="*1-200" name="remark"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">备注信息</label>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-12">
					<div class="box demo">
						<ul class="tab_menu">
							<li class="current">二维码返回信息</li>
						</ul>
						<div class="tab_box">
							<div>
								<div class="row">
									<div class="col-md-12">
										<button id="addScEwmnrxpzsubBtn" onclick="addLine()" class="btn btn-default" type="button">
											<i class="glyphicon glyphicon-plus"></i>&nbsp;添加
										</button>
										<button id="delScEwmnrxpzsubBtn" onclick="delLine()" class="btn btn-default" type="button">
											<i class="glyphicon glyphicon-trash"></i>&nbsp;删除
										</button>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<table id="scEwmnrxpzsub_table" class="table table-bordered">
											<tr>
												<th class="center">序号</th>
												<th class="center"></th>
												<th>内容项代码</th>
												<th>内容项名称</th>
												<th>备注</th>
											</tr>
											<tbody id="add_scEwmnrxpzsub_table" class="checkboxchrhc">
												<tr id="trDemo">
													<td class="center"><div name="xh">1</div></td>
													<td class="center"><input type="checkbox" name="ck"/></td>
														<input name="scEwmnrxpzsubList[0].id" type="hidden"/>
														<input name="scEwmnrxpzsubList[0].pzid" type="hidden"/>
														<input name="scEwmnrxpzsubList[0].delflag" type="hidden"/>
													<td>
														<input name="scEwmnrxpzsubList[0].nrxcode" maxlength="36" id="fieldcode0" type="text" readonly="true" class="form-control" datatype="*1-36">
														<label class="Validform_label" style="display: none;">内容项代码</label>
														</td>
													<td>
									                    <select id="fieldname0" name="nrxnamehidden0" class="form-control" datatype="*1-60" ignore="ignore" onchange="getcode(this)">
															<option value="">---请选择---</option>
														</select>
														<input name="scEwmnrxpzsubList[0].nrxname" type="hidden" />
														<label class="Validform_label" style="display: none;">内容项名称</label>
													</td>
													<td align="left">
														<input name="scEwmnrxpzsubList[0].remark" maxlength="300" type="text" class="form-control" ignore="ignore" datatype="*1-200">
														<label class="Validform_label" style="display: none;">备注</label>
													</td>
									   			</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 center">
					<button id="btn_sub" type="submit" class="btn btn-sure">确定</button>
          			<button type="submit" class="btn btn-cancel" onclick="closeCurrentTab()">关闭</button>
				</div>
			</div>
	</div>
 </t:formvalid>

	<!-- 添加 附表明细 模版 -->
	<table style="display:none">
	<tbody id="add_scEwmnrxpzsub_table_template" class="checkboxchrhc">
		<tr>
			 <td class="center"><div name="xh"></div></td>
			 <td class="center"><input type="checkbox" name="ck"/></td>
			 <td>
				<input id="fieldcode#index#" name="scEwmnrxpzsubList[#index#].nrxcode" maxlength="36" type="text" readonly="true" class="form-control"  datatype="*1-36"/>
				<label class="Validform_label" style="display: none;">内容项代码</label>
			</td>
			<td>
		  		<select id="fieldname#index#" name="nrxnamehidden#index#" class="form-control"  datatype="*1-60" ignore="ignore" onchange="getcode(this)">
					<option value="">---请选择---</option>
				</select>
				<input name="scEwmnrxpzsubList[#index#].nrxname" type="hidden" />
			  <label class="Validform_label" style="display: none;">内容项名称</label>
		  </td>
		  <td>
			  <input name="scEwmnrxpzsubList[#index#].remark" maxlength="200" type="text" class="form-control"  ignore="ignore"  datatype="*1-200"/>
			  <label class="Validform_label" style="display: none;">备注</label>
		  </td>
		</tr>
	</tbody>
		</table>
	<script type="text/javascript">
		$(document).ready(function() {
			$('input').not('.checkboxchrhc input').iCheck({
				checkboxClass : 'icheckbox_square-blue',
				radioClass : 'iradio_square-blue',
				increaseArea : '20%' 
			});
			// 全选
			$('.table input#selectAll').on('ifChecked', function(event) {
				$('.table input').iCheck('check');
			});
			// 取消全选
			$('.table input#selectAll').on('ifUnchecked', function(event) {
				$('.table input').iCheck('uncheck');
			});
			$('.demo').Tabs({
				event : 'click'
			});

			
		});
		function addTr() {
			var trClone = $("#trDemo").clone();
			$('input').iCheck({
				checkboxClass : 'icheckbox_square-blue',
				radioClass : 'iradio_square-blue',
				increaseArea : '20%' // optional
			});
			$("#scEwmnrxpzsub_table").append(trClone);
		}
	</script>
 </body>
 <script src = "webpage/com/chrhc/project/sc/ewmcode/scEwmnrxpz.js"></script>	