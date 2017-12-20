if('undefined' == typeof(ChrhcMap)){
	ChrhcMap = {
		
	};
}


ChrhcMap = function(config){
	this._init(config);
};
ChrhcMap.prototype = {
	baseMap:null,
	eyeMap:null,
	callBack:null,
	//<script type="text/javascript" src="http://APIInKey.alayun.net/?mapid=EdushiMap&city=nantong_ntdemo&w=800&x=11894&y=14420&eye=true&e=gb2312&z=1&v=2&zb=true&s=true"></script>
	baseApiUrl:ChrhcMapConfig.baseApiUrl,
	_addedLayers:null,//HashMap,存储已经添加的所有图层
	_layerIndex:100,

	_shapeEditor:null,
	_drawLayer:null,
	_centerMarkerEntityLayer:null,
	_tempMarkerEntityLayer:null,
	hasLoadedShapeEditor:false,
	_showCenter:false,
	_showContextMenu:false,
	_canEnableSpotClick:false,
	_contextMenuHashMap:null,
	_mapZoomChange:null,
	_mapMouseWheelEvent:null,
	drawState:"",
	measureResultLayer:null,
	/*
	地图中心点开关
	*/
	showCenter:function(state){
		var that = this;
		if(state){
			that._showCenter = true;
			that._addCenterMarker(that);
		}else{
			that._showCenter = false;
		if(that._centerMarkerEntityLayer!=null){
			that._centerMarkerEntityLayer.clearFeatures();
		}
			
		}
		
	},
	//初始化参数	
	/*
	option={ 
		mapId:"map"
		mapWidth:800,
		mapHeight:800,
		showEye:true,
		callBack:callBackFun
	
	}

	*/
	_init:function(option){
		var that = this;
			that._addedLayers = new HashMap();
			
			that.callBack = option.callBack;
			that._mapZoomChange = option.mapZoomChange;
			that._mapMouseWheelEvent = option.mapMouseWheelEvent;
		var mapId = option.mapId;
		
		var mapWidth = option.mapWidth==undefined?800:option.mapWidth;
		var mapHeight = option.mapHeight==undefined?800:option.mapHeight;
			that._showCenter = option.showCenter==undefined?false:option.showCenter;
		var centerTo = option.centerTo;
		//var initX = 8284;
		//var initY = 8584;//老接口对应位置废弃
		//var initX = 43622912;
		//var initY = 54094592;
		var initX = 43608160;
		var initY = 54102880;
		if(centerTo !=null &&centerTo!=undefined &&centerTo !='null'){
			initX = centerTo.split(",")[0];
			initY = centerTo.split(",")[1];
			if(option.showLevel==undefined ||option.showLevel ==null || option.showLevel =='null'){
				option.showLevel = 2;
			}
		}else{//说明session或者url参数都没有传递中心点
			if(option.showLevel==undefined ||option.showLevel==null || option.showLevel =='null'){
				option.showLevel = 5;
			}
		}
		var showEye = option.showEye==undefined?false:option.showEye;
		var showLevel = option.showLevel==undefined?5:option.showLevel;
		var showMapSwitch = option.showMapSwitch==undefined?false:option.showMapSwitch;
			that._showContextMenu = option.showContextMenu==undefined?false:option.showContextMenu;
		
		
		
		var innerHtml = ' <div id="EdushiMap"></div>'
						   +'<div id="eyeEdushiMap" style="position: absolute; bottom: 0px; right: 330px; bottom: 10px"></div>';
						   //alert(innerHtml);

		jQuery("#"+mapId).append(innerHtml);
		var that = this;
		//var api =that.baseApiUrl+"w="+mapWidth+"&h="+mapHeight+"&x="+initX+"&y="+initY+"&eye="+showEye+"&e=utf-8&z="+showLevel+"&v=0&zb=true&s="+showMapSwitch;
	
		var api =that.baseApiUrl+"w="+mapWidth+"&h="+mapHeight+"&x="+initX+"&y="+initY+"&eye="+showEye+"&e=utf-8&z="+showLevel+"&zb=true&s="+showMapSwitch;
			that._inputScript(api);
			
			
	},
	_loadModule:function(){
		var that = this;
			that.baseMap.main = that;
		//that.baseMap.LoadControl("InfoWindow", function () {
			
		//});
		that.baseMap.LoadModule('PointerTip', function () {
                
        })	;
		
		
		if(that._showCenter){
			that._addCenterMarker(that);
			that.baseMap.attachEvent(AlaMap.KeyWord.EventName.MapMovIng,function(){
				if(that._showCenter){
					that._addCenterMarker(that);
				}	
			});
			that.baseMap.attachEvent(AlaMap.KeyWord.EventName.MapMoveEnd,function(){
				if(that._showCenter){
					that._addCenterMarker(that);
				}
			});
			that.baseMap.attachEvent(AlaMap.KeyWord.EventName.MapMoveEnd,function(){
				if(that._showCenter){
					that._addCenterMarker(that);
				}
			});
			
		};
		/*that.baseMap.attachEvent(AlaMap.KeyWord.EventName.MapMouseWheel,function(e){
			var that = this;
				e;
				that.main._mapMouseWheelEvent(e);
				//that.currentLevel = that.baseMap.Zoom();
			});	
		*/	
		that.baseMap.attachEvent(AlaMap.KeyWord.EventName.MapZoomChange,function(zoom){
			var that = this;
			//debugger;
			if(that.main._mapZoomChange!=undefined){
				that.main._mapZoomChange(zoom);
			}
		});	
		
		that.baseMap.LoadModule('ShapeEditor', function () {//拖拽编辑模块也是外置的模块，需要动态加载
				that._shapeEditor = that.baseMap.ShapeEditor; //动态加载完毕后，地图对象上会有一个同模块名的对象，系统约定
				that._shapeEditor.onStop = function (curline, alllines) {//该事件中一般用于保存编辑后的坐标
				   /* var s = curline != null ? '当前正在编辑的图形坐标串：' + curline.GetCoords() : '当前没有正在编辑的图形 ';
					s += '\n共有可编辑图形：' + alllines.length + '个';
					alert(s);
				*/
				};
				if(that.callBack!=undefined){
					that.callBack(vEdushiMap,that);
				}
			});
	},
	_addCenterMarker:function(main){
		var x = main.baseMap.CenterX();
		var y = main.baseMap.CenterY();
		var config = {baseMap:main.baseMap,symbleType:"Markers",markerUrl:ChrhcMapConfig.centerMarkerUrl};
		var markerSymble = new ChrhcMap.Symble(config);
		var dataSource = [{name:"图标",
						   id:"图标_001",
						   coords:x+","+y
						}];
		var layerConfig = {
						map:main,//初始化地图返回的地图对象
						layerName:"图标",
						layerType:"Entitys",//
						symble:markerSymble,
						zIndex:1,
						dataSource:dataSource
				}
		if(main._centerMarkerEntityLayer==null){
			main._centerMarkerEntityLayer = new ChrhcMap.Layer(layerConfig);
		}else{
			main._centerMarkerEntityLayer.clearFeatures();
			main._centerMarkerEntityLayer.addData(dataSource);
		}	
	},
	_addTempMarker:function(x,y,markerUrl){
		var that = this;
		var config = {baseMap:that.baseMap,symbleType:"Markers",markerUrl:markerUrl};
		var markerSymble = new ChrhcMap.Symble(config);
		var dataSource = [{name:"图标",
						   id:"图标_002",
						   coords:x+","+y
						}];
		var layerConfig = {
						map:that,//初始化地图返回的地图对象
						layerName:"图标",
						layerType:"Entitys",//
						symble:markerSymble,
						dataSource:dataSource
				}
		if(that._tempMarkerEntityLayer==null){
			that._tempMarkerEntityLayer = new ChrhcMap.Layer(layerConfig);
		}else{
			that._tempMarkerEntityLayer.clearFeatures();
			that._tempMarkerEntityLayer.addData(dataSource);
		}	
	},
	_addLayer:function(key,layer){
		var that = this;
			that._addedLayers.put(key,layer);
			that._layerIndex++;
		
	},
	/*
	删除所有图层
	*/
	removeAllLayers:function(){
		var layers = this._addedLayers.values();
		for(var i=0;i<layers.length;i++){
			layers[i].innerHTML = '';			
		}
		//清空HashMap
		this._addedLayers.clear();
		
		
	},
	/*
	删除指定的图层
	layerNames:图层名称[]
	*/
	removeLayers:function(layerNames){
		var that = this;
		for(var i=0;i<layerNames.length;i++){
			if(layerNames[i]){
				var values = that._addedLayers.values();
				var keys = that._addedLayers.keySet();
				var layer = that._addedLayers.get(layerNames[i]);
				if(layer!=null){
					layer.innerHTML = '';	
				}
					
				that._addedLayers.remove(layerNames[i]);			
			}
		}
		this._drawLayer = null;
		
	},
	/*
	隐藏所有图层
	*/
	hiddenAllLayers:function(){
		var layers = this._addedLayers.values();
		for(var i=0;i<layers.length;i++){
			layers[i].style.display = "none";		
		}

		
	},
	/*
	显示所有图层
	*/
	showAllLayers:function(){
		var layers = this._addedLayers.values();
		for(var i=0;i<layers.length;i++){
			if(layers[i].innerHTML!=''){
				layers[i].style.display = "";		
			}
			
		}
		
	},
	/*
	获取所有已添加图层
	return:HashMap(key,value)
	*/
	getAllLayers:function(){
		var that = this;
		if(that._addedLayers!=null){
			return that._addedLayers;
		}else{
			return null;
		}
		
	},
	/*
	显示指定图层
	layerNames ：图层名称[]
	*/
	showLayers:function(layerNames){
		var that = this;
		//debugger;
		for(var i=0;i<layerNames.length;i++){
			if(layerNames[i]){
				if(layerNames[i]==ChrhcMapConfig.spotLayerName){
					 that.baseMap.ViewSpotAreas(true, ChrhcMapConfig.spotLayerName);
					
				}else{
					var layer = that._addedLayers.get(layerNames[i]);
					if(layer&&layer.style){
						layer.style.display = "";	
					}
				}
				
			}else{
				alert("layerNames");
			}
		}
		
		
	},
	/*
	隐藏指定图层
	layerNames ：图层名称[]
	*/
	hiddenLayers:function(layerNames){
		var that = this;
		for(var i=0;i<layerNames.length;i++){
			if(layerNames[i]){
				if(layerNames[i]==ChrhcMapConfig.spotLayerName){
					 that.baseMap.ViewSpotAreas(true, ChrhcMapConfig.spotLayerName);
					
				}else{
					var layer = that._addedLayers.get(layerNames[i]);
					if(layer&&layer.style){
						layer.style.display = "none";	
					}
				}
			}else{
				alert("layerNames");
			}
		}
		
		
	},
	_inputScript:function(inc){
		var that = this;
		var isWinRT = (typeof Windows === "undefined") ? false : true;
		//alert("isWinRT="+isWinRT);
        if (isWinRT) {
            var script = '<' + 'script type="text/javascript" src="' + inc + '"' + '><' + '/script>';
            document.writeln(script);
        } else {
            var script = document.createElement("script");
            script.src = inc;
			script.mainFun = that;
            document.getElementsByTagName("HEAD")[0].appendChild(script);	
		}
		//如果是IE
			if(!+[1,]){
				script.onreadystatechange = function(){
				//alert(!script.readyState +" "+ script.readyState+"=='loaded'" +" "+ script.readyState+"=='complete'");
						if(!script.readyState     //这是FF的判断语句，因为ff下没有readyState这人值，IE的readyState肯定有值
							|| script.readyState=='loaded' || script.readyState=='complete'   // 这是IE的判断语句
							){
							//alert("script.readyState="+script.readyState);
							var that = script.mainFun;				
													script.onreadystatechange=null;										that._initMap(that);

												}	
							}
				
			}else{//非IE
				script.onload = function(){
				//alert(!script.readyState +" "+ script.readyState+"=='loaded'" +" "+ script.readyState+"=='complete'");
						if(!script.readyState     //这是FF的判断语句，因为ff下没有readyState这人值，IE的readyState肯定有值
							|| script.readyState=='loaded' || script.readyState=='complete'   // 这是IE的判断语句
							){
							//alert("script.readyState="+script.readyState);
							var that = script.mainFun;									
													script.onreadystatechange=null;
													that._initMap(that);
												}	
							}
			}
		
    },
	
	_initMap:function(mainFun){
		var that = mainFun;
		//alert("that="+that);
		that.baseMap = vEdushiMap;
		that.baseMap.main = mainFun;
        that.eyeMap = typeof veyeEdushiMap === "undefined"? null: veyeEdushiMap;
		that._loadModule();
        onresize();
		that.baseMap.Property.flgContextMenu = that._showContextMenu;
		  vEdushiMap.ViewSpotAreas(true, 'hotarea');
		
		
	},
	/*
		打开建筑物点击事件
		state:true/false
		callBackFunction：function (e){
			e:ID/Name/Coords[]/Address
		}
	*/
	enableSpotClick:function(callBackFunction,delay){
		var that = this;
			delay = delay==null?500:delay;
			that.baseMap.attachEvent(AlaMap.KeyWord.EventName.SpotClick, function (spot) { //热区点击事
				 setTimeout(timeOut,delay);
				 function timeOut(){
					 callBackFunction(spot);
				 }
				
				
					
              //  this.detachEvent(AlaMap.KeyWord.EventName.SpotClick, arguments.callee);
            });
		
	},
	/*
		控制右键菜单是否显示
		state：true,false
	*/
	showContextMenu:function(state,contextMenuData,scope){
		var that = this;
		
		if(that.baseMap!=null){
			that.baseMap.Property.flgContextMenu = state;
		}
		if(state){
			that._contextMenuHashMap = null;
			that._contextMenuHashMap = new HashMap();
			var contextmenu = that.baseMap['ContextMenu'];
				that._clearContextMenu(contextmenu);
				that._createContextMenu(contextmenu,contextMenuData,scope);
			
		}
	},
	resetContexMenu:function(contextMenuData,scope){
		var that = this;
		var contextmenu = that.baseMap['ContextMenu'];
		 if (contextmenu) {
             for (var key in contextmenu.Items) {
                 var item = contextmenu.Items[key];
                 for(var i=0;i<contextMenuData.length;i++){
             		var oKey = contextMenuData[i].Key;
             		if(key = oKey){
             			item.Visible = contextMenuData[i].Visible;
             		}
                 }
             }
         }
         	
         /* that.baseMap.main = that;  
		 */
	    //that.baseMap.attachEvent(AlaMap.KeyWord.EventName.ContextMenuClick,that._contextMenuClickEvent, [scope]);
	},
	 //清除默认菜单

    _clearContextMenu:function(contextmenu) {
		var that = this;
                if (contextmenu) {
                    for (var key in contextmenu.Items) {
                        var item = contextmenu.Items[key];
                        if (item.Body) {
                            item.Body.parentNode.removeChild(item.Body);
                        }
                        delete contextmenu.Items[key];
                    }
                }
    },
	//创建右键菜单

    _createContextMenu:function(contextmenu,contextMenuData,scope) {
    	var that = this;		
    	for(var i=0;i<contextMenuData.length;i++){
    		var Key = contextMenuData[i].Key;
    		var Caption = contextMenuData[i].Caption;
    		var Visible = contextMenuData[i].Visible;
    		var CallBack = contextMenuData[i].CallBack;
    		var newitem = new AlaMap.MenuItem();
	    		newitem.Key = Key;
	    		newitem.Caption = Caption;
	    		newitem.Visible = Visible;
	            contextmenu.Append(newitem);
	            that._contextMenuHashMap.put(Key,CallBack);
    	}
    	that.baseMap.main = that;  	
    	that.baseMap.attachEvent(AlaMap.KeyWord.EventName.ContextMenuClick,that._contextMenuClickEvent, [scope]);
        
	},
	_contextMenuClickEvent:function(menukey,winx,winy,x,y,spot,menuelement,entity,e){
		
		var that = this.main;
		var menukey = menukey;
		var winx = winx;
		var winy = winy;
		var x = x;
		var y = y;
		var spot = spot;
		var menuelement =menuelement;
		var entity = entity;
		var id;
		var name;
		var coords;
		var layerId;
		if(menuelement!=null&menuelement!=undefined){
			id = menuelement.id;
			name = menuelement.name;
			coords = menuelement.coords;
			layerId = menuelement.layerId;
		}
		var callBackReturnObject = {key:menukey,id:id,layerId:layerId,name:name,coords:coords,layerControl:e};
		var isContain = that._contextMenuHashMap.containsKey(menukey);
		this.detachEvent(AlaMap.KeyWord.EventName.ContextMenuClick, arguments.callee); //使用完后把自己销毁
		//a.enableEdit(true,["40280e814d64a6e2014d64c6fc2e0002"]);
		if(menukey =="editOldData"&&layerId !=null&&layerId!=undefined&&e!=undefined){
			e.enableEdit(true,[layerId]);
		}
		if(menukey =="submitEditedData"&&layerId !=null&&layerId!=undefined&&e!=undefined){
			e.enableEdit(false,[layerId]);
			
		}
		if(isContain){
			var callBack = that._contextMenuHashMap.get(menukey);
				if(callBack!=null&&callBack!=undefined){
					callBack(callBackReturnObject);
				}
				
		}
		
		
		
	},
	/*定位到某个位置
	*lon:经度
	*lat:维度
	*scale:缩放比例尺
	*effect:是否平滑移动，主要是2.5维
	*callBackFun:定位结束后执行的回调函数，主要是2.5维
	*markerUrl:如果该参数不为null,则表示要在定位中心点添加指定的marker
	*/
	setCenter:function(lon, lat, scale,effect,callBackFun,markerUrl){
		
		var that = this;
		
		debugger;
			scale = scale==null?2:scale;
		var isEffect = effect==null||effect==""||effect==undefined?false:true;
		var isCallBackFun = callBackFun==null||callBackFun==""||callBackFun==undefined? null:callBackFun;
		
		if(that.baseMap&&lon&&lat){
			that.baseMap.MoveTo(lon,lat,isEffect,callBackFun);
			that.baseMap.ZoomTo(scale,isEffect);
		}else{
			alert("定位参数错误");
		}
		if(markerUrl!=null){
			that._addTempMarker(lon,lat,markerUrl);
			
		}
		
	},
	getCenter:function(){
		var centerX = this.baseMap.CenterX();
		var centerY = this.baseMap.CenterY();
		var centerCoords = [centerX,centerY];
		return centerCoords;
	},
	zoomTo:function(level,isEffect){
		var that = this;
			level = level<0?0:level;
			isEffect = isEffect==null?true:isEffect;
			that.baseMap.ZoomTo(level,isEffect);
	},
	pan:function(type){
		var arrCoords =  this.getMapPosCurrentRegion();
		//左下
		var minX = arrCoords[3].X, minY = arrCoords[1].Y,
		//右上
			maxX = arrCoords[1].X, maxY = arrCoords[3].Y;
		var level = this.getCurrentZoom();
		var centerArr = this.getCenter();
		var newCenterX;
		var newCenterY;
		switch (type)
		{
		case "0":
			 newCenterX = centerArr[0];
			 newCenterY = maxY;
		  break;
		case "1":
			 newCenterX = centerArr[0];
			 newCenterY = minY;
		  break;
		case "2":
			 newCenterX = minX;
			 newCenterY = centerArr[1];
		  break;
		case "3":
			 newCenterX = maxX;
			 newCenterY = centerArr[1];
		  break;
		}
		this.setCenter(newCenterX,newCenterY,level,true);
	},
	getCurrentZoom:function(){
		var that = this;
		var zoom = that.baseMap.Zoom();
		return zoom;
	},
	/*测距，测面
	type:1:2:面积
	
	
	*/
	measure:function(type){
			var type = type==""||type==null||type==undefined?"1":type;
			if(this.baseMap!=null&&type =="1"){
				this.baseMap.main = this;
				this.baseMap.StartScale();
				//测距取点操作进行中
				this.baseMap.attachEvent(AlaMap.KeyWord.EventName.ScaleIng, function (coords,a,b,c,d) {
					debugger;
					var that = this.main;
				/*	var dataSource = {
								name:"测距",
								id:	"measureResult_"+UtilMisc.getCurrentTime();,
								coords :coords,
								style : {
											Size:5,
											Color:'#ff3300',
											FillColor:'#ff3300',
											Strokecolor:'#ff8300',
											Opacity:0.8,
											Dashstyle:"dashdot"
										}
								}
						
					};
					if(that.measureResultLayer==null){
						var layerConfig = {
							map:that,
							layerName:"测距",
							layerType:"Vector"
						};
						
						that.measureResultLayer = new ChrhcMap.Layer(layerConfig);
					}
					that.measureResultLayer.addData([dataSource]);
					*/
				});

				//测距取点操作结束
				this.baseMap.attachEvent(AlaMap.KeyWord.EventName.ScaleEnd, function (distance, flg) {
					
				});
				/*#################以下代码是将测量时获得的线进行重画在地图上。后期开发。
				
				
				*/
			}else if(this.baseMap!=null&&type =="2"){
				this.baseMap.LoadModule('Area', function() {
					
					this.baseMap.attachEvent(AlaMap.KeyWord.EventName.AreaEnd, function(area, flg) {
										  // alert('面积'+area+'平方米'+ "  flg="+flg);
						//area:面积，flg:true->系统执行了stop，false->人工执行了stop
					   
											});
					this.baseMap.Area.StartArea();
				});	
			}	
	},
	//停止测距方法
	stopScal:function(){
		if(this.baseMap){
			this.baseMap.StopScale();
		}
		//
	},
    //---------拖拽编辑测试--------------
     _initEditor:function(drawEndCallBack,main) {
		 var that = this;
				that._shapeEditor.onStop = function (curline, alllines) {//该事件中一般用于保存编辑后的坐标
				   /* var s = curline != null ? '当前正在编辑的图形坐标串：' + curline.GetCoords() : '当前没有正在编辑的图形 ';
					s += '\n共有可编辑图形：' + alllines.length + '个';
					alert(s);
				*/
				};
				
				if(drawEndCallBack!=undefined&&drawEndCallBack!=null){
					
					that._shapeEditor.onDragStick = function(coords){
						var attribute = this.Current.attribute;
						var shapeId = this.Current.ID;
						var coords = this.Current.GetCoords();
						var callBackObject = {success:true,drawType:main,main:main,shapeId:shapeId,coords:coords,attribute:attribute};
							drawEndCallBack(callBackObject);
					};
				}
			
    },
	
	    //画可编辑的折线
    _drawPolyLineEditEnabled:function(drawEndCallBack) {
    	this.drawState = "line";
		this.baseMap.drawMain = this;
        this.baseMap.attachEvent(AlaMap.KeyWord.EventName.GetCoordsEnd, function (coords) {
			var that = this.drawMain;
			if(that.drawState =="line"){
			this.detachEvent(AlaMap.KeyWord.EventName.GetCoordsEnd, arguments.callee); //使用完后把自己销毁
            var lineid = that._createNewShapeData(null,coords,true);
			if(lineid!=null){
				var callBackObject = {success:true,drawType:that.drawState,shapeId:lineid,coords:coords+""};
				if(drawEndCallBack!=undefined&&drawEndCallBack!=null){
					drawEndCallBack(callBackObject);
				}
			}
			}
			
        });
        this.baseMap.StartGetCoords();
    },
    //画可编辑的多边形
    _drawPolyEditEnabled:function(drawEndCallBack) {
    	this.drawState = "polygon";
		this.baseMap.drawMain = this;
        this.baseMap.attachEvent(AlaMap.KeyWord.EventName.GetCoordsEnd, function (coords) {
			var that = this.drawMain;
			if(that.drawState =="polygon"){
				this.detachEvent(AlaMap.KeyWord.EventName.GetCoordsEnd, arguments.callee); //使用完后把自己销毁
				
				var lineid = that._createNewShapeData(null,coords,false);
				//debugger;
				if(lineid!=null){
					var current = that._shapeEditor.Get(lineid);
					var startX,startY;
					var success = false;
					if(current!=null&&current.Points.length>0){
						 startX = current.Points[0].X;
						 startY = current.Points[0].Y;
						 success = true;
					}
					var callBackObject = {success:success,drawType:that.drawState,shapeId:lineid,coords:coords+","+startX+","+startY};
					if(drawEndCallBack!=undefined&&drawEndCallBack!=null){
						drawEndCallBack(callBackObject);
					}	
					
				}
			}
				
			
        });
        this.baseMap.StartGetCoords();
		
    },
	addShapeEditorData:function(shape,IsPolyLine){
			var coords = shape.coords;
			var that = this;
			var lineid = that._createNewShapeData(shape,coords,IsPolyLine);
			return lineid;
	},
	_createNewShapeData:function(shape,coords,IsPolyLine){
		var that = this;
		//debugger;
		if(coords!=null){
			var line1 = new AlaMap.Module.ShapeEditor.LineData(coords);
				line1.IsPolyLine = IsPolyLine;
				line1.attribute = shape;
			var lineid = that._shapeEditor.Add(line1);
				that._shapeEditor.Show(lineid);
				return lineid;
		}else{
			return null;
		}
		
	},
	/*
	style:{
			Color:"#FFEE00",
			FillColor:"yellow",
			Opacity:0.7,
			Size:2,
			StrokeColor:"#FFEE00"
	}
	return newId;
	*/
	reSetDrawStyle:function(id,style){
		var that = this;
		var current = that._shapeEditor.Get(id);
		var coords = current.GetCoords();
		var line1 = new AlaMap.Module.ShapeEditor.LineData(coords);
			line1.IsPolyLine = current.IsPolyLine;
			if(current.IsPolyLine){
				style.StrokeColor = style.Color;
			}
			line1.Style = style;
		var rId = that._shapeEditor.Remove(id);
		var lineid = that._shapeEditor.Add(line1);
			that._shapeEditor.Show(lineid);
		return lineid;
	},
	 //画可编辑的点实体
	 /*drawEndCallBack:自定义回调方法
		markerSymble：画图所用的图标样式
	 
	 */
    _drawEntityEditEnabled:function(markerSymble,drawEndCallBack,spotClickCallBackEvent) {
		if(this._drawLayer==null){
			
			var layerConfig = {
						map:this,//初始化地图返回的地图对象
						layerName:"drawEntity",
						layerType:"Entitys",//
						symble:markerSymble,
						editEvent:{onDrawEnd:drawEndCallBack}//仅当config.enableEdit==true有效
				};
			this._drawLayer = new ChrhcMap.Layer(layerConfig);
			
		}
			
		this.baseMap.PointerTip.Show(ChrhcMapConfig.mouseTipsHtml, ChrhcMapConfig.mouseTipsOffsetX,ChrhcMapConfig.mouseTipsOffsetY);
		this.baseMap.drawMain = this;
		//alert("se="+spotClickCallBackEvent);
		this.enableSpotClick(spotClickCallBackEvent);
		this.baseMap.attachEvent(AlaMap.KeyWord.EventName.MapClick, this._drawEntityOnMapClickEvent);
        
        //this.baseMap.StartGetCoords();
		
    },
    _drawEntityOnMapClickEvent:function(x,y,e){
    	
    	this.PointerTip.Hide();
		//this.detachEvent(AlaMap.KeyWord.EventName.MapClick, arguments.callee);
		var that = this.drawMain;

		var id = "drawPoint_"+UtilMisc.getCurrentTime();
			that._innertData(that._drawLayer,id,x,y);
		if(that._drawLayer._editEvent!=null&&that._drawLayer._editEvent.onDrawEnd!=undefined){
			that._drawLayer._editEvent.onDrawEnd(id,id,x+","+y);	
			that._canEnableSpotClick = true;
		}
    },
   
	_innertData:function(layer,id,x,y){
		var dataSource =[];
		
		    dataSource.push(
						{name:id,
						id:id,
						coords:x+","+y
						});
						
					if(layer!=null){
						layer.clearFeatures();
						layer.addData(dataSource);
					}
	},				
		
	/*
	在编辑状态下，删除所选择的对象
	callBack(id):删除所选对象后执行的回调方法,id:所删除对象的ID
	*/
	removeSelectedShape:function(callBack){
		if(this._shapeEditor!=null&&this._shapeEditor.Current != null){
			var removedId = this._shapeEditor.Current.ID;
			this._shapeEditor.Remove(this._shapeEditor.Current.ID);
			if(callBack!=null){
				callBack(removedId);
			}
            alert('成功删除选中图形');
		}else {
            alert('请选中要编辑的图形');
        }
		
	},
	/*
	根据ID删除处于编辑状态的对象
	*/
	removeShapeById:function(shapeId){
		if(this._shapeEditor!=null&&shapeId!=null){
			
			this._shapeEditor.Remove(shapeId);
            alert('成功删除选中图形');
		}else {
            alert('请选中要编辑的图形');
        }
		
	},
	/*
	根据实体ID进行删除
	*/
	removeEntityById:function(entityId){
		if(this._drawLayer!=null&&entityId!=null){
			this._drawLayer.clearFeatures([entityId]);
		}
		
	},
	/*删除所有的已编辑对象
	callBack:成功删除后执行的回调方法
	
	*/
	removeAllShape:function(callBack){
		if(this._shapeEditor!=null){
			this._shapeEditor.Clear();
			if(callBack!=null){
				callBack();
			}
		}	
	},
	/*
	开始手工绘图
	type:绘图类型：polyline:线；poly：面；point：点
	drawEndCallBack(id,coords):画图结束后执行的回调方法,return:id:所画对象的ID;coords：所画的坐标串
	markerSymble:画图标时所需要自定义的图标属性
	*/
	startDraw:function(type,markerSymble,drawEndCallBack,spotClickCallBackEvent){
		
		this._initEditor(drawEndCallBack,type);
	
		switch (type)
			{
			case "polyline":
				this._drawPolyLineEditEnabled(drawEndCallBack);
			  break;
			case "polygon":
				this._drawPolyEditEnabled(drawEndCallBack);
			  break;
			case "point":
				this._drawEntityEditEnabled(markerSymble,drawEndCallBack,spotClickCallBackEvent);
			  break;
			}
		
	},
	/*停止编辑，一般用于触发保存事件
		停止编辑后，将会清除所有的已编辑的对象
	*/
    stopDraw:function() {
    	this.drawState = "";
		this.baseMap.StopGetCoords();
		if(this._shapeEditor!=null){
			this._shapeEditor.Stop();
			this._shapeEditor.Clear();	
		} 
		if(this._drawLayer!=null){
			this.removeLayers([this._drawLayer.layer.id]);
		}	
		this.baseMap.detachEvent(AlaMap.KeyWord.EventName.MapClick, this._drawEntityOnMapClickEvent);
		this.baseMap.PointerTip.Hide();
		//debugger;
		
    },
	/*
	添加信息窗口
	title:窗体标题
	divHtml:信息窗口主题内容,可以为DIV
	x: x轴位置,number
	y: y轴位置,number
	autoRemove：是否自动删除，默认为false
	*/
	addInfoWindow:function(title,divHtml,x,y,autoRemove){
		var that = this;
			autoRemove = autoRemove?true:false;
		if(that.baseMap!=undefined&&that.baseMap.InfoWindow!=null){
			that.baseMap.InfoWindow.Open('<div style=font-size:15px><h>'+title+'</h></div>'+divHtml, x,y);
			that.baseMap.InfoWindow.autoRemove = autoRemove;
		}
	},
	removeInfoWindow:function(){
		if(that.baseMap!=undefined&&that.baseMap.InfoWindow!=null){
			that.baseMap.InfoWindow.Close();
		}	
	},

	clearMarker:function(){
		if(that._markerEntityLayer!=null){
			that._markerEntityLayer.clearFeatures();
		}
	},
	reSetMapSize:function(windowWidth,windowHeight,addWidth,addHeight){
		var that  = this;
		var windowWidth = windowWidth==null?fnGetWindowWidth():windowWidth;
		var windowHeight =windowHeight==null? fnGetWindowHeight():windowHeight;
		 windowWidth = addWidth==null?windowWidth:windowWidth+addWidth;
		 windowHeight =addHeight==null? windowHeight:windowHeight+addHeight;
		
		//alert("windowHeight="+windowHeight+" windowHeight2="+windowHeight2);
		    document.getElementById("EdushiMap").style.height = windowHeight - 7 + "px";
		    document.getElementById("EdushiMap").style.width = windowWidth-7 + "px";
		    document.getElementById("EdushiMap").style.overflow  = "hidden";
		    document.getElementById("eyeEdushiMap").style.position = "absolute";
		    document.getElementById("eyeEdushiMap").style.bottom = "20px";
		    document.getElementById("eyeEdushiMap").style.right = "30px";
		    if (typeof (that.baseMap) != 'undefined') {
		    	that.baseMap.MapWidth(windowWidth-7);
		    	that.baseMap.MapHeight(windowHeight - 7);
		    }
	},
	/*
	 * string.Format("当前屏幕四角坐标：\n
	 * 左上角：{0},{1}\n
	 * 右上角：{2},{3}\n
	 * 右下角：{4},{5}\n
	 * 左下角：{6},{7}",

                arrCoords[0].X, arrCoords[0].Y,

                arrCoords[1].X, arrCoords[1].Y,

                arrCoords[2].X, arrCoords[2].Y,

                arrCoords[3].X, arrCoords[3].Y)
	 */
	getMapPosCurrentRegion:function(){
		var arrCoords = this.baseMap.GetMapPosCurrentRegion();
		return arrCoords;
	}

};
ChrhcMap.prototype.constructor = ChrhcMap;


