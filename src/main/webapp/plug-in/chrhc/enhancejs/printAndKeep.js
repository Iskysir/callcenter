function print(){
	alert(11111);
	if(window.location.search.indexOf("print=1") != -1){
		$("#buttonPanel").find("div").append('<button type="button" class="btn btn btn-cancel" onclick="prev()">打印</button>');
	}

}
function prev(){

	$.post(
			$("#formobj").attr("action"),
			$("#formobj").serialize() ,
		    function(data) {
				addOneTab("预览", "scCerTemplateController.do?tempPrint&id=" + document.getElementsByName("id")[0].value + "&tableName=" + document.getElementsByName("tableName")[0].value);
	});
}