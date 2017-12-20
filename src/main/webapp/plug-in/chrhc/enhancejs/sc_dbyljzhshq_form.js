$(document).ready(function(){
	//编号不可编辑
	$("#code").attr("readonly","readonly");	
	
	$("#code").parent().find("span[style*='red']").after("<A title='打印' class='printbtn-new inner-newbtn' id='dbyl' style='cursor:pointer'   icon='icon-search' plain='true'></A>");
	$("#dbyl").on("click",function(){

	  //addOneTab('人口基本信息',"chrhcAutoListController.do?list&id=sc_rkjbxxnew&ssjt_id="+id+"ssjt="+$("#hzxm").val());
	var title = "医疗救助申请暨审批表";
	var rk_id =  $("#rk_id").val();
		if(rk_id.length > 0 ){
			//var url = "scDataTransconfController.do?tiaozhuan&dtKey=dbsqb&tableName=sc_dbsqb&rk_id="+rk_id+"&scQuickTitle=医疗救助申请暨审批表 ";
			  
			//parent.parent.addTab(title,encodeURI(url));
			debugger;
			var myDate = new Date();
						var myt=myDate.getMilliseconds();
						var reporturl =window.top.PROVREPORTURL;
						var zip = window.top.ZIP;
						reporturl= reporturl+"statement/医疗救助申请暨审批表.cpt&rk_id="+rk_id+"&zip="+zip+"&d="+myt;
						parent.parent.addTab("打印医疗救助申请暨审批表", encodeURI(reporturl));
			
		}else{
			$.dialog.alert("请维护申请人姓名");
		}
	
});

});



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


window.onload=function(){
	debugger;
	var click_timer=null;
	var idcard="";
	
	click_timer =window.setInterval (function(){
		//clxxshow(idcard);
		debugger;
		 idcard= $("#cert_id").val();
		if(!idcard||idcard==null||idcard==""){
			//return;
		}else{
			clearInterval(click_timer);
			clxxshow(idcard);
			
		}
	},800);
    
}
function clxxshow(idcard){
	
	if(!idcard||idcard==null||idcard=="")return;
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
    // idcard="372925198711141319";
    var flag="德城区城乡困难居民医疗救助";
    var url="scGzptlcController.do?fjzl&idcard="+idcard+"&flag="+flag+"&pagesize=50&url="+dblburl;
    debugger;
    $.ajax({
	    url:encodeURI(url),
	    //data:{"docWarId":docWarId},
		type:"get",
	    dataType:"json",
	    async:false,
	    success:function(data){
	    	debugger;
	    	if(!data)return;
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


