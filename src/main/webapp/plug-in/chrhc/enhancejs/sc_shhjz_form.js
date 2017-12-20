$(document).ready(
		function(){
			var date = new Date();
			 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
			 //设置时间在当前时间之前
			 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"',onpicked:function(){(this).blur();}})";
			 $("input[name='don_datetime']").attr('onClick',attval);
		
}); 