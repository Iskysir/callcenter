var gxlx = {};
var docfile;
var sfaddzp;//是否需要处理照片问题

$(function($){
	
	
	/****/
	if($.isEmptyObject(gxlx)){
		$.ajax({
		    url:"kinshipController.do?getQslxSex",
			type:"Post",
		    dataType:"json",
		    async:false,
		    success:function(data){
		    	
		    	//alert(data.msg);
		    	//$.data(gxlx,"gxlx",data.attributes)
		    	gxlx = data.attributes;
				//console.log(gxlx);
				}
				
		    
		});
	}
	
	var date = new Date();
	var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	//设置时间在当前时间之前
	var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"',onpicked:function(){(this).blur();}})"
	$("input[name='csrq']").attr('onClick',attval);
	$("input[name='zxrq']").attr('onClick',attval);
	$("input[name='sfsw']").attr('onClick',attval);
var $sfzdtd = $("input[name='zdwfrq']"); //取得重点服务人群控件对象
var $sfzdtdpar = $sfzdtd.parents(".row"); //取得重点服务人群控件的父级对象
var $sfzdfwrq = $("#sfzdfwrq");//取得是否重点服务人群select控件对象

var $sfzdwktd = $("input[name='zdwkrq']"); //取得重点稳控人群控件对象
var $sfzdwktdpar = $sfzdwktd.parents(".row"); //取得重点稳控人群控件的父级对象
var $sfzdwkrq = $("#sfzdwkrq");//取得是否重点稳控人群select控件对象

var init = function(){
//alert("确定");
var urldata = getUrlData();
var bizType = urldata.bizType;
var id = urldata.id;
var jthz = urldata.jthz;
var docWarId = urldata.docWarId;
//**********zwt*****
var viewflag = urldata.mode;
	//只读
	if("read"==viewflag){
		//alert(viewflag);
		var sfzhview=$("#obligatea").val();
		if(!sfzhview){
			var sfzval = $("#sfzh").val(); 
			var sfzval_head = sfzval.substr(0,2);
			var sfzval_mid = sfzval.substr(6,4);
			var sfzval_end=sfzval.substr(14,4);
			sfzhview=sfzval_head+"****"+sfzval_mid+"****"+sfzval_end;
		}
		
		 //$("#sfzh").attr("disabled",false);
		 //alert($("#obligatea").val());
		 $("#sfzh").val(sfzhview);
		 //var  csrqval =$("#csrq").val();
		 //csrqval =csrqval.substr(0,4);
		 //$("#csrq").val(csrqval+"-**-**");
		// $("#sfzh").attr("disabled",true);
		
	}
//**********zwt******	
if(!id ){

	$("select[name='sfhz'] option[value='N']").attr('selected',true);//设置默认不是户主
	//$("select[name='sfzdfwrq'] option[value='N']").attr('selected',true);//设置默认不是重点服务人群zwt
//	$("select[name='sfzdwkrq'] option[value='N']").attr('selected',true);//设置默认不是重点稳控人群zwt
	if(jthz){
		$("select[name='sfhz'] option[value='Y']").attr('selected',true);
	}
	 var sfhz = document.getElementsByName('sfhz')[0];
     var selectedId = sfhz.selectedIndex;	
     var sfhz_val =sfhz.options[selectedId].value;
	  if("Y"==sfhz_val){
	    	
	    	 $("select[name='yhzgx']").attr({"disabled":true,"ignore":"ignore"});
	    	
	        $("select[name='yhzgx']").parent().find("span[style*='red']").html("");
	    	
	    
	    	 $("select[name='yhzgx']").val("");
	    	 $("#ssjt").attr({"disabled":true,"ignore":"ignore"}).parent().find("span[style*='red']").html("");
	    	 $("#ssjt").next().attr("disabled",true);
	    	 
	     }
	var $radioa = $("input[type=radio][name='rysx'][value='1']");
	$radioa.attr("checked",true); 
	$radioa.parent().addClass("checked",true); 
}else{
	
	   var sfhz = document.getElementsByName('sfhz')[0];
	     var selectedId = sfhz.selectedIndex;	
	 var sfhz_val =sfhz.options[selectedId].value;
     if("Y"==sfhz_val){
    	 var ssjtval = $("#ssjt").val();
    	 if(ssjtval){
    		 $("#sfhz").attr("disabled",true);
    	 }
    	 
    	 $("select[name='yhzgx']").attr({"disabled":true,"ignore":"ignore"});
    	
        $("select[name='yhzgx']").parent().find("span[style*='red']").html("");
    	
    
    	 $("select[name='yhzgx']").val("");
    	 $("#ssjt").attr({"disabled":true,"ignore":"ignore"}).parent().find("span[style*='red']").html("");
    	 $("#ssjt").next().attr("disabled",true);
    	 
     }
}
if(bizType){


	$("select[name='sfhz']").attr({"disabled":true});
	 $("#ssjt").attr({"disabled":true});
	 $("#ssjt").next().attr("disabled",true);

}


if(docWarId){
	$.ajax({
	    url:"scDocWarController.do?getDetailDocWar",
	    data:{"docWarId":docWarId},
		type:"Post",
	    dataType:"json",
	    async:false,
	    success:function(data){
	    	if(data.success){
				var address	= data.obj.address;
				var birthday = 	data.obj.birthday;
				var idcardnum = data.obj.idcardnum;
				var name = data.obj.name;
				var nation = data.obj.nation;
				var qfjg = data.obj.qfjg;
				var sex = data.obj.sex;
				var yxqstar = data.obj.yxqstar;
				var yxqend = data.obj.yxqend;
				docfile = data.obj.docFile;
				$("#xm").val(name).attr({"disabled":true});
				$("#sfzh").val(idcardnum).attr({"disabled":true});
				$("#sfzh").parent().find("span[class='suoshu']").attr({"disabled":true});
				$("#csrq").val(birthday);
				$("#xb").val(sex);
				$("#mz").val(nation).attr({"disabled":true});
				$("#sfzdz").val(address).attr({"disabled":true});
				$("#qfjg").val(qfjg).attr({"disabled":true});
				$("#ksyxq").val(yxqstar);
				$("#jsyxq").val(yxqend);
				var src = "commonController.do?viewFile&fileid="+data.obj.docFile+"&subclassname=org.jeecgframework.web.system.pojo.base.TSAttachment";
				
				var divcontent = "<div style='float:left; margin-right:20px;'><img src="+src+" style='cursor: pointer;' width='102px' height='126px'/></div>";
				$("#filetype").empty();
				$("#filetype").append(divcontent);
				sfaddzp = true;
			}					
	    },
		error:function(data){
			$.messager.alert('错误',data.msg);
			return false;
		}
	});	
}
//初始化处理重点服务人群控件是否显示开始
var sfzdfwrq = $sfzdfwrq.val();

if(sfzdfwrq == 'Y'){
$sfzdtdpar.show();
}else{
$sfzdtd.removeAttr("checked");
$sfzdtdpar.hide();
}
//初始化处理重点服务人群控件是否显示结束


//初始化处理重点稳控人群控件是否显示开始
var sfzdwkrq = $sfzdwkrq.val();

if(sfzdwkrq == 'Y'){
$sfzdwktdpar.show();
}else{
$sfzdwktd.removeAttr("checked");
$sfzdwktd.parents(".checked").removeClass("checked");
$sfzdwktdpar.hide();
}
//初始化处理重点稳控人群控件是否显示结束


}

//处理重点服务人群控件内容改变函数
$sfzdfwrq.on("change",function(){
var flag = $(this).val();
if(flag == 'Y'){
$sfzdtdpar.show();
}else{
$sfzdtd.removeAttr("checked");
$sfzdtd.parents(".checked").removeClass("checked");
$sfzdtdpar.hide();

}
});

//处理重点稳控人群控件内容改变函数
$sfzdwkrq.on("change",function(){
var flag = $(this).val();
if(flag == 'Y'){
$sfzdwktdpar.show();
}else{
$sfzdwktd.removeAttr("checked");
var dd = $sfzdwktd.parent(".checked");
$sfzdwktd.parent(".checked").removeClass("checked");
$sfzdwktdpar.hide();

}
});
init();
 
//是否是户主控件内容改变事件
$("select[name='sfhz']").change(function(){	
     var sfhz = document.getElementsByName('sfhz')[0];
     var $yhzgx = $("select[name='yhzgx']"); 
	  var $yhzgxpar = $yhzgx.parents(".row"); 
     var selectedId = sfhz.selectedIndex;	
     var sfhz_val =sfhz.options[selectedId].value;
     if("Y"==sfhz_val){
    	 
    	 $("select[name='yhzgx']").attr({"disabled":true,"ignore":"ignore"})
    	 $("select[name='yhzgx']").val("");
    	
    	 $("select[name='yhzgx']").parent().find("span[style*='red']").html("");
    	 $("#ssjt").attr({"disabled":true,"ignore":"ignore"}).parent().find("span[style*='red']").html("");
    	 $("#ssjt").next().attr("disabled",true);
    	
    	 $("#ssjt").val("");
    	 $("#ssjt_id").val("");
    	 var yhzgxcktip = $("#yhzgx").removeClass("Validform_error").parent().find("span[class*=Validform_wrong]");
    	 yhzgxcktip.html("").removeClass("Validform_wrong");
    	 
    	 var ssjtcktip = $("#ssjt").removeClass("Validform_error").parent().find("span[class*=Validform_wrong]");
    	 ssjtcktip.html("").removeClass("Validform_wrong");
    	// var dd =  $("#ssjt").next().next().next();
    	// dd.html("").removeClass("Validform_wrong");
    	 
    	 
    	 
     }else{
    	
    	 $("select[name='yhzgx']").attr("disabled",false).removeAttr("ignore");
    	 $("select[name='yhzgx']").parent().find("span[style*='red']").html("*");
    	 $("#ssjt").attr("disabled",false).removeAttr("ignore").parent().find("span[style*='red']").html("*");
    	 $("#ssjt").next().attr("disabled",false);
    	 
    	
    	
     }
})



});
function beforeSubmit_(){
//********更改身份证展示zwt  star********
	var sfzval_ = $("#obligatea").val();
	//alert(sfzval_);
	//alert(sfzval_.indexOf("*")<0);
	if(!sfzval_||sfzval_.indexOf("*")<0){
		//alert(123);
		var sfzval = $("#sfzh").val();
		//var sfzval_head = sfzval.substr(0,10);
		//var sfzval_end=sfzval.substr(14,4);
		var sfzval_head = sfzval.substr(0,2);
		var sfzval_mid = sfzval.substr(6,4);
		var sfzval_end=sfzval.substr(14,4);
		sfzhview=sfzval_head+"****"+sfzval_mid+"****"+sfzval_end;
		
		
		$("#obligatea").val(sfzhview);	
	}

	//********更改身份证展示zwt  end********
	
	var ryid = $("#ssjt_id").val();

	var sex = $("select[name='xb']").val();
	var sfhz = $("select[name='sfhz']").val();
	var that=true;
	if(sfhz == 'N'){
		var $selectgxlx = $("select[name='yhzgx']");
		var type = $selectgxlx.val();
		var kintype = gxlx[type];
		if(kintype){
			var gxtype = kintype.sex;
			if(sex != gxtype){
				$.dialog.alert('关系类型与所选人员性别无法对应，请重新选择！');
				return false;
			}
		}
		
		var id = $("input[name='id']").val()
		var hz_id = $("#hz_id").val();
		var qsgx = $("#yhzgx").val();
		if(id){			
			$.ajax({
			    url:"kinshipController.do?dodelrk",
			    data:{"rkid":id,"hz_id":hz_id,"qsgx":qsgx},
				type:"Post",
			    dataType:"json",
			    async:false,
			    success:function(datajt){
			    	
			    	if(!datajt.success){
						
						that = false;
						return false;
					}					
			    },
				error:function(data){
					$.messager.alert('错误',data.msg);
					return false;
				}
			});
			
		}
		
	}	
	if(that){
		 $("#ssjt").attr("disabled",false);  
		 $("select[name='yhzgx']").attr("disabled",false);
		 $("select[name='sfhz']").attr("disabled",false);
	}
	return that;
}
function whqsgx(data){
	var sfhz = data.obj.sfhz;
	if(sfhz == 'N'){
		var ssjt_id = data.obj.ssjt_id;
		var qsId = data.obj.id;
		var name = data.obj.xm;
		var gxlxa = data.obj.yhzgx;
		var kintype = gxlx[gxlxa];
		if(kintype){
			
			$.ajax({
			    url:"kinshipController.do?getjtxx",
			    data:{"ssjt_id":ssjt_id},
				type:"Post",
			    dataType:"json",
			    async:false,
			    success:function(datajt){
			    
			    	var ryId = datajt.obj[0].hz_id;   
			    	var ryName = datajt.obj[0].hzxm;
			    	
			    	$.ajax({
					    url:"kinshipController.do?doAdd",
					    data:{"ryId":ryId,"ryName":ryName,"qsId":qsId,"name":name,"gxlx":gxlxa},
						type:"Post",
					    dataType:"json",
					    async:false,
					    success:function(datajt){
					    
					    	
							
					    },
						error:function(data){
							$.messager.alert('错误',data.msg);
							return false;
						}
					});
			    },
				error:function(data){
					$.messager.alert('错误',data.msg);
					return false;
				}
			});
		}
	//	var ryName = data.obj.name;

		
	}
	
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
function csrqadd(sfzh){
	var sfzhboolean = idcardvalid(sfzh);
	if(sfzhboolean){
		var obj = showBirthday(sfzh);
		$("#csrq").val(obj.csrq).blur();
		$("#xb").val(obj.xb).blur();
	}
}
$("#sfzh").on("change",function(){
	var sfzh = $(this).val();
	csrqadd(sfzh);
});
function popupcsrq(obj){
	var shzh = obj.sfzh;
	sfaddzp = true;
	csrqadd(sfzh);
}
