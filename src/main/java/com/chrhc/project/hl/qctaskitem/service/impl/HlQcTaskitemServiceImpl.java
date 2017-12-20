package com.chrhc.project.hl.qctaskitem.service.impl;
import com.chrhc.project.hl.qctaskitem.service.HlQcTaskitemServiceI;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.chrhc.project.hl.qctaskitem.entity.HlQcTaskitemEntity;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;

@Service("hlQcTaskitemService")
@Transactional
public class HlQcTaskitemServiceImpl extends CommonServiceImpl implements HlQcTaskitemServiceI {


	@Autowired
	private CommonService commonService;

	public<T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((HlQcTaskitemEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((HlQcTaskitemEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((HlQcTaskitemEntity)entity);
 	}

	@Override
	public List<Map<String, Object>> querySingle(String sql, Map params,String sort, String order, int page, int rows) {
		StringBuilder sqlB = new StringBuilder();
		sqlB.append(sql);
		this.dealQuerySql(params,sqlB);
		if(!StringUtil.isEmpty(sort)&& !StringUtil.isEmpty(order)){
			sqlB.append(" ORDER BY "+sort+" "+ order);
		}
		List<Map<String, Object>> result = commonService.findForJdbcParam(sqlB
				.toString(), page, rows);
		return result;
	}
	private void dealQuerySql( Map params,StringBuilder sqlB){
		if (params.size() >= 1) {
			sqlB.append(" WHERE 1=1 ");
			Iterator it = params.keySet().iterator();
			while (it.hasNext()) {
				String key = String.valueOf(it.next());
				String value = String.valueOf(params.get(key));
				if (!StringUtil.isEmpty(value) && !"null".equals(value)) {
						sqlB.append(" AND ");
						sqlB.append(" " + key +  value );
					}

			}
		}
	}

	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 *
	 * @return
	 */
 	public boolean doAddSql(HlQcTaskitemEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(HlQcTaskitemEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(HlQcTaskitemEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,HlQcTaskitemEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{version_num}",String.valueOf(t.getVersionNum()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{delflag}",String.valueOf(t.getDelflag()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{orderid}",String.valueOf(t.getOrderid()));
 		sql  = sql.replace("#{ordertype}",String.valueOf(t.getOrdertype()));
 		sql  = sql.replace("#{taskid}",String.valueOf(t.getTaskid()));
 		sql  = sql.replace("#{avsorce}",String.valueOf(t.getAvsorce()));
 		sql  = sql.replace("#{bza}",String.valueOf(t.getBza()));
 		sql  = sql.replace("#{bzb}",String.valueOf(t.getBzb()));
 		sql  = sql.replace("#{bzc}",String.valueOf(t.getBzc()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}