<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
//流程对象
var line = workflow.getLine(nodeid);
//author:zhangdaihao date:20140802 for:去掉空格
if(line.condition!=null&&line.condition!=""){
	line.condition = line.condition.replace(/(^\s*)|(\s*$)/g,"");
}

//属性表格定义
rows = [
         { "name": "ID", "group": "节点属性", "value": line.lineId,"field": "id", "editor": "text" },
         { "name": "Lable", "group": "节点属性", "value": line.lineName, "field": "name", "editor": "text" },
         { "name": "表达式", "group": "分支条件", "value": line.condition, "field": "condition", "editor": "text" }
         
       ];
 //保存属性
function saveFlowProperties(){
	line.lineId=rows[0].value;
	line.lineName=rows[1].value;
	line.condition=rows[2].value;
	line.setLabel(rows[1].value);
}
 //构建属性表格数据
function populateFlowProperites(){
	rows[0].value=line.lineId;
	rows[1].value=line.lineName;
	rows[2].value=line.condition;
	flowpropertygrid();
} 
 //加载属性表格数据
function flowpropertygrid(){
	$('#flow-properties').propertygrid('loadData', rows);
	}
$(function(){
//创建属性表格
$('#flow-properties').propertygrid({
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
  	saveFlowProperties();//自动保存
   }
});
flowpropertygrid();
});
</script>
<div id="flow-properties-layout" class="easyui-layout" fit="true">
 <div id="flow-properties-panel" region="center" border="true">
  <div id="flow-properties-accordion" class="easyui-accordion" fit="true" border="false">
   <div id="flow" style="padding: 1px;" title="流程属性面板" class="properties-menu">
    <table id="flow-properties">
    </table>
   </div>
   <div id="flowlisteners" title="执行监听器" style="overflow: hidden; padding: 1px;">
    <t:datagrid name="flowlistenerList" actionUrl="processController.do?getNodelisteners&type=1&processNode=${id}&processId=${processId}" idField="id" pagination="false">
     <t:dgCol title="id" hidden="true" field="id"></t:dgCol>
     <t:dgCol title="名称" field="TPListerer_listenername" width="40"></t:dgCol>
     <t:dgCol title="状态" field="status" replace="已启用_1,已禁用_0" width="40"></t:dgCol>
     <t:dgCol title="事件" hidden="true" field="TPListerer_listenereven" width="40"></t:dgCol>
     <t:dgCol title="类型" field="TPListerer_listenertype" width="40"></t:dgCol>
     <t:dgCol title="值" hidden="true" field="TPListerer_listenervalue" width="40"></t:dgCol>
     <t:dgCol title="操作" field="opt" width="40"></t:dgCol>
     <t:dgFunOpt exp="status#eq#0" funname="setFlowListener()" title="启用" />
     <t:dgFunOpt exp="status#eq#1" funname="setFlowListener()" title="禁用" />
     <t:dgDelOpt exp="status#eq#0" url="processController.do?delProcesListeren&id={id}" title="删除"></t:dgDelOpt>
    </t:datagrid>
    <div id="flowlistenerListtb" style="padding: 3px; height: 25px">
     <div style="float: left;">
      <div class="form">
       <input name="listenerid" type="hidden" value="${listenerid}" id="listenerid">
       <t:choose hiddenName="listenerid" hiddenid="id" fun="saveFlowListener" url="processController.do?chooseListener&typeid=1" name="listenerList" icon="icon-add" title="执行监听器"></t:choose>
      </div>
     </div>
    </div>
   </div>
  </div>
 </div>
</div>
<script type="text/javascript">
 //保存监听
 function saveFlowListener() {
  var listenerid = $('#listenerid').val();
  $.ajax({
   url : "processController.do?saveProcessListener",
   type : 'POST',
   data : {
  	type:1,
    processNode : '${id}',
    processkey : '${processId}',
    listenerid : listenerid
   },
   dataType : 'json',
   success : function(data) {
    if (data.success) {
     $('#flowlistenerList').datagrid('reload');

    }
   }
  });
  

 }
 function setFlowListener(index)
 {
  var row = $('#flowlistenerList').datagrid('getRows')[index];
  $.ajax({
   url : "processController.do?setProcessListener",
   type : 'POST',
   data : {
    id :row.id
   },
   dataType : 'json',
   success : function(data) {
    if (data.success) {
  
     var listener =  new draw2d.DecoratedConnection.Listener();
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
     
     line.listeners.add(listener);
    }
    else
    {
    	line.deleteListener(row.id);
    }
    reloadflowlistenerList();
   }
  });
  
 }
</script>
