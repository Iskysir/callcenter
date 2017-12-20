$(document).ready(function(){
    
	 var date = new Date();
	 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	 //填表时间设置在当前时间之前
	 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
	 
	 $("input[name='date']").attr('onClick',attval);
	// $("input[name='fillingtime']").attr('onClick',attval);
	 
	 var d = new Date();
	 var str = getNowDateFormate(d);
		
		if ($("#fillingtime").val()) {
			$("#fillingtime").attr('readonly',true);
		} else {
			$("#fillingtime").val(str).attr('readOnly','readOnly');
		}

})//document 加载

function getNowDateFormate(now){
	 var strYear = now.getFullYear();
	 var strMonth = now.getMonth().toString();
	 var strDay = now.getDate().toString();
	 if(strMonth.length<2){
		 strMonth = "0"+String(parseInt(strMonth)+1);
	 }
	 if(strDay.length<2){
		 strDay = "0"+strDay;
	 }
	 
	 displayTime = strYear +"-"+strMonth+"-"+strDay;  
	 /*if(strTime.substring(5,6)<10){
	   var displayTime = strTime.substring(0,4)+"-0"+strTime.substring(5,6)+"-"+strTime.substring(7,9);
	  }else{	   
	   var displayTime = strTime.substring(0,4)+"-"+strTime.substring(5,7)+"-"+strTime.substring(8,10);
	 };*/
	 return displayTime;
	}