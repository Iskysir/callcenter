$(function($){
	$("#xm").attr('readonly',false);
	var date = new Date();
	var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})"
	$("input[name='rq']").attr('onClick',attval);
})