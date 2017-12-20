var string = {};
string.Format = function () {
    var param = Array.prototype.slice.apply(arguments);
    var format = param.shift();
    return format.replace(/\{(\d+)}/g, function (m, i) {
        return param[i];
    });
};

function fnGetWindowWidth() {var string = {};
string.Format = function () {
    var param = Array.prototype.slice.apply(arguments);
    var format = param.shift();
    return format.replace(/\{(\d+)}/g, function (m, i) {
        return param[i];
    });
};

function fnGetWindowWidth() {
    //debugger;
    var vw = 0;
    var _dEt = document.documentElement;
    var _dBy = document.body;
    if (typeof window.innerWidth == 'number') {
        vw = window.innerWidth;
    }
    else {
        if (_dEt && _dEt.clientWidth) {
            vw = _dEt.clientWidth;
        }
        else {
            if (_dBy && _dBy.clientWidth) vw = _dBy.clientWidth;
        }
    }
    if (!vw || vw < 100) {
        vw = 100;
    }
    return vw;
}
function fnGetWindowHeight() {
    //debugger;
    var vh = 0;
    var _dEt = document.documentElement;
    var _dBy = document.body;
    if (typeof window.innerHeight == 'number') {
        vh = window.innerHeight;
    }
    else {
        if (_dEt && _dEt.clientHeight) {
            vh = _dEt.clientHeight;
        }
        else {
            if (_dBy && _dBy.clientHeight) vh = _dBy.clientHeight;
        }
    }
    if (!vh || vh < 100) {
        vh = 100;
    }
    return vh;
};

function onresize() {
    //debugger;
    var windowWidth = fnGetWindowWidth();
    var windowHeight = fnGetWindowHeight();
    document.getElementById("EdushiMap").style.height = windowHeight - 5 + "px";
    document.getElementById("EdushiMap").style.width = windowWidth-5 + "px";
    document.getElementById("eyeEdushiMap").style.position = "absolute";
    document.getElementById("eyeEdushiMap").style.bottom = "20px";
    document.getElementById("eyeEdushiMap").style.right = "30px";

    if (typeof (vM) != 'undefined') {
        vM.MapWidth(windowWidth);
        vM.MapHeight(windowHeight - 33);
    }
}

var Class = {
    create: function () {
        return function () {
            return this.initialize.apply(this, arguments)
        }
    }
};
//模拟继承
Object.extend = function (instance, source) {
    if (instance && source) {
        for (var property in source) {
            instance[property] = source[property];
        }
        return instance;
    }
};
Function.prototype.bind = function () {
    var b = this,
        a = $A(arguments),
        c = a.shift();
    return function () {
        return b.apply(c, a.concat($A(arguments)))
    }
};
Function.prototype.bindAsEventListener = function (c) {
    var b = this,
        a = $A(arguments),
        c = a.shift();
    return function (d) {
        return b.apply(c, [d || window.event].concat(a).concat($A(arguments)))
    }
};
var $A = Array.from = function (a) {
    if (!a) return [];
    if (a.toArray) return a.toArray();
    else {
        for (var c = [], b = 0, d = a.length; b < d; b++) c.push(a[b]);
        return c
    }
};
Object.extend(String.prototype, {
    //去字符串头部空格
    ltrim: function () {
        return this.replace(/^\s+/g, "");
    },
    //去字符串尾部空格
    rtrim: function () {
        return this.replace(/\s+$/g, "");
    },
    //去字符串两头空格
    trim: function () {
        return this.replace(/^\s+|\s+$/g, "");
    },
    //检测字符串是否以指定的字符开头
    startWith: function (str) {
        if (str) {
            return this.includeWith(str) == 0;
        }
        return false;
    },
    //检测字符串是否以指定的字符结尾
    endWith: function (str) {
        if (str) {
            return this.lastIndexOf(str) == 0;
        }
        return false;
    },
    //检测字符串中是否包含指定的字符
    contains: function (str) {
        if (str) {
            return this.indexOf(str) != -1;
        }
    },
    isEmpty: function () {
        return this.trim() == "";
    },
    isInteger: function () {
        var exp = this.toString();
        if (!exp.length) return true;
        return /^[0-9]+$/g.test(this);
    },
    isDate: function () {
        var exp = this.toString().replace(/-/, "/");
        return Date.parse(exp).isNumber();
    },
    isNumber: function () {
        var exp = this.toString();
        if (!exp.length) return true;
        return /^[0-9]+(\.\d+)?$/g.test(exp);
    },
    Format: function () {
        var param = Array.prototype.slice.apply(arguments);
        var format = param.shift();
        return format.replace(/\{(\d+)}/g, function (m, i) {
            return param[i];
        });
    }
});
Object.extend(Number.prototype, {
    isNumber: function () {
        return /^[0-9]+$/g.test(this.toString());
    },
    isDecimal: function () {
        return /^[0-9]+(\.\d+)?$/g.test(this.toString());
    }
});
function fnRequest(strName) {
    var strHref = window.document.location.href;
    var intPos = strHref.indexOf("?");
    var strRight = strHref.substr(intPos + 1);
    var arrTmp = strRight.split("&");
    for (var i = 0; i < arrTmp.length; i++) {
        var arrTemp = arrTmp[i].split("=");
        if (arrTemp[0].toUpperCase() == strName.toUpperCase()) return arrTemp[1];
    }
    return "";
}
function fnGetWindowWidth() {
    var vw = 0;
    var _dEt = document.documentElement;
    var _dBy = document.body;
    if (typeof window.innerWidth == 'number') {
        vw = window.innerWidth;
    }
    else {
        if (_dEt && _dEt.clientWidth) {
            vw = _dEt.clientWidth;
        }
        else {
            if (_dBy && _dBy.clientWidth) vw = _dBy.clientWidth;
        }
    }
    if (!vw || vw < 100) {
        vw = 100;
    }
    return vw;
}
function fnGetWindowHeight() {
    var vh = 0;
    var _dEt = document.documentElement;
    var _dBy = document.body;
    if (typeof window.innerHeight == 'number') {
        vh = window.innerHeight;
    }
    else {
        if (_dEt && _dEt.clientHeight) {
            vh = _dEt.clientHeight;
        }
        else {
            if (_dBy && _dBy.clientHeight) vh = _dBy.clientHeight;
        }
    }
    if (!vh || vh < 100) {
        vh = 100;
    }
    return vh;
};

function ENetwork() { };
ENetwork.GetExecutionID = function () {
    var a = new Date, b = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate(), a.getHours(), a.getMinutes(), a.getSeconds(), a.getMilliseconds());
    b += Math.round(Math.random() * 1000000);
    return b
};
ENetwork.DownloadScript = function (a, b, c) {
    try {
        if (a == null || a == "undefined" || a.length == 0) {
            throw new ENetworkException("ENetwork:DownloadScript", "err_noscripturl", l24ht);
        }
        var elScript = document.createElement("script");
        elScript.type = "text/javascript";
        elScript.language = "javascript";
        elScript.id = typeof (c) == "undefined" ? ENetwork.GetExecutionID() : c;
        elScript.src = a;
        if (document.getElementById(c)) {
            ENetwork.GetAttachTarget().removeChild(document.getElementById(c));
        }
        ENetwork.GetAttachTarget().appendChild(elScript);
        if (navigator.userAgent.indexOf("IE") >= 0) {
            elScript.onreadystatechange = function () {
                if (elScript && ("loaded" == elScript.readyState || "complete" == elScript.readyState)) {
                    elScript.onreadystatechange = null;
                    if (b) {
                        b();
                    }
                }
            }
        }
        else {
            elScript.onload = function () {
                elScript.onload = null;
                if (b) {
                    b();
                }
            }
        }
        return elScript.id;
    }
    catch (e) {
        alert('加载失败！');
    }
};
ENetwork.GetAttachTarget = function () {
    if (document.getElementsByTagName("head")[0] != null) {
        return document.getElementsByTagName("head")[0];
    }
    else {
        throw new ENetworkException("ENetwork:cstr", "err_noheadelement", l611ft);
    }
};
function ENetworkException(b, c, a) {
    this.source = b;
    this.name = c;
    this.message = a;
}
ENetworkException.prototype.Name = this.name;
ENetworkException.prototype.Source = this.source;
ENetworkException.prototype.Message = this.message;

