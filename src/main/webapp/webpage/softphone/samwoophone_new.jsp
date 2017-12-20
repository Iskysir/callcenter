<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/hotline/common/hotlinejsphead.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/hotline/common/hotlinemeta.jsp"%>
<title>软电话</title>
<%@ include file="/WEB-INF/views/hotline/common/hotlinecss.jsp"%>
<script type="text/javascript">
	var _agentStatusFlag = null;
 this._agentStatus={};
//this.agentStatus.isrunning=false;

this._agentStatus.quartzJs = function (conf){
	//var that = this._agentStatus;
	this.isrunning = false;
		
	var that = this;
	if(_agentStatusFlag){
		window.clearInterval(_agentStatusFlag);
		//alert("如果有的话，clear");
	}

	this._quartzjs = function (){
		//alert("abc"+that._iCount);
		//that.isrunning = true;
		var NowTime = new Date();
		var lnow = NowTime.getTime();
	//	console.log(conf.ope+"nedd time is "+(conf.lendTime-lnow));
		if((conf.lendTime-lnow)<=0){
			window.clearInterval(that._iCount);
			//alert(that._iCount);
			that.isrunning = false;
			art.dialog({
				title : '时间结束提示',
				icon : 'face-smile',
				lock : true,
				content : conf.ope+'时间到了！'
				});
			return false;
		}
	};
	
	this._iCount = setInterval(this._quartzjs,10000);

	_agentStatusFlag = this._iCount;
};

var AgentStaff=function()
{
};
var phoneNumber,curBizNo,curBizId,AgentStatusCH,AgentStatusCHBefore,ConsultAgentId,ConsultAgentUsername,ConsultAgentDn;
var bAgentIDSpeaked = false;
function SetCurBizNo(bizId,bizNo)
{
	curBizNo=bizNo;
	curBizId=bizId;
	/* aOCX.DoSetAssociatedData("curBizId",bizId);
	aOCX.DoSetAssociatedData("curBizNo",curBizNo); */
}

function SetIncomingCallNum(teleNum)
{
	//alert(teleNum);
	$("#callNumber").html(teleNum);
}
/**
 * 按钮初始化
 */
function SetButtoninit(){
	/*document.getElementById("btnHook").disabled = true;
	document.getElementById("btnConference").disabled = true;
	document.getElementById("btnConsult").disabled = true;
	document.getElementById("btnConsultRedirect").disabled = true;
	document.getElementById("btnDial").disabled = true;
	document.getElementById("btnDisconnect").disabled = true;
	//document.getElementById("btnFax").disabled = true;
	document.getElementById("btnHold").disabled = true;
	document.getElementById("btnListen").disabled = true;
	//document.getElementById("btnOutPhone").disabled = true;
	document.getElementById("btnPause").disabled = true;
	document.getElementById("btnRopCall").disabled = true;
	document.getElementById("btnTransfer").disabled = true;
	document.getElementById("btnAuto").disabled = true;
	document.getElementById("btnAgentStatus").disabled = true;
	document.getElementById("btnAfterCall").disabled = true;*/
	
	$("#Pausebtn").attr("class","disabled");
	$("#Hookbtn").attr("class","disabled");
	$("#Conference").attr("class","disabled");
	$("#Consultbtn").attr("class","disabled");
	$("#ConsultRedirectbtn").attr("class","disabled");
	$("#Dialbtn").attr("class","disabled");
	$("#Disconnectbtn").attr("class","disabled");
	$("#Holdbtn").attr("class","disabled");
	$("#Listenbtn").attr("class","disabled");
	$("#Pausebtn").attr("class","disabled");
	$("#RopCallbtn").attr("class","disabled");
	$("#Transferbtn").attr("class","disabled");
	$("#Autobtn").attr("class","disabled");
	$("#AgentStatusbtn").attr("class","disabled");
	$("#AfterCallbtn").attr("class","disabled");


	
}

/**
 * 检测是否安装了控件
 */
function checkInstalledOcx()
{
	try
	{
		aOCX.SetToolsVisible(0);
	}
	catch(exp)
	{
		//alert(1);
		 var dialogAgentOcx = art.dialog({
             title:"座席控件下载",
       
      
             content:'请点击【下载】按钮安装座席软电话控件，安装完毕后重启IE浏览器再登录。',
             ok:function(){
            	 $.fileDownload("${ctx}/resources/themes/hotline/ocx/AgentOCXSetup.zip", {httpMethod : "POST"});
             },
             okVal:"下载",
             cancel:function(){
            	 
             },
             cancelVal:"返回",
             id: "dialog_agentocxdownload",
             lock:true
       
         });
	}
}
/**
 * 创建加载
 */
function window_onload(){
	SetButtoninit();
	<chr:perm code="softphone_agent softphone_admin">
	checkInstalledOcx();
	</chr:perm>
}
/**
 * 登录功能
 */
 function btnLoginAndLogout_onclick(){
	var text=$("#Loginbtn").getBtnText();
	//alert(text);
	if(text=="签入")
	{
		btnLogin_onclick();
		//$("Loginbtn").setDisable();
		//$("Loginbtn").setText("签出");
	}
	if(text=="签出")
	{
		btnLogOut_onclick();
		$("#agentStatus").html("未签入");
		//$("Loginbtn").setText("签入");
	}
 }
 
function btnLogin_onclick(){
	aOCX.DatabaseConnectionString="${DatabaseConnectionString}";
	aOCX.SetToolsVisible(0);
	aOCX.AgentID = "${userInfo.obligateA}";
	aOCX.Password = "${userInfo.obligateB}";
	aOCX.DeviceName = "${userInfo.obligateC}";
	//aOCX.AgentID = 'Agent2';
	//aOCX.Password = '2';
	if("${userInfo.obligateA}"&&"${userInfo.obligateB}"&&"${userInfo.obligateC}")
	{
		aOCX.LogIn();
	}
	else
	{
		art.dialog({title: '提示',icon:'warning',lock: true,content: "该用户未配置座席信息，请联系管理员。",cancel:function(){},cancelVal:"返回"});
	}
	
	//alert(aOCX.DatabaseConnectionString);
}
/**
 * 退出功能
 */
function btnLogOut_onclick(){
	aOCX.LogOUT();
	//追加日志
	addLog("signout");
	this.SetButtoninit();
	document.getElementById("btnLogin").disabled = false;
}
function showVideo(sAni)
{
	$.post("${ctx}/playview/getTelMessage?tel="+sAni+"&_x="+Math.random(),
			null, 
			function(data) {
					var resultJson=eval("("+data+")");
					if(resultJson.result=="true"||resultJson.result==true)
					{
						uiBase.addOneTab("报警柱:"+sAni,"${ctx}/playview/showView?tel="+sAni);
					}
			});
}

/*
 * 视频通话来电弹屏
 */
function aOCX_CallArriveVideo(sAni,sDnis,uuid,sData){

	bAgentIDSpeaked = false;;
	document.getElementById("Ani").value = sAni;
	$("#callNumber").html(sAni);	
	uiBase.addOneTab("视频呼入"+sAni,"${ctx}/channelrecord/addOrder2?infoSource=ChannelTypeVideo&telephone="+sAni+"&uuid="+uuid );	
	addLog("videoCall");			
	phoneNumber=sAni;
}

/*
 * 语音通话来电弹屏
 */
function aOCX_CallArrive(sAni,sDnis,sData){
	
	var sName = aOCX.DoGetAssociatedData("callid");
	//alert(sName);
	addCallLog("ivrEnd",sName);
	/* if(!addCallLog("ivrEnd",sName)){
		return false;
	}; */
	bAgentIDSpeaked = false;
	//alert(sAni);
	document.getElementById("Ani").value = sAni;
	$("#callNumber").html(sAni);
	//alert(sData);
	//alert(uiBase.addOneTab);
	//
	/* alert(aOCX.DoGetAssociatedData("curBizId"));
	//通过随路数据获取业务信息
	curBizId=aOCX.DoGetAssociatedData("curBizId");
	curBizNo=aOCX.DoGetAssociatedData("curBizNo");
	alert(curBizId +"   "+curBizNo); */
	
	//默认磋商不是业务，只有呼叫进入中状态 弹屏
	if("true"!="${sys_consultBiz}"&&AgentStatusCH=="呼叫进入中状态")
	{
		
		uiBase.addOneTab("呼入:"+sAni,"${ctx}/business/accept/add?infoSource=ChannelTypeTelephone&telephone="+sAni);
		showVideo(sAni);
		addLog("call");
	}
	else
	{
		
	}
			
	phoneNumber=sAni;
	
}

