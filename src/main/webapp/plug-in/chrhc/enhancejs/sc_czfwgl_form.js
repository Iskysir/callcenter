$(document).ready(function(){
		var id=$("input[name='id'][type='hidden']").val();
		if(!id){
	        $("input[type=radio][name='is_duty'][value='shi']").attr("checked",true); 
	    	$("input[type=radio][name='is_duty'][value='shi']").parent().addClass("checked",true);  
		}
});