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
			<!--<table  cellpadding="0" cellspacing="1" class="formtable"> -->
				<#global pocl_index=0>
				<#list columns as po>
					<#if (pocl_index-typetitle_index)%2==0>
						<!--<tr>-->
							<div class="row">
					</#if>
				
					<#if po.show_type=='typetitle'>
	
						<#if ((pocl_index-typetitle_index-1)%2==0)>
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
							<#global typetitle_index=(po_index+1)> 
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
						<#else><!--#if po.show_type != 'file' -->
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
									<label class="control-label fl" for="${po.field_name}">${po.content}</label>
							<#else>
								<div class="col-md-6" >
								<div class="form-group">
								<label class="control-label" for="${po.field_name}">
								${po.content}
								<!--<#if po.is_null=='N'>
										<span style="color: red;">*</span>
										</#if>	-->
								</label>
								<!--<td align="right">
									<label class="Validform_label">
										${po.content}:
										<#if po.is_null=='N'>
										<span style="color: red;">*</span>
										</#if>	
									</label>
								</td>
								<td class="value">-->
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
					<#if ((pocl_index-typetitle_index)%2!=0)||(!po_has_next)>
					<!--</tr>-->
						</div>
					</#if>
					</#if>
					
					<!--<#if ((po_index-typetitle_index)%2==0)&&(!po_has_next)>
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
					</#if>-->
				<#global pocl_index=(pocl_index+1)>
			  </#list>
			  
			  <#list columnsarea as po>
			 <!-- <tr>
					<td align="right">
						<label class="Validform_label">
							${po.content}:
						</label>
					</td>
					<td class="value" colspan="3">
						<textarea id="${po.field_name}" name="${po.field_name}" 
						       style="width: 600px" class="inputxt" rows="6"
				               <#if po.field_valid_type?if_exists?html != ''>
				               datatype="${po.field_valid_type?if_exists?html}"
				               <#else>
				               <#if po.is_null != 'Y'>datatype="*"</#if>
				               <#if po.validate_attr?if_exists?html != ''>
					              	${po.validate_attr}
					                </#if>
				               </#if>
				                <#if po.validate_attr?if_exists?html != ''>
					              	${po.validate_attr}
					                </#if>
				               >${data['${tableName}']['${po.field_name}']?if_exists?html}</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">${po.content?if_exists?html}</label>
					</td>
				</tr>-->
					<div class="row">
					<div class="col-md-12">
						<label class="control-label">
						${po.content}
							
						</label>
						<textarea id="${po.field_name}" name="${po.field_name}" 
						        rows="3"  cols="80" class="form-control"
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
			  </#list>
			</table>