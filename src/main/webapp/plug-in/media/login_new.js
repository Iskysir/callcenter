var url_title_map = {};

function initFirstMenu() {
	var colors = ['red','orange','yellow','green','blue','cyan','purple'];
	
	$.getJSON("loginController.do?getTree",{},function(data){
         var menuStr = 	'';
         var func ;
		  $.each(data, function(i, menu){
			  if(!menu.isParent){
				  func = "addTab('" + menu.name + "','" + menu.functionurl + "','" + menu.id + "') ";
				  menuStr += ('<li onclick="' + func + '"><a class="active" href="javascript:void(0);"><div class="tinct ' + colors[i % 7] +'"></div><i class="fa fa-desktop fa-3x"></i><span class="title">' + menu.name +'</span></a></li>');
			  }else {
				  func = "loadSubMenu(this,'" + menu.id + "') ";
				  menuStr += ('<li onclick="' + func + '"><a class="active" href="javascript:void(0);"><div class="tinct ' + colors[i % 7] +'"></div><i class="fa fa-desktop fa-3x"></i><span class="title">' + menu.name +'</span><span class="arrow "></span></a></li>');
			  }
			 
		    });
		$("#menuTree").append(menuStr);
	});
}

function loadSubMenu(obj,id) {
	 if ($(obj).children().hasClass('sub-menu') == true) {
		 return ;
     }
	$.getJSON("loginController.do?getTree&id=" + id,{},function(data){
		var menuStr = '<ul class="sub-menu">';
		var func ;
		$.each(data, function(i, menu){
			  if(!menu.isParent){
				  func = "addTab('" + menu.name + "','" + menu.functionurl + "','" + menu.id + "') ";
				  menuStr += ('<li onclick="' + func + '"><a href="javascript:void(0);"  url="' + menu.functionurl + '" class="add">' + menu.name + '</a></li>');
			  }else {
				  func = "loadSubMenu(this,'" + menu.id + "') ";
				  menuStr += ('<li onclick="' + func + '"><a href="javascript:void(0);" class="add">' + menu.name + '<span class="arrow"></span></a></li>');
			  }
		});
		menuStr += '</ul>';
		$(obj).append(menuStr);
	
		$(obj).find("a").first().click();
	});
}
function addTab(subtitle, url, id) {

	$.post(
		"scMostMenuController.do?mostMenuList",
		{"menuId":id}
	);

	
	var curA = $("a[url='" + url +"']");
	var position = curA.text();
	var p = curA.parent().parent().prev("a");
	
	while(p.size() > 0){
		position = (p.text() + "&nbsp;>&nbsp;" + position);
		p = p.parent().parent().prev("a");
		
	}
	url_title_map[serverFullPath + "/" + url + "#"] = position;
	url_title_map[serverFullPath + "/" + url] = position;
	
	var pahei = $(".page-content").height()-60;
	var progress = $("div.messager-progress");
	var currentTab=window.top.$('#maintabs').tabs('getSelected');
	if(progress.length){return;}
	rowid="";
	$.messager.progress({
		text : loading,
		interval : 200
	});
	if (!$('#maintabs').tabs('exists', subtitle)) {
		//判断是否进行href方式打开tab，默认为iframe方式
		if(url.indexOf('isHref') != -1){
			$('#maintabs').tabs('add', {
				title : subtitle,
				href : url,
				closable : true,
				//icon : icon,
				parentTab:currentTab
			});	
		}else{
			if(window.top.$('#maintabs').tabs('tabs').length > 10){
				//alert($.dialog.alert);
				lhgDialog.alert('打开窗口不能超过10个');
				$.messager.progress('close');
				return ;
			}
			$('#maintabs').tabs('add', {
			});		
			$('#maintabs').tabs('update', {
				tab : $('#maintabs').tabs('getSelected'),
				options : {
					title : subtitle,
					content : '<iframe src="' + url + '" frameborder="0"   style="width:100%;height:'+pahei+'px"></iframe>',
					//cache:false,
					closable : true,
					//icon : icon,
					parentTab:currentTab
				}
			});
			
		}

	} else {
		$('#maintabs').tabs('select', subtitle);
		$('#maintabs').tabs('update', {
			tab : $('#maintabs').tabs('getSelected'),
			options : {
				title : subtitle,
				content : '<iframe src="' + url + '" frameborder="0"   style="width:100%;height:'+pahei+'px"></iframe>',
				//cache:false,
				closable : true,
				//icon : icon,
				parentTab:currentTab
			}
		});
		
		$.messager.progress('close');
	}

	tabClose();

}

function tabClose() {
	/* 双击关闭TAB选项卡 */
	$(".tabs-inner").dblclick(function() {
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close', subtitle);
	})
	/* 为选项卡绑定右键 */
	$(".tabs-inner").bind('contextmenu', function(e) {
		$('#mm').menu('show', {
			left : e.pageX,
			top : e.pageY
		});

		var subtitle = $(this).children(".tabs-closable").text();

		$('#mm').data("currtab", subtitle);
		// $('#maintabs').tabs('select',subtitle);
		return false;
	});
}

$.parser.onComplete = function() {/* 页面所有easyui组件渲染成功后，隐藏等待信息 */
	if ($.browser.msie && $.browser.version < 7) {/* 解决IE6的PNG背景不透明BUG */
	}
	window.setTimeout(function() {
		$.messager.progress('close');
	}, 200);
};