<#setting number_format="################.####">
<#setting date_format="yyyy-MM-dd HH:mm:ss">

<!DOCTYPE html>
<html>
 	<#include "jformhtmlhead.ftl">
 <body style="overflow-y: scroll" >
  <form id="formobj" action="chrhcFormBuildController.do?saveOrUpdate" name="formobj" method="post" class="form-inline valideForm">
  <div class="container form-card">
			<input type="hidden" id="btn_sub" class="btn_sub"/>
			<input type="hidden" name="tableName" value="${tableName?if_exists?html}" >
			<input type="hidden" name="id" value="${id?if_exists?html}" >
			<input type="hidden" name="layerId" value="${layerId?if_exists?html}" >
			<#if bizType?? && bizType!=''> <input type="hidden" name="biztype" value="${bizType}" > </#if>
			<#global typetitle_index=0>  
			<#list columnhidden as po>
			<#if po.field_name!='biztype'>
				
				<#if po.show_type=='date' || po.show_type=='datetime'||po.type=='Date'>
					<input type="hidden" id="${po.field_name}" name="${po.field_name}" value="${data['${tableName}']['${po.field_name}']?if_exists?html}" >
				<#else>
					<input type="hidden" id="${po.field_name}" name="${po.field_name}" value="${data['${tableName}']['${po.field_name}']?if_exists?html}" >
				</#if>
			 
			</#if>
			</#list>
			<div class="row">
				<div class="col-md-12">
					<div class="group-title">
						<label>基本信息</label>
					</div>
				</div>
			</div>
			<!--	<table cellpadding="0" cellspacing="1" class="formtable">-->
				<#global pocl_index=0>
			   <#list columns as po>
			    <#if (columns?size>10)>
					<#if (pocl_index-typetitle_index)%2==0>
						<!--<tr>-->
						<div class="row">
					</#if>
				<#else>
					<!--<tr>-->
						<div class="row">
				</#if>
			
					<#if po.show_type=='typetitle'>
	
						<#if ((pocl_index-typetitle_index-1)%2==0 )>
							<!--<td align="right">
								<label class="Validform_label">
								</label>
							</td>
							<td class="value">
							</td>
							</tr>-->
							<div class="col-md-6" >
							<div class="form-group">
							</div>
							</div>
							</div>
						<#else> 
							<!-- </tr> -->
							</div>
						</#if>
					
				 
					<!--</table>
						<#global typetitle_index=(pocl_index+1)> 
						${po.content}<br/> 
					<table cellpadding="0" cellspacing="1" class="formtable">
					 	<tr>-->
					 	<#global typetitle_index=(pocl_index+1)> 
						<div class="row">
						<div class="col-md-12">
						<div class="group-title">
						<label>${po.content}</label>
						</div>
						</div>
						</div>
			
					<#else>
						<#if po.show_type=='file'>
							<#if ((pocl_index-1)%2==0 )>
								<div class="col-md-6" >
								<div class="form-group">
								</div>
								</div>
								</div>
								<div class="row">
							<#else>
								<#global pocl_index=(pocl_index+1)>
							</#if>
						    <div class="col-md-12">
						    	<div class="form-group" style="float:left;">
								<label class="control-label fl" style="margin-top:20px;" for="${po.field_name}">${po.content}</label>
						<#else><!--if po.show_type != 'file' -->
							<#if po.show_type=='checkbox'>
								<#if ((pocl_index-1)%2==0 )>
									<div class="col-md-6" >
									<div class="form-group">
									</div>
									</div>
									</div>
									<div class="row">
								<#else>
									<#global pocl_index=(pocl_index+1)>
								</#if>
							    <div class="col-md-12">
							    	<div class="form-group" style="float:left;">
									<label class="control-label fl"  for="${po.field_name}">${po.content}</label>
							<#else>
								<div class="col-md-6" >
									<div class="form-group">
									<label class="control-label" for="${po.field_name}">
									${po.content}
									<!--<#if po.is_null=='N'>
											<span style="color: red;">*</span>
											</#if>	-->
									</label>
									<!--<td align="right" >
										<label class="Validform_label">
											${po.content}:
											<#if po.is_null=='N'>
											<span style="color: red;">*</span>
											</#if>	
										</label>
									</td>-->
									<!--<td class="value">-->
							</#if>
							<#assign field_name = po.field_name>
							<#assign field_value = data['${tableName}']['${po.field_name}']?if_exists?html>
						</#if>
						<#if po.show_type == 'date' || po.show_type == 'datetime'>
						   
						    <#if po.type == 'string'>
						    <#assign date_field_value = data['${tableName}']['${po.field_name}']?if_exists?html>
						    <#assign datetime_field_value = data['${tableName}']['${po.field_name}']?if_exists?html>
						    <#else>
						    
							 <#assign date_field_value = data['${tableName}']['${po.field_name}']?if_exists?html>
						    <#assign datetime_field_value = data['${tableName}']['${po.field_name}']?if_exists?html>
							</#if>
						</#if>

					<#include "jformfield.ftl">
					<#if po.is_null=='N'>
						<span style="color: red;">*</span>
					</#if>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">${po.content?if_exists?html}</label>
						
					<!--</td> -->
					</div>
					</div>
					<#if (columns?size>10)>
					<#if ((pocl_index-typetitle_index)%2!=0)||(!po_has_next)>
						
						</div>
					</#if>
					<#else>
						</div>
					</#if>
				</#if>
					
				<!--<#if (columns?size>10)>
					<#if ((po_index-typetitle_index)%2==0)&&(!po_has_next)>
					<td align="right">
						<label class="Validform_label">
						</label>
					</td>
					<td class="value">
						
					</td>
					</#if>
					<#if ((po_index-typetitle_index)%2!=0)||(!po_has_next)>
						<!--</tr>
						</div>
					</#if>
				<#else>
					<!--</tr>
						</div>
				</#if>-->
				<#global pocl_index=(pocl_index+1)>
			  </#list>
			  
			  <#list columnsarea as po>
			  <#if (columns?size>10)>
			    <!--  <tr>
					<td align="right">
						<label class="Validform_label">
							${po.content}:
							<#if po.is_null=='N'>
							<span style="color: red;">*</span>
							</#if>	
						</label>
					</td>
					<td class="value" colspan="3">
						<textarea id="${po.field_name}" name="${po.field_name}" 
						       style="width: 600px" class="inputxt" rows="6"
						<#if po.operationCodesReadOnly?if_exists> readonly = "readonly"</#if>
						<#if po.validate_attr?if_exists?html != ''>
					              	${po.validate_attr}
					                </#if>
				               <#if po.field_valid_type?if_exists?html != ''>
				               datatype="${po.field_valid_type?if_exists?html}"
				               <#else>
				               <#if po.is_null != 'Y'>datatype="*"</#if> 
				               </#if>>${data['${tableName}']['${po.field_name}']?if_exists?html}</textarea>
				               <br/>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">${po.content?if_exists?html}</label>
					</td>
				</tr> -->
				<div class="row">
					<div class="col-md-12">
						<label class="control-label">
						${po.content}
							<!--<#if po.is_null=='N'>
							<span style="color: red;">*</span>
							</#if>	-->
						</label>
						<textarea id="${po.field_name}" name="${po.field_name}" 
						        class="form-control" rows="3" cols="80" 
						<#if po.operationCodesReadOnly?if_exists> readonly = "readonly"</#if>
						<#if po.validate_attr?if_exists?html != ''>
					              	${po.validate_attr}
					                </#if>
				               <#if po.field_valid_type?if_exists?html != ''>
				               datatype="${po.field_valid_type?if_exists?html}"
				               <#else>
				               <#if po.is_null != 'Y'>datatype="*"</#if> 
				               </#if>>${data['${tableName}']['${po.field_name}']?if_exists?html}</textarea>
				         <#if po.is_null=='N'>
						<span style="color: red;">*</span>
						</#if>     
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">${po.content?if_exists?html}</label>
						
					</div>
				</div>
				<#else>
				<!--<tr>
					<td align="right">
						<label class="Validform_label">
							${po.content}:
							<#if po.is_null=='N'>
							<span style="color: red;">*</span>
							</#if>	
						</label>
					</td>
					<td class="value">
						<textarea id="${po.field_name}" name="${po.field_name}" 
						       style="width: 300px" class="inputxt" rows="6"
						<#if po.operationCodesReadOnly?if_exists> readonly = "readonly"</#if>
						<#if po.validate_attr?if_exists?html != ''>
					              	${po.validate_attr}
					                </#if>
				               <#if po.field_valid_type?if_exists?html != ''>
				               datatype="${po.field_valid_type?if_exists?html}"
				               <#else>
				               <#if po.is_null != 'Y'>datatype="*"</#if> 
				               </#if>>${data['${tableName}']['${po.field_name}']?if_exists?html}</textarea>
				               <br/>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">${po.content?if_exists?html}</label>
					</td>
				</tr> -->
				<div class="row">
					<div class="col-md-12">
						<label class="control-label">
						${po.content}
							<!--<#if po.is_null=='N'>
							<span style="color: red;">*</span>
							</#if>	-->
						</label>
						<textarea id="${po.field_name}" name="${po.field_name}" 
						        class="form-control" rows="3" cols="80" 
						<#if po.operationCodesReadOnly?if_exists> readonly = "readonly"</#if>
						<#if po.validate_attr?if_exists?html != ''>
					              	${po.validate_attr}
					                </#if>
				               <#if po.field_valid_type?if_exists?html != ''>
				               datatype="${po.field_valid_type?if_exists?html}"
				               <#else>
				               <#if po.is_null != 'Y'>datatype="*"</#if> 
				               </#if>>${data['${tableName}']['${po.field_name}']?if_exists?html}</textarea>
				           <#if po.is_null=='N'>
							<span style="color: red;">*</span>
							</#if>   
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">${po.content?if_exists?html}</label>
						
					</div>
				</div>
				</#if>
			  </#list>
			 <!-- <tr id = "sub_tr" style="display: none;">
				  <td colspan="2" align="center">
				  <input type="button" value="提交" onclick="neibuClick();" class="ui_state_highlight">
				  </td>
			  </tr>-->
			</table>
			<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css"/><link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css"/><script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script><script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script><script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script><SCRIPT type="text/javascript" src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></SCRIPT><script type="text/javascript">$(function(){$("#formobj").Validform({beforeSubmit:function(curform){try{return beforeSubmit_(); }catch(e){return true;} ;return true;},tiptype:"<#if head.tipType?? && head.tipType!=''> ${head.tipType} <#else>4</#if>",btnSubmit:"#btn_sub",btnReset:"#btn_reset",ajaxPost:true,usePlugin:{passwordstrength:{minLen:6,maxLen:18,trigger:function(obj,error){if(error){obj.parent().next().find(".Validform_checktip").show();obj.find(".passwordStrength").hide();}else{$(".passwordStrength").show();obj.parent().next().find(".Validform_checktip").hide();}}}},callback:function(data){if(data.success==true){uploadFile(data);}else{if(data.responseText==''||data.responseText==undefined){$.messager.alert('错误', data.msg);$.Hidemsg();}else{try{var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'),data.responseText.indexOf('错误信息')); $.messager.alert('错误',emsg);$.Hidemsg();}catch(ex){$.messager.alert('错误',data.responseText+'');}} return false;}if(!neibuClickFlag){var win = frameElement.api.opener; win.reloadTable();}}});});</script>
			<div class="row" id="buttonPanel">
				<div class="col-md-12 center">
					<button type="submit" class="btn btn-sure">确定</button>
          			<button type="button" class="btn btn-cancel" onclick="closeCurrentTab()">关闭</button>
				</div>
			</div>
	</div>
	</form>
<script type="text/javascript">
   $(function(){
   $('input').iCheck({
				checkboxClass : 'icheckbox_square-blue',
				radioClass : 'iradio_square-blue',
				increaseArea : '20%' 
			});
   <#list columns as po>
  		<#if po.show_type=='popupgis'>
  		var gis = $('#${po.field_name}').val().length;	
  		if(gis){
  		$("#showgis").show();
  		}	
  		</#if>
  	</#list>
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
	}
	if(location.href.indexOf("mode=onbutton")!=-1){
		//其他模式显示提交按钮
		$("#sub_tr").show();
	}
   });
  function upload() {
  	<#list columns as po>
  		<#if po.show_type=='file'>
  		$('#${po.field_name}').uploadify('upload', '*');		
  		</#if>
  	</#list>
  }
  var neibuClickFlag = false;
  function neibuClick() {
	  neibuClickFlag = true; 
	  $('#btn_sub').trigger('click');
  }
  function cancel() {
  	<#list columns as po>
  		<#if po.show_type=='file'>
 	 $('#${po.field_name}').uploadify('cancel', '*');
 	 	</#if>
  	</#list>
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
	  			///alert(win);
				//GIS编辑时，回调GIS方法
				if(win)
				{
			  		if($("input[name='layerId']").val()){
			  			win.businessCallBackDrawPointOrVector($("input[name='layerId']").val(),data.obj.id,data.obj.name,data.obj.gisxy);
			  		}
					win.reloadTable(data);
					win.tip(data.msg);
				}
				if($("select[name='sfhz']").length){
		  		whqsgx(data);
		  		}
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
</script>
	${js_plug_in?if_exists}	
 </body>
</html>