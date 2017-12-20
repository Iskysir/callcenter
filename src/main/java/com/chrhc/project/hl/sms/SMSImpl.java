package com.chrhc.project.hl.sms;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 发送短信 <br>
 * 创建日期：2012-8-10 <br>
 * <b>Copyright 2012 上海华腾软件系统有限公司　All Rights Reserved</b>
 * 
 * @author nan.jiang
 * @since 2012-8-10
 * @version 1.0
 */
@Service("smsTCP")
public class SMSImpl implements ISMS {
	private static Logger logger = Logger.getLogger(SMSImpl.class);
	@Autowired
	private SMSService smsService;

	/**
	 * @return the smsService
	 */
	public SMSService getSmsService() {
		return smsService;
	}

	/**
	 * @param smsService
	 *            the smsService to set
	 */
	public void setSmsService(SMSService smsService) {
		this.smsService = smsService;
	}

	 
 
	public String doSendMessage(SmsReqMsg smsReqMsg) throws Exception {
		SmsResMsg resBody = new SmsResMsg();
		String res = "";
		try {
			 res = smsService.doService(smsReqMsg, resBody);
			return  res;
		} catch (Exception e) {
			 logger.error("doRegisterUser:" + e);
			// throw new DomainException(e);
		}
	return res;
	}

}
