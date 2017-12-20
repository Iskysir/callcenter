<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<div class="zTreeDemoBackground col-md-3" style="height:550px; overflow:scroll;">
    <ul id="treeOrg" class="ztree"></ul>
</div> 
<div class="col-md-9 ">

      <form id="pageform" onsubmit="return preSub();" > 
        <div class="row m-t-10 m-b-10">
        	<div class="col-md-4">
	            <input class="m-r-10 search-input" type="text" placeholder="请输入姓名" name="search_LIKE_userName" value="${param.search_LIKE_userName}" id="queryusername">
        	</div>
        	<div class="col-md-3">
	            <button class="btn btn-primary" onclick="changePage();queryUser();" type="button">查询</button>
        	</div>
        	
        	<!-- <div class="col-md-3">
	           <button class="btn btn-primary" type="button" id="clearBtn">清除</button>
        	</div> -->
        </div>
    </form>
	<div id="userlist"></div>
    
</div>



<script type="text/javascript">

var treeid ;
/**
 * 点击树的支点触发相应的操作
 */
function triggerUser (treeNode){
	
	if($("input[name='page']",$('#pageform')).size() > 0){
		$("input[name='page']",$('#pageform')).get(0).value=1;
	}
	$("#queryusername").val('');
	treeid = treeNode.id;
	queryUser();


}

function changePage(){
	if($("input[name='page']",$('#pageform')).size() > 0){
		$("input[name='page']",$('#pageform')).get(0).value=1;
	}
}

function preSub(){
	if($("input[name='page']",$('#pageform')).size() > 1){
		//alert($("input[name='page']",$('#pageform')).size());
		$("input[name='page']",$('#pageform')).eq(0).remove();
		//alert($("input[name='page']",$('#pageform')).size());
		$("input[name='page.size']",$('#pageform')).eq(0).remove();
	}
	queryUser();
	return false;
}

function queryUser(){

	$.ajax({
        cache : false,
        type : "POST",
        async : false,
        data: $('#pageform').serialize(),
        url : basePath + "/common/listuser/" + treeid + "/${selType}",
        success : function(data) {
        	$('#userlist').html(data);
        	$("#pageSize").attr("disabled","disabled");
        }
	});
	
	var data = $("#selUserDialog").data();
	
	for(var p in data){
		if($("input[value='" + p  + "']")){
			$("input[value='" + p  + "']").iCheck('check');
		}
	}
}
function getInteliFont(treeId, node)
{
	var inteliOrg="${inteliOrg}";
	var inteliFont={'background-color':'green', 'color':'white'};
	//alert(inteliOrg+":"+node.id);
	var inteliOrgArray=inteliOrg.split(",");
	//alert(inteliOrg);
	//alert(inteliOrgArray);
	//alert(inteliOrgArray[1]);
	var matched=false;
	for(i=0;i<inteliOrgArray.length;i++)
	{
		//alert(inteliOrgArray[i]);
		//alert(inteliOrgArray.length);
		if(inteliOrgArray[i]==node.id)
		{
			//alert(inteliOrg);
			//alert(inteliOrgArray[i]+":"+node.id);
			matched=true;
			break;
		}
		else
		{
			matched=false;
		}
	}
	
	if(matched==true)
	{
		return inteliFont;
	}
	else
	{
		return {};	
	}
	
}
var ztreeSetting = {
	view : {
		selectedMulti : false,
		fontCss: getInteliFont
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		onClick : function(event, treeId, treeNode) {
			triggerUser(treeNode);
		} 
	}
};


	$.fn.zTree.init($("#treeOrg"), ztreeSetting, ${orgTree});
	
	var zTree = $.fn.zTree.getZTreeObj("treeOrg");
	var node = zTree.getNodeByParam("id", "1");
	zTree.selectNode(node, false);
	zTree.expandAll(true); 
	var nodes = zTree.getSelectedNodes();
	
	if('mult' == '${ selType}'){
		triggerUser(nodes[0]);
	}else{
		var data_ = $("#selUserDialog").data();
		for(var p in data_){
			$("#queryusername").val(data_[p]);
		}
		//$("#queryusername").val('${userInfo.displayName}');
		treeid = nodes[0].id;
		queryUser();
	}
	

</script>