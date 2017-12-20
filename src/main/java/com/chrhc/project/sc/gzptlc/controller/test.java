package com.chrhc.project.sc.gzptlc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;

import com.chrhc.project.sc.gzptlc.entity.ScGzptlcEntity;

public class test {
private int a;

	public int getA() {
	return a;
}

public void setA(int a) {
	this.a = a;
}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*// TODO Auto-generated method stub
		List<Object> list = new ArrayList<Object>(); 
		Object obj =4;
		test t= new test();
		System.out.println(obj+"");
		//t.setA(obj);
		for(int i=0;i<3;i++){
		String b = (i+"");
		list.add(b);
		}
		System.out.println(list);*/
		
		
		tj() ;
	}
		
		private static boolean  tj() {
			boolean re = true;
			HttpClient httpClient = new HttpClient();
			httpClient.getParams().setAuthenticationPreemptive(true);
			// 创建POST方法的实例
			PostMethod postMethod = new PostMethod("http://10.61.160.7:8280/cpub-back-web/api/app/netservice/declare/submitApprove");
			// 使用系统提供的默认的恢复策略
			//postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
			postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
			//取得用户
			//TSUser user = ResourceUtil.getSessionUserName();
			postMethod.addParameter("userLoginName","nanlong");
			postMethod.addParameter("userName", "南龙社区");
			postMethod.addParameter("instanceId","71837f0959b24eb2aad011f6853d8bfd" );
			postMethod.addParameter("taskResult", "1");
			postMethod.addParameter("taskReply", "测试你好");
			
			String result = null;// 初始化返回结果（String类型）
			byte[] responseBody = null;// 初始化返回结果（byte[]类型）
			//boolean  responseBody = null;// 初始化返回结果（byte[]类型）
			// 执行getMethod
			int statusCode=0;
			try {
				statusCode = httpClient.executeMethod(postMethod);
				if (statusCode != HttpStatus.SC_OK) {
					System.err.println("Method failed: "+ postMethod.getStatusLine());
					return false;
				}
				// 返回结果（byte[]类型）
				responseBody = postMethod.getResponseBody();
				// 返回结果（String类型，GBK格式）
				result = new String(responseBody, "utf-8");
				
				JSONObject jObject = JSONObject.fromObject(result);
				JSONObject jObjectcontent=   jObject.getJSONObject("content");
				boolean  rs =  (boolean) jObjectcontent.get("rs");
				re=rs;
				
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				// 释放连接
				postMethod.releaseConnection();
			}
			return re;
		}
}