/*
 * 电话转接
 */
function aOCX_CallTransfer(sAni,sDnis,bizId,sData)
{
	//alert(sAni+" "+sDnis+" "+bizId+" "+sData);
	uiBase.addOneTab("转接:"+sAni,"${ctx}/business/accept/modify/"+bizId);
}

function aOCX_EVTButtonStatus(status,AgentStatusCH,bEnable)
{
	//document.getElementById("msg").innerHTML=document.getElementById("msg").innerHTML+"<li>"+status+":"+AgentStatusCH+"</li>";
}


function window_onunload(){
	
}
/**
 * 配置按钮处理方法
 */
function btnConfigure_onclick(){

	aOCX.ShowConfig();
	//追加日志
	addLog("configure");
}
/**
 * 登录成功事件
 */
function aOCX_EVTLoginSuc(){
	document.getElementById("btnLogin").disabled = true;
	addLog("LoginSuccess");
}
/**
 * 登录失败处理方法
 */
function aOCX_EVTLoginFailed(reason){
	addLog("LoginFailed");
	//art.dialog({title: '提示',icon:'warning',lock: true,content: "座席已签入，请稍后再试试或使用其他座席签入。"});
	//alert(reason);
}
/**
 * 修改当前状态值
 */
function aOCX_EVTReturnStatusCH(status){
	//alert(status);
	AgentStatusCHBefore=AgentStatusCH;
	AgentStatusCH=status;
	document.getElementById("txtStatus").value = status;
	$("#agentStatus").html(status);
	
}

function aOCX_EVTAnswerSucc(){
	if(!bAgentIDSpeaked) {
		aOCX.CmdPlayAgentIDWelcome();
		bAgentIDSpeaked = true;
		//操作日志
		addLog("AnswerSuccess");
	}
}

/**
 *  拨出电话
  *	undo 拨出后事件处理
 */
function aOCX_DialOut(num)
{
	
		aOCX.DialOut(num);
		phoneNumber=num;
		//操作日志
		addLog("DialOut "+num);
}
/*
 * 弹出拨号窗体
 */
function showDialDialog(showType)
{
	/*parent.uiBase.showDialog({
		title: showType,
		width: 640,
		height: 200,
		iframe: '${ctx}/softphone/agentdial?_x='+Math.random(),
		modal: false,

		});*/
	$.dialog.open('${ctx}/softphone/agentdial?showType='+encodeURI(showType)+'&_x='+Math.random(),{title: showType,
                width: "298px",
                padding:"10px",
                content:document.getElementById("dialer_dialogid"),
                follow:document.getElementById("js-dialer"),
                cancel:function(){},
                cancelVal:"返回",
                id: "dialog_waibo"});
}

/**
 * 外拨按钮功能
 */
function btnDial_onclick(){
	var text=$("#Dialbtn").getBtnText();
	if(text == "外拨"){
		//aOCX.CmdDialOut();
		showDialDialog("外拨");
		addLog("Dialout");
		
	}else{
		aOCX.CmdMakeCallStop();
		$("#DialbtnSpan").html("外拨");
		//操作日志
		addLog("MakeCallStop");
	}
}
//
function aOCX_EVTDialOut(){
	uiBase.addOneTab("外拨:"+phoneNumber,"${ctx}/business/accept/add?infoSource=ChannelTypeTelephone&telephone="+phoneNumber);
	//document.getElementById("btnDial").value="取消";
	//alert()
	//'showModalDialog "DialOut.htm",window
	//aOCX.ShowDialOut();
	
}
/**
 * 接听按钮功能
 */
function btnCallAnswer()
{
	aOCX.CmdAnswer();
	//操作日志
	//alert("接听");
	addLog("answer");
}
function btnCallHandup()
{
	aOCX.CmdOnHook();
	//操作日志
	addLog("OnHook");
}
function btnHook_onclick(){
	if (document.getElementById("btnHook").value.substring(0, 2) == "接听"){
		btnCallAnswer();
    
	}else if (document.getElementById("btnHook").value.substring(0, 2) == "接受"){
		aOCX.CmdConsultAnswer();
		//操作日志
		addLog("ConsultAnswer");
	}
	else{
		btnCallHandup();
    }
}
/**
 * 保持按钮功能
 */
function btnHold_onclick(){
	if(document.getElementById("btnHold").value.substring(0, 2) == "保持") {
		aOCX.CmdHold();
		//操作日志
		addLog("Hold");
		$("#Consultbtn").attr("class","disabled");
		//$("#btnHook").attr("class","disabled");//
		//alert($("#btnHook").children());
	}else{
		
		$("#Consultbtn").attr("class","");
		//操作日志
		addLog("HoldStop");
		aOCX.CmdHoldStop();
	}
}
/**
 * 转接按钮功能
 */
function btnTransfer_onclick(){
	if(document.getElementById("btnTransfer").value.substring(0, 2)== "转接" ){
		btnConsultTransfer();
		//aOCX.CmdTransfer();
		//showDialDialog("转接");
	}
	else {
		aOCX.CmdTransferStop();
		//操作日志
		addLog("TransferStop");
	}
}
/**
 *转接电话 
 */
 function aOCX_OutPhone(num)
 {
 	//alert(num);
	 aOCX.OutPhone(num);
	//操作日志
	 addLog("outphone: "+num);
 }
/**
 * 磋商功能
 */

function CmdConsultToAgent(agentId,agentUsername,agentDn)
{
	ConsultAgentId=agentId;
	ConsultAgentUsername=agentUsername;
	ConsultAgentDn=agentDn;
	//alert("CmdConsultToAgent &&&  ConsultAgentUsername:"+ConsultAgentUsername+",ConsultAgentDn："+ConsultAgentDn+"curBizId:"+curBizId);
	aOCX.CmdConsultToAgent(agentId,"","");
	//document.getElementById("btnConsult").value="取消";
	//undo 设置按钮状态
	//操作日志
	 addLog("ConsultAgent");
}
/**
 * 磋商取消
 */
function CmdConsultCancel()
{
	aOCX.CmdConsultCancel();
	//操作日志
	addLog("CmdConsultCancel");
}

/**
*		弹出座席列表界面 磋商/磋商转接/监听/ 
*/
function showAgentList(showType)
{
	  /*parent.uiBase.showDialog({
		title: showType,
		width: 640,
		height: 200,
		closed: false,
		cache: false,
		href: '${ctx}/softphone/agentlist?_x='+Math.random(),
		iframe:true,
		modal: false,
		resizable:true
		});*/
	var followEl=null;
	if(showType=="磋商")
	{
		followEl=document.getElementById("js-consultations");
	}
	if(showType=="转接")
	{
		followEl=document.getElementById("js-ConsultRedirect");
	}
	if(showType=="监听")
	{
		followEl=document.getElementById("js-Listen");
	}
	if(sshowType=="强插")
	{
		followEl=document.getElementById("js-Intrude");
	}
	
	  art.dialog.open('${ctx}/softphone/agentlist?showType='+encodeURI(showType)+'&_x='+Math.random(),
	  			{title: showType, width: 640,follow:followEl,lock:true,
                	cancel:function(){
                	//alert(showType);
                	if(showType=="磋商")
                	{
                		if(AgentStatusCH=="显示磋商查询结果状态")
                		{
	                		CmdConsultCancel();
	                		$("#ConsultbtnSpan").html("磋商");
                		}
                	}
                	if(showType=="转接")
                	{
                		
                	}
                	if(showType=="强插")
                	{
                		if(AgentStatusCH=="显示查询结果状态")
                		{
	                		//aOCX.CmdListenStop();
                			CmdIntrudeStop();
                		}
                	}
                	if(showType=="监听")
                	{
                		if(AgentStatusCH=="显示查询结果状态")
                		{
	                		//aOCX.CmdListenStop();
                			CmdListenStop();
                		}
                	}
                },
                close:function(){
                	//alert(showType);
                	if(showType=="磋商")
                	{
                		if(AgentStatusCH=="显示磋商查询结果状态")
                		{
                			CmdConsultCancel();
                			$("#ConsultbtnSpan").html("磋商");
                		}
                	}
                	if(showType=="转接")
                	{
                		
                	}
                	if(showType=="监听")
                	{
                		if(AgentStatusCH=="显示查询结果状态")
                		{
                			CmdListenStop();
                		}
                	}
                	if(showType=="强插")
                	{
                		if(AgentStatusCH=="显示查询结果状态")
                		{
	                		//aOCX.CmdListenStop();
                			CmdIntrudeStop();
                		}
                	}
                },
                cancelVal:"返回",
                id: "dialog_agentList"});
}

