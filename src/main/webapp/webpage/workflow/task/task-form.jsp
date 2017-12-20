<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<c:if test="${not empty nodeStart }">
<iframe src="" id="taskForm" width="100%" FRAMEBORDER=0></iframe>
</c:if>
<c:if test="${empty  nodeStart }">
 <br> <br> <br> <br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无法显示附加页面,因为该流程没有配置起始节点:nodeStart
</c:if>
<script type="text/javascript">

		$("#taskForm").height($(document).height()-75);
		
		$('#passBtn').linkbutton({   
		});  
		$('#returnBtn').linkbutton({   
		}); 
		function procPass(yes){
			var iframe  = window.frames["iframeChild"].document;
			var inputvar = $("[vartype]", iframe);
			setvar(yes, inputvar, window.frames["iframeChild"]);
			var formData = {};
			$(iframe).find("input,textarea,select").each(function(){
				formData[$(this).attr("name")]= $(this).val();
			});
			var formAction = iframe.forms["formobj"].action;
			//ajax方式提交iframe内的表单
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				data : formData,
				url : formAction,// 请求的action路径
				error : function() {// 请求失败处理函数
				},
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var msg = d.msg;
						W.tip(msg);
						W.reloadTable();
						windowapi.close();
					}
				}
			});
		}

		var  iframe = document.getElementById("taskForm");  
		iframe. onload = iframe. onreadystatechange = iframeload;   
		iframe.src = "${nodeStart}";  
		
		function iframeload() {  
			if (!iframe.readyState || iframe.readyState == "complete") {  
			    $("#taskForm")[0].contentWindow.$("#back_btn").remove() ;
			    $("#taskForm")[0].contentWindow.$(".dataprve").remove() ;
			    $("#taskForm")[0].contentWindow.$(".datanext").remove() ;
			}  
		}

</script>
