<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title>磋商</title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinecss.jsp"%>
 <%-- <link rel="stylesheet" href="${ctx}/resources/themes/hotline/common/css/index.css"> --%>
<script type="text/javascript">
	
/*父窗口Window*/
var agentWindow=parent.$("iframe[name=softphoneFrame]")[0].contentWindow;
var agentcurBizId,agentcurBizNo;
/*磋商*/
function btn_CmdConsultToAgent(btn)
{
	var agentId=$(btn).attr("objid");
	var agentUsername=$(btn).attr("agentUsername");
	var agentDn=$(btn).attr("agentDn");
	var agentcurBizId=agentWindow.curBizId;
	var agentcurBizNo=agentWindow.curBizNo;
	var ani=agentWindow.phoneNumber;
	//alert("agentId："+agentId+",agentUsername："+agentUsername+"，agentDn："+agentDn);
	//var agentId=$("input[type='radio']:checked").val();
	 //alert(agentId);
	agentWindow.consult(agentId);
	$("#ConsultbtnSpan").html("取消");
	//操作日志
	//addLog("ConsultToAgent");
	$.dialog.list['dialog_agentList'].close();
	//top.$(".aui_state_focus").remove();
	agentWindow.art.dialog.list['dialog_agentList'].close();
}

/*外线磋商*/
function btn_CmdConsultToOutLine()
{
	
	var number=$("#telephoneNum").val();
	
	var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;   
    var tel = /^\d{3,4}-?\d{7,9}$/;   
   	var result =   (tel.test(number) || mobile.test(number));  
	
	
	if(!result){
		
		$("#errorhtml").html("请输入正确的电话或手机号码");
		return false;
	}
	
	agentWindow.aOCX_CmdConsultToOutLine(number);
	//操作日志
	addLog("ConsultToOutLine: "+number);
	//art.dialog.list['dialog_agentList'].close();
	//top.$(".aui_state_focus").remove();
	agentWindow.art.dialog.list['dialog_agentList'].close();
}

/*内部转接*/
function btn_CmdTransferToAgent(btn)
{
	var agentId=$(btn).attr("objid");
	/*var agentUsername=$(btn).attr("agentUsername");
	var agentDn=$(btn).attr("agentDn");
	var agentcurBizId=agentWindow.curBizId;
	var agentcurBizNo=agentWindow.curBizNo;
	var ani=agentWindow.phoneNumber;*/
	agentWindow.blindTransfer(agentId);
	agentWindow.art.dialog.list['dialog_agentList'].close();
	//操作日志
	//addLog("ConsultToAgent");
	//$.dialog.list['dialog_agentList'].close();
	//top.$(".aui_state_focus").remove();
}

/*设置所有按钮状态 true可用 false不可用，excludeName 除此外都进行设置*/
function setAllbtnDisable(exclude,disValue)
{
	
	$.each($("button"), function(i,item){
		if($(item).html()!=exclude)
		{
			$(item).attr("disabled",disValue);
		}
	});
}
function btn_CmdListenToAgent(btn)
{
	//alert(btn.innerHTML);
	var $btn=$(btn);
	var agentId=$btn.attr("objid");
	//alert(agentId);
	agentWindow.eavesdrop(agentId);
	//alert(top.art.dialog.list[0]);
	//debugger;
	agentWindow.art.dialog.list['dialog_agentList'].close();
	//top.$(".aui_state_focus").remove();
	/* if($btn.html()=="监听")
	{
		var agentId=$btn.attr("objid");
		$btn.html("停止监听");
		agentWindow.CmdListenToAgent(agentId,"","");
		setAllbtnDisable("停止监听",true);
		//操作日志
		addLog("ListenToAgent");
	}
	else if ($btn.html()=="停止监听") 
	{
		agentWindow.CmdListenStop();
		$btn.html("监听");
		setAllbtnDisable("停止监听",false);
		//操作日志
		addLog("ListenStop");
	}
	else
	{
		agentWindow.CmdListenStop();
		setAllbtnDisable("停止监听",false);
		//操作日志
		addLog("ListenStop");
	} */
	  
}

function btn_CmdIntrudeToAgent(btn)
{
	var $btn=$(btn);
	var agentId=$btn.attr("objid");
	//alert(agentId);
	agentWindow.bargein(agentId);
	agentWindow.art.dialog.list['dialog_agentList'].close();
	//top.art.dialog.list['dialog_agentList'].close();
	//top.$(".aui_state_focus").remove();
}
function btn_Conference(btn)
{
	var $btn=$(btn);
	var agentId=$btn.attr("objid");
	//alert(agentId);
	agentWindow.threeway(agentId);
	agentWindow.art.dialog.list['dialog_agentList'].close();
}
</script>

