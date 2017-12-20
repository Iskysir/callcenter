
/*
config={
	baseMap:baseMap,//必选
	symbleType:symbleType,//必选:Markers/Text/Complex
	style:style,//Text、Complex时有效
	markerUrl:markerUrl,//Markers、Complex时有效
	autoresize:false,
	width:70,//type="Number":   元素宽度，默认为0
	height:30,//type="Number":   元素高度，默认为0
	offectX:0,//type="Number":   偏移像素X
	offectY:0,//type="Number":   偏移像素Y
	//enableEdit:false,//type="Boolen":   是否能移动
	autoremove:false,//type="Boolen":   是否自动删除
	autozoomchangeremove:false//type="Boolen":   级别缩放是否删除	

}
*/
ChrhcMap.Symble = new Class({
	div:null,
	_symbleType: null,
	_baseMap:null,
	_style:null,
	_markerUrl:null,
	_host:null,
	//enableEdit:false,
	offectX:0,
	offectY:0,
	width:0,
	height:0,
	autoremove:false,
	autozoomchangeremove:false,
    initialize: function(config){
        this.config = config;
		if(config!=undefined&&config!=null){
			if(config.baseMap!=undefined){
				this._baseMap = config.baseMap;
			}
			if(config.style!=undefined){
				this._style = config.style;
			}
			if(config.markerUrl!=undefined){
				this._markerUrl = config.markerUrl;
			}
			if(config.symbleType!=undefined){
				this._symbleType = config.symbleType;
			}
			if(config.offectX!=undefined){
				this.offectX = config.offectX;
			}
			if(config.offectY!=undefined){
				this.offectY = config.offectY;
			}
			if(config.width!=undefined){
				this.width = config.width;
			}
			if(config.height!=undefined){
				this.height = config.height;
			}
			if(config.autoremove!=undefined){
				this.autoremove = config.autoremove;
			}
			if(config.autozoomchangeremove!=undefined){
				this.autozoomchangeremove = config.autozoomchangeremove;
			}
			if(config.enableEdit!=undefined){
				this.enableEdit = config.enableEdit;
			}
			
		}
		this._host = UtilMisc.getContextPath();
		 
    },
	_createDiv:function(baseMap){
		
		var div = this._baseMap.$C('div');
			div.geoType = "entity";
			div.style.margin ="0px";//"margin:5px;width:200px; height:200px;background-color:White;";
		return div;
	},
	createSymble:function(layerId,id,name,x,y){

		switch (this._symbleType)
			{
			case "Markers":
				this.div = this._createMarkerSymble(layerId,id,name,x,y);
			  break;
			case "Text":
				this.div = this._createTextSymble(layerId,id,name,x,y);
			  break;
			case "Complex":
				this.div = this._createComplexSymble(layerId,id,name,x,y);
			  break;
			}
		
	},
	_createMarkerSymble:function(layerId,id,name,x,y){
		var div =this._createDiv();		
		var img="<a href=\"javascript:void(0)\" title=\""+name+"\"  layerId=\""+layerId+"\" id=\""+id+"\" name =\""+name+"\" style=\"background-image:url("+this._host+"/plug-in/chrhc/images/markers.png); ";
			img = img+"width:22px;height:29px;display:inline-block;text-decoration:none;"+this._style+" \">";
			img = img+"</a>";
		//var img = '<img layerId=\"'+layerId+'\" id=\"'+id+'\" name =\"'+name+'\" src=\"'+this._host+"/plug-in/gis/alMap/"+this._markerUrl+'\"  alt=\"'+name+'\" />';
		//var img = "<div><a href=\"javascript:void(0)\" class=\"bubble no-1\"></a></div>";
			div.innerHTML = img;//string.Format(img,"",name);//string.Format('', "", name);
			div.width="22px";
			div.height="29px";
			div.id = id;
			div.name = name;
			div.geoType = "entity";
			div.x = x;
			div.y = y;
		return div;							
	},

	_createTextSymble:function(layerId,id,name,x,y){
		var div =this._createDiv();
		var span = '<span layerId=\"'+layerId+'\" id=\"'+id+'\" name =\"'+name+'\" title="{1}"  style="'+this._style+'">{1}</span>';
			div.innerHTML = string.Format(span,"",name);//string.Format('', "", name);
			div.id = id;
			div.name = name;
			div.geoType = "entity";
			div.x = x;
			div.y = y;
		return div;							
	},
	/*
	 * 
	 * 
	 * 	<div class="tooltipp" style="width:88px;">
				<div class="tooltipp-main">
					如家快捷酒店
				</div>
				<div class="tooltipp-bottom"></div>
			</div><a href=\"javascript:void(0)\" title=\""+name+"\"  layerId=\""+layerId+"\" id=\""+id+"\" name =\""+name+"\"
	 */
	_createComplexSymble:function(layerId,id,name,x,y){
		var div =this._createDiv();
		var html = "<div  style=\"position:relative; border:1px solid red; z-index:600 !important;font-size:12px; word-break:keep-all; white-space: nowrap;\" layerId=\""+layerId+"\" id=\""+id+"\" name =\""+name+"\">";
			html = html+"<div style=\"position:absolute; top:0px;left:0px;width:auto;height:30px;line-height:30px;padding:0px 6px;color:#FFFFFF;background-color:#E33522;\" layerId=\""+layerId+"\" id=\""+id+"\" name =\""+name+"\">";
			html = html+name;
			html = html+"</div>";
			html = html+"<div style=\"position:absolute;top:30px;left:5px;width:20px;height:10px;background:url("+this._host+"/plug-in/chrhc/images/tooltip.png) no-repeat;\" layerId=\""+layerId+"\" id=\""+id+"\" name =\""+name+"\"></div>";
			html = html+"</div>";
		//var complex = '<div class=\"tooltipp\" style=\"width:88px;\"  layerId=\"'+layerId+'\" id=\"'+id+'\" name =\"'+name+'\" title="{1}">{1}</span><img layerId=\"'+layerId+'\" id=\"'+id+'\" name =\"'+name+'\"  src=\"'+this._host+"/plug-in/gis/alMap/"+this._markerUrl+'\"   alt=\"'+name+'\" />';
			div.innerHTML = string.Format(html,"",name);//string.Format('', "", name);
			div.id = id;
			div.name =name;
			div.geoType = "entity";
			div.x = x;
			div.y = y;
			//$("#mapDiv").append(html);
		return div;							
	}
});
