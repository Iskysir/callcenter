<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<%@include file="/template/jformhtmlhead.ftl"%>

<body style="overflow-y: scroll">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="scReportPermController.do?doAdd" tiptype="1">
		<c:set var="tableName" value="sc_report_perm" />
		<div class="container form-card">
			<input id="permType" name="permType" type="hidden"
				value="${permType}"> <input id="permId" name="permId"
				type="hidden" value="${permId}">

			<div class="row">
				<div class="col-md-12">
					<button class="btn btn-default" type="button"
						id="addBtn_ScReportPerm">
						<i class="glyphicon glyphicon-plus"></i>&nbsp;添加
					</button>
					<button class="btn btn-default" type="button"
						id="delBtn_ScReportPerm">
						<i class="glyphicon glyphicon-trash"></i>&nbsp;删除
					</button>
				</div>
			</div>

			<div
				style="width: auto; height: 400px; overflow-y: auto; overflow-x: scroll; border: 1px solid #DDD;">
				<div class="row">
					<div class="col-md-12">
						<table class="table table-bordered checkboxchrhc"
							id="ScReportPerm_table">
							<tr>
								<th class="center" style="width: 50px;">序号</th>
								<th class="center" style="width: 50px;">操作</th>
								<th>自定义报表编码<span style="color: red;">*</span></th>
								<th>自定义报表名称</th>
								<th>自定义报表数据</th>
							</tr>
							<tbody id="add_sc_report_perm_table">
								<c:if
									test="${scReportPermListMap != null && scReportPermListMap.size() > 0}">
									<c:forEach var="scRP" items="${scReportPermListMap}"
										varStatus="status">
										<tr id="code_${scRP.report_code}">
											<td align="center"><div style="width: 50px;" name="xh"></div></td>
											<td align="center"><input style="width: 30px;"
												type="checkbox" name="ck" id="${scRP.id}" /> <input
												type="hidden" name="${tableName}[${status.index}].id"
												id="${tableName}[${status.index}].id" value="${scRP.id}" />
												<input type="hidden"
												name="${tableName}[${status.index}].permType"
												id="${tableName}[${status.index}].permType"
												value="${scRP.perm_type}" /> <input type="hidden"
												name="${tableName}[${status.index}].permId"
												id="${tableName}[${status.index}].permId"
												value="${scRP.perm_id}" /> <input type="hidden"
												name="${tableName}[${status.index}].createName"
												id="${tableName}[${status.index}].createName"
												value="${scRP.create_name}" /> <input type="hidden"
												name="${tableName}[${status.index}].createBy"
												id="${tableName}[${status.index}].createBy"
												value="${scRP.create_by}" /> <input type="hidden"
												name="${tableName}[${status.index}].createDate"
												id="${tableName}[${status.index}].createDate"
												value="${scRP.create_date}" /> <input type="hidden"
												name="${tableName}[${status.index}].updateName"
												id="${tableName}[${status.index}].updateName"
												value="${scRP.update_name}" /> <input type="hidden"
												name="${tableName}[${status.index}].updateBy"
												id="${tableName}[${status.index}].updateBy"
												value="${scRP.update_by}" /> <input type="hidden"
												name="${tableName}[${status.index}].updateDate"
												id="${tableName}[${status.index}].updateDate"
												value="${scRP.update_date}" /> <input type="hidden"
												name="${tableName}[${status.index}].versionNum"
												id="${tableName}[${status.index}].versionNum"
												value="${scRP.version_num}" /></td>
											<td align="left"><input type="text"
												name="${tableName}[${status.index}].reportCode"
												id="${tableName}[${status.index}].reportCode"
												value="${scRP.report_code}" class="form-control w260"
												readonly /></td>
											<td align="left"><input type="text"
												name="${tableName}[${status.index}].reportname"
												id="${tableName}[${status.index}].reportname"
												value="${scRP.reportname}" class="form-control w260"
												readonly /></td>
											<td align="left" style="position: relative !important;">
												<input type="text"
												name="${tableName}[${status.index}].dataIds"
												id="${tableName}[${status.index}].dataIds"
												value="${scRP.data_ids}" class="form-control w260" readonly
												onclick="inputClick(this,'','${scRP.report_code}','${tableName}[${status.index}].dataIds=id','{singleSelect:false}')" />
												<span style="left: 240px !important;top: 16px !important;" class="suoshu"
												name="${tableName}[${status.index}].suoshu"
												id="${tableName}[${status.index}].suoshu" 
												onClick="inputClick(this,'','${scRP.report_code}','${tableName}[${status.index}].dataIds=id','{singleSelect:false}')"></span>
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
						<table style="display: none">
							<tbody id="add_ScReportPerm_table_template">
								<tr id="code_#reportCode#">
									<td align="center"><div style="width: 50px;" name="xh"></div></td>
									<td align="center"><input style="width: 30px;"
										type="checkbox" /></td>
									<td align="left"><input type="text"
										name="${tableName}[#index#].reportCode"
										id="${tableName}[#index#].reportCode"
										class="form-control w260" readonly /> <input type="hidden"
										name="${tableName}[#index#].id" id="${tableName}[#index#].id" />
										<input type="hidden" name="${tableName}[#index#].permType"
										id="${tableName}[#index#].permType" /> <input type="hidden"
										name="${tableName}[#index#].permId"
										id="${tableName}[#index#].permId" /></td>
									<td align="left"><input type="text"
										name="${tableName}[#index#].reportName"
										id="${tableName}[#index#].reportName"
										class="form-control w260" readonly /></td>
									<td align="left" style="position: relative !important;"><input type="text"
										name="${tableName}[#index#].dataIds"
										id="${tableName}[#index#].dataIds"
										class="form-control w260" readonly />
										<span style="left: 240px !important;top: 16px !important;" class="suoshu"
										name="${tableName}[#index#].suoshu"
										id="${tableName}[#index#].suoshu" ></span>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<script type="text/javascript">
			function optSave(){
				
			}
			$(function() {
				$("#formobj").Validform({
					beforeSubmit : function(curform) {
						var rows = $("#add_sc_report_perm_table > tr");
						if( rows.length <= 0){
							tip('请选择至少一条报表');
							return false;
						}
						return true;
					},
					tiptype : 4,
					btnSubmit : "#btn_sub",
					btnReset : "#btn_reset",
					ajaxPost : true,
					callback : function(data) {
						if (data.success == true) {
				  			var win = getParentWindow();
							win.tip(data.msg);
							if(frameElement.api)
							{
								frameElement.api.close();
							}
							else
							{
								closeCurrentTab();
							}
						} else {
							if (data.responseText == ''
									|| data.responseText == undefined) {
								$.messager.alert('错误', data.msg);
								$.Hidemsg();
							} else {
								try {
									var emsg = data.responseText
											.substring(
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
					}
				});
			});
			</script>
			<div class="row" id="buttonPanel">
				<div class="col-md-12 center">
					<button type="submit" class="btn btn-sure">确定</button>
					<button type="button" class="btn btn-cancel"
						onclick="closeCurrentTab()">关闭</button>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		$('#addBtn_ScReportPerm').bind('click', function(){  
			var extendsAttr = 'Code=Code,Name=Name';
			var config = "{singleSelect:false}";
			var url = "cgReportController.do?popup&id=getScReportPerm&config=" + config;
			$.dialog({
				content: "url:" + url,
				lock : true,
				title:"选择",
				width:800,
				height: 400,
				cache:false,
			    ok: function(){
			    	var relation = {};
			    	if(extendsAttr){
			    		var attrs = extendsAttr.split(",");
			    		var temp ;
			    		for ( var i= 0; i < attrs.length; i++) {
			    			temp = attrs[i].split("=");
			    			relation[temp[1]] = temp[0];
						}
			    	}
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("请选择");
			    		return false;
				    }else {
				    	$.each( selected, function(i, n){
					    	var tr =  $("#add_ScReportPerm_table_template tr").clone();
					    	var codeIds;
				    		for(var p in relation){
					    		var lp = n[p.toLocaleLowerCase()];
					    		var trId;
						    	if(p == 'Code'){
						    		codeIds = lp;
						    		trId = "code_" + lp;
							    	tr.attr('id',trId);
	
							    	if($("#add_sc_report_perm_table > tr[id=" + trId + "]").length>0){
							    		tr = $("#add_sc_report_perm_table > tr[id=" + trId + "]");
									}
							    }
				    			$(":input,span", tr).each(function(){
				    				var $this = $(this), name = $this.attr('name');
				    				if(name != null){
				    					if ($this.attr("type") == 'text' && name.indexOf(p) >= 0){
					    					$this.val(lp);
				    					}else if($this.attr("type") == 'hidden' && name.indexOf("permType") >= 0){
					    					$this.val($("#permType").val());
				    					}else if($this.attr("type") == 'hidden' && name.indexOf("permId") >= 0){
					    					$this.val($("#permId").val());
				    					}else if($this.attr("type") == 'text' && name.indexOf("dataIds") >= 0){
					    					if(codeIds != null && codeIds != "undefined"){
						    					var ck = "inputClick(this,'','" + codeIds + "','${tableName}[#index#].dataIds=id','{singleSelect:false}')";
						    					$this.attr('onclick',ck);
					    					}
				    					}else if(name.indexOf("suoshu") >= 0){
					    					if(codeIds != null && codeIds != "undefined"){
						    					var ck = "inputClick(this,'','" + codeIds + "','${tableName}[#index#].dataIds=id','{singleSelect:false}')";
						    					$this.attr('onclick',ck);
					    					}
					    				}
				    				}
				    			});
				    		}
						 	$("#add_sc_report_perm_table").append(tr);
				    	});
				    	resetTrNum("add_sc_report_perm_table");
				    	return true;
				    }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
			return false;
		});
		function getTableRowSelected()
		{
			var dataArray=new Array();
			$tbody = $("#add_sc_report_perm_table");
			$tbody.find('>tr').each(function(i){
				$('input', this).each(function(){
					var $this = $(this), name = $this.attr('name'), val = $this.val(),checked=$this.attr('checked');
					if(checked)
					{
						//alert(i);
						var idValue=$("[name='sc_report_perm["+i+"].id']").val();
						dataArray.push(idValue);
					}
				}
				);
				
			});
			return dataArray;
		}
		$('#delBtn_ScReportPerm').bind('click', function(){
			var rows = getTableRowSelected();
			//getTableRowSelected();
			if( rows == null || rows == "undefined" || rows.length<=0){
				tipAlert('请选择至少一条信息');
				return;
			}
			$.dialog.confirm('确定删除吗?', function(r) {
				if(!r){return;}
	
				//获取选中的ID串
				
				var ids = '';
				var rowNumber=0;
				$(rows).each(function(){
					if(this != null){
						id=this;
						if(id != null && id != "undefined" && id.length > 0){
						ids+= id;
						ids+=',';
						}
					}
					rowNumber++;
				});
				ids = ids.substring(0,ids.length-1);
				
				//删除操作
				$.ajax({
					url:"scReportPermController.do?doBatchDel&ids="+ids,
					type:"Post",
					dataType: 'text',
					success:function(data){
						var d = $.parseJSON(data);
						if (d.success) {
							$("#add_sc_report_perm_table").find("input:checked").parent().parent().remove();
					        resetTrNum('add_sc_report_perm_table');
						}else{
							$.dialog.alert(d.msg);
						}
					},
					error:function(data){
						$.messager.alert('错误',data.msg);
					}
				});
			});
	
	
	        return false;
		});
		//初始化下标
		function resetTrNum(tableId) {
			$tbody = $("#add_sc_report_perm_table");
			$tbody.find('>tr').each(function(i){
				$(':input,span', this).each(function(){
					var $this = $(this), name = $this.attr('name'), val = $this.val(),ck=$this.attr("onclick");
					if(name!=null){
						if (name.indexOf("#index#") >= 0){
							$this.attr("name",name.replace('#index#',i));
							$this.attr("id",$this.attr("name"));
							if(ck != null && ck != "undefined"){
								$this.attr("onclick",ck.replace('#index#',i));
							}
						}else{
							var s = name.indexOf("[");
							var e = name.indexOf("]");
							var new_name = name.substring(s+1,e);
							$this.attr("name",name.replace(new_name,i));
							$this.attr("id",$this.attr("name"));
							if(ck != null && ck != "undefined"){
								$this.attr("onclick",ck.replace('#index#',i));
							}
						}
					}
				});
				$(this).find('div[name=\'xh\']').html(i+1);
			});
		}
		resetTrNum("add_sc_report_perm_table");
		</script>
	</t:formvalid>
</body>
</html>