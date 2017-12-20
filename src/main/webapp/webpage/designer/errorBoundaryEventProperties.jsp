<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
var tid = '${id}';
var boundaryEvent = workflow.getFigure(tid);
populateBoundaryEventProperites();
function saveBoundaryEventProperties(){
	boundaryEvent.boundaryId=$('#id').val();
	boundaryEvent.attached=$('#attached').val();
	boundaryEvent.expression=$('#expression').val();
	tip("保存成功!");
}
function populateBoundaryEventProperites(){
	$('#id').val(boundaryEvent.boundaryId);
	$('#attached').val(boundaryEvent.attached);
	$('#expression').val(boundaryEvent.expression);
}
</script>
<div id="boundaryEvent-properties-layout" class="easyui-layout" fit="true">
	<div id="boundaryEvent-properties-toolbar-panel" region="north" border="false" style="height:30px;background:#E1F0F2;">
		<a href="##" id="sb2" class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="saveBoundaryEventProperties()">Save</a>
	</div>
	<div id="boundaryEvent-properties-panel" region="center" border="true">
		<div id="boundaryEvent-properties-accordion" class="easyui-accordion" fit="true" border="false">
			<div id="general" title="异常边界属性" selected="true" class="properties-menu">
				<table id="general-properties">
					<tr>
						<td align="right">Id:</td>
						<td><input type="text" id="id" name="id"/></td>
					</tr>
					<tr>
						<td align="right">错误ID:</td>
						<td>
							<input type="text" id="expression" name="expression"/>
					   <td>
					</tr>
					<tr>
						<td align="right">依附到:</td>
						<td><input type="text" id="attached" name="attached"/></td>
					</tr>
					
				</table>
			</div>
		</div>
	</div>
</div>