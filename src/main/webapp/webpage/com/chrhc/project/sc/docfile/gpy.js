
	 var Directory = "C:\\PHOTO\\";
     var AutoGrapPath = "C:\\currentDir";
     var click_timer=null;
     var FINALINDEXT=-1;
     
     

     //初始化 
     function gpyinit(){
    	 //$('#gaopaiyi').dialog({ id:'gaopaiyi'});
		//var fso = new ActiveXObject("Scripting.FileSystemObject");	
		//var f1 = fso.createTextFile("c://myjstest.txt",true);
		//var f2 = fso.GetFile("C:/currentDir/1/2.PNG");
		//f2.Delete();
    	 debugger;
        
    	try{ 
    	var int =window.top.VIDEOCAP.BuildVideo();
    	window.top.$("#VIDEOCAP").attr("width","350");
		window.top.$("#VIDEOCAP").attr("height","300");
		window.top.$("#gpyrow1").css("display","block");
		window.top.$("#gpyrow2").css("display","block");
		/*20150909 mengfx 高拍仪引起的滚动条问题整改*/
		window.top.$("#gaopaiyi").css("position","static");
    	//alert(int);
    	window.top.VIDEOCAP.ReSetUpDevice(0);
    	
    	window.top.VIDEOCAP.VideoFit();//视频合适
		 //VIDEOCAP.AddUsePreview();//预览功能
		 str_houzui = "jpg";
		 window.top.VIDEOCAP.SetLimitFileSize(1024);//初始化图片限制1024k之内
		 window.top.VIDEOCAP.SetTakeColorMode(2, 0);//彩色图片
	    if( gpy_link()){
	    	throw new error("请正确连接仪器！ ");	
	    }
    	}catch(e){
       	   //window.top.downloadSclient();
    	   //$.dialog({ id:'testinit', time:5, title:'提示',icon: 'warning',content:'请正确连接仪器！'});
    	   tipAlert('请正确连接仪器！','gpy');
       	   throw new error("请正确连接高拍仪！ ");
       	}
    }
		//主摄像头 
		function start_MainCampreview() {
			
			VIDEOCAP.ReSetUpDevice(0);
			 VIDEOCAP.VideoFit();//视频合适
			var num = VIDEOCAP.GetColorSpaceOutputsizeNum(0);

			
			//VIDEOCAP.AddUsePreview();//预览

		}
		 //副摄像头 
		 function start_SubCampreview() {
             VIDEOCAP.ReSetUpDevice(1);
             VIDEOCAP.VideoFit();//视频合适
             var num = VIDEOCAP.GetColorSpaceOutputsizeNum(0);

             /*var selindex = 0;
             var width = VIDEOCAP.GetMediaWidth();

             var height = VIDEOCAP.GetMediaHeight();

             for ( var i = 0; i < num; i++) {
                 var w = VIDEOCAP.GetOutPutSizeWidth(0, i);
                 var h = VIDEOCAP.GetOutPutSizeHeight(0, i);
                 var str = w.toString() + "X" + h.toString();
                 
                 if (w == width) selindex = i;
             }*/
            
         }
		/**拍照 i 是序列号  */
		  function Takepic(i) {
	           
                 var str_houzui ="jpg";
                // if(objSelect.options[selectedId].value) {
                //	 str_houzui=objSelect.options[selectedId].value ;
                // } else {str_houzui ="JPG";}
                 var date = new Date();
                 var p_year= date.getYear();
                 var p_month= date.getMonth()+1;
                 var p_day= date.getDate();
                 var p_hours= date.getHours();
                 var p_min= date.getMinutes();
                 var p_ss=date.getMilliseconds();
                 var p_date ="F"+i+"_doc"+p_year+p_month+p_day+p_hours+p_min+p_ss;
                 //alert(p_date);
	             var Path = Directory + p_date+"." + str_houzui;    //在光阵仪器中只能识别windows的文件分隔符/
				 var path_="C:/PHOTO/"+ p_date+"." + str_houzui;	// 在后台处理的是文件分隔符是 \  所以在上传时替换一下
	             
				 window.top.VIDEOCAP.SetCurrentPicPath(Directory);
				 window.top.VIDEOCAP.SetLimitFileSize(1024);//图片限制1024k之内
				 window.top.VIDEOCAP.SetJpgQuality(80);    //设置JPEG压缩比， 0~100，100为不压缩。
				 window.top.VIDEOCAP.SetPicDpi(400);    //设置DPI 
	             
				 window.top.VIDEOCAP.TakePicture(Path);
	             //加入到上传控件
	             //addphoto(path_); //cute 上传控件
	             addphotonew(path_);
	             //在画面显示图片缩略图
	             var img="<img alt='证明材料图' title=''  src='"+path_+"' width='160px' height='120px' />";// ondblclick=' tr_del(this)'
	             // debugger;
	             var indexfianl_ = scanImg();
	             if(indexfianl_){
	            	 $("#f_photo"+indexfianl_+" img").attr("src",path_);
	             }else{
	               $("#f_photo"+i).append(img);
	             }
	             }
		 
		//图片格式选项添加
		function addoption2(s) {
            var obj = document.getElementById("formatsel").options;
            var opt = new Option(s, s);
            obj.options.add(opt);//ie与firefox  兼容的方法
        }
		//图片类型（彩色、黑白）
		function addoption1(s) {
            var obj = document.getElementById("picsel").options;
           // alert(obj);
            var opt1 = new Option("彩色", "1");
           // var opt2 = new Option("黑白", "2");
            obj.options.add(opt1);
          //  obj.options.add(opt2);
        }
		//设置图片类型选中
	    function seloption1(index) {
             var objSelect = document.getElementById("picsel").options;
             objSelect.options[index].selected = true;
        }
	  //设置图片格式选中
	    function seloption2(index) {
            var objSelect = document.getElementById("formatsel").options;
            objSelect.options[index].selected = true;
        }
	  //视频输出大小（暂时不用）
	    function seloption(index) {
            var objSelect = document.getElementById("sel").options;
            objSelect.options[index].selected = true;
        }
	  //左旋
	    function RotateLeft(url) {
            VIDEOCAP.RotateLeft();

        }
		//右旋
        function RotateRight(url) {
            VIDEOCAP.RotateRight();
        }
		//缩小
        function ZoomIn() {

            VIDEOCAP.ZoomIn();
        }
		//放大
        function ZoomOut() {

            VIDEOCAP.ZoomOut();
        }
		//图片格式改变函数（暂时没用）
        function PicType() {
            var obj = document.getElementById("formatsel").options;
            var x = obj.selectedIndex;

        };
        // 增加文件大小标签
        function addoption3(s) {
            var obj = document.getElementById("limitfilesizeSel");
            var opt = new Option(s, obj.length);
            obj.options.add(opt);
        }
        //选中文件大小
        function seloption3(index) {
            var objSelect = document.getElementById("limitfilesizeSel");
            objSelect.options[index].selected = true;
        }
        
        //限制文件大小
        function limitfilesize(obj) {
        	 
            if (obj.checked) {
                VIDEOCAP.EnableLimitFileSize(1);
                var obj = document.getElementById("limitfilesizeSel");
                var x = obj.selectedIndex;

                if (x == 0) VIDEOCAP.SetLimitFileSize(200);
                if (x == 1) VIDEOCAP.SetLimitFileSize(300);
                if (x == 2) VIDEOCAP.SetLimitFileSize(500);
                if (x == 3) VIDEOCAP.SetLimitFileSize(600);

            }
            else {
                VIDEOCAP.EnableLimitFileSize(0);


            }
        }
        
        //读取身份证功能(主表中读取)
        function ReadIDCARDInfo_Z() {
        	
           var b = VIDEOCAP.ReadIdCard();
           if (b == 1) {
        	   var id_num =VIDEOCAP.Get_IDNumber();
        	   //身份证号重复
        	   var repeat=$("input[name!='idcardnum'][value='"+id_num+"']").attr("value");
        		 
        	   if(repeat&&repeat==id_num){
        			  $.dialog({ id:'test30', time:2, title:'提示',icon: 'warning',content:'身份证号码重复，请确认！'});

        		}
               
               //姓名
               //$("input[name='scFileList[#index#].name']").attr('value',VIDEOCAP.Get_IDName());
               $("input[name='name']").val(VIDEOCAP.Get_IDName());
               
          	   //性别
               var sex = VIDEOCAP.Get_Sex();
              
               
               // $("select[name='sex']").attr('value',xb(sex));
               // 遍历选中标签
               $("select[name='sex'] option").each(function(){
            	   if($(this).text() ==sex){
            	    $(this).attr("selected",true);
            	    return;
            	   }
            	  });
          	   //身份证号
               $("input[name='idcardnum']").attr('value',id_num);
               //出生日期
               $("input[name='birthday']").attr('value',VIDEOCAP.Get_BornYear()+"-"+VIDEOCAP.Get_BornMonth()+"-"+VIDEOCAP.Get_BournDay());
               //地址   scFileList[#index#].address
               $("input[name='address']").attr('value',VIDEOCAP.Get_Address());
               //民族      
              // $("select[name='nation']").attr('value',mz(VIDEOCAP.Get_Nation()));
              
               var mz_text= VIDEOCAP.Get_Nation();
               //$("select[name='nation'] option[label='"+VIDEOCAP.Get_Nation()+"族']").attr("selected",true);
               // 遍历选中标签
               $("select[name='nation'] option").each(function(){
            	   if($(this).text() == mz_text+"族"){
            	    $(this).attr("selected",true);
            	    return;
            	   }
            	  });

               //发证机关
               $("input[name='qfjg']").attr('value',VIDEOCAP.Get_Mechanics());
               
               var yxq =VIDEOCAP.Get_VaildDate();
               var midindex=yxq.indexOf("-");
               //-------------有效期处理
               var yxqstar=yxq.substring(0,midindex);
               var yxqstar_=yxqstar.substring(0,4)+"-"+yxqstar.substring(4,6)+"-"+yxqstar.substring(6);
               var yxqend=yxq.substring(midindex+1);
               var yxqend_=yxqend.substring(0,4)+"-"+yxqend.substring(4,6)+"-"+yxqend.substring(6);
               //-------------有效期处理
               $("input[name='yxqstar']").attr('value',yxqstar_);
               $("input[name='yxqend']").attr('value',yxqend_);
              if( comp2Nowtime(yxqend_)){};
               
               //VIDEOCAP.Save_Photo("c:\\IDCARD\\touxiang.jpg");  //保存身份证大头照
               var num_=VIDEOCAP.Get_IDNumber().substring(10);
               var cardname="Z"+num_+"card.jpg";
               var path="c:/IDCARD/";
               VIDEOCAP.SaveIdCard("c:/IDCARD/"+cardname);  //保存身份证大头照
               
             //首先删除上传控件中的图片然后添加
               addphoto_dbclick_( $("#z_photo").find("img"));
               
             //上传控件add
               //addphoto(path+cardname);
               addphotonew(path+cardname);
               var img="<img alt='证件图' title='双击预览' src='"+path+cardname+"' width='180px' height='160px'  />";//onclick='addphoto_click(this)' ondblclick='addphoto_dbclick(this)'
               $("#z_photo").append(img);
               //让身份证字段获得焦点  ---validform 失去焦点验证
               $("input[name='idcardnum']").focus();
               //window.alert(info+"ok");
               $("select[name='docType'] option[value='1']").attr("selected",true);
               
               //$("input").attr("disabled",true);
   			   //$("select").attr("disabled",true);
               //设置画面不可编辑
   			   $("input[name!='bz']").attr("disabled",true);
   			   $("select[name!=docType]").attr("disabled",true);
           }else{
        	   //alert("请放置身份证！");
        	   //$.dialog({ id:'a30', time:5, title:'提示',icon: 'warning',content:'请放置身份证！'});
             tipAlert('请放置身份证！');
           }

       }
        
        
        //读取身份证功能(附件中读取)
        function ReadIDCARDInfo_F(i) {
        	
           var info;
           var b = window.top.VIDEOCAP.ReadIdCard();
           if (b == 1) {
        	   //身份证号重复
        	   if(repeat(window.top.VIDEOCAP.Get_IDNumber())){
        		 
        		   //tr_del( $("input[name='scFileList["+i+"].name']"));
        		   return;
        	   }
               /*info = "身份证名称: " + VIDEOCAP.Get_IDName() + "  身份证号码: " + VIDEOCAP.Get_IDNumber();
               info += "\r\n";
               info += "性别 : " + VIDEOCAP.Get_Sex() + "  民族 : " + VIDEOCAP.Get_Nation();
               info += "\r\n";
               info += "住址 :" + VIDEOCAP.Get_Address();
               info += "\r\n";
               info += "出生 :" + VIDEOCAP.Get_BornYear() + "年" + VIDEOCAP.Get_BornMonth() + "月" + VIDEOCAP.Get_BournDay() + "日";
               info += "\r\n";
               info += "有效期限 :" + VIDEOCAP.Get_VaildDate();
               info += "\r\n";
               info += "签发机关 :" + VIDEOCAP.Get_Mechanics();*/
               //姓名
               //$("input[name='scFileList[#index#].name']").attr('value',VIDEOCAP.Get_IDName());
               $("input[name='scFileList["+i+"].name']").val(window.top.VIDEOCAP.Get_IDName());
               
          	   //性别
               var sex = window.top.VIDEOCAP.Get_Sex();
               
               $("input[name='scFileList["+i+"].sex']").attr('value',sex);
               //$("input[name='scFileList["+i+"].sex']").attr('value',VIDEOCAP.Get_Sex());
          	   //身份证号
               $("input[name='scFileList["+i+"].idcardNum']").attr('value',window.top.VIDEOCAP.Get_IDNumber());
               //出生日期
               $("input[name='scFileList["+i+"].birthday']").attr('value',window.top.VIDEOCAP.Get_BornYear()+"-"+window.top.VIDEOCAP.Get_BornMonth()+"-"+window.top.VIDEOCAP.Get_BournDay());
               //地址   scFileList[#index#].address
               $("input[name='scFileList["+i+"].address']").attr('value',window.top.VIDEOCAP.Get_Address());
               //民族      
               $("input[name='scFileList["+i+"].nation']").attr('value',window.top.VIDEOCAP.Get_Nation());
               //签发机关
               $("input[name='scFileList["+i+"].qfjg']").attr('value',window.top.VIDEOCAP.Get_Mechanics());
               var yxq =window.top.VIDEOCAP.Get_VaildDate();
               var midindex=yxq.indexOf("-");
               var yxqstar=yxq.substring(0,midindex);
               var yxqstar_=yxqstar.substring(0,4)+"-"+yxqstar.substring(4,6)+"-"+yxqstar.substring(6);
               var yxqend=yxq.substring(midindex+1);
               var yxqend_=yxqend.substring(0,4)+"-"+yxqend.substring(4,6)+"-"+yxqend.substring(6);
               
               $("input[name='scFileList["+i+"].yxqstar']").attr('value',yxqstar_);
               $("input[name='scFileList["+i+"].yxqend']").attr('value',yxqend_);
               //有效期的判断 提示
               if(comp2Nowtime(yxqend_)){};
               //VIDEOCAP.Save_Photo("c:/touxiang.jpg");  //保存身份证大头照
               // VIDEOCAP.SaveIdCard("c:\\idcard.jpg");  //保存身份证大头照
               var num_=window.top.VIDEOCAP.Get_IDNumber().substring(10);
               var cardname="F"+i+"_"+num_+"card.jpg";
               var path="c:/IDCARD/";
               window.top.VIDEOCAP.SaveIdCard("c:/IDCARD/"+cardname);  //保存身份证图片
               //上传控件add
               //addphoto(path+cardname); cut 上传
               addphotonew(path+cardname);
               //缩略图
               var img="<img alt='证件图'  title='' src='"+path+cardname+"' width='160' height='120'/>";// onMouseOut   ondblclick='tr_del(this)' onMouseOver='mouseover(this,event)' onMouseOut='mouseout(this)'
               var indexfianl_ = scanImg();
	           if(indexfianl_){
	            	 $("#f_photo"+i+" img").attr("src",path+cardname);
               }else{
            	   $("#f_photo"+i).append(img);
               }
               
              // window.alert(info+"ok");
           }else{
        	   tr_del( $("input[name='scFileList["+i+"].name']"));
        	   //cutOne(photoindext);
        	   //alert(" 请放置身份证！");
        	   //$.dialog({ id:'test30', time:5, title:'提示',icon: 'warning',content:'请放置身份证！'});
             tipAlert('请放置身份证！');
           }

       }
   //读取身份证判断方法     
   function ReadIDCARDInfo(flag){
	   
	   if(!flag&&flag!=0){
		   
		   ReadIDCARDInfo_Z();  
	   }else{
		   ReadIDCARDInfo_F(flag);
		   
	   }
	   
   } 
  
   /** 读取身份证头像 的方法 */
   //读取身份证功能(主表中读取)
   function ReadIDCARDInfoTx_Z() {
   	
	  
	   
      var b = VIDEOCAP.ReadIdCard();
      if (b == 1) {
   	   var id_num =VIDEOCAP.Get_IDNumber();
   	   //身份证号重复
   	   //var repeat=$("input[name!='idcardnum'][value='"+id_num+"']").attr("value");
   		 
   	   /*if(repeat&&repeat==id_num){
   			  $.dialog({ id:'test30', time:2, title:'提示',icon: 'warning',content:'身份证号码重复，请确认！'});

   		}*/
          
          //姓名
          //$("input[name='scFileList[#index#].name']").attr('value',VIDEOCAP.Get_IDName());
          $("input[name='name']").val(VIDEOCAP.Get_IDName());
          
     	   //性别
          var sex = VIDEOCAP.Get_Sex();
         
          
          // $("select[name='sex']").attr('value',xb(sex));
          // 遍历选中标签
          $("select[name='sex'] option").each(function(){
       	   if($(this).text() ==sex){
       	    $(this).attr("selected",true);
       	    return;
       	   }
       	  });
     	   //身份证号
          $("input[name='idcardnum']").attr('value',id_num);
          //出生日期
          $("input[name='birthday']").attr('value',VIDEOCAP.Get_BornYear()+"-"+VIDEOCAP.Get_BornMonth()+"-"+VIDEOCAP.Get_BournDay());
          //地址   scFileList[#index#].address
          $("input[name='address']").attr('value',VIDEOCAP.Get_Address());
          //民族      
         // $("select[name='nation']").attr('value',mz(VIDEOCAP.Get_Nation()));
         
          var mz_text= VIDEOCAP.Get_Nation();
          //$("select[name='nation'] option[label='"+VIDEOCAP.Get_Nation()+"族']").attr("selected",true);
          // 遍历选中标签
          $("select[name='nation'] option").each(function(){
       	   if($(this).text() == mz_text+"族"){
       	    $(this).attr("selected",true);
       	    return;
       	   }
       	  });

          //发证机关
          $("input[name='qfjg']").attr('value',VIDEOCAP.Get_Mechanics());
          
          var yxq =VIDEOCAP.Get_VaildDate();
          var midindex=yxq.indexOf("-");
          //-------------有效期处理
          var yxqstar=yxq.substring(0,midindex);
          var yxqstar_=yxqstar.substring(0,4)+"-"+yxqstar.substring(4,6)+"-"+yxqstar.substring(6);
          var yxqend=yxq.substring(midindex+1);
          var yxqend_=yxqend.substring(0,4)+"-"+yxqend.substring(4,6)+"-"+yxqend.substring(6);
          //-------------有效期处理
          $("input[name='yxqstar']").attr('value',yxqstar_);
          $("input[name='yxqend']").attr('value',yxqend_);
         if( comp2Nowtime(yxqend_)){};
          
          //VIDEOCAP.Save_Photo("c:\\IDCARD\\touxiang.jpg");  //保存身份证大头照
          var num_=VIDEOCAP.Get_IDNumber().substring(10);
          var cardname="Z2"+num_+"card.jpg";
          var path="c:/IDCARD/";
          VIDEOCAP.Save_Photo("c:/IDCARD/"+cardname);  //保存身份证大头照
          //上传控件add
          addphotonew(path+cardname);
        //保存身份证正反面
          var cardname_="F1"+num_+"card.jpg";
          VIDEOCAP.SaveIdCard("c:/IDCARD/"+cardname_);
          //清空附件表格 确定下面只能显示一张本人图片
          	$("#f_table").empty();
  			picpathall="";
  			addline(1);
          
          var img="<img alt='证明材料图' title=''  src='"+path+cardname_+"' width='160px' height='120px'/>";// ondblclick=' tr_del(this)'
          $("#f_photo"+1).append(img);
        //首先删除上传控件中的图片然后添加
          addphoto_dbclick_( $("#z_photo").find("img"));
          
        //上传控件add
          //addphoto(path+cardname);
          addphotonew(path+cardname_);
          var img="<img alt='证件图' title='双击预览' src='"+path+cardname+"' width='180px' height='160px'  />";//onclick='addphoto_click(this)' ondblclick='addphoto_dbclick(this)'
          $("#z_photo").append(img);
          //让身份证字段获得焦点  ---validform 失去焦点验证
          $("input[name='idcardnum']").focus();
          //window.alert(info+"ok");
          $("select[name='docType'] option[value='1']").attr("selected",true);
          
          //$("input").attr("disabled",true);
			   //$("select").attr("disabled",true);
          //设置画面不可编辑
			   $("input[name!='bz']").attr("disabled",true);
			   $("select[name!=docType]").attr("disabled",true);
      }else{
   	   //alert("请放置身份证！");
   	   //$.dialog({ id:'a30', time:5, title:'提示',icon: 'warning',content:'请放置身份证！'});
       tipAlert('请放置身份证！');
      }

  }
   
   
   
   
   
 //上传图片的添加
   function addphoto(path){
	   obj = document.getElementById("CuteCtrl1"); 
	   //alert(path); 
	   obj.AddFile(path);  
	   
   }
   
   
   
   // 添加文件 单击
   function addphoto_click(photo){
	   clearTimeout(click_timer);
	   click_timer =window.setTimeout(function(){
		   //alert(" danji"+photo);
		   var path=photo.getAttribute("src");
		   addphoto(path);  
	   },300);
   }
   //单击事件处理
   function addphoto_click_(photo){
	   //obj = document.getElementById("CuteCtrl1");cut 上传控件对象
	   var path=photo.getAttribute("src");
	   // alert(path); 
	   obj.AddFile(path);  
   }
        
   //  双击
   function addphoto_dbclick(photo){
	   //alert("双击");
	   clearTimeout(click_timer);
	   addphoto_dbclick_(photo);
   } 
   //双击事件处理
   function addphoto_dbclick_(photo){
	   //obj = document.getElementById("CuteCtrl1");cut 上传控件对象
	   //var path=photo.getAttribute("src");
	   var path=$(photo).attr("src");
	   //photo.parentNode.removeChild(photo);
	   $(photo).remove();
	   //alert(path); 
	   // cutlist(path);  cut中删除文件
	   delthotonew(path);
   } 
   //添加行处理
   /**index 参数 scFileList 下标 ； cardflag 是否是身份证（true 代表是，false：不是 ;finalpicFlag true:固定不能删除的，false ：不固定的可删除的）；  **/
   function  addline(index,cardflag,finalpicFlag){
//	  debugger;
	   var f_td_name =$("<span style='display:none;'></span>").html("<input name='scFileList["+index+"].name' maxlength='32' type='hidden'  style='width:120px;'/>");
	   var f_td_idcard =$("<span style='display:none;'></span>").html(" <input name='scFileList["+index+"].idcardNum' maxlength='32'  type='hidden'   style='width:120px;'/>");
	   var f_td_sex =$("<span style='display:none;'></span>").html(" <input name='scFileList["+index+"].sex' maxlength='32'  type='hidden'   style='width:120px;'/>");
	   var f_td_birthday =$("<span style='display:none;'></span>").html("<input name='scFileList["+index+"].birthday' maxlength='32'  type='hidden'   style='width:120px;'/>");
	   var f_td_nation =$("<span style='display:none;'></span>").html("<input name='scFileList["+index+"].nation' maxlength='32'  type='hidden'  style='width:120px;'/>");
	   var f_td_address =$("<span style='display:none;'></span>").html("<input name='scFileList["+index+"].address' maxlength='32'  type='hidden'  style='width:120px;'/>");
	   var f_td_yxqstar =$("<span style='display:none;'></span>").html("<input name='scFileList["+index+"].yxqstar' maxlength='32'  type='hidden'  style='width:120px;'/>");
	   var f_td_qfjg =$("<span style='display:none;'></span>").html("<input name='scFileList["+index+"].yxqend' maxlength='32'  type='hidden'  style='width:120px;'/>");
	   var f_td_yxqend =$("<span style='display:none;'></span>").html("<input name='scFileList["+index+"].qfjg' maxlength='32'  type='hidden'  style='width:120px;'/>");
	   var f_td_photo="";
	   if(finalpicFlag){
		   f_td_photo =$("<span id='f_photo"+index+"'></span>").html("<img  title=''  src='plug-in/chrhc/images/docbackground.png' width='160px' height='120px'/>  <input name='scFileList["+index+"].photo' maxlength='32' value='1' type='hidden'   style='width:120px;'/>  <input name='scFileList["+index+"].file'  type='hidden' value ='1' />    ");
	   }else{
		   
		   f_td_photo =$("<span id='f_photo"+index+"'></span>").html("<input name='scFileList["+index+"].photo' maxlength='32'  type='hidden'   style='width:120px;'/>");
	   }
	  // var f_td_del =$("<span style='display:none;'></span>").html("<input TYPE='button' VALUE='删除' onclick='tr_del(this)' />");//<input TYPE='button' VALUE='删除' onclick='tr_del(this)' />
	   var f_td_del=$("<span class='span-bg span-delete'> <i class='pic-icon pic-delete'></i></span>");
	   if(cardflag){
	   var f_td_info=$("<span class='span-bg span-info' > <i class='pic-icon pic-info'></i></span>");
	   }else {
		   f_td_info="";
	   }
	   var f_td_see=$("<span class='span-bg span-see'><i class='pic-icon pic-see'></i></span>");
	   //var f_td_id=$("<span></span>").html("<input name='scFileList["+index+"].id'  type='hidden'/>");
	  // var f_td_createName=$("<span></span>").html("<input name='scFileList["+index+"].createName'  type='hidden'/>");
	  // var f_td_createBy=$("<span></span>").html("<input name='scFileList["+index+"].createBy'  type='hidden'/>");
	  // var f_td_createDate=$("<span></span>").html("<input name='scFileList["+index+"].createDate'  type='hidden'/>");
	  // var f_td_updateName=$("<span></span>").html("<input name='scFileList["+index+"].updateName'  type='hidden'/>");
	  // var f_td_docForeignId=$("<span></span>").html("<input name='scFileList["+index+"].docForeignId'  type='hidden'/>");
	   //
	   var f_tr="";
	  if(finalpicFlag){
		  
		   f_tr =$("<div class='pic-box' finalflag ='true' ></div>").append(f_td_photo,f_td_del,f_td_see,f_td_info,f_td_name,f_td_idcard,f_td_sex,f_td_birthday,f_td_nation,f_td_address,f_td_yxqstar,f_td_yxqend,f_td_qfjg);//f_td_yxq,
	  }else{
		   f_tr =$("<div class='pic-box'></div>").append(f_td_photo,f_td_del,f_td_see,f_td_info,f_td_name,f_td_idcard,f_td_sex,f_td_birthday,f_td_nation,f_td_address,f_td_yxqstar,f_td_yxqend,f_td_qfjg);//f_td_yxq,  
	  }
	   
	   $("#f_table").append(f_tr);
	   
   }
   //删除行数据并且把控件图片删除
  function tr_del(but){
	  //var path=photo.getAttribute("src");
	 
	  var path =$(but).parents("div .pic-box").find('img').attr('src');
	  //从控件中删除
	  //cutlist(path);  //cut 从控件中删除图片
	  delthotonew(path);
	  //but.parentNode.parentNode.parentNode.removeChild( but.parentNode.parentNode);
	  //alert(typeof $(but).parents("div.pic-box").attr("finalflag")+1);
	  //alert($(but).parents("div.pic-box").attr("finalflag")=="true");
	  
	  //	  if(photoindext> FINALINDEXT){
//	  debugger;
	  if($(but).parents("div.pic-box").attr("finalflag")=="true"){
		  $(but).parents("div .pic-box").find('img').attr('src',"plug-in/chrhc/images/docbackground.png");
		 
	   }else{
		   $(but).parents("div.pic-box").remove();
	   }
	  cutOne();
	 // alert("删除成功");
	  
  } 
