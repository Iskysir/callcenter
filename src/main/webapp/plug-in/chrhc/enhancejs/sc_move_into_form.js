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
	
//	设置打印按钮，在人口信息中打开相应编辑时会显示
	if(window.location.search.indexOf("print=1") != -1){
		$("#buttonPanel").find("div").empty("button");
		$("#buttonPanel").find("div").append('<button type="button" class="btn btn-sure" onclick="prev()">打印</button>');
		$("#buttonPanel").find("div").append('<button type="button" class="btn btn btn-cancel" onclick="closeCurrentTab()">关闭</button>');
	}

});
//function prev(){
//	
//	if($('#formobj').Validform().check()){
//		$.post(
//				$("#formobj").attr("action"),
//				$("#formobj").serialize() ,
//			    function(data) {
//					addOneTab("打印户口迁入证明", "scCerTemplateController.do?tempPrint&id=" + document.getElementsByName("id")[0].value + "&tableName=" + document.getElementsByName("tableName")[0].value);
//		});			
//	}
//
//	
//}
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
					reporturl= reporturl+"statement/户口迁入证明.cpt&id="+id+"&zip="+zip+"&d="+myt;
					parent.parent.addTab("打印户口迁入证明", encodeURI(reporturl));
					//parent.parent.addTab("打印户口迁入证明", "scCerTemplateController.do?tempPrint&id=" + id + "&tableName=sc_move_into");
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
	
});