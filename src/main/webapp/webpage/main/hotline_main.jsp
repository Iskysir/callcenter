<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="en" class="ie8 no-js">
<head>
    <meta charset="utf-8" />
    <title>热线服务平台</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="renderer" content="ie-stand">
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />

    <link rel="stylesheet" type="text/css" href="plug-in/scmedia/jquery.easyui/themes/default/easyui.css">
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body id="cc" class="easyui-layout">

<div data-options="region:'north',border:true,split:true,title:''" style="height:71px;padding:2px;overflow-y: hidden">
    <div id="softphone" style="width:99.8%;height:60px;"><iframe style="width:100%;height:60px;" scrolling="no" name="softphoneFrame"  src="webpage/main/softphone_fs.jsp"></iframe></div>
    <div id="clientInfo" style="height:169px;width:99.8%;">
        <div style="width:49.8%;margin-top:2px;float: left;height:50px;">
            <table id='clientTable' class="easyui-datagrid" title="客户信息" style="height:170px;" data-options="onLoadSuccess:function(data){}" singleSelect="true"
                   rownumbers="true" toolbar="#clientQuery" loadMsg="正在查询..." fitColumns="true"  url="chrhcAutoListController.do?datagrid&configId=hl_customer&field=id,create_name,create_by,update_name,update_by,version_num,sys_org_code,delflag,create_date,update_date,cm_name,cm_sex,cm_type,cm_code,cm_tel,cm_sq,cm_adress,&dataRule=null&biztype=&page=1&rows=1&sort=create_date&order=desc">
                <thead>
                <tr>
                    <th field="id" Width="80" hidden="true">id</th>
                    <th field="cm_code" Width="80">编号</th>
                    <th field="cm_name" Width="80">姓名</th>
                    <th field="cm_sex" Width="80">性别</th>
                    <th field="cm_type" width="100">类型</th>
                    <th field="cm_tel" width="80">电话</th>
                    <th field="cm_sq" width="80">市区</th>
                    <th field="create_date" width="80">创建时间</th>
                </tr>
                </thead>
                <!-- <tbody style="">
                     <tr>
                         <td>张三</td>
                         <td>易通支付</td>
                         <td>13888888888</td>
                         <td>2016-5-22 23:22</td>
                         <td>2016-6-7 23:22</td>
                     </tr>
                     <tr>
                         <td>李四</td>
                         <td>一卡通</td>
                         <td>13666666666</td>
                         <td>2016-5-22 23:22</td>
                         <td>2016-6-7 23:22</td>
                     </tr>
                     <tr>
                         <td>王五</td>
                         <td>易通支付</td>
                         <td>13888888888</td>
                         <td>2016-5-22 23:22</td>
                         <td>2016-6-7 23:22</td>
                     </tr>
                     <tr>
                         <td>张三</td>
                         <td>易通支付</td>
                         <td>13888888888</td>
                         <td>2016-5-22 23:22</td>
                         <td>2016-6-7 23:22</td>
                     </tr>
                 </tbody>-->
            </table>

            <div id="clientQuery" style="padding:5px">
                <span>客户名称:</span><input type="text" id="cm_name" name="cm_name" value="" size=10 />
                <span>电话:</span><input type="text" id="cm_tel" name="cm_tel" value="" size=12 />
                <a href="javascript:searchMainClient()"  class="easyui-linkbutton" >查询</a>
                <a href="javascript:addClient()"  class="easyui-linkbutton">新增</a>
                <div>
                </div>
            </div>
        </div>
        <div style="width:50%;margin-top:2px;margin-left:2px;float: left">
            <table id='histTable' class="easyui-datagrid" title="历史来电" style="height:170px;"
                   rownumbers="true" toolbar="#histQuery" loadMsg="正在查询..." fitColumns="true" singleSelect="true"
                   url="chrhcAutoListController.do?datagrid&configId=hl_history_calls&field=id,create_name,create_by,create_date,update_name,update_by,update_date,version_num,sys_org_code,delflag,customer_code,customer_name,customer_tel,call_date,zxh,zx_name,customer_id,hl_company,orderrecordid,&dataRule=null&biztype=&page=1&rows=5&sort=create_date&order=desc">
                <thead>
                <tr>
                    <th field="id" Width="80" hidden="true">id</th>
                    <th field="customer_code" Width="80">编号</th>
                    <th field="customer_name" Width="40">姓名</th>
                    <th field="customer_tel" Width="80">电话</th>
                    <th field="create_date" Width="80">来电时间</th>
                    <th field="zxh" Width="40">座席工号</th>
                    <th field="zx_name" Width="40">座席姓名</th>
                    <th field="hl_company" Width="40">所属公司</th>
                    <th field="orderrecordid" Width="80" data-options="formatter:histCallBiz">业务编号</th>

                </tr>
                </thead>

            </table>

            <div id="histQuery" style="padding:5px">
                <span>编号:</span><input type="text" id="customer_code" name="customer_code" value="" size=10 />
                <span>姓名:</span><input type="text" id="customer_name" name="customer_name" value="" size=10 />
                <span>电话:</span><input type="text" id="customer_tel" name="customer_tel" value="" size=10 />
                <span>座席工号:</span><input type="text" id="zxh" name="zxh" value="" size=10 />
                <span>座席姓名:</span><input type="text" id="zx_name" name="zx_name" value="" size=10 />
                <a href="javascript:searchHisCalls()" class="easyui-linkbutton">查询</a>
            </div>
        </div>
    </div>

    <div style="text-align:right;margin-right:10px;position:absolute;right:35px;top:15px;height:32px;width:150px;color:white;font-size: 14px">
        <span id="timetable" style="color: white"></span>
        <span class="username" style="">${currentOrgName}</span><br/>
        <span class="username" style="">${realName}</span>
    </div>


    <span onclick="javascript:$.dialog.confirm('你确定要退出系统吗？', function(){location.href='loginController.do?logout';}, function(){});" style="position:absolute;right:7px;top:18px;height:32px;width:32px;background-image: url(plug-in/hotline/img/exit.png)"></span>
