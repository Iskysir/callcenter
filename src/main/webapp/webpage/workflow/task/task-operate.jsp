<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>任务处理</title>
  	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" /> 
	
   <style type="text/css">
   	#t_table td label {font-size:15px;}
  </style>
 	<t:base type="DatePicker"></t:base>	
 	<!-- <link href="plug-in/media/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="plug-in/media/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/style.css" rel="stylesheet" type="text/css"/>
 	<link href="plug-in/media/css/style-add.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>
   	<link href="plug-in/media/jquery.easyui/themes/gray/easyui.css" rel="stylesheet" type="text/css" >
   	<script src="plug-in/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
	<script src="plug-in/media/js/bootstrap.min.js" type="text/javascript"></script> --> 
 </head>
 <body class="page_body">
 <section id="mainbox">
   <t:formvalid formid="formobj" layout="table" dialog="true" usePlugin="password">
	   <input name="taskId" id="taskId" type="hidden" value="${taskId}" />
	   <input name="bormoney" id="bormoney" type="hidden" vartype="B" value="${bormoney}">
	   <input name="keys" id="keys" type="hidden" />
	   <input name="values" id="values" type="hidden" />
	   <input name="types" id="types" type="hidden" />

    	<div  class="jindu_box" style="padding: 10px; margin: 10px;">
	    	<div style="margin: 15px auto; height: 50px; width: 900px;" id="tabs-project">
		    	<c:if test="${bpmLogListCount-3 > 0}">
		    		<div class="progress"></div>
		    		<div class="progress"></div>
		    	</c:if>
		    	 <c:forEach items="${bpmLogList}" var="bpmLg" varStatus="name" >
		    	 	<c:if test="${name.index < bpmLogNewListCount}">
		    	 		<div class="progress"></div>
		    	 		<div class="progress progress1">
			    	 		<div class="detial">
						       <b>${bpmLg.task_node }</b><br/>
						        [<span style="color:red;">时间:
						       	<fmt:formatDate value="${bpmLg.op_time }" pattern="MM-dd HH:mm:ss"/></span>]<br/>
						       [<span>操作人：${bpmLg.op_name }]</span>
						    </div>
					    </div>
		    	 	</c:if>
		    	 </c:forEach>
		    	 <c:if test="${taskName != null }">
		    	 	<div class='progress progress_cancel'></div>
		    	 	<div class="progress progress3">
			    	 	<div class="detial">
			                <span><b>${taskName}</b></span><br>
							[<span>任务人：</span>]<br>
			          	</div>
		          	</div>
		    	 </c:if>		    	 
	    	 </div>
    	 </div>
    	 
    	 <fieldset class="fieldset_clearreset">
    	 	<legend>意见信息</legend>
		    	<table class="table">
		      	<thead>
		        <tr>
		          <th>审查节点</th>
		          <th>时间</th>
		          <th>操作人</th>
		          <th>意见信息</th>
		        </tr>
		      	</thead>
		      	<tbody>
		      	<c:forEach items="${bpmLogList}" var="bpmLog">
			      	<tr>
			          <td>${bpmLog.task_node }</td>
			          <td><fmt:formatDate value="${bpmLog.op_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			          <td>${bpmLog.op_name }</td>
			          <td class="font_blue">${bpmLog.memo }</td>
			          
			        </tr>
			        <c:forEach items="${bpmLog.bpmFiles}" var="bpmFile">
				     		<tr height="35">
					     		<td>
					     			[<span style="color:blue">附件</span>] ${bpmFile.attachmenttitle}
									<a href="commonController.do?viewFile&fileid=${bpmFile.id}&subclassname=org.jeecgframework.workflow.pojo.base.TPBpmFile" title="下载">下载</a>
									<a href="javascript:void(0);"
										onclick="openwindow('预览','commonController.do?openViewFile&fileid=${bpmFile.id}&subclassname=org.jeecgframework.workflow.pojo.base.TPBpmFile','fList','800','700')">预览</a>
					     		</td>
					     		<td></td><td></td><td></td>
				     		</tr>
			    		</c:forEach>
		      	</c:forEach>
		      </tbody>
		    </table>
    	 </fieldset>
    	 
    	 <fieldset class="fieldset_clearreset">
    	 	<legend>处理意见</legend>
    	 	<textarea name="reason" style="resize: none; width:99%; height:65px;"></textarea>
    	 	<span class="Validform_checktip"></span>
  
    	 	<div class="form jeecgDetail" style="padding: 3px;">
    	 	<input type="hidden" id="bpmlogId" name="bpmlogId"/>
    	 	<br/>			
			<t:upload name="fiels" id="file_upload" extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;*.jpg;" buttonText="添加文件" formData="bpmlogId" uploader="activitiController.do?saveBpmFiles">
			</t:upload>
			<div class="form" id="filediv" style="height: 50px"></div>		
    	 	 </div>
			
    	 	<div class="chrhc_label_div" style="display:none">
    	 	
        		<label class="radio-inline">
            		<input type="radio" name="model" value="1" onchange="changeModel(1);" onClick="this.blur();" checked/>单分支模式
        		</label>
        		<label class="radio-inline">
            		<input type="radio" name="model" value="2" onchange="changeModel(2);" onClick="this.blur();" />多分支模式
            		<span id="manyModel" style="display:none">
				 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red">多分支模式默认执行所有分支：</span>
					<c:forEach items="${transitionList}" var="trans">
						<input type="checkbox" name="transition" value="${trans.nextnode}" checked disabled>${trans.Transition }
					</c:forEach>
		  			</span>
        		</label>
        		<c:if test="${histListSize > 0 }">
        		<label class="radio-inline">
            		<input type="radio" name="model" value="3" onchange="changeModel(3);" onClick="this.blur();"/>驳回  
			  		<span id="rejectModel" style="display:none">
						        <select name="rejectModelNode">
					  				<c:forEach items="${histListNode}" var="histNode">
					  					<option value="${histNode.task_def_key_}">${histNode.name_ }</option>
					  				</c:forEach>
			  			</select>
			  		</span>	
        		</label>
        		
        		</c:if>
    		</div>
		    <div class="chrhc_input_div">
		       	<span style="float: left; line-height: 28px;">指定下一步操作人：</span> 	        
		        <div class="tabs_btn_div" style="float: left;">		        	
		        	<input type="text" name="last" id="last" readonly="readonly"> 
					<input name="id" type="hidden" value="" id="id"> 					
            		<t:choose hiddenName="id" hiddenid="id" url="activitiController.do?goEntrust" name="entrusterList" icon="icon-search" title="用户列表" textname="last" isclear="true"></t:choose> 					
		    	</div>
		        <span class="fontred" style="float: left; line-height: 28px;">(如果不指定则按照系统默认)</span>
		    </div>
    	 </fieldset>
    	 <div style="width:100%; margin:0 auto; text-align:center;">
			 <div class="but_input_div">
			  		<div id="singleModel" style="display:black">
							<input type="hidden" name="option" id="option" />
				  			<input type="hidden" name="nextnode" id="nextnode" />
							<c:forEach items="${transitionList}" var="trans" varStatus='status'>
							<c:if test='${status.index==0 }'>
								<input type="button" class="btn btn_01" onclick="procPass('${trans.Transition }','${trans.nextnode}')" >
							</c:if>
							<c:if test='${status.index==1 }'>
								<input type="button" class="btn btn_02" onclick="procPass('${trans.Transition }','${trans.nextnode}')" >
							</c:if>
							<c:if test='${status.index > 1 }'>
								<input type="button" class="btn btn_03" onclick="procPass('${trans.Transition }','${trans.nextnode}')" >
							</c:if>	
							</c:forEach>
				  		</div>
				  		<div id="manyModelButton" style="display:none">
				  			<input type="button" class="btn btn_01" onclick="manyModelSubmit();" value="提交">
				  			<input type="hidden" name="transStr" id="transStr">
				  		</div>
				<!-- <input type="button" class="btn btn_01"/>
				<input type="button" class="btn btn_02"/>
			    <input type="button" class="btn btn_03"/> -->
			  </div> 
		  </div>
  	</t:formvalid>
  	
  	
  	
  	
