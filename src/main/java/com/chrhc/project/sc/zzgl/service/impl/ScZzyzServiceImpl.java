package com.chrhc.project.sc.zzgl.service.impl;
import com.chrhc.project.sc.zzgl.service.ScZzyzServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.chrhc.project.sc.zzgl.entity.ScZzyzEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("scZzyzService")
@Transactional
public class ScZzyzServiceImpl extends CommonServiceImpl implements ScZzyzServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((ScZzyzEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((ScZzyzEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((ScZzyzEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScZzyzEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScZzyzEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScZzyzEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,ScZzyzEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{zzbh}",String.valueOf(t.getZzbh()));
 		sql  = sql.replace("#{yzjg}",String.valueOf(t.getYzjg()));
 		sql  = sql.replace("#{yzsj}",String.valueOf(t.getYzsj()));
 		sql  = sql.replace("#{scbj}",String.valueOf(t.getScbj()));
 		sql  = sql.replace("#{version_num}",String.valueOf(t.getVersionNum()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{bz}",String.valueOf(t.getBz()));
 		sql  = sql.replace("#{zzdyid}",String.valueOf(t.getZzdyid()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}