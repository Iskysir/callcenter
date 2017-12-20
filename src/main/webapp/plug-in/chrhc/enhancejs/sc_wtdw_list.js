/**
 * 删除功能——若该文体队伍有文体活动记录，则不允许删除
 */
function delObj(url,name) {
	var id=url.substr(url.lastIndexOf('&id=')+4);
	url="scWtdwController.do?doDel&id="+id;
        var  wthd = true;
        var wthd2;
	$.ajax({
		url: 'scWtdwController.do?doCheckWthd&id='+id,
		type : 'POST',
		dataType : 'text',
		async : false,
		cache : false,
		success: function(data){
			var d = $.parseJSON(data);
			wthd = d.success;
      wthd2 = d.msg;
		}
	});      
        if(wthd) {
            createdialog('删除确认','确定删除该记录吗?', url,name);
        }else{
            if(!wthd){
              //$.dialog.alert(wthd2);
              $.dialog.alert(wthd2,reloadsc_wtdwList);
            }
        }
}
/**
 * 批量删除 ——若文体队伍有文体活动记录，则不允许删除。
 */
function sc_wtdwdelBatch(){
	//
	var ids =  getsc_wtdwListSelections('id');
	if(ids.length<=0){
		tip('请至少选择一条信息');
		return;
	}
	delObjNew('确定要删除吗?', function(r) {
		$.ajax({
			url:"scWtdwController.do?doBatchDel&ids="+ids,
			type:"Post",
			dataType: 'text',
			success:function(data){
				var d = $.parseJSON(data);
				if (d.success) {
					tip(d.msg);
					reloadsc_wtdwList();
				}else{
					tip(d.msg,reloadsc_wtdwList);
				}
			},
			error:function(data){
				$.messager.alert('删除时出现错误',data.msg);
			}
		});
	});
}