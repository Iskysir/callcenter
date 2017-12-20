						<link href="plug-in/chrhc/style.css" rel="stylesheet">
						<#if po.show_type=='text'>
							<input id="${field_name}" name="${field_name}" type="text"
							        class="form-control w260" value="${field_value}"
							       <#if po.content?if_exists?html != ''>
					               nullmsg="请输入${po.content}！"
					                </#if>
					               <#if po.operationCodesReadOnly?exists> readonly = "readonly"</#if>
					                <#if po.validate_attr?if_exists?html != ''>
					              	${po.validate_attr}
					                </#if>
					         
						       <#if po.field_valid_type?if_exists?html != ''>
					               datatype="${po.field_valid_type?if_exists?html}"
					               <#else>
					               <#if po.type == 'int'>
					               datatype="n1-9"  <#if po.is_null == 'Y'>ignore="ignore" </#if> errormsg="请填写1到9位整数"
					               <#elseif po.type=='double'>
					               datatype="/^(-?\d{1,10})(\.\d{1,4})?$/" <#if po.is_null == 'Y'>ignore="ignore" </#if> errormsg="填写数字,可带四位小数"
					               <#else>
					               <#if po.is_null != 'Y'>datatype="*"</#if>
					               </#if>
					               </#if>>
						<#elseif po.show_type=='password'>
							<input id="${field_name}" name="${field_name}"  type="password"
							       class="form-control w260" value="${field_value}"
							       <#if po.content?if_exists?html != ''>
					               nullmsg="请输入${po.content}！"
					                </#if>
					               <#if po.operationCodesReadOnly?if_exists> readonly = "readonly"</#if>
						       <#if po.field_valid_type?if_exists?html != ''>
					               datatype="${po.field_valid_type?if_exists?html}"
					               <#else>
					               <#if po.is_null != 'Y'>datatype="*"</#if>
					               </#if>>
						
						<#elseif po.show_type=='radio'>
					        <@DictData name="${po.dict_field?if_exists?html}" text="${po.dict_text?if_exists?html}" tablename="${po.dict_table?if_exists?html}" var="dataList">
								<#list dataList as dictdata> 
								<label class="radio-inline">
								<input value="${dictdata.typecode?if_exists?html}"  name="${field_name}" type="radio"
								<#if po.operationCodesReadOnly?if_exists>onclick="return false;"</#if>
								<#if dictdata.typecode?if_exists?html=="${field_value}"> checked="true" </#if>>
									${dictdata.typename?if_exists?html}
								</label>
								</#list> 
							</@DictData>
					               
						<#elseif po.show_type=='checkbox'>
							<#assign checkboxstr>${field_value}</#assign>
							<#assign checkboxlist=checkboxstr?split(",")>
							<@DictData name="${po.dict_field?if_exists?html}" text="${po.dict_text?if_exists?html}" tablename="${po.dict_table?if_exists?html}" var="dataList">
								<div style="float: left; margin-left: 6px; width:730px;">
								<#list dataList as dictdata> 
								<label class="checkbox-inline">
								<input value="${dictdata.typecode?if_exists?html}" name="${field_name}" type="checkbox"
								<#if po.operationCodesReadOnly?if_exists>onclick="return false;"</#if>
								<#list checkboxlist as x >
								<#if dictdata.typecode?if_exists?html=="${x?if_exists?html}"> checked="true" </#if></#list>>
									${dictdata.typename?if_exists?html}
								</label>
								</#list> 
								</div>
							</@DictData>
					               
						<#elseif po.show_type=='list'>
							<@DictData name="${po.dict_field?if_exists?html}" text="${po.dict_text?if_exists?html}" tablename="${po.dict_table?if_exists?html}" filter="${po.gis_attr?if_exists?html}" var="dataList">
								<select class="form-control w260" id="${field_name}" name="${field_name}" <#if po.operationCodesReadOnly?if_exists>onfocus="this.defOpt=this.selectedIndex" 
								onchange="this.selectedIndex=this.defOpt;"</#if>  
								<#if po.field_valid_type?if_exists?html != ''>
					               datatype="${po.field_valid_type?if_exists?html}"
					               </#if> 
					               
					                <#if po.validate_attr?if_exists?html != ''>
					              	${po.validate_attr}
					              	<#else>
					               <#if po.is_null != 'Y'>datatype="*"</#if> 
					                </#if>
					                
					                >
									<option value="">---请选择---</option>
									<#list dataList as dictdata> 
									<option value="${dictdata.typecode?if_exists?html}" 
									<#if dictdata.typecode?if_exists?html=="${field_value}"> selected="selected" </#if>>
										${dictdata.typename?if_exists?html}
									</option> 
									</#list> 
								</select>
							</@DictData>
							
						<#elseif po.show_type=='date'>
							<input id="${field_name}" name="${field_name}" type="text"
							       value="${date_field_value}"
							       class="form-control w260" onClick="WdatePicker({onpicked:function(){(this).blur();},<#if po.operationCodesReadOnly?if_exists> readonly = true</#if>})" 
					              readonly = "readonly"
					              <#if po.operationCodesReadOnly?exists> readonly = "readonly"</#if>
					              <#if po.content?if_exists?html != ''>
					               nullmsg="请输入${po.content}！"
					                </#if>
					              <#if po.validate_attr?if_exists?html != ''>
					              	${po.validate_attr}
					                </#if>
						       <#if po.field_valid_type?if_exists?html != ''>
					               datatype="${po.field_valid_type?if_exists?html}"
					               <#else>
					               <#if po.is_null != 'Y'>datatype="*"</#if> 
					               </#if>>
						
						<#elseif po.show_type=='datetime'>
							<input id="${field_name}" name="${field_name}" type="text"
							        value="${datetime_field_value}"
							       class="form-control w260" onClick="WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd HH:mm:ss'<#if po.operationCodesReadOnly?if_exists> ,readonly = true</#if>})"
						       readonly = "readonly"
						         <#if po.operationCodesReadOnly?exists> readonly = "readonly"</#if>
						         <#if po.content?if_exists?html != ''>
					               nullmsg="请输入${po.content}！"
					                </#if>
						         <#if po.validate_attr?if_exists?html != ''>
					              	${po.validate_attr}
					                </#if>
						       <#if po.field_valid_type?if_exists?html != ''>
					               datatype="${po.field_valid_type?if_exists?html}"
					               <#else>
					               <#if po.is_null != 'Y'>datatype="*"</#if> 
					               </#if>>
						
						<#elseif po.show_type=='popup'>
							<input id="${field_name}" name="${field_name}"  type="text"
							       class="form-control w260"
							       onClick="inputClick(this,'${po.dict_text?if_exists?html}','${po.dict_table?if_exists?html}','${(po.popup_attr)!}','${po.gis_attr?if_exists?html}');" 
							       value="${field_value}"
					                readonly = "readonly"
					               <#if po.content?if_exists?html != ''>
					               nullmsg="请输入${po.content}！"
					                </#if>
					                
						       <#if po.field_valid_type?if_exists?html != ''>
					               datatype="${po.field_valid_type?if_exists?html}"
					               <#else>
					               <#if po.is_null != 'Y'>datatype="*"</#if>
					               </#if>
					                <#if po.validate_attr?if_exists?html != ''>
					              	${po.validate_attr}
					                </#if>
					               >
					               <span class="suoshu" onClick="inputClick(this,'${po.dict_text?if_exists?html}','${po.dict_table?if_exists?html}','${(po.popup_attr)!}','${po.gis_attr?if_exists?html}');" ></span>
						<#elseif po.show_type=='idcardread'>
					 
							<input id="${field_name}" name="${field_name}"  type="text"
							       class="form-control w260"
							       value="${field_value}"
					               <#if po.operationCodesReadOnly?if_exists> readonly = "readonly"</#if>
						       <#if po.field_valid_type?if_exists?html != ''>
					               datatype="${po.field_valid_type?if_exists?html}"
					               <#else>
					               <#if po.is_null != 'Y'>datatype="*"</#if>
					               </#if>
					                <#if po.validate_attr?if_exists?html != ''>
					              		${po.validate_attr!}
					                </#if>
					               
					               >		
					               <a href="javascript:void(0)"  class="read-card btn btn-default" plain="true" icon="icon-search" onClick="idcardRead('${po.field_name}','${po.gis_attr?if_exists?html}')" id="cleargis"></a>	
								   <span class="suoshu" onClick="inputClick(this,'${po.dict_text?if_exists?html}','idcard','${po.popup_attr!}','${po.gis_attr?if_exists?html}');" ></span>
						<#elseif po.show_type=='popupgis'>
							<input id="${field_name}" name="${field_name}"  type="text"
							       class="form-control w260"
							       style="color:#EEE;font-size:1px"
							       <#if editType?if_exists?html != 'gis'>
							       onClick="inputClickGis(this,'${po.gis_attr?if_exists?html}','${po.dict_table?if_exists?html}');" 
							        </#if>
							       value="${field_value}" readonly = "readonly"
					               <#if po.operationCodesReadOnly?if_exists> readonly = "readonly"</#if>
					               <#if po.content?if_exists?html != ''>
					               nullmsg="请输入${po.content}！"
					                </#if>
					              <#if po.validate_attr?if_exists?html != ''>
					              	${po.validate_attr}
					                </#if>
						       <#if po.field_valid_type?if_exists?html != ''>
					               datatype="${po.field_valid_type?if_exists?html}"
					               <#else>
					               <#if po.is_null != 'Y'>datatype="*"</#if>
					               </#if>>
					                <#if editType?if_exists?html != 'gis'>
					               <span class="dingwei" onClick="inputClickGis('#${po.field_name}','${po.gis_attr?if_exists?html}','${po.dict_table?if_exists?html}');"  ></span>
					               <span class="yidingwei" onClick="inputClickGis('#${po.field_name}','${po.gis_attr?if_exists?html}','${po.dict_table?if_exists?html}');" 
					                id="showgis" style="display:none;"><i class="glyphicon glyphicon-ok"></i>&nbsp;已定位，点击查看</span>
					                <a href="javascript:void(0)"  class="easyui-linkbutton" plain="true" icon="icon-redo" onClick="clearGis('${po.field_name}')" id="cleargis">清空</a>
					                </#if>
					              <!-- <a href="javascript:void(0)"  class="easyui-linkbutton" plain="true" icon="icon-redo"  onClick="inputClickGis('#${po.field_name}','${po.gis_attr?if_exists?html}','${po.dict_table?if_exists?html}');"  id="showgis" style="display:none;">查看</a>-->
						<#elseif po.show_type=='file'>
							<div style="float: left; margin-left: 6px;">
								<#if po.field_valid_type?if_exists?html != '' && po.field_valid_type == "photo" && filesList?exists && (filesList?size > 0)>
									<div style="float:left; margin-right:20px;">
										<img src="commonController.do?openJSPFile&fileid=${filesList[0]['fileKey']}" onclick="openwindow('预览','commonController.do?openViewFile&fileid=${filesList[0]['fileKey']}&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity','fList',700,500)" style="cursor: pointer;" width="169px" height="220px"/>
									</div>
								</#if>
								<div style="float:left;margin-left:6px;">
									<ul>
										<#list filesList as fileB>
											<#if fileB['field'] == po.field_name>
											<li>
												<span class="picName"><i class="glyphicon glyphicon-picture"></i>&nbsp;${fileB['title']}</span>
												<a href="commonController.do?viewFile&fileid=${fileB['fileKey']}&subclassname=c" title="下载" class="btn btn-default">下载</a>
												<a href="javascript:void(0);" onclick="openwindow('预览','commonController.do?openViewFile&fileid=${fileB['fileKey']}&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity','fList',700,500)" class="btn btn-default">预览</a>
												<a id="uploadify_a_${po.field_name}" href="javascript:void(0);" onclick="delFile('cgUploadController.do?delFile&id=${fileB['fileKey']}',this)" class="btn btn-default">删除</a>
											</li>
											</#if>
										</#list>
									</ul>
								</div>
								<#if !(po.operationCodesReadOnly ??)>
								<div style="clear:both;" id="uploadify_div_${po.field_name}">
									<script type="text/javascript">
										var serverMsg="";
										var m = new Map();
										var attr = '${po.gis_attr}'|| "{}";
										var fileType =eval('('+attr+')');
										var exts = fileType.fileTypeExts || '*.rar;*.zip;*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;*.jpg;*.gif;*.png;*.apk';
										var validtype = '${po.field_valid_type}'|| "";
										if(validtype != '' && validtype == "photo"){
											exts = "*.jpg;*.png;*.JPG;*.PNG;";
										}
										$(function(){$('#${po.field_name}').uploadify({
											buttonText:'添加文件',
											auto:false,
											progressData:'speed',
											multi:true,
											height:34,
											width:265,
											overrideEvents:['onDialogClose'],
											fileTypeDesc:'文件格式:',
											queueSizeLimit: fileType.queueSizeLimit || 10,
											queueID:'filediv_${po.field_name}',
											fileTypeExts: exts,
											fileSizeLimit:'15MB',
											swf:'plug-in/uploadify/uploadify.swf',	
											uploader:'cgUploadController.do?saveFiles&jsessionid='+$("#sessionUID").val()+'',
											onUploadStart : function(file) { 
												var cgFormId=$("input[name='id']").val();
												$('#${po.field_name}').uploadify("settings", "formData", {'cgFormId':cgFormId,'cgFormName':'${tableName?if_exists?html}','cgFormField':'${po.field_name}'});
											} ,
											onQueueComplete : function(queueData) {
												 var win = getParentWindow();//frameElement.api.opener;
												 win.reloadTable();
												 win.tip(serverMsg);
												if(frameElement.api)
												{
													frameElement.api.close();
												}
												else
												{
													closeCurrentTab();
												}
											},
											onUploadSuccess : function(file, data, response) {var d=$.parseJSON(data);if(d.success){var win = getParentWindow();serverMsg = d.msg;}},onFallback : function(){tip("<a href='javascript:void(0);' onclick='downLoadFlashFile();' style='color:blue;'>您未安装FLASH控件，无法上传图片，请安装FLASH控件后再试！点击下载</a>")},onSelectError : function(file, errorCode, errorMsg){switch(errorCode) {case -100:tip("上传的文件数量已经超出系统限制的"+$('#${po.field_name}').uploadify('settings','queueSizeLimit')+"个文件！");break;case -110:tip("文件 ["+file.name+"] 大小超出系统限制的"+$('#${po.field_name}').uploadify('settings','fileSizeLimit')+"大小！");break;case -120:tip("文件 ["+file.name+"] 大小异常！");break;case -130:tip("文件 ["+file.name+"] 类型不正确！");break;}},

											onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) { }
											});
										});
									</script>
									<span id="file_uploadspan" style="display:inline-block;margin-top:10px;"><input type="file" name="${po.field_name}" id="${po.field_name}" /></span>
								</div>
								<div class="form" id="filediv_${po.field_name}" style="min-width:360px;"> </div>
								</#if>
							</div>
						<#else>
							<input id="${field_name}" name="${field_name}" type="text"
							     class="form-control w260" value="${field_value}"
							       <#if po.content?if_exists?html != ''>
					               nullmsg="请输入${po.content}！"
					                </#if>
					               <#if po.field_valid_type?if_exists?html != ''>
					               datatype="${po.field_valid_type?if_exists?html}"
					               <#else>
					               <#if po.type == 'int'>
					               datatype="n1-9" 
					               <#elseif po.type=='double'>
					               datatype="/^(-?\d{1,10})(\.\d{1,4})?$/" errormsg="填写数字,可带四位小数"
					               <#else>
					               <#if po.is_null != 'Y'>datatype="*"</#if>
					               </#if>
					               </#if>>

						</#if>