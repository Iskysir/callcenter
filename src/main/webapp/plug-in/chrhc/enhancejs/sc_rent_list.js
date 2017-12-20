
function preview(id) {
	var myDate = new Date();
	var myt=myDate.getMilliseconds();
	var reporturl =window.top.PROVREPORTURL;
	var zip = window.top.ZIP;
	reporturl= reporturl+"statement/出租户证明.cpt&id="+id+"&zip="+zip+"&d="+myt;
	addOneTab("预览", encodeURI(reporturl));

}
	
