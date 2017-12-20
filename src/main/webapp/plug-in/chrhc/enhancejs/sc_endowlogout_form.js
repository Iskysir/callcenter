$(document).ready(function(){

	var date = new Date();
	 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	 //设置时间在当前时间之前
	 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
	 //insureddate 注销日期
	 $("input[name='cancelldate']").attr('onClick',attval);
	//birthday 受益人出生日期
	 $("input[name='benibirthday']").attr('onClick',attval);
	 
	 $("input[name='code']").attr("readonly","readonly");
	 
	 var id=$("input[name='id'][type='hidden']").val();
	if(!id){
		 var getParam = getUrlData();
		 if(getParam.flag == "1"){
			 $("input[name='name']").attr("readonly","readonly");
		 }
		 
		 $("input[name='endowid']").val(getParam.endowid);
		 $("input[name='code']").val(getParam.code);
		 $("input[name='name']").val(getParam.name);
		 $("input[name='pidcard']").val(getParam.pidcard);
	}
})//document 加载

