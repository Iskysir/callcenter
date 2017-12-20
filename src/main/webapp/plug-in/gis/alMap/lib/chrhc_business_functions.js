

/**
 * 系统配置类
 */
ChrhcMap_Business_Functions = (function() {	
	return {
		getCooordsType:function(coords){
			var coordsLength = 0;
			if(coords!=null){
				var coordsArr = coords.split(",");
				    coordsLength = coordsArr.length;
			}
			//如果是面
			if(coordsLength>5&&coordsArr[0]==coordsArr[coordsLength-2]&&coordsArr[1]==coordsArr[coordsLength-1]){
					//创建各类图层，二次开发往地图上加东西的'容器'							
				return ChrhcMapConfig.shapePolygon;	
			}else if(coordsLength>3){
					//如果是线
				return ChrhcMapConfig.shapeLine;
			}else if(coordsLength==2){
				return ChrhcMapConfig.entityPoint;
			}
			return null;
		},
		//画可编辑的点实体
		getPoint:function(cMap,drawPointEnd,spotClickCallBackEvent) {
			//this.stopDraw(cMap);
			var baseMap = cMap.baseMap;
			var config = {baseMap:baseMap,symbleType:"Markers",style:"background-position:0px -117px;",offectX:12,offectY:29};
			var markerSymble = new ChrhcMap.Symble(config);	
				cMap.startDraw("point",markerSymble,drawPointEnd,spotClickCallBackEvent);
		},
			 //画可编辑的折线
		getPolyLine:function(cMap,drawLineEnd) {
			//this.stopDraw(cMap);
			cMap.startDraw("polyline",null,drawLineEnd);
		},
		//画可编辑的多边形
		getPolygon:function(cMap,drawLineEnd) {
			//this.stopDraw(cMap);
			cMap.startDraw("polygon",null,drawLineEnd);
		},
		stopDraw:function(cMap){
			cMap.stopDraw();
		}
		 
	}
})();