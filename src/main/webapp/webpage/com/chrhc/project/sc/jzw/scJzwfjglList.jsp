<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/jzw/css/style.css" rel="stylesheet">
<script type="text/javascript" src="plug-in/tools/curdtools_zh-cn.js"></script>
<div class="building">
	<div class="building-title">${scJzwgl.name}</div>
	<div class="building-main">
		<table>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<c:forEach var="dy" items="${lstDy}">
						<th><span class="unit">${dy.unit}单元</span></th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:set var="fjCnt" value="${fn:length(lstFj)}" />
				<c:set var="lrNum" value="1" />
				<c:set var="lrFlag" value="false" />
				<c:forEach var="fj" items="${lstFj}" varStatus="sf">
					<tr>
						<td><c:set var="f" value="${fjCnt - sf.index}" />
						<span class="floor">${fjCnt - sf.index}F</span></td>
						<c:forEach var="dy" items="${lstDy}">
							<td>
								<span class="home">
									<span class="cell-left" id='lft_${lrNum}' style="display: none;"></span>
									<c:forEach var="rm" items="${fj}" varStatus="rf">
										<c:if test="${dy.id == rm.unit}">
											<span class="cell">
												<span class="home-num">${rm.room}</span>
												
												<div id="lbl_${rm.id}" class="home-icons">
												<t:optAuthFilter name="add">
												<i class="icon-plus" onclick="addJtxx('${rm.id}','${scJzwgl.name}')" title="添加家庭信息"></i>
												</t:optAuthFilter>
												</div>
												
												<script	type="text/javascript">
													var jt = eval('(${rm.jtxx})');
													if (jt != '') {
														var html = '';
														html = $("#lbl_${rm.id}").html();
														for ( var i = 0; i < jt.length; i++) {
															var xx = jt[i].jfid + "\",\"" + jt[i].jtid;
															html += "<i class='icon "+jt[i].cssname+"' onclick='gotoJtxx(\"" + xx + "\");' title='"+ jt[i].name+"|"+jt[i].hzxm +"'></i>";
															
															$.ajax({
															    url:"kinshipController.do?getjtrksx",
															    data:{"jtId":jt[i].jtid},
																type:"Post",
															    dataType:"json",
															    async:false,
															    success:function(datajt){	
															 
															    	if(datajt.success){
																		$.each(datajt.obj,function(n,m){
																			var xm = m.xm;
																			var id = m.id;
																			var cssname = m.cssname;
																			var name = m.name;
																			html += "<i class='icon "+cssname+"' onclick='gotorkxx(\"" + id + "\",\""+xm+"\");' title='"+ name+"|"+xm +"'></i>";
																		});																																		
																	}					
															    },
																error:function(data){
																	$.messager.alert('错误',data.msg);
																	return false;
																}
															});
															
															
															
															
															
															
															
														}
														$("#lbl_${rm.id}").html(html);
													}
												</script>
												<c:set var="lrFlag" value="true" />
											</span>
										</c:if>
									</c:forEach>
									<span class="cell-right" id='rht_${lrNum}' style="display: none;"></span>
									<script	type="text/javascript">
										if("${lrFlag}" == "true" ){
											$("#lft_${lrNum}").css("display","block");
											$("#rht_${lrNum}").css("display","block");
										}
									</script>
									<c:set var="lrNum" value="${lrNum + 1 }" />
									<c:set var="lrFlag" value="false" />
								</span>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- div class="legend">
		<div class="fleft circle">图例</div>
		<div class="fleft icons-list">
			<div class="icon-wrap">
				<i class="icon icon-01"></i><span>普通居民</span>
			</div>
			<div class="icon-wrap">
				<i class="icon icon-02"></i><span>租住户</span>
			</div>
			<div class="icon-wrap">
				<i class="icon icon-03"></i><span>家庭</span>
			</div>
			<div class="icon-wrap">
				<i class="icon icon-04"></i><span>空房</span>
			</div>
		</div>
	</div -->
</div>
<div id="tuli" class="tuli_box">
	<span id="tuli_close" class="tuli_close" title="关闭图例">X</span>
</div>
<script type="text/javascript">
$(document).ready(function() {
	var fr = "${fRooms}";
	//$(".building").css("height", $(window).height() - 110);
	if(fr == 1) fr = fr * 2;
	if(fr < 4 && fr != 0) fr = fr * 2;
	$(".building-title,.building-main").css("width", 46 + 120 * fr);
	
	$("#tuli_close").click(function(){
		$("#tuli").hide();
	});
	
});

var addflag = false;
function gotoJtxx(fjid, jtid) {
	addflag = false;
	var url = "chrhcFormBuildController.do?ftlForm&tableName=sc_jtxxnew&bizType=&id="+jtid+"&sqly_id="+fjid;
	//createwindow("家庭信息编辑", url,850,550);
	//addOneTab("家庭信息编辑", url);
	add("家庭信息编辑", url,"scJzwfjglList");
}

function addJtxx(fjid,jzwname) {
	addflag = true;
	var url = "chrhcFormBuildController.do?ftlForm&tableName=sc_jtxxnew&bizType=&sqly_id="+fjid +"&lydz=" + jzwname;
	//createwindow("家庭信息录入", encodeURI(url), 850, 550, "scJzwfjglList");
	add("家庭信息录入", encodeURI(url),"scJzwfjglList");
}

function callBackFj(fjid, jtid, hzxm){
	/* if(addflag){
		var html = '';
		var xx = fjid + "\",\"" + jtid;
		html = $("#lbl_"+fjid).html();
		html += "<i class='icon icon-03' onclick='gotoJtxx(\"" + xx + "\");' title='"+ hzxm +"'></i>";
		$("#lbl_"+fjid).html(html);
		
	} */
	//直接刷新当前页面
	window.location.href = window.location.href;
}
function gotorkxx(id,xm) {
	var url = "chrhcFormBuildController.do?ftlForm&tableName=sc_rkjbxxnew&mode=read&bizType=&id="+id
	//createwindow("家庭信息编辑", url,850,550);
	//addOneTab("家庭信息编辑", url);
	addOneTab(xm+"基本信息", url);
}

</script>