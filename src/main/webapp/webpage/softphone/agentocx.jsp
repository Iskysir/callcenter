<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>呼叫系统方法测试</title>


<script type="text/javascript">
var bAgentIDSpeaked = false;
/**
 * 按钮初始化
 */
function SetButtoninit(){
	document.getElementById("btnHook").disabled = true;
	document.getElementById("btnConference").disabled = true;
	document.getElementById("btnConsult").disabled = true;
	document.getElementById("btnAuto").disabled = true;
	document.getElementById("btnDial").disabled = true;
	document.getElementById("btnDisconnect").disabled = true;
	document.getElementById("btnFax").disabled = true;
	document.getElementById("btnHold").disabled = true;
	document.getElementById("btnListen").disabled = true;
	document.getElementById("btnOutPhone").disabled = true;
	document.getElementById("btnPause").disabled = true;
	document.getElementById("btnRopCall").disabled = true;
	document.getElementById("btnTransfer").disabled = true;
	
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

	aOCX.AgentID = document.getElementById("AgentID").value;
	aOCX.Password = document.getElementById("AgentPwd").value;
	//aOCX.AgentID = 'Agent2';
	//aOCX.Password = '2';
	aOCX.LogIn();

}
/**
 * 退出功能
 */
function btnLogOut_onclick(){
	aOCX.LogOUT();
	this.SetButtoninit();
	document.getElementById("btnLogin").disabled = false;
}

function aOCX_CallArrive(sAni,sDnis,sData){
	bAgentIDSpeaked = false;
	document.getElementById("Ani").value = sAni;
}

function aOCX_EVTButtonStatus(sName,sTitle,bEnable){
	switch(sTitle) 
    {
    case "OnHook":
        sTitle = "挂机"; break;
    case "OffHook":
        sTitle = "摘机"; break;
    case "HOOKOFFCONSULT":
        sTitle = "接受"; break;
    case "Hold":
        sTitle = "保持"; break;
    case "HoldCancel":
        sTitle = "取消"; break;
    case "Transfer":
        sTitle = "转接"; break;
    case "CancelTransfer":
        sTitle = "取消"; break;
    case "DialOut":
        sTitle = "外拨"; break;
    case "CancelDialOut":
        sTitle = "取消"; break;
    case "Consultation":
        sTitle = "磋商"; break;
    case "CancelConsultation":
        sTitle = "取消"; break;
    case "StopConsultation":
        sTitle = "磋商结束"; break;
    case "ConsultTransfer":
        sTitle = "磋商转接"; break;
    case "Auto":
        sTitle = "磋商转接"; break;
    case "OutPhone":
        sTitle = "自动"; break;
    case "CancelOutPhone":
        sTitle = "取消"; break;
    case "Play":
        sTitle = "放音"; break;
    case "PlayCancel":
        sTitle = "结束"; break;
    case "Fax":
        sTitle = "传真"; break;
    case "FaxStop":
        sTitle = "结束"; break;
    case "Pause":
        sTitle = "暂停"; break;
    case "Continue":
        sTitle = "恢复"; break;
    case "ContinueDialTask":
        sTitle = "放弃回访"; break;
    case "Listen":
        sTitle = "监听"; break;
    case "CancelListen":
        sTitle = "结束"; break;
    case "Disconnect":
        sTitle = "强插"; break;
    case "Conference":
        sTitle = "会议"; break;
    case "CancelConference":
        sTitle = "取消"; break;
    case "RopCall":
        sTitle = "拦截"; break;
    case "LOGINSUCC":
        sTitle = "登录成功"; break;
    case "LOGINFAIL":
        sTitle = "登录失败"; break;
    case "TRANSFERFAIL":
        sTitle = "转接失败"; break;
    }

//'MsgBox sName & ".." & sTitle & "..." & bEnable & ":" & bDisable
	var bDisable = false;
	if (bEnable = 0){
		bDisable = true;
	}else{
		bDisable = false;
	}
	

switch(sName){
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
    	document.getElementById("btnOutPhone").value = sTitle;
    	document.getElementById("btnOutPhone").disabled = bDisable;break;
    case "Fax":
    	document.getElementById("btnFax").value = sTitle;
    	document.getElementById("btnFax").disabled = bDisable;break;
    case "Pause":
    	document.getElementById("btnPause").value = sTitle;
    	document.getElementById("btnPause").disabled = bDisable;break;
    case "Conference":
    	document.getElementById("btnConference").value = sTitle;
    	document.getElementById("btnConference").disabled = bDisable;break;
    case "Play":
        
    case "Listen":
    	document.getElementById("btnListen").value = sTitle;
    	document.getElementById("btnListen").disabled = bDisable;break;     
    case "Disconnect":
        //'SetButtonOption Title, Enable, Me.Disconnect
        document.getElementById("btnDisconnect").value = sTitle;
        document.getElementById("btnDisconnect").disabled = bDisable;break;
    case "RopCall":
    	document.getElementById("btnRopCall").value = sTitle;
    	document.getElementById("btnRopCall").disabled = bDisable;break;
    case "Init":
        SetButtonInit();break;
    default:
     SetButtonInit();break;
	}
}
function window_onunload(){
	
}
/**
 * 配置按钮处理方法
 */
