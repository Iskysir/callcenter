 function delObj(url,name) {

       var urldata  = getUrlData(url);
      
       var id = urldata.id;
       var mainTable = urldata.configId;
	   var checkurl="kinshipController.do?checkdel";
       var data = {};
       data.id=id;
       data.mainTable = mainTable;
       data.zTable="sc_cgss";
       data.wjIdName="cgxx_id";
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
	function sc_cgxxdelBatch(){
	
		//获取选中的ID串
		var ids = getsc_cgxxListSelections('id');
		if(ids.length<=0){
			tipAlert('请选择至少一条信息');
			return;
		}
		delObjNew('确定删除吗?', function(r) {
			 var id = ids;
       var mainTable =  tablename;
	   var checkurl="kinshipController.do?checkdel";
       var data = {};
       data.id=id;
       data.mainTable = mainTable;
       data.zTable="sc_cgss";
       data.wjIdName="cgxx_id";
	   $.ajax({
	    url:checkurl,
	    data:data,
		type:"Post",
	    dataType:"json",
	    async:false,
	    success:function(data){
			if(data.success){
					
			$.ajax({
			    url:"chrhcAutoListController.do?delBatch",
			    data:{'ids':ids,'configId':'sc_cgxx'},
				type:"Post",
			    dataType:"json",
			    success:function(data){
					tip(data.msg);
					reloadsc_cgxxList();
			    },
				error:function(data){
					$.messager.alert('错误',data.msg);
				}
			});			
			}else{
				tip(data.msg);
				return false;
			}
			
	    },
		error:function(data){
			$.messager.alert('错误',data.msg);
			return false;
		}
	    });    
		
			}
		);
	}