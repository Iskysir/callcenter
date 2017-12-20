<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <input type="hidden" value="${ ry_name}" id="ryName">
  <input type="hidden" value="${ ry_id}" id="ryId">
  <t:datagrid name="kinshipList" checkbox="true"   title="亲属关系" actionUrl="kinshipController.do?datagrid&ryId=${ry_id}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="关系类型"  field="gxlx"   query="true" queryMode="single" dictionary="qsgx" width="120"></t:dgCol>
   <t:dgCol title="姓名"  field="name"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="身份证号"  field="obligatea" queryMode="group"  width="150"></t:dgCol>
   <t:dgCol title="备注"  field="bz"    queryMode="group"  width="180"></t:dgCol>
   <t:dgCol title="人员id"  field="ryId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="删除标识"  field="delflag"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="删除时间"  field="delDate"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
  
   <t:dgCol title="预留字段b"  field="obligateb"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="预留字段c"  field="obligatec"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="预留字段d"  field="obligated"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="预留字段e"  field="obligatee"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="亲属姓名id"  field="qsId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="人员名字"  field="ryName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
  
   <t:optAuthFilter name="add">
   <t:dgToolBar title="新增" icon="icon-add" url="kinshipController.do?goAdd&ryId=${ry_id}&ryName=${ry_name }" funname="add" height="300" width="700"></t:dgToolBar>
   </t:optAuthFilter>
   <t:optAuthFilter name="update">
   <t:dgToolBar title="编辑" icon="icon-edit" url="kinshipController.do?goUpdate" funname="update" height="300" width="700"></t:dgToolBar>
   </t:optAuthFilter>
   <t:optAuthFilter name="delete">
   <t:dgToolBar title="删除"  icon="icon-remove" url="kinshipController.do?doBatchDel" funname="deleteALLSelect" > </t:dgToolBar>
   </t:optAuthFilter>
   <t:optAuthFilter name="query">
   <t:dgToolBar title="查看" icon="icon-search" url="kinshipController.do?goUpdate" funname="detail" height="300" width="700"></t:dgToolBar>
   </t:optAuthFilter>
   <t:optAuthFilter name="kinshiptu">
   	<t:dgToolBar title="亲属关系图" icon="icon-kinship"  funname="kinshipdiagram" ></t:dgToolBar>
   	</t:optAuthFilter>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/chrhc/project/sc/kinship/kinshipList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
	 $("a[href='#']").css("margin-right","6px");
 });
 function kinshipdiagram(){
		var id ='${ ry_id}';
		var name ='${ ry_name}';
		var url = "kinshipController.do?diagram&id="+id;
		var title = name+"亲属关系图";
		addOneTab(title, url);
		//openwindow(title,url,"kinshipList","1100px","900px");
	}
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'kinshipController.do?upload', "kinshipList");
}

//导出
function ExportXls() {
	JeecgExcelExport("kinshipController.do?exportXls","kinshipList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("kinshipController.do?exportXlsByT","kinshipList");
}
function sc_jtxxnewExportExcel(){
	var queryParams = $('#kinshipList').datagrid('options').queryParams;
	$('#kinshipListtb').find('*').each(function() {
	    queryParams[$(this).attr('name')] = $(this).val();
	});
	var params = '&';
	$.each(queryParams, function(key, val){
		params+='&'+key+'='+val;
	}); 
	var fields = '&field=';
	$.each($('#kinshipList').datagrid('options').columns[0], function(i, val){
		if(val.field != 'opt'){
			if(val.field!='ck')
			{
				fields+=val.field+',';
			}
		}
	}); 
	window.location.href = "excelTempletController.do?exportXls&tableName=sc_qsgx&bizType="+encodeURI(params+fields)
}

 </script>