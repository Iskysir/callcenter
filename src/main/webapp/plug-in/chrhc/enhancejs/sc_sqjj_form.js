var urlpath = getUrlData();
init();
function init(){
	 // GIS编辑时，GIS信息不可更改
	if(urlpath.layerId != null && urlpath.layerId != "undefined" && urlpath.layerId != ""){
		$("#cleargis").hide();
	}
	
	$("#sqbh,#ssq,#ssjdxz").attr("readonly",true);
	
	
	
	var date = new Date();
	var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	//设置时间在当前时间之前
	var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})"
	$("input[name='clsj']").attr('onClick',attval);
	$("input[name='kpsj']").attr('onClick',attval);
	$("input[name='lssj']").attr('onClick',attval);
	$("input[name='hxsj']").attr('onClick',attval);
	$("input[name='sysj']").attr('onClick',attval);
	$("input[name='wmsj']").attr('onClick',attval);
	
}