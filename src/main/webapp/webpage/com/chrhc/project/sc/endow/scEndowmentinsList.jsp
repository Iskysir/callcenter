<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="scEndowmentinsList" checkbox="true" fitColumns="false" title="养老保险参保记录" actionUrl="scEndowmentinsController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="版本号"  field="versionNum"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="养老保险编号"  field="code"    queryMode="single"  width="120" formatter="linkformatter" ></t:dgCol>
   <t:dgCol title="姓名"  field="name"   query="true" queryMode="single"  width="120" ></t:dgCol>
   <t:dgCol title="身份证号"  field="idcard"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="参保日期"  field="insureddate" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="参保状况"  field="insuredstate"   query="true" queryMode="single" dictionary="protectstate" width="120"></t:dgCol>
   <t:dgCol title="性别"  field="sex"   query="true" queryMode="single" dictionary="sex" width="120"></t:dgCol>
   <t:dgCol title="民族"  field="nation"  hidden="true"  queryMode="single" dictionary="mz" width="120"></t:dgCol>
   <t:dgCol title="出生日期"  field="birthday"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="联系电话"  field="phone"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="邮编"  field="postcode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="户籍地址"  field="paddress"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="居住地址"  field="address"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="户籍性质"  field="holdnation"  hidden="true" query="true" queryMode="single" dictionary="householdnation" width="120"></t:dgCol>
   <t:dgCol title="个人缴费额"  field="insamount"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="企业职工基本养老保险"  field="enterpriseins"  hidden="true"  queryMode="single" dictionary="yesno" width="120"></t:dgCol>
   <t:dgCol title="企业养老保险起始时间"  field="enterprisedate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="被征地农民社会保障"  field="landlessins"  hidden="true"  queryMode="single" dictionary="yesno" width="120"></t:dgCol>
   <t:dgCol title="被征地保障起始时间"  field="landlessinsdate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="老农保"  field="oldins"  hidden="true"  queryMode="single" dictionary="yesno" width="120"></t:dgCol>
   <t:dgCol title="老农保起始时间"  field="oldinsdate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="其他保险"  field="otherins"  hidden="true"  queryMode="single" dictionary="yesno" width="120"></t:dgCol>
   <t:dgCol title="其他起始时间"  field="otherinsdate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="地理位置"  field="gisxy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="特殊参保群体"  field="specialgroup"  hidden="true"  queryMode="single" dictionary="specialgroup" width="120"></t:dgCol>
   <t:dgCol title="人口信息ID外键"  field="rkId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="部门名称"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="删除标记"  field="delflag"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="scEndowmentinsController.do?doDel&id={id}" />
   <t:dgToolBar title="新增" icon="icon-add" url="scEndowmentinsController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="scEndowmentinsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="scEndowmentinsController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   	<t:dgToolBar title="注销" icon="pictures"  funname="cancel" ></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/chrhc/project/sc/endow/scEndowmentinsList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#scEndowmentinsListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#scEndowmentinsListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#scEndowmentinsListtb").find("input[name='insureddate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#scEndowmentinsListtb").find("input[name='insureddate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#scEndowmentinsListtb").find("input[name='enterprisedate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#scEndowmentinsListtb").find("input[name='landlessinsdate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#scEndowmentinsListtb").find("input[name='oldinsdate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#scEndowmentinsListtb").find("input[name='otherinsdate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });

//导入
function ImportXls() {
	openuploadwin('Excel导入', 'scEndowmentinsController.do?upload', "scEndowmentinsList");
}

//导出
function ExportXls() {
	JeecgExcelExport("scEndowmentinsController.do?exportXls","scEndowmentinsList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("scEndowmentinsController.do?exportXlsByT","scEndowmentinsList");
}

 </script>