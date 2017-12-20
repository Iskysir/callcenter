$(document).ready(function(){ 
	 var id=$("input[name='id'][type='hidden']").val();
		if(!id){
			//是否默认值设置
			$("input[type=radio][name='enterpriseins'][value='no']").attr("checked",true); 
			$("input[type=radio][name='enterpriseins'][value='no']").parent().addClass("checked",true);
			
			
			$("input[type=radio][name='landlessins'][value='no']").attr("checked",true); 
			$("input[type=radio][name='landlessins'][value='no']").parent().addClass("checked",true);  
			
			$("input[type=radio][name='oldins'][value='no']").attr("checked",true); 
			$("input[type=radio][name='oldins'][value='no']").parent().addClass("checked",true); 
			
			$("input[type=radio][name='otherins'][value='no']").attr("checked",true); 
			$("input[type=radio][name='otherins'][value='no']").parent().addClass("checked",true); 
		}
})//document 加载

//通用弹出式文件上传
function commonUpload(callback){
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
               iframe.uploadCallback(callback);
                   return true;
       },
       cancelVal: '关闭',
       cancel: function(){
       } 
   });
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

function changeTypeTab(){
 	if($(".Validform_error").closest("div.tab-pane").size() > 0){
 		var aid ;
		$(".Validform_error").closest("div.tab-pane").each(function(n,v){
			if(n == 0){
				aid = $(v).attr("id");
			}
		}) ;
		$('a[data-toggle="tab"][href="#' + aid +'"]').tab('show') ;
 	}
}

function nextRecord(stepNum){
	debugger;
	var id = $("input[name='id']").val();
	alert(id);
	$(this).parent.nextRecord(id,stepNum);
}