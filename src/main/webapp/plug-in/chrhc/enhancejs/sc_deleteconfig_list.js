function del(id){
//	var id = getSelected("id");
	$.post(
			"scDeleteconfigsController.do?doBatchDel&id="+id,
			"",
			function(data){
				var d = $.parseJSON(data);
				if (d.success) {
					tip(d.msg);
					
				}else{
					$.dialog.alert(d.msg);
				}
			});
}