/**
*		磋商按钮功能
*/
function btnConsult_onclick(){
	//alert("btnConsult_onclick");
	//alert(parent.uiBase);
	var text=$("#Consultbtn").getBtnText();
	if(text== "停止" )
	{
	  //aOCX.CmdMakeCallStop();
	  aOCX.CmdConsultStop();
	  
	//操作日志
	  addLog("ConsultStop");
	}
	else if(text== "取消" )
	{
		CmdConsultCancel();
	}
			
	else if(text== "磋商" )
	{
		aOCX.CmdConsult();
	  //showAgentList("磋商");
  }
  else{
	  aOCX.CmdConsultStop();
	//操作日志
	  addLog("ConsultStop");
  }
	  
	
  /*if(document.getElementById("btnConsult").value.substring(0, 4) == "磋商结束" ){
	  aOCX.CmdConsultStop();
	}
  else if(document.getElementById("btnConsult").value.substring(0, 2)== "磋商" ){
	  aOCX.CmdConsult();
  }else
	  aOCX.CmdConsultStop();*/
}



function aOCX_EVTConsult(agentList){
	//alert(agentList);
	//aOCX.ShowConsultDlg();
	showAgentList("磋商");
	//操作日志
	addLog("ShowConsultDlg");
}
/**
 * 磋商成功功能
 */
function aOCX_EVTConsultSucc(){
	
	//document.getElementById("btnConsult").value="停止";
	$("#ConsultbtnSpan").html("停止");
	//undo磋商成功设置按钮状态
	//DAgentID = sAgentID;
}
/**
 * 暂停按钮功能
 */
function btnPause_onclick(){
	if(document.getElementById("btnPause").value.substring(0, 2)== "忙碌" ){
		aOCX.CmdPause();
		//操作日志
		addLog("CmdPause");
	}else{
		aOCX.CmdContinue();
		//操作日志
		addLog("CmdContinue");
	}
}
/**
 * 磋商转接按钮功能
 */
function btnConsultTransfer(){
	//agentWindow.CmdConsultTransfer(ConsultAgentId,"","");
	CmdConsultTransfer(ConsultAgentId,phoneNumber,'');
	//showAgentList("磋商转接");
}
function CmdConsultTransfer(agentId,ani,dnis)
{
	
	var url="${ctx}/softphone/calltransfer?_x="+Math.random();
	//alert(url);
	//alert("ConsultAgentUsername:"+ConsultAgentUsername+",ani:"+ani+",ConsultAgentDn："+ConsultAgentDn+"curBizId:"+curBizId);
	$.post(
			url,
			{userName:ConsultAgentUsername,ani:ani,dnis:ConsultAgentDn,bizId:curBizId,data:curBizNo},
			function(data){
					//alert(data);
					if(data.type=="result"&&data.result=="1")
					{
						aOCX.CmdConsultTransfer(agentId,ani,dnis,"");
						//操作日志
						addLog("ConsultTransfer");
						//alert(agentcurBizNo);
						//agentWindow.CmdConsultTransfer(agentId,agentUsername,agentDn);
						//agentWindow.aOCX_setAgentStatus(status,senconds);
						//操作日志
						//addLog("record "+fileName);	
						//alert("录音记录已保存");
						//uiBase.getParentTabWindow().$("form").submit();
						//uiBase.trigerParentTabFunc('$("form").submit()');
						//uiBase.closeCurrentTab();
					}
				});
}

/*
 * 磋商转接外线
 */
function aOCX_CmdConsultToOutLine(num)
{
	//alert(num);
	aOCX.CmdConsultToOutLine(num);
	//操作日志
	addLog("ConsultToOutLine:" + num);
}
/**
 * 会议按钮功能
 */
function btnConference_onclick(){
	//if(document.getElementById("btnConference").value.substring(0, 2)=="会议" ){
		aOCX.CmdConference();
		//操作日志
		addLog("ConsultToOutLine");
	//}
	//else{
		//aOCX.CmdMakeCallStop();
	//}
}



/**
 * 监听按钮功能
 */
function btnListen_onclick(){
	/*if(document.getElementById("btnListen").value.substring(0, 2)== "监听") {
		aOCX.CmdListen();
	}
	else{
		aOCX.CmdListenStop();
	}*/
	
	var text=$("#ListenbtnSpan").html();
	if(text== "监听" )
	{
		//aOCX.CmdListen();
		//showAgentList("监听");
		aOCX.CmdListen();
	}
	else{
		CmdListenStop();
	}
	
}
/*监听功能*/
function CmdListenToAgent(agentId)
{
	$("#ListenbtnSpan").html("停止");
	//alert($("#ListenbtnSpan").html());
	//alert(agentId);
	aOCX.CmdListenToAgent(agentId);
	
	//document.getElementById("btnListen").value="停止";
	//操作日志
	addLog("ListenToAgent:" + agentId);
}
function CmdListenStop()
{
	$("#ListenbtnSpan").html("监听");
	//alert($("#ListenbtnSpan").html());
	//alert("CmdListenStop");
	aOCX.CmdListenStop();
	listenStatus="EVTListenStop";
	aOCX_EVTReturnStatus("AUX");
	aOCX_EVTReturnStatusCH("暂停状态");
	//document.getElementById("btnListen").value="监听";
	//操作日志
	addLog("ListenStop");
}
var intrudeStatus;
function CmdIntrudeStop()
{
	$("#DisconnectbtnSpan").html("强插");
	//alert($("#ListenbtnSpan").html());
	//alert("CmdListenStop");
	aOCX.CmdIntrudeStop();
	intrudeStatus="EVTIntrudeStop";
	aOCX_EVTReturnStatus("AUX");
	aOCX_EVTReturnStatusCH("暂停状态");
	//document.getElementById("btnListen").value="监听";
	//操作日志
	addLog("IntrudeStop");
}
var listenStatus;
function aOCX_EVTListen(){
	alert("aOCX_EVTListen");
	listenStatus="EVTListenBegin";
	showAgentList("监听");
	//aOCX.ShowListenDlg();
	//操作日志
	addLog("ShowListenDlg");
}
/**
 * 强插按钮功能
 */
function CmdIntrudeToAgent(agentId)
{
	aOCX.CmdIntrudeAgent(agentId);
	intrudeStatus=""
	addLog("IntrudeToAgent"+agentId);
}
 
 
function btnIntrude_onclick(){
	aOCX.CmdIntrude();
	//showAgentList("强插");
	//aOCX.ShowListenDlg();
	//操作日志
	//addLog("ShowListenDlg");
}
/**
 * 拦截按钮功能
 */
function btnRopCall_onclick(){
	aOCX.CmdRopCall();
	//操作日志
	addLog("RopCall");
}

function aOCX_EVTTransfer(AgentList){
	aOCX.ShowTransferDlg();
	//操作日志
	addLog("ShowTransferDlg");
}
/**
 * 传真按钮功能
 */
