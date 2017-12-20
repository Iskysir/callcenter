package com.chrhc.project.sc.kinship.service;
import com.chrhc.project.sc.kinship.entity.KinshipEntity;
import com.chrhc.project.sc.kinship.entity.PersonRecord;
import com.chrhc.project.sc.kinship.util.CurdTypeEnum;
import com.chrhc.project.sc.kinship.util.KinshipEnum;

import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface KinshipServiceI extends CommonService{
	/**
	 * 通过spark获取亲属关系，返回json字符串
	 * @param ry_graphid
	 * @return
	 */
	public String getSparkQsgx(int ry_graphid);
	/**
	 * 通过spark添加亲属关系
	 * @param ry_graphid
	 * @param ry_Id
	 * @param qs_graphid
	 * @param qs_Id
	 * @param qsgx
	 * @param ry_xb
	 * @param qs_xb
	 * @return
	 */
	public String addSparkQsgx(int ry_graphid, String ry_Id, int qs_graphid, String qs_Id, String qsgx, String ry_xb, String qs_xb);
	/**
	 * 通过spark删除亲属关系
	 * @param ry_graphid
	 * @param ry_Id
	 * @param qs_graphid
	 * @param qs_Id
	 * @param qsgx
	 * @return
	 */
	public String deleteSparkQsgx(int ry_graphid, String ry_Id, int qs_graphid, String qs_Id, String qsgx);
	/**
	 * 获取人员履历信息
	 * @param rk_id
	 * @return
	 */
	public void getPersonRecord(String rk_id,List<PersonRecord> listpr);
	/**
	 * 获取家庭人员属性
	 * @param jtId
	 */
	public void getjtrksx(String jtId,List<Map<String, String>> sxlist);
	/**
	 * 取得关联信息记录
	 * @param mainTable
	 * @param zTable
	 * @param wjId
	 * @return
	 */
	public List<Map<String, Object>>  getGlcheck(String mainTable,String zTable,String wjIdName,String id);
	/**
	 * 根据人员Id取得此人亲属关系json数据
	 * @param id
	 * @return
	 */
	public String getQsGxById(String id);
	
	/**
	 * 拼接亲属关系人员ID，方便查找所需要的亲属关系
	 * @param map
	 * @param sb
	 */
	public void appendRyId(Map<String, List<Map<String,String>>>  map,StringBuffer sb,KinshipEnum enumkinship);

	/**
	 * 
	 * @param qsGxId['id1','id2'...]
	 * @param qsgx
	 * @return
	 */
	public List<Object[]> getQsGxByCode(String qsGxId,String qsgx);
	/**
	 * 根据人员ID取得人员基本信息
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getRkjbxx(String id);
	/**
	 * 根据亲属关系获取相对应关系
	 * @param code
	 * @return
	 */
	public KinshipEnum getKinShip(KinshipEntity kinship);
	/**
	 * 反向维护亲属关系
	 * @param kinship
	 */
	public void addReverseKinship(KinshipEntity kinship,CurdTypeEnum enumtype);
	
	/**
	 * 是否需要新增反向关系
	 * @param gxlx
	 * @param ry_id
	 * @param qs_id
	 * @return 
	 * @return
	 */
	public Map<String, KinshipEntity>  getFlagAddKinship(String gxlx,String ry_id,String qs_id);
	/**
	 * 是否需要新增反向关系[判断主键id]
	 * @param gxlx
	 * @param ry_id
	 * @param qs_id
	 * @param id
	 * @return 
	 * @return
	 */
	public Map<String, KinshipEntity>  getFlagAddKinship(String gxlx,String ry_id,String qs_id,String id);
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(KinshipEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(KinshipEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(KinshipEntity t);
}
