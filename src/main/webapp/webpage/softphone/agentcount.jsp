<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title>磋商</title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinecss.jsp"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejs.jsp"%>
 <link rel="stylesheet" href="${ctx}/resources/themes/hotline/common/css/index.css">
<script type="text/javascript">
	
/*父窗口Window*/
var agentWindow=parent.$("iframe[name=softphoneFrame]")[0].contentWindow;


</script>

<style type="text/css">
.AgentStatus_1{color:#339100}
.AgentStatus_4{color:#e93935}
.AgentStatus_2{color:#e93935}
.AgentStatus_5{color:#e93935}
.AgentStatus_3{color:#ff6600}
.dataShow{width:400px;height:339px;position:absolute;right:125px;top:55px;padding-right:12px;}
.datas{height:288px;border:none;}
.bs {
    color: #636363;
    height: 40px;
    line-height: 40px;
}
</style>

</head>
<body style="height: auto;width: 95%">
        <div id="consultations_dialogid" style="padding-left:10px;padding-right:10px;">
           <form action="" method="post" >
           <div class="row m-t-10">
           <div class="col-md-12">
           	<div class="col-md-6">
           		<div class="hide pie">饼图</div>
           		<div id="agentColumn" style="width:90%; height: 600px; margin-right:10%;"></div>
           	</div>
           	<div class="col-md-6">
           		<div class="dataShow" id="dataShow" style="width:90%;height:600px; margin-left:10%;">
           			<h2 class="bs" id="dataShow_bs"></h2>
                    <div class="datas" style="width:100%;height:540px;overflow-y:auto;">
                    	<table id="modeList" class="table table-striped dataTable table-fixed-layout border">
		                    <thead>
		                        <tr class="">
		                        	<th>座席员</th>
		                        	<th>内线号码</th>
		                        	<th>状态</th>
		                        	<!--<th>持续时间(s)</th>
		                            <th width="120px">技能组</th>
		                            <th>IP地址</th>-->
		                        </tr>
		                    </thead>
		                    <tbody>
		                    	<c:forEach items="${statuslist}" var="user" varStatus="status">
									<tr onclick="showAgentStatusSum('${user.bind_exten}')">
										<td><a href="JavaScript:void(0)" onclick="showAgentStatusSum('${user.bind_exten}')">${user.displayName}</a></td>
										<td>${user.bind_exten}</td>
										<td>${user.status_name}</td>
									</tr>
								</c:forEach>
		                    </tbody>
		                </table>
                    </div>
                </div>
               </div>
               </div>
              </div>
            </form>         
        </div>
     
<script type="text/javascript">
	$(function(){
		//alert('(${statusCountlist})');
		var arr=eval('(${statusCountlist})');
		//alert(arr);
		/*var arr = new Array();
		var result = ${agentList};
		$("#dataShow").show();
		$("#dataShow_bs").text("座席状态统计");
		for(var i = 0; i < result.length; i++) {
			arr[i] = new Array();
			arr[i][0] = result[i].modename;
			arr[i][1] = result[i].agentcount;
			arr[i][2] = result[i].usercount;
		}*/
		
		$('#agentColumn').highcharts({ 
			chart: { plotBackgroundColor: null, plotBorderWidth: null, plotShadow: false }, 
			title: { text: ('座席状态占比图') }, 
			tooltip: { pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>' }, 
			exporting:{ 
	           	enabled:false
	           },
			legend: {
	              layout: 'vertical',
	            align: 'center',
	            verticalAlign: 'bottom',
	            itemMarginTop: 5,
	            itemMarginBottom: 5,
	           },
			plotOptions: { 
				pie: { 
					allowPointSelect: true, 
					cursor: 'pointer', 
					dataLabels: { 
						enabled: true,
					 	color: '#000000',
					 	connectorColor: '#000000',
					 	format: '<b>{point.name}</b>: {point.percentage:.1f} %'
					 }, 
					showInLegend: true
				} 
			}, 
			series: [{ 
				type: 'pie', 
				name: '座席状态占比', 
				data: arr
			}] 
		});
	})
	//alert(1);
	function showAgentStatusSum(agentid)
	{
		var now=new Date();
		var DateStr=now.getFullYear()+"-"+now.getMonth()+"-"+now.getDate();
		var url="http://192.168.10.102:9080/report/ReportServer?reportlet=hotline/[5ea7][5e2d][5de5][4f5c][65f6][957f][5360][6bd4][56fe].cpt&agentnum="+agentid+"&beginDate="+DateStr+"&endDate="+DateStr;
		//alert(url);
		top.addTab("座席状态统计",url);
	}
    function setUrl(){
    	window.location.href = "${ctx}/softphone/agentMonitor.do?refresh";
    }
	setInterval("setUrl()",3000);
</script>                        
</body>
</html>