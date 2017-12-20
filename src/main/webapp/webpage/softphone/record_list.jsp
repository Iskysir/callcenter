<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title></title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinecss.jsp"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejs.jsp"%>

<script type="text/javascript">

	function toReply(id,type){
		var title = ""; 
		if('ChannelTypeMail' == type){
			title = '邮件';
		}else if('ChannelTypeSinaweibo' == type){
			title = '微博';
		}else if('ChannelTypeSms' == type){
			title = '短信';
		}
		
		uiBase.addOneTab(title + '回复',"${ctx}/channelrecord/toReply/" + id);	
	}

	function view(id,type){
		uiBase.addOneTab(type + '查看',"${ctx}/channelrecord/view/" + id );	
	}	
	
	function viewOrder(id){
		if(id){
			uiBase.addOneTab('查看业务工单','${ctx}/business/accept/modify/'+id+"?pageTag=viewPage");	
		}
	}		
	
	var period = 1000 * 10;
	var refreshFlag = '${param.refresh}';
	var timeout ;
	var interval;
	var countDownTime = period / 1000;
	$(function(){
			if(refreshFlag){
				interval = setInterval("countDown()",1000);
				timeout = setTimeout("searchForm.submit();",period);
			}
		}
	);
	
	function refresh_(){
		if(refreshFlag){
			$("#refresh").val("");
			if(timeout){
				clearTimeout(timeout);
				clearInterval(interval);
				countDownTime = period / 1000;
			}
		}else {
			$("#refresh").val("true");
			interval = setInterval("countDown()",1000);
			timeout = setTimeout("searchForm.submit();",period);
		}
		refreshFlag = !refreshFlag;
		$("#refreshBtn").html(refreshFlag ? '停止&nbsp;<span id="countDown"></span>' : '定时刷新');
		
	}
	
	function countDown(){
		countDownTime--;
		$("#countDown").html(countDownTime);
	}
	
	function dealObligateE(){
		
	}

	function toDeleted(id){
		var d = art.dialog({
		    title: '删除提示',
		    icon:'delete',
		    lock:true,
		    content: '确定要删除吗？ ',
		    //okValue: '确定',
		    ok: function () {
		    	$.ajax({
					url:"${ctx}/channelrecord/delete/"+id,
					method:"get",
					success:function (data){
						var resultJson = eval('('+data+')');
						if(resultJson.result == "success"){
							 $("#searchForm").submit();
							 art.dialog({title: '删除提示',icon: 'succeed',lock: false,content: '删除成功，2秒后会自动关闭……',time:2});
						}else{
							art.dialog({title: '删除提示',icon: 'error',lock: false,content: '删除失败，2秒后会自动关闭……',time:2});
						}	
					},
					error:function(data){
						art.dialog({title: '删除提示',icon: 'error',lock: false,content: '删除失败，2秒后会自动关闭……',time:2});
					}
				});
		    },
		   // cancelValue: '取消',
		    cancel: true
		});
		d.show(); 
	}



</script>

