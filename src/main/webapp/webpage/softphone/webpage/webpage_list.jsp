<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title></title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinecss.jsp"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejs.jsp"%>

<script type="text/javascript">
		
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

	/**
	 * 新增网页受理信息
	 */
	function toAddWebPage(){
		uiBase.addOneTab('新增',"${ctx}/channelrecord/wp/toAddWebPage");
	}
	/**
	* 修改页面
	*/
	function editWebPage(id){
		uiBase.addOneTab('修改',"${ctx}/channelrecord/wp/editWebPage/"+id);
	}
	/**
	* 回复页面
	*/	
	function toReplyWebPage(id){
		uiBase.addOneTab('处理',"${ctx}/channelrecord/wp/toReplyWebPage/"+id);
	}
</script>

</head>
<body>

	<div class="inner-box">	
        <div class="search-form">
            <form id="searchForm" method="post" action="${ctx}/channelrecord/wp/list/ChannelTypeWebPage">
            	 <input type="hidden" id="refresh" name="refresh" value="${param.refresh}" >
                <div class="row">
                     <!-- <div class="col-md-4">
                        <label for="exampleInputInfoB">发送类型：</label>
                        <select class="form-control-plus search-select" id="search_sendType" name="search_LIKE_sendType">
							<option value="">全部</option>
							<option value="1">接收</option>
							<option value="2">发送</option> 
                        </select>
                    </div>   -->
                    <div class="col-md-4">
                        <label for="exampleInputInfoA">发送人：</label>
                        <input type="text" class="form-control-plus search-input" name="search_EQ_name" value="${param.search_EQ_name}" >
                    </div>
                    <div class="col-md-4">
                        <label for="exampleInputInfoA">标题：</label>
                        <input type="text" class="form-control-plus search-input" name="search_LIKE_subject" value="${param.search_LIKE_subject}" >
                    </div>
 
                     <div class="col-md-4">
                        <label for="exampleInputInfoA">工单编号：</label>
                        <input type="text" class="form-control-plus search-input"  name="search_LIKE_obligateE" value="${param.search_LIKE_obligateE}" >
                    </div>
                                       

   
             	</div>
             	
             	<%-- <div class="row m-t-10">
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
               </div> --%>
               
               <div class="row m-t-10">
                    <div class="col-md-10">
                         <button type="submit" class="btn btn-primary">查询</button>
                         <button type="button" id="refreshBtn" class="btn btn-primary" onclick="refresh_();">${param.refresh == 'true' ? '停止&nbsp;<span id="countDown"></span>' : '定时刷新'}</button>
                    	 <input type="button" value="新增" class="btn btn-primary" onclick="toAddWebPage()"/>
                    </div>
                </div>             	
             	
             </form>
        </div>		
        <table class="table table-bordered table-hover table-fixed-layout"  id="userlistTable">
            <thead>
				<tr>
					<th style="width:34px;"><input type="checkbox" id="js-checkall" icheck></th>
					<th>发送人</th>					
					<th>标题</th>					
					<th>发送时间</th>
					<th>工单编号</th>
					<th>是否公开</th>
					<th style="width:200px;">操作</th>
				</tr>
			</thead>
			<tbody>	
				<c:forEach items="${page.content}" var="one">
					<tr id="datalist">
						<td><input type="checkbox" name="ids" value="${one.id}" icheck/></td>
						<td><c:out value="${one.name}" /></td>
						<td class="ft-black">
							<a href="#" id="showView" name="${one.id}" class="btn-link">${one.subject}</a>
						</td>
						<td><fmt:formatDate  value="${one.createDatetime}"   pattern="yyyy-MM-dd HH:mm:ss"/> </td>
						<td><c:if test="${not empty one.obligateE}">
						<a href="javascript:viewOrder('${one.obligateC}')" class="btn-link">${one.obligateE}</a></c:if></td>
						<c:choose>
						<c:when test="${one.isPublic == '0'}">
						<td>不公开</td>
						</c:when >
						<c:when  test="${one.isPublic == '1'}">
						<td>公开</td>
						</c:when >
						<c:otherwise>
						<td></td>
						</c:otherwise>
						</c:choose>
						<td>
							<a href="javascript:editWebPage('${one.id}')" ><i class="fa fa-eye green"></i><span>修改</span></a>
							<a href="javascript:toReplyWebPage('${one.id}')" ><i class="fa fa-mail-reply green"></i><span>处理</span></a>							
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
<script type="text/javascript">
	$(document).ready(function(){ 
		$("#datalist a[id='showView']").click(function(){
			
     			var id = $(this).attr("name");	
     			var is = isPublic(id);
     			
				if(is == '0'){
					toSearchCode(id);
				}else{    				
	     			uiBase.addOneTab('查看',"${ctx}/channelrecord/wp/viewWebPage/"+id);	
				}

     		});
	});
	//判断给信息是否公开
	function isPublic(id){
		var is;
		$.ajax({
			type : "post",
			url : "${ctx}/channelrecord/wp/getIsPublic/"+id,
			async : false,
			success : function(data) {
				//art.dialog({title: '提交提示',icon: 'succeed',lock: false,content: '提交成功，2秒后会自动关闭……',time:2});
				var resultJson = eval("(" + data + ")");
				is = resultJson.isPublic;
			}
		});		
		return is;
	}
	//弹出输入查询码页面
	function toSearchCode(id) {
			art.dialog.open('${ctx}/channelrecord/wp/toSearchCode/' + id + '?_x='+ Math.random(), {
			title : '查询码',
			id : 'searchCodeDialog',
			width : '30%',
			height : '25%'
			});							
	}
</script>
</html>