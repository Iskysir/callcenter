
var enter = "";
var landless = "";
var oldins = "";
var otherins = "";
var enterdate = "";
var landlessdate = "";
var oldinsdate = "";
var otherinsdate = "";
$(document).ready(function(){

	var id=$("input[name='id'][type='hidden']").val();
	if(id) {
		
		//setTimeout(sleep(3000),1000);
		 //sleep(3000);
		 enter = $("input[type=radio][name='enterpriseins'][checked='checked']").val();
		 landless = $("input[type=radio][name='landlessins'][checked='checked']").val();
		 oldins = $("input[type=radio][name='oldins'][checked='checked']").val();
		 otherins = $("input[type=radio][name='otherins'][checked='checked']").val();

		 enterdate = $("#enterprisedate").val();
		 landlessdate = $("#landlessinsdate").val();
		 oldinsdate = $("#oldinsdate").val();
		 otherinsdate = $("#otherinsdate").val();
	}
	
	$("input[type=radio][name='enterpriseins']").parent().parent().parent().remove();
	$("input[type=radio][name='landlessins']").parent().parent().parent().parent().parent().remove();
	$("input[type=radio][name='oldins']").parent().parent().parent().parent().parent().remove();
	$("input[type=radio][name='otherins']").parent().parent().parent().parent().parent().remove();
		
	var a = $("#insamount").parent().parent().parent().next();
	
	 a.load("demo/endowment.html","",function(){
		 
		 var date = new Date();
		 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		 //设置时间在当前时间之前
		 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
		 //insureddate 参保日期
		 $("input[name='insureddate']").attr('onClick',attval);
		//birthday 出生日期
		 $("input[name='birthday']").attr('onClick',attval);
		//enterprisedate 企业养老保险起始时间
		 $("input[name='enterprisedate']").attr('onClick',attval);
		//landlessinsdate 被征地保障起始时间
		 $("input[name='landlessinsdate']").attr('onClick',attval);
		//oldinsdate 老农保起始时间
		 $("input[name='oldinsdate']").attr('onClick',attval);
		 //otherinsdate 其他起始时间
		 $("input[name='otherinsdate']").attr('onClick',attval);
		 
		 var id=$("input[name='id'][type='hidden']").val();
		if(!id){
			//是否默认值设置
			//企业职工基本养老保险
			$("input[type=radio][name='enterpriseins'][value='no']").attr("checked",true); 
			$("input[type=radio][name='enterpriseins'][value='no']").parent().addClass("checked",true);
			$("#enterprisedate").attr("disabled",true);
			
			//被征地农民社会保障
			$("input[type=radio][name='landlessins'][value='no']").attr("checked",true); 
			$("input[type=radio][name='landlessins'][value='no']").parent().addClass("checked",true);
			$("#landlessinsdate").attr("disabled",true);
			
			//老农保
			$("input[type=radio][name='oldins'][value='no']").attr("checked",true); 
			$("input[type=radio][name='oldins'][value='no']").parent().addClass("checked",true);
			$("#oldinsdate").attr("disabled",true);
			
			//其他保险
			$("input[type=radio][name='otherins'][value='no']").attr("checked",true); 
			$("input[type=radio][name='otherins'][value='no']").parent().addClass("checked",true); 
			$("#otherinsdate").attr("disabled",true);
		} else{
			//企业职工基本养老保险
			$("input[type=radio][name='enterpriseins'][value='"+ enter +"']").attr("checked",true); 
			$("input[type=radio][name='enterpriseins'][value='"+ enter +"']").parent().addClass("checked",true);

			$("#enterprisedate").val(enterdate);
			
			//被征地农民社会保障
			$("input[type=radio][name='landlessins'][value='"+ landless +"']").attr("checked",true); 
			$("input[type=radio][name='landlessins'][value='"+ landless +"']").parent().addClass("checked",true);
			
			$("#landlessinsdate").val(landlessdate);
			
			//老农保
			$("input[type=radio][name='oldins'][value='"+ oldins +"']").attr("checked",true); 
			$("input[type=radio][name='oldins'][value='"+ oldins +"']").parent().addClass("checked",true);
			
			$("#oldinsdate").val(oldinsdate);
			
			//其他保险
			$("input[type=radio][name='otherins'][value='"+ otherins +"']").attr("checked",true); 
			$("input[type=radio][name='otherins'][value='"+ otherins +"']").parent().addClass("checked",true); 
			
			$("#otherinsdate").val(otherinsdate);
		}

		//企业职工基本养老保险
		 var enterradio = document.getElementsByName("enterpriseins");
		 for(var i = 0;i < enterradio.length;i++){
		    if(enterradio[i].checked && enterradio[i].value == 'no'){
		    	$("#enterprisedate").attr("disabled",true);
		    } else {
		    	$("#enterprisedate").attr("disabled",false);
		    }
		 }
		 
		//被征地农民社会保障
		 var landlessradio = document.getElementsByName("landlessins");
		 for(var i = 0;i < landlessradio.length;i++){
		    if(landlessradio[i].checked && landlessradio[i].value == 'no'){
		    	$("#landlessinsdate").attr("disabled",true);
		    } else {
		    	$("#landlessinsdate").attr("disabled",false);
		    }
		 }
		 
		//老农保
		 var oldinsradio = document.getElementsByName("oldins");
		 for(var i = 0;i < oldinsradio.length;i++){
		    if(oldinsradio[i].checked && oldinsradio[i].value == 'no'){
		    	$("#oldinsdate").attr("disabled",true);
		    } else {
		    	$("#oldinsdate").attr("disabled",false);
		    }
		 }
		 
		//其他保险
		 var otherinsradio = document.getElementsByName("otherins");
		 for(var i = 0;i < otherinsradio.length;i++){
		    if(otherinsradio[i].checked && otherinsradio[i].value == 'no'){
		    	$("#otherinsdate").attr("disabled",true);
		    } else {
		    	$("#otherinsdate").attr("disabled",false);
		    }
		 }


		//企业职工基本养老保险
		$("input[type=radio][name='enterpriseins']").on('ifChecked', function(event){  
			var $selectedvalue = $("input[name='enterpriseins']:checked").val();
			
			if ($selectedvalue == 'no') {
				$("#enterprisedate").attr("disabled",true);
			} else {
				$("#enterprisedate").attr("disabled",false);
			}
			  
		}); 
		//被征地农民社会保障
		$("input[type=radio][name='landlessins']").on('ifChecked', function(event){  
			
			var $selectedvalue = $("input[name='landlessins']:checked").val();
			
			if ($selectedvalue == 'no') {
				$("#landlessinsdate").attr("disabled",true);
			} else {
				$("#landlessinsdate").attr("disabled",false);
			}
			  
		}); 
		//老农保
		$("input[type=radio][name='oldins']").on('ifChecked', function(event){  
			
			var $selectedvalue = $("input[name='oldins']:checked").val();
			
			if ($selectedvalue == 'no') {
				$("#oldinsdate").attr("disabled",true);
			} else {
				$("#oldinsdate").attr("disabled",false);
			}
			  
		}); 
		//其他保险
		$("input[type=radio][name='otherins']").on('ifChecked', function(event){  
			
			var $selectedvalue = $("input[name='otherins']:checked").val();
			
			if ($selectedvalue == 'no') {
				$("#otherinsdate").attr("disabled",true);
			} else {
				$("#otherinsdate").attr("disabled",false);
			}
			  
		}); 
	 });
	 
});//document 加载

function sleep(numberMillis) {
	var now = new Date();
	var exitTime = now.getTime() + numberMillis;
	while (true) {
		now = new Date();
		if (now.getTime() > exitTime)
		return;
	}
}