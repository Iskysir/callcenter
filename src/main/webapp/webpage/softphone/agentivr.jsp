<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title>转接</title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinecss.jsp"%>
 <link rel="stylesheet" href="${ctx}/resources/themes/hotline/common/css/index.css">
<script type="text/javascript">
	

var agentWindow=parent.$("iframe[name=softphoneFrame]")[0].contentWindow;
function btn_CmdAuto(btn)
{
	var flow=$(btn).attr("objid");
	//alert(flow);
	agentWindow.transferIvr(flow);
	//art.dialog.list['dialog_ivr'].close();
	agentWindow.art.dialog.list['dialog_ivr'].close();
	addLog("IVR Transfered");
}
</script>
</head>
<body>
 <div id="ivr_dialogid" style="padding-left:10px;padding-right:10px;">
           <form action="" method="post">
                <table id="ivrList" class="table table-striped dataTable table-fixed-layout border">
                   
				<thead>
					<tr>
						<th width="150px">IVR编号</th>
						<th>名称</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${ivrList}" var="ivr" varStatus="index">
					<c:if test="${ivr.status=='1'}">
					<tr>
						
						<td>${ivr.code}</td>
						<td>${ivr.name}</td>
						<td>
							<a href="javascript:;" objid="${ivr.code}" onclick="btn_CmdAuto(this)">
	                                    <i class="fa fa-comments-o blue"></i>
	                                    <span class="black">转接</span>
	                               		</a>
						</td>
					</tr>
					</c:if>
					</c:forEach>
				</tbody>
               </table>
          </form>
</div>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejs.jsp"%>                       
</body>
</html>