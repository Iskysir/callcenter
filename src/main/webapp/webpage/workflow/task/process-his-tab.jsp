<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title></title>
 	<t:base type="jquery,tools,easyui,DatePicker"></t:base>	
 	<link href="plug-in/media/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="plug-in/media/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/style.css" rel="stylesheet" type="text/css"/>
 	<link href="plug-in/media/css/style-add.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/media/css/default.css" rel="stylesheet" type="text/css"/>
   	<link href="plug-in/media/jquery.easyui/themes/gray/easyui.css" rel="stylesheet" type="text/css" >
	<script src="plug-in/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
	<script src="plug-in/media/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body class="page_body">
<section id="mainbox"> 
  	<div class="tab-content tab-box chrhc_tabs_box">
        <ul class="nav nav-tabs chrhc_tabs">
            <li id="fjymLi" class="active"><a href="javascript:void(0);" onclick="showFormPage()" data-toggle="tab">附加页面</a></li>
            <li id="rwclLi"><a href="javascript:void(0);" onclick="showTaskOperate()" data-toggle="tab">任务处理记录</a></li>
            <li id="lctLi"><a href="javascript:void(0);" onclick="showProcessPicture()" data-toggle="tab">流程图</a></li>
        </ul>
        <div class="tab-pane active" id="formPage">
            <%-- <iframe style="margin: 0px; padding: 0px; overflow-x: hidden; overflow-y: auto;" src="taskController.do?goTaskForm&taskId=${taskId }"></iframe> --%>
        </div>
        <div class="tab-pane" id="taskOperate">   
           	 
        </div>
        <div class="tab-pane" id="processPicture">   
          	  
        </div>
  </div>
</section>

	<script type="text/javascript">
	 $(document).ready(function() { 
		 	var obj = $("iframe[src^='taskController.do?goProcessHisTab']",window.parent.document);
			var objParent = obj.parent();
			objParent.css("padding","0px");
		 
		 	$.ajax({  
		        type: "post",
		        //cache : false, 
		        dataType:"html",
		        async:  false ,
		        url:"taskController.do?goProcessHisForm&processInstanceId=${processInstanceId }",       
		        success:function(data){
		        	//alert(data);
					$("#formPage").append(data);
			    },
				error:function(data){
					alert(data);
				}
		    });
		 });
		 
		function showFormPage(){
			doElementClass("fjymLi","rwclLi","lctLi");
			doElementClass("formPage","taskOperate","processPicture");
			
		 	//alert("showFormPage");
		 	$.ajax({  
		        type: "post",
		        //cache : false, 
		        dataType:"html",
		        async:  false ,
		        url:"taskController.do?goProcessHisForm&processInstanceId=${processInstanceId }",       
		        success:function(data){
		        	//alert(data);
		        	doDivShowAndHidden("formPage","taskOperate","processPicture",data);
			    },
				error:function(data){
					alert(data);
				}
		    });
		}
		function showTaskOperate(){
			doElementClass("rwclLi","fjymLi","lctLi");
			doElementClass("taskOperate","formPage","processPicture");

			//alert("showTaskOperate");
		 	$.ajax({  
		        type: "post",
		        //cache : false, 
		        dataType:"html",
		        async:  false ,
		        url:"taskController.do?goProcessHisOperate&processInstanceId=${processInstanceId }",       
		        success:function(data){
		        	//alert(data);
		        	doDivShowAndHidden("taskOperate","formPage","processPicture",data);		        			        	
			    },
				error:function(data){
					alert(data);
				}
		    });			
		}
		function showProcessPicture(){
			doElementClass("lctLi","rwclLi","fjymLi");
			doElementClass("processPicture","taskOperate","formPage");

			//alert("showProcessPicture");
		 	$.ajax({  
		        type: "post",
		        //cache : false, 
		        dataType:"html",
		        async:  false ,
		        url:"activitiController.do?viewProcessInstanceHistory&processInstanceId=${processInstanceId}",       
		        success:function(data){
		        	//alert(data);
		        	doDivShowAndHidden("processPicture","taskOperate","formPage",data);		        	
			    },
				error:function(data){
					alert(data);
				}
		    });		
		}
		//tab的class操作
		function doElementClass(add,no1,no2){
			if(!$("#"+add).hasClass("active")){
				$("#"+add).addClass("active");
			}
			//alert(no1+"=="+$("#"+no1).hasClass("active"));
			if($("#"+no1).hasClass("active")){
				$("#"+no1).removeClass("active");
			}
			if($("#"+no2).hasClass("active")){
				$("#"+no2).removeClass("active");
			}	
		}
		//div的显示与隐藏
		function doDivShowAndHidden(show,hi1,hi2,data){
			
			$("#"+hi1).html('');
			$("#"+hi1).css('display', 'none');
        	
			$("#"+hi2).html('');
			$("#"+hi2).css('display', 'none');

			//div显示
        	$("#"+show).css('display', 'block');
        	//$("#"+show).addClass("active");
        	$("#"+show).append(data);
			
		}
     </script>
</body>
<!-- END BODY -->
</html>