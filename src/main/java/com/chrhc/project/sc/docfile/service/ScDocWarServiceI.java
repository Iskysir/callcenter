package com.chrhc.project.sc.docfile.service;
import com.chrhc.project.sc.docfile.entity.ScDocWarEntity;
import com.chrhc.project.sc.docfile.entity.ScFileEntity;

import java.util.List;
import org.jeecgframework.core.common.service.CommonService;
import java.io.Serializable;

public interface ScDocWarServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(ScDocWarEntity scDocWar,
	        List<ScFileEntity> scFileList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(ScDocWarEntity scDocWar,
	        List<ScFileEntity> scFileList);
	public void delMain (ScDocWarEntity scDocWar) throws Exception ;
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScDocWarEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScDocWarEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScDocWarEntity t);
 	/**
 	 * 通用保存方式
 	 * @param entity
 	 * @return
 	 */
 	public <T> Serializable save_general(T entity);
 	/**
 	 * 删除附件
 	 * @param fileid cgformupload的id
 	 * @param dataid cgform的业务数据  id 
 	 * @param flag   Z 主表 F 附表 
 	 * @throws Exception 
 	 */
 	public  void del_pic(String fileid,String dataid,String flag) throws Exception;
 	
}
