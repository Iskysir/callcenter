
<%
	String realPath = "http://" + request.getServerName() + ":"
			+ request.getServerPort() + request.getContextPath();
	String currentGisxy = (String)session.getAttribute("currentGisxy");
%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>2.5维地图DEMO</title>
<meta http-equiv="X-UA-Compatible"
	content="IE=8;text/html;charset=utf-8" />
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<script type="text/javascript"
	src="<%=realPath%>/plug-in/gis/alMap/lib/ChrhcMap.Include.js"></script>
<script type="text/javascript"
	src="<%=realPath%>/plug-in/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="<%=realPath%>/plug-in/tools/dataformat.js"></script>
<link id="easyuiTheme" rel="stylesheet"
	href="<%=realPath%>/plug-in/easyui/themes/default/easyui.css"
	type="text/css"></link>
<link rel="stylesheet"
	href="<%=realPath%>/plug-in/easyui/themes/icon.css" type="text/css"></link>
<link rel="stylesheet" type="text/css"
	href="<%=realPath%>/plug-in/accordion/css/accordion.css">
<link rel="stylesheet" type="text/css"
	href="<%=realPath%>/plug-in/accordion/css/icons.css">
<script type="text/javascript"
	src="<%=realPath%>/plug-in/easyui/jquery.easyui.min.1.3.2.js">
	
</script>
<script type="text/javascript"
	src="<%=realPath%>/plug-in/easyui/locale/zh-cn.js">
	
</script>
<script type="text/javascript"
	src="<%=realPath%>/plug-in/tools/syUtil.js"></script>
<script type="text/javascript"
	src="<%=realPath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="<%=realPath%>/plug-in/tools/css/common.css"
	type="text/css"></link>
<link rel="stylesheet" href="<%=realPath%>/plug-in/chrhc/gis.css"
	type="text/css"></link>
<script type="text/javascript"
	src="<%=realPath%>/plug-in/lhgDialog/lhgdialog.min.js"></script>
<script type="text/javascript"
	src="<%=realPath%>/plug-in/tools/curdtools_zh-cn.js"></script>
