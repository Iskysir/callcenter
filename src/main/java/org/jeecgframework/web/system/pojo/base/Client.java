package org.jeecgframework.web.system.pojo.base;

import java.util.Map;

/**
 * 在线用户对象
 * 
 * @author JueYue
 * @date 2013-9-28
 * @version 1.0
 */
public class Client implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private TSUser user;

	private Map<String, TSFunction> functions;
	/**
	 * 用户IP
	 */
	private java.lang.String ip;
	/**
	 *登录时间
	 */
	private java.util.Date logindatetime;

	/**
	 * 保存单点登录密码
	 */
	private String singleloginpwd;

	public TSUser getUser() {
		return user;
	}

	public String getSingleloginpwd() {
		return singleloginpwd;
	}

	public void setSingleloginpwd(String singleloginpwd) {
		this.singleloginpwd = singleloginpwd;
	}

	public void setUser(TSUser user) {
		this.user = user;
	}


	public Map<String, TSFunction> getFunctions() {
		return functions;
	}

	public void setFunctions(Map<String, TSFunction> functions) {
		this.functions = functions;
	}

	public java.lang.String getIp() {
		return ip;
	}

	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}

	public java.util.Date getLogindatetime() {
		return logindatetime;
	}

	public void setLogindatetime(java.util.Date logindatetime) {
		this.logindatetime = logindatetime;
	}


}
