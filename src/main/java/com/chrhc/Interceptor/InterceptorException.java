package com.chrhc.Interceptor;

public class InterceptorException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InterceptorException(String msg){
        super(msg);
    }
	public InterceptorException(String errorCode,Object msg){
        
		super(String.valueOf(msg));
        
	}

}
