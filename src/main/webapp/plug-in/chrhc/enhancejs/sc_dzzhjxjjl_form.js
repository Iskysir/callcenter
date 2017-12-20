$(document).ready(function(){
    
	 var date = new Date();
	 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	 //设置时间在当前时间之前
	 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
	 $("input[name='electiontime']").attr('onClick',attval);
})//document 加载

function beforeSubmit_(){

     var cnum = parseInt($("#turnoutnum").val()); //参选人数
     var tnum = parseInt($("#electednum").val());  //当选人数

     if(cnum < tnum) {
	     $("#turnoutnum").parent().find('span.Validform_checktip').text('参选人数不能小于当选人数！').addClass("Validform_wrong").css("display","block");
	     return false;
     } else {
    	$("#turnoutnum").parent().find('span.Validform_checktip').removeClass("Validform_checktip Validform_right");
    }
     return true;
}