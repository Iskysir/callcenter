$(document).ready(function(){
	var date = new Date();
	 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	 //设置时间在当前时间之前
	 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
	 //insureddate 参保登记日期
	 $("input[name='indate']").attr('onClick',attval);
	//birthday 出生日期
	 $("input[name='birthday']").attr('onClick',attval);
	//enterprisedate 参保缴费日期
	 $("input[name='paydate']").attr('onClick',attval);
	
})//document 加载