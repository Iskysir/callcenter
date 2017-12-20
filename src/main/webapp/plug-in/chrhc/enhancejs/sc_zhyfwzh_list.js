/**
 * 删除志愿服务者--若志愿服务者有志愿服务记录，不允许删除
 */
function delObj(url,name) {
	var id=url.substr(url.lastIndexOf('&id=')+4);
	url="scZhyfwzhController.do?doDel&id="+id;
        var  fwjl = true;
        var fwjl2;
	
       $.ajax({
		url: 'scZhyfwzhController.do?doCheckZhyfwjl&id='+id,
		type : 'POST',
		dataType : 'text',
		async : false,
		cache : false,
		success: function(data){
			var d = $.parseJSON(data);
			fwjl = d.success;
            fwjl2 = d.msg;
		}
	});
        if(fwjl) {
            createdialog('删除确认 ', '确定删除该记录吗 ?', url,name);
        }else{
              if(!fwjl){
              //$.dialog.alert(fwjl2);
               $.dialog.alert(fwjl2,reloadsc_zhyfwzhList);
            }
        }
}
/**
 * 批量删除志愿服务者-若志愿服务者有志愿服务记录，不允许删除
 */
function sc_zhyfwzhdelBatch(){

	var ids =  getsc_zhyfwzhListSelections('id');
	if(ids.length<=0){
		tipAlert('请选择至少一条信息');
		return;
	}
	delObjNew('确定要删除吗?', function(r) {
		$.ajax({
			url:"scZhyfwzhController.do?doBatchDel&ids="+ids,
			type:"Post",
			dataType: 'text',
			success:function(data){
				var d = $.parseJSON(data);
				if (d.success) {
					tip(d.msg);
					reloadsc_zhyfwzhList();
				}else{
					$.dialog.alert(d.msg,reloadsc_zhyfwzhList);
				}
			},
			error:function(data){
				$.messager.alert('删除时出现错误',data.msg);
			}
		});
	});
}   