//调用后台 删除行数据并且把控件图片删除
  function del(fileid,dataid,but){
	  //alert("del");
	  var host = window.location.host;
	  var pathname=window.location.pathname;
	  var a = pathname.split('/');//.join(',');
      //var url=url_+"scDocWarController.do?delphoto&fileid="+fileid+"&dataid="+dataid;
		 var url="http://"+host+"/"+a[1]+"/scDocWarController.do?delphoto&fileid="+fileid+"&dataid="+dataid;
      //alert(url);
		
      $.get(url,function(data){
      	var d=$.parseJSON(data);
      	if(d.success){
      		//alert("ok");
      		//删除行
      		//debugger;
      		if($(but).parents("div.pic-box").attr("finalflag")=="true"){
      			 $(but).parents("div.pic-box").find('img').attr('src',"plug-in/chrhc/images/docbackground.png");
      			$(but).parents("div.pic-box").find("input[name$='].id']").val("");  //[id^='f_photo']
      			$(but).parents("div.pic-box").find("input[name$='].photo']").val("1");  //[id^='f_photo']
      			 
      	   }else{
      		 $(but).parents("div .pic-box").remove();
      	   }
      		cutOne();
      	}
  	 });  
   }
  //本页面判断身份证重复   参数是判断重复的值( 重复返回 true否则返回false)
  function repeat(val){
	  
	  var repeat=$("input[value='"+val+"']").attr("value");
	  
	  if(repeat&&repeat==val){
		  $.dialog({ id:'test30', time:2, title:'提示',icon: 'warning',content:'身份证号码重复，请确认！'});

	  }
    return false;	  
  } 
  
  //鼠标悬停时间
 function  mouseover(img,e){
	var picbox=  $(img).parents("div .pic-box");//.find('img').attr('src');
	
	var x=e.clientX	;
	var y=e.clientY;
	var div_xt=$("#xtxs");
	
	//	设置参数值
	var xt_name =picbox.find("input[name$='.name']").val();
	if(!xt_name||xt_name==''){
		return ;
	}
	var xt_sex =picbox.find("input[name$='.sex']").val();
	var xt_birthday = picbox.find("input[name$='.birthday']").val();
	var xt_cardnum = picbox.find("input[name$='.idcardNum']").val();
	var xt_nation = picbox.find("input[name$='.nation']").val();
	var xt_address =picbox.find("input[name$='.address']").val();//address
	var xt_yxqstar =picbox.find("input[name$='.yxqstar']").val();//yxqstar
	var xt_yxqend =picbox.find("input[name$='.yxqend']").val();
	var xt_qfjg =picbox.find("input[name$='.qfjg']").val();
	
	//if(div_xt){
	// 创建 显示框
	var xtdiv = $("<div id='xtxs'style='height:100% , width:100%' ></div>");//display:none,style='height:300px , width:200px'align="center"
	var xttable = $("<table cellspacing='1' border=1 height='100%' width='100%'></table>");
	var xttr1 = $("<tr height='40px'><td >姓名：</td> <td id='name' align='center'>"+xt_name+" </td> <td>证件号码：</td> <td id='cardnum'  align='center'> "+xt_cardnum+"</td></tr>");
	var xttr2 = $("<tr height='40px'><td>性别：</td> <td id='sex' align='center'>"+xt_sex+"</td> <td>出生日期：</td> <td id='birthday' align='center'>"+xt_birthday+"</td></tr>");
	var xttr3 = $("<tr height='40px'><td>民族：</td> <td id='nation'  align='center'>"+xt_nation+"</td> <td>住址：</td> <td id='address'  align='center'>"+xt_address+"</td></tr>");
	var xttr4 = $("<tr height='40px'> <td>签发机关：</td> <td id='qfjg'  align='center'>"+xt_qfjg+"</td> <td>有效期：</td> <td id='yxqstar'  align='center'>"+xt_yxqstar+"~"+xt_yxqend+"</td></tr>");
	xttable.append(xttr1,xttr2,xttr3,xttr4);
	xtdiv.append(xttable);
	//$("body").append(xtdiv);
	//}
	
	//设置位置
	div_xt=$("#xtxs");
	//div_xt.css("position","fixed");// position:fixed;
	//div_xt.css("left",x);// position:fixed;
	//div_xt.css("top",y);// position:fixed;
	//div_xt.css("border","1px solid red");// border:1px solid red;
	//$("#xtxs").css("border","1px solid red");// border:1px solid red;
	div_xt.find("#name").text(xt_name);
	div_xt.find("#sex").text(xt_sex);
	div_xt.find("#birthday").text(xt_birthday);
	div_xt.find("#cardnum").text(xt_cardnum);
	div_xt.find("#nation").text(xt_nation);
	div_xt.find("#address").text(xt_address);
	div_xt.find("#yxqstar").text(xt_yxqstar+"-"+xt_yxqend);
	div_xt.find("#qfjg").text(xt_qfjg);
	$("#xtxs").show();
	return xtdiv;
  }
 
 //鼠标离开时的事件
  function mouseout(img){
	  $("#xtxs").remove();  
  }
  //向上传控件增加 图片的path ---上传
  function addphotonew(path){
	 var index_path = picpathall.indexOf(path);
	 if(index_path<0){
		 picpathall= picpathall+path+",";
	 }
	  
  }
