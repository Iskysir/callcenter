package com.chrhc.project.sc.gzpt.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Scope("prototype")
@Controller
@RequestMapping("/gzptController")
public class GzptController {
	//@Autowired
	
	//private JdbcTemplate jdbcTemplateA;
	 
	@RequestMapping(params = "getdata")
	public   String  getgzptdata(){
		 /*ThreadLocal<String> threadlocal = new ThreadLocal<String>();
		 //threadlocal.set("dataSource_A");
		ApplicationContext context = ApplicationContextUtil.getContext();
		JdbcTemplate templateObj = (JdbcTemplate) context.getBean("jdbcTemplate");
		//JdbcTemplate jdbctemplate = 
		String sql ="select * from testa";
		List<Map<String,Object>> baseList = jdbcTemplateA.queryForList(sql);
		for(Map<String,Object> x:baseList){
			System.out.println(x.toString());
		}*/
		return "baseListbaseList";
	}
	
	@RequestMapping(params = "getdatas")
	public   ModelAndView  getgzptdatas(String url){
		ModelAndView mv  = new ModelAndView("com/chrhc/project/sc/gzpt/scGzptList");
		HttpClient httpClient = new HttpClient();
		// 创建POST方法的实例
		PostMethod postMethod = new PostMethod(url);
		//HttpPost method = new HttpPost(url);
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		TSUser user= ResourceUtil.getSessionUserName();
		postMethod.addParameter("userLoginName", user.getUserName());
		postMethod.addParameter("orgCode", user.getOrgCodes());
		postMethod.addParameter("roles", user.getRoleid());
		//postMethod.addParameter("startTime", user.getUserName());
		//postMethod.addParameter("endTime", user.getUserName());
		postMethod.addParameter("page", "1");
		postMethod.addParameter("pagesize", "15");
		//执行结果
		try {
			int responscode = httpClient.executeMethod(postMethod);
			byte[] responseBody = postMethod.getResponseBody(); 
			System.out.println(new String(responseBody));  
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mv;
	}
	public static void setdatasource(){
		  ThreadLocal<String> threadlocal = new ThreadLocal<String>();
		
	}
}
