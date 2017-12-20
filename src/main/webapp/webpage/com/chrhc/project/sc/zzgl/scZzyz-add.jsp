<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%
String path_java = request.getContextPath();
String basePath_java = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path_java+"/";
%>
<!DOCTYPE html>
<html>
<head>
<title>证照验真</title>
 <t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
<link id="easyuiTheme" rel="stylesheet"	href="plug-in/easyui/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="plug-in/easyui/themes/icon.css"
	type="text/css"></link>
<link rel="stylesheet" type="text/css"	href="plug-in/accordion/css/accordion.css">
<link href="plug-in/chrhc/bootstrap.css" rel="stylesheet">
<link href="plug-in/chrhc/style.css" rel="stylesheet">
<link href="plug-in/icheck/skin/square/blue.css" rel="stylesheet">
<script type="text/javascript"
	src="plug-in/easyui/jquery.easyui.min.1.3.2.js"></script>
<script type="text/javascript" src="plug-in/easyui/locale/zh-cn.js"></script>
<script type="text/javascript" src="plug-in/tools/syUtil.js"></script>
<script type="text/javascript"
	src="plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
<script type="text/javascript" src="plug-in/tools/curdtools_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/tools/easyuiextend.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>

<script src="plug-in/icheck/icheck.js"></script>
<script src="plug-in/chrhc/jquery.tabs.js"></script>
<script src="plug-in/chrhc/jquery.lazyload.js"></script>
<script src="plug-in/Validform/js/Validform_v5.3.1.js" type="text/javascript"></script>

<script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript"
	src="webpage/com/chrhc/project/sc/docfile/gpy.js"></script>
<script src="plug-in/chrhc/bootstrap.min.js"></script>
<script src="plug-in/chrhc/ie8/html5.js"></script>
<script src="plug-in/chrhc/ie8/respond.min.js"></script>
	<script type="text/javascript"
	src="plug-in/artDiglog/jquery.artDialog.js?skin=default"></script>
<%--  validform --%>	
<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css"/>
<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css"/>
<link rel="stylesheet" href="plug-in/chrhc/currentPosition.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>

