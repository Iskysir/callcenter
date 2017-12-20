//﻿var jq = jQuery.noConflict();
/**
 * 增删改工具栏
 */
/*window.onerror = function() {
	return true;
};*/
var iframe;// iframe操作对象
var win;//窗口对象
var gridname="";//操作datagrid对象名称
var windowapi , W;//内容页中调用窗口实例对象接口

var doctypename = "";
var doctypeid = "";
if(frameElement&&frameElement.api&&frameElement.api.opener)
	frameElement.api.opener;
if(frameElement&&frameElement.api)
	windowapi = frameElement.api;
if(windowapi&&windowapi.opener)
	W = windowapi.opener;


function upload(curform) {
	upload();
}
//列表刷新
function reloadTable(){	
	try{
		$('#'+gridname).datagrid('reload');
		$('#'+gridname).treegrid('reload');
	}catch(ex){
		//donothing
	}
}
function reloadTable(data){	
	try{
		if(gridname != null && gridname != "" && gridname == "scJzwfjglList"){
			// 刷新Tab
			if(data != null && data != "undefined" 
				&& data.obj != null && data.obj != "undefined"){
				var jtid = data.obj.id;
				var fjid = data.obj.sqly_id;
				var hzxm = data.obj.hzxm;
				callBackFj(fjid,jtid,hzxm);
			}
			/**
			var currTab = window.top.$('#maintabs').tabs('getSelected');
			var url = $(currTab.panel('options').content).attr('src');
			window.top.$('#maintabs').tabs('update', {
				tab : currTab,
				options : {
					content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>'
				}
			});**/
		}
		$('#'+gridname).datagrid('reload');
		$('#'+gridname).treegrid('reload');
	}catch(ex){
		//donothing
	}
}
/**
 * 添加事件打开窗口
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 */
function add(title,url,gname,width,height) {
	gridname=gname;
	
	//alert(gridname);
	//alert(top.window.dialogStr);
	if(top.window.dialogStr&&(!gridname||top.window.dialogStr.indexOf(gridname)>=0))
	{
		createwindow(title, url,width,height);
	}
	else
	{
		addOneTab(title,url);
	}
	//createwindow(title, addurl,width,height);
	
}
/**
 * 树列表添加事件打开窗口
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 */
function addTreeNode(title,addurl,gname) {
	if (rowid != '') {
		addurl += '&id='+rowid;
	}
	gridname=gname;
	createwindow(title, addurl);
}
/**
 * 更新事件打开窗口
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 * @param id//主键字段
 */
function update(title,url, id,width,height) {
	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择编辑项目');
		return;
	}
	if (rowsData.length>1) {
		tip('请选择一条记录再编辑');
		return;
	}
	
	url += '&id='+rowsData[0].id;
	//alert(top.window.dialogStr);
	
	if(top.window.dialogStr&&(!gridname||top.window.dialogStr.indexOf(gridname)>=0))
	{
		createwindow(title, url,width,height);
	}
	else
	{
		addOneTab(title,url);
	}
	
	//addOneTab(title,url);
	//createwindow(title,url,width,height);
}

/**
 * 如果页面是详细查看页面，无效化所有表单元素，只能进行查看
 */
$(function(){
	if(location.href.indexOf("load=detail")!=-1){
		$(":input").attr("disabled","true");
		//$(":input").attr("style","border:0;border-bottom:1 solid black;background:white;");
	}
});

/**
 * 查看详细事件打开窗口
 * @param title 查看框标题
 * @param addurl//目标页面地址
 * @param id//主键字段
 */
function detail(title,url, id,width,height) {
	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
//	if (rowData.id == '') {
//		tip('请选择查看项目');
//		return;
//	}
	
	if (!rowsData || rowsData.length == 0) {
		tip('请选择查看项目');
		return;
	}
	if (rowsData.length > 1) {
		tip('请选择一条记录再查看');
		return;
	}
    url += '&load=detail&id='+rowsData[0].id;
    //addOneTab(title,url);
	//createdetailwindow(title,url,width,height);
    if(top.window.dialogStr.indexOf(gridname)>=0)
	{
		createwindow(title, url,width,height);
	}
	else
	{
		addOneTab(title,url);
	}
}

/**
 * 多记录刪除請求
 * @param title
 * @param url
 * @param gname
 * @return
 */
function deleteALLSelect(title,url,gname) {
	gridname=gname;
    var ids = [];
    var rows = $("#"+gname).datagrid('getSelections');
    if (rows.length > 0) {
/*    	$.dialog.confirm('确定删除吗?', function(r) {
		   if (r) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				$.ajax({
					url : url,
					type : 'post',
					data : {
						ids : ids.join(',')
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#"+gname).datagrid('unselectAll');
							ids='';
						}
					}
				});
			}
		});*/
    	
		top.art.dialog({
			id:"cancelDialogId",
		    title: '操作提示',
		    icon:'delete',
		    lock:true,
		    content: '确定删除吗? ',
		    ok: function () {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				$.ajax({
					url : url,
					type : 'post',
					data : {
						ids : ids.join(',')
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#"+gname).datagrid('unselectAll');
							ids='';
						}
					}
				});
		    },
		    cancel: true
		});    	
    	
	} else {
		tipAlert("请选择至少一条信息");
	}
}

/**
 * 查看时的弹出窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function createdetailwindow(title, addurl,width,height) {
	width = width?width:700;
	height = height?height:400;
	if(width=="100%" || height=="100%"){
		width = window.top.document.body.offsetWidth;
		height =window.top.document.body.offsetHeight-100;
	}
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			content: 'url:'+addurl,
			lock : true,
			width:width,
			height: height,
			title:title,
			opacity : 0.3,
			cache:false, 
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}else{
		W.$.dialog({
			content: 'url:'+addurl,
			lock : true,
			width:width,
			height: height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false, 
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}
	
}
/**
 * 全屏编辑
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 * @param id//主键字段
 */
function editfs(title,url) {
	var name=gridname;
	 if (rowid == '') {
		tip('请选择编辑项目');
		return;
	}
	url += '&id='+rowid;
	openwindow(title,url,name,800,500);
}
// 删除调用函数
function delObj(url,name) {
	/*gridname=name;
	createdialog('删除确认 ', '确定删除该记录吗 ?', url,name);*/
	
	top.art.dialog({
		id:"cancelDialogId",
	    title: '操作提示',
	    icon:'delete',
	    lock:true,
	    content: '确定删除该记录吗 ?',
	    ok: function () {
	    	doSubmit(url,name);
	    },
	    cancel: true
	});
}

//删除调用函数
function delObjNew(content,callback) {
	top.art.dialog({
		id:"cancelDialogId",
	    title: '操作提示',
	    icon:'delete',
	    lock:true,
	    content: content,
	    ok: function () {
	    	callback();
	    },
	    cancel: true
	});
}

// 删除调用函数
function confuploadify(url, id) {
	$.dialog.setting.zIndex = $.dialog.setting.zIndex+20;
	$.dialog.confirm('确定删除吗', function(){
		deluploadify(url, id);
	}, function(){
	}).zindex();
}
function anniu(id){
	$.post(
	"scMessageController.do?detail"		
	);
}
/**
 * 执行删除附件
 * 
 * @param url
 * @param index
 */
