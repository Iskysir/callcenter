var gxlx = {};

	//初始化取得关系类型json

	init();
	function init(){
	
		if($.isEmptyObject(gxlx)){
			$.ajax({
			    url:"kinshipController.do?getQslxSex",
				type:"Post",
			    dataType:"json",
			    async:false,
			    success:function(data){
			    	
			    	//alert(data.msg);
			    	//$.data(gxlx,"gxlx",data.attributes)
			    	gxlx = data.attributes;
					//console.log(gxlx);
					}
					
			    
			});
		}
		$("#formobj").attr({"class":"form-inline valideForm"});
		$("select").attr({"class":"form-control w260"});
		if(location.href.indexOf("load=detail")!=-1){
			//查看模式控件禁用
			$("#formobj").find(":input").attr("disabled","disabled");
			$("#buttonPanel").hide();
		
		}
		
	}
	 var neibuClickFlag = false;
	  function neibuClick() {
		  neibuClickFlag = true; 
		  $('#btn_sub').trigger('click');
	  }
	 function uploadFile(data){
		
	  		if(!$("input[name='id']").val()){
	  			$("input[name='id']").val(data.obj.id);
	  		}
	  		if($(".uploadify-queue-item").length>0){
	  			upload();
	  		}else{
	  			if (neibuClickFlag){
	  				alert(data.msg);
	  				neibuClickFlag = false;
	  			}else {
	  			//getParentWindow();
	  				//alert(getParentWindow().document.body.innerHTML);
		  			var win = getParentWindow();
		  			///alert(win);
					win.reloadTable(data);
					win.tip(data.msg);
					if(frameElement.api)
					{
						frameElement.api.close();
					}
					else
					{
						closeCurrentTab();
					}
	  			}
	  		}
	  	}
    function detailrk(id){
    	$.ajax({
		    url:"kinshipController.do?checkperson",
		    data:{"id":id},
			type:"Post",
		    dataType:"json",
		    async:false,
		    success:function(data){
				if(data.success){
					var url = "chrhcFormBuildController.do?ftlForm&tableName=sc_rkjbxxnew&mode=read&bizType=";
			    	url += '&load=detail&id='+id;
			    	createdetailwindow("查看人口基本信息",url,'1100px','700px');	
				}else{
					
					$.dialog.alert("此人不存在，请重新维护此亲属关系");
				}
				
		    },
			error:function(data){
				$.messager.alert('错误',data.msg);
				return false;
			}
		});
    	
    }
	//通用弹出式文件上传
	function commonUpload(callback){
	    $.dialog({
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
	function updateselect(){
		
	
		var id = $("#id").val();
		var ryid = $("#ryId").val();
		var qsid = $("#qsId").val();
		if(ryid == qsid){
			$.dialog.alert('维护关系人不能选择本人！');
			return false;
		}
		var $selectgxlx = $("select[name='gxlx']");
		
		var sex = $("#sex").val();
		if(sex){
			/*if(sex == "男"){
				sex = "1";
			}else{
				sex = "2";
			}*/
			var type = $selectgxlx.val();
			var gxtype = gxlx[type].sex;
			if(sex != gxtype){
				$.dialog.alert('关系类型与所选人员性别无法对应，请重新选择！');
				return false;
			}
		}
		var that=true;
		$.ajax({
		    url:"kinshipController.do?checkadd",
		    data:{"ryId":ryid,"qsId":qsid,"gxlx":$selectgxlx.val(),"id":id},
			type:"Post",
		    dataType:"json",
		    async:false,
		    success:function(data){
		    	///alert(data.msg);
		    	
				if(!data.success){
					$.dialog.alert(data.msg+'已存在此关系，请重新维护!');
					that = false;
					return false;
				}
				
		    },
			error:function(data){
				$.messager.alert('错误',data.msg);
				return false;
			}
		});
		if(that){
			$selectgxlx.attr("disabled",false);  
		}
		
		return that;
		
		
		
		
	}
	function addbeforeSubmit(){
		debugger;
		var ryid = $("#ryId").val();
		var qsid = $("#qsId").val();
		if(ryid == qsid){
			$.dialog.alert('维护关系人不能选择本人！');
			return false;
		}
		var sex = $("#sex").val();
		/*if(sex == "男"){
			sex = "1";
		}else{
			sex = "2";
		}*/
		var $selectgxlx = $("select[name='gxlx']");
		var type = $selectgxlx.val();
		var gxtype = gxlx[type].sex;
		if(sex != gxtype){
			$.dialog.alert('关系类型与所选人员性别无法对应，请重新选择！');
			return false;
		}
		//debugger;
		var that=true;
		$.ajax({
		    url:"kinshipController.do?checkadd",
		    data:$("#formobj").serialize(),
			type:"Post",
		    dataType:"json",
		    async:false,
		    success:function(data){
		    	///alert(data.msg);
		    	
				if(!data.success){
					$.dialog.alert(data.msg+'已存在此关系，请重新维护!');
					that = false;
					return false;
				}
				
		    },
			error:function(data){
				$.messager.alert('错误',data.msg);
				return false;
			}
		});
		return that;
	}
