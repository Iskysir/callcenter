<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  
  <t:datagrid name="kinshipList" checkbox="true"   title="巡更监控" actionUrl="kinshipController.do?xgViewDatagrid" idField="code" fit="true" queryMode="group">
   <t:dgCol title="巡检器名称"  field="xjqmc"   queryMode="group"  width="120" style="color:blue"></t:dgCol>
   <t:dgCol title="巡检器"  field="xjq"  query="false"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="记录号"  field="jlh"  query="false" queryMode="single"  width="150" hidden="true"></t:dgCol>
   <t:dgCol title="卡号"  field="kh"  query="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="巡检日期"  field="rq"   queryMode="group"  width="180"></t:dgCol>
   <t:dgCol title="巡逻点"  field="xld"  queryMode="single"  width="120" query="true"></t:dgCol> 
   <t:dgCol title="处理状态"  field="clzt"  queryMode="single"  width="90" style="color:green"></t:dgCol>
   <t:dgCol title="安装位置"  field="azwz"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="巡逻员"  field="xly"  queryMode="single"  width="120"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/chrhc/project/sc/kinship/prove_view_list.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
	 $("a[href='#']").css("margin-right","6px");

 		/*window.setInterval(showalert, 60000);
 		function showalert()
 		{
 			$("#kinshipList").datagrid('reload');  
 		} */
	 
	
		
		
 });

 </script>