<style type="text/css">
.AgentStatus_1{color:#339100}
.AgentStatus_4{color:#e93935}
.AgentStatus_2{color:#e93935}
.AgentStatus_5{color:#e93935}
.AgentStatus_3{color:#ff6600}
</style>

</head>
<body>
	 <c:if test="${showType == '磋商'}">
	<!--<div style="padding-left:10px;padding-right:10px;margin:10px;">
	外线号码：	<input type="text" id="telephoneNum"  value="">
			<button onclick="btn_CmdConsultToOutLine()" class="btn btn-primary">外线磋商</button>
			<span id="errorhtml" style="color:red;"></span>
	</div>-->
	</c:if>
	<!--磋商   start-->
        <div id="consultations_dialogid" style="padding-left:10px;padding-right:10px;">
           <form action="" method="post" >
                <table id="agentList" class="table table-bordered table-hover table-fixed-layout">
                    <thead>
                        <tr class="">
							<!-- <th width="20%">角色</th>-->
                            <th >技能组</th>
                            <th >座席员</th>
                            <th >话机</th>
                            <th >状态</th>
                            <th class="col-md-3">操作</th>
                        </tr>
                    </thead>

                    <tbody>
                    
                    	<c:forEach items="${agentList}" var="user" varStatus="status">

                    	<c:if test="${((showType == '磋商'||showType == '三方'||showType == '转接') && (user.agent_status==0||user.agent_status==8)||(showType == '监听'||showType == '强插')&&user.agent_status==2)&&user.agentId!=fn:toUpperCase(agentMap.AGENTID)}">
							<tr>
								
								<!--<td>${user.role}</td>-->
								<td><input type="hidden" trtype="AgentId" value="${user.agentid}">${user.skill}</td>
								<td>${user.displayName}</td>
								<td>${user.agentPhone}</td>
								<td><span class="AgentStatus_${user.userStatus} status">${user.userStatus}</span></td>
								<td>
									<c:if test="${showType == '磋商'}">
									
										<a href="javascript:;" onclick="btn_CmdConsultToAgent(this)"   objid="${user.agentid}" agentUsername="${user.userName}" agentDn="${user.agentPhone}">
	                                    <i class="fa fa-comments-o blue"></i>
	                                    <span class="black">磋商</span>
	                               		</a>
	                               		
	                               		
                               		</c:if>
                               		<c:if test="${showType == '转接'}">
                               		<a href="javascript:;" onclick="btn_CmdTransferToAgent(this)" objid="${user.agentid}">
                               		<i class="fa fa-comments-o blue"></i>
                                    <span class="black">转接</span>
                               		</a>
                               		</c:if>
                               		<c:if test="${showType == '监听'}">
                               		<a href="javascript:;" onclick="btn_CmdListenToAgent(this)" objid="${user.agentid}">
                               		<i class="fa fa-comments-o blue"></i>
                                    <span class="black">监听</span>
                               		</a>
                               		</c:if>
                               		
                               		<c:if test="${showType == '强插'}">
                               		<a href="javascript:;" onclick="btn_CmdIntrudeToAgent(this)" objid="${user.agentid}">
                               		<i class="fa fa-comments-o blue"></i>
                                    <span class="black">强插</span>
                               		</a>
                               		</c:if>

									<c:if test="${showType == '三方'}">
										<a href="javascript:;" onclick="btn_Conference(this)" objid="${user.agentid}">
											<i class="fa fa-comments-o blue"></i>
											<span class="black">三方</span>
										</a>
									</c:if>
                               		

								</td>
							</tr>
							</c:if>
						</c:forEach>

                    </tbody>
                </table>
            </form>         
        </div>

     <%@ include file="/WEB-INF/views/hotline/common/hotlinejs.jsp"%>
       <script type="text/javascript">
			function checkStatus()
			{
				var url="${ctx}/softphone/rest/agent/status?_x="+Math.random();
				//alert($.getJSON);
				$.getJSON( url, function( data ) {
						$.each(data, function(i,item){
					   		//alert(item.agentid);
					   		//alert($("#agentList tbody tr" ).html());
					   		var rows=$("#agentList tbody tr" );
						   		for(var i=0;i<rows.length;i++)
							   	{
								   	//alert($(rows[i]).find("input[trtype=AgentId]").attr("value"));
								   	if($(rows[i]).find("input[trtype=AgentId]").attr("value")==item.agentid)
									{
								   		//alert($(rows[i]).find(".status"));
								   		var statusField=$(rows[i]).find(".status");
								   		statusField.attr("class","AgentStatus_"+item.workmode+" status");
								   		if(item.workmode=="1")
								   		{
								   			
								   			statusField.html("空闲");
								   			
								   		}
								   		if(item.workmode=="2")
								   		{
								   			statusField.html("通话");
								   		}
								   		if(item.workmode=="3")
								   		{
								   			statusField.html("话后处理");
								   		}
								   		if(item.workmode=="4")
								   		{
								   			//$(rows[i]).find(".status").html("忙碌");
								   			statusField.html("忙碌");
								   		}
								   		if(item.workmode=="5")
								   		{
								   			//$(rows[i]).find(".status").html("振铃");
								   			statusField.html("振铃");
								   		}
								   		
									}
							   	}
							
					  });
						//alert(data);
					  });
					
									
				
			}
			setInterval("checkStatus()",1200);
       </script>                        
</body>
</html>