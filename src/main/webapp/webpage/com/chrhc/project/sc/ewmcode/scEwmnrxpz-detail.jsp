<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>二维码内容项配置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script src="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.js" type="text/javascript"></script>
  <link href="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.css" rel="stylesheet" type="text/css" />

<link href="plug-in/chrhc/bootstrap.css" rel="stylesheet">
<link href="plug-in/chrhc/style.css" rel="stylesheet">
<link href="plug-in/icheck/skin/square/blue.css" rel="stylesheet">
	
<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<script src="plug-in/chrhc/bootstrap.min.js"></script>
<script src="plug-in/icheck/icheck.js"></script>
<script src="plug-in/chrhc/jquery.tabs.js"></script>
<script src="plug-in/chrhc/jquery.lazyload.js"></script>
<script src="plug-in/Validform/js/Validform_v5.3.1.js" type="text/javascript"></script>
  <script type="text/javascript">
  $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	$(".tabs-wrap").css('width','100%');


	$("#formobj").attr('class', 'form-inline valideForm');

	$("#sourcetable").val('${scEwmnrxpzPage.sourcetable}').attr('class', 'form-control w260').attr('readOnly','readOnly');
  });
var oldtable = '${scEwmnrxpzPage.sourcetable}';


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

		$("#sourcetable").val(data.tableName);

		

		if(oldtable == tablename){
			getTableFieldX(tablename);
		} else {

			$("#add_scEwmnrxpzsub_table tr").not('tr:eq(0)').remove();
			$("#add_scEwmnrxpzsub_table tr:eq(0) :input").val("");
			getTableField(tablename);
		}

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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="4" action="">
	<input id="id" name="id" type="hidden" value="${scEwmnrxpzPage.id }">
	<input id="delflag" name="delflag" type="hidden" value="${scEwmnrxpzPage.delflag }">
	<input id="versionNum" name="versionNum" type="hidden" value="${scEwmnrxpzPage.versionNum }">
	<div class="container form-card">
			<div class="row">
				<div class="col-md-12">
					<label class="control-label">业务编号:</label>
					<input id="ywbh" name="ywbh" type="text" class="form-control w260" readonly="readonly" value='${scEwmnrxpzPage.ywbh}'>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<label class="control-label">业务名称: </label>
					<input id="ywmc" name="ywmc" type="text" class="form-control w260" readonly="readonly" value='${scEwmnrxpzPage.ywmc}'>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<label class="control-label">前端传输条件:</label>
					<input id="frontfield" name="frontfield" type="text" class="form-control w260" readonly="readonly" value='${scEwmnrxpzPage.frontfield}'>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<label class="control-label">数据来源表:</label>
					<t:autocomplete minLength="1" dataSource="commonController.do?getAutoList" closefun="close" valueField="id" searchField="tableName"
		         		labelField="tableName" parse="parse" formatItem="formatItem" result="callBack"  name="sourcetable" entityName="CgFormHeadEntity" datatype="*" maxRows="10" nullmsg="请输入关键字" errormsg="数据不存在,请重新输入">
		        	</t:autocomplete>
				</div>
			</div>
			<div class="row">
		    	<div class="col-md-12">
					<label class="control-label" for="exampleInputName2">是否已经使用:</label>
					<t:dictSelect field="sfyy" type="radio"
						typeGroupCode="yesno" defaultVal="${scEwmnrxpzPage.sfyy}" hasLabel="false"  title="是否已经使用"></t:dictSelect>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<label class="control-label">备注信息</label>
					<textarea id="remark" class="form-control" rows="3" cols="80" ignore="ignore"  name="remark">${scEwmnrxpzPage.remark}</textarea>
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
												<th class="center"><input type="checkbox" /></th>
												<th>内容项代码</th>
												<th>内容项名称</th>
												<th>备注</th>
											</tr>
											<tbody id="add_scEwmnrxpzsub_table">
												<c:forEach items="${scEwmnrxpzsubList}" var="poVal" varStatus="stuts">
													<tr>
														<td class="center"><div name="xh">${stuts.index+1 }</div></td>
														<td class="center"><input type="checkbox" name="ck" /></td>
															<input name="scEwmnrxpzsubList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
															<input name="scEwmnrxpzsubList[${stuts.index }].pzid" type="hidden" value="${poVal.pzid }"/>
															<input name="scEwmnrxpzsubList[${stuts.index }].delflag" type="hidden" value="${poVal.delflag }"/>
														   <td>
															  <input name="scEwmnrxpzsubList[${stuts.index }].nrxcode" maxlength="36" id="fieldcode'${stuts.index }'" type="text" class="form-control" datatype="*1-36" value="${poVal.nrxcode }">
															  <label class="Validform_label" style="display: none;">内容项代码</label>
														   </td>
														   <td>
														   	 <input name="scEwmnrxpzsubList[${stuts.index }].nrxname" type="hidden" value="${poVal.nrxname }"/>
															 <select id="fieldname'${stuts.index }'" name="nrxnamehidden${stuts.index }" class="form-control" datatype="*1-100" ignore="ignore" onchange="getcode(this)">
																<option value="">---请选择---</option>
																<c:forEach items="${fieldlist}" var="field" varStatus="status">
																	<c:if test="${field.content==poVal.nrxname }">
										                              <option value="${field.field_name}" selected="selected">${field.content}</option>
										                            </c:if>
										                              <option value="${field.field_name}" >${field.content}</option>
										            			</c:forEach>
															</select>
															  <label class="Validform_label" style="display: none;">内容项名称</label>
														   </td>
														   <td>
															  <input name="scEwmnrxpzsubList[${stuts.index }].remark" maxlength="300"  type="text" class="form-control"  datatype="*1-200" value="${poVal.remark }">
															  <label class="Validform_label" style="display: none;">备注</label>
														   </td>
										   			</tr>
												</c:forEach>
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
					<button type="submit" class="btn btn-sure">确定</button>
          			<button type="submit" class="btn btn-cancel">关闭</button>
				</div>
			</div>
	</div>
</t:formvalid>
 </body>
 <script src = "webpage/com/chrhc/project/sc/ewmcode/scEwmnrxpz.js"></script>	