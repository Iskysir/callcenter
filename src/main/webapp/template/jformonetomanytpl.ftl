		<tbody id="add_${sub}_table_template" class="checkboxchrhc">
				<tr>
					<td align="center"><div style="width: 50px;" name="xh"></div></td>
					<td align="center">
					<input style="width:30px;" type="checkbox" name="ck"/>
					<input type="hidden" name="${sub}[#index#].id" id="${sub}[#index#].id" />
					<#list field['${sub}'].hiddenFieldList as subTableField >
					<input type="hidden" name="${sub}[#index#].${subTableField.field_name}" id="${sub}[#index#].${subTableField.field_name}"/>
					</#list> 
					</td>
					<#list field['${sub}'].fieldList as po >
					<td align="left">
					<#assign field_name = '${sub}[#index#].${po.field_name}'>
									<#assign field_value = ''>
									<#if po.show_type == 'date' || po.show_type == 'datetime'>
										<#assign date_field_value = (subTableData['${po.field_name}']?string("yyyy-MM-dd"))!>
										<#assign datetime_field_value = (subTableData['${po.field_name}']?string("yyyy-MM-dd HH:mm:ss"))!>
									</#if>
									<#include "jformfield.ftl">
					
					<label class="Validform_label" style="display: none;">${po.content?if_exists?html}</label>
					
					<span class="Validform_checktip"></span>
					
					</td>
					</#list>
					</tr>
			 </tbody>
			 <script type="text/javascript">
			 	$("table.table .form-control").removeClass("w260").css("width","230px");
			 </script>