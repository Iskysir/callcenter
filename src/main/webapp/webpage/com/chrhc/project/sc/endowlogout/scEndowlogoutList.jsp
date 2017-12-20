<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="scEndowlogoutList" checkbox="true" fitColumns="false" title="养老保险注销记录" actionUrl="scEndowlogoutController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="版本号"  field="versionNum"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="组织编号"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="删除标志"  field="delflag"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="参保编号"  field="code"    queryMode="single"  width="120" formatter="linkformatter"></t:dgCol>
   <t:dgCol title="参保人姓名"  field="name"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="身份证号"  field="pidcard"  hidden="true" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="注销原因"  field="cancellreason"  hidden="true" query="true" queryMode="single" dictionary="cancellreason" width="120"></t:dgCol>
   <t:dgCol title="注销日期"  field="cancelldate" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="受益人姓名"  field="beniname"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="受益人性别"  field="benisex"  hidden="true"  queryMode="single" dictionary="sex" width="120"></t:dgCol>
   <t:dgCol title="受益人出生日期"  field="benibirthday" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="受益人身份证号"  field="beniidcard"  hidden="true" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="与参保人关系"  field="relation"    queryMode="single" dictionary="qsgx" width="120"></t:dgCol>
   <t:dgCol title="户籍所在地"  field="paddress"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="联系人电话"  field="telphone"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="居住地址"  field="address"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="领取个人账户余额的指定银行"  field="nominatedbank"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="银行账号"  field="bankcode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="人口信息ID外键"  field="rkId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="养老保险id"  field="endowid"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="scEndowlogoutController.do?doDel&id={id}" />
   <t:dgToolBar title="新增" icon="icon-add" url="scEndowlogoutController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="scEndowlogoutController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="scEndowlogoutController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/chrhc/project/sc/endowlogout/scEndowlogoutList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#scEndowlogoutListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#scEndowlogoutListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#scEndowlogoutListtb").find("input[name='cancelldate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#scEndowlogoutListtb").find("input[name='cancelldate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#scEndowlogoutListtb").find("input[name='benibirthday']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'scEndowlogoutController.do?upload', "scEndowlogoutList");
}

//导出
function ExportXls() {
	JeecgExcelExport("scEndowlogoutController.do?exportXls","scEndowlogoutList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("scEndowlogoutController.do?exportXlsByT","scEndowlogoutList");
}

$('#scEndowlogoutList').datagrid(
{	onDblClickRow:function(rowIndex,rowData)
			{view(rowData.id);}
});


//查看
function view(id){
	$(".datagrid").hide();
	var url = 'scEndowmentinsController.do?goUpdate';
	if(!id){
		id = getSelected("id");
	}
	url += '&id='+ id;
	$("#editFormDiv").show();
	$("#editForm").attr("src",url); 
}
 </script>