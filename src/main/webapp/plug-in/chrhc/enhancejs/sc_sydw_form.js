$(document).ready(function(){
	var id=$("input[name='id'][type='hidden']").val();
	if(!id){
		//是否参照公务员法
	    $("input[type=radio][name='isgovern'][value='yes']").attr("checked",true); 
	  	$("input[type=radio][name='isgovern'][value='yes']").parent().addClass("checked",true);
	}
    
})