function btnFax_onclick(){
	aOCX.CmdFax();
	//操作日志
	addLog("Fax");
}
function aOCX_EVTRecord(fileName){
	//alert("file:"+fileName+"bizNo:"+curBizNo+"bizId:"+curBizId);
	var url="${ctx}/record/save";
	//alert(url);
	$.post(
			url,
			{orderId:curBizId,orderCode:curBizNo,recordPath:fileName},
			function(data){
					//alert(data);
					if(data.type=="result"&&data.result=="1")
					{
						//agentWindow.aOCX_setAgentStatus(status,senconds);
						//操作日志
						//addLog("record "+fileName);	
						//alert("录音记录已保存");
						//uiBase.getParentTabWindow().$("form").submit();
						//uiBase.trigerParentTabFunc('$("form").submit()');
						//uiBase.closeCurrentTab();
					}
				});
}

/**
 * 状态设置按钮
 */
function btn_setAgentStatus()
{
	 /*parent.uiBase.showDialog({
		title: "状态设置",
		width: 640,
		height: 350,
		closed: false,
		url: '${ctx}/softphone/agentstatus?_x='+Math.random(),
		});
	top.dialog({
		title: "状态设置",
		width: 640,
		height: 350,
		closed: false,
		url: '${ctx}/softphone/agentstatus?_x='+Math.random(),
		});*/
	art.dialog.open('${ctx}/softphone/agentstatus?_x='+Math.random(),{title: '状态设置',width: '392px',
				 follow:document.getElementById("js-status"),
                padding: '10px',cancel:function(){},
                cancelVal:'返回',
                id: 'dialog_zhuagntai'});
		
}

/**
 *  设置座席状态
 *	undo 持续时间过后给出持续的提示信息
 */
function aOCX_setAgentStatus(status,senconds)
{
	if(status=="AgentStatus_1")
	{
		aOCX.CmdContinue();
		//操作日志
		addLog("WrapEnd");
	}
	else
	{
		aOCX.CmdPause();
		//操作日志
		addLog("Pause");
	}
	
}
/*
 *转IVR
 */
function aOCX_CmdAuto(flow,agentId)
{
	aOCX.DoSetAssociatedData("AgentID",agentId);
	aOCX.CmdAuto2(flow,"BLINDTRANSFER");
	//操作日志
	addLog("Auto:" + agentId);
}
/*
 *转IVR按钮
 */
function btnCMDAuto()
{
	
	/*parent.uiBase.showDialog({
		title: "转IVR",
		width: 640,
		height: 200,
		closed: false,
		cache: false,
		href: '${ctx}/softphone/agentivr?_x='+Math.random(),
		iframe:true,
		modal: false,
		resizable:true
		});*/
	art.dialog.open('${ctx}/softphone/agentivr?_x='+Math.random(),{title: '转IVR',width: '392px',
		 follow:document.getElementById("js-CMDAuto"),
         padding: '10px',cancel:function(){},
         cancelVal:'返回',
         id: 'dialog_ivr'});
}
/**
 *  结束话后处理
 */
function btnAfterCall_onclick()
{
	aOCX.WrapEnd ();
	//操作日志
	addLog("WrapEnd");
}

/**
 *  根据状态设置按钮显示
 */

 var callid = null;