</div>
</div>
</div>
<div region="west" split="true" href="loginController.do?leftnew" title="<span style:height:30px;font:13px;>导航菜单</span>" style="width: 200px; padding: 1px;"></div>

</div>
<div data-options="region:'south',border:true,collapsed:true,title:'队列信息',tools:[{iconCls:'icon-reload',handler:function(){$('#queueTable').datagrid('reload');}}]" style="height:0px;">
    <table id='queueTable' class="easyui-datagrid" title="" style="height:0px;"
           rownumbers="true"  loadMsg="正在查询..." fitColumns="true" singleSelect="true"
           url="">
        <thead>
        <tr>
            <th field="id" Width="80" hidden="true">id</th>
            <th field="queue_enter_time" Width="80">来电时间</th>
            <th field="callerid" Width="40">主叫号码</th>
            <th field="statusname" Width="80">状态</th>
            <th field="queuename" Width="80">队列</th>
            <th field="serving_agent" Width="40">座席工号</th>
        </tr>
        </thead>
    </table>
</div>
<div id="mainPanle" region="center" style="overflow: hidden;">
    <div id="maintabs" class="easyui-tabs" fit="true" border="false">
    </div>
</div>


<script src="plug-in/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script src="plug-in/scmedia/jquery.easyui/jquery.easyui.min.js"></script>
<script src="plug-in/scmedia/login_menu.js" type="text/javascript"></script>
<t:base type="tools"></t:base>



<!-- END PAGE LEVEL SCRIPTS -->

