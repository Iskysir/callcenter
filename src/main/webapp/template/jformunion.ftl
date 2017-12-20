<#setting number_format="0.#####################">
<!DOCTYPE html>
<html>
 <#include "jformhtmlhead.ftl">
 
 <body>
  <form id="formobj" action="chrhcFormBuildController.do?saveOrUpdateMore" name="formobj" method="post" class="form-inline valideForm">
  <input type="hidden" id="btn_sub" class="btn_sub" />
	  
	  <section id="mainbox"> 
		  <div class="tab-content tab-box">
		  	<div class="Current_position" style="display: none">
		    	<img src="plug-in/media/image/dingwei.png"/><span id="current_position_text">当前所在的位置：</span>
	    		<script type="text/javascript">
	    			if(parent.parent.url_title_map[parent.location.href]){
	    				document.write(parent.parent.url_title_map[parent.location.href]);
	    			}else {
		    			if(location.href.indexOf('scQuickTitle') != -1){
							var reg = new RegExp("(^|&)scQuickTitle=([^&]*)(&|$)");
							var r  = window.location.search.substr(1).match(reg);
							document.write("<a href='javascript:;'>快捷业务</a>" + "<a href='javascript:;' class='last'>" +decodeURI(r[2]) + "</a>");
		    			}		    			
	    			}
	    		</script>				
		    </div>
		  	<div class="tabs_btn_div">
		  		<span style="display:none;" class="btn btn_bag_1" id="process_btn" type="button" onclick="$('#btn_sub').click();sub_process=true;changeTypeTab();" title="提交"><i class="fa fa-paper-plane"></i></span>
		    	<span class="btn btn_bag_1" id="save_btn" type="button" onclick="$('#btn_sub').click();changeTypeTab();" title="保存"><i class="fa fa-floppy-o"></i></span>
		        <span class="btn btn_bag_2" id="reset_btn" type="button" onclick="location.reload();" title="重置"><i class="fa fa-repeat"></i></span>
		        <span class="btn btn_bag_3" id="back_btn" type="button" onclick='sub_process=false;back_fun();' title="返回"><i class="fa fa-reply"></i></span>
   		    </div>
		    <ul class="nav nav-tabs">
		      	<li class="active"><a href="#jbxx" data-toggle="tab">基本信息</a></li>
		      	
		      	<#list typeTitleList as tt>
		      	    <li><a href="#${tt.field_name!}" data-toggle="tab">${tt.content!}</a></li>
		      	 </#list>
				
				<#assign subTableStr>${head.subTableStr?if_exists?html}</#assign>
				<#assign subtablelist=subTableStr?split(",")>
				
				<#list subtablelist as sub >
					<li><a href="#sub_${sub_index}" data-toggle="tab">${field['${sub}'].head.content?if_exists?html}</a></li>
				</#list>
						   
		    </ul>
		    
			    <div class="tab-pane active" id="jbxx">
				 	 <div class="container form-card">
						<#include "jformhead.ftl">
				  	  </div> 
			  	</div>
 
 				<#list subtablelist as sub >
	 				<div class="tab-pane" id="sub_${sub_index}"  >
						<div class="tab-pane container form-card">
							<div class="box demo" id="tabchrhc">
							<div class="tab_box">
								
								    <#if field['${sub}']?exists >
								    	<#if sub_index == 0>
								    	<div>
								    	<#else>
								    	<div class="">
								    	</#if>
								    	<#if field['${sub}'].head.relationType==1 >
									    <#include "jformonetoone.ftl">
									    <#else>
									    <#include "jformonetomany.ftl">
									    </#if>
									    </div>
									</#if>
								
							</div>
						</div>
					</div>
				</div>
				
			</#list>
			
	 	</div>
		    <a href="javascript:location.href = prve_url;" class="dataprve" style="display:none;"></a>
  			<a href="javascript:location.href = next_url;" class="datanext"  style="display:none;"></a>  			
	</section>
	 
 

			
			