function aOCX_EVTReturnStatus(status)
{
	
	
	$("#callChannel1").attr("class","call-free");
	document.getElementById("btnAfterCall").disabled = true;
	$("#AfterCallbtn").attr("class","disabled");
	$("#agentStatusIcon").attr("class","fa");
	<c:if test="${!(empty debug) and debug == '1'}"> 
	document.getElementById("msg").innerHTML=document.getElementById("msg").innerHTML+"<li>"+status+":"+AgentStatusCH+"</li>";
	</c:if>
	//alert(status);
	//添加判断，点击时对于状态的判断的计时 停止
	if(_agentStatusFlag){
		window.clearInterval(_agentStatusFlag);
		_agentStatusFlag=null;
	}
	//console.log(status);
	switch(status){
    case "Hook":
    	document.getElementById("btnHook").value = sTitle;
    	document.getElementById("btnHook").disabled = bDisable;
    	if(bDisable)
    		$("#Hookbtn").attr("class","disabled");
    	else{
    		
    		$("#Hookbtn").attr("class","");
    		$("#HookbtnSpan").html(sTitle);
    		
    		}
    	
    	break;
    case "Hold":
    	
    	 $("#Consultbtn").attr("class","disabled");
		document.getElementById("btnHold").value = sTitle;
		document.getElementById("btnHold").disabled = bDisable;
		if(bDisable)
    		$("#Holdbtn").attr("class","disabled");
    	else
    		$("#Holdbtn").attr("class","");
    	$("#HoldbtnSpan").html(sTitle);
		break;
    case "Transfer":
    	document.getElementById("btnTransfer").value = sTitle;
    	document.getElementById("btnTransfer").disabled = bDisable;
    	if(bDisable)
    		$("#Transferbtn").attr("class","disabled");
    	else
    		$("#Transferbtn").attr("class","");
    	
    	$("#TransferbtnSpan").html(sTitle);
    	
    	break;
    case "DialOut":
    	//alert("DialOut");
    	//var sName = aOCX.DoGetAssociatedData("callid");
		
		
    	document.getElementById("btnDial").value = sTitle;
    	document.getElementById("btnDial").disabled = bDisable;
    	if(bDisable)
    		$("#Dialbtn").attr("class","disabled");
    	else
    		$("#Dialbtn").attr("class","");
    	//$("#DialbtnSpan").html(sTitle);
    	
    	break;
    case "Consultation":
		document.getElementById("btnConsult").value = sTitle;
		document.getElementById("btnConsult").disabled = bDisable;
		if(bDisable)
    		$("#Consultbtn").attr("class","disabled");
    	else
    		$("#Consultbtn").attr("class","");
    	$("#ConsultbtnSpan").html(sTitle);
		
		break;
    case "Auto":
    	document.getElementById("btnAuto").value = sTitle;
    	document.getElementById("btnAuto").disabled = bDisable;
    	
    	if(bDisable)
    		$("#Autobtn").attr("class","disabled");
    	else
    		$("#Autobtn").attr("class","");
    	$("#AutobtnSpan").html(sTitle);
    	break;
    case "OutPhone":
    	//alert("OutPhone");
    	//document.getElementById("btnOutPhone").value = sTitle;
    	//document.getElementById("btnOutPhone").disabled = bDisable;break;
    case "Fax":
    	document.getElementById("btnFax").value = sTitle;
    	document.getElementById("btnFax").disabled = bDisable;break;
    case "Pause":
    	phoneNumber="";
    	curBizNo="";
    	curBizId="";
    	document.getElementById("btnPause").value = "空闲";
    	document.getElementById("btnPause").disabled = false;
    	document.getElementById("btnHook").disabled = true;
		document.getElementById("btnHold").disabled = true;
		document.getElementById("btnTransfer").disabled = true;
		document.getElementById("btnConsult").disabled = true;
		document.getElementById("btnConsultRedirect").disabled = true;
		document.getElementById("btnAuto").disabled = true;
		document.getElementById("btnHold").disabled = true;
		document.getElementById("btnConference").disabled = true;
		document.getElementById("btnAgentStatus").disabled = false;
	
		$("#PausebtnSpan").html("空闲");
		$("#ListenbtnSpan").html("监听");
		$("#ListenbtnSpan").html("监听");
		
		$("#Pausebtn").attr("class","disabled");
		$("#Hookbtn").attr("class","disabled");
		$("#Holdbtn").attr("class","disabled");
		$("#Transferbtn").attr("class","disabled");
		$("#Consultbtn").attr("class","disabled");
		$("#ConsultRedirectbtn").attr("class","disabled");
		$("#Autobtn").attr("class","disabled");
		$("#Holdbtn").attr("class","disabled");
		$("#Conferencebtn").attr("class","disabled");
		$("#AgentStatusbtn").attr("class","disabled");
		
		break;
	case "ING":
		//alert("ING"+1027);
		//document.getElementById("btnLogOut").disabled = true;
		$("#agentStatusIcon").attr("class","fa fa-phone");
		if(AgentStatusCH=="磋商进入中状态")
		{
			//磋商接受方
			document.getElementById("btnHook").value="接受";
			document.getElementById("btnHook").disabled = false;
			document.getElementById("btnLogOut").disabled = true;
			
			$("#ConsultbtnSpan").html("停止");
			$("#HookbtnSpan").html("接受");
			$("#Hookbtn").attr("class","");
			$("#Loginbtn").attr("class","disabled");
			$("#Pausebtn").attr("class","disabled");//忙碌(空闲)按钮
			
		}
		if(AgentStatusCH=="呼叫进入中状态")
		{
			
			
			var sName = aOCX.DoGetAssociatedData("callid");
			
	    	addCallLog("startRing",sName);
			
			document.getElementById("btnHook").value="接听";
			document.getElementById("btnHook").disabled = false;
			document.getElementById("btnLogOut").disabled = true;
			
			$("#HookbtnSpan").html("接听");
			$("#Hookbtn").attr("class","");
			$("#Loginbtn").attr("class","disabled");
			$("#Pausebtn").attr("class","disabled");
		}
		if(AgentStatusCH=="查询中状态")
		{
			document.getElementById("btnConsult").value="停止";
			document.getElementById("btnConsult").disabled = false;
			
			$("#ConsultbtnSpan").html("停止");
			$("#Consultbtn").attr("class","");
		}
		if(AgentStatusCH=="外拨中状态")
		{
			//alert("INGDAILOUT");
			//aOCX.DoSetAssociatedData( "callid","123456");
			callid = addCallLog("INGDAILOUT","abc");//记录外拨
			
			$("#DialbtnSpan").html("取消");
			
		}
		if(AgentStatusCH=="磋商中状态")
		{
			//磋商发起方
			$("#ConsultbtnSpan").html("停止");
			
			 $("#Holdbtn").attr("class","disabled");//保持
		    $("#Hookbtn").attr("class","disabled");//挂断（接听）
		    $("#Autobtn").attr("class","disabled");//转IVR
		    $("#ConsultRedirectbtn").attr("class","disabled");//转接
		    $("#Conferencebtn").attr("class","disabled");//会议
			
		}
		if(AgentStatusCH=="磋商外线中状态")
		{
			$("#Transferbtn").attr("class","");
	    	$("#Conferencebtn").attr("class","disabled");//会议
			$("#ConsultRedirectbtn").attr("class","disabled")//转接;
			
			 $("#Holdbtn").attr("class","disabled");//保持
	    	$("#Hookbtn").attr("class","disabled");//挂断（接听）
	    	$("#Autobtn").attr("class","disabled");//转IVR
			
			$("#ConsultbtnSpan").html("停止");
		}
		if(AgentStatusCH=="磋商会议中状态")
		{
			$("#Hookbtn").attr("class","");
		    $("#Pausebtn").attr("class","disabled");
			$("#Listenbtn").attr("class","disabled");
			$("#Disconnectbtn").attr("class","disabled");
			$("#RopCallbtn").attr("class","disabled");
			$("#Loginbtn").attr("class","disabled");
			$("#Holdbtn").attr("class","disabled");
			$("#Transferbtn").attr("class","disabled");
			$("#Consultbtn").attr("class","disabled");
			$("#ConsultRedirectbtn").attr("class","disabled");
			$("#Autobtn").attr("class","disabled");
			$("#Conferencebtn").attr("class","disabled");
			$("#AfterCallbtn").attr("class","disabled");
			$("#AgentStatusbtn").attr("class","disabled");
		}
		if(AgentStatusCH=="被加入会议中状态")
		{
			$("#Hookbtn").attr("class","disabled");
		    $("#Pausebtn").attr("class","disabled");
			$("#Listenbtn").attr("class","disabled");
			$("#Disconnectbtn").attr("class","disabled");
			$("#RopCallbtn").attr("class","disabled");
			$("#Loginbtn").attr("class","disabled");
			$("#Holdbtn").attr("class","disabled");
			$("#Transferbtn").attr("class","disabled");
			$("#Consultbtn").attr("class","disabled");
			$("#ConsultRedirectbtn").attr("class","disabled");
			$("#Autobtn").attr("class","disabled");
			$("#Conferencebtn").attr("class","disabled");
			$("#AfterCallbtn").attr("class","disabled");
			$("#AgentStatusbtn").attr("class","disabled");
		}
		break;
	 case "AUX":
		//操作日志
		addLog("CmdPause");
		
		if($("#DialbtnSpan").html()=="取消"){
			$("#DialbtnSpan").html("外拨");
			if(callid !=null){
	    		//sName = callid;
	    		addCallLog("canceldailout",callid);
	    	}
			
			
		}
		
    	document.getElementById("btnPause").value = "空闲";
    	document.getElementById("btnPause").disabled = false;
    	document.getElementById("btnHook").disabled = true;
		document.getElementById("btnHold").disabled = true;
		document.getElementById("btnTransfer").disabled = true;
		document.getElementById("btnConsult").disabled = true;
		document.getElementById("btnConsultRedirect").disabled = true;
		document.getElementById("btnAuto").disabled = true;
		document.getElementById("btnHold").disabled = true;
		document.getElementById("btnConference").disabled = true;
		document.getElementById("btnAgentStatus").disabled = false;
		document.getElementById("btnListen").disabled = false;
		document.getElementById("btnDisconnect").disabled = false;
		document.getElementById("btnRopCall").disabled = false;
		document.getElementById("btnLogOut").disabled = false;
		document.getElementById("btnDial").disabled = false;
		
		$("#LogInbtn").attr("class","");
		$("#LoginbtnSpan").html("签出");
		$("#Pausebtn").attr("class","");
		$("#PausebtnSpan").html("空闲");
		$("#ListenbtnSpan").html("监听");
		
		
		$("#Loginbtn").attr("class","");
		$("#Dialbtn").attr("class","");
		$("#AgentStatusbtn").attr("class","");
		$("#Listenbtn").attr("class","");
		
		
		$("#Hookbtn").attr("class","disabled");
		$("#Holdbtn").attr("class","disabled");
		$("#Transferbtn").attr("class","disabled");
		$("#Consultbtn").attr("class","disabled");
		$("#ConsultRedirectbtn").attr("class","disabled");
		$("#Autobtn").attr("class","disabled");
		$("#Holdbtn").attr("class","disabled");
		$("#Conferencebtn").attr("class","disabled");
		$("#Disconnectbtn").attr("class","disabled");
		$("#RopCallbtn").attr("class","disabled");
		
		break;
		case "Connection closed":
			$("#LogInbtn").attr("class","");
			$("#LoginbtnSpan").html("签入");
			//document.getElementById("btnLogIn").disabled = false;
			//document.getElementById("btnLogOut").disabled = true;
			//document.getElementById("txtStatus").value="未登录";
			$("#agentStatus").html("未签入");
			SetButtoninit();
			break;
		case "Not Login":
			;
			break;
    case "CONFERENCE":
    	/* document.getElementById("btnConference").value = sTitle;
    	document.getElementById("btnConference").disabled = bDisable;
    	$("#agentStatusIcon").attr("class","fa fa-phone");
    	if(bDisable)
    		$("#Conferencebtn").attr("class","disabled");
    	else
    		$("#Conferencebtn").attr("class","");
    	$("#ConferencebtnSpan").html(sTitle); */
    	$("#Hookbtn").attr("class","");
    	$("#HookbtnSpan").html("挂机");
	    $("#Pausebtn").attr("class","disabled");
		$("#Listenbtn").attr("class","disabled");
		$("#Disconnectbtn").attr("class","disabled");
		$("#RopCallbtn").attr("class","disabled");
		$("#Loginbtn").attr("class","disabled");
		$("#Holdbtn").attr("class","disabled");
		$("#Transferbtn").attr("class","disabled");
		$("#Consultbtn").attr("class","disabled");
		$("#ConsultRedirectbtn").attr("class","disabled");
		$("#Autobtn").attr("class","disabled");
		$("#Conferencebtn").attr("class","disabled");
		$("#AfterCallbtn").attr("class","disabled");
		$("#AgentStatusbtn").attr("class","disabled");
    	break;
    case "Play":
		
		document.getElementById("btnPause").value = "忙碌";
		document.getElementById("btnListen").disabled = true;
		document.getElementById("btnDisconnect").disabled = true;
		document.getElementById("btnRopCall").disabled = true;
		document.getElementById("btnLogOut").disabled = true;
		
		$("#PausebtnSpan").html(sTitle);
		$("#Listenbtn").attr("class","disabled");
		$("#Disconnectbtn").attr("class","disabled");
		$("#RopCallbtn").attr("class","disabled");
		$("#Loginbtn").attr("class","disabled");
		
    	break;
    
    case "CONSULTED":
    
    	document.getElementById("btnHold").value = "保持";
    	document.getElementById("btnHook").value = "挂机";
    	document.getElementById("btnConsult").value="停止";
    	document.getElementById("btnPause").disabled = true;
		document.getElementById("btnListen").disabled = true;
		document.getElementById("btnDisconnect").disabled = true;
		document.getElementById("btnRopCall").disabled = true;
		document.getElementById("btnLogOut").disabled = true;
		document.getElementById("btnHook").disabled = true;
		document.getElementById("btnHold").disabled = true;
		document.getElementById("btnTransfer").disabled = true;
		document.getElementById("btnConsult").disabled = false;
		document.getElementById("btnConsultRedirect").disabled = true;
		document.getElementById("btnAuto").disabled = true;
		document.getElementById("btnConference").disabled = true;
		
		$("#HoldbtnSpan").html("保持");
		$("#HookbtnSpan").html("挂机");
		$("#ConsultbtnSpan").html("停止");
		
		$("#Pausebtn").attr("class","disabled");
		$("#Listenbtn").attr("class","disabled");
		$("#Disconnectbtn").attr("class","disabled");
		$("#RopCallbtn").attr("class","disabled");
		$("#Loginbtn").attr("class","disabled");
		$("#Hookbtn").attr("class","disabled");
		$("#Holdbtn").attr("class","disabled");
		$("#Transferbtn").attr("class","disabled");
		$("#Consultbtn").attr("class","");
		$("#ConsultRedirectbtn").attr("class","disabled");
		$("#Autobtn").attr("class","disabled");
		$("#Conferencebtn").attr("class","disabled");
		$("#agentStatusIcon").attr("class","fa fa-phone");
		break;
    case "ACD":
    	//alert(status);
    	var sName = aOCX.DoGetAssociatedData("callid");
    	if(callid !=null){
    		sName = callid;
    	}
    	
    	addCallLog("ACD",sName);
    	$("#Dialbtn").attr("class","disabled");
    	
    	$("#ConsultbtnSpan").html("磋商");
    	 document.getElementById("btnHold").value = "保持";
		 document.getElementById("btnHook").value = "挂机";
    	
		document.getElementById("btnPause").disabled = true;
		document.getElementById("btnListen").disabled = true;
		document.getElementById("btnDisconnect").disabled = true;
		document.getElementById("btnRopCall").disabled = true;
		document.getElementById("btnLogOut").disabled = true;

		document.getElementById("btnHook").disabled = false;
		
		document.getElementById("btnHold").disabled = false;
		document.getElementById("btnTransfer").disabled = false;
		document.getElementById("btnConsult").disabled = false;
		document.getElementById("btnConsultRedirect").disabled = false;
		document.getElementById("btnAuto").disabled = false;
		document.getElementById("btnHold").disabled = false;
		document.getElementById("btnConference").disabled = false;
		
		ConsultAgentId="";
		
		$("#agentStatusIcon").attr("class","fa fa-phone");
		$("#HoldbtnSpan").html("保持");
		$("#HookbtnSpan").html("挂机");
		$("#Pausebtn").attr("class","disabled");
		$("#Listenbtn").attr("class","disabled");
		$("#Disconnectbtn").attr("class","disabled");
		$("#RopCallbtn").attr("class","disabled");
		$("#Loginbtn").attr("class","disabled");
		
		$("#Hookbtn").attr("class","");
		$("#Holdbtn").attr("class","");
		//$("#Transferbtn").attr("class","");
		$("#Consultbtn").attr("class","");
		//$("#ConsultRedirectbtn").attr("class","");
		$("#Autobtn").attr("class","");
		//$("#Conferencebtn").attr("class","");
		$("#callChannel1").attr("class","call-busy");
    break;
    case "HOLD":
    	
    	 $("#Consultbtn").attr("class","disabled");//磋商
    	$("#Hookbtn").attr("class","disabled");//挂断（接听）
    	$("#Autobtn").attr("class","disabled");//转IVR
    	
    document.getElementById("btnHold").value = "恢复";
    document.getElementById("btnHold").disabled = false;
    
    $("#HoldbtnSpan").html("恢复");
    $("#Holdbtn").attr("class","");
    
    break;
	case "AVAIL":
		//操作日志
		addLog("CmdContinue");
		//alert(status);
		document.getElementById("btnConsult").value="磋商";
		document.getElementById("btnPause").value = "忙碌";
		document.getElementById("btnHold").value = "保持";
		document.getElementById("btnHook").value = "接听";
		
		
		document.getElementById("btnPause").disabled = false;
		document.getElementById("btnListen").disabled = true;
		document.getElementById("btnDisconnect").disabled = true;
		document.getElementById("btnRopCall").disabled = true;
		document.getElementById("btnLogOut").disabled = true;
		document.getElementById("btnHook").disabled = true;
		document.getElementById("btnHold").disabled = true;
		document.getElementById("btnTransfer").disabled = true;
		document.getElementById("btnConsult").disabled = true;
		document.getElementById("btnConsultRedirect").disabled = true;
		document.getElementById("btnAuto").disabled = true;
		document.getElementById("btnConference").disabled = true;
		
		$("#ConsultbtnSpan").html("磋商");
		$("#PausebtnSpan").html("忙碌");
		
		//alert($("span",$("#Pausebtn")).html());
		
		$("#HoldbtnSpan").html("保持");
		$("#HookbtnSpan").html("接听");

		
		
		$("#Pausebtn").attr("class","");
		$("#Listenbtn").attr("class","disabled");
		$("#Disconnectbtn").attr("class","disabled");
		$("#RopCallbtn").attr("class","disabled");
		$("#Loginbtn").attr("class","disabled");
		$("#Hookbtn").attr("class","disabled");
		$("#Dialbtn").attr("class","disabled");
		$("#Holdbtn").attr("class","disabled");
		$("#Transferbtn").attr("class","disabled");
		$("#Consultbtn").attr("class","disabled");
		$("#ConsultRedirectbtn").attr("class","disabled");
		$("#Autobtn").attr("class","disabled");
		$("#Conferencebtn").attr("class","disabled");
    break;
    case "ACW":
		//alert(status);
		var sName = aOCX.DoGetAssociatedData("callid");
		if( callid !=null){
    		sName = callid;
    	}
		addCallLog("ACW",sName);
		if(callid!=null){
			
			callid = null;
		}
		
    	/* if(!addCallLog("ACW",sName)){
    		return false;
    	}; */
		document.getElementById("btnHold").value = "保持";
		document.getElementById("btnPause").disabled = true;
		document.getElementById("btnListen").disabled = true;
		document.getElementById("btnDisconnect").disabled = true;
		document.getElementById("btnRopCall").disabled = true;
		document.getElementById("btnLogOut").disabled = true;
		document.getElementById("btnHook").disabled = true;
		document.getElementById("btnHold").disabled = true;
		document.getElementById("btnTransfer").disabled = true;
		document.getElementById("btnConsult").disabled = true;
		document.getElementById("btnConsultRedirect").disabled = true;
		document.getElementById("btnAuto").disabled = true;
		document.getElementById("btnConference").disabled = true;
	    document.getElementById("btnAfterCall").disabled = false;
	    
	    $("#HoldbtnSpan").html("保持");
	    
	    $("#Pausebtn").attr("class","disabled");
		$("#Listenbtn").attr("class","disabled");
		$("#Disconnectbtn").attr("class","disabled");
		$("#RopCallbtn").attr("class","disabled");
		$("#Loginbtn").attr("class","disabled");
		$("#Hookbtn").attr("class","disabled");
		$("#Holdbtn").attr("class","disabled");
		$("#Transferbtn").attr("class","disabled");
		$("#Consultbtn").attr("class","disabled");
		$("#ConsultRedirectbtn").attr("class","disabled");
		$("#Autobtn").attr("class","disabled");
		$("#Conferencebtn").attr("class","disabled");
		$("#AfterCallbtn").attr("class","");
		$("#AgentStatusbtn").attr("class","disabled");
		$("#Dialbtn").attr("class","disabled");
		
        break;
    case "RESULT":
    	if(AgentStatusCH=="显示磋商查询结果状态")
		{
    		$("#ConsultbtnSpan").html("取消");
		}
    	break;
    case "CONSULT":
    	$("#Transferbtn").attr("class","");
    	$("#Conferencebtn").attr("class","");//会议
		$("#ConsultRedirectbtn").attr("class","");//转接
		//$("#ConsultRedirectbtn").attr("class","disabled")
		//$("#Conferencebtn").attr("class","disabled");//会议
    	break;
    case "LISTEN":
    	$("#Hookbtn").attr("class","disabled");
	    $("#Pausebtn").attr("class","disabled");
		$("#Listenbtn").attr("class","");
		$("#Disconnectbtn").attr("class","disabled");
		$("#RopCallbtn").attr("class","disabled");
		$("#Loginbtn").attr("class","disabled");
		$("#Holdbtn").attr("class","disabled");
		$("#Transferbtn").attr("class","disabled");
		$("#Consultbtn").attr("class","disabled");
		$("#ConsultRedirectbtn").attr("class","disabled");
		$("#Autobtn").attr("class","disabled");
		$("#Conferencebtn").attr("class","disabled");
		$("#AfterCallbtn").attr("class","disabled");
		$("#AgentStatusbtn").attr("class","disabled");
		$("#Dialbtn").attr("class","disabled");
		$("#ListenbtnSpan").html("停止");
		break;
    	//document.getElementById("btnListen").value = sTitle;
    	//document.getElementById("btnListen").disabled = bDisable;break;     
    case "Disconnect":
        //'SetButtonOption Title, Enable, Me.Disconnect
        //document.getElementById("btnDisconnect").value = sTitle;
       // document.getElementById("btnDisconnect").disabled = bDisable;break;
    case "RopCall":
    	//document.getElementById("btnRopCall").value = sTitle;
    	//document.getElementById("btnRopCall").disabled = bDisable;break;
    case "Init":
        SetButtonInit();break;
    case "外线磋商状态":
    	$("#Conferencebtn").attr("class","");//会议
    	break;
    default:
    	if("Login Failed"==status)
    	{
    		art.dialog({title: '提示',icon:'warning',lock: true,content: "座席已签入，请使用其他座席签入。",cancel:function(){},cancelVal:"返回"});
    		addLog("LoginFailed");
    	}
    	else
    	{
    		//alert(status);
    	}
		
     //SetButtonInit();
	 break;
	}
	$(".nav>li").click(function(){
		if($(this).hasClass("disabled")){
			return false;
		}
	});
}