function deluploadify(url, id) {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,// 请求的action路径
		error : function() {// 请求失败处理函数
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				$("#" + id).remove();// 移除SPAN
				m.remove(id);// 移除MAP对象内字符串
			}

		}
	});
}
// 普通询问操作调用函数
function confirm(url, content,name) {
	createdialog('提示信息 ', content, url,name);
}
/**
 * 提示信息
 */
function tip_old(msg) {
	$.dialog.setting.zIndex = 1980;
	$.dialog.tips(msg, 1);
}
/**
 * 提示信息
 */
function tip(msg) {
	/*$.dialog.setting.zIndex = 1980;
	$.messager.show({
		title : '提示信息',
		msg : msg,
		timeout : 1000 * 6
	});*/
	top.art.dialog({title: "提示信息",icon: 'succeed',lock:true,content:msg,time:3});
}

function tipAlert(msg,icon_) {
	if(!icon_){
		icon_ = 'warning';
	}
	top.art.dialog({title: "提示信息",icon: icon_,lock:true,content:msg,ok:function(){}});
}
 
/**
 * 提示信息像alert一样
 */
function alertTip(msg,title) {
	$.dialog.setting.zIndex = 1980;
	title = title?title:"提示信息";
	$.dialog({
			title:title,
			icon:'tips.gif',
			content: msg
		}).zindex();
}
/**
 * 创建添加或编辑窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function createwindow(title, addurl,width,height,name) {
	if(name != "undefined" && name != null && name != ""){
		gridname = name;
	}
	width = width?width:700;
	height = height?height:400;
	if(width=="100%" || height=="100%"){
		width = window.top.document.body.offsetWidth;
		height =window.top.document.body.offsetHeight-100;
	}
    //--author：JueYue---------date：20140427---------for：弹出bug修改,设置了zindex()函数
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			content: 'url:'+addurl,
			lock : true,
			//zIndex:1990,
			width:width,
			height:height,
			title:title,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
				saveObj();
				return false;
		    },
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}else{
		W.$.dialog({
			content: 'url:'+addurl,
			lock : true,
			width:width,
			//zIndex:1990,
			height:height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
				saveObj();
				return false;
		    },
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}
    //--author：JueYue---------date：20140427---------for：弹出bug修改,设置了zindex()函数
	
}
/**
 * 创建上传页面窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function openuploadwin(title, url,name,width, height) {
	gridname=name;
	$.dialog({
	    content: 'url:'+url,
	    cache:false,
	    button: [
	        {
	            name: "开始上传",
	            callback: function(){
	            	iframe = this.iframe.contentWindow;
					iframe.upload();
					return false;
	            },
	            focus: true
	        },
	        {
	            name: "取消上传",
	            callback: function(){
	            	iframe = this.iframe.contentWindow;
					iframe.cancel();
	            }
	        }
	    ]
	}).zindex();
}
/**
 * 创建查询页面窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function opensearchdwin(title, url, width, height) {
	$.dialog({
		content: 'url:'+url,
		title : title,
		lock : true,
		height : height,
		cache:false,
		width : width,
		opacity : 0.3,
		button : [ {
			name : '查询',
			callback : function() {
				iframe = this.iframe.contentWindow;
				iframe.searchs();
			},
			focus : true
		}, {
			name : '取消',
			callback : function() {

			}
		} ]
	}).zindex();
}
/**
 * 创建不带按钮的窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function openwindow(title, url,name, width, height) {
	$.dialog.setting.zIndex = $.dialog.setting.zIndex+10;
	gridname=name;
	if (typeof (width) == 'undefined'&&typeof (height) != 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				title : title,
				cache:false,
				lock : true,
				width: 'auto',
			    height: height
			}).zindex();
		}else{
			$.dialog({
				content: 'url:'+url,
				title : title,
				cache:false,
				parent:windowapi,
				lock : true,
				width: 'auto',
			    height: height
			}).zindex();
		}
	}
	if (typeof (height) == 'undefined'&&typeof (width) != 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				title : title,
				lock : true,
				width: width,
				cache:false,
			    height: 'auto'
			}).zindex();
		}else{
			$.dialog({
				content: 'url:'+url,
				title : title,
				lock : true,
				parent:windowapi,
				width: width,
				cache:false,
			    height: 'auto'
			}).zindex();
		}
	}
	if (typeof (width) == 'undefined'&&typeof (height) == 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				title : title,
				lock : true,
				width: 'auto',
				cache:false,
			    height: 'auto'
			}).zindex();
		}else{
			$.dialog({
				content: 'url:'+url,
				title : title,
				lock : true,
				parent:windowapi,
				width: 'auto',
				cache:false,
			    height: 'auto'
			}).zindex();
		}
	}
	
	if (typeof (width) != 'undefined'&&typeof (height) != 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				width: width,
			    height:height,
				content: 'url:'+url,
				title : title,
				cache:false,
				lock : true
			}).zindex();
		}else{
			$.dialog({
				width: width,
			    height:height,
				content: 'url:'+url,
				parent:windowapi,
				title : title,
				cache:false,
				lock : true
			}).zindex();
		}
	}
}

/**
 * 创建询问窗口
 * 
 * @param title
 * @param content
 * @param url
 */
function createdialog(title, content, url,name) {
	
/*	$.dialog.setting.zIndex = $.dialog.setting.zIndex+50;
	$.dialog.confirm(content, function(){
		doSubmit(url,name);
		rowid = '';
	}, function(){
	}).zindex();*/
	
	top.art.dialog({
		id:"cancelDialogId",
	    title: title,
	    icon:'delete',
	    lock:true,
	    content: content,
	    ok: function () {
	    	doSubmit(url,name);
			rowid = '';
	    },
	    cancel: true
	});	
}
/**
 * 执行保存
 * 
 * @param url
 * @param gridname
 */
function saveObj() {
	//alert($('#btn_sub', iframe.document).val());
	$('#btn_sub', iframe.document).click();
}

/**
 * 执行AJAX提交FORM
 * 
 * @param url
 * @param gridname
 */
function ajaxSubForm(url) {
	$('#myform', iframe.document).form('submit', {
		url : url,
		onSubmit : function() {
			iframe.editor.sync();
		},
		success : function(r) {
			tip('操作成功');
			reloadTable();
		}
	});
}
/**
 * 执行查询
 * 
 * @param url
 * @param gridname
 */
function search() {

	$('#btn_sub', iframe.document).click();
	iframe.search();
}

/**
 * 执行操作
 * 
 * @param url
 * @param index
 */
