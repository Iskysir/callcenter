<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="hlSatisfiedDegreeList" checkbox="true" fitColumns="false" title="回访满意度" actionUrl="hlSatisfiedDegreeController.do?datagrid" idField="id" fit="true" queryMode="group">
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
   <t:dgCol title="满意度"  field="degeree"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="回访状态"  field="stState"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="有效工作量"  field="yxgzl"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="无效工作量"  field="wxgzl"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="失效工作量"  field="sxgzl"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="回访人"  field="visitPerson"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="回访时间"  field="visitTime" formatter="yyyy-MM-dd"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="客户录入"  field="visitInput"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="录入时间"  field="inputTime" formatter="yyyy-MM-dd"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="业务类型"  field="busType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="业务主键"  field="busId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="预留字段a"  field="bza"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="预留字段b"  field="bzb"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="回访意见"  field="visitOption"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="hlSatisfiedDegreeController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="hlSatisfiedDegreeController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="hlSatisfiedDegreeController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="hlSatisfiedDegreeController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="hlSatisfiedDegreeController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/chrhc/project/hl/visit/hlSatisfiedDegreeList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#hlSatisfiedDegreeListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#hlSatisfiedDegreeListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#hlSatisfiedDegreeListtb").find("input[name='visitTime']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#hlSatisfiedDegreeListtb").find("input[name='inputTime']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'hlSatisfiedDegreeController.do?upload', "hlSatisfiedDegreeList");
}

//导出
function ExportXls() {
	JeecgExcelExport("hlSatisfiedDegreeController.do?exportXls","hlSatisfiedDegreeList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("hlSatisfiedDegreeController.do?exportXlsByT","hlSatisfiedDegreeList");
}
 </script>