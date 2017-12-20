$(document).ready(
		function(){
			var date = new Date();
			 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
			 //设置时间在当前时间之前
			 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"',onpicked:function(){(this).blur();}})";
			 $("input[name='don_date']").attr('onClick',attval);
});
/**
 * 回调方法，根据身份证号计算年龄
 * @param selected
 */
function getAgeCallBack(selected){
	getAgeByCertId(selected.sfzh,"age");
}