<script type="text/javascript"
	src="<%=realPath%>/plug-in/tools/easyuiextend.js"></script>
	 
	
    <script type="text/javascript">
    	var currentGisxy = "<%=currentGisxy%>";
    
        var baseMap, eyeMap;
		var cMap;
		var entityAdded=false;
		var entityLayer ;
		var currentLayer;
		var mapHeight = 700;
		var mapWidth =1500;
		var layerControl;
		var layerEditControl;
		var urlConfigString = '${config}';
		var centerTo = null; 
		var coords = null;
		var drawType = null;
		var drawPointEndCallBack = null;
		var drawLineEndCallBack = null;
		var gisid = null;
		var gisname = null;
		//查询模块的对象
		var bQuery;
		//图层编辑状态，建筑物被点击后返回其ID,不打开新窗口
		var layerEditBuildClickedThenReturnId;
		var layerEditBuildClickedThenReturnName;
		var enableLayerControl ;
		var enableQuery ;
		var enableDataEdit ;
		var enableSpotClickForMain ;
		var from ;
		var showLevel;
		/*
		图层可控制中，当前选择的图层ID，当点击“创建、编辑、停止编辑等按钮”时赋值。
		*/
		var currentLayerControlAddDataLayerId;
		//图层编辑中，单选，此值存储最后一次点击的图层对象
		var lastSelectedEditLayerDom;
		var layerNameHashMap;
		  
		var queryResultLayer;
		var contextPath;
		var urlConfig;
		var urlConfig2;
		var urlConfigString2 = UtilMisc.getQueryString("config");
			//var style = UtilMisc.getQueryString("style");
			//alert(urlConfigString+style);
			//alert(urlConfigString);
			//  
			if(urlConfigString!=null&&urlConfigString!=""){
				 urlConfig = eval('(' + urlConfigString + ')');	
			}else if(urlConfigString2!=null&&urlConfigString2!=""){
				urlConfig = eval('(' + urlConfigString2 + ')');	
			}
			
        function InitMap() {

        	contextPath = UtilMisc.getContextPath();
		/*
		config:{
			name:"测试3",
			id:"3",
			centerTo:"",
			drawType:"point",
			enableEdit:"",
			coords :'10636,14728.5,10608,14890.5,11656,14466.5,10590,14568.5,10544,14276.5,10636,14728.5',// coordsArr[index],//收尾相接是面
			style:{
				Size:5,
				FillColor:'#ff3300',
				Strokecolor:'#ff8300',
				Opacity:0.4,
				Dashstyle:"longdash"//线段样式(IE下有效)，可用：solid|dot|dash|dashdot|longdash|longdashdot|longdashdotdot|shortdot|shortdash|shortdashdot|shortdashdotd
			}
		}
		*/
		
		  var windowWidth = fnGetWindowWidth()-7;
		  var windowHeight = fnGetWindowHeight()-7;
			if(urlConfig!=null){
				 centerTo = urlConfig.centerTo;
				 coords = urlConfig.coords;
				 gisid = urlConfig.gisid;
				 gisname = urlConfig.gisname;
				 drawType = urlConfig.drawType;
				// showLevel = urlConfig.showLevel==null?0:urlConfig.showLevel;
				 mapWidth = urlConfig.mapWidth==null?windowWidth:urlConfig.mapWidth-5;
				 mapHeight = urlConfig.mapHeight==null?windowHeight:urlConfig.mapHeight-5;	
				 $("#giscoords").val(coords);
				 $("#gisname").val(gisname);
				 $("#gisid").val(gisid);
			}
			
			if(centerTo == null || centerTo ==''){
				centerTo = currentGisxy;
			}
			
			var option={ 
					mapId:"mapDiv",
					centerTo:centerTo,
					showLevel:showLevel,
					mapWidth:mapWidth,
					
					mapHeight:mapHeight,
					showCenter:false,
					callBack:initBussinesFunction,//地图初始化完毕后执行的方法
					mapZoomChange:MapZoomChange
					
				};
			   cMap = new ChrhcMap(option);
        }
        /*
        地图放大
        */
        function mapZoomIn(){
        	var zoom  = cMap.getCurrentZoom();
        		cMap.zoomTo(zoom-1);
        }
        /*
        地图缩小
        */
        function mapZoomOut(){
        	var zoom  = cMap.getCurrentZoom();
        		cMap.zoomTo(zoom+1);
        }
		/*
		图层显示：在指定比例尺显示控制代码
		*/
		function MapZoomChange(zoom){
			//debugger;
			//alert();
			//主页图层控制
				if(enableLayerControl&&layerControl){
					layerControl.changeLayerControlDisplay(zoom);
				}
				//主页的编辑
				if(enableDataEdit&&layerEditControl){
					layerEditControl.changeLayerControlDisplay(zoom);
				}
		}
		function initBussinesFunction(map){
			baseMap = map;
			if(!urlConfig || urlConfig ==null ||urlConfig==''){
				urlConfig = {};
			}


			//#################################自动画图并编辑部分##############################
			var shapeType;
			//debugger;
			if(urlConfig!=null){
				var enableEditE = urlConfig.enableEdit==undefined?false:urlConfig.enableEdit;
				var enableSpotClickE = urlConfig.enableSpotClick==undefined?true:urlConfig.enableSpotClick;
				
				 enableLayerControl = urlConfig.enableLayerControl==undefined?false:urlConfig.enableLayerControl;
				 enableDataEdit = urlConfig.enableDataEdit==undefined?false:urlConfig.enableDataEdit;
				 enableMeasure = urlConfig.enableMeasure==undefined?false:urlConfig.enableMeasure;
				 enableQuery = urlConfig.enableQuery==undefined?false:urlConfig.enableQuery;
				 enableSpotClickForMain = urlConfig.enableSpotClickForMain==undefined?false:urlConfig.enableSpotClickForMain;
				 from = urlConfig.from==undefined?null:urlConfig.from;
				 
				if(coords!=null){
					shapeType = ChrhcMap_Business_Functions.getCooordsType(coords);
					debugger;
					var dataSource  = [urlConfig];	
					for(var i=0;i<dataSource.length;i++){
						dataSource[i].style ={
											Size:5,
											Color:'#ff3300',
											FillColor:'#ff3300',
											Strokecolor:'#ff8300',
											Opacity:0.8,
											Dashstyle:"dashdot"//线段样式(IE下有效)，可用：solid|dot|dash|dashdot|longdash|longdashdot|longdashdotdot|shortdot|shortdash|shortdashdot|shortdashdotd
										};
						dataSource[i].id = dataSource[i].id==null?i:dataSource[i].id;
					}
					var layerConfig = {
							map:cMap,//初始化地图返回的地图对象
							layerName:"画图",
							visible:true,
							editEvent:{onDragStick:onDragCompleteEvent,onDrawEnd:drawPointEnd},//仅当config.enableEdit==true，且画图类型为线、面时有效
							dataSource:dataSource
						};
					
						if(shapeType ==ChrhcMapConfig.entityPoint){
							var markerConfig = {baseMap:baseMap,symbleType:"Markers",style:"background-position:0px -117px;",offectX:12,offectY:29};
							var markerSymble = new ChrhcMap.Symble(markerConfig);
								layerConfig.layerType = "Entitys";
								layerConfig.symble = markerSymble;
							
						}else if(shapeType == ChrhcMapConfig.shapeLine||shapeType == ChrhcMapConfig.shapePolygon){
								layerConfig.layerType = "Vector";
								
						} 
						
						currentLayer = new ChrhcMap.Layer(layerConfig);
						if(urlConfig.enableEdit&&currentLayer!=null){
							currentLayer.enableEdit(urlConfig.enableEdit,true);
						}
				}	
				//业务编辑界面的编辑事件
				if(enableEditE&&(shapeType!=drawType)){
					drawNewData(drawType,drawPointEnd,drawLineEnd);
				}
				//业务编辑页面的建筑物事件
				if(enableSpotClickE){
					cMap.enableSpotClick(spotClickCallBackEvent);
				}
				//主页图层控制
				if(enableLayerControl){
					$("#layerButtonControlDiv")[0].style.display = "";
					
				}
				//主页的编辑
				if(enableDataEdit){
					$("#layerEditButtonControlDiv")[0].style.display = "";
					
				}
				if(enableMeasure){
					$("#caseButton")[0].style.display = "";
					$("#rulerButton")[0].style.display = "";
					
					
				}

				if(enableQuery){
					initQueryHtml();
				}	
				//主页的建筑物事件
				if(enableSpotClickForMain){
					cMap.enableSpotClick(spotClickShowMoreInfoEvent,1);
				}
			}
			
			authControl();
		}


		function authControl(){
            $("#caseButton").hide();
            $("#rulerButton").hide();
			$("#layerButtonControlDiv").hide();
			$("#layerEditButtonControlDiv").hide();
			$("#queryDiv").hide();


			<t:optAuthFilter name="case-button">
			  $("#caseButton").show();
			</t:optAuthFilter>
			<t:optAuthFilter name="ruler-button">
			    $("#rulerButton").show();
			</t:optAuthFilter>
			<t:optAuthFilter name="layer">
			    $("#layerButtonControlDiv").show();
			</t:optAuthFilter>
			<t:optAuthFilter name="layer-edit">
			    $("#layerEditButtonControlDiv").show();
			</t:optAuthFilter>
			<t:optAuthFilter name="query">
			    $("#queryDiv").show();
			</t:optAuthFilter>
		}


		function spotClickShowMoreInfoEvent(e){
			//alert("ID:"+e.ID+" , 名称："+e.Name+" ,坐标串为："+e.Coords);
			  
			if(from =="phone"){
			/*
			添加手机端点击事件
			*/
			//alert("e.ID="+e.ID);
				phone.setBuildingId(e.ID);//手机端代码，手机端之间调用
			}else if(from =="layerEdit"){
				layerEditBuildClickedThenReturnId = e.ID;
				layerEditBuildClickedThenReturnName = e.Name;
			}else{
				var buildIngCheck = new ChrhcMap.CheckBuildIngQuery(null,addOneTab);
					buildIngCheck.addOneTab(e.ID);
			}
			
			//createwindow("详细信息", url,900,800);
			
		}
		
		function initQueryHtml(){
			var config={domId:"queryDiv",
						listTypeId:"queryList",
						resultHtmlId:"queryResultDiv",
						resultListDivId:"map-search-result-main",
						listClickEvent:setSlectedQueryType
			};
				bQuery =new ChrhcMap.BusinessQuery(config);
			
		}
	
		function drawNewData(drawType,drawPointEndEvent,drawVectorEndEvent){
			//#################################手工绘图部分##############################
			 				//layerType = drawType==ChrhcMapConfig.entityPoint?ChrhcMapConfig.entityPoint:ChrhcMapConfig.vectorLayerType;
				
							switch (drawType)
							{
					
								case "point":
									var callBack = drawPointEndEvent==null?drawPointEnd:drawPointEndEvent;
										
										ChrhcMap_Business_Functions.getPoint(cMap,callBack,spotClickCallBackEvent);
										
										enableSpotClick = false;	
								  break;
								case "line":
									//var callBack = drawEndEvent==null?drawLineEnd:drawEndEvent
										ChrhcMap_Business_Functions.getPolyLine(cMap,drawVectorEndEvent);
								  break;
								case "polygon":
									//var callBack = drawPointEndCallBack==null?drawLineEnd:drawLineEndCallBack
										ChrhcMap_Business_Functions.getPolygon(cMap,drawVectorEndEvent);
								  break;
								 default:
									 ChrhcMap_Business_Functions.getPolygon(cMap,drawVectorEndEvent);
								 break;
							}
		}
		function initContextMenu(visible1,visible2,visible3){
			var contextMenu = [{Key:"addNewData",Caption:"新建",Visible:visible1,CallBack:contextMenuAddNewLayerDataEvent},
							   {Key:"editOldData",Caption:"修改",Visible:visible2,CallBack:contextMenuAddNewLayerDataEvent},
							   {Key:"submitEditedData",Caption:"提交",Visible:visible3,CallBack:contextMenuAddNewLayerDataEvent}];
			cMap.showContextMenu(true,contextMenu,layerControl);

		}
		function resetContexMenu(visible1,visible2,visible3){
			var contextMenu = [{Key:"addNewData",Caption:"新建",Visible:visible1,CallBack:contextMenuAddNewLayerDataEvent},
							   {Key:"editOldData",Caption:"修改",Visible:visible2,CallBack:contextMenuAddNewLayerDataEvent},
							   {Key:"submitEditedData",Caption:"提交",Visible:visible3,CallBack:contextMenuAddNewLayerDataEvent}];
			cMap.resetContexMenu(contextMenu,layerControl);
			
		}
		var addOrEditTargetLayerId;
		var dataId ;
		var dataName;
		var dataCoords;
		var menuKey;
		var addOrEditUrl;
		var lastContextMenuId;
		var editCurrentLayerType;
		var selectedLayers = new HashMap();
		var selectedEditLayers = new HashMap();
		/*
		当面或者线画图结束时执行
		*/
		function drawNewVectorLayerDataEndEvent(e){
			//alert("当前所画的图形的ID为："+e.shapeId+" 坐标串为："+e.coords);
			$("#giscoords").val(e.coords);
			$("#gisid").val(e.shapeId);
			var coords = $("#giscoords").attr("value");			
			var layerId = currentLayerControlAddDataLayerId;
			var rows = layerEditControlHashMap.get(layerId);
			var layerName ="";
			if(rows!=null){
				layerName = rows.name;
			}
			var title="新建"+layerName,width=900,height=600;
			var currentLayer = layerEditControlHashMap.get(layerId);
			//添加服务地址
			var addOrEditUrl;
				if(currentLayer!=undefined){
					addOrEditUrl = contextPath+"/"+currentLayer.addurl;
				}		
			
						//alert(addOrEditUrl);
				addOneTab(title, addOrEditUrl+"&layerId="+layerId+"&gisxy="+e.coords);
					//createwindow(title, addOrEditUrl+"&gisxy="+e.coords,width,height);
				layerEditControl.enableEdit(false,[layerId]);
				$("#layer-create-cancel").hide();
				$("#layer-create").show();
				ChrhcMap_Business_Functions.stopDraw(cMap);
				$("#layer-control").attr("disabled",false);
				//TODO 判断有选中才置为可用
				$("#layer-edit").attr("disabled",false).find("span").removeClass("disable");
			/*
				回调执行，将新画的数据添加到地图控制图层中
			*/	
			/*
			此处将获取的坐标串传递给新打开的窗口
			*/
			
		}
		/*
		业务窗口关闭时，回调此方法，在地图上进行添加图标
		画点
		*/
		function businessCallBackDrawPointOrVector(layerId,id,name,coords){
			from = null;
			
			var dataSource = [{
				name:name,
				id:id,
				coords:coords,
				style:{
							Size:5,
							FillColor:'#ff3300',
							Strokecolor:'#ff8300',
							Opacity:0.4,
							Dashstyle:"longdash"}				
			}];
			
			layerEditControl.addData(layerId,dataSource);
			//layerEditControl.enableEdit(false,[layerId]);
		}
		/*
		return :id,name, coords
		*/
		function drawNewPointLayerDataEndEvent(id,name, coords){
			//alert("当前所画的图形ID===="+id+"坐标串："+coords);
		
			$("#giscoords").val(coords);
			$("#gisid").val(id);
			var layerId = currentLayerControlAddDataLayerId;
			var rows = layerEditControlHashMap.get(layerId);
			var layerName ="";
			if(rows!=null){
				layerName = rows.name;
			}
			var title="新建"+layerName,width=900,height=600;
			var layerRow = layerEditControlHashMap.get(layerId);
			var eventUrl = layerRow.addurl;
			setTimeout(timeOut,100);
			 function timeOut(){
				 var selecttype = layerEditControlHashMap.get(currentLayerControlAddDataLayerId).selecttype;
				 if((selecttype==ChrhcMapConfig.editLayerControlSelectType_Building)&&layerEditBuildClickedThenReturnId!=null){
						var  addurl = contextPath+"/"+eventUrl+"&layerId="+layerId+"&gisxy="+coords+"&gisname="+layerEditBuildClickedThenReturnName+"&gisid="+layerEditBuildClickedThenReturnId;
							 addOneTab(title, addurl);
								layerEditControl.enableEdit(false,[layerId]);
								$("#layer-create-cancel").hide();
								$("#layer-create").show();
								ChrhcMap_Business_Functions.stopDraw(cMap);
								$("#layer-control").attr("disabled",false);
								//TODO 判断有选中才置为可用
								$("#layer-edit").attr("disabled",false).find("span").removeClass("disable");
						/*
						业务窗口回调执行
						*/
						//businessCallBackDrawPointOrVector(layerId,coords,"测试"+coords,coords);
					}else if(selecttype==ChrhcMapConfig.editLayerControlSelectType_Building){
						alert("请选择建筑物!");
					}else{
						var  addurl = contextPath+"/"+eventUrl+"&layerId="+layerId+"&gisxy="+coords;
							 addOneTab(title, addurl);
								layerEditControl.enableEdit(false,[layerId]);
								$("#layer-create-cancel").hide();
								$("#layer-create").show();
								ChrhcMap_Business_Functions.stopDraw(cMap);
								$("#layer-control").attr("disabled",false);
								//TODO 判断有选中才置为可用
								$("#layer-edit").attr("disabled",false).find("span").removeClass("disable");
					}
				 layerEditBuildClickedThenReturnId=null;
			 }
			
			
			
			
			/*
			此处将获取的坐标串传递给新打开的窗口
			*/

		}
		
	var layerControlGroup=0;
	/*
	图层控制模块初始化完毕后的回调方法
	<li>图层1</li>
	<li class="li-checked">图层2</li>
	<li class="li-checked">图层3</li>
	<li>图层4</li>
	<li>图层5</li>
	*/
	function initEndCallBackFunction(data){
			
		var layerControlDiv = new Array();
			layerControlDiv.push(" <ul>");
		for(var i=0;i<data.length;i++){
			var id = data[i].id;
			var name = data[i].name;
				
			var checked = data[i].checked==undefined?false:true;
				layerControlDiv.push("<li id=\"show_"+id+"\" onclick=\"showOrHiddenLayer(this);\" checked=\""+checked+"\"");
			if(checked){
				layerControlDiv.push(" class=\"li-checked\" ");
				selectedLayers.put(id,id);
			}
			layerControlDiv.push(">"+name+"</li>");			
		}
		layerControlDiv.push("</ul>");
	
	
		//$("#layerControl").append.display = "";
		//alert(layerControlDiv.join(""));
		
	 	$("#layerControl").append(layerControlDiv.join(""));
	}	
	function initLayerEditEndCallBackFunction(data){
		
		var layerControlDiv = new Array();
			//layerControlDiv.push(" 	<li>图层1<span class=\"checked-icons\"></span></li>");
		for(var i=0;i<data.length;i++){
			var addurl = data[i].addurl;
			var editurl = data[i].editurl;
			if(editurl!=""&&addurl!=""){
				var id = data[i].id;
				var name = data[i].name;
					
				var checked = data[i].checked==undefined?false:true;
					layerControlDiv.push("<li  id=\"edit_"+id+"\"  checked=\""+checked+"\">");
				//if(checked){
					layerControlDiv.push("<span class=\"checked-icons\"></span> ");
					selectedEditLayers.put(id,id);
				//}
				layerControlDiv.push(""+name+"</li>");	
			}
		}
		
	 	//alert(layerControlDiv.join(""));
		$("ul#layer-control").append(layerControlDiv.join(""));
		//点击选中其中一个图层
		$("ul#layer-control li").unbind("click");
			$("ul#layer-control li").click(function(){
			
				if("disabled"!=$("#layer-control").attr("disabled")){
					//alert("1");
					if($(this).hasClass("li-checked")){
						//alert("2");
						$(this).removeClass("li-checked");
						$("#layer-create").attr("disabled",true).show().find("span").addClass("disable");
						$("#layer-edit").attr("disabled",true).show().find("span").addClass("disable");
					}else{
						//alert("3");
						
						$(this).addClass("li-checked").siblings().removeClass("li-checked");
						$("#layer-create").attr("disabled",false).show().find("span").removeClass("disable");
						$("#layer-edit").attr("disabled",false).show().find("span").removeClass("disable");
					}
					showOrHiddenEditLayer(this);
				}
			});
		 
	}
	/*
	图层控制，创建
	*/
	
	function addNewData(){
		if(lastSelectedEditLayerDom!=null){
			//debugger;
			//if(currentLayerControlAddDataLayerId!=null){
			//	stopEditCurrentLayer(currentLayerControlAddDataLayerId);
			//}
			//debugger;
			var key ;
			var layerId = lastSelectedEditLayerDom.id;
			if(layerId!=null){
				var layerIdArr = layerId.split("_");
					key = layerIdArr[1];
			}
				currentLayerControlAddDataLayerId = key;
			//var dom = document.getElementById(layerId);
			//var isChecked = $("#"+layerId).attr("checked");
			//	showOrHiddenEditLayer(lastSelectedEditLayerDom,true);
			
			var layertype = layerEditControlHashMap.get(key).layertype;
			
				from = "layerEdit";			
				drawNewData(layertype,drawNewPointLayerDataEndEvent,drawNewVectorLayerDataEndEvent);
		}else{
			alert("请选择图层!");
			
		}
		
	}
	/*
	图层控制，编辑
	*/
	function startEditCurrentLayer(){

		if(lastSelectedEditLayerDom!=null){
			var key = lastSelectedEditLayerDom.id;
			var layerId  ;
			if(key!=null){
				var layerIdArr = key.split("_");
					layerId = layerIdArr[1];
			}
			layerEditControl.enableEdit(true,[layerId]);
		}else{
			alert("请选择图层!");
		}
	}
	/*
	图层控制，停止编辑
	*/
	function stopEditCurrentLayer(){
		if(lastSelectedEditLayerDom!=null){
		var key = lastSelectedEditLayerDom.id;
			var layerId  ;
			if(key!=null){
				var layerIdArr = key.split("_");
					layerId = layerIdArr[1];
			}
			layerEditControl.enableEdit(false,[layerId]);
			//ChrhcMap_Business_Functions.stopDraw(cMap);

		}
			
	}
	function showOrHiddenLayer(that){
		//alert(that.checked);
		var isChecked = that.checked;
		var domId = that.id;
		var key ;
		if(domId!=null){
			var domIdArr = domId.split("_");
				key = domIdArr[1];
		}
		
		if(isChecked=="true"){
			that.className = "";
			that.checked = "false";
			layerControl.hiddenLayer(key);
			selectedLayers.remove(key);
		}else if(isChecked=="false"){
			that.className = "li-checked";
			that.checked = "true";
			//layerControl.showLayer(key);
			selectedLayers.put(key);
			layerControl.refreshLayerData(key);
		};
	}

	function showOrHiddenEditLayer(that,show){
		
		stopEditCurrentLayer();
		
		//debugger;
		var isChecked = that.checked;
		var domId = that.id;
		var key ;
		if(domId!=null){
			var domIdArr = domId.split("_");
				key = domIdArr[1];
		}
		
		if(lastSelectedEditLayerDom!=null&&lastSelectedEditLayerDom!=that){

			lastSelectedEditLayerDom.checked = "false";
			var lastDomId = lastSelectedEditLayerDom.id;
			var lastKey;
			if(lastDomId!=null){
				var lastDomIdArr = lastDomId.split("_");
				lastKey = lastDomIdArr[1];
			}
			layerEditControl.removeLayers(lastKey);
		}
		if(isChecked=="true"&&key!=null&&show==null){

			that.checked = "false";
			layerEditControl.removeLayers(key);
			selectedEditLayers.remove(key);
		}else if(isChecked=="false"&&key!=null){
			
			lastSelectedEditLayerDom = that;

			that.checked = "true";

			selectedEditLayers.put(key);
			layerEditControl.refreshLayerData(key);
		};
		//debugger;
	}
	function onLayerClickEvent(e){
		if(e!=null){
			var dataID ;
			var layerId;
			
			if(e.layerId!=null){
				var dataIDS = e.id.split("_");
					dataID =  dataIDS[1];
				var layerIDS = e.layerId.split("_");
					layerId = layerIDS[1];
					layerControlClickInfoWindowHtml(false,dataID,layerId);
			}			
		}
			
	}
	function onLayerEditClickEvent(e){
		
		if(e!=null){
			var dataID ;
			var layerId;
			
			if(e.layerId!=null){
				var dataIDS = e.id.split("_");
					dataID = dataIDS[1];
				var layerIDS = e.layerId.split("_");
					layerId = layerIDS[1];
					layerControlClickInfoWindowHtml(true,dataID,layerId);
			}			
		}
			
	}
	function layerControlClickInfoWindowHtml(isEdit,dataId,layerId){
		var title = isEdit?"编辑":"查看",width=900,height=600;
		
		var layerName ="";
		var layerRow;
		
		var clickUrl ;
		if(isEdit){
			 	 layerRow = layerEditControlHashMap.get(layerId);
			var editurl = layerRow.editurl;
				editurl = contextPath+"/"+editurl+dataId;
				clickUrl = editurl;
		}else{
				 layerRow = layerControlHashMap.get(layerId);
			var normalurl = layerRow.clickurl;
				normalurl = contextPath+"/"+normalurl+dataId;
				clickUrl = normalurl;
		}
		if(layerRow!=null){
			layerName = layerRow.name;
		}
			title = title+layerName;
		//createwindow(title, clickurl,width,height);
		addOneTab(title, clickUrl);
	}
	function layerControlEditUpdateWindowHtml(dataId,layerId,coords){
		var title="业务信息",width=900,height=600;
		var layerRow = layerEditControlHashMap.get(layerId);
		var eventUrl = layerRow.editurl;
		var editurl = contextPath+"/"+eventUrl+dataId+"&gisxy="+coords;
			//createwindow(title, editurl,width,height);
			addOneTab(title, editurl);
	}
		/*建筑物点击时触发
		e.Name:建筑物名称
		e.ID:建筑物ID
		e.Coords:建筑物坐标串
		*/
		function spotClickCallBackEvent(e){
		//alert("ID:"+e.ID+" , 名称："+e.Name+" ,坐标串为："+e.Coords);
		$("#gisname").val(e.Name);
		$("#gisid").val(e.ID);
		
			/*
			此处编写自己的业务流程
			*/
		}
		function clickEvent(e){
			alert("当前是自定义点击事件"+e.name);
			//e.removeNode();
			//alert("当前选择的对象是"+e.name+" ID:"+e.id);
			/*
			此处自定义选择某个对象后触发的相关事件，比如说：根据ID进行查询
			*/
		}
		function onmouseover(e){
			//alert("当前是自定义鼠标经过事件"+e.name);
		}
		function editCompleteEvent(e1,e2){
			//alert("自定义编辑结束事件"+e1.GetCoords());
		}
		/*
		e:dom;dom.entityInfo={X:123,Y:123,autoresize:false,W:70,H:30,exX:0,exY:0,enableEdit:false,autoremove:false,autozoomchangeremove:false}
		*/
		function onDragCompleteEvent(id,name,coords){
			
			//alert("当前编辑的实体ID是:"+id+"== 名称是"+name+" 坐标串："+coords);
			$("#giscoords").val(coords);
			$("#gisname").val(name);
			$("#gisid").val(id);
			/*
			此处可以对当前编辑过的对象进行数据库的更新操作
			*/
		}
		/*
		当图层处于可编辑状态，图标拖拽完毕后执行的方法，可以进行坐标串的更新操作
		*/
		function onLayerControlDragCompleteEvent(id,name,coords){
			if(lastSelectedEditLayerDom!=null){
				var key = lastSelectedEditLayerDom.id;
			
				var layerId  ;
				if(key!=null){
					var layerIdArr = key.split("_");
						layerId = layerIdArr[1];
				}
			
			var layerRow = layerEditControlHashMap.get(layerId);
			var dataId;
			if(id!=null){
				var dataIDS = id.split("_");
					dataId = dataIDS[1];
			}
			//layerControlEditUpdateWindowHtml(dataId,layerId,coords);
			/*
			此处可以对当前编辑过的对象进行数据库的更新操作
			*/
			var updategisxyurl = layerRow.updategisxyurl;
			if(updategisxyurl!=null&&updategisxyurl!=""){
				updategisxyurl = contextPath+"/"+string.Format(updategisxyurl,coords,id);
				var commit = new ChrhcMap.Query(updategisxyurl,"post",null,null,this);
					commit.execute(null);
			//alert("updategisxyurl="+updategisxyurl);
			}else{
				alert("更新服务暂未实现!");
			}
				
			
			}
		}
		/*
		return :id,name, coords
		*/
		function drawPointEnd(id,name, coords){
			//alert("当前所画的图形ID:"+id+"坐标串："+coords+"name"+name);
			$("#giscoords").val(coords);
			$("#gisname").val("");
			$("#gisid").val("");
			
		}
		/*
		var callBackObject = {shapeId:shapeId,coords:coords};
		*/
		function drawLineEnd(e){
			//alert("当前所画的图形的ID为=="+e.shapeId+" 坐标串为："+e.coords);
			
			var coords = e.coords;
			var drawType = e.drawType;
			var success = e.success;
			if(coords!=null&&success){
				if(drawType==ChrhcMapConfig.shapePolygon){
					var coordsArr = coords.split(",");
					var length = coordsArr.length;
					if(length<7){
						ChrhcMap_Business_Functions.stopDraw(cMap);
						alert(ChrhcMapConfig.businessEditCoordsError);
						drawNewData(ChrhcMapConfig.shapePolygon,null,drawLineEnd);
					}else{
						$("#giscoords").val(e.coords);
					}
				}else if(drawType==ChrhcMapConfig.shapeLine){
					var coordsArr = coords.split(",");					
					var length = coordsArr.length;
					if(length<4){
						ChrhcMap_Business_Functions.stopDraw(cMap);
						alert(ChrhcMapConfig.businessEditCoordsError);
						drawNewData(ChrhcMapConfig.shapeLine,null,drawLineEnd);
					}else{
						$("#giscoords").val(coords);
					}
					
				}else{
					$("#giscoords").val(e.coords);
				}
				
				
			}else{
				alert(ChrhcMapConfig.businessEditCoordsError);
				drawNewData(ChrhcMapConfig.shapePolygon,null,drawLineEnd);
			}
			
			/*
			此处可以进行数据的提交
			*/
		}


		function showQueryResult(){
			
		}
		function centerTo1(x,y){
			cMap.setCenter(x,y,null,true);
		}
		function initLayerControl(){
			$("#layerControl").empty();
			if(layerEditControl){
				layerEditControl.hiddenAllLayers();
			}
			//chrhcAutoListController.do?datagrid&configId=sc_gislayer&field=id,name,layertype,status,deleted,create_name,create_by,create_date,update_name,update_by,update_date,datasql,clickurl,hoverurl,editurl,addurl,&biztype=
			var url=contextPath+"/"+ChrhcMapConfig.layerControlServiceUrl+"&_x="+Math.random();//chrhcAutoListController.do?datagrid&configId=sc_gislayer&field=id,name,layertype,status,deleted,create_name,create_by,create_date,update_name,update_by,update_date,datasql,clickurl,hoverurl,editurl,addurl,level&biztype=";//"/scGislayerController.do?datagrid&field=id,name,layertype,status,deleted,create_name,create_by,create_date,update_name,update_by,update_date,datasql,clickurl,hoverurl,addurl,editurl";
			var type = "post";
			var where =null;
			var callBack = initLayerControlData;
			
			var query = new ChrhcMap.Query(url,type,where,callBack,this);
			query.execute(where);
			//$("#layerButtonControlDiv")[0].style.display = "";
			
		}
		function initEditLayerControl(){
			$("#layer-control").empty();
			if(layerControl){
				layerControl.hiddenAllLayers();
			}
			//chrhcAutoListController.do?datagrid&configId=sc_gislayer&field=id,name,layertype,status,deleted,create_name,create_by,create_date,update_name,update_by,update_date,datasql,clickurl,hoverurl,editurl,addurl,&biztype=
			var url=contextPath+"/"+ChrhcMapConfig.layerEditControlServiceUrl+"&_x="+Math.random();//chrhcAutoListController.do?datagrid&configId=sc_gislayer&field=id,name,layertype,status,deleted,create_name,create_by,create_date,update_name,update_by,update_date,datasql,clickurl,hoverurl,editurl,addurl,updategisxyurl,level,selecttype&biztype=";//"/scGislayerController.do?datagrid&field=id,name,layertype,status,deleted,create_name,create_by,create_date,update_name,update_by,update_date,datasql,clickurl,hoverurl,addurl,editurl";
			var type = "post";
			var where =null;
			var callBack = initEditLayerControlData;
			
			var query = new ChrhcMap.Query(url,type,where,callBack,this);
				query.execute(where);
			//$("#layerEditButtonControlDiv")[0].style.display = "";
		}
		
		var layerControlHashMap;
		function initLayerControlData(jsonData,isObject){
			//alert(jsonData);
			layerControlHashMap = new HashMap();
			var data;
			if(!isObject){
				try{
					data = eval('(' + jsonData + ')');
				}catch(e){
					return;
				}
				
			}
			if(isObject){
				data = jsonData;
			}
			var rows = data.rows;
				initEndCallBackFunction(rows);
			var dataSource = [];
			var url = contextPath+"/scGislayerController.do?getData";
			var layerHashMap = new HashMap();
			var queryServiceUrlHashMap = new HashMap();
			for(var i=0;i<rows.length;i++){
				var name = rows[i].name;
				var id = rows[i].id;
				var layerType = rows[i].layertype;
				//debugger;
				var layerLevel = rows[i].level==null?"0":rows[i].level;
				var showLevel = parseInt(layerLevel);
				var data = {
						layerType:layerType,
						layerName:id,
						layerId:id,
						layerZhName:name,
						showLevel:showLevel,
						url:url+"&id="+id,
						where:"where",
						visible:false
						};
				dataSource.push(data);
				var layerTypeControl = layerType==ChrhcMapConfig.entityPoint?ChrhcMapConfig.entityLayerType:ChrhcMapConfig.vectorLayerType;
				
				var layerHashMapValue = {
										 isChecked:false,showLevel:showLevel,
										 layerType:layerTypeControl,layerId:id,
										 layerName:name,visible:false,
										 layer:null,layerBq:null
										 };
					layerHashMap.put(id,layerHashMapValue);
					queryServiceUrlHashMap.put(id,url+"&id="+id);
					layerControlHashMap.put(id,rows[i]);
					
			}
						
			var layerControlData=[{
						//folderName:"所有图层",
						dataSource:dataSource
					}];
			 layerControl =  new ChrhcMap.LayerControl(cMap,layerControlData,null,onLayerClickEvent,drawPointEnd,onLayerControlDragCompleteEvent,true);		
			 layerControl.layerHashMap = layerHashMap;
			 layerControl.queryServiceUrlHashMap = queryServiceUrlHashMap;
		}
		var layerEditControlHashMap;
		function initEditLayerControlData(jsonData,isObject){
			
			//alert(jsonData);
			layerEditControlHashMap = new HashMap();
			var data;
			if(!isObject){
				try{
					data = eval('(' + jsonData + ')');
				}catch(e){
					return;
				}
				
			}
			if(isObject){
				data = jsonData;
			}
			var rows = data.rows;
				initLayerEditEndCallBackFunction(rows);
			var dataSource = [];
			var url = contextPath+"/scGislayerController.do?getData";
			var layerHashMap = new HashMap();
			var queryServiceUrlHashMap = new HashMap();
			for(var i=0;i<rows.length;i++){
				var addurl = rows[i].addurl;
				var editurl = rows[i].editurl;
				if(editurl!=""&&addurl!=""){
					var name = rows[i].name;
					var id = rows[i].id;
					var layerType = rows[i].layertype;
					var selecttype = rows[i].selecttype;
					var layerLevel = rows[i].level==null?"0":rows[i].level;
					var showLevel = parseInt(layerLevel);
					var data = {
							layerType:layerType,
							layerName:id,
							layerId:id,
							layerZhName:name,
							url:url+"&id="+id,
							selecttype:selecttype,
							showLevel:showLevel,
							where:"where",
							visible:false
							};
					dataSource.push(data);
					var layerTypeControl = layerType==ChrhcMapConfig.entityPoint?ChrhcMapConfig.entityLayerType:ChrhcMapConfig.vectorLayerType;
					
					var layerHashMapValue = {
											 isChecked:false,showLevel:showLevel,
											 layerType:layerTypeControl,layerId:id,
											 layerName:name,visible:false,
											 layer:null,layerBq:null
											 };
						layerHashMap.put(id,layerHashMapValue);
						layerEditControlHashMap.put(id,rows[i]);
						queryServiceUrlHashMap.put(id,url+"&id="+id);
				}
				
					
			}
						
			var layerControlData=[{
						//folderName:"所有图层",
						dataSource:dataSource
					}];

			layerEditControl =  new ChrhcMap.LayerControl(cMap,layerControlData,null,onLayerEditClickEvent,drawPointEnd,onLayerControlDragCompleteEvent);	
			layerEditControl.layerHashMap = layerHashMap;
			layerEditControl.queryServiceUrlHashMap = queryServiceUrlHashMap;
		}
		function hiddenAllEditLayer(){
			if(layerEditControl!=null){
				layerEditControl.removeAllLayers();
			}
			
		}
		function hiddenAllLayer(){
			if(layerControl!=null){
				layerControl.removeAllLayers();
				//layerControl.hiddenAllLayers();
				
			}
			
		}
		function queryPOI(page){
			$("#queryServicesDiv").hide();
			$(".datagrid-pager").hide();
			
			$("#currentQueryPageNum").val(page);
			 cMap.reSetMapSize();
			 removeQueryResult();
			var queryTextValue = $("#queryConditionInput").attr("value");
			if(queryTextValue==""){
			//	alert("请输入查询内容");
				//return;
			}
			bQuery.executeQuery(queryTextValue,page);
			
		}

		function removeQueryResult(){
			$("#map-search-result-main").empty();
			 if(bQuery.queryResultLayer!=null){
				 bQuery.queryResultLayer.clearFeatures();
			 }
		}
		function setSlectedQueryType(id){
			//alert("id="+id);
			//$("#currentQueryPageNum").val("1");
			bQuery.setQueryType(id);
			queryPOI(1);
		}
		function measure(type){
			if(cMap){
				cMap.measure(type);
			}
		}
		function forwardQuery(){
			
			var num = $("#currentQueryPageNum").attr("value");
			var currentNum = parseInt(num);
			var totalPage = bQuery.totalPage;
			if(totalPage>0&&currentNum<totalPage&&currentNum>0){
				currentNum = currentNum+1;
				$("#currentQueryPageNum").val(currentNum);
				queryPOI(currentNum);
			}
			
			//alert("currentNum="+currentNum);
		}
		function backQuery(){
			var num = $("#currentQueryPageNum").attr("value");
			var currentNum = parseInt(num);
			var totalPage = bQuery.totalPage;
			if(totalPage>0&&currentNum>1&&currentNum<totalPage+1){
				currentNum = currentNum-1;
				$("#currentQueryPageNum").val(currentNum);
				queryPOI(currentNum);
			}
			
		}
		function loadQuery(){
			var num = $("#currentQueryPageNum").attr("value");
			var totalPage = bQuery.totalPage;
			var currentNum = parseInt(num);
			if(totalPage>0&&currentNum>0&&currentNum<totalPage){
				queryPOI(currentNum);
			}
		}
		/***
		 *手机端调用函数*moveLeft->moveUp,pc端将来也可以用于左右上下移动地图
		 */
		function moveLeft(){
			cMap.pan("2");
		}
		function moveRight(){
			cMap.pan("3");
		}
		function moveDown(){
			cMap.pan("1");
		}
		function moveUp(){
			cMap.pan("0");
		}
    </script>