//通用复制方法
function fnCopyToClipboard(txt, msg) {
    if (window.clipboardData) {
        window.clipboardData.clearData();
        window.clipboardData.setData("Text", txt);
    } else if (navigator.userAgent.indexOf("Opera") != -1) {
        window.location = txt;
    } else if (window.netscape) {
        try {
            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
        } catch (e) {
            alert("您的浏览器未开启复制功能！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");
            return false;
        }
        var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
        if (!clip)
            return;
        var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
        if (!trans)
            return;
        trans.addDataFlavor('text/unicode');
        var str = new Object();
        var len = new Object();
        var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
        var copytext = txt;
        str.data = copytext;
        trans.setTransferData("text/unicode", str, copytext.length * 2);
        var clipid = Components.interfaces.nsIClipboard;
        if (!clip)
            return false;
        clip.setData(trans, null, clipid.kGlobalClipboard);
    }
    else {
        alert('您的浏览器不支持拷贝功能。');
        return false;
    }
    if (msg != null && msg != '') {
        alert(msg);
    }
    return true;
}
function setCookie(name, value) {
    var argv = setCookie.arguments;
    var argc = setCookie.arguments.length;
    var expires = (argc > 2) ? argv[2] : null;
    if (expires != null) {
        var LargeExpDate = new Date();
        LargeExpDate.setTime(LargeExpDate.getTime() + (expires * 1000 * 3600 * 24));
    }
    document.cookie = name + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + LargeExpDate.toGMTString()));
}

