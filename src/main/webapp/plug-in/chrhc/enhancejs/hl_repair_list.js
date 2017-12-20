/**
 * Created by Administrator on 2016/7/19.
 */
$(function($){

})
function gorepairview(){
    var userInfo = getUserInofo();
    var repairqueryurl = userInfo.repairqueryurl;
    //window.open(repairqueryurl)
   addOneTab('报修查询', repairqueryurl, 'pictures');
}
