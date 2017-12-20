
					    <div title="${field['${sub}'].head.content?if_exists?html}" style="margin:0px;padding:0px;overflow:hidden;">
							<div>
							<table cellpadding="0" cellspacing="1" class="formtable" id="${sub}_table">
								<#if data['${sub}']?exists&&(data['${sub}']?size>0) >
								<#if (data['${sub}']?size>1) >
									<div><font color="red">该附表下存在多条数据</font></div>
								<#else>
										<#list data['${sub}'] as subTableData >
											<input type="hidden" name="${sub}[${subTableData_index}].id" id="${sub}[${subTableData_index}].id" value="${subTableData['id']?if_exists?html}"/>
											<#list field['${sub}'].hiddenFieldList as subTableField >
											<input type="hidden" name="${sub}[${subTableData_index}].${subTableField.field_name}" id="${sub}[${subTableData_index}].${subTableField.field_name}" value="${subTableData['${subTableField.field_name}']?if_exists?html}"/>
											</#list> 
											<#list field['${sub}'].fieldNoAreaList as po >
											<#if po_index%2==0>
											<!--<tr> -->
											<div class="row">
											</#if>
											<div class="col-md-6" >
											<div class="form-group">
											<label class="control-label" for="${po.field_name}">
											${po.content}:			
											</label>
											<!--<td align="right">
												<label class="Validform_label">
													${po.content?if_exists?html}
												<#if po.is_null=='N'>
												<span style="color: red;">*</span>
												</#if>	
												</label>
											</td>
											<td class="value">-->
										
											<#assign field_name = '${sub}[${subTableData_index}].${po.field_name}'>
											<#assign field_value = "${subTableData['${po.field_name}']?if_exists?html}">
											
											<#if po.show_type == 'date' || po.show_type == 'datetime'>
											 	<#if po.type == 'string'>
						  						<#assign date_field_value = "${subTableData['${po.field_name}']?if_exists?html}">
						  						<#assign datetime_field_value = "${subTableData['${po.field_name}']?if_exists?html}">
						  					  <#else>
						  						 <#assign date_field_value = (subTableData['${po.field_name}']?string("yyyy-MM-dd"))!>
												<#assign datetime_field_value = (subTableData['${po.field_name}']?string("yyyy-MM-dd HH:mm:ss"))!>						
										      </#if>
											
												
												
											</#if>
											<#include "jformfield.ftl">
											<#if po.is_null=='N'>
											<span style="color: red;">*</span>
											</#if>
											<label class="Validform_label" style="display: none;">${po.content?if_exists?html}</label>
											<!--</td> -->
											</div>
											</div>
											<!--<#if (po_index%2==0)&&(!po_has_next)>
												<td align="right">
													<label class="Validform_label">
													</label>
												</td>
												<td class="value">
												</td>
											</#if>-->
											<#if (po_index%2!=0)||(!po_has_next)>
												<!--</tr> -->
												</div>
											</#if>
											</#list>
											<#list field['${sub}'].fieldAreaList as subTableField>
										  	<!--<tr>
												<td align="right">
													<label class="Validform_label">
														${subTableField.content?if_exists?html}
													</label>
												</td>
												<td class="value" colspan="3">
													<textarea id="${sub}[${subTableData_index}].${subTableField.field_name}" name="${sub}[${subTableData_index}].${subTableField.field_name}" 
													       style="width: 600px" class="inputxt" rows="6"
											               <#if subTableField.field_valid_type?if_exists?html != ''>
											               datatype="${subTableField.field_valid_type?if_exists?html}"
											               <#else>
											               <#if subTableField.is_null != 'Y'>datatype="*"</#if>
											               </#if>>${subTableData['${subTableField.field_name}']?if_exists?html}</textarea>
													<span class="Validform_checktip"></span>
													<label class="Validform_label" style="display: none;">${subTableData['${subTableField.content}']?if_exists?html}</label>
												</td>
											</tr> -->
											<div class="row">
											<div class="col-md-12">
											<label class="control-label">
											${subTableField.content?if_exists?html}	
											</label>
											<textarea id="${sub}[${subTableData_index}].${subTableField.field_name}" name="${sub}[${subTableData_index}].${subTableField.field_name}" 
											 rows="6"  style="width: 600px" 
											 <#if subTableField.field_valid_type?if_exists?html != ''>
											               datatype="${subTableField.field_valid_type?if_exists?html}"
											               <#else>
											               <#if subTableField.is_null != 'Y'>datatype="*"</#if>
											               </#if>>${subTableData['${subTableField.field_name}']?if_exists?html}</textarea>
				              					<#if subTableField.is_null=='N'>
												<span style="color: red;">*</span>
												</#if>	
													<span class="Validform_checktip"></span>
													<label class="Validform_label" style="display: none;">${subTableData['${subTableField.content}']?if_exists?html}</label>
												</div>
												</div>
										  	</#list>
										</#list>
									</#if>
									<#else>
										<input type="hidden" name="${sub}[0].id" id="${sub}[0].id" />
										<#list field['${sub}'].hiddenFieldList as subTableField >
										<input type="hidden" name="${sub}[0].${subTableField.field_name}" id="${sub}[0].${subTableField.field_name}"/>
										</#list> 
										<#list field['${sub}'].fieldNoAreaList as po >
										<#if po_index%2==0>
										<!--<tr> -->
											<div class="row">
										</#if>
										<!--<td align="right">
											<label class="Validform_label">
												${po.content?if_exists?html}
												<#if po.is_null=='N'>
												<span style="color: red;">*</span>
												</#if>	
											</label>
										</td>
										<td class="value">-->
											<div class="col-md-6" >
											<div class="form-group">
											<label class="control-label" for="${po.field_name}">
											${po.content?if_exists?html}
											</label>
										<#assign field_name = '${sub}[0].${po.field_name}'>
										
										<#include "jformfield.ftl">
											<#if po.is_null=='N'>
												<span style="color: red;">*</span>
												</#if>	
										<label class="Validform_label" style="display: none;">${po.content?if_exists?html}</label>
										<!--</td> -->
											</div>
											</div>
										<!--<#if (po_index%2==0)&&(!po_has_next)>
												<td align="right">
													<label class="Validform_label">
													</label>
												</td>
												<td class="value">
												</td>
											</#if>-->
											<#if (po_index%2!=0)||(!po_has_next)>
												<!--</tr> -->
												</div>
											</#if>
										</#list>
										<#list field['${sub}'].fieldAreaList as subTableField>
										  	<!--<tr>
												<td align="right">
													<label class="Validform_label">
														${subTableField.content?if_exists?html}
													</label>
												</td>
												<td class="value" colspan="3">
													<textarea id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}" 
													       style="width: 600px" class="inputxt" rows="6"
											               <#if subTableField.field_valid_type?if_exists?html != ''>
											               datatype="${subTableField.field_valid_type?if_exists?html}"
											               <#else>
											               <#if subTableField.is_null != 'Y'>datatype="*"</#if>
											               </#if>></textarea>
													<span class="Validform_checktip"></span>
													<label class="Validform_label" style="display: none;">${sub}[0].${subTableField.content?if_exists?html}</label>
												</td>
											</tr>-->
											
											<div class="row">
											<div class="col-md-12">
											<label class="control-label">
											${subTableField.content?if_exists?html}	
											</label>
											<textarea id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}" 
											 rows="6"  style="width: 600px" 
											 <#if subTableField.field_valid_type?if_exists?html != ''>
											               datatype="${subTableField.field_valid_type?if_exists?html}"
											               <#else>
											               <#if subTableField.is_null != 'Y'>datatype="*"</#if>
											               </#if>></textarea>
				              					<#if subTableField.is_null=='N'>
												<span style="color: red;">*</span>
												</#if>	
													<span class="Validform_checktip"></span>
													<label class="Validform_label" style="display: none;">${sub}[0].${subTableField.content?if_exists?html}</label>
													</div>
												</div>
											
											
									  	</#list>
								</#if>
							</table>
							</div>
						</div>
					