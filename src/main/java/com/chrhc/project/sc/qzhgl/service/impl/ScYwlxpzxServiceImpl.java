package com.chrhc.project.sc.qzhgl.service.impl;
import com.chrhc.project.sc.qzhgl.service.ScYwlxpzxServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.chrhc.project.sc.qzhgl.entity.ScYwlxpzxEntity;
import com.chrhc.project.sc.qzhgl.entity.ScYwlxpzxsubEntity;

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


@Service("scYwlxpzxService")
@Transactional
public class ScYwlxpzxServiceImpl extends CommonServiceImpl implements ScYwlxpzxServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((ScYwlxpzxEntity)entity);
 	}
	
	public void addMain(ScYwlxpzxEntity scYwlxpzx,
	        List<ScYwlxpzxsubEntity> scYwlxpzxsubList){
			//保存主信息
			this.save(scYwlxpzx);
		
			/**保存-签章配置项子项*/
			for(ScYwlxpzxsubEntity scYwlxpzxsub:scYwlxpzxsubList){
				//外键设置
				scYwlxpzxsub.setPzxId(scYwlxpzx.getId());
				this.save(scYwlxpzxsub);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(scYwlxpzx);
	}

	
	public void updateMain(ScYwlxpzxEntity scYwlxpzx,
	        List<ScYwlxpzxsubEntity> scYwlxpzxsubList) {
		//保存主表信息
		this.saveOrUpdate(scYwlxpzx);
		//===================================================================================
		//获取参数
		Object id0 = scYwlxpzx.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-签章配置项子项
	    String hql0 = "from ScYwlxpzxsubEntity where 1 = 1 AND pZX_ID = ? ";
	    List<ScYwlxpzxsubEntity> scYwlxpzxsubOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-签章配置项子项
		for(ScYwlxpzxsubEntity oldE:scYwlxpzxsubOldList){
			boolean isUpdate = false;
				for(ScYwlxpzxsubEntity sendE:scYwlxpzxsubList){
					//需要更新的明细数据-签章配置项子项
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-签章配置项子项
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-签章配置项子项
			for(ScYwlxpzxsubEntity scYwlxpzxsub:scYwlxpzxsubList){
				if(oConvertUtils.isEmpty(scYwlxpzxsub.getId())){
					//外键设置
					scYwlxpzxsub.setPzxId(scYwlxpzx.getId());
					this.save(scYwlxpzxsub);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(scYwlxpzx);
	}

	
	public void delMain(ScYwlxpzxEntity scYwlxpzx) {
		//删除主表信息
		this.delete(scYwlxpzx);
		//===================================================================================
		//获取参数
		Object id0 = scYwlxpzx.getId();
		//===================================================================================
		//删除-签章配置项子项
	    String hql0 = "from ScYwlxpzxsubEntity where 1 = 1 AND pZX_ID = ? ";
	    List<ScYwlxpzxsubEntity> scYwlxpzxsubOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(scYwlxpzxsubOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScYwlxpzxEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScYwlxpzxEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScYwlxpzxEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,ScYwlxpzxEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{version_num}",String.valueOf(t.getVersionNum()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{bus_type}",String.valueOf(t.getBusType()));
 		sql  = sql.replace("#{bus_name}",String.valueOf(t.getBusName()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{sort_no}",String.valueOf(t.getSortNo()));
 		sql  = sql.replace("#{obligatea}",String.valueOf(t.getObligatea()));
 		sql  = sql.replace("#{obligateb}",String.valueOf(t.getObligateb()));
 		sql  = sql.replace("#{obligatec}",String.valueOf(t.getObligatec()));
 		sql  = sql.replace("#{obligated}",String.valueOf(t.getObligated()));
 		sql  = sql.replace("#{obligatee}",String.valueOf(t.getObligatee()));
 		sql  = sql.replace("#{delflag}",String.valueOf(t.getDelflag()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}