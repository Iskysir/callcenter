$(document).ready(
		function(){
			var date = new Date();
			 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
			 //设置时间在当前时间之前
			 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
			 $("input[name='birthday']").attr('onClick',attval);
			 $("input[name='marry_date']").attr('onClick',attval);
			 $("input[name='bear_date']").attr('onClick',attval); 
			 $("input[name='cert_date']").attr('onClick',attval); 
}); 