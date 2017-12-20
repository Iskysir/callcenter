$(document).ready(function(){
    var  id= $("input[name='id'][type='hidden']").val();
    
    $("#big_typename").attr("readonly","readonly");
    $("#apl_name").attr("readonly","readonly");
    if(id){
         $("#citypartcode").attr("readonly","readonly");
         
    }else{
    	//$("#big_typename").attr("readonly","readonly");
    	//$("#apl_name").attr("readonly","readonly");
    	//$("#big_typename").parent().prev().hide();         
        $("#big_typename").parent().parent().hide();
        //$("#apl_name").parent().prev().hide();         
        $("#apl_name").parent().parent().hide();
        
        //$("#citypartcode").parent().prev().hide();         
        $("#citypartcode").parent().parent().hide();
    }  
    //var on = $("#type_name").attr("onclick");
    //$("#type_name").attr("onclick",on+"showSome()");
    //$(".suoshu").attr("onclick",on+"showSome()");
    
});//document 加载

var showSome = function(){	   
    $("#big_typename").parent().parent().show();
    $("#apl_name").parent().parent().show();
};
//$("#type_name").click(function(){
//	alert('34565');
//	inputClick(this,'type','citypart_dict','type_name=name,type_code=code,apl_name=type,citypart_apl=aplcode,big_typename=bigtypename,big_type=bigtypecode','{\"singleSelect\":\"true\"}');
//	
//	$("#big_typename").parent().parent().show();
//    $("#apl_name").parent().parent().show();
//});
//curdtools_zh-cn.js定义的回调方法
var old_typeCode = $("#type_code").val(); 
var  ids= $("input[name='id'][type='hidden']").val();
function clearGisForBj(selected){	
	var typeCode = selected.code;
	if(ids){
		//编辑页面
		if(old_typeCode && typeCode != old_typeCode){
		    $("#cleargis").click();
		}
	}else{
		//新建页面
		if(typeCode != old_typeCode){	
			$("#cleargis").click();
		}
		old_typeCode = typeCode;
	}
	
};
