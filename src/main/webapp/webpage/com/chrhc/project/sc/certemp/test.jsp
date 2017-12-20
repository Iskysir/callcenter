<html xmlns:m="http://schemas.microsoft.com/office/2004/12/omml">
<head>
	<title></title>
	<link href="plug-in/easyui/themes/default/easyui.css" id="easyuiTheme" rel="stylesheet" type="text/css" />
	<link href="plug-in/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
	<link href="plug-in/accordion/css/accordion.css" rel="stylesheet" type="text/css" />
	<link href="plug-in/Validform/css/style.css" rel="stylesheet" type="text/css" />
	<link href="plug-in/Validform/css/tablefrom.css" rel="stylesheet" type="text/css" />
	<style type="text/css">body{font-size:12px;}table{border: 1px solid #000000;padding:0; margin:0 auto;border-collapse: collapse;width:100%;align:right;}td {border: 1px solid #000000;background: #fff;font-size:12px;padding: 3px 3px 3px 8px;color: #000000;word-break: keep-all;}
	</style>
	<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script><script type="text/javascript" src="plug-in/tools/dataformat.js"></script><script type="text/javascript" src="plug-in/easyui/jquery.easyui.min.1.3.2.js"></script><script type="text/javascript" src="plug-in/easyui/locale/zh-cn.js"></script><script type="text/javascript" src="plug-in/tools/syUtil.js"></script><script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script><script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script><script type="text/javascript">$(function(){$("#formobj").Validform({tiptype:4,btnSubmit:"#btn_sub",btnReset:"#btn_reset",ajaxPost:true,usePlugin:{passwordstrength:{minLen:6,maxLen:18,trigger:function(obj,error){if(error){obj.parent().next().find(".Validform_checktip").show();obj.find(".passwordStrength").hide();}else{$(".passwordStrength").show();obj.parent().next().find(".Validform_checktip").hide();}}}},callback:function(data){if(data.success==true){if(!neibuClickFlag){var win = frameElement.api.opener;frameElement.api.close();win.tip(data.msg);win.reloadTable();}else {alert(data.msg)}}else{if(data.responseText==''||data.responseText==undefined)$("#formobj").html(data.msg);else $("#formobj").html(data.responseText); return false;}if(!neibuClickFlag){var win = frameElement.api.opener;win.reloadTable();}}});});</script><script type="text/javascript" src="plug-in/tools/curdtools_zh-cn.js"></script><script type="text/javascript" src="plug-in/tools/easyuiextend.js"></script><script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script><script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script><script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script><script type="text/javascript" src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></script>
</head>
<body link="#0066cc" vlink="purple">
<div>
<p style="text-align: center"><strong><span style="font-size: 28px">婚育证明</span></strong></p>

<p align="left"><span style="font-size: 24px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 我社区居民</span><u style="font-size: 24px">&nbsp;&nbsp;&nbsp;&nbsp;${name!}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u><span style="font-size: 24px">性别</span><u style="font-size: 24px">&nbsp;&nbsp;&nbsp;&nbsp;${sex!}&nbsp;&nbsp;&nbsp;&nbsp;</u><span style="font-size: 24px">，</span><u style="font-size: 24px">&nbsp; ${mBirthdayDate?string(&quot;yyyy&quot;)!} &nbsp;</u><span style="font-size: 24px">年</span><u style="font-size: 24px">&nbsp; &nbsp;${mBirthdayDate?string(&quot;MM&quot;)!} &nbsp;</u><span style="font-size: 24px">月</span><u style="font-size: 24px">&nbsp; &nbsp;${mBirthdayDate?string(&quot;dd&quot;)!} &nbsp;&nbsp;</u><span style="font-size: 24px">日出生，</span><u style="font-size: 24px">&nbsp; &nbsp; &nbsp;${marryday?string(&quot;yyyy&quot;)!} &nbsp; &nbsp;&nbsp;</u><span style="font-size: 24px">年</span><u style="font-size: 24px">&nbsp; &nbsp; ${marryday?string(&quot;MM&quot;)!}&nbsp; &nbsp;</u><span style="font-size: 24px">月</span><u style="font-size: 24px">&nbsp; &nbsp;${birthday?string(&quot;dd&quot;)!} &nbsp; &nbsp;</u><span style="font-size: 24px">日</span><u style="font-size: 24px">&nbsp; &nbsp; &nbsp;${name!} &nbsp; &nbsp;&nbsp;</u><span style="font-size: 24px">与</span><u style="font-size: 24px">&nbsp; &nbsp; ${spouseName!}&nbsp; &nbsp; &nbsp;&nbsp;</u><span style="font-size: 24px">依法登记结婚，双方均系初婚，</span><u style="font-size: 24px">&nbsp; &nbsp; &nbsp;${birthDate?string(&quot;yyyy&quot;)!} &nbsp; &nbsp;&nbsp;</u><span style="font-size: 24px">年</span><u style="font-size: 24px">&nbsp; &nbsp;${birthDate?string(&quot;MM&quot;)!} &nbsp;&nbsp;&nbsp;</u><span style="font-size: 24px">月</span><u style="font-size: 24px">&nbsp; &nbsp;${birthDate?string(&quot;dd&quot;)!} &nbsp; &nbsp;</u><span style="font-size: 24px">日政策内生育一男（女）孩。</span><u style="font-size: 24px">&nbsp; &nbsp; ${childName!}&nbsp; &nbsp; &nbsp;&nbsp;</u><span style="font-size: 24px">系独生子女。</span></p>

<p align="left">&nbsp;</p>

<p align="left" style="margin-left: 120px"><br />
<br />
<span style="font-size: 24px">特此证明</span></p>

<p align="left">&nbsp;</p>

<p align="left">&nbsp;</p>

<p style="text-align: right"><span style="font-size: 24px">经办人： &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 经办人：</span></p>

<p style="text-align: right"><span style="font-size: 24px">乡（镇、街办）计生办（盖章）&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 村（居、单位）</span></p>
</div>

<div align="center" id="sub_tr" style="display: none"><input class="ui_state_highlight" onclick="neibuClick()" type="button" value="提交" /></div>
<script type="text/javascript">$(function(){if(location.href.indexOf("mode=read")!=-1){$('#formobj').find(':input').attr('disabled','disabled');}if(location.href.indexOf("mode=onbutton")!=-1){$("#sub_tr").show();} });var neibuClickFlag = false; function neibuClick() {neibuClickFlag = true;$('#btn_sub').trigger('click');}</script><script type="text/javascript">${js_plug_in?if_exists}</script></body>
</html>
