/**
 * Created by Administrator on 2016/6/27.
 */


$(function($){
    var urldata = getUrlData();
    var isread = urldata.isread;
    if(isread){

        $("#add").remove();
        $("#delete").remove();
        $("#excel").remove();
        var user = getUserInofo();
        $('#io_noticeList').datagrid({
            queryParams: {
                touserid: user.username,
                ispublish:'Y'
            },
            onLoadSuccess:function(data){
                debugger;
                var cc = $(".datagrid-cell");
                var bb = $("td[field='_number']");
                var aa = $("td[field='_number']").find("span");
                $("td[filed='_number']").find("span").removeAttr("onclick");
            }
        });




       // var touserid = '32322';
       // $("<input name='touserid'value="+touserid+" hidden>").appendTo($('#io_noticeListtb'));
    }
});

