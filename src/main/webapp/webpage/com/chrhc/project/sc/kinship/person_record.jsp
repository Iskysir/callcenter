<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>人员履历</title>
	<link href="plug-in/personrecord/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/personrecord/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/personrecord/css/style.css" rel="stylesheet" type="text/css"/>
  <link href="plug-in/personrecord/css/style-add.css" rel="stylesheet" type="text/css"/>
  <link href="plug-in/personrecord/css/5icool.org.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/personrecord/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="plug-in/personrecord/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>
  <link rel="stylesheet" type="text/css" href="plug-in/personrecord/jquery.easyui/themes/gray/easyui.css">
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body class="page_body">
<section id="mainbox"> 
	<div class="Current_position">
      <img src="plug-in/personrecord/image/dingwei.png"/><span>当前所在的位置：</span>
      <a href="javascript:;">人口信息</a>
      <a href="javascript:;" class="last">人员履历</a>
    
  	</div>
  	<div class="rk_tab_div">
    <table>
          <tr>
            <th colspan ="7"><span id="xmlv"></span>人口履历 <img class="lay_i" src="plug-in/personrecord/image/lay_i.png"/></th>
          </tr>
          <tr class="even_tr">
            <td class="w_100">姓名</td>
            <td id="xm">&nbsp;</td>
            <td class="w_100">性别</td>
            <td id="xb">&nbsp;</td>
            <td class="w_100">民族</td>
            <td id="mz">&nbsp;</td>
            <td rowspan="6" style="background-color:#FFF; width:140px;" id="grzp"></td>
          </tr>
          <tr>
            <td>曾用名</td>
            <td id="cym">&nbsp;</td>
            <td>出生年月</td>
            <td id="csrq">&nbsp;</td>
            <td>政治面貌</td>
            <td id="zzlb">&nbsp;</td>
          </tr>
          <tr  class="even_tr">
            <td>籍贯</td>
            <td colspan="3" id="hjdz">&nbsp;</td>
            <td>最高学历</td>
            <td id="whcd">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2">工作单位、单位性质</td>
            <td colspan="4" id="gzdw">&nbsp;</td>
          </tr>
          <tr  class="even_tr">
            <td colspan="2">身份证号码</td>
            <td colspan="2" id="sfzh">&nbsp;</td>
            <td>健康状况</td>
            <td id="jkzk">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2">家庭住址</td>
            <td colspan="2" id="xjzdz">&nbsp;</td>
            <td>工作类别</td>
            <td id="gzlb">&nbsp;</td>
          </tr>
          <tr  class="even_tr">
            <td colspan="2">所属家庭</td>
            <td colspan="2" id="ssjt">&nbsp;</td>
            <td>与户主关系</td>
            <td colspan="2" id="yhzgx">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2">联系方式</td>
            <td colspan="2" id="lxdh">&nbsp;</td>
            <td>婚姻状况</td>
            <td colspan="2" id="hyzk">&nbsp;</td>
           </tr>
         
          <tr  class="even_tr">
            <td colspan="7" class="shrink_td" op='hid_td_4'><i class="fa  fa-angle-double-down"></i>重要事件记录</td>
          </tr>
          <tr>
            <td colspan="7" class="hid_td_4" style="padding:0px;">
            <table style="width:100%; margin:0px; border:none;">
	            <c:forEach  var="listpr" items="${listpr}" varStatus="status">
		            <tr style="border-bottom:1px solid #97d0fd; padding:5px 10px;">
			            <td style="border:none; text-align:left; border-right:1px solid #97d0fd; width:120px;">${listpr.createTime }</td>
			            <td style="border:none; text-align:left;">${listpr.recordName }</td>
		            </tr>
	            </c:forEach>
            </table>
            </td>
          </tr>
         
	</table>
    
        <div class="history" id="demo10" style="display:none;">
        <c:forEach items="${prmap}" var="entry" varStatus="status">
        	
        	<div class="history-date">
        		 <ul>   
			<c:choose>
	  		 <c:when test="${status.first }">
	  			 <h2 class="first">
	  		 </c:when>
	 		<c:otherwise>
	 			<h2 class="date02">
	   		</c:otherwise>	   		
	  	    </c:choose>
	  	     	<a href="#nogo">${entry.key}年</a>
	  	      	</h2>
	  	      	
	  	      		<c:forEach  var="personrecord" items="${entry.value}">
	  	      			<li class="green">
	  	      		 	<h3>${personrecord.month}月</h3>
                        <dl>
                            <dt><span>${personrecord.day}日 &nbsp;${personrecord.recordName }</span></dt>
                        </dl>
                        </li>
	  	      		</c:forEach>
	  	      	
       			</ul>
            </div>
		</c:forEach>
        </div>
	</div>
    </div>  	
  <a href="javascript:void(0);"class="dataprve" id="dataprve" style="display:none;"></a>
  <a href="javascript:void(0);"class="datanext" id ="datanext" style="display:none;"></a>
