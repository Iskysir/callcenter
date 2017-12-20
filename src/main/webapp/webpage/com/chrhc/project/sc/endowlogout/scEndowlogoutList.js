function linkformatter(value,rec,index){
	var href='';
	var tabname='养老保险注销记录';
	var url = 'scEndowlogoutController.do?goUpdate&id='+rec.id;
	
	href += "<a href = '#' onclick=\"addOneTab('"+tabname+"','"+ url+"')\" ><u>"+value+"</u></a>";
	return href;
}