</head>
<body onload="InitMap();">
	<div id="main" >
		<div id="layer"></div>
		<div id="mapDiv" style="position: relative;">
		  
			<!-- 搜索框图层 -->
			<div class="map-search" id="queryDiv" style="display:none">
				<input id="queryConditionInput" type="text" placeholder="搜索道路、地标、站点等…" />
				<button onclick="queryPOI(1)"></button>
			</div>

			<!-- 搜索结果图层 -->
			<div class="map-search-result" id="queryResultDiv" style ="display:none">	
			<div class="map-search-result-top">
					<div class="map-search-result-title">查询结果</div>
					<div class="map-search-result-close">X</div>
			</div>
			<div id="map-search-result-main"></div>
			<div class="datagrid-pager pagination" style="display:none">
					<table cellspacing="0" cellpadding="0" border="0">
						<tbody>
							<tr>
								<td><div class="pagination-btn-separator"></div></td>
								<td><a href="javascript:void(0)"
									class="l-btn l-btn-plain " id=""><span
										class="l-btn-left"><span class="l-btn-text"><span
												class="l-btn-empty pagination-first">&nbsp;</span></span></span></a></td>
								<td><a href="javascript:void(0)"
									class="l-btn l-btn-plain " id=""><span
										class="l-btn-left"><span class="l-btn-text"><span
												class="l-btn-empty pagination-prev">&nbsp;</span></span></span></a></td>
								<td><div class="pagination-btn-separator"></div></td>
								<td><span style="padding-left: 6px;"></span></td>
								<td><input class="pagination-num" id ="currentQueryPageNum"type="text" value="1"
									size="2"></td>
								<td id="queryResultTotal"></td>
								<td><div class="pagination-btn-separator"></div></td>
								<td><a href="javascript:void(0)"
									class="l-btn l-btn-plain " id=""><span
										class="l-btn-left"><span class="l-btn-text"><span
												class="l-btn-empty pagination-next">&nbsp;</span></span></span></a></td>
								<td><a href="javascript:void(0)"
									class="l-btn l-btn-plain " id=""><span
										class="l-btn-left"><span class="l-btn-text"><span
												class="l-btn-empty pagination-last">&nbsp;</span></span></span></a></td>
								<td><div class="pagination-btn-separator"></div></td>
								<td><a href="javascript:void(0)" class="l-btn l-btn-plain"
									id=""><span class="l-btn-left"><span
											class="l-btn-text"><span
												class="l-btn-empty pagination-load">&nbsp;</span></span></span></a><span id="total"></span></td>
							</tr>
						</tbody>
					</table>
					<div style="clear: both;"></div>
				</div>
			</div>
			
			<!-- 热门分类图层 -->
			<div class="map-hot-classification" style="display:none" id="queryServicesDiv" >
				<span id="queryServicesDiv-close" style="width:16px;height:20px;color:#333;position: absolute;right:3px;top:5px;font-size:16px;cursor:pointer;">X</span>
				<span class="hot-classification-title">热门分类：</span>
				<ul id="queryList" >
				<!--  	<li class="checked">全部</li>
					<li>酒店</li>
					<li>汽车站</li>	
				-->	
				</ul>
			</div>
			<!-- 图层控制按钮 -->
			<div class="gis-icons layer-control-button" id="layerEditButtonControlDiv" style="display:none;" title="图层编辑"></div>
			<div class="gis-icons layer-show-button" id="layerButtonControlDiv" style="display:none;" title="图层显示"></div>
			<div class="gis-icons ruler-button" id="rulerButton" style="display:none;" title="测量距离"></div>
			<div class="gis-icons case-button" id="caseButton" style="display:none;" title="测量面积"></div>
			
			<!-- 图层显示图层 -->
			<div class="layer-list"  style="display:none;">
				<div class="layer-list-top">
					<div class="layer-list-title">图层显示</div>
					<div class="layer-list-close">X</div>
				</div>
				<div class="layer-list-main" id="layerControl">
				</div>
			</div>
			<!-- 图层控制图层 -->
			<div class="layer-edit-list"  style="display:none;">
				<div class="layer-list-top">
					<div class="layer-list-title">图层编辑</div>
					<div class="layer-list-close">X</div>
				</div>
				<div class="layer-list-main" id="layerEditControl">
					<ul id="layer-control">
					</ul>
					
				</div>
			<div class="layer-inner-buttons">
						<button id="layer-create" disabled="disabled"><span class="layer-button-icons layer-create disable"></span>新建</button>
						<button id="layer-create-cancel" style="display:none;"><span class="layer-button-icons layer-cancel"></span>退出新建</button>
						<button id="layer-edit" disabled="disabled"><span class="layer-button-icons layer-edit disable"></span>编辑</button>
						<button id="layer-edit-cancel" style="display:none;"><span class="layer-button-icons layer-cancel"></span>退出编辑</button>
					</div>
			</div>
			
			<!-- tooltipp -->
		
		</div>
	</div>
	<input id="giscoords" type="hidden">
	<input id="gisname" type="hidden">
	<input id="gisid" type="hidden">
	<script type="text/javascript">
		$(function(){
			
			
			$(".pagination-load").click(function(){
				loadQuery();
				
			});
			
			$(".pagination-next").click(function(){
				forwardQuery();
				
			});
			$(".pagination-prev").click(function(){
				backQuery();
				
			});
			$(".pagination-first").click(function(){
				if(bQuery!=null&&bQuery.totalPage>0){
					queryPOI(1);
					$("#currentQueryPageNum").val(1);
				}
				
				
			});
			$(".pagination-last").click(function(){
				if(bQuery!=null&&bQuery.totalPage>0){
					queryPOI(bQuery.totalPage);
					$("#currentQueryPageNum").val(bQuery.totalPage );
				}
				
			});
			$(".ruler-button").click(function(){
				measure("1");
				
			});
			$(".case-button").click(function(){
				measure("2");
				
			});
			$("#queryConditionInput").blur(function(){
				//$("#queryServicesDiv").hide();
				});
			$("#queryListTypeClose").click(function(){
				$("#queryServicesDiv").hide();
				
			});
			$('#queryConditionInput').click(function(){
				$("#queryServicesDiv").show();
				$(".map-search-result").hide();
			});
			$('#queryConditionInput').bind('keypress',function(event){
	            if(event.keyCode == "13")    
	            {
					
	            	queryPOI(1);
					
	            }
	        });
			$(".map-search-result-close").click(function(){
				$(".map-search-result").hide();
				removeQueryResult();
				cMap.reSetMapSize(null,null,20,20);
			});
			$(".layer-list-close").click(function(){
				layerClose();
				editLayerClose();
					
				
			});
			$(".layer-control-button").click(function(){
				if("block"==$(".layer-edit-list").css("display")){
					$(".layer-edit-list").hide();
				}else{
					$(".layer-edit-list").show();
					$(".layer-list").hide();
					$("#layer-control").attr("disabled",false);
					
					$("#layer-create-cancel").hide();
					$("#layer-edit-cancel").hide();
					$("#layer-create").attr("disabled",true).show().find("span").addClass("disable");
					$("#layer-edit").attr("disabled",true).show().find("span").addClass("disable");
					initEditLayerControl();
				}
				
			});
			$(".layer-show-button").click(function(){
				if("block"==$(".layer-list").css("display")){
					$(".layer-list").hide();
				}else{
					$(".layer-edit-list").hide();
					$(".layer-list").show();
					
					initLayerControl();
				}
				
			});
			
			$("#queryServicesDiv-close").click(function(){
				$("#queryServicesDiv").hide();
			});
			
			//图层新建按钮点击
			$("#layer-create").click(function(){
				$("#layer-control").attr("disabled",true);
				$("#layer-create").hide();
				$("#layer-create-cancel").show();
				$("#layer-edit").attr("disabled",true).find("span").addClass("disable");
				addNewData();
			});
			//图层退出新建按钮点击
			$("#layer-create-cancel").click(function(){
				$("#layer-control").attr("disabled",false);
				$("#layer-create").show();
				$("#layer-create-cancel").hide();
				ChrhcMap_Business_Functions.stopDraw(cMap);
				//TODO 判断有选中才置为可用
				$("#layer-edit").attr("disabled",false).find("span").removeClass("disable");
			});
			//图层编辑按钮点击
			$("#layer-edit").click(function(){
				$("#layer-control").attr("disabled",true);
				$("#layer-edit").hide();
				$("#layer-edit-cancel").show();
				$("#layer-create").attr("disabled",true).find("span").addClass("disable");
				startEditCurrentLayer();
			});
			//图层退出编辑按钮点击
			$("#layer-edit-cancel").click(function(){
				$("#layer-control").attr("disabled",false);
				$("#layer-edit").show();
				$("#layer-edit-cancel").hide();
				$("#layer-create").attr("disabled",false).find("span").removeClass("disable");
				stopEditCurrentLayer();
			});
			var ruleButtonObj = $("#rulerButton");
			var layerEditButtonObj = $("#layerEditButtonControlDiv");
			var layerButtonObj = $("#layerButtonControlDiv");
			var arr = [$("#queryDiv"),layerEditButtonObj,layerButtonObj,ruleButtonObj,$("#caseButton")];
			var domRegistClickEventGroup = new ChrhcMap.DomClickRegistGroup(arr);
			//在点击查询文本框、测量面积、图层编辑、图层显示的时候要自定结束测量
			domRegistClickEventGroup.registEvent(ruleButtonObj,finishScalClick);
			domRegistClickEventGroup.registEvent(layerEditButtonObj,editLayerClose);
			domRegistClickEventGroup.registEvent(layerButtonObj,layerClose);


		});
		//结束测距方法
		function finishScalClick(){
			if(cMap!=null){
				cMap.stopScal();
			}
		}
		function layerClose(){
			$(".layer-list").hide();
			ChrhcMap_Business_Functions.stopDraw(cMap);
			hiddenAllLayer();
		}

		function editLayerClose(){

			$(".layer-edit-list").hide();
			ChrhcMap_Business_Functions.stopDraw(cMap);
			hiddenAllEditLayer();
		}
		$(document).ajaxComplete(function(event, xhr, settings) {
			/* 绑定事件 */			
			//if(xhr.responseText.indexOf('loginController.do?login') != -1&&xhr.responseText.indexOf("$(document).ajaxComplete(function(event, xhr, settings)")==-1){
			   
				//判断如果当前页面不为主框架，则将主框架进行跳转
			    //alert(xhr.responseText.substring(xhr.responseText.indexOf('loginController.do?login'),xhr.responseText.length));
			//  	var tagert_URL = "<%=path%>/loginController.do?login";
			//    if(self==top){
			//    	window.location.href = tagert_URL;
			//    }else{
			//    	top.location.href = tagert_URL;
			//    }
			//}
		});
	</script>
</body>

</html>
