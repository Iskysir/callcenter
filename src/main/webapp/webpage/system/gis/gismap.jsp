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
<title>社区网格</title>
<meta http-equiv="X-UA-Compatible"
	content="IE=8;text/html;charset=utf-8" />
<!-- 页面样式引用 -->
<link rel="stylesheet"  href="<c:out value="${webRoot}"/>/plug-in/gis/alMap/newstyle/css/font-awesome.min.css" type="text/css">
<!--[if IE 7]>
<link rel="stylesheet" type="text/css" href="css/font-awesome-ie7.min.css">
<![endif]-->
<link rel="stylesheet" href="<c:out value="${webRoot}"/>/plug-in/gis/alMap/newstyle/css/Library.css" type="text/css">
<link rel="stylesheet" href="<c:out value="${webRoot}"/>/plug-in/gis/alMap/newstyle/css/style.css" type="text/css">
<script type="text/javascript" src="<c:out value="${webRoot}"/>/plug-in/gis/alMap/newstyle/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<c:out value="${webRoot}"/>/plug-in/gis/alMap/newstyle/js/main.js"></script>
<!--[if lte IE 9]>
<script type="text/javascript" src="js/html5.js"></script>
<script type="text/javascript" src="js/PIE.js"></script>
<script type="text/javascript" src="js/pieuser.js"></script>
<![endif]-->
<!-- 页面样式引用 END -->

<!-- Gis 组件js依赖js、css-->

<link id="easyuiTheme" rel="stylesheet"
    href="<c:out value="${webRoot}"/>/plug-in/easyui/themes/default/easyui.css"
    type="text/css"></link>
<link rel="stylesheet"
    href="<c:out value="${webRoot}"/>/plug-in/easyui/themes/icon.css" type="text/css"></link>
    <script type="text/javascript"
    src="<c:out value="${webRoot}"/>/plug-in/easyui/jquery.easyui.min.1.3.2.js">
    
</script>
<script type="text/javascript"
    src="<c:out value="${webRoot}"/>/plug-in/easyui/locale/zh-cn.js">
    </script>
<!-- <script type="text/javascript"
    src="<c:out value="${webRoot}"/>/plug-in/tools/easyuiextend.js"></script> -->
  
<!-- Gis 组件js依赖js、css END-->

<!-- Gis 组件js引用 -->
<script type="text/javascript"
	src="<c:out value="${webRoot}"/>/plug-in/gis/alMap/lib/ChrhcMap.Include.js"></script>
<!-- Gis 组件js引用 END-->

<!-- 本页面业务js引用 -->
<script type="text/javascript">
 //----页面参数
 var curZoom = '${curZoom}';
 var centerX = '${centerX}';
 var centerY = '${centerY}';
 var currentGisxy = "<%=currentGisxy%>";
</script>

<script type="text/javascript"
    src="<c:out value="${webRoot}"/>/webpage/system/gis/gismap.js"></script>
<!-- 本页面业务js引用 END-->

</head>

<body>

    <div class="menu_div_page">
        
        <div id="mapDiv" style="width:100%; height:100%;  position:absolute; top:0px; left:0px;overflow: hidden;"/>
        <ul class="grid_ico_ul">
            <!-- selected 为选中效果-->
            <li class="mapRefresh"  title="刷新"><div class="ico_li_div f_ic_1"></div></li>
            <t:optAuthFilter name="layer">
            <li class="lay_li " title="图层" >
                <div id="layer-show-button" class="ico_li_div f_ic_2 layer-show-button" ></div>
                <div class="ico_layer_div">
                    <div class="la_title_div">图层显示<i class='remove_div_i fa  fa-remove rt layer-list-close' title="关闭"></i><!-- <img class="rt" src="images/icons/arrow.png"/> --></div>
                   <!--  <div class="la_li_div">
                        <div class="checkboxFive">
                            <input type="checkbox" checked="true" value="1" id="checkboxOneInput_1" name="" />
                        </div>
                        <label class="label_checkbox" for="checkboxOneInput_1" name="checkboxOneInput_1">建筑物人口</label>
                    </div>
                    <div class="la_li_div">
                        <div class="checkboxFive">
                            <input type="checkbox" value="1" id="checkboxOneInput_2" name="" />
                        </div>
                        <label class="label_checkbox" for="checkboxOneInput_2" name="checkboxOneInput_2">建筑物分布</label>
                    </div>
                    <div class="la_li_div">
                        <div class="checkboxFive">
                            <input type="checkbox" value="1" id="checkboxOneInput_3" name="" />
                        </div>
                        <label class="label_checkbox" for="checkboxOneInput_3" name="checkboxOneInput_3">楼院划分</label>
                    </div>
                    <div class="la_li_div">
                        <div class="checkboxFive">
                            <input type="checkbox" value="1" id="checkboxOneInput_4" name="" />
                        </div>
                        <label class="label_checkbox" for="checkboxOneInput_4" name="checkboxOneInput_4">社区人口</label>
                    </div>
                    <div class="la_li_div">
                        <div class="checkboxFive">
                            <input type="checkbox" value="1" id="checkboxOneInput_5" name="" />
                        </div>
                        <label class="label_checkbox" for="checkboxOneInput_5" name="checkboxOneInput_5">社区划分</label>
                    </div> -->
                </div>
            </li>
            </t:optAuthFilter>
            <t:optAuthFilter name="case-button">
            <li title="左键测面，右键结束测面。" ><div id="case-button" class="ico_li_div f_ic_3 case-button" ></div></li>
            </t:optAuthFilter>
            <t:optAuthFilter name="ruler-button">
            <li title="左键测量，右键撤销，双击结束测量。" ><div id="ruler-button" class="ico_li_div f_ic_4 ruler-button" ></div></li>
            </t:optAuthFilter>
        </ul>
               
    </div>
   <form method="post" action="">
    <input id="giscoords" type="hidden">
	<input id="gisname" type="hidden">
	<input id="gisid" type="hidden">

    <input id="curZoom" name="curZoom" type="hidden">
    <input id="centerX" name="centerX" type="hidden">
    <input id="centerY" name="centerY" type="hidden">
    </form>
   
</body>


</html>

