<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<link href="plug-in/scmedia/css/style.css" rel="stylesheet"  type="text/css">
<style type="text/css">
	.datagrid .datagrid-pager {
		bottom:40px !important;
		width:100%;
	}
</style>
<!-- <div class="tab-content tab-box datagrid">
  	<div style="height: 40px;line-height:20px;background-color:#fff;">
    	<img src="plug-in/media/image/dingwei.png" style="float:left; margin-top:5px; clear:both;"/>
    	<span style="font-size:14px; color:#666666; float:left; margin-top:10px;">当前所在的位置：</span>
    	<a href="javascript:void(0)" style="color:#333333; font-size:14px; padding:0px 3px 0px 6px; float:left; margin-top:10px;">个人办公</a>
    	<img src="plug-in/media/image/arrow.png" style="float:left; margin-top:17px;"/>&nbsp;&nbsp;
    	<a href="javascript:void(0)" style="color:#666666; font-size:14px;margin-top:10px;">我的任务</a>
    </div>
</div> -->
<div class="tab-content tab-box datagrid">
  	<div style="height: 40px;line-height:40px;background-color:#fff;">
    	<img src="plug-in/media/image/dingwei.png" style="float:left; margin-top:5px;"/>
    	<span style="float:left; font-size:14px; color:#666666;">当前所在的位置：</span>
    	<a href="javascript:void(0)" style="float:left; color:#333333; font-size:14px; padding:0px 3px 0px 6px;">个人办公</a>
    	<img src="plug-in/media/image/arrow.png" style="margin-top:17px;"/>&nbsp;
    	<a href="javascript:void(0)" style="color:#666666;">我的任务</a>
    </div>
</div>
<t:datagrid fitColumns="false" name="myTaskList" queryMode="group" title="我的任务列表" actionUrl="taskController.do?taskAllList" idField="id">
 <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
 <t:dgCol title="流程编号" field="Process_processDefinition_id" width="80" hidden="true"></t:dgCol>
 <t:dgCol title="流程名称" field="Process_processDefinition_name" width="200" query="true"></t:dgCol>
 <t:dgCol title="业务编码" field="businessCode" width="180" query="true"></t:dgCol>
 <t:dgCol title="流程实例" field="Process_task_processInstanceId" width="100" hidden="true"></t:dgCol>
 <t:dgCol title="任务发起人" field="TSUser_userName" width="130" hidden="true"></t:dgCol>
 <t:dgCol title="任务办理人" field="Process_task_assignee" width="130"></t:dgCol>
 <t:dgCol title="开始时间" field="Process_task_createTime" width="175" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
 <t:dgCol title="结束时间" field="Process_task_dueTime" width="150" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
 <t:dgCol title="状态" field="TSPrjstatus_description" width="100"></t:dgCol>
 <t:dgCol title="当前环节" field="Process_task_name" width="200"></t:dgCol>
 <t:dgCol hidden="true" title="TASK ID(该字段隐藏)" field="Process_task_id"></t:dgCol>
 <t:dgCol hidden="true" title="key" field="Process_task_taskDefinitionKey"></t:dgCol>
 <t:dgCol title="操作" field="opt" width="250"></t:dgCol>
 <t:dgConfOpt exp="Process_task_assignee#empty#true" url="activitiController.do?claim&taskId={Process_task_id}" message="确定签收?" title="签收"></t:dgConfOpt>
  <%--<t:dgFunOpt exp="Process_task_assignee#empty#false" funname="openhandleMixTab(Process_task_id,Process_task_name)" title="办理"></t:dgFunOpt> --%>
 <t:dgFunOpt exp="Process_task_assignee#empty#false" funname="openhandleMix(Process_task_id,Process_processDefinition_name)" title="办理"></t:dgFunOpt>
 <t:dgFunOpt exp="Process_task_assignee#empty#false" funname="selectEntruster(Process_task_id,Process_processDefinition_name)" title="委托"></t:dgFunOpt>
</t:datagrid>