//从上传控件删除 图片的path ---取消上传
  function delthotonew(path){
	 var pathstr =picpathall.split(",");
	 var picpathall_='';
	 for(var i=0;i<pathstr.length;i++){
		 if(pathstr[i]!=path&&""!=pathstr[i]){
			 picpathall_=picpathall_+ pathstr[i]+",";
		 }
	 }
	 //picpathall =picpathall_.substring(0,picpathall_.length-1);
	 picpathall =picpathall_;
  }
  
//增加验证(非身份证类型)
  function adddatatype(){
	  //姓名
	  $("#name").attr("datatype","s1-20");
	  //性别
	  $("#sex").attr("datatype","s1-6");
	 
	  //证件号码
	  $("#idcardnum").attr("datatype","s1-20");
	  //证件号码查重验证  validType="sc_doc_war,idcardnum,id"
	 // $("#idcardnum").attr("validType","sc_doc_war,idcardnum,id");
	  //  住址
	  $("#address").attr("datatype","s1-50");
	  //  民族
	  //$("#nation").attr("datatype","s1-20"); //ignore="ignore"
	  //签发机关
	 // $("#qfjg").attr("datatype","s1-20");
	  //证件有效期开始  class="form-control w260" class="Wdate"
	 // $("#yxqstar").attr("class","form-control w260 Wdate");
	  //证件有效期结束
	 // $("#yxqend").attr("class","form-control w260 Wdate");
	  //出生日期
	  //$("#birthday").attr("class","form-control w260 Wdate");
	  
  }
  
  /** 民族的字典处理*/
  function  mz(val){
	  if(val=="汉"){
		  return "01";
	  }else if(val=="壮"){
		  return "";
	  }else if(val=="满"){
		  return "";
	  }else if(val=="回"){
		  return "";
	  }else if(val=="苗"){
		  return "";
	  }else if(val=="维吾尔"){
		  return "";
	  }else if(val=="土家"){
		  return "";
	  }else if(val=="彝"){
		  return "";
	  }else if(val=="蒙古"){
		  return "";
	  }else if(val=="藏"){
		  return "";
	  }else if(val=="布依"){
		  return "";
	  }else if(val=="侗"){
		  return "";
	  }else if(val=="瑶"){
		  return "";
	  }else if(val=="朝鲜"){
		  return "";
	  }else if(val=="白"){
		  return "";
	  }else if(val=="哈尼"){
		  return "";
	  }else if(val=="哈萨克"){
		  return "";
	  }else if(val=="黎"){
		  return "";
	  }else if(val=="傣"){
		  return "";
	  }else if(val=="畬"){
		  return "";
	  }else if(val=="傈僳"){
		  return "";
	  }else if(val=="仡佬"){
		  return "";
	  }else if(val=="东乡"){
		  return "";
	  }else if(val=="高山"){
		  return "";
	  }else if(val=="拉枯"){
		  return "";
	  }else if(val=="睡"){
		  return "";
	  }else if(val=="佤"){
		  return "";
	  }else if(val=="纳西"){
		  return "";
	  }else if(val=="羌"){
		  return "";
	  }else if(val=="土"){
		  return "";
	  }else if(val=="仫佬"){
		  return "";
	  }else if(val=="锡伯"){
		  return "";
	  }else if(val=="柯尔克孜"){
		  return "";
	  }else if(val=="达斡尔"){
		  return "";
	  }else if(val=="景颇"){
		  return "";
	  }else if(val=="毛南"){
		  return "";
	  }else if(val=="撒拉"){
		  return "";
	  }else if(val=="布朗"){
		  return "";
	  }else if(val=="塔吉克"){
		  return "";
	  }else if(val=="阿昌"){
		  return "";
	  }else if(val=="普米"){
		  return "";
	  }else if(val=="鄂温克"){
		  return "";
	  }else if(val=="怒"){
		  return "";
	  }else if(val=="京"){
		  return "";
	  }else if(val=="基诺克"){
		  return "";
	  }else if(val=="德昂族"){
		  return "";
	  }else if(val=="保安"){
		  return "";
	  }else if(val=="俄罗斯"){
		  return "";
	  }else if(val=="裕固"){
		  return "";
	  }else if(val=="乌孜别克"){
		  return "";
	  }else if(val=="门巴"){
		  return "";
	  }else if(val=="鄂伦春"){
		  return "";
	  }else if(val=="独龙"){
		  return "";
	  }else if(val=="塔塔尔"){
		  return "";
	  }else if(val=="赫哲"){
		  return "";
	  }else if(val=="珞巴"){
		  return "";
	  }else {
		  return "99";
	  }
  }
  /**性别处理*/
  function  xb(val){
	 if(val=='男'){
		 return 1;
	 } else if(val=='女'){
		 return 2;
	 }else{
		 return 3;
	 }
	  
  }
  
  /** 更新初始化 文档类型区别 函数*/
  function  wdlx(){
	  //var val=$("select[name='docType'] option[selected='selected']").val(); 不支持
	  var val =$("select[name='docType']").find("option:selected").val();
		//身份证类型
		if(val=='1'){
			//alert($("input").attr("readonly"));
			$("input").attr("disabled",true);
			$("select").attr("disabled",true);
			//$("input[name='bz']").attr("readonly",false);
			$("select[name=docType]").attr("disabled",false);
		//其他类型
		}else{
			$("input").attr("disabled",false);
			$("select").attr("disabled",false);
			//$('input').removeAttr("readonly");//去除input元素的readonly属性
			//添加验证
			adddatatype();
		}
		
		$("input[name='bz']").attr("disabled",false);
  }
  /** 提交后再把原本不可编辑的字段设置disabled（解决在身份证类型下验证不通过时 提交字段设置为可编辑的的bug ） */
  function  aftersub(){
	  //var val=$("select[name='docType'] option[selected='selected']").text();  //MLGB 突然不支持这种没写法了
	  var val =$("select[name='docType']").find("option:selected").val();
		//身份证类型
		if(val=='1'){
			//alert($("input").attr("readonly"));
			$("input").attr("disabled",true);
			$("select").attr("disabled",true);
			//$("input[name='bz']").attr("readonly",false);
			$("select[name=docType]").attr("disabled",false);
		//其他类型
		}else{
			$("input").attr("disabled",false);
			$("select").attr("disabled",false);
			//$('input').removeAttr("readonly");//去除input元素的readonly属性

		}
		
		$("input[name='bz']").attr("disabled",false);
  }
  
  /** 更新初始化  函数*/
  /** 新增初始化 的函数*/
  
  /*** 统一集成身份证读卡器  新中新盒式优先 高拍仪次之*/
  /**
   * 读取身份证的通用调用方法
   * 1、把身份证存放位置C:/IDCARD/目录下
   * 2、参数：js对象格式  Object{photopath（身份证存放路径）：xxx}  例：c:/idcard/
   * 3 、返回值  Object{name:"inputID",sex:"inputID",birthday:"xxx",cardNum:"XXX",nation:"xxxx",address:"xxx",validDateSatar:"XXX"validDateEnd:xxxx,mechanism:xxxx}
   */
  function readIDcard_General(parameter){
  	if(!parameter){
  	 var parameter=new Object();
  	 parameter.photopath= "C:/IDCARD/";
  	}else if(parameter.photopath){
  		parameter.photopath= "C:/IDCARD/";
  	}
  	//新通读卡
  	
  		var readcard =idcardRead_xzx(parameter);
  		if(readcard){
  			return readcard;
  			
  		}
  	
  	
  	//高拍仪读卡
  	readcard =ReadIDCARDInfo_gpy(parameter);
  	if(readcard){
  		return readcard;
  	}
  }

  /**通过读取身份证硬件设备的获取身份信息  parameter.photopath  图片保存路径 例：c:/idcard/ */
  function idcardRead_xzx(parameter){
  	var syncardocx=$("#SynCardOcx1");
  	if(!syncardocx){
  		var obj =  document.createElement('object'); 
  		obj.setAttribute("classid","clsid:46E4B248-8A41-45C5-B896-738ED44C1587"); 
  		obj.setAttribute("id","SynCardOcx1");
  		obj.setAttribute("codeBase","SynCardOcx.CAB#version=1,0,0,1"); 
  		obj.setAttribute("width",0); 
  		obj.setAttribute("height",0); 
  		document.body.appendChild(obj);
  	}
  	try{

  	 var nRet;
  	 SynCardOcx1.SetPhotoType(4);
  	 var findReader_str = SynCardOcx1.FindReader();
  	 if (findReader_str < 0){
  		return false;
  	 }
  	 SynCardOcx1.SetPhotoPath(2,parameter.photopath);;//设置照片保存路径?????????图片名称怎么确定
  	 nRet = SynCardOcx1.ReadCardMsg();
  	if(nRet==0)	{
  		var obj =	new Object();
  		///姓名
  		obj.name= SynCardOcx1.NameA;
  		//性别
  		obj.sex= SynCardOcx1.Sex;//
  		//出生日期
  		obj.birthday= SynCardOcx1.Born;//
  		//身份证号
  		obj.cardNum= SynCardOcx1.CardNo;//
  		//民族
  		obj.nation= SynCardOcx1.Nation;//
  		//	住址
  		obj.address=  SynCardOcx1.Address;
  		//有效期
  		obj.validDateSatar=SynCardOcx1.UserLifeB;// 身份证有效期开始  
  		obj.validDateEnd=SynCardOcx1.UserLifeE;// 身份证有效期结束
  		//签发机关
  		obj.mechanism= SynCardOcx1.Police;// 签发机关
  		
  		return obj;	
  	
  	}
  	else if(nRet==65){
  		//alert("请确认已放置身份证，并且所放位置正确");	
  		$.dialog({ id:'test30', time:5, title:'提示',icon: 'warning',content:'请确认已放置身份证，并且所放位置正确！'});
  		return new Object();
  	}
  	else if(nRet==1)	{
  		return false;
  	}
  	}catch(e){
  		$("#SynCardOcx1").remove();
  		return false;
  	}
  }


  /**高拍仪的读卡器 通用方法    photopath  图片保存路径 例：c:/idcard/ */
  function ReadIDCARDInfo_gpy(parameter) {
  	var gpy_readcard=$("#VIDEOCAP");
  	if(!gpy_readcard){
  	var obj =  document.createElement('object'); 
  	obj.setAttribute("classid","clsid:6CA705D0-BB6E-46DF-BE44-64809B0B0E36"); 
  	obj.setAttribute("id","VIDEOCAP");
  	obj.setAttribute("codeBase","*.cab#version=0,0,0,0"); 
  	obj.setAttribute("width",0); 
  	obj.setAttribute("height",0); 
  	document.body.appendChild(obj); 
  	}
      var info;
      try{
      var b = VIDEOCAP.ReadIdCard();
      if (b == 1) {
      	var obj =	new Object();
  		///姓名
  		obj.name= VIDEOCAP.Get_IDName();
  		//性别
  		//obj.sex= VIDEOCAP.Get_Sex();
  		var sexcode = dictionary('',VIDEOCAP.Get_Sex(),'sex');
  		obj.sex=sexcode;
  		//出生日期
  		obj.birthday=VIDEOCAP.Get_BornYear()+"-"+VIDEOCAP.Get_BornMonth()+"-"+VIDEOCAP.Get_BournDay();//
  		//身份证号
  		obj.cardNum= VIDEOCAP.Get_IDNumber();//
  		//民族
  		//obj.nation= VIDEOCAP.Get_Nation();//
  		var nationcode = dictionary('', VIDEOCAP.Get_Nation(),'mz');//字典处理
  		obj.nation=nationcode;
  		//	住址
  		obj.address=  VIDEOCAP.Get_Address();
  		var yxq = VIDEOCAP.Get_VaildDate();
  		if(yxq){
  			 var	midindex= yxq.indexOf('-');
  			 var yxqstar=yxq.substring(0,midindex);
             var yxqstar_=yxqstar.substring(0,4)+"-"+yxqstar.substring(4,6)+"-"+yxqstar.substring(6);
             var yxqend=yxq.substring(midindex+1);
             var yxqend_=yxqend.substring(0,4)+"-"+yxqend.substring(4,6)+"-"+yxqend.substring(6);
  			//有效期
  			obj.validDateSatar=yxq.substring(0,yxqstar_);
  			obj.validDateEnd=yxq.substring(yxqend_);
  		}
  		//签发机关
  		obj.mechanism=VIDEOCAP.Get_Mechanics();
  		 var num_=VIDEOCAP.Get_IDNumber().substring(10);
           var cardname="F"+i+"_"+num_+"card.jpg";
           VIDEOCAP.SaveIdCard(parameter.photoPath+cardname);  //保存身份证图片
  		return obj;
     
      }else{
      	//alert("请在有效区域放置有效身份证！");
      	$.dialog({ id:'test30', time:2, title:'提示',icon: 'warning',content:'请在有效区域放置有效身份证！'});
      	return false;
      }
      }catch(e){
      	$("#VIDEOCAP").remove();
  		return false;
      }
  }
  /** 系统字典时 tableclass 值:''不是系统字典时 值：字典table的实体类的全名;
   * 
   * */
  function dictionary(tableclass,text,groupcode){
	  
	  try{
		  var host = window.location.host;
			 var pathname=window.location.pathname;
			 var a = pathname.split('/');//.join(',');
			 var url ="http://"+host+"/"+a[1]+"/scDocWarController.do?getajaxcode&tableClass="+groupcode+"&groupcode="+groupcode+"&data="+text;
			 url= encodeURI(url);
			 
		  $.get(url,function(data){
	         return data;
	 	 }); 
		  
	  }catch(e){
		  return "";
	  }
	  
	  
  }
  
	  //将结束日期的起始日期设置为有效开始日期
	function stardate(input){
	//alert(	$(input).val());
	var attval= "WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd',minDate:'"+$(input).val()+"'})";
	//var attval= "WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd',minDate:'2019-08-01'})";
	//alert(attval);
	$("#yxqend").attr('onClick',""+attval+"");
	//alert($("#yxqend").attr('onClick'));
	
	}
	//将有效开始日期的最大日期设置为有效结束日期
	function enddate(input){
	//alert(	$(input).val());
	var attval= "WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd',maxDate:'"+$(input).val()+"'})";
	$("#yxqstar").attr('onClick',attval);
	
	}

	/** 通用的时间前后设置 第一个参数是时间控件对象，第二个参数 是 结束时间标签 的id*/	
	function starDate(input,endid){
		//alert($(input).val());
	var attval= "WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd',minDate:'"+$(input).val()+"'})";
	//alert($("#"+endid).attr('Onchange'));
	$("#"+endid).attr('onClick',""+attval+"");
	//alert($("#createDate_end").attr('onClick'));
	
	}
	/** 通用的时间前后设置 第一个参数是本时间控件对象， 第二个参数是 开始时间控件的id*/	
	function endDate(input,starid){
	var attval= "WdatePicker({onpicked:function(){(this).blur();},dateFmt:'yyyy-MM-dd',maxDate:'"+$(input).val()+"'})";
	$("#"+starid).attr('onClick',attval);
	
	}	
	
	/** 时间 与当前时间的比较 大于当前时间返回 true  否则 false*/
	function comp2Nowtime(date1){
		
		date1 = date1.replace(/\-/gi,"/");
		
		var time1 = new Date(date1).getTime();
		var time2 = new Date().getTime();
		if(time1 <= time2){
			$.dialog({ id:'test31', time:3, title:'提示',icon: 'warning',content:'身份证已过期！'});
			return true;
		}else{
			
			return false;
		}
	}
	
	/** 时间 与当前时间的比较 大于当前时间返回 true  否则 false*/
	function comp2Nowtime_sub(date1){
		
		date1 = date1.replace(/\-/gi,"/");
		
		var time1 = new Date(date1).getTime();
		var time2 = new Date().getTime();
		if(time1 <= time2){
			//$.dialog({ id:'test31', time:3, title:'提示',icon: 'warning',content:'身份证已过期！'});
			return true;
		}else{
			
			return false;
		}
	}
	
