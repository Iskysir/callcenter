


/**
 * 系统配置类
 */
ChrhcMapConfig = (function() {	
	return {
		//baseApiUrl:"http://61.164.37.195:8003/Default.aspx?mapid=EdushiMap&city=xsxwjd&",//以前的老地址目前不用
		baseApiUrl:"http://10.61.160.20:9000/Index.aspx?mapid=EdushiMap&city=dzdcq&v=dzdcq&",
		queryServiceUrl:"chrhcAutoListController.do?datagrid&configId=sc_gisquery&dataRule=N&field=id,create_name,create_by,create_date,update_name,update_by,update_date,version_num,code,name,dataurl,queryfield,searchfield,showfield,&biztype&sort=code&order=asc",
		layerControlServiceUrl:"chrhcAutoListController.do?datagrid&configId=sc_gislayer&dataRule=N&field=id,name,layertype,status,deleted,create_name,create_by,create_date,update_name,update_by,update_date,clickurl,hoverurl,editurl,addurl,level&biztype=&sort=name&order=asc",	
		layerEditControlServiceUrl:"chrhcAutoListController.do?datagrid&configId=sc_gislayer&dataRule=N&field=id,name,layertype,status,deleted,create_name,create_by,create_date,update_name,update_by,update_date,clickurl,hoverurl,editurl,addurl,updategisxyurl,level,selecttype&biztype=&sort=name&order=asc",//"/scGislayerController.do?datagrid&field=id,name,layertype,status,deleted,create_name,create_by,create_date,update_name,update_by,update_date,datasql,clickurl,hoverurl,addurl,editurl";
		centerMarkerUrl:"images/center.png",
		getPointMarkerUrl:"images/markerbig.png",
		spotLayerName:"hotarea",
		editLayerControlSelectType_Building:"building",
		buildingClickDetailInfoTitle:"详细信息",
		messageTitle:"提示信息",
		BuildingDetailInfoHasDeleteMsg:"该建筑物未绑定基础信息，请确认！",
		BuildingDetailInfoHasNoAddMsg:"该建筑物未生成房间信息，请确认！",
		//判断建筑物是否需要弹出窗口
		checkBuildIngIsNeedAddPopupWindowServiceUrl:"scJzwglController.do?dochkJzwfj&gisId=",
		buildIngPopupWindowServiceUrl:"scJzwglController.do?findFjByGisId&id=",
		/*
		鼠标提示样式
		*/
		 mouseTipsHtml :'<div style="background-color:White">单击即可进行添加！</div>',
		 /*
		鼠标提示X偏移量
		*/
		 mouseTipsOffsetX : 10,
		 /*
		鼠标提示Y偏移量
		*/
		 mouseTipsOffsetY : 1,
		 
		/*
			实体点对象类型
		*/
		entityPoint:"point",
		/*
			shape line对象类型
		*/
		shapeLine:"line",
		/*
			shape polygon对象类型
		*/
		shapePolygon:"polygon",
		/*
		图层类型
		*/
		entityLayerType:"Entitys",
		
		vectorLayerType:"Vector",
		
		businessEditCoordsError:"操作错误，请重新绘图!"
	};
})();



