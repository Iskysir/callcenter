$(document).ready(
		function(){
			var date = new Date();
			 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
			 //设置时间在当前时间之前
			 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
			 $("input[name='reg_datetime']").attr('onClick',attval);
			 //图书管理编号不允许编辑
			 $("input[name='code']").attr("readonly","readonly");
});