var cancel = function(){
	debugger;
var selects = $('#scEndowmentinsList').datagrid('getSelections');

if(selects.length){
   if(selects.length > 1){
    $.dialog.alert("请选择一条记录");
   }else if(selects[0].insuredstate == 'cancelled'){
	   $.dialog.alert("该记录已经注销，请重新选择。");
   }
   else{
   var id = selects[0].id;
   var name = selects[0].name;
   var code = selects[0].code;
   var idcard = selects[0].idcard;
   //addOneTab(name +'养老保险注销',"chrhcAutoListController.do?ftlForm&tableName=sc_endowlogout&id=sc_endowlogout&code="+code+"&name="+name);
   
   
    $(".datagrid").hide();
	//var url = 'chrhcFormBuildController.do?ftlForm&tableName=sc_endowlogout&bizType=${bizType!}${initquery}';
    var url = 'scEndowlogoutController.do?cancel';
	if(!id){
		id = getSelected("id");
	}
	url += '&endowid='+ id + '&code='+code+'&name='+name+'&pidcard='+idcard + '&flag=1';
	
	addOneTab(name +'养老保险注销',url);
   }

}else{
 $.dialog.alert("请至少选择一条记录");
return false;
}

};


function linkformatter(value,rec,index){
	var href='';
	var tabname='养老保险参保记录';
	var url = 'scEndowmentinsController.do?goUpdate&id='+rec.id;
	
	href += "<a href = '#' onclick=\"addOneTab('"+tabname+"','"+ url+"')\" ><u>"+value+"</u></a>";
	return href;
}

function nextRecord(id,stepNum){
	debugger;
	var queryParams = $('#scEndowmentinsList').datagrid('options').queryParams;
	$('#scEndowmentinsListtb').find('*').each(function() {
	    queryParams[$(this).attr('name')] = $(this).val();
	});
	
	var params = '&id=' + id + '&stepNum=' + stepNum;
	
	var sortName = $('#scEndowmentinsList').datagrid('options').sortName;  
	params += ('&sort=' + sortName);
	
	var sortOrder = $('#scEndowmentinsList').datagrid('options').sortOrder;  
	params += ('&order=' + sortOrder);
	
	$.each(queryParams, function(key, val){
		params+='&'+key+'='+val;
	}); 
	var fields = '&field=';
	$.each($('#scEndowmentinsList').datagrid('options').columns[0], function(i, val){
		if(val.field != 'opt'){
			if(val.field!='ck')
			{
				fields+=val.field+',';
			}
		}
	}); 
	
	var url = "chrhcAutoListController.do?nextRecord&configId=scEndowmentins" ;
	
	$.post(
		url,
		params+fields,
		function(data){
			var d = $.parseJSON(data);
			var id = d.msg;	 
			if(id){
				var url = 'scEndowmentinsController.do?goUpdate&id='+ id;
				$("#editForm").attr("src",url); 
			}
		}
	);
	
}