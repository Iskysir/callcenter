package com.chrhc.project.sc.endow.service.impl;
import com.chrhc.project.sc.endow.service.ScEndowmentinsServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.chrhc.project.sc.endow.entity.ScEndowmentinsEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("scEndowmentinsService")
@Transactional
public class ScEndowmentinsServiceImpl extends CommonServiceImpl implements ScEndowmentinsServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((ScEndowmentinsEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((ScEndowmentinsEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((ScEndowmentinsEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScEndowmentinsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScEndowmentinsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScEndowmentinsEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,ScEndowmentinsEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{version_num}",String.valueOf(t.getVersionNum()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{code}",String.valueOf(t.getCode()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{idcard}",String.valueOf(t.getIdcard()));
 		sql  = sql.replace("#{insureddate}",String.valueOf(t.getInsureddate()));
 		sql  = sql.replace("#{insuredstate}",String.valueOf(t.getInsuredstate()));
 		sql  = sql.replace("#{sex}",String.valueOf(t.getSex()));
 		sql  = sql.replace("#{nation}",String.valueOf(t.getNation()));
 		sql  = sql.replace("#{birthday}",String.valueOf(t.getBirthday()));
 		sql  = sql.replace("#{phone}",String.valueOf(t.getPhone()));
 		sql  = sql.replace("#{postcode}",String.valueOf(t.getPostcode()));
 		sql  = sql.replace("#{paddress}",String.valueOf(t.getPaddress()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{holdnation}",String.valueOf(t.getHoldnation()));
 		sql  = sql.replace("#{insamount}",String.valueOf(t.getInsamount()));
 		sql  = sql.replace("#{enterpriseins}",String.valueOf(t.getEnterpriseins()));
 		sql  = sql.replace("#{enterprisedate}",String.valueOf(t.getEnterprisedate()));
 		sql  = sql.replace("#{landlessins}",String.valueOf(t.getLandlessins()));
 		sql  = sql.replace("#{landlessinsdate}",String.valueOf(t.getLandlessinsdate()));
 		sql  = sql.replace("#{oldins}",String.valueOf(t.getOldins()));
 		sql  = sql.replace("#{oldinsdate}",String.valueOf(t.getOldinsdate()));
 		sql  = sql.replace("#{otherins}",String.valueOf(t.getOtherins()));
 		sql  = sql.replace("#{otherinsdate}",String.valueOf(t.getOtherinsdate()));
 		sql  = sql.replace("#{gisxy}",String.valueOf(t.getGisxy()));
 		sql  = sql.replace("#{specialgroup}",String.valueOf(t.getSpecialgroup()));
 		sql  = sql.replace("#{rk_id}",String.valueOf(t.getRkId()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{delflag}",String.valueOf(t.getDelflag()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}