<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title>座席状态</title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinecss.jsp"%>
 <link rel="stylesheet" href="${ctx}/resources/themes/hotline/common/css/index.css">
 <%@ include file="/WEB-INF/views/hotline/common/hotlinejs.jsp"%>
<script type="text/javascript">
	
/*父窗口Window*/
var agentWindow=parent.$("iframe[name=softphoneFrame]")[0].contentWindow;
function btn_setAgentStatus(btn)
{
	var status=$(btn).attr("objid");
	var senconds="0";
	var ope ="";
	var index = $(this).parents("td").prev().attr("id");//.children("select");
	//alert("index is "+index);
	var rows=$("#status_dialogid tbody tr" );
						   		for(var i=0;i<rows.length;i++)
							   	{
								   	//alert($(rows[i]).find("input[trtype=AgentId]").attr("value"));
								   	if($(rows[i]).find("span[objid="+status+"]").attr("objid")==status)
									{
								   		senconds=$(rows[i]).find("select[name=seconds]").val();
								   		ope = $(rows[i]).children().first().text();
								   		//console.log(senconds+","+ope);
								   		break;
									}
							   	}
	//alert(status+":"+senconds);
	var url="${ctx}/softphone/rest/agent/status/set?status="+status+"&seconds="+senconds;
	//alert(url);
	$.get(
			url,
			function(data){
					//alert(data);
					if(data.type=="result"&&data.result=="1")
					{
						//alert(agentWindow.aOCX_setAgentStatus);
						agentWindow.aOCX_setAgentStatus(status,senconds);
						//alert("状态设置成功");
						addLog("AgentStatus");
						//uiBase.getParentTabWindow().$("form").submit();
						//uiBase.trigerParentTabFunc('$("form").submit()');
						//uiBase.closeCurrentTab();
						//agentWindow.agentStatus={};
						var mm = senconds*60*1000;  //毫秒
						
						var beginTime = new Date();
						var lbeginTime = beginTime.getTime();
						var lendTime = beginTime.getTime()+mm;
						//alert(mm+",'"+lendTime);
						//ope = ope;
						//alert(agentWindow._agentStatus.quartzJs);
						var qjs = new  agentWindow._agentStatus.quartzJs({lendTime:lendTime,ope:ope});
						//alert(_qjs.isrunning);
					}
					/* else
					{
						//uiBase.processFieldError(data);
					} */
				}
				
		);
}
</script>
<style type="text/css">
.AgentStatus_1{color:#339100}
.AgentStatus_2{color:#e93935}
.AgentStatus_3{color:#ff6600}
</style>
</head>
<body style="overflow:hidden">
<div id="status_dialogid" style="display: block;">
 <form action="" method="post" >
                <table class="table table-striped dataTable table-fixed-layout border" style="margin-left:10px;margin-right:10px;width:372px;padding-top:10px;">
                    <thead>
                        <tr>
                            <th >状态</th>
                            <th class="col-md-6">持续时间</th>
                            <th >操作</th>
                        </tr>
                    </thead>

                    <tbody>
                        
                     <c:forEach items="${statusList}" var="status" varStatus="index">
						<tr>
							<td id="aa" class="${status.code}">${status.name}</td>
							<td><chr:Dict className="status select" categoryCode="${status.code}" name="seconds"  isAll="false"> </chr:Dict></td>
							<td>
								 <a href="javascript:;">
                                    <i class="fa fa-clipboard blue"></i>
								<span objid="${status.code}" onclick="btn_setAgentStatus(this)" class="black">设置</span>
								</a>
							</td>
						</tr>
					</c:forEach>
						
                        
                        
                    </tbody>
                </table>
            </form>         
   </div>

	 <!--  <table id="agentStatus" class="table table-bordered table-hover">
														<thead>
															<tr>
																<th>状态</th>
																<th>持续时间</th>
																<th>操作</th>
															</tr>
														</thead>
														<tbody>
														<c:forEach items="${statusList}" var="status" varStatus="index">
															<tr>
																
																<td>${status.name}</td>
																<td><chr:Dict className="input-select width_70" categoryCode="${status.code}" name="seconds"  isAll="false"> </chr:Dict></td>
																<td class="col-md-3">
																	<button objid="${status.code}" onclick="btn_setAgentStatus(this)">设置</button>
																	<!--<button objid="${user.agentid}">拦截</button>
																	<button objid="${user.agentid}">强插</button>-->
																<!--  </td>
															</tr>
															</c:forEach>
														</tbody>
                                                     </table>        -->               
</body>
</html>