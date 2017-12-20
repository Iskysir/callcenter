var oldValue = $("select[name='bus_type']").val();
$(document)
		.ready(
				function() {
					var objs = $("tbody[id='add_sc_qzhglsub_table'] select[name^='sc_qzhglsub['][name$='.is_exist']");
					if (objs && objs.length > 0) {
						for ( var i = 0; i < objs.length; i++) {
							var selectName = "sc_qzhglsub[" + i + "].is_exist";
							$("input[name='sc_qzhglsub[" + i + "].item_name']")
									.attr("readonly", "readonly");
							$("select[name='" + selectName + "']").bind(
									'change',
									doExistChange(selectName, "sc_qzhglsub["
											+ i + "].remark"), i);
							var value = $(
									"tbody[id='add_sc_qzhglsub_table'] select[name='"
											+ selectName + "']").val();
							if (value == 'fou') {
								$(
										"tbody[id='add_sc_qzhglsub_table'] input[name='sc_qzhglsub["
												+ i + "].remark']").attr(
										'datatype', '*2-300');
								$(
										"tbody[id='add_sc_qzhglsub_table'] input[name='sc_qzhglsub["
												+ i + "].remark']").removeAttr(
										"ignore");
								$(
										"tbody[id='add_sc_qzhglsub_table'] input[name='sc_qzhglsub["
												+ i + "].remark']")
										.after(
												"<span name='sc_qzhglsub["
														+ i
														+ "].remarkSpan' style=\"color: red;\">*</span>");
							}
						}
					} else {
						// 若签章业务无签章配置子项
						doQzhglsub(oldValue);
					}
					$("#addBtn_sc_qzhglsub").hide();// 不允许手动添加
					
				});

$("select[name='bus_type']").change(
		function() {

			var busValue = $("select[name='bus_type']").val();
			var id = $("input[name='id']").val();
			if (id) {
				// id非空，编辑页面
				if (busValue == oldValue) {
					if ($("tbody[id='add_sc_qzhglsub_table'] tr").length > 0) {
						$("tbody[id='add_sc_qzhglsub_table'] tr").remove();
					}
					$.ajax({
						url : "scQzhglController.do?getQzhglsub&id=" + id,
						type : "Post",
						dataType : 'text',
						success : function(data) {
							var d = $.parseJSON(data);
							if (d.success) {
								var obj = d.obj;
								for ( var i = 0; i < obj.length; i++) {
									$("#addBtn_sc_qzhglsub").click();
									$(
											"input[name='sc_qzhglsub[" + i
													+ "].item_code']").val(
											obj[i].itemCode);
									$(
											"input[name='sc_qzhglsub[" + i
													+ "].item_name']").val(
											obj[i].itemName);
									$(
											"input[name='sc_qzhglsub[" + i
													+ "].item_name']").attr(
											"readonly", "readonly");
									$(
											"select[name='sc_qzhglsub[" + i
													+ "].is_exist']").val(
											obj[i].isExist);
									$(
											"input[name='sc_qzhglsub[" + i
													+ "].remark']").val(
											obj[i].remark);
									$(
											"input[name='sc_qzhglsub[" + i
													+ "].sort_no']").val(
											obj[i].sortNo);

									var selectName = "sc_qzhglsub[" + i
											+ "].is_exist";
									var nextName = "sc_qzhglsub[" + i
											+ "].remark";
									doExistChange(selectName, nextName, i);
								}
							} else {
								// 若签章业务无签章配置子项
								doQzhglsub(busValue);
							}
						},
						error : function(data) {
							$.messager.alert('出现错误', data.msg);
						}
					});
				} else {
					// id非空，但修改业务类型时
					doQzhglsub(busValue);
				}
			} else {
				// id为空，新增页面
				doQzhglsub(busValue);
			}
		});
