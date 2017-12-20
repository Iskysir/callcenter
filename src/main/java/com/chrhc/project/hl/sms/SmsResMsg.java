package com.chrhc.project.hl.sms;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

//import com.huateng.util.ConvertUtil;


/**
 * @类说明:短信交易应答
 * @创建人:111111
 * @创建日期:2012-4-20
 */
public class SmsResMsg {
	private String txnCode;
	private String rspCode;
	private String intTxnSeq;
	private String smsNumber;
	private String smsContent;
	 

	/**
	 * @return the txnCode
	 */
	public String getTxnCode() {
		return txnCode;
	}


	/**
	 * @param txnCode the txnCode to set
	 */
	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}


	/**
	 * @return the rspCode
	 */
	public String getRspCode() {
		return rspCode;
	}


	/**
	 * @param rspCode the rspCode to set
	 */
	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}


	/**
	 * @return the intTxnSeq
	 */
	public String getIntTxnSeq() {
		return intTxnSeq;
	}


	/**
	 * @param intTxnSeq the intTxnSeq to set
	 */
	public void setIntTxnSeq(String intTxnSeq) {
		this.intTxnSeq = intTxnSeq;
	}


	/**
	 * @return the smsNumber
	 */
	public String getSmsNumber() {
		return smsNumber;
	}


	/**
	 * @param smsNumber the smsNumber to set
	 */
	public void setSmsNumber(String smsNumber) {
		this.smsNumber = smsNumber;
	}


	/**
	 * @return the smsContent
	 */
	public String getSmsContent() {
		return smsContent;
	}


	/**
	 * @param smsContent the smsContent to set
	 */
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}


	/**
	 * @since 2012-8-10
	 * @param obj Object
	 * @return 
	 * <br><b>author: nan.jiang</b>
	 * <br>创建时间：2012-8-10 上午10:07:55
	 */
	public static String toString(Object obj) {
		return ToStringBuilder.reflectionToString(obj,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
