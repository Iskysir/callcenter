<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:datagrid checkbox="${checkbox}" title="用户列表" name="userList" actionUrl="activitiController.do?getUsers" idField="id" pagination="false">
 <t:dgCol title="用户名" field="firstName" width="40"></t:dgCol>
 <t:dgCol title="用户账号" field="id" width="40"></t:dgCol>
</t:datagrid>
<script type="text/javascript">
function xuanze(){
	var ids=getuserListSelections("id");
	$('#expression').val(ids);
}
</script>
