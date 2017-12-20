if('undefined' == typeof(ChrhcMap)){
	ChrhcMap = {
		
	};
}

/*
		option:{
				map:"",//非空
				layerName:"图表",//非空
				layerId:"",//可自定义，如果为空，系统自动添加
				layerType:"Entitys",//图层类型，包括：Entitys,图标;Vector:矢量图层，主要用来画用户自定义的线、面数据，非空
				displayInLayerSwitcher:false,//是否在默认的图层控制中显示，保留开发
				symble:markerSymble,
				editEvent:{onDragStick:onDragCompleteEvent},
				event:{onClick:clickEvent,onMouseOver:onmouseover},//可以为空
				dataSource:[
							{
							name:"",
							coords:""//坐标串
							
							}
				]//可以为空
		}
		*/
ChrhcMap.Layer = function(option){
	this._init(option);
};

ChrhcMap.Layer.prototype = {
	
	chrhcLayerId:8000,
	layer:null,
	layerType:null,
	baseMap:null,
	lineLayer:null,
	polyLayer:null,
	circleLayer:null,
	_tempCoords:null,
	_lineFeatures:null,
	_polygonFeatures:null,
	_entityFeatures:null,
	_shapeEditor:null,
	_heighLightLayer:null,
	chrhcBaseMap:null,
	isBaseLayer:false,
	_editEvent:null,
	_event:null,
	_addedEntityIds:null,
	_addedLineIds:null,
	_addPolyIds:null,
	_dataSource:null,
	_config:null,
	_enableEditState:false,
	_symble:null,
	_mouseOverInfo:null,
	_mouseClickInfo:null,
	_hadEnabledEdit:false,
	_shapeEditorDataHashMap:null,
	_ClassId:null,
	//当前图层是否可见
	visible:false,
    //layer绑定的回调事件（主要是注册鼠标滚轮事件，当前用于删除的时候注销事件）
	_callBackAttach:null,
	//初始化参数	
	_init:function(option){
		var that = this;
		that._entityFeatures = [];
		that._lineFeatures = [];
		that._polygonFeatures = [];
		that._ClassId  = UtilMisc.getCurrentTime();
		
		//默认值
		/*
		option:{baseMap:"",layerName:"图表",layerType:"Markers",displayInLayerSwitcher:false}
		*/
		var layerName = option.layerName;
			that.layerType = option.layerType;
			that.chrhcBaseMap = option.map;
			that.baseMap = that.chrhcBaseMap.baseMap;
		var isBaseLayer = option.isBaseLayer;
		

		var displayInLayerSwitcher = option.displayInLayerSwitcher;
		//debugger;
			switch (that.layerType)
			{
			case "Entitys":
				that.layer = that.createMarkersLayer(option);
			  break;
			case "Line":
				that.layer = that.createTiledDynamicRESTLayer(option);
			  break;
			case "Image":
			  that.layer = that.createImageLayer(option);
			  break;
			case "UTFGrid":
			 that.layer = that.createUTFGridLayer(option);
			  break;
			case "Vector":
			  that.layer = that.createVectorLayer(option);
			  break;
			case "WMTS":
			  that.layer = that.createWMTSLayer(option);
			  break;
			case "KML":
			  that.layer = that.createKmlLayer(option);
			  break;
			}


		
	},
	addData:function(dataParam){
		var that = this;
		switch (that.layerType)
			{
			case "Entitys":
				that._addMarkersToEntitysLayer(dataParam);
			  break;
			case "TiledDynamicRESTLayer":
				that.layer = that.createTiledDynamicRESTLayer(option);
			  break;
			case "Image":
			  that.layer = that.createImageLayer(option);
			  break;
			case "UTFGrid":
			 that.layer = that.createUTFGridLayer(option);
			  break;
			case "Vector":
				that._addFeaturesToVectorLayer(dataParam);
			  break;
			case "WMTS":
			  that.layer = that.createWMTSLayer(option);
			  break;
			case "KML":
			  that.layer = that.createKmlLayer(option);
			  break;
			}
	},
	/*
		option:{
				baseMap:"",
				layerName:"图表",
				layerType:"Markers",
				displayInLayerSwitcher:false,
				editEvent:{},
				symble:{},
				dataSource:[
							{coords:"10636,14728.5",
							name:"name",
							iconUrl:iconUrl,//此处的iconUrl会替换上面的
							addClickPopupHtml:'<img src="images/xila.jpg">"',
							windowSize:"200,130",
							autoSize:true
							}
				],//features与dataSource二者选一即可
				features：[],//features与dataSource二者选一即可
		}
		*/
	createMarkersLayer:function(option){
		//初始化标记图层类
		//初始化基本属性
		var that = this;
		that._dataSource = new HashMap();
		that._addedEntityIds = new HashMap();
		var layerName = option.layerName;
		var dataSource = option.dataSource==undefined?[]:option.dataSource;
		for(var i=0;i<dataSource.length;i++){
			var id = dataSource[i].id;
			that._dataSource.put(that._ClassId+"_"+id,dataSource[i]);	
		}
			that._editEvent = option.editEvent;
			that._event = option.event;
			//创建新的图层
		var entityLayer = that._createNewLayer(option);
		var symbole = new ChrhcMap.Symble();
			that._symble = option.symble==undefined?symbole:option.symble;
		if(that._dataSource.size()>0){
			//初始化业务属性	
				that._addEntityData(entityLayer,that._dataSource);
		}
		return entityLayer;
	},	
		
	
		/*
		option:{
				baseMap:"",
				layerName:"图表",
				layerType:"Vector",
				event:{onclick:onclick,onmouseover:onmouseover},
				dataSource:[
							{
							coords : '10436,14228.5,10608,14190.5,10646,14466.5,10190,14468.5,10304,14276.5,10436,14228.5',//收尾相接是面，其他是线
							iconUrl:iconUrl,//此处的iconUrl会替换上面的
							addClickPopupHtml:'<img src="images/xila.jpg">"',
							windowSize:"200,130",
							autoSize:true
							}
				],//features与dataSource二者选一即可
				features：[],//features与dataSource二者选一即可
		}
		*/
	createVectorLayer:function(option){
		var that = this;
			that._dataSource = new HashMap();
			that._addedEntityIds = new HashMap();
		var dataSource = option.dataSource==undefined?[]:option.dataSource;
		for(var i=0;i<dataSource.length;i++){
			var id = dataSource[i].id;
				that._dataSource.put(id,dataSource[i]);	
		}

		var layerName = option.layerName;
			that._editEvent = option.editEvent;
			that._event = option.event;
		var vectorLayer = this._createNewLayer(option);
		if(that._dataSource.size()>0){
			this._addVectorData(vectorLayer,that._dataSource.values());
			//this.chrhcBaseMap._addLayer(layerName+"_"+layerId,vectorLayer);
		}
		return vectorLayer;		
	},
	/*
		添加实体数据或者图标数据图层的数据
		图层初始化完毕后，调用addData方法时调用
	*/
	_addMarkersToEntitysLayer:function(dataSource){
		var entityLayer = this.layer;
		var dataSourceHashMap = new HashMap();
		var dataSource = dataSource==undefined?[]:dataSource;
		for(var i=0;i<dataSource.length;i++){
			var id = dataSource[i].id;
			this._dataSource.put(id,dataSource[i]);	
				dataSourceHashMap.put(id,dataSource[i]);	
		}
		var keyset = dataSourceHashMap.keySet();
		this._addEntityData(entityLayer,dataSourceHashMap);
		
	},
	/*
		添加矢量图层的数据
		图层初始化完毕后，调用addData方法时调用
	*/
	_addFeaturesToVectorLayer:function(dataSource){
		var that = this;
		var vectorLayer = this.layer;
		
		for(var i=0;i<dataSource.length;i++){
			var id = dataSource[i].id;
				that._dataSource.put(id,dataSource[i]);	
		}
		this._addVectorData(vectorLayer,dataSource);
		
	},
		/*entityLayer：layerObj :所要添加到的图层对象
		dataSource：[],:数据源
						dataSource:[
							{coords:"10636,14728.5",
							name:"",
							iconUrl:iconUrl,//此处的iconUrl会替换上面的
							addClickPopupHtml:'<img src="images/xila.jpg">"',
							windowSize:"200,130",
							autoSize:true
							}
				]
		symble:{autoresize:false,w:70,h:30,exX:0,exY:0,enableEdit:false,autoremove:false,autozoomchangeremove:false}://实体对象的相关参数	
					autoresize:false,
					w: type="Number":   元素宽度，不指定输入false
					h: type="Number":   元素高度，不指定输入false
					exX: type="Number":   偏移像素X
					exY: type="Number":   偏移像素Y
					enableEdit: type="Boolen":   是否能移动
					autoremove: type="Boolen":   是否自动删除
					autozoomchangeremove: type="Boolen":   级别缩放是否删除		
		*/
	_addEntityData:function(entityLayer,dataSourceHashMap){
		
		var that = this;
		var symbleM = this._symble;
		
		var targetLayer = entityLayer;
		var candrag,autoresize,autoremove,autozoomchangeremove;
		if(symbleM!=null){
			candrag = symbleM.enableEdit==undefined?false:symbleM.enableEdit;
			autoresize = symbleM.autoresize==undefined?false:symbleM.autoresize;	
			autoremove = symbleM.autoremove==undefined?false:symbleM.autoremove;
			autozoomchangeremove = symbleM.autozoomchangeremove==undefined?false:symbleM.autozoomchangeremove;
		}
		
		var layerId = entityLayer.id;
		var dataSource = dataSourceHashMap.values();
		if(dataSource!=undefined){
			for(var i=0;i<dataSource.length;i++){
				var symble = symbleM;
				var centerxy = dataSource[i].centerxy;
				var coords = centerxy!=null?centerxy:dataSource[i].coords;
				var name = dataSource[i].name;
				var id = dataSource[i].id;
				var symble2 = dataSource[i].symble;
				if(symble2!=undefined){
					symble = symble2;
					candrag = symble2.enableEdit==undefined?false:symble2.enableEdit;
					autoresize = symble2.autoresize==undefined?false:symble2.autoresize;	
					autoremove = symble2.autoremove==undefined?false:symble2.autoremove;
					autozoomchangeremove = symble2.autozoomchangeremove==undefined?false:symble2.autozoomchangeremove;
				}
					if(coords!=undefined&&id!=undefined){
						var coordsArr = coords.split(",");
						var length = coordsArr.length;
							if(length==2){
								//如果是点
								
								var name = dataSource[i].name;
								var x = parseFloat(coordsArr[0]);
								var y = parseFloat(coordsArr[1]);
									symble.createSymble(layerId,id,name,x,y);
								var dom = symble.div;
									dom.main = that;
									dom.geoType = ChrhcMapConfig.entityPoint;//"point";
									dom.name = name;
									dom.id = that._ClassId+"_"+id;
									dom.coords = coords;
									dom.layerId = targetLayer.id;
									that._addDomEvent(symble.div);
								var ids = "drawPoint_"+UtilMisc.getCurrentTime();
									//参数：(o, layer, autoresize, x, y, w, h, exX, exY, candrag, autoremove, autozoomchangeremove)
								//console.log('dom.name:'+dom.name+" autoresize:"+autoresize);
								//console.log('x:'+x+" y:"+y+" symble.width:"+symble.width+" symble.height:"+symble.height+" symble.offectX:"+symble.offectX+" symble.offectY:"+symble.offectY);
								//console.log('symble.div:'+symble.div+" targetLayer:"+targetLayer+" candrag:"+candrag+" autoremove:"+autoremove+" autozoomchangeremove:"+autozoomchangeremove);
								var entityId = null
								try{
									entityId = that.baseMap.AppendEntity(symble.div, targetLayer, autoresize, x, y, symble.width, symble.height, symble.offectX, symble.offectY, candrag, autoremove, autozoomchangeremove);	
								}catch(e){
									console.log('e:'+e);
								}
								
								//console.log('entityId:'+entityId);
								if(entityId!=null&&(entityId+"")!="false"&&(entityId+"")!="true"){
										that._addedEntityIds.put(entityId,symble.div);
									}
									
							}		
					}
			}
			var keySet = that._addedEntityIds.keySet();
			var keySet1=keySet;
		}
	},
	_addVectorData:function(newLayer,dataSource){
		var that = this;
		var values = that._addedEntityIds.values();
		var keySet = that._addedEntityIds.keySet();
		var targetLayer = newLayer;
		var layerId = that.chrhcBaseMap._layerIndex;
		//var dataSource = dataSourceHashMap.values();
		for(var i=0;i<dataSource.length;i++){
				var coords = dataSource[i].coords;
				var name = dataSource[i].name;
					if(coords){
						var coordsArr = coords.split(",");
						var length = coordsArr.length;
						this._tempCoords = coords;
						
						/*			coords : '10536,14528.5,10608,14590.5,11646,14466.5,10590,14468.5,10504,14276.5,10536,14528.5',//收尾相接是面
									style:{
										Size：5,
										Fillcolor:'#ff3300',
										Strokecolor:'#ff8300',
										Opacity:0.4,
										Dashstyle:"longdash"
									}
									*/

							//如果是面
							if(length>5&&coordsArr[0]==coordsArr[length-2]&&coordsArr[1]==coordsArr[length-1]){
								//创建各类图层，二次开发往地图上加东西的'容器'	
								//dataSource[i].coords = coordsArr.slice(0,length-2).toString();
								this._polygonFeatures.push(dataSource[i]);
								
							}else if(length>3){
								//如果是线
								this._lineFeatures.push(dataSource[i]);
							}
							
					}
			}
		
			this._draw(newLayer,this._lineFeatures,this._polygonFeatures);
		
	},

	_draw:function(a,b,c,d,e,f) {
            //var coords = '10436,14228.5,10608,14190.5,10646,14466.5,10190,14468.5';
            //地图坐标(AlaMap坐标需要转换成相对于盒子的坐标,画poly同理
            
			//debugger;635927283
			var _layer = a;
			var main = this;
			var _lineFeatures = b;
			var _polygonFeatures = c;
			var _event = e;
			var baseMap = this.baseMap;
			var _callBack = this._draw;
			if(e!=undefined){
				_layer = e.layer;
				_lineFeatures = e.line;	
				_polygonFeatures = e.polygon;
				baseMap = e.baseMap;
				_callBack = e.callBack;
				_event = e.event;
				main = e.main;
			}else
			if(d!=undefined){
				_layer = d.layer;
				_lineFeatures = d.line;	
				_polygonFeatures = d.polygon;
				baseMap = d.baseMap;
				_callBack = d.callBack;
				_event = d.event;
				main = d.main;
			}
			if(_layer!=undefined){
				_layer.innerHTML = '';
			}
			
			if(_lineFeatures!=undefined&&_polygonFeatures!=undefined){
				var layerVis = main.visible?"":"none";
					_layer.style.display = layerVis;
					//debugger;
				for(var i=0;i<_lineFeatures.length;i++){
				var lineStyle =  _lineFeatures[i].style;
				if(lineStyle!=undefined){
				 var line = baseMap.DrawPolyLine(_layer,_lineFeatures[i].coords, lineStyle.Size, lineStyle.Color, lineStyle.Opacity,lineStyle.Dashstyle);
					line.main = main;
					line.layerId = _layer.id;
					line.featureStyle = _lineFeatures[i];
					line.geoType = ChrhcMapConfig.shapeLine;
					line.name = _lineFeatures[i].name;
					line.id = main._ClassId+"_"+ _lineFeatures[i].id;
					line.coords = _lineFeatures[i].coords;
					main._addDomEvent(line);
					if(line!=undefined){
						main._addedEntityIds.put(_lineFeatures[i].id,_lineFeatures[i]);
					}
				}
			}
			
				for(var i=0;i<_polygonFeatures.length;i++){
					var polyStyle =  _polygonFeatures[i].style;
					if(polyStyle!=undefined){
						var polygon =  baseMap.DrawPoly(_layer,_polygonFeatures[i].coords, polyStyle.Size,polyStyle.FillColor, polyStyle.Strokecolor,polyStyle.Opacity, polyStyle.Dashstyle);
							polygon.main = main;
							polygon.layerId = _layer.id;
							//debugger;
							polygon.geoType = ChrhcMapConfig.shapePolygon;
							polygon.name = _polygonFeatures[i].name;
							polygon.id = main._ClassId+"_"+_polygonFeatures[i].id;
							polygon.coords = _polygonFeatures[i].coords;
							polygon.featureStyle = _polygonFeatures[i];
						main._addDomEvent(polygon);
						if(polygon!=undefined){
							main._addedEntityIds.put(_polygonFeatures[i].id,_polygonFeatures[i]);
						}
						
					}
					

				}
				var callBackObj = {
								main:main,
								baseMap:baseMap,
								layer:_layer, 
								line:_lineFeatures,
								polygon:_polygonFeatures,
								callBack:_callBack,
								event:_event
								};
			//_layer.removeNode();	
            //注册级别切换事，重绘事件
            _callBackAttach = _callBack;
            baseMap.detachEvent(AlaMap.KeyWord.EventName.MapZoomChange, _callBack);
            baseMap.attachEvent(AlaMap.KeyWord.EventName.MapZoomChange, _callBack, [callBackObj]);
			baseMap.detachEvent(AlaMap.KeyWord.EventName.MapMoveEnd, _callBack);
            baseMap.attachEvent(AlaMap.KeyWord.EventName.MapMoveEnd, _callBack, [callBackObj]);
            baseMap.detachEvent(AlaMap.KeyWord.EventName.Repaint, _callBack);
            baseMap.attachEvent(AlaMap.KeyWord.EventName.Repaint, _callBack);
			}
			
			

        },
		_createNewLayer:function(option){
			var that = this;
			var layerName = option.layerName;
			var zIndex =option.zIndex==undefined?this.chrhcBaseMap._layerIndex:option.zIndex;
				that.visible = option.visible;
			var visible = option.visible==false?"none":"";			
			var layerId = option.layerId==undefined?this.chrhcBaseMap._layerIndex:option.layerId;
			var newLayer = that.baseMap.NewMapLayer(layerName+"_"+layerId,zIndex, true);
				newLayer.innerHTML = '';
				newLayer.id = layerId;//+"_"+layerId;
				//alert("visible="+visible);
				//debugger;
				newLayer.style.display = visible;	
				newLayer.layerName = layerName;//+"_"+layerId;
			var heighLightLayer = that.baseMap.NewMapLayer(layerName+"_"+9998,9998, true);
				heighLightLayer.innerHTML = '';
					
			that._heighLightLayer = heighLightLayer;
			that.chrhcBaseMap._addLayer(layerId,newLayer);
			
		return newLayer;
	},
	/*
	添加矢量图层的鼠标事件
	
	*/
	_addDomEvent:function(dom){
		//debugger;
		var event = this._event;
			dom.heighLight = null;
			/*
			自定义鼠标事件
			*/
				var onmouseover = null;
				var onclick = null;
				var onmouseleave = null;
				if(event!=undefined){
					 onmouseover = event.onMouseOver;
				     onclick = event.onClick;
					 onmouseleave = event.onmouseleave;
				}
				
			/*
			默认鼠标事件
			*/
			dom.onmouseover = function(e){
						var that = this.main;
						var geoType = this.geoType;
						var heighLight = null;
							that._heighLightLayer.innerHTML = '';
						
							if(geoType==ChrhcMapConfig.shapeLine){
									var style =  this.featureStyle.style;
								if(style!=undefined){			
									 heighLight =  baseMap.DrawPolyLine(that._heighLightLayer,this.featureStyle.coords, style.Size+2,'#0E02B6', style.Opacity, style.Dashstyle);			
									}
								}else if(geoType==ChrhcMapConfig.shapePolygon){
									var style =  this.featureStyle.style;
									if(style!=undefined){
									 heighLight =  baseMap.DrawPoly(that._heighLightLayer,this.featureStyle.coords, style.Size+2,'#A19AFE',"#0E02B6", style.Opacity, style.Dashstyle);			
									}
								}else if(geoType==ChrhcMapConfig.entityPoint){
									//alert("现在是实体"+this.name);
									dom.style.zIndex = 1000;
									
								}
						
								
						if(heighLight!=null){
							heighLight.name = this.name;
							heighLight.id = this.id;
							heighLight.coords = this.coords;
							heighLight.layerId = this.layerId;
							heighLight.onmouseleave = function(){
								//this.removeNode();	
								this.parentNode.innerHTML = '';
							};
							heighLight.onclick = function(){
									//alert(this.name);
								if(onclick!=undefined&&onclick!=null){
									//执行用户自定义方法
									onclick(this);			
									}	
								};
						}
								/*
									自定义鼠标事件
								*/
						if(onmouseover!=undefined&&onmouseover!=null){

								//执行用户自定义方法
								onmouseover(this);
							
						}
								
					};
					
			dom.onmouseleave = function(e){
					
						dom.style.zIndex = 0;
						
						if(baseMap!=null&&baseMap.InfoWindow!=null&&baseMap.InfoWindow.autoRemove){
							baseMap.InfoWindow.Close();
						}
							
						/*
						自定义鼠标事件
						*/
						if(onmouseleave!=undefined&&onmouseleave!=null){
							
								//默认的高亮操作
								/*
								
								*/
								//执行用户自定义方法
								onmouseleave(this);
							
						}
						
					};
				dom.onclick = function(e){
						
						if(onclick!=undefined&&onclick!=null){
									
							//默认的高亮操作
						/*
						*/
						//执行用户自定义方法
						onclick(this);
									
						}	
					};
				
		},
 
	/*
	图层控制功能
	state:true: 显示   false:隐藏
	
	*/
	layerShow:function(state){
			var that = this;
			if(that.chrhcBaseMap&&!state&&that.layer){
				that.chrhcBaseMap.hiddenLayers([that.layer.id]);
				that.visible = state;
			}else if(that.chrhcBaseMap&&that.layer){
				that.chrhcBaseMap.showLayers([that.layer.id]);
				that.visible = state;
			}
			
			
		},
	removeLayers:function(){
		var that = this;
			if(that.chrhcBaseMap&&that.layer){
				//console.log(typeof(_callBackAttach)=="undefined");
				if(typeof(_callBackAttach)!="undefined" && _callBackAttach != null){
					baseMap.detachEvent(AlaMap.KeyWord.EventName.MapZoomChange, _callBackAttach);            
				    baseMap.detachEvent(AlaMap.KeyWord.EventName.MapMoveEnd, _callBackAttach);           
	                baseMap.detachEvent(AlaMap.KeyWord.EventName.Repaint, _callBackAttach);
				}
                
				that.chrhcBaseMap.removeLayers([that.layer.id]);
				that.visible = false;
			}
			
	}	,
	
	/*
	图层编辑
	state: true:可编辑，false:不可编辑
	*/
	enableEdit:function(state,isUpdate){
		var state = state==""||state==null||state==undefined?false:state;
		var that = this;
			that.baseMap.that = that;
		if(that._enableEditState!=state){
			that._enableEditState = state;
			
		if(that.baseMap!=undefined&&state){
				//如果是对实体对象进行编辑，则只可以对该对象进行拖动
				if(that.layerType=="Entitys"){
					
						var dataSourceHM = that._dataSource;
						
						var dataSource = dataSourceHM.values();
						if(dataSource!=undefined&&isUpdate){
							that.baseMap.attachEvent(AlaMap.KeyWord.EventName.MapClick, function (x,y,e) {
								var that = this.that;
								var id = "drawPoint_"+UtilMisc.getCurrentTime();
								var dataSource =[];
								
									dataSource.push(
												{name:id,
												id:id,
												coords:x+","+y
												});
											
									that.clearFeatures();
									that.addData(dataSource);
								if(that._editEvent!=undefined){
									that._editEvent.onDrawEnd(id,id,x+","+y);	
								}			
							});
						
						}else if(dataSource!=undefined){
							//先清除已添加的实体对象
							that.clearFeatures();
							that._symble.enableEdit = true;
							
						//再次添加
							that._addEntityData(that.layer,that._dataSource);

							that.baseMap.detachEvent(AlaMap.KeyWord.EventName.EntityMouseUp, that._entityMouseUpEvent);
						    that.baseMap.attachEvent(AlaMap.KeyWord.EventName.EntityMouseUp,that._entityMouseUpEvent );
						}
						
					    
						
				}else{
					//如果是对矢量（线、面）进行编辑，则可以对该对象进行轮廓拖动
					//先删除现有图层数据
					if(that.layer!=null){
						that.layer.innerHTML = '';
					}
					that._initEditData(that);
				}
				that._hadEnabledEdit = true;	
				
			}  else{
				//debugger;
				if(that.layerType=="Entitys"){

					that._symble.enableEdit = false;
					//alert("state="+state);
						that.clearFeatures();
						that._addEntityData(that.layer,that._dataSource,that._config);
						var succ = that.baseMap.detachEvent(AlaMap.KeyWord.EventName.EntityMouseUp,that._entityMouseUpEvent);
					
				}else{
					that._polygonFeatures = null;
					that._polygonFeatures = [];
					that._lineFeatures = null;
					that._lineFeatures = [];
					that._addVectorData(that.layer,that._dataSource.values());
					that.chrhcBaseMap.stopDraw();
					that._shapeEditorDataHashMap = null;
				}
				 
				that._hadEnabledEdit = false;
			}
			
		}
	},
	_entityOnMapClickEvent:function(x,y,e){
		var that = this.main;
		var id = "drawPoint_"+UtilMisc.getCurrentTime();
		var dataSource =[];
		
		    dataSource.push(
						{name:id,
						id:id,
						coords:x+","+y
						});
						
					if(layer!=null){
						that.layer.clearFeatures();
						that.layer.addData(dataSource);
					}
	},
	/*
	对于实体类图层，当编辑状态下，鼠标拖动结束后触发的事件
	目的：更新暂存的实体位置信息，便于编辑结束后进行重绘
	*/
	_entityMouseUpEvent:function(entityid) {
		//debugger;
							var that = this.that;
							var entityInfo  = this.GetEntityInfo(entityid);
							if(entityInfo!=null&&that._dataSource!=undefined){
									//更新现有数据的坐标信息
								var dataSource = that._dataSource.get(entityid);
								if(dataSource!=null){
									dataSource.coords = entityInfo.X+","+entityInfo.Y;
									that._dataSource.remove(entityid);
									that._dataSource.put(entityid,dataSource);
									
									var entity = that._addedEntityIds.get(entityid);
									var entityName = entity.name;
										entity.entityInfo = entityInfo;
									if(that._editEvent!=undefined){
										that._editEvent.onDragStick(entityid,entityName,entityInfo.X+","+entityInfo.Y);
									}
								}
									
								
							}
						
								
								//alert("当前编辑的实体ID是:"+entityid+" 名称是"+entityName);
						},
		        //---------拖拽编辑--------------
    _initEditData:function(main) {				
		main.chrhcBaseMap._initEditor(callBackFunction,main);
		if(main._shapeEditorDataHashMap==null){
			_shapeEditorDataHashMap = new HashMap();
		}
		for(var i=0;i<main._lineFeatures.length;i++){
			var lshapeId = main.chrhcBaseMap.addShapeEditorData(main._lineFeatures[i],true);
				_shapeEditorDataHashMap.put(main._lineFeatures[i],lshapeId);
		}
		
		for(var i=0;i<main._polygonFeatures.length;i++){
			var pshapeId = main.chrhcBaseMap.addShapeEditorData(main._polygonFeatures[i],false);
				_shapeEditorDataHashMap.put(main._polygonFeatures[i],pshapeId);
		}
		/*
		var callBackObject = {main:main,shapeId:shapeId,coords:coords,attribute:attribute};
		*/
		function callBackFunction(that){
							
							/*
							此处当拖拽完毕后，要更新旧数据
							*/
							var attributeId = that.attribute.id;
							var coords = that.coords;
							var name = "";
							var editedShape = that.main._dataSource.get(attributeId);
								editedShape.coords = coords;
								that.main._dataSource.remove(attributeId);
								that.main._dataSource.put(attributeId,editedShape);
				if(main._editEvent!=undefined){
						var onStop = main._editEvent.onStop;
						var onDragStick = main._editEvent.onDragStick;	
							onDragStick(attributeId,name,coords);
					}
			
		}
			
    },
	/*
	清除实体
	return entityIds[]:已删除的实体ID
	*/
    clearFeatures:function(entityIds) {
		  var that = this;
		  var removeFeatures = [];
		  var removedEntityIds = [];
			if(entityIds&&entityIds.length>0){
				 removeFeatures = entityIds;
				  var id ;
				 for(var i in removeFeatures){
					 id = removeFeatures[i];
					 removeFeatures[i] = that._ClassId+"_"+id;
				 }
			}else if(that._addedEntityIds.size()>0){
				 removeFeatures = that._addedEntityIds.keySet();
			}
			var keySet = that._addedEntityIds.keySet();
			for(var i=0;i<removeFeatures.length;i++){
					 if (that.baseMap!=undefined&&that._addedEntityIds!=null) {
							var entityInfo = that.baseMap.GetEntityInfo(removeFeatures[i]);
							if(entityInfo!=undefined&&entityInfo!=null){
								that.baseMap.RemoveEntity(removeFeatures[i]);
								that._addedEntityIds.remove(removeFeatures[i]);
							}
							
						}
						else {
							alert('Entity'+that.removeFeatures[i]+'不存在');
						}				
				} 
				return removeFeatures;
	},
	/*
	style:{
			Color:"yellow",
			FillColor:"yellow",
			Opacity:0.7,
			Size:2,
			StrokeColor:"#FFEE00"
	}
	return newId;
	*/
	reSetDrawStyle:function(id,style){
		var that = this;
		if(that._hadEnabledEdit&&that._shapeEditorDataHashMap!=null){
			var shapeEditorDataId = that._shapeEditorDataHashMap.get(id);
			var lineid = that.chrhcBaseMap.reSetDrawStyle(shapeEditorDataId,style);
		}
		var editedShape = that._dataSource.get(id);
			style.DashStyle = editedShape.style.DashStyle;
			editedShape.style = style;
			that._dataSource.remove(id);
			that._dataSource.put(id,editedShape);
			that.layer.innerHTML = '';
			that._polygonFeatures = null;
			that._polygonFeatures = [];
			that._lineFeatures = null;
			that._lineFeatures = [];
			that._addVectorData(that.layer,that._dataSource.values());
	},	
}

ChrhcMap.Layer.prototype.constructor = ChrhcMap.Layer;


