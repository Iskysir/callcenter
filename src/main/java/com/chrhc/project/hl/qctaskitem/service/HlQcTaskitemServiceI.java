package com.chrhc.project.hl.qctaskitem.service;
import com.chrhc.project.hl.qctaskitem.entity.HlQcTaskitemEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface HlQcTaskitemServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);

	/**
	 * 自已定义sql查询数据
	 * @param sql
	 * @param params
	 * @param page
	 * @param rows
     * @return
     */
	public List<Map<String, Object>> querySingle(String sql, Map params,String sort,String order,int page,int rows);
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(HlQcTaskitemEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(HlQcTaskitemEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(HlQcTaskitemEntity t);
}
