/**
 * 删除志愿者团队——若该志愿者团队有志愿者成员，不允许删除
 * @param url 操作链接  
 * @param name 列表名称
 */
function delObj(url,name) {
	var id=url.substr(url.lastIndexOf('&id=')+4);
	url="scZhyzhfwdController.do?doDel&id="+id;
        var  zhyzh = true;
        var  fwjl = true;
        var zhyzh2;
        var fwjl2;
	$.ajax({
		url: 'scZhyzhfwdController.do?doCheckZhyzh&id='+id,
		type : 'POST',
		dataType : 'text',
		async : false,
		cache : false,
		success: function(data){
			var d = $.parseJSON(data);
			zhyzh = d.success;
                        zhyzh2 = d.msg;
		}
	});
       $.ajax({
		url: 'scZhyzhfwdController.do?doCheckZhyfwjl&id='+id,
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
        if(zhyzh && fwjl) {
            createdialog('删除确认 ', '确定删除该记录吗 ?', url,name);
        }else{
            if(!zhyzh){
              //$.dialog.alert(zhyzh2);
              $.dialog.alert(zhyzh2,reloadsc_zhyzhfwdList);
            }else if(!fwjl){
              //$.dialog.alert(fwjl2);
              $.dialog.alert(fwjl2,reloadsc_zhyzhfwdList);
            }
        }
}
/**
 * 批量删除志愿服务队——若志愿服务队有志愿服务者成员或志愿服务记录，不允许删除
 * @param url 操作链接   
 * @param name 列表名称   
 */
function sc_zhyzhfwddelBatch(){

	var ids =  getsc_zhyzhfwdListSelections('id');
	if(ids.length<=0){
		tipAlert('请选择至少一条信息');
		return;
	}
	$.dialog.confirm('确定要删除吗?', function(r) {
		if(!r){return;}
		$.ajax({
			url:"scZhyzhfwdController.do?doBatchDel&ids="+ids,
			type:"Post",
			dataType: 'text',
			success:function(data){
				var d = $.parseJSON(data);
				if (d.success) {
					tip(d.msg);
					reloadsc_zhyzhfwdList();
				}else{
					$.dialog.alert(d.msg,reloadsc_zhyzhfwdList);
				}
			},
			error:function(data){
				$.messager.alert('删除时出现错误',data.msg);
			}
		});
	});
}   