<script type="text/javascript">
	//增加时的拍照次数   用于记录每条附件数据的序号，以及图片生成序号（）
	var photoindext = 1;
	var picpathall ="";
	var fileid="";
	var data_id="";
	var tableName="";
	$(document).ready(function() {
		debugger;
		$("#formobj").attr("class","form-inline valideForm"); 
		// 高拍仪初始化
		try{
			//判断是否安装控件 
			pageinit();
			//初始化高拍仪
			gpyinit();
			
			// 设置证照验真的flag
			window.top.$("#zzyzflag").val("1");
			gpyartdialog();
			
		}catch(e){
			//$("#gaopaiyi").remove();
			deploygpy();
			$("#uploadFile1").remove();
		}

		//双击 预览事件（附表图片）
		 $(".pic-box[id!='z_photo']  img").live("dblclick",function(){
			 //var path =$(this).parents("div .pic-box").find('img').attr('src');
			 var path =$(this).attr('src');
					art_dialog("查看图片","<img src='"+path+"'style='position:absolute;left:23px;top:72px;right:20px;bottom:5px;width:93%;height:83%' />",'EA693L');
				
		 });
		/* $("#freadcard").click(function() {
			addline(photoindext);
			ReadIDCARDInfo(photoindext);
			photoindext = photoindext + 1;
		}); */
		
		//附件图片操作
		 $('input').iCheck({
				checkboxClass : 'icheckbox_square-blue',
				radioClass : 'iradio_square-blue',
				increaseArea : '20%' // optional
			});
			//预览
			$(".span-see").live("click",function() {
				//clearTimeout(click_timer);
				
					 var path =$(this).parents("div .pic-box").find('img').attr('src');
						//alert(path);
						var xt_cardnum = $(this).parents("div .pic-box").find("input[name$='.idcardNum']").val();
						if(xt_cardnum){
							art.dialog({title:"查看身份证",
							    content: "<img src='"+path+"' width='100%'height='100%'/>", //'<img src='+path+' width="100%" height="100%" />',
							    id: 'EA693L',
							    height:600,
							    width:800,
							    resize:true
							});
						}else{
							art_dialog("查看图片","<img src='"+path+"' style='position:absolute;left:23px;top:72px;right:20px;bottom:5px;width:93%;height:83%'/>",'EA693L');//
						}     
				
			});
			
			//删除附件图片
			
			$(".span-delete").live("click",function() {
				//alert ("删除");
				tr_del(this);
			});
			
			
	  
	});
	
	//  文件上传
	function uploadcut() {
		var seeionid="";
	//alert(picpathall);
	if(picpathall.length>5){
	
		 var time_ =  new Date().getTime();
	
		 var url ="";
	     url="<%=basePath_java%>"+"scDocWarController.do;jsessionid=<%=session.getId()%>?addfiles&"+time_;
	     //alert(url);
	     var sessionid="<%=session.getId()%>";
	     //alert(sessionid);
	     //picpathall = picpathall.substring(0,picpathall.length-1);
	     //alert(picpathall);
	     var msg = document.getElementById("uploadFile1").Upload(url,sessionid,picpathall);
	
			var d=$.parseJSON(msg);
	    
		 if(d!=null&&d.success){
	    	 
			 for ( var key in d) {
				 
				 if (key.indexOf("F") != -1) {
						//然后赋值给予附表字段
						fileid = d[key];

					}

				} 

				
				var url_yz="<%=basePath_java%>"+"dec_wmController.do?decodeByftpid&fileId="+fileid;
				
				 $.get(url_yz,function(data){
					 	
					// var re_data = ajax_general(url_yz);
						debugger;
						var re_data=$.parseJSON(data);
						if(re_data.success){
							$("#yzjg").val(re_data.msg);
							data_id =re_data.attributes.data_id;
							tableName =re_data.attributes.tableName;
						}else{
							$("#yzjg").val(re_data.msg);
						}
						//alert(re_data.attributes);
						//打开证照模板
						//var data_id ="4028993250dbfc490150dbfc49b70000";
						//var tableName ="sc_stay";
						//var openurl ="<%=basePath_java%>"+"scCerTemplateController.do?tempPrint&id="+data_id+"&tableName="+tableName;
						//window.open(openurl);
						//addOneTab("证明",openurl);
						
						//alert(openurl);
						
						if(re_data.attributes){
							$("#zzbh").val(re_data.attributes.code);
						};
						$("#formobj").submit();
			 	 }); 
				//把上传地址赋空  
				//picpathall="";  因为验真模块只显示单张图片所以不用 置空
				
	     }else{
	    	 $.dialog({ id:'test30', time:3, title:'提示',icon: 'warning',content:'请重新操作！'});
	     }
	    
	}else{
		
		  $.dialog({ id:'test30', time:3, title:'提示',icon: 'warning',content:'请拍照之后再验真！'});
	}	
 }
	
	var neibuClickFlag = false;
	/**回调函数*/
	function uploadFile(data){
		debugger;
		//alert(data.obj.id);
	if(!$("input[name='id']").val()){
		$("input[name='id']").val(data.obj.id);
	}
	
	if($(".uploadify-queue-item").length>0){
		upload();
	}else{
				if (neibuClickFlag){
					alert(data.msg);
					neibuClickFlag = false;
				}else {
					//alert(data);
					var win = getParentWindow();
					win.reloadTable(data);
					//win.tip(data.msg);
					if(frameElement.api)
					{
						frameElement.api.close();
					}
					else
					{
					
						//打开证照模板
						if(data_id){
						//if(true){
						//var openurl ="<%=basePath_java%>"+"scCerTemplateController.do?tempPrint&id="+data_id+"&tableName="+tableName;
						//addOneTab("证明",openurl);
						
						//window.open("http://www.hao123.com/");
						//closeCurrentTab();
						//打开证照模板 zwt20151223
								
								var code='businesscode';
								//var codevalue='';
								var lable ='businessdes';
								var url_zm="<%=basePath_java%>"+"scDocWarController.do?getselfDictionary&tablename=sc_rk_yw_config&code="+code+"&label="+lable+"&codevalue="+tableName;
								debugger;
								 $.ajax({type: "GET",
									  url:url_zm,
									  async:false,
									  success: function(datazm){
										  debugger;
									
										  if(datazm){
										  
												 //alert(datazm);
												 
													datazm = datazm.replace("\"","");
													datazm = datazm.replace("\"","");
													// alert(datazm);
													var myDate = new Date();
													var myt=myDate.getMilliseconds();
													var reporturl =window.top.PROVREPORTURL;
													var zip = window.top.ZIP;
													reporturl= reporturl+"statement/"+datazm+".cpt&id="+data_id+"&zip="+zip+"&d="+myt;
													addOneTab("证明", encodeURI(reporturl));
											 }
										  
									  },
									  error:function(XMLHttpRequest, textStatus, errorThrown){
								  
									  }
								 }); 
						data_id="";
						tableName="";
						}
						closetab("验真");
					
					//4
				}
				
			}
	}
}
/**关闭窗口事件*/
window.onbeforeunload = function() {
		closeartdialog();
		
		window.top.$("#zzyzflag").val("");
	}
