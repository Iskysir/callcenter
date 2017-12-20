package com.chrhc.project.hl.sound.dao;

import org.springframework.util.StringUtils;

public abstract class FileManageDaoTemplate implements FileManageDao {
	//主机ip
    public String host="" ;
    //端口号，默认为22
    public int port = 22 ;
    //服务器用户名,默认为root
    public String userName ;
    //服务器密码
    public String password ;
    //服务器上传地址
    public String targetBaseLocation ="" ;
    //服务器连接超时时间(ms)，默认60000
    public int timeout = 60000;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public String getTargetBaseLocation() {
		return targetBaseLocation;
	}
	public void setTargetBaseLocation(String targetBaseLocation) {
		if(!StringUtils.hasLength(targetBaseLocation)){
			return ;
		}
		targetBaseLocation = StringUtils.trimLeadingCharacter(targetBaseLocation, '/') ;
		targetBaseLocation = StringUtils.trimTrailingCharacter(targetBaseLocation, '/') ;
		
		this.targetBaseLocation = targetBaseLocation ;
	}
}
