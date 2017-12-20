package com.chrhc.project.sc.zhyzh.service.impl;
import com.chrhc.project.sc.zhyzh.service.ScZhyzhfwjlServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.chrhc.project.sc.zhyzh.entity.ScZhyzhfwjlEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("scZhyzhfwjlService")
@Transactional
public class ScZhyzhfwjlServiceImpl extends CommonServiceImpl implements ScZhyzhfwjlServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((ScZhyzhfwjlEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((ScZhyzhfwjlEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((ScZhyzhfwjlEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScZhyzhfwjlEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScZhyzhfwjlEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScZhyzhfwjlEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,ScZhyzhfwjlEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{version_num}",String.valueOf(t.getVersionNum()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{serv_objtype}",String.valueOf(t.getServObjtype()));
 		sql  = sql.replace("#{is_team}",String.valueOf(t.getIsTeam()));
 		sql  = sql.replace("#{serv_datetime}",String.valueOf(t.getServDatetime()));
 		sql  = sql.replace("#{part_name}",String.valueOf(t.getPartName()));
 		sql  = sql.replace("#{memname}",String.valueOf(t.getMemname()));
 		sql  = sql.replace("#{mem_id}",String.valueOf(t.getMemId()));
 		sql  = sql.replace("#{num}",String.valueOf(t.getNum()));
 		sql  = sql.replace("#{hours}",String.valueOf(t.getHours()));
 		sql  = sql.replace("#{serv_addr}",String.valueOf(t.getServAddr()));
 		sql  = sql.replace("#{serv_content}",String.valueOf(t.getServContent()));
 		sql  = sql.replace("#{conf_body}",String.valueOf(t.getConfBody()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{gisxy}",String.valueOf(t.getGisxy()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}