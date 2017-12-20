<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
	.datagrid .datagrid-pager {
		bottom:36px;
	}
</style>
 

    <div class="Current_position">
	    <img src="plug-in/media/image/dingwei.png"/><span style="font-size:14px; color:#666666;">当前所在的位置：</span>
		<script type="text/javascript">
				if(parent.url_title_map[ location.href + "#"]){
					document.write(parent.url_title_map[ location.href + "#"]);
				}
		</script>
    </div>
  <t:datagrid name="scZzyzList" checkbox="true" fitColumns="true" title="证照验真" actionUrl="scZzyzController.do?datagrid" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="证照编号"  field="zzbh"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="验证结果"  field="yzjg"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="验证时间"  field="yzsj" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="删除标记"  field="scbj"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="版本号"  field="versionNum"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="部门名称"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="bz"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="证照打印ID"  field="zzdyid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120"></t:dgCol>
  <%--  <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:optAuthFilter name="delete">
   <t:dgDelOpt title="删除" url="scZzyzController.do?doDel&id={id}" />
   </t:optAuthFilter> --%>
   <t:optAuthFilter name="add">
   <t:dgToolBar title="验真" icon="icon-add" url="scZzyzController.do?goAdd" funname="add"></t:dgToolBar>
   </t:optAuthFilter>
     <t:optAuthFilter name="delete">
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="scZzyzController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   </t:optAuthFilter>
   <t:dgToolBar title="查看" icon="icon-search" url="scZzyzController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:optAuthFilter name="excel">
   <t:dgToolBar title="Excel导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   </t:optAuthFilter>
   <%--<t:dgToolBar title="编辑" icon="icon-edit" url="scZzyzController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="scZzyzController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="scZzyzController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  
 <script src = "webpage/com/chrhc/project/sc/zzgl/scZzyzList.js"></script>		
 <script type="text/javascript"
	src="webpage/com/chrhc/project/sc/docfile/gpy.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
	 debugger;
 		//给时间控件加上样式
 			//$("#scZzyzListtb").find("input[name='yzsj_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd '});});
 			//$("#scZzyzListtb").find("input[name='yzsj_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#scZzyzListtb").find("input[name='zzbh']").attr("style","height:29px;width:120px;border:#D2D2D2 1px solid !important;line-height:29px");
 			$("#scZzyzListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:29px;width:120px;line-height:29px").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:"#F{$dp.$D(\'createDate_end\') }"});});//|| \'9999-10-01\'    //,maxDate:"#F{$dp.$D(\'createDate_end\') }"
 			//$("#scZzyzListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").attr("onClick","WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd',minDate:1988-01-01");
 			$("#scZzyzListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:29px;width:120px;line-height:29px").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:"#F{$dp.$D(\'createDate_begin\')}"});});//minDate:"#F{$dp.$D(\'createDate_begin\')}",
 			//$("#scZzyzListtb").find("input[name='createDate_begin']").attr("Onchange","starDate(this,'createDate_end')");
 			/* $("#scZzyzListtb").find("input[name='createDate_begin']").live('Onchange', function(){
 				alert("ok");
 				 //stardate(this);starDate(input,endid)
 			});   */
 			//$("#scZzyzListtb").find("input[name='createDate_end']").change(function(){alert("ok");});
 			//$("a[href='#']").css("margin-right","6px");
 
 });
//覆盖查看函数
 function detail(title,url, id,width,height) {
 	gridname=id;
 	var rowsData = $('#'+id).datagrid('getSelections');
// 	if (rowData.id == '') {
// 		tip('请选择查看项目');
// 		return;
// 	}
 	
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
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'scZzyzController.do?upload', "scZzyzList");
}

//导出
function ExportXls() {
	JeecgExcelExport("scZzyzController.do?exportXls","scZzyzList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("scZzyzController.do?exportXlsByT","scZzyzList");
}
 </script>