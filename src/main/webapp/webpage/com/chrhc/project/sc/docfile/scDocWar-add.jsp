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
<title>电子资料</title>
 <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 <link href="plug-in/icheck/skin/square/blue.css" rel="stylesheet">
<link href="plug-in/chrhc/bootstrap.css" rel="stylesheet">
<link href="plug-in/chrhc/style.css" rel="stylesheet">
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
<link id="easyuiTheme" rel="stylesheet"
	href="plug-in/easyui/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="plug-in/easyui/themes/icon.css"
	type="text/css"></link>
<link rel="stylesheet" type="text/css"
	href="plug-in/accordion/css/accordion.css">
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
<script type="text/javascript"src="webpage/com/chrhc/project/sc/docfile/gpy.js"></script>
<script src="plug-in/chrhc/bootstrap.min.js"></script>
<script src="plug-in/chrhc/ie8/html5.js"></script>
<script src="plug-in/chrhc/ie8/respond.min.js"></script>
	<script type="text/javascript"
	src="plug-in/artDiglog/jquery.artDialog.js?skin=default"></script>
<%--  validform --%>	

<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css"/>
<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css"/>
<script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>

<script type="text/javascript">
	//增加时的拍照次数   用于记录每条附件数据的序号，以及图片生成序号（）
	var photoindext = 1;
    var picpathall ="";
	
	var tempdir="";//getTempDir()
	var rephototype =""; 
	$(document).ready(function() {
		//var sss = window .top.$("#testgpy").val();//attr("class");
		debugger;
		
		try{
		$("#formobj").attr("class","form-inline valideForm"); 
		
		$("select[name='docType']").attr("class","form-control w260"); 
		//$("select[name='docType']").attr("datatype","*"); 
		//$("select[name='nation']").attr("datatype","*"); 
		//document.getElementById("docType").options[0].selected = true;//默认第一个选中
		//$('#docType>option:nth-child(1)').attr("selected",true);
		//$("#docType").get(0).selectedIndex=1;
		var mySelect = document.getElementsByName('docType')[0];
		 //设置身份证默认选中
		 mySelect.selectedIndex=1;
		 $("select[name!=docType]").attr("disabled",true);
		 $("input[name!=bz]").attr("disabled",true);
		 
		 
		$("select[name='nation']").attr("class","form-control w260"); 
		//$("select[name='nation']").attr("readonly","readonly"); 
		$("select[name='sex']").attr("class","form-control w260"); 
		//$("select[name='sex']").attr("readonly","readonly"); 
		//$("select[name='sex']").attr("datatype","*"); 
		//$("select[name='sex']").find("option[index=1]").attr("selected",true); NG
		 mySelect = document.getElementsByName('sex')[0];
		 mySelect.selectedIndex=1;//默认选中第一项
		 mySelect = document.getElementsByName('nation')[0];
		 mySelect.selectedIndex=1;//默认选中第一项
		 
		 $("select[name!=docType]").attr("disabled",true);
		 //初始化固定显示的图片材料类型
		 rephototype = attachmentInit("${tablecode}");
		//判断是否安装控件
		pageinit();
		// 高拍仪初始化
			gpyinit();
			
			gpyartdialog();
			
			
		}catch(e){
			//$("#gaopaiyi").remove();
			//高拍仪最小化
			deploygpy();
			$("#uploadFile1").remove();
		}
		
		

		$("#Takepic").click(function() {
			var indFlag=scanImg();
			if(indFlag){
				
				Takepic(parseInt(indFlag));	
			}else{
				if(photoindext> FINALINDEXT){
					  addline(photoindext);  
				  }
				
				Takepic(photoindext);
				photoindext = photoindext + 1;	
			}
			  
		});
		$("#freadcard").click(function() {
		var indFlag=scanImg();
		if(indFlag){
				
				ReadIDCARDInfo(parseInt(indFlag));	
			}else{
		
			 if(photoindext> FINALINDEXT){
				 addline(photoindext,true);
			  }
			
			ReadIDCARDInfo(photoindext);
			photoindext = photoindext + 1;
			}
		});
		
		//文档类型改变事件
		 $("select[name='docType']").change(function(event) {
			 
			//var val=$("select[name='docType'] option[selected='selected']").val();不支持了 MLGB
			 var val =$("select[name='docType']").find("option:selected").val();
			//身份证类型
			if(val=='1'){
				//$("input").attr("readonly","readonly");
				//$("select").attr("readonly",true);
				$("select").attr("disabled",true);
				$("input").attr("disabled",true);
				//$("input[name='bz']").attr("readonly",false);
				//设置文档类型可读
				$("select[name='docType']").attr("disabled",false);
			//其他类型
			}else{
				$("input").attr("disabled",false);
				//$("select").attr("readonly",false);
				$("select").attr("disabled",false);
				//$('input').removeAttr("readonly");//去除input元素的readonly属性
				//添加验证
				adddatatype();
				
			}
			$("select[name='docType']").attr("disabled",false);
			$("input[name='bz']").attr("disabled",false);
		}); 	 
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
							art_dialog("查看身份证","<img src='"+path+"'style='width:100%;height:100%'/>",'EA691L');
							
						}else{
							art_dialog("查看图片","<img src='"+path+"' style='position:absolute;left:23px;top:72px;right:20px;bottom:5px;width:93%;height:90%;height:83%'/>",'EA693L');
							//art_dialog("查看图片","<img src='"+path+"' style='position:absolute;left:23px;top:72px;right:20px;bottom:5px;width:93%'/>",'EA693L');//
						}     
				  
				//取得图片地址，弹窗显示
				//alert($(this).parent().find("span.pic-address").text());
				
			});
			//双击 预览事件（主身份证）
			 $("#z_photo img").live("dblclick",function(){
				 //var path =$(this).parents("div .pic-box").find('img').attr('src');
				 var path =$(this).attr('src');
					//alert(path);
					art_dialog("查看办理人身份证","<img src='"+path+"' style='width:600px;height:500px'/>",'EA693L');
						
					
			 });
			//双击 预览事件（附表图片）
			 $(".pic-box[id!='z_photo']  img").live("dblclick",function(){
				 //var path =$(this).parents("div .pic-box").find('img').attr('src');
				 var path =$(this).attr('src');
				 var xt_cardnum = $(this).parents("div .pic-box").find("input[name$='idcardNum']").val();
					if(xt_cardnum){
						art_dialog("查看身份证","<img src='"+path+"' style='width:100%;height:100%'/>",'EA691L');
						
					}else{
						art_dialog("查看图片","<img src='"+path+"' style='position:absolute;left:23px;top:72px;right:20px;bottom:5px;width:93%;height:83%'/>",'EA693L');
						
					}
					
			 });
			//删除附件图片
			
			$(".span-delete").live("click",function() {
				//alert ("删除");
				tr_del(this);
			});
			//附件图片信息
			$(".pic-info").live("click",function() {
				//取得图片地址，弹窗显示
				//alert($(this).parent().find("span.pic-address").text());
				
				var table_dive= mouseover(this,event);
				if(!table_dive) return false;
				//alert(table_dive[0]);
				art_dialog("查看身份证信息",table_dive[0],'EA683L',200,460,'50%',"30%");
				
			});
			
			//附件图片信息
			$(".span-see-single").live("click",function() {
				//取得图片地址，弹窗显示
                var path =$(this).parents("div .pic-box").find('img').attr('src');
				
                art_dialog("查看办理人身份证","<img src='"+path+"' width='100%'height='100%'/>",'EA693L');
               
			});
	
	
	});
	
	
	
	//  文件上传
	function uploadcut() {
	var seeionid="";

	//设置disable=true 时提交可以上传值
	$("select").attr("disabled",false);
	$("input").attr("disabled",false);
	//证件有效期验证
	var endyxq_z = $("#yxqend").val();
	if(comp2Nowtime_sub(endyxq_z)){
		$.dialog({ id:'test31', time:3, title:'提示',icon: 'error',content:'证件已出有效期，不能办理业务!'});
		aftersub();
		return;
	}
	debugger;
	if(picpathall.length>5){

		 var url ="";
	     url="<%=basePath_java%>"+"scDocWarController.do;jsessionid=<%=session.getId()%>?addfiles";
	     //alert(url);
	     var sessionid="<%=session.getId()%>";
	     //alert(sessionid);
	     picpathall = picpathall.substring(0,picpathall.length-1);
	     //alert(picpathall);
	     var msg = document.getElementById("uploadFile1").Upload(url,sessionid,picpathall);
	   		
	   		
	     var d=$.parseJSON(msg);
	     if(d!=null&&d.success){
				
				for ( var key in d) {
					if (key.indexOf("F") != -1) {
						var index_ = key.substring(1);
						//然后赋值给予附表字段
						$("input[name='scFileList[" + index_ + "].photo']").val( d[key]);

					} else
					//此处给   主表 附表 的附件字段 赋值（cgform的id）
						if (key.indexOf("Z") != -1) {
							$("input[name='docFile']").val(d[key]);
						}

				}
				//把上传地址赋空
				picpathall="";
				$("#formobj").submit();
	    	
				aftersub();
	     } else{
	    	 $.dialog({ id:'test31', time:5, title:'提示',icon: 'error',content:'操作失败，请重新操作!'});
	     }
	    
	}else{
		debugger;
		$("#formobj").submit();	
		aftersub();
		
	}	
 }
	
	//上传完成的函数废弃
	function CuteCtrl_ItemSelected(filename) {
		//alert(filename);
		var d = $.parseJSON(filename);
		//alert(d.success);
		if (d.success) {
			//var win = frameElement.api.opener;
			//var serverMsg = d.msg;
			var reattr = d.attributes;
			
			for ( var key in reattr) {

				if (key.indexOf("F") != -1) {
					var index_ = key.substring(1);
					//然后赋值给予附表字段
					$("input[name='scFileList[" + index_ + "].photo']").val(
							reattr[key]);

				} else
				//此处给   主表 附表 的附件字段 赋值（cgform的id）
				if (key.indexOf("Z") != -1) {
					$("input[name='docFile']").val(reattr[key]);
				}

			}
			$("#formobj").submit();
			//防止关闭窗口后后台得不到数据
			//window.setTimeout(closeCurrentTab(),2000);
			//alert(serverMsg+re_value1+":"+re_value2);
		} else {
			alert("有误！");

		}
	}

	function subdata() {


	}

	function erro_upload(data) {
		alert(data + "erro");
	}
	var neibuClickFlag = false;
	
	function uploadFile(data){
		
		//alert(data.obj.id);
		debugger;
	if(!$("input[name='id']").val()){
		$("input[name='id']").val(data.obj.id);
		if("${doctype}"=='1'){
			reparameter(data.obj.id,rephototype);
		}
		 
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
		//win.reloadTable(data);
		//win.tip(data.msg);
		if(frameElement.api)
		{
			frameElement.api.close();
		}
		else
		{
			closeCurrentTab();
		}
		}
	}
}

