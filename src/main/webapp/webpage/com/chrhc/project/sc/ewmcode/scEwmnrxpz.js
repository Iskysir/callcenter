if(location.href.indexOf("load=detail")!=-1){
			//查看模式控件禁用
			$("#formobj").find(":input").attr("disabled","disabled");
			$("button").hide();
		
}
var neibuClickFlag = false;
	  function neibuClick() {
		  neibuClickFlag = true; 
		  $('#btn_sub').trigger('click');
	  }
	 function uploadFile(data){
	  		if(!$("input[name='id']").val()){
	  			$("input[name='id']").val(data.obj.id);
	  		}
	  		if($(".uploadify-queue-item").length>0){
	  			upload();
	  		}else{
	  			if (neibuClickFlag){
	  				alert(data.msg);
	  				neibuClickFlag = false;
	  			}else {
	  			//getParentWindow();
	  				//alert(getParentWindow().document.body.innerHTML);
		  			var win = getParentWindow();
		  			///alert(win);
					win.reloadTable(data);
					win.tip(data.msg);
					if(frameElement.api)
					{
						frameElement.api.close();
					}
					else
					{
						closeCurrentTab();
					}
	  			}
	  		}
	  	}

//初始化下标
function resetTrNum(tableId) {
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		$(':input, select,button,a', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'), val = $this.val();
			if(name!=null){
				if (name.indexOf("#index#") >= 0){
					$this.attr("name",name.replace('#index#',i));
				}else{
					var s = name.indexOf("[");
					var e = name.indexOf("]");
					var new_name = name.substring(s+1,e);
					$this.attr("name",name.replace(new_name,i));
				}
			}
			if(id!=null){
				if (id.indexOf("#index#") >= 0){
					$this.attr("id",id.replace('#index#',i));
				}else{
					var s = id.indexOf("[");
					var e = id.indexOf("]");
					var new_id = id.substring(s+1,e);
					$this.attr("id",id.replace(new_id,i));
				}
			}
			if(onclick_str!=null){
				if (onclick_str.indexOf("#index#") >= 0){
					$this.attr("onclick",onclick_str.replace(/#index#/g,i));
				}else{
				}
			}
		});
		$(this).find('div[name=\'xh\']').html(i+1);
	});
}
//通用弹出式文件上传
function commonUpload(callback,inputId){
    $.dialog({
           content: "url:systemController.do?commonUpload",
           lock : true,
           title:"文件上传",
           zIndex:2100,
           width:700,
           height: 200,
           parent:windowapi,
           cache:false,
       ok: function(){
               var iframe = this.iframe.contentWindow;
               iframe.uploadCallback(callback,inputId);
               return true;
       },
       cancelVal: '关闭',
       cancel: function(){
       } 
   });
}
//通用弹出式文件上传-回调
function commonUploadDefaultCallBack(url,name,inputId){
	$("#"+inputId+"_href").attr('href',url).html('下载');
	$("#"+inputId).val(url);
}
function browseImages(inputId, Img) {// 图片管理器，可多个上传共用
		var finder = new CKFinder();
		finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
			$("#" + Img).attr("src", fileUrl);
			$("#" + inputId).attr("value", fileUrl);
		};
		finder.resourceType = 'Images';// 指定ckfinder只为图片进行管理
		finder.selectActionData = inputId; //接收地址的input ID
		finder.removePlugins = 'help';// 移除帮助(只有英文)
		finder.defaultLanguage = 'zh-cn';
		finder.popup();
	}
function browseFiles(inputId, file) {// 文件管理器，可多个上传共用
	var finder = new CKFinder();
	finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
		$("#" + file).attr("href", fileUrl);
		$("#" + inputId).attr("value", fileUrl);
		decode(fileUrl, file);
	};
	finder.resourceType = 'Files';// 指定ckfinder只为文件进行管理
	finder.selectActionData = inputId; //接收地址的input ID
	finder.removePlugins = 'help';// 移除帮助(只有英文)
	finder.defaultLanguage = 'zh-cn';
	finder.popup();
}
function decode(value, id) {//value传入值,id接受值
	var last = value.lastIndexOf("/");
	var filename = value.substring(last + 1, value.length);
	$("#" + id).text(decodeURIComponent(filename));
}

function getTableField(tablename)
{
	var aname = $("select");
	aname.find("option").slice(0).remove();
	aname.append('<option value="">---请选择---</option>');
	if(tablename=='')
	{
		tip('请输入数据来源表！');
		return;
	} else {
		$.ajax({
			url:"scEwmnrxpzController.do?getTableField&tablename="+tablename,
			async : false,
			cache : false,
			type : 'POST',
			error : function() {// 请求失败处理函数
				$.messager.alert('错误',data.msg);
				return false;
			},
			success : function(data) {
				//var resultJson = jQuery.parseJSON(data); 
				var resultJson = eval("(" + data +")");

				for(var i = 0; i < resultJson.obj.length; i++){
					//alert(resultJson.obj[i].field_name);
					aname.append("<option value='" + resultJson.obj[i].field_name + "'>" + resultJson.obj[i].content + "</option>");
				}
			}
		});
	}
}

function getcode(obj){
    var xStr = "scEwmnrxpzsubList[" + obj.name.substr(obj.name.length - 1,1) + "].nrxcode";
    var hiddenname = "scEwmnrxpzsubList[" + obj.name.substr(obj.name.length - 1,1) + "].nrxname";
	
	$("input[name='"+ hiddenname +"']").val($("select[name='"+obj.name+"']").find("option:selected").text());
	
	$("input[name='"+ xStr +"']").val(obj.value);

}

function getTableFieldX(tablename)
{
		if($("#add_scEwmnrxpzsub_table tr").length > 1){
			//编辑
			i = $("#add_scEwmnrxpzsub_table tr").length - 1;
		} else {
			//新增
			i = i+1;
		}

	//var tablename = $("#sourcetable").val();
	var aname = $("#fieldname"+i);
	aname.find("option").slice(0).remove();
	aname.append('<option value="">---请选择---</option>');
	if(tablename=='')
	{
		tip('请输入数据来源表！');
		return;
	} else {
		$.ajax({
			url:"scEwmnrxpzController.do?getTableField&tablename="+tablename,
			async : false,
			cache : false,
			type : 'POST',
			error : function() {// 请求失败处理函数
				$.messager.alert('错误',data.msg);
				return false;
			},
			success : function(data) {
				var resultJson = eval("(" + data +")");
				for(var j = 0; j < resultJson.obj.length; j++){
					aname.append("<option value='" + resultJson.obj[j].field_name + "'>" + resultJson.obj[j].content + "</option>");
				}
			}
		});
	}
}
