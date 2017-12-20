<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
<!--
var task = workflow.getFigure(nodeid);//当前节点对象
//属性表格定义
rows = [
    
            { "name": "ID", "group": "任务属性", "value": task.taskId,"field": "taskId", "editor": "text" },
            { "name": "名称", "group": "任务属性", "value": task.taskName, "field": "taskName", "editor": "text" },
            { "name": "描述", "group": "任务属性", "value": task.documentation, "field": "documentation", "editor": "text" },
            { "name": "表单Key", "group": "表单属性", "value": task.formKey, "field": "formKey", "editor": "text" }
           
          
        ];
$(function(){
	$('#task_extend').val(task.task_extend);
	$('#isSequential').val(task.isSequential);
	$('#loopCardinality').val(task.loopCardinality);
	$('#collection').val(task.collection);
	$('#elementVariable').val(task.elementVariable);
	$('#completionCondition').val(task.completionCondition);
	$('#performerType').combobox({
			editable:false,
			onChange:function(newValue, oldValue){
				$('#expression').val('');
				switchTaskCandidatesList(newValue);
			}
		});

	
	task_candidate_panel=$('#task-candidate-panel').panel({
		border:false
		//minimized:true,
	});
	var ptype='';
	if($('#performerType').combobox('getValue')!=''){
		ptype=$('#performerType').combobox('getValue');
	}
	$('#performerType').combobox('setValue',ptype);
	switchTaskCandidatesList(ptype);
});
function switchTaskCandidatesList(performerType){
	if(performerType == 'candidateUsers'){
		task_candidate_panel.panel("refresh","processController.do?processProperties&turn=candidateUsersConfig&checkbox=true");
	}else if(performerType == 'candidateGroups'){
		task_candidate_panel.panel("refresh","processController.do?processProperties&turn=candidateGroupsConfig&checkbox=true");
	}else if(performerType == 'assignee'){
		task_candidate_panel.panel("refresh","processController.do?processProperties&turn=candidateUsersConfig&checkbox=false");
	}
}

//保存
function saveTaskProperties(){
	task.taskId=$.trim(rows[0].value);
	task.taskName=rows[1].value;
	task.formKey=rows[3].value;
	task.documentation=rows[2].value;
	task.setId($.trim(rows[0].value));
	task.setContent($.trim(rows[1].value));
	task.performerType=$('#performerType').combobox('getValue');
	task.expression=$.trim($('#expression').val());
	task.isUseExpression=true;
	task.task_extend=$.trim($('#task_extend').val());
	task.loopCardinality=$.trim($('#loopCardinality').val());
	task.isSequential=$.trim($('#isSequential').val());
	task.loopCardinality=$.trim($('#loopCardinality').val());
	task.collection=$.trim($('#collection').val());
	task.elementVariable=$.trim($('#elementVariable').val());
	task.completionCondition=$.trim($('#completionCondition').val());
}
//加载变量
function populateTaskProperites(){
	$('#performerType').combobox('setValue',task.performerType);
	$('#expression').val(task.expression);
	rows[0].value=task.taskId;
	rows[1].value=task.taskName;
	rows[2].value=task.documentation;
	rows[3].value=task.formKey;
	
}

//加载属性表格数据
function propertygrid(){
	$('#task-propertygrid').propertygrid('loadData', rows);
	populateTaskProperites();
	}
//创建属性表格
$('#task-propertygrid').propertygrid({
  width: 'auto',
  height: 'auto',
  showGroup: true,
  scrollbarSize: 0,
  border:0,
  columns: [[
          { field: 'name', title: '属性名', width: 30, resizable: false },
          { field: 'value', title: '属性值', width: 100, resizable: false }
  ]],
  onAfterEdit:function(){  
  	saveTaskProperties();//自动保存
   }
});
propertygrid();

//-->
</script>


<script type="text/javascript">
<!--
	//获取执行监听器id
	function getOldListenerIds(){
		var listeners=task.listeners;
		  var listenersIds=new Array();
		  for(var i=0;i<listeners.getSize();i++){
			var listener = listeners.get(i);
			listenersIds.push(listener.getId());
		  }
		return listenersIds.join(",");
	}
	//添加执行监听器
	function addListener(id,event,serviceType,value){
		var ls=task.getListener(id);
		if(!ls){
			var listener = new draw2d.Task.Listener();
			listener.id = id;
			listener.event = event;
			listener.serviceType = serviceType;
			listener.serviceClass = value;
			listener.serviceExpression = value;
			task.addListener(listener);
		}
	}
	//删除执行监听器
	function removeListener(id){
		task.deleteListener(id);
	}	

