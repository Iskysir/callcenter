<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>流程设计器</title>
  <t:base type="designer,tools"></t:base>
  <script type="text/javascript">
	$(function() {
		try {
			_task_obj = $('#task');
			_task_context_menu = $('#task-context-menu').menu({});
			$('.easyui-linkbutton').draggable({
				proxy : function(source) {
					var n = $('<div class="draggable-model-proxy"></div>');
					n.html($(source).html()).appendTo('body');
					return n;
				},
				deltaX : 0,
				deltaY : 0,
				revert : true,
				cursor : 'auto',
				onStartDrag : function() {
					$(this).draggable('options').cursor = 'not-allowed';
				},
				onStopDrag : function() {
					$(this).draggable('options').cursor = 'auto';
				}
			});
			$('#paintarea').droppable({
				accept : '.easyui-linkbutton',
				onDragEnter : function(e, source) {
					$(source).draggable('options').cursor = 'auto';
				},
				onDragLeave : function(e, source) {
					$(source).draggable('options').cursor = 'not-allowed';
				},
				onDrop : function(e, source) {
					$(this).removeClass('over');
					var wfModel = $(source).attr('wfModel');
					var shape = $(source).attr('shape');
					if (wfModel) {
						var x = $(source).draggable('proxy').offset().left;
						var y = $(source).draggable('proxy').offset().top;
						var xOffset = workflow.getAbsoluteX();
						var yOffset = workflow.getAbsoluteY();
						var scrollLeft = workflow.getScrollLeft();
						var scrollTop = workflow.getScrollTop();
						addModel(wfModel, x - xOffset + scrollLeft, y - yOffset + scrollTop, shape);
					}
				}
			});
			//$('#paintarea').bind('contextmenu',function(e){
			//alert(e.target.tagName);
			//});

		} catch (e) {

		}
	});
