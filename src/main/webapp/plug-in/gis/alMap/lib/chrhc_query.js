
/*
config={domId:"queryDiv",listTypeId:"queryList",resultHtmlId:"queryResultDiv",resultListDivId:"map-search-result-main",listClickEvent:setSlectedQueryType}

}
*/
ChrhcMap.BusinessQuery = new Class({
	//查询页面放置的位置
	_domId:null,
	//查询初始化时获取查询服务的服务地址
	_queryServiceUrl:null,
	//上下文地址
	_contextPath :null,
	//初始化完毕后，将所有的查询服务地址存储在HashMap中
	_serviceHashMap:null,
	_listClickEvent:null,
	_currentQueryTypeId:null,
	_resultListDivId:null,
	_resultHtmlId:null,
	queryResultLayer:null,
	currentPage:1,
	totalPage:0,
    initialize: function(config){
		
        this.config = config;
		if(config!=undefined&&config!=null){
			if(config.domId!=undefined){
				this._domId = config.domId;
			}
			if(config.listTypeId!=undefined){
				this._listTypeId = config.listTypeId;
			}
			if(config.listClickEvent!=undefined){
				this._listClickEvent = config.listClickEvent;
			}
			if(config.resultListDivId!=undefined){
				this._resultListDivId = config.resultListDivId;
			}
			if(config.resultListDivId!=undefined){
				this._resultListDivId = config.resultListDivId;
			}
			if(config.resultHtmlId!=undefined){
				this._resultHtmlId = config.resultHtmlId;
			}
			
		};
		this._queryServiceUrl = ChrhcMapConfig.queryServiceUrl;
		this._contextPath = UtilMisc.getContextPath();
		this._serviceHashMap = new HashMap();
			$("#"+this._domId)[0].style.display = "";
		this._host = UtilMisc.getContextPath();
		this.initQueryServiceUrl();
    },
	executeQuery:function(text,page){
		this.currentPage = page;
		var currentQueryServiceObj = this._serviceHashMap.get(this._currentQueryTypeId);
		var queryUrl = currentQueryServiceObj.dataUrl;
			this._query(queryUrl,text);
	},
	_query:function(url,text){
		var queryUrl = this._contextPath+"/"+url +text+"&page="+this.currentPage+"&rows=10";
		var type = "post";
		var where =null;
		var callBack = this._queryEndCallBackFunction;	
		//debugger;
		var query = new ChrhcMap.Query(queryUrl,type,where,callBack,this);
			query.execute(where);
		
	},
	_queryEndCallBackFunction:function(jsonData,isObject){
			var that = this;
			var main = that.scope;
			var currentQueryServiceObj = main._serviceHashMap.get(main._currentQueryTypeId);
			var showfields = currentQueryServiceObj.showfield;
			
			var data;
			if(!isObject){
				data = eval('(' + jsonData + ')');
			}
			if(isObject){
				data = jsonData;
			}
			var rows = data.rows;
			var total = data.total;
			var page  = total/10;
			var pageyu = total%10;
				if(pageyu!=0){
					main.totalPage = parseInt(page)+1;
				}else{
					main.totalPage = parseInt(page);
				}
				
			//alert("page="+parseInt(page+1));
			var dataSource = [];
			//$("#map-search-result-main").empty();
			var queryResultDivHtml = new Array();
				queryResultDivHtml.push(" <div class=\"map-search-result-main\"><ul>");
			for(var i=0;i<rows.length;i++){
				if(i==10){
					break;
				}
				
				var coords = rows[i].gisxy;
				var id = rows[i].id;
					
				var coordsArr = coords.split(",");
				var x = coordsArr[0];
				var y = coordsArr[1];
				var address = rows[i].address;
					queryResultDivHtml.push(" <li class=\"search-item\"  onclick=\"centerTo1("+x+","+y+")\">");
					queryResultDivHtml.push(" <div class=\"cf\">");
					queryResultDivHtml.push(" <div class=\"col-l\">");
					queryResultDivHtml.push(" <a href=\"javascript:void(0)\" class=\"bubble no-"+(i+1)+"\"></a>");
					queryResultDivHtml.push(" </div>");
					queryResultDivHtml.push(" <div class=\"ml_10 gis_l_address\">");
					queryResultDivHtml.push(" <div>");
				var dataNameValue;
				var imgUrls;
					for(var j=0;j<showfields.length;j++){
						var showfield = showfields[j];
						var field = showfield.field;
						var value = rows[i][field];
						var showName = showfield.showName;
						if(field=="name"){
							queryResultDivHtml.push(" <span> <a href=\"javascript:void(0)\" class=\"n-blue\">"+showName+":"+value+"</a></span>");
							queryResultDivHtml.push(" </div>");
							dataNameValue = value;
						}else
						if(showName!="photo"&&value!=null&&value!=""&&value!=undefined){
							queryResultDivHtml.push(" <div class=\"content-des\">"+showName+":"+value+"</div>");
						}else if(showName =="photo"&&value!=""&&value!=null&&value!=undefined){
							 imgUrls = value.split(",");
														
						}
					}
					queryResultDivHtml.push(" </div>");
					if(imgUrls!=null&&imgUrls.length>0){
						//添加图片展示
						queryResultDivHtml.push(" <div class=\"gis_r_img\">");
						queryResultDivHtml.push(" <img style='height:60px !important;' src=\""+main._host+"/"+imgUrls[0]+"\"");
						queryResultDivHtml.push(" width=\"90px\" height=\"60px\" />");
						queryResultDivHtml.push(" </div>");
						//queryResultDivHtml.push(" <div class=\"content-des\">显示图片"+":"+imgUrls[0]+"</div>");	
					}

					queryResultDivHtml.push(" </div>");
					queryResultDivHtml.push(" </li>");
				var config = {baseMap:baseMap,symbleType:"Markers",style:"background-position:-"+i*24+"px -198px;",offectX:12,offectY:29};
				var markerSymble = new ChrhcMap.Symble(config);
					dataSource.push(
								{name:dataNameValue,
								 id:id,
								 coords:coords,
								 symble:markerSymble
								});			
			}
			//queryResultDivHtml.push(" </ul><li>共"+total+"条数据</li></div>");

			$("#"+main._resultListDivId).append(queryResultDivHtml.join(""));
			$("#"+main._resultHtmlId)[0].style.display = "";
			$("#queryResultTotal").empty();
			if(total==0){
				
				$("#currentQueryPageNum").val(total);
			}
			
			$("#queryResultTotal").append("<span style=\"padding-right: 6px;\" >/"+main.totalPage +"</span>");
			$("#total").html("共"+total+"条");
			$(".datagrid-pager").show();
			var windowWidth = $(document).width();
			var windowHeight = $(document).height();
			cMap.reSetMapSize(windowWidth,windowHeight);
			var layerConfig = {
					map:cMap,//初始化地图返回的地图对象
					layerName:"复合",
					layerType:"Entitys",//
								
				//	event:{onClick:onQueryResultMarkerClickEvent,onMouseOver:onQueryResultMarkerOverEvent},
					dataSource:dataSource
			};
			main.queryResultLayer = new ChrhcMap.Layer(layerConfig);
			
		},
	initQueryServiceUrl:function(){
		
		var queryUrl = this._contextPath+"/"+this._queryServiceUrl ;
		var type = "post";
		var where =null;
		var callBack = this.queryServiceEndCallBackFunction;	
		var query = new ChrhcMap.Query(queryUrl,type,where,callBack,this);
			query.execute(where);
	},
	queryServiceEndCallBackFunction:function(jsonData,isObject){
		//debugger;
			var that = this;
			var main = that.scope;
			var data;
			if(!isObject){
				data = eval('(' + jsonData + ')');
			}
			if(isObject){
				data = jsonData;
			}
			var rows = data.rows;
			var queryListType = [];
			for(var i=0;i<rows.length;i++){	
				var id = rows[i].id;
				var name = rows[i].name;
				var dataUrl = rows[i].dataurl;
				var showfield =  rows[i].showfield;
				var serviceObj = {id:id,name:name,dataUrl:dataUrl,showfield:showfield};
						main._serviceHashMap.put(id,serviceObj);
				if(i==0){
					main._currentQueryTypeId = id;
					queryListType.push("<li class=\"checked\" onclick=\"setSlectedQueryType('"+id+"')\">"+name+"</li>");//
				}else{			
					queryListType.push("<li onclick=\"setSlectedQueryType('"+id+"')\">"+name+"</li>");// <button onclick=\"setSlectedQueryType('"+id+"')\">"+name+"</button>");
				}
			}
			//debugger;
			//alert(queryListType.join(""));
			$("#"+main._listTypeId).append(queryListType.join(""));
	},
	setQueryType:function(id){
		this._currentQueryTypeId = id;
	}

});

