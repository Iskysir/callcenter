<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>角色集合</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<script type="text/javascript">
	$(function() {
		$('#roleList').datagrid({
			idField : 'id',
			url : 'userController.do?datagridRole&field=id,roleName,',
			fit : true,
			loadMsg : '数据加载中...',
			pageSize : 10,
			pagination : false,
			pageList : [ 10, 20, 30 ],
			sortOrder : 'asc',
			rownumbers : true,
			singleSelect : false,
			fitColumns : true,
			showFooter : true,
			frozenColumns : [ [ {
				field : 'ck',
				checkbox : 'true'
			}, ] ],
			columns : [ [ {
				field : 'id',
				title : '编号',
				hidden : true,
				sortable : true
			}, {
				field : 'roleName',
				title : '角色名称',
				width : 50,
				sortable : true
			} ] ],
			onLoadSuccess : function(data) {
				$("#roleList").datagrid("clearSelections");	
				var roleId = '${roleId}';
				if(roleId){
					var ids = roleId.split(',');
					for(var i=0;i<ids.length;i++){
						$("#roleList").datagrid("selectRecord",ids[i]);
					}
				} 
			},
			onClickRow : function(rowIndex, rowData) {
				rowid = rowData.id;
				gridname = 'roleList';
			}
		});
		$('#roleList').datagrid('getPager').pagination({
			beforePageText : '',
			afterPageText : '/{pages}',
			displayMsg : '{from}-{to}共 {total}条',
			showPageList : true,
			showRefresh : false
		});
		$('#roleList').datagrid('getPager').pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				$(this).pagination('loading');
				$(this).pagination('loaded');
			}
		});
		
	});
	function reloadTable() {
		try {
			$('#' + gridname).datagrid('reload');
			$('#' + gridname).treegrid('reload');
		} catch (ex) {
		}
	}
	function reloadroleList() {
		$('#roleList').datagrid('reload');
	}
	function getroleListSelected(field) {
		return getSelected(field);
	}
	function getSelected(field) {
		var row = $('#' + gridname).datagrid('getSelected');
		if (row != null) {
			value = row[field];
		} else {
			value = '';
		}
		return value;
	}
	function getroleListSelections(field) {
		var ids = [];
		var rows = $('#roleList').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i][field]);
		}
		ids.join(',');
		return ids;
	};
	function getSelectRows() {
		return $('#roleList').datagrid('getChecked');
	}
	function roleListsearch() {
		var queryParams = $('#roleList').datagrid('options').queryParams;
		$('#roleListtb').find('*').each(function() {
			queryParams[$(this).attr('name')] = $(this).val();
		});
		$('#roleList').datagrid({
			url : 'userController.do?datagridRole&field=id,roleName,',
			pageNumber : 1
		});
	}
	function dosearch(params) {
		var jsonparams = $.parseJSON(params);
		$('#roleList').datagrid({
			url : 'userController.do?datagridRole&field=id,roleName,',
			queryParams : jsonparams
		});
	}
	function roleListsearchbox(value, name) {
		var queryParams = $('#roleList').datagrid('options').queryParams;
		queryParams[name] = value;
		queryParams.searchfield = name;
		$('#roleList').datagrid('reload');
	}
	$('#roleListsearchbox').searchbox({
		searcher : function(value, name) {
			roleListsearchbox(value, name);
		},
		menu : '#roleListmm',
		prompt : '请输入查询关键字'
	});
	function EnterPress(e) {
		var e = e || window.event;
		if (e.keyCode == 13) {
			roleListsearch();
		}
	}
	function searchReset(name) {
		$("#" + name + "tb").find(":input").val("");
		roleListsearch();
	}
</script>
<table width="100%" id="roleList" toolbar="#roleListtb"></table>
<div id="roleListtb" style="padding: 3px; height: auto">
	<div name="searchColums">
		<span style="display: -moz-inline-box; display: inline-block;"><span
			style="vertical-align: middle; display: -moz-inline-box; display: inline-block; width: 80px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"
			title="角色名称">角色名称：</span><input onkeypress="EnterPress(event)"
			onkeydown="EnterPress()" type="text" name="roleName"
			style="width: 100px" /></span>
	</div>
	<div style="height: 30px;" class="datagrid-toolbar">
		<span style="float: left;"></span><span style="float: right"><a
			href="#" class="easyui-linkbutton" iconCls="icon-search"
			onclick="roleListsearch()">查询</a><a href="#"
			class="easyui-linkbutton" iconCls="icon-reload"
			onclick="searchReset('roleList')">重置</a></span>
	</div>
</body>

</html>
