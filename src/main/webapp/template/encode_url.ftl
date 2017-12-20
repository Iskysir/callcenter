
<script type="text/javascript" src="plug-in/jquery/jquery.base64.js"></script>

<script type="text/javascript">
	(function($) {
		$.getUrlParam = function(url ,name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			var r = url.match(reg);
			if (r != null) {
				return unescape(r[2]);
			}
			return '';
		};
	})(jQuery);
	
	function replaceParamVal(oUrl ,paramName,replaceWith) {
	    var re=eval('/('+ paramName+'=)([^&]*)/gi');
	    return oUrl.replace(re,paramName+'='+replaceWith);
	}


$(function(){
	$(document).ajaxSend(function(evt, request, settings) {
		var fieldVal = $.getUrlParam(settings.url , 'field') ;
		if(fieldVal){
			settings.url = replaceParamVal(settings.url , 'field', $.base64.encode(fieldVal))
			//alert(settings.url);
		}
		//alert(replaceParamVal(settings.url , 'field',"000"));
		//
	});	
});
</script>