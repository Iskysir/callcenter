package org.jeecgframework.core.extend.bsdifftools;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ResourceUtil;


/**
 * 常量类
 */
public class DiffConStant {
	/**根目录*/
	public static final String BSDIFF_HOME;
	static{
		BSDIFF_HOME = ResourceUtil.getSessionattachmenttitle("");
	}
	/**文件上传保存根目录*/
	public static final String UPLOAD_BASE_DIR = "upload";
	
	public static final String DIFF_FILE = "difffiles";
	/**文件转换工具根目录*/
	public static final String DIFFTOOLS_BASE_DIR = "bsdifftools";
	/**BSDIFF*/
	public static final String DIFFTOOLS_PATH ="bsdiff.exe";
	/**BSPATCH*/
	public static final String PATCHTOOLS_GIF2SWF_PATH ="bspatch.exe";
	/**DIFF文件后缀*/
	public static final String DIFF_STUFFIX = "patch";
	public static String DIFFTOOLS_HOME="";
	/**
	 * 根据扩展名获取转换工具
	 * @param extend
	 * @return
	 */
	public static String getBiffToolsPath(HttpServletRequest request,String extend)
	{
		DIFFTOOLS_HOME=request.getSession().getServletContext().getRealPath("/") + DIFFTOOLS_BASE_DIR + UPLOAD_BASE_DIR + DIFF_FILE + "/";
		DIFFTOOLS_HOME += DIFFTOOLS_PATH;

		return DIFFTOOLS_HOME;
	}
	
	/**
	 * 获取转换文件所在路径
	 * @param extend
	 * @return
	 */
	public static String getBiffFilePath(String filename, HttpServletRequest request)
	{
		DIFFTOOLS_HOME=request.getSession().getServletContext().getRealPath("/") + DIFFTOOLS_BASE_DIR + UPLOAD_BASE_DIR + DIFF_FILE + "/";
		DIFFTOOLS_HOME += filename;
		return DIFFTOOLS_HOME;
	}
	
	/**
	 * 获取转换文件所在路径
	 * @param extend
	 * @return
	 */
	public static String getBiffPath(HttpServletRequest request)
	{
		DIFFTOOLS_HOME=request.getSession().getServletContext().getRealPath("/") + DIFFTOOLS_BASE_DIR + UPLOAD_BASE_DIR + DIFF_FILE + "/";
		return DIFFTOOLS_HOME;
	}
}
