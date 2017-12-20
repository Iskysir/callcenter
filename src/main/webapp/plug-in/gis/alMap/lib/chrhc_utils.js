
/*
chrhcMap:ChrhcMap对象
dataSources：[{folderName:"文件夹名称",dataSource:[{layerType:"point",layerName:"英文名称",layerZhName:"中文名称",url:"服务地址",where:"where",visible:true}]}];
initEndCallBackFunction:用户自定义初始化完毕后的回调方法，return:[{layerKey:layerName,layerZhName:layerZhName}]
*/
ChrhcMap.LayerControl = new Class({
	dataSource:null,
	layerHashMap:null,//{layerName:"中文名称",layer:ChrhcLayerObject}editEvent:{onStop:editCompleteEvent,onDragStick:onDragCompleteEvent},
    lastLayer:null,
	controlId:null,
	isEdit:false,
	queryServiceUrlHashMap:null,
	isReFresh:false,
	initialize: function(chrhcMap,dataSources,initEndCallBackFunction,onClickEndEvent,onEditCompleteEvent,onDragCompleteEvent,isEdit){
		this.controlId = UtilMisc.getCurrentTime();
	    this.dataSources = dataSources;
		this.chrhcMap = chrhcMap;
		//this.layerHashMap = new HashMap();
		//this.queryServiceUrlHashMap = new HashMap();
		this.initEndCallBackFunction = initEndCallBackFunction;
		this.onClickEndEvent = onClickEndEvent;
		this.onDragCompleteEvent = onDragCompleteEvent;
		this.onEditCompleteEvent = onEditCompleteEvent;
		//this.initData(dataSources,this._queryEndcallBackFunction);
		this.isEdit = isEdit;
		
		//this.initTreeDataTest();
    },
	zoomToShowLevel:function(key){
			var layerHashMapValue = this.layerHashMap.get(key);
			var showLevel = layerHashMapValue.showLevel;
			this.chrhcMap.zoomTo(showLevel);
	},
	refreshLayerData:function(layerId){
		
		if(layerId!=null){
			this.zoomToShowLevel(layerId);
			this.removeLayers(layerId);
			var serviceUrl = this.queryServiceUrlHashMap.get(layerId);
			if(serviceUrl!=null){
				this._queryLayerSource(serviceUrl,"post",null,this._queryEndcallBackFunction,true);
			}
		}
	},
    changeLayerControlDisplay:function(currentZoom){
		//debugger;
    	var layers = this.layerHashMap.values();
    	for(var i=0;i<layers.length;i++){
    		
    		var layerControl = layers[i];
    		var showLevel = layerControl.showLevel;
    		var isChecked = layerControl.isChecked;
			//debugger;
    		//隐藏
    		if(currentZoom>showLevel){
    			if(layerControl.layer!=null){
    				layerControl.layer.layerShow(false&&isChecked);
    			}
    			
    			if(layerControl.layerBq!=null){
    				layerControl.layerBq.layerShow(false&&isChecked);
				}
    			
    		}else if(currentZoom<showLevel||currentZoom==showLevel){
    			if(layerControl.layer!=null){
    				layerControl.layer.layerShow(true&&isChecked);
    			}
    			
    			if(layerControl.layerBq!=null){
    				layerControl.layerBq.layerShow(true&&isChecked);
				}
    			
    		}
    	}
    	
    },
	/*
	显示图层
	layerKeys：图层keys,数组
	*/
	showLayers:function(layerKeys){
		//this.hiddenAllLayers();
		for(var i=0;i<layerKeys.length;i++){
			var layerHashMapValue = this.layerHashMap.get(layerKeys[i]);
			var showLevel = layerHashMapValue.showLevel;
			var currentZoom = this.chrhcMap.baseMap.Zoom();
				if(showLevel>currentZoom||showLevel==currentZoom){
					layerHashMapValue.layer.layerShow(true);
					if(layerHashMapValue.layerBq!=null){
						layerHashMapValue.layerBq.layerShow(true);
					}
				}
		}
		
	},
	showLayer:function(key){
		//this.hiddenAllLayers();
			var layerHashMapValue = this.layerHashMap.get(key);
			var showLevel = layerHashMapValue.showLevel;
			var currentZoom = this.chrhcMap.baseMap.Zoom();
			this.chrhcMap.zoomTo(showLevel);
			//debugger;
				//if(showLevel>currentZoom||showLevel==currentZoom){
					layerHashMapValue.layer.layerShow(true);
					if(layerHashMapValue.layerBq!=null){
						layerHashMapValue.layerBq.layerShow(true);
					}
					
				//}
				
				layerHashMapValue.isChecked = true;
				this.layerHashMap.put(key,layerHashMapValue);	
		
	},
	/*
	隐藏所有图层
	*/
	hiddenAllLayers:function(){
		var allLayers = this.layerHashMap.values();
		for(var i=0;i<allLayers.length;i++){
			if(allLayers[i].layer!=null){
				allLayers[i].layer.layerShow(false);
			}
			
			if(allLayers[i].layerBq!=null){
				allLayers[i].layerBq.layerShow(false);
			}
		}	
	},
	/*
	隐藏指定的图层组
	*/
	hiddenLayers:function(keys){
		var allLayers = this.layerHashMap.values();
		for(var i=0;i<keys.length;i++){
			keys[i].layer.layerShow(false);
			if(keys[i].layerBq!=null){
				keys[i].layerBq.layerShow(false);
			}
		}	
	},
	hiddenLayer:function(key){
		//debugger;
		var layerHashMapValue = this.layerHashMap.get(key);
			layerHashMapValue.layer.layerShow(false);
			
			if(layerHashMapValue.layerBq!=null){
				layerHashMapValue.layerBq.layerShow(false);
			}
			layerHashMapValue.isChecked = false;
			this.layerHashMap.put(key,layerHashMapValue);	
	},
	removeLayers:function(key){
		var layerHashMapValue = this.layerHashMap.get(key);
		if(layerHashMapValue.layer!=null){
			layerHashMapValue.layer.removeLayers();
			
			if(layerHashMapValue.layerBq!=null){
				layerHashMapValue.layerBq.removeLayers();
			}
			layerHashMapValue.isChecked = false;
			this.layerHashMap.put(key,layerHashMapValue);	
		}
			
	},
	removeAllLayers:function(){
		var allLayers = this.layerHashMap.values();
		for(var i=0;i<allLayers.length;i++){
			if(allLayers[i].layer!=null){
				allLayers[i].layer.removeLayers();
				if(allLayers[i].layerBq!=null){
					allLayers[i].layerBq.removeLayers();
				}
			}
			
		}	
	},
	addData:function(layerId,dataSource){
		 var layerValues = this.layerHashMap.get(layerId);
		 	if(layerValues!=null&&layerValues.layer!=null){
		 		layerValues.layer.addData(dataSource);
		 	}else{
		 		alert("错误,未找到对应的图层对象");
		 	}
		
		
	},
	enableEdit:function(state,layerKeys){
		//lastLayer.enableEdit(state);
		for(var i=0;i<layerKeys.length;i++){
			
			var values = this.layerHashMap.values();
			var va = this.layerHashMap.values();
			var key = this.layerHashMap.keySet();
			var layerHashMapValue = this.layerHashMap.get(layerKeys[i]);
				if(layerHashMapValue!=null&&layerHashMapValue.layer!=null){
					layerHashMapValue.layer.enableEdit(state);
				}
			
		}
		
	},
	
	/*
	layerControlConfigHashMap;
	layerConfig:[{layerName:""
				restUrl:""}
				]
	{layerName:"静态变量"}
	*/
	initData:function(dataSources){
		var callBack = this._queryEndcallBackFunction;
			dataSources = dataSources==null?this.dataSources:dataSources;
		for(var i=0;i<dataSources.length;i++){
			var dataSource  = dataSources[i].dataSource;
			var folderName = dataSources[i].folderName;
			//alert("dataSource.length="+dataSource.length);
			for(var j=0;j<dataSource.length;j++){
				var layerName = dataSource[j].layerName;
				var layerId = dataSource[j].layerId;
				var layerZhName = dataSource[j].layerZhName;
				var where = dataSource[j].where;
				var visible = dataSource[j].visible;
				var showLevel = dataSource[j].showLevel==undefined?null:dataSource[j].showLevel;
				var url = dataSource[j].url;
				var layerType = dataSource[j].layerType==ChrhcMapConfig.entityPoint?ChrhcMapConfig.entityLayerType:ChrhcMapConfig.vectorLayerType;
				this.queryServiceUrlHashMap.put(layerId,url);
				if(this.layerHashMap!=null){
					var layerHashMapValue = {isChecked:false,showLevel:showLevel,layerType:layerType,folderName:folderName,folderId:this.controlId+"_"+i+"/"+j,layerId:layerId,layerName:layerZhName,visible:visible,layer:null,layerBq:null};
						this.layerHashMap.put(layerName,layerHashMapValue);
					//url = url+"&tableName="+layerName+"&layerKey="+(layerName+"_"+j);
					var type = "post";
					/*
					此处根据url进行判断是否跨域查询，给type赋值
					*/
					
					/*
					此处扩展，根据restUrl进行查询
					*/
					this._queryLayerSource(url,type,where,callBack);
				}
				
			}
			
		}
		
	},
	_queryLayerSource:function(url,type,where,callBack,isReFresh){
		this.isReFresh = isReFresh;
		var query = new ChrhcMap.Query(url,type,where,callBack,this);
			query.execute(where);
	},
	/*
	return:data:[dataSource1,dataSource2,.....]
	dataSource:{layerKey:"",dataSource:[
									{
									name:"测试1",
									id:"1",
									coords : '8024,8156,7704,8526,7884,8968,8742,9138,9046,8938,8986,8262,8144,7958',//收尾不相接是线
									style:{
										Size:5, //线条大小
										Color:'#ff3300',
										Opacity:0.5,//透明度（0-1之间的小数
										Dashstyle:"dot" //线段样式(IE下有效)，可用：solid|dot|dash|dashdot|longdash|longdashdot|longdashdotdot|shortdot|shortdash|shortdashdot|shortdashdotdot
									}
									
									},
									{
									name:"测试2",
									id:"2",
									coords : '7588,7970,8294,8568,9104,9112',//收尾不相接是线
									style:{
										Size:4, //线条大小
										Color:'#ff3300',
										Opacity:0.8,//透明度（0-1之间的小数
										Dashstyle:"dash" 
									}
									},
									
									{
									name:"测试3",
									id:"3",
									centerxy:"",
									coords :'8828,7954,7534,8754,7794,9192,9194,8138,8828,7954',// coordsArr[index],//收尾相接是面
									style:{
										Size:5,
										FillColor:'#ff3300',
										Strokecolor:'#ff8300',
										Opacity:0.4,
										Dashstyle:"longdash"//线段样式(IE下有效)，可用：solid|dot|dash|dashdot|longdash|longdashdot|longdashdotdot|shortdot|shortdash|shortdashdot|shortdashdotd
									}
									}
							]
							}
	*/
	_queryEndcallBackFunction:function(jsonData,isObject){
		var that = this;
		var data;
		//alert("jsonData="+jsonData);
		if(!isObject){
			data = eval('(' + jsonData + ')');
		}
		if(isObject){
			data = jsonData;
		}
		var returnLayerControl = new HashMap();
		for(var i=0;i<data.length;i++){
			var dataSource = data[i].dataSource;
			//debugger;
			var layerKey = data[i].layerKey;
			var layerHashMapValue = that.scope.layerHashMap.get(layerKey);
			var layerZhName = layerHashMapValue.layerName;
			var visible = !that.scope.isReFresh?layerHashMapValue.visible:that.scope.isReFresh;
				layerHashMapValue.isChecked = visible;
			var layerType = layerHashMapValue.layerType;
			var checked = layerHashMapValue.checked;
			//所属文件夹名称
			var folderName = layerHashMapValue.folderName;
			//所属文件夹ID
			var folderId = layerHashMapValue.folderId;
			var config = {baseMap:that.scope.chrhcMap.baseMap,symbleType:"Complex",offectX:12,offectY:29};
			var markerSymble = new ChrhcMap.Symble(config);
					
			var option = {
							map:that.scope.chrhcMap,
							layerId:that.scope.controlId+"_"+layerKey,
							layerName:layerZhName,

							layerType:layerType,
							visible:visible,
							symble:markerSymble,
							event:{onClick:that.scope.onClickEndEvent},//,onMouseOver:onmouseover},
							//editEvent:{onDragStick:onDragCompleteEvent},
							editEvent:{onStop:that.scope.editCompleteEvent,onDragStick:that.scope.onDragCompleteEvent},
							dataSource:dataSource
			};
			
				//alert("dataSource="+layerZhName+" "+that.scope.controlId+"_"+layerKey);
				var newLayer = new ChrhcMap.Layer(option);
				
					layerHashMapValue.layer = newLayer;
					//debugger;
					if(layerType==ChrhcMapConfig.vectorLayerType){
						var dataSourceBq = dataSource;
						
						var optionBq = {
								map:that.scope.chrhcMap,
								layerId:that.scope.controlId+"||_"+layerKey,
								layerName:layerZhName,
								
								layerType:ChrhcMapConfig.entityLayerType,
								visible:visible,
								symble:markerSymble,
								event:{onClick:that.scope.onClickEndEvent},//,onMouseOver:onmouseover},
								dataSource:dataSource
							};
				
					var newLayerBq = new ChrhcMap.Layer(optionBq);
						layerHashMapValue.layerBq = newLayerBq;
						
					}
					
					that.scope.layerHashMap.put(layerKey,layerHashMapValue);
			
				var containsKey = returnLayerControl.containsKey(folderId);
				var controlDatas =[];
			//if(that.scope.isEdit){	
				if(containsKey){
					controlDatas = returnLayerControl.get(folderId);	
				}
		
					controlDatas.push({folderName:folderName,layerKey:layerKey,layerZhName:layerZhName,checked:visible});
					returnLayerControl.put(folderId,controlDatas);
			//}
			
		}
		 /*
		 [{
	"id":"folder_1",
	"text":"Folder1",
	"iconCls":"icon-ok",
	"children":[{
		"id":"测试图层",
		"text":"测试图层",
		"checked":false
	}]
	}]

		 
		 
		if(!that.scope.isReFresh){
			 var returnData = that.scope._layerControlDataToJson(returnLayerControl);
			 	that.scope.initEndCallBackFunction(returnData);
		}
		
		*/
	},
	
	_layerControlDataToJson:function(controlDataHashMap){
		var returnData = [];
		if(controlDataHashMap!=null){
			
			var keySet = controlDataHashMap.keySet();
			
			for(var i=0;i<keySet.length;i++){
				var dataSources = controlDataHashMap.get(keySet[i]);
				var id = keySet[i];
				var text;
				var children =[];
				for(var j=0;j<dataSources.length;j++){
					var layerKey = dataSources[j].layerKey;
					var layerZhName = dataSources[j].layerZhName;
					var checked = dataSources[j].checked;
						text = dataSources[j].folderName;
					var childrenObject = {"id":layerKey,"text":layerZhName,"checked":checked};
						children.push(childrenObject);
						//children.push(childrenObject);
				}
				if(text!=undefined){
					returnData.push({"id":"folder_"+id,"text":text,"children":children});
				}else{
					returnData = children;
				}
				
			}
			
		}
		return returnData;
		
	}
});
/*
data: {
		layerName:"Donald Duck",
		where:"Duckburg"
					  }
*/
ChrhcMap.Query = new Class({
	dataSource:null,
	layerHashMap:null,
	url:null,
	where:null,
	callBackFunction:null,
	scope:null,
    initialize: function(url,type,where,callBackFunction,scope){
		this.url = encodeURI(url);
		this.type = type;
		this.where = where;
		this.callBackFunction = callBackFunction;
		this.scope = scope;
        
    },
	execute:function(where ){
		var that = this;
		
		if(where!=null){
			
			this.where = where;
		}
		 /*
		如果是同域，查询方法
		*/
		if(that.type=="get"){
			$.get(this.url,function(data,status){
				if(that.callBackFunction!=null){
				that.callBackFunction(data,false);	
				}
			
		  });
		}else if(that.type =="post"){
			 $.post(this.url,
					 {where:this.where},
					  function(data,status){
						if(typeof data=="object"&&that.callBackFunction!=null){
							that.callBackFunction(data,true);
						}else if(typeof data!="object"&&that.callBackFunction!=null){
							that.callBackFunction(data,false);
						}
						
					  }
					);
		}else{
		/*
		如果是跨域，查询方法
		*/
		$.getJSON(this.url+"&callback=?", 
					{where:this.where},
					function(data,status){
						that.callBackFunction(data,true);
					  }
				);	
			
		}	
	}
	
});
/***
 * @time 2015-09-21
 * 维护了一组dom元素，在put方法中会把dom注册的事件注册到除该dom外其他dom的click事件
 * new DomClickRegistGroup([$('#id1'),$('#id2')...]);
 * @type {Class}
 */
ChrhcMap.DomClickRegistGroup = new Class({
	domChains : null,//存放注册的dom
	initialize: function(radioChainsArr){
		//this.domChains = new HashMap();
		this.domChains = radioChainsArr;
	},
	registEvent:function(dom,callEndEvent){//给组中除放入的dom外的其他dom注册结束事件
		//var m = this.domChains.keySet();
		var radioDom;		
		var len = this.domChains.length;
		for(var i =0;i<len;i++){
			radioDom = this.domChains[i];
			if(radioDom != dom && radioDom[0]){//判断是$(dom)对象，并且传入dom对象外的其他dom
				$(radioDom).click(function(){
					callEndEvent();
				});
			}
		}
	}
});