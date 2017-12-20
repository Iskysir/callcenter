package org.jeecgframework.core.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SFTPUtil {

	/** 服务器IP */
	private java.lang.String ip;
	/** FTP端口号 */
	private java.lang.Integer port;
	/** 传输超时时间 */
	private java.lang.Integer timeout;
	/** FTP登录名 */
	private java.lang.String userName;
	/** FTP密码 */
	private java.lang.String password;
	/** 编码 */
	private java.lang.String encoding;
	/** 远程目录 */
	private java.lang.String path;
	/** 访问URL */
	private java.lang.String url;

	/**
	 * 设置SFTP连接信息
	 */
	private void setSFTPInfo() {
		PropertiesUtil p = new PropertiesUtil("sysConfig.properties");
		this.setIp(p.readProperty("sftp.ip"));
		this.setPort(oConvertUtils.getInt(p.readProperty("sftp.port")) == 0 ? 21
				: oConvertUtils.getInt(p.readProperty("sftp.port")));
		this.setTimeout(oConvertUtils.getInt(p.readProperty("sftp.timeout")) == 0 ? 21
				: oConvertUtils.getInt(p.readProperty("sftp.timeout")));
		this.setUserName(p.readProperty("sftp.username"));
		this.setPassword(p.readProperty("sftp.password"));
		this.setEncoding(p.readProperty("sftp.encoding"));
		this.setPath(p.readProperty("sftp.path"));
		this.setUrl(p.readProperty("sftp.url"));
	}

	/**
	 * 获取连接通道
	 * 
	 * @return
	 * @throws JSchException
	 */
	public ChannelSftp getChannel() throws JSchException {
		// 设置SFTP连接信息
		setSFTPInfo();

		JSch jsch = new JSch(); // 创建JSch对象
		Session session = jsch.getSession(userName, ip, port); // 根据用户名，主机ip，端口获取一个Session对象
		if (password != null) {
			session.setPassword(password); // 设置密码
		}
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config); // 为Session对象设置properties
		session.setTimeout(timeout); // 设置timeout时间
		session.connect(); // 通过Session建立链接
		Channel channel = session.openChannel("sftp"); // 打开SFTP通道
		channel.connect(); // 建立SFTP通道的连接
		return (ChannelSftp) channel;
	}

	/**
	 * 关闭连接通道
	 * 
	 * @throws Exception
	 */
	public void closeChannel(Channel channel) {
		if (channel == null) {
			return;
		}

		channel.disconnect();

		try {
			// 关闭会话
			if (channel.getSession() != null) {
				channel.getSession().disconnect();
			}
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传文件到文件服务器
	 * 
	 * @param inputStream
	 * @param fileName
	 * @throws Exception
	 * @throws JSchException
	 */
	public void upload(InputStream inputStream, String directory,
			String fileName) throws Exception {
		ChannelSftp channelSftp = null;
		try {
			channelSftp = getChannel();
			channelSftp.put(inputStream, path + directory + fileName);
			channelSftp.quit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeChannel(channelSftp);
		}
	}

	/**
	 * 从文件服务器上下载文件
	 * 
	 * @throws Exception
	 */
	public void download(OutputStream outputStream, String directory,
			String fileName) throws Exception {
		ChannelSftp channelSftp = null;
		try {
			channelSftp = getChannel();
			channelSftp.get(path + directory + fileName, outputStream, null,
					ChannelSftp.OVERWRITE, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			closeChannel(channelSftp);
		}
	}

	public InputStream getAttachmentFile(String directory, String fileName)
			throws Exception {
		return null;
	}

	public void delete(String directory, String fileName) throws Exception {
		ChannelSftp channelSftp = null;
		try {
			channelSftp = getChannel();
			channelSftp.rm(path + directory + fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			closeChannel(channelSftp);
		}
	}

	public boolean isExist(String directory, String fileName) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public java.lang.String getIp() {
		return ip;
	}

	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}

	public java.lang.Integer getPort() {
		return port;
	}

	public void setPort(java.lang.Integer port) {
		this.port = port;
	}

	public java.lang.Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(java.lang.Integer timeout) {
		this.timeout = timeout;
	}

	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public java.lang.String getPassword() {
		return password;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	public java.lang.String getEncoding() {
		return encoding;
	}

	public void setEncoding(java.lang.String encoding) {
		this.encoding = encoding;
	}

	public java.lang.String getPath() {
		return path;
	}

	public void setPath(java.lang.String path) {
		this.path = path;
	}

	public java.lang.String getUrl() {
		return url;
	}

	public void setUrl(java.lang.String url) {
		this.url = url;
	}
}
