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
<title>审批页面</title>
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
<script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js?self=true&skin=chrome"></script>
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
<%-- --%>	<script type="text/javascript"	src="plug-in/artDiglog/jquery.artDialog.js?skin=default"></script>
<%-- --%>	<script type="text/javascript"	src="plug-in/artDiglog/iframeTools.source.js"></script>
<%--  validform --%>	
<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css"/>
<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css"/>
<script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>
<style type="text/css">
.aui_close{
display:none;
}

</style>
</head>
<body style="overflow-x:hidden;">
<div class="Current_position">
    	<img src="plug-in/media/image/dingwei.png"/><span>当前所在的位置：</span>
		
     	 <a href="javascript:;">个人办公</a>&nbsp;
     	  <a href="javascript:;">审批详情</a>
    </div>

	<%--引入的demo画面 --%>
	<t:formvalid layout="table" tiptype="4"  action="scGzptlcController.do?doUpdate"  formid="formobj" dialog="true">
	
	<%-- <form class="form-inline valideForm" action="scDocWarController.do?doAdd" id="formobj" method="post" name="formobj" id="formobj"> --%> 
		<div class="container form-card">
		<input type="hidden" name="MAX_FILE_SIZE" value="10485760"><%--//上传控件的输入 --%>
		<input id="id" name="id" type="hidden" value="${scGzptlcPage.instanceID}"/>
		<input id="instanceID" name="instanceID" type="hidden" value="${scGzptlcPage.instanceID}"/>
		<input id="acpcode" name="acpcode" type="hidden" value="${scGzptlcPage.acceptcode}"/>
		<input id="declare_id_num" name="declare_id_num" type="hidden" value="${scGzptlcPage.declare_id_num}"/>
		<input id="url" name="url" type="hidden" value="http://10.61.160.7:8280/cpub-back-web/api/app/netservice/declare/submitApprove?"/>
		
		
			
 		<div class="row" style="display: none">
		    <div class="col-md-12">
		    	
		    	<div  style="float:left;" class="fl">
			    	<div id='z_photo'class="fl pic-box" style="padding:0px;">
			    		
			    		<c:if test="${fn:length(scDocWarPage.docFile)<1}">
				    	<img src="plug-in/chrhc/images/sfzmodel.png"   width="180px" height="160px"/>
				    	</c:if>
				    	<c:if test="${fn:length(scDocWarPage.docFile)>1}">
				    	<img src='commonController.do?viewFile&fileid=${scDocWarPage.docFile}&subclassname=org.jeecgframework.web.system.pojo.base.TSAttachment' width="180px" height="160px" />
				    	
				    	</c:if>
			    		<span class="span-bg span-see-single"><i class="pic-icon pic-see"></i></span>
			    	</div>
			    	<div style="float:left; margin-left:80px;">
			    		<ul>
						  </ul>
			    	</div>
		    	</div>
		    </div>
		  </div>
		  <div class="row" style="display: block">
				<div class="col-md-6">
					<label class="control-label" for="name">受理类型</label>
					<input id="name" name="name" type="text" style="width: 150px" readonly="readonly"class="form-control w260"  value="${scGzptlcPage.biz_type}"/>
					
				</div>
				<div class="col-md-6">
					<label class="control-label" for="bizName">受理名称</label>
					<input  type="text" class="form-control w260" id="bizName" name="bizName"  readonly="readonly" value="${scGzptlcPage.biz_name}"/><%--validType="sc_doc_war,idcardnum,id" --%>
				
				</div>
		</div>
		<div class="row" style="display: block">
				<div class="col-md-6">
					<label class="control-label" for="name">申请人姓名</label>
					<input id="name" name="name" type="text" style="width: 150px" readonly="readonly"class="form-control w260"  value="${scGzptlcPage.declare_per_name}"/>
					
				</div>
				<div class="col-md-6">
					<label class="control-label" for="idcardnum">证件号码</label>
					<input  type="text" class="form-control w260" id="idcardnum" name="idcardnum"  readonly="readonly" value="${scGzptlcPage.declare_id_num}"/><%--validType="sc_doc_war,idcardnum,id" --%>
				
				</div>
		</div>
		<div class="row" style="display: block">
				<div class="col-md-6">
					<label class="control-label" for="sex">联系电话</label>
					<input type="text" class="form-control w260" id="lxdh" name="lxdh"readonly="readonly"value="${scGzptlcPage.declare_phone}">
					<%--<t:dictSelect field="sex" type="list"
						typeGroupCode="sex"  hasLabel="false"  title="性别" defaultVal="${scDocWarPage.sex}" >
						</t:dictSelect> --%>
					
				</div>
				
				<div class="col-md-6">
					<label class="control-label" for="birthday">邮箱</label>
					<input id="birthday" name="birthday" class="form-control w260" value="${scGzptlcPage.declare_email}"
						type="text" readonly="readonly"/><%--onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" --%>
				
		</div>
		</div>
		<div class="row" style="display: block">
				<div class="col-md-6">
					<label class="control-label" for="nation">申请人地址</label>
					<input type="text" class="form-control w260" id="nation" name="nation" readonly="readonly"value="${scGzptlcPage.address}"/>
					<%--<t:dictSelect field="nation" type="list"
						typeGroupCode="mz"  hasLabel="false"  title="民族" defaultVal="${scDocWarPage.nation}"></t:dictSelect>  --%> 
						
				</div>
				<div class="col-md-6">
					<label class="control-label" for="address">受理区域</label>
					<input type="text" class="form-control w260" id="address"readonly="readonly" name="address"   value="${scGzptlcPage.slqy}"/>
				
				</div>
		</div>
	
		<div class="row" style="display: block">
				<div class="col-md-6">
					<label class="control-label" for="yxqstar">审批状态</label>
				
						<select id="department_approve_state"name="department_approve_state" class="form-control w260" datatype="*1-5" >
						<option value="">请选择</option>
						<c:choose> 
			  				<c:when test="${empty scGzptlcPage.department_approve_state}"> 
			  					<option value="1">通过</option>
								<option value="0">未通过</option>
			  				</c:when>
			  				<c:when test="${scGzptlcPage.department_approve_state eq '1'}"> 
			  				 <option value="1" selected>通过</option>
							<option value="0">未通过</option>
			  				</c:when>
			  				<c:when test="${scGzptlcPage.department_approve_state eq '0'}"> 
			  				 <option value="1" >通过</option>
							<option value="0"selected>未通过</option>
			  				</c:when>
			  				<c:otherwise>
			  				 <option value="1">通过</option>
							<option value="0">未通过</option>
							
			  				</c:otherwise>
			  				</c:choose>
						
						</select>
						<span style="color:red;">*</span>
				</div>
			<div class="col-md-6">
					<label class="control-label" for="address">受理部门</label>
					<input type="text" class="form-control w260" id="address" name="address"  readonly="readonly" value="${scGzptlcPage.depart}"/>
				
				</div>
				
				
				
		</div>
		<div class="row" style="display: block">
		<div class="col-md-6">
					<label class="control-label" for="bz">审批意见</label>
					<%-- -- <input type="text" class="form-control w260" id="bz" name="bz"datatype="*1-200"  ignore="ignore" value="${scDocWarPage.bz}"/> ${scGzptlcPage.spyj} --%>
					
					<c:choose>
					<c:when test="${empty scGzptlcPage.spyj}">
					<textarea rows="2" cols="40" class="form-control w260"  id="spyj" name="spyj"  datatype="*2-70"></textarea>
					</c:when>
					<c:otherwise>
					<c:if test="${scGzptlcPage.spyj eq 'null'}">
					<textarea rows="2" cols="40" class="form-control w260"  id="spyj" name="spyj"  datatype="*2-70"></textarea>
					</c:if>
					<c:if test="${scGzptlcPage.spyj ne 'null'}">
					<textarea rows="2" cols="40" class="form-control w260"  id="spyj" name="spyj"  datatype="*2-70">${scGzptlcPage.spyj}</textarea>
					</c:if>
					
					</c:otherwise>
					</c:choose>
					<span style="color:red;">*</span>
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
		<div class="row" style="display: block">
		 <c:forEach items="${scGzptlcPage.fj}" var="fj" varStatus="stuts">
		<div class="col-md-6">
				<label class="control-label" for="yxqend">${fj['stuffName']}:</label>
				<c:if test="${fj['stuffDelivery'] eq 1}">
				<a href="${fj['attachmentRealPath']}" target="_blank" style="color:red;text-decoration:underline">预   &ensp;&ensp;览</a>
				</c:if>
				<c:if test="${fj['stuffDelivery'] ne 1}">
				<span style="color:red;font-size:26">现场递交</span>
				</c:if>
				</div>	
		</c:forEach>
		</div>
				
		
		
		
		  
		  
	</div>
	
	<div class="row" id="buttonPanel">
				<div class="col-md-12 center">
				<c:if test="${flag eq 'up'}">
					<button type="button" class="btn btn-sure" id="docsub" onclick='sub()'>确定</button>
				</c:if>
          			<button type="button" class="btn btn-cancel"id='closebut' onclick="closeCurrentTab()">关闭</button>
				</div>
	</div>
		
	
	
	</t:formvalid>
	
	<%-- 悬停窗口div 
