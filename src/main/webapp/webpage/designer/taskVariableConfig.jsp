<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@include file="/context/mytags.jsp"%>
	<t:base type="jquery,easyui,tools"></t:base> 
<script type="text/javascript">
<!--
var variableFieldsEditCount = 0;
var variableId = '${id}';
var processNode='${processNode}';
var processId='${processId}';
var processDefinitionId='${processDefinitionId}';
var files='processproid,processprokey,processprotype,processproval,processproexp,processproname,processprodatatype';
$(function(){
	_task_variable_fields_dg=$('#task-variables-fields-list').datagrid({
		//title:"Listener",
		url:'activitiController.do?getVariable&processNode='+processNode+'&processId='+processId+'&field='+files+'&variableId='+variableId,//
		singleSelect:true,
		width:700,
		height:200,
		iconCls:'icon-edit',
		//fit:true,
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
		    	if(variableFieldsEditCount>0){
		    		$.messager.alert("error","有可编辑的单元格，不能添加",'error');
					return;
				}
				$('#task-variables-fields-list').datagrid('appendRow',{
					processproid:'',
					processprokey:'',
					processprotype:'',
					processproval:'',
					processproexp:'',
					processproname:'',
					processprodatatype:'',
					action:''
				});
				var index = $('#task-variables-fields-list').datagrid('getRows').length-1;
				$('#task-variables-fields-list').datagrid('beginEdit', index);
	        }
	    }],
		
		onDblClickRow:function(rowIndex,rowData){
			editVariableField(rowIndex);
		},
		
		onBeforeEdit:function(index,row){
			row.editing = true;
			$(this).datagrid('refreshRow', index);
			variableFieldsEditCount++;
		},
		onAfterEdit:function(index,row){
			row.editing = false;
			$(this).datagrid('refreshRow', index);
			variableFieldsEditCount--;
		},
		onCancelEdit:function(index,row){
			row.editing = false;
			$(this).datagrid('refreshRow', index);
			variableFieldsEditCount--;
		}
	});
	$('#fieldSaveBt').linkbutton({
		iconCls:"icon-save"
	});
	$('#fieldCancelBt').linkbutton({
		iconCls:"icon-cancel"
	});
});

function variableFieldsActionFormatter(value,rowData,rowIndex){
	var id = rowIndex;
	var s='<img onclick="saveVariableField('+id+')" src="plug-in/designer/img/ok.png" title="'+"确定"+'" style="cursor:hand;"/>';
	var c='<img onclick="cancelVariableField('+id+')" src="plug-in/designer/img/cancel.png" title="'+"取消"+'" style="cursor:hand;"/>';
	var e='<img onclick="editVariableField('+id+')" src="plug-in/designer/img/modify.png" title="'+"修改"+'" style="cursor:hand;"/>';
	if(rowData.editing)
		return s;
	else
		return e;
}
function cancelVariableField(id){
	_task_variable_fields_dg.datagrid('cancelEdit', id);
}
function editVariableField(id){
	_task_variable_fields_dg.datagrid('beginEdit', id);
}
function saveVariableField(id){
	//alert(id);
	_task_variable_fields_dg.datagrid('endEdit', id);
	//alert(editcount);
}

