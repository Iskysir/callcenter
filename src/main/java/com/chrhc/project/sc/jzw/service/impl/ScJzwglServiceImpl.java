package com.chrhc.project.sc.jzw.service.impl;
import com.chrhc.project.sc.jzw.service.ScJzwglServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.chrhc.project.sc.jzw.entity.ScJzwglEntity;
import com.chrhc.project.sc.jzw.entity.ScJzwdyglEntity;

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


@Service("scJzwglService")
@Transactional
public class ScJzwglServiceImpl extends CommonServiceImpl implements ScJzwglServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((ScJzwglEntity)entity);
 	}
	
	public void addMain(ScJzwglEntity scJzwgl,
	        List<ScJzwdyglEntity> scJzwdyglList){
			//保存主信息
			this.save(scJzwgl);
		
			/**保存-建筑物单元管理*/
			for(ScJzwdyglEntity scJzwdygl:scJzwdyglList){
				//外键设置
				scJzwdygl.setJzwId(scJzwgl.getId());
				this.save(scJzwdygl);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(scJzwgl);
	}

	
	public void updateMain(ScJzwglEntity scJzwgl,
	        List<ScJzwdyglEntity> scJzwdyglList) {
		//保存主表信息
		this.saveOrUpdate(scJzwgl);
		//===================================================================================
		//获取参数
		Object id0 = scJzwgl.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-建筑物单元管理
	    String hql0 = "from ScJzwdyglEntity where 1 = 1 AND jZW_ID = ? ";
	    List<ScJzwdyglEntity> scJzwdyglOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-建筑物单元管理
		for(ScJzwdyglEntity oldE:scJzwdyglOldList){
			boolean isUpdate = false;
				for(ScJzwdyglEntity sendE:scJzwdyglList){
					//需要更新的明细数据-建筑物单元管理
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-建筑物单元管理
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-建筑物单元管理
			for(ScJzwdyglEntity scJzwdygl:scJzwdyglList){
				if(oConvertUtils.isEmpty(scJzwdygl.getId())){
					//外键设置
					scJzwdygl.setJzwId(scJzwgl.getId());
					this.save(scJzwdygl);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(scJzwgl);
	}

	
	public void delMain(ScJzwglEntity scJzwgl) {
		//删除主表信息
		this.delete(scJzwgl);
		//===================================================================================
		//获取参数
		Object id0 = scJzwgl.getId();
		//===================================================================================
		//删除-建筑物单元管理
	    String hql0 = "from ScJzwdyglEntity where 1 = 1 AND jZW_ID = ? ";
	    List<ScJzwdyglEntity> scJzwdyglOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(scJzwdyglOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScJzwglEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScJzwglEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScJzwglEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,ScJzwglEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{number}",String.valueOf(t.getNumber()));
 		sql  = sql.replace("#{gisxy}",String.valueOf(t.getGisxy()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{units}",String.valueOf(t.getUnits()));
 		sql  = sql.replace("#{type}",String.valueOf(t.getType()));
 		sql  = sql.replace("#{usadge}",String.valueOf(t.getUsadge()));
 		sql  = sql.replace("#{struct}",String.valueOf(t.getStruct()));
 		sql  = sql.replace("#{build_year}",String.valueOf(t.getBuildYear()));
 		sql  = sql.replace("#{property_limit}",String.valueOf(t.getPropertyLimit()));
 		sql  = sql.replace("#{property_unit}",String.valueOf(t.getPropertyUnit()));
 		sql  = sql.replace("#{addr}",String.valueOf(t.getAddr()));
 		sql  = sql.replace("#{gis_id}",String.valueOf(t.getGisId()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{auto_flag}",String.valueOf(t.getAutoFlag()));
 		sql  = sql.replace("#{auto_code}",String.valueOf(t.getAutoCode()));
 		sql  = sql.replace("#{del_flag}",String.valueOf(t.getDelFlag()));
 		sql  = sql.replace("#{del_date}",String.valueOf(t.getDelDate()));
 		sql  = sql.replace("#{obligatea}",String.valueOf(t.getObligatea()));
 		sql  = sql.replace("#{obligateb}",String.valueOf(t.getObligateb()));
 		sql  = sql.replace("#{obligatec}",String.valueOf(t.getObligatec()));
 		sql  = sql.replace("#{obligated}",String.valueOf(t.getObligated()));
 		sql  = sql.replace("#{obligatee}",String.valueOf(t.getObligatee()));
 		sql  = sql.replace("#{obligatef}",String.valueOf(t.getObligatef()));
 		sql  = sql.replace("#{obligateg}",String.valueOf(t.getObligateg()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}