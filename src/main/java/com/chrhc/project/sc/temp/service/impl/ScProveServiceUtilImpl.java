package com.chrhc.project.sc.temp.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ListUtils;
import org.jeecgframework.web.cgform.common.CgAutoListConstant;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;
import org.jeecgframework.web.cgform.service.autolist.CgTableServiceI;
import org.jeecgframework.web.cgform.service.autolist.ConfigServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.chrhc.project.sc.temp.service.ScProveServiceUtil;

@Service("scProveServiceUtil")
@Transactional
public class ScProveServiceUtilImpl extends CommonServiceImpl implements ScProveServiceUtil {
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScProveServiceUtilImpl.class);
	
	@Autowired
	private ConfigServiceI configService;
	@Autowired
	private CgTableServiceI cgTableService;
	
	/**
	 * 判断配置和子女信息
	 * @param parameters
	 * @return
	 */
	public AjaxJson checkQsgxByRkId(Map<String,Object> parameters){
		AjaxJson j = new AjaxJson();
				
		j = this.checkSpouseByRkId(parameters);
		if(j.isSuccess()){
			j = this.checkChildByRkId(parameters);
		}else{
			return j;
		}	
		return j;			
	}
	/**
	 * 通过人口id和关系类别编码判断配偶信息
	 * @param rk_id
	 * @param qsgx
	 * @return
	 */
	public AjaxJson checkSpouseByRkId(Map<String, Object> parameters, String qsgx) {

		AjaxJson j = new AjaxJson();
		String message = "验证成功";

		String rk_id = parameters.get("rk_id") + "";
		String spouseName = parameters.get("spouse_name") + "";
		String marryday = parameters.get("marryday") + "";

		//判断配偶信息
		List<Map<String, Object>> list = this.getQsGxByCode("'" + rk_id + "'", qsgx);
		logger.info("list=" + list);
		if (!StringUtils.isEmpty(spouseName)) {
			if (null == list || list.size() <= 0) {
				message = "配偶信息不存在";
				j.setMsg(message);
				j.setSuccess(false);
				return j;
			} else {
				for (Map<String, Object> map : list) {
					String hus = map.get("xm").toString();
					if (spouseName.equals(hus)) {
						message = "配偶信息相符";
						j.setMsg(message);
						j.setSuccess(true);
						break;
					} else {
						message = "配偶信息不符";
						j.setMsg(message);
						j.setSuccess(false);
					}
				}
			}
		}
		return j;
	}
	/**
	 * 通过人口id和关系类别编码判断配偶信息
	 * @param rk_id
	 * @return
	 */
	public AjaxJson checkSpouseByRkId(Map<String,Object> parameters){
	
		String qsgx = this.getQsgxTypeByRkId(parameters);	
		
		return checkSpouseByRkId(parameters,qsgx);
	}
	/**
	 * 根据rkid，获得对应的配偶信息
	 * @param parameters
	 * @return
	 */
	public List<Map<String, Object>> getSpouseInfoByRkId(Map<String,Object> parameters){
		String rk_id = parameters.get("rk_id") + "";			
		String qsgx = this.getQsgxTypeByRkId(parameters);		
		//判断配偶信息
		List<Map<String, Object>> list = this.getQsGxByCode("'" + rk_id + "'", qsgx);
		logger.info("list=" + list);
		if (!ListUtils.isNullOrEmpty(list)) {
			return list;
		} 
		return list;				
	}
	/**
	 * 通过人口id和关系类别编码判断子女信息
	 * @param parameters
	 * @return
	 */
	public AjaxJson checkChildByRkId(Map<String,Object> parameters){
		AjaxJson j = new AjaxJson();
		String message = "验证成功";
		
		String rk_id = parameters.get("rk_id")+"";
		String childName = parameters.get("child_name")+"";
		String birthDate = parameters.get("birth_date")+"";
		String qsgx = "'3','4'";
		
		StringBuffer sb = new StringBuffer("'"+rk_id+"'");
		//获得该人口id的配偶的人口id，有可能子女与该人口没有关系，但与其配偶有亲属关系
		List<Map<String, Object>> spouseList = this.getSpouseInfoByRkId(parameters);
		String spouseRkid = new String("");
		if(!ListUtils.isNullOrEmpty(spouseList)){
			Map<String, Object> map = spouseList.get(0);
			if(MapUtils.isNotEmpty(map)){
				spouseRkid = map.get("id")+"";
				sb.append(",'"+spouseRkid+"'");
			}
		}
		//判断子女信息
				List<Map<String, Object>> list2 = this.getQsGxByCode(sb.toString(),qsgx);
				logger.info("list2="+list2);
				if(!StringUtils.isEmpty(childName)){
					if(null == list2 || list2.size() <= 0){
						message = "子女信息不存在";
						j.setMsg(message);
						j.setSuccess(false);
						return j;
					}else{
						for(Map<String, Object> map : list2){
							String child = map.get("xm").toString();
							if(childName.equals(child)){
								message = "子女信息相符";
								j.setMsg(message);
								j.setSuccess(true);
								break;
							}else{
								message = "子女信息不相符";
								j.setMsg(message);
								j.setSuccess(false);
							}					
						}						
					}
				}
				return j;
	}
	/**
	 * 根据人口id和关系类别编码获得对应的人口信息
	 * @param qsGxId
	 * @param qsgx
	 * @return
	 */
	public List<Map<String, Object>> getQsGxByCode(String qsGxId, String qsgx) {
		String sql = "SELECT DISTINCT QS_ID FROM SC_QSGX AS T WHERE T.RY_ID IN ("+qsGxId+") AND T.GXLX IN ("+qsgx+") and delflag='0'" ;
		List<String> list=findListbySql(sql);
		
		List<Map<String, Object>> rkxxlist = this.getRkxxById(list);
		
		return rkxxlist;
	}
	/**
	 * 根据id，获得人口库基本信息
	 * @param list
	 * @return
	 */
	public List<Map<String,Object>> getRkxxById(List<String> list){
		
		List<Map<String, Object>> rkxxlist = new ArrayList<Map<String, Object>>();
		String config = "sc_rkjbxxnew";
		Map<String, Object> configs = configService.queryConfigsByCode(config, "");
		String table = (String) configs.get(CgAutoListConstant.TABLENAME);
		List<CgFormFieldEntity> beans = (List<CgFormFieldEntity>) configs.get(CgAutoListConstant.FILEDS);
		StringBuffer fields = new StringBuffer("");
		if(null != beans && beans.size() > 0){
			for(int i= 0;i<beans.size();i++){
				if(i == beans.size() - 1){
					fields.append(beans.get(i).getFieldName());
				}else{
					fields.append(beans.get(i).getFieldName()).append(",");
				}
			}				
		}
		Map<String,Object> params =  new HashMap<String,Object>();
		for(String id : list){
			if(!StringUtils.isEmpty(id)){
				params.put("id", "='"+id+"'");
			}
			params.put("delflag", "='0'");
			rkxxlist.addAll(cgTableService.querySingle(table, fields.toString(), params,"CREATE_DATE","DESC", 1,1));
		}
		
		logger.info("*****rkxxlist="+rkxxlist);
		return rkxxlist;
	}
	/**
	 * 判断两个人口id是否是夫妻关系
	 * @param rk_id 女id
	 * @param m_rk_id 男id
	 * @return
	 */
	public boolean checkWifeAndHusbandByRkId(String rk_id,String m_rk_id){
		boolean oo = true;
		
		String sql = "SELECT DISTINCT T.ID FROM SC_QSGX AS T WHERE T.RY_ID = '"+rk_id+"' AND T.QS_ID = '"+m_rk_id+"' AND T.GXLX IN ('5','6') and T.delflag='0'" ;
		List<String> list= new ArrayList<String>();
		try{
			list = findListbySql(sql);
		}catch(NullPointerException e){
			return false;
		}

		if(null == list || list.size() <= 0){
			oo = false;
		}
		return oo;
	}
	/**
	 * 通过人口id，获得人口信息，返回配偶之间的亲属类别
	 * @param parameters
	 * @return
	 */
	public String getQsgxTypeByRkId(Map<String,Object> parameters){
		
		String rk_id = parameters.get("rk_id") + "";
		String sex = parameters.get("sex")+"";
		String qsgx = "";
		
		List<String> idList = new ArrayList<String>();		
		idList.add(rk_id);
		List<Map<String,Object>> rkList = this.getRkxxById(idList);
		Map<String,Object> rkMap = rkList.get(0);
		
		if(StringUtils.isEmpty(sex) || "null".equals(sex)){
			sex = rkMap.get("xb")+"";
		}
		
		if("1".equals(sex)){
			qsgx = "'6'";
		}else if("2".equals(sex)){
			qsgx = "'5'";
		}else{
			qsgx = "'5','6'";
		}
		return qsgx;
	}
}
