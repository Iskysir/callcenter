package com.chrhc.project.sc.endowlogout.service;
import com.chrhc.project.sc.endowlogout.entity.ScEndowlogoutEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface ScEndowlogoutServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScEndowlogoutEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScEndowlogoutEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScEndowlogoutEntity t);
}