function aOCX_EVTWrapUp()
{
	
	//alert("aOCX_EVTWrapUp");
	//alert(AgentStatusCHBefore);
	if(AgentStatusCHBefore=="结束监听中状态"||AgentStatusCHBefore=="监听状态")
	{
		aOCX.WrapEnd();
		aOCX.CmdPause();
	}
}
function aOCX_EVTListenStopSucc()
{
	//alert("aOCX_EVTListenStopSucc");
}
function aOCX_EVTIntrude()
{
	showAgentList("强插");
	//aOCX.ShowListenDlg();
	//操作日志
	addLog("ShowIntrudeDlg");
}
</script>

<SCRIPT type="text/javascript" FOR="aOCX"
	EVENT="EVTReturnStatusCH(status)">
    aOCX_EVTReturnStatusCH(status);
 </SCRIPT>
<SCRIPT type="text/javascript" FOR="aOCX"
	EVENT="EVTReturnStatus(status)">
    aOCX_EVTReturnStatus(status);
 </SCRIPT>
<SCRIPT type="text/javascript" FOR="aOCX"
	EVENT="EVTButtonStatus(sName,sTitle,bEnable)">
 	aOCX_EVTButtonStatus(sName,sTitle,bEnable);
 </SCRIPT>
<SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTLoginSuc()">
  	aOCX_EVTLoginSuc();
 </SCRIPT>
