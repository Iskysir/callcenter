<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title>磋商</title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinecss.jsp"%>
 <link rel="stylesheet" href="${ctx}/resources/themes/hotline/common/css/index.css">
 <script src="${ctx}/resources/general/common/highcharts/highcharts.js" type="text/javascript"></script>
<script type="text/javascript">
	
/*父窗口Window*/
var agentWindow=parent.$("iframe[name=softphoneFrame]")[0].contentWindow;

/*磋商*/
function btn_CmdConsultToAgent(btn)
{
	var agentId=$(btn).attr("objid");
	agentWindow.CmdConsultToAgent(agentId,"","");
	//操作日志
	addLog("ConsultToAgent");
}

/*外线磋商*/
function btn_CmdConsultToOutLine()
{
	var number=$("#telephoneNum").val();
	agentWindow.aOCX_CmdConsultToOutLine(number);
	//操作日志
	addLog("ConsultToOutLine: "+number);
}

/*内部转接*/
function btn_CmdTransferToAgent(btn)
{
	var agentId=$(btn).attr("objid");
	agentWindow.CmdConsultTransfer(agentId,"","");
	//操作日志
	addLog("inner transfer");
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
	alert(btn.innerHTML);
	var $btn=$(btn);
	if($btn.html()=="监听")
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
	}
	  
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
        <div id="consultations_dialogid" style="padding-left:10px;padding-right:10px;">
           <form action="" method="post" >
           		<div class="row m-t-10">
					<div class="col-md-4">
						<label for="exampleInputInfoB">技能组：</label>
						<chr:Dict categoryCode="AgentSkill" name="search_skill" id="search_skill" value="${searchParams.skill}" className="form-control-plus search-select" isAll="true"> </chr:Dict>
					</div>
					<div class="col-md-8">
                        <chr:perm code="contact_channel_query"><button type="submit" class="btn btn-primary" style="margin-left:75px;">查询</button></chr:perm>
                    </div>
				</div>
                <table id="agentList" class="table table-striped dataTable table-fixed-layout border">
                    <thead>
                        <tr class="">
                            <th width="20%">角色</th>
                            <th >技能组</th>
                            <th >座席员</th>
                            <th >话机</th>
                            <th >状态</th>
                        </tr>
                    </thead>

                    <tbody>
                    
                    	<c:forEach items="${agentList}" var="user" varStatus="status">
							<tr>
								<td>${user.role}
									<input type="hidden" trtype="AgentId" value="${user.agentid}">
								</td>
								<td>${user.skill}</td>
								<td>${user.displayName}</td>
								<td>${user.agentPhone}</td>
								<td><span class="AgentStatus_${user.workmode} status">${user.workmode}</span></td>
							</tr>
						</c:forEach>
                    
                   
                    </tbody>
                </table>
            </form>
            <div id="agentStatusColumn" class="analysisPart" style="padding-top:0;">
				<div class="supplyTable clearf ofh">
	            <div id="statusColumn" class="energyShow" style="border-top:0;">
	            	<div class="hide pie">饼图</div>
	            	<div class="chart fl" id="statusColumn_column" style="width:350px; height:300px;"></div>
	                <div class="dataShow" id="dataShow_statusColumn" style="width:500px;">
	                	<h2 class="bs" id="dataShow_statusColumn_bs">${user.displayName}工时分配</h2>
	                    <div class="datas" style="height:288px;overflow-y:auto;">
	                    	<table id="dataShow_workcountPie_table">
	                    		<thead>
	                    			<tr>
	                    				<th>
	                    					座席员
	                    				</th>
	                    				<th>
	                    					状态
	                    				</th>
	                    			</tr>
	                    		</thead>
	                    		<tbody>
	                    			<c:forEach  var="agent" items="${agentList}" >
										<tr>
											<td>
												${agent.id}
											</td>
											<td>
												<span class="AgentStatus_${agent.workmode} status">${agent.workmode}</span>
											</td>
										</tr>
									</c:forEach>
	                    		</tbody>
	                    	</table>
	                    </div>
	                </div>
	            </div> 
	    		</div>
			</div>  
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
					
									
				
			};

			$(function () {
		        $('#statusColumn_column').highcharts({
		            chart: {
		                type: 'column'
		            },
		            title: {
		                text: ' '
		            },
		            subtitle: {
		                text: ' '
		            },
		            xAxis: {
		                categories: ['空闲','通话','话后处理','忙碌','振铃']
		            },exporting:{ 
		            	enabled:false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示 
		            },
		            yAxis: {
		                min: 0,
		                title: {
		                    text: '人数(人)'
		                },
		                labels: {
		                    formatter: function() {
		                        return Highcharts.numberFormat(this.value, 0);
		                    }
		                }
		            },
		            tooltip: {
		                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		                    '<td style="padding:0"><b>{point.y} 人</b></td></tr>',
		                footerFormat: '</table>',
		                shared: true,
		                useHTML: true
		            },
		            plotOptions: {
		                column: {
		                    pointPadding: 0.2,
		                    borderWidth: 0
		                }
		            },
		            series: [{
		                name: '座席员监控',
		                data: [
		                	<#list agentList as agent>
							<#if '${agent.agentcount!}' == "">
							0,
							<#else>
			        	    ${agent.agentcount},
			        	    </#if>
			        	    </#list>
		                ]
	                }]
		        });
		    });


			//$.post(url,{pver:verid},function (response) {downList = response.downList;installList = response.installList;timList = response.timList;
			//设置Y轴坐标值
			//verChart.series[0].setData(downList);verChart.series[1].setData(installList);
			//设置X轴坐标值
			//verChart.xAxis[0].setCategories(timList);}, "json" );
			
			setInterval("checkStatus()",1200);
       </script>                        
</body>
</html>