function getCookie(Name) //cookies读取JS 
{
    var search = Name + "="
    if (document.cookie.length > 0) {
        offset = document.cookie.indexOf(search)
        if (offset != -1) {
            offset += search.length
            end = document.cookie.indexOf(";", offset)
            if (end == -1) end = document.cookie.length
            return unescape(document.cookie.substring(offset, end))
        }
        else return "";
    }
}
function deleteCookie(name) {
    var expdate = new Date();
    expdate.setTime(expdate.getTime() - (86400 * 1000 * 1));
    setCookie(name, "", expdate);
}

function AlaDemo() { };
AlaDemo.$ = function () {
    var objElements = [];
    for (var i = 0; i < arguments.length; i++) {
        var objEle = arguments[i];
        if (typeof arguments[i] == 'string') {
            objEle = document.getElementById(arguments[i]);
        }
        objElements.push(objEle);
    }
    if (arguments.length == 1) {
        return objEle;
    }
    else {
        return objElements;
    }
};
AlaDemo.$Rnd = function (flg) {
    var d, s = '';
    if (!flg) flg = 100000;
    d = new Date();
    s += d.getHours();
    s += d.getMinutes();
    s += d.getSeconds();
    s += d.getMilliseconds();
    return Math.round(Math.random() * flg).toString() + s.toString();
};
AlaDemo.loadScript = function (url, callback) {
    /// <summary>
    /// 加载jacascript
    /// </summary>
    /// <param name="url">js路径</param>
    /// <param name="callback">加载成功回调函数</param>
    var script = document.createElement("script")
    script.type = "text/javascript";
    if (script.readyState) { //IE 
        script.onreadystatechange = function () {
            if (script.readyState == "loaded" || script.readyState == "complete") {
                script.onreadystatechange = null;
                callback();
            }
        };
    } else { //Others 
        script.onload = function () {
            callback();
        };
    }
    script.src = url;
    document.getElementsByTagName("head")[0].appendChild(script);
};
AlaDemo.Ajax = function () {
    function request(url, opt) {
        /// <summary>ajax方法</summary>
        /// <param name="url" type="String" mayBeNull="false">请求的URL（无参数）</param>
        /// <param name="opt" type="Object" mayBeNull="true">配置参数对象{async:false//默认异步,method:''GET''//请求方式,parameters:a=1//url参数,success:function(xhr){}//成功方法,failure:function(xhr){}//失败方法,loading:function(xhr){}//正在请求方法}</param>
        /// <returns type="Object">返回Ajax对象</returns>
        function fn() { }
        var async = opt.async !== false,
        method = opt.method || 'GET',
        parameters = opt.parameters || null,
        success = opt.success || fn,
        failure = opt.failure || fn;
        loading = opt.loading || fn;
        method = method.toUpperCase();
        if (method == 'GET' && parameters) {
            url += (url.indexOf('?') == -1 ? '?' : '&') + parameters;
            data = null;
        }
        var xhr = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject('Microsoft.XMLHTTP');
        xhr.onreadystatechange = function () {
            _onStateChange(xhr, success, failure, loading);
        };
        xhr.open(method, url, async);
        if (method == 'POST') {
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded;');
            //xhr.setRequestHeader('Content-type', 'application/json,text/javascript');
        }
        xhr.send(parameters);
        return xhr;
    }
    function _onStateChange(xhr, success, failure, loading) {
        //debugger;
        if (xhr.readyState == 4) {//处理完毕
            var s = xhr.status;
            if (s >= 200 && s < 300) {
                success(xhr);
            } else if (s == 400) {
                failure(xhr);
            } else if (s == 404) {
                failure(xhr);
            } else if (s = 500) {
                failure(xhr);
            } else {
                failure(xhr);
            }
        } else if (xhr.readyState == 1) {//正在加载
            loading(xhr);
        } else {
            //2、加载完毕 3、正在处理
        }
    }
    return { request: request };
} (); 
    //debugger;
    var vw = 0;
    var _dEt = document.documentElement;
    var _dBy = document.body;
    if (typeof window.innerWidth == 'number') {
        vw = window.innerWidth;
    }
    else {
        if (_dEt && _dEt.clientWidth) {
            vw = _dEt.clientWidth;
        }
        else {
            if (_dBy && _dBy.clientWidth) vw = _dBy.clientWidth;
        }
    }
    if (!vw || vw < 100) {
        vw = 100;
    }
    return vw;
}
function fnGetWindowHeight() {
    //debugger;
    var vh = 0;
    var _dEt = document.documentElement;
    var _dBy = document.body;
    if (typeof window.innerHeight == 'number') {
        vh = window.innerHeight;
    }
    else {
        if (_dEt && _dEt.clientHeight) {
            vh = _dEt.clientHeight;
        }
        else {
            if (_dBy && _dBy.clientHeight) vh = _dBy.clientHeight;
        }
    }
    if (!vh || vh < 100) {
        vh = 100;
    }
    return vh;
};

