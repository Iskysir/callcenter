
$(document).ready(function(){
	 var date = new Date();
	 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	 //设置时间在当前时间之前
	 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
	 var id=$("input[name='id'][type='hidden']").val();
	//最后年审时间
	 $("input[name='lastauidt']").attr('onClick',attval);
	if(!id){
		//是否登记办照默认值设置
	    $("input[type=radio][name='isregist'][value='yes']").attr("checked",true); 
		$("input[type=radio][name='isregist'][value='yes']").parent().addClass("checked",true);  
	}
});