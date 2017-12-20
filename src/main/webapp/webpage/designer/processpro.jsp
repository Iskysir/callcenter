<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>添加流程变量</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" layout="div" action="processController.do?saveVariable">
    <input name="processproid" type="hidden" value="${processpro.processproid}">
    <input name="processId" type="hidden" value="${processId}">
    <input name="TProcess.processid" type="hidden" value="${processDefinitionId}">
    <input name="TProcessnode.processnodeid" type="hidden" value="${processnode.processnodeid}">
    <input name="procesnode" type="hidden" value="${processNode}">
    <input name="processDefinitionId" type="hidden" value="${processDefinitionId}">
    <fieldset class="step">
     <legend>
      流程变量
     </legend>
     <div class="form">
      <label class="form">
       变量名称:
      </label>
      <input name="processprokey" value="${processpro.processprokey }" datatype="s3-50" errormsg="变量名称范围在3~50位字符!">
      <span class="Validform_checktip">变量名称范围在3~50位字符,且不为空</span>
     </div>
     <div class="form">
      <label class="form">
       变量类型:
      </label>
      <input name="processprotype" value="${processpro.processprotype }">
     </div>
     <div class="form">
      <label class="form">
       变量值:
      </label>
      <input name="processproval" value="${processpro.processproval}">
     </div>
     <div class="form">
      <label class="form">
       变量值来源:
      </label>
      <select name="processprodatatype">
       <option value="database" <c:if test="${processpro.processprodatatype=='database'}">selected="selected"</c:if>>
        数据库
       </option>
       <option value="page" <c:if test="${processpro.processprodatatype=='page'}">selected="selected"</c:if>>
        页面
       </option>
      </select>
     </div>
     <div class="form">
      <label class="form">
       变量表达式:
      </label>
      <input name="processproexp" value="${processpro.processproexp}">
     </div>
     <div class="form">
      <label class="form">
       变量描述:
      </label>
      <input name="processproname" value="${processpro.processproname}">
     </div>
    </fieldset>
  </t:formvalid>
 </body>
</html>
