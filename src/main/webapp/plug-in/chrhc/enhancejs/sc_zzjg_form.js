function beforeSubmit_(){
	
	//邮箱
	var szMail = $("#mailaddress").val();
    
    var szReg=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
    if(szMail != ""){
    	if(!szReg.test(szMail)){
            $("#mailaddress").parent().find('span.Validform_checktip').text( '邮箱格式不正确，请重新输入！').addClass("Validform_checktip Validform_wrong");
            return false;
        }else{
        	$("#mailaddress").parent().find('span.Validform_checktip').removeClass("Validform_checktip Validform_right");
        }

    }
    
    return true;
}
