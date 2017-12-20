var chrhcMapBaseUrl;
(function() {
    var isWinRT = (typeof Windows === "undefined") ? false : true;
    var r = new RegExp("(^|(.*?\\/))(ChrhcMap.Include\.js)(\\?|$)"),
    s = document.getElementsByTagName('script'),
    src, m, baseurl = "";
    for(var i=0, len=s.length; i<len; i++) {
        src = s[i].getAttribute('src');
        if(src) {
            var m = src.match(r);
            if(m) {
                baseurl = m[1];
				chrhcMapBaseUrl = baseurl;
                break;
            }
        }
    }
    function inputScript(inc){
        if (!isWinRT) {
            var script = '<' + 'script type="text/javascript" src="' + inc + '"' + '><' + '/script>';
            document.writeln(script);
        } else {
            var script = document.createElement("script");
            script.src = inc;
            document.getElementsByTagName("HEAD")[0].appendChild(script);
        }
    }
    function inputCSS(url,style){
        if (!isWinRT) {
            var css = '<' + 'link rel="stylesheet" href="' + style + '"' + '><' + '/>';
            document.writeln(css);
        } else { 
            var link = document.createElement("link");
            link.rel = "stylesheet";
            link.href =  "/theme/default/" + style;
            document.getElementsByTagName("HEAD")[0].appendChild(link);
        }
    }
    //加载类库资源文件
    function loadSMLibs() {
        inputScript(baseurl+'../js/Library.js');
	//inputScript(baseurl+'../easyui/jquery-1.7.2.min.js');
		inputScript(baseurl+'../js/HashMap.js');
		//inputScript(baseurl+'../easyui/jquery.easyui.min.js');
        inputCSS("",baseurl + '../css/bootstrap.min.css');
	   // inputCSS("",baseurl + '../easyui/themes/default/easyui.css');
		inputCSS("",baseurl + '../easyui/themes/icon.css');
       /*  inputCSS("",baseurl + '../theme/default/google.css');
		inputCSS("",baseurl + '../examples/css/infowindow.css');
		*/
    }
    //引入汉化资源文件
    function loadLocalization() {
        inputScript(baseurl + 'Lang/zh-CN.js');
    }
	function loadChrhcJs(){
		 inputScript(baseurl + 'chrhc_sysConfig.js');
		 inputScript(baseurl + 'synergy.base.js');
		 inputScript(baseurl + 'chrhc_map.js');
		 inputScript(baseurl + 'chrhc_layer.js');
		 inputScript(baseurl + 'chrhc_business_functions.js');
		 inputScript(baseurl + 'chrhc_symble.js');
		 inputScript(baseurl + 'chrhc_utils.js');
		 inputScript(baseurl + 'chrhc_query.js');
		
		 
		 
		 

	}

    loadSMLibs();loadChrhcJs();
})();
