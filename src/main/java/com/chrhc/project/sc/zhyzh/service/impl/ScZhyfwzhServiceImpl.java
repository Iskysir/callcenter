package com.chrhc.project.sc.zhyzh.service.impl;
import com.chrhc.project.sc.zhyzh.service.ScZhyfwzhServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.chrhc.project.sc.zhyzh.entity.ScZhyfwzhEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("scZhyfwzhService")
@Transactional
public class ScZhyfwzhServiceImpl extends CommonServiceImpl implements ScZhyfwzhServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((ScZhyfwzhEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((ScZhyfwzhEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((ScZhyfwzhEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScZhyfwzhEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScZhyfwzhEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScZhyfwzhEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,ScZhyfwzhEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{gender}",String.valueOf(t.getGender()));
 		sql  = sql.replace("#{cert_id}",String.valueOf(t.getCertId()));
 		sql  = sql.replace("#{nation}",String.valueOf(t.getNation()));
 		sql  = sql.replace("#{edu_degree}",String.valueOf(t.getEduDegree()));
 		sql  = sql.replace("#{code}",String.valueOf(t.getCode()));
 		sql  = sql.replace("#{reg_datetime}",String.valueOf(t.getRegDatetime()));
 		sql  = sql.replace("#{work_unit}",String.valueOf(t.getWorkUnit()));
 		sql  = sql.replace("#{political}",String.valueOf(t.getPolitical()));
 		sql  = sql.replace("#{position}",String.valueOf(t.getPosition()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{work_tele}",String.valueOf(t.getWorkTele()));
 		sql  = sql.replace("#{home_tele}",String.valueOf(t.getHomeTele()));
 		sql  = sql.replace("#{mobile_phone}",String.valueOf(t.getMobilePhone()));
 		sql  = sql.replace("#{mail}",String.valueOf(t.getMail()));
 		sql  = sql.replace("#{serv_stat}",String.valueOf(t.getServStat()));
 		sql  = sql.replace("#{belong_id}",String.valueOf(t.getBelongId()));
 		sql  = sql.replace("#{is_medical}",String.valueOf(t.getIsMedical()));
 		sql  = sql.replace("#{is_expe}",String.valueOf(t.getIsExpe()));
 		sql  = sql.replace("#{hobbies}",String.valueOf(t.getHobbies()));
 		sql  = sql.replace("#{resume}",String.valueOf(t.getResume()));
 		sql  = sql.replace("#{train_record}",String.valueOf(t.getTrainRecord()));
 		sql  = sql.replace("#{serv_time}",String.valueOf(t.getServTime()));
 		sql  = sql.replace("#{serv_period}",String.valueOf(t.getServPeriod()));
 		sql  = sql.replace("#{serv_pair}",String.valueOf(t.getServPair()));
 		sql  = sql.replace("#{serv_edu}",String.valueOf(t.getServEdu()));
 		sql  = sql.replace("#{serv_health}",String.valueOf(t.getServHealth()));
 		sql  = sql.replace("#{serv_relief}",String.valueOf(t.getServRelief()));
 		sql  = sql.replace("#{serv_environ}",String.valueOf(t.getServEnviron()));
 		sql  = sql.replace("#{serv_police}",String.valueOf(t.getServPolice()));
 		sql  = sql.replace("#{serv_event}",String.valueOf(t.getServEvent()));
 		sql  = sql.replace("#{serv_org}",String.valueOf(t.getServOrg()));
 		sql  = sql.replace("#{serv_house}",String.valueOf(t.getServHouse()));
 		sql  = sql.replace("#{serv_right}",String.valueOf(t.getServRight()));
 		sql  = sql.replace("#{serv_other}",String.valueOf(t.getServOther()));
 		sql  = sql.replace("#{pic_id}",String.valueOf(t.getPicId()));
 		sql  = sql.replace("#{level}",String.valueOf(t.getLevel()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{gisxy}",String.valueOf(t.getGisxy()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}