function btnConfigure_onclick(){
	aOCX.ShowConfig();
}
/**
 * 登录成功事件
 */
function aOCX_EVTLoginSuc(){
	document.getElementById("btnLogin").disabled = true;
}
/**
 * 登录失败处理方法
 */
function aOCX_EVTLoginFailed(reason){
	alert(reason);
}
/**
 * 修改当前状态值
 */
function aOCX_EVTReturnStatusCH(status){
	document.getElementById("txtStatus").value = status;
}

function aOCX_EVTAnswerSucc(){
	if(!bAgentIDSpeaked) {
		aOCX.CmdPlayAgentIDWelcome();
		bAgentIDSpeaked = true;
	}
}
/**
 * 外拨按钮功能
 */
function btnDial_onclick(){
	if(document.getElementById("btnDial").value.substring(0, 2) == "外拨"){
		aOCX.CmdDialOut();
	}else{
		aOCX.CmdMakeCallStop();
	}
}
function aOCX_EVTDialOut(){
	//'showModalDialog "DialOut.htm",window
	aOCX.ShowDialOut();
}
/**
 * 摘机按钮功能
 */
function btnHook_onclick(){
	if (document.getElementById("btnHook").value.substring(0, 2) == "摘机"){
		aOCX.CmdAnswer();
    
	}else if (document.getElementById("btnHook").value.substring(0, 2) == "接受"){
		aOCX.CmdConsultAnswer();
	}
	else{
		aOCX.CmdOnHook();
    }
}
/**
 * 保持按钮功能
 */
function btnHold_onclick(){
	if(document.getElementById("btnHold").value.substring(0, 2) == "保持") {
		aOCX.CmdHold();
	}else
		aOCX.CmdHoldStop();
}
/**
 * 转接按钮功能
 */
function btnTransfer_onclick(){
	if(document.getElementById("btnTransfer").value.substring(0, 2)== "转接" ){
		aOCX.CmdTransfer();
	}
	else aOCX.CmdTransferStop();
}
/**
 * 磋商按钮功能
 */
function btnConsult_onclick(){
  if(document.getElementById("btnConsult").value.substring(0, 4) == "磋商结束" ){
	  aOCX.CmdConsultStop();
	}
  else if(document.getElementById("btnConsult").value.substring(0, 2)== "磋商" ){
	  aOCX.CmdConsult();
  }else
	  aOCX.CmdConsultStop();
}

function aOCX_EVTConsult(){
	aOCX.ShowConsultDlg();
}
/**
 * 暂停按钮功能
 */
function btnPause_onclick(){
	if(document.getElementById("btnPause").value.substring(0, 2)== "暂停" ){
		aOCX.CmdPause();
	}else{
		aOCX.CmdContinue();
	}
}
/**
 * 磋商转接按钮功能
 */
function btnAuto_onclick(){
	aOCX.CmdConsultTransfer(DAgentID,"","","");
}
/**
 * 磋商成功功能
 */
function aOCX_EVTConsultSucc(sAgentID){
	DAgentID = sAgentID;
}
/**
 * 会议按钮功能
 */
function btnConference_onclick(){
	if(document.getElementById("btnConference").value.substring(0, 2)== "会议" ){
		aOCX.CmdConference();
	}
	else{
		aOCX.CmdMakeCallStop();
	}
}
/**
 * 监听按钮功能
 */
function btnListen_onclick(){
	if(document.getElementById("btnListen").value.substring(0, 2)== "监听") {
		aOCX.CmdListen();
	}
	else{
		aOCX.CmdListenStop();
	}
}

function aOCX_EVTListen(){
	aOCX.ShowListenDlg();
}
/**
 * 强拆按钮功能
 */
function btnDisconnect_onclick(){
	aOCX.CmdDisconnect();
}
/**
 * 拦截按钮功能
 */
function btnRopCall_onclick(){
	aOCX.CmdRopCall();
}

function aOCX_EVTTransfer(AgentList){
	aOCX.ShowTransferDlg();
}
/**
 * 传真按钮功能
 */
function btnFax_onclick(){
	aOCX.CmdFax();
}

</script>

<SCRIPT type="text/javascript" FOR="aOCX" EVENT="EVTReturnStatusCH(status)" >
    aOCX_EVTReturnStatusCH(status);
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
 
</head>


