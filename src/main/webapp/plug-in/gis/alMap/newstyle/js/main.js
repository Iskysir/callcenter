/*======================
  Name:main.js
  Data:2015.5.10 13:44
  Author:zhucf
  Notes:框架JS
  =====================*/
$(document).ready(function(){ 


/*===菜单收缩相关JS===*/
$('.shrink').on('click', function (e) {
	if($('.mainbox').hasClass('shrink_mainbox')){
		$('.mainbox').removeClass('shrink_mainbox');
	}else{
		$('.mainbox').addClass('shrink_mainbox');
	}
});

/*===表格 图标选中相关JS===*/
$(function() {
  $('label_radio').on('click',function(){
    var radioId = $(this).attr('name');
    $('label_radio').removeAttr('checked') && $(this).attr('class', 'checked');
    $('input[type="radio"]').removeAttr('checked') && $('#' + radioId).attr('checked', 'checked');
  });
  
   $('label_checkbox').on('click',function(){
    var radioId = $(this).attr('name');
    $('label_checkbox').removeAttr('checked') && $(this).attr('class', 'checked');
    $('input[type="checkbox"]').removeAttr('checked') && $('#' + radioId).attr('checked', 'checked');
  });
 
});

/*===高度自适应相关JS===*/
adaptiveH();

/*===点击菜单相关JS===*/

  $('.menu_no li').on('click',function(){
		$('.menu_no li').each(function() {
			if( $(this).hasClass('selected')){
				$(this).removeClass('selected');
			}
		});
		$(this).addClass('selected');	
  });

  $('.menu_ul li').on('click',function(){
		window.location.href=$(this).attr("href");	
  });
  
  /*===点击菜单相关JS===*/

  $('.grid_ico_ul').on('click','li',function(){
		$('.grid_ico_ul li').each(function() {
			if( $(this).hasClass('selected')){
				$(this).removeClass('selected');
			}
		});
		if( $(this).hasClass('lay_li_rm')){
			
			$(this).removeClass('selected');
			$(this).removeClass('lay_li_rm');
		}else{
			$(this).addClass('selected');
		}

		
  });
  
  $('.remove_div_i').on('click',function(){
		$('.grid_ico_ul li').each(function() {
			if( $(this).hasClass('lay_li')){
				$(this).addClass('lay_li_rm');
			}
		});
  });

  

}); 


function adaptiveH(){
	var  win = $(window).height();
	var  hea = $('.header').height();
	var  foot = $('.footer').height();
	var mai = win - hea - foot-5;
	$('.mainbox').height(mai);
}

window.onload=function(){  
  window.onresize = adaptiveH;  
  adaptiveH();  
}  