<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title>答复信息查看</title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinecss.jsp"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejs.jsp"%>
<script
	src="${ctx}/resources/general/common/jquery/jquery.fileDownload.js"
	type="text/javascript"></script>
<script
	src="${ctx}/resources/general/common/uploadify/jquery.uploadify-3.1.min.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/general/common/uploadify/swfobject.js"
	type="text/javascript"></script>
<script
	src="${ctx}/resources/general/common/flexpaper/flexpaper_flash.js"
	type="text/javascript"></script>

<script type="text/javascript">

</script>
<style>
#ywslOrderDiv a{color: #339100;}
</style>
</head>
<body>
	<div class="col-md-12">
		<div class="row">
			<div class="grid simple">
				<div class="grid-title">
					<h4>请填信息:</h4>
					<div class="tools">
						<a class="reload-2" href="#grid-config"></a>
					</div>
				</div>
				<form name="inputForm" id="inputForm" method="post">
                    <div class="col-md-12 m-t-30">
						<div class="information-form">

							<!-- 隐藏字段 -->							
							<input type="hidden" name="id" id="id" value="${channelRecord.id}"></input>
							<input type="hidden" name="createUserId"
								value="${channelRecord.createUserId}" /> <input type="hidden"
								name="createDatetime" value="${channelRecord.createDatetime}" /> <input
								type="hidden" name="lastUpdateUserId"
								value="${channelRecord.lastUpdateUserId}" /> <input type="hidden"
								name="lastUpdateDatetime" value="${channelRecord.lastUpdateDatetime}" />
							 <input type="hidden"
								name="type" value="${channelRecord.type}" />	
									
							<div class="col-md-4 ie8correct">
								<label class="field-title" for="name">姓名：</label> <input
									type="text" name="name" id="name" 
									value="${channelRecord.name}" required maxlength="50" readonly="readonly">
							</div>
							<div class="col-md-4 ie8correct">
								<label class="field-title" for="gender">性别：</label>
								<chr:Dict categoryCode="genderType" name="gender"
									id="gender" value="${channelRecord.gender}" isAll="true" js="disabled='disabled'" >
								</chr:Dict>
							</div>
							<div class="col-md-4 ie8correct">
								<label class="field-title" for="mobilePhone">手机号：</label>							
								<input type="text" name="mobilePhone" id="mobilePhone" value="${channelRecord.mobilePhone}" readonly="readonly" required data-rule-iphone6>								
							</div>

							<div class="col-md-4 ie8correct">
								<label class="field-title" for="address">地址：</label>
								<input type="text" name="address" id="address" maxlength="200" readonly="readonly" value="${channelRecord.address}">								
							</div>

							<div class="col-md-4 ie8correct">
								<label class="field-title" for="telephone">电话：</label>
								<input type="text" name="telephone" id="telephone" value="${channelRecord.telephone}" readonly="readonly" data-rule-iphone6>
							</div>
							<div class="col-md-4 ie8correct">
								<label class="field-title" for="email">Email：</label> <input
									type="text" name="email" id="email" value="${channelRecord.email}" readonly="readonly"
									data-rule-email="true" />
							</div>							
							
							<div class="col-md-4 ie8correct">
								<label class="field-title" for="webpageType">类型：</label>
								<chr:Dict categoryCode="orderType" name="webpageType"
									id="webpageType" value="${channelRecord.webpageType}" isAll="true" js="disabled='disabled'">
								</chr:Dict>
							</div>
							
							<div class="col-md-4 ie8correct">
				                    <label class="field-title" for="gender">是否公开：</label>
				                   	<chr:Dict categoryCode="isPublic" name="isPublic"
										id="isPublic" value="${channelRecord.isPublic}" isAll="true" js="disabled='disabled'">
									</chr:Dict>
				            </div>	
							
							<div class="col-md-4 ie8correct">
								<label class="field-title" for="searchCode">查询码(10位)：</label>
								<input type="text" name="searchCode" id="searchCode" maxlength="10" value="${channelRecord.searchCode}" readonly="readonly">								
							</div>
							
							<div class="col-md-8">
								<label class="field-title" for="subject">主题：</label> <input
									type="text" id="subject" name="subject" 
									value="${channelRecord.subject}"  placeholder="请输入主题......"  required maxlength="200" readonly="readonly"/>
							</div>

							<div class="col-md-8 ie8correct1">
								<label class="field-title" for="content">内容：</label>
								<textarea rows="5" id="content" name="content" maxlength="500" readonly="readonly" required>${channelRecord.content}</textarea>
							</div>
							
							<div class="col-md-12">
								<!--上传附件相关功能  -->
								<div id="infileDiv" class="row">
									<div class="col-md-6 ie8correct attachment">
										<input type='file' id='infile' name='infile' />
									</div>
									<div class="col-md-4 ie8correct">
										<input type="hidden" name="attachids" id="attachids"
											value="${attachids}" />
									</div>
								</div>
								<div id="fileTableDiv" class="row">
									<div class="col-md-8 ie8correct">
										<table id="uploadfileList" class="table table-bordered table-hover table-fixed-layout">
										</table>
									</div>
								</div>
							
								<c:if test="${not empty attachmentList}">
									<span id="attachmentSpan" style="">附件：</span>
									<div id="attachmentListDiv">
										<c:forEach var="attachment" items="${attachmentList}">
										<div class="attachment_box">
										<c:if test="${fn:toLowerCase(attachment.fileExt) == '.jpg' or fn:toLowerCase(attachment.fileExt) == '.jpeg' or fn:toLowerCase(attachment.fileExt) == '.png'}">
										<img id="att_${attachment.id}" class="file-img" src="${ctx}/uploadAction/showImage/${attachment.url}" title="点击下载文件" onclick="downloadAtta('${attachment.id}') " style="cursor:pointer; ">
										</c:if>
										<c:if test="${fn:toLowerCase(attachment.fileExt) == '.doc' or fn:toLowerCase(attachment.fileExt) == '.pdf' or fn:toLowerCase(attachment.fileExt) == '.xls' or 
										fn:toLowerCase(attachment.fileExt) == '.txt' or fn:toLowerCase(attachment.fileExt) == '.docx' or fn:toLowerCase(attachment.fileExt) == '.xlsx'}">
										<img id="att_${attachment.id}" class="file-img" src="${ctx}/resources/themes/hotline/common/images/download.jpg" title="点击下载文件" onclick="downloadAtta('${attachment.id}') " style="cursor:pointer; ">
										</c:if>
											<%-- <a id="att_down_${attachment.id}"
												onclick="downloadAtta('${attachment.id}')"><i
												class='fa fa-download green'></i><span>下载</span></a>
											<a id="att_del_${attachment.id}"
												onclick="deleteAtta('${attachment.id}')"><i
												class='fa fa-trash-o green'></i><span>删除</span></a> --%>
												<a id="att_del_${attachment.id}"
												onclick="deleteAtta('${attachment.id}')" class="delete" style="cursor:pointer; "><i
												class='fa fa-trash-o'></i><span>删除</span></a>
												<a  id="att_down_${attachment.id}" onclick="downloadAtta('${attachment.id}') " style="cursor:pointer; " title="点击下载文件">${attachment.fileName}</a>
												</div>
										</c:forEach>
									</div>
								</c:if>
							</div>
							<div class="col-md-8 ie8correct1">
								<label class="field-title" for="replyContent">处理单位答复内容：</label>
								<textarea rows="5" id="replyContent" name="replyContent" maxlength="500" required>${channelRecord.replyContent}</textarea>
							</div>
						</div>
						</div>
				</form>
				
			</div>
		</div>
	</div>
				
					
				

	
	
	<script type="text/javascript">
		$(document).ready(				
				function() {	
					var is = $("#isPublic").val();
					if(is == '0'){
						$("#inputForm label[for='searchCode']")
						.replaceWith(
								"<label class='field-title' for='searchCode'><span style='color: red;'>*</span>查询码(10位)：</label>");
						$("#searchCode")
						.replaceWith(
								"<input type='text' name='searchCode' id='searchCode' maxlength='10' value='${channelRecord.searchCode}' required data-rule-searchCode/>");
					}			
		});		
		/**
		 * 加载页面时，加载上传附件功能
		 */
		$(document)
				.ready(
						function() {
							var fls = flashChecker();
							if (!fls.f) {
								$("#infile")
										.before(
												"<font color='red'>没有安装Flash Palyer，上传附件功能不能正常使用</font");
								$("#infile").hide();
							} else {
								try {
									if ($('#infile').length > 0) {
										$('#infile').uploadify('destroy');
									}
								} catch (e) {
								}
								$("#infile")
										.uploadify(
												{
													'swf' : '${ctx}/resources/general/common/uploadify/uploadify.swf?ver='
															+ Math.random(),
													'uploader' : '${ctx}/uploadAction/upload?attachtype=business',
													'cancelImg' : '${ctx}/resources/general/common/uploadify/uploadify-cancel.png',
													'fileObjName' : 'infile',
													'expressInstall' : 'expressInstall.swf',
													//在浏览窗口底部的文件类型下拉菜单中显示的文本
													'fileTypeDesc' : '支持的格式：doc,docx,xls,xlsx,pdf,txt,jpg,png,jpeg',
													//允许上传的文件后缀
													'fileTypeExts' : '*.doc;*.docx;*.xls;*.xlsx;*.pdf;*.txt;*.jpg;*.png;*.jpeg',
													//上传文件的大小限制
													'fileSizeLimit' : '20MB',
													'hideButton' : true,
													'auto' : true,
													'multi' : false,
													'height' : "30",
													'width' : "90",
													onUploadStart : function(
															file) {
														var leng = file.name.length;
														if (leng > 200) {
															art
																	.dialog({
																		title : '上传提示',
																		icon : 'error',
																		lock : false,
																		content : '上传失败，文件名长度需短于200字符，2秒后会自动关闭……',
																		time : 2
																	});
															stop();
														}

													},
													onUploadSuccess : function(
															file, data,
															response) {
														var fileDOM = "<tr id='datalist' name='file_"+data+"'><td width='400px' clospan="+3+" title='"+file.name+"'>"
																+ file.name
																+ "</td>"
																+ "<td width='200px'>"
																+ "<a id='"+data+"' name='deletefile' href='javascript:return false'><i class='fa fa-trash-o green'></i><span>删除</span></a>&nbsp;&nbsp;&nbsp;"
																+ "<a id='"+data+"' name='downloadfile' href='javascript:return false'><i class='fa fa-download green'></i><span>下载</span> </a>&nbsp;&nbsp;&nbsp;"
																+
																/* "<a id='"+data+"' name='previewAttach' onclick=previewAttachById('"+data +"') href='javascript:return false'> 预览 </a>" + */
																"</td></tr>";
														//添加文件列表信息
														$("#uploadfileList")
																.append(fileDOM);
														//控制提交附件按钮的显示，当有附件时，显示该按钮。
														/* $("#uploadSubmitBtn").css(
																'display', ''); */
														//绑定删除事件
														$(
																"#uploadfileList a[name='deletefile']")
																.unbind();
														$(
																"#uploadfileList a[name='deletefile']")
																.bind(
																		"click",
																		function() {
																			//实时获取附件id，并发送ajax请求删除附件
																			var attachId = $(
																					this)
																					.attr(
																							"id");
																			var d = art
																					.dialog({
																						title : '删除提示',
																						icon : 'delete',
																						lock : true,
																						content : '确定要删除吗？ ',
																						ok : function() {
																							$
																									.ajax({
																										type : "get",
																										url : "${ctx}/uploadAction/deleteById/"
																												+ attachId,
																										success : function(
																												data) {
																											var resultJson = eval('('
																													+ data
																													+ ')');
																											if (resultJson.result == "0") {
																												$(
																														"#uploadfileList tr[name=file_"
																																+ attachId
																																+ "]")
																														.remove();
																												art
																														.dialog({
																															title : '删除提示',
																															icon : 'succeed',
																															lock : false,
																															content : '删除成功，2秒后会自动关闭……',
																															time : 2
																														});
																											}
																										},
																										error : function(
																												data) {
																											art
																													.dialog({
																														title : '删除提示',
																														icon : 'error',
																														lock : false,
																														content : '删除失败，2秒后会自动关闭……',
																														time : 2
																													});
																										}
																									});
																						},
																						cancel : true
																					});
																		});

														$(
																"#uploadfileList a[name='downloadfile']")
																.unbind();
														$(
																"#uploadfileList a[name='downloadfile']")
																.bind(
																		"click",
																		function() {
																			//实时获取附件id，并发送ajax请求下载附件
																			var attachId = $(
																					this)
																					.attr(
																							"id");
																			$
																					.fileDownload(
																							"${ctx}/uploadAction/downloadById/"
																									+ attachId,
																							{
																								httpMethod : "GET",
																							})
																					.done(
																							function() {
																								art
																										.dialog({
																											title : '下载提示',
																											icon : 'succeed',
																											lock : false,
																											content : '下载成功，2秒后会自动关闭……',
																											time : 2
																										});
																							})
																					.fail(
																							function() {
																								art
																										.dialog({
																											title : '下载提示',
																											icon : 'error',
																											lock : false,
																											content : '下载失败，2秒后会自动关闭……',
																											time : 2
																										});
																							});
																		});

													}.bind(this),

													'buttonText' : '选择附件'
												});
							}
						});


		/**
		 * 上传附件提交，保存工单与附件的关系
		 */
		function uploadSubmit(id) {
			var attachids = "";
			$("#uploadfileList tr[name^='file_']").each(function(index, dom) {
				if (index > 0) {
					attachids += ";";
				}
				attachids += String($(dom).attr("name")).substring(5);
			});
			if (attachids) {
				$("#attachids").val(attachids);
				//提交请求数据
				$.ajax({
					type : "GET",
					url : "${ctx}/channelrecord/wp/uploadSubmit",
					async : false,
					data : {recordId:id,attachids:attachids},
					success : function(result) {
						var resultJson = eval("(" + result + ")");
						if (resultJson.result == "success") {
						} else {
							art.dialog({
								title : '上传提示',
								icon : 'error',
								lock : false,
								content : '上传失败，2秒后会自动关闭……',
								time : 2
							});
						}
					}		
				});
			} 
		}
		//提交保存数据
		 function webpageSubmit() {	
			 		
				if ($("#inputForm").valid()) {	
					$.ajax({
						type : "post",
						url : "${ctx}/channelrecord/wp/saveWebPage",
						data : $("form").serialize(),
						async : false,
						success : function(data) {
							art.dialog({title: '提交提示',icon: 'succeed',lock: false,content: '提交成功，2秒后会自动关闭……',time:2});
							var resultJson = eval("(" + data + ")");
							if (resultJson.result == "success") {
								uploadSubmit(resultJson.id);
							} 
							
							uiBase.refreshParentTab();
							uiBase.closeCurrentTab();
						}
					});			
				}	
				else{
					art.dialog({title: '提交提示',icon: 'error',lock: false,content: '验证未通过，2秒后会自动关闭……',time:2});
				}
			}
		 	/**
			 * 检测是否安装 flashplayer  否则上传附件显示不正常
			 */
			function flashChecker() {
				var hasFlash = 0; //是否安装了flash 
				var flashVersion = 0; //flash版本 
				if (document.all) {
					var swf = null;
					try {
						swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
					} catch (e) {

					}

					if (swf) {
						hasFlash = 1;
						VSwf = swf.GetVariable("$version");
						flashVersion = parseInt(VSwf.split(" ")[1].split(",")[0]);
					}
				} else {
					if (navigator.plugins && navigator.plugins.length > 0) {
						var swf = navigator.plugins["Shockwave Flash"];
						if (swf) {
							hasFlash = 1;
							var words = swf.description.split(" ");
							for ( var i = 0; i < words.length; ++i) {
								if (isNaN(parseInt(words[i])))
									continue;
								flashVersion = parseInt(words[i]);
							}
						}
					}
				}
				return {
					f : hasFlash,
					v : flashVersion
				};
			}
	</script>
</body>
</html>