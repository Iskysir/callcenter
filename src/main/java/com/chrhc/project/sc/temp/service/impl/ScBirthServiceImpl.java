package com.chrhc.project.sc.temp.service.impl;
import com.chrhc.project.sc.temp.service.ScBirthServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.chrhc.project.sc.temp.entity.ScBirthEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("scBirthService")
@Transactional
public class ScBirthServiceImpl extends CommonServiceImpl implements ScBirthServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((ScBirthEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((ScBirthEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((ScBirthEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScBirthEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScBirthEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScBirthEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,ScBirthEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{version_num}",String.valueOf(t.getVersionNum()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{code}",String.valueOf(t.getCode()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{sex}",String.valueOf(t.getSex()));
 		sql  = sql.replace("#{m_birthday_date}",String.valueOf(t.getMBirthdayDate()));
 		sql  = sql.replace("#{spouse_name}",String.valueOf(t.getSpouseName()));
 		sql  = sql.replace("#{marryday}",String.valueOf(t.getMarryday()));
 		sql  = sql.replace("#{child_name}",String.valueOf(t.getChildName()));
 		sql  = sql.replace("#{birth_date}",String.valueOf(t.getBirthDate()));
 		sql  = sql.replace("#{fill_in_date}",String.valueOf(t.getFillInDate()));
 		sql  = sql.replace("#{doc_typename}",String.valueOf(t.getDocTypename()));
 		sql  = sql.replace("#{obligatea}",String.valueOf(t.getObligatea()));
 		sql  = sql.replace("#{obligateb}",String.valueOf(t.getObligateb()));
 		sql  = sql.replace("#{print_status}",String.valueOf(t.getPrintStatus()));
 		sql  = sql.replace("#{print_num}",String.valueOf(t.getPrintNum()));
 		sql  = sql.replace("#{delflag}",String.valueOf(t.getDelflag()));
 		sql  = sql.replace("#{obligatec}",String.valueOf(t.getObligatec()));
 		sql  = sql.replace("#{obligatee}",String.valueOf(t.getObligatee()));
 		sql  = sql.replace("#{obligated}",String.valueOf(t.getObligated()));
 		sql  = sql.replace("#{rk_id}",String.valueOf(t.getRkId()));
 		sql  = sql.replace("#{doc_id}",String.valueOf(t.getDocId()));
 		sql  = sql.replace("#{spouse_rk_id}",String.valueOf(t.getSpouseRkId()));
 		sql  = sql.replace("#{child_rk_id}",String.valueOf(t.getChildRkId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}