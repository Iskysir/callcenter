
var cancel = function(){
var selects = $('#sc_endowmentinsList').datagrid('getSelections');

if(selects.length){
   if(selects.length > 1){
    $.dialog.alert("请选择一条记录");
   }else{
   var id = selects[0].id;
   var name = selects[0].name;
   var code = selects[0].code;
   var idcard = selects[0].idcard;
   
   var url = 'chrhcFormBuildController.do?ftlForm&tableName=sc_endowlogout&bizType=${bizType!}${initquery}';
	if(!id){
		id = getSelected("id");
	}
	url += '&endowid='+ id + '&code='+code+'&name='+name+'&pidcard='+idcard + '&flag=1';
   
   
   addOneTab(name +'养老保险注销',url);
   
   
   // $(".datagrid").hide();
	//var url = 'chrhcFormBuildController.do?ftlForm&tableName=sc_endowlogout&bizType=${bizType!}${initquery}';
	//if(!id){
//		id = getSelected("id");
//	}
	//url += '&endowid='+ id + '&code='+code+'&name='+name+'&pidcard='+idcard + '&flag=1';
	//$("#editFormDiv").show();
	//$("#editForm").attr("src",url); 
   }

}else{
 $.dialog.alert("请至少选择一条记录");
return false;
}

};

//关联删除验证
function sc_endowmentinsdelBatch(){

	var name = getsc_endowmentinsListSelected('name');
	//养老保险编号
	var code = getsc_endowmentinsListSelected('code');

	//获取选中的ID串
	var ids = getsc_endowmentinsListSelections('id');
	if(ids.length<=0){
		tipAlert('请选择至少一条信息');
		return;
	}
	delObjNew('确定删除吗?', function(r) {
		 var id = ids;
   var mainTable = tablename;
   var checkurl="scEwmnrxpzController.do?checkdel";
 
   var datajt = {};
   datajt.id=id;
   datajt.mainTable = mainTable;
   datajt.zTable="sc_endowlogout";
   datajt.wjIdName="endowid";
   $.ajax({
	    url:checkurl,
	    data:datajt,
		type:"Post",
	    dataType:"json",
	    async:false,
	    success:function(data){
	    	if(data.success){
				 $.ajax({
				    url:"chrhcAutoListController.do?delBatch",
				    data:{'ids':ids,'configId':'sc_endowmentins'},
					type:"Post",
				    dataType:"json",
				    success:function(data){
						tip(data.msg);
						reloadsc_endowmentinsList();
				    },
					error:function(data){
						$.messager.alert('错误',data.msg);
					}
				});		
				}else{
					tip(name+""+data.msg);
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