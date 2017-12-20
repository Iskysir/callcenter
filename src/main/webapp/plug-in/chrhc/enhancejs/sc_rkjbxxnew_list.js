$(function($){
var urldata = getUrlData();
var bizType = urldata.bizType;
if(bizType){

var $kinship = $("#kinship");
//$kinship.hide();
}

});

//关联删除验证
function sc_rkjbxxnewdelBatch(){

	var name = getsc_rkjbxxnewListSelected('xm');

	//获取选中的ID串
	var ids = getsc_rkjbxxnewListSelections('id');
	if(ids.length<=0){
		tipAlert('请选择至少一条信息');
		return;
	}
	delObjNew('确定删除吗?', function(r) {
		 var id = ids;
   var mainTable = tablename;
   var checkurl="kinshipController.do?checkdel";
   var datark = {};
   datark.id=id;
   datark.mainTable = mainTable;
   datark.zTable="sc_rk_busines";
   datark.wjIdName="rkxx_id";
 
   var datajt = {};
   datajt.id=id;
   datajt.mainTable = mainTable;
   datajt.zTable="sc_jtxxnew";
   datajt.wjIdName="hz_id";
   $.ajax({
	    url:checkurl,
	    data:datajt,
		type:"Post",
	    dataType:"json",
	    async:false,
	    success:function(data){
	   
			if(data.success){
				 $.ajax({
					    url:checkurl,
					    data:datark,
						type:"Post",
					    dataType:"json",
					    async:false,
					    success:function(data){
					    	
							if(data.success){
							 $.ajax({
							    url:"chrhcAutoListController.do?delBatch",
							    data:{'ids':ids,'configId':'sc_rkjbxxnew'},
								type:"Post",
							    dataType:"json",
							    success:function(data){
									tip(data.msg);
									reloadsc_rkjbxxnewList();
							    },
								error:function(data){
									$.messager.alert('错误',data.msg);
								}
							});		
							}else{
								tipAlert(name+""+data.msg);
								return false;
							}
							
					    },
						error:function(data){
							$.messager.alert('错误',data.msg);
							return false;
						}
					    }); 			
			}else{
				tipAlert("与家庭"+data.msg);
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

var kinship = function(){
var selects = $('#sc_rkjbxxnewList').datagrid('getSelections');

if(selects.length){
   if(selects.length > 1){
	   tipAlert('请选择至少一条信息');
   }else{
   var id = selects[0].id;
   var name = selects[0].xm;
   addOneTab(name +'亲属关系维护',"kinshipController.do?kinship&id="+id+"&name="+name);
   }

}else{
	tipAlert('请选择至少一条信息');
return false;
}

};
var queryrk = function(){
debugger;
	var selects = $('#sc_rkjbxxnewList').datagrid('getSelections');

	if(selects.length){
	   if(selects.length > 1){
		   tipAlert('请选择至少一条信息');
	   }else{
		   var id = selects[0].id;
		   var name = selects[0].xm;
		   var personurl = "kinshipController.do?personrecord&id="+id;
		   addOneTab("人员履历", personurl);
/*		   var proveurl = "cgReportController.do?popup&id=sc_rk_yw_config&config={\"queryType\":\"**\",\"singleSelect\":true\}";
		   $.dialog({
				content: 'url:'+proveurl,
				lock : true,
				width:'800px',
				//zIndex:1990,
				height:'400px',
				title:'人口业务信息查询',
				scrolling:0,
				cache:false,
				
			    ok: function(){
			    	debugger;
				    iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
			    		 tipAlert("请选择一条记录");
			    		return false;
				    }else{
				    	var ywcode = selected[0].businesscode;
				    	var businessdes = selected[0].businessdes;
				    	var rkxx_filed = selected[0].rkxx_filed;
				    	var url = "chrhcAutoListController.do?list&id="+ywcode+"&"+rkxx_filed+"="+id;
				    	addOneTab(businessdes, url);
				    }
		    	},
			    cancelVal: '关闭',
			    cancel: true 为true等价于function(){}
			}).zindex();*/
		   
			
	   }

	}else{
		tipAlert('请选择至少一条信息');
	return false;
	}


};

var prove = function(){
	var selects = $('#sc_rkjbxxnewList').datagrid('getSelections');

	if(selects.length){
	   if(selects.length > 1){
		   tipAlert('请选择至少一条信息');
	   }else{
		   var id = selects[0].id;
		   
		   var proveurl = "kinshipController.do?prove";
		   $.dialog({
				content: 'url:'+proveurl,
				lock : true,
				width:'482px',
				//zIndex:1990,
				height:'68px',
				title:'证明开具',
				scrolling:0,
				cache:false,
				
			    ok: function(){
			    	
			    	iframe = this.iframe.contentWindow;
			    
					var dtKey = iframe.$("[name='provetype']").val();
					var text = iframe.$("[name='provetype']").find("option:selected").text();
					if(dtKey == "" || dtKey == null){
			    	
							tipAlert('请选择证明类型');
				    		return false;
			    		
			    		
			    	}
			    	
			    	$.post(
						      "scDataTransconfController.do?transData",
						      {
							  	"dtKey" : dtKey,
							  	"id" : id
							  },
						      function(data) {
									var obj = jQuery.parseJSON(data);
									  
									addOneTab(text, obj.attributes.url);

				   });
			    	
			    	
					
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		   
			
	   }

	}else{
		tipAlert('请选择至少一条信息');
	return false;
	}

};

/*var prove = function(){
	var selects = $('#sc_rkjbxxnewList').datagrid('getSelections');

	if(selects.length){
	   if(selects.length > 1){
	    tipAlert("请选择一条记录");
	   }else{
		   var id = selects[0].id;
			$.post(
				      "scDataTransconfController.do?transData",
				      {
					  	"dtKey" : "living_proof",
					  	"id" : id
					  },
				      function(data) {
				
							var obj = jQuery.parseJSON(data);
							  
							addOneTab("居住证明编辑", obj.attributes.url);

				      });
	   }

	}else{
	 tipAlert("请至少选择一条记录");
	return false;
	}

};*/

var into = function(){
	var selects = $('#sc_rkjbxxnewList').datagrid('getSelections');

	if(selects.length){
	   if(selects.length > 1){
	    tipAlert("请选择一条记录");
	   }else{
		   var id = selects[0].id;
			$.post(
				      "scDataTransconfController.do?transData",
				      {
					  	"dtKey" : "move_into",
					  	"id" : id
					  },
				      function(data) {
				
							var obj = jQuery.parseJSON(data);
							  
							addOneTab("户口迁入编辑", obj.attributes.url);

				      });
	   }

	}else{
		tipAlert("请至少选择一条记录");
	return false;
	}

};

var rent = function(){
	var selects = $('#sc_rkjbxxnewList').datagrid('getSelections');
	if(selects.length){
	   if(selects.length > 1){
	    tipAlert("请选择一条记录");
	   }else{
		   var id = selects[0].id;
			$.post(
				      "scDataTransconfController.do?transData",
				      {
					  	"dtKey" : "rent",
					  	"id" : id
					  },
				      function(data) {
				
							var obj = jQuery.parseJSON(data);
							  
							addOneTab("出租户编辑", obj.attributes.url);

				      });
	   }

	}else{
	 tipAlert("请至少选择一条记录");
	return false;
	}

};
var prve_next_url ;
function nextRecordperson(id,stepNum){
	var $tablenamea = $("#"+tablename+"List");
	var queryParams = $tablenamea.datagrid('options').queryParams;
	 $("#"+tablename+"Listtb").find('*').each(function() {
	    queryParams[$(this).attr('name')] = $(this).val();
	});
	
	var params = '&id=' + id + '&stepNum=' + stepNum;
	
	var sortName = $tablenamea.datagrid('options').sortName;  
	params += ('&sort=' + sortName);
	
	var sortOrder = $tablenamea.datagrid('options').sortOrder;  
	params += ('&order=' + sortOrder);
	
	$.each(queryParams, function(key, val){
		params+='&'+key+'='+val;
	}); 
	var fields = '&field=';
	$.each($tablenamea.datagrid('options').columns[0], function(i, val){
		if(val.field != 'opt'){
			if(val.field!='ck')
			{
				fields+=val.field+',';
			}
		}
	}); 
	
	var url = "chrhcAutoListController.do?nextRecord&configId=sc_rkjbxxnew&bizType="+params+fields ;
	$.ajax({
	    url:url,
		type:"get",
	    dataType:"json",
	    async:false,
	    success:function(data){
	    	var path ="";
	    	if(data.success){
	    		
				//var d = $.parseJSON(data);
				var id = data.msg;	 
				
				if(id){
					var url = "kinshipController.do?personrecord&id="+id;
				  prve_next_url = url;
				}else{
					prve_next_url = "";
				}
	    		
	    	}	    	
	    				
	    },
		error:function(data){
			$.messager.alert('错误',data.msg);
			return false;
		}
	});
	/*$.post(
		url,
		params+fields,
		function(data){
			debugger;
			var d = $.parseJSON(data);
			var id = d.msg;	 
			alert(id+"下一个");
			if(id){
				var url = "kinshipController.do?personrecord&id="+id;
			  prve_next_url = url;
			}else{
				prve_next_url = "";
			}
		}
	);*/
return 	prve_next_url;
}
