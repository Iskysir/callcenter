$(function(){
	var date = new Date();
	var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	//设置时间为今天
	$("input[name='fill_in_date']").val(date1);
	//日期设置为只读
	var cObj = document.getElementById("fill_in_date");
	cObj.setAttribute("readOnly",'true');
	var cObj1 = document.getElementById("code");
	cObj1.setAttribute("readOnly",'true');
	
	var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})";
	$("input[name='birthday_date']").attr('onClick',attval);
	$("input[name='marry_date']").attr('onClick',attval);
	$("input[name='child_birthday']").attr('onClick',attval);
});

function prev() {
	if ($('#formobj').Validform().check()) {

		var id = $("input[name='id']").val();
		$(":input").attr("disabled", false);
		var is = beforeSubmit_();
		if (is) {
			$.post(
					$("#formobj").attr("action"),
					$("#formobj").serialize() ,
					
				    function(data) {
						var myDate = new Date();
						var myt=myDate.getMilliseconds();
						var reporturl =window.top.PROVREPORTURL;
						var zip = window.top.ZIP;
						reporturl= reporturl+"statement/独生子女证补办.cpt&id="+id+"&zip="+zip+"&d="+myt;
						parent.parent.addTab("打印独生子女证补办", encodeURI(reporturl));
						//parent.parent.addTab("打印独生子女补正证明", "scCerTemplateController.do?tempPrint&id=" + id + "&tableName=sc_prove");
			});
		}
		$(":input").attr("disabled", true);
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
	
	//添加提交之前执行方法
	$("form[name='formobj']").attr("onsubmit","beforeSubmit_");	
});

//提交之前的操作
function beforeSubmit_(){
   
    var that=true;
    $.ajax({
	    url:"scProveController.do?beforeSubmit",
	    data: $("#formobj").serialize(),
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



