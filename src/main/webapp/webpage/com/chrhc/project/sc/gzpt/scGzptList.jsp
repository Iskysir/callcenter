<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<link rel="stylesheet" href="plug-in/chrhc/currentPosition.css" type="text/css"></link>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!----> <link href="plug-in/chrhc/style.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="plug-in/accordion/css/icons.css">
 <style type="text/css"> 
.datagrid .datagrid-pager {
  margin-top: -44px;
}
 </style>
 <div class="Current_position">
    	<img src="plug-in/media/image/dingwei.png"/><span>当前所在的位置：</span>
		<a href="javascript:;">基础信息管理</a>&nbsp;
     	 <a href="javascript:;">电子资料库</a>&nbsp;
     	  <a href="javascript:;">电子资料库</a>
    </div>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="scDocWarList" checkbox="true" fitColumns="true" title="电子资料" actionUrl="scDocWarController.do?datagrid" idField="id" fit="true" queryMode="group" sortOrder="desc" sortName="createDate">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd hh:mm"  query="true" queryMode="group"  width="120"></t:dgCol><%--extend="'class':'Wdate'" --%>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="版本号"  field="versionNum"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="部门名称"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <%--<t:dgCol title="文档类型"  field="docType"   query="true" queryMode="single" dictionary="doctype" width="120" dictionary="t_s_depart,org_code,departname"></t:dgCol>--%>
   <t:dgCol title="文档名称"  field="docName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="文档地址"  field="docUrl"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="附件"  field="docFile"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="删除标记"  field="delflag"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="机构"  field="tsDepart.departname"   queryMode="single"  width="120" ></t:dgCol>
   
   <%-- 
   <t:dgCol title="姓名"  field="name"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="性别"  field="sex"    queryMode="single"  dictionary="sex" width="120"></t:dgCol>
   <t:dgCol title="出生日期"  field="birthday"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="证件号码"  field="idcardnum"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="民族"  field="nation"    queryMode="single" dictionary="mz" width="120"></t:dgCol>
   <t:dgCol title="住址"  field="address"    queryMode="single"  width="120"></t:dgCol>
   
   <t:dgCol title="身份证有效期开始"  field="yxqstar"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="身份证有效期结束"  field="yxqend"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="签发机关"  field="qfjg" hidden="true"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="备注信息"  field="bz"    queryMode="single"  width="120"></t:dgCol> operationCode="add" operationCode="excel" operationCode="update" operationCode="delete"--%>
   <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="scDocWarController.do?doDel&id={id}" /> --%>
   <t:optAuthFilter name="add">
   	<t:dgToolBar title="新增" icon="icon-add" url="scDocWarController.do?goAdd" funname="add" ></t:dgToolBar>
   </t:optAuthFilter>
     <t:optAuthFilter name="update">
   <t:dgToolBar title="编辑" icon="icon-edit" url="scDocWarController.do?goUpdate&flag=up" funname="update" ></t:dgToolBar>
     </t:optAuthFilter>
      <t:optAuthFilter name="delete">
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="scDocWarController.do?doBatchDel" funname="deleteALLSelect" ></t:dgToolBar>
     </t:optAuthFilter>
   <t:dgToolBar title="查看" icon="icon-view" url="scDocWarController.do?goUpdate&flag=vi" funname="detail"></t:dgToolBar>
    <t:optAuthFilter name="excel">
   <t:dgToolBar title="Excel导出" icon="icon-putout" funname="ExportXls" ></t:dgToolBar>
     </t:optAuthFilter>
   
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/chrhc/project/sc/docfile/scDocWarList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
		 //var datagrid = $("#scDocWarListtb");
     	 //datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html());
 		//给时间控件加上样式
 			$("#scDocWarListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:29px;width:150px;line-height:29px").click(function(){WdatePicker({maxDate:'#F{$dp.$D(\'createDate_end\')||\'9999-10-01\'}',dateFmt:'yyyy-MM-dd HH:mm'});});
 			$("#scDocWarListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:29px;width:150px;line-height:29px").click(function(){WdatePicker({minDate:'#F{$dp.$D(\'createDate_begin\')}',dateFmt:'yyyy-MM-dd HH:mm'});});
 			$("#scDocWarListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			//$("#scDocWarListtb").find("input[name='ctreateTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			//$("#scDocWarListtb").find("input[name='ctreateTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#scDocWarListtb").find("input[name='name']").attr("style","height:29px;width:120px;border:#D2D2D2 1px solid !important;line-height:29px");
 			$("#scDocWarListtb").find("input[name='idcardnum']").attr("style","height:29px;width:120px;border:#D2D2D2 1px solid !important;line-height:29px");
 			//$("#scDocWarListtb").find("select[name='docType']").attr("style","height:29px;width:120px;border:#D2D2D2 1px solid !important;line-height:29px");
 			$("a[href='#']").css("margin-right","6px");
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'scDocWarController.do?upload', "scDocWarList");
}

//导出
function ExportXls() {
	JeecgExcelExport("scDocWarController.do?exportXls","scDocWarList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("scDocWarController.do?exportXlsByT","scDocWarList");
}
//覆盖查看函数
function detail(title,url, id,width,height) {
	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
//	if (rowData.id == '') {
//		tip('请选择查看项目');
//		return;
//	}
	
	if (!rowsData || rowsData.length == 0) {
		tip('请选择查看项目');
		return;
	}
	if (rowsData.length > 1) {
		tip('请选择一条记录再查看');
		return;
	}
    url += '&load=&id='+rowsData[0].id;
    //addOneTab(title,url);
	//createdetailwindow(title,url,width,height);
    if(top.window.dialogStr.indexOf(gridname)>=0)
	{
		createwindow(title, url,width,height);
	}
	else
	{
		addOneTab(title,url);
	}
}


 </script>