<SCRIPT type="text/javascript" FOR="aOCX"
	EVENT="CallArrive(sAni,sDnis,sData)">
   	aOCX_CallArrive(sAni,sDnis,sData);
 </SCRIPT>
<SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTAnswerSucc()">
  	aOCX_EVTAnswerSucc();
 </SCRIPT>
<SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTDialOut()">
   	aOCX_EVTDialOut();
 </SCRIPT>
<SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTConsult(agentList)">
    aOCX_EVTConsult(agentList);
 </SCRIPT>
<SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTConsultSucc()">
     aOCX_EVTConsultSucc();
 </SCRIPT>
<SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTTransfer(AgentList)">
     aOCX_EVTTransfer(AgentList);
 </SCRIPT>
<SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTListen()">
   aOCX_EVTListen();
 </SCRIPT>
 
<SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTListenStopSucc()">
   aOCX_EVTListenStopSucc();
 </SCRIPT>

<SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTRecord(fileName)">
   aOCX_EVTRecord(fileName);
 </SCRIPT>

<SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTWrapUp()">
   aOCX_EVTWrapUp();
 </SCRIPT>
 
 <script type="text/javascript"  FOR="aOCX" EVENT="EVTIntrude()">
 aOCX_EVTIntrude();
 </script>







</head>


