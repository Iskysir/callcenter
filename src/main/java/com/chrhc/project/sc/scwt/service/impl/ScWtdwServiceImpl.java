package com.chrhc.project.sc.scwt.service.impl;
import com.chrhc.project.sc.scwt.service.ScWtdwServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.chrhc.project.sc.scwt.entity.ScWtdwEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("scWtdwService")
@Transactional
public class ScWtdwServiceImpl extends CommonServiceImpl implements ScWtdwServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((ScWtdwEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((ScWtdwEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((ScWtdwEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScWtdwEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScWtdwEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScWtdwEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,ScWtdwEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{unit_name}",String.valueOf(t.getUnitName()));
 		sql  = sql.replace("#{team_name}",String.valueOf(t.getTeamName()));
 		sql  = sql.replace("#{found_date}",String.valueOf(t.getFoundDate()));
 		sql  = sql.replace("#{team_type}",String.valueOf(t.getTeamType()));
 		sql  = sql.replace("#{level}",String.valueOf(t.getLevel()));
 		sql  = sql.replace("#{act_address}",String.valueOf(t.getActAddress()));
 		sql  = sql.replace("#{main_act}",String.valueOf(t.getMainAct()));
 		sql  = sql.replace("#{busi_scope}",String.valueOf(t.getBusiScope()));
 		sql  = sql.replace("#{act_area}",String.valueOf(t.getActArea()));
 		sql  = sql.replace("#{equip_sum}",String.valueOf(t.getEquipSum()));
 		sql  = sql.replace("#{act_project}",String.valueOf(t.getActProject()));
 		sql  = sql.replace("#{num}",String.valueOf(t.getNum()));
 		sql  = sql.replace("#{sub_num}",String.valueOf(t.getSubNum()));
 		sql  = sql.replace("#{team_intro}",String.valueOf(t.getTeamIntro()));
 		sql  = sql.replace("#{duty_name}",String.valueOf(t.getDutyName()));
 		sql  = sql.replace("#{contact_tele}",String.valueOf(t.getContactTele()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{gisxy}",String.valueOf(t.getGisxy()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}