//-->
</script>
 </head>
 <body id="designer" class="easyui-layout">
  <div region="west" split="true" iconCls="palette-icon" title="流程元素" style="width: 110px;">
   <div class="easyui-accordion" fit="true" border="false">
    <div id="event" title="事件" iconCls="palette-menu-icon" class="palette-menu">
     <a href="#" class="easyui-linkbutton" plain="true" iconCls="start-event-icon" wfModel="Start">开始</a>
     <br>
     <a href="#" class="easyui-linkbutton" plain="true" iconCls="end-event-icon" wfModel="End">结束</a>
     <br>
    </div>
    <div id="task" title="任务" iconCls="palette-menu-icon" selected="true" class="palette-menu">
     <a href="#" class="easyui-linkbutton" plain="true" iconCls="user-task-icon" wfModel="UserTask">用户任务</a>
     <br>
     <a href="#" class="easyui-linkbutton" plain="true" iconCls="manual-task-icon" wfModel="ManualTask">手动任务</a>
     <br>
     <a href="#" class="easyui-linkbutton" plain="true" iconCls="service-task-icon" wfModel="ServiceTask">服务任务</a>
     <br>
     <a href="#" class="easyui-linkbutton" plain="true" iconCls="script-task-icon" wfModel="ScriptTask">脚本任务</a>
     <br>
     <a href="#" class="easyui-linkbutton" plain="true" iconCls="mail-task-icon" wfModel="MailTask">邮件任务</a>
     <br>
     <a href="#" class="easyui-linkbutton" plain="true" iconCls="receive-task-icon" wfModel="ReceiveTask">接收任务</a>
     <br>
     <a href="#" class="easyui-linkbutton" plain="true" iconCls="business-rule-task-icon" wfModel="BusinessRuleTask">业务规则</a>
     <br>
     <%--
     author:zhangdaihao  date:20140823  for：用不上暂时屏蔽掉，下面原来叫做调用活动，更名
      <a href="#" class="easyui-linkbutton" plain="true" iconCls="subprocess-icon">子流程</a>
      <a href="#" class="easyui-linkbutton" plain="true" iconCls="callactivity-icon" wfModel="CallActivity">包容</a>
     <br>
      --%>
    
     <a href="#" class="easyui-linkbutton" plain="true" iconCls="subprocess-icon" wfModel="CallActivity">子流程</a>
     <br>
    </div>
    <div id="gateway" title="网关" iconCls="palette-menu-icon" class="palette-menu">
     <a href="#" class="easyui-linkbutton" plain="true" iconCls="parallel-gateway-icon" wfModel="ParallelGateway">同步</a>
     <br>
     <a href="#" class="easyui-linkbutton" plain="true" iconCls="exclusive-gateway-icon" wfModel="ExclusiveGateway">分支</a>
     <br>
    </div>
    <div id="boundary-event" title="边界事件" iconCls="palette-menu-icon" class="palette-menu">
     <a href="#" class="easyui-linkbutton" plain="true" iconCls="timer-boundary-event-icon" wfModel="TimerBoundary">时间边界</a>
     <br>
     <a href="#" class="easyui-linkbutton" plain="true" iconCls="error-boundary-event-icon" wfModel="ErrorBoundary">错误边界</a>
     <br>
    </div>
   </div>
  </div>
  <div id="process-panel" region="center" style="padding: 1px" split="true" iconCls="process-icon" title="流程">
   <div id="process-definition-tab">
    <div id="designer-area" title="设计" style="POSITION: absolute; width: 100%; height: 100%; padding: 0; border: none; overflow: auto;">
     <div id="paintarea" style="POSITION: absolute; WIDTH: 2000px; HEIGHT: 2000px"></div>
    </div>
    <div id="xml-area" title="源码" style="width: 100%; height: 100%; overflow: hidden; overflow-x: hidden; overflow-y: hidden;">
     <textarea id="descriptorarea" rows="38" style="width: 100%; height: 100%; padding: 0; border: none; font-size: 12px" readonly="readonly"></textarea>
    </div>
   </div>
  </div>
  <!-- toolbar -->
  <!-- update-begin--Author:chenxu  Date:20130408 for：修改流程时，流程类型不能显示 -->
  <div id="toolbar-panel" region="north" border="false" style="height: 55px; background: #d8e4fe;" title="工具栏">
   <input type="hidden" name="processId" id="processId" value="${processid}">
   <img width="20" height="18" title="创建流程" src="plug-in/designer/img/save.png" onclick="saveProcessDef()" class="buttonStyle" />
   <img width="20" height="18" title="上一步" src="plug-in/designer/img/back.png" onclick="undo()" class="buttonStyle" />
   <img width="20" height="18" title="下一步" src="plug-in/designer/img/next.png" onclick="redo()" class="buttonStyle" />
   <img width="20" height="18" title="导出" src="plug-in/designer/img/printer.png" onclick="exportProcessDef(this)" class="buttonStyle" />
  </div>
  <div region="east" id="properties-panel" href="processController.do?processProperties&turn=processProperties&processId=${processid }" split="true" iconCls="properties-icon" title="流程属性"  style="padding:1px;width: 280px;">
  </div>
  <!-- update-end--Author:chenxu  Date:20130408 for：修改流程时，流程类型不能显示 -->
  <!-- task context menu -->
  <div id="task-context-menu" class="easyui-menu" style="width: 120px;">
   <div id="properties-task-context-menu" iconCls="properties-icon">
    属性
   </div>
   <div id="delete-task-context-menu" iconCls="icon-remove">
    删除
   </div>
  </div>
  <!-- form configuration window -->
  <div id="form-win" title="表单配置" style="width: 720px; height: 300px;">
  </div>
  <!-- form configuration window -->
  <div id="variable-win" title="变量配置" style="width: 720px; height: 300px;">
  </div>
  <!-- listener configuration window -->
  <div id="listener-win" title="监听配置" style="width: 720px; height: 300px;">
  </div>
  <!-- candidate configuration window -->
  <div id="task-candidate-win" title="任务配置" style="width: 720px; height: 300px;">
  </div>
 <script type="text/javascript">
	$('#process-definition-tab').tabs({
		fit : true,
		onSelect : function(title) {
			if (title == '设计') {
			} else if (title == '源码') {
				$('#descriptorarea').val(workflow.toXML());

			}
		}
	});
</script>
 <script type="text/javascript">
createCanvas('${processid}',false);
</script>
 </body>
</html>
