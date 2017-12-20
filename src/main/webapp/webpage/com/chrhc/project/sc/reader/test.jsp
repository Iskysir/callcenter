<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>新中新二代证阅读器控件测试程序JavaScript</title>


<script language="JavaScript1.2">
	function ReadCard1_onclick()
	{
		var nRet;
		//	SynCardOcx1.SetPhotoType(4);
		nRet = SynCardOcx1.ReadCardMsg();
		if(nRet==0)
		{
			document.getElementById("idcard").value=SynCardOcx1.CardNo +"\r\n";
			document.getElementById("name").value=SynCardOcx1.NameA +"\r\n";
			if(SynCardOcx1.Nation==01){
				document.getElementById("nation").value="汉族 " +"\r\n";
			}
			if(SynCardOcx1.Sex==1){
				document.getElementById("sex").value="男" +"\r\n";
			}else{
				document.getElementById("sex").value="女" +"\r\n";
			}
		}
		else if(nRet==65)
		{
			alert("请确认已放置身份证，并且所放位置正确");	
			document.getElementByTagName("input").value="";
		}
		else if(nRet==1)
		{
			alert("阅读器未接入");	
			document.getElementByTagName("input").value="";
		}
	}


  function FindReader_onclick()
  {
  	var str;
  	str = SynCardOcx1.FindReader();
  	if (str > 0)
  	{
  		if(str>1000)
  		{
  			str =document.all['S1'].value+ "读卡器连接在USB " + str+"\r\n" ;
  		}
  		else
  		{
  			str =document.all['S1'].value+ "读卡器连接在COM " + str+"\r\n" ;
  		}
  	}
  	else
  	{
  		str =document.all['S1'].value+ "没有找到读卡器\r\n";
  	}
  	document.all['S1'].value=str;
 	
  }
  function ReadSAMID_onclick()
  {
  	var str=SynCardOcx1.GetSAMID();
  	document.all['S1'].value=document.all['S1'].value+"读卡器SAMID为:"+str+"\r\n";
  }
  function Clear_onclick()
  {
  	document.all['S1'].value="";
  }
  function ReadCard_onclick()
  {
	var nRet;
	//	SynCardOcx1.SetPhotoType(4);
  	nRet = SynCardOcx1.ReadCardMsg();
  	alert(nRet);
  	if(nRet==0)
  	{
  		document.all['S1'].value=document.all['S1'].value+"读卡成功\r\n";	
  		document.all['S1'].value=document.all['S1'].value+"姓名:"+SynCardOcx1.NameA +"\r\n";
  		document.all['S1'].value=document.all['S1'].value+"性别:"+SynCardOcx1.Sex +"\r\n";
  		document.all['S1'].value=document.all['S1'].value+"民族:"+SynCardOcx1.Nation +"\r\n";
  		document.all['S1'].value=document.all['S1'].value+"出生日期:"+SynCardOcx1.Born +"\r\n";
  		document.all['S1'].value=document.all['S1'].value+"地址:"+SynCardOcx1.Address +"\r\n";
  		document.all['S1'].value=document.all['S1'].value+"身份证号:"+SynCardOcx1.CardNo +"\r\n";
  		document.all['S1'].value=document.all['S1'].value+"有效期开始:"+SynCardOcx1.UserLifeB +"\r\n";
  		document.all['S1'].value=document.all['S1'].value+"有效期结束:"+SynCardOcx1.UserLifeE +"\r\n";
  		document.all['S1'].value=document.all['S1'].value+"发证机关:"+SynCardOcx1.Police +"\r\n";
  		document.all['S1'].value=document.all['S1'].value+"照片文件名:"+SynCardOcx1.PhotoName +"\r\n";
  	}
  	else if(nRet==65)
  	{
  		document.all['S1'].value=document.all['S1'].value+"请确认已放置身份证，并且所放位置正确  \r\n";	
  	}
  	else if(nRet==1)
  	{
  		document.all['S1'].value=document.all['S1'].value+"阅读器未接入  \r\n";	
  	}
  }
  function ReadCardAuto_onclick()
  {
	
  	SynCardOcx1.SetloopTime(1000);
    alert("");
  	SynCardOcx1.SetReadType(1);
  }

  function PhotoPath_onclick()
  {
  	var str="";
  	SynCardOcx1.SetPhotoPath(0,str);
  	document.all['S1'].value=document.all['S1'].value+"照片保存路径设置为C盘根目录\r\n";
  }
  function PhotoPath3_onclick()
  {
  	var str="D:\\Photo";
  	var nRet;
  	nRet= SynCardOcx1.SetPhotoPath(2,str);
  	alert(nRet);
  	if(nRet == 0)
  	{
  		document.all['S1'].value=document.all['S1'].value+"照片保存路径设置为"+str+"\r\n";
  	}
  	else
  	{
  		document.all['S1'].value=document.all['S1'].value+"照片保存路径设置失败\r\n";  	
  	}
  }
  
  function FPInit_onclick()
  {
  	var str;
  	str = SynCardOcx1.InitFP();
  	if(str == 1)
  	{
  		document.all['S1'].value=document.all['S1'].value+"初始化指纹仪成功"+"  \r\n";
  	}
  	else
  	{
  		document.all['S1'].value=document.all['S1'].value+"初始化指纹仪失败"+"  \r\n";
  	}
  }
  function FPClose_onclick()
  {
    var str;
  	str = SynCardOcx1.CloseFP();
  	if(str == 1)
  	{
  		document.all['S1'].value=document.all['S1'].value+"关闭指纹仪成功"+"  \r\n";
  	}
  	else
  	{
  		document.all['S1'].value=document.all['S1'].value+"关闭指纹仪失败"+"  \r\n";
  	}	
  }
  function FPSound_onclick()
  {
  	/*
右手拇指 0BH 11
右手食指 0CH 12 
右手中指 0DH 13
右手环指 0EH 14
右手小指 0FH 15
左手拇指 10H 16
左手食指 11H 17
左手中指 12H 18
左手环指 13H 19
左手小指 14H 20
自定义信息  20H~2FH 32~47
*/
  	var str;
  	str = SynCardOcx1.SendSound(11);
    if(str == 0)
  	{
  		document.all['S1'].value=document.all['S1'].value+"发送声音成功"+"  \r\n";
  	}
  	else
  	{
  		document.all['S1'].value=document.all['S1'].value+"发送声音失败"+"  \r\n";
  	}		
  }
  function FPGetData_onclick()
  {
  	var str;
  	str = SynCardOcx1.BeginCapture()
  	if(str == 1)
  	{/*
右手拇指 0BH 11
右手食指 0CH 12 
右手中指 0DH 13
右手环指 0EH 14
右手小指 0FH 15
左手拇指 10H 16
左手食指 11H 17
左手中指 12H 18
左手环指 13H 19
左手小指 14H 20
自定义信息  20H~2FH 32~47
*/
  		str = SynCardOcx1.GetFPRawData(11);
  		alert(str);
  		if(str == -9)
  		{
  			document.all['S1'].value=document.all['S1'].value+"采集指纹成功"+"  \r\n";
  			str = SynCardOcx1.FPRAWFile;
  			document.all['S1'].value=document.all['S1'].value+"RAW文件为："+str+ "  \r\n";
  			str = SynCardOcx1.FPBMPFile;
  			document.all['S1'].value=document.all['S1'].value+"BMP文件为："+str+ "  \r\n";
  			str = SynCardOcx1.FPWLTFile();
  			document.all['S1'].value=document.all['S1'].value+"指纹特征文件为："+str+ "  \r\n";
  			str = SynCardOcx1.FPScore;
  			document.all['S1'].value=document.all['S1'].value+"指纹采集质量："+str+ "  \r\n";
  		}
  		else if(str == -1){
  			document.all['S1'].value=document.all['S1'].value+"采集指纹失败,指纹仪已关闭  "+"  \r\n";
  	  	}
	  	else
  		{
  			document.all['S1'].value=document.all['S1'].value+"采集指纹失败"+"  \r\n";
  		}
  		SynCardOcx1.EndCapture();
  	}
  	else
  	{
  		SynCardOcx1.EndCapture();
  		document.all['S1'].value=document.all['S1'].value+"开始采集指纹失败"+"  \r\n";
  	}
  }
  function FPMatch_onclick()
  {
  	var str;
  	var strRaw;
  	var strFP;
  	strRaw = SynCardOcx1.FPRAWFile;
  	strFP = SynCardOcx1.FPWLTFile();
  	str = SynCardOcx1.FeatureMatch(strRaw,strFP);
  	if(str >0)
  	{
  		document.all['S1'].value=document.all['S1'].value+"指纹数据比对成功,近似度为"+str + "  \r\n";
  	}
  	else
  	{
  		document.all['S1'].value=document.all['S1'].value+"指纹数据比对失败"+"  \r\n";
  	}
  }
  function GetBmp_onclick()
  {
  	var str;
  	var str2="C:\\20150525093812_3017371Fp.raw";
  	str = SynCardOcx1.GetBmp(str2);
  	document.all['S1'].value=document.all['S1'].value+"解码照片结果："+str + "  \r\n";
  }
  function LookFP(){

	
  }
