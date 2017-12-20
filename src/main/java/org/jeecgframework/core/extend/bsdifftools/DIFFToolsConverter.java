package org.jeecgframework.core.extend.bsdifftools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.PinyinUtil;


public class DIFFToolsConverter{
	/** DIFFTools bsdiff.exe路径 */
	private static String DIFF_PATH = DiffConStant.DIFFTOOLS_PATH;

	public void convert2DIFF(String serverPath, String clientPath, String diff, int sVersion, int cVersion) {
		File serverFile = new File(serverPath);
		File clientFile = new File(clientPath);
		File diffFile = new File(diff);
		
		if (!serverFile.exists()) {
			 org.jeecgframework.core.util.LogUtil.info("服务器版本文件不存在！");
			return;
		}
	
		if (!clientFile.exists()) {
			 org.jeecgframework.core.util.LogUtil.info("客户端版本文件不存在！");
			return;
		}
		
		if (diffFile.exists()) {
			 org.jeecgframework.core.util.LogUtil.info("差分文件已存在！");
			return;
		}
		String sFile = "upload/difffiles/" + serverPath.substring(serverPath.lastIndexOf("/")+1);
		String cFile = "upload/difffiles/" + clientPath.substring(clientPath.lastIndexOf("/")+1);
		
		//命令所在路径
		String commandPath = serverPath.substring(0, serverPath.indexOf("/upload"));
		
		String command = commandPath + "/bsdiff "+ serverPath + " " + clientPath + " " + commandPath+"/upload/difffiles/diff"+ sVersion + "_" + cVersion + ".patch";
		//String command = "bsdiff "+ serverPath + " " + clientPath + " " + serverPath.substring(0,serverPath.lastIndexOf("/")) + "/diff"+ sVersion + "_" + cVersion + ".patch";

		try {
			//Runtime.getRuntime().exec("cd "+commandPath);
			//Process process = Runtime.getRuntime().exec("cmd /c "+dir+ " cd " + commandPath + " " + command);
			Process process = Runtime.getRuntime().exec("cmd /c start " + command);

			try {
				InputStream fis = process.getInputStream();   
	            //用一个读输出流类去读    
	            BufferedReader br = new BufferedReader(new InputStreamReader(fis));   
	            String line = null;   
	            //逐行读取输出到控制台    
	            while ((line = br.readLine()) != null) {   
	                System.out.println(line);   
	            }   
				
				process.waitFor();
				org.jeecgframework.core.util.LogUtil.info("时间-------"+process.waitFor());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void convert2SWF(String inputFile, String extend) {
		String swfFile = PinyinUtil.getPinYinHeadChar(FileUtils.getFilePrefix2(inputFile)) + ".swf";
		//convert2SWF(inputFile, swfFile, extend);
	}
}
