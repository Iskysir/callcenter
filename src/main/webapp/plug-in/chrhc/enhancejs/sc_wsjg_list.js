 function delObj(url,name) {

       var urldata  = getUrlData(url);
      
       var id = urldata.id;
       var mainTable = urldata.configId;
	   var checkurl="kinshipController.do?checkdel";
       var data = {};
       data.id=id;
       data.mainTable = mainTable;
       data.zTable="sc_wsgzjl";
       data.wjIdName="institution";
	   $.ajax({
	    url:checkurl,
	    data:data,
		type:"Post",
	    dataType:"json",
	    async:false,
	    success:function(data){
			if(data.success){
				createdialog('删除确认 ', '确定删除该记录吗 ?', url,name);				
			}else{
				$.dialog.alert(data.msg);
				return false;
			}
			
	    },
		error:function(data){
			$.messager.alert('错误',data.msg);
			return false;
		}
	    });    
}

//批量删除
	function sc_wsjgdelBatch(){
	
		//获取选中的ID串
		var ids =  getsc_wsjgListSelections('id');
		if(ids.length<=0){
			tipAlert('请选择至少一条信息');
			return;
		}
		$.dialog.confirm('确定要删除吗?', function(r) {
			if(!r){return;}
			$.ajax({
				url:"scWsjgController.do?doBatchDel&ids="+ids,
				type:"Post",
				dataType: 'text',
				success:function(data){
					var d = $.parseJSON(data);
					if (d.success) {
						tip(d.msg);
						reloadsc_wsjgList();
					}else{
						$.dialog.alert(d.msg,reloadsc_wsjgList);
					}
				},
				error:function(data){
					$.messager.alert('删除时出现错误',data.msg);
				}
			});
		});
	}