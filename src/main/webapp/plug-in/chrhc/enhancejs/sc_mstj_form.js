$(document).ready(
		function(){
			var date = new Date();
			 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
			 //设置时间在当前时间之前
			 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
			 $("input[name='apply_datetime']").attr('onClick',attval);
			 $("input[name='fin_date']").attr('onClick',attval);
});