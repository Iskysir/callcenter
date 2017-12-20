<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
//流程对象
var process = workflow.process;
//属性表格定义
rows = [
         { "name": "ID", "group": "流程", "value": process.id,"field": "id", "editor": "text" },
         { "name": "名称", "group": "流程", "value": process.name, "field": "name", "editor": "text" },
         { "name": "命名空间", "group": "流程", "value": process.category, "field": "category", "editor": "text" },
         { "name": "描述", "group": "流程", "value": process.documentation, "field": "documentation", "editor": "text" }       
       ];
 //保存属性
function saveProcessProperties(){
	process.id=rows[0].value;
	process.name=rows[1].value;
	process.category=rows[2].value;
	process.documentation=rows[3].value;
}
 //构建属性表格数据
function populateProcessProperites(){
	rows[0].value=process.id;
	rows[1].value=process.name;
	rows[2].value=process.category;
	rows[3].value=process.documentation;
	propertygrid();
} 
 //加载属性表格数据
function propertygrid(){
	$('#general-properties').propertygrid('loadData', rows);
	}
$(function(){
//创建属性表格
$('#general-properties').propertygrid({
  width: 'auto',
  height: 'auto',
  showGroup: false,
  scrollbarSize: 0,
  border:0,
  columns: [[
          { field: 'name', title: '属性名', width: 30, resizable: false },
          { field: 'value', title: '属性值', width: 100, resizable: false }
  ]],
  onAfterEdit:function(){  
  	saveProcessProperties();//自动保存
   }
});
propertygrid();
});
</script>

<%--流程属性页面 --%>
<script type="text/javascript">
<!--
	//获取监听器id
	function getOldListenerIds(){
		var listeners=process.listeners;
		  var listenersIds=new Array();
		  for(var i=0;i<listeners.getSize();i++){
			var listener = listeners.get(i);
			listenersIds.push(listener.getId());
		  }
		return listenersIds.join(",");
	}
	//添加监听器
	function addListener(id,event,serviceType,value){
		var ls=process.getListener(id);
		if(!ls){
			var listener = new draw2d.Process.Listener();
			listener.id=id;
			listener.event = event;
			listener.serviceType=serviceType;
			listener.serviceClass = value;
			listener.serviceExpression = value;
			process.addListener(listener);
		}
	}
	//删除监听器
	function removeListener(id){
		process.deleteListener(id);
	}

//-->
</script>
<div id="process-properties-layout" class="easyui-layout" fit="true">
 <div id="process-properties-panel" region="center" border="true">
  <div id="task-properties-accordion" class="easyui-accordion" fit="true" border="false">
   <div id="general" style="padding: 1px;" title="流程属性面板" class="properties-menu">
    <div id="task-properties-toolbar-panel" region="north" border="false" style="padding: 3px; height: 25px; background: #E1F0F2;">
     流程类型:
     <select id="typeid" style="width: 150px; padding: 1px">
      <option value="0">
       --请选择流程类型--
      </option>
      <c:forEach items="${proTypeList}" var="type">
      <!-- update-begin--Author:chenxu  Date:20130408 for：修改流程时，流程类型不能显示 -->
       <option value="${type.id}" <c:if test="${type.id==typeId}">selected="selected"</c:if>>
       <!-- update-end--Author:chenxu  Date:20130408 for：修改流程时，流程类型不能显示 -->
        ${type.typename}
       </option>
      </c:forEach>
     </select>
    </div>
    <table id="general-properties">
    </table>
   </div>
  <div id="eventlisteners" title="执行监听器" style="overflow: hidden; padding: 1px;">
    <t:datagrid name="listenerList" actionUrl="processController.do?listenerGridYouXiao" title="添加，删除操作需点击保存" pagination="false" extendParams="queryParams:{ids:getOldListenerIds()}," idField="id">
	 <t:dgCol title="id" hidden="true" field="id"></t:dgCol>
	 <t:dgCol title="名称" field="listenername" width="30"></t:dgCol>
	 <t:dgCol title="事件" field="listenereven" width="30"></t:dgCol>
	 <t:dgCol title="类型" field="listenertype" width="30" dictionary="listenertype"></t:dgCol>
	 <t:dgCol title="执行内容" field="listenervalue" width="50"></t:dgCol>
	 <t:dgCol title="操作" field="opt" width="30"></t:dgCol>
	 <t:dgFunOpt funname="delRow(id)" title="删除"></t:dgFunOpt>
	</t:datagrid>
    <div id="eventlistenerListtb" style="padding: 3px; height: 25px">
     <div style="float: left;">
      <div class="form">
        <input name="listenerid" type="hidden" id="listenerid">
       <input name="listenername" type="hidden" id="listenername">
       <input name="listenereven" type="hidden" id="listenereven">
       <input name="listenertype" type="hidden" id="listenertype">
       <input name="listenervalue" type="hidden" id="listenervalue">
       <t:choose hiddenName="listenerid" hiddenid="id" fun="saveProcessListener" url="processController.do?chooseListener&typeid=1" textname="listenername,listenereven,listenertype,listenervalue" name="listenerList" icon="icon-add" title="执行监听器"></t:choose>
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
		var ls = process.getListener(listenerids[i]);
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
	  var row = $('#eventlistenerList').datagrid('getRows')[index];
	  $.ajax({
	   url : "processController.do?setProcessListener",
	   type : 'POST',
	   data : {
	    id :row.id
	   },
	   dataType : 'json',
	   success : function(data) {
	    if (data.success) {
	     var listener = new draw2d.Process.Listener();
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
	     
	      process.listeners.add(listener);
	    }
	    else
	    {
	      process.deleteListener(row.id);
	    }
	    reloadeventlistenerList();
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
