/**
 * 列表刷新-重写
 */
function reloadTable(){	
	$('#sc_jzwglList').datagrid('reload');
}

/**
 * 批量删除建筑物信息-检查是否生成房间信息
 */
function sc_jzwgldelBatch(){
	

	//获取选中的ID串
	var ids = getsc_jzwglListSelections('id');
	if(ids.length<=0){
		tipAlert('请选择至少一条信息');
		return;
	}
	//初步判断是否存在已生成房间信息的建筑物
	var flags = getsc_jzwglListSelections('auto_code');
	var arrflags = flags.split(',');
	for(var i = 0; i < arrflags.length; i++)
	{
		if(arrflags[i] != '0'){
			tipAlert('存在已生成房间信息的建筑物，不能删除！');
			return false;
		}
	}
	$.dialog.confirm('确定删除吗?', function(r) {
		if(!r){return;}
		$.ajax({
			url:"scJzwglController.do?doBatchDel&ids="+ids,
			type:"Post",
			dataType: 'text',
			success:function(data){
				var d = $.parseJSON(data);
				if (d.success) {
					tip(d.msg);
					reloadsc_jzwglList();
				}else{
					$.dialog.alert(d.msg,reloadsc_jzwglList);
				}
			},
			error:function(data){
				$.messager.alert('错误',data.msg);
			}
		});
	});
}

/**
 * 删除建筑物信息-若已生成房间信息不能删除
 * @param url 操作链接
 * @param name 列表名称
 */
function delObj(url,name) {
	var id=url.substr(url.lastIndexOf('&id=')+4);
	url="scJzwglController.do?doDel&id="+id;
	$.ajax({
		url: 'scJzwglController.do?dochkJzwfj&id='+id,
		type : 'POST',
		dataType : 'text',
		async : false,
		cache : false,
		success: function(data){
			var d = $.parseJSON(data);
			if (d.msg == "9") {
				$.dialog.alert('该建筑物信息已被删除，请确认！', reloadsc_jzwglList);
			}else if (d.msg != "0") {
				$.dialog.alert('该建筑物已生成房间信息,不能删除！', reloadsc_jzwglList);
			}else{
				gridname=name;
				createdialog('删除确认 ', '确定删除该记录吗 ?', url,name);
			}
		}
	});
}

/**
 * 生成建筑物房间信息
 * @param id 建筑物Id
 */
function doBatchJzwfj(id){
	gridname="sc_jzwglList";

	$.ajax({
		url: 'scJzwglController.do?dochkJzwfj&id='+id,
		type : 'POST',
		dataType : 'text',
		async : false,
		cache : false,
		success: function(data){
			var d = $.parseJSON(data);
			if (d.msg == "9") {
				$.dialog.alert('该建筑物信息已被删除，请确认。', reloadsc_jzwglList);
			}else if (d.msg == "1") {
				$.dialog.alert('该建筑物已生成房间信息！', reloadsc_jzwglList);
			}else{
				createdialog('生成确认 ', '生成房间信息后，该建筑物信息无法删除，并请慎重修改单元信息！确定生成吗 ?', 'scJzwglController.do?doBatchJzwfj&id='+id,name);
			}
		}
	});
}

/**
 * 查看建筑物房间信息
 * @param id 建筑物Id
 */
function doJzwfjView(id){
	gridname="sc_jzwglList";
	$.ajax({
		url: 'scJzwglController.do?dochkJzwfj&id='+id,
		type : 'POST',
		dataType : 'text',
		async : false,
		cache : false,
		success: function(data){
			var d = $.parseJSON(data);
			if (d.msg == "9") {
				$.dialog.alert('该建筑物信息已被删除，请确认！', reloadsc_jzwglList);
			}else if (d.msg == "1") {
				var name = d.obj;
				if(name.length > 10){
					name = name.substring(0,10) + '…';
				}
				addOneTab(name+'[房间信息]', 'scJzwglController.do?findFjByJzwId&id=' + id, 'pictures');
			}else{
				$.dialog.alert('该建筑物未生成房间信息，请确认！', reloadsc_jzwglList);
			}
		}
	});
}