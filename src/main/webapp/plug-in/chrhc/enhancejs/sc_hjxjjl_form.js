$(document).ready(function(){
    
	 var date = new Date();
	 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	 //设置时间在当前时间之前
	 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
	 $("input[name='electiontime']").attr('onClick',attval);
})//document 加载

function beforeSubmit_(){

     var cnum = parseInt($("#candidatenum").val());
     var tnum = parseInt($("#turnout").val());
     var fnum = parseInt($("#failnum").val());

     if(cnum < tnum || cnum < fnum) {
           $("#candidatenum").parent().find('span.Validform_checktip').text( '参选人数不能小于当选人数或者落选人数！').addClass("Validform_wrong").css("display","block");
           return false;
     } else if(cnum < (tnum+fnum) ){
             $("#candidatenum").parent().find('span.Validform_checktip').text( '参选人数不能小于当选人数和落选人数之和！').addClass("Validform_wrong").css("display","block");
             return false;
     }  else {
    	$("#candidatenum").parent().find('span.Validform_checktip').removeClass("Validform_checktip Validform_right");
    }
     
     return true;
}