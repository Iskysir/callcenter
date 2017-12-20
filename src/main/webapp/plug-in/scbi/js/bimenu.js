var dialog_Loading=false;
	function showLoadingDialog()
	{
		/* alert(1);
		alert(dialog_Loading); */
		if(dialog_Loading)
		{
			dialog_Loading.show();
		}
		else
		{
			
			dialog_Loading = window.top.art.dialog({
		         title:false,
		         esc: false,
		         cancel:false,
		         lock:true,
		         content:'<img src="plug-in/chrhc/images/logo_load.gif"/><span class="m-l-30" style="white-space:nowrap;"> 数据加载中…</span>'
		        });
		}
	}
	function closeLoadingDialog()
	{
		dialog_Loading.hide();
	}

/*通过类型获取六种图标图片url
 * */
function getUrlbyType(url,type)
{
	var  result="";
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
	return result;
}
$(document).ajaxComplete(function(event, xhr, settings) {
	/* 绑定事件 */
	//alert(xhr.responseText.indexOf('loginController.do?login'));
	if(xhr.responseText.indexOf('loginController.do?login') != -1&&xhr.responseText.indexOf("$(document).ajaxComplete(function(event, xhr, settings)")==-1){
	    //判断如果当前页面不为主框架，则将主框架进行跳转
	    //alert(xhr.responseText.substring(xhr.responseText.indexOf('loginController.do?login'),xhr.responseText.length));
	  	var tagert_URL = "<%=path%>/loginController.do?login";
	    if(self==top){
	    	window.location.href = tagert_URL;
	    }else{
	    	top.location.href = tagert_URL;
	    }
	}
});