/*
config={domId:"queryDiv",listTypeId:"queryList",resultHtmlId:"queryResultDiv",resultListDivId:"map-search-result-main",listClickEvent:setSlectedQueryType}

}
*/
ChrhcMap.CheckBuildIngQuery = new Class({
	_BuildId:null,
	_queryUrl:null,
	_contextPath:null,
	_callBackFunction:null,
	_popupWindowServiceUrl:null,
	
    initialize: function(BuildId,callBackFunction){
		
        this._BuildId = BuildId;
		this._queryUrl = ChrhcMapConfig.checkBuildIngIsNeedAddPopupWindowServiceUrl;
		this._contextPath = UtilMisc.getContextPath();
		this._callBackFunction = callBackFunction;
		this._popupWindowServiceUrl = ChrhcMapConfig.buildIngPopupWindowServiceUrl;
		
    },
	addOneTab:function(BuildId){
		
		if(BuildId!=null){
			this._BuildId = BuildId;
		}
			
		var queryUrl = this._contextPath+"/"+this._queryUrl +BuildId;
		var type = "post";
		var where =null;
		var callBack = this._queryEndCallBackFunction;	
		//debugger;
		var query = new ChrhcMap.Query(queryUrl,type,where,callBack,this);
			query.execute(where);
		
	},
	/*
	d.msg == "9"       状态：已不能存在此建筑物信息，提示信息：该建筑物信息已被删除，请确认！
 
d.msg  == "1"       该建筑物已生成房间信息,可以进行查看房间信息了
 
d.msg == "0" || "2"        状态：未生成建筑信息或需重新生成，提示信息：该建筑物未生成房间信息，请确认！
 var url = contextPath+"/scJzwglController.do?findFjByGisId&id="+e.ID;
				
				 addOneTab("详细信息", url);
	*/
	_queryEndCallBackFunction:function(jsonData,isObject){
			var that = this;
			var main = that.scope;
			
			var data;
			if(!isObject){
				data = eval('(' + jsonData + ')');
			}
			if(isObject){
				data = jsonData;
			}
			if(data!=null){
				var msg = data.msg;
				switch (msg)
						{
						case "1":
							main._callBackFunction(ChrhcMapConfig.buildingClickDetailInfoTitle, main._contextPath+"/"+main._popupWindowServiceUrl+main._BuildId);
						  break;
						case "9":
								$.messager.show({
									title:ChrhcMapConfig.messageTitle,
									msg:ChrhcMapConfig.BuildingDetailInfoHasDeleteMsg,
									showType:'show'
								});
						  break;
						case "3":
							$.messager.show({
								title:ChrhcMapConfig.messageTitle,
								msg:"您没有权限查看！",
								showType:'show'
							});
						break;
						default:
								$.messager.show({
									title:ChrhcMapConfig.messageTitle,
									msg:ChrhcMapConfig.BuildingDetailInfoHasNoAddMsg,
									showType:'show'
								});
							
						  break;
						}
			
			}
		/*	if(data!=null){
				var success = data.success;
				if(success){
					main._callBackFunction(ChrhcMapConfig.buildingClickDetailInfoTitle, main._contextPath+"/"+main._popupWindowServiceUrl+main._BuildId);
				}else{
					var msg = data.msg;
					$.messager.show({
						title:ChrhcMapConfig.messageTitle,
						msg:msg,
						showType:'show'
					});
				}
			}*/
		}

});