<div id="xtxs" style="display:none,height:300 , width:200">
<table>
<tr>
<td>姓名：</td>
<td id="name"></td>
<td>证件号码：</td>
<td id="cardnum"></td>
</tr>
<tr>
<td >性别：</td>
<td id="sex"></td>
<td>出生日期：</td>
<td id="birthday"></td>
</tr>
<tr>
<td>民族：</td>
<td id="nation"></td>
<td>住址：</td>
<td id="address"></td>
</tr>
<tr>
<td >开始有效期</td>
<td id ="yxqstar"></td>
<td>结束有效期</td>
<td id="yxqend"></td>
</tr>
</table>
</div>--%>
	

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
function sub(){
	 var date = new Date();
     var d_ss=date.getMilliseconds();
     $("#url").val($("#url").val()+d_ss);
	//alert($("#acpcode").val());
	//alert("${scGzptlcPage.instanceID}");
	$("#formobj").submit();	
	//closeCurrentTab();

	}

//提交完callback
function uploadFile(data){
	debugger;
if(!$("input[name='id']").val()){
	$("input[name='id']").val(data.obj.id);
};
if($(".uploadify-queue-item").length>0){
	upload();
}else{
	var tlnum=$("#lxdh").val();
	var msg=data.obj;
	var acpcod=$("#acpcode").val();
	var bizname=$("#bizName").val();
	var spyj=$("#spyj").val();
	var almsg="受理编号:"+acpcod+"。业务名称："+bizname+"。审核状态："+msg+"。审批意见："+spyj;
	//alert(almsg);
	sentmsg(almsg,tlnum);
	//var win = getParentWindow();
	//win.reloadTable(data);
	//win.tip(data.msg);
	if(frameElement.api)
	{
		frameElement.api.close();
	}
	else
	{
		var spflag =$("#department_approve_state").val();
		if(spflag=='0'){
			var win = getParentWindow();
			win.reloadTable(data);
			win.tip(data.msg);
			closeCurrentTab();
			}else{

				}
		var idcard=$("#idcardnum").val();
		var rkid="";
///  
		debugger;
		$.ajax({
		    type: 'post',
		    url: 'scQuickBusinessController.do?checkInRkkxx',
		    data: {
		    	EQ_sfzh:idcard
		    		    	
		    	},
		    async:false,
		    success: function(data) {
			    debugger;
		    	var d = $.parseJSON(data);
				//is = d.success;
		    	//无论是否更新，身份证信息都合法，可以显示可办理模块信息
		    	//getModelTypeBtn($("#idcardnum").val(),d.obj);
				rkid=d.obj;
								
		    }
		});
		//////
		$("#docsub").attr("disabled",true);
		debugger;
		var bizname=$("#bizName").val();
		var url ="";
		if(bizname=='申请残疾证'){
			 url = 'chrhcFormBuildController.do?ftlForm&tableName=sc_deformity&rk_id='+rkid+'&sfzh='+idcard;
			 parent.parent.addTab("残疾人证件申报受理", encodeURI(url));	
		}else if(bizname=='德城区城乡困难居民医疗救助'){
			 url = 'chrhcFormBuildController.do?ftlForm&tableName=sc_dbyljzhshq&bizType=&rk_id='+rkid+'&sfzh='+idcard;	
			 parent.parent.addTab("大病医疗救助申请", encodeURI(url));
		}
			;
		//$("#editFormDiv").show();
		//$("#editForm").attr("src",url); 
		//var win = getParentWindow();
		//win.closeCurrentTab();
		//setTimeout('closeCurrentTab()',2000);
		
		
	}
	}
}

