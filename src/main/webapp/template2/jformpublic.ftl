<input type="hidden" id="btn_sub" class="btn_sub"/>
			<input type="hidden" name="tableName" value="${tableName?if_exists?html}" >
			<input type="hidden" name="id" value="${id?if_exists?html}" >
			<#if bizType?? && bizType!=''> <input type="hidden" name="biztype" value="${bizType}" > </#if>
			<#global typetitle_index=0>  
			<#list columnhidden as po>
			<#if po.field_name!='biztype'>
				
				<#if po.show_type=='date' || po.show_type=='datetime'||po.type=='date'>
					<input type="hidden" id="${po.field_name}" name="${po.field_name}" value="${(data['${tableName}']['${po.field_name}']?string("yyyy-MM-dd HH:mm:ss"))!}" >
				<#else>
					<input type="hidden" id="${po.field_name}" name="${po.field_name}" value="${data['${tableName}']['${po.field_name}']?if_exists?html}" >
				</#if>
			 
			</#if>
			</#list>
			<table cellpadding="0" cellspacing="1" class="formtable">
			   <#list columns as po>
			    <#if (columns?size>10)>
					<#if (po_index-typetitle_index)%2==0>
						<tr>
					</#if>
				<#else>
					<tr>
				</#if>
			
					<#if po.show_type=='typetitle'>
	
						<#if ((po_index-typetitle_index-1)%2==0 )>
							<td align="right">
								<label class="Validform_label">
								</label>
							</td>
							<td class="value">
							</td>
							</tr>
						<#else> 
							</tr>
						</#if>
					
				 
					</table>
						<#global typetitle_index=(po_index+1)> 
						${po.content}<br/> 
					<table cellpadding="0" cellspacing="1" class="formtable">
					 	<tr>
					<#else>
					
					<td align="right" >
						<label class="Validform_label">
							${po.content}:
							<#if po.is_null=='N'>
							<span style="color: red;">*</span>
							</#if>	
						</label>
					</td>
					<td class="value">
					<#assign field_name = po.field_name>
					<#assign field_value = data['${tableName}']['${po.field_name}']?if_exists?html>
					<#if po.show_type == 'date' || po.show_type == 'datetime'>
						<#assign date_field_value = (data['${tableName}']['${po.field_name}']?string("yyyy-MM-dd"))!>
						<#assign datetime_field_value = (data['${tableName}']['${po.field_name}']?string("yyyy-MM-dd HH:mm:ss"))!>
					</#if>
						<#include "jformfield.ftl">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">${po.content?if_exists?html}</label>
					</td>
				</#if>
					
				<#if (columns?size>10)>
					<#if ((po_index-typetitle_index)%2==0)&&(!po_has_next)>
					<td align="right">
						<label class="Validform_label">
						</label>
					</td>
					<td class="value">
						
					</td>
					</#if>
					<#if ((po_index-typetitle_index)%2!=0)||(!po_has_next)>
						</tr>
					</#if>
				<#else>
					</tr>
				</#if>
			  </#list>
			  
			  <#list columnsarea as po>
			  <#if (columns?size>10)>
			  	<tr>
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
				</tr>
				<#else>
					<tr>
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
				</tr>
				</#if>
			  </#list>
			  <tr id = "sub_tr" style="display: none;">
				  <td colspan="2" align="center">
				  <input type="button" value="提交" onclick="neibuClick();" class="ui_state_highlight">
				  </td>
			  </tr>
			</table>