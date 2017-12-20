package com.chrhc.Interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.chrhc.framework.cgfrom.engine.ChrhcFormInterceptor;

public class TestInterceptor implements ChrhcFormInterceptor {

	@Override
	public void beforeAdd(Map data, HttpServletRequest request) {
		System.out.println("---------------------beforeAdd-----------------------");
	}

	@Override
	public void afterAdd(Map data, HttpServletRequest request) {
		System.out.println("---------------------afterAdd-----------------------");

	}

	@Override
	public void beforeUpdate(Map data, HttpServletRequest request) {
		System.out.println("---------------------beforeUpdate-----------------------");

	}

	@Override
	public void afterUpdate(Map data, HttpServletRequest request) {
		System.out.println("---------------------afterUpdate-----------------------");
	}

	@Override
	public void beforeDelete(String id, HttpServletRequest request) {
		System.out.println("---------------------beforeDelete-----------------------");
	}

	@Override
	public void afterDelete(String id, HttpServletRequest request) {
		System.out.println("---------------------afterDelete-----------------------");
	}

	 

}
