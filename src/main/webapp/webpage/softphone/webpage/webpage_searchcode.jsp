<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title>市民热线--业务受理</title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinecss.jsp"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejs.jsp"%>
</head>
<body>
	<div class="col-md-12">
		<div class="row">
				<form id="searchCodeForm" action="${ctx}/channelrecord/wp/searchCodeSubmit/${id}/${searchCode}" method="post">
					<div class="index-grid-body">
                    <div class="col-md-12 m-t-30">
					<div class="information-form">
						<input type="hidden" name="id" id="id" value="${id}"></input>
						<div class="col-md-2 ie8correct">
							<label class="field-title" for="searchCode">查询码(10位)：</label> <input
								type="text" name="searchCode" id="searchCode" value="${searchCode}"
								required data-rule-searchCode>
						</div>
						 <div class="col-md-3 ie8correct">
							<button type="button" onclick="searchCodeSubmit()" class="btn form-btn-sm certain-btn  m-r-10">提交</button>
							<button type="button"
								onclick="javascript:art.dialog.list['searchCodeDialog'].close();"
								class="btn form-btn-sm back-btn  m-r-10">返回</button>
						</div>
					</div>
					</div>
					</div>
				</form>
			</div>
		</div>
	<script type="text/javascript">
	//********************************表单验证 开始******************************************
    $(function() {
        var validator = $("searchCodeForm").validate({
            errorPlacement: function(error, element) {
                // Append error within linked label
                $( element )
                    .closest( "form" )
                        .find( "label[for='" + element.attr( "id" ) + "']" )
                            .append( error );
            },
            errorElement: "span"
        });
    });
    //********************************表单验证 结束******************************************
	
	$(document).ready(function(){
			
	});
	
	function searchCodeSubmit(){
		if ($("#searchCodeForm").valid()) {
			var id=$("#id").val();
			var searchCode=$("#searchCode").val();
			$.post("${ctx}/channelrecord/wp/searchCodeSubmit/"+id+"/"+searchCode,
					function(data) {
					var resultJson = eval("(" + data + ")");
					
						if (resultJson.type == "result" && resultJson.result == "1") {
							//uiBase.addOneTab('查看',"${ctx}/channelrecord/viewWebPage/"+id);
							window.open("${ctx}/channelrecord/wp/viewWebPage/"+id, "viewWebPageWindow","toolbar =no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
							art.dialog.list['searchCodeDialog'].close();
						}else{
							art.dialog({title: '提交提示',icon: 'error',lock: false,content: '查询码不正确，2秒后会自动关闭……',time:2});
						}
					});
			}
	}
	
	</script>
</body>
</html>
