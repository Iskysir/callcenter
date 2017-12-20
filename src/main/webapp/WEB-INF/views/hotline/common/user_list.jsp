<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>

	<table class="table table-bordered table-hover table-fixed-layout dataTable">
		<thead>
			<tr class="">
				<th class="col-md-1">
					<c:if test="${'mult' ==  selType }">
						<input id="js-checkall" type="checkbox" name="user_checkbox" />
					</c:if>
				</th>
				<th>姓名</th>
				<th>用户名</th>
				<th>办公电话</th>
			</tr>
		</thead>
	
		<tbody>
	
			<c:forEach items="${page.content}" var="user" varStatus="status">
				<tr>
					<td>
						<input id="user_${status.index+1}" name="user_checkbox" type="${'mult' ==  selType ? 'checkbox' : 'radio'}" value="${user.id}" userName="${user.displayName}" />
					</td>
					<td>${user.displayName}</td>
					<td>${user.userName}</td>
					<td>${user.officePhone}</td>
				</tr>
			</c:forEach>
		</tbody>
		
		<c:if test="${!empty page.content}"> 
			<tr>
				<td colspan="4">
			        <div class="row pageInfo">
			            <div class="col-md-12">
			                <tags:pagination page="${page }"/>
			            </div>
			        </div> 			
				</td>
			</tr>
		</c:if>
	</table>


<script type="text/javascript">
	$('input').iCheck({
		checkboxClass : 'icheckbox_square-blue',
		radioClass : 'iradio_square-blue'
	});
	
	
	<c:if test="${'mult' ==  selType }">
		var flag = 1;
		$('#js-checkall').next(".iCheck-helper").click(function() {
			if (flag == 1) {
				$(".table-fixed-layout input").iCheck('check');
				flag = 0;
			} else {
				$(".table-fixed-layout input").iCheck('uncheck');
				flag = 1;
			}
		})
	</c:if>
		
	$("input[name='user_checkbox']").on('ifChecked', function(event){
		<c:if test="${'mult' !=  selType }">
			var data = $("#selUserDialog").data();
			for(var p in data){
				$("#selUserDialog").removeData(p);
			};
		</c:if>
		$("#selUserDialog").data(this.value,$(this).attr("userName"));
	});
	
	$("input[name='user_checkbox']").on('ifUnchecked', function(event){
		$("#selUserDialog").removeData(this.value);
	});
</script>
