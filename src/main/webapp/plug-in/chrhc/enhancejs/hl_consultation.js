/**
 * Created by Administrator on 2016/6/27.
 */


$(function($){
    //var fff=parent.parent.frames[0].window.document.getElementById("history_call_id").value;
//处理所属公司赋值
    calledNumValue();
    hotline_yw_code();
    var history_call_id = top.history_call_id;
    if(history_call_id){
        $("#history_call_id").val(history_call_id);
    }
    transitService("transit_service");
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

            });
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
                    debugger;
                    var name = m.TERRITORYNAME;
                    var code = m.TERRITORYCODE;
                    $("<option value="+code+">"+name+"</option>").appendTo($("select[id=cities]"));
                })
            }
        });
    });

});
function beforeSubmit_(){
    debugger;
    var that = true;
    var province_name = $("#province option:selected").text();
    var cities_name = $("#cities option:selected").text();
    $("#province_name").val(province_name);
    $("#cities_name").val(cities_name);

    return that;
}