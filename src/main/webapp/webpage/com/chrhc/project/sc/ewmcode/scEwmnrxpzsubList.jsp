<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
 var i = 0;
	$('#addScEwmnrxpzsubBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delScEwmnrxpzsubBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addScEwmnrxpzsubBtn').bind('click', function(){ 
 		 var tr =  $("#add_scEwmnrxpzsub_table_template tr").clone();
	 	$("#add_scEwmnrxpzsub_table").append(tr);
	 	 
	 	 resetTrNum('add_scEwmnrxpzsub_table');
	     getTableFieldX($("#sourcetable").val());
	 	 return false;
    });  
	$('#delScEwmnrxpzsubBtn').bind('click', function(){   
      	$("#add_scEwmnrxpzsub_table").find("input:checked").parent().parent().remove();   
      	resetTrNum('add_scEwmnrxpzsub_table'); 
        return false;
    }); 
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#scEwmnrxpzsub_table").createhftable({
	    	height:'300px',
			width:'auto',
			fixFooter:false
			});
    });
</script>
<div class="panel-body panel-body-noheader panel-body-noborder" title="" href="scEwmnrxpzController.do?scEwmnrxpzsubList?id=${scEwmnrxpzPage.id}" style="margin: 0px; padding: 0px; overflow-x: hidden; overflow-y: auto; width: auto; height: auto;">
<div class="tab_box">
	<div>
		<div class="row">
			<div class="col-md-12">
				<button id="addScEwmnrxpzsubBtn" class="btn btn-default" type="button">
					<i class="glyphicon glyphicon-plus"></i>&nbsp;添加
				</button>
				<button id="delScEwmnrxpzsubBtn"  class="btn btn-default" type="button">
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
			<c:if test="${fn:length(scEwmnrxpzsubList)  <= 0 }">
					<tr>
						<td class="center"><div name="xh">1</div></td>
						<td class="center"><input type="checkbox" name="ck"/></td>
							<input name="scEwmnrxpzsubList[0].id" type="hidden"/>
							<input name="scEwmnrxpzsubList[0].pzid" type="hidden"/>
							<input name="scEwmnrxpzsubList[0].delflag" type="hidden"/>
						<td>
							<input name="scEwmnrxpzsubList[0].nrxcode" maxlength="36" id="fieldcode0" type="text" class="form-control" datatype="*1-36">
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
							<input name="scEwmnrxpzsubList[0].remark" maxlength="300" type="text" class="form-control" datatype="*1-200">
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
		   			</tr>
			</c:if>
			<c:if test="${fn:length(scEwmnrxpzsubList)  > 0 }">
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
			</c:if>	
			</tbody>
			
		</table>
			</div>
		</div>
	</div>
	</div>
</div>
	
		


