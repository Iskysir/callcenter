(function() {
	
	this.synergy = {
			version : '0.1.1',
			options : {
				operationTag : 'handle',
				urlTag : 'url',
				subMenuId : 'subMenu',
				contentId : 'inbox-wrapper'
			},
			path : '/',
			seesionId : ''
		};   
	
	/**
	 * 调度器,根据相应的URL调用不同的内容管理对象
	 */
	var dispatcher = (function() {
		var detailsList = {};
		var dispose = function(str) {
			return str.replace(/\//gm, '').replace(/\./gm, '');
		};

		return {
			register : function(id, details) {
				detailsList[dispose(id)] = details;
			},

			/**
			 * @param id：ajax请求URL
			 * @param isInit：是否点击菜单
			 * @returns
			 */
			dispatch : function(id, isInit) {
				var processedURL = String(dispose(id));
				var details = detailsList[processedURL];
				// 如果找不到已匹配的路径名称，则按最长匹配策略寻找匹配的url
				if (!details) {
					var resultKey = null;
					for ( var key in detailsList) {
						if (processedURL.indexOf(key, 0) != 0) {
							continue;
						}
						// 获取最长匹配的对象类
						if (resultKey == null || resultKey.length < String(key).length) {
							resultKey = key;
						}
					}
					// 获取匹配的处理类，并添加到地址映射中
					if (resultKey != null) {
						details = detailsList[resultKey];
						detailsList[processedURL] = details;
					}

				}
				// 获取目标JS类，并执行初始化操作
				if (details) {
					details.initHandlers();
					if (isInit) {
						details.initDetails();
					}
					return details;
				}
			}
		};
	})();

	/**
	 * @class 主界面包装类
	 * @description 主要界面的包装类。
	 * @version 0.1.1
	 */
	this.synergy.Wrapper = new Class({
		Implements : [ Options, Events ],

		options : {
			operationTag : synergy.options.operationTag,
			urlTag : synergy.options.urlTag,
			subMenuId : synergy.options.subMenuId,
			contentId : synergy.options.contentId,
			activeClass : 'active',
			synergy_path:initpath,
			refresh_time: 300000//5分钟  刷新时间
		},

		initialize : function(options) {
			this.setOptions(options);
			this.initGlobalAjaxEvent();
			this.initMainMenu();
			this.initSubMenu();
			this.initRemind();
		},
		
		/**
		 * 初始化全局ajax请求事件
		 */
		initGlobalAjaxEvent : function() {
			//设置全局ajax请求不使用缓存
			$.ajaxSetup({
				cache:false
			});
			
			// 使用ajaxComplete事件，在ajaxSuccess方法完成之后调用
			$(document).ajaxComplete(function(event, xhr, settings) {
				//启用handler标签对应的处理函数
				//$('body').find('[' + synergy.options.operationTag + ']').attr('disabled',false) ;
				
				/* 绑定事件 */
				var contenttype = xhr.getResponseHeader('Content-Type');
				if ((contenttype && contenttype.indexOf('html') >= 0) && xhr.readyState == "4") {
					var flag = String(settings.url).indexOf("loadMenu") == -1 ? false : true;
					this.details = dispatcher.dispatch(settings.url, flag);
					if (this.details) {
						this.details.content = this.content;
					}
					
					/*提示信息框的处理*/
					$(".table-striped td:not(td:has(input[type='checkbox'])):not(td:has(i)):not(td:has(label)):not(td:has(a)),.table-bordered td:not(td:has(input[type='checkbox'])):not(td:has(i)):not(td:has(label)):not(td:has(a)),div.ell").each(function(){
						var _td = $(this);
						if(_td.text().trim()!=""){
							$(this).attr("title",$(this).text());
							
						};
					});
					
					$('div.tooltip').hide();
				}

			}.bind(this));
		},

		/**
		 * 初始化主菜单
		 */
		initMainMenu : function() {
			$('#main-menu a').each(function(index, el) {

				var temphref = $(el).attr('href');
				var fullhref = window.location.href;
				
				if (fullhref.indexOf(temphref)>=0) {
					$(el).parent().addClass('active');
					return;
				}
			});
		},
		
		/**
		 * 初始化子菜单
		 */
		initSubMenu : function() {
			this.subMenu = $('#' + this.options.subMenuId);
			this.content = $('#' + this.options.contentId);
	
			/*添加点击事件*/
			this.subMenu.click(function(event) {
				var current = $(event.target);
				/*菜单切换关闭office控件*/
				try{
					var hw1 = document.getElementById("HWPostil1");
					if(hw1){
						hw1.CloseDoc(0);
					}
				}catch(e){
				}
				try{
					var hw2 = document.getElementById("HWPostil2");
					if(hw2){
						hw2.CloseDoc(0);
					}
				}catch(e){
				}
				try{
					var wo1 = document.getElementById("WebOffice1");
					if(wo1){
						wo1.CloseDoc(0);
					}
				}catch(e){
				}
				try{
					var wo2 = document.getElementById("WebOffice2");
					if(wo2){
						wo2.CloseDoc(0);
					}
				}catch(e){
				}
				/*菜单切换关闭office控件*/
				
				
				var url = current.attr('url');
				if (url) {
					this.loadContent(current);
				} else {
					current = current.next().find('li').first().find('a');
					this.loadContent(current);
				}
				return false;
			}.bind(this));
			/*加载默认菜单*/
			setTimeout(function(){
				var mr=UtilMisc.getCookie('mr');
				
				var isdispose=mr?mr.get('isdispose'):null ;
				
				if(isdispose&&(isdispose=="true")){
					var current = this.getCurrentMenu() ;
					this.initCurrentState(current);
					//当前高亮菜单项的名称和code
					this.activeMenuName = current.text() ;
					this.activeMenuCode = current.attr("code") ;
					this.fireEvent('dispose', mr) ;
					//如果是二级菜单则直接加载二级菜单项
					var menulevel = mr.get('menulevel')?mr.get('menulevel'):null ;
					if(menulevel&&menulevel=='2'){
						this.loadContent(current) ;
					}
					/*删除cookie，以免按f5涮洗时还是按此请求*/
					UtilMisc.removeCookie('mr') ;
					return ;
				}
				
				this.loadContent(this.getCurrentMenu()) ;
				/*删除cookie，以免按f5涮洗时还是按此请求*/
				UtilMisc.removeCookie('mr');
			}.bind(this),100);
		},
		
		loadContent:function(current) {
			//当前高亮菜单项的名称和code
			this.activeMenuName = current.text() ;
			this.activeMenuCode = current.attr("code") ;
			
			var url = current.attr('url') ;
			if(url){
				url=url +"?loadMenu=true" ;
				this.content.load(url, function() {
					this.initCurrentState(current);
				}.bind(this));
			}
		},
		
		/**
		 *初始化当前状态 
		 */
		initCurrentState : function(current) {
			this.subMenu.find('.active').removeClass('active');
			this.subMenu.find('.nextArrow-open').removeClass('nextArrow-open');
			
			var parentid = current.attr("parent");
			if (parentid){
				var parent=$('#' + parentid);
				parent.addClass('active');
				parent.find('a span').addClass('nextArrow-open');
			}
				
			current.parent().addClass('active');
			
			this.initModelInfo(current) ;
		},
		/**
		 * 初始化模块信息
		 * current：初始化模块的dom元素
		 */
		initModelInfo : function(current){
			//模块名字
			var modelName = String($('#main-menu li.active').text()).trim() ;
			//主菜单名字
			var mainMenuName = String(current.parents(".sub-menu").parent().children().first().text()).trim() ;
			//子菜单名字
			var subMenuName = String(current.text()).trim() ;
			//如果菜单名字为空，则菜单名称为模块名字
			if(mainMenuName.length<1){
				mainMenuName = modelName ;
			}
			var mainMenuDom = this.content.find(".gw-title1") ;
			if(mainMenuDom.length>0){
				//主菜单名字
				mainMenuDom.first().text(mainMenuName) ;
				//子菜单名字
				mainMenuDom.first().parent().children("h3").first().text(subMenuName) ;
			}
		},
		/**
		 * 获取当前菜单
		 */
		getCurrentMenu:function(){
			var mr=UtilMisc.getCookie('mr');
			var code=mr?mr.get('code'):null;
			if(code){
				return this.subMenu.find('a[code='+code+']');
			}
			return this.subMenu.find('a[url]').first();
		},
		
		/**
		 * 根据菜单code进行请求
		 */
		doRequestByCode:function(code){
			this.loadContent(this.subMenu.find('a[code='+code+']'));
		},
		
		/**
		 * 注册内容对象
		 */
		register : function(id, details) {
			dispatcher.register(id, details);
		},

		/**
		 * 获取当前内容对象
		 */
		getDetails : function() {
			return this.details;
		},

		/********************************************************
		 * 定时提醒
		 ********************************************************/
		initRemind: function(){
			if($('#chat-message-count').size() == 0) return false;
			this.refreshEvents();
			window.setInterval(this.refreshEvents.bind(this),this.options.refresh_time);
			
		},
		
		refreshEvents: function(){
			//通知公告
			if($('#rightNotice').size() > 0){
				this.notice();
			}
			//会议安排
			if($('#rightMeeting').size() > 0){
				this.meeting();
			}
			//待办事项
			if($('#rightApplybase').size() > 0){
				this.applyBase();
			}
			//收文
			if($('#rightDocRecv').size() > 0){
				this.docRecv();
			}
			//发文
			if($('#rightSendRecv').size() > 0){
				this.sendRecv();
			}
			//日程安排
			if($('#rightCalendar').size() > 0){
				this.calendar();
			}
			
			//加载完成后，最后执行统计
			this.plus();
		},
		
		plus: function(){
			var count = $('#rightNotice').find('li:gt(0)').size() + 
			$('#rightMeeting').find('li:gt(0)').size() + 
			$('#rightDocRecv').find('li:gt(0)').size() + 
			$('#rightSendRecv').find('li:gt(0)').size() + 
			$('#rightApplybase').find('li:gt(0)').size() +
			$('#rightCalendar').find('li:gt(0)').size();
			
			$('#chat-message-count').text(count);
			if(count == 0){
				$('#chat-message-count').hide();
			}
		},
		
		meeting: function(){
			var url = this.options.synergy_path + "/inteoffice/meetingapp/indexMeetingList";
			$.ajax({
				type:"post",
				url: url,
				async:false,
				dataType:"json",
				success:function(data){
					$('#rightMeeting').find('li:gt(0)').remove();
					var meetingall = data.meetingAll;
					if(meetingall.length > 0){
						$('#rightMeeting').show();
					}else{
						$('#rightMeeting').hide();
					}
					for(var i = 0; i < meetingall.length; i++){
						meeting = meetingall[i];
						$('#rightMeeting').append("<li class='wrapper-content'>" +
												"<div class='col-lg-10'>" +
												"<span><a onclick=\"UtilMisc.setCookie(\'mr\',\'code=inteoffice_meetingmanage\');\"  href='" + synergy.path + "/inteoffice/index'>" + meeting.topical + "</a></span>" +
												"</div>" +
												"</li>");
					}
				}.bind(this),
	            error:function() {
//	                alert('右侧提醒列表-会议安排，数据获取失败。');
	            }
			});
		},
		
		notice: function(){
			var url = this.options.synergy_path + "/peraffairs/notice/findPanelNotice/";
			$.ajax({
				type:"post",
				url: url,
				async:false,
				dataType:"json",
				success:function(data){
					$('#rightNotice').find('li:gt(0)').remove();
					var all = data.noticeAll;
					if(all.length > 0){
						$('#rightNotice').show();
					}else{
						$('#rightNotice').hide();
					}
					for(var i = 0; i < all.length; i++){
						obj = all[i];
						$('#rightNotice').append("<li class='wrapper-content'>" +
												"<div class='col-lg-10'>" +
												"<span><a onclick=\"UtilMisc.setCookie(\'mr\',\'code=peraffairs_notice\');\"  href='" + synergy.path + "/peraffairs/index'>" + obj.title + "</a></span>" +
												"</div>" +
												"</li>");
					}
				}.bind(this),
	            error:function() {
//	                alert('右侧提醒列表-通知公告，数据获取失败。');
	            }
			});
		},
		applyBase : function() {
			var url = this.options.synergy_path + "/adstaffairs/applybase/findpanelapplybase";
			$.ajax({
				type : "post",
				url : url,
				async : false,
				dataType : "json",
				success : function(data) {
					$("#rightApplybase").find("li:gt(0)").remove();
					var all = data.contentList;
					if (all) {
						if(all.length > 0){
							$("#rightApplybase").show();
						}else{
							$("#rightApplybase").hide();
						}
						for (var i = 0; i < all.length; i++) {
							var obj = all[i];
							$('#rightApplybase').append("<li class='wrapper-content'>" +
									"<div class='col-lg-10'>" +
									"<span><a onclick=\"UtilMisc.setCookie(\'mr\',\'code=adstaffairs_applybase_todoinit\');\"  href='" + synergy.path + "/adstaffairs/index'>" + obj.formContent.formTitle + "</a></span>" +
									"</div>" +
									"</li>");
						}
					}
				}.bind(this),
				error : function() {
//					alert('右侧提醒列表-待办事务，数据获取失败。');
				}
			});
		},
		docRecv: function(){
			var url = synergy.path + "/docmanage/docrecv/findAllList";
			$.ajax({
				type:"post",
				url: url,
				async:false,
				dataType:"json",
				success:function(data){
					$('#rightDocRecv').find('li:gt(0)').remove();
					if(data.length > 0){
						$('#rightDocRecv').show();
					}else{
						$('#rightDocRecv').hide();
					}
					for(var i = 0; i < data.length; i++){
						obj = data[i];
						$('#rightDocRecv').append("<li class='wrapper-content'>" +
												"<div class='col-lg-10'>" +
												"<span><a onclick=\"UtilMisc.setCookie(\'mr\',\'code=docmanage_docrecv&isdispose=true&id=" + obj.id + "&taskid=" + obj.curTaskId + "&viewtype=edit\');\" href='" + synergy.path + "/docmanage/index?code=docmanage_docrecv'>" + ((obj.title==undefined)?'':obj.title) + "</a></span>" +
												"</div>" +
												"</li>");
					}
				}.bind(this),
	            error:function() {
//	                alert('右侧提醒列表-通知公告，数据获取失败。');
	            }
			});
		},
		sendRecv: function(){
			var url = synergy.path + "/docmanage/docsend/findAllList";
			$.ajax({
				type:"post",
				url: url,
				async:false,
				dataType:"json",
				success:function(data){
					
					$('#rightSendRecv').find('li:gt(0)').remove();
					if(data.length > 0){
						$('#rightSendRecv').show();
					}else{
						$('#rightSendRecv').hide();
					}
					for(var i = 0; i < data.length; i++){
						obj = data[i];
						$('#rightSendRecv').append("<li class='wrapper-content'>" +
												"<div class='col-lg-10'>" +
												"<span><a onclick=\"UtilMisc.setCookie(\'mr\',\'code=docmanage_docsend&isdispose=true&id=" + obj.id + "&taskid=" + obj.curTaskId + "&viewtype=edit\');\" href='" + synergy.path + "/docmanage/index?code=docmanage_docsend'>" + ((obj.title==undefined)?'':obj.title) + "</a></span>" +
												"</div>" +
												"</li>");
					}
				}.bind(this),
	            error:function() {
//	                alert('右侧提醒列表-通知公告，数据获取失败。');
	            }
			});
		},
		calendar: function(){
			var url = synergy.path + "/peraffairs/schedule/calendar/findRemindList";
			$.ajax({
				type:"post",
				url: url,
				async:false,
				dataType:"json",
				success:function(data){
					var info = data.info;
					$('#rightCalendar').find('li:gt(0)').remove();
					if(info.length > 0){
						$('#rightCalendar').show();
					}else{
						$('#rightCalendar').hide();
					}
					for(var i = 0; i < info.length; i++){
						obj = info[i];
						$('#rightCalendar').append("<li class='wrapper-content'>" +
												"<div class='col-lg-10'>" +
												"<span><a href='" + synergy.path + "/peraffairs/index'>" + obj.content + "</a></span>" +
												"</div>" +
												"</li>");
					}
				}.bind(this),
	            error:function() {
//	                alert('右侧提醒列表-通知公告，数据获取失败。');
	            }
			});
		}
	});

	/**
	 * @class 主界面包装类
	 * @description 主要界面的包装类。
	 * @version 0.1.1
	 */
	this.synergy.Details = new Class({
		Implements : [ Options, Events ],

		options : {
			operationTag : synergy.options.operationTag,
			contentId : synergy.options.contentId,
			isAutoReg : false,
			regUrl : ''
		},

		initialize : function(options) {
			this.setOptions(options);
			this.autoReg();
		},
		/**
		 * 初始化handle标签的处理函数
		 */
		initHandlers : function() {
			var operationTag = this.options.operationTag;
			var currentObj = this;
			$('body').find('[' + operationTag + ']').each(function(i, dom) {
				// 使用dom的属性监测是否在dom上已经绑定了click事件
				if (!$(dom).data("hasClickHandler")) {
					//如果没有找到要绑定的函数，则不绑定，同时也不设置事件绑定标识hasClickHandler
					$(dom).click(function(event) {
						var fun = currentObj[$(dom).attr(operationTag)];
						if(fun){
							$(dom).attr('disabled',true) ;
							//调用Mootools对Function的扩展，执行函数
							fun.attempt([ dom, event ], currentObj);
							$(dom).attr('disabled',false) ;
						}
					});
					//如果已经绑定了事件，则不再重复绑定
					$(dom).data("hasClickHandler", 1);
				}
			});
		},

		initDetails : function() {
		},

		/**
		 * 实现自动注册
		 */
		autoReg : function() {
			if (this.options.isAutoReg) {
				dispatcher.register(this.options.regUrl, this);
			}
		}
	});

	/**
	 * 提供分页服务
	 */
	this.synergy.Page = new Class({
		Implements : [ Options, Events ],

		options : {
			target : null,
			isInitPage : true,
			conSelectStr : '#' + synergy.options.contentId
		// onPaging:分页加载时,在此分页事件中可以进行查询操作
		},

		initialize : function(options) {
			this.setOptions(options);
			if (this.options.isInitPage) {
				this.initPage();
			}
		},

		/**
		 * 初始化分页查询
		 */
		initPage : function() {

			var container = $(this.options.conSelectStr);
			var pageNumber = parseInt(container.find('.pagetemp').val()) + 1;

			container.find('.pageNo').click(function(event) {
				if (!$(event.delegateTarget).hasClass('active')) {
					this.doPageNumer(parseInt($(event.delegateTarget).text()));
				}

			}.bind(this));

			container.find('.prev').click(function(event) {
				if (!$(event.delegateTarget).hasClass('disabled')) {
					this.doPageNumer(pageNumber - 1);
				}

			}.bind(this));

			container.find('.next').click(function(event) {
				if (!$(event.delegateTarget).hasClass('disabled')) {
					this.doPageNumer(pageNumber + 1);
				}

			}.bind(this));

			container.find('.pageselect').change(function(event) {
				this.doPageNumer($(event.delegateTarget).val());
			}.bind(this));

		},

		/**
		 * 跳转到某个页面
		 */
		doPageNumer : function(pageNumber) {
			$(this.options.conSelectStr).find('.page').val(pageNumber);
			if (typeof (this.options.target) == 'function') {
				this.options.target();
			}
			this.fireEvent('paging', pageNumber);
		}
	});

	/**
	 * 创建Map
	 */
	this.synergy.Map = new Class({
		initialize : function() {
			this.elements = new Array();
			// 获取Map元素个数
			this.size = function() {
				return this.elements.length;
			},

			// 判断Map是否为空
			this.isEmpty = function() {
				return (this.elements.length < 1);
			},

			// 删除Map所有元素
			this.clear = function() {
				this.elements = new Array();
			},

			// 向Map中增加元素（key, value)
			this.put = function(_key, _value) {
				if (this.containsKey(_key) == true) {
					if (this.containsValue(_value)) {
						if (this.remove(_key) == true) {
							this.elements.push({
								key : _key,
								value : _value
							});
						}
					} else {
						this.elements.push({
							key : _key,
							value : _value
						});
					}
				} else {
					this.elements.push({
						key : _key,
						value : _value
					});
				}
			},
			
			// 向Map中增加元素（key, value) by xu_csheng 2014-07-28
			this.putByKey = function(_key, _value) {
				if (this.containsKey(_key) == true) {
					
						if (this.remove(_key) == true) {
							this.elements.push({
								key : _key,
								value : _value
							});
						}
				} else {
					this.elements.push({
						key : _key,
						value : _value
					});
				}
			},

			// 删除指定key的元素，成功返回true，失败返回false
			this.remove = function(_key) {
				var bln = false;
				try {
					for (i = 0; i < this.elements.length; i++) {
						if (this.elements[i].key == _key) {
							this.elements.splice(i, 1);
							return true;
						}
					}
				} catch (e) {
					bln = false;
				}
				return bln;
			},

			// 获取指定key的元素值value，失败返回null
			this.get = function(_key) {
				try {
					for (i = 0; i < this.elements.length; i++) {
						if (this.elements[i].key == _key) {
							return this.elements[i].value;
						}
					}
				} catch (e) {
					return null;
				}
			},

			// 获取指定索引的元素（使用element.key，element.value获取key和value），失败返回null
			this.element = function(_index) {
				if (_index < 0 || _index >= this.elements.length) {
					return null;
				}
				return this.elements[_index];
			},

			// 判断Map中是否含有指定key的元素
			this.containsKey = function(_key) {
				var bln = false;
				try {
					for (i = 0; i < this.elements.length; i++) {
						if (this.elements[i].key == _key) {
							bln = true;
						}
					}
				} catch (e) {
					bln = false;
				}
				return bln;
			},

			// 判断Map中是否含有指定value的元素
			this.containsValue = function(_value) {
				var bln = false;
				try {
					for (i = 0; i < this.elements.length; i++) {
						if (this.elements[i].value == _value) {
							bln = true;
						}
					}
				} catch (e) {
					bln = false;
				}
				return bln;
			},

			// 获取Map中所有key的数组（array）
			this.keys = function() {
				var arr = new Array();
				for (i = 0; i < this.elements.length; i++) {
					arr.push(this.elements[i].key);
				}
				return arr;
			},

			// 获取Map中所有value的数组（array）
			this.values = function() {
				var arr = new Array();
				for (i = 0; i < this.elements.length; i++) {
					arr.push(this.elements[i].value);
				}
				return arr;
			};

		}
	});

	
})();
