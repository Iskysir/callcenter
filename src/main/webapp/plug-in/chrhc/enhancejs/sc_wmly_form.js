$(document).ready(
		function(){
			var date = new Date();
			 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
			 //设置时间在当前时间之前
			 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
			 $("input[name='select_datetime']").attr('onClick',attval);
});
function beforeSubmit_(){
      var house = $("#house_no").val();
      var address = $("#address ").val();
      var  id= $("input[name='id'][type='hidden']").val();
      var that=true;
	$.ajax({
	    url:"scWmlyController.do?doCheckCom",
	    data:{id:id,houseNo:house,address :address },
	     type:"Post",
	    dataType:"json",
	    async:false,
	    success:function(data){  
			if(!data.success){
				$.messager.alert('提示',data.msg);
				that = false;
				return false;
			}		
	    },
		error:function(data){
			$.messager.alert('错误 ',data.msg);
			return false;
		}
	});
	return that;
}