<script type="text/javascript">

	var prve_url , next_url;
	$(window).load(function(){
		window.top.closeLoadingDialog();
	});
	
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
  		<#if po.show_type=='doctype'>
  		
  		var doctype = $('#${po.field_name}').val().length;	
  		if(doctype){
  		$("#adddoc").attr({'title':'编辑','class':'editbtn-new inner-newbtn'});
  		$("#editdoc").show();
  		}	
  		</#if>
  	</#list>
  	
    //查看模式情况下,删除和上传附件功能禁止使用
	if(location.href.indexOf("load=detail")!=-1){
		$(".jeecgDetail").hide();
	}
	
	if(location.href.indexOf("mode=read")!=-1){
		//查看模式控件禁用
		$("#adddoc").hide();
		$("#formobj").find(":input").attr("disabled","disabled");
		$("#cleargis").hide();
		$("#buttonPanel").hide();
		//$("#showgis").show();
		$(".suoshu").hide();
		//$("[id^='uploadify_']").hide();
		$("[class*='delflag']").hide();
		$("#file_uploadspan").hide();
		$("#save_btn").hide();	
		$("#reset_btn").hide();	
		$("#back_btn").attr("disabled",false);		

		var id = $("input[name='id']").val();
		prve_url = parent.nextRecord(id,-1);
		next_url = parent.nextRecord(id,1);	
		if(prve_url){
			$(".dataprve").show();
		}
 		if(next_url){
			$(".datanext").show();	  
		}
			
	}

	if(location.href.indexOf("mode=onbutton")!=-1){
		//其他模式显示提交按钮
		$("#sub_tr").show();
		
	}
	
 
	if($("#bpm_status").size() > 0 && ($("#bpm_status").val() == 1 || $("#bpm_status").val() == '')){
		//流程提交按钮
		$("#process_btn").show();
	}
			
   });
   
   
	var sub_process = false ;
	function submitCallback(data){
	 	if($("#process_btn").is(":visible")  && sub_process ){
			var url = 'activitiController.do?startOnlineProcess&configId=${tableName}&id=' + data.obj.id;
			$.post(url,{},function(data){
				parent.$("#editFormDiv").hide();
				parent.$("#editForm").attr("src","");
				parent.$(".datagrid").show();
			});
	 	}
	}
	
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
  		
  			if (neibuClickFlag){
  				alert(data.msg);
  				neibuClickFlag = false;
  			}else {
	  			var win =  getCurrentTab().find("iframe")[0].contentWindow;
				//GIS编辑时，回调GIS方法
				if(win)
				{
			  		if($("input[name='layerId']").val()){
			  			getParentWindow().businessCallBackDrawPointOrVector($("input[name='layerId']").val(),data.obj.id,data.obj.name,data.obj.gisxy);
			  		}
					win.reloadTable(data);
					win.tip(data.msg);
				}else{
					closeCurrentTab();
				}
		 
  			}
  			if($(".uploadify-queue-item").length>0){
  				upload();
  			}else{
  				 back_fun();
  			}
  		
  	}
	
	var datagrid_view_height = parent.$(".datagrid-view").height();
	var datagrid_header_height = parent.$(".datagrid-header").height();
	
  	function back_fun(){
		var editFormDiv = parent.$("#editFormDiv");
		if(editFormDiv.length){
			if(!sub_process){
				parent.$("#editFormDiv").hide();
				parent.$("#editForm").attr("src","");
				parent.$(".datagrid").show();
			}
		}else{
			parent.closeCurrentTab();
		} 	
		//处理datagrid-view
		parent.$(".datagrid-view").height(datagrid_view_height);
		parent.$(".datagrid-header").height(datagrid_header_height);
		parent.fitColumns();
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


			
		<div align="center"  id = "sub_tr" style="display: none;" > <input type="button" value="提交" onclick="neibuClick()" class="ui_state_highlight"></div>
		<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css"/><link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css"/><script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script><script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script><script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script><SCRIPT type="text/javascript" src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></SCRIPT>
 
 
		</form>
		<!-- 添加 产品明细 模版 -->
		<table style="display:none">
		<#assign subTableStr>${head.subTableStr?if_exists?html}</#assign>
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
	
	<script type="text/javascript">

			$(function() {
				$("#formobj")
						.Validform(
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
									tiptype : "<#if head.tipType?? && head.tipType!=''> ${head.tipType} <#else>4</#if>",
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
											if(submitCallback){
												submitCallback(data);
											}
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
					
		//切换tab校验
		$('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
			if(!$('#formobj').Validform().check(false,".active [datatype]")){
				return false;
			}
			e.target ;// 激活的标签页
			e.relatedTarget; // 前一个激活的标签页
		})		
			
		function changeTypeTab(){
		 	if($(".Validform_error").closest("div.tab-pane").size() > 0){
		 		var aid ;
				$(".Validform_error").closest("div.tab-pane").each(function(n,v){
					if(n == 0){
						aid = $(v).attr("id");
					}
				}) ;
				$('a[data-toggle="tab"][href="#' + aid +'"]').tab('show') ;
		 	}
		}		
	</script>
 </body>
 </html>