$(document).ready(function(){

	 $("#attendee").after('<a href="#" class="easyui-linkbutton  l-btn l-btn-plain" plain="true" icon="icon-redo" onClick="clearText()"><span class="l-btn-left"><span class="l-btn-text icon-redo l-btn-icon-left">清空</span></span></a>');

	 //活动时间设置
	 var date = new Date();
	 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	 //设置时间在当前时间之前
	 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
	 $("input[name='activitytime']").attr('onClick',attval);
	
})//document 加载

$("#attendee").click(function(){
	
	inputClick(this,'name','partymemberinfo','attendee=name','{"singleSelect":false}');
});

function clearText(){
	$("#attendee").val('');
}

function beforeSubmit_(){

    var cnum = parseInt($("#attendnum").val()); //参选人数
    var tnum = $("#attendee").val().split(",").length;

    if(cnum != tnum) {
   	   	//$("#attendnum").next().next().remove();
   	   	$("#attendnum").parent().find('span.Validform_checktip').text('参加人数与参加人员数量不相符，请确认！').addClass("Validform_wrong").css("display","block");
        //$("#attendnum").next('span').text( '参加人数与参加人员数量不相符，请确认！');
        //$("#attendnum").next('span').attr("class","Validform_checktip Validform_wrong");
        return false;
    }else {
    	$("#attendnum").parent().find('span.Validform_checktip').removeClass("Validform_checktip Validform_right");
    	//$("#attendnum").next('span').attr("class","Validform_checktip Validform_right");
    	//$("#attendnum").next('span').removeAttr("style");
    }
    return true;
}