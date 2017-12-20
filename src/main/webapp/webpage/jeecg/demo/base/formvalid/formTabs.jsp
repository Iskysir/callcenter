<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="tt" iframe="false" tabPosition="bottom">
	<t:tab href="demoController.do?autoupload&turn=formvalid/basic" icon="icon-search" title="表单验证" id="default"></t:tab>
	<t:tab href="demoController.do?autoupload&turn=autocomplete/basic" icon="icon-search" title="自动完成" id="autocom"></t:tab>
	<t:tab href="demoController.do?select" icon="icon-search" title="下拉联动" id="autoSelect"></t:tab>
</t:tabs>
<script type="text/javascript">
<!--
$(
		function(){
		$.post(
		      "scDataTransconfController.do?transData",
		      {
			  	"dtKey" : "test",
			  	"id" : "402881fa4dad9c28014dadcf8881000a"
			  },
		      function(data) {
		          alert(data);
		      
		      });
			}
);
//-->
</script>
