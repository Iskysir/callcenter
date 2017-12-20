
function whjtxx(data){
	var id = data.obj.id;
	var name = data.obj.xm;
	$("#hzxm").val(name);
	$("#hz_id").val(id);
}
var urlpath = getUrlData();
var id = urlpath.id; 
var sqly_id= urlpath.sqly_id;
init();
function init(){
	//*******************zwt   增加跨社区查询 star*******************
	$("#hzxm").parent().find("span[style*='red']").after("<a href='#' id='crossquerry' class='easyui-linkbutton' data-options=\"iconCls:'icon-add'\" plain='true' >跨社区查询</a>");
	//*******************zwt   增加跨社区查询 star*******************
if(id){

$("#hzxm").parent().find("span[style*='red']").after("<a href='#' id='rywh' class='easyui-linkbutton' data-options=\"iconCls:'icon-edit'\" plain='true' >成员</a>");
}else{
	$("#hzxm").parent().find("span[style*='red']").after("<a href='#' id='addhz' class='easyui-linkbutton' data-options=\"iconCls:'icon-add'\" plain='true' >户主</a>");
}

if(sqly_id){

$("#lydz").removeAttr("onclick").attr("readonly",true);
$("#lydz").parent().find("span[class='suoshu']").removeAttr("onclick").attr("readonly",true);
}


	var date = new Date();
	var date1=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	//设置时间在当前时间之前
	var attval= "WdatePicker({errDealMode:1,maxDate:'"+date1+"'})"
	$("input[name='jhrq']").attr('onClick',attval);
	$("input[name='whjtpxsj']").attr('onClick',attval);
	$("input[name='wsjtpxsj']").attr('onClick',attval);
	$("input[name='sphdsj']").attr('onClick',attval);
	
}

$("#rywh").on("click",function(){
  //addOneTab('人口基本信息',"chrhcAutoListController.do?list&id=sc_rkjbxxnew&ssjt_id="+id+"ssjt="+$("#hzxm").val());
var title = $("#hzxm").val()+"家庭成员维护";
var url = "chrhcAutoListController.do?list&id=sc_rkjbxxnew&ssjt_id="+id+"&bizType=true";
  //openwindow(title,encodeURI(url),"sc_rkjbxxnewList","1000px","600px");
parent.parent.addTab(title,encodeURI(url));
//addOneTab(title,encodeURI(url));
});
$("#addhz").on("click",function(){
	  //addOneTab('人口基本信息',"chrhcAutoListController.do?list&id=sc_rkjbxxnew&ssjt_id="+id+"ssjt="+$("#hzxm").val());
	var title = "新增户主";
	var url = "chrhcFormBuildController.do?ftlForm&tableName=sc_rkjbxxnew&bizType=true&dataRule=null&jthz=true";
	  //openwindow(title,encodeURI(url),"sc_rkjbxxnewList","1000px","600px");
	parent.parent.addTab(title,encodeURI(url));
	//addOneTab(title,encodeURI(url));
	});
function beforeSubmit_(){
	var that=true;
	var id = $("#hz_id").val();		
	if(id){			
			$.ajax({
			    url:"kinshipController.do?checkhz",
			    data:{"id":id},
				type:"Post",
			    dataType:"json",
			    async:false,
			    success:function(datajt){	
			    	if(!datajt.success){
						
						that = false;
						$.dialog.alert(datajt.msg);
						return false;
					}					
			    },
				error:function(data){
					$.messager.alert('错误',data.msg);
					return false;
				}
			});
			
		}	
	return that;
}



	//title:"弹出框";
	  //addOneTab('人口基本信息',"chrhcAutoListController.do?list&id=sc_rkjbxxnew&ssjt_id="+id+"ssjt="+$("#hzxm").val());
	/*$.dialog({
	    //content:'url:value/value02.html',
	    content:'<html><head>  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>'
	   	+'<title></title></head><body>身份证号：<input id="testid" value="测试"> </body> </html>',
	    //content:"url:plug-in/chrhc/enhancejs/newfile.html",
	    id:'coross',
	   
	    //self:true,
	    init:function(){
	       
	        this.iwin.document.getElementById('itxt').value = document.getElementById('txt1').value;
	    	alert(window.top.document.getElementByname('lydz').value);
	    },*/
	    
	   /** ok: function() {
	        //alert(window.top.document.getElementById("lydz").value);
	        //var api = frameElement.iframe;
	        //alert(api);
	        //var value =document.getElementById("testid").value;//弹出null
	        //alert(value);
	        var test=document.frames[0]; 
	        alert(test);
	        // alert($("#testid").val());//undefined
	        alert($("input[name='testid']"));//undefined
	           
	        alert(document.getElementById("lydz").value);//这是大页面的值 可以取得
	           //getvalues();
	        	return false;
	        },*/
	       // cancelVal: '关闭',
	      //  cancel: true 
	//});
	//////*******************************
	/*window.top.art.dialog({title:"123",
	    //content: '<html><head>  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>'
	    //	+'<title></title></head><body>身份证号：<input id="testid" value="测试"> </body> </html>',
	    	
	    content:"url:plug-in/chrhc/enhancejs/newfile.html",
	    id: "CROSS",
	    height:100,
	    width:200,
	    top:50,
	    left:200,
	    ok: function(){
	        //this.title('警告').content('请注意artDialog两秒后将关闭！').lock().time(2);
	    	alert(contentWindow.$("#testid").val());
	    	//alert(this.iframe.contentWindow.$("#testid").val());
	    	alert(document.getElementById("lydz").value);
	        return false;
	    }
	});*/
