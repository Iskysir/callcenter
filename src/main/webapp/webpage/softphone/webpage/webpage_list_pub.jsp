<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title>答复列表</title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinecss.jsp"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejs.jsp"%>
</head>
<body>

	<div class="inner-box">	
        <div class="search-form">
            <form id="searchForm" method="post" action="${ctx}/channelrecord/wp/listPub/ChannelTypeWebPage">
            	 <input type="hidden" id="refresh" name="refresh" value="${param.refresh}" >
                <div class="row">
                    <div class="col-md-4">
                        <label for="exampleInputInfoA">发送人：</label>
                        <input type="text" class="form-control-plus search-input" name="search_EQ_name" value="${param.search_EQ_name}" >
                    </div>
                    <div class="col-md-4">
                        <label for="exampleInputInfoA">标题：</label>
                        <input type="text" class="form-control-plus search-input" name="search_LIKE_subject" value="${param.search_LIKE_subject}" >
                    </div>
             	</div>
               
               <div class="row m-t-10">
                    <div class="col-md-10">
                         <button type="submit" class="btn btn-primary">查询</button>
                    	 <!-- <input type="button" value="新增" class="btn btn-primary" onclick="toAddWebPage()"/> -->
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
							<a href="javascript:viewWebPage('${one.id}')" ><i class="fa fa-eye green"></i><span>查看</span></a>
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
	     			//uiBase.addOneTab('查看',"${ctx}/channelrecord/viewWebPage/"+id);	
	     			window.open("${ctx}/channelrecord/wp/viewWebPage/"+id, "viewWebPageWindow","toolbar =no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
				}

     		});
	});
	//判断给信息是否公开
	function isPublic(id){
		var is='';
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
	//查看按钮动作
	function viewWebPage(id){
		var is = isPublic(id);
			
		if(is == '0'){
			toSearchCode(id);
		}else{    				
 			//uiBase.addOneTab('查看',"${ctx}/channelrecord/viewWebPage/"+id);	
 			window.open("${ctx}/channelrecord/wp/viewWebPage/"+id, "viewWebPageWindow","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
		}
	}

	/**
	 * 新增网页受理信息
	 */
	function toAddWebPage(){
		//uiBase.addOneTab('新增',"${ctx}/channelrecord/toAddWebPage");
		window.open("${ctx}/channelrecord/wp/toAddWebPage", "newWebPageWindow","toolbar =no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
	}
</script>
</html>