/**
 * Created by Administrator on 2016/6/27.
 */


$(function($){
    var urldata = getUrlData();

});

/**
 * 查看建筑物房间信息
 * @param id 建筑物Id
 */
function implement(id){
    gridname="hl_qc_taskList";
    $.ajax({
        url: 'HlqcController.do?implement&id='+id,
        type : 'POST',
        dataType : 'json',
        async : false,
        cache : false,
        success: function(data){
            var d = $.parseJSON(data);
            if (d.msg == "9") {
                $.dialog.alert('该建筑物信息已被删除，请确认！', reloadsc_jzwglList);
            }else if (d.msg == "1") {
                var name = d.obj;
                if(name.length > 10){
                    name = name.substring(0,10) + '…';
                }
                addOneTab(name+'[房间信息]', 'scJzwglController.do?findFjByJzwId&id=' + id, 'pictures');
            }else{
                $.dialog.alert('该建筑物未生成房间信息，请确认！', reloadsc_jzwglList);
            }
        }
    });
}