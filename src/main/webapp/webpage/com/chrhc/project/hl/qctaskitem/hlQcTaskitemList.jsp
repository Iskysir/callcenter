<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="hlQcTaskitemList" checkbox="true" fitColumns="false" title="质检考评" actionUrl="hlQcTaskitemController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>

   <t:dgCol title="业务工单"  field="orderid"    queryMode="single"  width="180"  formatter="getordercode"></t:dgCol>

   <t:dgCol title="业务类型"  field="ordertype"    queryMode="single"  width="120" dictionary="hl_bus_type"></t:dgCol>
   <t:dgCol title="质检任务主键"  field="taskid"    queryMode="single"  width="120" hidden="true"></t:dgCol>
   <t:dgCol title="质检任务"  field="taskname"    queryMode="single"  width="120" query="true"></t:dgCol>
   <t:dgCol title="评分人"  field="scorepersonname"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="考评得分"  field="avsorce"    queryMode="single"  width="120" formatter="avsorce"></t:dgCol>

   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt title="考评" funname="kaoping(id,taskid,taskname,scorepersonname,orderid,ordertype)"  />

  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/chrhc/project/hl/qctaskitem/hlQcTaskitemList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#hlQcTaskitemListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#hlQcTaskitemListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'hlQcTaskitemController.do?upload', "hlQcTaskitemList");
}

//导出
function ExportXls() {
	JeecgExcelExport("hlQcTaskitemController.do?exportXls","hlQcTaskitemList");
}

//模板下载
function ExportXlsByT() {
 JeecgExcelExport("hlQcTaskitemController.do?exportXlsByT", "hlQcTaskitemList");
}
 function kaoping(id,taskid,taskname,scorepersonname,orderid,ordertype){

  var url="hlQcTaskitemController.do?goScore&id="+id+"&taskid="+taskid+"&taskname="
          +taskname+"&scorepersonname="+scorepersonname+"&orderid="+orderid+"&ordertype="+ordertype;
  addOneTab('考评打分',url,'pictures')
 }
  function avsorce(value,rec,index){
   debugger;
   value = value == "null"?"":value;
   return value;
  }
  function getordercode(value,rec,index){
   var url;
   $.ajax({
    url:"hlQcTaskitemController.do?getOrderCode",
    data:{"id":rec.orderid,"bus_type":rec.ordertype},
    type:"Post",
    dataType:"json",
    async:false,
    success:function(data){
     debugger;
     var addurl = "chrhcFormBuildController.do?ftlForm&tableName="+rec.ordertype+"&mode=read&bizType=&id="+rec.orderid;
      url= "<a href='#' onclick=addOneTab('"+data.obj+"','"+addurl+"','pictures')>"+data.obj+"</a>";
     return url;
    }
   });
return url;
  }
 </script>