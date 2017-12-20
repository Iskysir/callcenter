<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/views/business/accept/accept_relate.jsp" %>

<script type="text/javascript">



	$("a[orderId]").click(function(){
		relateOrder_($(this).attr("orderId"));
	});
	
	/**
	 * 将两条订单关联
	 */
	function relateOrder_(id) {
		var resId = '${resId}';
		$.post(
			"${ctx}/channelrecord/saveRelateOrder/" + resId + "/" + id,
			"",
			function(data) {
				//alert(data);
		});
	}
	
	

</script>