package com.chrhc.project.sc.jzw.service;
import com.chrhc.project.sc.jzw.entity.ScJzwglEntity;
import com.chrhc.project.sc.jzw.entity.ScJzwdyglEntity;

import java.util.List;
import org.jeecgframework.core.common.service.CommonService;
import java.io.Serializable;

public interface ScJzwglServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(ScJzwglEntity scJzwgl,
	        List<ScJzwdyglEntity> scJzwdyglList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(ScJzwglEntity scJzwgl,
	        List<ScJzwdyglEntity> scJzwdyglList);
	public void delMain (ScJzwglEntity scJzwgl);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScJzwglEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScJzwglEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScJzwglEntity t);
}
