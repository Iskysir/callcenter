$(function($){
var date = new Date();
var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
//设置时间在当前时间之前
var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
$("input[name='birthday']").attr('onClick',attval);
$("input[name='work_date']").attr('onClick',attval);
$("input[name='army_date']").attr('onClick',attval);
debugger;
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

});
//对时间得先后顺序做限制
function beforeSubmit_(){
	
	//出生日期 birthday
    var birthday = $("#birthday").val();
    var birth=new Date(birthday.replace("-", "/").replace("-", "/")); 
    //入伍或参加工作时间
    var work_date = $("#work_date").val();
    var work=new Date(work_date.replace("-", "/").replace("-", "/")); 
    //退伍或退休时间
    var army_date = $("#army_date").val();
    var army=new Date(army_date.replace("-", "/").replace("-", "/")); 

    if(birth > work ) {
   	   $("#birthday").next().next().remove();
          $("#birthday").next('span').text( '出生日期不能晚于入伍或参加工作时间！');
          $("#birthday").next('span').attr("class","Validform_checktip Validform_wrong");
          return false;
    } else {
    	$("#birthday").next('span').attr("class","Validform_checktip Validform_right");
    	$("#birthday").next('span').removeAttr("style");
    }
    if( work > army) {
    	   $("#work_date").next().next().remove();
           $("#work_date").next('span').text( '入伍或参加工作时间不能晚于退伍或退休时间！');
           $("#work_date").next('span').attr("class","Validform_checktip Validform_wrong");
           return false;
     } else{
    	 $("#work_date").next('span').attr("class","Validform_checktip Validform_right");
    	 $("#work_date").next('span').removeAttr("style");
     }
    
    return true;
}