function load(){

//	PhotoPath_onclick();
//	PhotoName4_onclick();	
//	FindReader_onclick();

 // 		ReadCard_onclick();

//	ReadCardAuto_onclick();
}
</script>

</head>
<body leftmargin="0" topmargin="0" onLoad="load();">
<p>
<object classid="clsid:46E4B248-8A41-45C5-B896-738ED44C1587" id="SynCardOcx1" codeBase="SynCardOcx.CAB#version=1,0,0,1" width="0" height="0" >
</object>
</p>
<form method="POST" action="--WEBBOT-SELF--" name="form1">
  <p>
  <textarea rows="17" name="S1" cols="82"></textarea></p>
	<p>
  <input type="button" value="自动寻找读卡器" name="FindReadBtn" onclick="FindReader_onclick()">
  <input type="button" value="获得SAMID" name="GetSAMIDBtn" onclick="ReadSAMID_onclick()">
  <input type="button" value="手动读卡" name="ReadCardBtn" onclick="ReadCard_onclick()">
  <input type="button" value="自动读卡" name="ReadCardAutoBtn" onclick="ReadCardAuto_onclick()">
  <input type="button" value="清除所有信息" name="ClearBtn" onclick="Clear_onclick()"></p>
  <p>
  照片保存路径:&nbsp;&nbsp;
  <input type="button" value="C盘根目录" name="PathBtn" onclick="PhotoPath_onclick()">
  <input type="button" value="当前目录" name="Path2Btn" onclick="PhotoPath2_onclick()">
  <input type="button" value="指定路径" name="Path3Btn" onclick="PhotoPath3_onclick()"></p>
	<p>
  指纹仪操作:
  <input type="button" value="初始化指纹仪" name="PhotoNameBtn" onclick="FPInit_onclick()">
  <input type="button" value="关闭指纹仪" name="PhotoName2Btn" onclick="FPClose_onclick()">
  <input type="button" value="采集一张指纹" name="PhotoName3Btn" onclick="FPGetData_onclick()">
  <input type="button" value="比较指纹" name="PhotoName4Btn" onclick="FPMatch_onclick()">
  <input type="button" value="查看指纹" name="PhotoName4Btn" onclick="window.open('scReaderController.do?preFP')">
  <input type="button" value="语音提示" name="PhotoName4Btn0" onclick="FPSound_onclick()">
  <input type="button" value="设置照片路径" name="PhotoName4Btn0" onclick="PhotoPath3_onclick()">
  <input type="button" value="  解码照片  " name="GetBmp" onclick="GetBmp_onclick()"></p>
  <a href="javascript:" onClick="window.open('webpage/com/chrhc/project/sc/reader/test2.jsp')">测试</a> 
</form>
	身份证号：<input type="text" name="idcard" id="idcard"/>
	<input type="button" value ="扫描" onclick="ReadCard1_onclick()"/><br>
	姓名：<input type="text" name= "name" id="name"/><br>
	性别：<input type ="text" name = "sex" id = "sex"><br>
	民族：<input type="text" name="nation" id="nation"/><br>
</body>

</html>