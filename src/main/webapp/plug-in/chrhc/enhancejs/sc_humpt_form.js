$(document).ready(function(){
var idflag=$("input[name='id']");
//alert(idflag.val());
var host = window.location.host;
var pathname=window.location.pathname;
var a = pathname.split('/');//.join(',');

if(!idflag.val()){


	//清空节点
	dellist($("select[name='smalltype']"));
	
	var s = $("select[name='bigtype']");
	//var bo1="<option value='-' selected='selected'>?????</option>";
	//s.prepend(bo1);
	//清空大类选中
	$("select[name='bigtype'] option[selected='selected']").removeAttr('selected');
	var mySelect = document.getElementsByName('bigtype')[0];
	mySelect.selectedIndex=1;
	var selectedId = mySelect.selectedIndex;
	if(mySelect.options[selectedId].value==''){
	   
	    return;
	}
	    //ajax
	      var url ="http://"+host+"/"+a[1]+"/cgAutoListController.do?datagrid&configId=sc_humpt_zidian&&field=id,code,label&code="+mySelect.options[selectedId].value+"_*";
	      $.get(url,function(data){
	      var count = data.total;
	  //alert(count);
	 var data_ =data.rows;
	// alert(data_);
	//  $("select[name='smalltype']").append("<option value=''>---请选择---</option>");
	//增加行业类别?
	for(var i=0;i<count;i++){
		var o1="<option value="+data_ [i].code+">"+data_ [i].label+"</option>";
		$("select[name='smalltype']").append(o1);
	
	}
	
	});
	//行业小类别初始化
	//$("select[name='smalltype']").append("<option value=''>---请选择---</option>");
}else{
             
 

 var  smallvalue =$("select[name='smalltype']").val();
    	
    	var mySelect = document.getElementsByName('bigtype')[0];
                var selectedId = mySelect.selectedIndex;
               //清空?
              dellist($("select[name='smalltype']"));
              //  alert($("select[name='smalltype'] option[selected='selected']").removeAttr('selected'));

          //ajax调用?
            var url ="http://"+host+"/"+a[1]+"/cgAutoListController.do?datagrid&configId=sc_humpt_zidian&&field=id,code,label&code="+mySelect.options[selectedId].value+"_*";
             
            $.get(url,function(data){
             var count = data.total;
             var data_ =data.rows;
             for(var i=0;i<count;i++){
        	   var sma_value= data_ [i].code;
        	   if(smallvalue==sma_value){
               var o1="<option value='"+sma_value+"' selected='selected'>"+data_ [i].label+"</option>";
        	   }else{
        		   var o1="<option value="+sma_value+">"+data_ [i].label+"</option>"; 
        	   }
               $("select[name='smalltype']").append(o1);

  		   }
    	 });  
    	
    		
    	}

//change事件
$("select[name='bigtype']").change(function(){

      var host = window.location.host;
      var pathname=window.location.pathname;
      var a = pathname.split('/');//.join(',');
      //alert(a[1]);
      //alert(a);

     //alert($(this).text());
     // alert($("select[name='bigtype'] option[selected='selected']"));
      //清空?
        dellist($("select[name='smalltype']"));
       //原生js调用
      var mySelect = document.getElementsByName('bigtype')[0];
         var selectedId = mySelect.selectedIndex;
      if(mySelect.options[selectedId].value==''){
           //alert("????");
           return;
     }
           //ajax
             var url ="http://"+host+"/"+a[1]+"/cgAutoListController.do?datagrid&configId=sc_humpt_zidian&&field=id,code,label&code="+mySelect.options[selectedId].value+"_*";
             $.get(url,function(data){
             var count = data.total;
         //alert(count);
        var data_ =data.rows;
       // alert(data_);
      //  $("select[name='smalltype']").append("<option value=''>---请选择---</option>");
      //增加行业类别?
      for(var i=0;i<count;i++){
     // alert(data_ [0].code);
     // alert(data_ [0].label);
      var o1="<option value="+data_ [i].code+">"+data_ [i].label+"</option>";
      $("select[name='smalltype']").append(o1);

}

 });         
         });


}) //



//
function addlist(list){


}


//清空
function dellist(list){
list.empty();

}