var html_text_dui = $("select[name='part_name']").html();
var html_text_ren = $("input[name='memname']").html();

$(document).ready(
		function() {
			$("form[name='formobj']").attr("onsubmit","beforeSubmit_");
			
			//取得服务主题类别
			var objtype = $("input[name='serv_objtype'][checked]").val();
			var objtype_val = null;
			if (!objtype) {
				objtype_val = "fuwudui";
			} else {
				objtype_val = objtype;
			}
			//服务者类型
			if ('fuwuzhe' == objtype_val) {
				//alert('服务者');
				//设置服务者选中
				$("input[name='serv_objtype'][value='fuwuzhe']").attr(
						"checked", true);
				var divClass = $("input[name='serv_objtype'][value='fuwuzhe']")
						.parent().attr("class");
				$("input[name='serv_objtype'][value='fuwuzhe']").parent().attr(
						"class", divClass + " checked");
				
				$("input[name='part_name']").parent().parent().hide();
				$("input[name='num']").parent().parent().hide();
				//显示服务者
				$("input[name='memname']").parent().parent().show();
				$("input[name='memname']").attr("datatype","*");
				$("#memname").next("SPAN").after("<SPAN style=\"COLOR: red\">*</SPAN>");
				
				//var fwzobj = $("input[name='memname']");
				
				//fwzobj.show();
				//遍历标签 显示是不是服务队
				/*$("select[name='is_team'] option").each(function() {
					if ($(this).val() == 'shi') {
						$(this).attr('selected', false);
					} else if ($(this).val() == 'fou') {
						$(this).attr('selected', true);
					}

				});*/
				//服务队类型
			} else if ('fuwudui' == objtype_val) {
				//设置服务队选中
				$("input[name='serv_objtype'][value='fuwudui']").attr(
						"checked", true);
				var divClass = $("input[name='serv_objtype'][value='fuwudui']")
						.parent().attr("class");
				$("input[name='serv_objtype'][value='fuwudui']").parent().attr(
						"class", divClass + " checked");

				$("input[name='memname']").parent().parent().hide();
				
				$("input[name='part_name']").parent().parent().show();
				
				$("input[name='num']").parent().parent().show();
				
				var fwdobj = $("input[name='part_name']");
				fwdobj.show();
				fwdobj.attr("datatype","*");
				$("#part_name").after("<SPAN style=\"COLOR: red\"> *</SPAN>");
				//遍历标签 显示是不是服务队
				/*$("select[name='is_team'] option").each(function() {

					if ($(this).val() == 'fou') {
						$(this).attr('selected', false);
					} else if ($(this).val() == 'shi') {
						$(this).attr('selected', true);
					}
				});*/
			}
		});//document 加载

