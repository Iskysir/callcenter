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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="scEndowmentinsController.do?doUpdate" tiptype="1">
  <input id="btn_sub" class="btn_sub" type="hidden">
		 <section id="mainbox"> 
		 
		  <div class="tab-content tab-box">
		  	<div class="Current_position">
		    	<img src="plug-in/media/image/dingwei.png"><span>当前所在的位置：</span>
		    	<a href="javascript:;">
		    		<script type="text/javascript">
		    			document.write(parent.parent.url_title_map[parent.location.href]);
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
					<input id="id" name="id" type="hidden" value="${scEndowmentinsPage.id }">
					<input id="createName" name="createName" type="hidden" value="${scEndowmentinsPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${scEndowmentinsPage.createBy }">
					<input id="updateName" name="updateName" type="hidden" value="${scEndowmentinsPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${scEndowmentinsPage.updateBy }">
					<input id="versionNum" name="versionNum" type="hidden" value="${scEndowmentinsPage.versionNum }">
					<input id="createDate" name="createDate" type="hidden" value="${scEndowmentinsPage.createDate }">
					<input id="updateDate" name="updateDate" type="hidden" value="${scEndowmentinsPage.updateDate }">
					<input id="gisxy" name="gisxy" type="hidden" value="${scEndowmentinsPage.gisxy }">
					<input id="rkId" name="rkId" type="hidden" value="${scEndowmentinsPage.rkId }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${scEndowmentinsPage.sysOrgCode }">
					<input id="delflag" name="delflag" type="hidden" value="${scEndowmentinsPage.delflag }">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="code">养老保险编号</label>
									<input name="code" class="form-control w260" id="code" type="text" value="${scEndowmentinsPage.code}" datatype="*1-50" validType="sc_endowmentins,code,id">
									<span style="color: red;">*</span>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">养老保险编号</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="name">姓名</label>
									<input name="name" class="form-control w260" id="name" type="text" readOnly="readonly" value="${scEndowmentinsPage.name}" nullmsg="请输入姓名！" datatype="*1-50" validType="sc_endowmentins,rk_id,id">
					                <span class="suoshu" onclick="inputClick(this,'','sc_rkjbxxnew','rk_id=id,name=xm,sex=xb,nation=mz,birthday=csrq,idcard=sfzh','{&quot;queryType&quot;:&quot;**&quot;,&quot;singleSelect&quot;:true}');"></span>
									<span style="color: red;">*</span>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">姓名</label>
								</div>
							</div>		
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="idcard">身份证号</label>
									<input name="idcard" readonly="readonly" class="form-control w260" id="idcard" type="text" value="${scEndowmentinsPage.idcard}" nullmsg="请输入身份证号！" datatype="idcard" validType="sc_endowmentins,idcard,id">
									<span style="color: red;">*</span>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">身份证号</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="insureddate">参保日期</label>
									<input name="insureddate" class="form-control w260" id="insureddate" onclick="WdatePicker({errDealMode:1,maxDate:'%y-%M-%d'})" type="text" readOnly="readonly" value='<fmt:formatDate value='${scEndowmentinsPage.insureddate}' type="date" pattern="yyyy-MM-dd"/>' nullmsg="请输入参保日期！" datatype="date">
									<span style="color: red;">*</span>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">参保日期</label>
								</div>
							</div>			
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="insuredstate">参保状况</label>
									<t:dictSelect field="insuredstate" type="list" typeGroupCode="protectstate" defaultVal="${scEndowmentinsPage.insuredstate}" hasLabel="false"  title="参保状况"></t:dictSelect> 
									<span style="color: red;">*</span>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">参保状况</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="sex">性别</label>
									<t:dictSelect field="sex" type="list" typeGroupCode="sex" defaultVal="${scEndowmentinsPage.sex}" hasLabel="false"  title="性别"></t:dictSelect>
									<span style="color: red;">*</span>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">性别</label>
								</div>
							</div>		
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="nation">民族</label>
									<t:dictSelect field="nation" type="list" typeGroupCode="mz" defaultVal="${scEndowmentinsPage.nation}" hasLabel="false"  title="民族"></t:dictSelect>     
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">民族</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="birthday">出生日期</label>
									<input name="birthday" readonly="readonly"  class="form-control w260" id="birthday" onclick="WdatePicker({errDealMode:1,maxDate:'%y-%M-%d'})" type="text" value='<fmt:formatDate value='${scEndowmentinsPage.birthday}' type="date" pattern="yyyy-MM-dd"/>' nullmsg="请输入出生日期！" datatype="date">
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">出生日期</label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="phone">联系电话</label>
									<input name="phone" class="form-control w260" id="phone" type="text" value='${scEndowmentinsPage.phone}' nullmsg="请输入联系电话！" datatype="tel" ignore="ignore">
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">联系电话</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="postcode">邮编</label>
									<input name="postcode" class="form-control w260" id="postcode" type="text" value='${scEndowmentinsPage.postcode}' nullmsg="请输入邮编！" datatype="p" ignore="ignore">
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">邮编</label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="paddress">户籍地址</label>
									<input name="paddress" class="form-control w260" id="paddress" type="text" value='${scEndowmentinsPage.paddress}' nullmsg="请输入户籍地址！" datatype="*1-50" ignore="ignore">
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">户籍地址</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="address">居住地址</label>
									<input name="address" class="form-control w260" id="address" type="text" value='${scEndowmentinsPage.address}' nullmsg="请输入居住地址！" datatype="*1-50" ignore="ignore">
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">居住地址</label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="holdnation">户籍性质</label>
									<t:dictSelect field="holdnation" type="list" typeGroupCode="householdnation" defaultVal="${scEndowmentinsPage.holdnation}" hasLabel="false"  title="户籍性质"></t:dictSelect>     
									<span style="color: red;">*</span>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">户籍性质</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="insamount">个人缴费额</label>
									<input name="insamount" class="form-control w260" id="insamount" type="text" value='${scEndowmentinsPage.insamount}' nullmsg="请输入个人缴费额！" datatype="n1-10" ignore="ignore">
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">个人缴费额</label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<label class="control-label">参加其他养老保险状态:</label>
								<table cellpadding="0" cellspacing="1" class="formtable">
									<tr>
										<td>
											<label class="control-label" for="enterpriseins">企业职工基本养老保险</label>
										</td>
										<td>
											<t:dictSelect field="enterpriseins" type="radio" typeGroupCode="yesno" defaultVal="${scEndowmentinsPage.enterpriseins}" hasLabel="false"  title="企业职工基本养老保险"></t:dictSelect>     
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">企业职工基本养老保险</label>
										</td>
										<td>
											<label class="control-label" for="enterprisedate">起始时间</label>
										</td>
										<td>
											<input name="enterprisedate" class="form-control w260" id="enterprisedate" onclick="WdatePicker({onpicked:function(){(this).blur();},})" readOnly="readonly" value='<fmt:formatDate value='${scEndowmentinsPage.enterprisedate}' type="date" pattern="yyyy-MM-dd"/>' nullmsg="请输入企业养老保险起始时间！">
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">企业养老保险起始时间</label>
										</td>
									</tr>
									<tr>
										<td>
											<label class="control-label" for="landlessins">被征地农民社会保障</label>
										</td>
										<td>
											<t:dictSelect field="landlessins" type="radio" typeGroupCode="yesno" defaultVal="${scEndowmentinsPage.landlessins}" hasLabel="false"  title="被征地农民社会保障"></t:dictSelect>     
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">被征地农民社会保障</label>
										</td>
										<td>
											<label class="control-label" for="landlessinsdate">起始时间</label>
										</td>
										<td>
											<input name="landlessinsdate" class="form-control w260" id="landlessinsdate" onclick="WdatePicker({onpicked:function(){(this).blur();},})" readOnly="readonly" value='<fmt:formatDate value='${scEndowmentinsPage.landlessinsdate}' type="date" pattern="yyyy-MM-dd"/>' nullmsg="请输入被征地保障起始时间！">
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">被征地保障起始时间</label>
										</td>
									</tr>
									<tr>
										<td>
											<label class="control-label" for="oldins">老农保</label>
										</td>
										<td>
											<t:dictSelect field="oldins" type="radio" typeGroupCode="yesno" defaultVal="${scEndowmentinsPage.oldins}" hasLabel="false"  title="老农保"></t:dictSelect>     
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">老农保</label>
										</td>
										<td>
											<label class="control-label" for="oldinsdate">起始时间</label>
										</td>
										<td>
											<input name="oldinsdate" class="form-control w260" id="oldinsdate" onclick="WdatePicker({onpicked:function(){(this).blur();},})" readOnly="readonly" value='<fmt:formatDate value='${scEndowmentinsPage.oldinsdate}' type="date" pattern="yyyy-MM-dd"/>' nullmsg="请输入老农保起始时间！">
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">老农保起始时间</label>
										</td>
									</tr>
									<tr>
										<td>
											<label class="control-label" for="otherins">其他保险</label>
										</td>
										<td>
											<t:dictSelect field="otherins" type="radio"	typeGroupCode="yesno" defaultVal="${scEndowmentinsPage.otherins}" hasLabel="false"  title="其他保险"></t:dictSelect>     
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">其他保险</label>
										</td>
										<td>
											<label class="control-label" for="otherinsdate">起始时间</label>
										</td>
										<td>
											<input name="otherinsdate" class="form-control w260" id="otherinsdate" onclick="WdatePicker({onpicked:function(){(this).blur();},})" readOnly="readonly" value='<fmt:formatDate value='${scEndowmentinsPage.otherinsdate}' type="date" pattern="yyyy-MM-dd"/>' nullmsg="请输入其他起始时间！" ignore="ignore">
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">其他保险起始时间</label>
										</td>
									</tr>
								</table>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label" for="specialgroup">特殊参保群体</label>
									<t:dictSelect field="specialgroup" type="radio" typeGroupCode="specialgroup" defaultVal="${scEndowmentinsPage.specialgroup}" hasLabel="false"  title="特殊参保群体"></t:dictSelect>     
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">特殊参保群体</label>
								</div>
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

		function nextRecord(stepNum){
			var id = $("input[name='id']").val();
			alert(id);
			$(this).parent.nextRecord(id,stepNum);
		}
	</script>
  <script src = "webpage/com/chrhc/project/sc/endow/scEndowmentins.js"></script>
 
  