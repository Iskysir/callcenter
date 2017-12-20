package com.chrhc.project.sc.ewmcode.service.impl;
import com.chrhc.project.sc.ewmcode.service.ScEwmnrxpzServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.chrhc.project.sc.ewmcode.entity.ScEwmnrxpzEntity;
import com.chrhc.project.sc.ewmcode.entity.ScEwmnrxpzsubEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;


@Service("scEwmnrxpzService")
@Transactional
public class ScEwmnrxpzServiceImpl extends CommonServiceImpl implements ScEwmnrxpzServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((ScEwmnrxpzEntity)entity);
 	}
	
	public void addMain(ScEwmnrxpzEntity scEwmnrxpz,
	        List<ScEwmnrxpzsubEntity> scEwmnrxpzsubList){
			//保存主信息
			this.save(scEwmnrxpz);
		
			/**保存-二维码返回信息*/
			for(ScEwmnrxpzsubEntity scEwmnrxpzsub:scEwmnrxpzsubList){
				//外键设置
				scEwmnrxpzsub.setPzid(scEwmnrxpz.getId());
				this.save(scEwmnrxpzsub);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(scEwmnrxpz);
	}

	
	public void updateMain(ScEwmnrxpzEntity scEwmnrxpz,
	        List<ScEwmnrxpzsubEntity> scEwmnrxpzsubList) {
		//保存主表信息
		this.saveOrUpdate(scEwmnrxpz);
		//===================================================================================
		//获取参数
		Object id0 = scEwmnrxpz.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-二维码返回信息
	    String hql0 = "from ScEwmnrxpzsubEntity where 1 = 1 AND pZID = ? ";
	    List<ScEwmnrxpzsubEntity> scEwmnrxpzsubOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-二维码返回信息
		for(ScEwmnrxpzsubEntity oldE:scEwmnrxpzsubOldList){
			boolean isUpdate = false;
				for(ScEwmnrxpzsubEntity sendE:scEwmnrxpzsubList){
					//需要更新的明细数据-二维码返回信息
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-二维码返回信息
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-二维码返回信息
			for(ScEwmnrxpzsubEntity scEwmnrxpzsub:scEwmnrxpzsubList){
				if(oConvertUtils.isEmpty(scEwmnrxpzsub.getId())){
					//外键设置
					scEwmnrxpzsub.setPzid(scEwmnrxpz.getId());
					this.save(scEwmnrxpzsub);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(scEwmnrxpz);
	}

	
	public void delMain(ScEwmnrxpzEntity scEwmnrxpz) {
		//删除主表信息
		this.delete(scEwmnrxpz);
		//===================================================================================
		//获取参数
		Object id0 = scEwmnrxpz.getId();
		//===================================================================================
		//删除-二维码返回信息
	    String hql0 = "from ScEwmnrxpzsubEntity where 1 = 1 AND pZID = ? ";
	    List<ScEwmnrxpzsubEntity> scEwmnrxpzsubOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(scEwmnrxpzsubOldList);
	}
	
	//取得表字段信息
	public List<Map<String, Object>> getTableField(String tablename) {
		String sql = "select field_name,content from cgform_field where table_id = (select id from cgform_head where table_name = '"+ tablename+ "')";
		List<Map<String, Object>> tablefield = this.commonDao.findForJdbc(sql);

		return tablefield;
	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScEwmnrxpzEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScEwmnrxpzEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScEwmnrxpzEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,ScEwmnrxpzEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{ywbh}",String.valueOf(t.getYwbh()));
 		sql  = sql.replace("#{ywmc}",String.valueOf(t.getYwmc()));
 		sql  = sql.replace("#{frontfield}",String.valueOf(t.getFrontfield()));
 		sql  = sql.replace("#{sourcetable}",String.valueOf(t.getSourcetable()));
 		sql  = sql.replace("#{sfyy}",String.valueOf(t.getSfyy()));
 		sql  = sql.replace("#{delflag}",String.valueOf(t.getDelflag()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
	@Override
	public List<Map<String, Object>>  getGlcheck(String mainTable, String zTable,
			String wjIdName,String id) {
		String sql = "SELECT t.id from "+mainTable+" as t  INNER JOIN  "+zTable+"  as m ON  t.id = m."+wjIdName+" where t.id = ?  and m.delflag = '0' " ;
		List<Map<String, Object>> list = this.findForJdbc(sql, id);
		return list;
	}
}