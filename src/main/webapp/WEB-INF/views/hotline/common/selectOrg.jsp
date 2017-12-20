<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<div class="row">
<div class="zTreeDemoBackground col-md-6"
		style="height: 450px; overflow: scroll;">
		<ul id="treeOrg" class="ztree"></ul>
	</div>
	<div id="organization" class="col-md-6">
	
	</div>
</div>
<script type="text/javascript">

var treeid ;
	/**
	* 返回选择的数据
	*/
 	function returnData(){
 		var id=$("#orgId").val();
 		var name=$("#orgName").val();
 		var code=$("#orgCode").val();
 		$('#selOrgdialog').dialog('close');
 	}
 	/**
	* 清除选择的数据
	*/
 	function clearData(){
 		$("#orgId").val("");
 		$("#orgName").val("");
 		$("#orgCode").val("");
 		$('#selOrgdialog').dialog('close');
 	 }
 	
 	
 	//zxy 
 	
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
				triggerOperation(treeNode);
			} 
		}
	};

	
	$.ajax({
		cache : false,
		type : "POST",
		url : "${ctx}/system/organization/list",
		dataType : "json",
		success : function(data) {
			$.fn.zTree.init($("#treeOrg"), ztreeSetting, data);
			var zTree = $.fn.zTree.getZTreeObj("treeOrg");
			var ids = $("#selOrgDialog").data("ids");
			var names = $("#selOrgDialog").data("names");
			
			var node ;
			if(!ids){
				node = zTree.getNodeByParam("id", "1");
			}else{
				node = zTree.getNodeByParam("id", ids);
			}
			
			zTree.selectNode(node, false);
			var nodes = zTree.getSelectedNodes();
			
			triggerOperation(nodes[0]);
			zTree.expandAll(true);
						
		}
	});
	/**
	 * 点击树的支点触发相应的操作
	 */
	 function triggerOperation(treeNode){
		//var organiztion = this;
		var treeid = treeNode.id;
		$.ajax({
            cache : false,
            type : "POST",
            async : false,
            url : "${ctx}/common/orgDetail/"+treeid,
            success : function(data) {
            	$('#organization').html(data);
            }
		});
		//queryOrg();
	}
</script>