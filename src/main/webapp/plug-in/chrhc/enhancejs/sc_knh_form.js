$(function($){
var date = new Date();
var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
//设置时间在当前时间之前
var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";

$("input[name='help_starttime']").attr('onClick',attval);
//$("input[name='help_stoptime']").attr('onClick',attval);

});
//对时间得先后顺序做限制
function beforeSubmit_(){
	
	//帮困开始时间
    var help_starttime = $("#help_starttime").val();
    var start=new Date(help_starttime.replace("-", "/").replace("-", "/")); 
    //帮困结束时间
    var help_stoptime = $("#help_stoptime").val();
    var stop=new Date(help_stoptime.replace("-", "/").replace("-", "/")); 

    if(start > stop ) {
   	   $("#help_starttime").next().next().remove();
          $("#help_starttime").next('span').text( '帮困开始时间不能晚于结束时间！');
          $("#help_starttime").next('span').attr("class","Validform_checktip Validform_wrong");
          return false;
    } else {
    	$("#help_starttime").next('span').attr("class","Validform_checktip Validform_right");
    	$("#help_starttime").next('span').removeAttr("style");
    }
    
    return true;
}