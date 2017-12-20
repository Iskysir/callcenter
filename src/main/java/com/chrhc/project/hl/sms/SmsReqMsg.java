package com.chrhc.project.hl.sms;
/**
 * @类说明:短信报文请求交易类
 * @创建人:111111
 * @创建日期:2012-4-20
 */
public class SmsReqMsg {

	private String txnCode;

	private String intTxnTm;

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
	 * @return the intTxnTm
	 */
	public String getIntTxnTm() {
		return intTxnTm;
	}

	/**
	 * @param intTxnTm the intTxnTm to set
	 */
	public void setIntTxnTm(String intTxnTm) {
		this.intTxnTm = intTxnTm;
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

 

}
