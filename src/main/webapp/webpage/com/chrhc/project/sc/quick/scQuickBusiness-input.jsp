<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%
	String path_java = request.getContextPath();
	String basePath_java = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path_java + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>快捷业务</title>
   <link href="plug-in/saomiao/css/stylePlus.css" rel="stylesheet">  
   <link rel="stylesheet" href="plug-in/media/jquery.mCustomScrollbar/css/jquery.mCustomScrollbar.css" type="text/css">
  
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
 <link id="easyuiTheme" rel="stylesheet"
	href="plug-in/easyui/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="plug-in/easyui/themes/icon.css"
	type="text/css"></link>
<link rel="stylesheet" type="text/css"
	href="plug-in/accordion/css/accordion.css">
<link href="plug-in/chrhc/bootstrap.css" rel="stylesheet">
<link href="plug-in/chrhc/style.css" rel="stylesheet">
<link href="plug-in/icheck/skin/square/blue.css" rel="stylesheet">

<script type="text/javascript"
	src="plug-in/easyui/jquery.easyui.min.1.3.2.js"></script>
<script type="text/javascript" src="plug-in/easyui/locale/zh-cn.js"></script>
<script type="text/javascript" src="plug-in/tools/syUtil.js"></script>
<script type="text/javascript"
	src="plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
<script type="text/javascript" src="plug-in/tools/curdtools_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/tools/easyuiextend.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>

<script src="plug-in/icheck/icheck.js"></script>
<script src="plug-in/chrhc/jquery.tabs.js"></script>
<script src="plug-in/chrhc/jquery.lazyload.js"></script>

<script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script src="plug-in/chrhc/bootstrap.min.js"></script>
<script src="plug-in/chrhc/ie8/html5.js"></script>
<script src="plug-in/chrhc/ie8/respond.min.js"></script>
<script type="text/javascript"
	src="plug-in/artDiglog/jquery.artDialog.js?skin=default"></script>