function doSubmit(url,name,data) {
	gridname=name;
	//--author：JueYue ---------date：20140227---------for：把URL转换成POST参数防止URL参数超出范围的问题
	var paramsData = data;
	if(!paramsData){
		paramsData = new Object();
		if (url.indexOf("&") != -1) {
			var str = url.substr(url.indexOf("&")+1);
			url = url.substr(0,url.indexOf("&"));
			var strs = str.split("&");
			for(var i = 0; i < strs.length; i ++) {
				paramsData[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
			}
		}      
	}
	//--author：JueYue ---------date：20140227---------for：把URL转换成POST参数防止URL参数超出范围的问题
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		data : paramsData,
		url : url,// 请求的action路径
		error : function() {// 请求失败处理函数
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				var msg = d.msg;
				tip(msg);
				reloadTable();
			}
		}
	});
	
	
}
/**
 * 退出确认框
 * 
 * @param url
 * @param content
 * @param index
 */
function exit(url, content) {
	/*var dialog;
	if($.dialog.confirm)
	{
		dialog=$.dialog;
	}
	else
	{
		dialog=window.top.lhgDialog;
	}
	dialog.confirm(content, function(){
		window.location = url;
	}, function(){
	}).zindex();*/
	
	top.art.dialog({
		id:"cancelDialogId",
	    title: '操作提示',
	    icon:'warning',
	    lock:true,
	    content: content,
	    ok: function () {
	    	window.location = url;
	    },
	    cancel: true
	});	
}
/**
 * 模板页面ajax提交
 * 
 * @param url
 * @param gridname
 */
function ajaxdoSub(url, formname) {
	$('#' + formname).form('submit', {
		url : url,
		onSubmit : function() {
			editor.sync();
		},
		success : function(r) {
			tip('操作成功');
		}
	});
}
/**
 * ajax提交FORM
 * 
 * @param url
 * @param gridname
 */
function ajaxdoForm(url, formname) {
	$('#' + formname).form('submit', {
		url : url,
		onSubmit : function() {
		},
		success : function(r) {
			tip('操作成功');
		}
	});
}

function opensubwin(title, url, saveurl, okbutton, closebutton) {
	$.dialog({
		content: 'url:'+url,
		title : title,
		lock : true,
		opacity : 0.3,
		button : [ {
			name : okbutton,
			callback : function() {
				iframe = this.iframe.contentWindow;
				win = frameElement.api.opener;// 来源页面
				$('#btn_sub', iframe.document).click();
				return false;
			}
		}, {
			name : closebutton,
			callback : function() {
			}
		} ]

	}).zindex();
}