function onresize() {
    //debugger;
    var windowWidth = fnGetWindowWidth();
    var windowHeight = fnGetWindowHeight();
    
   // document.getElementById("EdushiMap").style.height = windowHeight + "px";
   // document.getElementById("EdushiMap").style.width = windowWidth+ "px";
    
    document.getElementById("EdushiMap").style.height = windowHeight - 5 + "px";
    document.getElementById("EdushiMap").style.width = windowWidth-5 + "px";
    document.getElementById("EdushiMap").style.overflow  = "hidden";
    
    document.getElementById("eyeEdushiMap").style.position = "absolute";
   
    document.getElementById("eyeEdushiMap").style.bottom = "20px";
    document.getElementById("eyeEdushiMap").style.right = "30px";
    //alert("windowWidth="+(document.getElementById("EdushiMap").style.height)+" windowHeight="+(document.getElementById("EdushiMap").style.width));
    if (typeof (vM) != 'undefined') {
        vM.MapWidth(windowWidth);
        vM.MapHeight(windowHeight);
    }
}

var Class = {
    create: function () {
        return function () {
            return this.initialize.apply(this, arguments)
        }
    }
};
//模拟继承
Object.extend = function (instance, source) {
    if (instance && source) {
        for (var property in source) {
            instance[property] = source[property];
        }
        return instance;
    }
};
Function.prototype.bind = function () {
    var b = this,
        a = $A(arguments),
        c = a.shift();
    return function () {
        return b.apply(c, a.concat($A(arguments)))
    }
};
Function.prototype.bindAsEventListener = function (c) {
    var b = this,
        a = $A(arguments),
        c = a.shift();
    return function (d) {
        return b.apply(c, [d || window.event].concat(a).concat($A(arguments)))
    }
};
var $A = Array.from = function (a) {
    if (!a) return [];
    if (a.toArray) return a.toArray();
    else {
        for (var c = [], b = 0, d = a.length; b < d; b++) c.push(a[b]);
        return c
    }
};
Object.extend(String.prototype, {
    //去字符串头部空格
    ltrim: function () {
        return this.replace(/^\s+/g, "");
    },
    //去字符串尾部空格
    rtrim: function () {
        return this.replace(/\s+$/g, "");
    },
    //去字符串两头空格
    trim: function () {
        return this.replace(/^\s+|\s+$/g, "");
    },
    //检测字符串是否以指定的字符开头
    startWith: function (str) {
        if (str) {
            return this.includeWith(str) == 0;
        }
        return false;
    },
    //检测字符串是否以指定的字符结尾
    endWith: function (str) {
        if (str) {
            return this.lastIndexOf(str) == 0;
        }
        return false;
    },
    //检测字符串中是否包含指定的字符
    contains: function (str) {
        if (str) {
            return this.indexOf(str) != -1;
        }
    },
    isEmpty: function () {
        return this.trim() == "";
    },
    isInteger: function () {
        var exp = this.toString();
        if (!exp.length) return true;
        return /^[0-9]+$/g.test(this);
    },
    isDate: function () {
        var exp = this.toString().replace(/-/, "/");
        return Date.parse(exp).isNumber();
    },
    isNumber: function () {
        var exp = this.toString();
        if (!exp.length) return true;
        return /^[0-9]+(\.\d+)?$/g.test(exp);
    },
    Format: function () {
        var param = Array.prototype.slice.apply(arguments);
        var format = param.shift();
        return format.replace(/\{(\d+)}/g, function (m, i) {
            return param[i];
        });
    }
});
Object.extend(Number.prototype, {
    isNumber: function () {
        return /^[0-9]+$/g.test(this.toString());
    },
    isDecimal: function () {
        return /^[0-9]+(\.\d+)?$/g.test(this.toString());
    }
});
function fnRequest(strName) {
    var strHref = window.document.location.href;
    var intPos = strHref.indexOf("?");
    var strRight = strHref.substr(intPos + 1);
    var arrTmp = strRight.split("&");
    for (var i = 0; i < arrTmp.length; i++) {
        var arrTemp = arrTmp[i].split("=");
        if (arrTemp[0].toUpperCase() == strName.toUpperCase()) return arrTemp[1];
    }
    return "";
}
function fnGetWindowWidth() {
    var vw = 0;
    var _dEt = document.documentElement;
    var _dBy = document.body;
    if (typeof window.innerWidth == 'number') {
        vw = window.innerWidth;
    }
    else {
        if (_dEt && _dEt.clientWidth) {
            vw = _dEt.clientWidth;
        }
        else {
            if (_dBy && _dBy.clientWidth) vw = _dBy.clientWidth;
        }
    }
    if (!vw || vw < 100) {
        vw = 100;
    }
    return vw;
}
function fnGetWindowHeight() {
    var vh = 0;
    var _dEt = document.documentElement;
    var _dBy = document.body;
    if (typeof window.innerHeight == 'number') {
        vh = window.innerHeight;
    }
    else {
        if (_dEt && _dEt.clientHeight) {
            vh = _dEt.clientHeight;
        }
        else {
            if (_dBy && _dBy.clientHeight) vh = _dBy.clientHeight;
        }
    }
    if (!vh || vh < 100) {
        vh = 100;
    }
    return vh;
};

