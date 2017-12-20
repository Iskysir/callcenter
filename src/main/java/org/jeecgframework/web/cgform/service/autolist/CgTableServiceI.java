package org.jeecgframework.web.cgform.service.autolist;

import java.util.List;
import java.util.Map;

import org.jeecgframework.web.cgform.entity.config.CgFormHeadEntity;
/**
 * 
 * @Title:CgTableServiceI
 * @description:动态表服务接口
 * @author 赵俊夫
 * @date Jul 5, 2013 3:01:55 PM
 * @version V1.0
 */
public interface CgTableServiceI {
	/**
	 * 单表条件分页查询
	 * @param table 表
	 * @param field 字段（以逗号分隔）
	 * @param params 查询条件
	 * @param page 分页
	 * @param rows 页面大小
	 * @return
	 */
	public List querySingle(String table,String field,Map params, int page,int rows);
	/**
	 * 单表条件分页查询+排序
	 * @param table 表
	 * @param field 字段（以逗号分隔）
	 * @param params 查询条件
	 * @param sort 排序字段
	 * @param order 排序规则
	 * @param page 分页
	 * @param rows 页面大小
	 * @return
	 */
	public List querySingle(String table,String field,Map params,String sort,String order, int page,int rows);
	/**
	 * 获得数据大小
	 * @param table 表
	 * @param field 字段（以逗号分隔）
	 * @param params 查询条件
	 * @param page 分页
	 * @param rows 页面大小
	 * @return
	 */
	public Long getQuerySingleSize(String table,String field,Map params);
	/**
	 * 删除单条数据
	 * @param table 表
	 * @param id 主键
	 * @return
	 */
	public boolean delete(CgFormHeadEntity head,String table,Object id);
	/**
	 * 删除多条数据
	 * @param table 表
	 * @param ids 主键串,以逗号分隔
	 * @return
	 */
	public boolean deleteBatch(CgFormHeadEntity head,String table,String[] ids);
	/**
	 * 查询上一条下一条id
	 * @param table 表
	 * @param  
	 * @return
	 */
	public String queryNextId(String table, Map params, String sort, String order, String id ,int steptNum);
	
}
