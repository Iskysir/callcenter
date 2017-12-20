package com.chrhc.project.sc.temp.service;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;


public interface ScProveServiceUtil extends CommonService{
	

	/**
	 * 判断配置和子女信息
	 * @param parameters
	 * @return
	 */
	public AjaxJson checkQsgxByRkId(Map<String,Object> parameters);
	/**
	 * 通过人口id和关系类别编码判断配偶信息
	 * @param rk_id
	 * @param qsgx
	 * @return
	 */
	public AjaxJson checkSpouseByRkId(Map<String, Object> parameters, String qsgx);
	/**
	 * 通过人口id和关系类别编码判断配偶信息
	 * @param rk_id
	 * @return
	 */
	public AjaxJson checkSpouseByRkId(Map<String,Object> parameters);
	/**
	 * 通过人口id和关系类别编码判断子女信息
	 * @param parameters
	 * @return
	 */
	public AjaxJson checkChildByRkId(Map<String,Object> parameters);
	/**
	 * 根据人口id和关系类别编码获得对应的人口信息
	 * @param qsGxId
	 * @param qsgx
	 * @return
	 */
	public List<Map<String, Object>> getQsGxByCode(String qsGxId, String qsgx);
	/**
	 * 根据id，获得人口库基本信息
	 * @param list
	 * @return
	 */
	public List<Map<String,Object>> getRkxxById(List<String> list);
	/**
	 * 判断两个人口id是否是夫妻关系
	 * @param rk_id 女id
	 * @param m_rk_id 男id
	 * @return
	 */
	public boolean checkWifeAndHusbandByRkId(String rk_id,String m_rk_id);
}
