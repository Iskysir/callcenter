package org.jeecgframework.core.util;


import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 *http操作工具类
 *
 * @author H
 *
 */
public class HttpUtils {
	private static Logger logger = Logger.getLogger(HttpUtils.class);
	/*public static String get(String url){
		String content = "";
		HttpEntity entity = null;
		KeyStore trustStore  = null;
		try {
			trustStore = KeyStore.getInstance("jks");
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		//加载证书文件
		FileInputStream instream = null;
		try {
			instream = new FileInputStream(new File("C:\\Java\\jdk1.7.0\\jre\\lib\\security\\mya.store"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			try {
				trustStore.load(instream, "111111".toCharArray());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (CertificateException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(trustStore)
                    .build();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}

		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom()
				.setSSLSocketFactory(sslsf)
				.build();
		try
		{

			HttpGet httpget = new HttpGet(url);

			System.out.println("executing request" + httpget.getRequestLine());

			CloseableHttpResponse response = null;
			try {
				response = httpclient.execute(httpget);
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				entity = response.getEntity();

				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				if (entity != null) {
					try {
						content = EntityUtils.toString(entity);
						System.out.println(EntityUtils.toString(entity));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} finally {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}*/
	/*private static HttpClient client = new DefaultHttpClient();
	public static String get(String url) throws IOException {
		System.setProperty("javax.net.ssl.trustStore", "C:\\Java\\jdk1.7.0\\jre\\lib\\security\\mya.store");
		System.setProperty("javax.net.ssl.trustStorePassword", "111111");
		*//*EasySSLProtocolSocketFactory easySSL = null;
		try {
			easySSL = new EasySSLProtocolSocketFactory();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Protocol easyhttps = new Protocol( "https", (ProtocolSocketFactory)easySSL, 443 );
		Protocol.registerProtocol( "https", easyhttps );*//*
		String body = null;
		try {
			client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
			client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "    Mozilla/5.0 (Windows NT 6.2; rv:18.0) Gecko/20100101 Firefox/18.0");

			KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream instream = new FileInputStream(new File("C:\\Java\\jdk1.7.0\\jre\\lib\\security\\mya.store"));
			//密匙库的密码
			trustStore.load(instream, "111111".toCharArray());
			//注册密匙库
			SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
			//不校验域名
			socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme sch = new Scheme("https", 443, socketFactory);
			client.getConnectionManager().getSchemeRegistry().register(sch);

			// Get请求
			HttpGet httpget = new HttpGet(url);
			// 设置参数
			//String str = EntityUtils.toString(new UrlEncodedFormEntity(params));
			httpget.setURI(new URI(httpget.getURI().toString()));
			// 发送请求
			HttpResponse httpresponse = client.execute(httpget);
			// 获取返回数据
			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity,"gbk");
			if (entity != null) {
				entity.consumeContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return body;

	}*/
	public static String get(String url){
		String content = "";
		BufferedReader in = null;
		String keyStore = ""; //证书的路径，pfx格式
		String keyPass =""; //pfx文件的密码
		SSLContext sslContext = null;
		try {
			keyStore  = ResourceUtil.getConfigByName("keyStore");
			keyPass = ResourceUtil.getConfigByName("keyPass");
			KeyStore ks = KeyStore.getInstance("pkcs12");
			// 加载pfx文件
			ks.load(new FileInputStream(keyStore), keyPass.toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("sunx509");
			kmf.init(ks, keyPass.toCharArray());
			//jkx设置为忽略认证
			TrustManager tm = new X509TrustManager()
			{
				@Override
				public X509Certificate[] getAcceptedIssuers()
				{
					return null;
				}
				@Override
				public void checkServerTrusted(X509Certificate[] chain,
											   String authType) throws CertificateException
				{
				}
				@Override
				public void checkClientTrusted(X509Certificate[] chain,
											   String authType) throws CertificateException
				{

				}
			};
			sslContext = SSLContext.getInstance("TLS");
//初始化
			sslContext.init(kmf.getKeyManagers(), new TrustManager[]{tm}, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			HttpClient httpclient = new DefaultHttpClient();
			//下面是重点
			SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext);
			//过滤主机名称
			socketFactory.setHostnameVerifier(new AllowAllHostnameVerifier());
			Scheme sch = new Scheme("https", 443, socketFactory);
			httpclient.getConnectionManager().getSchemeRegistry().register(sch);
			HttpGet hg = new HttpGet(url);
			HttpResponse response = null;
			try {
				response = httpclient.execute(hg);
				HttpEntity entity = response.getEntity();
				if (entity != null)
				{
					//InputStream instreams = entity.getContent();
					//content = convertStreamToString(instreams);
					content = EntityUtils.toString(entity,"gbk");
					if (entity != null) {
						//entity.consumeContent();
						EntityUtils.consume(entity);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (in != null) {
				try {

					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return  content;
	}

	public static String convertStreamToString(InputStream is)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = "";
		try
		{
			while ((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	/*
	public static String get(String url) throws IOException {
		HttpClient client=new HttpClient();
		BufferedReader in = null;

		String content = null;
		try {
			// 定义HttpClient

			// 实例化HTTP方法
			GetMethod get = new GetMethod(url);
			get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			int statusCode = client.executeMethod(get);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + get.getStatusLine());
			}
			content = get.getResponseBodyAsString();
			get.releaseConnection();
		} finally {
		}
			return content;
		}*/
	public static String getNcCheckLogin(String userCode,String password){
		String loginUrl= ResourceUtil.getConfigByName("ncserviceurl");
		//String loginUrl = "http://58.56.23.89:9081/service/SFServiceGw";
		HttpPost httpPost = new HttpPost(loginUrl);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("itftype", "callcenter2nc");
		jsonParam.put("itfkey", "callcenter2nc");
		jsonParam.put("USER_CODE", userCode);
		jsonParam.put("USER_PASSWORD", password);
		StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
		HttpResponse resp = null;
		try {
			resp = client.execute(httpPost);
			logger.info("resp=="+resp);
			logger.info("resp=="+resp.getStatusLine().getStatusCode());
			if(resp.getStatusLine().getStatusCode() == 200) {
				HttpEntity he = resp.getEntity();
				respContent = EntityUtils.toString(he,"UTF-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("respContent=="+respContent);
		return respContent;
	}

	public static String ncDispatching(String devid,String whlx,String gzms,String zpry,String pgry,String isBack){
		String loginUrl= ResourceUtil.getConfigByName("ncserviceurl");
		//String loginUrl = "http://58.56.23.89:9081/service/SFServiceGw";
		HttpPost httpPost = new HttpPost(loginUrl);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("itftype", "savedispatchbill");
		jsonParam.put("itfkey", "savedispatchbill");
		jsonParam.put("PK_MERCHANTMSG_D", devid);//设备主键
		jsonParam.put("TASKCONTENT", whlx);//维护类型
		jsonParam.put("WORKDESCRIPT", gzms);//故障描述
		jsonParam.put("DESIGNATEPASN", zpry);//转派人员
		jsonParam.put("CREATOR", pgry);//派工人员主键
		jsonParam.put("ISBACK", isBack);//是否回访

		StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
		HttpResponse resp = null;
		try {
			resp = client.execute(httpPost);
			logger.info("resppg=="+resp);
			logger.info("resppg=="+resp.getStatusLine().getStatusCode());
			if(resp.getStatusLine().getStatusCode() == 200) {
				HttpEntity he = resp.getEntity();
				respContent = EntityUtils.toString(he,"UTF-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("respContentpg=="+respContent);
		return respContent;
	}
	public static void  main(String[] args) throws IOException {
		//String res=get("http://oa.ecton.com.cn:90/cwbase/web/singleloginjson.aspx?AuthType=UserCode&AppCode=0001&UID=010177&PID=123456&frameType=pc&callType=1&callPara=");
		String res = getNcCheckLogin("ZHANGSAN","123456");
		System.out.println(res);
	}
}
