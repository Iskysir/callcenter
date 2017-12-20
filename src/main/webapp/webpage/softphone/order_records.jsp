<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title></title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinecss.jsp"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejs.jsp"%>

<script type="text/javascript">
			
		function view(id,type){
			uiBase.addOneTab(type + '查看',"${ctx}/channelrecord/view/" + id );	
		}	
		
		$(function(){
			$("#userlistTable img").click(function(){
				var src = $(this).attr("src");
				uiBase.showDialog({
				        title:"图片查看",
				        width: "800px",
				        height:"480px",
				        padding: "0 10px",
				        content:"<img src=" + src +" />"
				 });
			});
		});

</script>

</head>
<body>

	<div class="inner-box">	
        <table class="table table-bordered table-hover table-fixed-layout"  id="userlistTable" >
            <thead>
				<tr>
					<th>发送人</th>
					<th>收件人</th>
					<c:if test="${record.type =='ChannelTypeMail'}">
						<th>标题</th>
					</c:if>
					
					<c:if test="${record.type !='ChannelTypeMail'}">
					<th>内容</th>
					</c:if>
					
					<th>发送时间</th>
					<th>类型</th>
					<th>收发</th>
				  <th>操作</th> 
			</thead>
			<tbody>	
				<c:forEach items="${records}" var="one">
					<tr>
						<td><c:out value="${empty one.obligateB ? one.fromAddress : one.obligateB  }" escapeXml="true"/> </td>
						<td><c:out value="${empty one.bccAddress ? one.toAddress : one.bccAddress  }" escapeXml="true"/>  </td>
						<c:if test="${record.type == 'ChannelTypeMail'}">
							<td>${one.subject}</td>
						</c:if>
						<c:if test="${record.type !='ChannelTypeMail'}">
							<td>
							
								<c:if test="${ one.subject != 'image' }">
							 		${param.search_EQ_type == 'ChannelTypeMail' ? one.contentText : one.content}
							 	</c:if> 
								<c:if test="${ one.subject == 'image' }">
							 		 <img width="310" src="${ctx}/image/showImage/${one.id}">
							 	</c:if>						 	
							 </td>
						 </c:if>
						<td><fmt:formatDate  value="${one.sendTime}"   pattern="yyyy-MM-dd HH:mm:ss"/> </td>
						<td>${one.typeText}</td>
						<td>${one.sendTypeText}</td>
						<td><a href="javascript:view('${one.id}','${one.typeText}')" ><i class="fa fa-eye green"></i><span>查看</span></a></td>
					</tr>		
					
				</c:forEach>
			</tbody>
		</table>

	</div>
				
</body>
</html>