//绑定select 标签  是否是服务队
/*$("select[name='is_team']").change(function() {
	var fwzobj = $("input[name='memname']");
	var fwdobj = $("select[name='part_name']");
	var isteam = $("select[name='is_team'] option[selected='selected']").val();
	//根据原生js取得变化的标签
	var mySelect = document.getElementsByName('is_team')[0];
	var selectedId = mySelect.selectedIndex;
	isteam = mySelect.options[selectedId].value;
	if (isteam == 'shi') {
		//设置服务队选中
		$("input[name='serv_objtype'][value='fuwudui']").attr("checked", true);
		$("input[name='serv_objtype'][value='fuwuzhe']").removeAttr("checked");		
		var divClass = $("input[name='serv_objtype'][value='fuwudui']")
		.parent().attr("class");
		$("input[name='serv_objtype'][value='fuwuzhe']").parent().attr(
				"class", divClass);
		$("input[name='serv_objtype'][value='fuwudui']").parent().attr(
		"class", divClass + " checked");

		$("input[name='memname']").parent().parent().hide();

		$("select[name='part_name']").parent().parent().show();
		
		$("input[name='num']").parent().parent().show();
		
		fwdobj.html(html_text_dui);
		fwdobj.show();

	} else if (isteam == 'fou') {
		//设置服务者选中
		$("input[name='serv_objtype'][value='fuwuzhe']").attr("checked", true);
		$("input[name='serv_objtype'][value='fuwudui']").removeAttr("checked");
		var divClass = $("input[name='serv_objtype'][value='fuwuzhe']")
		.parent().attr("class");
		$("input[name='serv_objtype'][value='fuwudui']").parent().attr(
				"class", divClass);
		$("input[name='serv_objtype'][value='fuwuzhe']").parent().attr(
		"class", divClass + " checked");
		
		$("select[name='part_name']").parent().parent().hide();
		$("input[name='num']").parent().parent().hide();
		//显示服务者
		$("input[name='memname']").parent().parent().show();
		fwzobj.html(html_text_ren);
		fwzobj.show();
	}

});//select
*/
//绑定服务队radio 标签  
$("input[type='radio'][name='serv_objtype']").on('ifChecked', function(event) {
	var objtype_val = $("input[name='serv_objtype'][checked]").val();
	//用原生的js取值遍历
	var obj_ = document.getElementsByName('serv_objtype');
	for ( var i = 0; i < obj_.length; i++) {
		if (obj_[i].checked) {
			objtype_val = obj_[i].value;
		}
	}
	//服务者类型
	if ('fuwuzhe' == objtype_val) {
		//alert('服务者');
		//设置服务者选中
		$("input[name='serv_objtype'][value='fuwuzhe']").attr("checked", true);
		$("input[name='serv_objtype'][value='fuwudui']").removeAttr("checked");
		//隐藏服务队
		//$("select[name='part_name']").html('');//提交之前设置 值设空
		//隐藏之前，先删除必填项的标识
		$("input[name='part_name']").removeAttr("datatype");
		$("input[name='part_name']").next("SPAN").remove();
		$("input[name='part_name']").parent().parent().hide();
		$("input[name='num']").parent().parent().hide();
		//显示服务者
		$("input[name='memname']").parent().parent().show();
		var fwzobj = $("input[name='memname']");
		fwzobj.show();
		fwzobj.html(html_text_ren);
		
		//服务者输入框显示成必填项
		fwzobj.attr("datatype","*");
		$("#memname").next("SPAN").after("<SPAN style=\"COLOR: red\">*</SPAN>");
		//遍历标签 显示是不是服务队
		/*$("select[name='is_team'] option").each(function() {
			if ($(this).val() == 'shi') {
				$(this).attr('selected', false);
			} else if ($(this).val() == 'fou') {
				$(this).attr('selected', true);
			}

		});*/
		//服务队类型
	} else {
		//alert('服务dui');
		//设置服务队选中
		$("input[name='serv_objtype'][value='fuwudui']").attr("checked", true);
		$("input[name='serv_objtype'][value='fuwuzhe']").removeAttr("checked");

		//$("input[name='memname']").html(''); // 从提交之前设置   
		//显示服务队之前，服务者输入框必填项标识先删除
		$("input[name='memname']").removeAttr("datatype");
	    $("#memname").next("SPAN").next().remove();
		$("input[name='memname']").parent().parent().hide();
		
		$("input[name='num']").parent().parent().show();

		$("input[name='part_name']").parent().parent().show();
		var fwdobj = $("input[name='part_name']");
		fwdobj.html(html_text_dui);
		//fwdobj.show();
		//服务队选择框显示为必填项
		fwdobj.attr("datatype","*");
		$("#part_name").after("<SPAN style=\"COLOR: red\"> *</SPAN>");
		
		
		//遍历标签 显示是不是服务队
		/*$("select[name='is_team'] option").each(function() {
			if ($(this).val() == 'fou') {
				$(this).attr('selected', false);
			} else if ($(this).val() == 'shi') {
				$(this).attr('selected', true);
			}
		});*/
	}
});//绑定radio改变事件
//提交之前的操作
function beforeSubmit_(){
    var objtype_val = $("input[name='serv_objtype'][checked]").val(); 
    if ('fuwuzhe' == objtype_val){
    	$("#num").val('');  
    	//var fwdobj = $("input[name='part_name']");
		//fwdobj.html(html_text_dui);
		$("input[name='part_name']").val('');
		$("input[name='part_id']").val('');
    	//$("select[name='part_name'] option[selected='selected']").attr('selected',false);
    	//$("select[name='part_name'] option[selected='selected']").removeAttr('selected');
    }else{
    	$("#mem_id").val('');
    	$("input[name='memname']").val('');
    }

	return true;
}
