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
    
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;"><t:datagrid name="roleList" title="角色管理" actionUrl="roleController.do?roleGrid" idField="id">
	<t:dgCol title="common.code" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="common.role.code" field="roleCode"></t:dgCol>
	<t:dgCol title="common.role.name" field="roleName" query="true"></t:dgCol>
	<t:dgCol title="common.operation" field="opt"></t:dgCol>
	<t:dgFunOpt funname="delRole(id)" title="common.delete"></t:dgFunOpt>
	<t:dgFunOpt funname="userListbyrole(id,roleName)" title="common.user"></t:dgFunOpt>
	<t:dgFunOpt funname="setfunbyrole(id,roleName)" title="permission.set"></t:dgFunOpt>
	<t:dgFunOpt title="数据权限" funname="rolePermClick(id,roleName)" operationCode="dataPrvl"/>
	<t:dgToolBar title="common.add.param" langArg="common.role" icon="icon-add" url="roleController.do?addorupdate" funname="add"></t:dgToolBar>
	<t:dgToolBar title="common.edit.param" langArg="common.role" icon="icon-edit" url="roleController.do?addorupdate" funname="update"></t:dgToolBar>
</t:datagrid></div>
</div>
<div region="east" style="width: 600px;" split="true">
<div tools="#tt" class="easyui-panel" title='<t:mutiLang langKey="permission.set"/>' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<div id="tt"></div>
</div>

<script type="text/javascript">
function setfunbyrole(id,roleName) {
	$("#function-panel").panel(
		{
			title :roleName+ ':' + '<t:mutiLang langKey="current.permission"/>',
			href:"roleController.do?fun&roleId=" + id
		}
	);
	$('#function-panel').panel("refresh" );
	
}
//update-start--Author:gaofeng Date:20140822 for：查看角色的所有用户信息
function userListbyrole(id,roleName) {
	$("#function-panel").panel(
		{
			title :roleName+ ':' + '<t:mutiLang langKey="common.user"/>',
			href:"roleController.do?userList&roleId=" + id
		}
	);
	$('#function-panel').panel("refresh" );
	
}
//update-end--Author:gaofeng Date:20140822 for：查看角色的所有用户信息
//删除角色
function delRole(id){
	var tabName= 'roleList';
	var url= 'roleController.do?delRole&id='+id;
	$.dialog.confirm('<t:mutiLang langKey="confirm.delete.this.record"/>', function(){
		doSubmit(url,tabName);
		rowid = '';
		$("#function-panel").html("");//删除角色后，清空对应的权限
	}, function(){
	});
}
function rolePermClick(id,roleName) {
    var url = "scReportPermController.do?scReportPerm&permType=2&permId=" + id; 
	addOneTab("角色【" + roleName + "】报表权限设置", url);
}
</script>
