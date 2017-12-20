<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title>拨号</title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinecss.jsp"%>
 <link rel="stylesheet" href="${ctx}/resources/themes/hotline/common/css/index.css">
<script type="text/javascript">
	

var agentWindow=top.$("iframe[name=softphoneFrame]")[0].contentWindow;
function btn_setAgentStatus(btn)
{
	var status=$(btn).attr("objid");
	//alert(status);
	var senconds="0";
	var rows=$("#agentStatus tbody tr" );
						   		for(var i=0;i<rows.length;i++)
							   	{
								   	//alert($(rows[i]).find("input[trtype=AgentId]").attr("value"));
								   	if($(rows[i]).find("button[objid="+status+"]").attr("objid")==status)
									{
								   		senconds=$(rows[i]).find("select[name=seconds]").val();
								   		break;
									}
							   	}
	$.post(
			"${ctx}/rest/agent/status/"+status+"/"+senconds,
			null,
			function(data){
					if(data.type=="result"&&data.result=="1")
					{
						
						//alert("外拨成功");
						//uiBase.getParentTabWindow().$("form").submit();
						//uiBase.trigerParentTabFunc('$("form").submit()');
						//uiBase.closeCurrentTab();
					}
					/* else
					{
						//uiBase.processFieldError(data);
					} */
				}
				
		);
}
function btn_AgentDial()
{
	var num=$("#telephoneNum").val();
	agentWindow.aOCX_DialOut(num);
	agentWindow.$("#callNumber").html(num);
	//alert($("#dialog_waibo"));
	//alert(parent.$("#dialog_waibo"));
    //alert(top.$.dialog.list['dialog_waibo']);
    //alert(top.art.dialog({id: 'dialog_waibo'}));
    //top.art.dialog({id: 'dialog_waibo'}).close();
   // top.$(".aui_state_focus").remove();
    agentWindow.art.dialog.list['dialog_waibo'].close();
    //art.dialog.list['dialog_waibo'].close();
	//操作日志
	//addLog("AgentDial");
}
function btn_AgentTransfer()
{
	var num=$("#telephoneNum").val();
	agentWindow.aOCX_OutPhone(num);
	//操作日志
	//addLog("AgentTransfer");
}
</script>
</head>
<body style="overflow:hidden">
<!--外拨   start-->
        <div id="dialer_dialogid" style="display:block;margin-left:10px;margin-right:10px;">
           <form action="" method="post" >
                <ul class="dialer-ul" style="margin-top:10px;">
                    <li class="dialer-show"><input class="dialer-nub" type="text" value="" id="telephoneNum" ><div class="dialer-clear"></div></li>
                    <li>
                        <table class="table dataTable table-fixed-layout" >
                            <tbody>
                                <tr>
                                    <td><div class="phone-num">1</div></td>
                                    <td><div class="phone-num">2</div></td>
                                    <td><div class="phone-num">3</div></td>
                                </tr>
                                <tr>
                                    <td><div class="phone-num">4</div></td>
                                    <td><div class="phone-num">5</div></td>
                                    <td><div class="phone-num">6</div></td>
                                </tr>
                                <tr>
                                    <td><div class="phone-num">7</div></td>
                                    <td><div class="phone-num">8</div></td>
                                    <td><div class="phone-num">9</div></td>
                                </tr>
                                <tr>
                                    <td><div class="phone-num">*</div></td>
                                    <td><div class="phone-num">0</div></td>
                                    <td><div class="phone-num">#</div></td>
                                </tr>
                            </tbody>
                        </table>
                    </li>
                    <li>
                    	<c:if test="${showType == '外拨'}">
                        <button class="btn dialer-btn" type="button" onclick="btn_AgentDial()">拨号</button>
                        </c:if>
                        
                        <c:if test="${showType == '转接'}">
                        <button class="btn dialer-btn" type="button" onclick="btn_AgentTransfer()">转接</button>
                        </c:if>
                    </li>
                </ul>
           </form>
        </div>
        <%@ include file="/WEB-INF/views/hotline/common/hotlinejs.jsp"%>
        <!--外拨     end-->
	<!--  <div>
	外线号码：<input type="text" id="telephoneNum"  value=""> 
							<button onclick="btn_AgentDial()">拨号</button>
							<button onclick="btn_AgentTransfer()">转接</button>
						
	</div> -->  
	<script type="text/javascript">
	//外拨

    $('.dialer-clear').click( function() {
         //2014-08-26 mengfx 点击删除不再清空，而是一位位的删
         //$(".dialer-nub").attr("value","");
         var inputvalue = $(".dialer-nub").val()+$(this).html();
         //$(".dialer-nub").attr("value",inputvalue.substring(0,inputvalue.length-1));
         $(".dialer-nub").val(inputvalue.substring(0,inputvalue.length-1));
    });
    $('.dialer-ul td div').each(function(){
        $(this).click( function() {
        var inputvalue = $(".dialer-nub").val()+$(this).html();
        //$(".dialer-nub").attr('value',inputvalue);
        $(".dialer-nub").val(inputvalue);
    });
    });
	
	</script>   
</body>
</html>