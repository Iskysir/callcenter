var url_title_map = {};
/*通过类型获取六种图标图片url
 * */
function getUrlbyType(url,type)
{
	var  result="";
	if(url)
	{
	var index=url.lastIndexOf(".png");
	if(index>0)
	{
		var urlWithOutExtName=url.substring(0, index);
		var _index=urlWithOutExtName.lastIndexOf("_");
		if(_index>0)
		{
			var urlWithOut_=urlWithOutExtName.substring(0, _index);
			result=result+(urlWithOut_);
			result=result+("_");
			result=result+(type);
		}
		else
		{
			result=result+(urlWithOutExtName);
			result=result+("_");
			result=result+(type);
		}
		result=result+(".png");
	}
	}
	return result;
}
function initFirstMenu() {
	var colors = ['red','orange','yellow','green','blue','cyan','purple'];
	
	$.getJSON("loginController.do?getTree",{},function(data){

         var menuStr = 	'';
         var func ;
		  $.each(data, function(i, menu){
			  var urlHide=getUrlbyType(menu.PATH,"hide");;
			  var urlShow=getUrlbyType(menu.PATH,"show");
			 // alert(JSON.stringify(menu));
			  if(!menu.isParent){

				  func = "addTab('" + menu.NAME + "','" + menu.FUNCTIONURL + "','" + menu.ID + "') ";
				  menuStr += ('<li class="m_li" onclick="' + func + '"><a class="active m_a" href="javascript:void(0);"><div class="tinct ' + colors[i % 7] +'"></div><b class="img_url"><img class="img_h" src="'+urlHide+'"/><img class="img_w"  src="'+urlShow+'"/></b><span class="title">' + menu.NAME +'</span></a></li>');
			  }else {
				  func = "loadSubMenu(this,'" + menu.ID + "') ";
				  menuStr += ('<li class="m_li" onclick="' + func + '"><a class="active m_a" href="javascript:void(0);"><div class="tinct ' + colors[i % 7] +'"></div><b class="img_url"><img class="img_h" src="'+urlHide+'"/><img class="img_w" src="'+urlShow+'"/></b><span class="title">' + menu.NAME +'</span><span class="arrow "></span></a></li>');
			  }
			 
		    });
		$("#menuTree").append(menuStr);
		//addTab('首页','scUserDeskController.do?scUserDesk');
	});
}

function loadSubMenu(obj,id) {
	 if ($(obj).children().hasClass('sub-menu') == true) {
		 return ;
     }
	$.getJSON("loginController.do?getTree&id=" + id+"&_x="+Math.random(),{},function(data){
		var menuStr = '<ul class="sub-menu">';
		var func ;
		$.each(data, function(i, menu){
			  if(!menu.isParent){
				  func = "addTab('" + menu.NAME + "','" + menu.FUNCTIONURL + "','" + menu.ID + "') ";
				  menuStr += ('<li class="me_2" onclick="' + func + '"><a href="javascript:void(0);"  url="' + menu.FUNCTIONURL + '" class="add">' + menu.NAME + '</a></li>');
			  }else {
				  func = "loadSubMenu(this,'" + menu.ID + "') ";
				  menuStr += ('<li class="me_2" onclick="' + func + '"><a href="javascript:void(0);" class="add">' + menu.NAME + '<span class="arrow"></span></a></li>');
			  }
		});
		menuStr += '</ul>';
		$(obj).append(menuStr);
		
		$(obj).find("a").first().click();
	});
}
function addTab(subtitle, url, id) {

	if(id){
		$.post(
			"scMostMenuController.do?mostMenuList",
			{"menuId":id}
		);
	}
	
	var curA = $("a[url='" + url +"']");
	if(curA.text() ){
		var position = "<a class='last' href='javascript:void(0)'>" + curA.text() + "</a>";
		var p = curA.parent().parent().prev("a");
		
		while(p.size() > 0){
			position = ("<a href='javascript:void(0)'>" + p.text() + "</a>" + position );
			p = p.parent().parent().prev("a");
			
		}
		url_title_map[serverFullPath + "/" + url + "#"] = position;
		url_title_map[serverFullPath + "/" + url] = position;
	}
 
	var pahei = $(".page-content").height()-60;
	var progress = $("div.messager-progress");
	var currentTab=window.top.$('#maintabs').tabs('getSelected');
	if(progress.length){return;}
	rowid="";
	openLoadingDialog();
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
				tipAlert('打开窗口不能超过10个');
				window.top.closeLoadingDialog();
				return ;
			}
			$('#maintabs').tabs('add', {
			});		
			$('#maintabs').tabs('update', {
				tab : $('#maintabs').tabs('getSelected'),
				options : {
					title : subtitle,
					content : '<div style="padding:5px;"><iframe frameborder="0" scrolling="auto" src="'+url+'" style="width:100%;height:'+pahei+'px" ></iframe></div>',
					//cache:false,
					closable : true,
					//icon : icon,
					parentTab:currentTab
				}
			});
			window.top.closeLoadingDialog();
		}

	} else {
		$('#maintabs').tabs('select', subtitle);
		$('#maintabs').tabs('update', {
			tab : $('#maintabs').tabs('getSelected'),
			options : {
				title : subtitle,
				content : '<div style="padding:5px;"><iframe frameborder="0" scrolling="auto" src="'+url+'" style="width:100%;height:'+pahei+'px" ></iframe></div>',
				//cache:false,
				closable : true,
				//icon : icon,
				parentTab:currentTab
			}
		});
		
		window.top.closeLoadingDialog();
	}
	//tabClose();

}

