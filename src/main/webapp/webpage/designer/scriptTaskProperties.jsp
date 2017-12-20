<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
var tid = '${id}';
var task = workflow.getFigure(tid);
populateTaskProperites();
function saveTaskProperties(){
	task.taskId=$('#id').val();
	task.taskName=$('#name').val();
	task.setContent($('#name').val());
	task.expression=$('#expression').val();
	task.documentation=$('#documentation').val();
	task.scriptFormat=$('#scriptFormat').val();
	task.resultVariable=$('#resultVariable').val();
	tip("保存成功 !");
}
function populateTaskProperites(){
	$('#id').val(task.taskId);
	$('#name').val(task.taskName);
	$('#expression').val(task.expression);
	$('#documentation').val(task.documentation);
	$('#scriptFormat').val(task.scriptFormat);
	$('#resultVariable').val(task.resultVariable);
}
</script>
<div id="task-properties-layout" class="easyui-layout" fit="true">
	<div id="task-properties-toolbar-panel" region="north" border="false" style="height:30px;background:#E1F0F2;">
		<a href="##" id="sb2" class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="saveTaskProperties()">保存</a>
	</div>
	<div id="task-properties-panel" region="center" border="true">
		<div id="task-properties-accordion" class="easyui-accordion" fit="true" border="false">
			<div id="general" title="主属性" selected="true" class="properties-menu">
				<table id="general-properties">
					<tr>
						<td align="right">Id:</td>
						<td><input type="text" id="id" name="id"  value=""/></td>
					</tr>
					<tr>
						<td align="right">标签:</td>
						<td><input type="text" id="name" name="name"  value=""/></td>
					</tr>
					<tr>
						<td align="right">描述:</td>
						<td><textarea id="documentation" name="documentation" cols="15" rows="4"></textarea></td>
					</tr>
					<tr>
						<td align="right">脚本格式:</td>
						<td>
							<select id="scriptFormat" >
								<option value="javascript">javascript</option>
								<option value="groovy" >groovy</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">返回变量:</td>
						<td><input type="text" id="resultVariable" name="resultVariable"  value=""/></td>
					</tr>
					<tr>
						<td align="right">脚本:</td>
						<td>
						<textarea id="expression" name="expression" cols="25" rows="5"></textarea>
						</td>
					</tr>
				</table>
				<fieldset style="line-height: 21px;">
						<legend>说明</legend>
						<div>1.脚本任务，当流程执行到脚本任务时，执行相应的脚本。</div>
						<div>2.默认脚本变量不自动保存到流程变量，需在脚本中调用:execution.setVariable("myVar",val)保存到流程变量。</div>
						<div>3.脚本内容需要和脚本格式对应。</div>
				</fieldset>
			</div>
		</div>
	</div>
</div>