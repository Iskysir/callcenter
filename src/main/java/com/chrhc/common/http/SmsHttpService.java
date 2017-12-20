package com.chrhc.common.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.PropertiesUtil;
import org.jeecgframework.web.sms.util.Constants;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class SmsHttpService  {
	private static Logger logger = Logger.getLogger(SmsHttpService.class);
	// 接口帐号
	private static  String  SNAME = "huarong";
	// 接口密钥
	private static  String SPSD = "huarong1";
	// 接口帐号
	private static  String LONGURL  = "http://yl.mobsms.net/send/longsend.aspx";
	// 接口密钥
	private static  String SURL = "http://yl.mobsms.net/send/g70send.aspx";
	
	@Autowired
	private SystemService systemService;
	static {
		PropertiesUtil p = new PropertiesUtil("sysConfig.properties");		
		//SNAME = p.readProperty(Constants.SMS_WEB_ACCOUNT);
		//SPSD = p.readProperty(Constants.SMS_WEB_PASSWORD);
		LONGURL=p.readProperty(Constants.SMS_WEB_LONGSEND);
		SURL=p.readProperty(Constants.SMS_WEB_70SEND);
		SNAME=p.readProperty(Constants.SMS_WEB_ACCOUNT);
		SPSD=p.readProperty(Constants.SMS_WEB_PASSWORD);
		//LONGURL="http://yl.mobsms.net/send/longsend.aspx";
		//SURL="http://yl.mobsms.net/send/g70send.aspx";*/
	}

	public List<Map<String, Object>> getSmsConfig(String sysOrgcode){
		List<Map<String, Object>> list = null;
		if(StringUtils.isNotEmpty(sysOrgcode)){
			String sql = "select * from sc_sms_config t where t.sqbh = ?";
			  list = systemService.findForJdbc(sql, sysOrgcode);
		}
		return list;
	}
	
	/**
	 * 
	 * @param sysOrgcode 社区编码
	 * @param dstNumber 手机号码
	 * @param msg 发送内容
	 * @param time 定时发送 ,参数可传递空值
	 * @param extno 参数可传递空值
	 * @param seqid 参数可传递空值
	 * @return
	 */
	public  Map<String,Object> SendMessage(String sysOrgcode ,String dstNumber, String msg, String time, String extno,String seqid) {
		
		//List<Map<String, Object>> list = this.getSmsConfig(sysOrgcode);
		Map<String,Object> mapResult = new HashMap<String,Object>();
		String message = "";
		//if(list != null && list.size() > 0){
			//String SNAME = list.get(0).get("sms_web_account").toString();
			//String SPSD = list.get(0).get("sms_web_pwd").toString();		
			try {
				 boolean bLong = false;
				 if(msg.length()>70){
					 bLong = true;
				 } 
				String sMsg = java.net.URLEncoder.encode(msg, "GBK");
				
				StringBuffer sUrl = new StringBuffer();

				if(bLong){
					sUrl.append(LONGURL);
				}else{
					sUrl.append(SURL);
				}
				sUrl.append("?name=").append(SNAME);
				sUrl.append("&pwd=").append(SPSD);
				sUrl.append("&dst=").append(dstNumber);
				sUrl.append("&msg=").append(sMsg);
				sUrl.append("&time=").append("");
				sUrl.append("&sender=").append("");
				sUrl.append("&sequeid=").append(seqid);

				logger.info("提交："+sUrl);
				HttpRequester request = new HttpRequester();
				request.setDefaultContentEncoding("GBK");
				//HttpRespons hr = request.sendGet(sUrl.toString());
				HttpRespons hr = request.sendPost(sUrl.toString());
				String strRe = hr.getContent();
				
			//	String strRe = "num=1&success=13402222768&faile=&err=发送成功&errid=0";
				//System.out.println(strRe);
				logger.info("返回："+strRe);

				String sReArr[] = StringUtils.split(strRe, '&');
				String sNum = sReArr[0];
				String sSuccess = sReArr[1];
				String sFaile = sReArr[2];
				String sErr = sReArr[3];
				String sErrId = sReArr[4];
				//发送数量
				String sNumArr[] = StringUtils.split(sNum, '=');
				if(sNumArr.length==2){
					//String sNumValue = sNumArr[1];
					mapResult.put("sNumValue", sNumArr[1]);
				}else{
					mapResult.put("sNumValue", "");
				}
				
				//成功号码
				String sSucessArr[] = StringUtils.split(sSuccess, '=');
				if(sSucessArr.length==2){
					//String sSuccessValue = sSucessArr[1];
					mapResult.put("sSuccessValue", sSucessArr[1]);
				}else{
					mapResult.put("sSuccessValue", "");
				}
				
				
				//失败号码
				String sFaileArr[] = StringUtils.split(sFaile, '=');
				if(sFaileArr.length==2){
					//String sFaileValue = sFaileArr[1];
					mapResult.put("sFaileValue", sFaileArr[1]);
				}else{
					mapResult.put("sFaileValue", "");
				}
				
				
				//发送结果
				String sErrArr[] = StringUtils.split(sErr, '=');
				
				if(sErrArr.length==2){
					//String sErrValue = sErrArr[1];
					mapResult.put("sErrValue", sErrArr[1]);
				}else{
					mapResult.put("sErrValue", "");
				}
				//发送结果内容
				String sErrIdArr[] = StringUtils.split(sErrId, '=');
				
				if(sErrIdArr.length==2){
					//String sErrIdValue = sErrIdArr[1];
					mapResult.put("sErrIdValue", sErrIdArr[1]);
				}else{
					mapResult.put("sErrIdValue", "");
				}
			
			
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
//		}else{
//			message = "该社区账户密码未配置";
//		}
		mapResult.put("message", message);
		return mapResult;
	}
	
	public static void main(String[] args){
//		//for(int i=0;i<20;i++){
//			new SmsHttpService().SendMessage("13402222768", 
//					"ee", "", "","");
//		//}
		//String strRe = "num=1&success=13402222768,&faile=&err=发送成功&errid=0";
		//logger.info("返回："+strRe);
		
	//	Map<String,Object> mapResult =   new SmsHttpService().SendMessage("13583162671", "测试", "", "", "");
	//	System.out.println(mapResult);
		/*try {
			//String str = "测试";
			//String result = URLEncoder.encode(str, "gb2312");
			System.out.println(result);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		StringBuffer sUrl = new StringBuffer();

		
			sUrl.append("http://yl.mobsms.net/send/g70send.aspx");
		
		sUrl.append("?name=").append("shaobo");
		sUrl.append("&pwd=").append("shaobo123");
		sUrl.append("&dst=").append("13583162671");
		sUrl.append("&msg=").append("测试3333");
		sUrl.append("&time=").append("");
		sUrl.append("&sender=").append("");
		sUrl.append("&sequeid=").append("");

		logger.info("提交："+sUrl);
		HttpRequester request = new HttpRequester();
		request.setDefaultContentEncoding("GBK");
		//HttpRespons hr = request.sendGet(sUrl.toString());
		HttpRespons hr;
		try {
			hr = request.sendPost(sUrl.toString());
			String strRe = hr.getContent();
			System.out.println(strRe);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
