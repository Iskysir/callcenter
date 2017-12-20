$(function($){
var date = new Date();
var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
//设置时间在当前时间之前
var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
$("input[name='army_time']").attr('onClick',attval);
$("input[name='discharge_time']").attr('onClick',attval);
});
//对时间得先后顺序做限制
function beforeSubmit_(){
	
	//入伍时间
    var army_time = $("#army_time").val();
    var start=new Date(army_time.replace("-", "/").replace("-", "/")); 
    //退伍时间
    var discharge_time = $("#discharge_time").val();
    var stop=new Date(discharge_time.replace("-", "/").replace("-", "/")); 

    if(start > stop ) {
   	   $("#army_time").next().next().remove();
          $("#army_time").next('span').text( '入伍时间不能晚于退伍时间！');
          $("#army_time").next('span').attr("class","Validform_checktip Validform_wrong");
          return false;
    } else {
    	$("#army_time").next('span').attr("class","Validform_checktip Validform_right");
    	$("#army_time").next('span').removeAttr("style");
    }
    
    return true;
}