function ENetwork() { };
ENetwork.GetExecutionID = function () {
    var a = new Date, b = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate(), a.getHours(), a.getMinutes(), a.getSeconds(), a.getMilliseconds());
    b += Math.round(Math.random() * 1000000);
    return b
};
ENetwork.DownloadScript = function (a, b, c) {
    try {
        if (a == null || a == "undefined" || a.length == 0) {
            throw new ENetworkException("ENetwork:DownloadScript", "err_noscripturl", l24ht);
        }
        var elScript = document.createElement("script");
        elScript.type = "text/javascript";
        elScript.language = "javascript";
        elScript.id = typeof (c) == "undefined" ? ENetwork.GetExecutionID() : c;
        elScript.src = a;
        if (document.getElementById(c)) {
            ENetwork.GetAttachTarget().removeChild(document.getElementById(c));
        }
        ENetwork.GetAttachTarget().appendChild(elScript);
        if (navigator.userAgent.indexOf("IE") >= 0) {
            elScript.onreadystatechange = function () {
                if (elScript && ("loaded" == elScript.readyState || "complete" == elScript.readyState)) {
                    elScript.onreadystatechange = null;
                    if (b) {
                        b();
                    }
                }
            }
        }
        else {
            elScript.onload = function () {
                elScript.onload = null;
                if (b) {
                    b();
                }
            }
        }
        return elScript.id;
    }
    catch (e) {
        alert('加载失败！');
    }
};
ENetwork.GetAttachTarget = function () {
    if (document.getElementsByTagName("head")[0] != null) {
        return document.getElementsByTagName("head")[0];
    }
    else {
        throw new ENetworkException("ENetwork:cstr", "err_noheadelement", l611ft);
    }
};
function ENetworkException(b, c, a) {
    this.source = b;
    this.name = c;
    this.message = a;
}
ENetworkException.prototype.Name = this.name;
ENetworkException.prototype.Source = this.source;
ENetworkException.prototype.Message = this.message;

