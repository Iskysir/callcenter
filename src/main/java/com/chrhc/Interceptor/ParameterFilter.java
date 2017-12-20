package com.chrhc.Interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.util.StringUtil;

import com.chrhc.project.sc.common.Base64;

public class ParameterFilter implements Filter    {

	private final String FIELD = "field" ;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Map<String,String[]> m = new HashMap<String,String[]>(request.getParameterMap());  
		if(m.get(FIELD) != null){
			String fieldVal[] = m.get(FIELD);
			m.put(FIELD, new String[]{Base64.getFromBase64(fieldVal[0])});  
		}
		//m.put("id", new String[]{"cc_kitch_enter"});  
		request = new ParameterRequestWrapper((HttpServletRequest)request, m);  
		chain.doFilter(request, response);  
	
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	 
}