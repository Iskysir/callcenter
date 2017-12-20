$(document).ready(function(){
	var id=$("input[name='id'][type='hidden']").val();
	if(!id){
		//是否参加活动默认值设置
	    $("input[type=radio][name='isjoinactivity'][value='yes']").attr("checked",true); 
		$("input[type=radio][name='isjoinactivity'][value='yes']").parent().addClass("checked",true);  

	    //是否志愿者默认值设置
	    $("input[type=radio][name='isvolunteer'][value='yes']").attr("checked",true); 
		$("input[type=radio][name='isvolunteer'][value='yes']").parent().addClass("checked",true);  
	}
	var date = new Date();
	 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	 //设置时间在当前时间之前
	 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
	 $("input[name='birthday']").attr('onClick',attval);
	 //入党时间 jointime
	 $("input[name='jointime']").attr('onClick',attval);
	 //转入时间
	 $("input[name='releasetime']").attr('onClick',attval);
	 
});
