$(function($){
var date = new Date();
var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
//设置时间在当前时间之前
//var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"',minDate:'"+ date1+"'})";
//$("input[name='djsj']").attr('onClick',attval);
$("input[name='djsj']").val(date1);
$("input[name='djsj']").attr("readonly",true);
})