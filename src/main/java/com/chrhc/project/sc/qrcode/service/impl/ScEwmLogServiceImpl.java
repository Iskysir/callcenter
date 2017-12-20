package com.chrhc.project.sc.qrcode.service.impl;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

import com.chrhc.project.sc.qrcode.entity.ScEwmLogEntity;
import com.chrhc.project.sc.qrcode.service.ScEwmLogServiceI;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.io.Serializable;

@Service("scEwmLogService")
@Transactional
public class ScEwmLogServiceImpl extends CommonServiceImpl implements ScEwmLogServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((ScEwmLogEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((ScEwmLogEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((ScEwmLogEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScEwmLogEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScEwmLogEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScEwmLogEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,ScEwmLogEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{ yw_id}",String.valueOf(t.getYwId()));
 		sql  = sql.replace("#{yw_type}",String.valueOf(t.getYwType()));
 		sql  = sql.replace("#{yw_table}",String.valueOf(t.getYwTable()));
 		sql  = sql.replace("#{oper_times}",String.valueOf(t.getOperTimes()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{cardid}",String.valueOf(t.getCardid()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	/**
 	 * 根据实体字段名称与值查询数据
 	 */
 	public  <T> List<T> findByProperty(Class<T> entityclass,String propertyname,Object value){
 	return super.findByProperty(entityclass, propertyname, value);
 	}
 	/**
 	 * 通用查询方法（sql语句）
 	 * @param sql
 	 * @return
 	 */
 	public List  GeneralQuerry(String sql){
 		
 		return super.findListbySql(sql);
 	}
 	/**
 	 * 通用查询方法（Hql语句）
 	 * @param sql
 	 * @return
 	 */
 	public List  GeneralQuerry_hql(String hql){
 		
 		return super.findListbySql(hql);
 	}
 	
}