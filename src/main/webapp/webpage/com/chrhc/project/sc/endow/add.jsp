<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>养老保险参保记录</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <link href="plug-in/chrhc/style.css" rel="stylesheet">
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
   <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="chrhcFormBuildController.do?saveOrUpdate" tiptype="1">
	<input class="btn_sub" id="btn_sub" type="hidden">
		 <section id="mainbox"> 
		 
		  <div class="tab-content tab-box">
		  	<div class="Current_position">
		    	<img src="plug-in/media/image/dingwei.png"><span>当前所在的位置：</span><a href="javascript:;">对象管理</a>
		    </div>
		  	<div class="tabs_btn_div">
		    	<div class="btn" onclick="$('#btn_sub').click();"><i class="fa fa-floppy-o fa-1x"></i><span>&nbsp;保存</span></div>
		        <div class="btn" onclick='parent.$("#editFormDiv").hide();parent.$("#editForm").attr("src","");parent.$(".datagrid").show();'><i class="fa fa-reply fa-1x"></i><span>&nbsp;返回</span></div>
		    	
		    	<div class="btn" onclick="nextRecord(-1);"><i class="fa fa-refresh fa-1x"></i><span>&nbsp;上一条</span></div>
		    	<div class="btn" onclick="nextRecord(1);"><i class="fa fa-refresh fa-1x"></i><span>&nbsp;下一条</span></div> 
		    </div>
		    <ul class="nav nav-tabs">
		      	<li class="active"><a href="#jbxx" data-toggle="tab">基本信息</a></li>
		    </ul>
		    <div class="tab-pane active" id="jbxx">

				  <div class="container form-card">
							<input class="btn_sub" id="btn_sub" type="hidden">
							<input name="tableName" type="hidden" value="sc_endowmentins">
							<input name="id" type="hidden" value="">
							<input name="layerId" type="hidden" value="">
							<input name="create_name" id="create_name" type="hidden" value="">
							<input name="create_by" id="create_by" type="hidden" value="">
							<input name="update_name" id="update_name" type="hidden" value="">
							<input name="update_by" id="update_by" type="hidden" value="">
							<input name="version_num" id="version_num" type="hidden" value="">
							<input name="create_date" id="create_date" type="hidden" value="">
							<input name="update_date" id="update_date" type="hidden" value="">
							<input name="gisxy" id="gisxy" type="hidden" value="">
							<input name="rk_id" id="rk_id" type="hidden" value="">
							<input name="sys_org_code" id="sys_org_code" type="hidden" value="">
							<input name="delflag" id="delflag" type="hidden" value="0">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="code">养老保险编号</label>
									<input name="code" class="form-control w260 Validform_error" id="code" type="text" value="" nullmsg="请输入养老保险编号！" datatype="*1-50">
									<span style="color: red;">*</span>
									<span class="Validform_checktip Validform_wrong">请输入养老保险编号！</span>
									<label class="Validform_label" style="display: none;">养老保险编号</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="name">姓名</label>
									<input name="name" class="form-control w260" id="name" type="text" readOnly="readonly" value="" nullmsg="请输入姓名！" datatype="*1-50" validType="sc_endowmentins,rk_id,id">
					                <span class="suoshu" onclick="inputClick(this,'','sc_rkjbxxnew','rk_id=id,name=xm,sex=xb,nation=mz,birthday=csrq,idcard=sfzh','{&quot;queryType&quot;:&quot;**&quot;,&quot;singleSelect&quot;:true}');"></span>
									<span style="color: red;">*</span>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">姓名</label>
								</div>
							</div>		
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="idcard">身份证号</label>
									<input name="idcard" disabled="" class="form-control w260" id="idcard" type="text" value="" nullmsg="请输入身份证号！" datatype="idcard" validType="sc_endowmentins,idcard,id">
									<span style="color: red;">*</span>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">身份证号</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="insureddate">参保日期</label>
									<input name="insureddate" class="form-control w260" id="insureddate" onclick="WdatePicker({errDealMode:1,maxDate:'2015-8-19'})" type="text" readOnly="readonly" value="" nullmsg="请输入参保日期！" datatype="date">
									<span style="color: red;">*</span>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">参保日期</label>
								</div>
							</div>			
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="insuredstate">参保状况</label>
									<t:dictSelect field="insuredstate" type="list" typeGroupCode="protectstate" defaultVal="${scEndowmentinsPage.insuredstate}" hasLabel="false"  title="参保状况"></t:dictSelect> 
									<span style="color: red;">*</span>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">参保状况</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="sex">性别</label>
									<t:dictSelect field="sex" type="list" typeGroupCode="sex" defaultVal="${scEndowmentinsPage.sex}" hasLabel="false"  title="性别"></t:dictSelect>
									<span style="color: red;">*</span>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">性别</label>
								</div>
							</div>		
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="nation">民族</label>
									<t:dictSelect field="nation" type="list" typeGroupCode="mz" defaultVal="${scEndowmentinsPage.nation}" hasLabel="false"  title="民族"></t:dictSelect>     
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">民族</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="birthday">出生日期</label>
									<input name="birthday" disabled="" class="form-control w260" id="birthday" onclick="WdatePicker({errDealMode:1,maxDate:'2015-8-19'})" type="text" value="" nullmsg="请输入出生日期！" datatype="date">
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">出生日期</label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="phone">联系电话</label>
									<input name="phone" class="form-control w260" id="phone" type="text" value="" nullmsg="请输入联系电话！" datatype="tel" ignore="ignore">
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">联系电话</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="postcode">邮编</label>
									<input name="postcode" class="form-control w260" id="postcode" type="text" value="" nullmsg="请输入邮编！" datatype="p" ignore="ignore">
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">邮编</label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="paddress">户籍地址</label>
									<input name="paddress" class="form-control w260" id="paddress" type="text" value="" nullmsg="请输入户籍地址！" datatype="*1-50" ignore="ignore">
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">户籍地址</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="address">居住地址</label>
									<input name="address" class="form-control w260" id="address" type="text" value="" nullmsg="请输入居住地址！" datatype="*1-50" ignore="ignore">
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">居住地址</label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="holdnation">户籍性质</label>
									<t:dictSelect field="holdnation" type="list" typeGroupCode="householdnation" defaultVal="${scEndowmentinsPage.holdnation}" hasLabel="false"  title="户籍性质"></t:dictSelect>     
									<span style="color: red;">*</span>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">户籍性质</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="insamount">个人缴费额</label>
									<input name="insamount" class="form-control w260" id="insamount" type="text" value="" nullmsg="请输入个人缴费额！" datatype="n1-10" ignore="ignore">
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">个人缴费额</label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<label class="control-label">参加其他养老保险状态:</label>
								<table>
									<tr>
										<td>
											<label class="control-label" for="enterpriseins">企业职工基本养老保险</label>
										</td>
										<td>
											<t:dictSelect field="enterpriseins" type="radio" typeGroupCode="yesno" defaultVal="${scEndowmentinsPage.enterpriseins}" hasLabel="false"  title="企业职工基本养老保险"></t:dictSelect>     
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">企业职工基本养老保险</label>
										</td>
										<td>
											<label class="control-label" for="enterprisedate">起始时间</label>
										</td>
										<td>
											<input name="enterprisedate" class="form-control w260" id="enterprisedate" onclick="WdatePicker({onpicked:function(){(this).blur();},})" type="text" readOnly="readonly" value="" nullmsg="请输入企业养老保险起始时间！">
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">企业养老保险起始时间</label>
										</td>
									</tr>
									<tr>
										<td>
											<label class="control-label" for="landlessins">被征地农民社会保障</label>
										</td>
										<td>
											<t:dictSelect field="landlessins" type="radio" typeGroupCode="yesno" defaultVal="${scEndowmentinsPage.landlessins}" hasLabel="false"  title="被征地农民社会保障"></t:dictSelect>     
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">被征地农民社会保障</label>
										</td>
										<td>
											<label class="control-label" for="landlessinsdate">起始时间</label>
										</td>
										<td>
											<input name="landlessinsdate" class="form-control w260" id="landlessinsdate" onclick="WdatePicker({onpicked:function(){(this).blur();},})" type="text" readOnly="readonly" value="" nullmsg="请输入被征地保障起始时间！">
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">被征地保障起始时间</label>
										</td>
									</tr>
									<tr>
										<td>
											<label class="control-label" for="oldins">老农保</label>
										</td>
										<td>
											<t:dictSelect field="oldins" type="radio" typeGroupCode="yesno" defaultVal="${scEndowmentinsPage.oldins}" hasLabel="false"  title="老农保"></t:dictSelect>     
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">老农保</label>
										</td>
										<td>
											<label class="control-label" for="oldinsdate">起始时间</label>
										</td>
										<td>
											<input name="oldinsdate" class="form-control w260" id="oldinsdate" onclick="WdatePicker({onpicked:function(){(this).blur();},})" type="text" readOnly="readonly" value="" nullmsg="请输入老农保起始时间！">
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">老农保起始时间</label>
										</td>
									</tr>
									<tr>
										<td>
											<label class="control-label" for="otherins">其他保险</label>
										</td>
										<td>
											<t:dictSelect field="otherins" type="radio"	typeGroupCode="yesno" defaultVal="${scEndowmentinsPage.otherins}" hasLabel="false"  title="其他保险"></t:dictSelect>     
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">其他保险</label>
										</td>
										<td>
											<label class="control-label" for="otherinsdate">起始时间</label>
										</td>
										<td>
											<input name="otherinsdate" class="form-control w260" id="otherinsdate" onclick="WdatePicker({onpicked:function(){(this).blur();},})" type="text" readOnly="readonly" value="" nullmsg="请输入其他起始时间！" ignore="ignore">
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">其他保险起始时间</label>
										</td>
									</tr>
								</table>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="specialgroup">特殊参保群体</label>
									<t:dictSelect field="specialgroup" type="radio" typeGroupCode="specialgroup" defaultVal="${scEndowmentinsPage.specialgroup}" hasLabel="false"  title="特殊参保群体"></t:dictSelect>     
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">特殊参保群体</label>
								</div>
							</div>
						</div>
					</div>
		    </div>
		    
		     
	 
		  </div>
		  
		</section>


  
		
							<link href="plug-in/Validform/css/style.css" rel="stylesheet" type="text/css">
							<link href="plug-in/Validform/css/tablefrom.css" rel="stylesheet" type="text/css">
							<script src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js" type="text/javascript"></script>
							<script src="plug-in/Validform/js/Validform_Datatype_zh-cn.js" type="text/javascript"></script>
							<script src="plug-in/Validform/js/datatype_zh-cn.js" type="text/javascript"></script>
							<script src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js" type="text/javascript"></script>
							<script type="text/javascript">
									$(function() {
										$("#formobj").Validform(
										{
											beforeSubmit : function(curform) {
												try {
												//获取disable属性设置为false
												$("[disabled]").removeAttr("disabled");
													return beforeSubmit_();
												} catch (e) {
													return true;
												}
												;
												return true;
											},
											tiptype : "4",
											btnSubmit : "#btn_sub",
											btnReset : "#btn_reset",
											ajaxPost : true,
											usePlugin : {
												passwordstrength : {
													minLen : 6,
													maxLen : 18,
													trigger : function(obj, error) {
														if (error) {
															obj.parent().next().find(
																	".Validform_checktip").show();
															obj.find(".passwordStrength").hide();
														} else {
															$(".passwordStrength").show();
															obj.parent().next().find(
																	".Validform_checktip").hide();
														}
													}
												}
											},
											callback : function(data) {
												if (data.success == true) {
													uploadFile(data);
												} else {
													if (data.responseText == ''
															|| data.responseText == undefined) {
														$.messager.alert('错误', data.msg);
														$.Hidemsg();
													} else {
															try {
																var emsg = data.responseText.substring(
																		data.responseText
																				.indexOf('错误描述'),
																		data.responseText
																				.indexOf('错误信息'));
																$.messager.alert('错误', emsg);
																$.Hidemsg();
															} catch (ex) {
																$.messager.alert('错误',
																		data.responseText + '');
															}
														}
														return false;
													}
													if (!neibuClickFlag) {
											 
													}
												}
											});
										});
					
								
								</script>
	</t:formvalid>