//通用复制方法
function fnCopyToClipboard(txt, msg) {
    if (window.clipboardData) {
        window.clipboardData.clearData();
        window.clipboardData.setData("Text", txt);
    } else if (navigator.userAgent.indexOf("Opera") != -1) {
        window.location = txt;
    } else if (window.netscape) {
        try {
            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
        } catch (e) {
            alert("您的浏览器未开启复制功能！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");
            return false;
        }
        var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
        if (!clip)
            return;
        var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
        if (!trans)
            return;
        trans.addDataFlavor('text/unicode');
        var str = new Object();
        var len = new Object();
        var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
        var copytext = txt;
        str.data = copytext;
        trans.setTransferData("text/unicode", str, copytext.length * 2);
        var clipid = Components.interfaces.nsIClipboard;
        if (!clip)
            return false;
        clip.setData(trans, null, clipid.kGlobalClipboard);
    }
    else {
        alert('您的浏览器不支持拷贝功能。');
        return false;
    }
    if (msg != null && msg != '') {
        alert(msg);
    }
    return true;
}
function setCookie(name, value) {
    var argv = setCookie.arguments;
    var argc = setCookie.arguments.length;
    var expires = (argc > 2) ? argv[2] : null;
    if (expires != null) {
        var LargeExpDate = new Date();
        LargeExpDate.setTime(LargeExpDate.getTime() + (expires * 1000 * 3600 * 24));
    }
    document.cookie = name + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + LargeExpDate.toGMTString()));
}