/* 跨社区查询*/
$("#crossquerry").on("click",function(){	
	//*****************
	$.dialog.prompt("请输入身份证号：",
	function(val){
		if(!val||val.length<18){
			 alert("请认输入合法身份证号码！");
			 return false; 
		}
//查询全部人口的
//http://localhost:8080/sc/chrhcAutoListController.do?datagrid&configId=sc_rkjbxxnew&field=id,create_name,create_by,create_date,update_name,version_num,sys_org_code,update_by,update_date,obligateb,obligatec,obligated,obligatee,del_date,gisxy,grzp,xm,obligatea,sfzh,csrq,bm,sys_org_name,xb,mz,sfzdz,xjzdz,hjdz,csd,qfjg,ksyxq,jsyxq,delflag,jtzz,hukouxinxi,sfhz,hkxz,yhzgx,ssjt,hjzk,cym,hyzk,whcd,zzlb,byzk,hksfzx,zy,zxrq,qitaxinxi,sg,xx,zjxy,jkzk,lxdh,yx,gzdw,gzlb,dwxz,zc,jmxxkh,tszc,jhr,jhgx,ysr,xqmc,lh,dyh,jg,kuozhanxinxi,sfjtcy,tzlb,qwlb,bzxx,ssjt_id,rysx,sfzdfwrq,sfzdwkrq,zyz,zdwfrq,zdwkrq,bxxx,dblb,wbh,kdh,cjzk,syry,sfsw,qytxry,hz_id,lnr,yf,ylfn,ylbx,ylbxa,sfxs,lz,zary,&dataRule=null&sfzh=
//查询户主人口的		
//http://localhost:8080/sc/cgReportController.do?datagrid&configId=sc_hz&sfzh=372328198804090617	
		var host = window.location.host;
		var pathname=window.location.pathname;
		var a = pathname.split('/');
		//var url_="http://"+host+"/"+a[1]+"/cgReportController.do?datagrid&configId=sc_hz&sfzh=";
		//var url_="./cgReportController.do?datagrid&configId=sc_hz&sys_org_code=0&sfzh=";
		var url_="./scDocWarController.do?getselfDictionary&tablename=sc_rkjbxxnew&label=CONCAT(xm,'_',id)&code=sfhz='Y' and sfzh&codevalue=";
		 $.ajax({type: "GET",
		 url:url_+val,
		 async:false,
		 success: function(data){
			 if(!data){
				 // if(!data||!data.rows[0]){
					 alert("未查到数据，请确认输入身份证号码是否正确！");
					 return false;
			 }
			 data =data.replace(/(^\"*)/g, "");
			 data =	data.replace(/(\"*$)/g, "");
			var label = data.split("_");
				
			if(label.length>1){
				//var hzid=	label.rows[0]['id'];
				//var xm = label.rows[0]['xm'];
				$("#hz_id").val(label[1]);
				$("#hzxm").val(label[0]);
				//alert($("#hz_id").val()+$("#hzxm").val());
			}else{
				 alert("未查到数据，请确认输入身份证号码是否正确！！");
				 return false;
			}
			
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				  return false;
			}
		 });	
		
		
	},'37'
			
	);

	
});