//绑定右键菜单事件
function tabCloseEven() {
	 
	// 关闭当前
	$('#mm-tabclose').click(function() {
		var currtab_title = $('#mm').data("currtab");
		$('#maintabs').tabs('close', currtab_title);
	})
	// 全部关闭
	$('#mm-tabcloseall').click(function() {
		$('.tabs-inner span').each(function(i, n) {
			var t = $(n).text();
            if (t != '首页') {
                $('#maintabs').tabs('close', t);
            }
		});
	});
	// 关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function() {
		$('#mm-tabcloseright').click();
		$('#mm-tabcloseleft').click();
	});
	// 关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function() {
		var nextall = $('.tabs-selected').nextAll();
		if (nextall.length == 0) {
			// msgShow('系统提示','后边没有啦~~','error');
			//alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			$('#maintabs').tabs('close', t);
		});
		return false;
	});
	// 关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function() {
		var prevall = $('.tabs-selected').prevAll();
		if (prevall.length == 0) {
			//alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
            if (t != '首页') {
                $('#maintabs').tabs('close', t);
            }
		});
		return false;
	});

	// 退出
	$("#mm-exit").click(function() {
		$('#mm').menu('hide');
	});
}

function tabClose() {
	/* 双击关闭TAB选项卡 */
	$(".tabs-inner").dblclick(function() {
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close', subtitle);
	})
	/* 为选项卡绑定右键 */
	$(".tabs-inner").live('contextmenu', function(e) {
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

function initMyTask(){
	$.getJSON("taskController.do?taskAllList&field=id,Process_processDefinition_id,Process_processDefinition_name,Process_task_id,Process_task_name,Process_task_description",{"page":1,"rows":5},function(data){
		
		$("#taskCount").text(data.total);
		$("#taskCount2").text(data.total);
		if(data.total == 0){
			//$("#taskCount2").hide();
			document.getElementById("taskCount2").style.display="none";
		}
		var func ;
		$.each(data.rows, function(i, task){
			func = "addTab('"+ task.Process_processDefinition_name + "','taskController.do?goTaskTab&taskId=" + task.Process_task_id+ "') ";
			$("#mytaskLi").after('<li onclick="' + func + '"><a href="#"> <span class="task"> <span class="desc">' +task.Process_processDefinition_name+ '</span> <span class="percent">' + task.Process_task_description + '</span></span> '
					+ ' </span> <span class="progress progress-success "> <span style="width: ' + task.Process_task_description + ';" class="bar"></span> </span></a></li>');
		});
		 
	});
}

$.parser.onComplete = function() {/* 页面所有easyui组件渲染成功后，隐藏等待信息 */
	if ($.browser.msie && $.browser.version < 7) {/* 解决IE6的PNG背景不透明BUG */
	}
	window.setTimeout(function() {
		dialogLoading.close();
	}, 200);
};