<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="groupList" title="角色列表" checkbox="${checkbox}" actionUrl="activitiController.do?getGroups" idField="id" pagination="false">
  <t:dgCol title="角色名" field="name" width="40"></t:dgCol>
 <t:dgCol title="角色编码"  field="id" width="40"></t:dgCol>
</t:datagrid>
<script type="text/javascript">

function xuanze(){
	var ids=getgroupListSelections("id");
	$('#expression').val(ids);
}

</script>