</head>
<body style="overflow-x: hidden;">
	<div class="Current_position">
    	<img src="plug-in/scmedia/image/dingwei.png"/><span>当前所在的位置：</span><a href="javascript:parent.addTab('快捷业务','scQuickBusinessController.do?goAdd');" class="last">快捷业务</a>
    </div>

	<div class="flash">
      <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
        codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0"
        width="370" height="50" id="123" align="">
        <param name=movie value="plug-in/saomiao/123.swf">
        <param name="wmode" value="transparent">
        <param name=quality value=high>
        <embed src="plug-in/saomiao/123.swf" quality=high  width="370" height="50" name="123" align=""
        type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer">
        </embed></object>
    </div>
    <div class="id">
      <div class="h"></div>
      <button class="idbtn" onclick="refreshQuick();"></button> 
      <!-- <button class="idbtn" onclick="ReadIDCARDInfoTx_Z();"></button> -->
      <!--<button class="idbtn" onclick="getModelTypeBtn('370828198402085618','402837814fa6d1ca014fa6d1ca280000');"></button>-->
    </div>
	<!-- 快捷业务功能按钮及菜单显示区域 -->
	<div class="kj_box" style="display: none;">
		<div class="id_box">
			<!-- <div class="id_title">身份证照</div> -->
			<div class="id_pic">
				<div class="idpic_face"></div>
				<div class="idpic_face_reverse"></div>
				<div class="id_icon" onclick="javascript:parent.addTab('快捷业务','scQuickBusinessController.do?goAdd');"></div>
			</div>
		</div>
		<div class="kj_gundong">
			<div class="kj_list">
				<div class="kj_item">
					<div class="kj_icon" onclick="showModleTypeBtn('fsxx','发送信息')">
						<img src="plug-in/accordion/images/kj_01_bizshow.png" /> 
						<!-- <img src="<t:icon type='bizshow' url='plug-in/accordion/images/kj_01_bizshow.png'/>" /> -->
					</div>
					<div class="kj_title">发送信息</div>
				</div>
				<div class="kj_item">
					<div class="kj_icon" onclick="showModleTypeBtn('blzm','办理证明')">
						<img src="plug-in/accordion/images/kj_02_bizshow.png" />
					</div>
					<div class="kj_title">办理证明</div>
				</div>
				<div class="kj_item">
					<div class="kj_icon" onclick="showModleTypeBtn('bldb','办理低保')">
						<img src="plug-in/accordion/images/kj_03_bizshow.png" />
					</div>
					<div class="kj_title">办理低保</div>
				</div>
				<div class="kj_item">
					<div class="kj_icon" onclick="showModleTypeBtn('blylbx','办理养老保险')">
						<img src="plug-in/accordion/images/kj_04_bizshow.png" />
					</div>
					<div class="kj_title">办理养老保险</div>
				</div>
				<div class="kj_item">
					<div class="kj_icon" onclick="showModleTypeBtn('blylbx','办理医疗保险')">
						<img src="plug-in/accordion/images/kj_05_bizshow.png" />
					</div>
					<div class="kj_title">办理医疗保险</div>
				</div>
				<div class="kj_item">
					<div class="kj_icon" onclick="showModleTypeBtn('blzjsq','办理重疾申请')">
						<img src="plug-in/accordion/images/kj_06_bizshow.png" />
					</div>
					<div class="kj_title">办理大病救助</div>
				</div>
				<div class="kj_item">
					<div class="kj_icon" onclick="showModleTypeBtn('blzjysq','办理再就业申请')">
						<img src="plug-in/accordion/images/kj_07_bizshow.png" />
					</div>
					<div class="kj_title">办理再就业申请</div>
				</div>
				<div class="kj_item">
					<div class="kj_icon" onclick="showModleTypeBtn('blbz','办理殡葬')">
						<img src="plug-in/accordion/images/kj_08_bizshow.png" />
					</div>
					<div class="kj_title">办理殡葬</div>
				</div>
			</div>
		</div>
		<!--弹出二级列表start-->
		<div id="slt_box" class="slt_box">
			<div class="slt_header">
				<span class="slt_title">请选择办理类型</span> <span class="slt_close">X</span>
			</div>
			<div class="slt_content">
				<div class="kj_subitem">
					<div class="kj_subicon">
						<img src="plug-in/accordion/images/kj_sub_01_bizshow.png" />
					</div>
					<div class="kj_subtitle">证照验真</div>
				</div>
				<div class="kj_subitem">
					<div class="kj_subicon">
						<img src="plug-in/accordion/images/kj_sub_02_bizshow.png" />
					</div>
					<div class="kj_subtitle">出租户证明</div>
				</div>
				<div class="kj_subitem">
					<div class="kj_subicon">
						<img src="plug-in/accordion/images/kj_sub_03_bizshow.png" />
					</div>
					<div class="kj_subtitle">户口迁入证明</div>
				</div>
				<div class="kj_subitem">
					<div class="kj_subicon">
						<img src="plug-in/accordion/images/kj_sub_04_bizshow.png" />
					</div>
					<div class="kj_subtitle">婚育证明</div>
				</div>
				<div class="kj_subitem">
					<div class="kj_subicon">
						<img src="plug-in/accordion/images/kj_sub_05_bizshow.png" />
					</div>
					<div class="kj_subtitle">居住证明</div>
				</div>
				<div class="kj_subitem">
					<div class="kj_subicon">
						<img src="plug-in/accordion/images/kj_sub_06_bizshow.png" />
					</div>
					<div class="kj_subtitle">亲属关系证明</div>
				</div>
				<div class="kj_subitem">
					<div class="kj_subicon">
						<img src="plug-in/accordion/images/kj_sub_07_bizshow.png" />
					</div>
					<div class="kj_subtitle">申请办理残疾证明</div>
				</div>
				<div class="kj_subitem">
					<div class="kj_subicon">
						<img src="plug-in/accordion/images/kj_sub_08_bizshow.png" />
					</div>
					<div class="kj_subtitle">特殊情况</div>
				</div>
				<div class="kj_subitem">
					<div class="kj_subicon">
						<img src="plug-in/accordion/images/kj_sub_09_bizshow.png" />
					</div>
					<div class="kj_subtitle">一般补办独生子女证所需条件</div>
				</div>
				<div class="kj_subitem">
					<div class="kj_subicon">
						<img src="plug-in/accordion/images/kj_sub_10_bizshow.png" />
					</div>
					<div class="kj_subtitle">政策外怀孕人工终止妊娠证明</div>
				</div>
				<div class="kj_subitem">
					<div class="kj_subicon">
						<img src="plug-in/accordion/images/kj_sub_11_bizshow.png" />
					</div>
					<div class="kj_subtitle">独生子女证补证证明</div>
				</div>
			</div>
		</div>
		<!--弹出二级列表end-->
	</div>
	<!--遮罩层-->
	<div id="overlay"></div>



	<!-- 身份证信息，隐藏 -->
		<!-- 业务按钮显示区域 -->
	<div class="Information_div" style="display: none;">
			<%-- <t:formvalid layout="table" tiptype="4"
				action="scDocWarController.do?doAdd" formid="formobj" dialog="true"> --%>
				
				<form id="formobj" action="scDocWarController.do?doAdd" name="formobj" method="post">
				

				<div class="container form-card">
				<input type="hidden" id="btn_sub" class="btn_sub"/>
					<input id="id" name="id" type="hidden" value="${scDocWarPage.id }">
					<input id="createName" name="createName" type="hidden"
						value="${scDocWarPage.createName }"> <input id="createBy"
						name="createBy" type="hidden" value="${scDocWarPage.createBy }">
					<input id="createDate" name="createDate" type="hidden"
						value="${scDocWarPage.createDate }"> <input
						id="updateName" name="updateName" type="hidden"
						value="${scDocWarPage.updateName }"> <input id="updateBy"
						name="updateBy" type="hidden" value="${scDocWarPage.updateBy }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden"
						value="${scDocWarPage.sysOrgCode }"> <input
						id="updateDate" name="updateDate" type="hidden"
						value="${scDocWarPage.updateDate }"> <input id="delflag"
						name="delflag" type="hidden" value="${scDocWarPage.delflag }">
					<input id="versionNum" name="versionNum" type="hidden"
						value="${scDocWarPage.versionNum }"> <input id="docFile"
						name="docFile" type="hidden" style="width: 150px"
						value="${scDocWarPage.docFile }">
					<%-- <input id="ctreateTime" name="ctreateTime" class="form-control w260" type="hidden"   value="${scDocWarPage.ctreateTime }" class="Wdate" onClick="WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd HH:mm:ss'})"> --%>
					<input type="hidden" class="form-control w260" id="docName"
						name="docName" value="${scDocWarPage.docName }" /> <input
						type="hidden" class="form-control w260" id="docUrl" name="docUrl"
						value="${scDocWarPage.docUrl}">
					<div class="row">
						<div class="col-md-12">
							<div class="group-title">
								<label>办理人信息</label>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-md-12">
							<div class="form-group"
								style="float: left; margin-top: 101px !important; width: 94px;">
								<label class="control-label fl" for="name">办理人照片</label>
							</div>
							<div style="float: left;" class="fl">
								<div id='z_photo' class="fl pic-box" style="padding: 0px;">
									<img src="plug-in/chrhc/images/sfzmodel.png" width="180px"
										height="160px" /> <span class="span-bg span-see-single"><i
										class="pic-icon pic-see"></i></span>
								</div>
								<div style="float: left; margin-left: 80px;">
									<ul>


									</ul>
								</div>
								<!-- <div style="clear: both; padding-top: 10px">
									<button type="button" class="btn btn-sure"
										style="padding-left: 15px; padding-right: 15px;"
										onClick="ReadIDCARDInfoTx_Z()">
										<i class="gpyicon gpyicon-read"></i>&nbsp;读取办理人身份证
									</button>
								</div> -->
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<label class="control-label" for="docType">文档类型</label>
							<%--<input type="text" class="form-control w260" id="docType" name="docType" datatype="*1-20" ignore="ignore" > --%>
							<t:dictSelect field="docType" type="list" typeGroupCode="doctype"
								hasLabel="false" title="文档类型"></t:dictSelect>
							<span style="color: red;">*</span>
						</div>
					</div>

					<div class="row">
						<div class="col-md-6">
							<label class="control-label" for="name">姓名</label> <input
								id="name" name="name" type="text" style="width: 150px"
								class="form-control w260" datatype="s2-15" /> <span
								style="color: red;">*</span>
						</div>
						<div class="col-md-6">
							<label class="control-label" for="idcardnum">证件号码</label> <input
								type="text" class="form-control w260" id="idcardnum"
								name="idcardnum" datatype="s1-20" />
							<%--validType="sc_doc_war,idcardnum,id" --%>
							<span style="color: red;">*</span>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<label class="control-label" for="sex">性别</label>
							<%--<input type="text" class="form-control w260" id="sex" name="sex"readonly="readonly"datatype=""/> --%>
							<t:dictSelect field="sex" type="list" typeGroupCode="sex"
								hasLabel="false" title="性别">
							</t:dictSelect>
							<span style="color: red;">*</span>
						</div>
						<div class="col-md-6">
							<label class="control-label" for="birthday">出生日期</label> <input
								id="birthday" name="birthday" class="form-control w260"
								type="text" readonly="readonly" class="Wdate"
								onClick="WdatePicker({onpicked:function(){(this).blur();},})">
							<%--onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" --%>
						</div>
					</div>

					<div class="row">
						<div class="col-md-6">
							<label class="control-label" for="nation">民族</label>
							<%--<input type="text" class="form-control w260" id="nation" name="nation" readonly="readonly" datatype="" > --%>
							<t:dictSelect field="nation" type="list" typeGroupCode="mz"
								hasLabel="false" title="民族"></t:dictSelect>
							<span style="color: red;">*</span>
						</div>
						<div class="col-md-6">
							<label class="control-label" for="address">住址</label> <input
								type="text" class="form-control w260" id="address"
								name="address" datatype="*1-50" /> <span style="color: red;">*</span>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<label class="control-label" for="yxqstar">开始有效期</label>
							<%--<input type="text" class="form-control w260" class="Wdate" onClick="WdatePicker({onpicked:function(){(this).blur();},})"id="yxqstar" name="yxqstar" readonly="readonly"> --%>
							<input type="text" class="form-control w260" id="yxqstar"
								name="yxqstar" readonly="readonly" class="Wdate"
								onClick="WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd'})"
								datatype="*" ignore="ignore" Onchange="stardate(this)">
						</div>
						<div class="col-md-6">
							<label class="control-label" for="qfjg">签发机关</label> <input
								type="text" class="form-control w260" id="qfjg" name="qfjg"
								datatype="*1-50" ignore="ignore">
						</div>
					</div>
					<div class="row">

						<div class="col-md-6">
							<label class="control-label" for="yxqend">结束有效期</label> <input
								type="text" class="form-control w260 " class="Wdate" id="yxqend"
								name="yxqend" readonly="readonly"
								onClick="WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd',minDate:'1990-08-01'})"
								datatype="*" ignore="ignore" Onchange="enddate(this)" />
							<%--<input type="text"  class="form-control w260" class="Wdate" onClick="WdatePicker({onpicked:function(){(this).blur();},})" id="yxqend" name="yxqend" readonly="readonly"> ,startDate:'2015-01-01'--%>
						</div>
						<div class="col-md-6">
							<label class="control-label" for="bz">备注</label>
							<%--<input type="text" class="form-control w260" id="bz" name="bz"datatype="*1-200"  ignore="ignore" /> --%>

							<textarea rows="1" cols="20" class="form-control w260" id="bz"
								name="bz" datatype="*1-200" ignore="ignore"></textarea>
						</div>


					</div>
					<!-- 自定义附表 -->
					<div class="row">
						<div class="col-md-12">
							<div class="group-title">
								<label>附件信息</label>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12" id="f_table"></div>
					</div>
				</div>				
			<%-- </t:formvalid> --%>
			</form>
	</div>
	
    <%--高拍仪画面集成区域    长，宽都设置1px，相当于隐藏 
	<div class="container gpy-card" id="gaopaiyi"
		style="display: block; width: 1px; height: 1px;">
		<div class="row">
			<div class="col-md-12">
				<object classid="clsid:6CA705D0-BB6E-46DF-BE44-64809B0B0E36"
					id=VIDEOCAP CODEBASE="*.cab#version=0,0,0,0" width=1 height=1>
				</object>
			</div>
		</div>
		<div class="row" style="display: none;">
			<div class="col-md-12 center">

				<button class="btn btn-default" type="button"
					onClick="start_MainCampreview()"
					style="padding-top: 1px; padding-left: 3px; padding-right: 3px; font-size: 12px">
					<i class="gpyicon gpyicon-zsxt"></i>&nbsp;主摄像头
				</button>
				<button class="btn btn-default" type="button"
					onClick="start_SubCampreview()"
					style="padding-top: 1px; padding-left: 3px; padding-right: 3px; font-size: 12px">
					<i class="gpyicon gpyicon-fsxt"></i>&nbsp;副摄像头
				</button>
				<button class="btn btn-default" type="button" onClick="RotateLeft()"
					style="padding-top: 1px; padding-left: 3px; padding-right: 3px; font-size: 12px">
					<i class="gpyicon gpyicon-zx"></i>&nbsp;左旋&nbsp;
				</button>
				<button class="btn btn-default" type="button"
					onClick="RotateRight()"
					style="padding-top: 1px; padding-left: 3px; padding-right: 3px; font-size: 12px">
					<i class="gpyicon gpyicon-yx"></i>&nbsp;右旋&nbsp;
				</button>--%>
				<%--  <button class="btn btn-default" type="button" onClick="ZoomIn()" style="padding-left:3px">
						<i class="gpyicon gpyicon-fd"></i>&nbsp;放大
					</button>
					<button class="btn btn-default" type="button" onClick="ZoomOut()" style="padding-left:3px">
						<i class="gpyicon gpyicon-sx"></i>&nbsp;缩小
					</button>--%>
        <%--
			</div>
		</div>
		<div class="row" style="display: none;">
			<div class="col-md-12 center">
				<button type="button" class="btn btn-sure" id="freadcard"
					style="padding-left: 15px; padding-right: 15px;">
					<i class="gpyicon gpyicon-read"></i>&nbsp;读取其他身份证
				</button>
				<button type="button" class="btn btn-blue" id="Takepic"
					style="padding-left: 15px; padding-right: 15px;">
					<i class="gpyicon gpyicon-photograph"></i>&nbsp;拍照
				</button>
			</div>
		</div>
	</div>
    --%>
	<!-- 身份证信息与人口库信息对比 -->
	<div id="compareInfoDiv" style="display: none;">
			<div class="form-card">
				<div class="row">
					<div class="col-md-6">
						<label
							style="width: 250px;text-align: right; font-size:19px;">身份证信息</label>
					</div>
					<div class="col-md-6">
						<label
							style="width: 250px;text-align: right;font-size:19px;">人口库中身份信息</label>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<label class="control-label" for="new_name">姓名</label> <input
							id="new_name" type="text" style="width: 150px"
							class="form-control w260" />
					</div>
					<div class="col-md-6">
						<label class="control-label" for="rk_name">姓名</label> <input
							id="rk_name" type="text" style="width: 150px"
							class="form-control w260" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<label class="control-label" for="new_idcardnum">证件号码</label> <input
							id="new_idcardnum" type="text" class="form-control w260" value="" />
					</div>
					<div class="col-md-6">
						<label class="control-label" for="rk_idcardnum">证件号码</label> <input
							id="rk_idcardnum" type="text" class="form-control w260" value="" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<label class="control-label" for="new_sex">性别</label>
						<t:dictSelect field="sex" type="list" id="new_sex"
							typeGroupCode="sex" hasLabel="false" title="性别">
						</t:dictSelect>
					</div>

					<div class="col-md-6">
						<label class="control-label" for="rk_sex">性别</label>
						<t:dictSelect field="sex" type="list" id="rk_sex"
							typeGroupCode="sex" hasLabel="false" title="性别">
						</t:dictSelect>
					</div>

				</div>
				<div class="row">
					<div class="col-md-6">
						<label class="control-label" for="new_birthday">出生日期</label> <input
							id="new_birthday" class="form-control w260" class="Wdate"
							onClick="WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd'})"
							type="text" readonly="readonly">
					</div>

					<div class="col-md-6">
						<label class="control-label" for="rk_birthday">出生日期</label> <input
							id="rk_birthday" class="form-control w260" class="Wdate"
							onClick="WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd'})"
							type="text" readonly="readonly">
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<label class="control-label" for="new_nation">民族</label>
						<t:dictSelect field="nation" type="list" id="new_nation"
							typeGroupCode="mz" hasLabel="false" title="民族"></t:dictSelect>

					</div>

					<div class="col-md-6">
						<label class="control-label" for="rk_nation">民族</label>
						<t:dictSelect field="nation" type="list" id="rk_nation"
							typeGroupCode="mz" hasLabel="false" title="民族"></t:dictSelect>

					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<label class="control-label" for="new_address">住址</label> <input
							id="new_address" type="text" class="form-control w260" />
					</div>

					<div class="col-md-6">
						<label class="control-label" for="rk_address">住址</label> <input
							id="rk_address" type="text" class="form-control w260" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<label class="control-label" for="new_yxqstar">开始有效期</label> <input
							id="new_yxqstar" type="text" class="form-control w260"
							onClick="WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd'})"
							readonly="readonly" class="Wdate" />
					</div>

					<div class="col-md-6">
						<label class="control-label" for="rk_yxqstar">开始有效期</label> <input
							id="rk_yxqstar" type="text" class="form-control w260"
							onClick="WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd'})"
							readonly="readonly" class="Wdate" />
					</div>
				</div>
				<div class="row">

					<div class="col-md-6">
						<label class="control-label" for="new_yxqend">结束有效期</label> <input
							id="new_yxqend" type="text" class="form-control w260"
							class="Wdate"
							onClick="WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd'})" />
					</div>

					<div class="col-md-6">
						<label class="control-label" for="rk_yxqend">结束有效期</label> <input
							id="rk_yxqend" type="text" class="form-control w260"
							class="Wdate"
							onClick="WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd'})" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<label class="control-label" for="new_qfjg">签发机关</label> <input
							id="new_qfjg" type="text" class="form-control w260">
					</div>

					<div class="col-md-6">
						<label class="control-label" for="rk_qfjg">签发机关</label> <input
							id="rk_qfjg" type="text" class="form-control w260">
					</div>
				</div>

			</div>
	</div>

	<%-- 上传控件  --%>
	<OBJECT ID="uploadFile1" WIDTH=0 HEIGHT=0
		CLASSID="CLSID:3F5ADE93-9B79-46A3-88A2-D2ED39682BF2">
		<PARAM NAME="_Version" VALUE="65536">
		<PARAM NAME="_ExtentX" VALUE="2646">
		<PARAM NAME="_ExtentY" VALUE="1323">
		<PARAM NAME="_StockProps" VALUE="0">
		<PARAM NAME="showMsg" VALUE="1">
	</OBJECT>