</section>
<!--mainbox end-->
	<!-- END FOOTER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<script src="plug-in/personrecord/js/jquery-1.10.1.min.js" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="plug-in/personrecord/js/bootstrap.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="plug-in/personrecord/artDialogDemo/js/artDialog.js?skin=idialog"></script>
    <script src="plug-in/personrecord/js/5icool.org.js" type="text/javascript"></script>
    
    <script src="plug-in/personrecord/js/style-add.js" type="text/javascript"></script>
	<!--[if lt IE 9]>
	<script src="media/js/excanvas.min.js"></script>
	<![endif]-->   
  <!--[if lte IE 9]>
  <script src="media/js/html5.js" type="text/javascript"></script>
  <script src="media/js/PIE.js" type="text/javascript"></script>
  <script src="media/js/pieuser_2.js" type="text/javascript"></script>
  <![endif]-->
	<script>
		 jQuery(document).ready(function() {
      
		 	// App.init();  
		
		 	 var urldata = getUrlData();
		 	 var id = urldata.id;
		 	 rkjbxxinit();
		 	 //getrkxxgrzp();
		 	/*var dataprveurl =  getParentWindow().nextRecordperson(id,'-1');
		 	var datanexturl =  getParentWindow().nextRecordperson(id,'1');
		 	if(dataprveurl){
		 		$(".dataprve").show();
		 	}
		 	if(datanexturl){
		 		$(".datanext").show();
		 	}*/
		 //处理人员履历基本信息
			 function rkjbxxinit(){
			
				 $.ajax({
					 url:"chrhcAutoListController.do?datagrid&configId=sc_rkjbxxnew&field=id,create_name,create_by,create_date,update_name,version_num,sys_org_code,update_by,update_date,obligatea,obligateb,obligatec,obligated,obligatee,del_date,gisxy,grzp,xm,sfzh,csrq,bm,xb,mz,sfzdz,xjzdz,hjdz,csd,qfjg,ksyxq,jsyxq,delflag,jtzz,hukouxinxi,sfhz,hkxz,yhzgx,ssjt,hjzk,cym,hyzk,whcd,zzlb,byzk,hksfzx,zy,zxrq,qitaxinxi,sg,xx,zjxy,jkzk,lxdh,yx,gzdw,gzlb,dwxz,zc,jmxxkh,tszc,jhr,jhgx,ysr,xqmc,lh,dyh,jg,kuozhanxinxi,sfjtcy,tzlb,qwlb,bzxx,ssjt_id,rysx,sfzdfwrq,sfzdwkrq,zyz,zdwfrq,zdwkrq,bxxx,dblb,wbh,kdh,cjzk,syry,sfsw,qytxry,hz_id,lnr,yf,ylfn,ylbx,ylbxa,sfxs,lz,zary,&dataRule=null&biztype=",
					    data:{'id':id},
						type:"Post",
					    dataType:"json",
					    async:false,
					    success:function(data){	
							if(data.rows.length){
								/* var aa = getCurrentTab();
								var bb = getCurrentTab().panel('options');
								var tab = getCurrentTab().panel('options').tab;
								aa.panel('setTitle','232323'); */
								
								var pr = data.rows[0]
								var xm = pr.xm;
								var xb = pr.xb;
								var mz = pr.mz;
								var cym = pr.cym;
								var csrq = pr.csrq;
								var zzlb = pr.zzlb;
								var hjdz = pr.hjdz;
								var whcd = pr.whcd;
								var sfzh = pr.sfzh;
								var xjzdz = pr.xjzdz;
								var lxdh = pr.lxdh;
								var gzdw = pr.gzdw;
								var dwxz = pr.dwxz;
								var ssjt = pr.ssjt;
								var yhzgx = pr.yhzgx;
								var jkzk = pr.jkzk;
								var gzlb = pr.gzlb;
								var hyzk = pr.hyzk;
								$("#xm").html(xm);
								$("#xmlv").html(xm);
								$("#xb").html(xb);
								$("#mz").html(mz);
								$("#cym").html(cym);
								$("#csrq").html(csrq);
								$("#zzlb").html(zzlb);
								$("#hjdz").html(hjdz);
								$("#whcd").html(whcd);
								$("#sfzh").html(sfzh);
								$("#xjzdz").html(xjzdz);
								$("#lxdh").html(lxdh);
								$("#gzdw").html(gzdw+"、"+dwxz);
								$("#ssjt").html(ssjt);
								$("#yhzgx").html(yhzgx);
								$("#jkzk").html(jkzk);
								$("#gzlb").html(gzlb);
								$("#hyzk").html(hyzk);
								getrkxxgrzp();
							}else{
								
								return false;
							}
							
					    },
						error:function(data){
							
							return false;
						}
					    });	 
			 }
			 function getrkxxgrzp(){
					if(!id){
						return;
					}
					$.ajax({
					    url:"scDocWarController.do?getrkxx",
					    data:{"id":id},
						type:"get",
					    dataType:"json",
					    async:false,
					    success:function(data){
					    	
					    	var path ="";
					    	if(data.success){
					    		//$("#imgdiv").remove();
					    		debugger;
					    		var src ="";
					    		var attrs = data.attributes;
					    		if(attrs){
					    			path = attrs.grzp;
					    		}
					    		
					    		if(path==null||path==''){
					    			var xbval = $("#xb").html();
					    			if("男" == xbval){
					    				src="plug-in/chrhc/images/nan.jpg";
					    			}else if("女" == xbval){
					    				src="plug-in/chrhc/images/nv.jpg";
					    			}else{
					    				src="plug-in/chrhc/images/docbackground.png";
					    			}
					    			
					    		}else{
					    			src = "commonController.do?viewFileftppath&ftppath="+path+"&subclassname=org.jeecgframework.web.system.pojo.base.TSAttachment";	
					    		}
					    		
								/*var divcontent = "<div  id='imgdiv'  style='float:right; margin-right:340px; border: 1px solid #A9C9E2;'><img src="+src+" style='cursor: pointer;' width='102px' height='126px'/></div>";
								
					    		$("#grzp").css("display","none");
					    			
								$("#grzp").after(divcontent);*/	
					    		
					    		addel(src);
								//alert("12");
					    		
					    	}	    	
					    				
					    },
						error:function(data){
							$.messager.alert('错误',data.msg);
							return false;
						}
					});
					
				}
			 /** 清空头像区域  添加显示图片*/
			 function addel(src){
			 	var divcontent="";
			 	if(src==null||src==''){
			 		src="plug-in/chrhc/images/docbackground.png";
			 	}
			 	if(src.indexOf('docbackground.png')>-1 || src.indexOf('nan.jpg')>-1 || src.indexOf('nv.jpg')>-1){
			 		divcontent = "<div id='uploadify_div_grzp' style='float:left'><img src="+src+" style='cursor: pointer;width:162px;height:196px;'alt='此人没有图像信息' title='此人没有图像信息'/></div>";
			 	}else{
			 		divcontent = "<div id='uploadify_div_grzp' style='float:left' ><img src="+src+" style='cursor: pointer;width:162px;height:196px;'alt='此人没有图像信息' title='图像信息'/></div>";
			 	}
			    //var filtype = $("#filetype");
			 	//var childs=$("#uploadify_div_grzp")[0];
			 	//filtype[0].removeChild(childs);		
			 	
			 	$("#grzp").html(divcontent);	
			 }
			 $("#dataprve").on("click",function(){
				var url =  getParentWindow().nextRecordperson(id,'-1');
			
				location.href = url;
			 })
			 $("#datanext").on("click",function(){
				 var url = getParentWindow().nextRecordperson(id,'1');
				 
				 location.href = url;
			 })
			 
		 });
	
  </script>
	<!-- END JAVASCRIPTS -->
</body>
