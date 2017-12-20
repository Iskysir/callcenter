$(function($){
var date = new Date();
var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
//设置时间在当前时间之前
var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
$("input[name='s_stime']").attr('onClick',attval);
$("input[name='f_time']").attr('onClick',attval);
$("input[name='birthday']").attr('onClick',attval);
});
//对时间得先后顺序做限制
function beforeSubmit_(){
	
	//出生日期
    var birthday = $("#birthday").val();
    var birth=new Date(birthday.replace("-", "/").replace("-", "/")); 
    //任职开始时间
    var s_stime = $("#s_stime").val();
    var start=new Date(s_stime.replace("-", "/").replace("-", "/")); 
    //任职结束时间
    var f_time = $("#f_time").val();
    var stop=new Date(f_time.replace("-", "/").replace("-", "/"));
    
    if(birth > start ) {
    	   $("#birthday").next().next().remove();
           $("#birthday").next('span').text( '出生日期不能晚于任职开始时间！');
           $("#birthday").next('span').attr("class","Validform_checktip Validform_wrong");
           return false;
     } else {
     	$("#birthday").next('span').attr("class","Validform_checktip Validform_right");
     	$("#birthday").next('span').removeAttr("style");
     }

    if(start > stop ) {
   	   $("#s_stime").next().next().remove();
          $("#s_stime").next('span').text( '任职开始时间不能晚于任职结束时间！');
          $("#s_stime").next('span').attr("class","Validform_checktip Validform_wrong");
          return false;
    } else {
    	$("#s_stime").next('span').attr("class","Validform_checktip Validform_right");
    	$("#s_stime").next('span').removeAttr("style");
    }
    
    return true;
}