</body>
<script src="webpage/com/chrhc/project/sc/quick/scQuickBusiness.js"></script>
<script src="plug-in/media/jquery.mCustomScrollbar/js/jquery.mCustomScrollbar.concat.min.js" type="text/javascript"></script>


    <script type="text/javascript">
      var donghua01,donghua02;
      
      $(document).ready(function(){
    	try{ 
			//判断是否安装控件
			pageinit();
			// 高拍仪初始化
			gpyinit();

		}catch(e){
			$("#gaopaiyi").remove();
			$("#uploadFile1").remove();
		}

    	  function moveDown() {
              $(".h").animate({top:"375px"},3000);
               try{ 
            	  if(!gpy_link()){
                	  ReadIDCARDInfoTx_Z();
                  } 
               }catch(e){
                   //alert(e);
              } 
                                          
            }
            function moveUp() {
              $(".h").animate({top:"10px"},3000);
              try{
            	  if(!gpy_link()){
                	  ReadIDCARDInfoTx_Z();
                  } 
               }catch(e){
            	   //alert(e);
              }     
            }
            donghua01 = setInterval(function(){ moveDown();},1000);
            donghua02 = setInterval(function(){ moveUp();},1000);
      });

          $(document).ready(
				function() {
					$(".kj_icon,.kj_subicon").mouseover(
							function() {
								var uurl = $(this).find("img")
										.attr("src");
								var hz = /\.[^\.]+/.exec(uurl);
								uurl = uurl.replace("show.png", "hide.png");
								uurl = uurl.replace(hz, "");
								
								//$(this).find("img").attr("src",
								//		uurl + "over" + hz);
								$(this).find("img").attr("src",
										uurl + hz);
							});

					$(".kj_icon,.kj_subicon").mouseout(function() {
						var uurl = $(this).find("img").attr("src");
						var hz = /\.[^\.]+/.exec(uurl);
						//uurl = uurl.replace("over.png", "");
						uurl = uurl.replace("hide.png", "show");
						$(this).find("img").attr("src", uurl + hz);
					});

					//点击一级项目展示二级项目
					// $(".kj_icon").click(function() {
					//	showOverlay();
					//	adjust("slt_box");
					//	$(".slt_box").slideDown();
					//}); 

					$(".slt_close").click(function() {
						$(".slt_box").slideUp();
						hideOverlay();
					});

					//showOverlay();
					//adjust("slt_box");
					$(window).load(function() {
						$(".kj_gundong").mCustomScrollbar({
							axis : "x",
							theme : "3d"//可以在这里修改主题
						});
					});
		}); 

        //*******************开始加载高拍仪相关*******************************
			//增加时的拍照次数   用于记录每条附件数据的序号，以及图片生成序号（）
			var photoindext = 1;
			var picpathall = "";
			var tempdir = "";//getTempDir()

			$(document).ready(function() {

				//********************初始化样式开始************************************
				/* $("#formobj").attr("class", "form-inline valideForm");
				$("select[name='docType']").attr("class",
						"form-control w260");
				$("select[name='docType']").attr("datatype", "*");
				$("select[name='nation']").attr("class",
						"form-control w260");
				$("select[name='nation']").attr("datatype", "*"); */

				var mySelect = document.getElementsByName('docType')[0];
				//设置身份证默认选中
				mySelect.selectedIndex = 1;
				$("select[name!=docType]").attr("disabled", true);
				$("input[name!=bz]").attr("disabled", true);

				//$("select[name='nation']").attr("readonly","readonly"); 
				//$("select[name='sex']").attr("class", "form-control w260");
				//$("select[name='sex']").attr("readonly","readonly"); 
				//$("select[name='sex']").attr("datatype", "*");
				//$("select[name='sex']").find("option[index=1]").attr("selected",true); NG
				mySelect = document.getElementsByName('sex')[0];
				mySelect.selectedIndex = 1;//默认选中第一项
				mySelect = document.getElementsByName('nation')[0];
				mySelect.selectedIndex = 1;//默认选中第一项
				//********************初始化样式结束************************************
			});
	  //图片鼠标放上或者离开图片变化效果
      function mouseOverAndOut(){
    	  $(".kj_icon,.kj_subicon").mouseover(
					function() {
						var uurl = $(this).find("img").attr("src");
						 var urlHide=window.top.getUrlbyType(uurl,"bizhide");
						 $(this).find("img").attr("src",urlHide);
					});

			$(".kj_icon,.kj_subicon").mouseout(function() {
				var uurl = $(this).find("img").attr("src");
				var urlShow=window.top.getUrlbyType(uurl,"bizshow");
				$(this).find("img").attr("src",urlShow);
			});
      }
      /* 显示遮罩层 */
      function showOverlay() {
          $("#overlay").height(pageHeight());
          $("#overlay").width(pageWidth());
          //alert(pageHeight());
          // fadeTo第一个参数为速度，第二个为透明度
          // 多重方式控制透明度，保证兼容性，但也带来修改麻烦的问题
          $("#overlay").fadeTo(200, 0.5);
      }

      /* 隐藏覆盖层 */
      function hideOverlay() {
          $("#overlay").fadeOut(200);
      }

      /* 当前页面高度 */
      function pageHeight() {
          //return document.body.scrollHeight;
          return window.scrollHeight;
      }

      /* 当前页面宽度 */
      function pageWidth() {
          return document.body.scrollWidth;
      }

      /* 定位到页面中心 */
        function adjust(id) {
            var w = $(document.getElementById(id)).width();
            var h = $(document.getElementById(id)).height();
            var t = scrollY() + (windowHeight()/2) - (h/2);
            if(t < 0) t = 0;
            
            var l = scrollX() + (windowWidth()/2) - (w/2);
            if(l < 0) l = 0;
            $(document.getElementById(id)).css({left: l+'px', top: t+'px'});
        }

        //浏览器视口的高度
        function windowHeight() {
            var de = document.documentElement;
            return self.innerHeight || (de && de.clientHeight) || document.body.clientHeight;
        }

        //浏览器视口的宽度
        function windowWidth() {
            var de = document.documentElement;
            return self.innerWidth || (de && de.clientWidth) || document.body.clientWidth
        }

        /* 浏览器垂直滚动位置 */
        function scrollY() {
            var de = document.documentElement;
            return self.pageYOffset || (de && de.scrollTop) || document.body.scrollTop;
        }

        /* 浏览器水平滚动位置 */
        function scrollX() {
            var de = document.documentElement;
            return self.pageXOffset || (de && de.scrollLeft) || document.body.scrollLeft;
        }
				
					//文件上传
					function uploadcut() {
						// 设置disable=true 时提交可以上传值
						$("select").attr("disabled", false);
						$("input").attr("disabled", false);
						// 证件有效期验证
						var endyxq_z = $("#yxqend").val();
						if (comp2Nowtime_sub(endyxq_z)) {
							$.dialog({
								id : 'test31',
								time : 3,
								title : '提示',
								icon : 'error',
								content : '证件已出有效期，不能办理业务!'
							});
							return;
						}
						if (picpathall.length > 5) {

							var url = "";
							url = "<%=basePath_java%>"+ "scDocWarController.do;jsessionid=<%=session.getId()%>?addfiles";
							var sessionid = "<%=session.getId()%>";
							picpathall = picpathall.substring(0,
									picpathall.length - 1);
							var msg = document.getElementById("uploadFile1")
							.Upload(url, sessionid, picpathall);
							//debugger;
					//alert(msg);
					var jsonObj =eval("("+msg +")");			
					var f1Str = jsonObj.F1;
					var zStr = jsonObj.Z;
					var successflag = jsonObj.success;					
					//debugger;
					if (successflag) {
						if(f1Str){
							$("input[name='scFileList[1].photo']").val(f1Str);
						}
						if(zStr){
							$("input[name='docFile']").val(zStr);
						}						
						// 把上传地址赋空
						picpathall = "";
						//$("#formobj").submit();
						submitForm();
							} else {
								$.dialog({
									id : 'test31',
									time : 5,
									title : '提示',
									icon : 'error',
									content : '操作失败，请重新操作!'
								});
							}
						} else {
							//$("#formobj").submit();
							submitForm();
						}
		}
	//手动提交form
	function submitForm(){
		$.ajax({  
            type: "post",  
            url: "scDocWarController.do?doAdd",       
            data: $("#formobj").serialize(),      
            success: function(data) {  
                //alert("添加成功！"+data);
                uploadFile(data);  
            },  
            error: function(data) {  
                alert(data);  
            }  
        });  
	}
	var neibuClickFlag = false;
	function uploadFile(data) {
		//alert('2222222');
		//alert(data);
		var jsonObj =eval("("+data +")");
		//alert(jsonObj.obj.id);
		if (!$("input[name='id']").val()) {
			$("input[name='id']").val(jsonObj.obj.id);
			var docWarId = '';
			if ("inputCertInfo" == fromFun) {
				//输入电子文档后，输入录入人口库信息
				docWarId = jsonObj.obj.id;
			} else if ("updateCertInfo" == fromFun) {
				//输入电子文档后，更新人口库信息
				docWarId = jsonObj.obj.id;
			}
			//alert("docWarId="+docWarId);
			var host = window.location.host;
			var pathname = window.location.pathname;
			var a = pathname.split('/');
			//.join(',');
			var url = "http://"
					+ host
					+ "/"
					+ a[1]
					+ "/chrhcFormBuildController.do?ftlForm&tableName=sc_rkjbxxnew&bizType=&dataRule=null&docWarId="
					+ docWarId;
			//alert(url);
			if (rk_id) {
				url = url + "&id=" + rk_id;
			}
			//alert(url);
			//addOneTab("人口信息", url, "");
			parent.addTab("人口信息", url);
		}

		if ($(".uploadify-queue-item").length > 0) {
			upload();
		} else {
			if (neibuClickFlag) {
				neibuClickFlag = false;
			} else {
				var win = getParentWindow();
				win.reloadTable(data);
				//win.tip(data.msg);
				if (frameElement.api) {
					frameElement.api.close();
				} else {
					//closeCurrentTab();
				}
			}
		}
	}
	</script>


</html>