//-->
</script>
<div id="task-properties-layout" class="easyui-layout" fit="true">
 <div id="task-properties-toolbar-panel" region="north" border="false" style="height:30px; background: #E1F0F2;">
  <a href="##" id="sb2" class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="saveTaskProperties()">保存</a>
 </div>
 <div id="task-properties-panel" region="center" border="true">
  <div id="task-properties-accordion" class="easyui-accordion" fit="true" border="false">
   <div id="task" title="节点属性" selected="true" class="properties-menu">
    <table id="task-propertygrid">
    </table>
   </div>
   <div id="main-config" title="人员配置" class="properties-menu">
    <div class="datagrid-toolbar" style="height:auto">
     <table id="main-properties">
      <tr>
       <td align="right">
       	 类型:
       </td>
       <td>
        <select id="performerType" name="performerType" style="width:100px;">
         <option value="assignee">处理人</option>
         <option value="candidateUsers">备选人员
         </option>
         <option value="candidateGroups">备选角色</option>
        </select>
        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="xuanze();">选择</a>
       </td>
      </tr>
      <tr>
       <td align="right">
   		     表达式:
       </td>
       <td>
        <input type="text" id="expression" name="expression" style="width:190px" />
       </td>
      </tr>
     </table>
    </div>
    <div id="task-candidate-panel" class="easyui-panel" style="overflow: hidden; width: 280px; height: 450px; padding:1px;">
    </div>
   </div>
 
   <div id="taskHuiQianProperties" title="会签属性" selected="true">
    <table id="main-properties">
      <tr>
	       <td align="right">
	       	 状态:
	       </td>
	       <td>
		        <select id="isSequential" name="isSequential" style="width:160px;">
		         <option value="">不启动多实例</option>
		         <option value="true">并行 </option>
		         <option value="false">顺序</option>
		        </select>
	       </td>
      </tr>
	  <tr>
	       <td align="right">
	   		     循环数量:
	       </td>
	       <td>
	        <input type="text" id="loopCardinality" name="loopCardinality" style="width:160px" />
	       </td>
      </tr>
      <tr>
	       <td align="right">
	   		     循环集合:
	       </td>
	       <td>
	        <input type="text" id="collection" name="collection" style="width:160px" />
	       </td>
      </tr>
      <tr>
	       <td align="right">
	   		     元素名称:
	       </td>
	       <td>
	        <input type="text" id="elementVariable" name="elementVariable" style="width:160px" />
	       </td>
      </tr>
       <tr>
	       <td align="right">
	   		     结束条件:
	       </td>
	       <td>
	        <input type="text" id="completionCondition" name="completionCondition" style="width:160px" />
	       </td>
      </tr>
     </table>
    <fieldset style="line-height: 21px;">
		<legend>说明</legend>
		<div>1.\${flowUtil.stringToList(assigneeUserIdList)}，将字符串转换成集合，暴露的SpringBean方法</div>
		<div>2.多实例任务Activiti默认会创建3个流程变量，nrOfInstances:实例总数，nrOfActiveInstances:当前活跃的，也就是当前还未完成的，对于顺序的多实例，此值总是1,nrOfCompletedInstances:已完成的实例个数。</div>
		<div>3.状态:不启动多实例,则只会创建一个任务，默认不启动，不启动多实例，一下配置都无效，true:顺序执行，fasle:并行,同时执行。</div>
		<div>4.循环数量:指定创建多任务的数量。可使用表达式从流程变量获取。</div>
		<div>5.循环集合:流程变量中一个集合类型变量的变量名称。根据集合元素多少创建任务数量。可使用表达式。例:流程变量：assigneeUserIdList=[user1,user2]，可用assigneeUserIdList。</div>
		<div>6.集合元素:集合中每个元素的变量名称，可在每个任务中获取,可使用表达式。例：集合为当定义集合元素名称为:assigneeUserId,可在任务直接指派人员用表达式\${assigneeUserId}获取，用于动态会签。</div>
		<div>7.结束条件:多实例活动结束的条件，默认为完成多全部实例，当表达式返回true时结束多实例活动。例：\${nrOfCompletedInstances/nrOfInstances>=0.6} 说明当有60%的任务完成时，会完成此多实例，删除其他未完成的，继续下面的流程。</div>
	</fieldset>
   </div>
   
   <!--  author：zhangdaihao date:20140801 for:因为报错暂时注释掉
   <div id="taskExtendProperties" title="扩展属性" selected="true" style="overflow: hidden;padding:1px;">
    <textarea style="margin: 2px; width: 257px; height: 189px;" name="task_extend" id="task_extend"></textarea>
   </div>
  
   <div id="variableProperties" title="流程变量" style="overflow: hidden;padding:1px;">
    <t:datagrid name="variableList" actionUrl="processController.do?getVariables&processNode=${id}&processId=${processId}" idField="processproid" pagination="false">
     <t:dgCol title="Id" hidden="true" field="processproid"></t:dgCol>
     <t:dgCol title="ID" field="processprokey" width="40"></t:dgCol>
     <t:dgCol title="名称" field="processproname" width="40"></t:dgCol>
     <t:dgCol title="值" field="processproval" width="40"></t:dgCol>
     <t:dgCol title="操作" field="opt"></t:dgCol>
     <t:dgDelOpt url="processController.do?deleteVariable&variableId={processproid}" title="删除"></t:dgDelOpt>
    </t:datagrid>
    <div id="variableListtb" style="padding:3px; height: 25px">
     <div style="float: left;">
      <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="add('添加','processController.do?addOrupdateVariable&processNode=${id }&processId=${processId}&processDefinitionId=${processDefinitionId}')">添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="update('编辑','processController.do?addOrupdateVariable&processNode=${id }&processId=${processId}&processDefinitionId=${processDefinitionId}','processproid')">编辑</a>
     </div>
    </div>
   </div>
   -->
   
   <div id="listeners" title="任务监听器" style="overflow: hidden;padding:1px;">
    <t:datagrid title="添加删除操作,需点击保存" name="listenerList" actionUrl="processController.do?listenerGridYouXiao" pagination="false" extendParams="queryParams:{ids:getOldListenerIds()}," idField="id">
	 <t:dgCol title="id" hidden="true" field="id"></t:dgCol>
	 <t:dgCol title="名称" field="listenername" width="30"></t:dgCol>
	 <t:dgCol title="事件" field="listenereven" width="30"></t:dgCol>
	 <t:dgCol title="类型" field="listenertype" width="30" dictionary="listenertype"></t:dgCol>
	 <t:dgCol title="执行内容" field="listenervalue" width="50"></t:dgCol>
	 <t:dgCol title="操作" field="opt" width="30"></t:dgCol>
	 <t:dgFunOpt funname="delRow(id)" title="删除"></t:dgFunOpt>
	</t:datagrid>
    
    <div id="listenerListtb" style="padding:3px; height: 25px">
     <div style="float: left;">
      <div class="form">
       <input name="listenerid" type="hidden" id="listenerid">
       <input name="listenername" type="hidden" id="listenername">
       <input name="listenereven" type="hidden" id="listenereven">
       <input name="listenertype" type="hidden" id="listenertype">
       <input name="listenervalue" type="hidden" id="listenervalue">
       <t:choose hiddenName="listenerid" hiddenid="id" fun="saveProcessListener" url="processController.do?chooseListener&typeid=2" textname="listenername,listenereven,listenertype,listenervalue" name="listenerList" icon="icon-add" title="监听列表"></t:choose>
      </div>
     </div>
    </div>
   </div>
  </div>
 </div>
