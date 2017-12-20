var urlpath = getUrlData();
var upId = urlpath.id;
init();
function init(){
	 // GIS编辑时，GIS信息不可更改
	if(urlpath.layerId != null && urlpath.layerId != "undefined" && urlpath.layerId != ""){
		 $("#gis_id").val(urlpath.gisid);
		 $("#name").val(urlpath.gisname);
		 //$("#gisxy").removeAttr("onclick").attr("readonly",true);
		 //$("#showgis").removeAttr("onclick").attr("readonly",true);
		 $("#cleargis").hide();
	}
}
/**
 * 初始化
 */
var dyChange = false;
$(function($) {
	// 单元数不可编辑
	$("#units").attr("readonly", true);

	// 新增时，单元数
	if ($("#units").val() == null || $("#units").val() == ""
			|| $("#units").val() == "0") {
		$("#units").val(0);
	}

	// 已生成房间信息时，单元不可编辑
	// if($("#auto_flag").val()!=null && $("#auto_flag").val()==1){
	// $("#addBtn_sc_jzwdygl").attr("readonly", true);
	// $("#addBtn_sc_jzwdygl").attr("disabled", "disabled");
	//
	// $("#delBtn_sc_jzwdygl").attr("readonly", true);
	// $("#delBtn_sc_jzwdygl").attr("disabled", "disabled");
	//
	// $("#sc_jzwdygl_table").attr("readonly", true);
	// $("#sc_jzwdygl_table").attr("disabled", "disabled");
	//
	// $("#sc_jzwdygl_table input").attr("readonly", true);
	// }
	$("#sc_jzwdygl_table tbody[id='add_sc_jzwdygl_table'] tr input[type='text']")
	.change(function() {
		dyChange = true; // 单元信息变更
	});
});

/**
 * 单元添加按钮按下时，单元数+1
 */
$('#addBtn_sc_jzwdygl').bind('click', function() {
	dyChange = true; // 单元信息变更
	$("#units").val(parseInt($("#units").val()) + 1);
});

/**
 * 单元删除按钮按下时，单元数减少
 */
$('#delBtn_sc_jzwdygl').bind(
		'click',
		function() {
			dyChange = true; // 单元信息变更
			var flag = 0;
			$("#sc_jzwdygl_table").find("input[type='checkbox']:checked").each(
					function() {
						flag += 1;
					});

			if ((parseInt($("#units").val()) - flag) < 1) {
				$.dialog.alert('建筑物至少包含一个单元信息');
			}
			$("#units").val(parseInt($("#units").val()) - flag);
		});

/**
 * 验证-单元号不能重复
 */
function beforeSubmit_() {
	// 判断建筑物的信息是否已存在
	var chkflag = true;
	debugger;
	var gislength = $("#gis_id").val().length;
	if( gislength > 0){
	$.ajax({
		url : 'scJzwglController.do?dochkJzwGis&id='
				+ $("input[name=id]").val() + '&gisid=' + $("#gis_id").val(),
		type : 'POST',
		dataType : 'text',
		async : false,
		cache : false,
		success : function(data) {
			debugger;
			var d = $.parseJSON(data);
			chkflag = d.success;
		}
	});
	}
	if (chkflag == false) {
		$.dialog.alert('当前选择建筑物已关联建筑物信息！');
		return false;
	}

	// 判断-至少包括一个单元
	var arr = [];
	var flag = 0;
	$("#sc_jzwdygl_table tbody[id='add_sc_jzwdygl_table'] tr").each(function() {
		arr.push($("td:eq(2)", this).find("input[type='text']").val());
		flag += 1;
	});
	if (flag == 0) {
		$.dialog.alert('建筑物至少包含一个单元信息！');
		return false;
	}

	// 判断-单元号不能重复
	if (arr.length == $.unique(arr).length) {
		// 未重复
		// return true;
	} else {
		// 重复
		$.dialog.alert('存在重复单元号,请修改');
		return false;
	}

	// 房间信息生成标识
	if (upId != "undefined" && upId != null && upId != "" && dyChange == true 
			&& $("#auto_flag").val() != "" && $("#auto_flag").val() != "0"
			&& $("auto_code").val() != "" && $("#auto_code").val() != "0") {
		$("#auto_flag").val(2);
		$("#auto_code").val(2);
	}
}