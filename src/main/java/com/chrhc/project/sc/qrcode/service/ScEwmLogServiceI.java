package com.chrhc.project.sc.qrcode.service;
import com.chrhc.project.sc.qrcode.entity.ScEwmLogEntity;

import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;

public interface ScEwmLogServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScEwmLogEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScEwmLogEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScEwmLogEntity t);
 	/**
 	 * 通用查询
 	 * @param sql
 	 * @return
 	 */
 	public List  GeneralQuerry(String sql);
 	//更具名值对查询
 	public  <T> List<T> findByProperty(Class<T> entityclass,String propertyname,Object value);
}
