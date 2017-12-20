package com.chrhc.project.sc.qzhgl.service.impl;
import com.chrhc.project.sc.qzhgl.service.ScQzhglServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.chrhc.project.sc.qzhgl.entity.ScQzhglEntity;
import com.chrhc.project.sc.qzhgl.entity.ScQzhglsubEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import java.util.ArrayList;
import java.util.UUID;
import java.io.Serializable;


@Service("scQzhglService")
@Transactional
public class ScQzhglServiceImpl extends CommonServiceImpl implements ScQzhglServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((ScQzhglEntity)entity);
 	}
	
	public void addMain(ScQzhglEntity scQzhgl,
	        List<ScQzhglsubEntity> scQzhglsubList){
			//保存主信息
			this.save(scQzhgl);
		
			/**保存-签章管理子项*/
			for(ScQzhglsubEntity scQzhglsub:scQzhglsubList){
				//外键设置
				scQzhglsub.setBelongId(scQzhgl.getId());
				this.save(scQzhglsub);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(scQzhgl);
	}

	
	public void updateMain(ScQzhglEntity scQzhgl,
	        List<ScQzhglsubEntity> scQzhglsubList) {
		//保存主表信息
		this.saveOrUpdate(scQzhgl);
		//===================================================================================
		//获取参数
		Object id0 = scQzhgl.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-签章管理子项
	    String hql0 = "from ScQzhglsubEntity where 1 = 1 AND bELONG_ID = ? ";
	    List<ScQzhglsubEntity> scQzhglsubOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-签章管理子项
		for(ScQzhglsubEntity oldE:scQzhglsubOldList){
			boolean isUpdate = false;
				for(ScQzhglsubEntity sendE:scQzhglsubList){
					//需要更新的明细数据-签章管理子项
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							this.saveOrUpdate(oldE);
						} catch (Exception e) {
							e.printStackTrace();
							throw new BusinessException(e.getMessage());
						}
						isUpdate= true;
		    			break;
		    		}
		    	}
	    		if(!isUpdate){
		    		//如果数据库存在的明细，前台没有传递过来则是删除-签章管理子项
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-签章管理子项
			for(ScQzhglsubEntity scQzhglsub:scQzhglsubList){
				if(oConvertUtils.isEmpty(scQzhglsub.getId())){
					//外键设置
					scQzhglsub.setBelongId(scQzhgl.getId());
					this.save(scQzhglsub);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(scQzhgl);
	}

	
	public void delMain(ScQzhglEntity scQzhgl) {
		//删除主表信息
		this.delete(scQzhgl);
		//===================================================================================
		//获取参数
		Object id0 = scQzhgl.getId();
		//===================================================================================
		//删除-签章管理子项
	    String hql0 = "from ScQzhglsubEntity where 1 = 1 AND bELONG_ID = ? ";
	    List<ScQzhglsubEntity> scQzhglsubOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(scQzhglsubOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScQzhglEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScQzhglEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScQzhglEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,ScQzhglEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{version_num}",String.valueOf(t.getVersionNum()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{bus_type}",String.valueOf(t.getBusType()));
 		sql  = sql.replace("#{rkk_id}",String.valueOf(t.getRkkId()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{cert_id}",String.valueOf(t.getCertId()));
 		sql  = sql.replace("#{telephone}",String.valueOf(t.getTelephone()));
 		sql  = sql.replace("#{doc_name}",String.valueOf(t.getDocName()));
 		sql  = sql.replace("#{doc_typename}",String.valueOf(t.getDocTypename()));
 		sql  = sql.replace("#{doc_type}",String.valueOf(t.getDocType()));
 		sql  = sql.replace("#{doc_id}",String.valueOf(t.getDocId()));
 		sql  = sql.replace("#{deal_datetime}",String.valueOf(t.getDealDatetime()));
 		sql  = sql.replace("#{obligatea}",String.valueOf(t.getObligatea()));
 		sql  = sql.replace("#{obligateb}",String.valueOf(t.getObligateb()));
 		sql  = sql.replace("#{obligatec}",String.valueOf(t.getObligatec()));
 		sql  = sql.replace("#{delflag}",String.valueOf(t.getDelflag()));
 		sql  = sql.replace("#{obligated}",String.valueOf(t.getObligated()));
 		sql  = sql.replace("#{obligatee}",String.valueOf(t.getObligatee()));
 		sql  = sql.replace("#{obligatef}",String.valueOf(t.getObligatef()));
 		sql  = sql.replace("#{obligateg}",String.valueOf(t.getObligateg()));
 		sql  = sql.replace("#{obligateh}",String.valueOf(t.getObligateh()));
 		sql  = sql.replace("#{obligatei}",String.valueOf(t.getObligatei()));
 		sql  = sql.replace("#{obligatej}",String.valueOf(t.getObligatej()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}