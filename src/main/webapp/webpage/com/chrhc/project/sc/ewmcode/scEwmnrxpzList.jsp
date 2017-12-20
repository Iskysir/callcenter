<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="scEwmnrxpzList" checkbox="true" fitColumns="true" title="二维码配置" actionUrl="scEwmnrxpzController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="业务编号"  field="ywbh"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="业务名称"  field="ywmc"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="前端传输条件"  field="frontfield"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="数据来源表"  field="sourcetable"    queryMode="single" autocomplete="true" width="120"></t:dgCol>
   <t:dgCol title="是否已经使用"  field="sfyy"    queryMode="single" dictionary="yesno" width="120"></t:dgCol>
   <t:dgCol title="删除标记"  field="delflag"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="remark"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="删除标记"  field="delflag"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="remark"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="scEwmnrxpzController.do?doDel&id={id}" />
   <t:dgToolBar title="新增" icon="icon-add" url="scEwmnrxpzController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="scEwmnrxpzController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="scEwmnrxpzController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="scEwmnrxpzController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/chrhc/project/sc/ewmcode/scEwmnrxpzList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式

	 $("a[href='#']").css("margin-right","6px");
 });
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'scEwmnrxpzController.do?upload', "scEwmnrxpzList");
}

//导出
function ExportXls() {
	JeecgExcelExport("scEwmnrxpzController.do?exportXls","scEwmnrxpzList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("scEwmnrxpzController.do?exportXlsByT","scEwmnrxpzList");
}
 </script>