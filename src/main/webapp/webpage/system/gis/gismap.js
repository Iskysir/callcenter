 var baseMap, eyeMap;
 var cMap;

 var layerControl;
 var enableLayerControl;
 var layerEditControl;
 var enableDataEdit;
 var contextPath;
 var urlConfig = null;
 var mapHeight = 700;
 var mapWidth = 1500;
 var centerTo = null;
 var coords = null;

 var showLevel = null; //地图放大缩小级别
 var selectedLayers = new HashMap();
 var layerControlDiv = null; //图层内容存放数组

 var enableSpotClickForMain;
 var from;

 var initLayerFalg = false; //初始化图层的标志，因为是回调方式渲染，防止重复初始化

 function InitMap() {
     contextPath = UtilMisc.getContextPath();
     var windowWidth = fnGetWindowWidth() - 0;
     var windowHeight = fnGetWindowHeight() - 0;

     //--------------用于新增刷新按钮功能-------------------------------------------
     if (curZoom && curZoom != null) {
         showLevel = curZoom;
     }
     if (centerX && centerX != null && centerY && centerY != null) {
         centerTo = centerX + "," + centerY;
     }
     
     if(centerTo==null ||centerTo==''){
        centerTo = currentGisxy;
     }
     mapWidth = windowWidth;
     mapHeight = windowHeight;
     //--------------用于新增刷新按钮功能-----end-------------------------------------
     var option = {
         mapId: "mapDiv",
         centerTo: centerTo,
         showLevel: showLevel,
         mapWidth: mapWidth,

         mapHeight: mapHeight,
         showCenter: false,
         callBack: initBussinesFunction, //地图初始化完毕后执行的方法
         mapZoomChange: MapZoomChange

     };
     cMap = new ChrhcMap(option);
 }
 /*
 地图放大
 */
 function mapZoomIn() {
     var zoom = cMap.getCurrentZoom();
     cMap.zoomTo(zoom - 1);
 }
 /*
 地图缩小
 */
 function mapZoomOut() {
     var zoom = cMap.getCurrentZoom();
     cMap.zoomTo(zoom + 1);
 }
 /*
 图层显示：在指定比例尺显示控制代码
 */
 function MapZoomChange(zoom) {
     //debugger;        
     //主页图层控制
     if (enableLayerControl && layerControl) {
         layerControl.changeLayerControlDisplay(zoom);
     }
     //主页的编辑
     if (enableDataEdit && layerEditControl) {
         layerEditControl.changeLayerControlDisplay(zoom);
     }
 }

 function initBussinesFunction(map) {
     baseMap = map;
     cMap.enableSpotClick(spotClickShowMoreInfoEvent,1);
    

 }

 function spotClickShowMoreInfoEvent(e){
            //alert("ID:"+e.ID+" , 名称："+e.Name+" ,坐标串为："+e.Coords);
              
            var buildIngCheck = new ChrhcMap.CheckBuildIngQuery(null,addOneshowModalDialog);
                    buildIngCheck.addOneTab(e.ID);
            
            //createwindow("详细信息", url,900,800);
            
        }
