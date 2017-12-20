package com.chrhc.project.sc.ewmcode.service;
import com.chrhc.project.sc.ewmcode.entity.ScEwmnrxpzEntity;
import com.chrhc.project.sc.ewmcode.entity.ScEwmnrxpzsubEntity;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;
import java.io.Serializable;

public interface ScEwmnrxpzServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(ScEwmnrxpzEntity scEwmnrxpz,
	        List<ScEwmnrxpzsubEntity> scEwmnrxpzsubList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(ScEwmnrxpzEntity scEwmnrxpz,
	        List<ScEwmnrxpzsubEntity> scEwmnrxpzsubList);
	public void delMain (ScEwmnrxpzEntity scEwmnrxpz);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScEwmnrxpzEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScEwmnrxpzEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScEwmnrxpzEntity t);
 	
 	/**
 	 * 取得表字段信息
 	 * @param tablename
 	 * @return
 	 */
 	public List<Map<String, Object>> getTableField(String tablename);
 	
	/**
	 * 取得关联信息记录
	 * @param mainTable
	 * @param zTable
	 * @param wjId
	 * @return
	 */
	public List<Map<String, Object>>  getGlcheck(String mainTable,String zTable,String wjIdName,String id);
}
