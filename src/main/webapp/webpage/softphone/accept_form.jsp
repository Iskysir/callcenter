<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/views/business/accept/accept_input.jsp" %>

<script type="text/javascript">
	parent.frames["chatFrame"].orderId = '${order.id}';
	//uiBase.trigerParentTabFunc('orderId = "${order.id}" ;');
</script>