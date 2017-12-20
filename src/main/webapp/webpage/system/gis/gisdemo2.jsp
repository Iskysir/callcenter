
<%
	String realPath = "http://" + request.getServerName() + ":"
			+ request.getServerPort() + request.getContextPath();
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
	src="<%=realPath%>/plug-in/tools/curdtools_zh-cn.js"></script>
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

<%
	String enableLayerControl = (String) request
			.getParameter("enableLayerControl");
	String enableDataEdit = (String) request
			.getParameter("enableDataEdit");
	String enableQuery = (String) request.getParameter("enableQuery");
	String enableSpotClick = (String) request
			.getParameter("enableSpotClick");
%>

<script type="text/javascript">
	var baseMap, eyeMap;
	var cMap;
	var entityAdded = false;
	var entityLayer;
	var currentLayer;
	var mapHeight = 700;
	var mapWidth = 1500;
	var layerControl;
	var urlConfigString = '${config}';
	var centerTo = null;
	var coords = null;
	var drawType = null;
	var enableEdit = null;
	var drawPointEndCallBack = null;
	var drawLineEndCallBack = null;
	var enableSpotClick = null;
	var gisid = null;
	var gisname = null;
	var enableLayerControl =
<%=enableLayerControl%>
	;
	var queryResultLayer;
	var contextPath;
	//var urlConfigString = UtilMisc.getQueryString("config");
	//var style = UtilMisc.getQueryString("style");
	//alert(urlConfigString+style);
	//alert(urlConfigString);
	//debugger;
	if (urlConfigString != null && urlConfigString != "") {
		var urlConfig = eval('(' + urlConfigString + ')');

		if (urlConfig != null) {
			centerTo = urlConfig.centerTo;
			coords = urlConfig.coords;
			//alert(coords);
			gisid = urlConfig.gisid;
			gisname = urlConfig.gisname;
			drawType = urlConfig.drawType;
			enableEdit = urlConfig.enableEdit;
			mapWidth = urlConfig.mapWidth == null ? 1500
					: urlConfig.mapWidth - 5;
			mapHeight = urlConfig.mapHeight == null ? 900
					: urlConfig.mapHeight - 5;
			drawPointEndCallBack = urlConfig.drawPointEndCallBack;
			drawLineEndCallBack = urlConfig.drawLineEnd;

			enableSpotClick = urlConfig.enableSpotClick == undefined ? true
					: urlConfig.enableSpotClick;

		}

	}

	function InitMap() {

		$(window).resize(function() {
			//alert();
			if (cMap) {
				cMap.reSetMapSize();
			}
		});
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
		$("#giscoords").val(coords);
		$("#gisname").val(gisname);
		$("#gisid").val(gisid);
		var option = {
			mapId : "mapDiv",
			centerTo : centerTo,
			mapWidth : mapWidth,
			mapHeight : mapHeight,
			showCenter : false,
			callBack : initBussinesFunction
		//地图初始化完毕后执行的方法
		};
		cMap = new ChrhcMap(option);
	}
	function initBussinesFunction(map) {
		baseMap = map;

		//#################################自动画图并编辑部分##############################
		var shapeType;
		if (urlConfig != null) {
			if (coords != null) {
				shapeType = ChrhcMap_Business_Functions.getCooordsType(coords);
				var dataSource = [ urlConfig ];
				for ( var i = 0; i < dataSource.length; i++) {
					dataSource[i].style = {
						Size : 5,
						Color : '#ff3300',
						FillColor : '#ff3300',
						Strokecolor : '#ff8300',
						Opacity : 0.8,
						Dashstyle : "dashdot"//线段样式(IE下有效)，可用：solid|dot|dash|dashdot|longdash|longdashdot|longdashdotdot|shortdot|shortdash|shortdashdot|shortdashdotd
					}
					dataSource[i].id = dataSource[i].id == null ? i
							: dataSource[i].id;
				}
				var layerConfig = {
					map : cMap,//初始化地图返回的地图对象
					layerName : "画图",
					editEvent : {
						onDragStick : onDragCompleteEvent,
						onDrawEnd : drawPointEnd
					},//仅当config.enableEdit==true，且画图类型为线、面时有效
					dataSource : dataSource
				};

				if (shapeType == ChrhcMapConfig.entityPoint) {
					var markerConfig = {
						baseMap : baseMap,
						symbleType : "Markers",
						markerUrl : ChrhcMapConfig.getPointMarkerUrl
					};
					var markerSymble = new ChrhcMap.Symble(markerConfig);
					layerConfig.layerType = "Entitys";
					layerConfig.symble = markerSymble;

				} else if (shapeType == ChrhcMapConfig.shapeLine
						|| shapeType == ChrhcMapConfig.shapePolygon) {
					layerConfig.layerType = "Vector";

				}

				currentLayer = new ChrhcMap.Layer(layerConfig);
				if (enableEdit && currentLayer != null) {
					currentLayer.enableEdit(enableEdit, true);
				}
			}
		}
		if (enableEdit && (shapeType != drawType)) {
			drawNewData(drawType, drawPointEnd, drawLineEnd);
		}
		if (enableSpotClick) {
			cMap.enableSpotClick(spotClickCallBackEvent);
		}
		if (enableLayerControl) {
			initLayerControl();
		}

	}
	function drawNewData(drawType, drawPointEndEvent, drawVectorEndEvent) {
		//#################################手工绘图部分##############################
		//layerType = drawType==ChrhcMapConfig.entityPoint?ChrhcMapConfig.entityPoint:ChrhcMapConfig.vectorLayerType;

		switch (drawType) {

		case "point":
			var callBack = drawPointEndEvent == null ? drawPointEnd
					: drawPointEndEvent;

			ChrhcMap_Business_Functions.getPoint(cMap, callBack,
					spotClickCallBackEvent);

			enableSpotClick = false;
			break;
		case "line":
			//var callBack = drawEndEvent==null?drawLineEnd:drawEndEvent
			ChrhcMap_Business_Functions.getPolyLine(cMap, drawVectorEndEvent);
			break;
		case "polygon":
			//var callBack = drawPointEndCallBack==null?drawLineEnd:drawLineEndCallBack
			ChrhcMap_Business_Functions.getPolygon(cMap, drawVectorEndEvent);
			break;
		default:
			ChrhcMap_Business_Functions.getPolygon(cMap, drawVectorEndEvent);
			break;
		}
	}
	function initContextMenu(visible1, visible2, visible3) {
		var contextMenu = [ {
			Key : "addNewData",
			Caption : "新建",
			Visible : visible1,
			CallBack : contextMenuAddNewLayerDataEvent
		}, {
			Key : "editOldData",
			Caption : "修改",
			Visible : visible2,
			CallBack : contextMenuAddNewLayerDataEvent
		}, {
			Key : "submitEditedData",
			Caption : "提交",
			Visible : visible3,
			CallBack : contextMenuAddNewLayerDataEvent
		} ];
		cMap.showContextMenu(true, contextMenu, layerControl);

	}
	function resetContexMenu(visible1, visible2, visible3) {
		var contextMenu = [ {
			Key : "addNewData",
			Caption : "新建",
			Visible : visible1,
			CallBack : contextMenuAddNewLayerDataEvent
		}, {
			Key : "editOldData",
			Caption : "修改",
			Visible : visible2,
			CallBack : contextMenuAddNewLayerDataEvent
		}, {
			Key : "submitEditedData",
			Caption : "提交",
			Visible : visible3,
			CallBack : contextMenuAddNewLayerDataEvent
		} ];
		cMap.resetContexMenu(contextMenu, layerControl);

	}
	var addOrEditTargetLayerId;
	var dataId;
	var dataName;
	var dataCoords;
	var menuKey;
	var addOrEditUrl;
	var lastContextMenuId;
	var editCurrentLayerType;
	function contextMenuAddNewLayerDataEvent(e) {

		var key = e.key;
		var selectedLayers = getChecked();
		/*
		新建对象：先画图，画图结束后再根据addurl打开新窗口，同时将画图结果传递给新窗口
		 */
		if (key == "addNewData") {

			if (selectedLayers != null && selectedLayers.length == 1) {
				var layertype = layerControlHashMap.get(selectedLayers[0]).layertype;
				drawNewData(layertype, drawNewPointLayerDataEndEvent,
						drawNewVectorLayerDataEndEvent);
				//initContextMenu(true,false,true);
				addOrEditTargetLayerId = selectedLayers[0];
				var currentLayer = layerControlHashMap
						.get(addOrEditTargetLayerId);
				if (currentLayer != undefined) {
					//addurl,editurl
					//var  addurl = contextPath+"/chrhcFormBuildController.do?ftlForm&tableName=sc_sqjj&mode=read&load=detail&id="+dataId;
					addOrEditUrl = contextPath + "/" + currentLayer.addurl;
				}
			} else {
				alert("请选择一个图层");
				initContextMenu(true, true, false);
			}

		} else
		/*
		修改已有对象：先对选中的图层进行修改，修改结束后再根据editurl打开新窗口，同时将画图结果传递给新窗口
		 */
		if (key == "editOldData") {
			dataId = e.id;
			dataName = e.name;
			dataCoords = e.coords;
			addOrEditTargetLayerId = e.layerId;
			if (addOrEditTargetLayerId == null
					|| addOrEditTargetLayerId == undefined) {
				initContextMenu(true, true, false);
				alert("请选中要编辑的对象");
			} else {
				var currentLayer = layerControlHashMap
						.get(addOrEditTargetLayerId);

				if (currentLayer != undefined
						&& currentLayer.layertype != "point") {
					initContextMenu(true, false, true);
					//resetContexMenu(true,false,true);
				}
				if (currentLayer != undefined) {
					//addurl,editurl
					//var  addurl = contextPath+"/chrhcFormBuildController.do?ftlForm&tableName=sc_sqjj&mode=read&load=detail&id="+dataId;
					addOrEditUrl = contextPath + "/" + currentLayer.editurl
							+ dataId;
					editCurrentLayerType = currentLayer.layertype;
				}

			}

		} else
		/*
		新建对象：先画图，画图结束后再根据addurl打开新窗口，同时将画图结果传递给新窗口
		 */
		if (key == "submitEditedData") {
			var coords;
			var dataId;
			//drawNewData("polygon",drawNewLayerPointDataEndEvent,drawNewLayerDataEndEvent);
			if (lastContextMenuId == "editOldData") {
				layerControl.enableEdit(false, [ addOrEditTargetLayerId ]);
			}
			initContextMenu(true, true, false);
			var title = "业务信息", width = 900, height = 600;
			//alert(addOrEditUrl);
			createwindow(title, addOrEditUrl + "&id=" + dataId + "&gisxy="
					+ coords, width, height);

			/*
			提交后，需要将坐标串、当前数据ID，传递给新打开的窗口
			 */
		}
		lastContextMenuId = key;

	}
	function drawNewVectorLayerDataEndEvent(e) {
		//alert("当前所画的图形的ID为："+e.shapeId+" 坐标串为："+e.coords);
		$("#giscoords").val(e.coords);
		$("#gisid").val(e.shapeId);
		var coords = $("#giscoords").attr("value");
		var selectedLayers = getChecked();
		alert("添加地址：" + addOrEditUrl + "&gisxy=" + coords);
		var layerId = selectedLayers[0];

		ChrhcMap_Business_Functions.stopDraw(cMap);
		var dataSource = [ {
			name : "",
			id : e.shapeId,
			coords : coords,
			style : {
				Size : 5,
				FillColor : '#ff3300',
				Strokecolor : '#ff8300',
				Opacity : 0.4,
				Dashstyle : "longdash"
			}
		} ];
		layerControl.addData(layerId, dataSource);
		var title = "业务信息", width = 900, height = 600;
		//alert(addOrEditUrl);
		createwindow(title, addOrEditUrl + "&gisxy=" + e.coords, width, height);
		initContextMenu(true, true, false);
		/*
		此处将获取的坐标串传递给新打开的窗口
		 */

	}
	/*
	return :id,name, coords
	 */
	function drawNewPointLayerDataEndEvent(id, name, coords) {
		alert("当前所画的图形ID====" + id + "坐标串：" + coords);
		ChrhcMap_Business_Functions.stopDraw(cMap);
		var title = "业务信息", width = 900, height = 600;
		var layerRow = layerControlHashMap.get(addOrEditTargetLayerId);
		var eventUrl = layerRow.addurl;
		var addurl = contextPath + "/" + eventUrl + "&gisxy=" + coords;
		var dataSource = [ {
			name : id,
			id : id,
			coords : coords
		} ];
		layerControl.addData(addOrEditTargetLayerId, dataSource);
		createwindow(title, addurl, width, height);

		layerControl.enableEdit(false, [ addOrEditTargetLayerId ]);
		initContextMenu(true, true, false);

		/*
		此处将获取的坐标串传递给新打开的窗口
		 */

	}

	var layerControlGroup = 0;
	/*
	图层控制模块初始化完毕后的回调方法
	 */
	function initEndCallBackFunction(data) {
		layerControlGroup++;
		var ul = " <ul id=\"data"+layerControlGroup+"\" class=\"easyui-tree\" data-options=\"checkbox:true\"></ul>";
		$("#treeData").append(ul);
		$("#data" + layerControlGroup).tree({
			data : data,
			onClick : function(node) {

			}
		});

	}
	function onLayerClickEvent(e) {
		var dataID;
		var layerId;
		if (e != null) {
			dataID = e.id;
			layerId = e.layerId;
			layerControlClickInfoWindowHtml(dataID, layerId);
		}

	}
	function layerControlClickInfoWindowHtml(dataId, layerId) {
		var title = "详细信息", width = 900, height = 600;
		var layerRow = layerControlHashMap.get(layerId);
		var eventUrl = layerRow.clickurl;
		var clickurl = contextPath + "/" + eventUrl + dataId;
		createwindow(title, clickurl, width, height);

	}
	function layerControlEditUpdateWindowHtml(dataId, layerId, coords) {
		var title = "业务信息", width = 900, height = 600;
		var layerRow = layerControlHashMap.get(layerId);
		var eventUrl = layerRow.editurl;
		var editurl = contextPath + "/" + eventUrl + dataId + "&gisxy="
				+ coords;
		createwindow(title, editurl, width, height);

	}
	/*建筑物点击时触发
	e.Name:建筑物名称
	e.ID:建筑物ID
	e.Coords:建筑物坐标串
	 */
	function spotClickCallBackEvent(e) {
		//alert("ID:"+e.ID+" , 名称："+e.Name+" ,坐标串为："+e.Coords);
		$("#gisname").val(e.Name);
		$("#gisid").val(e.ID);
		/*
		此处编写自己的业务流程
		 */
	}
	function clickEvent(e) {
		//alert("当前是自定义点击事件"+e.name);
		//e.removeNode();
		//alert("当前选择的对象是"+e.name+" ID:"+e.id);
		/*
		此处自定义选择某个对象后触发的相关事件，比如说：根据ID进行查询
		 */
	}
	function onmouseover(e) {
		//alert("当前是自定义鼠标经过事件"+e.name);
	}
	function editCompleteEvent(e1, e2) {
		//alert("自定义编辑结束事件"+e1.GetCoords());
	}
	/*
	e:dom;dom.entityInfo={X:123,Y:123,autoresize:false,W:70,H:30,exX:0,exY:0,enableEdit:false,autoremove:false,autozoomchangeremove:false}
	 */
	function onDragCompleteEvent(id, name, coords) {

		//alert("当前编辑的实体ID是:"+id+"== 名称是"+name+" 坐标串："+coords);
		$("#giscoords").val(coords);
		$("#gisname").val(name);
		$("#gisid").val(id);
		/*
		此处可以对当前编辑过的对象进行数据库的更新操作
		 */
	}
	function onLayerControlDragCompleteEvent(id, name, coords) {
		if (editCurrentLayerType == "point") {
			layerControl.enableEdit(false, [ addOrEditTargetLayerId ]);
			initContextMenu(true, true, false);
		}
		layerControlEditUpdateWindowHtml(id, addOrEditTargetLayerId, coords);
		$("#giscoords").val(coords);
		$("#gisname").val(name);
		$("#gisid").val(id);
		/*
		此处可以对当前编辑过的对象进行数据库的更新操作
		 */
	}
	/*
	return :id,name, coords
	 */
	function drawPointEnd(id, name, coords) {
		//alert("当前所画的图形ID:"+id+"坐标串："+coords+"name"+name);
		$("#giscoords").val(coords);
		$("#gisname").val("");
		$("#gisid").val("");
	}
	/*
	var callBackObject = {shapeId:shapeId,coords:coords};
	 */
	function drawLineEnd(e) {
		//alert("当前所画的图形的ID为："+e.shapeId+" 坐标串为："+e.coords);
		$("#giscoords").val(e.coords);
		/*
		此处可以进行数据的提交
		 */
	}
	function getChecked() {
		//$("#data"+layerControlGroup)
		var showLayer = [];
		for ( var j = 1; j < layerControlGroup + 1; j++) {

			var nodes = $("#data" + j).tree('getChecked');
			for ( var i = 0; i < nodes.length; i++) {

				var key = nodes[i].id;
				if (key != null && key.indexOf("folder") == -1) {
					showLayer.push(key);
				}
			}
		}
		return showLayer;

	}
	function showLayer() {
		var showLayer = getChecked();
		layerControl.showLayers(showLayer);
	}
	function showQueryResult() {

	}
	function centerTo1(x, y) {
		cMap.setCenter(x, y, null, true);
	}
	function initLayerControl() {
		//chrhcAutoListController.do?datagrid&configId=sc_gislayer&field=id,name,layertype,status,deleted,create_name,create_by,create_date,update_name,update_by,update_date,datasql,clickurl,hoverurl,editurl,addurl,&biztype=
		var url = contextPath
				+ "/chrhcAutoListController.do?datagrid&configId=sc_gislayer&field=id,name,layertype,status,deleted,create_name,create_by,create_date,update_name,update_by,update_date,datasql,clickurl,hoverurl,editurl,addurl,&biztype=";//"/scGislayerController.do?datagrid&field=id,name,layertype,status,deleted,create_name,create_by,create_date,update_name,update_by,update_date,datasql,clickurl,hoverurl,addurl,editurl";
		var type = "post";
		var where = null;
		var callBack = initLayerControlData;

		var query = new ChrhcMap.Query(url, type, where, callBack, this);
		query.execute(where);
		initLayerControlHtml();

	}
	function initLayerControlHtml() {
		var layerControlDiv = new Array();
		layerControlDiv
				.push(" <div id=\"w\" class=\"easyui-window\" data-options=\"title:'图层控制'\"  style=\" top: 90px; left:300px;width:260px;height:300px;\">");
		layerControlDiv
				.push(" <div class=\"easyui-layout\" data-options=\"fit:true\">");
		layerControlDiv
				.push(" <div id=\"treeData\" data-options=\"region:'center',border:false\" style=\"padding:10px;background:#fff;border:1px solid #ccc;\">");

		layerControlDiv.push(" </div>");
		layerControlDiv
				.push(" <div data-options=\"region:'south',border:false\" style=\"text-align:center;padding:5px 0;\">");
		layerControlDiv
				.push(" <a class=\"easyui-linkbutton\"  href=\"javascript:void(0)\" onclick=\"showLayer()\">显示选中</a>");
		layerControlDiv.push(" </div>");
		layerControlDiv.push(" </div>");
		$("#layerControl").append.display = "";
		$("#layerControl").append(layerControlDiv.join(""));
	}
	var layerControlHashMap;
	function initLayerControlData(jsonData, isObject) {
		//alert(jsonData);
		layerControlHashMap = new HashMap();
		var data;
		if (!isObject) {
			data = eval('(' + jsonData + ')');
		}
		if (isObject) {
			data = jsonData;
		}
		var rows = data.rows;
		var dataSource = [];
		var url = contextPath + "/scGislayerController.do?getData";
		for ( var i = 0; i < rows.length; i++) {
			var name = rows[i].name;
			var id = rows[i].id;
			var layertype = rows[i].layertype;
			var data = {
				layerType : layertype,
				layerName : id,
				layerId : id,
				layerZhName : name,
				url : url + "&id=" + id,
				where : "where",
				visible : true
			};
			dataSource.push(data);

			layerControlHashMap.put(id, rows[i]);

		}

		var layerControlData = [ {
			//folderName:"所有图层",
			dataSource : dataSource
		} ];
		layerControl = new ChrhcMap.LayerControl(cMap, layerControlData,
				initEndCallBackFunction, onLayerClickEvent, drawPointEnd,
				onLayerControlDragCompleteEvent);
		initContextMenu(true, true, false);
		//}
	}

	function queryPOI() {
		cMap.reSetMapSize();
		if (queryResultLayer != null) {
			queryResultLayer.clearFeatures();
		}
		var url = contextPath
				+ "/chrhcAutoListController.do?datagrid&configId=sc_humpt&field=id,create_name,create_by,create_date,update_name,update_by,sys_org_code,update_date,bigtype,smalltype,address ,gisxy,tablet,name,othername,simplename,xian_code,jd_code,jwh_code,postoffice,telnum,&biztype=";
		var queryTextValue = $("#queryId").attr("value");
		if (queryTextValue == "") {
			alert("请输入查询内容");
			return;
		}
		var queryUrl = string.Format(url, "", queryTextValue);
		var type = "post";
		var where = null;
		var callBack = queryPOIEndCallBackFunction;

		var query = new ChrhcMap.Query(queryUrl, type, where, callBack, this);
		query.execute(where);
	}
	function queryPOIEndCallBackFunction(jsonData, isObject) {
		var data;
		if (!isObject) {
			data = eval('(' + jsonData + ')');
		}
		if (isObject) {
			data = jsonData;
		}
		var rows = data.rows;
		var dataSource = [];
		$("#queryResult").empty();
		for ( var i = 0; i < rows.length; i++) {
			var name = rows[i].name;
			var coords = rows[i].gisxy;
			var id = rows[i].id;
			var coordsArr = coords.split(",");
			var x = coordsArr[0];
			var y = coordsArr[1];
			var address = rows[i].address;
			var html = "<div style=\"background:#E1EEFE\" onmouseover=\"this.style.backgroundColor=#E100EE;\" onmouseout=\"this.style.backgroundColor=#E1EEFE;\" onclick=\"centerTo1("
					+ x + "," + y + ")\">";
			html = html + "<a href=\"#\" >";
			html = html + "<h4 id =\""+id+"\" >名称：" + name + "</h4><h4 >地址："
					+ address + "</h4>  </a></div>";
			$("#queryResult").append(html);
			dataSource.push({
				name : name,
				id : id,
				coords : coords
			});

		}
		var config = {
			baseMap : baseMap,
			symbleType : "Complex",
			markerUrl : "images/markerbig.png",
			style : "padding:2px;border:2px solid red;font-size:12px;font-weight:bold;background-color:#fff;"
		};
		var markerSymble = new ChrhcMap.Symble(config);
		var layerConfig = {
			map : cMap,//初始化地图返回的地图对象
			layerName : "复合",
			layerType : "Entitys",//
			symble : markerSymble,
			//event:{onClick:clickEvent,onMouseOver:onmouseover},
			dataSource : dataSource
		};
		queryResultLayer = new ChrhcMap.Layer(layerConfig);

	}
