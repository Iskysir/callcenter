<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="scMessageList" checkbox="true" fitColumns="false" title="消息" actionUrl="scMessageController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="版本号"  field="versionNum"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="组织编号"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="删除标志"  field="delflag"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="信息类型"  field="mType"   url="scMessageController.do?detail"  queryMode="single" dictionary="es_type" width="120" ></t:dgCol>
   <t:dgCol title="内容"  field="content"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="消息标题"  field="title"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="发送人"  field="publisher"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="接收人"  field="receiver"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="发送时间"  field="pTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="remark"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="模板类型"  field="modelType"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="scMessageController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="scMessageController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="scMessageController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="scMessageController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="scMessageController.do?detail" funname="detail()"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/chrhc/project/sc/message/scMessageList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#scMessageListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#scMessageListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#scMessageListtb").find("input[name='pTime']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'scMessageController.do?upload', "scMessageList");
}

//导出
function ExportXls() {
	JeecgExcelExport("scMessageController.do?exportXls","scMessageList");
}
function detail(){
	$.ajax({
		url: 'scMessageController.do?detail',
		type : 'POST',
		dataType : 'text',
		
		async : false,
		cache : false,
		

	});
}
//模板下载
function ExportXlsByT() {
	JeecgExcelExport("scMessageController.do?exportXlsByT","scMessageList");
}
 </script>
 <body>
 
 </body>
