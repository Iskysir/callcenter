<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>回访题目</title>
    <%@ include file="/webpage/com/chrhc/project/hl/common/hljsandcss.jsp"%>
    <script type="text/javascript">
        var visitlist = '${visitlist}';
        var visitlistjson = '${visitlistjson}';
    </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="4" beforeSubmit="updateselect">
    <section id="mainbox">
        <div class="tab-content tab-box">
            <div class="tab-pane active" id="jbxx">
                <c:forEach items="${visitlist}" var="HlVisitItemEntity" varStatus="status">
                <div class="container form-card">

                        <div class="row">
                            <div class="col-md-12">
                                <div  style="text-align: center;">
                                    <label >${ status.index + 1}.${HlVisitItemEntity.itemName}</label><br>
                                    是<input type="radio" name="${HlVisitItemEntity.id}" value="yes" <c:if test="${HlVisitItemEntity.bza=='yes'}"> checked</c:if> ><br>
                                    否<input type="radio" name="${HlVisitItemEntity.id}" value="no" <c:if test="${HlVisitItemEntity.bza=='no'}"> checked</c:if> >
                                </div>
                            </div>
                        </div>


                </div>
                </c:forEach>
            </div>
        </div>
    </section>
</t:formvalid>
</body>
<script src = "webpage/com/chrhc/project/hl/visit/hlBusVisit.js"></script>