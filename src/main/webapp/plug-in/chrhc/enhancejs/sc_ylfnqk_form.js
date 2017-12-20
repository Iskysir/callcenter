$(function(){
 var date = new Date();
 var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
 //设置时间在当前时间之前
 var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
 $("input[name='jksj']").attr('onClick',attval);
 $("input[name='lqlzsj']").attr('onClick',attval);
 $("input[name='yhzsj']").attr('onClick',attval);
 $("input[name='dsznzsj']").attr('onClick',attval);
 $("input[name='ehzsj']").attr('onClick',attval);
});
$(document).ready(function(){
	var id=$("input[name='id'][type='hidden']").val();
	if(!id){
		//是否志愿者默认值设置
	    $("input[type=radio][name='hf'][value='shi']").attr("checked",true); 
		$("input[type=radio][name='hf'][value='shi']").parent().addClass("checked",true);  
		$("input[type=radio][name='gllx'][value='cz']").attr("checked",true); 
		$("input[type=radio][name='gllx'][value='cz']").parent().addClass("checked",true);  
	}
    
});//document 加载

function beforeSubmit_(){
	
	//建卡时间
    var jksj = $("#jksj").val();
    var jk=new Date(jksj.replace("-", "/").replace("-", "/")); 
    //领取两证时间
    var lqlzsj = $("#lqlzsj").val();
    var lq=new Date(lqlzsj.replace("-", "/").replace("-", "/")); 
	//独生子女证时间
    var dsznzsj = $("#dsznzsj").val();
    var ds=new Date(dsznzsj.replace("-", "/").replace("-", "/")); 
    //一孩证时间
    var yhzsj = $("#yhzsj").val();
    var yh=new Date(yhzsj.replace("-", "/").replace("-", "/")); 
	//二孩证时间
    var ehzsj = $("#ehzsj").val();
    var eh=new Date(ehzsj.replace("-", "/").replace("-", "/")); 
    if(jk > lq ) {
   	   $("#jksj").next().next().remove();
          $("#jksj").next('span').text( '建卡时间不能晚于领取两证时间！');
          $("#jksj").next('span').attr("class","Validform_checktip Validform_wrong");
          return false;
    } else {
    	$("#jksj").next('span').attr("class","Validform_checktip Validform_right");
    	$("#jksj").next('span').removeAttr("style");
    }
    if(lq > ds ) {
    	   $("#lqlzsj").next().next().remove();
           $("#lqlzsj").next('span').text( '领取两证时间不能晚于独生子女证时间！');
           $("#lqlzsj").next('span').attr("class","Validform_checktip Validform_wrong");
           return false;
     } else {
     	$("#lqlzsj").next('span').attr("class","Validform_checktip Validform_right");
     	$("#lqlzsj").next('span').removeAttr("style");
     }
    if(yh > eh ) {
    	   $("#yhzsj").next().next().remove();
           $("#yhzsj").next('span').text( '建卡时间不能晚于领取两证时间！');
           $("#yhzsj").next('span').attr("class","Validform_checktip Validform_wrong");
           return false;
     } else {
     	$("#yhzsj").next('span').attr("class","Validform_checktip Validform_right");
     	$("#yhzsj").next('span').removeAttr("style");
     }
    
    return true;
}