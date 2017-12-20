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
	boundaryEvent.timeType=$('#timeType').combobox('getValue');
	tip("保存成功!");
}
function populateBoundaryEventProperites(){
	$('#id').val(boundaryEvent.boundaryId);
	$('#attached').val(boundaryEvent.attached);
	$('#expression').val(boundaryEvent.expression);
	$('#boundaryEvent').combobox('setValue',boundaryEvent.timeType);
}

</script>
<div id="boundaryEvent-properties-layout" class="easyui-layout" fit="true">
	<div id="boundaryEvent-properties-toolbar-panel" region="north" border="false" style="height:30px;background:#E1F0F2;">
		<a href="##" id="sb2" class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="saveBoundaryEventProperties()">Save</a>
	</div>
	<div id="boundaryEvent-properties-panel" region="center" border="true">
		<div id="boundaryEvent-properties-accordion" class="easyui-accordion" fit="true" border="false">
			<div id="general" title="时间边界属性" selected="true" class="properties-menu">
				<table id="general-properties">
					<tr>
						<td align="right">Id:</td>
						<td><input type="text" id="id" name="id" /></td>
					</tr>
					<tr>
						<td align="right">事件类型:</td>
						<td>
							<select id="timeType" name="timeType">
							<option value="timeDate">timeDate</option>
							<option value="timeDuration">timeDuration</option>
							<option value="timeCycle">timeCycle</option>
							</select>
					   <td>
					</tr>
					<tr>
						<td align="right">expression:</td>
						<td>
							<input type="text" id="expression" name="expression" />
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