<script type="text/javascript">
    var history_call_id,business_id,business_code,yw_code,calledNum;
    function histCallBiz(value,row,index)
    {
        var res="";
        //alert(value);
        if(value&&value!="")
        {
            $.ajax({
                async:false,
                type: "POST",
                url: "${ctx}/softphone/queryCallBiz.do",
                data:{callId:row.id},
                success: function(data){
                    var result=eval("("+data+")");
                    if(result.obj)
                    {
                        // alert(result.obj);
                        $.each(result.obj, function(i,one){
                            res=res+"<a href=\"javascript:showYwById('"+one.BUSINESS_ID+"','"+one.BUSINESS_CODE+"','"+one.CONTENT+"')\">"+one.YW_CODE+"</a> &nbsp;";
                        });
                        // alert(res);
                    }
                }
            });
            //$.post("${ctx}/softphone/queryCallBiz.do",{callId:value},function(data){});
        }

        return res;
    }
    function showYwById(ywId,bizCode,content)
    {
        var url="${ctx}/chrhcFormBuildController.do?ftlForm&tableName="+bizCode+"&mode=read&bizType=&id="+ywId;
        addTab(content,url,"yw");
    }
    function window_onload()
    {

    }
    var clientInfoShowed=false;
    function clientShow()
    {
        $("#cc").layout("panel", "north").panel("resize",{height:240});
        $("#cc").layout("resize");
        clientInfoShowed=true;
    }
    function clientHideOrShow()
    {
        if(clientInfoShowed==true)
        {
            $("#cc").layout("panel", "north").panel("resize",{height:71});
            $("#cc").layout("resize");
            clientInfoShowed=false;
        }
        else
        {
            $("#cc").layout("panel", "north").panel("resize",{height:240});
            $("#cc").layout("resize");
            clientInfoShowed=true;
        }
    }
    function adjust(obj){
        var ha;
        if($.browser.msie) { ha = 95;} //IE
        else if($.browser.safari){ ha = 95;} //谷歌
        else if($.browser.mozilla) { ha = 95;} //火狐
        else if($.browser.opera) { ha = 95;}


        var height = $(window).height(); //浏览器当前窗口文档的高度
        /* $(".page-content").height(height-ha);
         $(".collapse_div").height(height-ha-60);
         $(".sidebar-menu_1").height(height-ha-60);

         $(".easyui-tabs").height(height-ha);*/
    }
    window.onload=function(){
        window.onresize = adjust;
        adjust();

    }
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
                content:'<img class="js_loading" src="plug-in/chrhc/images/logo_load.gif"/><span style="white-space:nowrap; margin-left:25px;"> 数据加载中，请稍后</span>'
            });
            $(".js_loading").parent("div.aui_content").css("cssText","padding:15px 38px!important;border:5px solid #75b2d7").parent(".aui_main").css("cssText","padding-top:0px!important; border:1px solid #187ebc;");
            $(".aui_inner").css("cssText","border:none!important;");
        }
    }
    function closeLoadingDialog()
    {
        dialog_Loading.hide();
    }
    jQuery(document).ready(function() {
        // $('.layout-expand-south').panel({title: "新title"});
        initFirstMenu();
    });
</script>
<!-- END JAVASCRIPTS -->
<script type="text/javascript">

    lhgDialog =$.dialog;
    dialogStr = "functionList,typeGridTree,dbSourceList,iconList,mutiLangList,territoryList,categoryList,scCerTemplateList,cgformButtonList,typeValueList,processnodeList,busbaseList,myTaskList,processproList,listenerList,cgreportConfigHeadList,operationList,roleUserList,scCerTemplateList,scCerTemplateList,departUserList";
    serverFullPath="${webRoot}";

    // sclient 客户端 文件下载
    function downloadSclient()
    {
        //alert(1);
        window.top.art.dialog({
            title:"客户端控件下载",
            content:'请点击【下载】按钮安装客户端控件，安装完毕后重启IE浏览器、拔插设备后再登录。',
            ok:function(){
                //$.download(serverFullPath+"/sclient/download.do", "","post");
                window.open(serverFullPath+"/sclient/download.do");
            },
            okVal:"下载",
            cancel:function(){

            },
            cancelVal:"返回",
            id: "dialog_sclientdownload",
            lock:false

        });
    }



    var userAgent = navigator.userAgent.toLowerCase();
    // Figure out what browser is being used
    jQuery.browser = {
        version: (userAgent.match( /.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/ ) || [])[1],
        safari: /webkit/.test( userAgent ),
        opera: /opera/.test( userAgent ),
        msie: /msie/.test( userAgent ) && !/opera/.test( userAgent ),
        mozilla: /mozilla/.test( userAgent ) && !/(compatible|webkit)/.test( userAgent )
    };
