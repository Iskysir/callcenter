/**
 * Created by Administrator on 2016/6/27.
 */


$(function($){

    $("#ncpgid").parent().find("span[class='suoshu']").removeAttr("onclick").attr("readonly",true);
    //终端号字段处理
    $("#zdh").removeAttr("readonly");
    $("#zdh").removeAttr("disabled");
    $("#input_mode").attr("disabled","disabled");
    $("#update_name").attr("disabled","disabled");
    $("#update_by").attr("disabled","disabled");
    //设置终端数量默认值为1
    $("#zdsl").val("1");
//处理所属公司赋值
    calledNumValue();
    hotline_yw_code();
    var history_call_id = top.history_call_id;
    if(history_call_id){
        $("#history_call_id").val(history_call_id);
    }
    //新增时设置派工状态及报修种类
    debugger;
    var urlpath = getUrlData();
    var bxid = urlpath.id;
    if(!bxid){
        // $("#pgzt").val("2");//已生成派工
        $("#bx_type").val("2");//电话报修
        manual();//手工输入终端号处理
        $("#gzms").val("商户名称: 联系人: 联系方式: 具体地址: 终端号: 机型: 故障内容: ");
    }
    var bx_type = $("#bx_type").val();
    if(bx_type == '1'){
        $("#zxjd").show();
    }
    $("#zdh").on("blur",function(){
        var zdh = $("#zdh").val();
        if(zdh.length){
            var url = "hlVisitItemController.do?queryZdh&zdh="+zdh;
            $.post(url,{},function(data){
                debugger;
                var flag = data.success;
                if(flag){
                    var value =  data.obj[0];

                    debugger;
                    //devid=devid,zdh=zdh,shh=shh,shmc=shm,lxr=contact,zjdz=zjdz,zdjtazdz=address,sdjqmc=sdjqmc,ds=ds,cj=cs,tzr=tzr,jx=jx,lxfs=tel
                    $("#devid").val(value.DEVID);
                    $("#zdh").val(value.ZDH);
                    $("#shh").val(value.SHH);
                    $("#shmc").val(value.SHM);
                    $("#lxr").val(value.CONTACT);
                    $("#lxfs").val(value.TEL);
                    $("#zjdz").val(value.ZJDZ);
                    $("#zdjtazdz").val(value.ADDRESS);
                    $("#sdjqmc").val(value.SDJQMC);
                    $("#ds").val(value.DS);
                    $("#jx").val(value.JX);
                    $("#cj").val(value.CS);
                    $("#tzr").val(value.TZR);

                }else{
                    $.dialog.alert('未查询到终端数据！');
                    $("#devid").val("");
                    $("#zdh").val("");
                    $("#shh").val("");
                    $("#shmc").val("");
                    $("#lxr").val("");
                    $("#lxfs").val("");
                    $("#zjdz").val("");
                    $("#zdjtazdz").val("");
                    $("#sdjqmc").val("");
                    $("#ds").val("");
                    $("#jx").val("");
                    $("#cj").val("");
                    $("#tzr").val("");
                }

            },"json");
        }


    })
    $("#zxjd").on("click",function(){
        var url = "hlVisitItemController.do?updateZxjd&id="+$("input[name='id']").val();
        $.post(url,{},function(data){
            parent.reloadTable();
            parent.$("#editFormDiv").hide();
            parent.$("#editForm").attr("src","");
            parent.$(".datagrid").show();
        });

    })
    $("#visitscore").on("click",function(){
        $.dialog({
            content: "url:hlVisitItemController.do?goManual",
            lock : true,
            title:"终端号维护",
            zIndex:2100,
            width:1000,
            height: 580,
            parent:windowapi,
            cache:false,
            ok: function(){
                var array = new Array();
                var iframe = this.iframe.contentWindow;
                debugger;
                //  var dd = iframe.$("form").serialize();
                var zdh = iframe.$("#zdh").val();
                var shh = iframe.$("#shh").val();
                var shmc = iframe.$("#shmc").val();
                var lxr = iframe.$("#lxr").val();
                var lxfs = iframe.$("#lxfs").val();
                var zjdz = iframe.$("#zjdz").val();
                var zdjtazdz = iframe.$("#zdjtazdz").val();
                var sdjqmc = iframe.$("#sdjqmc").val();
                var ds = iframe.$("#ds").val();
                var jx = iframe.$("#jx").val();
                var cj = iframe.$("#cj").val();
                var tzr = iframe.$("#tzr").val();

                $("#zdh").val(zdh);
                $("#shh").val(shh);
                $("#shmc").val(shmc);
                $("#lxr").val(lxr);
                $("#lxfs").val(lxfs);
                $("#zjdz").val(zjdz);
                $("#zdjtazdz").val(zdjtazdz);
                $("#sdjqmc").val(sdjqmc);
                $("#ds").val(ds);
                $("#jx").val(jx);
                $("#cj").val(cj);
                $("#tzr").val(tzr);
                $("#input_mode").val("2");//设置为手动录入
                return true;
            },
            cancelVal: '关闭',
            cancel: function(){
            }
        });
    });
    sound_record();
    $.ajax({
        url:"territoryController.do?getProvince",
        type:"Post",
        dataType:"json",
        async:false,
        success:function(data){
            var province = $("#province").val();
            var cities = $("#cities").val();
            $("#cities").remove();
            $("#province").remove();
            $("label[for='cities']").after("<select class='form-control' style='width: 130px;' id='province' name='province'></select>");
            /*   $("#cities").after("<input id='province' name='province' value='山东省'>" );
             $('#province').combobox({
             data:data.obj,
             valueField:'TERRITORYCODE',
             textField:'TERRITORYNAME'
             });*/
            $.each(data.obj,function(i,m){

                var name = m.TERRITORYNAME;
                var code = m.TERRITORYCODE;
                if(province == code){
                    $("<option value="+code+" selected>"+name+"</option>").appendTo($("select[id=province]"));
                }else {
                    $("<option value="+code+">"+name+"</option>").appendTo($("select[id=province]"));
                }

            })
            $("select[id='province']").after("<select class='form-control' style='width: 130px;' id='cities' name='cities'></select>");
            var id = ($("#province").val());
            $.ajax({
                url:"territoryController.do?getCity",
                data:{"code":id},
                type:"Post",
                dataType:"json",
                async:false,
                success:function(datacities){
                    $.each(datacities.obj,function(i,m){
                        debugger;
                        var name = m.TERRITORYNAME;
                        var code = m.TERRITORYCODE;
                        if(cities == code){
                            $("<option value="+code+" selected>"+name+"</option>").appendTo($("select[id=cities]"));
                        }else {
                            $("<option value="+code+">"+name+"</option>").appendTo($("select[id=cities]"));
                        }

                    })
                }
            });

        }


    });
    $("#province").change(function(){
        $("#cities").empty();
        var id = ($("#province").val());
        $.ajax({
            url:"territoryController.do?getCity",
            data:{"code":id},
            type:"Post",
            dataType:"json",
            async:false,
            success:function(datacities){
                $.each(datacities.obj,function(i,m){
                    var name = m.TERRITORYNAME;
                    var code = m.TERRITORYCODE;
                    $("<option value="+code+">"+name+"</option>").appendTo($("select[id=cities]"));
                })
            }
        });
    });

});
function beforeSubmit_(){
    var that = true;
    var province_name = $("#province option:selected").text();
    var cities_name = $("#cities option:selected").text();
    $("#province_name").val(province_name);
    $("#cities_name").val(cities_name);

    return that;
}

//手工输入终端号处理
function manual(){
    $("#zdh").parent().find("span[style*='red']").after("<a href='#' id='visitscore' style='color: blue;font-weight: bold ' >【手工输入】</a>");

}




