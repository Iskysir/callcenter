

$(document).ready(function(){
	var id=$("input[name='id'][type='hidden']").val();
	if(!id){
		//是否志愿者默认值设置
	    $("input[type=radio][name='allowance'][value='shi']").attr("checked",true); 
		$("input[type=radio][name='allowance'][value='shi']").parent().addClass("checked",true);  
		$("input[type=radio][name='unemployment_insurance'][value='shi']").attr("checked",true); 
		$("input[type=radio][name='unemployment_insurance'][value='shi']").parent().addClass("checked",true);  
		$("input[type=radio][name='job_card'][value='shi']").attr("checked",true); 
		$("input[type=radio][name='job_card'][value='shi']").parent().addClass("checked",true);  
		$("input[type=radio][name='ill'][value='shi']").attr("checked",true); 
		$("input[type=radio][name='ill'][value='shi']").parent().addClass("checked",true);  
		$("input[type=radio][name='want_job'][value='shi']").attr("checked",true); 
		$("input[type=radio][name='want_job'][value='shi']").parent().addClass("checked",true);  
	}
    
	var date = new Date();
	var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	//设置时间在当前时间之前
	var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
	$("input[name='unemployment_date']").attr('onClick',attval);
	$("input[name='allowance_sta_time']").attr('onClick',attval);
	$("input[name='get_unemployment_date']").attr('onClick',attval);
	
});//document 加载