/** 通用的ajax调用  (有必须在回调函数执行完之后执行要求的函数 不适合)*/	
	function ajax_general(url){
		var re= false;
		 $.get(url,function(data){
			// debugger;
			  re=data;
	         return re;
	 	 }); 	
		
	}
/** 初始化 判断控件是否安装 */	
	function pageinit(){
		
		try{
			
			var upobj = document.getElementById("uploadFile1");
			
		    upobj.showMsg="0";
		    
		    debugger;
		    var ax = new ActiveXObject("UPLOADFILE.uploadFileCtrl.1"); 
		    if(!ax){
		    	  throw new error("没有装载控件！ ");
		    }
		   // alert("控件初始化");
		   // throw new error("没有装载控件！ ");
		}catch(e){
			
      	    window.top.downloadSclient();
      	  throw new error("没有装载控件！ ");
		}
	}
	
/**  判断是否连接仪器*/	
function gpy_link(){
	 var w = window.top.VIDEOCAP.GetOutPutSizeWidth(0, 0);
	 if(w<1){
		 //$.dialog({ id:'testinit', time:3, title:'提示',icon: 'warning',content:'请正确连接仪器！'});
		 return true;
	 }
}	

/** 进入电子资料画面 初始化时显示几张附件图片   args ： 图片类型code url：字典url */
function attachmentInit(tablecode){
//	getphototype
	 //var arrValue="";
	 var label ="";
	 var host = window.location.host;
	 var pathname=window.location.pathname;
	 var a = pathname.split('/');
	 var url_ ="http://"+host+"/"+a[1]+"/scDocWarController.do?getphototype&tablecode="+tablecode;
	 url_= encodeURI(url_);
	 if(tablecode!=""){
	 $.ajax({type: "GET",
		  url:url_,
		  async:false,
		  success: function(data){
			  	label = data;
			  	/*if(label.){
			  		
			  	}*/
			  	label =label.replace(/(^\"*)/g, "");
			  	label =	label.replace(/(\"*$)/g, "");
			  	//arrValue=data;
			  
		  },
		  error:function(XMLHttpRequest, textStatus, errorThrown){
	  
		  }
	 }); 
	 }
	var arr = label.split(",");
	
	for(var item in arr){
		item = parseInt(item);
		if(""==arr[item]){
			continue;
		}
		 //身份证类型code 是“01” 
		 if(arr[item].indexOf("身份证")!=-1){
			 addline(item+1,true,true);
		 }else{
			 addline(item+1,false,true);
		 }
		 photoindext =item+2;
		 FINALINDEXT =item+1;
		 $("#f_photo"+FINALINDEXT+" img").attr("alt",arr[item]);
		 $("#f_photo"+FINALINDEXT+" img").after("<span id='' class='pic-address'>"+arr[item]+"</span>");
		 
		 $("input[name='scFileList["+FINALINDEXT+"].file']").attr('value',arr[item]);
		
		
	}
	return label;
	//var arrval = arrValue.split(",");
	
	
	
}
/** list下角标  减一(删除时使用)*/
function cutOne(){
	if(photoindext>(FINALINDEXT+1)){
		photoindext =photoindext-1;	
	}
	
}
/** 扫描 图片区域 包含 final 属性的标签 判断是否包含有效图片 */
function  scanImg(){
	var re=false;
	
	$("div.pic-box[finalflag='true'] img").each(function(indext,el){
		//alert($(el).val());
		//alert(parseInt($(el).parent("[id^='f_photo']").attr("id")));
		//alert($(el).parent("[id^='f_photo']").attr("id"));
//		debugger;
		if($(el).attr("src").indexOf('docbackground.png')>-1){
			var indext_id=   $(el).parent("[id^='f_photo']").attr("id");
			var reg = new RegExp("\\D+(\\d+)","gi") ;
			
			 if(reg.test(indext_id)){
				  re= RegExp.$1;
				  //alert(RegExp.$1);
				 return false;
				} 
		}
		
	  });
	return re;
}
/** 将 id 与字典返回调用画面*/
function reparameter(id,str){
	debugger;
	var win= getParentWindow();
	
	var formedit = win.$("#editForm");
	if(formedit.length){
		var docparent = win.$("#editForm")[0].contentWindow;
		docparent.pushdoctype(str,id);
		}else{
			win.pushdoctype(str,id);
		}
	
	
	
}
/** 弹出artdialog title:标题  ；content：展示内容；id：唯一标示 h:高，w：宽;t:距离顶部距离：l：距离左边距离*/
function art_dialog(title,content ,id,h,w,t,l){
	if(!id){
		id="EA695L";
	}
	if(!h){
		h=500;//"35%";
	}
	if(!w){
		w=650;//"45%";
	}
	if(!t){
		t=56;
	}
	if(!l){
		l=10;
	}
	//alert(1);
	window.top.art.dialog({title:title,
	    content: content, //'<img src='+path+' width="100%" height="100%" />',
	    id: id,
	    height:h,
	    width:w,
	    top:t,
	    left:l,
	   // padding:'70px 15px 20px 15px', 
	    zIndex:9999,
	    resize:true
	});
	
	
}
/**返回  图片个数  暂时没用*/
function getPhotouindext(){
	return  photoindext;
}
/** 将高拍仪最小化*/
function deploygpy(){
	window.top.$("#VIDEOCAP").attr("width","1");
	window.top.$("#VIDEOCAP").attr("height","1");
	window.top.$("#gpyrow1").css("display","none");
	window.top.$("#gpyrow2").css("display","none");
	
}
/**关闭dialog*/
function closeartdialog(){
	window.top.$("#gaopaiyi").css({"position":"absolute","top":"0px","left":"0px"});
	window.top.art.dialog({id:"EF893L"}).close();
}
/**证照验真 拍照**/
function  zzyzTakepic(){
	$("#f_table").empty();
	picpathall="";
	addline(1);
	
	$("#f_table").find(".span-info").remove();
	Takepic(1);
	//photoindext = photoindext + 1;
}
/**弹出高拍仪窗口 */
function gpyartdialog(){
	window.top.art.dialog({title:"高拍仪",
		left: '100%',
		top:96,
	    content:  window.top.$("#gaopaiyi")[0],
	    id: 'EF893L',
	    height:350,
	    width:400,
	    resize:true,
	    esc:false,
	    init: function () {
	      // $(".aui_close").hide();
	    },
	    cancel:false,
	    close:function(){
			deploygpy();
			return true;
		}
	}); 
	
	
}
/**上传图片前进行check*/
function uploadcheck(){
	var upflag =scanImg();
	if(upflag){
		//$.dialog({ id:'test31', time:30, title:'提示',icon: 'error',content:'存在未录入的资料!',ok:"ok1",Cancel:function() {alert("cancel");}});	
		 $.dialog({ id:'test30',  title:'提示',icon: 'warning',content:'存在未录入的资料!',ok:function() {uploadcut(); },cancel:function() {;}});
		// alert("dd");
	}else{
		uploadcut();
	}	
	
	
}
