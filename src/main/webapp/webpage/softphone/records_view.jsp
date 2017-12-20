<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title></title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinecss.jsp"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejs.jsp"%>

<script type="text/javascript">

		function addOrder(id){
			uiBase.addOneTab('工单新增',"${ctx}/channelrecord/addOrder/" + id );	
		}

		function viewOrder(id){
			uiBase.addOneTab('工单查看',"${ctx}/business/accept/view/" + id );	
		}

		function toRelate(id){	
			uiBase.addOneTab('关联工单信息','${ctx}/channelrecord/relateOrder/' + id);		
		}	
</script>

</head>
<body>
	<div class="inner-box">	
        <table class="table table-bordered table-hover table-fixed-layout"  id="userlistTable" >
            <thead>
				<tr>
					<th>发送人</th>
					<c:if test="${param.search_EQ_type =='ChannelTypeMail'}">
						<th>标题</th>
					</c:if>
					<th>内容</th>
					<th>发送时间</th>
					<th>类型</th>
					<th>收发</th>
					<!-- <th>操作</th> -->
				</tr>
			</thead>
			<tbody>	
				<c:forEach items="${records}" var="one">
					<tr>
						<td>${one.fromAddress}</td>
						
						<c:if test="${param.search_EQ_type == 'ChannelTypeMail'}">
							<td>${one.subject}</td>
						</c:if>
						
						<td>${one.contentText}</td>
						<td><fmt:formatDate  value="${one.sendTime}"   pattern="yyyy-MM-dd HH:mm:ss"/> </td>
						<td>${one.typeText}</td>
						<td>${one.sendTypeText}</td>
						
					</tr>		
					
				</c:forEach>
			</tbody>
		</table>

        <div class="row pageInfo">
            <div class="col-md-6">
				<c:choose>
					<c:when test="${empty record.obligateC}">
						<button type="button" class="btn btn-primary" onclick="addOrder('${record.id}');">新建工单</button>
						<%-- <button type="button" class="btn btn-primary" onclick="toRelate('${record.id}');">关联工单</button> --%>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn btn-primary" onclick="viewOrder('${record.obligateC}');">查看工单</button>
					</c:otherwise>
				</c:choose>
						
				<button type="button" class="btn btn-default" onclick="uiBase.closeCurrentTab();" >返回</button>
            </div>
        </div>
        
	</div>
	
	
</body>
</html>