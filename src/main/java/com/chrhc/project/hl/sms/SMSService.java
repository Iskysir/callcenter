package com.chrhc.project.hl.sms;


import org.apache.log4j.Logger;

import com.huateng.commons.comm.STCPSocket;
import com.huateng.commons.comm.STCPSocketFactory;
import com.huateng.commons.lang.fill.FillUtils;
import com.huateng.utils.DumpUtils;
import com.huateng.xmapper.XMapper;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;


/**
 * 发送报文
 * <br>创建日期：2012-8-10
 * <br><b>Copyright 2012 上海华腾软件系统有限公司　All Rights Reserved</b>
 * @author nan.jiang
 * @since 2012-8-10
 * @version 1.0
 */
@Service("smsService")
public class SMSService {
    //TODO
	private static Logger logger = Logger.getLogger(SMSService.class);

	private String coresysIp;

	private int coresysPort;

	private int timeout;

	private String dataKey;

	private String macKey;

	private boolean checkDataKey;
	
	private boolean checkMacKey;
	
	private static String xmapHead = "head";
	
	/**
	 * @since 2012-8-10
	 * @param reqBody Object
	 * @param resBody Object
	 * @throws DomainException Exception
	 * <br><b>author: nan.jiang</b>
	 * <br>创建时间：2012-8-10 上午10:24:40
	 */
	public String doService(Object reqBody, Object resBody) {

		String reqBodyName = reqBody.getClass().getName();
		
		String resBodyName = resBody.getClass().getName();
		
		reqBodyName = reqBodyName.substring(reqBodyName.lastIndexOf('.') + 1, reqBodyName.length());
		
		resBodyName = resBodyName.substring(resBodyName.lastIndexOf('.') + 1, resBodyName.length());
		String res = "";
		try {

			String patha = getClass().getClassLoader().getResource("xmap.xml").getPath();
			XMapper.init(patha);
			byte[] reqBodyBA = (byte[]) XMapper.map(reqBodyName, reqBody);
			
			
			byte[] resBodyBA;
			
			//resBodyBA = send(reqBodyBA, coresysIp, coresysPort, timeout);
			coresysIp = ResourceUtil.getConfigByName("coresysIp");
			coresysPort = Integer.parseInt(ResourceUtil.getConfigByName("coresysPort"));
			resBodyBA = send(reqBodyBA, coresysIp, coresysPort, 1);
			//System.out.println("resBodyName:" + resBodyName);
			res = new String(resBodyBA,"UTF-8");
			//String resa = new String(resBodyBA);
			//String resb = new String(resBodyBA,"ISO-8859-1");
			DumpUtils.dump(resBodyBA);

			if (resBodyBA.length != 0) {
				
				XMapper.unmap(resBodyName, resBodyBA, resBody);
				
			}

		} catch (Exception ex) {
			

			
		}
		return res;
	}

	
private byte[] send(byte[] reqBodyBA, String ip, int port, int timeout) throws Exception {
		
		byte[] resBodyBA = null;
		
		STCPSocket client = STCPSocketFactory.getSTCPSocketFactory().getSTCPSocket();
		
		try {
		
			//logger.info("ip: " + ip + "; port: " + port);
			
			client.connect(ip, port, timeout);
			
			int bodyLen = reqBodyBA.length;
			
			int totalLen = bodyLen;
			
			String lenStr = FillUtils.zeroFill(totalLen + "", 4);
			
			byte[] lenBA = lenStr.getBytes();
			
			client.write(lenBA);
			
			client.write(reqBodyBA);
			
			DumpUtils.dump(reqBodyBA);
			
			client.read(lenBA);
			
			lenStr = new String(lenBA);
			
			totalLen = Integer.parseInt(lenStr);
			
			bodyLen = totalLen - 4 - 16;
			
			resBodyBA = new byte[bodyLen];
			
			client.read(resBodyBA);
			
			byte[] resMacBA = new byte[16];
			
			client.read(resMacBA);

			DumpUtils.dump(resBodyBA);
			
			DumpUtils.dump(resMacBA);
		} finally {
			
			STCPSocketFactory.getSTCPSocketFactory().releaseSTCPSocket(client);
			
		}
		
		return resBodyBA;
	}





	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public static void setLogger(Logger logger) {
		SMSService.logger = logger;
	}

	/**
	 * @return the coresysIp
	 */
	public String getCoresysIp() {
		return coresysIp;
	}

	/**
	 * @param coresysIp the coresysIp to set
	 */
	public void setCoresysIp(String coresysIp) {
		this.coresysIp = coresysIp;
	}

	/**
	 * @return the coresysPort
	 */
	public int getCoresysPort() {
		return coresysPort;
	}

	/**
	 * @param coresysPort the coresysPort to set
	 */
	public void setCoresysPort(int coresysPort) {
		this.coresysPort = coresysPort;
	}

	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the dataKey
	 */
	public String getDataKey() {
		return dataKey;
	}

	/**
	 * @param dataKey the dataKey to set
	 */
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	/**
	 * @return the macKey
	 */
	public String getMacKey() {
		return macKey;
	}

	/**
	 * @param macKey the macKey to set
	 */
	public void setMacKey(String macKey) {
		this.macKey = macKey;
	}

	/**
	 * @return the checkDataKey
	 */
	public boolean isCheckDataKey() {
		return checkDataKey;
	}

	/**
	 * @param checkDataKey the checkDataKey to set
	 */
	public void setCheckDataKey(boolean checkDataKey) {
		this.checkDataKey = checkDataKey;
	}

	/**
	 * @return the checkMacKey
	 */
	public boolean isCheckMacKey() {
		return checkMacKey;
	}

	/**
	 * @param checkMacKey the checkMacKey to set
	 */
	public void setCheckMacKey(boolean checkMacKey) {
		this.checkMacKey = checkMacKey;
	}

	/**
	 * @return the xmapHead
	 */
	public static String getXmapHead() {
		return xmapHead;
	}

	/**
	 * @param xmapHead the xmapHead to set
	 */
	public static void setXmapHead(String xmapHead) {
		SMSService.xmapHead = xmapHead;
	}
	

 
}