<script type="text/javascript">

	function nextRecord(stepNum){
		var id = $("input[name='id']").val();
		parent.nextRecord(id,stepNum);
	}

   $(function(){
   $('input').iCheck({
				checkboxClass : 'icheckbox_square-blue',
				radioClass : 'iradio_square-blue',
				increaseArea : '20%' 
			});
    //查看模式情况下,删除和上传附件功能禁止使用
	if(location.href.indexOf("load=detail")!=-1){
		$(".jeecgDetail").hide();
	}
	
	if(location.href.indexOf("mode=read")!=-1){
		//查看模式控件禁用
		$("#formobj").find(":input").attr("disabled","disabled");
		$("#cleargis").hide();
		$("#buttonPanel").hide();
		//$("#showgis").show();
		$(".suoshu").hide();
		$("[id^='uploadify_']").hide();
	 
		
		$("#buttonPanel2").show();
		$(".btn-cancel").attr("disabled",false);
		
	}
	
	//新增隐藏上一条下一条	
	if(!$("input[name='id']").val()){
		$(".nextRecord").hide();
	}
  		
  		
	if(location.href.indexOf("mode=onbutton")!=-1){
		//其他模式显示提交按钮
		$("#sub_tr").show();
	}
   });
  function upload() {
  }
  var neibuClickFlag = false;
  function neibuClick() {
	  neibuClickFlag = true; 
	  $('#btn_sub').trigger('click');
  }
  function cancel() {
  }
  

  
 
  
  function uploadFile(data){

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
  			//getParentWindow();
  				//alert(getParentWindow().document.body.innerHTML);
  			
  				var win = getParentWindow();
  				
				//处理是否户主的亲属关系开始
  				if($("select[name='sfhz']").length){
		  			whqsgx(data);
		  		}
				//处理是否户主的亲属关系结束
  			    //处理家庭信息户主回写开始
  		
  				var urldata = getUrlData();
				if(urldata.jthz){
				var iframejt = win.$("#editForm")[0].contentWindow;
				iframejt.whjtxx(data);
				parent.closeCurrentTab();
				return;
				}
	  			//处理家庭信息户主回写结束
	  			
	  			//处理快捷业务回写开始
	  			if(urldata.docWarId){
				win.showBusinessBtn(data.obj.sfzh);
				parent.closeCurrentTab();
				return;
				}
	  			//处理快捷业务回写结束
  				
	  			
	  			///alert(win);
				//GIS编辑时，回调GIS方法
				if(win)
				{
			  		if($("input[name='layerId']").val()){
			  			win.businessCallBackDrawPointOrVector($("input[name='layerId']").val(),data.obj.id,data.obj.name,data.obj.gisxy);
			  		}
					parent.reloadTable(data);
					win.tip(data.msg);
					
				}
				
				
				parent.$("#editFormDiv").hide();
				parent.$("#editForm").attr("src","");
				parent.$(".datagrid").show();


  			}
  		}
  	}
	//$.dialog.setting.zIndex = 999999999;
	function delFile(url,obj){
	$.dialog.setting.zIndex = $.dialog.setting.zIndex+20;
		$.dialog.confirm("确认删除该条记录?", function(){
		  	$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : url,// 请求的action路径
				error : function() {// 请求失败处理函数
				},
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var msg = d.msg;
						tip(msg);
						$(obj).closest("li").hide("slow");
						$("img",$(obj).closest("div").parent()).remove();
					}
				}
			});  
		}, function(){
		}).zindex();
	}

	/**
	 * 下载flash安装文件
	 */
	function downLoadFlashFile(){
		var filePath = "downfiles/flash/flashplayer17ax_ra_install.exe";
		window.open(filePath,"下载",'height=100, width=400, top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes,alwaysLowered=yes');
	}
	/**
	 * 下载驱动安装文件
	 */
	function downLoadDriver(){
		var filePath = "http://192.168.1.73:8080/sc/downfiles/sclient.msi ";
		window.open(filePath,"下载",'height=100, width=400, top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes,alwaysLowered=yes');
	}
	

		//切换tab校验
		$('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
			if(!$('#formobj').Validform().check(false,".active [datatype]")){
				return false;
			}
			e.target ;// 激活的标签页
			e.relatedTarget; // 前一个激活的标签页
		})		
	
</script>
	<script src="plug-in/chrhc/enhancejs/sc_endowmentins_form.js" type="text/javascript"></script>	
 

 
</body>
 
