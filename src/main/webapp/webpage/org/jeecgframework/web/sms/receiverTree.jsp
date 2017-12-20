<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:base type="ckeditor,jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>

<script type="text/javascript">
	$(function() {
		$('#functionid').tree({
			checkbox : true,
			url : 'roleController.do?getRoleTree&roleId=${roleId}',
			onLoadSuccess : function(node) {
				expandAll();
			}
		});
		
	});
	
	
	function expandAll() {
		var node = $('#functionid').tree('getSelected');
		if (node) {
			$('#functionid').tree('expandAll', node.target);
		} else {
			$('#functionid').tree('expandAll');
		}
	}
	function selecrAll() {
		var node = $('#functionid').tree('getRoots');
		for ( var i = 0; i < node.length; i++) {
			var childrenNode =  $('#functionid').tree('getChildren',node[i].target);
			for ( var j = 0; j < childrenNode.length; j++) {
				$('#functionid').tree("check",childrenNode[j].target);
			}
	    }
	}
	function reset() {
		$('#functionid').tree('reload');
	}

	$('#selecrAllBtn').linkbutton({   
	}); 
	$('#resetBtn').linkbutton({   
	});   
	function update_msg(){
		if(""){
		}else{
			$.post(
				"tSSmsController.do?doUpdate"
			);
		};
	}
</script>
<form action="tSSmsController.do?doAdd">
<div class="easyui-layout" fit="true" style="position:absolute;top:30px;bottom:60px;left:0;width:20%;height:100px">
<div region="center" style="padding: 1px;">
<div class="easyui-panel" style="left:20%;padding: 1px;" fit="true" border="false" id="functionListPanel"><input type="hidden" name="roleId" value="${roleId}" id="rid"> <a id="selecrAllBtn"
	onclick="selecrAll();"><t:mutiLang langKey="select.all"/></a> <a id="resetBtn" onclick="reset();"><t:mutiLang langKey="common.reset"/></a>
<ul id="functionid"></ul>
</div>
</div>
</div>


<div style="position:absolute;top:30px;left:20%;width:80%;height:100px">
<input type="checkbox" name="message" value="1"/>短信
<input type="checkbox" name="message" value="2"/>邮件
<input type="checkbox" name="message" value="3"/>微信&nbsp;&nbsp;&nbsp;&nbsp;
模板类型&nbsp;<select name="temp">
	<option name="choose" value="">---请选择---</option>

</select>

<input type="submit" value="发送" onclick="update_msg()">&nbsp;&nbsp;&nbsp;
<input type="button" value="取消" onclick="">

<br><br><br>
<tr>
	<td class="value">
		<t:ckeditor name="template" isfinder="false" type="width:1100,height:570" value=""></t:ckeditor>
	</td>
</tr>
</div>

</form>
