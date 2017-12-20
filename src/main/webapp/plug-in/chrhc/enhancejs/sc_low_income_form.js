
var date = new Date();
var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
//设置时间在当前时间之前
var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
$("input[name='application_time']").attr('onClick',attval);
$("input[name='start_time']").attr('onClick',attval);
$("input[name='birthday']").attr('onClick',attval);
//$("input[name='stop_time']").attr('onClick',attval);
$("input[name='poor_start_time']").attr('onClick',attval);
//$("input[name='poor_stop_time']").attr('onClick',attval);
//用于展示个人头像图片zwt
var id= $("input[name='id']").val();
var rkid= $("input[name='rk_id']").val();
if(id){
	getrkxx();	
}else{
	if(rkid){
		getrkxx();	
	}else{
		addel("");
	}

}
//保存默认提交
/*function submitCallback(data){
	var url = 'activitiController.do?startOnlineProcess&configId=sc_low_income&id=' + data.obj.id  ;
	asynRequest(url,function(data){
		
	})
	
}*/

//对时间得先后顺序做限制
function beforeSubmit_(){
	
	//出生日期 birthday
    var birthday = $("#birthday").val();
    var birth=new Date(birthday.replace("-", "/").replace("-", "/")); 
    //申请时间
    var application_time = $("#application_time").val();
    var application=new Date(application_time.replace("-", "/").replace("-", "/")); 
    //保障金开始时间
    var start_time = $("#start_time").val();
    var start=new Date(start_time.replace("-", "/").replace("-", "/")); 
    //保障金结束时间
    var stop_time = $("#stop_time").val();
    var stop=new Date(stop_time.replace("-", "/").replace("-", "/")); 
    //帮困卡开始时间
    var poor_start_time = $("#poor_start_time").val();
    var poor_start=new Date(poor_start_time.replace("-", "/").replace("-", "/")); 
    //帮困卡结束时间
    var poor_stop_time = $("#poor_stop_time").val();
    var poor_stop=new Date(poor_stop_time.replace("-", "/").replace("-", "/")); 

    if(birth > application ) {
   	   $("#birthday").next().next().remove();
          $("#birthday").next('span').text( '出生日期不能晚于申请时间！');
          $("#birthday").next('span').attr("class","Validform_checktip Validform_wrong");
          return false;
    } else {
    	$("#birthday").next('span').attr("class","Validform_checktip Validform_right");
    	$("#birthday").next('span').removeAttr("style");
    }
    if( application > start) {
    	   $("#application_time").next().next().remove();
           $("#application_time").next('span').text( '申请时间不能晚于开始时间！');
           $("#application_time").next('span').attr("class","Validform_checktip Validform_wrong");
           return false;
     } else{
    	 $("#application_time").next('span').attr("class","Validform_checktip Validform_right");
    	 $("#application_time").next('span').removeAttr("style");
     }
    if( start > stop) {
 	   $("#start_time").next().next().remove();
        $("#start_time").next('span').text( '保障金开始时间不能晚于结束时间！');
        $("#start_time").next('span').attr("class","Validform_checktip Validform_wrong");
        return false;
    } else {
    	$("#start_time").next('span').attr("class","Validform_checktip Validform_right");
    	$("#start_time").next('span').removeAttr("style");
    }
    if( birth > poor_start) {
  	   $("#poor_start_time").next().next().remove();
         $("#poor_start_time").next('span').text( '出生日期不能晚于帮困卡开始时间！');
         $("#poor_start_time").next('span').attr("class","Validform_checktip Validform_wrong");
         return false;
     } else {
     	$("#poor_start_time").next('span').attr("class","Validform_checktip Validform_right");
     	$("#poor_start_time").next('span').removeAttr("style");
     }
    if( poor_start > poor_stop) {
  	   $("#poor_start_time").next().next().remove();
         $("#poor_start_time").next('span').text( '帮困卡开始时间不能晚于结束时间！');
         $("#poor_start_time").next('span').attr("class","Validform_checktip Validform_wrong");
         return false;
     } else {
     	$("#poor_start_time").next('span').attr("class","Validform_checktip Validform_right");
     	$("#poor_start_time").next('span').removeAttr("style");
     }
    
    return true;
}