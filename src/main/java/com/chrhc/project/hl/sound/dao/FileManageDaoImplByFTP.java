package com.chrhc.project.hl.sound.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

public class FileManageDaoImplByFTP extends FileManageDaoTemplate {
	/**
	 * 获取ftp客户端连接
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	public FTPClient getFtpClient(String directory) throws SocketException, IOException{
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(host, port);
		ftpClient.setConnectTimeout(timeout);
		ftpClient.login(userName, password);
		//设置文件上传目录
		if(StringUtils.hasLength(directory.trim())){
			directory = targetBaseLocation+"/"+StringUtils.trimLeadingCharacter(directory, '/');
		}else{
			directory = targetBaseLocation ;
		}
		//设置上传目录
		boolean flag = ftpClient.changeWorkingDirectory(directory) ;
		//如果目录不存在，则创建目录
		if(!flag){
			String path = null ;
			for (String folderName : directory.split("/")) {
				if(path==null){
					path = folderName ;
				}else{
					path += "/"+folderName ;
				}
				ftpClient.makeDirectory(path);
			}
			flag = ftpClient.changeWorkingDirectory(path) ;
		}
		
		ftpClient.setBufferSize(1024); 
		ftpClient.setControlEncoding("utf-8");
		//设置文件类型（二进制）       
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
		return ftpClient ;
	}

	@Override
	public void upload(InputStream inputStream,String directory, String fileName) {
		FTPClient ftpClient = null ;
		try {
			ftpClient = getFtpClient(directory);
			//文件名称使用数字+英文，使用汉字会有乱码
			ftpClient.storeFile(new String(fileName.getBytes("utf-8"),"iso-8859-1"), inputStream); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭连接
	        IOUtils.closeQuietly(inputStream);       
	        close(ftpClient); 
		}
	}

	/*@Override
	public void download(OutputStream outputStream,String directory, String fileName){
		FTPClient ftpClient = null ;
		try {
			ftpClient = getFtpClient(directory);
			ftpClient.retrieveFile(fileName, outputStream) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close(ftpClient);    
		}
	}*/
	@Override
	public void download(OutputStream outputStream, String fileName){
		FTPClient ftpClient = null ;
		try {
			ftpClient = getFtpClient("");
			ftpClient.retrieveFile(fileName, outputStream) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close(ftpClient);
		}
	}

	@Override
	public void delete(String directory, String fileName) {
		FTPClient ftpClient = null ;
		try {
			ftpClient = getFtpClient(directory);
			ftpClient.deleteFile(new String(fileName.getBytes("utf-8"),"iso-8859-1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭连接
			close(ftpClient);  
		}
	}
	
	public InputStream getAttachmentFile(String directory,String fileName) throws Exception{
		FTPClient ftpClient = null ;
		InputStream inputStream = null; 
		try {
			ftpClient = getFtpClient(directory);
			boolean fileIsExist = isExist(ftpClient, fileName) ;
			if(fileIsExist){
				inputStream = ftpClient.retrieveFileStream(fileName);
			}else{
				throw new Exception("FTP服务器文件 "+fileName+" 不存在.......") ;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			throw e ;
		}finally{
			//关闭连接
			close(ftpClient);  
		}
		return inputStream ;
	}
	
	public void close(FTPClient ftpClient){
		if(ftpClient!=null){
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 判断ftpClient当前所在目录下，名称为fileName的文件是否存在
	 * @param ftpClient
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public boolean isExist(FTPClient ftpClient,final String fileName) throws Exception {
		boolean flag = false ;
		try {
			FTPFile[] ftpFiles = ftpClient.listFiles(null, new FTPFileFilter() {
				@Override
				public boolean accept(FTPFile file) {
					if(file.getName().equals(fileName)){
						return true ;
					}
					return false ;
				}
			}) ;
			
			if(ftpFiles!=null&&ftpFiles.length>0){
				flag = true ;
			}
		} catch (Exception e) {
			System.out.println("-------query ftp file exist fail......");
			throw e ;
		}
		
		return flag ;
	}

}
