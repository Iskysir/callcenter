package com.chrhc.project.sc.qzhgl.service;
import com.chrhc.project.sc.qzhgl.entity.ScYwlxpzxEntity;
import com.chrhc.project.sc.qzhgl.entity.ScYwlxpzxsubEntity;

import java.util.List;
import org.jeecgframework.core.common.service.CommonService;
import java.io.Serializable;

public interface ScYwlxpzxServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(ScYwlxpzxEntity scYwlxpzx,
	        List<ScYwlxpzxsubEntity> scYwlxpzxsubList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(ScYwlxpzxEntity scYwlxpzx,
	        List<ScYwlxpzxsubEntity> scYwlxpzxsubList);
	public void delMain (ScYwlxpzxEntity scYwlxpzx);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScYwlxpzxEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScYwlxpzxEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScYwlxpzxEntity t);
}