window.onbeforeunload = function() {
	closeartdialog();
}
	
	
</script>

<style type="text/css">
.aui_close{
display:none;
}
</style>
</head>
<body style="overflow-x:hidden;">
 <div class="Current_position">
    	<img src="plug-in/media/image/dingwei.png"/><span>当前所在的位置：</span>
		<a href="javascript:;">基础信息管理</a>&nbsp;
     	 <a href="javascript:;">电子资料库</a>&nbsp;
     	  <a href="javascript:;">电子资料库</a>
    </div>
	<%--引入的demo画面 --%>
	<t:formvalid layout="table" tiptype="4"  action="scDocWarController.do?doAdd"  formid="formobj" dialog="true">
	
		<div class="container form-card">
		
		<input id="id" name="id" type="hidden" value="${scDocWarPage.id }">
		<input id="createName" name="createName" type="hidden"
			value="${scDocWarPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${scDocWarPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${scDocWarPage.createDate }">
		<input id="updateName" name="updateName" type="hidden"
			value="${scDocWarPage.updateName }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${scDocWarPage.updateBy }">
		<input id="sysOrgCode" name="sysOrgCode" type="hidden"
			value="${scDocWarPage.sysOrgCode }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${scDocWarPage.updateDate }">
		<input id="delflag" name="delflag" type="hidden"
			value="${scDocWarPage.delflag }">
		<input id="versionNum" name="versionNum" type="hidden"
			value="${scDocWarPage.versionNum }">
		 <input id="docFile" name="docFile" type="hidden" style="width: 150px" value="${scDocWarPage.docFile }">
		<%-- <input id="ctreateTime" name="ctreateTime" class="form-control w260" type="hidden"   value="${scDocWarPage.ctreateTime }" class="Wdate" onClick="WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd HH:mm:ss'})"> --%>
		<input type="hidden" class="form-control w260" id="docName" name="docName" value="${scDocWarPage.docName }"/>
		<input type="hidden" class="form-control w260" id="docUrl" name="docUrl"value="${scDocWarPage.docUrl}">
		<div class="row" style="display: none">
		    <div class="col-md-12">
		    	<div class="group-title">
					<label>办理人信息</label>
				</div>
		    </div>
		</div>	
			
 		<div class="row" style="display: none">
		    <div class="col-md-12">
		    	<div class="form-group" style="float:left;margin-top:101px !important;width:94px;">
		    		<label class="control-label fl" for="name" >办理人照片</label>
		    	</div>
		    	<div style="float:left;" class="fl">
			    	<div   id='z_photo'class="fl pic-box" style="padding:0px;">
			    		<img src="plug-in/chrhc/images/sfzmodel.png"  width="180px" height="160px" />
			    		<span class="span-bg span-see-single"><i class="pic-icon pic-see"></i></span>
			    	</div>
			    	<div style="float:left; margin-left:80px;">
			    		<ul>
						  
						  
						  </ul>
			    	</div>
			    	<div style="clear:both;padding-top:10px">
			    		<button type="button" class="btn btn-sure"style="padding-left:15px;padding-right:15px;" onClick="ReadIDCARDInfo()">
							<i class="gpyicon gpyicon-read"></i>&nbsp;读取办理人身份证
						</button>
			    	</div>
		    	</div>
		    </div>
		  </div>
		<div class="row" style="display: none">
				<div class="col-md-6">
					<label class="control-label" for="docType">文档类型</label>
					<%--<input type="text" class="form-control w260" id="docType" name="docType" datatype="*1-20" ignore="ignore" > --%>
					<t:dictSelect field="docType" type="list"
						typeGroupCode="doctype"  hasLabel="false"  title="文档类型"></t:dictSelect>  
						<span style="color:red;">*</span>  
				</div>
		</div>
		
		<div class="row" style="display: none">
				<div class="col-md-6">
					<label class="control-label" for="name">姓名</label>
					<input id="name" name="name" type="text" style="width: 150px" class="form-control w260"    /><%-- datatype="s2-15"--%>
					<span style="color:red;">*</span>
				</div>
				<div class="col-md-6">
					<label class="control-label" for="idcardnum">证件号码</label>
					<input  type="text" class="form-control w260" id="idcardnum" name="idcardnum"    /><%--   datatype="s1-20"  validType="sc_doc_war,idcardnum,id" --%>
				<span style="color:red;">*</span>
				</div>
		</div>
		<div class="row" style="display: none">
				<div class="col-md-6">
					<label class="control-label" for="sex">性别</label>
					<%--<input type="text" class="form-control w260" id="sex" name="sex"readonly="readonly"datatype=""/> --%>
					<t:dictSelect field="sex" type="list"
						typeGroupCode="sex"  hasLabel="false"  title="性别" >
						</t:dictSelect>
					<span style="color:red;">*</span>
				</div>
				<div class="col-md-6">
					<label class="control-label" for="birthday">出生日期</label>
					<input id="birthday" name="birthday" class="form-control w260"
						type="text" readonly="readonly" class="Wdate" onClick="WdatePicker({onpicked:function(){(this).blur();},})" ><%--onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" --%>
				</div>
		</div>
		
		<div class="row" style="display: none">
				<div class="col-md-6">
					<label class="control-label" for="nation">民族</label>
					<%--<input type="text" class="form-control w260" id="nation" name="nation" readonly="readonly" datatype="" > --%>
					<t:dictSelect field="nation" type="list"
						typeGroupCode="mz"  hasLabel="false"  title="民族"></t:dictSelect>    
					<span style="color:red;">*</span>
				</div>
				<div class="col-md-6">
					<label class="control-label" for="address">住址</label>
					<input type="text" class="form-control w260" id="address" name="address"  /><%-- datatype="*1-50" --%>
					<span style="color:red;">*</span>
				</div>
		</div>
		<div class="row" style="display: none">
				<div class="col-md-6">
					<label class="control-label" for="yxqstar">开始有效期</label>
					<%--<input type="text" class="form-control w260" class="Wdate" onClick="WdatePicker({onpicked:function(){(this).blur();},})"id="yxqstar" name="yxqstar" readonly="readonly"> --%>
				<input type="text" class="form-control w260" id="yxqstar" name="yxqstar" readonly="readonly" class="Wdate" onClick="WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd'})"datatype="*"  ignore="ignore" Onchange="stardate(this)">
				</div>
				<div class="col-md-6">
					<label class="control-label" for="yxqstar">签发机关</label>
					<input type="text" class="form-control w260" id="qfjg" name="qfjg"   datatype="*1-50"  ignore="ignore" >
				</div>
		</div>
		<div class="row" style="display: none">
				
			<div class="col-md-6">
					<label class="control-label" for="yxqend">结束有效期</label>
					<input type="text" class="form-control w260 "  class="Wdate"  id="yxqend" name="yxqend"readonly="readonly"onClick="WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd',minDate:'1990-08-01'})" datatype="*"  ignore="ignore"  Onchange="enddate(this)"/>
					<%--<input type="text"  class="form-control w260" class="Wdate" onClick="WdatePicker({onpicked:function(){(this).blur();},})" id="yxqend" name="yxqend" readonly="readonly"> ,startDate:'2015-01-01'--%>
				</div>	
			<div class="col-md-6">
					<label class="control-label" for="bz">备注</label>
					<%--<input type="text" class="form-control w260" id="bz" name="bz"datatype="*1-200"  ignore="ignore" /> --%>
					
					<textarea rows="1" cols="20" class="form-control w260" id="bz" name="bz" datatype="*1-200" ignore="ignore"></textarea>
			</div>		
				
				
		</div>
		
		
		<!-- 自定义附表 -->
		<div class="row">
			<div class="col-md-12">
				<div class="group-title">
					<label>材料信息</label>
				</div>
			</div>
		</div>

 		<%--<div class="row">
		    <div class="col-md-12">
		    	<button class="btn btn-default" type="button"><i class="glyphicon glyphicon-plus"></i>&nbsp;添加</button>
		    	<button class="btn btn-default" type="button"><i class="glyphicon glyphicon-trash"></i>&nbsp;删除</button>
		    </div>
		  </div> --%>
		  <div class="row" >
		  	<div class="col-md-12" id="f_table">
		  		

		  	</div>
		  </div>
	</div>
	<div class="row" id="buttonPanel">
				<div class="col-md-12 center">
					<button type="button" class="btn btn-sure" id="docsub" onclick='uploadcheck()'>确定</button>
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




