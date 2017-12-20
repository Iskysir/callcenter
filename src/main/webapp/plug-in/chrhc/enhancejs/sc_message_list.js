function look(){
	var id = getSelected("id");
	if(id == null||id==""){
		alert("请选择一条数据");
	}else{
		
		addOneTab("信息详情", "scMessageController.do?look&id=" + getSelected("id") );
	}
}
$(function(){
	
	sc_messageview = function  (id) {
		$(".datagrid").hide();
		var url = "scMessageController.do?look&id=" +id;
		if(!id){
			id = getSelected("id");
		}
		url += '&id='+ id;
		$("#editFormDiv").show();
		$("#editForm").attr("src",url);
	};
	sc_messageupdate = function (id){
		$(".datagrid").hide();
		var url = "scMessageController.do?look&id=" +id;
		if(!id){
			id = getSelected("id");
		}
		url += '&id='+ id;
		$("#editFormDiv").show();
		$("#editForm").attr("src",url);	
	}
});

$(document).ready(function(){
	$(".addbtn-new").hide();
});

function nextRecord(id,stepNum){
	
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
	
	var url = "chrhcAutoListController.do?nextRecord&configId=sc_message&bizType=" ;
	
	$.ajax({
			type: 'POST',
			url: url ,
			data: params+fields ,
			dataType:'json', 
			async : false ,
			success :function(data){
			var id = data.msg;	 
			if(id){
				url = "scMessageController.do?look&tableName=sc_message";
				if($("#editForm").attr("src").indexOf("mode=read")!=-1){
					url += "&mode=read";
				}
				url += "&bizType="+initquery+"&id="+ id
				//$("#editForm").attr("src",url); 
			}else {
				url = '' ;
			}
		} }
	);
	
	return url ;
	
}

/*$(document).ready(function() {
	debugger;
	var aa = $("td[field='title']");
	$("td[field='title']").hide();
	
});*/