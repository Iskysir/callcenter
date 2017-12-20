/**
 * Created by Administrator on 2016/7/19.
 */
$(function($){

    var urldata = getUrlData();
    var id = urldata.id;
    if(id){
        debugger;
        $("#degeree").after("<a href='#' id='visitscore' style='color: blue;font-weight: bold ' >【回访题目】</a>");
        var date = new Date();
        var dd = date.Format("yyyy-MM-dd");
        var datevi = $("input[name='visit_time']").val();
        if(!datevi){
            $("input[name='visit_time']").val( date.Format("yyyy-MM-dd"));
        }
    }
    $("#visitscore").on("click",function(){
          $.dialog({
         content: "url:hlVisitItemController.do?goHlVisitItem&bus_id="+$("#bus_id").val(),
         lock : true,
         title:"回访题目",
         zIndex:2100,
         width:700,
         height: 580,
         parent:windowapi,
         cache:false,
         ok: function(){
             var array = new Array();
             var iframe = this.iframe.contentWindow;
             var visitlist=eval("("+iframe.visitlistjson+")");
             var total = 0;
             var yestotal = 0;
             $.each(visitlist,function(e,m){
                 var visitobject = {};
                 var visitid = m.id;
                 var valuea = iframe.$("input[name='"+visitid+"']:checked").val();
                 if(valuea){
                     visitobject.visitId = visitid;//题目主键id
                     visitobject.visitResult = valuea;//是否
                     visitobject.busId = $("#bus_id").val();
                     visitobject.busType = $("#bus_type").val();
                     total++;
                     if("yes" == valuea){
                         yestotal++;
                     }
                     array.push(visitobject);
                 }
             });
             //保存业务与题库关联
             $.ajax({
                 url:"hlVisitItemController.do?savebusvisit",
                 data:{"visitlist":JSON.stringify(array),"busId":$("#bus_id").val()},
                 type:"Post",
                 dataType:"json",
                 async:false,
                 success:function(data){
                     debugger;

                 }
             });
             if(total != 0){
                 //debugger;
                // var c = parseInt(yestotal) * 100 /parseInt(total)+'%';
                 var manyidu = Math.round(yestotal / total * 10000) / 100.00 + "%"
                 $("#degeree").val(manyidu)
             }
         return true;
         },
         cancelVal: '关闭',
         cancel: function(){
         }
         });
    });
});
