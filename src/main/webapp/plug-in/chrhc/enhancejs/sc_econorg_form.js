$(document).ready(function(){
	var date = new Date();
	 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	 //设置时间在当前时间之前
	 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
	 //经济组织成立日期
	 $("input[name='establishdate']").attr('onClick',attval);
	 //组织机构代码证颁证日期
	 $("input[name='certifiedate']").attr('onClick',attval);
	 //组织机构代码证废置日期
	 //$("input[name='nickdate']").attr('onClick',attval);
	 //组织机构代码证变更日期
	 $("input[name='changedate']").attr('onClick',attval);
})//document 加载