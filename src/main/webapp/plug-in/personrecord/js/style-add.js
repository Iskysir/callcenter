$(function() { 
        // 表格收缩展开 相关JS
        $('table').on('click', '.shrink_td', function () {
			var op = $(this).attr('op');
			if($(this).hasClass('op')){
				
				$(this).removeClass('op');
				$('.'+op).removeClass('h_t');
			}else{	
				$(this).addClass('op');
				$('.'+op).addClass('h_t');
			}
        });
		
		
		$('.rk_tab_div').on('click', '.lay_i', function () {
			var list = art.dialog.list;
			for (var i in list) {
				list[i].close();
			};
			

			open_dialog();
        });
		
		var wh = $('.rk_tab_div').width();
		if(wh < 1275){
			$('.rk_tab_div table').css('margin','10px auto');
		}else{
			open_dialog();
		}
		
		$('.rk_tab_div').on('mouseover', '.lay_i', function () {
		
			$(this).attr('src','plug-in/personrecord/image/lay_i_2.png');
			$(this).css('margin-left','-75px');
			
		});
		
		$('.rk_tab_div').on('mouseout', '.lay_i', function () {
		
			$(this).attr('src','plug-in/personrecord/image/lay_i.png');
			
		});
		
});

function open_dialog(){
		var dialog10 = art.dialog({
				title:"时间轴",
				content:document.getElementById("demo10"),
				left:"98%",
				top:"10%",
				cancel:function(){},
				cancelVal:"关闭"
			});
	}