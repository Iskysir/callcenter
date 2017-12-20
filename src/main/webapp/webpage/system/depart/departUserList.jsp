<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="departUserList" title="common.operation"
            actionUrl="departController.do?userDatagrid&departid=${departid}" fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="common.username" sortable="false" field="userName" query="true"></t:dgCol>
	<t:dgCol title="common.real.name" field="realName" query="true"></t:dgCol>
	<t:dgCol title="common.status" sortable="true" field="status" replace="common.active_1,common.inactive_0,super.admin_-1"></t:dgCol>
	<t:dgCol title="common.operation" field="opt"></t:dgCol>
	<t:dgDelOpt title="移除" url="userController.do?removeDepartAndMem&id={id}&userName={userName}" />
	<%--<t:dgToolBar title="common.add.param" langArg="common.user" icon="icon-add" url="userController.do?addorupdate&departid=${departid}" funname="add"></t:dgToolBar>
	<t:dgToolBar title="common.edit.param" langArg="common.user" icon="icon-edit" url="userController.do?addorupdate&departid=${departid}" funname="update"></t:dgToolBar>--%>
    <%--update-start--Author:zhangguoming  Date:20140826 for：添加有客户--%>
	<t:dgToolBar title="common.add.exist.user" icon="icon-add" url="departController.do?goAddUserToOrg&orgId=${departid}" funname="add" width="500"></t:dgToolBar>
    <%--update-end--Author:zhangguoming  Date:20140826 for：添加有客户--%>
</t:datagrid>
<script type="text/javascript">
function delObj(url,name) {
	var orgId='${departid}';
	$.dialog.confirm('确定要移除吗?', function(r) {
		if(!r){return;}
		$.ajax({
			url:url+"&orgId="+orgId,
			type:"Post",
			dataType: 'text',
			success:function(data){
				var d = $.parseJSON(data);
				if (d.success) {
					tip(d.msg);
					reloaddepartUserList();
				}else{
					$.dialog.alert(d.msg,reloaddepartUserList);
				}
			},
			error:function(data){
				$.messager.alert('移除时出现错误',data.msg);
			}
		});
	});
}
	function reloadTable() {
		try {
			$('#' + gridname).datagrid('reload');
			$('#' + gridname).treegrid('reload');
		} catch (ex) {
		}
	}
	function reloaddepartUserList() {
		$('#departUserList').datagrid('reload');
	}
</script>
