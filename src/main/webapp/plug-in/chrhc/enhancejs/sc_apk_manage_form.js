$(function(){

	$("#apk_version").attr("readonly","readonly");
	if(!$("#apk_version").val()){
	  $.get(
	      "appController.do?queryApkVersion",
	      "",
	      function(data) {
	          var obj = $.parseJSON(data);
	          $("#apk_version").val(obj.attributes.version + 1);
	         
	      });
	}
});