</script>

</head>
<body class="easyui-layout" onload="InitMap();">
	<div data-options="region:'north',split:true" title=""
		style="height: 50px; padding: 1px;">
		<input id="queryId" type="text" name="name"></input> <a href="#"
			class="easyui-linkbutton" data-options="iconCls:'icon-search'"
			onclick="queryPOI()"></a>
	</div>
	<div id="west" data-options="region:'west',split:true,collapsed:true"
		title="控制面板" style="width: 280px; padding1: 1px; overflow: hidden;">
		<div class="easyui-accordion" data-options="fit:true,border:false">

			<div id="queryResult" title="查询结果" data-options="selected:false"
				style="padding: 10px;"></div>
			<div id="layerControl" title="图层控制" data-options="selected:true"
				style="display: none; padding: 10px;"></div>
		</div>
	</div>


	<div
		data-options="region:'east',split:true,collapsed:true,title:'East'"
		style="width: 100px; padding: 10px;">east region</div>
	<div id="main" data-options="region:'center',title:''"
		style="width: 1500px; height: 900px; padding: 0px;">
		<div id="layer"></div>
		<div id="mapDiv" style="position: relative;">
			<!-- 搜索框图层 -->
			<div class="map-search">
				<input type="text" placeholder="搜索道路、地标、站点等…" />
				<button></button>
			</div>
			<!-- 热门分类图层 -->
			<div class="map-hot-classification">
				<span class="hot-classification-title">热门分类：</span>
				<ul>
					<li class="checked">全部</li>
					<li>酒店</li>
					<li>汽车站</li>
					<li>火车站</li>
					<li>餐饮</li>
					<li>医院</li>
					<li>超市</li>
					<li>商场</li>
					<li>酒店</li>
					<li>汽车站</li>
					<li>火车站</li>
					<li>餐饮</li>
					<li>医院</li>
					<li>超市</li>
					<li>商场</li>
					<li>酒店</li>
					<li>汽车站</li>
					<li>火车站</li>
					<li>餐饮</li>
					<li>医院</li>
					<li>超市</li>
					<li>商场</li>
				</ul>
			</div>
			<!-- 搜索结果图层 -->
			<div class="map-search-result" style="display:none;">
				<div class="map-search-result-top">
					<div class="map-search-result-title">查询结果</div>
					<div class="map-search-result-close">X</div>
				</div>
				<div class="map-search-result-main">
					<ul>
						<li class="search-item">
							<div class="cf">
								<div class="col-l">
									<a href="javascript:void(0)" class="bubble no-1"></a>
								</div>
								<div class="ml_10 gis_l_address">
									<div>
										<span><a href="javascript:void(0)" class="n-blue">龙都国际大酒店</a></span>
									</div>
									<div class="content-des n-grey">星级酒店</div>
									<div class="content-des">天桥区北园路421号（东亚家居旁边）</div>
									<div class="content-des">0531-88776655</div>
								</div>
								<div class="gis_r_img">
									<img src="<%=realPath%>/plug-in/chrhc/images/gisImg.png"
										width="90px" height="60px" />
								</div>
							</div>
						</li>
						<li class="search-item">
							<div class="cf">
								<div class="col-l">
									<a href="javascript:void(0)" class="bubble no-2"></a>
								</div>
								<div class="ml_10 gis_l_address">
									<div>
										<span><a href="javascript:void(0)" class="n-blue">龙都国际大酒店</a></span>
									</div>
									<div class="content-des n-grey">星级酒店</div>
									<div class="content-des">天桥区北园路421号（东亚家居旁边）</div>
									<div class="content-des">0531-88776655</div>
								</div>
								<div class="gis_r_img">
									<img src="<%=realPath%>/plug-in/chrhc/images/gisImg.png"
										width="90px" height="60px" />
								</div>
							</div>
						</li>
						<li class="search-item">
							<div class="cf">
								<div class="col-l">
									<a href="javascript:void(0)" class="bubble no-3"></a>
								</div>
								<div class="ml_10 gis_l_address">
									<div>
										<span><a href="javascript:void(0)" class="n-blue">龙都国际大酒店</a></span>
									</div>
									<div class="content-des n-grey">星级酒店</div>
									<div class="content-des">天桥区北园路421号（东亚家居旁边）</div>
									<div class="content-des">0531-88776655</div>
								</div>
								<div class="gis_r_img">
									<img src="<%=realPath%>/plug-in/chrhc/images/gisImg.png"
										width="90px" height="60px" />
								</div>
							</div>
						</li>
						<li class="search-item">
							<div class="cf">
								<div class="col-l">
									<a href="javascript:void(0)" class="bubble no-4"></a>
								</div>
								<div class="ml_10 gis_l_address">
									<div>
										<span><a href="javascript:void(0)" class="n-blue">龙都国际大酒店</a></span>
									</div>
									<div class="content-des n-grey">星级酒店</div>
									<div class="content-des">天桥区北园路421号（东亚家居旁边）</div>
									<div class="content-des">0531-88776655</div>
								</div>
								<div class="gis_r_img">
									<img src="<%=realPath%>/plug-in/chrhc/images/gisImg.png"
										width="90px" height="60px" />
								</div>
							</div>
						</li>
						<li class="search-item">
							<div class="cf">
								<div class="col-l">
									<a href="javascript:void(0)" class="bubble no-5"></a>
								</div>
								<div class="ml_10 gis_l_address">
									<div>
										<span><a href="javascript:void(0)" class="n-blue">龙都国际大酒店</a></span>
									</div>
									<div class="content-des n-grey">星级酒店</div>
									<div class="content-des">天桥区北园路421号（东亚家居旁边）</div>
									<div class="content-des">0531-88776655</div>
								</div>
								<div class="gis_r_img">
									<img src="<%=realPath%>/plug-in/chrhc/images/gisImg.png"
										width="90px" height="60px" />
								</div>
							</div>
						</li>
						<li class="search-item">
							<div class="cf">
								<div class="col-l">
									<a href="javascript:void(0)" class="bubble no-6"></a>
								</div>
								<div class="ml_10 gis_l_address">
									<div>
										<span><a href="javascript:void(0)" class="n-blue">龙都国际大酒店</a></span>
									</div>
									<div class="content-des n-grey">星级酒店</div>
									<div class="content-des">天桥区北园路421号（东亚家居旁边）</div>
									<div class="content-des">0531-88776655</div>
								</div>
								<div class="gis_r_img">
									<img src="<%=realPath%>/plug-in/chrhc/images/gisImg.png"
										width="90px" height="60px" />
								</div>
							</div>
						</li>
						<li class="search-item">
							<div class="cf">
								<div class="col-l">
									<a href="javascript:void(0)" class="bubble no-7"></a>
								</div>
								<div class="ml_10 gis_l_address">
									<div>
										<span><a href="javascript:void(0)" class="n-blue">龙都国际大酒店</a></span>
									</div>
									<div class="content-des n-grey">星级酒店</div>
									<div class="content-des">天桥区北园路421号（东亚家居旁边）</div>
									<div class="content-des">0531-88776655</div>
								</div>
								<div class="gis_r_img">
									<img src="<%=realPath%>/plug-in/chrhc/images/gisImg.png"
										width="90px" height="60px" />
								</div>
							</div>
						</li>
					</ul>
				</div>
				<div class="datagrid-pager pagination">
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
								<td><input class="pagination-num" type="text" value="1"
									size="2"></td>
								<td><span style="padding-right: 6px;">/1</span></td>
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
												class="l-btn-empty pagination-load">&nbsp;</span></span></span></a></td>
							</tr>
						</tbody>
					</table>
					<div style="clear: both;"></div>
				</div>
			</div>
			<!-- 右键菜单图层 -->
			<div class="right-button-box">
				<ul>
					<li>新建</li>
					<li>修改</li>
					<li>其他</li>
				</ul>
			</div>
			<!-- 图层控制按钮 -->
			<div class="gis-icons layer-control-button"></div>
			<div class="gis-icons layer-show-button"></div>
			<div class="gis-icons ruler-button"></div>
			<div class="gis-icons case-button"></div>
			<!-- 图层显示图层 -->
			<div class="layer-list">
				<div class="layer-list-top">
					<div class="layer-list-title">图层显示</div>
					<div class="layer-list-close">X</div>
				</div>
				<div class="layer-list-main">
					<ul id="layer-show-hide">
						<li>图层1<span class="checked-icons"></span></li>
						<li class="li-checked">图层2<span class="checked-icons"></span></li>
						<li class="li-checked">图层3<span class="checked-icons"></span></li>
						<li>图层4<span class="checked-icons"></span></li>
						<li>图层5<span class="checked-icons"></span></li>
					</ul>
				</div>
			</div>
			<!-- 图层控制图层 -->
			<div class="layer-edit-list">
				<div class="layer-list-top">
					<div class="layer-list-title">图层编辑</div>
					<div class="layer-list-close">X</div>
				</div>
				<div class="layer-list-main">
					<ul id="layer-control">
						<li>图层1<span class="checked-icons"></span></li>
						<li>图层2<span class="checked-icons"></span></li>
						<li>图层3<span class="checked-icons"></span></li>
						<li>图层4<span class="checked-icons"></span></li>
						<li>图层5<span class="checked-icons"></span></li>
					</ul>
					<div class="layer-inner-buttons">
						<button id="layer-create" disabled="disabled">
							<span class="layer-button-icons layer-create disable"></span>新建
						</button>
						<button id="layer-create-cancel" style="display: none;">
							<span class="layer-button-icons layer-cancel"></span>退出新建
						</button>
						<button id="layer-edit" disabled="disabled">
							<span class="layer-button-icons layer-edit disable"></span>编辑
						</button>
						<button id="layer-edit-cancel" style="display: none;">
							<span class="layer-button-icons layer-cancel"></span>退出编辑
						</button>
					</div>
				</div>
			</div>
			<!-- tooltipp -->
			<div class="tooltipp" style="width: 88px;">
				<div class="tooltipp-main">如家快捷酒店</div>
				<div class="tooltipp-bottom"></div>
			</div>
		</div>
	</div>
	<input id="giscoords" type="hidden">
	<input id="gisname" type="hidden">
	<input id="gisid" type="hidden">
	<script type="text/javascript">
		$(function() {
			$(".map-search-result-close").click(function() {
				$(".map-search-result").hide();
			});
			$(".layer-list-close").click(function() {
				$(".layer-list").hide();
			});
			$(".layer-show-button").click(function() {
				if ("block" == $(".layer-list").css("display")) {
					$(".layer-list").hide();
				} else {
					$(".layer-list").show();
				}

			});
			$(".map-hot-classification li").click(function() {
				$(this).addClass("checked").siblings().removeClass("checked");
			});
			//图层新建按钮点击
			$("#layer-create").click(
					function() {
						$("#layer-control").attr("disabled", true);
						$("#layer-create").hide();
						$("#layer-create-cancel").show();
						$("#layer-edit").attr("disabled", true).find("span")
								.addClass("disable");
					});
			//图层退出新建按钮点击
			$("#layer-create-cancel").click(
					function() {
						$("#layer-control").attr("disabled", false);
						$("#layer-create").show();
						$("#layer-create-cancel").hide();
						//TODO 判断有选中才置为可用
						$("#layer-edit").attr("disabled", false).find("span")
								.removeClass("disable");
					});
			//图层编辑按钮点击
			$("#layer-edit").click(
					function() {
						$("#layer-control").attr("disabled", true);
						$("#layer-edit").hide();
						$("#layer-edit-cancel").show();
						$("#layer-create").attr("disabled", true).find("span")
								.addClass("disable");
					});
			//图层退出编辑按钮点击
			$("#layer-edit-cancel").click(
					function() {
						$("#layer-control").attr("disabled", false);
						$("#layer-edit").show();
						$("#layer-edit-cancel").hide();
						$("#layer-create").attr("disabled", false).find("span")
								.removeClass("disable");
					});
			//点击选中其中一个图层
			$("ul#layer-control li")
					.click(
							function() {
								if ("disabled" != $("#layer-control").attr(
										"disabled")) {
									if ($(this).hasClass("li-checked")) {
										$(this).removeClass("li-checked");
										$("#layer-create").attr("disabled",
												true).show().find("span")
												.addClass("disable");
										$("#layer-edit").attr("disabled", true)
												.show().find("span").addClass(
														"disable");
									} else {
										$(this).addClass("li-checked")
												.siblings().removeClass(
														"li-checked");
										$("#layer-create").attr("disabled",
												false).show().find("span")
												.removeClass("disable");
										$("#layer-edit")
												.attr("disabled", false).show()
												.find("span").removeClass(
														"disable");
									}
								}

							});
		});
	</script>
</body>

</html>
