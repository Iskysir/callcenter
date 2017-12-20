<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="random" value="<%=System.currentTimeMillis() %>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>软电话</title>
<%@ include file="/WEB-INF/views/hotline/hotlinehead.jsp"%>
<script type="text/javascript">

var AgentStaff=function()
{
		
	};
var phoneNumber,
curBizNo,curBizId,AgentStatusCH,ConsultAgentId;

var bAgentIDSpeaked = false;
function SetCurBizNo(bizId,bizNo)
{
	curBizNo=bizNo;
	curBizId=bizId;
}
/**
 * 按钮初始化
 */
function SetButtoninit(){
	document.getElementById("btnHook").disabled = true;
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
	document.getElementById("btnAfterCall").disabled = true;
	
	
}
/**
 * 创建加载
 */
function window_onload(){
	SetButtoninit();
}
/**
 * 登录功能
 */
function btnLogin_onclick(){
	aOCX.DatabaseConnectionString="${DatabaseConnectionString}";
	aOCX.SetToolsVisible(0);
	aOCX.AgentID = "${userInfo.obligateA}";
	aOCX.Password = "${userInfo.obligateB}";
	aOCX.DeviceName = "${userInfo.obligateC}";
	//aOCX.AgentID = 'Agent2';
	//aOCX.Password = '2';
	aOCX.LogIn();
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

function aOCX_CallArrive(sAni,sDnis,sData){
	bAgentIDSpeaked = false;
	//alert(sAni);
	document.getElementById("Ani").value = sAni;
	//alert(sData);
	//alert(uiBase.addOneTab);
	//
	//默认磋商不是业务，只有呼叫进入中状态 弹屏
	if("true"!="${sys_consultBiz}"&&AgentStatusCH=="呼叫进入中状态")
	{
		uiBase.addOneTab("呼入:"+sAni,"${ctx}/business/accept/add?infoSource=1&telephone="+sAni);
		addLog("call");
	}
	else
	{
		
	}
			
	phoneNumber=sAni;
}
function aOCX_EVTButtonStatus(status,AgentStatusCH,bEnable)
{
	//document.getElementById("msg").innerHTML=document.getElementById("msg").innerHTML+"<li>"+status+":"+AgentStatusCH+"</li>";
}

function aOCX_EVTReturnStatus(status)
{
	
	document.getElementById("btnAfterCall").disabled = true;
	<c:if test="${!(empty debug) and debug == '1'}"> 
	document.getElementById("msg").innerHTML=document.getElementById("msg").innerHTML+"<li>"+status+":"+AgentStatusCH+"</li>";
	</c:if>
	//alert(status);
	switch(status){
    case "Hook":
    	document.getElementById("btnHook").value = sTitle;
    	document.getElementById("btnHook").disabled = bDisable;break;
    case "Hold":
		document.getElementById("btnHold").value = sTitle;
		document.getElementById("btnHold").disabled = bDisable;break;
    case "Transfer":
    	document.getElementById("btnTransfer").value = sTitle;
    	document.getElementById("btnTransfer").disabled = bDisable;break;
    case "DialOut":
    	document.getElementById("btnDial").value = sTitle;
    	document.getElementById("btnDial").disabled = bDisable;break;
    case "Consultation":
		document.getElementById("btnConsult").value = sTitle;
		document.getElementById("btnConsult").disabled = bDisable;break;
    case "Auto":
    	document.getElementById("btnAuto").value = sTitle;
    	document.getElementById("btnAuto").disabled = bDisable;break;
    case "OutPhone":
    	//document.getElementById("btnOutPhone").value = sTitle;
    	//document.getElementById("btnOutPhone").disabled = bDisable;break;
    case "Fax":
    	document.getElementById("btnFax").value = sTitle;
    	document.getElementById("btnFax").disabled = bDisable;break;
    case "Pause":
    
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
		
		
		
		
	
		break;
	case "ING":
		//document.getElementById("btnLogOut").disabled = true;
		if(AgentStatusCH=="磋商进入中状态")
		{
			document.getElementById("btnHook").value="接受";
			document.getElementById("btnHook").disabled = false;
			document.getElementById("btnLogOut").disabled = true;
		}
		if(AgentStatusCH=="呼叫进入中状态")
		{
			document.getElementById("btnHook").value="接听";
			document.getElementById("btnHook").disabled = false;
			document.getElementById("btnLogOut").disabled = true;
		}
		if(AgentStatusCH=="查询中状态")
		{
			document.getElementById("btnConsult").value="停止";
			document.getElementById("btnConsult").disabled = false;
		}
		break;
	 case "AUX":
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
		
		break;
		case "Connection closed":
			
			document.getElementById("btnLogIn").disabled = false;
			document.getElementById("btnLogOut").disabled = true;
			document.getElementById("txtStatus").value="未登录";
			break;
		case "Not Login":
			;
			break;
    case "Conference":
    	document.getElementById("btnConference").value = sTitle;
    	document.getElementById("btnConference").disabled = bDisable;break;
    case "Play":
		
		document.getElementById("btnPause").value = "忙碌";
		document.getElementById("btnListen").disabled = true;
		document.getElementById("btnDisconnect").disabled = true;
		document.getElementById("btnRopCall").disabled = true;
		document.getElementById("btnLogOut").disabled = true;
    break;
    
    case "CONSULTED":
    document.getElementById("btnPause").disabled = true;
		document.getElementById("btnListen").disabled = true;
		document.getElementById("btnDisconnect").disabled = true;
		document.getElementById("btnRopCall").disabled = true;
		document.getElementById("btnLogOut").disabled = true;
		document.getElementById("btnHold").value = "保持";
		document.getElementById("btnHook").value = "挂机";
		document.getElementById("btnHook").disabled = true;
		
		document.getElementById("btnHold").disabled = true;
		document.getElementById("btnTransfer").disabled = true;
		document.getElementById("btnConsult").value="停止";
		document.getElementById("btnConsult").disabled = false;
		document.getElementById("btnConsultRedirect").disabled = true;
		document.getElementById("btnAuto").disabled = true;
		document.getElementById("btnHold").disabled = true;
		document.getElementById("btnConference").disabled = true;
		break;
    case "ACD":
    //alert(status);
		document.getElementById("btnPause").disabled = true;
		document.getElementById("btnListen").disabled = true;
		document.getElementById("btnDisconnect").disabled = true;
		document.getElementById("btnRopCall").disabled = true;
		document.getElementById("btnLogOut").disabled = true;
		document.getElementById("btnHold").value = "保持";
		 document.getElementById("btnHook").value = "挂机";
		document.getElementById("btnHook").disabled = false;
		
		document.getElementById("btnHold").disabled = false;
		document.getElementById("btnTransfer").disabled = false;
		document.getElementById("btnConsult").disabled = false;
		document.getElementById("btnConsultRedirect").disabled = false;
		document.getElementById("btnAuto").disabled = false;
		document.getElementById("btnHold").disabled = false;
		document.getElementById("btnConference").disabled = false;
		ConsultAgentId="";
		
    break;
    case "HOLD":
    document.getElementById("btnHold").value = "恢复";
    document.getElementById("btnHold").disabled = false;
    break;
	case "AVAIL":
		//alert(status);
		document.getElementById("btnPause").value = "忙碌";
		document.getElementById("btnPause").disabled = false;
		document.getElementById("btnHold").value = "保持";
		document.getElementById("btnListen").disabled = true;
		document.getElementById("btnDisconnect").disabled = true;
		document.getElementById("btnRopCall").disabled = true;
		document.getElementById("btnLogOut").disabled = true;
		document.getElementById("btnHold").value = "保持";
		document.getElementById("btnHook").value = "接听";
		document.getElementById("btnHook").disabled = true;
		
		document.getElementById("btnHold").disabled = true;
		document.getElementById("btnTransfer").disabled = true;
		document.getElementById("btnConsult").value="磋商";
		document.getElementById("btnConsult").disabled = true;
		document.getElementById("btnConsultRedirect").disabled = true;
		document.getElementById("btnAuto").disabled = true;
		document.getElementById("btnHold").disabled = true;
		document.getElementById("btnConference").disabled = true;
    break;
    case "ACW":
		//alert(status);
		document.getElementById("btnPause").disabled = true;
		document.getElementById("btnListen").disabled = true;
		document.getElementById("btnDisconnect").disabled = true;
		document.getElementById("btnRopCall").disabled = true;
		document.getElementById("btnLogOut").disabled = true;
		
		 document.getElementById("btnHold").value = "保持";
		document.getElementById("btnHook").disabled = true;
		
		document.getElementById("btnHold").disabled = true;
		document.getElementById("btnTransfer").disabled = true;
		document.getElementById("btnConsult").disabled = true;
		document.getElementById("btnConsultRedirect").disabled = true;
		document.getElementById("btnAuto").disabled = true;
		document.getElementById("btnHold").disabled = true;
		document.getElementById("btnConference").disabled = true;
	  document.getElementById("btnAfterCall").disabled = false;
        break;
    case "Listen":
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
    default:
		alert(status);
     //SetButtonInit();
	 break;
	}
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
	alert(reason);
}
/**
 * 修改当前状态值
 */
function aOCX_EVTReturnStatusCH(status){
	//alert(status);
	AgentStatusCH=status;
	document.getElementById("txtStatus").value = status;
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
	parent.uiBase.showDialog({
		title: showType,
		width: 640,
		height: 200,
		closed: false,
		cache: false,
		href: '${ctx}/softphone/agentdial?_x='+Math.random(),
		iframe:true,
		modal: false,
		resizable:true
		});
}

/**
 * 外拨按钮功能
 */
function btnDial_onclick(){
	if(document.getElementById("btnDial").value.substring(0, 2) == "外拨"){
		//aOCX.CmdDialOut();
		showDialDialog("外拨");
		
	}else{
		aOCX.CmdMakeCallStop();
		//操作日志
		addLog("MakeCallStop");
	}
}
//
function aOCX_EVTDialOut(){
	uiBase.addOneTab("外拨:"+phoneNumber,"${ctx}/business/accept/add?infoSource=1&telephone="+phoneNumber);
	//document.getElementById("btnDial").value="取消";
	//alert()
	//'showModalDialog "DialOut.htm",window
	//aOCX.ShowDialOut();
	
}
/**
 * 接听按钮功能
 */
function btnHook_onclick(){
	if (document.getElementById("btnHook").value.substring(0, 2) == "接听"){
		aOCX.CmdAnswer();
		//操作日志
		addLog("answer");
    
	}else if (document.getElementById("btnHook").value.substring(0, 2) == "接受"){
		aOCX.CmdConsultAnswer();
		//操作日志
		addLog("ConsultAnswer");
	}
	else{
		aOCX.CmdOnHook();
		//操作日志
		addLog("OnHook");
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
	}else
		aOCX.CmdHoldStop();
		//操作日志
		addLog("HoldStop");
}
/**
 * 转接按钮功能
 */
function btnTransfer_onclick(){
	if(document.getElementById("btnTransfer").value.substring(0, 2)== "转接" ){
		//aOCX.CmdTransfer();
		showDialDialog("转接");
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
	 aOCX.OutPhone(num);
	//操作日志
	 addLog("outphone: "+num);
 }
/**
 * 磋商功能
 */

function CmdConsultToAgent(agentId,anis,dnis)
{
	ConsultAgentId=agentId;
	aOCX.CmdConsultToAgent(agentId,anis,dnis);
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
	addLog("ConsultAgent");
}

/**
*		弹出座席列表界面 磋商/磋商转接/监听/ 
*/
function showAgentList(showType)
{
	  parent.uiBase.showDialog({
		title: showType,
		width: 640,
		height: 200,
		closed: false,
		cache: false,
		href: '${ctx}/softphone/agentlist?_x='+Math.random(),
		iframe:true,
		modal: false,
		resizable:true
		});
}

/**
*		磋商按钮功能
*/
function btnConsult_onclick(){
	//alert("btnConsult_onclick");
	//alert(parent.uiBase);
	
	if(document.getElementById("btnConsult").value.substring(0, 2) == "停止" )
	{
	  aOCX.CmdConsultStop();
	//操作日志
	  addLog("ConsultStop");
	}
	else if(document.getElementById("btnConsult").value.substring(0, 2)== "取消" )
	{
		CmdConsultCancel();
	}
			
	else if(document.getElementById("btnConsult").value.substring(0, 2)== "磋商" )
	{
	  showAgentList("磋商");
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



function aOCX_EVTConsult(){
	aOCX.ShowConsultDlg();
	//操作日志
	addLog("ShowConsultDlg");
}
/**
 * 磋商成功功能
 */
function aOCX_EVTConsultSucc(sAgentID){
	document.getElementById("btnConsult").value="停止";
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
	showAgentList("磋商转接");
}
function CmdConsultTransfer(agentId,ani,dnis)
{
	aOCX.CmdConsultTransfer(agentId,ani,dnis,"");
	//操作日志
	addLog("ConsultTransfer");
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
	if(document.getElementById("btnListen").value.substring(0, 2)== "监听") {
		//aOCX.CmdListen();
		showAgentList("监听");
	}
	else{
		aOCX.CmdListenStop();
		//操作日志
		addLog("ListenStop");
	}
	
}
/*监听功能*/
function CmdListenToAgent(agentId)
{
	//alert(agentId);
	aOCX.CmdListenToAgent(agentId);
	document.getElementById("btnListen").value="停止";
	//操作日志
	addLog("ListenToAgent:" + agentId);
}
function CmdListenStop()
{
	//alert("CmdListenStop");
	aOCX.CmdListenStop();
	document.getElementById("btnListen").value="监听";
	//操作日志
	addLog("ListenStop");
}

function aOCX_EVTListen(){
	aOCX.ShowListenDlg();
	//操作日志
	addLog("ShowListenDlg");
}
/**
 * 强拆按钮功能
 */
function btnDisconnect_onclick(){
	aOCX.CmdDisconnect();
	//操作日志
	addLog("Disconnect");
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
						alert("录音记录已保存");
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
	 parent.uiBase.showDialog({
		title: "状态设置",
		width: 640,
		height: 350,
		closed: false,
		cache: false,
		href: '${ctx}/softphone/agentstatus?_x='+Math.random(),
		iframe:true,
		modal: false,
		resizable:true
		});
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
	aOCX.CmdAuto2(flow,agentId);
	//操作日志
	addLog("Auto:" + agentId);
}
/*
 *转IVR按钮
 */
function btnCMDAuto()
{
	
	parent.uiBase.showDialog({
		title: "转IVR",
		width: 640,
		height: 200,
		closed: false,
		cache: false,
		href: '${ctx}/softphone/agentivr?_x='+Math.random(),
		iframe:true,
		modal: false,
		resizable:true
		});
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



</script>

<SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTReturnStatusCH(status)" >
    aOCX_EVTReturnStatusCH(status);
 </SCRIPT>
 <SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTReturnStatus(status)" >
    aOCX_EVTReturnStatus(status);
 </SCRIPT>
 <SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTButtonStatus(sName,sTitle,bEnable)" >
 		aOCX_EVTButtonStatus(sName,sTitle,bEnable);
 </SCRIPT>
  <SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTLoginSuc()" >
  	aOCX_EVTLoginSuc();
 </SCRIPT>
  <SCRIPT type="text/javascript" FOR="aOCX" EVENT="CallArrive(sAni,sDnis,sData)" >
   	aOCX_CallArrive(sAni,sDnis,sData);
 </SCRIPT>
  <SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTAnswerSucc()" >
  	aOCX_EVTAnswerSucc();
 </SCRIPT>
   <SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTDialOut()" >
   	aOCX_EVTDialOut();
 </SCRIPT>
    <SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTConsult()" >
    aOCX_EVTConsult();
 </SCRIPT>
  <SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTConsultSucc()" >
     aOCX_EVTConsultSucc(sAgentID);
 </SCRIPT>
     <SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTTransfer(AgentList)" >
     aOCX_EVTTransfer(AgentList);
 </SCRIPT>
   <SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTListen()" >
   aOCX_EVTListen();
 </SCRIPT>
 
 <SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTRecord(fileName)" >
   aOCX_EVTRecord(fileName);
 </SCRIPT>
 

  
 
</head>


<body onload="return window_onload()">
	<TABLE cellSpacing=1 cellPadding=1 width="75%" border=0>
		<TR>
			<TD><INPUT id=Ani></TD>
			<TD><INPUT id=btnLogin type=button value=签入 onclick='btnLogin_onclick()'></TD>
			<TD><INPUT id=btnLogOut type=button value=签出 onclick='btnLogOut_onclick()'></TD>
			<TD><INPUT type=button value=忙碌 id=btnPause onclick='btnPause_onclick()'></TD>
			<TD><INPUT type=button value=接听 id=btnHook name=button1 onclick='btnHook_onclick()'></TD>
			<TD><INPUT type=button value=保持 id=btnHold onclick='btnHold_onclick()'></TD>
			<TD><INPUT type=button value=转接 id=btnTransfer onclick='btnTransfer_onclick()'></TD>
			<TD><INPUT type=button value=外拨 id=btnDial onclick='btnDial_onclick()'></TD>
			<TD><INPUT type=button value=磋商 id=btnConsult onclick='btnConsult_onclick()'></TD>
			<TD><INPUT type=button value=磋商转接 id=btnConsultRedirect onclick='btnConsultTransfer()'></TD>
			<TD><INPUT type=button value=IVR转接 id=btnAuto onclick='btnCMDAuto()'></TD>
			<!-- <TD><INPUT type=button value=外线 id=btnOutPhone onclick=''></TD> -->
			<!-- <TD><INPUT type=button value=传真 id=btnFax onclick='btnFax_onclick()'></TD> -->
			<TD><INPUT type=button value=会议 id=btnConference onclick='btnConference_onclick()'></TD>
			<TD><INPUT type=button value=话后完成 id=btnAfterCall onclick='btnAfterCall_onclick()'></TD>

			<TD><INPUT type=button value=监听 id=btnListen onclick='btnListen_onclick()'></TD>
			<TD><INPUT type=button value=强拆 id=btnDisconnect onclick='btnDisconnect_onclick'></TD>
			<TD><INPUT type=button value=拦截 id=btnRopCall onclick='btnRopCall_onclick()'></TD>

			<TD><INPUT type=button value=状态 id=btnAgentStatus onclick='btn_setAgentStatus()'></TD>

			<TD><INPUT id=btnConfigure type=button value=配置 onclick='btnConfigure_onclick()'></TD>


			<TD>当前状态:<INPUT id=txtStatus></TD>

		</TR>
	</TABLE>
	<P>主叫号码：</P>
	<P>
		
	</P>
	<P>
		<OBJECT id=aOCX style="WIDTH: 151px; HEIGHT: 23px"
			data=data:application/x-oleobject;base64,np6Npeezi02KZ8WyuR3P+QADAACbDwAAYQIAAA==
			classid=clsid:A58D9E9E-B3E7-4D8B-8A67-C5B2B91DCFF9></OBJECT>
	</P>


	<P>
		<div id="msg"></div>	
	</P>
	
</body>
</html>