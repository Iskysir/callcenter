
function preview(id) {
	
	var myDate = new Date();
	var myt=myDate.getMilliseconds();
	var reporturl =window.top.PROVREPORTURL;
	var zip = window.top.ZIP;
	//var rk_id =getSelected("id");
	//alert(rec.id);
	reporturl= reporturl+"statement/医疗救助申请暨审批表.cpt&rk_id="+id+"&zip="+zip+"&d="+myt;
	addOneTab("预览", encodeURI(reporturl));
	 //addOneTab("预览", "scCerTemplateController.do?tempPrint&id=" + id + "&tableName=" + tablename);
}
	
