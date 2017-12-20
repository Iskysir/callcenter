package com.chrhc.project.sc.endowlogout.service.impl;
import com.chrhc.project.sc.endowlogout.service.ScEndowlogoutServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.chrhc.project.sc.endowlogout.entity.ScEndowlogoutEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("scEndowlogoutService")
@Transactional
public class ScEndowlogoutServiceImpl extends CommonServiceImpl implements ScEndowlogoutServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((ScEndowlogoutEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((ScEndowlogoutEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((ScEndowlogoutEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScEndowlogoutEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScEndowlogoutEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScEndowlogoutEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,ScEndowlogoutEntity t){
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
 		sql  = sql.replace("#{code}",String.valueOf(t.getCode()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{pidcard}",String.valueOf(t.getPidcard()));
 		sql  = sql.replace("#{cancellreason}",String.valueOf(t.getCancellreason()));
 		sql  = sql.replace("#{cancelldate}",String.valueOf(t.getCancelldate()));
 		sql  = sql.replace("#{beniname}",String.valueOf(t.getBeniname()));
 		sql  = sql.replace("#{benisex}",String.valueOf(t.getBenisex()));
 		sql  = sql.replace("#{benibirthday}",String.valueOf(t.getBenibirthday()));
 		sql  = sql.replace("#{beniidcard}",String.valueOf(t.getBeniidcard()));
 		sql  = sql.replace("#{relation}",String.valueOf(t.getRelation()));
 		sql  = sql.replace("#{paddress}",String.valueOf(t.getPaddress()));
 		sql  = sql.replace("#{telphone}",String.valueOf(t.getTelphone()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{nominatedbank}",String.valueOf(t.getNominatedbank()));
 		sql  = sql.replace("#{bankcode}",String.valueOf(t.getBankcode()));
 		sql  = sql.replace("#{rk_id}",String.valueOf(t.getRkId()));
 		sql  = sql.replace("#{endowid}",String.valueOf(t.getEndowid()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}