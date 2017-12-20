package com.chrhc.framework.cgfrom.engine;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ChrhcFormInterceptor {
	
	
	public void beforeAdd(Map data ,HttpServletRequest request);
	
	public void afterAdd(Map data ,HttpServletRequest request);
	
	public void beforeUpdate(Map data ,HttpServletRequest request);
	
	public void afterUpdate(Map data ,HttpServletRequest request);
	
	public void beforeDelete(String id ,HttpServletRequest request);
	
	public void afterDelete(String id ,HttpServletRequest request);
	
}
