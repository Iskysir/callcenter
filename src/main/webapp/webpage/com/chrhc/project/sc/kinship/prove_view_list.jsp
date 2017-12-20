<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  
  <t:datagrid name="kinshipList" checkbox="true"   title="证明查询" actionUrl="kinshipController.do?proveViewDatagrid" idField="code" fit="true" queryMode="group">
   <t:dgCol title="编号"  field="code"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="姓名"  field="name"  query="false"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="身份证号"  field="idcard"  query="false" queryMode="single"  width="150" hidden="true"></t:dgCol>
   <t:dgCol title="所属社区"  field="sqname"  query="false"  queryMode="single"  width="180"></t:dgCol>
   <t:dgCol title="证明类型"  field="type"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="办理时间"  field="create_date"  queryMode="single"  width="120"></t:dgCol> 
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