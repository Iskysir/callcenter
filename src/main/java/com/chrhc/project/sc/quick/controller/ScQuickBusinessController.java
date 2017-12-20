package com.chrhc.project.sc.quick.controller;
import com.alibaba.druid.util.StringUtils;
import com.chrhc.common.QuickBusinessConfig;
import com.chrhc.project.sc.docfile.service.ScDocWarServiceI;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.context.annotation.Scope;
import org.hibernate.Query;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.web.cgform.common.CgAutoListConstant;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;
import org.jeecgframework.web.cgform.service.autolist.CgTableServiceI;
import org.jeecgframework.web.cgform.service.autolist.ConfigServiceI;
import org.jeecgframework.web.cgform.service.build.DataBaseService;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.jeecgframework.web.cgreport.service.core.CgReportServiceI;
import org.jeecgframework.web.system.pojo.base.TSIcon;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.service.SystemService;


import java.util.Map;



/**   
 * @Title: Controller
 * @Description: 快捷业务
 * @author onlineGenerator
 * @date 2015-07-28 13:26:35
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scQuickBusinessController")
public class ScQuickBusinessController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScQuickBusinessController.class);

	@Autowired
	private ConfigServiceI configService;
	@Autowired
	private CgTableServiceI cgTableService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	@Autowired
	private CgReportServiceI cgReportService;
	@Autowired
	private DataBaseService dataBaseService;
	
	@Autowired
	private ScDocWarServiceI scDocWarService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 快捷业务列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scQuickBusiness")
	public ModelAndView scQuickBusiness(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/quick/scQuickBusinessList");
	}	
	/**
	 * 快捷业务新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(HttpServletRequest req) {
		//show_btn_modleType 业务模块类型，为了可以动态变化，配置在quickBusiness.properties中
		List<TSType> businessTypeList = this.getDictionaryByGroupCode(QuickBusinessConfig.getValue("show_btn_modleType"));
		req.setAttribute("modleTypeList", businessTypeList);
		
		return new ModelAndView("com/chrhc/project/sc/quick/scQuickBusiness-input");
	}		
	
	/**
	 * 通过身份证号，判断是否在人口库中存在
	 * @param certId
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "isExistInRkkxx")
	@ResponseBody
	public AjaxJson isExistInRkkxx(String certId,String configId,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "身份证信息存在";
		
		try{
			String jversion = "";
			
			Map<String, Object>  configs = configService.queryConfigsByCode(configId,jversion);	
			String table = (String) configs.get(CgAutoListConstant.TABLENAME);
			List<CgFormFieldEntity> beans = (List<CgFormFieldEntity>) configs.get(CgAutoListConstant.FILEDS);
			StringBuffer field = new StringBuffer("");
			if(null != beans && beans.size() > 0){
				for(int i= 0;i<beans.size();i++){
					if(i == beans.size() - 1){
						field.append(beans.get(i).getFieldName());
					}else{
						field.append(beans.get(i).getFieldName()).append(",");
					}
				}				
			}
			
			Map<String,Object> params =  new HashMap<String,Object>();
			params.put("sfzh", "='"+certId+"'");
			params.put("delflag", "='0'");
			
			List<Map<String, Object>> result=cgTableService.querySingle(table, field.toString(), params,"create_date","DESC", 1,100);
			int size = result.size();
			if(size > 0){
				j.setSuccess(true);
				j.setObj(result.get(0));
			}else{
				j.setSuccess(false);
				message = "身份证信息不存在";
			}
		}catch(Exception e){
			e.printStackTrace();
			j.setSuccess(false);
			message = "身份证信息不存在";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 读取身份证后，判断该身份证信息与人口库中身份证信息是否一致
	 * @param scDocWar
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkInRkkxx")
	@ResponseBody
	public AjaxJson checkInRkkxx(HttpServletRequest request){
		logger.debug("********checkInRkkxx*********");
		
		AjaxJson j = new AjaxJson();
		/*Map<String, Object> params = getParametersStartingWith(
				request, "");*/
		Map<String,Object> parameters =  new HashMap<String,Object>();
		parameters = getParametersStartingWith(request, "EQ_");
		//String field = "id,create_name,create_by,create_date,update_name,version_num,sys_org_code,update_by,update_date,obligatea,obligateb,obligatec,obligated,obligatee,del_date,gisxy,grzp,xm,sfzh,csrq,bm,xb,mz,sfzdz,xjzdz,hjdz,csd,qfjg,ksyxq,jsyxq,delflag,jtzz,hukouxinxi,sfhz,hkxz,yhzgx,ssjt,hjzk,cym,hyzk,whcd,zzlb,byzk,hksfzx,zy,zxrq,qitaxinxi,sg,xx,zjxy,jkzk,lxdh,gzdw,gzlb,dwxz,zc,jmxxkh,tszc,jhr,jhgx,ysr,xqmc,lh,dyh,jg,kuozhanxinxi,sfjtcy,tzlb,qwlb,bzxx,ssjt_id,rysx,sfzdfwrq,sfzdwkrq,zyz,zdwfrq,zdwkrq,bxxx,dblb,wbh,kdh,cjzk,syry,sfsw,qytxry,hz_id,lnr,yf,ylfn";
		String configId = "sc_rkjbxxnew";
		
		//step.1 获取动态配置
		String jversion = "";
		Map<String, Object>  configs = configService.queryConfigsByCode(configId,jversion);
		String table = (String) configs.get(CgAutoListConstant.TABLENAME);
		
		List<CgFormFieldEntity> beans = (List<CgFormFieldEntity>) configs.get(CgAutoListConstant.FILEDS);
		StringBuffer field = new StringBuffer("");
		if(null != beans && beans.size() > 0){
			for(int i= 0;i<beans.size();i++){
				if(i == beans.size() - 1){
					field.append(beans.get(i).getFieldName());
				}else{
					field.append(beans.get(i).getFieldName()).append(",");
				}
			}				
		}
		
		//step.2 获取查询条件以及值
		/*List<CgFormFieldEntity> beans = (List<CgFormFieldEntity>) configs.get(CgAutoListConstant.FILEDS);
		for(CgFormFieldEntity b:beans){
				QueryParamUtil.loadQueryParams(request,b,params);
		}*/
		int p = 1;
		int r = 100;
		//step.3 进行查询返回结果		
		//zxy 逻辑删除添加过滤数据
		Map<String,Object> params =  new HashMap<String,Object>();
		params.put("sfzh", "='"+parameters.get("sfzh")+"'");
		params.put("delflag", "='0'");
		List<Map<String, Object>> result=cgTableService.querySingle(table, field.toString(), params,"sfzh","desc", p,r );
		Map<String,Object> dbResult =  new HashMap<String,Object>();
		if(null != result && result.size() > 0){
			dbResult = result.get(0);
		}else{
			j.setSuccess(false);
			j.setMsg("人口库中不存在该身份证信息");
			return j;
		}
		Set<String> keySet = parameters.keySet();
		StringBuffer sb = new StringBuffer("");
		Map<String,Object> attrMap =  new HashMap<String,Object>();
		if(null != keySet && keySet.size() > 0){
			Object[] keyArray = keySet.toArray();
			for(int i=0;i<keyArray.length;i++){
				String key = keyArray[i]+"";
				//将date类型的值转成字符串，否则传到前台转json时出错
				if(dbResult.get(key) instanceof Date) {
					dbResult.put(key, (dbResult.get(key)+"").substring(0, 10));					
				}
				if((parameters.get(key)+"").equals(dbResult.get(key)+"")){
					
				}else{
					if(i != keyArray.length){
						sb.append(key+",");
					}else{
						sb.append(key);
					}
					
				}
				attrMap.put(key, dbResult.get(key));
			}
		}
		if(sb.toString().length()>0){
			attrMap.put("keyStr", sb.toString());
			j.setSuccess(false);
			j.setAttributes(attrMap);
			j.setObj(dbResult.get("id"));
			j.setMsg("身份证信息与人口库中信息不一致");
		}else{
			j.setSuccess(true);
			j.setObj(dbResult.get("id"));
			j.setMsg("身份证信息与人口库中信息一致");
		}
		return j;
	}
	/**
	 * 将前台传递过来的参数转换成map字段形式
	 * @param request
	 * @param prefix
	 * @return
	 */
	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Validate.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while ((paramNames != null) && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if ((values == null) || (values.length == 0)) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}
	/**
	 * 根据给定的字典类型code，查询字典数据
	 * @param groupcode
	 * @return
	 */
	public List<TSType> getDictionaryByGroupCode(String groupcode){		
		List<TSType> listtype = new ArrayList<TSType>();
		try{
			 List<TSTypegroup> listGroup =scDocWarService.findByProperty(TSTypegroup.class, "typegroupcode", groupcode);
			 if(listGroup!=null&&listGroup.size()>0){
				 String hql="from TSType where 1=1 and TSTypegroup='"+listGroup.get(0).getId()+"'";
				 listtype =  scDocWarService.findByQueryString(hql);
			 }	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return listtype ;	
		 
	}
	/**
	 * 根据给定的字典类型code和限定的字典code，查询字典数据
	 * @param groupcode
	 * @param typeCodeList
	 * @return
	 */
	public List<TSType> getDictionaryByGroupCodeInList(String groupcode,Set<String> typeCodeSet){		
		List<TSType> listtype = new ArrayList<TSType>();
		try{
			 List<TSTypegroup> listGroup =scDocWarService.findByProperty(TSTypegroup.class, "typegroupcode", groupcode);
			 if(listGroup!=null&&listGroup.size()>0){
				 String hql="from TSType where 1=1 and TSTypegroup='"+listGroup.get(0).getId()+"'";
				 
				 if(null != typeCodeSet && typeCodeSet.size() > 0){
					 hql = hql + " and typecode in (:typeCodeList)";
					 Query query = scDocWarService.getSession().createQuery(hql);
					 query.setParameterList("typeCodeList", typeCodeSet.toArray());
					 listtype = query.list();
				 }else{
					 Query query = scDocWarService.getSession().createQuery(hql);
					 listtype = query.list();
				 } 
			 }	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return listtype ;	
		 
	}
	
	/**
	 * 根据身份证id，获得业务类型按钮
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getModelTypeBtn")
	@ResponseBody
	public AjaxJson getModelTypeBtn(ServletRequest request){
		AjaxJson j = new AjaxJson();
				
		Map<String,Object> parameters =  getParametersStartingWith(request, "EQ_");
		
		//获得人口库信息
		Map<String, Object> rkMap = this.getRkxxForQuick(parameters);
		if(null == rkMap || rkMap.size() == 0){
			j.setSuccess(false);
			j.setMsg("人口库中信息为空");
			return j;
		}
		//获得人口业务配置表中的相关信息
		List<Map<String, Object>> configList = this.getRkYwConfigForQuick(parameters);
		if(null == configList || configList.size() == 0){
			j.setMsg("人口回写配置表中无相关配置");
			j.setSuccess(false);
			return j;
		}
		//获得人口库信息与人口业务配置表信息需要比对的字段
		String[] fieldArray = this.getCompareFieldForQuick();
		if (null == fieldArray || fieldArray.length == 0){
			j.setMsg("需要比对的字段信息为空");
			j.setSuccess(false);
			return j;
		}
		
		//可以显示的业务模块编码的集合	
		Set<String> modelSet = new HashSet<String>();
		
		  for (Map<String, Object> configMap : configList) {
				Map<String, Object> yMap = new HashMap<String, Object>();
				Map<String, Object> sameMap = new HashMap<String, Object>();
				for (int i = 0; i < fieldArray.length; i++) {
					Object rkStr = rkMap.get(fieldArray[i]);
					Object configStr = configMap.get(fieldArray[i]);
					/**比对模式：以人口业务配置表中为基准，若该表中为空，则不限制显示；
					  * 若不为空，则人口信息表中与之一致才显示，且只需满足人口业务配置表中不为空的项即可。
					  */
					if(null != configStr && !"".equals(configStr+"") && !"null".equals(configStr+"")){						
						yMap.put(fieldArray[i], configStr);//记录人口业务配置表中不为空的字段
						if(null != rkStr && !"".equals(rkStr+"") && !"null".equals(rkStr+"") && (configStr+"").equals(rkStr+"")){
							sameMap.put(fieldArray[i], configStr);//记录人口信息表中不为空且与人口业务配置表中字段相等的字段
						}
					}
					
					
					/* 比对方式2
					 * //该比对方式为：只要人口信息表中与人口业务配置表中一致，按钮则显示，即使都不配置均为null，也显示	
					if(null == configStr || "".equals(configStr+"")){
						if(null == rkStr){
							sameMap.put(fieldArray[i], configStr);
						}else if("".equals(rkStr+"")){
							sameMap.put(fieldArray[i], configStr);
						}
					}else {
						if((configStr+"").equals(rkStr+"")){
							sameMap.put(fieldArray[i], configStr);
						}
					}*/
					
					/* 比对方式1：
					 * 该比对方式为：首先，在sc_rk_yw_config表中配置为‘Y’,且人口信息表中与人口业务配置表中一致，按钮才显示
					 * if (null != configStr && "Y".equals(configStr)) {
						//首先，保证配置表中配置为Y，说明业务必须改配置项						
						yMap.put(fieldArray[i], configStr);
						if (configStr.equals(rkStr)) {
							//然后，若配置表中的值与人口中的值相同。
							sameMap.put(fieldArray[i], configStr);
						}
					}*/
					
				}
				/* 比对方式1
				 * 该比对方式为：首先，在sc_rk_yw_config表中配置为‘Y’,且人口信息表中与人口业务配置表中一致，按钮才显示
				 * if (yMap.size() > 0 && yMap.size() == sameMap.size()) {
					boolean isSame = compareMap(yMap, sameMap);
					if (isSame) {
						//配置表和人口库中配置一致,该业务按钮可以显示	
						//获得业务模块的值
						String tmp = configMap.get("modeltype") + "";
						if (null != tmp && !"".equals(tmp) && !"null".equals(tmp)) {
							String[] tmpArr = tmp.split(",");
							if (null != tmpArr && tmpArr.length > 0) {
								for (String str : tmpArr) {
									if (!modelSet.contains(str.trim())) {
										//将可以显示的模块按钮放入
										modelSet.add(str.trim());
									}
								}
							}
						}
					}// isSame结束	
				} else {

				}*/
				
				//配置表和人口库中配置一致,该业务按钮可以显示	
				//获得业务模块的值
				/* 比对方式2
				 * if (sameMap.size() > 0 && sameMap.size() == fieldArray.length){
					String tmp = configMap.get("modeltype") + "";
					if (null != tmp && !"".equals(tmp) && !"null".equals(tmp)) {
						String[] tmpArr = tmp.split(",");
						if (null != tmpArr && tmpArr.length > 0) {
							for (String str : tmpArr) {
								if (!modelSet.contains(str.trim())) {
									//将可以显示的模块按钮放入
									modelSet.add(str.trim());
								}
							}
						}
					}
					
				}*/
				
				if (yMap.size() > 0) {
					if(yMap.size() == sameMap.size()){
						boolean isSame = compareMap(yMap, sameMap);
						if (isSame) {
							//配置表和人口库中配置一致,该业务按钮可以显示	
							//获得业务模块的值
							modelSet.addAll(this.splitModelTypeForQuick(configMap,"modeltype"));
						}
					}
				}else if(yMap.size() == 0){
					//说明人口业务配置表中没有配置信息，所有的人口信息均可显示该业务
					//获得业务模块的值
					modelSet.addAll(this.splitModelTypeForQuick(configMap,"modeltype"));
				}			
		}//循环结束
		if (null != modelSet && modelSet.size() > 0) {
			List<Map<String, Object>> modelTypeList = new ArrayList<Map<String, Object>>();
			//获得业务模块的相关信息，图标路径
			modelTypeList = this.getModelTypeInfoForQuick(modelSet);
			j.setSuccess(true);
			j.setObj(JSONHelper.toJSONString(modelTypeList));
		} else {
			j.setSuccess(false);
			j.setMsg("没有可显示的模块按钮");
		}
		return j;
	}
	/**
	 * 获得业务模块后，根据人口库id和业务模块code，获得该用户在该业务模块下可以办理的业务
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "showBusinessBtn")
	@ResponseBody
	public AjaxJson showBusinessBtnByModel(ServletRequest request){
		AjaxJson j = new AjaxJson();
		
		Map<String,Object> parameters =  getParametersStartingWith(request, "EQ_");
		
		//获得人口库信息
		Map<String, Object> rkMap = this.getRkxxForQuick(parameters);
		if(null == rkMap || rkMap.size() == 0){
			j.setSuccess(false);
			j.setMsg("人口库中信息为空");
			return j;
		}
		//获得人口业务配置表中的相关信息
		List<Map<String, Object>> configList = this.getRkYwConfigForQuick(parameters);
		if(null == configList || configList.size() == 0){
			j.setMsg("人口回写配置表中无相关配置");
			j.setSuccess(false);
			return j;
		}
		//获得人口库信息与人口业务配置表信息需要比对的字段
		String[] fieldArray = this.getCompareFieldForQuick();
		if (null == fieldArray || fieldArray.length == 0){
			j.setMsg("需要比对的字段信息为空");
			j.setSuccess(false);
			return j;
		}
		
		//可以显示的业务按钮列表
		List<Map<String, Object>> btnList = new ArrayList<Map<String, Object>>();

		for (Map<String, Object> configMap : configList) {
				Map<String, Object> yMap = new HashMap<String, Object>();//存放业务配置表中配置为Y的配置项
				Map<String, Object> sameMap = new HashMap<String, Object>();//存放业务配置表中配置为Y且人口库中配置项相同的配置项
				for (int i = 0; i < fieldArray.length; i++) {
					Object rkStr = rkMap.get(fieldArray[i]);
					Object configStr = configMap.get(fieldArray[i]);
					
					/**比对模式：以人口业务配置表中为基准，若该表中为空，则不限制显示；
					  * 若不为空，则人口信息表中与之一致才显示，且只需满足人口业务配置表中不为空的项即可。
					  */
					if(null != configStr && !"".equals(configStr+"") && !"null".equals(configStr+"")){						
						yMap.put(fieldArray[i], configStr);//记录人口业务配置表中不为空的字段
						if(null != rkStr && !"".equals(rkStr+"") && !"null".equals(rkStr+"") && (configStr+"").equals(rkStr+"")){
							sameMap.put(fieldArray[i], configStr);//记录人口信息表中不为空且与人口业务配置表中字段相等的字段
						}
					}
					
					/*//该比对方式为：只要人口信息表中与人口业务配置表中一致，按钮则显示，即使都不配置均为null，也显示					
					if(null == configStr || "".equals(configStr+"")){
						if(null == rkStr){
							sameMap.put(fieldArray[i], configStr);
						}else if("".equals(rkStr+"")){
							sameMap.put(fieldArray[i], configStr);
						}
					}else {
						if((configStr+"").equals(rkStr+"")){
							sameMap.put(fieldArray[i], configStr);
						}
					}*/
					/* 该比对方式为：首先，在sc_rk_yw_config表中配置为‘Y’,且人口信息表中与人口业务配置表中一致，按钮才显示
					 * if (null != configStr && "Y".equals(configStr)) {
						//首先，保证配置表中配置为Y，说明业务必须改配置项						
						yMap.put(fieldArray[i], configStr);
						if (configStr.equals(rkStr)) {
							//然后，若配置表中的值与人口中的值相同。
							sameMap.put(fieldArray[i], configStr);
						}
					}*/
				}
				/* 该比对方式为：首先，在sc_rk_yw_config表中配置为‘Y’,且人口信息表中与人口业务配置表中一致，按钮才显示
				 * if (yMap.size() > 0 && yMap.size() == sameMap.size()) {
					boolean isSame = compareMap(yMap, sameMap);
					if (isSame) {
						//说明该按钮可以显示
						//保存可以显示的业务和业务描述 
						Map<String, Object> btnMap = new HashMap<String, Object>();
						btnMap.put("id", configMap.get("id"));
						btnMap.put("key", configMap.get(showBtnCode));
						btnMap.put("value", configMap.get(showBtnName));
						btnMap.put("type", configMap.get("businesstype"));
						btnMap.put("url", configMap.get(bizUrl));
						btnMap.put("field", configMap.get("rkxx_field"));
						btnMap.put("ywtbpath", configMap.get("ywtbpath"));
						btnMap.put("ywtb", configMap.get("ywtb"));
						btnMap.put("ywtbid", configMap.get("ywtbid"));
						btnList.add(btnMap);
					}
				} else {

				}*/
				/*if (sameMap.size() > 0 && sameMap.size() == fieldArray.length){
					//说明该按钮可以显示
					//保存可以显示的业务和业务描述 
					Map<String, Object> btnMap = new HashMap<String, Object>();
					btnMap.put("id", configMap.get("id"));
					btnMap.put("key", configMap.get(showBtnCode));
					btnMap.put("value", configMap.get(showBtnName));
					btnMap.put("type", configMap.get("businesstype"));
					btnMap.put("url", configMap.get(bizUrl));
					btnMap.put("field", configMap.get("rkxx_field"));
					btnMap.put("ywtbpath", configMap.get("ywtbpath"));
					btnMap.put("ywtb", configMap.get("ywtb"));
					btnMap.put("ywtbid", configMap.get("ywtbid"));
					btnList.add(btnMap);
				}*/
				
				if (yMap.size() > 0) {
					if(yMap.size() == sameMap.size()){
						boolean isSame = compareMap(yMap, sameMap);
						if (isSame) {
							//说明该按钮可以显示
							//保存可以显示的业务和业务描述 							
							btnList.addAll(this.getBusinessInfoForQuick(configMap));							
						}
					}
				}else if(yMap.size() == 0){
					//说明人口业务配置表中没有配置信息，所有的人口信息均可显示该业务
					//说明该按钮可以显示
					//保存可以显示的业务和业务描述 
					btnList.addAll(this.getBusinessInfoForQuick(configMap));
				}
				
		}
		if(null != btnList && btnList.size() > 0){
			j.setSuccess(true);
			j.setObj(JSONHelper.toJSONString(btnList));
		}else{
			j.setSuccess(false);
			j.setMsg("没有可办理的业务");
			//j.setObj(JSONHelper.toJSONString(btnList));
		}
		return j;		
	}
	/**
	 * 根据业务配置表的id，获得业务配置表中相应的信息
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getScRkYwConfigById")
	@ResponseBody
	public AjaxJson getScRkYwConfigById(ServletRequest request){
		AjaxJson j = new AjaxJson();

		Map<String,Object> parameters =  getParametersStartingWith(request, "EQ_");
		String table = QuickBusinessConfig.getValue("rk_yw_table_name");	
		Map<String, Object> map = dataBaseService.findOneForJdbc(table, parameters.get("id").toString());
		
		j.setSuccess(true);
		j.setObj(JSONHelper.toJSONString(map));
		 
		return j;
	}
	/**
	 * 比对两个map的key，value
	 * @param map1
	 * @param map2
	 * @return
	 */
	private static boolean compareMap(Map<String,Object> map1, Map<String,Object> map2) {
	    boolean contain = false;
	    for (Object o : map1.keySet()) {
	        contain = map2.containsKey(o);
	        if (contain) {
	            contain = map1.get(o).equals(map2.get(o));
	        }
	        if (!contain) {
	            return false;
	        }
	    }
	    return true;
	}
	/**
	 * 快捷业务中，根据身份证号获得人口基本信息数据
	 * @param parameters
	 * @return
	 */
	private Map<String, Object> getRkxxForQuick(Map<String,Object> parameters){
		
		String sfzh = parameters.get("cert_id")+"";
		Map<String, Object> rkMap = new HashMap<String, Object>();

		Map<String, Object>  configsRk = configService.queryConfigsByCode(QuickBusinessConfig.getValue("rk_table_name"),"");	
		String rkTableName = (String) configsRk.get(CgAutoListConstant.TABLENAME);
		List<CgFormFieldEntity> beansRk = (List<CgFormFieldEntity>) configsRk.get(CgAutoListConstant.FILEDS);
		StringBuffer fieldRk = new StringBuffer("");
		if(null != beansRk && beansRk.size() > 0){
			for(int i= 0;i<beansRk.size();i++){
				if(i == beansRk.size() - 1){
					fieldRk.append(beansRk.get(i).getFieldName());
				}else{
					fieldRk.append(beansRk.get(i).getFieldName()).append(",");
				}
			}				
		}
		Map<String,Object> paramsRk =  new HashMap<String,Object>();
		if(null != sfzh && !"".equals(sfzh)){
			paramsRk.put("sfzh", "='"+sfzh+"'");			
		}
		paramsRk.put("delflag", "='0'");
		List<Map<String, Object>> rkList=cgTableService.querySingle(rkTableName, fieldRk.toString(), paramsRk,"create_date","DESC", 1,100);
		if(null != rkList && rkList.size() > 0){
			rkMap = rkList.get(0);
		}
		System.out.println("*******人口库信息rkMap="+rkMap);
		

		return rkMap;
	}
	/**
	 * 快捷业务中，查询人口业务配置相关信息
	 * @param parameters
	 * @return
	 */
	private List<Map<String, Object>> getRkYwConfigForQuick(Map<String, Object> parameters) {
		//获得人口业务配置表信息************************************************开始
		String modelType = null;		
		if(parameters.containsKey("modelType")){
			modelType = parameters.get("modelType") + "";
		}

		String config = QuickBusinessConfig.getValue("rk_yw_table_name");

		Map<String, Object> configs = configService.queryConfigsByCode(config, "");
		String table = (String) configs.get(CgAutoListConstant.TABLENAME);
		List<CgFormFieldEntity> beans = (List<CgFormFieldEntity>) configs.get(CgAutoListConstant.FILEDS);
		StringBuffer field = new StringBuffer("");
		if (null != beans && beans.size() > 0) {
			for (int i = 0; i < beans.size(); i++) {
				if (i == beans.size() - 1) {
					field.append(beans.get(i).getFieldName());
				} else {
					field.append(beans.get(i).getFieldName()).append(",");
				}
			}
		}
		Map<String, Object> params = new HashMap<String, Object>();
		/*if (null != modelType && null != modelType && !"null".equals(modelType)) {
			params.put("modeltype", " like '%" + modelType + "%'");
		}*/
		params.put("delflag", "='0'");
		List<Map<String, Object>> configList = new ArrayList<Map<String, Object>>();
		configList = cgTableService.querySingle(table, field.toString(), params, "xh", "ASC", 1, 1000);
		System.out.println("*******配置信息configList=" + configList);
		//获得人口业务配置表信息************************************************结束
		return configIsInModeTypeForQuick(configList,modelType);
		//return configList;
	}
	/**
	 * 获得人口库信息与人口业务配置表信息需要比对的字段，信息配置在quickBusiness.properties中
	 * @return
	 */
	private String[] getCompareFieldForQuick() {

		String compareField = QuickBusinessConfig.getValue("compare_field");
		System.out.println("**********需要对比的字段为compareField=" + compareField);
		String[] fieldArray = null;
		if (null != compareField) {
			fieldArray = compareField.split(",");
		}
		return fieldArray;
	}
	/**
	 * 根据modelSet中的业务模块code，获得模块按钮的相关信息
	 * @param modelSet
	 * @return
	 */
	private List<Map<String, Object>> getModelTypeInfoForQuick(Set<String> modelSet){
		
		List<Map<String, Object>> modelTypeList = new ArrayList<Map<String, Object>>();
		
		String modelType = QuickBusinessConfig.getValue("show_btn_modleType");
		List<TSType> tsTypeList = this.getDictionaryByGroupCodeInList(modelType, modelSet);
		
		if (null != tsTypeList && tsTypeList.size() > 0) {
			for (TSType ts : tsTypeList) {
				Map<String, Object> tmpMap = new HashMap<String, Object>();
				tmpMap.put("key", ts.getTypecode());
				tmpMap.put("value", ts.getTypename());

				String iconId = ts.getTypeiconid();
				TSIcon tsTcon = null;
				List<TSIcon> iconList = new ArrayList<TSIcon>();
				if(null != iconId && !"".equals(iconId) && !"null".equals(iconId)){
					iconList = systemService.findByProperty(TSIcon.class, "id", iconId);
				}
				if(null != iconList && iconList.size() > 0){
					tsTcon = iconList.get(0);
					tmpMap.put("iconName", tsTcon.getIconName());
					tmpMap.put("iconPath", tsTcon.getIconPath());					
					tmpMap.put("picName", tsTcon.getPicName());
				}			
				modelTypeList.add(tmpMap);
			}
		}
		
		return modelTypeList;
	}
	
	/**
	 * 根据人口业务配置表中的字段及值的map，解析splitStr指定的字段的值
	 * @param configMap
	 * @return
	 */
	private Set<String> splitModelTypeForQuick(Map<String, Object> configMap,String splitStr){
		  Set<String> modelSet = new HashSet<String>();
		  String tmp = configMap.get(splitStr) + "";
			if (null != tmp && !"".equals(tmp) && !"null".equals(tmp)) {
				String[] tmpArr = tmp.split(",");
				if (null != tmpArr && tmpArr.length > 0) {
					for (String str : tmpArr) {
						if (!modelSet.contains(str.trim())) {
							//将可以显示的模块按钮放入
							modelSet.add(str.trim());
						}
					}
				}
			}
		  return modelSet;
	  }
	/**
	 * 根据人口业务配置表中的字段及值的map,获得相关信息
	 * @param configMap
	 * @return
	 */
	private List<Map<String, Object>> getBusinessInfoForQuick(Map<String, Object> configMap){
		List<Map<String, Object>> btnList = new ArrayList<Map<String, Object>>();
		Map<String, Object> btnMap = new HashMap<String, Object>();
		btnMap.put("id", configMap.get("id"));
		btnMap.put("key", configMap.get("businesscode"));
		btnMap.put("value", configMap.get("businessdes"));
		btnMap.put("type", configMap.get("businesstype"));
		btnMap.put("url", configMap.get("bizurl"));
		btnMap.put("field", configMap.get("rkxx_field"));
		btnMap.put("ywtbpath", configMap.get("ywtbpath"));
		btnMap.put("ywtb", configMap.get("ywtb"));
		btnMap.put("ywtbid", configMap.get("ywtbid"));
		btnList.add(btnMap);
		
		return btnList;
	}
	/**
	 * 判断配置人口业务配置表中的数据是否属于某个业务模块
	 * 人口业务配置表中的数据可能属于多个模块，以bljz,mzyw,blzjysq方式存储
	 * @param configList
	 * @param modelType
	 * @return
	 */
	private List<Map<String, Object>> configIsInModeTypeForQuick(List<Map<String, Object>> configList, String modelType) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (StringUtils.isEmpty(modelType)) {
			return configList;
		}
		if (null != configList && configList.size() > 0) {
			for (int j = 0; j < configList.size(); j++) {
				boolean isExist = false;
				Map<String, Object> map = configList.get(j);
				String model = map.get("modeltype") + "";
				if (null != model && !"".equals(model) && !"null".equals(model)) {
					String[] arr = model.split(",");					
					for (int i = 0; i < arr.length; i++) {
						if (modelType.equals(arr[i])) {
							isExist = true;
							break;
						}
					}					
				}
				if (isExist) {
					list.add(map);
				}
			}
		}
		return list;
	}
}