//通过身份证号，判断人口库是否存在该身份证
/* function isExistInRkkxx(id_num){
	var is = true;
	$.ajax({
		url: 'scQuickBusinessController.do?isExistInRkkxx',
		type : 'POST',
		dataType : 'text',
		data : {
			certId : id_num,
			configId : 'sc_rkjbxxnew'
		},
		async : false,
		cache : false,
		success: function(data){
			var d = $.parseJSON(data);
			is = d.success; 
			if(is){
				rk_id = d.obj.id;
				//alert(rk_id);
			}
		}
	});
	
	return is;
} */
$(document).ready(function() {
	var flag ="${flag}";
	if(flag=='vi'){
		$(":input").attr("disabled",true);
		$("#closebut").attr("disabled",false);
	}
	(function (config) {  
	    //config['lock'] = true;  
	    config['fixed'] = true;  
	    config['okVal'] = 'Ok';  
	    config['cancelVal'] = 'Cancel';  
	    config['prompt']=function(a){};  
	})(art.dialog.defaults);
      /*   $("#btn").click(function(){
        	sentmsg("123456","123");
        });  */
        	

});
//发送消息js
function sentmsg(msg,tlnum){
///
	debugger;
	//tlnum="18553136561";
	//alert(tlnum);
	var host = window.location.host;
		var pathname=window.location.pathname;
		var a = pathname.split('/');
		var url_="http://"+host+"/"+a[1]+"/scGzptlcController.do?sendmsg&msg="+msg+"&telnum="+tlnum;
		 $.ajax({type: "GET",
			 url:encodeURI(url_),
			 async:false,
			 success: function(data){
				 debugger;
				 if(data){
				data =data.replace(/(^\"*)/g, "");
				 data =	data.replace(/(\"*$)/g, "");
					 }
				 if(data=="1"){
					 /* art.dialog({
						title:"提示",
						time:3,
						content:"发送消息成功"
						});   */
						//var win = getParentWindow();
						//win.tip("消息发送成功");
						//alert("发送消息成功");
				 }
				 },
				 error:function(XMLHttpRequest, textStatus, errorThrown){
					 /* art.dialog({
						title:"提示",
						time:3,
						content:"发送消息失败"
						}); */ 
						//alert("发送消息失败");
						//var win = getParentWindow();
						//win.tip("发送消息失败");
					  return false;
				}
		 });
			
}

</script>	
</body>
<script src="webpage/com/chrhc/project/sc/docfile/scDocWar.js">
</script>
<%----%>

</html>
