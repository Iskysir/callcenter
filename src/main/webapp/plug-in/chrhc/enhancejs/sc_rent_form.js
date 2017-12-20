$(function($){
var date = new Date();
var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
//设置时间为今天
$("input[name='fill_in_date']").val(date1);
var cObj = document.getElementById("fill_in_date");
cObj.setAttribute("readOnly",'true');
var cObj1 = document.getElementById("code");
cObj1.setAttribute("readOnly",'true');

//设置时间在当前时间之前
var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
$("input[name='rent_sta_date']").attr('onClick',attval);

//if(window.location.search.indexOf("print=1") != -1){
//	$("a[class='dataprve']").hide();
//	$("a[class='datanext']").hide();
//	$("#mainbox").append('<div class="an_div" style="margin-top:542px;padding-left: 110px; padding-bottom: 10px;"><button type="button" class="btn btn-sure" onclick="prev()">打印</button><button type="button" class="btn btn btn-cancel" onclick="closeCurrentTab()">关闭</button></div>');

//}
});
function prev(){
	
	if($('#formobj').Validform().check()){
		var id = $("input[name='id']").val();
		$(":input").attr("disabled",false);
		$.post(
				$("#formobj").attr("action"),
				$("#formobj").serialize() ,
				
			    function(data) {
					var myDate = new Date();
					var myt=myDate.getMilliseconds();
					var reporturl =window.top.PROVREPORTURL;
					var zip = window.top.ZIP;
					reporturl= reporturl+"statement/出租户证明.cpt&id="+id+"&zip="+zip+"&d="+myt;
					parent.parent.addTab("打印出租户证明", encodeURI(reporturl));
					//parent.parent.addTab("打印出租户证明", "scCerTemplateController.do?tempPrint&id=" + id + "&tableName=sc_rent");
		});	
		$(":input").attr("disabled",true);
	}

	
}

$(document).ready(function(){
	$("#reset_btn").hide();
	if(window.location.search.indexOf("print=1") != -1){
		$("#save_btn").hide();
		$("#back_btn").hide();
		$(".dataprve").hide();
		$(".datanext").hide();
		$(".tabs_btn_div").append(' <button class="btn printbtn-new" id="reset_btn" type="button" onclick="prev()"></button>');
	}
	//添加提交之前执行方法  暂时不用
	//$("form[name='formobj']").attr("onsubmit","beforeSubmit_");	
});
//提交之前的操作
function beforeSubmit_noused(){
    var rent_name = $("#rent_name").val();
    var rent_idcard = $("#rent_idcard").val();
    var master_name = $("#master_name").val();
    var obligatea = $("#obligatea").val();
    var obligatec = $("#obligatec").val();
    
    var that=true;
    $.ajax({
	    url:"scRentController.do?beforeSubmit",
	    data:{rentName:rent_name,rentIdcard:rent_idcard,masterName :master_name,obligatea:obligatea,obligatec:obligatec },
	    type:"Post",
	    dataType:"json",
	    async:false,
	    success:function(data){  
			if(!data.success){
				//$.messager.alert('提示',data.msg);
				tip(data.msg);
				that = false;
				return false;
			}		
	    },
		error:function(data){
			tip(data.msg);
			that = false;
			return false;
		}
	});
	return that;
}