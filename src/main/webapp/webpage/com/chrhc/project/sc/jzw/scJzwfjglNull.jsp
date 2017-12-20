<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div>
	<c:if test="${errFlag == 'NULL'}">
	当前建筑物物未关联建筑物信息，请确认！
	</c:if>
	<c:if test="${errFlag == 'AUTOFALSE'}">
	当前建筑物【${name}】未生成或需重新生成房间信息，请确认！
	</c:if>
</div>
