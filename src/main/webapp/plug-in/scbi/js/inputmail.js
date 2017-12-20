/*======================
  Name:inputmail.js
  Data:2015.9.23 13:44
  Author:zhucf
  Notes:搜索菜单JS
  =====================*/
  
(function($){
	
	$.fn.extend({
		"changeTips":function(value){
			value = $.extend({
				divTip:""
			},value);
			
			var $this = $("#menu_Search_name");
			var indexLi = 0;
			

			
			//点击document隐藏下拉层
			$(document).click(function(event){
				
				
				
				if($(event.target).attr("class") == value.divTip||$(event.target).is("li")){
					
							if($(event.target).is("li")){
								var pa = $(event.target).parent().attr("class");
								if(pa == 'on_changes'){
									
										var liVal = $(event.target).text();
										//alert(liVal);
										leftMenuClick(event.target);
										$this.val(liVal);
										blus();	
									}
								
							}else{
								
								var liVal = $(event.target).text();
								alert(liVal);
								/*
								var title=$(event.target).attr("title");
								var url=$(event.target).attr("url");
								alert(url);*/
								$this.val(liVal);
								blus();		
							}
					
				}else{
					if($(event.target).attr("class") == "menu_S_i"){
						
						valChange();
					}else{
						blus();
					}
					
				}
			});
			
			//隐藏下拉层
			function blus(){
				$(value.divTip).hide();
			}
			
			//键盘上下执行的函数
			function keychang(up){
				if(up == "up"){
					if(indexLi == 1){
						indexLi = $(value.divTip).children().length-1;
					}else{
						indexLi--;
					}
				}else{
					if(indexLi ==  $(value.divTip).children().length-1){
						indexLi = 1;
					}else{
						indexLi++;
					}
				}
				$(value.divTip).children().eq(indexLi).addClass("active").siblings().removeClass();	
			}
			
			//值发生改变时
			function valChange(){
				
				var tex = $this.val();//输入框的值
				//让提示层显示，并对里面的LI遍历
				
				if($this.val()==""){
					
					blus();
				}else{
					//ajax 获取数据
					
					var html = $.ajax({
						  url: encodeURI("scbiMenuController.do?userMenu&key="+tex),
						  async: false
						}).responseText;
					//alert(html);
					var stu1=eval(html);
					/*var stu1 = [
						{ 
							"menu": '人口密度分布图',
							"img_small":"ico_small_01.png"
						},
						{ 
							"menu": '常驻人口密度分布图',
							"img_small":"ico_small_02.png"
						},
						{ 
							"menu": '流动人口密度分布图',
							"img_small":"ico_small_03.png"
						},
						{ 
							"menu": '低保户密度分布图',
							"img_small":"ico_small_04.png"
						},
						{ 
							"menu": '育龄妇女密度分布图',
							"img_small":"ico_small_05.png"
						},
						{ 
							"menu": '新生儿密度分布图',
							"img_small":"ico_small_06.png"
						},
					]*/
					//清空UL数据；
					$('.on_changes').empty();
					
					var se = $(stu1).size();
					
					if(se == 0){
						$('.on_changes').append('<li title="">没有匹配菜单..</li>');
					}
					else{
						$('.on_changes').append('<li title="">请选择菜单..</li>');
						for(var i in stu1) {
							$('.on_changes').append('<li id="'+stu1[i].id+'" title="'+stu1[i].name+'" img="'+stu1[i].path+'" url="'+stu1[i].functionurl+'" onclick=leftMenuClick(this)"></li>');
						}
					}
					
					$(value.divTip).show().children().each(function(index) {
						
							var img_sma = $(this).attr("img");
							var valAttr = '<img src="'+img_sma+'"/>'+$(this).attr("title");
						
							if(index==1){
									$(this).html(valAttr).addClass("active").siblings().removeClass();
								
								}
								//索引值大于1的LI元素进处处理
								if(index>1){
								
									$(this).html(valAttr);
									
								}
					})
				}			
			}
			
			
			
			//输入框值发生改变的时候执行函数，这里的事件用判断处理浏览器兼容性;
			if($.browser.msie){
					if($.browser.version == '9.0' ){
						$("body").on("propertychange input", "#menu_Search_name", function () { 
							valChange();
							
						});	
					}else{
						if($.browser.version == '10.0' || $.browser.version == '11.0'){
								$('#menu_Search_name').bind("input",function(){
									valChange();
								});
						}

						$('#menu_Search_name').bind("propertychange",function(){
							valChange();
						
						});
					}
			}else{
				$('#menu_Search_name').bind("input",function(){
					valChange();
					
				});
				
			}
			
			
			//鼠标点击和悬停LI
			$('.on_changes').on('hover', 'li', function() {
				indexLi = $(this).index();//获取当前鼠标悬停时的LI索引值;
				if($(this).index()!=0){
					$(this).addClass("active").siblings().removeClass();
				}
			});
					
		
			//按键盘的上下移动LI的背景色
			$this.keydown(function(event){
				if(event.which == 38){//向上
					keychang("up");
				}else if(event.which == 40){//向下
					keychang();
				}else if(event.which == 13){ //回车
					alert($(event.target).html());
					return;
					/*var liVal = $(value.divTip).children().eq(indexLi).text();
					$this.val(liVal);*/
					//blus();
				}
			});				
		}	
	});	

})(jQuery);

var menuSeacher=null;

$(function(){
	menuSeacher=$("#menu_Search_name").changeTips({
		divTip:".on_changes"
	}); 
	

	
		
})