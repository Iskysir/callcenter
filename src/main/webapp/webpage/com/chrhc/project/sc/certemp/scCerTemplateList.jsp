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
    
    
  <t:datagrid name="scCerTemplateList" checkbox="true"   title="证照模板" actionUrl="scCerTemplateController.do?datagrid"  idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="模板名称"  field="tempName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="模板类型"  field="tempType"    queryMode="group" dictionary="template_type" width="120"></t:dgCol>
 
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="scCerTemplateController.do?doDel&id={id}" />
   <t:dgFunOpt title="预览" funname="preview(id)" />
    
   <t:dgToolBar title="录入" icon="icon-add" url="scCerTemplateController.do?goAdd" funname="addTab"  ></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="scCerTemplateController.do?goUpdate" funname="update" width="1200" height="650"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="scCerTemplateController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="scCerTemplateController.do?goUpdate" funname="detail"></t:dgToolBar>


  </t:datagrid>
 
 <script src = "webpage/com/chrhc/project/sc/certemp/scCerTemplateList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });

 function addTab(id) {
	 parent.addTab("模板录入", "scCerTemplateController.do?goAdd");
}

	
 function preview(id) {
	 addOneTab("预览", "scCerTemplateController.do?preview&id=" + id);
}
	

//导入
function ImportXls() {
	openuploadwin('Excel导入', 'scCerTemplateController.do?upload', "scCerTemplateList");
}

//导出
function ExportXls() {
	JeecgExcelExport("scCerTemplateController.do?exportXls","scCerTemplateList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("scCerTemplateController.do?exportXlsByT","scCerTemplateList");
}
 </script>