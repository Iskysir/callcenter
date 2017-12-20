package com.chrhc.project.sc.userdesk.page;

import org.jeecgframework.web.system.pojo.base.TSFunction;

import com.chrhc.project.sc.userdesk.entity.ScUserDeskEntity;

  
public class ScUserDeskPage implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ScUserDeskEntity userDeskEntity;
	
	private TSFunction function;

	public ScUserDeskEntity getUserDeskEntity() {
		return userDeskEntity;
	}

	public void setUserDeskEntity(ScUserDeskEntity userDeskEntity) {
		this.userDeskEntity = userDeskEntity;
	}

	public TSFunction getFunction() {
		return function;
	}

	public void setFunction(TSFunction function) {
		this.function = function;
	}
}