// 生成业务配置子项
function doQzhglsub(busValue) {
	if ($("tbody[id='add_sc_qzhglsub_table'] tr").length > 0) {
		$("tbody[id='add_sc_qzhglsub_table'] tr").remove();
	}
	if (busValue) {
		$.ajax({
			url : "scYwlxpzxController.do?getPzxsub&busType=" + busValue,
			type : "Post",
			dataType : 'text',
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var attr = d.attributes;
					var sub = attr.sub;

					for ( var i = 0; i < sub.length; i++) {
						$("#addBtn_sc_qzhglsub").click();
						$("input[name='sc_qzhglsub[" + i + "].item_code']")
								.val(sub[i].itemCode);
						$("input[name='sc_qzhglsub[" + i + "].item_name']")
								.val(sub[i].itemName);
						$("input[name='sc_qzhglsub[" + i + "].item_name']")
								.attr("readonly", "readonly");
						$("input[name='sc_qzhglsub[" + i + "].sort_no']").val(
								sub[i].sortNo);

						var selectName = "sc_qzhglsub[" + i + "].is_exist";
						var nextName = "sc_qzhglsub[" + i + "].remark";
						doExistChange(selectName, nextName, i);
					}

				} else {
					tip(d.msg);
				}
			},
			error : function(data) {
				$.messager.alert('出现错误', data.msg);
			}
		});
	} else {

	}
}
// is_exist下拉框change事件
function doExistChange(selectName, nextName, i) {
	$("select[name='" + selectName + "']").change(
			function() {
				var is = $("select[name='" + selectName + "']").val();
				if (is == 'fou') {
					$("input[name='" + nextName + "']").attr('datatype', '*2-300');
					$("input[name='" + nextName + "']").attr('class', 'form-control');
					$("input[name='" + nextName + "']").removeAttr("ignore");
					$("input[name='" + nextName + "']").after(
							"<span name='" + nextName
									+ "Span' style=\"color: red;\">*</span>");
					$("table.table .form-control").removeClass("w260").css("width","230px");
				} else {
					$("input[name='" + nextName + "']").attr('datatype',
							'*2-300');
					if (typeof ($("input[name='" + nextName + "']")
							.attr("ignore")) == "undefined") {
						$("input[name='" + nextName + "']").attr('ignore',
								'ignore');
					}
					if($("span[name='" + nextName + "Span']")){
						$("span[name='" + nextName + "Span']").parent().find(".Validform_checktip").attr("class","Validform_checktip");
						$("span[name='" + nextName + "Span']").parent().find(".Validform_checktip").html('');
						if ($("span[name='" + nextName + "Span']")) {
							$("span[name='" + nextName + "Span']").remove();
						}
					}
					
					
				}
			});
}
// 提交之前的动作,回填姓名，身份证号数据
function beforeSubmit_() {
	var d = new Date();
	var str = getNowDateFormate(d);
	
	if ($("#deal_datetime").val()) {
	} else {
		$("#deal_datetime").val(str);
	}

	var rkk_id = $("#rkk_id").val();
	var name = $("#name").val();
	var cert_id = $("#cert_id").val();
	//alert("rkk_id="+rkk_id+"****name="+name+"*****cert_id="+cert_id);	
	var that = true;
	$.ajax({
		url : "scQzhglController.do?doRkxxById",
		data : {
			id : rkk_id,
			name : name,
			cert_id : cert_id
		},
		type : "Post",
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.success) {
				that = true;
				return true;
			}
		},
		error : function(data) {
			$.messager.alert('错误 ', data.msg);
			return false;
		}
	});
	return that;
}

function getNowDateFormate(now){
	 var strYear = now.getFullYear();
	 var strMonth = now.getMonth().toString();
	 var strDay = now.getDate().toString();
	 if(strMonth.length<2){
		 strMonth = "0"+String(parseInt(strMonth)+1);
	 }
	 if(strDay.length<2){
		 strDay = "0"+strDay;
	 }
	 
	 displayTime = strYear +"-"+strMonth+"-"+strDay;  
	 /*if(strTime.substring(5,6)<10){
	   var displayTime = strTime.substring(0,4)+"-0"+strTime.substring(5,6)+"-"+strTime.substring(7,9);
	  }else{	   
	   var displayTime = strTime.substring(0,4)+"-"+strTime.substring(5,7)+"-"+strTime.substring(8,10);
	 };*/
	 return displayTime;
	}