</head>
<body>

	<div class="inner-box">	
        <div class="search-form">
            <form id="searchForm" method="post" action="${ctx}/channelrecord/list/${type}" onsubmit="dealObligateE();">
            	 <input type="hidden" id="refresh" name="refresh" value="${param.refresh}" >
                <div class="row">
                     <div class="col-md-4">
                        <label for="exampleInputInfoB">发送类型：</label>
                        <select class="form-control-plus search-select" id="search_sendType" name="search_LIKE_sendType">
							<option value="">全部</option>
							<option value="1">接收</option>
							<option value="2">发送</option> 
                        </select>
                    </div>  
                    
                    <div class="col-md-4">
                        <label for="exampleInputInfoA">内容：</label>
                        <input type="text" class="form-control-plus search-input" name="search_LIKE_content" value="${param.search_LIKE_content}" >
                    </div>
 
                     <div class="col-md-4">
                        <label for="exampleInputInfoA">工单编号：</label>
                        <input type="text" class="form-control-plus search-input"  name="search_LIKE_obligateE" value="${param.search_LIKE_obligateE}" >
                    </div>
                                       

   
             	</div>
             	
             	<div class="row m-t-10">
             	   	<div class="col-md-4">
                        <label for="exampleInputInfoB">是否处理：</label>
                        <select class="form-control-plus search-select" id="orderRelate" name="orderRelate" >
							<option value="">全部</option>
							<option value="wgl">未处理</option> 
							<option value="gl">已处理</option>
                        </select>
                    </div>  
                    
               		<c:if test="${type=='ChannelTypeMail'}">
	                    <div class="col-md-4">
	                        <label for="exampleInputInfoA">标题：</label>
	                        <input type="text" class="form-control-plus search-input" name="search_LIKE_subject" value="${param.search_LIKE_subject}" >
	                    </div>
                    </c:if>                  
               </div>
               
               <div class="row m-t-10">
                    <div class="col-md-10">
                         <button type="submit" class="btn btn-primary">查询</button>
                         <button type="button" id="refreshBtn" class="btn btn-primary" onclick="refresh_();">${param.refresh == 'true' ? '停止&nbsp;<span id="countDown"></span>' : '定时刷新'}</button>
                    </div>
                </div>             	
             	
             </form>
        </div>
        
        <script type="text/javascript">
        	$("#search_sendType").val('${param.search_LIKE_sendType}');
        	$("#orderRelate").val('${param.orderRelate}');
        </script>
			
        <table class="table table-bordered table-hover table-fixed-layout"  id="userlistTable">
            <thead>
				<tr>
					<th style="width:34px;"><input type="checkbox" id="js-checkall" icheck></th>
					<th>发送人</th>
					<th>接收人</th>
					<c:if test="${type =='ChannelTypeMail'}">
						<th>标题</th>
					</c:if>
					<c:if test="${type !='ChannelTypeMail'}">
						<th>内容</th>
					</c:if>
					<th>发送时间</th>
					<th>类型</th>
					<th>收发类型</th>
					<th>工单编号</th>
					<th style="width:200px;">操作</th>
				</tr>
			</thead>
			<tbody>	
				<c:forEach items="${page.content}" var="one">
					<tr>
						<td><input type="checkbox" name="ids" value="${one.id}" icheck/></td>
						<c:if test="${type == 'ChannelTypeSinaweibo'}">
							<td><c:out value="${one.fromAddress}" escapeXml="true"/></td>
						</c:if>
						<c:if test="${type != 'ChannelTypeSinaweibo'}">
							<td><c:out value="${empty one.obligateB ? one.fromAddress : one.obligateB  }" escapeXml="true"/></td>
						</c:if>
						
						<td><c:out value="${one.toAddress}" /></td>						
						<c:if test="${type == 'ChannelTypeMail'}">
							<td>${one.subject}</td>
						</c:if>
						<c:if test="${type !='ChannelTypeMail'}">
							<td><c:out value="${one.contentText}" escapeXml="true"/></td>
						</c:if>
						<td><fmt:formatDate  value="${one.sendTime}"   pattern="yyyy-MM-dd HH:mm:ss"/> </td>
						<td>${one.typeText}</td>
						<td>${one.sendTypeText}</td>
						<td><c:if test="${not empty one.obligateE}">
						<a href="javascript:viewOrder('${one.obligateC}')" class="btn-link">${one.obligateE}</a></c:if></td>
						<td>
							<a href="javascript:view('${one.id}','${one.typeText}')" ><i class="fa fa-eye green"></i><span>查看</span></a>
							&nbsp;
							<c:if test="${!one.send }">
								<c:if test="${one.type=='ChannelTypeMail' || one.type=='ChannelTypeSinaweibo'|| one.type=='ChannelTypeSms'}">
									<a href="javascript:toReply('${one.id}','${one.type}')" ><i class="fa fa-mail-reply green"></i><span>回复</span></a>
								</c:if>
							</c:if>
							
							<a onclick="toDeleted('${one.id}')"><i class="fa fa-eraser green"></i><span>删除</span></a>
						</td>
						
					</tr>		
					
				</c:forEach>
			</tbody>
		</table>

        <div class="row pageInfo">
            <div class="col-md-6">
            </div>
            <div class="col-md-6">
                <tags:pagination page="${page }"/>
            </div>
        </div>
        
	</div>
</body>
</html>