<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>维护终端号</title>
    <%@ include file="/webpage/com/chrhc/project/hl/common/hljsandcss.jsp"%>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        window.onload = function()
        {
            $("#zdh").val(W.$("#zdh").val());
            $("#shh").val(W.$("#shh").val());
            $("#shmc").val(W.$("#shmc").val());
            $("#lxr").val(W.$("#lxr").val());
            $("#lxfs").val(W.$("#lxfs").val());
            $("#zjdz").val(W.$("#zjdz").val());
            $("#zdjtazdz").val(W.$("#zdjtazdz").val());
            $("#sdjqmc").val(W.$("#sdjqmc").val());
            $("#ds").val(W.$("#ds").val());
            $("#jx").val(W.$("#jx").val());
            $("#cj").val(W.$("#cj").val());
            $("#tzr").val(W.$("#tzr").val());

        };

    </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="4" beforeSubmit="updateselect">
    <section id="mainbox">
        <div class="tab-content tab-box">
            <div class="tab-pane active" id="jbxx">

                <div class="container form-card">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="control-label" >终端号</label>
                                    <input type="text" class="form-control w260" name="zdh" id="zdh" >
                                    </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="control-label" >商户号</label>
                                    <input type="text"  class="form-control w260" name="shh" id="shh" >
                                    </div>
                            </div>
                        </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label" >商户名称</label>
                                <input type="text" class="form-control w260" name="shmc" id="shmc" >
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label" >联系人</label>
                                <input type="text"  class="form-control w260" name="lxr" id="lxr" >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label" >联系方式</label>
                                <input type="text" class="form-control w260" name="lxfs" id="lxfs" >
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label" >装机地址</label>
                                <input type="text"  class="form-control w260" name="zjdz" id="zjdz" >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label" >终端具体安装地址</label>
                                <input type="text" class="form-control w260" name="zdjtazdz" id="zdjtazdz" >
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label" >收单机器名称</label>
                                <input type="text"  class="form-control w260" name="sdjqmc" id="sdjqmc" >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label" >地市</label>
                                <input type="text" class="form-control w260" name="ds" id="ds" >
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label" >机型</label>
                                <input type="text" class="form-control w260" name="jx" id="jx" >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label" >厂家</label>
                                <input type="text" class="form-control w260" name="cj" id="cj" >
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label" >拓展人</label>
                                <input type="text"  class="form-control w260" name="tzr" id="tzr" >
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</t:formvalid>
</body>
<script src = "webpage/com/chrhc/project/hl/visit/hlBusVisit.js"></script>