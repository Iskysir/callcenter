<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>亲属关系表图</title>
  <link href="plug-in/family/css/style.css" rel="stylesheet">
  <link href="plug-in/family/css/jquery.webui-popover.css" rel="stylesheet">
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
    <script src = "webpage/com/chrhc/project/sc/kinship/kinship.js"></script>	
  <script src="plug-in/family/js/jquery.webui-popover.js"></script>	
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
  <body class="family-box">
    <div class="family">
      <div class="family-title">
        <span class="title-first">居民家庭亲属关系图</span>
       <!--   <span class="title-second">Family Relationship</span>-->
      
      </div>
      <div class="family-map">
        <span class="role role-zf"></span>
        <span class="role role-zm"></span>
        <span class="role role-wzf"></span>
        <span class="role role-wzm"></span>


        <div class="node1A"></div>
        <div class="node1B"></div>


        <span class="role role-gm"></span>
        <span class="role role-gz"></span>
        <span class="role role-bf"></span>
        <span class="role role-bm"></span>
        <span class="role role-fq"></span>
        <span class="role role-mq"></span>
        <span class="role role-jm"></span>
        <span class="role role-jj"></span>
        <span class="role role-yz"></span>
        <span class="role role-ym"></span>


        <div class="node2A"></div>
        <div class="node2B"></div>
        <div class="node2C"></div>
        <div class="node2D"></div>
        <div class="node2E"></div>


        <span class="role role-bxdA"></span>
        <span class="role role-bjmA"></span>
        <span class="role role-txd"></span>
        <span class="role role-tjm"></span>
        <span class="role role-gg"></span>
        <span class="role role-ss"></span>
        <span class="role role-lg"></span>
        <span class="role role-lp"></span>
        <span class="role role-jiej"></span>
        <span class="role role-jief"></span>
        <span class="role role-bxdB"></span>
        <span class="role role-bjmB"></span>
        <span class="role role-bxdC"></span>
        <span class="role role-bjmC"></span>


        <div class="node3A"></div>
        <div class="node3B"></div>
        <div class="node3C"></div>


        <span class="role role-zz"></span>
        <span class="role role-zn"></span>
        <span class="role role-ez"></span>
        <span class="role role-ex"></span>
        <span class="role role-nx"></span>
        <span class="role role-ne"></span>
        <span class="role role-ws"></span>
        <span class="role role-wsn"></span>


        <div class="node4A"></div>
        <div class="node4B"></div>


        <span class="role role-sz"></span>
        <span class="role role-sn"></span>
        <span class="role role-wsun"></span>
        <span class="role role-wsunn"></span>
      </div>
    </div>
    <script>
      (function(){
    	var qsgxjson = '${qsgxjson}';
    	var sex = '${sex}';
    	var urlConfig = eval('(' + qsgxjson + ')');
        var settings = {
            trigger:'click',
            title:'亲属信息',
            content:'',
            width:300,            
            multi:false,           
            closeable:true,
            style:'',
            padding:true
        };

        function initPopover(){  
  			if(sex == '1'){
  				$('span.role-lg').attr({"style":"background-color:#E74C3C;"});
  			}else{
  				$('span.role-lp').attr({"style":"background-color:#E74C3C;"});
  			}
      
         	for(var props in urlConfig) { 
         		debugger;
         		var gxlxname = gxlx[props].name;
        		var aa = urlConfig[props];
        		 var contenta = "";
         		$.each(aa,function(k,m){
         			contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+m.id+'\')" >'+m.name+'</span></p>';
         		}
         		) 
         		settings.content = contenta;
         		var $gxlxse = null;
         		switch(props)
        		{
        		case "1": //父亲
        			$gxlxse = $('span.role-fq');
        			
        		  break;
        		case "2": //母亲
        			$gxlxse =$('span.role-mq');
        			
        		  break;
        		case "3"://儿子
        			$gxlxse =$('span.role-ez');
        			
        		  break;
        		case "4"://女儿
        			$gxlxse =$('span.role-ne');
        			
        		  break;
        		case "5"://丈夫
        			$gxlxse =$('span.role-lg');
        		
        			
        		  break;
        		case "6"://妻子
        			$gxlxse =$('span.role-lp');
        			
        		  break;
        		case "7"://儿媳
        			$gxlxse =$('span.role-ex');
        			
        		  break;
        		case "8"://女婿
        			$gxlxse =$('span.role-nx');
        			
        		  break;
        		case "51"://叔叔伯父
        			$gxlxse =$('span.role-bf');
        			
        		  break;
        		  
        		case "55"://姑妈
        			$gxlxse =$('span.role-gm');
        			
        		  break;
        		case "9"://祖父
        			$gxlxse =$('span.role-zf');
        			
        		  break;
        		case "10"://祖母
        			$gxlxse =$('span.role-zm');
        			
        		  break;
        		case "59"://舅舅
        			$gxlxse =$('span.role-jj');
        			
        		  break;
        		case "63"://姨妈
        			$gxlxse =$('span.role-ym');
        			
        		  break;
        		case "11"://外祖父
        			$gxlxse =$('span.role-wzf');
        			
        		  break;
        		case "12"://外祖母
        			$gxlxse =$('span.role-wzm');
        			
        		  break;
        		case "13"://孙子
        			$gxlxse =$('span.role-sz');
        			
        		  break;
        		case "14"://孙女
        			$gxlxse =$('span.role-sn');
        			
        		  break;
        		case "15"://外孙
        			$gxlxse =$('span.role-wsun');
        			
        		  break;
        		case "16"://外孙女
        			$gxlxse =$('span.role-wsunn');
        			
        		  break;
        		case "17"://哥哥
        			  if(!$('span.role-gg').hasClass("role-checked")){
          		    	$gxlxse =$('span.role-gg');   
          		    	var map = new Map();
         		    	 	var contenta = "";
         		    	 	var gg = urlConfig[props];
         		    	 	if(gg){
         		    	 		$.each(gg,function(h,j){
                   	         		if(!map.containsKey(j.id)){
               	         				map.put(j.id,j.name);               	         			
               	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
               	         			}
                 	         		})
         		    	 	}         	                 	         		
         	         		var bb = urlConfig["67"];
         	         		gxlxname = gxlx["67"].name;
         	         		if(bb){
           	         		$.each(bb,function(h,j){
    	         			if(!map.containsKey(j.id)){
    	         				map.put(j.id,j.name);
    	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
    	         			}
    	         			}) 
         	         		}
    	         			var cc = urlConfig["18"];
    	         			gxlxname = gxlx["18"].name;
           	         		if(cc){
           	         		$.each(cc,function(r,t){
        	         			if(!map.containsKey(t.id)){
        	         				map.put(t.id,t.name);
        	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+t.id+'\')" >'+t.name+'</span></p>';
        	         			}
        	         			}) 
           	         		}
           	         		
           	         		settings.content = contenta;
          		    }
        			
        		  break;
        		case "18"://弟弟
        			  if(!$('span.role-gg').hasClass("role-checked")){
            		    	$gxlxse =$('span.role-gg');   
            		    	var map = new Map();
           		    	 	var contenta = "";
           		    	 	var gg = urlConfig[props];
           		    	 	if(gg){
           		    	 		$.each(gg,function(h,j){
                     	         		if(!map.containsKey(j.id)){
                 	         				map.put(j.id,j.name);               	         			
                 	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
                 	         			}
                   	         		})
           		    	 	}         	                 	         		
           	         		var bb = urlConfig["67"];
           	         	     gxlxname = gxlx["67"].name;
           	         		if(bb){
             	         		$.each(bb,function(h,j){
      	         			if(!map.containsKey(j.id)){
      	         				map.put(j.id,j.name);
      	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
      	         			}
      	         			}) 
           	         		}
      	         			var cc = urlConfig["17"];
      	         			gxlxname = gxlx["17"].name;
             	         		if(cc){
             	         		$.each(cc,function(r,t){
          	         			if(!map.containsKey(t.id)){
          	         				map.put(t.id,t.name);
          	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+t.id+'\')" >'+t.name+'</span></p>';
          	         			}
          	         			}) 
             	         		}
             	         		
             	         		settings.content = contenta;
            		    }
        			
        		  break;
        		case "19"://姐姐
        			  if(!$('span.role-jiej').hasClass("role-checked")){
        				  $gxlxse =$('span.role-jiej'); 
          		    	var map = new Map();
         		    	 	var contenta = "";
         		    	 	var gg = urlConfig[props];
         		    	 	if(gg){
         		    	 		$.each(gg,function(h,j){
                   	         		if(!map.containsKey(j.id)){
               	         				map.put(j.id,j.name);               	         			
               	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
               	         			}
                 	         		})
         		    	 	}         	                 	         		
         	         		var bb = urlConfig["20"];
         	         		gxlxname = gxlx["20"].name;
         	         		if(bb){
           	         		$.each(bb,function(h,j){
    	         			if(!map.containsKey(j.id)){
    	         				map.put(j.id,j.name);
    	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
    	         			}
    	         			}) 
         	         		}
    	         			var cc = urlConfig["68"];
    	         			gxlxname = gxlx["68"].name;
           	         		if(cc){
           	         		$.each(cc,function(r,t){
        	         			if(!map.containsKey(t.id)){
        	         				map.put(t.id,t.name);
        	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+t.id+'\')" >'+t.name+'</span></p>';
        	         			}
        	         			}) 
           	         		}
           	         		
           	         		settings.content = contenta;
          		    }
        			
        			
        		  break;
        		case "20"://妹妹
        			 if(!$('span.role-jiej').hasClass("role-checked")){
       				  $gxlxse =$('span.role-jiej'); 
         		    	var map = new Map();
        		    	 	var contenta = "";
        		    	 	var gg = urlConfig[props];
        		    	 	if(gg){
        		    	 		$.each(gg,function(h,j){
                  	         		if(!map.containsKey(j.id)){
              	         				map.put(j.id,j.name);               	         			
              	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
              	         			}
                	         		})
        		    	 	}         	                 	         		
        	         		var bb = urlConfig["19"];
        	         		gxlxname = gxlx["19"].name;
        	         		if(bb){
          	         		$.each(bb,function(h,j){
   	         			if(!map.containsKey(j.id)){
   	         				map.put(j.id,j.name);
   	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
   	         			}
   	         			}) 
        	         		}
   	         			var cc = urlConfig["68"];
   	         			gxlxname = gxlx["68"].name;
          	         		if(cc){
          	         		$.each(cc,function(r,t){
       	         			if(!map.containsKey(t.id)){
       	         				map.put(t.id,t.name);
       	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+t.id+'\')" >'+t.name+'</span></p>';
       	         			}
       	         			}) 
          	         		}
          	         		
          	         		settings.content = contenta;
         		    }
        			
        		  break;
        		case "67"://哥哥弟弟
        	
        		  if(!$('span.role-gg').hasClass("role-checked")){
        		    	$gxlxse =$('span.role-gg');   
        		    	var map = new Map();
       		    	 	var contenta = "";
       		    	 	var gg = urlConfig[props];
       		    	 	if(gg){
       		    	 		$.each(gg,function(h,j){
                 	         		if(!map.containsKey(j.id)){
             	         				map.put(j.id,j.name);               	         			
             	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
             	         			}
               	         		})
       		    	 	}         	                 	         		
       	         		var bb = urlConfig["17"];
       	         		gxlxname = gxlx["17"].name;
       	         		if(bb){
         	         		$.each(bb,function(h,j){
  	         			if(!map.containsKey(j.id)){
  	         				map.put(j.id,j.name);
  	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
  	         			}
  	         			}) 
       	         		}
  	         			var cc = urlConfig["18"];
  	         			gxlxname = gxlx["18"].name;
         	         		if(cc){
         	         		$.each(cc,function(r,t){
      	         			if(!map.containsKey(t.id)){
      	         				map.put(t.id,t.name);
      	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+t.id+'\')" >'+t.name+'</span></p>';
      	         			}
      	         			}) 
         	         		}
         	         		
         	         		settings.content = contenta;
        		    }
        			
        		
        		  break;
        		case "68"://姐姐妹妹
        			 if(!$('span.role-jiej').hasClass("role-checked")){
       				  $gxlxse =$('span.role-jiej'); 
         		    	var map = new Map();
        		    	 	var contenta = "";
        		    	 	var gg = urlConfig[props];
        		    	 	if(gg){
        		    	 		$.each(gg,function(h,j){
                  	         		if(!map.containsKey(j.id)){
              	         				map.put(j.id,j.name);               	         			
              	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
              	         			}
                	         		})
        		    	 	}         	                 	         		
        	         		var bb = urlConfig["19"];
        	         		gxlxname = gxlx["19"].name;
        	         		if(bb){
          	         		$.each(bb,function(h,j){
   	         			if(!map.containsKey(j.id)){
   	         				map.put(j.id,j.name);
   	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
   	         			}
   	         			}) 
        	         		}
   	         			var cc = urlConfig["20"];
   	         			gxlxname = gxlx["20"].name;
          	         		if(cc){
          	         		$.each(cc,function(r,t){
       	         			if(!map.containsKey(t.id)){
       	         				map.put(t.id,t.name);
       	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+t.id+'\')" >'+t.name+'</span></p>';
       	         			}
       	         			}) 
          	         		}
          	         		
          	         		settings.content = contenta;
         		    }
        			
        		  break;
        		case "23"://侄子
        			$gxlxse =$('span.role-zz');
        			
        		  break;
        		case "24"://侄女
        			$gxlxse =$('span.role-zn');
        			
        		  break;
        		case "28"://外甥
        			$gxlxse =$('span.role-ws');
        			
        		  break;
        		case "29"://外甥女
        			$gxlxse =$('span.role-wsn');
        			
        		  break;
        		case "30"://妹夫
        			
        			 if(!$('span.role-jief').hasClass("role-checked")){
        				 $gxlxse =$('span.role-jief');
            		    	var map = new Map();
           		    	 	var contenta = "";
           		    	 	var gg = urlConfig[props];
           		    	 	if(gg){
           		    	 		$.each(gg,function(h,j){
                     	         		if(!map.containsKey(j.id)){
                 	         				map.put(j.id,j.name);               	         			
                 	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
                 	         			}
                   	         		})
           		    	 	}         	                 	         		
           	         		var bb = urlConfig["31"];
           	         		gxlxname = gxlx["31"].name;
           	         		if(bb){
             	         		$.each(bb,function(h,j){
      	         			if(!map.containsKey(j.id)){
      	         				map.put(j.id,j.name);
      	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
      	         			}
      	         			}) 
           	         		}
      	         			var cc = urlConfig["69"];
      	         			gxlxname = gxlx["69"].name;
             	         		if(cc){
             	         		$.each(cc,function(r,t){
          	         			if(!map.containsKey(t.id)){
          	         				map.put(t.id,t.name);
          	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+t.id+'\')" >'+t.name+'</span></p>';
          	         			}
          	         			}) 
             	         		}
             	         		
             	         		settings.content = contenta;
            		    }
        			
        		  break;
        		case "31"://姐夫
        			 if(!$('span.role-jief').hasClass("role-checked")){
        				 $gxlxse =$('span.role-jief');
            		    	var map = new Map();
           		    	 	var contenta = "";
           		    	 	var gg = urlConfig[props];
           		    	 	if(gg){
           		    	 		$.each(gg,function(h,j){
                     	         		if(!map.containsKey(j.id)){
                 	         				map.put(j.id,j.name);               	         			
                 	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
                 	         			}
                   	         		})
           		    	 	}         	                 	         		
           	         		var bb = urlConfig["30"];
           	         		gxlxname = gxlx["30"].name;
           	         		if(bb){
             	         		$.each(bb,function(h,j){
      	         			if(!map.containsKey(j.id)){
      	         				map.put(j.id,j.name);
      	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
      	         			}
      	         			}) 
           	         		}
      	         			var cc = urlConfig["69"];
      	         			gxlxname = gxlx["69"].name;
             	         		if(cc){
             	         		$.each(cc,function(r,t){
          	         			if(!map.containsKey(t.id)){
          	         				map.put(t.id,t.name);
          	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+t.id+'\')" >'+t.name+'</span></p>';
          	         			}
          	         			}) 
             	         		}
             	         		
             	         		settings.content = contenta;
            		    }
        			
        			
        		  break;
        		case "69"://姐妹夫
        			 if(!$('span.role-jief').hasClass("role-checked")){
        				 $gxlxse =$('span.role-jief');
            		    	var map = new Map();
           		    	 	var contenta = "";
           		    	 	var gg = urlConfig[props];
           		    	 	if(gg){
           		    	 		$.each(gg,function(h,j){
                     	         		if(!map.containsKey(j.id)){
                 	         				map.put(j.id,j.name);               	         			
                 	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
                 	         			}
                   	         		})
           		    	 	}         	                 	         		
           	         		var bb = urlConfig["30"];
           	         		gxlxname = gxlx["30"].name;
           	         		if(bb){
             	         		$.each(bb,function(h,j){
      	         			if(!map.containsKey(j.id)){
      	         				map.put(j.id,j.name);
      	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+j.id+'\')" >'+j.name+'</span></p>';
      	         			}
      	         			}) 
           	         		}
      	         			var cc = urlConfig["31"];
      	         			gxlxname = gxlx["31"].name;
             	         		if(cc){
             	         		$.each(cc,function(r,t){
          	         			if(!map.containsKey(t.id)){
          	         				map.put(t.id,t.name);
          	         				contenta += '<p class="tips"><b>'+gxlxname+'</b><br/><span style="color:#0F6FCF;cursor: pointer;" onclick="detailrk(\''+t.id+'\')" >'+t.name+'</span></p>';
          	         			}
          	         			}) 
             	         		}
             	         		
             	         		settings.content = contenta;
            		    }
        			
        			
        		  break;
        		  
        		case "50"://嫂嫂弟媳
        			$gxlxse =$('span.role-ss');
        			
        		  break;
        		case "52"://婶婶伯母
        			$gxlxse =$('span.role-bm');
        			
        		  break;
        		case "53"://堂兄弟
        			$gxlxse =$('span.role-txd');
        			
        		  break;
        		case "54"://堂姐妹
        			$gxlxse =$('span.role-tjm');
        			
        		  break;
        		case "56"://姑丈
        			$gxlxse =$('span.role-gz');
        			
        		  break;
        		case "57"://姑妈表兄弟
        			$gxlxse =$('span.role-bxdA');
        			
        		  break;
        		case "58"://姑妈表姐妹
        			$gxlxse =$('span.role-bjmA');
        			
        		  break;
        		case "60"://舅妈
        			$gxlxse =$('span.role-jm');
        			
        		  break;
        		case "61"://舅舅表兄弟
        			$gxlxse =$('span.role-bxdB');
        			
        		  break;
        		  
        		case "62"://舅舅表姐妹
        			$gxlxse =$('span.role-bjmB');
        			
        		  break;
        		case "64"://姨丈
        			$gxlxse =$('span.role-yz');
        			
        		  break;
        		case "65"://姨妈表兄弟
        			$gxlxse =$('span.role-bxdC');
        			
        		  break;
        		case "66"://姨妈表姐妹
        			$gxlxse =$('span.role-bjmC');
        			
        		  break;
        		default:
        		  break;
        		}
         		if($gxlxse != null){
         			$gxlxse.addClass("role-checked");
             		$gxlxse.webuiPopover('destroy').webuiPopover(settings);
         		}
         		
        	} 
        }
        initPopover();
       
      })();
    </script>
  </body>
