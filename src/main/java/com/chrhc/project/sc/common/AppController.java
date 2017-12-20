package com.chrhc.project.sc.common;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.extend.bsdifftools.DIFFToolsConverter;
import org.jeecgframework.core.extend.bsdifftools.DiffConStant;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.FtpUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.entity.upload.CgUploadEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.jeecgframework.core.util.FtpUtils;


/**   
 * @Title: Controller
 * @Description: 证照模板
 * @author onlineGenerator
 * @date 2015-05-08 10:21:53
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/appController")
public class AppController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AppController.class);
 
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
 
	
	/**
	 * @return
	 */
	@RequestMapping(params = "checkUpdate")
	@ResponseBody
	public AjaxJson checkUpdate(String apkVersionCode, HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		try{
			Map dataMap = new HashMap();
			//差分包fth路径
			String diffVersion = "";
			//获得客户端版本号
			
			int clientVersion = Integer.parseInt(apkVersionCode);
			
			//取得服务器端最新版本号
			Map serverMap = systemService.findOneForJdbc(" select max(apk_version) as apk_version from sc_apk_manage  ");
			int serverVersion =  (int) serverMap.get("apk_version");
			
			//获得服务器的最新版本
			serverMap =  systemService.findOneForJdbc(" select * from sc_apk_manage where apk_version = ? " , serverVersion);
			
			List<CgUploadEntity> serverBeans = systemService.findByProperty(CgUploadEntity.class, "cgformId", serverMap.get("id"));
			
			//取得服务器端最新版本文件名
			String ftpFilePath = (String)serverMap.get("apk_file");
			
			String ftpPath = ftpFilePath.substring(0, ftpFilePath.lastIndexOf("/"));

			String serverfilename = ftpFilePath.substring(ftpFilePath.lastIndexOf("/")+1);
			
			if(serverVersion - clientVersion > 3){
				//返回服务器的完整版本
				dataMap.put("isDiff", false);
				dataMap.put("serverVersionCode",  serverMap.get("apk_version"));
				if(serverBeans != null && !serverBeans.isEmpty()){
					dataMap.put("apkUrl", MessageFormat.format("commonController.do?viewFile&fileid={0}&subclassname=c", serverBeans.get(0).getId()));
				}
			} else {
				//差分包文件名
				diffVersion = "diff"+serverVersion + "_" + clientVersion + ".patch";
				//ftp存放路径
				String directory ="upload/files/sc_apk_manage/";
				//差分包不存在
				if(!checkFileExist(diffVersion,ftpPath)){
					//客户端文件名
					Map clientMap = systemService.findOneForJdbc(" select * from sc_apk_manage where apk_version = ? " , clientVersion);
					//取得服务器端最新版本文件名
					String clientFilePath = (String)clientMap.get("apk_file");

					String clientfilename = clientFilePath.substring(clientFilePath.lastIndexOf("/")+1);
					
					FtpUtils ftp = new FtpUtils();
					//下载最新版本
					String localpath = (String)request.getSession().getServletContext().getRealPath("/") + "bsdifftools/upload/difffiles/";
					
					ftp.ftpDownFiles(ftpPath, localpath, serverfilename);
					//下载客户端版本
					ftp.ftpDownFiles(ftpPath, localpath, clientfilename);
					//作成差分文件
					DIFFToolsConverter  convert = new DIFFToolsConverter();
					convert.convert2DIFF(localpath+clientfilename, localpath+serverfilename, 
							diffVersion, serverVersion, clientVersion);
					
					File diffFile = new File(localpath+diffVersion);
					
					if(diffFile.exists()){
						
						FTPClient ftpClient=ftp.getFtpClient(directory);
						//上传文件
						 FileInputStream in = new FileInputStream(diffFile);
						//上传到ftp服务器
						try {
							ftp.upload(in, directory, diffVersion, "", ftpClient);
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("单个文件上传出错！！！");
						}
						if(in!=null){
							in.close();
						}
						//关闭ftp
						ftp.close(ftpClient); 
					}

				}
				//返回服务器的完整版本
				dataMap.put("isDiff", true);
				dataMap.put("serverVersionCode",  serverMap.get("apk_version"));
				//下载差分包到客户移动端
				dataMap.put("apkUrl", MessageFormat.format("commonController.do?viewFileftppath&contentfield={0}&subclassname=c&ftppath="+directory+diffVersion, ""));
			}
			j.setAttributes(dataMap);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	 
		return j;
	}
	
	/**
	 * @return
	 */
	@RequestMapping(params = "queryApkVersion")
	@ResponseBody
	public AjaxJson queryApkVersion(){
		AjaxJson j = new AjaxJson();
		try{
			
			Map map = systemService.findOneForJdbc(" select max(apk_version) as apk_version from sc_apk_manage  ");
			int version =  (int) map.get("apk_version");
			
	 
			Map dataMap = new HashMap();
			dataMap.put("version",  version );
 
			j.setAttributes(dataMap);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	 
		return j;
	}
	
	
	//判断ftp服务器上是否存在差分文件
	public boolean checkFileExist(String filename, String ftpfilepath) throws FileNotFoundException  {
		boolean flag;
		
		FtpUtils ftp = new FtpUtils();
		
		//String serverPath =request.getSession().getServletContext().getRealPath("/") + "bsdifftools/upload/difffiles/";
		
		flag = ftp.isExist(ftpfilepath, filename);

		return flag;
	}
	
}