function refreshAllVariableFields(){
	var rs = _task_variable_fields_dg.datagrid('getRows');
	for(var i=0;i<rs.length;i++){
		var ri =_task_variable_fields_dg.datagrid('getRowIndex',rs[i]);
		_task_variable_fields_dg.datagrid('refreshRow',ri);
	}
}
function createNewVariable(){
	
}
function getExsitingForm(){
	
}
function getVariableFieldsGridChangeRows(){
	if(variableFieldsEditCount>0){
		$.messager.alert("error","",'error');
		return null;
	}
    var insertRows = _task_variable_fields_dg.datagrid('getChanges','inserted');   
    var updateRows = _task_variable_fields_dg.datagrid('getChanges','updated');   
    var deleteRows = _task_variable_fields_dg.datagrid('getChanges','deleted');   
    var changesRows = {   
            inserted : [],   
            updated : [],
            deleted : []  
            };   
    if (insertRows.length>0) {   
        for (var i=0;i<insertRows.length;i++) {   
            changesRows.inserted.push(insertRows[i]);
        }   
    }   

    if (updateRows.length>0) {   
        for (var k=0;k<updateRows.length;k++) {   
            changesRows.updated.push(updateRows[k]);
        }   
    }   
       
    if (deleteRows.length>0) {   
        for (var j=0;j<deleteRows.length;j++) {   
            changesRows.deleted.push(deleteRows[j]);   
        }   
    }
    return changesRows;
}
function saveVariableConfig(){
	if(variableId != "" && variableId != null && variableId!="null"&&variableId!="NULL"){
		var r = updateExistingVariable();
		if(!r)return;		
	}else{
		var r = insertNewVariable();
		if(!r)return;
	}
	_variable_win.window('close');
	
}
function insertNewVariable(){
    var changesRows = getVariableFieldsGridChangeRows();
    var params="";
    if(changesRows == null)return false;
    var insertRows = changesRows['inserted'];   
    if (insertRows.length>0) {   
        for (var i=0;i<insertRows.length;i++) {
        	var id=insertRows[i].processproid;
        	var name=insertRows[i].processprokey;
        	var value=insertRows[i].processproval;
        	var type=insertRows[i].processprotype;
        	var exp=insertRows[i].processproexp;
        	var remark=insertRows[i].processproname;
        	var source=insertRows[i].processprodatatype;
        	params=params+"processId="+processId+"###tid="+tid+"###name="+name+"###type="+type+"###value="+value+"###exp="+exp+"###remark="+remark+"###source="+source+"###varibleid="+id+"@@@";
       }   
    }
    
    $.ajax({
		url : "activitiController.do?saveProcessDescriptor",
		type : 'POST',
		data : {
			processDescriptor : '',
			processName : '',
			processId : processId,
			params:params,
			nodes:'',
			processDefinitionId:processDefinitionId
		},
		dataType : 'json',
		error : function() {
			return "";
		},
		success : function(data) {
			if (data.success) {
				$.messager.alert('Info', '保存成功!', 'info');
				$('#task-variable-properties-list').datagrid('reload');
			} 
		}
	});
	return true;
}
function updateExistingVariable(){
	var params="";
	var changesRows = getVariableFieldsGridChangeRows();
    if(changesRows == null)return false;
    var insertRows = changesRows['inserted'];   
    var updateRows = changesRows['updated'];   
    if (insertRows.length>0) {   
        for (var i=0;i<insertRows.length;i++) {  
        	var id=insertRows[i].processproid;
        	var name=insertRows[i].processprokey;
        	var value=insertRows[i].processproval;
        	var type=insertRows[i].processprotype;
        	var exp=insertRows[i].processproexp;
        	var remark=insertRows[i].processproname;
        	var source=insertRows[i].processprodatatype;
        	params=params+"processId="+processId+"###tid="+tid+"###name="+name+"###type="+type+"###value="+value+"###exp="+exp+"###remark="+remark+"###source="+source+"###varibleid="+id+"@@@";
        }   
    }   

    if (updateRows.length>0) { 
        for (var k=0;k<updateRows.length;k++) { 
        	var id=updateRows[k].processproid;
        	var name=updateRows[k].processprokey;
        	var value=updateRows[k].processproval;
        	var type=updateRows[k].processprotype;
        	var exp=updateRows[k].processproexp;
        	var remark=updateRows[k].processproname;
        	var source=updateRows[k].processprodatatype;
        	params=params+"processId="+processId+"###tid="+tid+"###name="+name+"###type="+type+"###value="+value+"###exp="+exp+"###remark="+remark+"###source="+source+"###varibleid="+id+"@@@";
        }   
    }  
    $.ajax({
		url : "activitiController.do?saveProcessDescriptor",
		type : 'POST',
		data : {
			processDescriptor : '',
			processName : '',
			processId : processId,
			params:params,
			nodes:'',
			processDefinitionId:processDefinitionId
		},
		dataType : 'json',
		error : function() {
			return "";
		},
		success : function(data) {
			if (data.success) {
				$.messager.alert('Info', '保存成功!', 'info');
				$('#task-variable-properties-list').datagrid('reload');
			} 
		}
	});   
    return true;
}


function closeTaskVariableWin(){
	_variable_win.window('close');
}
//-->
</script>
<table>
		<tr>
			<td>
				<table id="task-variables-fields-list">
					<thead>
					<tr>
					<th field="processproid" hidden="true"></th>
					<th field="processprokey" width="100" align="middle" sortable="false" editor="{
						type:'validatebox',
						options:{
						required:true,
						validType:'length[1,100]'
					}}">名称</th>
					<th field="processprotype" width="100" align="middle" sortable="false" editor="{
						type:'combobox',
						options:{
							editable:false,
							data:[{id:'S',text:'字符',selected:true},{id:'I',text:'整型'},{id:'B',text:'布尔型'},{id:'F',text:'单精度浮点数'},{id:'L',text:'长整型'},{id:'D',text:'日期'},{id:'SD',text:'sql Date类型'},{id:'N',text:'双精度浮点数'}],
							valueField:'id',
							textField:'text'
					}}">类型</th>
					<th field="processproval" width="100" align="middle" sortable="false" editor="{
						type:'validatebox',
						options:{
						validType:'length[1,100]'
					}}">值</th>
					<th field="processproexp" width="100" align="middle" sortable="false" editor="{
						type:'validatebox',
						options:{
						validType:'length[1,100]'
					}}">表达式</th>
					<th field="processproname" width="100" align="middle" sortable="false" editor="{
						type:'validatebox',
						options:{
						validType:'length[1,100]'
					}}">描述</th>
					<th field="processprodatatype" width="100" align="middle" sortable="false" editor="{
						type:'combobox',
						options:{
							editable:false,
							data:[{id:'database',text:'数据库'},{id:'page',text:'页面'}],
							valueField:'id',
							textField:'text'
					}}">来源</th>
					<th field="action" width="80" align="middle" formatter="variableFieldsActionFormatter">操作</th>
					</tr>
					</thead>
				</table>
			</td>
		</tr>
		<tr>
			<td align="center">
				<a href="##" id="fieldSaveBt" onclick="saveVariableConfig()">Save</a>
				<a href="##" id="fieldCancelBt" onclick="closeTaskVariableWin()">Cancel</a>
			</td>
		</tr>
</table>