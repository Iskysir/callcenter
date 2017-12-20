
					    <div title="${field['${sub}'].head.content?if_exists?html}" style="margin:0px;padding:0px;overflow:hidden;">
							<script type="text/javascript"> 
								
							    $(document).ready(function(){
							    	$(".datagrid-toolbar").parent().css("width","auto");
							    	if(location.href.indexOf("load=detail")!=-1){
							    		$(".datagrid-toolbar").hide();
										$(":input").each(function(){
											var $thisThing = $(this);
											$thisThing.attr("title",$thisThing.val());
										});
							    	}
							    	resetTrNum('add_${sub}_table');
							    	
							    	
							    /*$('#addBtn_${sub}').linkbutton({   
								    iconCls: 'icon-add'  
								});  
								$('#delBtn_${sub}').linkbutton({   
								    iconCls: 'icon-remove'  
								}); */
								$('#addBtn_${sub}').bind('click', function(){  
								 
							 		 var tr =  $("#add_${sub}_table_template tr").clone();
							 		 //console.log(tr);
								 	 $("#add_${sub}_table").append(tr);
								 	 resetTrNum('add_${sub}_table');
								 	 $("table.table .form-control").removeClass("w260").css("width","230px");
								 	 return false;
							    }); 
								$('#delBtn_${sub}').bind('click', function(){   
							       $("#add_${sub}_table").find("input:checked").parent().parent().remove();   
							        resetTrNum('add_${sub}_table');
							        return false;
							    });
							
		
							    });
							</script>
							<!--<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
								<a id="addBtn_${sub}"  href="javascript:void(0)">添加</a> <a id="delBtn_${sub}" href="#">删除</a> 
							</div>-->
							
							<div class="row">
									<div class="col-md-12">
										<button class="btn btn-default" type="button" id="addBtn_${sub}">
											<i class="glyphicon glyphicon-plus"></i>&nbsp;添加
										</button>
										<button class="btn btn-default" type="button" id="delBtn_${sub}">
											<i class="glyphicon glyphicon-trash"></i>&nbsp;删除
										</button>
									</div>
							</div>
							<div style="width: auto;overflow-y:auto;overflow-x:scroll;">
							<div class="row">
									<div class="col-md-12">
										<table class="table table-bordered checkboxchrhc" id="${sub}_table">
											<tr>
												<th class="center" style="width: 50px;">序号</th>
												<th class="center" style="width: 50px;">操作</th>
												<#list field['${sub}'].fieldList as po >
												<th>${po.content?if_exists?html}
												<#if po.is_null=='N'>
												<span style="color: red;">*</span>
												</#if>
												</th>
												</#list>
											</tr>
											
								<tbody id="add_${sub}_table">
								<#if data['${sub}']?exists&&(data['${sub}']?size>0) >
								<#list data['${sub}'] as subTableData >
									<tr>
									<td align="center"><div style="width: 50px;" name="xh"></div></td>
									<td align="center">
										<input style="width:30px;" type="checkbox" name="ck"/>
										<input type="hidden" name="${sub}[${subTableData_index}].id" id="${sub}[${subTableData_index}].id" value="${subTableData['id']?if_exists?html}"/>
										<#list field['${sub}'].hiddenFieldList as po >
										<input type="hidden" name="${sub}[${subTableData_index}].${po.field_name}" id="${sub}[${subTableData_index}].${po.field_name}" value="${subTableData['${po.field_name}']?if_exists?html}"/>
										</#list> 
									</td>
									<#list field['${sub}'].fieldList as po >
									<td align="left">
									<#assign field_name = '${sub}[${subTableData_index}].${po.field_name}'>
									<#assign field_value = subTableData['${po.field_name}']?if_exists?html>
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
									<label class="Validform_label" style="display: none;">${po.content?if_exists?html}</label>
									</td>
									</#list>
									</tr>
								</#list>
								<#else>
								</#if>	
								</tbody>
										</table>
										
									</div>
							</div>
							</div>
							
							</div>
							
							
							
							
						<!--	<div style="width: auto;height: 400px;overflow-y:auto;overflow-x:scroll;">
							<table border="0" cellpadding="2" cellspacing="0" id="${sub}_table">
							<tr bgcolor="#E6E6E6">
								<td align="center" bgcolor="#EEEEEE">序号</td>
								<td align="center" bgcolor="#EEEEEE">操作</td>
							<#list field['${sub}'].fieldList as po >
								<td align="left" bgcolor="#EEEEEE">${po.content?if_exists?html}
								<#if po.is_null=='N'>
								<span style="color: red;">*</span>
								</#if>
								</td>
							</#list>
							</tr>
							<tbody id="add_${sub}_table">
								<#if data['${sub}']?exists&&(data['${sub}']?size>0) >
								<#list data['${sub}'] as subTableData >
									<tr>
									<td align="center"><div style="width: 25px;" name="xh"></div></td>
									<td align="center">
										<input style="width:20px;" type="checkbox" name="ck"/>
										<input type="hidden" name="${sub}[${subTableData_index}].id" id="${sub}[${subTableData_index}].id" value="${subTableData['id']?if_exists?html}"/>
										<#list field['${sub}'].hiddenFieldList as po >
										<input type="hidden" name="${sub}[${subTableData_index}].${po.field_name}" id="${sub}[${subTableData_index}].${po.field_name}" value="${subTableData['${po.field_name}']?if_exists?html}"/>
										</#list> 
									</td>
									<#list field['${sub}'].fieldList as po >
									<td align="left">
									<#assign field_name = '${sub}[${subTableData_index}].${po.field_name}'>
									<#assign field_value = subTableData['${po.field_name}']?if_exists?html>
									<#if po.show_type == 'date' || po.show_type == 'datetime'>
										<#assign date_field_value = (subTableData['${po.field_name}']?string("yyyy-MM-dd"))!>
										<#assign datetime_field_value = (subTableData['${po.field_name}']?string("yyyy-MM-dd HH:mm:ss"))!>
									</#if>
									<#include "jformfield.ftl">
									<label class="Validform_label" style="display: none;">${po.content?if_exists?html}</label>
									</td>
									</#list>
									</tr>
								</#list>
								<#else>
									<!--<tr style="display:none">
									<td align="center"><div style="width: 25px;" name="xh"></div></td>
									<td align="center">
									<input style="width:20px;" type="checkbox" name="ck"/>
									<input type="hidden" name="${sub}[0].id" id="${sub}[0].id" />
									<#list field['${sub}'].hiddenFieldList as po >
									<input type="hidden" name="${sub}[0].${po.field_name}" id="${sub}[0].${po.field_name}"/>
									</#list> 
									</td>
									<#list field['${sub}'].fieldList as po >
									<td align="left">
									<#if po.show_type=='text'>
										<input id="${sub}[0].${po.field_name}" name="${sub}[0].${po.field_name}" type="text"
										       style="width: 150px" class="inputxt"
								               nullmsg="请输入${po.content}！"
								               
								               <#if po.field_valid_type?if_exists?html != ''>
								               datatype="${po.field_valid_type?if_exists?html}"
								               <#else>
								               <#if po.type == 'int'>
								               datatype="n1-9" errormsg="请填写1到9位整数"
								               <#elseif po.type=='double'>
								               datatype="/^(-?\d{1,10})(\.\d{1,4})?$/" errormsg="填写数字,可带四位小数"
								                <#else>
					               				<#if po.is_null != 'Y'>datatype="*"</#if>
								               </#if></#if>
								               <#if po.validate_attr?if_exists?html != ''>
								              	${po.validate_attr}
								               </#if>
								               >
									
									<#elseif po.show_type=='password'>
										<input id="${sub}[0].${po.field_name}" name="${sub}[0].${po.field_name}"  type="password"
										       style="width: 150px" class="inputxt" 
								               nullmsg="请输入${po.content}！"
								               
								               <#if po.field_valid_type?if_exists?html != ''>
								               datatype="${po.field_valid_type?if_exists?html}"
								               <#else>
					               				<#if po.is_null != 'Y'>datatype="*"</#if>
								               </#if>>
									
									<#elseif po.show_type=='radio'>
								        <@DictData name="${po.dict_field?if_exists?html}" text="${po.dict_text?if_exists?html}" tablename="${po.dict_table?if_exists?html}" var="dataList">
											<#list dataList as dictdata> 
											<input value="${dictdata.typecode?if_exists?html}" name="${sub}[0].${po.field_name}" type="radio">
												${dictdata.typename?if_exists?html}
											</#list> 
										</@DictData>
								               
									<#elseif po.show_type=='checkbox'>
										<@DictData name="${po.dict_field?if_exists?html}" text="${po.dict_text?if_exists?html}" tablename="${po.dict_table?if_exists?html}" var="dataList">
											<#list dataList as dictdata> 
											<input value="${dictdata.typecode?if_exists?html}" name="${sub}[0].${po.field_name}" type="checkbox">
												${dictdata.typename?if_exists?html}
											</#list> 
										</@DictData>
								               
									<#elseif po.show_type=='list'>
										<@DictData name="${po.dict_field?if_exists?html}" text="${po.dict_text?if_exists?html}" tablename="${po.dict_table?if_exists?html}" var="dataList">
											<select id="${sub}[0].${po.field_name}" name="${sub}[0].${po.field_name}" >
												<#list dataList as dictdata> 
												<option value="${dictdata.typecode?if_exists?html}" >
													${dictdata.typename?if_exists?html}
												</option> 
												</#list> 
											</select>
										</@DictData>
										
									<#elseif po.show_type=='date'>
										<input id="${sub}[0].${po.field_name}" name="${sub}[0].${po.field_name}" type="text"
										       style="width: 150px"  
										       class="Wdate" onClick="WdatePicker()" 
								               nullmsg="请输入${po.content}！"
								               
								               <#if po.field_valid_type?if_exists?html != ''>
								               datatype="${po.field_valid_type?if_exists?html}"
								               <#else>
					               				<#if po.is_null != 'Y'>datatype="*"</#if>
								               </#if>>
									
									<#elseif po.show_type=='datetime'>
										<input id="${sub}[0].${po.field_name}" name="${sub}[0].${po.field_name}" type="text"
										       style="width: 150px"  
										       class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								               nullmsg="请输入${po.content}！"
								               
								               <#if po.field_valid_type?if_exists?html != ''>
								               datatype="${po.field_valid_type?if_exists?html}"
								               <#else>
					               				<#if po.is_null != 'Y'>datatype="*"</#if>
								               </#if>>
								               
									<#elseif po.show_type=='popup'>
										<input id="${sub}[0].${po.field_name}" name="${sub}[0].${po.field_name}"  type="text"
										       style="width: 150px" class="searchbox-inputtext15" 
										       onClick="inputClick(this,'${po.dict_text?if_exists?html}','${po.dict_table?if_exists?html}');" 
								               nullmsg="请输入${po.content}！"
								               
								               <#if po.field_valid_type?if_exists?html != ''>
								               datatype="${po.field_valid_type?if_exists?html}"
								               <#else>
					               				<#if po.is_null != 'Y'>datatype="*"</#if>
								               </#if>>
									
									<#elseif po.show_type=='file'>
										<input id="${sub}[0].${po.field_name}" name="${sub}[0].${po.field_name}" type="text"
										       style="width: 150px" class="inputxt" 
								               nullmsg="请输入${po.content}！"
								               
								               <#if po.field_valid_type?if_exists?html != ''>
								               datatype="${po.field_valid_type?if_exists?html}"
								               <#else>
					               				<#if po.is_null != 'Y'>datatype="*"</#if>
								               </#if>>
								               
									<#else>
										<input id="${sub}[0].${po.field_name}" name="${sub}[0].${po.field_name}" type="text"
										       style="width: 150px" class="inputxt"
								               nullmsg="请输入${po.content}！"
								               
								               <#if po.field_valid_type?if_exists?html != ''>
								               datatype="${po.field_valid_type?if_exists?html}"
								               <#else>
								               <#if po.type == 'int'>
								               datatype="n1-9" errormsg="请填写1到9位整数"
								               <#elseif po.type=='double'>
								               datatype="/^(-?\d{1,10})(\.\d{1,4})?$/" errormsg="填写数字,可带四位小数"
								                <#else>
					               				<#if po.is_null != 'Y'>datatype="*"</#if>
								               </#if></#if>
								               <#if po.validate_attr?if_exists?html != ''>
								              	${po.validate_attr}
								               </#if>
								               >
									</#if>
									<label class="Validform_label" style="display: none;">${po.content?if_exists?html}</label>
									</td>
									</#list>
									</tr>
								</#if>
							</tbody>
							</table>
							</div>
						</div>-->
					
							
							