$(document).ready(function(){


$("select[name='bigtype']").change(function(){

      var host = window.location.host;
      var pathname=window.location.pathname;
      var a = pathname.split('/');//.join(',');

     //alert($(this).text());
     // alert($("select[name='bigtype'] option[selected='selected']"));
    //原生js?
   var mySelect = document.getElementsByName('bigtype')[0];
     var selectedId = mySelect.selectedIndex;
      //清空?
        dellist($("select[name='smalltype']"));
            var re= new RegExp("^CY\S*?");
           //判断如果是餐饮大类则增加下级节点
             //if(re.test($("select[name='bigtype'] option[selected='selected']").val())){
                //   var o1="<option value='CY01'>小吃??/option>";
                   //$("select[name='smalltype']").append(o1);
              // }
             //ajax
             var url ="http://"+host +"/"+a[1]+ "/cgAutoListController.do?datagrid&configId=sc_humpt_zidian&&field=id,code,label&code="+mySelect.options[selectedId].value+"_*";
             $.get(url,function(data){
             var count = data.total;
       //  alert(count);
        var data_ =data.rows;
       // alert(data_);
      //
var o1="<option value=''>--请选择--</option>";
      $("select[name='smalltype']").prepend(o1);
//增加节点
      for(var i=0;i<count;i++){
     // alert(data_ [0].code);
      //  alert(data_ [0].label);
      var o1="<option value="+data_ [i].code+">"+data_ [i].label+"</option>";
      $("select[name='smalltype']").append(o1);

}

 });         
         });


}); //





function addlist(list){


}


//清空
function dellist(list){
list.empty();

}

//查询重置
function sc_humptsearchReset(name){ 
	//	alert("ok");
	$("#"+name+"tb").find("input[type!='hidden']").val("");
	$("#"+name+"tb").find("select").val("");
	resetsmalltype();
	//再次查询数据
	sc_humptListsearch();
}
//小类别重置(如果参数存在则根据参数查询内容重新赋值)
function resetsmalltype(codevalue){
	var host = window.location.host;
    var pathname=window.location.pathname;
    var a = pathname.split('/');//.join(',');
	//清空
    dellist($("select[name='smalltype']"));
    var url ="http://"+host +"/"+a[1]+ "/cgAutoListController.do?datagrid&configId=sc_humpt_zidian&&field=id,code,label&";
    if(codevalue){
       url =url+"code="+codevalue+"_*";	
    }
    $.get(url,function(data){
	    var count = data.total;
		var data_ =data.rows;
		var o1="<option value=''>--请选择--</option>";
		$("select[name='smalltype']").prepend(o1);
		//增加节点
		for(var i=0;i<count;i++){
		var o1="<option value="+data_ [i].code+">"+data_ [i].label+"</option>";
		$("select[name='smalltype']").append(o1);
		
		}
	
	}); 
	
	
}

