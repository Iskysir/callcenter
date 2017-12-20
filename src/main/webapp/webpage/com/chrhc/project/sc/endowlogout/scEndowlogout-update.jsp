<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title></title>
  <script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
  <script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
  <link id="easyuiTheme" rel="stylesheet" href="plug-in/easyui/themes/default/easyui.css" type="text/css"></link>
  <link rel="stylesheet" href="plug-in/easyui/themes/icon.css" type="text/css"></link>
  <link rel="stylesheet" type="text/css" href="plug-in/accordion/css/accordion.css">
  <link href="plug-in/chrhc/bootstrap.css" rel="stylesheet">
  <link href="plug-in/chrhc/style.css" rel="stylesheet">
  <link href="plug-in/icheck/skin/square/blue.css" rel="stylesheet">
  <style type="text/css">
  	a.easyui-linkbutton.l-btn.l-btn-plain { height:30px !important; line-height:25px !important; margin-left:5px !important;}
  	a.easyui-linkbutton.l-btn.l-btn-plain span.l-btn-left {padding-left:3px; padding-right:3px;}
  </style>
  <script type="text/javascript" src="plug-in/easyui/jquery.easyui.min.1.3.2.js"></script>
  <script type="text/javascript" src="plug-in/easyui/locale/zh-cn.js"></script>
  <script type="text/javascript" src="plug-in/tools/syUtil.js"></script>
  <script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
  <script type="text/javascript" src="plug-in/tools/curdtools_zh-cn.js"></script>
  <script type="text/javascript" src="plug-in/tools/easyuiextend.js"></script>
  <link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
  <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
  <script type="text/javascript" src="plug-in/tools/Map.js"></script>
  <script src="plug-in/chrhc/bootstrap.js"></script>
  <script src="plug-in/chrhc/ie8/html5.js"></script>
  <script src="plug-in/chrhc/ie8/respond.min.js"></script>
  <script src="plug-in/icheck/icheck.js"></script>
  <script src="plug-in/chrhc/jquery.tabs.js"></script>
  <script src="plug-in/chrhc/jquery.lazyload.js"></script>
  