</script>
<script>
    function adjust(obj){
        var ha=0;
        if($.browser.msie) { ha = 95;} //IE
        else if($.browser.safari){ ha = 95;} //谷歌
        else if($.browser.mozilla) { ha = 95;} //火狐
        else if($.browser.opera) { ha = 95;}
        else { alert("i don't konw!"); }

        var height = $(window).height(); //浏览器当前窗口文档的高度
        /*  $(".page-content").height(height-ha);
         $(".collapse_div").height(height-ha-60);
         $(".sidebar-menu_1").height(height-ha-60);
         //加滚动条，设置宽度
         $(".sidebar-menu_1").width("226px");
         $(".easyui-tabs").height(height-ha);


         $(".panel .panel-body div iframe").height(height-160);
         $(".tabs-panels").height(height-141);
         $(".panel-body").height(height-141);*/
    }
    window.onload=function(){
        window.onresize = adjust;
        adjust();
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
    function searchMainClient()
    {
        //alert($("#cm_tel").val());
        $('#clientTable').datagrid('load', {
            cm_name: ""+$("#cm_name").val()+"",
            cm_tel:""+$("#cm_tel").val()+"",
        });

    }
    function searchClient(phoneNum)
    {
        $("#cm_tel").val(phoneNum);
        searchMainClient();
    }
    function searchHisCalls()
    {
        $('#histTable').datagrid('load', {
            customer_code: ""+$("#customer_code").val()+"",
            customer_name:""+$("#customer_name").val()+"",
            customer_tel:""+$("#customer_tel").val()+"",
            zxh:""+$("#zxh").val()+"",
            zx_name:""+$("#zx_name").val()+"",
        });
    }
    function searchHisCallsByNum(phoneNum)
    {
        $("#customer_tel").val(phoneNum);
        searchHisCalls();
    }


    //query hl_history_call
    function queryHSCall(phoneNum)
    {
        var url="";

    }

    function addClient()
    {
        var agentWindow=top.$("iframe[name=softphoneFrame]")[0].contentWindow;
        //alert(agentWindow);
        var cm_tel=agentWindow.$("#callNumber").html();
        //alert(cm_tel);
        var url="chrhcFormBuildController.do?ftlForm&tableName=hl_customer&bizType=&dataRule=null&cm_tel="+cm_tel;
        addTab("客户信息",url,"clientInfoFromMain");
    }
    function addTabtx(obj){
        var linkCfg=$(obj);
        addOneTab("通知阅读",linkCfg.attr("url"));
    };
    $(function($){
        var user = getUserInofo();
        var touserId = user.username;
        var ioUrl ="hlVisitItemController.do?getCountIsread";
        $.ajax({
            type: "POST",
            url: ioUrl,
            dataType:"json",
            async:false,
            success: function(data){
                debugger;
                if(data.obj != 0){
                    $.dialog({
                        id: 'msg',
                        title: "消息提醒",
                        content: "您有<a url='chrhcAutoListController.do?list&id=io_notice&isread=true' onclick='addTabtx(this)' href='javascript:return false;' iframe=true style='color: red;cursor:pointer'>【"+data.obj+"】</a>条通知公告未读请查阅",
                        width: 250,
                        height: 100,
                        left: '100%',
                        top: '100%',
                        fixed: true,
                        drag: false,
                        resize: false
                    });
                }}
        });
        function getQueueAgentStatus()
        {
            $.post("${ctx}/softphone/queueStatus.do?check",null,function(data) {
                //var resultJson=eval('('+data+')');
                //var resultJson=$.parseJSON(data);
                //alert(resultJson.waitNum);
                var resultStr=eval("("+data+")");
                var htmlStr="<span style='margin-right: 50px;float:right;'>"+resultStr+"</span>";
                $(".layout-panel-south .panel-header .panel-title").html(htmlStr);
                $(".layout-expand-south .panel-header .panel-title").html(htmlStr);
            });

        }
        //getQueueAgentStatus();
        setInterval(getQueueAgentStatus,2000);
    });
</script>

<script type="text/javascript">
    var myVar = setInterval(function(){ myTimer() }, 1000);
    var jsVar=  <%=java.util.Calendar.getInstance().getTimeInMillis()%>;
    var timeZoneOffset=<%=java.util.TimeZone.getDefault().getOffset(System.currentTimeMillis())%>;

    jsVar=jsVar+timeZoneOffset;
    function myTimer() {
        jsVar=jsVar+1000;
        var d = new Date(jsVar);
        var day= d.getFullYear()+"-"+ d.getUTCMonth()+"-"+ d.getUTCDay()+" "+ d.getUTCMonth()+":"+ d.getUTCMinutes()+":"+ d.getUTCSeconds();
        var t=d.toUTCString();
        document.getElementById("timelable").innerHTML = day;
    }

</script>

<script type="text/javascript" src="plug-in/artDiglog/jquery.artDialog.js?skin=default"></script>

<script type="text/javascript" src="${ctx}/resources/themes/hotline/common/js/iframeTool.js"></script>

<script src="plug-in/scmedia/login_menu.js" type="text/javascript"></script>

<t:base type="tools"></t:base>
<link rel="stylesheet" href="plug-in/easyui/themes/icon.css" type="text/css"/>
</body>
<!-- END BODY -->
</html>