<body onload="return window_onload()">
 <!--header start-->
    <div class="container-fluid header">
        <ul class="header-seperation">
            <li class="logo"></li>
            <li class="logo-font">市民热线服务平台</li>
        </ul>
        <ul class="call-f">
            <li class="call-left">
                <div class="call-1"></div>
                <div class="call-2"></div>
                <div class="call-3"></div>
            </li>
            <li class="call-center">
                <div class="call-input">888777</div>
                <div class="font-12 bold">
                    <i class="fa fa-phone"></i>
                    <span>Talking</span>
                    <span class="right">02:06</span>
                </div>
            </li>
            <li class="call-right">
              <div class="call-right-l"></div>
              <div class="call-right-r"></div>
            </li>
        </ul>
        <ul class="nav">
            <li class="active"><a href="javascript:;"><i class="fa fa-sign-in"></i><span>签入</span></a></li>
            <li class="disabled" ><a href="javascript:;"><i class="fa fa-minus-circle"></i><span>空闲</span></a></li>
            <li class="disabled"><a href="javascript:;"><i class="fa fa-phone"></i><span>接听</span></a></li>
            <li class="disabled"><a href="javascript:;"><i class="fa fa-music"></i><span>保持</span></a></li>
            <li class="disabled"><a href="javascript:;"><i class="fa fa-flash"></i><span>释放</span></a></li>
            <li><a href="javascript:;" id="js-consultations" class="js-consultations"><i class="fa fa-comments-o"></i><span>磋商</span></a></li>
            <li><a href="javascript:;"><i class="fa fa-share-square-o"></i><span>转接</span></a></li>
            <li><a href="javascript:;"><i class="fa fa-comments"></i><span>会议</span></a></li>
            <li><a href="javascript:;"><i class="fa fa-external-link"></i><span>转IVR</span></a></li>
            <li><a href="javascript:;" id="js-dialer" class="js-dialer"><i class="fa fa-phone-square"></i><span>外拨</span></a></li>
            <li><a href="javascript:;"><i class="fa fa-shield"></i><span>监听</span></a></li>
            <li><a href="javascript:;"><i class="fa fa-ban"></i><span>拦截</span></a></li>
            <li><a href="javascript:;"><i class="fa fa-cloud-download"></i><span>强插</span></a></li>
            <li><a href="javascript:;"><i class="fa fa-road"></i><span>监控</span></a></li>
            <li><a href="javascript:;" id="js-status" class="js-status"><i class="fa fa-clipboard"></i><span>状态</span></a></li>
        </ul>
    </div>
    <!--header end-->



	<TABLE cellSpacing=1 cellPadding=1 width="75%" border=1>

		<TR>
			<TD><INPUT type=button value=摘机 id=btnHook name=button1 onclick='btnHook_onclick()'></TD>
			<TD><INPUT type=button value=保持 id=btnHold onclick='btnHold_onclick()'></TD>
			<TD><INPUT type=button value=转接 id=btnTransfer onclick='btnTransfer_onclick()'></TD>
			<TD><INPUT type=button value=外拨 id=btnDial onclick='btnDial_onclick()'></TD>
			<TD><INPUT type=button value=磋商 id=btnConsult onclick='btnConsult_onclick()'></TD>
			<TD><INPUT type=button value=磋商转接 id=btnAuto onclick='btnAuto_onclick()'></TD>
			<TD><INPUT type=button value=外线 id=btnOutPhone onclick=''></TD>
		</TR>
		<TR>
			<TD><INPUT type=button value=传真 id=btnFax onclick='btnFax_onclick()'></TD>
			<TD><INPUT type=button value=暂停 id=btnPause onclick='btnPause_onclick()'></TD>
			<TD><INPUT type=button value=会议 id=btnConference onclick='btnConference_onclick()'></TD>
			<TD><INPUT type=button value=监听 id=btnListen onclick='btnListen_onclick()'></TD>
			<TD><INPUT type=button value=强拆 id=btnDisconnect onclick='btnDisconnect_onclick'></TD>
			<TD><INPUT type=button value=拦截 id=btnRopCall onclick='btnRopCall_onclick()'></TD>
			<TD></TD>
		</TR>
		<TR>
			<TD></TD>
			<TD></TD>
			<TD></TD>
			<TD></TD>
			<TD></TD>
			<TD></TD>
			<TD></TD>
		</TR>
		<TR>
			<TD><INPUT id=btnLogin type=button value=登录 onclick='btnLogin_onclick()'></TD>
			<TD><INPUT id=btnLogOut type=button value=登出 onclick='btnLogOut_onclick()'></TD>
			<TD><INPUT id=btnConfigure type=button value=配置 onclick='btnConfigure_onclick()'></TD>
			<TD></TD>
			<TD></TD>
			<TD></TD>
			<TD></TD>
		</TR>
	</TABLE>
	<P>主叫号码：</P>
	<P>
		<INPUT id=Ani>
	</P>
	<P>
		<OBJECT id=aOCX style="WIDTH: 151px; HEIGHT: 23px"
			data=data:application/x-oleobject;base64,np6Npeezi02KZ8WyuR3P+QADAACbDwAAYQIAAA==
			classid=clsid:A58D9E9E-B3E7-4D8B-8A67-C5B2B91DCFF9></OBJECT>
	</P>


	<P>
	<TABLE cellSpacing=1 cellPadding=1 width="75%" border=1>

		<TR>
			<TD>坐席工号:</TD>
			<TD><INPUT id=AgentID></TD>
		</TR>
		<TR>
			<TD>坐席密码:</TD>
			<TD><INPUT id=AgentPwd></TD>
		</TR>

		<TR>
			<TD>当前状态:</TD>
			<TD><INPUT id=txtStatus></TD>
		</TR>

	</TABLE>
	
</body>
</html>