function openauditwin(title, url, saveurl, okbutton, backbutton, closebutton) {
	$.dialog({
		content: 'url:'+url,
		title : title,
		lock : true,
		opacity : 0.3,
		button : [ {
			name : okbutton,
			callback : function() {
				iframe = this.iframe.contentWindow;
				win = $.dialog.open.origin;// 来源页面
				$('#btn_sub', iframe.document).click();
				return false;
			}
		}, {
			name : backbutton,
			callback : function() {
				iframe = this.iframe.contentWindow;
				win = frameElement.api.opener;// 来源页面
				$('#formobj', iframe.document).form('submit', {
					url : saveurl + "&code=exit",
					onSubmit : function() {
						$('#code').val('exit');
					},
					success : function(r) {
						$.dialog.tips('操作成功', 2);
						win.location.reload();
					}
				});

			}
		}, {
			name : closebutton,
			callback : function() {
			}
		} ]

	}).zindex();
}
/*获取Cookie值*/
function getCookie(c_name)
{
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=")
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1
			c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1)
				c_end = document.cookie.length
			return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
}
// 添加标签
function addOneTab(subtitle, url, icon) {
	parent.addTab(subtitle, url, icon);
}
// 关闭自身TAB刷新父TABgrid
function closetab(title) {
	debugger;
	//暂时先不刷新
	//window.top.document.getElementById('tabiframe').contentWindow.reloadTable();
	//window.top.document.getElementById('maintabs').contentWindow.reloadTable();
	window.top.$('#maintabs').tabs('close', title);
	//tip("添加成功");
}
//清空
function clearGis(name){
	$('#'+name).val("");
	 $("#showgis").hide();
}
//popupgis
function inputClickGis(obj,name,code){

	var codes =  $(obj).val();
	
	var id = $("input[name='id']").val();
	this.name = name || "{\"drawType\":\"point\"}";
	var config = $.parseJSON(this.name);   
	//this.name.drawType =  name.drawType || "point";
	var gisname = config.gisname;
	var gisid = config.gisid;
	var gistype = config.gistype;
	var jzwType = config.jzwType;
	//config.enableSpotClick=true;
	if(gisid){
		config.gisid = $("#"+gisid).val();
	}
	if(gisname){
		config.gisname = $("#"+gisname).val();
	}
	if(gistype){
		config.drawType =  $("#"+gistype).val();
	}else{
		if(!config.drawType || config.drawType == ""){
			config.drawType = "point";
		}
	}
	
	if(location.href.indexOf("mode=read")!=-1){
		config.enableEdit = false;
		
	}else if(location.href.indexOf("&layerId=")!=-1){
		config.enableEdit = false;
		
	}else if(location.href.indexOf("&editType=gis")!=-1){
		config.enableEdit = false;
		
	}else{
		config.enableEdit = true;
	}
	if(codes != "" && codes != null){
		
		config.coords = codes;
		var temp = codes.split(",");
		if(temp.length>1){
			config.centerTo = temp[0]+","+temp[1];	
		}
		
	}
	 var mapWidth = config.mapWidth==null?800:config.mapWidth;
	 var mapHeight = config.mapHeight==null?600:config.mapHeight;
	 config.mapWidth = mapWidth;
	 config.mapHeight = mapHeight
	config = JSON.stringify(config);
	 $.dialog.setting.zIndex = $.dialog.setting.zIndex+20;
	 $.dialog({
		 content: "url:systemController.do?gisDemo&config="+config,
			lock : true,
			title:"选择地理位置",
			width:mapWidth+'px',
			height: mapHeight+'px',
			cache:false,
			esc:false,
			resize:false,
			drag:false,
			max:false,
		    ok: function(){
		    	/*iframe = this.iframe.contentWindow;
		    	var selected = iframe.getSelectRows();
		    	if (selected == '' || selected == null ){
			    	alert("请选择");
		    		return false;
			    }else {
				    var str = "";
			    	$.each( selected, function(i, n){
				    	if (i==0)
				    	str+= n[name];
				    	else
			    		str+= ","+n[name];
			    	});
			    	$(obj).val("");
			    	//$('#myText').searchbox('setValue', str);
				    $(obj).val(str);
			    	return true;
			    }*/
		    	
		    	iframe = this.iframe.contentWindow;
		    	var gisxy =  iframe.$("#giscoords").val();
		    	var gisnamevalue = iframe.$("#gisname").val();
		    	var gisidvalue = iframe.$("#gisid").val();
		    	if(gisxy == "" || gisxy == null){
		    		if(location.href.indexOf("mode=read")==-1){
		    			$.dialog.alert('请选择地址位置');
			    		return false;
		    		}
		    		
		    	}else{
			    	if(jzwType != "undefined" && jzwType == true){
			    		// 建筑物类型true的场合下必须返回建筑物ID
			    		if(gisidvalue == null || gisidvalue == ""){
				    		if(location.href.indexOf("mode=read")==-1){
				    			$.dialog.alert('请选择一建筑物');
					    		return false;
				    		}
			    		}
			    	}

		    		 $(obj).val(gisxy);
		    		 $("#"+gisname).val(gisnamevalue);
		    		 $("#"+gisid).val(gisidvalue);
		    		 $("#showgis").show();
		    	}
		    	 $(obj).blur();
		    	 $("#"+gisname).blur();
	    		 $("#"+gisid).blur();
		    	
		    },
		    parent: windowapi,
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
}
//po
//popup  
//object: this  name:需要选择的列表的字段  code:动态报表的code
//动态列表页面查询datagrid属性 singleSelect ： true 只能选择一条记录
function inputClick(obj,name,code,extendsAttr,config) {
	debugger;

	$.dialog.setting.zIndex = $.dialog.setting.zIndex+20;
	config = config || "{}";
	 //$.dialog.setting.zIndex = 99999;
	 if(code==""){
		 alert("popup参数配置不全");
		 return;
	 }
	 config = eval("("+config+")");
	var content_ = "url:cgReportController.do?popup&id="+code;
	 if(code == "hl_base_user"){
		 content_= "url:cgReportControllerHl.do?popup&id="+code;
	 }

	 var attrs = [];
	 if(extendsAttr){
		 attrs = extendsAttr.split(",");
		 if(attrs.length > 0 ){
			 var temp = attrs[0].split("=");
		 
			 config['id_mapping'] = attrs[0];
		 
			 var configStr = JSON.stringify(config);
			 content_ += "&config="+configStr;
			// content_ += ('&config={"' + temp[0] + '":"' + temp[1] + '"}');
		 }
	 }

	 if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: content_ ,
				lock : true,
				title:"选择",
				width:900,
				//zIndex:2000,
				height: 500,
				cache:false,
			    ok: function(){

			    	var relation = {};
		    		var temp ;
		    		for ( var i= 0; i < attrs.length; i++) {
		    			temp = attrs[i].split("=");
		    			relation[temp[1]] = temp[0];
					}
		    		
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("请选择");
			    		return false;
				    }else {
					    var str = {};
				    	$.each( selected, function(i, n){

				    		for(var p in relation){
				    			
					    		if(n[p] == null){
					    			n[p] = '';
					    		}
					    		if(n[p + "_codevalue"]){
					    			n[p] = n[p + "_codevalue"];
					    		}
						    	if (i==0){
						    		str[relation[p]] = '';
						    		str[relation[p]] += n[p];
						    	}else{
						    		str[relation[p]] += ","+n[p];	
						    	}
				    		}
				    	});
			    		for(var p in str){
			    			var name = p.replace(".","\\.").replace("[","\\[").replace("]","\\]");
			    			$("#" + name).val(str[p]);
			    			$("#" + name).blur();
							if(code == "hl_bucusname_info"){ //报修业务选择录入模式特殊处理
								$("#input_mode").val("1");//设置为选择录入
							}
			    		}

				    	if(config && config["callback"]) {
				    		try{
					    		var fun = eval(config["callback"]); 
					    		if(fun){
						    		fun.apply(window,selected);
					    		}
				    		}catch(e){}
				    	}
			    	 
				    	/*$(obj).val("");
				    	//$('#myText').searchbox('setValue', str);
					    $(obj).val(str);*/
				    	return true;
				    }
			    	
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}else{
			$.dialog({
				content: content_,
				lock : true,
				title:"选择",
				width:900,
				height: 500,
				//zIndex:2000,
				parent:windowapi,
				cache:false,
			    ok: function(){
			    	var relation = {};
		    		var temp ;
		    		for ( var i= 0; i < attrs.length; i++) {
		    			temp = attrs[i].split("=");
		    			relation[temp[1]] = temp[0];
					}
			    		
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("请选择");
			    		return false;
				    }else {
					    var str = {};
				    	$.each( selected, function(i, n){
				    		for(var p in relation){
					    		if(n[p] == null){
					    			n[p] = '';
					    		}
					    		
					    		if(n[p + "_codevalue"]){
					    			n[p] = n[p + "_codevalue"];
					    		}
					    		
						    	if (i==0){
						    		str[relation[p]] = '';
						    		str[relation[p]] += n[p];
						    	}else{
						    		str[relation[p]] += ","+n[p];	
						    	}
				    		}
				    	});
				    	
			    		for(var p in str){
			    			var name = p.replace(".","\\.").replace("[","\\[").replace("]","\\]");
			    			$("#" + name).val(str[p]);
			    			$("#" + name).blur();
			    		}

				    	if(config && config["callback"]) {
				    		try{
					    		var fun = eval(config["callback"]); 
					    		if(fun){
						    		fun.apply(window,selected);
					    		}
				    		}catch(e){}
				    	}
			    	 
		 
				    	/*$(obj).val("");
				    	//$('#myText').searchbox('setValue', str);
					    $(obj).val(str);*/
				    	return true;
				    }
			    	
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
}
/**
 * 文档类型拍照选择
 * @param docname
 * @param docwarid
 */
function selectdoctype(docname,docwarid,type){
	debugger;
	docwarid = docwarid || "doc_id";
	doctypename = docname;
	doctypeid = docwarid;
	var docwaridvalue = $("#"+docwarid).val();
	var tableName = $("input[name=tableName]").val();
	var title = "电子资料";
	var url = "scDocWarController.do?goAdd&doctype=1&tablecode="+tableName;
	if(docwaridvalue){
		if(type == '1'){
			url = "scDocWarController.do?goUpdate&flag=up&doctype=1&id="+docwaridvalue+"&tablecode="+tableName;
		}else if(type == '2'){
			url = "scDocWarController.do?goUpdate&flag=vi&load=&id="+docwaridvalue+"&doctype=1&tablecode="+tableName;
		}
		
	}
	
	parent.parent.addTab(title,encodeURI(url));
	
}
function pushdoctype(name,id){
	debugger;
	$("#"+doctypename).val(name);
	$("#"+doctypeid).val(id);
}
/*
	自定义url的弹出
	obj:要填充的控件,可以为多个，以逗号分隔
	name:列表中对应的字段,可以为多个，以逗号分隔（与obj要对应）
	url：弹出页面的Url
*/
function popClick(obj,name,url) {
	$.dialog.setting.zIndex = $.dialog.setting.zIndex+20;
	 //$.dialog.setting.zIndex = 2001;
	var names = name.split(",");
	var objs = obj.split(",");
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择",
				width:700,
				height: 400,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("请选择");
			    		return false;
				    }else {
				    	for(var i1=0;i1<names.length;i1++){
						    var str = "";
					    	$.each( selected, function(i, n){
						    	if (i==0)
						    	str+= n[names[i1]];
						    	else{
									str+= ",";
									str+=n[names[i1]];
								}
					    	});
							if($("#"+objs[i1]).length>=1){
								$("#"+objs[i1]).val("");
								$("#"+objs[i1]).val(str);
							}else{
								$("input[name='"+objs[i1]+"']").val("");
								$("input[name='"+objs[i1]+"']").val(str);
							}
						 }
				    	return true;
				    }
					 
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}else{
			$.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择",
				width:700,
				height: 400,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("请选择");
			    		return false;
				    }else {
				    	for(var i1=0;i1<names.length;i1++){
						    var str = "";
					    	$.each( selected, function(i, n){
						    	if (i==0)
						    	str+= n[names[i1]];
						    	else{
									str+= ",";
									str+=n[names[i1]];
								}
					    	});
					    	if($("#"+objs[i1]).length>=1){
								$("#"+objs[i1]).val("");
								$("#"+objs[i1]).val(str);
							}else{
								$("[name='"+objs[i1]+"']").val("");
								$("[name='"+objs[i1]+"']").val(str);
							}
						 }
				    	return true;
				    }
					
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
}
/**
 * Jeecg Excel 导出
 * 代入查询条件
 */
function JeecgExcelExport(url,datagridId){
	var queryParams = $('#'+datagridId).datagrid('options').queryParams;
	$('#'+datagridId+'tb').find('*').each(function() {
	    queryParams[$(this).attr('name')] = $(this).val();
	});
	var params = '&';
	$.each(queryParams, function(key, val){
		params+='&'+key+'='+val;
	}); 
	var fields = '&field=';
	$.each($('#'+ datagridId).datagrid('options').columns[0], function(i, val){
		if(val.field != 'opt'){
			fields+=val.field+',';
		}
	}); 
	window.location.href = url+ encodeURI(fields+params);
}
/**
 * 自动完成的解析函数
 * @param data
 * @returns {Array}
 */
function jeecgAutoParse(data){
	var parsed = [];
    	$.each(data.rows,function(index,row){
    		parsed.push({data:row,result:row,value:row.id});
    	});
			return parsed;
}
/**
 * 获取url参数 
 * @param urlpath
 * @returns
 */
function getUrlData(urlpath) {
	
	var url = urlpath || location.href;
	 url=  decodeURIComponent(url);
   // var url = decodeURIComponent(location.href);
    var data = url.split("?")[1];
    if (!data) {
        return {};
    }
    data = data.split("&");
    var datastr = "{";
    for (var i = 0; i < data.length; i++) {
        var temp = data[i].split("=");
        if (!temp[1]) { continue; }
        datastr += '"' + temp[0] + '":"' + temp[1].replace(/#$/, '').replace(new RegExp("\"", "g"), "\\\"") + '",';
    }
    if (datastr.length > 2) {
        datastr = datastr.substring(0, datastr.length - 1);
    }
    datastr += '}';
    return eval('(' + datastr + ')');
}



function getParentWindow()
{
	var allDialog=top.window.$(".ui_border");
	//alert($(this.frameElement).parent().attr("class"));
	var iframeName=this.frameElement.name;
	var dialogDivShow=new Array();
	var currentDialog;
	var currentZindex;
	for(i=0;i<allDialog.length;i++)
	{
		//alert(i);
		var lengthPx=$(allDialog[i]).parent().css("left");
		var length=parseInt(lengthPx.substr(0,lengthPx.length-2));
		if(length>0)
		{
			//alert(length);
			
			//alert($(allDialog[i]).parent().css("z-index"));
			//alert($("iframe",$(allDialog[i])).attr("name"));
			if($("iframe",$(allDialog[i])).attr("name")==iframeName)
	  		{
	  			currentDialog=$(allDialog[i]);
	  			currentZindex=$(allDialog[i]).parent().css("z-index");
	  		}
	  		else
	  		{
	  			dialogDivShow.push($(allDialog[i]));
	  		}
		}
		
	}
	if(dialogDivShow.length>1)
	{
	  	dialogDivShow.sort(DialogAscSort);
	  	var parentWindow=$("iframe",$(dialogDivShow[0]))[0].contentWindow;
	  	return parentWindow;
	}
	else
	{
		
		var currentTab=getCurrentTab();
		
		if(currentDialog)
		{
			return currentTab.find("iframe")[0].contentWindow;
		}
		else
		{
			var parentTab=getParentTab();
			if(parentTab&&parentTab.find('iframe')[0])
				return parentTab.find('iframe')[0].contentWindow;
		}
		
		
	}
	/*for(i=0;i<dialogDivShow.length;i++)
	{
		alert(dialogDivShow[i].parent().css("z-index"));
	}*/
	//alert($("iframe",$(dialogDivShow[0]))[0].contentWindow);
	
}

function getParentTab()
{
	//alert(getCurrentTab().panel('options').url);
	//alert(getCurrentTab().panel('options').parentTab.find("iframe")[0].contentWindow.document.body.innertHTML);
	//alert(getCurrentTab().panel('options').parentTab[0].innerHTML);
	var currentTab=getCurrentTab();
	var parentTab=false;
	if(currentTab)
	{
		parentTab=currentTab.panel('options').parentTab;
	}
	//alert(parentTab);
	//alert(parentTab.html());
	return parentTab;
}

function getCurrentTab()
{
	var mainTabs = top.window.$('#maintabs');
	var currentTab=false;
	if(mainTabs.length>0)
		currentTab=mainTabs.tabs("getSelected");
	return currentTab;
}

function closeCurrentTab()
{
	var mainTabs = window.parent.$('#maintabs');
	var currentTab=mainTabs.tabs("getSelected");
	var index =mainTabs.tabs('getTabIndex',currentTab);
	
	var parentTab=getParentTab();
	//alert(parentTab);
	if(parentTab)
	{
		mainTabs.tabs('select',mainTabs.tabs('getTabIndex',parentTab));
	}
	else
	{
		if(index>=1)
		{
			mainTabs.tabs('select',index-1);
		}
	}
	
	mainTabs.tabs('close',index);
	
	
}


function DialogAscSort(a,b)
{
  return b.parent().css("z-index")-a.parent().css("z-index");
}


if(frameElement&&frameElement.api&&frameElement.api.opener)
	frameElement.api.opener=getParentWindow();

var SynCardOcx;

//通过读取身份证硬件设备的获取身份信息
function idcardRead(cardInputId,extendAttr){
	try{
		if(!SynCardOcx){
		var obj =  document.createElement('object'); 
		obj.setAttribute("classid","clsid:46E4B248-8A41-45C5-B896-738ED44C1587"); 
		obj.setAttribute("id","SynCardOcx1");
		obj.setAttribute("codeBase","SynCardOcx.CAB#version=1,0,0,1"); 
		obj.setAttribute("width",0); 
		obj.setAttribute("height",0); 
		document.body.appendChild(obj); 
		SynCardOcx=SynCardOcx1;
		}
		var nRet;
		try{
		 SynCardOcx1.SetPhotoType(1);
		 if(true){
				
				alert(SynCardOcx1.SetPhotoType(1));
				}
				SynCardOcx1.SetPhotoName(2);//照片保存文件名设置为 身份证号
				SynCardOcx1.SetPhotoPath(0,"");//"照片保存路径设置为C盘根目录
		}catch(e){
			window.top.downloadSclient();
		}
		 FindReader_onclick();
//		 PhotoPath3_onclick();//设置照片保存路径
		nRet = SynCardOcx1.ReadCardMsg();
//		debugger;
		if(nRet==0)	{
			$("#" + cardInputId ).val(SynCardOcx1.CardNo);
			
			
			
//			alert(SynCardOcx1.PhotoName);
	//		$("input[name='"+cardInputId+"']").val(SynCardOcx1.CardNo);
			if(extendAttr){
				var temp = $.parseJSON(extendAttr);
				var jsonMap = {};
				
				var img = $('<img  id="imgPre"  src="'+SynCardOcx1.PhotoName+'"/>');
//				alert( SynCardOcx1.PhotoName);
				
				for(var p in temp){
					jsonMap[temp[p]] = p;
				}
				
				if(jsonMap.name){
					$("#" + jsonMap.name ).val( SynCardOcx1.NameA);
				}
				if(jsonMap.addr){
					$("#" + jsonMap.addr ).val( SynCardOcx1.Address);
				}
				if(jsonMap.nation){
					$("#" + jsonMap.nation ).val( SynCardOcx1.Nation);
					
				}
				if(jsonMap.birthday){
					$("#" + jsonMap.birthday ).val( (SynCardOcx1.Born).substring(0,4)+"-"+(SynCardOcx1.Born).substring(4,6)+"-"+(SynCardOcx1.Born).substring(6,8));
					
				}
				if(jsonMap.sex){
					$("#" + jsonMap.sex ).val( SynCardOcx1.Sex);
				}
				if(jsonMap.lifeB){
					$("#" + jsonMap.lifeB ).val( SynCardOcx1.UserLifeB);
				}
				if(jsonMap.lifeE){
					$("#" + jsonMap.lifeE ).val( SynCardOcx1.UserLifeE);
				}
				if(jsonMap.police){
					$("#" + jsonMap.police ).val( SynCardOcx1.Police);
				}
				if(jsonMap.photo){
					$("#" + jsonMap.photo ).append( img);
					$("#" + jsonMap.photo ).append( "图片路径为："+SynCardOcx1.PhotoName);
//					document.getElementById("grzp").innerHTML='<img src='+SynCardOcx1.PhotoName+'>';
				}
			}
		}
		else if(nRet==65)	{
			$.dialog.alert("请确认已放置身份证，并且所放位置正确");	
		}
		else if(nRet==1)	{
//			ReadIDCARDInfo_Z();
			$.dialog.alert("设备未连接或者未就绪，请检查！");
		}
	}catch(e){
//		ReadIDCARDInfo_Z();
		tip("<a href='javascript:void(0);' onclick='downLoadDriver();' style='color:blue;'>您未安装相关驱动，无法读取身份证，请安装驱动后再试！点击下载</a>");
	}
}
function FindReader_onclick()
{
	var str;
	str = SynCardOcx1.FindReader();
	if (str < 0)
	{
		$.dialog.alert("没有找到读卡器");
	}
}
var SynCardOcx2;
function ReadIDCARDInfo_Z() {
	if(!SynCardOcx2){
	var obj =  document.createElement('object'); 
	obj.setAttribute("classid","clsid:6CA705D0-BB6E-46DF-BE44-64809B0B0E36"); 
	obj.setAttribute("id","VIDEOCAP");
	obj.setAttribute("codeBase","*.cab#version=0,0,0,0"); 
	obj.setAttribute("width",0); 
	obj.setAttribute("height",0); 
	document.body.appendChild(obj); 
	SynCardOcx2=VIDEOCAP;
	}
//    var info;
//    VIDEOCAP.Save_Photo("D:\\");//甚至照片保存路径
    var b = VIDEOCAP.ReadIdCard();
    if (b == 1) {
    	$("#" + cardInputId ).val(VIDEOCAP.Get_IDNumber());
    	if(extendAttr){
			var temp = $.parseJSON(extendAttr);
			var jsonMap = {};
			for(var p in temp){
				jsonMap[temp[p]] = p;
			}
			
			if(jsonMap.name){
				$("#" + jsonMap.name ).val(VIDEOCAP.Get_IDName());
			}
			if(jsonMap.addr){
				$("#" + jsonMap.addr ).val(VIDEOCAP.Get_Address());
			}
			if(jsonMap.nation){
				$("#" + jsonMap.nation ).val(VIDEOCAP.Get_Nation());
			}
			if(jsonMap.birthday){
				$("#" + jsonMap.birthday ).val( VIDEOCAP.Get_BornYear() +"-"+ VIDEOCAP.Get_BornMonth() +"-"+ VIDEOCAP.Get_BournDay());
			}
			if(jsonMap.sex){
				$("#" + jsonMap.sex ).val( VIDEOCAP.Get_Sex());
			}
			if(jsonMap.lifeB){
				$("#" + jsonMap.lifeB ).val( (VIDEOCAP.Get_VaildDate().split("-"))[0]);
			}
			if(jsonMap.lifeE){
				$("#" + jsonMap.lifeE ).val( (VIDEOCAP.Get_VaildDate().split("-"))[1]);
			}
			if(jsonMap.police){
				$("#" + jsonMap.police ).val( VIDEOCAP.Get_Mechanics());
			}
			
		}
    
    }else{
    	$.dialog.alert("设备未连接或者未就绪，请检查！！！");
    }
}
function PhotoPath3_onclick()
{
	var str="D:\\";
	var nRet;
	nRet= SynCardOcx1.SetPhotoPath(2,str);
	if(nRet == 0)
	{
		
	}
	else
	{
		
	}
}

function selectDefaultValue(config,data,datagridId){
	try{
	var id_mapping = config["id_mapping"];
	var mapping = id_mapping.split("=");

		var obj = W.document.getElementById(mapping[0]) ;
		if(!obj){
			return ;
		}
		var obj_value = obj.value ;
		if(obj_value){
			obj_value = ("," + obj_value + ",");
			for(var i=0;i<data.rows.length;i++){
				if(obj_value.indexOf("," + data.rows[i][mapping[1]] + "," ) != -1 ){
					$("#" + datagridId).datagrid("selectRecord",data.rows[i].id);
					//$("#" + datagridId).datagrid("checkRow",i);

				}
			}
		}
	}catch(e){

	}


	 
 
	
}

/** 
* 从 file 域获取 本地图片 url 
*/ 
function getFileUrl(sourceId) { 
var url; 
if (navigator.userAgent.indexOf("MSIE")>=1) { // IE 
url = document.getElementById(sourceId).value; 
} else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox 
url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0)); 
} else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome 
url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0)); 
} 
return url; 
} 

