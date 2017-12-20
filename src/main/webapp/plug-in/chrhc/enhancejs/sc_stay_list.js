
function preview(id) {
	
	var myDate = new Date();
	var myt=myDate.getMilliseconds();
	var reporturl =window.top.PROVREPORTURL;
	var zip = window.top.ZIP;
	reporturl= reporturl+"statement/居住证明.cpt&id="+id+"&zip="+zip+"&d="+myt;
	addOneTab("预览", encodeURI(reporturl));
	 //addOneTab("预览", "scCerTemplateController.do?tempPrint&id=" + id + "&tableName=" + tablename);
}
	