</script>
<!-- ServerReply event   上传完成事件 -->
<SCRIPT LANGUAGE=javascript FOR=CuteCtrl1 EVENT="ServerReply(filename)">
<!--
	CuteCtrl_ItemSelected(filename)
//-->
</SCRIPT>
<SCRIPT LANGUAGE=javascript FOR=CuteCtrl1 EVENT="Error(filename)">
<!--
	erro_upload(filename)
//-->
</SCRIPT>
<style type="text/css">
.aui_close{
display:none;
}
</style>
</head>
<body style="overflow-x:hidden;">
<div class="Current_position">
    	<img src="plug-in/media/image/dingwei.png"/><span>当前所在的位置：</span>
		<a href="javascript:;">证明管理</a>&nbsp;
     	 <a href="javascript:;">证明管理</a>&nbsp;
     	  <a href="javascript:;">证照验真</a>
    </div>

	<%--引入的demo画面 --%>
	<t:formvalid layout="table" tiptype="4"  action="scZzyzController.do?doAdd"  formid="formobj" dialog="true">
	
		<div class="container form-card">
		
					<input id="id" name="id" type="hidden" value="${scZzyzPage.id }">
					<input id="yzjg" name="yzjg" type="hidden" value="${scZzyzPage.yzjg }">
					<input id="yzsj" name="yzsj" type="hidden" value="${scZzyzPage.yzsj }">
					<input id="scbj" name="scbj" type="hidden" value="${scZzyzPage.scbj }">
					<input id="versionNum" name="versionNum" type="hidden" value="${scZzyzPage.versionNum }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${scZzyzPage.sysOrgCode }">
					<input id="zzdyid" name="zzdyid" type="hidden" value="${scZzyzPage.zzdyid }">
					<input id="createDate" name="createDate" type="hidden" value="${scZzyzPage.createDate }">
		<div class="row">
		    <div class="col-md-12">
		    	<div class="group-title">
					<label>验证信息</label>
				</div>
		    </div>
		</div>	
			
 		
		
		<div class="row">
			
				<div class="col-md-6">
					<label class="control-label" for="bz">备注</label>
					<!-- <input  type="text" class="form-control w260" id="bz" name="bz" datatype="*1-200" ignore="ignore" /> --><%--validType="sc_doc_war,idcardnum,id" --%>
				<textarea rows="3" cols="20" class="form-control w260" id="bz" name="bz" datatype="*1-200" ignore="ignore"></textarea>
				
				</div>
				  	<div class="col-md-6">
					<!-- <label class="control-label" for="zzbh">证件编号</label> -->
					<input id="zzbh" name="zzbh" type="hidden" style="width: 150px" class="form-control w260" readonly="readonly"/>
				</div> 
		</div>
		
		<!-- 图片信息 -->
		<div class="row">
			<div class="col-md-12">
				<div class="group-title">
					<label>图片</label>
				</div>
			</div>
		</div>
		<div class="row" >
		  	<div class="col-md-12" id="f_table">
		  		
		  	</div>
		  </div>
	
	</div>
	<div class="row" id="buttonPanel">
				<div class="col-md-12 center">
					<button type="button" class="btn btn-sure" id="docsub" onclick='uploadcut()'>确定</button>
          			<button type="button" class="btn btn-cancel" onclick="closeCurrentTab()">关闭</button>
				</div>
	</div>
		
	
	
	</t:formvalid>
	

	

	<%--高拍仪画面集成区域 --%>
	
	

<%-- 上传控件  --%>
<OBJECT ID="uploadFile1" WIDTH=0 HEIGHT=0
 CLASSID="CLSID:3F5ADE93-9B79-46A3-88A2-D2ED39682BF2">
    <PARAM NAME="_Version" VALUE="65536">
    <PARAM NAME="_ExtentX" VALUE="2646">
    <PARAM NAME="_ExtentY" VALUE="1323">
    <PARAM NAME="_StockProps" VALUE="0">
    <PARAM NAME="showMsg" VALUE="1">
</OBJECT>

	
<script type="text/javascript">
</script>	
</body>
<script src="webpage/com/chrhc/project/sc/docfile/scDocWar.js"></script>
<%----%>

</html>




