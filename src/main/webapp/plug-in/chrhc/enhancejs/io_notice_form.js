/**
 * Created by Administrator on 2016/6/27.
 */


$(function($){
    debugger;
    var urlpath = getUrlData();
    var id = urlpath.id;
    if(id){
        $.ajax({
            url:"hlVisitItemController.do?updateIsread",
            data:{"id":id},
            type:"Post",
            dataType:"json",
            async:false,
            success:function(datacities){
            debugger;
            }
        });
    }


});


