<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('#addScFileBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delScFileBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addScFileBtn').bind('click', function(){   
 		 var tr =  $("#add_scFile_table_template tr").clone();
	 	 $("#add_scFile_table").append(tr);
	 	 resetTrNum('add_scFile_table');
	 	 return false;
    });  
	$('#delScFileBtn').bind('click', function(){   
      	$("#add_scFile_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_scFile_table'); 
        return false;
    }); 
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#scFile_table").createhftable({
	    	height:'300px',
			width:'auto',
			fixFooter:false
			});
    });
</script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addScFileBtn" href="#">添加</a> <a id="delScFileBtn" href="#">删除</a> 
</div>
<table border="0" cellpadding="2" cellspacing="0" id="scFile_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="center" bgcolor="#EEEEEE">操作</td>
				  <td align="left" bgcolor="#EEEEEE">
						姓名
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						身份证号
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						性别
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						出生日期
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						民族
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						住址
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						身份证照片
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						图片
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						视频
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						音频
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						文档
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						指纹
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						备注
				  </td>
	</tr>
	<tbody id="add_scFile_table">	
	<c:if test="${fn:length(scFileList)  <= 0 }">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">1</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>
					<input name="scFileList[0].id" type="hidden"/>
					<input name="scFileList[0].createName" type="hidden"/>
					<input name="scFileList[0].createBy" type="hidden"/>
					<input name="scFileList[0].createDate" type="hidden"/>
					<input name="scFileList[0].updateName" type="hidden"/>
					<input name="scFileList[0].updateBy" type="hidden"/>
					<input name="scFileList[0].updateDate" type="hidden"/>
					<input name="scFileList[0].docForeignId" type="hidden"/>
					<input name="scFileList[0].delflag" type="hidden"/>
				  <td align="left">
					  	<input name="scFileList[0].name" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">姓名</label>
					</td>
				  <td align="left">
					  	<input name="scFileList[0].idcardNum" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">身份证号</label>
					</td>
				  <td align="left">
					  	<input name="scFileList[0].sex" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">性别</label>
					</td>
				  <td align="left">
					  	<input name="scFileList[0].birthday" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">出生日期</label>
					</td>
				  <td align="left">
					  	<input name="scFileList[0].nation" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">民族</label>
					</td>
				  <td align="left">
					  	<input name="scFileList[0].address" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">住址</label>
					</td>
				  <td align="left">
							<input type="hidden" id="scFileList[0].idcardPhoto" name="scFileList[0].idcardPhoto" />
										<a  target="_blank" id="scFileList[0].idcardPhoto_href">未上传</a>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'scFileList\\[0\\]\\.idcardPhoto')"/> 
					  <label class="Validform_label" style="display: none;">身份证照片</label>
					</td>
				  <td align="left">
							<input type="hidden" id="scFileList[0].photo" name="scFileList[0].photo" />
										<a  target="_blank" id="scFileList[0].photo_href">未上传</a>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'scFileList\\[0\\]\\.photo')"/> 
					  <label class="Validform_label" style="display: none;">图片</label>
					</td>
				  <td align="left">
							<input type="hidden" id="scFileList[0].video" name="scFileList[0].video" />
										<a  target="_blank" id="scFileList[0].video_href">未上传</a>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'scFileList\\[0\\]\\.video')"/> 
					  <label class="Validform_label" style="display: none;">视频</label>
					</td>
				  <td align="left">
							<input type="hidden" id="scFileList[0].mp3" name="scFileList[0].mp3" />
										<a  target="_blank" id="scFileList[0].mp3_href">未上传</a>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'scFileList\\[0\\]\\.mp3')"/> 
					  <label class="Validform_label" style="display: none;">音频</label>
					</td>
				  <td align="left">
							<input type="hidden" id="scFileList[0].file" name="scFileList[0].file" />
										<a  target="_blank" id="scFileList[0].file_href">未上传</a>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'scFileList\\[0\\]\\.file')"/> 
					  <label class="Validform_label" style="display: none;">文档</label>
					</td>
				  <td align="left">
							<input type="hidden" id="scFileList[0].fingerprint" name="scFileList[0].fingerprint" />
										<a  target="_blank" id="scFileList[0].fingerprint_href">未上传</a>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'scFileList\\[0\\]\\.fingerprint')"/> 
					  <label class="Validform_label" style="display: none;">指纹</label>
					</td>
				  <td align="left">
					       	<input name="scFileList[0].remark" maxlength="500" 
						  		type="text" class="inputxt"  style="width:120px;"
					               
					                >
					  <label class="Validform_label" style="display: none;">备注</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(scFileList)  > 0 }">
		<c:forEach items="${scFileList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>
					<input name="scFileList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="scFileList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="scFileList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="scFileList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="scFileList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="scFileList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="scFileList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="scFileList[${stuts.index }].docForeignId" type="hidden" value="${poVal.docForeignId }"/>
					<input name="scFileList[${stuts.index }].delflag" type="hidden" value="${poVal.delflag }"/>
				   <td align="left">
					  	<input name="scFileList[${stuts.index }].name" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					                value="${poVal.name }">
					  <label class="Validform_label" style="display: none;">姓名</label>
				   </td>
				   <td align="left">
					  	<input name="scFileList[${stuts.index }].idcardNum" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					                value="${poVal.idcardNum }">
					  <label class="Validform_label" style="display: none;">身份证号</label>
				   </td>
				   <td align="left">
					  	<input name="scFileList[${stuts.index }].sex" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					                value="${poVal.sex }">
					  <label class="Validform_label" style="display: none;">性别</label>
				   </td>
				   <td align="left">
					  	<input name="scFileList[${stuts.index }].birthday" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					                value="${poVal.birthday }">
					  <label class="Validform_label" style="display: none;">出生日期</label>
				   </td>
				   <td align="left">
					  	<input name="scFileList[${stuts.index }].nation" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					                value="${poVal.nation }">
					  <label class="Validform_label" style="display: none;">民族</label>
				   </td>
				   <td align="left">
					  	<input name="scFileList[${stuts.index }].address" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					                value="${poVal.address }">
					  <label class="Validform_label" style="display: none;">住址</label>
				   </td>
				   <td align="left">
					        <input type="hidden" id="scFileList[${stuts.index }].idcardPhoto" name="scFileList[${stuts.index }].idcardPhoto"  value="${poVal.idcardPhoto }"/>
										<c:if test="${empty poVal.idcardPhoto}">
											<a  target="_blank" id="scFileList[${stuts.index }].idcardPhoto_href">未上传</a>
										</c:if>
										<c:if test="${!empty poVal.idcardPhoto}">
											<a  href="${poVal.idcardPhoto}"  target="_blank" id="scFileList[${stuts.index }].idcardPhoto_href">${(poVal.idcardPhoto).substring((poVal.idcardPhoto).lastIndexOf("/")+1)}</a>
										</c:if>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'scFileList\\[${stuts.index }\\]\\.idcardPhoto')"/> 
					  <label class="Validform_label" style="display: none;">身份证照片</label>
				   </td>
				   <td align="left">
					        <input type="hidden" id="scFileList[${stuts.index }].photo" name="scFileList[${stuts.index }].photo"  value="${poVal.photo }"/>
										<c:if test="${empty poVal.photo}">
											<a  target="_blank" id="scFileList[${stuts.index }].photo_href">未上传</a>
										</c:if>
										<c:if test="${!empty poVal.photo}">
											<a  href="${poVal.photo}"  target="_blank" id="scFileList[${stuts.index }].photo_href">${(poVal.photo).substring((poVal.photo).lastIndexOf("/")+1)}</a>
										</c:if>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'scFileList\\[${stuts.index }\\]\\.photo')"/> 
					  <label class="Validform_label" style="display: none;">图片</label>
				   </td>
				   <td align="left">
					        <input type="hidden" id="scFileList[${stuts.index }].video" name="scFileList[${stuts.index }].video"  value="${poVal.video }"/>
										<c:if test="${empty poVal.video}">
											<a  target="_blank" id="scFileList[${stuts.index }].video_href">未上传</a>
										</c:if>
										<c:if test="${!empty poVal.video}">
											<a  href="${poVal.video}"  target="_blank" id="scFileList[${stuts.index }].video_href">${(poVal.video).substring((poVal.video).lastIndexOf("/")+1)}</a>
										</c:if>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'scFileList\\[${stuts.index }\\]\\.video')"/> 
					  <label class="Validform_label" style="display: none;">视频</label>
				   </td>
				   <td align="left">
					        <input type="hidden" id="scFileList[${stuts.index }].mp3" name="scFileList[${stuts.index }].mp3"  value="${poVal.mp3 }"/>
										<c:if test="${empty poVal.mp3}">
											<a  target="_blank" id="scFileList[${stuts.index }].mp3_href">未上传</a>
										</c:if>
										<c:if test="${!empty poVal.mp3}">
											<a  href="${poVal.mp3}"  target="_blank" id="scFileList[${stuts.index }].mp3_href">${(poVal.mp3).substring((poVal.mp3).lastIndexOf("/")+1)}</a>
										</c:if>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'scFileList\\[${stuts.index }\\]\\.mp3')"/> 
					  <label class="Validform_label" style="display: none;">音频</label>
				   </td>
				   <td align="left">
					        <input type="hidden" id="scFileList[${stuts.index }].file" name="scFileList[${stuts.index }].file"  value="${poVal.file }"/>
										<c:if test="${empty poVal.file}">
											<a  target="_blank" id="scFileList[${stuts.index }].file_href">未上传</a>
										</c:if>
										<c:if test="${!empty poVal.file}">
											<a  href="${poVal.file}"  target="_blank" id="scFileList[${stuts.index }].file_href">${(poVal.file).substring((poVal.file).lastIndexOf("/")+1)}</a>
										</c:if>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'scFileList\\[${stuts.index }\\]\\.file')"/> 
					  <label class="Validform_label" style="display: none;">文档</label>
				   </td>
				   <td align="left">
					        <input type="hidden" id="scFileList[${stuts.index }].fingerprint" name="scFileList[${stuts.index }].fingerprint"  value="${poVal.fingerprint }"/>
										<c:if test="${empty poVal.fingerprint}">
											<a  target="_blank" id="scFileList[${stuts.index }].fingerprint_href">未上传</a>
										</c:if>
										<c:if test="${!empty poVal.fingerprint}">
											<a  href="${poVal.fingerprint}"  target="_blank" id="scFileList[${stuts.index }].fingerprint_href">${(poVal.fingerprint).substring((poVal.fingerprint).lastIndexOf("/")+1)}</a>
										</c:if>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'scFileList\\[${stuts.index }\\]\\.fingerprint')"/> 
					  <label class="Validform_label" style="display: none;">指纹</label>
				   </td>
				   <td align="left">
					       	<input name="scFileList[${stuts.index }].remark" maxlength="500" 
						  		type="text" class="inputxt"  style="width:120px;"
					               
					                value="${poVal.remark }">
					  <label class="Validform_label" style="display: none;">备注</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
