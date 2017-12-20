<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title></title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinecss.jsp"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejs.jsp"%>
<script
	src="${ctx}/resources/general/common/jquery/jquery.fileDownload.js"
	type="text/javascript"></script>
<script type="text/javascript">
 

	function voicemailRecord(id,subject){
		//uiBase.addOneTab("留言收听","${ctx}/channelrecord/voicemailRecord/" + id );	
		$("#record_dialogid").load(
				"${ctx}/channelrecord/voicemailRecord" ,
				{"subject":subject},
				function() {
					
		});
		
		parent.$.dialog({
			title : "留言收听",
			width : "800px",
			padding : "10px",
			lock : false,
			content : document
					.getElementById("record_dialogid"),
			cancel : function() {
				$("#record_dialogid").empty();
			},
			cancelVal : "返回",
			button : [ {
				name : '下载',
				callback : function() {
					downloadRecord();
					return false;
				}
			} ],
			id : "record_dialogid"
		});
	}	
	
function dealObligateE(){
		
	}
	
</script>

</head>
<body>

	<div class="inner-box">	
        <div class="search-form">
            <form id="pageform" method="post" action="${ctx}/channelrecord/list/${type}" onsubmit="dealObligateE();">
            	 <input type="hidden" id="refresh" name="refresh" value="${param.refresh}" >
                <div class="row">
                    
                    <div class="col-md-4">
                        <label for="exampleInputInfoA">发送人：</label>
                        <input type="text" class="form-control-plus search-input" name="search_LIKE_fromAddress" value="${param.search_LIKE_fromAddress}" >
                    </div>
                      <div class="col-md-4">
                         <button type="submit" class="btn btn-primary">查询</button>
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
					<th>创建时间</th>
					<th>录音</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>	
				<c:forEach items="${page.content}" var="one">
					<tr>
						<td><input type="checkbox" name="ids" value="${one.id}" icheck/></td>
						<td><c:out value="${one.fromAddress}" /></td>						
						<td><fmt:formatDate  value="${one.createDatetime}"   pattern="yyyy-MM-dd HH:mm:ss"/> </td>
						<td>${one.content}</td>
						<td>
							<a href="javascript:voicemailRecord('${one.id}','${one.subject}')" ><i class="fa fa-eye green"></i><span>录音</span></a>
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
	
		<div id="record_dialogid" style="display: none;"></div>
</body>


</html>