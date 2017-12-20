package org.jeecgframework.core.common.dao;


import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;

import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.ImportFile;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.extend.template.Template;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;

public interface ICommonDao extends IGenericBaseCommonDao{
	
	
	/**
	 * admin账户密码初始化
	 * @param user
	 */
	public void pwdInit(TSUser user,String newPwd);
	/**
	 * 检查用户是否存在
	 * */
	public TSUser getUserByUserIdAndUserNameExits(TSUser user);
	
	/**
	 * 检查用户是否存在,CAS登录用
	 * @param doukai
	 * @return
	 */
	public TSUser getUserByUserIdAndUserNameExitsForCas(TSUser user);
	
	public String getUserRole(TSUser user);
	/**
	 * 文件上传
	 * @param request
	 */
	public <T> T  uploadFile(UploadFile uploadFile);
	public <T> T  uploadFileLocal(UploadFile uploadFile);
	
	/**
	 * 文件上传或预览
	 * @param uploadFile
	 * @return
	 */
	public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile);

	public Map<Object,Object> getDataSourceMap(Template template);
	/**
	 * 生成XML文件
	 * @param fileName XML全路径
	 */
	public HttpServletResponse createXml(ImportFile importFile);
	/**
	 * 解析XML文件
	 * @param fileName XML全路径
	 */
	public void parserXml(String fileName);
	public List<ComboTree> comTree(List<TSDepart> all,ComboTree comboTree);

//    update-begin--Author:zhangguoming  Date:20140819 for：添加recuisive方法参数
	/**
     * 根据模型生成ComboTree JSON
     *
     * @param all 全部对象
     * @param comboTreeModel 模型
     * @param in 已拥有的对象
     * @param recursive 是否递归加载所有子节点
     * @return List<ComboTree>
     */
	public  List<ComboTree> ComboTree(List all, ComboTreeModel comboTreeModel, List in, boolean recursive);
//    update-end--Author:zhangguoming  Date:20140819 for：添加recuisive方法参数
	public  List<TreeGrid> treegrid(List all,TreeGridModel treeGridModel);

	/**
	 * 下载文件：From FTP To Server
	 * @param svRealPath
	 * @param svRealPath2
	 * @param svRealPath3
	 * @return
	 * @throws FileNotFoundException
	 */
	public String downFTPToSV(String svRealPath, String svRealPath2, String svRealPath3) throws FileNotFoundException;

	/**
	 * 下载文件：From FTP To Server
	 * @param uploadFile
	 * @return
	 * @throws FileNotFoundException
	 */
	public String downFTPToSV(UploadFile uploadFile) throws FileNotFoundException;

	/**
	 * 
	 * @param directory
	 * @param fileName
	 * @param response
	 * @throws FileNotFoundException
	 */
	public void downFTPToJsp(String directory, String fileName,
			HttpServletResponse response);
}

