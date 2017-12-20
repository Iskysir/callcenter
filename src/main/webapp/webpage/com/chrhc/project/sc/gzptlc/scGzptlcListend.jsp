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
     	 <a href="javascript:;">个人办公</a>&nbsp;
     	  <a href="javascript:;">已审核列表</a>
    </div>
<div class="easyui-layout" fit="true" >
  <div region="center" style="padding:1px;">
  <t:datagrid name="scGzptlcList" checkbox="true" fitColumns="true" title="电子资料" actionUrl="scGzptlcController.do?getdatas&url=http://10.61.160.7:8280/cpub-back-web/api/app/netservice/declare/taskcompletelist" idField="id" fit="true" queryMode="group" sortOrder="desc" sortName="createDate">
   <t:dgCol title="主键"  field="id"  hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="业务类型"  field="biz_type"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="业务名称   "  field="biz_name"    queryMode="single"  width="120"></t:dgCol>
  <t:dgCol title="姓名"  field="declare_per_name"   query="true" queryMode="single"  width="120"></t:dgCol>
  <t:dgCol title="身份证号"  field="declare_id_num"  hidden="true"  query="true" queryMode="single"  width="120"></t:dgCol> 
   <t:dgCol title="电话"  field="declare_phone"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="邮箱"  field="declare_email"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="提交日期"  field="submit_time"  formatter="yyyy-MM-dd hh:mm"  query="false" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="审核状态"  field="department_approve_state"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="实例ID"  field="instance_id" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="受理编号"  field="accept_code"  hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="机构"  field="declare_id_num"  hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="流程当前节点编码"  field="activity_code" hidden="true"  queryMode="single"  width="120"></t:dgCol>
  
   
   <%--  <t:optAuthFilter name="update">
   <t:dgToolBar title="编辑" icon="icon-edit" url="scGzptlcController.do?goUpdate&flag=up&url=http://10.61.160.7:8280/cpub-back-web/api/app/netservice/declare/get/" funname="update" ></t:dgToolBar>
     </t:optAuthFilter> --%>
   <t:dgToolBar title="查看" icon="icon-view" url="scGzptlcController.do?goUpdate&flag=vi&url=http://10.61.160.7:8280/cpub-back-web/api/app/netservice/declare/get/" funname="detail"></t:dgToolBar>

   
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
function scGzptlcListsearch(){

var url="&url=http://10.61.160.7:8280/cpub-back-web/api/app/netservice/declare/taskcompletelist";
var url_ ="scGzptlcController.do?getdatas&declare_per_name=";
var declare_per_name = $("input[name='declare.per.name']").val();
var declare_id_num = $("input[name='declare.id.num']").val();
url_=url_+declare_per_name+"&declare_id_num="+declare_id_num+url;
//alert(url_);
$('#scGzptlcList').datagrid({url:encodeURI(url_),pageNumber:1});	
}
 </script>