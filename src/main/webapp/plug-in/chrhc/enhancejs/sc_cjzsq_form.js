$(function($){
	

});


window.onload=function(){

	debugger;
	var click_timer=null;
	var idcard="";
	
	click_timer =window.setInterval (function(){
		//clxxshow(idcard);
		debugger;
		 idcard= $("#sfzh").val();
		if(!idcard||idcard==null||idcard==""){
			//return;
		}else{
			clearInterval(click_timer);
			clxxshow(idcard);
			
		}
	},800);
    
    
}

function clxxshow(idcard){
	
	if(idcard==null||idcard=="")return;
    //alert(sfzh);
    var bzparent= $("#remark").parent().parent(".row");
    var divrow = $("<div id='clxx' class='row'><div class='col-md-12'> <div class='group-title'> <label>材料信息</label> </div></div></div>");
    /*<div class="row">
	<div class="col-md-12">
			<div class="group-title">
			<label>材料信息</label>
			</div>
	</div>
	</div>*/
    bzparent.after(divrow);
   // var dblburl="http://10.61.160.7:8280/cpub-back-web/api/app/netservice/declare/tasklist";
    var dblburl="http://10.61.160.7:8280/cpub-back-web/api/app/netservice/declare/taskcompletelist";
     //idcard="372925198711141319";
    var flag="申请残疾证";
    var url="scGzptlcController.do?fjzl&idcard="+idcard+"&flag="+flag+"&pagesize=50&url="+dblburl;
    debugger;
    $.ajax({
	    url:encodeURI(url),
	    //data:{"docWarId":docWarId},
		type:"get",
	    dataType:"json",
	    async:false,
	    success:function(data){
	    	if(!data)return;
	    	debugger;
	    		var clparent= $("#clxx");
	    		var abq="";
	    		
	    		var divrowout=$("<div id='clinfo'class='row' style='display: block'></div>");
	    		clparent.after(divrowout);
	    		var divrow_ ="";
	    		for(var i=0; i<data.length; i++) {
	    			var stuffDelivery = data[i].stuffDelivery;
		    		if(stuffDelivery=='1'){
		    			 abq="<a href='"+data[i].attachmentRealPath+"'target='_blank' style='color:red;text-decoration:underline'>预   &ensp;&ensp;览</a>";
		    			
		    		}else{
		    			 abq="<span style='color:red;font-size:26'>现场递交</span>";
		    		}
		    		
	    			divrow_ = $("<div class='col-md-6'> <label class='control-label' for='yxqend'>"+data[i].stuffName+":</label>"+abq+"</div>");
	    			//alert(data[i].stuffName);
	    	
	    				
	    			$("#clinfo").append(divrow_);
	    		}
	    	
								
	    },
		error:function(data){
			$.messager.alert('错误',data.msg);
			return false;
		}
	});
    
}
function beforeSubmit_(){

}

function saverkxxgrzp(docfile,cgformId,cgformName,cgformField){
	$.ajax({
	    url:"scDocWarController.do?saverkxxgrzp",
	    data:{
	    	"docfile":docfile,
	    	"cgformId":cgformId,
	    	"cgformName":cgformName,
	    	"cgformField":cgformField,
	    	},
		type:"Post",
	    dataType:"json",
	    async:false,
	    success:function(data){
	    					
	    },
		error:function(data){
			$.messager.alert('错误',data.msg);
			return false;
		}
	});
}

