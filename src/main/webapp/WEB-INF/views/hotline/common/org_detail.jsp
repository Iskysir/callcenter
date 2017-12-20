<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

     	
         	<br>
                 <input type="hidden" name="id" id="orgId" value="${organization.id}"></input>
				<input type="hidden" name="name" id="orgName" value="${organization.name}"></input>
				<input type="hidden" name="code" id="orgCode" value="${organization.code}"></input>
            
         <br>
         			<div class="col-md-12 ie8correct"> 
                                <label class="field-title">类型：</label>
                                <span>${organization.categoryName}</span>
          			</div>
            <br>      <div class="col-md-12 ie8correct m-t-10"> 
                                <label class="field-title">名称：</label>
                                <span>${organization.name}</span>
          </div>
            <br>     <div class="col-md-12 ie8correct m-t-10">
                                <label class="field-title">编码：</label>
                                <span>${organization.code}</span>
         </div>
            <br>  <div class="col-md-12 ie8correct m-t-10">    
                                <label class="field-title">备注：</label>
                                <span>${organization.remark}</span>                  
         </div>

