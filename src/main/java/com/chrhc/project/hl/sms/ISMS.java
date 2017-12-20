package com.chrhc.project.hl.sms;


/**
 * <br>创建日期：2012-8-10
 * <br><b>Copyright 2012 上海华腾软件系统有限公司　All Rights Reserved</b>
 * @author nan.jiang
 * @since 2012-8-10
 * @version 1.0
 */
public interface ISMS {
	
   
	/**
	 * @param smsReqMsg 
	 * @since 2012-8-10
	 * @param transInfo
	 * @return ResultDataVo
	 * @throws Exception 
	 * <br><b>author: nan.jiang</b>
	 * <br>创建时间：2012-8-10 上午10:10:14
	 */
	public String doSendMessage(SmsReqMsg smsReqMsg) throws Exception;
	
	 }
