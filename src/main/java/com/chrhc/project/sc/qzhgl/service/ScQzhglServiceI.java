package com.chrhc.project.sc.qzhgl.service;
import com.chrhc.project.sc.qzhgl.entity.ScQzhglEntity;
import com.chrhc.project.sc.qzhgl.entity.ScQzhglsubEntity;

import java.util.List;
import org.jeecgframework.core.common.service.CommonService;
import java.io.Serializable;

public interface ScQzhglServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(ScQzhglEntity scQzhgl,
	        List<ScQzhglsubEntity> scQzhglsubList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(ScQzhglEntity scQzhgl,
	        List<ScQzhglsubEntity> scQzhglsubList);
	public void delMain (ScQzhglEntity scQzhgl);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScQzhglEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScQzhglEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScQzhglEntity t);
}
