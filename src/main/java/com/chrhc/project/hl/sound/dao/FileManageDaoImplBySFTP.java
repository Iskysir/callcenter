package com.chrhc.project.hl.sound.dao;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class FileManageDaoImplBySFTP extends FileManageDaoTemplate {
    /**
     * 获取连接通道
     * @return
     * @throws JSchException
     */
    public ChannelSftp getChannel() throws JSchException {
        JSch jsch = new JSch(); // 创建JSch对象
        Session session = jsch.getSession(userName, host, port); // 根据用户名，主机ip，端口获取一个Session对象
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
     * @throws Exception
     */
    public void closeChannel(Channel channel) {
    	if(channel==null){
    		return ;
    	}
    	
    	channel.disconnect();
    	
    	try {
	    	//关闭会话
			if (channel.getSession() != null) {
				channel.getSession().disconnect();
			}
		} catch (JSchException e) {
			e.printStackTrace();
		}
    }
    /**
     * 上传文件到文件服务器
     * @param inputStream
     * @param fileName
     * @throws JSchException 
     */
    @Override
    public void upload(InputStream inputStream,String directory,String fileName) {
		ChannelSftp channelSftp = null;
		try {
			channelSftp = getChannel();
			channelSftp.put(inputStream, targetBaseLocation+directory+fileName);
			channelSftp.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeChannel(channelSftp);
		}
    }
    /**
     * 从文件服务器上下载文件
     */
    @Override
    public void download(OutputStream outputStream,String fileName){
		ChannelSftp channelSftp = null;
		try {
			channelSftp = getChannel();
			//channelSftp.get(targetBaseLocation+directory+fileName, outputStream, null,ChannelSftp.OVERWRITE , 0);
			channelSftp.get(targetBaseLocation+fileName, outputStream, null,ChannelSftp.OVERWRITE , 0);
		} catch (Exception e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeChannel(channelSftp);
		}
    }
    
    public InputStream getAttachmentFile(String directory,String fileName){
    	return null ;
	}
    
	@Override
	public void delete(String directory,String fileName) {
		ChannelSftp channelSftp = null;
		try {
			channelSftp = getChannel();
			channelSftp.rm(targetBaseLocation+directory+fileName);
		} catch (Exception e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeChannel(channelSftp);
		}
	}
}