/** 
* 将本地图片 显示到浏览器上 
*/ 
function preImg(sourceId, targetId) { 
var url = getFileUrl(sourceId); 
var imgPre = document.getElementById(targetId); 
imgPre.src = url; 
} 
/** 根据id取的个人基本信息
 *  并且在展示页面展示个人证件头像
 * */
function getrkxx(){
	
	var id= $("input[name='rk_id']").val();//$("#rk_id").val();
	//alert(id);
	if(!id){
		return;
	}
	$.ajax({
	    url:"scDocWarController.do?getrkxx",
	    data:{"id":id},
		type:"get",
	    dataType:"json",
	    async:false,
	    success:function(data){
	    	var path ="";
	    	if(data.success){
	    		//$("#imgdiv").remove();
	    		var src ="";
	    		var attrs = data.attributes;
	    		if(attrs){
	    			path = attrs.grzp;
	    		}
	    		
	    		if(path==null||path==''){
	    			
	    			src="plug-in/chrhc/images/docbackground.png";
	    		}else{
	    			src = "commonController.do?viewFileftppath&ftppath="+path+"&subclassname=org.jeecgframework.web.system.pojo.base.TSAttachment";	
	    		}
	    		
				/*var divcontent = "<div  id='imgdiv'  style='float:right; margin-right:340px; border: 1px solid #A9C9E2;'><img src="+src+" style='cursor: pointer;' width='102px' height='126px'/></div>";
				
	    		$("#grzp").css("display","none");
	    			
				$("#grzp").after(divcontent);*/	
	    		
	    		addel(src);
				//alert("12");
	    		
	    	}	    	
	    				
	    },
		error:function(data){
			$.messager.alert('错误',data.msg);
			return false;
		}
	});
	
}
/** 清空头像区域  添加显示图片*/
function addel(src){
	var divcontent="";
	if(src==null||src==''){
		src="plug-in/chrhc/images/docbackground.png";
	}
	if(src.indexOf('docbackground.png')>-1){
		divcontent = "<div id='uploadify_div_grzp' style='float:left; margin-right:20px;'><img src="+src+" style='cursor: pointer;'alt='此人没有图像信息' title='此人没有图像信息' width='102px' height='126px'/></div>";
	}else{
		divcontent = "<div id='uploadify_div_grzp' style='float:left; margin-right:20px;'><img src="+src+" style='cursor: pointer;'alt='此人没有图像信息' title='图像信息' width='102px' height='126px'/></div>";
	}
	var filtype = $("#filetype");
	var childs=$("#uploadify_div_grzp")[0];
	filtype[0].removeChild(childs);//firstChild				
	//$("#filetype").empty();
	$("#filetype").append(divcontent);	
}