<link href="plug-in/media/css/style.css" rel="stylesheet" type="text/css"/> 
<link href="plug-in/media/css/default.css" rel="stylesheet" type="text/css" id="style_color"/> 
  <script type="text/javascript">
	$(document).ready(function(){
		$('#tt').tabs({
		   onSelect:function(title){
		       $('#tt .panel-body').css('width','auto');
			}
		});
		$(".tabs-wrap").css('width','100%');

		$("#formobj").attr('class', 'form-inline valideForm');

		$("select").attr({"class":"form-control w260"});
 
		
	  });
  </script>
 </head>
 <body style="overflow-y: scroll" >
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="scEndowlogoutController.do?doUpdate" tiptype="1">
  	<input id="btn_sub" class="btn_sub" type="hidden">
	<section id="mainbox"> 
		 
		  <div class="tab-content tab-box">
		  	<div class="Current_position">
		    	<img src="plug-in/media/image/dingwei.png"><span>当前所在的位置：</span>
		    	<a href="javascript:;">
		    		<script type="text/javascript">
		    			document.write($(this).parent.$(this).parent.url_title_map[$(this).parent.location.href]);
		    		</script>
		    	</a>
		    </div>
		  	<div class="tabs_btn_div">
		    	<div class="btn" onclick="$('#btn_sub').click();changeTypeTab();"><i class="fa fa-floppy-o fa-1x"></i><span>&nbsp;保存</span></div>
		        <div class="btn" onclick='$(this).parent.$("#editFormDiv").hide();$(this).parent.$("#editForm").attr("src","");$(this).parent.$(".datagrid").show();'><i class="fa fa-reply fa-1x"></i><span>&nbsp;返回</span></div>
		    	
		    	<div class="btn" onclick="nextRecord(-1);"><i class="fa fa-refresh fa-1x"></i><span>&nbsp;上一条</span></div>
		    	<div class="btn" onclick="nextRecord(1);"><i class="fa fa-refresh fa-1x"></i><span>&nbsp;下一条</span></div> 
		    </div>
		    <ul class="nav nav-tabs">
		      	<li class="active"><a href="#jbxx" data-toggle="tab">基本信息</a></li>
		    </ul>
		    <div class="tab-pane active" id="jbxx">

				  <div class="container form-card">
					<input id="id" name="id" type="hidden" value="${scEndowlogoutPage.id }">
					<input id="createName" name="createName" type="hidden" value="${scEndowlogoutPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${scEndowlogoutPage.createBy }">
					<input id="updateName" name="updateName" type="hidden" value="${scEndowlogoutPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${scEndowlogoutPage.updateBy }">
					<input id="versionNum" name="versionNum" type="hidden" value="${scEndowlogoutPage.versionNum }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${scEndowlogoutPage.sysOrgCode }">
					<input id="delflag" name="delflag" type="hidden" value="${scEndowlogoutPage.delflag }">
					<input id="createDate" name="createDate" type="hidden" value="${scEndowlogoutPage.createDate }">
					<input id="updateDate" name="updateDate" type="hidden" value="${scEndowlogoutPage.updateDate }">
					<input id="rkId" name="rkId" type="hidden" value="${scEndowlogoutPage.rkId }">
					<input id="endowid" name="endowid" type="hidden" value="${scEndowlogoutPage.endowid }">
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label" for="code">参保编号</label>
								<input id="code" name="code" class="form-control w260" value="${scEndowlogoutPage.code }" nullmsg="请输入参保编号！" validtype="sc_endowlogout,code,id" datatype="*1-50" type="text">
								<span style="color: red;">*</span>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">参保编号</label>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label" for="name">参保人姓名</label>
								<input id="name" name="name" class="form-control w260" value="${scEndowlogoutPage.name }" nullmsg="请输入参保人姓名！" datatype="*1-50" type="text">
				               <span class="suoshu" onclick="inputClick(this,'','endowmentins','endowid=id,code=code,name=name,pidcard=idcard','{&quot;queryType&quot;:&quot;**&quot;,&quot;singleSelect&quot;:true}');"></span>
								<span style="color: red;">*</span>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">参保人姓名</label>
							</div>
						</div>
					</div>	 
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label" for="pidcard">身份证号</label>
								<input id="pidcard" name="pidcard" class="form-control w260" value="${scEndowlogoutPage.pidcard }" nullmsg="请输入身份证号！" validtype="sc_endowlogout,pidcard,id" readonly="readonly" datatype="idcard" type="text">
								<span style="color: red;">*</span>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">身份证号</label>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label" for="cancellreason">注销原因</label>
								<t:dictSelect field="cancellreason" type="list" typeGroupCode="cancellreason" defaultVal="${scEndowlogoutPage.cancellreason}" hasLabel="false"  title="注销原因"></t:dictSelect>
								<span style="color: red;">*</span>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">注销原因</label>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label" for="cancelldate">注销日期</label>
								<input id="cancelldate" name="cancelldate" value="${scEndowlogoutPage.cancelldate }" class="form-control w260" onclick="WdatePicker({errDealMode:1,maxDate:'%y-%M-%d'})" nullmsg="请输入注销日期！" datatype="date" type="text">
								<span style="color: red;">*</span>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">注销日期</label>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label" for="beniname">受益人姓名</label>
								<input id="beniname" name="beniname" class="form-control w260" value="${scEndowlogoutPage.beniname }"  nullmsg="请输入受益人姓名！" datatype="*1-50" validtype="sc_endowlogout,rk_id,id" type="text">
				               <span class="suoshu" onclick="inputClick(this,'','sc_rkjbxxnew','rk_id=id,beniname=xm,benisex=xb,benibirthday=csrq,beniidcard=sfzh','{&quot;singleSelect&quot;:true,&quot;beniidcard&quot;:&quot;cardInputId&quot;,&quot;beniname&quot;:&quot;name&quot;,&quot;benibirthday&quot;:&quot;birthday&quot;,&quot;benisex&quot;:&quot;sex&quot;}');"></span>
								<span style="color: red;">*</span>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">受益人姓名</label>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label" for="benisex">受益人性别</label>
								<t:dictSelect field="benisex" type="list" typeGroupCode="sex" defaultVal="${scEndowlogoutPage.benisex}" hasLabel="false"  title="受益人性别"></t:dictSelect>     
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">受益人性别</label>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label" for="benibirthday">受益人出生日期</label>
								<input id="benibirthday" name="benibirthday" value="${scEndowlogoutPage.benibirthday }" class="form-control w260" onclick="WdatePicker({errDealMode:1,maxDate:'%y-%M-%d'})" readonly="readonly" nullmsg="请输入受益人出生日期！" readonly="readonly" datatype="date" type="text">
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">受益人出生日期</label>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label" for="beniidcard">受益人身份证号</label>
								<input id="beniidcard" name="beniidcard" class="form-control w260" value="${scEndowlogoutPage.beniidcard }" nullmsg="请输入受益人身份证号！" validtype="sc_endowlogout,beniidcard,id"  readonly="readonly" datatype="idcard" type="text">
								<span style="color: red;">*</span>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">受益人身份证号</label>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label" for="relation">与参保人关系</label>
								<t:dictSelect field="relation" type="list" typeGroupCode="qsgx" defaultVal="${scEndowlogoutPage.relation}" hasLabel="false"  title="与参保人关系"></t:dictSelect>     
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">与参保人关系</label>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label" for="paddress">户籍所在地</label>
								<input id="paddress" name="paddress" class="form-control w260" value="${scEndowlogoutPage.paddress }" nullmsg="请输入户籍所在地！" ignore="ignore" datatype="*1-50" type="text">
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">户籍所在地</label>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label" for="telphone">联系人电话</label>
								<input id="telphone" name="telphone" class="form-control w260" value="${scEndowlogoutPage.telphone }" nullmsg="请输入联系人电话！" ignore="ignore" datatype="tel" type="text">
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">联系人电话</label>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label" for="address">居住地址</label>
								<input id="address" name="address" class="form-control w260" value="${scEndowlogoutPage.address }" nullmsg="请输入居住地址！" ignore="ignore" datatype="*1-50" type="text">
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">居住地址</label>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label" for="nominatedbank">领取个人账户余额的指定银行</label>
								<t:dictSelect field="nominatedbank" type="list" typeGroupCode="" defaultVal="${scEndowlogoutPage.nominatedbank}" hasLabel="false"  title="领取个人账户余额的指定银行"></t:dictSelect>     
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">领取个人账户余额的指定银行</label>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label" for="bankcode">银行账号</label>
								<input id="bankcode" name="bankcode" class="form-control w260" value="${scEndowlogoutPage.bankcode }" nullmsg="请输入银行账号！" ignore="ignore" datatype="*1-50" type="text">
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">银行账号</label>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 center">
							<button id="btn_sub" type="submit" class="btn btn-sure">确定</button>
		          			<button type="submit" class="btn btn-cancel" onclick="closeCurrentTab()">关闭</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	</t:formvalid>
 </body>
  <script type="text/javascript">
		$(document).ready(function() {
			$('input').not('.checkboxchrhc input').iCheck({
				checkboxClass : 'icheckbox_square-blue',
				radioClass : 'iradio_square-blue',
				increaseArea : '20%' 
			});
			// 全选
			$('.table input#selectAll').on('ifChecked', function(event) {
				$('.table input').iCheck('check');
			});
			// 取消全选
			$('.table input#selectAll').on('ifUnchecked', function(event) {
				$('.table input').iCheck('uncheck');
			});
			$('.demo').Tabs({
				event : 'click'
			});
		});
	</script>
  <script src = "webpage/com/chrhc/project/sc/endowlogout/scEndowlogout.js"></script>		