function getCookie(Name) //cookies读取JS 
{
    var search = Name + "="
    if (document.cookie.length > 0) {
        offset = document.cookie.indexOf(search)
        if (offset != -1) {
            offset += search.length
            end = document.cookie.indexOf(";", offset)
            if (end == -1) end = document.cookie.length
            return unescape(document.cookie.substring(offset, end))
        }
        else return "";
    }
}
function deleteCookie(name) {
    var expdate = new Date();
    expdate.setTime(expdate.getTime() - (86400 * 1000 * 1));
    setCookie(name, "", expdate);
}

function AlaDemo() { };
AlaDemo.$ = function () {
    var objElements = [];
    for (var i = 0; i < arguments.length; i++) {
        var objEle = arguments[i];
        if (typeof arguments[i] == 'string') {
            objEle = document.getElementById(arguments[i]);
        }
        objElements.push(objEle);
    }
    if (arguments.length == 1) {
        return objEle;
    }
    else {
        return objElements;
    }
};
AlaDemo.$Rnd = function (flg) {
    var d, s = '';
    if (!flg) flg = 100000;
    d = new Date();
    s += d.getHours();
    s += d.getMinutes();
    s += d.getSeconds();
    s += d.getMilliseconds();
    return Math.round(Math.random() * flg).toString() + s.toString();
};
AlaDemo.loadScript = function (url, callback) {
    /// <summary>
    /// 加载jacascript
    /// </summary>
    /// <param name="url">js路径</param>
    /// <param name="callback">加载成功回调函数</param>
    var script = document.createElement("script")
    script.type = "text/javascript";
    if (script.readyState) { //IE 
        script.onreadystatechange = function () {
            if (script.readyState == "loaded" || script.readyState == "complete") {
                script.onreadystatechange = null;
                callback();
            }
        };
    } else { //Others 
        script.onload = function () {
            callback();
        };
    }
    script.src = url;
    document.getElementsByTagName("head")[0].appendChild(script);
};
AlaDemo.Ajax = function () {
    function request(url, opt) {
        /// <summary>ajax方法</summary>
        /// <param name="url" type="String" mayBeNull="false">请求的URL（无参数）</param>
        /// <param name="opt" type="Object" mayBeNull="true">配置参数对象{async:false//默认异步,method:''GET''//请求方式,parameters:a=1//url参数,success:function(xhr){}//成功方法,failure:function(xhr){}//失败方法,loading:function(xhr){}//正在请求方法}</param>
        /// <returns type="Object">返回Ajax对象</returns>
        function fn() { }
        var async = opt.async !== false,
        method = opt.method || 'GET',
        parameters = opt.parameters || null,
        success = opt.success || fn,
        failure = opt.failure || fn;
        loading = opt.loading || fn;
        method = method.toUpperCase();
        if (method == 'GET' && parameters) {
            url += (url.indexOf('?') == -1 ? '?' : '&') + parameters;
            data = null;
        }
        var xhr = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject('Microsoft.XMLHTTP');
        xhr.onreadystatechange = function () {
            _onStateChange(xhr, success, failure, loading);
        };
        xhr.open(method, url, async);
        if (method == 'POST') {
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded;');
            //xhr.setRequestHeader('Content-type', 'application/json,text/javascript');
        }
        xhr.send(parameters);
        return xhr;
    }
    function _onStateChange(xhr, success, failure, loading) {
        //debugger;
        if (xhr.readyState == 4) {//处理完毕
            var s = xhr.status;
            if (s >= 200 && s < 300) {
                success(xhr);
            } else if (s == 400) {
                failure(xhr);
            } else if (s == 404) {
                failure(xhr);
            } else if (s = 500) {
                failure(xhr);
            } else {
                failure(xhr);
            }
        } else if (xhr.readyState == 1) {//正在加载
            loading(xhr);
        } else {
            //2、加载完毕 3、正在处理
        }
    }
    return { request: request };
} (); 