/**
 * 根据身份证号取得出生日期和性别
 * @param val
 * @returns {___anonymous38731_38732}
 */
function showBirthday(val) {
    var birthdayValue;
    if (15 == val.length) { //15位身份证号码
        birthdayValue = val.charAt(6) + val.charAt(7);
        if (parseInt(birthdayValue) < 10) {
            birthdayValue = '20' + birthdayValue;
        }
        else {
            birthdayValue = '19' + birthdayValue;
        }
        birthdayValue = birthdayValue + '-' + val.charAt(8) + val.charAt(9) + '-' + val.charAt(10) + val.charAt(11);
        if (parseInt(val.charAt(14) / 2) * 2 != val.charAt(14))
            sex = '1';
        else
            sex = '2';
        sr = birthdayValue;
    }
    if (18 == val.length) { //18位身份证号码
        birthdayValue = val.charAt(6) + val.charAt(7) + val.charAt(8) + val.charAt(9) + '-' + val.charAt(10) + val.charAt(11)

   + '-' + val.charAt(12) + val.charAt(13);
        if (parseInt(val.charAt(16) / 2) * 2 != val.charAt(16))
            sex = '1';
        else
            sex = '2';

        sr = birthdayValue;
    }
    var object = {};
    object.csrq = sr;
    object.xb = sex;
    return object;
}
/**
 * 身份证号验证
 * @param gets
 * @returns {Boolean}
 */
