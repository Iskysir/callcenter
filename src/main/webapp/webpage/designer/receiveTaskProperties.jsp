<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
<!--
var tid = '${id}';
var task = workflow.getFigure(tid);
$(function(){
	$('#performerType').combobox({
			editable:false,
			onChange:function(newValue, oldValue){
				switchTaskCandidatesList(newValue);
			}
		});
	_listener_win = $('#listener-win').window({
		//href:'${ctx}/wf/procdef/procdef!forTaskListenerConfig.action',   
	    closed:true,
	    //cache:false,
	    draggable:false,
	    collapsible:false,
	    minimizable:false,
	    maximizable:false,
	    modal:true,
	    shadow:true
	});
	$('#task-listeners-list').datagrid({
		//title:"Listener",
		//url:'${ctx}/wf/procdef/procdef!search.action',//加载表格数据的URL
		singleSelect:true,
		//width:500,
		height:300,
		//idField:'id',
		//pagination:true,
		//pageSize:15,
		//pageNumber:1,
		//pageList:[10,15],
		rownumbers:true,
		//sortName:'id',
	    //sortOrder:'asc',
	    striped:true,
	    toolbar:[{
	        text:'New',
	        iconCls:'icon-add',
	        handler:function(){
	    		_listener_win.window('open');
	    		//_listener_frame.src="";
	    		_listener_win.window('refresh','activitiController.do?processProperties&turn=taskListenerConfig');
	    		//alert(_listener_frame.document.body.innerHTML);
	        }
	    }]
	});
	$('#task-form-properties-list').datagrid({
		//title:"Listener",
		//url:'${ctx}/wf/procdef/procdef!search.action',//加载表格数据的URL
		singleSelect:true,
		//width:500,
		height:300,
		//idField:'id',
		//pagination:true,
		//pageSize:15,
		//pageNumber:1,
		//pageList:[10,15],
		rownumbers:true,
		//sortName:'id',
	    //sortOrder:'asc',
	    striped:true,
	    toolbar:[{
	        text:'New',
	        iconCls:'icon-add',
	        handler:function(){
	        }
	    }]
	});
	task_candidate_panel=$('#task-candidate-panel').panel({
		border:false,
		//minimized:true,
		height:450
		//width:
		//fit:true
	});
	populateTaskProperites();
	//switchTaskCandidatesList($('#performerType').combobox('getValue'));
});
function switchTaskCandidatesList(performerType){
	if(performerType == 'candidateUsers'){
		task_candidate_panel.panel("refresh","activitiController.do?processProperties&turn=candidateUsersConfig");
	}else if(performerType == 'candidateGroups'){
		task_candidate_panel.panel("refresh","activitiController.do?processProperties&turn=candidateGroupsConfig");
	}
}
function listenerActionBt(value,rowData,rowIndex){
	var id = rowData.id;
	var e = '<img onclick="editListener(\''+id+'\')" src="plug-in/designer/img/edit.gif" title="'+"修改"+'" style="cursor:hand;"/>';   
    var d = '<img onclick="deleteListener(\''+id+'\')" src="plug-in/designer/img/delete.gif" title="'+"删除"+'" style="cursor:hand;"/>';
	return e+'&nbsp;'+d;
}
function editListener(id){
	_listener_win.window('open');
	_listener_win.window('refresh','taskListenerConfig.html');
}
function deleteListener(id){
	task.deleteListener(id);
	loadTaskListeners();
}
function formActionBt(value,rowData,rowIndex){
	var id = rowData.id;
	var e = '<img onclick="editForm('+id+')" src="${ctx}/image/edit.gif" title="'+"修改"+'" style="cursor:hand;"/>';   
    var d = '<img onclick="deleteForm('+id+')" src="${ctx}/image/delete.gif" title="'+"删除"+'" style="cursor:hand;"/>';
	return e+'&nbsp;'+d;
}
function editForm(id){
	
}
function deleteForm(id){
	
}
function saveTaskProperties(){
	task.taskId=$('#id').val();
	task.taskName=$('#name').val();
	task.setContent($('#name').val());
}
function populateTaskProperites(){
	$('#id').val(task.taskId);
	$('#name').val(task.taskName);
}
function loadTaskListeners(){
	var listeners = task.listeners;
	var listener_grid_rows=[];
	for(var i=0;i<listeners.getSize();i++){
		var listener = listeners.get(i);
		var nlistener = {
					id:listener.getId(),
					listenerImplimentation:listener.getServiceImplementation(),
					type:listener.serviceType,
					event:listener.event,
					fields:listener.getFieldsString(),
					action:''
				};
		listener_grid_rows[i]=nlistener;
	};
	var listener_grid_data={
			total:listeners.getSize(),
			rows:listener_grid_rows
	};
	$('#task-listeners-list').datagrid('loadData',listener_grid_data);
}
//-->
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
				</table>
			</div>
		</div>
	</div>
</div>