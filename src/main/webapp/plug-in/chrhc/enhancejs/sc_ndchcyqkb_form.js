$(function($){
var date = new Date();
var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
//设置时间在当前时间之前
var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
$("input[name='f_dylkssj']").attr('onClick',attval);
$("input[name='f_dyljssj']").attr('onClick',attval);
$("input[name='s_dylkssj']").attr('onClick',attval);
$("input[name='s_dyljssj']").attr('onClick',attval);
$("input[name='t_dylkssj']").attr('onClick',attval);
$("input[name='t_dyljssj']").attr('onClick',attval);
});
//对时间得先后顺序做限制
function beforeSubmit_(){
	
	//一轮开始时间
    var f_dylkssj = $("#f_dylkssj").val();
    var fs=new Date(f_dylkssj.replace("-", "/").replace("-", "/")); 
    //一轮结束时间
    var f_dyljssj = $("#f_dyljssj").val();
    var ff=new Date(f_dyljssj.replace("-", "/").replace("-", "/")); 
  //二轮开始时间
    var s_dylkssj = $("#s_dylkssj").val();
    var ss=new Date(s_dylkssj.replace("-", "/").replace("-", "/")); 
    //二轮结束时间
    var s_dyljssj = $("#s_dyljssj").val();
    var sf=new Date(s_dyljssj.replace("-", "/").replace("-", "/")); 
  //三轮开始时间
    var t_dylkssj = $("#t_dylkssj").val();
    var ts=new Date(t_dylkssj.replace("-", "/").replace("-", "/")); 
    //三轮结束时间
    var t_dyljssj = $("#t_dyljssj").val();
    var tf=new Date(t_dyljssj.replace("-", "/").replace("-", "/")); 
    if(fs > ff ) {
    	   $("#f_dylkssj").next().next().remove();
           $("#f_dylkssj").next('span').text( '开始时间不能晚于结束时间！');
           $("#f_dylkssj").next('span').attr("class","Validform_checktip Validform_wrong");
           return false;
     } else {
     	$("#f_dylkssj").next('span').attr("class","Validform_checktip Validform_right");
     	$("#f_dylkssj").next('span').removeAttr("style");
     }

    if(ff > ss ) {
   	   $("#s_dylkssj").next().next().remove();
          $("#s_dylkssj").next('span').text( '二轮开始时间要晚于一轮结束时间！');
          $("#s_dylkssj").next('span').attr("class","Validform_checktip Validform_wrong");
          return false;
    } else {
    	$("#s_dylkssj").next('span').attr("class","Validform_checktip Validform_right");
    	$("#s_dylkssj").next('span').removeAttr("style");
    }
    if(ss > sf ) {
 	   $("#s_dylkssj").next().next().remove();
        $("#s_dylkssj").next('span').text( '开始时间不能晚于结束时间！');
        $("#s_dylkssj").next('span').attr("class","Validform_checktip Validform_wrong");
        return false;
	  } else {
	  	$("#s_dylkssj").next('span').attr("class","Validform_checktip Validform_right");
	  	$("#s_dylkssj").next('span').removeAttr("style");
	  }
	
	 if(sf > ts ) {
		   $("#t_dylkssj").next().next().remove();
	       $("#t_dylkssj").next('span').text( '三轮开始时间要晚于二轮结束时间！');
	       $("#t_dylkssj").next('span').attr("class","Validform_checktip Validform_wrong");
	       return false;
	 } else {
	 	$("#t_dylkssj").next('span').attr("class","Validform_checktip Validform_right");
	 	$("#t_dylkssj").next('span').removeAttr("style");
	 }
    
	 if(ts > tf ) {
	 	   $("#t_dylkssj").next().next().remove();
	        $("#t_dylkssj").next('span').text( '开始时间不能晚于结束时间！');
	        $("#t_dylkssj").next('span').attr("class","Validform_checktip Validform_wrong");
	        return false;
		  } else {
		  	$("#t_dylkssj").next('span').attr("class","Validform_checktip Validform_right");
		  	$("#t_dylkssj").next('span').removeAttr("style");
		  }
    return true;
}