</section>
<script type="text/javascript">
	$(document).ready(function() {
	 	  	//debugger;
	 	  	//处理上次按钮的样式
	 	  	$("#file_upload-button").addClass("btn");
	 	  	$("#file_upload-button").css("background-image","url(plug-in/media/image/bj_tjfj.jpg)");
	 	  	$("#file_upload-button").css({"width":"146px" ,"height":"32px"});
	 	  	$("span .uploadify-button-text").text("");

			//处理选择操作人按钮样式
			//$(".tabs_btn_div a[icon='icon-search']").addClass("btn btn_bag_1");
			$(".tabs_btn_div a[icon='icon-search']").text("");
			$(".tabs_btn_div a[icon='icon-search']").css("text-decoration","none");
			//$(".tabs_btn_div a[icon='icon-search']").append("<i class=\"fa fa-search\"></i>");
			$(".tabs_btn_div a[icon='icon-search']").append("&nbsp;&nbsp;&nbsp;<img src=\"plug-in/media/image/searchImg.jpg\"/>&nbsp;&nbsp;");		
			//$(".tabs_btn_div a[icon='icon-redo']").addClass("btn btn_bag_2");
			$(".tabs_btn_div a[icon='icon-redo']").text("");
			$(".tabs_btn_div a[icon='icon-redo']").css("text-decoration","none");
			$(".tabs_btn_div a[icon='icon-redo']").append("&nbsp;&nbsp;<img src=\"plug-in/media/image/redoImg.jpg\"/>&nbsp;&nbsp;");
			//$(".tabs_btn_div a[icon='icon-redo']").append("<i class=\"fa fa-repeat\"></i>");
			//$(".tabs_btn_div").append("<button class=\"btn btn_bag_1\"><i class=\"fa fa-search\"></i></button><button class=\"btn btn_bag_2\"><i class=\"fa fa-repeat\"></i></button>");
								

	});
		
		
	function procPass(yes, nextnode) {
		$("#option").val(yes);
		$("#nextnode").val(nextnode);
		this.submitProcess();
	}
	/**
	 * 多分支模式 提交
	 */
	function manyModelSubmit() {
		this.submitProcess();
	}
	//流程提交
	function submitProcess() {
		var formData = {};
		$(formobj).find("input,textarea,select").each(function() {
			if ($(this).attr("name") == 'model') {
				//formData[$(this).attr("name")]= $('input[name="model"]:checked').val();
				formData[$(this).attr("name")] = '1';
			} else {
				formData[$(this).attr("name")] = $(this).val();
			}
		});
		/* getParentWindow().getParentWindow().tip("hahahahahhahaha");
		getParentWindow().getParentWindow().refreshCurTab();
		closeCurrentTab(); */
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			data : formData,
			url : 'activitiController.do?processComplete',// 请求的action路径
			error : function() {// 请求失败处理函数
			},
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					$("#bpmlogId").val(d.obj.id);
					if ($(".uploadify-queue-item").length > 0) {
						//upload();
						$('#file_upload').uploadify('upload', '*');
						if(getParentWindow().getParentWindow().refreshCurTab){
							getParentWindow().getParentWindow().tip(d.msg);
							getParentWindow().getParentWindow().refreshCurTab();
						}
						closeCurrentTab();
						return flag;
					} else {
						var msg = d.msg;
						if(getParentWindow().getParentWindow().refreshCurTab){
							getParentWindow().getParentWindow().tip(msg);
							getParentWindow().getParentWindow().refreshCurTab();
						}						
						closeCurrentTab();
					}
				}
			}
		});
	}

	/**
	 * 单分支模式/多分支模式切换
	 */
	function changeModel(value) {
		if (value == 1) {
			//单分支模式
			$("#singleModel").show();
			$("#manyModel").hide();
			$("#manyModelButton").hide();
			$("#rejectModel").hide();
		} else if (value == 2) {
			//多分支模式
			$("#singleModel").hide();
			$("#rejectModel").hide();
			$("#manyModel").show();
			$("#manyModelButton").show();
		} else {
			$("#singleModel").hide();
			$("#manyModel").hide();
			$("#rejectModel").show();
			$("#manyModelButton").show();
		}

	}
</script>


 </body>
 
</html>
