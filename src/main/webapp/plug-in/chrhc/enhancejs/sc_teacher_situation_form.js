$(function($){
var date = new Date();
var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
//设置时间在当前时间之前
var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
$("input[name='fill_time']").attr('onClick',attval);
$("input[name='birthday']").attr('onClick',attval);
});

//对时间得先后顺序做限制
function beforeSubmit_(){
	
	//出生日期
    var birthday = $("#birthday").val();
    var start=new Date(birthday.replace("-", "/").replace("-", "/")); 
    //填报时间
    var fill_time = $("#fill_time").val();
    var stop=new Date(fill_time.replace("-", "/").replace("-", "/")); 

    if(start > stop ) {
   	   $("#birthday").next().next().remove();
          $("#birthday").next('span').text( '出生日期不能晚于填报日期！');
          $("#birthday").next('span').attr("class","Validform_checktip Validform_wrong");
          return false;
    } else {
    	$("#birthday").next('span').attr("class","Validform_checktip Validform_right");
    	$("#birthday").next('span').removeAttr("style");
    }
    
    return true;
}