<#setting number_format="0.#####################">
<!DOCTYPE html>
<html>
 <#include "jformhtmlhead.ftl">
 <script type="text/javascript">
 $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	$(".tabs-wrap").css('width','100%');
  });
  //初始化下标
	function resetTrNum(tableId) {
		$tbody = $("#"+tableId+"");
		$tbody.find('>tr').each(function(i){
			$(':input, select', this).each(function(){
				var $this = $(this), name = $this.attr('name'), val = $this.val();
				if(name!=null){
					if (name.indexOf("#index#") >= 0){
						$this.attr("name",name.replace('#index#',i));
					}else{
						var s = name.indexOf("[");
						var e = name.indexOf("]");
						var new_name = name.substring(s+1,e);
						$this.attr("name",name.replace(new_name,i));
					}
				}
			});
			$(this).find('div[name=\'xh\']').html(i+1);
		});
	}
 </script>
 <body>
  <form id="formobj" action="chrhcFormBuildController.do?saveOrUpdateMore" name="formobj" method="post" class="form-inline valideForm">
  <input type="hidden" id="btn_sub" class="btn_sub" />
  <div class="container form-card">
	<#include "jformhead.ftl">
			
			
<script type="text/javascript">
   $(function(){
    $('#tabchrhc').Tabs({
		event : 'click'
	});
	$('input').not('.checkboxchrhc input').iCheck({
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
	$.dialog.setting.zIndex =1990;
	function delFile(url,obj){
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
					}
				}
				});  
			}, function(){
		});
	}
	/**
	 * 下载flash安装文件
	 */
	function downLoadFlashFile(){
		var filePath = "downfiles/flash/flashplayer17ax_ra_install.exe";
		window.open(filePath,"下载",'height=100, width=400, top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes,alwaysLowered=yes');
	}
</script>
			<div style="width: auto;height: auto;">
				<div style="width:690px;height:1px;"></div>
				<div class="row">
				<div class="col-md-12">
					<div class="box demo" id="tabchrhc">
			<!--	<div id="tt" tabPosition="top" border=flase style="margin:0px;padding:0px;overflow:hidden;width:auto;" class="easyui-tabs" fit="false"> -->
				<#assign subTableStr>${head.subCode?if_exists?html}</#assign>
				<#assign subtablelist=subTableStr?split(",")>
				<ul class="tab_menu">
				<#list subtablelist as sub >
				    <#if sub_index == 0>
				    <li class="current">${field['${sub}'].head.content?if_exists?html}</li>
				    <#else>
				      <li>${field['${sub}'].head.content?if_exists?html}</li>
				    </#if>
				</#list>
				</ul>
				<div class="tab_box">
				<#list subtablelist as sub >
				    <#if field['${sub}']?exists >
				    	<#if sub_index == 0>
				    	<div>
				    	<#else>
				    	<div class="hide">
				    	</#if>
				    	<#if field['${sub}'].head.relationType==1 >
					    <#include "jformonetoone.ftl">
					    <#else>
					    <#include "jformonetomany.ftl">
					    </#if>
					    </div>
					</#if>
				</#list>
				</div>
				<!--</div> -->
				</div>
				</div>
				</div>
			</div>
		<div align="center"  id = "sub_tr" style="display: none;" > <input type="button" value="提交" onclick="neibuClick()" class="ui_state_highlight"></div>
		<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css"/><link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css"/><script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script><script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script><script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script><SCRIPT type="text/javascript" src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></SCRIPT><script type="text/javascript">$(function(){$("#formobj").Validform({beforeSubmit:function(curform){try{return beforeSubmit_(); }catch(e){return true;} ;return true;},tiptype:"<#if head.tipType?? && head.tipType!=''> ${head.tipType} <#else>4</#if>",btnSubmit:"#btn_sub",btnReset:"#btn_reset",ajaxPost:true,usePlugin:{passwordstrength:{minLen:6,maxLen:18,trigger:function(obj,error){if(error){obj.parent().next().find(".Validform_checktip").show();obj.find(".passwordStrength").hide();}else{$(".passwordStrength").show();obj.parent().next().find(".Validform_checktip").hide();}}}},callback:function(data){if(data.success==true){uploadFile(data);}else{if(data.responseText==''||data.responseText==undefined){$.messager.alert('错误', data.msg);$.Hidemsg();}else{try{var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'),data.responseText.indexOf('错误信息')); $.messager.alert('错误',emsg);$.Hidemsg();}catch(ex){$.messager.alert('错误',data.responseText+'');}} return false;}if(!neibuClickFlag){var win = frameElement.api.opener; win.reloadTable();}}});});</script>
		<div class="row" id="buttonPanel">
				<div class="col-md-12 center">
					<button type="submit" class="btn btn-sure">确定</button>
          			<button type="button" class="btn btn-cancel" onclick="closeCurrentTab()">关闭</button>
				</div>
			</div>
		</div>
		</form>
		<!-- 添加 产品明细 模版 -->
		<table style="display:none">
		<#assign subTableStr>${head.subCode?if_exists?html}</#assign>
		<#assign subtablelist=subTableStr?split(",")>
		<#list subtablelist as sub >
		    <#if field['${sub}']?exists >
				<#if field['${sub}'].head.relationType!=1 >
			    <#include "jformonetomanytpl.ftl">
			    </#if>
			</#if>
		</#list>
		</table>
	${js_plug_in?if_exists}	
 </body>
 </html>