// 添加标签
function addOneTab(subtitle, url, icon) {
    parent.addTab(subtitle, url, icon);
}
//模态弹出方式
function addOneshowModalDialog(subtitle, url, icon){
    var dialogWidth = 1024;
    var dialogHeight = 768;
    var windowWidth = fnGetWindowWidth() * (0.7);
    var windowHeight = fnGetWindowHeight() * (0.7);
    if(dialogWidth < windowWidth){
        dialogWidth = windowWidth;
    }
    if(dialogHeight < windowHeight){
        dialogHeight = windowHeight
    }
    
    window.top.lhgDialog({
        content: 'url:'+url,
        lock : true,
        //zIndex:1990,
        width:dialogWidth,
        height:dialogHeight,
        title:subtitle,
        opacity : 0.3,
        cache:false,
        cancelVal: '关闭',
        cancel: true /*为true等价于function(){}*/
    }).zindex();
    //var someValue=window.showModalDialog(url,subtitle,"dialogWidth="+dialogWidth+"px;dialogHeight="+dialogHeight+"px;center:yes;status=no;help=no;scrollbars=yes;");
    //window.top.createwindow(subtitle, url,dialogWidth,dialogHeight);
    //window.top.art.dialog.open(url+'&_x='+Math.random(),{title: subtitle,width:dialogWidth,height:dialogHeight,id:subtitle,resize:true});

}


 function measure(type) {
     if (cMap) {
         cMap.measure(type);
     }
 }

 //显示图层方法
 function showLayerControl() {
     if (layerControlDiv != null && layerControlDiv.length > 0) { //判断图层是否存在
         return;
     } else { //如果没有图层数据则初始化
         initLayerControl();
     }

 }

 //初始化图层方法
 function initLayerControl() {
     if (initLayerFalg) {
         return;
     }

     initLayerFalg = true;
     //$("#layerControl").empty();
     $(".la_li_div").remove(); //清空数据元素
     if (layerEditControl) {
         layerEditControl.hiddenAllLayers();
     }
     //chrhcAutoListController.do?datagrid&configId=sc_gislayer&field=id,name,layertype,status,deleted,create_name,create_by,create_date,update_name,update_by,update_date,datasql,clickurl,hoverurl,editurl,addurl,&biztype=
     var url = contextPath + "/" + ChrhcMapConfig.layerControlServiceUrl + "&_x=" + Math.random(); //chrhcAutoListController.do?datagrid&configId=sc_gislayer&field=id,name,layertype,status,deleted,create_name,create_by,create_date,update_name,update_by,update_date,datasql,clickurl,hoverurl,editurl,addurl,level&biztype=";//"/scGislayerController.do?datagrid&field=id,name,layertype,status,deleted,create_name,create_by,create_date,update_name,update_by,update_date,datasql,clickurl,hoverurl,addurl,editurl";
     var type = "post";
     var where = null;
     var callBack = initLayerControlData;

     var query = new ChrhcMap.Query(url, type, where, callBack, this);
     query.execute(where);


 }

 var layerControlHashMap;
 function initLayerControlData(jsonData, isObject) {
     //alert(jsonData);
     layerControlHashMap = new HashMap();
     var data;
     if (!isObject) {
         try {
             data = eval('(' + jsonData + ')');
         } catch (e) {
             return;
         }

     }
     if (isObject) {
         data = jsonData;
     }
     var rows = data.rows;
     initEndCallBackFunction(rows);
     var dataSource = [];
     var url = contextPath + "/scGislayerController.do?getData";
     var layerHashMap = new HashMap();
     var queryServiceUrlHashMap = new HashMap();
     for (var i = 0; i < rows.length; i++) {
         var name = rows[i].name;
         var id = rows[i].id;
         var layerType = rows[i].layertype;
         //debugger;
         var layerLevel = rows[i].level == null ? "0" : rows[i].level;
         var showLevel = parseInt(layerLevel);
         var data = {
             layerType: layerType,
             layerName: id,
             layerId: id,
             layerZhName: name,
             showLevel: showLevel,
             url: url + "&id=" + id,
             where: "where",
             visible: false
         };
         dataSource.push(data);
         var layerTypeControl = layerType == ChrhcMapConfig.entityPoint ? ChrhcMapConfig.entityLayerType : ChrhcMapConfig.vectorLayerType;

         var layerHashMapValue = {
             isChecked: false,
             showLevel: showLevel,
             layerType: layerTypeControl,
             layerId: id,
             layerName: name,
             visible: false,
             layer: null,
             layerBq: null
         };
         layerHashMap.put(id, layerHashMapValue);
         queryServiceUrlHashMap.put(id, url + "&id=" + id);
         layerControlHashMap.put(id, rows[i]);

     }

     var layerControlData = [{
         //folderName:"所有图层",
         dataSource: dataSource
     }];
     layerControl = new ChrhcMap.LayerControl(cMap, layerControlData, null, onLayerClickEvent, drawPointEnd, onLayerControlDragCompleteEvent, true);
     layerControl.layerHashMap = layerHashMap;
     layerControl.queryServiceUrlHashMap = queryServiceUrlHashMap;
 }

 var layerControlGroup = 0;
 /*
    图层控制模块初始化完毕后的回调方法
    <div class="la_li_div">
          <div class="checkboxFive">
                <input type="checkbox" value="1" id="checkboxOneInput_1" name="" />
                        </div>
                        <label class="label_checkbox" for="checkboxOneInput_1" name="checkboxOneInput_1">建筑物人口</label>
                    </div>
    */
 function initEndCallBackFunction(data) {

     layerControlDiv = new Array();

     for (var i = 0; i < data.length; i++) {
         var id = data[i].id;
         var name = data[i].name;

         var checked = data[i].checked == undefined ? false : true;
         layerControlDiv.push("<div class=\"la_li_div\" onclick=\"checkBoxRowClick(this);\" >");
         //layerControlDiv.push("<li id=\"show_"+id+"\" onclick=\"showOrHiddenLayer(this);\" checked=\""+checked+"\"");
         layerControlDiv.push("<div class=\"checkboxFive\">");
         layerControlDiv.push("<input type=\"checkbox\" onclick=\"showOrHiddenLayer(this);\" ");
         if (checked) {
             //layerControlDiv.push(" class=\"li-checked\" ");
             //selectedLayers.put(id,id); checked="true"
             layerControlDiv.push(" checked=\"true\" ");
             selectedLayers.put(id, id);
         }
         layerControlDiv.push(" value=\"" + id + "\" id=\"checkboxOneInput_" + id + "\" name=\"checkboxOneInput\" />");

         layerControlDiv.push("</div>");
         layerControlDiv.push("<label class=\"label_checkbox\" for=\"checkboxOneInput_" + id + "\" name=\"checkboxOneInput_" + id + "\">" + name + "</label>");
         layerControlDiv.push("</div>");
     }



     //$("#layerControl").append.display = "";
     //alert(layerControlDiv.join(""));

     $(".ico_layer_div").append(layerControlDiv.join(""));

     $(".checkboxFive input:checkbox").click(function(evt){
        evt.stopPropagation(); //因为checkbox所在行也要求选中事件，所以在此阻断，防止事件冲突
     });
     $(".label_checkbox").click(function(evt){
        evt.stopPropagation(); //因为checkbox所在行也要求选中事件，所以在此阻断，防止事件冲突
     });

 }

 function onLayerClickEvent(e) {
     if (e != null) {
         var dataID;
         var layerId;

         if (e.layerId != null) {
             var dataIDS = e.id.split("_");
             dataID = dataIDS[1];
             var layerIDS = e.layerId.split("_");
             layerId = layerIDS[1];
             layerControlClickInfoWindowHtml(false, dataID, layerId);
         }
     }

 }

 function layerControlClickInfoWindowHtml(isEdit, dataId, layerId) {
     var title = isEdit ? "编辑" : "查看",
         width = 900,
         height = 600;

     var layerName = "";
     var layerRow;

     var clickUrl;
     if (isEdit) {

     } else {
         layerRow = layerControlHashMap.get(layerId);
         var normalurl = layerRow.clickurl;
         normalurl = contextPath + "/" + normalurl + dataId;
         clickUrl = normalurl;
     }
     if (layerRow != null) {
         layerName = layerRow.name;
     }
     title = title + layerName;
     //createwindow(title, clickurl,width,height);
    // addOneTab(title, clickUrl);
     addOneshowModalDialog(title,clickUrl);
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
 当图层处于可编辑状态，图标拖拽完毕后执行的方法，可以进行坐标串的更新操作
 */
 function onLayerControlDragCompleteEvent(id, name, coords) {

 }

 function showOrHiddenLayer(that) {
     //alert(that.checked);
     var isChecked = that.checked;

     var domId = that.id;
     var key;
     if (domId != null) {
         var domIdArr = domId.split("_");
         key = domIdArr[1];
     }

     if (isChecked == false) {
         layerControl.hiddenLayer(key);
         selectedLayers.remove(key);
     } else if (isChecked == true) {
         selectedLayers.put(key);
         layerControl.refreshLayerData(key);
     }
     //alert('e');
 }

 function hiddenAllLayer() {
     if (layerControl != null) {
         layerControl.removeAllLayers();
         //layerControl.hiddenAllLayers();
     }

 }

 function mapRefresh() {

     //draw('4')
     if (cMap != null && baseMap != null) {
         var zoom = cMap.getCurrentZoom();
         var centerCoords = cMap.getCenter();
         //$("form").submit();
         $("#curZoom").val(zoom);
         $("#centerX").val(centerCoords[0]);
         $("#centerY").val(centerCoords[1]);         
         $("form").submit();
     }
 }

 //结束测距方法
 function finishScalClick() {
     if (cMap != null) {
         cMap.stopScal();
     }
 }
 //图层关闭逻辑
 function layerClose() {
     ChrhcMap_Business_Functions.stopDraw(cMap);
     hiddenAllLayer();
     $(".checkboxFive input:checkbox").attr("checked", false);
 }
 //页面按钮之间切换逻辑
 function buttonSwitch() {
     var ruleButtonObj = $(".ruler-button");
     var layerShowButton = $(".layer-show-button");
     var arr = [layerShowButton, ruleButtonObj, $(".case-button")];
     var domRegistClickEventGroup = new ChrhcMap.DomClickRegistGroup(arr);
     //在点击查询文本框、测量面积、图层编辑、图层显示的时候要自定结束测量
     domRegistClickEventGroup.registEvent(ruleButtonObj, finishScalClick);
     domRegistClickEventGroup.registEvent(layerShowButton, layerClose);
 }

 function checkBoxRowClick(that){
    
          //var check = $(this).children().find("input:checkbox");
          var check = $(that).find("input:checkbox"); 
          var rs = check.is(':checked');
          //console.log("gismap.js check="+check+" check.type = "+check.attr('type'));
          //console.log("gismap.js checkBoxRowClick rs="+rs);
          if(rs){ 
                //check.attr('checked',false);
                check[0].checked = false;
                //console.log("gismap.js checkBoxRowClick setcheck false");
          }else{
                //check.attr('checked',true);
                check[0].checked = true;
//console.log("gismap.js checkBoxRowClick setcheck true1");
          }
          showOrHiddenLayer(check[0]);
    
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

 $(function() {

     InitMap();

     //刷新按钮click
     $(".mapRefresh").click(function() {
         mapRefresh();

     });
     //测距按钮click
     $(".ruler-button").click(function() {
         measure("1");

     });
     //测面按钮click
     $(".case-button").click(function() {
         measure("2");

     });
     //图层按钮关闭click
     $(".layer-list-close").click(function() {
         layerClose();
     });

     //图层按钮显示click
     $(".layer-show-button").click(function() {
         showLayerControl();
     });

     
     //document ready后执行图层初始化方法
     showLayerControl(); //该方法响应较慢，所以页面初始化时先执行
     buttonSwitch();
     

 });
