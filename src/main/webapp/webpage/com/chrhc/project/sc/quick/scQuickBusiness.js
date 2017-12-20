
	 var Directory = "C:\\PHOTO\\";
     var AutoGrapPath = "C:\\currentDir";
     var click_timer=null;
    
     //初始化 
     function gpyinit(){
    	 //$('#gaopaiyi').dialog({ id:'gaopaiyi'});
		//var fso = new ActiveXObject("Scripting.FileSystemObject");	
		//var f1 = fso.createTextFile("c://myjstest.txt",true);
		//var f2 = fso.GetFile("C:/currentDir/1/2.PNG");
		//f2.Delete();
    	
    	try{ 
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
    	 //art.dialog({ id:'testinit', time:5, title:'提示',icon: 'warning',content:'请正确连接仪器！'});
    	 tipAlert('请正确连接仪器！','gpy');
       	 throw new error("请正确连接高拍仪！ ");
       	}
    }
		//主摄像头 
		function start_MainCampreview() {
			
			window.top.VIDEOCAP.ReSetUpDevice(0);
			 window.top.VIDEOCAP.VideoFit();//视频合适
			var num = window.top.VIDEOCAP.GetColorSpaceOutputsizeNum(0);

			
			//VIDEOCAP.AddUsePreview();//预览

		}
		 //副摄像头 
		 function start_SubCampreview() {
             window.top.VIDEOCAP.ReSetUpDevice(1);
             window.top.VIDEOCAP.VideoFit();//视频合适
             var num = window.top.VIDEOCAP.GetColorSpaceOutputsizeNum(0);

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
		//拍照 i 是序列号
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
	             var img="<img alt='证明材料图' title=''  src='"+path_+"' width='160px' height='120px'/>";// ondblclick=' tr_del(this)'
	               $("#f_photo"+i).append(img);
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
            window.top.VIDEOCAP.RotateLeft();

        }
		//右旋
        function RotateRight(url) {
            window.top.VIDEOCAP.RotateRight();
        }
		//缩小
        function ZoomIn() {

            window.top.VIDEOCAP.ZoomIn();
        }
		//放大
        function ZoomOut() {

            window.top.VIDEOCAP.ZoomOut();
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
                window.top.VIDEOCAP.EnableLimitFileSize(1);
                var obj = document.getElementById("limitfilesizeSel");
                var x = obj.selectedIndex;

                if (x == 0) window.top.VIDEOCAP.SetLimitFileSize(200);
                if (x == 1) window.top.VIDEOCAP.SetLimitFileSize(300);
                if (x == 2) window.top.VIDEOCAP.SetLimitFileSize(500);
                if (x == 3) window.top.VIDEOCAP.SetLimitFileSize(600);

            }
            else {
                window.top.VIDEOCAP.EnableLimitFileSize(0);


            }
        }
   /** 读取身份证头像 的方法 */
   //读取身份证功能(主表中读取)
   function ReadIDCARDInfoTx_Z() {
	var b = window.top.VIDEOCAP.ReadIdCard();
	if (b == 1) {
		var id_num = window.top.VIDEOCAP.Get_IDNumber();
		//alert(id_num);
		// 姓名
		$("input[name='name']").val(window.top.VIDEOCAP.Get_IDName());
		// 性别
		var sex = window.top.VIDEOCAP.Get_Sex();
		$("select[name='sex'] option").each(function() {
			if ($(this).text() == sex) {
				$(this).attr("selected", true);
				return;
			}
		});
		// 身份证号
		$("input[name='idcardnum']").attr('value', id_num);
		// 出生日期
		$("input[name='birthday']").attr(
				'value',
				window.top.VIDEOCAP.Get_BornYear() + "-" + window.top.VIDEOCAP.Get_BornMonth() + "-"
						+ window.top.VIDEOCAP.Get_BournDay());
		// 地址 scFileList[#index#].address
		$("input[name='address']").attr('value', window.top.VIDEOCAP.Get_Address());
		// 民族
		var mz_text = window.top.VIDEOCAP.Get_Nation();
		// 遍历选中标签
		$("select[name='nation'] option").each(function() {
			if ($(this).text() == mz_text + "族") {
				$(this).attr("selected", true);
				return;
			}
		});

		// 发证机关
		$("input[name='qfjg']").attr('value', window.top.VIDEOCAP.Get_Mechanics());

		var yxq = window.top.VIDEOCAP.Get_VaildDate();
		var midindex = yxq.indexOf("-");
		// -------------有效期处理
		var yxqstar = yxq.substring(0, midindex);
		var yxqstar_ = yxqstar.substring(0, 4) + "-" + yxqstar.substring(4, 6)
				+ "-" + yxqstar.substring(6);
		var yxqend = yxq.substring(midindex + 1);
		var yxqend_ = yxqend.substring(0, 4) + "-" + yxqend.substring(4, 6)
				+ "-" + yxqend.substring(6);
		// -------------有效期处理
		$("input[name='yxqstar']").attr('value', yxqstar_);
		$("input[name='yxqend']").attr('value', yxqend_);
		if (comp2Nowtime(yxqend_)) {
		};

		// VIDEOCAP.Save_Photo("c:\\IDCARD\\touxiang.jpg"); //保存身份证大头照
		var num_ = window.top.VIDEOCAP.Get_IDNumber().substring(10);
		var cardname = "F1_" + num_ + "headcard.jpg";
		var path = "c:/IDCARD/";
		window.top.VIDEOCAP.Save_Photo("c:/IDCARD/" + cardname); // 保存身份证大头照

		//保存身份证正反面
		var cardname_ = "Z1" + num_ + "card.jpg";
		window.top.VIDEOCAP.SaveIdCard("c:/IDCARD/" + cardname_);
		
		// 清空附件表格 确定下面只能显示一张本人图片
		$("#f_table").empty();
		picpathall = "";
		addline(1);

		// 上传控件add
		// addphoto(path+cardname);
		addphotonew(path + cardname_);// 正反面
		// 上传控件add
		addphotonew(path + cardname);// 大头照

		// var img="<img alt='证明材料图' title='' src='"+path+cardname_+"'
		// width='160px' height='120px'/>";// ondblclick=' tr_del(this)'
		var img = "<img alt='身份证大头照' title=''  src='" + path + cardname
				+ "' width='160px' height='120px'/>";
		$("#f_photo" + 1).append(img);

		// 首先删除上传控件中的图片然后添加
		addphoto_dbclick_($("#z_photo").find("img"));

		// var img="<img alt='证件图' title='双击预览' src='"+path+cardname+"'
		// width='180px' height='160px' />";//onclick='addphoto_click(this)'
		// ondblclick='addphoto_dbclick(this)'
		var img = "<img alt='身份证反正面' title='双击预览' src='" + path + cardname_
				+ "' width='400px' height='252px'  />";
		//$("#z_photo").append(img);
		// onclick='addphoto_click(this)'
		// ondblclick='addphoto_dbclick(this)'
		//$(".idpic_face").append(img);
		//修改显示图片，显示身份证反正面
		$(".idpic_face").css("background-image", "url('"+path + cardname_+"')");		
		$(".idpic_face_reverse").css("background-image", "url('"+path + cardname_+"')");
		// 让身份证字段获得焦点 ---validform 失去焦点验证
		//$("input[name='idcardnum']").focus();
		// window.alert(info+"ok");
		//$("select[name='docType'] option[value='1']").attr("selected", true);

		// $("input").attr("disabled",true);
		// $("select").attr("disabled",true);
		// 设置画面不可编辑
		//$("input[name!='bz']").attr("disabled", true);
		//$("select[name!=docType]").attr("disabled", true);

		
		//停止定时任务，定时扫描身份证任务
		if(donghua01){
			clearInterval(donghua01);
		}
		if(donghua02){
			clearInterval(donghua02);
		}		
		// 快捷业务
		this.doQuick(id_num);

	} else {
		// alert("请放置身份证！");
		/*art.dialog({
			id : 'a30',
			time : 5,
			title : '提示',
			icon : 'warning',
			content : '请放置身份证！'
		});*/
	}
}
 // 上传图片的添加
   function addphoto(path){
	   obj = document.getElementById("CuteCtrl1"); 
	   // alert(path);
	   obj.AddFile(path);  
	   
   }
   
   
   
   // 添加文件 单击
   function addphoto_click(photo){
	   clearTimeout(click_timer);
	   click_timer =window.setTimeout(function(){
		   // alert(" danji"+photo);
		   var path=photo.getAttribute("src");
		   addphoto(path);  
	   },300);
   }
   // 单击事件处理
   function addphoto_click_(photo){
	   // obj = document.getElementById("CuteCtrl1");cut 上传控件对象
	   var path=photo.getAttribute("src");
	   // alert(path);
	   obj.AddFile(path);  
   }
        
   // 双击
   function addphoto_dbclick(photo){
	   // alert("双击");
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
   function  addline(index,cardflag){
	   //alert("add"+index);
	   var f_td_name =$("<span style='display:none;'></span>").html("姓名<input name='scFileList["+index+"].name' maxlength='32' type='hidden'  style='width:120px;'/>");
	   var f_td_idcard =$("<span style='display:none;'></span>").html(" 身份证号 <input name='scFileList["+index+"].idcardNum' maxlength='32'  type='hidden'   style='width:120px;'/>");
	   var f_td_sex =$("<span style='display:none;'></span>").html("性别 <input name='scFileList["+index+"].sex' maxlength='32'  type='hidden'   style='width:120px;'/>");
	   var f_td_birthday =$("<span style='display:none;'></span>").html("出生日期 <input name='scFileList["+index+"].birthday' maxlength='32'  type='hidden'   style='width:120px;'/>");
	   var f_td_nation =$("<span style='display:none;'></span>").html("民族 <input name='scFileList["+index+"].nation' maxlength='32'  type='hidden'  style='width:120px;'/>");
	   var f_td_address =$("<span style='display:none;'></span>").html("住址<input name='scFileList["+index+"].address' maxlength='32'  type='hidden'  style='width:120px;'/>");
	   var f_td_yxqstar =$("<span style='display:none;'></span>").html("身份证有效期<input name='scFileList["+index+"].yxqstar' maxlength='32'  type='hidden'  style='width:120px;'/>");
	   var f_td_qfjg =$("<span style='display:none;'></span>").html("身份证有效期<input name='scFileList["+index+"].yxqend' maxlength='32'  type='hidden'  style='width:120px;'/>");
	   var f_td_yxqend =$("<span style='display:none;'></span>").html("签发机关<input name='scFileList["+index+"].qfjg' maxlength='32'  type='hidden'  style='width:120px;'/>");
	   var f_td_photo =$("<span id='f_photo"+index+"'></span>").html("<input name='scFileList["+index+"].photo' maxlength='32'  type='hidden'   style='width:120px;'/>");
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
	  
	   var f_tr =$("<div class='pic-box'></div>").append(f_td_photo,f_td_del,f_td_see,f_td_info,f_td_name,f_td_idcard,f_td_sex,f_td_birthday,f_td_nation,f_td_address,f_td_yxqstar,f_td_yxqend,f_td_qfjg);//f_td_yxq,
	   
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
	  $(but).parents("div .pic-box").remove();
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
      		$(but).parents("div .pic-box").remove();	
      	}
  	 });  
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
  		art.dialog({ id:'test30', time:5, title:'提示',icon: 'warning',content:'请确认已放置身份证，并且所放位置正确！'});
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
      try{
      var b = window.top.VIDEOCAP.ReadIdCard();
      if (b == 1) {
      	var obj =	new Object();
  		///姓名
  		obj.name= window.top.VIDEOCAP.Get_IDName();
  		//性别
  		//obj.sex= window.top.VIDEOCAP.Get_Sex();
  		var sexcode = dictionary('',window.top.VIDEOCAP.Get_Sex(),'sex');
  		obj.sex=sexcode;
  		//出生日期
  		obj.birthday=window.top.VIDEOCAP.Get_BornYear()+"-"+window.top.VIDEOCAP.Get_BornMonth()+"-"+window.top.VIDEOCAP.Get_BournDay();//
  		//身份证号
  		obj.cardNum= window.top.VIDEOCAP.Get_IDNumber();//
  		//民族
  		//obj.nation= window.top.VIDEOCAP.Get_Nation();//
  		var nationcode = dictionary('', window.top.VIDEOCAP.Get_Nation(),'mz');//字典处理
  		obj.nation=nationcode;
  		//	住址
  		obj.address=  window.top.VIDEOCAP.Get_Address();
  		var yxq = window.top.VIDEOCAP.Get_VaildDate();
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
  		obj.mechanism=window.top.VIDEOCAP.Get_Mechanics();
  		 var num_=window.top.VIDEOCAP.Get_IDNumber().substring(10);
           var cardname="F"+i+"_"+num_+"card.jpg";
           window.top.VIDEOCAP.SaveIdCard(parameter.photoPath+cardname);  //保存身份证图片
  		return obj;
     
      }else{
      	//alert("请在有效区域放置有效身份证！");
      	art.dialog({ id:'test30', time:2, title:'提示',icon: 'warning',content:'请在有效区域放置有效身份证！'});
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
			art.dialog({ id:'test31', time:3, title:'提示',icon: 'warning',content:'身份证已过期！'});
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
		 $.get(url,function(data){
	         return data;
	 	 }); 	
		
	}
/** 初始化 判断控件是否安装 */	
	function pageinit(){
		try{
			var upobj = document.getElementById("uploadFile1");
		    upobj.showMsg="0";
		   // alert("控件初始化");
		}catch(e){
      	 window.top.downloadSclient();
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

//通用弹出式文件上传
function commonUpload(callback){
    art.dialog({
           content: "url:systemController.do?commonUpload",
           lock : true,
           title:"文件上传",
           zIndex:2100,
           width:700,
           height: 200,
           parent:windowapi,
           cache:false,
       ok: function(){
               var iframe = this.iframe.contentWindow;
               iframe.uploadCallback(callback);
                   return true;
       },
       cancelVal: '关闭',
       cancel: function(){
       } 
   });
}
function browseImages(inputId, Img) {// 图片管理器，可多个上传共用
		var finder = new CKFinder();
		finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
			$("#" + Img).attr("src", fileUrl);
			$("#" + inputId).attr("value", fileUrl);
		};
		finder.resourceType = 'Images';// 指定ckfinder只为图片进行管理
		finder.selectActionData = inputId; //接收地址的input ID
		finder.removePlugins = 'help';// 移除帮助(只有英文)
		finder.defaultLanguage = 'zh-cn';
		finder.popup();
	}
function browseFiles(inputId, file) {// 文件管理器，可多个上传共用
	var finder = new CKFinder();
	finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
		$("#" + file).attr("href", fileUrl);
		$("#" + inputId).attr("value", fileUrl);
		decode(fileUrl, file);
	};
	finder.resourceType = 'Files';// 指定ckfinder只为文件进行管理
	finder.selectActionData = inputId; //接收地址的input ID
	finder.removePlugins = 'help';// 移除帮助(只有英文)
	finder.defaultLanguage = 'zh-cn';
	finder.popup();
}
function decode(value, id) {//value传入值,id接受值
	var last = value.lastIndexOf("/");
	var filename = value.substring(last + 1, value.length);
	$("#" + id).text(decodeURIComponent(filename));
}

var fromFun;
var rk_id;
//快捷业务
function doQuick(id_num){
	var is = isExistInRkkxx(id_num);
	//alert("***is="+is);
	if(is){
		//控制页面转换，由扫描身份证页面变换到快捷业务功能显示页面
		shaomiaoToKj();
		//在人口库中
		$.ajax({
		    type: 'post',
		    url: 'scQuickBusinessController.do?checkInRkkxx',
		    data: {
		    	EQ_xm:$("#name").val(),
		    	EQ_sfzh:$("#idcardnum").val(),
		    	EQ_xb:$("select[name='sex']").val(),
		    	EQ_csrq:$("#birthday").val(),
		    	EQ_mz:$("select[name='nation']").val(),
		    	EQ_sfzdz:$("#address").val(),
		    	EQ_ksyxq:$("#yxqstar").val()+" 00:00:00",
		    	EQ_jsyxq:$("#yxqend").val()+" 00:00:00",	
		    	EQ_qfjg:$("#qfjg").val()
		    		    	
		    },
		    success: function(data) {
		    	var d = $.parseJSON(data);
				//is = d.success;
		    	//无论是否更新，身份证信息都合法，可以显示可办理模块信息
		    	getModelTypeBtn($("#idcardnum").val(),d.obj);
				//核实身份证信息是否与人口库中身份证信息一致		    	
				if(d.success){
					//信息一致
					//显示业务按钮
					//getModelTypeBtn($("#idcardnum").val(),d.obj);
				}else{
					//信息不一致 
					//alert("信息不一致=="+d.success+"==="+d.msg);
					//不用显示高拍仪拍照 add fangxu-wang 20150803
					//$("#gaopaiyi").hide();
					//填充身份证信息
					$("#new_name").val($("#name").val());
			    	$("#new_idcardnum").val($("#idcardnum").val());
			    	$("select[id='new_sex']").val($("select[name='sex']").val());
			    	$("#new_birthday").val($("#birthday").val());
			    	$("select[id='new_nation']").val($("select[name='nation']").val());
			    	$("#new_address").val($("#address").val());
			    	$("#new_yxqstar").val($("#yxqstar").val());
			    	$("#new_yxqend").val($("#yxqend").val());
			    	$("#new_qfjg").val($("#qfjg").val());
					//填充人口库信息
			    	$("#rk_name").val(d.attributes.xm);
			    	$("#rk_idcardnum").val(d.attributes.sfzh);
			    	$("select[id='rk_sex']").val(d.attributes.xb);
			    	if(d.attributes.csrq){
				    	$("#rk_birthday").val(d.attributes.csrq.substr(0,10));
			    	}
			    	$("select[id='rk_nation']").val(d.attributes.mz);
			    	$("#rk_address").val(d.attributes.sfzdz);
			    	if(d.attributes.ksyxq){
			    		$("#rk_yxqstar").val(d.attributes.ksyxq.substr(0,10));
			    	}
			    	if(d.attributes.jsyxq){
				    	$("#rk_yxqend").val(d.attributes.jsyxq.substr(0,10));
			    	}
			    	$("#rk_qfjg").val(d.attributes.qfjg);			    	
			    	
					$("input[id^='new_']").removeAttr("disabled");
					$("select[id^='new_']").removeAttr("disabled");
					$("input[id^='rk_']").removeAttr("disabled");
					$("select[id^='rk_']").removeAttr("disabled");
					//$("input[id^='new_']").attr("readonly","readonly");
					//$("select[id^='new_']").attr("readonly","readonly");
					var keyStr = d.attributes.keyStr;
					var keys= new Array();   
					keys = keyStr.split(",");
					//alert("***keyStr="+keyStr);
					if(keys && keys.length > 0){
						for(var i=0;i<keys.length;i++){
							switch(keys[i]){
								case "xm" : changeFont("name","color: red;");break;
							    case "sfzh":changeFont("idcardnum","color: red;");break;
							    case "xb" : changeFont("sex","color: red;");break;
							    case "csrq" : changeFont("birthday","color: red;");break;
							    case "mz" : changeFont("nation","color: red;");break;
							    case "sfzdz" : changeFont("address","color: red;");break;
							    case "ksyxq" : changeFont("yxqstar","color: red;");break;
							    case "jsyxq" : changeFont("yxqend","color: red;");break;
							    case "qfjg" : changeFont("qfjg","color: red;");break;
							}
						}
					}
					
					art.dialog(
						 	{
							 	content: document.getElementById("compareInfoDiv"),
							 	title: d.msg+",请确认是否更新人口库信息!",
							 	icon: 'warning',
							 	lock : true,
							 	//left :'50%',
							 	//top :'45%',
			 				    resizable: false,
			 				    modal: true,
						 	    okVal: '是',
						 	    ok:function() {updateCertInfo();close();},
						 	    cancelVal: '否',
						        cancel: function() { }
						 	
					 	});					
				}				
		    }
		});
	
	}else{
		//不在人口库中
		window.top.art.dialog(
			 	{
				 	content: '身份证信息不在人口基本信息库，是否录入',
				 	title: '信息录入确认',
				 	icon: 'warning',
				 	lock : true,
				 	width :320,
				 	height :100,
				 	left :'50%',
				 	top :'45%',
 				    resizable: false,
 				    modal: true,
			 	    okVal: '是',
			 	    ok:function() {inputCertInfo();close();},
			 	    cancelVal: '否',
			        cancel: function() { //$(".kj_list").remove();
			        	return true;
			        }
			 	
		 });
 	}
	debugger;
	/**   检查是否存在待办业务 star   **/
		//id_num="372925198711141319";
		checkdbyw(id_num);
	/**   检查是否存在待办业务 end   **/	
}
//通过身份证号，判断人口库是否存在该身份证
function isExistInRkkxx(id_num){
	var is = true;
	$.ajax({
		url: 'scQuickBusinessController.do?isExistInRkkxx',
		type : 'POST',
		dataType : 'text',
		data : {
			certId : id_num,
			configId : 'sc_rkjbxxnew'
		},
		async : false,
		cache : false,
		success: function(data){
			var d = $.parseJSON(data);
			is = d.success; 
			if(is){
				rk_id = d.obj.id;
				//alert(rk_id);
			}
		}
	});
	
	return is;
}


//录入身份证信息，先录入电子文档，在录入人口库信息
function inputCertInfo(){
	fromFun = "inputCertInfo";
	//$('#docsub').trigger("click");//提交按钮触发，保存电子文档信息
	uploadcut();
}
//当身份证信息与人口库信息不一致时，更新人口库信息操作
function updateCertInfo(){
	fromFun = "updateCertInfo";
	uploadcut();
}
//改变指定字段的字体颜色
function changeFont(id,color){
	$("label[for='new_"+id+"']").attr("style",color);
	$("label[for='rk_"+id+"']").attr("style",color);
	
	$("input[id='new_"+id+"']").attr("style",color);
	$("input[id='rk_"+id+"']").attr("style",color);
}


/**
 * 根据人口库信息id，获得该用户可办理的业务
 * @param cert_id 身份证号
 * @param rkId 人口库id
 */
function getModelTypeBtn(cert_id,rkId){
	//控制页面转换，由扫描身份证页面变换到快捷业务功能显示页面
	shaomiaoToKj();
	
	//$("#businessBtnDiv").removeAttr("style");
	if(rkId){
		rk_id = rkId;
	}
	$.ajax({
		url: 'scQuickBusinessController.do?getModelTypeBtn',
		type : 'POST',
		dataType : 'text',
		data : {
			EQ_cert_id:cert_id
		},
		async : false,
		cache : false,
		success: function(data){
			//alert(data);
			var d = $.parseJSON(data);
			var jsonObj =eval("("+d.obj +")"); 
			if(d.success){
				//alert(d.obj);
				var btnHtml='';
				$(".kj_list").empty();
				
				for(var i=0;i<jsonObj.length;i++){	
					
					btnHtml =btnHtml+"<div class=\"kj_item\"><div id='"+jsonObj[i].key+"' name='"+jsonObj[i].value+"' class=\"kj_icon\"><img src=\""+jsonObj[i].iconPath+"\" onerror=\"this.src='plug-in/accordion/images/05默认图标_bizshow.png'\"/></div><div class=\"kj_title\">"+jsonObj[i].value+"</div></div>";
					
					//btnHtml =btnHtml+"<div class=\"kj_item\"><div id='"+jsonObj[i].key+"' name='"+jsonObj[i].value+"' class=\"kj_icon "+kj+"\"></div><div class=\"kj_title\">"+jsonObj[i].value+"</div></div>";
					$(document).on('click', '#'+jsonObj[i].key, function () {		
						showBusinessBtnByModleType(cert_id,this.id,this.name);						  
					}); 
					//btnHtml =btnHtml+ "<button type=\"button\" class=\"btn btn-sure\" id='modleType_"+jsonObj[i].key+"' onclick=\"showModleTypeBtn('"+jsonObj[i].key+"','"+jsonObj[i].value+"');\">"+jsonObj[i].value+"</button>";
				}
				//alert(btnHtml);
				
				$(".kj_list").append(btnHtml);
				mouseOverAndOut();//需要重新绑定图片鼠标放上或者离开图片变化效果
			}else{
				//删除以后的模块信息，与$(".kj_list").empty();相同作用
				var objs = $(".kj_list").find(".kj_item");
				if(objs.length>0){
					$(".kj_list").find(".kj_item").remove();
				}
			}
		}	
	});
	
	
}
/**
 * 根据人口库信息id，获得该用户可办理的业务
 * @param modelType 业务模块类型
 */
function showModleTypeBtn(cert_id,modelType,modelTypeName){
	//alert("showModleTypeBtn");
	//var cert_id = $("#idcardnum").val();
	showBusinessBtnByModleType(cert_id,modelType,modelTypeName);
	
}
/**
 * 根据业务模块编码，获得sc_rk_yw_config获得该模块下的业务
 * @param cert_id 身份证号
 * @param modelType 业务模块编码
 */
function showBusinessBtnByModleType(cert_id,modelType,modelTypeName){
	//alert(cert_id+"****"+modelType);	
	$.ajax({
		url: 'scQuickBusinessController.do?showBusinessBtn',
		type : 'POST',
		dataType : 'text',
		data : {
			EQ_cert_id:cert_id,
			EQ_modelType:modelType
		},
		async : false,
		cache : false,
		success: function(data){
			var d = $.parseJSON(data);
			var jsonObj =eval("("+d.obj +")"); 
			if(d.success){
				var trVar='';
				//当只有一个业务按钮时，不再弹出对话框选择业务，直接导航到业务页面
				if(jsonObj.length == 1){
					var btn = jsonObj[0];
					goBusiness(btn.id,btn.value);
					
				}else if(jsonObj.length > 1){
					//当有多于一个业务按钮时，弹出对话框选择业务，选择业务按钮后导航到业务页面
					/*if($(".slt_content").find(".kj_subitem").length>0){
						//存在的按钮，删除
						$(".slt_content").find(".kj_subitem").remove();
					}*/
					$(".slt_content").empty();
					for(var i=0;i<jsonObj.length;i++){ 
						var btn = jsonObj[i];						
							//trVar = trVar+"<div class=\"kj_subitem\"><div id=\""+btn.id+"\" class=\"kj_subicon "+kjsub+"\"></div><div class=\"kj_subtitle\">"+btn.value+"</div> </div>"
							
							trVar = trVar+"<div class=\"kj_subitem\">"+
							"<div id=\""+btn.id+"\" name=\""+btn.value+"\" class=\"kj_subicon\">"+
								"<img src=\""+btn.ywtbpath+"\" onerror=\"this.src='plug-in/accordion/images/05默认图标_sub_bizshow.png'\"/></div>"+
							"<div class=\"kj_subtitle\">"+btn.value+"</div>"+
							"</div>";					
							$(document).on('click', '#'+btn.id, function () {	
							  if(this.id != ''){
								  var name=$("#"+this.id).attr("name");
									goBusiness(this.id,name);
								}
							});							
					}
					//alert(trVar);
					  $(".slt_content").append(trVar);
					  mouseOverAndOut();//需要重新绑定图片鼠标放上或者离开图片变化效果
					  showOverlay();
			          adjust("slt_box");
			          $(".slt_box").slideDown();
					//$("#showBtnTable").append("<div id='btnTr'>"+trVar+"</div>");					
					/*art.dialog({
						title:modelTypeName,
						width :230,
					 	height :400,
						content:document.getElementById("showBtnTable"),
						ok:function(){
							//art.dialog("已提交").time(1);
							if(tabKey != ''){
								goBusiness(tabKey);
							}else{
								close();
							}
						}				
					});*/
				} //jsonObj.length > 1 end
			}else{
				//tip(d.msg);
				art.dialog({
					id : 'noBusiness',
					time : 3,
					title : '提示',
					icon : 'warning',
					content : '该业务模块没有可办理业务！'
				});
			}
		}
	});
}
/**
 * 业务按钮跳转
 * @param key 业务模块编码
 * @param tabName 业务模块名称
 * @param url 业务模块的url
 */
function goBusiness(id,name){
	$.ajax({
		url: 'scQuickBusinessController.do?getScRkYwConfigById',
		type : 'POST',
		dataType : 'text',
		data : {
			EQ_id:id
		},
		async : false,
		cache : false,
		success: function(data){
			var d = $.parseJSON(data);
			var jsonObj =eval("("+d.obj +")"); 
			if(d.success){
				//alert(d.obj);
				//alert(jsonObj[0].id);
				var url = jsonObj[0].bizurl;
				if(url && "null"!= url && null != url){

				}else{
					var host = window.location.host;
					var pathname=window.location.pathname;
					var a = pathname.split('/');
					var key= jsonObj[0].businesscode;
					url ="http://"+host+"/"+a[1]+"/chrhcFormBuildController.do?ftlForm&tableName="+key+"&bizType=&dataRule=null&mark_name=true";		 					 
				}	
				var field = jsonObj[0].rkxx_field;
				//alert(rk_id);
				if(field && "null"!= field && null != field )
				 {
					url=url+"&"+field+"="+rk_id; 					
				 }else{
					 url=url+"&rk_id="+rk_id;
				 }
				url = url+"&scQuickTitle="+name;
				//addOneTab(jsonObj[0].businessdes,url,"");
				parent.addTab(jsonObj[0].businessdes,url);
			}
		}	
	});
}
//扫描页面扫描完身份证显示快捷业务页面的处理
function shaomiaoToKj(){
	//$(".flash").attr("style","display: none;");
	//$(".id").attr("style","display: none;");
	$(".flash").remove();
	$(".id").remove();
	$(".kj_box").attr("style","display: block;");
}
/**
 * 刷新当前页面
 */
function refreshQuick(){
	parent.addTab('快捷业务','scQuickBusinessController.do?goAdd');
}
/**检查是否存在未办理业务***/
function checkdbyw(idcard){
	//var idcard="372925198711141319";
	var flag=false;
	 var host = window.location.host;
	 var pathname=window.location.pathname;
	 var a = pathname.split('/');
	 //var url_ ="http://"+host+"/"+a[1]+"/scDocWarController.do?getphototype&tablecode="+tablecode;
	var url_="http://"+host+"/"+a[1]+"/scGzptlcController.do?checkwsbs&url=http://10.61.160.7:8280/cpub-back-web/api/app/netservice/declare/tasklist&pagesize&idcard="+idcard;
	 $.ajax({
	 type: "GET",
	 url:url_,
	 async:false,
	 success: function(data){
		 debugger;
		 if(data){
			 data =data.replace(/(^\"*)/g, "");
			 data =	data.replace(/(\"*$)/g, "");
			var ts= parseInt(data);
		if(!isNaN(ts)&&ts>0){
			flag=true;
		}else{
			flag=false;
		}
		//alert("发送失败");
		//return false;
		 }
		
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			//alert(123);
			  return false;
		}
	 });
	 if(flag){
		 
		 
			window.top.art.dialog(
				 	{
					 	content: '此人存在未办理预约任务是否办理',
					 	title: '信息录入确认',
					 	//icon: 'warning',
					 	lock : true,
					 	width :230,
					 	height :30,
					 	left :'50%',
					 	top :'45%',
	 				    resizable: false,
	 				    modal: true,
				 	    okVal: '是',
				 	    ok:function() {parent.addTab('待办业务列表',"scGzptlcController.do?scGzptlc&idcard="+idcard);close();},
				 	    cancelVal: '否',
				        cancel: function() { //$(".kj_list").remove();
				        	return true;
				        }
				 	
			 });
	
	 }
	
	return falg;
	
}