</div>
<script type="text/javascript">
	//保存监听
	function saveProcessListener() {
		var listenerid = $('#listenerid').val();
		var listenereven = $('#listenereven').val();
		var listenertype = $('#listenertype').val();
		var listenervalue = $('#listenervalue').val();
		var listenername = $('#listenername').val();
		var listenerids = listenerid.split(",");
		var listenerevens = listenereven.split(",");
		var listenertypes = listenertype.split(",");
		var listenervalues = listenervalue.split(",");
		var listenernames = listenername.split(",");
		for(var i=0;i<listenerids.length;i++){ 
			var ls=task.getListener(listenerids[i]);
			addListener(listenerids[i],listenerevens[i],listenertypes[i],listenervalues[i]);
			if(!ls){
				$('#listenerList').datagrid('appendRow',{
					id:listenerids[i],
					listenername:listenernames[i],
					listenereven:listenerevens[i],
					listenertype:listenertypes[i],
					listenervalue:listenervalues[i]
				});
			}
		}
	}
	
	function setProcessListener(index)
	{
		var row = $('#listenerList').datagrid('getRows')[index];
		$.ajax({
			url : "processController.do?setProcessListener",
			type : 'POST',
			data : {
				id :row.id
			},
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					var listener = new draw2d.Task.Listener();
					listener.event=row.TPListerer_listenereven;
					listener.id=row.id;
					listener.serviceType = row.TPListerer_listenertype;
					if(row.TPListerer_listenertype=="javaClass")
					{
						listener.serviceClass= row.TPListerer_listenervalue;
					}
					else
					{
					 	listener.serviceExpression=row.TPListerer_listenervalue;
					}
					task.listeners.add(listener);
				}
				else
				{
					task.deleteListener(row.id);
				}
				reloadlistenerList();
			}
		});
		
	}
	//删除流程监听
	function delRow(id){
  	  var rows=$('#listenerList').datagrid('getSelections');
  	  for(var i=0;i<rows.length;i++){
  		  var row=rows[i];
  		  var index=$('#listenerList').datagrid('getRowIndex',row);
      	  $('#listenerList').datagrid('deleteRow',index);
      	  removeListener(row.id);
  	  }
    }
</script>