function idcardvalid(gets){
	var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子;
	var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值，10代表X;

	if (gets.length == 15) {   
		return isValidityBrithBy15IdCard(gets);   
	}else if (gets.length == 18){   
		var a_idCard = gets.split("");// 得到身份证数组   
		if (isValidityBrithBy18IdCard(gets)&&isTrueValidateCodeBy18IdCard(a_idCard)) {   
			return true;   
		}   
		return false;
	}
	return false;
	
	function isTrueValidateCodeBy18IdCard(a_idCard) {   
		var sum = 0; // 声明加权求和变量   
		if (a_idCard[17].toLowerCase() == 'x') {   
			a_idCard[17] = 10;// 将最后位为x的验证码替换为10方便后续操作   
		}   
		for ( var i = 0; i < 17; i++) {   
			sum += Wi[i] * a_idCard[i];// 加权求和   
		}   
		valCodePosition = sum % 11;// 得到验证码所位置   
		if (a_idCard[17] == ValideCode[valCodePosition]) {   
			return true;   
		}
		return false;   
	}
	
	function isValidityBrithBy18IdCard(idCard18){   
		var year = idCard18.substring(6,10);   
		var month = idCard18.substring(10,12);   
		var day = idCard18.substring(12,14);   
		var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
		// 这里用getFullYear()获取年份，避免千年虫问题   
		if(temp_date.getFullYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){   
			return false;   
		}
		return true;   
	}
	
	function isValidityBrithBy15IdCard(idCard15){   
		var year =  idCard15.substring(6,8);   
		var month = idCard15.substring(8,10);   
		var day = idCard15.substring(10,12);
		var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
		// 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
		if(temp_date.getYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){   
			return false;   
		}
		return true;
	}
}
/** 
* 通过身份证号获得年龄 
*/ 
function getAgeByCertId(cert_id,ageid) {
	// 获取输入身份证号码
	var UUserCard = cert_id;
	// 获取出生日期
	UUserCard.substring(6, 10) + "-" + UUserCard.substring(10, 12) + "-"
			+ UUserCard.substring(12, 14);

	/*if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) {
		alert("男");
		// 是男则执行代码 ...
	} else {
		alert("女");
		// 是女则执行代码 ...
	}*/

	// 获取年龄
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var day = myDate.getDate();
	var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1;
	if (UUserCard.substring(10, 12) < month
			|| UUserCard.substring(10, 12) == month
			&& UUserCard.substring(12, 14) <= day) {
		age++;
	}
	$("#" + ageid).val(age);
	// 年龄 age
}

function todellname(file_name){
	var urldata = getUrlData();
	var mark_name = urldata.mark_name;
	if(mark_name){
		var suoshu = $("#"+file_name).parent().find("span[class='suoshu']");
		if(suoshu.length){
			suoshu.attr({"disabled":true});
		}
	}
}
function GetXmlHttpObject()
{
  var xmlHttp=null;
  try
    {
    // Firefox, Opera 8.0+, Safari
    xmlHttp=new XMLHttpRequest();
    }
  catch (e)
    {
    // Internet Explorer
    try
      {
      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
      }
    catch (e)
      {
      xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
      }
    }
return xmlHttp;
}
var synRequest=function(url,func)
{
	var xmlHttp=GetXmlHttpObject();
	var handleStateChange=function (){ 
            if(xmlHttp.readyState == 4){ 
                if (xmlHttp.status == 200 || xmlHttp.status == 0){
                	func(xmlHttp);
                }
                }
                };
	xmlHttp.onreadystatechange = handleStateChange;
	xmlHttp.open("GET",url,false);
    xmlHttp.setRequestHeader("If-Modified-Since","0");
    xmlHttp.send(null);
};
var asynRequest=function(url,func)
{
	var xmlHttp=GetXmlHttpObject();
	var handleStateChange=function (){ 
            if(xmlHttp.readyState == 4){ 
                if (xmlHttp.status == 200 || xmlHttp.status == 0){
                	func(xmlHttp);
                }
                }
                };
	xmlHttp.onreadystatechange = handleStateChange;
	xmlHttp.open("GET",url,true);
    xmlHttp.setRequestHeader("If-Modified-Since","0");
    xmlHttp.send(null);
};