<body onload="return window_onload()">

	<!--header start-->
	<div class="container-fluid header">
		<ul class="header-seperation">
			<li class="logo"></li>
			<li class="logo-font">昌邑民生热线</li>
		</ul>
		<chr:perm code="softphone_agent">
			<ul class="call-f">
				<li class="call-left">
					<div class="call-free" id="callChannel1">
						<span>1</span>
					</div>
					<div class="call-free" id="callChannel2">
						<span>2</span>
					</div>
					<div class="call-free" id="callChannel3">
						<span>3</span>
					</div>
				</li>
				<li class="call-center">
					<div class="call-input" id="callNumber"></div>
					<div class="font-12 bold">
						<i class="fa " id="agentStatusIcon"></i> <span id="agentStatus">未签入</span>
						<span class="right" id="callDuration"></span>
					</div>
				</li>
				<li class="call-right">
					<div class="call-right-l" onclick="btnCallAnswer();"></div>
					<div class="call-right-r" onclick="btnCallHandup();"></div>
				</li>
			</ul>
		</chr:perm>
		<ul class="nav">
			<chr:perm code="softphone_agent">
				<li id="Loginbtn" onclick=""><a
					href="javascript:btnLoginAndLogout_onclick();"><i
						class="fa fa-sign-in"></i><span id="LoginbtnSpan">签入</span></a></li>
				<li class="disabled" id="Pausebtn" onclick=""><a
					href="javascript:btnPause_onclick();"><i
						class="fa fa-minus-circle"></i><span id="PausebtnSpan">空闲</span></a></li>
				<li class="disabled" id="Hookbtn" onclick=""><a
					href="javascript:btnHook_onclick();"><i class="fa fa-phone"></i><span
						id="HookbtnSpan">接听</span></a></li>
				<li class="disabled" id="Holdbtn" onclick=""><a
					href="javascript:btnHold_onclick();"><i class="fa fa-music"></i><span
						id="HoldbtnSpan">保持</span></a></li>
				<li class="disabled" id="Autobtn" onclick=""><a
					href="javascript:btnCMDAuto();" id="js-CMDAuto"><i
						class="fa fa-external-link"></i><span id="AutobtnSpan">转IVR</span></a></li>
				<li class="disabled" id="Dialbtn" onclick=""><a
					href="javascript:btnDial_onclick();" id="js-dialer"
					class="js-dialer"><i class="fa fa-phone-square"></i><span
						id="DialbtnSpan">外拨</span></a></li>
				<li class="disabled" id="AfterCallbtn" onclick=""><a
					href="javascript:btnAfterCall_onclick();"><i class="fa fa-road"></i><span
						id="AfterCallbtnSpan">话后</span></a></li>
				<div style="display:none"><li class="disabled" id="AgentStatusbtn" onclick="" ><a
					href="javascript:btn_setAgentStatus();" id="js-status"
					class="js-status"><i class="fa fa-clipboard"></i><span
						id="AgentStatusbtnSpan">状态</span></a></li></div>
			
			<!-- <li class="disabled" id="btnPause" onclick=""><a href="javascript:btnLoginAndLogout_onclick();"><i class="fa fa-flash"></i><span>释放</span></a></li>-->
			
				<li class="disabled" id="Consultbtn" onclick=""><a
					href="javascript:btnConsult_onclick();" id="js-consultations"
					class="js-consultations"><i class="fa fa-comments-o"></i><span
						id="ConsultbtnSpan">磋商</span></a></li>
				<li class="disabled" id="ConsultRedirectbtn" onclick=""><a
					href="javascript:btnTransfer_onclick();" id="js-ConsultRedirect"><i
						class="fa fa-share-square-o"></i><span id="ConsultRedirectbtnSpan">转接</span></a></li>
				<li class="disabled" id="Conferencebtn" onclick=""><a
					href="javascript:btnConference_onclick();"><i
						class="fa fa-comments"></i><span id="ConferencebtnSpan">会议</span></a></li>
			</chr:perm>
			</ur>
			<ul class="nav" id="softphone_admin_contoller" style="display:none">
			
	
				<li class="disabled" id="Listenbtn" onclick=""><a
					href="javascript:btnListen_onclick();" id="js-Listen"><i
						class="fa fa-shield"></i><span id="ListenbtnSpan">监听</span></a></li>
				
				<li class="disabled" id="Disconnectbtn" onclick=""><a
					href="javascript:btnIntrude_onclick();" id="js-Intrude"><i
						class="fa fa-cloud-download"></i><span id="DisconnectbtnSpan">强插</span></a></li>
						
				<li class="disabled" id="RopCallbtn" onclick=""><a
					href="javascript:btnRopCall_onclick();"><i class="fa fa-ban"></i><span
						id="RopCallbtnSpan">拦截</span></a></li>
			
				<!-- <button onclick="showVideo(110);">视频测试</button> -->
			
		</ul>
	</div>
	<!--header end-->
	<TABLE cellSpacing=1 cellPadding=1 width="75%" border=0>
		<TR>
			<TD><INPUT id=Ani></TD>
			<TD><INPUT id="btnLogin" type="button" value="签入"
				onclick='btnLogin_onclick()'></TD>
			<TD><INPUT id=btnLogOut type=button value=签出
				onclick='btnLogOut_onclick()'></TD>
			<TD><INPUT type=button value=忙碌 id=btnPause
				onclick='btnPause_onclick()'></TD>
			<TD><INPUT type=button value=接听 id=btnHook name=button1
				onclick='btnHook_onclick()'></TD>
			<TD><INPUT type=button value=保持 id=btnHold
				onclick='btnHold_onclick()'></TD>
			<TD><INPUT type=button value=转接 id=btnTransfer
				onclick='btnTransfer_onclick()'></TD>
			<TD><INPUT type=button value=外拨 id=btnDial
				onclick='btnDial_onclick()'></TD>
			<TD><INPUT type=button value=磋商 id=btnConsult
				onclick='btnConsult_onclick()'></TD>
			<TD><INPUT type=button value=磋商转接 id=btnConsultRedirect
				onclick='btnConsultTransfer()'></TD>
			<TD><INPUT type=button value=IVR转接 id=btnAuto
				onclick='btnCMDAuto()'></TD>
			<!-- <TD><INPUT type=button value=外线 id=btnOutPhone onclick=''></TD> -->
			<!-- <TD><INPUT type=button value=传真 id=btnFax onclick='btnFax_onclick()'></TD> -->
			<TD><INPUT type=button value=会议 id=btnConference
				onclick='btnConference_onclick()'></TD>
			<TD><INPUT type=button value=话后完成 id=btnAfterCall
				onclick='btnAfterCall_onclick()'></TD>

			<TD><INPUT type=button value=监听 id=btnListen
				onclick='btnListen_onclick()'></TD>
			<TD><INPUT type=button value=强拆 id=btnDisconnect
				onclick='btnDisconnect_onclick'></TD>
			<TD><INPUT type=button value=拦截 id=btnRopCall
				onclick='btnRopCall_onclick()'></TD>

			<TD><INPUT type=button value=状态 id=btnAgentStatus
				onclick='btn_setAgentStatus()'></TD>

			<TD><INPUT id=btnConfigure type=button value=配置
				onclick='btnConfigure_onclick()'></TD>


			<TD>当前状态:<INPUT id=txtStatus></TD>

		</TR>
	</TABLE>
	<P>主叫号码：</P>
	<P></P>
	<P>
		<chr:perm code="softphone_agent">
			<OBJECT id=aOCX style="WIDTH: 151px; HEIGHT: 23px"
				data=data:application/x-oleobject;base64,np6Npeezi02KZ8WyuR3P+QADAACbDwAAYQIAAA==
				classid=clsid:A58D9E9E-B3E7-4D8B-8A67-C5B2B91DCFF9></OBJECT>
		</chr:perm>
	</P>


	<P>
	<div id="msg"></div>
	</P>

	<!--  <div id="AgentOcxDownloadDlg" style="display:none;">
			
			请点击链接下载并安装座席软电话控件，安装完毕后重启IE浏览器<br>
			
			<a href="javascript:return false;" onclick="window.open('${ctx}/resources/themes/hotline/ocx/AgentOCXSetup.zip','下载控件')" style="color:red">下载</a>
	</div>-->
	<%@ include file="/WEB-INF/views/hotline/common/hotlinejs.jsp"%>
	<script
		src="${ctx}/resources/general/common/jquery/jquery.fileDownload.js"
		type="text/javascript"></script>
	<script type="text/javascript">
	
	<chr:perm code="softphone_admin">
	
		$("#softphone_admin_contoller").show();
	</chr:perm>
	
	
	/*jQuery.fn.setActive=function () {
		var selectorOne=this.selector;
		if(selectorOne.indexOf("#")>=0)
		{	
		}
		else
		{
			selectorOne="#"+selectorOne;
		}
		$(selectorOne).attr("class","");
		//$(selectorOne).addClass("active");
	};
	jQuery.fn.setActive=function (elId) {
		$("#"+elId).removeClass("disabled");
		//$("#"+elId).addClass("active");
	};
	jQuery.fn.setDisable=function (elId) {
		//$("#"+elId).removeClass("active");
		$("#"+elId).addClass("disabled");
	};
	jQuery.fn.setDisable=function () {
		var selectorOne=this.selector;
		if(selectorOne.indexOf("#")>=0)
		{	
		}
		else
		{
			selectorOne="#"+selectorOne;
		}
		//$(selectorOne).removeClass("active");
		$(selectorOne).addClass("disabled");
	};
	jQuery.fn.setBtnText=function (text){
		var selectorOne=this.selector;
		if(selectorOne.indexOf("#")>=0)
		{	
		}
		else
		{
			selectorOne="#"+selectorOne;
		}
		alert($(selectorOne).find("span").html());
		$(selectorOne).find("span").html(text);
	};
	var setBtnTextById=function (elId,text){
		alert($("#"+elId).find("span").html());
		$("#"+elId).find("span").html(text);
	};*/
	jQuery.fn.getBtnText=function (){
		var selectorOne=this.selector;
		if(selectorOne.indexOf("#")>=0)
		{	
		}
		else
		{
			selectorOne="#"+selectorOne;
		}
		return $(selectorOne).find("span").html();
	};
	jQuery.fn.getBtnTextById=function (elId){
		return $("#"+elId).find("span").html();
	};
	
	
</script>




</body>
</html>