<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title>受理监控</title>
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
.dataShow{height:500px;background:url(../images/datashow.png) no-repeat;position:absolute;right:25px;top:55px;padding-right:12px;}
.datas{height:288px;padding:15px;}
.datas table thead th {}
.bs {
    background: url("../images/storg.png") repeat-x scroll 0 0 rgba(0, 0, 0, 0);
 
    color: #636363;
    height: 40px;
    line-height: 40px;
    padding-left: 15px;
}
</style>

</head>
<body>

<input  type="hidden" id="startime" name="startime" value="${starttimeM}">
        <div id="consultations_dialogid" style="padding-left:10px;padding-right:10px;height: 800;width: 400" >
         <form action="" method="post" >
           <div class="row m-t-10">
           <div class="col-md-12">
           	<div class="col-md-6">
           		<div id="agentColumn" style="width:70%; height: 400px; margin-right:10%;" ></div>
           	</div>
           	<div class="col-md-6">
           		<div class="dataShow" id="dataShow" style="width:130%;height:400px; margin-left:0;" >
           			<h2 class="bs" id="dataShow_bs">受理监控 </h2>
                    <div class="datas" style="width:100%;height:400px;overflow-y:auto;">
                    	<table id="modeList" class="table table-striped dataTable table-fixed-layout ">
		                    <thead>
		                        <tr class="">
		                        	<th style="width:120px;">开始时间</th>
		                        	<th style="width:120px;">结束时间</th>
		                        	<th style="width:80px;">受理渠道</th>
		                        	<th style="width:60px;">受理数</th>
		                        	<th style="width:60px;">等待中</th>
		                        	<th style="width:60px;">服务中</th>
		                        	<th style="width:60px;">放弃数</th>
		                        	<th style="width:60px;">完成数</th>
		                        </tr>
		                    </thead>
		                    <tbody>
		                    	<c:forEach items="${seriList}" var="user" varStatus="status">
									<tr>
										<td><fmt:formatDate value="${datestart}"
																pattern="MM-dd HH:mm:ss" /></td>
			                        	<td><fmt:formatDate value="${dateEnd }"
																pattern="MM-dd HH:mm:ss" /></td>
			                        	<td>${user.name }</td>
			                        	<td>${user.data[0] }</td>
			                        	
			                        	<c:choose>

											    <c:when test="${user.data[1]>= callWaitMaxNum}">
											
											      <td style="background-color: red;"><font color="ffffff">${user.data[1] } </font></td>
											
											    </c:when>
											    
											   <c:otherwise>  
											     <td >${user.data[1] }</td>
											   </c:otherwise>

  										</c:choose>
			                        	
			                        	
			                        	<td>${user.data[2] }</td>
			                        	<td>${user.data[3] }</td>
			                        	<td>${user.data[4] }</td>
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
			    
		$('#agentColumn').highcharts({
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: '受理监控'
	        },
	        xAxis: {
	            categories: ["受理数","等待中","服务中","放弃数","完成数" ]//'通话中数', '丢弃数', '服务完成数'
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: '数量'
	            },
	            stackLabels: {
	                enabled: true,
	                style: {
	                    fontWeight: 'bold',
	                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
	                }
	            }
	        },
	        legend: {
	            align: 'right',
	            x: 10,
	            verticalAlign: 'top',
	            y: 20,
	            floating: true,
	            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
	            borderColor: '#CCC',
	            borderWidth: 1,
	            shadow: false
	        },
	        tooltip: {
	            formatter: function() {
	                return '<b>'+ this.x +'</b><br/>'+
	                    this.series.name +': '+ this.y +'<br/>'+
	                    'Total: '+ this.point.stackTotal;
	            }
	        },
	        plotOptions: {
	            column: {
	                stacking: 'normal',
	                dataLabels: {
	                    enabled: true,
	                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
	                }
	            }
	        },
	        series: ${seri}
	    });
	})
	
	function setUrl(){
		var starttime = document.getElementById("startime").value;
    	window.location.href = "${ctx}/rlmonitor/ivrMonitor?startime="+starttime;
    }
	setInterval("setUrl()",10000);

</script>                        
</body>
</html>