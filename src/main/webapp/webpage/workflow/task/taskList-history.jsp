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
<div class="tab-content tab-box datagrid">
  	<div style="height: 40px;line-height:40px;background-color:#fff;">
    	<img src="plug-in/media/image/dingwei.png" style="float:left; margin-top:5px;"/>
    	<span style="float:left; font-size:14px; color:#666666;">当前所在的位置：</span>
    	<a href="javascript:void(0)" style="float:left; color:#333333; font-size:14px; padding:0px 3px 0px 6px;">个人办公</a>
    	<img src="plug-in/media/image/arrow.png" style="margin-top:17px;"/>&nbsp;
    	<a href="javascript:void(0)" style="color:#666666;">历史任务</a>
    </div>
</div>
<t:datagrid fitColumns="false" name="historyTaskList" queryMode="group" title="历史任务列表" actionUrl="taskController.do?taskHistoryList" idField="id">
 <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
 <t:dgCol title="流程编号" field="procDefId" width="80" hidden="true"></t:dgCol>
 <t:dgCol title="流程名称" field="prodef_name" width="300" query="true"></t:dgCol>
 <t:dgCol title="流程实例" field="proInsl_procInstId" width="100" hidden="true"></t:dgCol>
 <t:dgCol title="任务名称" field="name" width="250"></t:dgCol>
 <t:dgCol title="任务发起人" field="proInsl_startUserId" width="180" hidden="true"></t:dgCol>
 <t:dgCol title="任务处理人" field="assignee" width="150"></t:dgCol>
 <t:dgCol title="开始时间" field="startTime" width="230" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
 <t:dgCol title="结束时间" field="endTime" width="200" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
 <t:dgCol title="持续时间" field="durationStr"  width="200" hidden="true"></t:dgCol>
 <t:dgCol title="操作" field="opt" width="255"></t:dgCol>
 <%-- <t:dgFunOpt funname="viewHistory(proInsl_procInstId)" title="<font style=color:red>查看</font>"></t:dgFunOpt>--%>
 <t:dgFunOpt funname="goProcessHisTab(proInsl_procInstId,prodef_name)" title="历史"></t:dgFunOpt>
 </t:datagrid>
<SCRIPT type="text/javascript">
//查看流程历史
function viewHistory(processInstanceId){
	var url = "";
	var title = "流程历史";
	url = "activitiController.do?viewProcessInstanceHistory&processInstanceId="+processInstanceId;
	addOneTab(title, url);
}
</SCRIPT>