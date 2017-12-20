package org.jeecgframework.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.io.CopyStreamException;
import org.jeecgframework.core.common.exception.BusinessException;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

/**
 * FTP操作工具类
 * 
 * @author 杨菲
 * 
 */
public class FtpUtils {

	/** 服务器IP */
	private java.lang.String ip;
	/** FTP端口号 */
	private java.lang.Integer port;
	/** 传输超时时间 */
	private java.lang.Integer timeout;
	/** FTP登录名 */
	private java.lang.String username;
	/** FTP密码 */
	private java.lang.String password;
	/** 编码 */
	private java.lang.String encoding;
	/** 远程目录 */
	private java.lang.String path;
	/** 访问URL */
	private java.lang.String url;

	/**
	 * 上传文件
	 * 
	 * @param inputStream
	 *            ：文件输入流
	 * @param directory
	 *            ：文件存放路径
	 * @param fileName
	 *            ：上传文件名称
	 * @param realName
	 *            ：实际文件名称
	 * @param ftpClient
	 *            ：FTP
	 */
	public void upload(InputStream inputStream, String directory,
			String fileName, String realName, FTPClient ftpClient)
			throws Exception {
		try {

			String ftpFileName = fileName + ".ftptemp";

			// 文件名称使用数字+英文，使用汉字会有乱码
			boolean flag = ftpClient.storeFile(
					new String(ftpFileName.getBytes("utf-8"), "iso-8859-1"),
					inputStream);

			if (!flag) {
				throw new Exception("上传文件 " + realName + " 失败。");
			}

			boolean renameflag = ftpClient.rename(
					new String(ftpFileName.getBytes("utf-8"), "iso-8859-1"),
					new String(fileName.getBytes("utf-8"), "iso-8859-1"));

			if (!renameflag) {
				throw new Exception("上传文件 " + realName + "，重命名失败。");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			// 关闭连接
			IOUtils.closeQuietly(inputStream);
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param outputStream
	 *            ：文件输出流
	 * @param fileName
	 *            ：下载文件名称
	 * @return
	 * @throws Exception
	 */
	public void download(OutputStream outputStream, String directory,
			String fileName) throws Exception {
		FTPClient ftpClient = null;
		try {
			ftpClient = getFtpClient(directory);
			boolean fileIsExist = isExist(ftpClient, fileName);
			if (fileIsExist) {
				boolean flag = ftpClient.retrieveFile(fileName, outputStream);
				if (!flag) {
					throw new Exception("下载文件 " + fileName + " 失败。");
				}
			} else {
				throw new Exception("FTP服务器文件 " + fileName + " 不存在.......");
			}
		} catch (Exception e) {
			if (e instanceof CopyStreamException) {
				CopyStreamException copyStreamException = (CopyStreamException) e;
				System.out.println("CopyStreamException 已经传输的文件数 -> "
						+ copyStreamException.getTotalBytesTransferred());
			} else {
				System.out.println("ftp一般异常->" + e.getClass().getName());
			}
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			close(ftpClient);
		}
	}

	/**
	 * 根据文档全名删除文件
	 * 
	 * @param fileName
	 * @param directory
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public void delete(String directory, String fileName) throws Exception {
		FTPClient ftpClient = null;
		try {
			ftpClient = getFtpClient(directory);

			// 判断文件是否存在
			if (isExist(ftpClient, fileName)) {
				// 删除
				boolean flag = ftpClient.deleteFile(new String(fileName
						.getBytes("utf-8"), "iso-8859-1"));
				if (!flag) {
					throw new Exception("删除文件 " + fileName + " 失败。");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			// 关闭连接
			close(ftpClient);
		}
	}

	/**
	 * 获取文件的输入流
	 * 
	 * @param directory
	 * @param fileName
	 * @return
	 */
	public InputStream getAttachmentFile(String directory, String fileName)
			throws Exception {
		FTPClient ftpClient = null;
		InputStream inputStream = null;
		try {
			ftpClient = getFtpClient(directory);
			boolean fileIsExist = isExist(ftpClient, fileName);
			if (fileIsExist) {
				inputStream = ftpClient.retrieveFileStream(fileName);
			} else {
				throw new Exception("FTP服务器文件 " + fileName + " 不存在.......");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			// 关闭连接
			close(ftpClient);
		}
		return inputStream;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param ftpClient
	 * @param fileName
	 * @return
	 */
	public boolean isExist(FTPClient ftpClient, final String fileName)
			throws Exception {
		boolean flag = false;
		try {
			FTPFile[] ftpFiles = ftpClient.listFiles(null, new FTPFileFilter() {
				// @Override
				public boolean accept(FTPFile file) {
					if (file.getName().equals(fileName)) {
						return true;
					}
					return false;
				}
			});
			if (ftpFiles != null && ftpFiles.length > 0) {
				flag = true;
			}
		} catch (Exception e) {
			// System.out.println("-------query ftp file exist fail......");
			throw e;
		}

		return flag;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param directory
	 * @param fileName
	 * @return
	 */
	public boolean isExist(String directory, final String fileName) {
		boolean flag = false;
		FTPClient ftpClient = null;
		try {
			ftpClient = getFtpClient(directory);

			FTPFile[] ftpFiles = ftpClient.listFiles(null, new FTPFileFilter() {
				// @Override
				public boolean accept(FTPFile file) {
					if (file.getName().equals(fileName)) {
						return true;
					}
					return false;
				}
			});

			if (ftpFiles != null && ftpFiles.length > 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			close(ftpClient);
		}

		return flag;
	}

	/**
	 * 获取ftpClient某一目录下的文件信息
	 * 
	 * @param directory
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryFileInfo(String directory,
			final String fileName) throws Exception {
		FTPClient ftpClient = null;
		FTPFile[] ftpFiles = null;
		try {
			ftpClient = getFtpClient(directory);

			ftpFiles = ftpClient.listFiles(null, new FTPFileFilter() {
				// @Override
				public boolean accept(FTPFile file) {
					if (file.getName().equals(fileName)) {
						return true;
					}
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(ftpClient);
		}

		if (ftpFiles != null && ftpFiles.length > 0) {
			Map<String, Object> attributeMap = new HashMap<String, Object>();
			FTPFile ftpFile = ftpFiles[0];

			attributeMap.put("name", ftpFile.getName());
			attributeMap.put("size", ftpFile.getSize());
			attributeMap.put("base_directory", directory);
			attributeMap.put("last_modification_time", new DateTime(ftpFile
					.getTimestamp().getTime())
					.toString("yyyy-MM-dd HH:mm:ss.SSS"));

			return attributeMap;
		} else {
			return null;
		}
	}

	//下载文件到本地
	public boolean ftpDownFiles(String ftpfilepath, String localpath, String sFile) {
		
		boolean success = false;

		FTPClient ftpClient = null;
		FTPFile[] fs = null;
		
		try {
			ftpClient = getFtpClient(ftpfilepath);

			fs = ftpClient.listFiles();    
            for(FTPFile ff:fs){    
                if(ff.getName().equals(sFile)){    
                        
                    File localFile = new File(localpath+"/");  
                    if (!localFile.exists()) {  
                        localFile.mkdirs();  
                    }
                    OutputStream is = new FileOutputStream(localFile+"/"+ff.getName());    
                    ftpClient.retrieveFile(ff.getName(), is);    
                    is.close();    
                }
            }
            ftpClient.logout();
            success = true;
		 } catch (IOException e) {
             e.printStackTrace();
         } finally {
             if (ftpClient.isConnected()) {
                 try {
                	 ftpClient.disconnect();
                 	} catch (IOException ioe) {
                 }
             }
         }
		return success;

	}
	
	
	
	
	
	/**
	 * 获取ftp客户端连接
	 * 
	 * @param directory
	 *            ：文件存放路径
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	public FTPClient getFtpClient(String directory) throws SocketException,
			IOException {
		// 设置FTP连接信息
		setFTPInfo();

		// FTP
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(ip, port);
		ftpClient.setConnectTimeout(timeout);
		boolean loginFlag = ftpClient.login(username, password);
		if (!loginFlag) {
			throw new SocketException("ftp login error ,please check setting .");
		}

		// 设置文件上传目录
		if (StringUtils.hasLength(directory.trim())) {
			directory = path + "/"
					+ StringUtils.trimLeadingCharacter(directory, '/');
		} else {
			directory = path;
		}
		// 设置上传目录
		boolean flag = ftpClient.changeWorkingDirectory(directory);
		// 如果目录不存在，则创建目录
		if (!flag) {
			String fPath = null;
			for (String folderName : directory.split("/")) {
				if (fPath == null) {
					fPath = folderName;
				} else {
					fPath += "/" + folderName;
				}
				ftpClient.makeDirectory(fPath);
			}
			flag = ftpClient.changeWorkingDirectory(fPath);
		}

		ftpClient.setBufferSize(1024);
		ftpClient.setControlEncoding("utf-8");
		// 设置文件类型（二进制）
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		// 设置ftp上传模式为client向server传
		ftpClient.enterLocalPassiveMode();

		return ftpClient;
	}

	/**
	 * ftp客户端关闭
	 * 
	 * @param ftpClient
	 *            ：FTP
	 * @return
	 * @throws IOException
	 */
	public void close(FTPClient ftpClient) {
		if (ftpClient != null) {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				throw new BusinessException(e.getMessage());
			}
		}
	}
	
	/**
	 * 设置FTP连接信息
	 */
	private void setFTPInfo(){
		PropertiesUtil p = new PropertiesUtil("sysConfig.properties");
		this.setIp(p.readProperty("ftp.ip"));
		this.setPort(oConvertUtils.getInt(p.readProperty("ftp.port")) == 0 ? 21 : oConvertUtils.getInt(p.readProperty("ftp.port")));
		this.setTimeout(oConvertUtils.getInt(p.readProperty("ftp.timeout")) == 0 ? 21 : oConvertUtils.getInt(p.readProperty("ftp.timeout")));
		this.setUsername(p.readProperty("ftp.username"));
		this.setPassword(p.readProperty("ftp.password"));
		this.setEncoding(p.readProperty("ftp.encoding"));
		this.setPath(p.readProperty("ftp.path"));
		this.setUrl(p.readProperty("ftp.url"));
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

	public java.lang.String getUsername() {
		return username;
	}

	public void setUsername(java.lang.String username) {
		this.username = username;
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
