function delObj(url,name){
	var id=url.substr(url.lastIndexOf('&id=')+4);
	$.dialog.confirm('确定要删除吗?', function(r) {
		if(!r){return;}
		$.ajax({
			url:'scYwlxpzxController.do?doDel&id='+id,
			type:"Post",
			dataType: 'text',
			success:function(data){
				var d = $.parseJSON(data);
				if (d.success) {
					tip(d.msg);
					reloadsc_ywlxpzxList();
				}else{
					$.dialog.alert(d.msg,reloadsc_ywlxpzxList);
				}
			},
			error:function(data){
				$.messager.alert('删除时出现错误',data.msg);
			}
		});
	});
}	
/**
 * 批量删除。
 */
function sc_ywlxpzxdelBatch(){
	//获取选中的ID串
	var ids = getsc_ywlxpzxListSelections('id');
	if(ids.length<=0){
		tipAlert('请选择至少一条信息');
		return;
	}
	$.dialog.setting.zIndex = $.dialog.setting.zIndex+20;
	$.dialog.confirm('确定要删除吗?', function(r) {
		if(!r){return;}
		$.ajax({
			url:"scYwlxpzxController.do?doBatchDel&ids="+ids,
			type:"Post",
			dataType: 'text',
			success:function(data){
				var d = $.parseJSON(data);
				if (d.success) {
					tip(d.msg);
					reloadsc_ywlxpzxList();
				}else{
					$.dialog.alert(d.msg,reloadsc_ywlxpzxList);
				}
			},
			error:function(data){
				$.messager.alert('删除时出现错误',data.msg);
			}
		});
	});
}