function openLoadingDialog(){
	window.top.showLoadingDialog();
}
//录音查看
function sound_record(){
	var dd= $("input[name='id']");
	var businessId = dd.val();
	$.ajax({
		url:"HlSoundRecordController.do?getHlOrderReord",
		data:{"businessId":businessId},
		type:"Post",
		dataType:"json",
		async:false,
		success:function(data){
			var urla = "<div class='form-control' style='height:95px;width: 50%'> ";
			if(data.obj){
				$.each(data.obj,function(i,m){
					var path = m.recordpath;
					var code = m.recordcode;
					urla += "<a style='color: #339100; cursor: pointer;'onclick=toRecord('"+path+"') >"+code+"</a><br/>";
				});
			}
			urla+="</div>";
			$("#sound_record").after(urla);
			$("#sound_record").remove();
		}
	});
}
//录音下载
function  toRecord(path){
	art.dialog.open('HlSoundRecordController.do?soundRecord&path=' + path + '&_x='
		+ Math.random(), {
		title : '录音文件',
		id : 'recordDialog',
		width : '320px',
		height : '102px',
		button: [
			{
				name: '下载',
				callback: function () {
					//window.open("HlSoundRecordController.do?downloadFile&path="+path)
					window.location.href = "HlSoundRecordController.do?downloadFile&path="+path;
					return false;
				},
				focus: true
			},
			{
				name: '关闭'
			}
		]
	});
}
//转接服务岗操作
function transitService(id){
	var userInfo = getUserInofo();
	var serviceurl = userInfo.serviceurl;
	//serviceurl = encodeURI(serviceurl);
	$("#"+id).after("<a href='#' id='visitscore' style='color: blue;font-weight: bold ' onclick=\"parent.addOneTab('业务联络单','"+serviceurl+"','pictures')\">【业务联络单】</a>");
}
function  getUserInofo(){
	var userInfo = {};
	$.ajax({
		url:"HlSoundRecordController.do?getUserInfo",
		type:"Post",
		dataType:"json",
		async:false,
		success:function(datacities){
			userInfo = datacities.attributes;
		}
	});
	return userInfo;
}
//日期格式化
Date.prototype.Format = function (fmt) {
	var o = {
		"M+": this.getMonth() + 1, //月份
		"d+": this.getDate(), //日
		"h+": this.getHours(), //小时
		"m+": this.getMinutes(), //分
		"s+": this.getSeconds(), //秒
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度
		"S": this.getMilliseconds() //毫秒
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
//查看回访
function govisitview(id){
	govisit(id);
}
//回访受理
function govisit(id){
	/*var urllist = "chrhcAutoListController.do?datagrid&configId=hl_satisfied_degree&field=id,create_name,create_by,create_date,update_name,update_by," +
	 "update_date,version_num,sys_org_code,delflag,degeree,st_state," +
	 "yxgzl,wxgzl,sxgzl,visit_person,visit_time,visit_input," +
	 "input_time,bus_type,bus_id,bza,bzb,visit_option,&dataRule=null&biztype=";*/
	var urllist = "hlSatisfiedDegreeController.do?getDegree";
	//查询是否存在满意度数据
	var zjid;
	$.ajax({
		url: urllist+"&busId="+id,
		type : 'POST',
		dataType : 'json',
		async : false,
		cache : false,
		success: function(data){
			var total = data.obj.length;
			if(total){
				//已存在数据
				zjid = data.obj[0].id;
			}else{
				//无数据，新增
				$.ajax({
					url: "chrhcFormBuildController.do?saveOrUpdate&tableName=hl_satisfied_degree&bus_id="+id+"&bus_type="+tablename+"&st_state=N",
					data: {"create_name":"","create_by":"","version_num":"","sys_org_code":"","delflag":"","create_date":""},
					type : 'POST',
					dataType : 'json',
					async : false,
					cache : false,
					success: function(dataj){
						debugger;
						zjid = dataj.obj.id;
					}
				});
			}

		}
	});
	var url = "chrhcFormBuildController.do?ftlForm&tableName=hl_satisfied_degree&id="+zjid+"&bizType=&dataRule=nul";

	addOneTab('回访受理', url, 'pictures');
}
//被叫号码处理所属公司只在新增记录时处理
function calledNumValue(){
	var urlpath = getUrlData();
	var bxid = urlpath.id;
	if(!bxid){
		var calledNum = top.calledNum;
		var hl_company = $("#hl_company");
		if(calledNum == '4006186900' || calledNum == '88870761'){//一卡通
			hl_company.val('1');
		}else if(calledNum == '4006186787' || calledNum == '66590410'){//易通
			hl_company.val('2');
		}
	}

}
//业务编辑或修改时赋值business_id,business_code,yw_code
function hotline_yw_code(){
	var map = new Map();
	map.put("hl_repair","报修单");
	map.put("hl_BuAlliance","商户加盟");
	map.put("HL_COMPLAINT","投诉咨询");
	map.put("hl_consultation","业务咨询");
	map.put("hl_dial_out","外拨业务");
	var business_id;
	var yw_code;
	var tableName;
	var curTabWin = null;
	var curTab = getCurrentTab();
	if (curTab && curTab.find('iframe').length > 0) {
		curTabWin = curTab.find('iframe')[0].contentWindow;
		if(curTabWin.$("#editForm").length > 0){
			var nn = curTabWin.$("#editForm")[0].contentWindow;
			tableName = nn.$("input[name='tableName']").val();
			business_id = nn.$("input[name='id']").val();
			yw_code = nn.$("#code").val();
		}
	}else{
		var urlpath = getUrlData();
		var bxid = urlpath.id;
		if(bxid){
			business_id = $("input[name='id']").val();
			yw_code = $("#code").val();
			tableName = $("input[name='tableName']").val();
		}
	}
	var flag = map.containsKey(tableName);//只对业务表单处理赋值
	if(flag){
		if(business_id){
			top.business_id = business_id;
			top.business_code = tableName;
			top.yw_code = yw_code;
		}

	}

}
$(document).ready(function(){
	//zxy 2015年10月27日  无tab页 
	var mainTabs = window.parent.$('#maintabs');
	if(!mainTabs.length>0)
	{
		//alert("undefined");
		if($(".Current_position").length>0)
			$(".Current_position").hide();
	}
	//zxy 2015年10月27日 scbi 无tab页
	
});
