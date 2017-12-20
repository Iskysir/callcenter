<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>证明类型选择</title>
  <link href="plug-in/chrhc/bootstrap.css" rel="stylesheet">
<link href="plug-in/chrhc/style.css" rel="stylesheet">
<link href="plug-in/icheck/skin/square/blue.css" rel="stylesheet">
<style>
	.form-control {display: inline-block !important;}
</style>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script src="plug-in/chrhc/bootstrap.min.js"></script>
<script src="plug-in/chrhc/ie8/html5.js"></script>
<script src="plug-in/chrhc/ie8/respond.min.js"></script>
<script src="plug-in/icheck/icheck.js"></script>
<script src="plug-in/chrhc/jquery.tabs.js"></script>
<script src="plug-in/chrhc/jquery.lazyload.js"></script>
   <script type="text/javascript">
  $(function($){
  	$("#formobj").attr({"class":"form-inline valideForm"});
	$("select").attr({"class":"form-control w260"});
  });
  </script>	
 </head>
 <body>


 <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"  tiptype="4">
   <div class="container">				
			<div class="row">
			<div class="col-md-6">
			<div class="form-group">
						<label class="control-label" for=provetype>证明类型</label> 
						<t:dictSelect field="provetype" type="list"
									typeGroupCode="provetype"  hasLabel="false"  title="证明类型"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">证明类型</label>
							<span style="color: red;">*</span>						
					</div>
			</div>
			</div>			
			</div>
		</t:formvalid>
		
 </body>
	