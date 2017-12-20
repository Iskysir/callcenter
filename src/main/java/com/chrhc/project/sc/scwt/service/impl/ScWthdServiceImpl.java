package com.chrhc.project.sc.scwt.service.impl;
import com.chrhc.project.sc.scwt.service.ScWthdServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.chrhc.project.sc.scwt.entity.ScWthdEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("scWthdService")
@Transactional
public class ScWthdServiceImpl extends CommonServiceImpl implements ScWthdServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((ScWthdEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((ScWthdEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((ScWthdEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScWthdEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScWthdEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScWthdEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,ScWthdEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{team_id}",String.valueOf(t.getTeamId()));
 		sql  = sql.replace("#{act_datetime}",String.valueOf(t.getActDatetime()));
 		sql  = sql.replace("#{host_unit}",String.valueOf(t.getHostUnit()));
 		sql  = sql.replace("#{org_unit}",String.valueOf(t.getOrgUnit()));
 		sql  = sql.replace("#{act_address}",String.valueOf(t.getActAddress()));
 		sql  = sql.replace("#{act_scope}",String.valueOf(t.getActScope()));
 		sql  = sql.replace("#{act_title}",String.valueOf(t.getActTitle()));
 		sql  = sql.replace("#{pic_num}",String.valueOf(t.getPicNum()));
 		sql  = sql.replace("#{act_form}",String.valueOf(t.getActForm()));
 		sql  = sql.replace("#{act_result}",String.valueOf(t.getActResult()));
 		sql  = sql.replace("#{funds}",String.valueOf(t.getFunds()));
 		sql  = sql.replace("#{join_unit}",String.valueOf(t.getJoinUnit()));
 		sql  = sql.replace("#{act_content}",String.valueOf(t.getActContent()));
 		sql  = sql.replace("#{join_num}",String.valueOf(t.getJoinNum()));
 		sql  = sql.replace("#{viewer_num}",String.valueOf(t.getViewerNum()));
 		sql  = sql.replace("#{act_num}",String.valueOf(t.getActNum()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{gisxy}",String.valueOf(t.getGisxy()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}