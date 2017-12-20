$(document).ready(function(){
	var id=$("input[name='id'][type='hidden']").val();
	if(!id){
		//是否默认值设置
		$("input[type=radio][name='ishelpdeal'][value='yes']").attr("checked",true); 
		$("input[type=radio][name='ishelpdeal'][value='yes']").parent().addClass("checked",true);  
		
		$("input[type=radio][name='iscertificate'][value='yes']").attr("checked",true); 
		$("input[type=radio][name='iscertificate'][value='yes']").parent().addClass("checked",true);  
	}
	
	var date = new Date();
	 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	 //设置时间在当前时间之前
	 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
	 $("input[name='birthday']").attr('onClick',attval);
	 
	 $("input[name='worktime']").attr('onClick',attval);
	 
	 
	 
   
});//document 加载

function beforeSubmit_(){
	
	//出生日期 birthday
    var birthday = $("#birthday").val();
    var birth=new Date(birthday.replace("-", "/").replace("-", "/")); 
    //参加工作时间
    var worktime = $("#worktime").val();
    var work=new Date(worktime.replace("-", "/").replace("-", "/")); 
    //离退休时间retiretime
    var retiretime = $("#retiretime").val();
    var retire=new Date(retiretime.replace("-", "/").replace("-", "/")); 

    if(birth > work ) {
        $("#birthday").parent().find('span.Validform_checktip').text( '出生日期不能大于参加工作时间！').addClass("Validform_checktip Validform_wrong");
        return false;
    } else {
    	//$("#birthday").parent().find('span.Validform_checktip').removeClass("Validform_checktip Validform_right");
    }
    if( work > retire) {
    	$("#worktime").parent().find('span.Validform_checktip').text( '参加工作时间不能大于离退休时间！').addClass("Validform_checktip Validform_wrong");
           return false;
     } else{
    	 $("#worktime").parent().find('span.Validform_checktip').removeClass("Validform_checktip Validform_right");
     }
    if( birth > retire) {
    	$("#retiretime").parent().find('span.Validform_checktip').text( '离退休时间不能小于出生日期！').addClass("Validform_checktip Validform_wrong");
        return false;
    } else {
    	$("#retiretime").parent().find('span.Validform_checktip').removeClass("Validform_checktip Validform_right");
    }
    
    return true;
}