$(document).ready(
		function() {
			$("input[name='part_name']").parent().hide();
			$("input[name='memname']").parent().hide();
			
			 $("select[name='serv_objtype']").change(function(){				
				var value = $("select[name='serv_objtype']").val();				
				if(value){
					if("fuwudui" == value){
						$("input[name='part_name']").parent().show();
						$("input[name='memname']").val('');
						$("input[name='memname']").parent().hide();
					}
					if("fuwuzhe" == value){
						$("input[name='memname']").parent().show();
						$("input[name='part_name']").val('');
						$("input[name='part_name']").parent().hide();
					}				
				}else{
					$("input[name='memname']").val('');
					$("input[name='part_name']").val('');
					$("input[name='part_name']").parent().hide();
					$("input[name='memname']").parent().hide();					
				}
				
			 });
		});//document 加载