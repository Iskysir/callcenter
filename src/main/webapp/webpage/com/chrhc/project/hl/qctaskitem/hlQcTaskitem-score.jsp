<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>考评打分</title>
    <%@ include file="/webpage/com/chrhc/project/hl/common/hljsandcss.jsp"%>
    <script type="text/javascript">
        function iFrameHeight() {
            var ifm= document.getElementById("orderviewa");
            var subWeb = document.frames ? document.frames["orderviewa"].document :
                    ifm.contentDocument;
            if(ifm != null && subWeb != null) {
                ifm.height = subWeb.body.scrollHeight;
            }
        }
    </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
             action="hlQcTaskitemController.do?doUpdate" tiptype="4" beforeSubmit="updateselect">
    <section id="mainbox">
        <div class="tab-content tab-box">
            <div class="tab-pane active" id="jbxx">
                <div class="container form-card">
                    <div class="row">
                        <div class="col-md-12" id="orderview" >
                            <iframe id="orderviewa"   width="100%"  frameborder="no" border="0"  src=""  onLoad="iFrameHeight()" ></iframe>
                        </div>
                    </div>
                </div>
                <div class="container form-card">
                    <input id="id" name="id" type="hidden" value="${hlQcTaskitemPage.id }">
                    <input id="createName" name="createName" type="hidden" value="${hlQcTaskitemPage.createName }">
                    <input id="createBy" name="createBy" type="hidden" value="${hlQcTaskitemPage.createBy }">
                    <input id="updateName" name="updateName" type="hidden" value="${hlQcTaskitemPage.updateName }">
                    <input id="updateBy" name="updateBy" type="hidden" value="${hlQcTaskitemPage.updateBy }">
                    <input id="versionNum" name="versionNum" type="hidden" value="${hlQcTaskitemPage.versionNum }">
                    <input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${hlQcTaskitemPage.sysOrgCode }">
                    <input id="delflag" name="delflag" type="hidden" value="${hlQcTaskitemPage.delflag }">
                    <input id="createDate" name="createDate" type="hidden" value="${hlQcTaskitemPage.createDate }">
                    <input id="updateDate" name="updateDate" type="hidden" value="${hlQcTaskitemPage.updateDate }">
                    <input id="taskid" name="taskid" type="hidden" value="${hlQcTaskitemPage.taskid }">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label" for="taskname">任务名称</label>
                                <input id="taskname" name="taskname" type="text" class="form-control w260" readonly="readonly">
                                <span class="Validform_checktip"></span>
                                <label class="Validform_label" style="display: none;">业务工单</label>


                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label" for="scorepersonname">评分人</label>
                                <input id="scorepersonname" name="scorepersonname" type="text" class="form-control w260" readonly="readonly">
                                <span class="Validform_checktip"></span>
                                <label class="Validform_label" style="display: none;">评分人</label>


                            </div>
                        </div>
                    </div>



                    <div class="row" id="buttonPanel">
                        <div class="col-md-12 center">
                            <button type="button" class="btn btn-sure" id="score">考评打分</button>
                            <button type="button" class="btn btn-cancel" onclick="closeCurrentTab()">关闭</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</t:formvalid>
</body>
<script src="webpage/com/chrhc/project/hl/qctaskitem/hlQcTaskitem.js"></script>