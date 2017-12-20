package com.chrhc.project.sc.qzhgl.controller;
import com.chrhc.project.sc.qzhgl.entity.ScQzhglEntity;
import com.chrhc.project.sc.qzhgl.entity.ScYwlxpzxEntity;
import com.chrhc.project.sc.qzhgl.service.ScYwlxpzxServiceI;
import com.chrhc.project.sc.qzhgl.page.ScYwlxpzxPage;
import com.chrhc.project.sc.qzhgl.entity.ScYwlxpzxsubEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;


/**   
 * @Title: Controller
 * @Description: 业务类型配置项
 * @author onlineGenerator
 * @date 2015-05-14 09:37:23
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scYwlxpzxController")
public class ScYwlxpzxController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScYwlxpzxController.class);

	@Autowired
	private ScYwlxpzxServiceI scYwlxpzxService;
	@Autowired
	private SystemService systemService;


	/**
	 * 业务类型配置项列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scYwlxpzx")
	public ModelAndView scYwlxpzx(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/qzhgl/scYwlxpzxList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(ScYwlxpzxEntity scYwlxpzx,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScYwlxpzxEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scYwlxpzx);
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scYwlxpzxService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 根据业务类型获得配置项
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "getPzxsub")
	@ResponseBody
	public AjaxJson getPzxsub(HttpServletRequest request,String busType){
		AjaxJson j = new AjaxJson();
		HashMap<String,Object> subMap = new HashMap<String,Object>();
		String message="查询业务配置项子项数据成功";
		String hql0 = "from ScYwlxpzxEntity where 1 = 1 AND delflag='0' AND busType = ? ";
		String hql1 = "from ScYwlxpzxsubEntity where 1 = 1 AND delflag='0' AND pzxId = ? order by sortNo asc";
		//List<ScYwlxpzxEntity> list = systemService.findByProperty(ScYwlxpzxEntity.class, "busType", busType);
		List<ScYwlxpzxEntity> list = systemService.findHql(hql0, busType);
		if(null != list && list.size() > 0){
			List<ScYwlxpzxsubEntity> subList = systemService.findHql(hql1, list.get(0).getId());
			//List<ScYwlxpzxsubEntity> subList = systemService.findByProperty(ScYwlxpzxsubEntity.class,"pzxId",list.get(0).getId());
			/*StringBuffer sb = new StringBuffer("select * from sc_ywlxpzxsub where 1=1 and PZX_ID = '");
			sb.append(list.get(0).getId());
			sb.append("'");
			sb.append(" order by sort_no asc ");
			List<ScYwlxpzxsubEntity> subList = systemService.findListbySql(sb.toString());*/
			
			/*if (null != subList && subList.size() > 0) {
				Collections.sort(subList, new Comparator(){
					@Override
					public int compare(Object o1, Object o2) {	
						Integer no1 = ((ScYwlxpzxsubEntity)o1).getSortNo();
						Integer no2 = ((ScYwlxpzxsubEntity)o2).getSortNo();;
						if(null == no2 || "null".equals(no2) || "".equals(no2)){
							no2=new Integer(999);
						}
						if(null == no1 || "null".equals(no1) || "".equals(no1)){
							no1=new Integer(999);
						}
						if(no1.intValue() == no2.intValue()) {
							int a1 = ((ScYwlxpzxsubEntity)o1).getId().compareTo(((ScYwlxpzxsubEntity)o2).getId());
							return a1;							
						}else{
							int al = no1.compareTo(no2);
							return al;
						}
					}
				});
			}*/
			
			subMap.put("sub", subList);
			if(null == subList || subList.size() <= 0){
				message="未查询到该配置项对应的子项数据";
				j.setSuccess(false);
			}
		}else{
			message="未查询到该配置项数据";
			j.setSuccess(false);
		}
		j.setAttributes(subMap);
		j.setMsg(message);
		return j;
	}
	/**
	 * 删除签章配置项
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		ScYwlxpzxEntity scYwlxpzx = systemService.getEntity(ScYwlxpzxEntity.class, id);
		String message = "删除成功";
		try{
			List<String> list = this.doCheck(scYwlxpzx.getBusType());
			if(list != null && list.size() > 0){
				message = "该签章配置项有签章业务记录，不允许删除";
				j.setSuccess(false);
				j.setMsg(message);
				return j;
			}else{
				//scYwlxpzxService.delMain(scYwlxpzx);
				scYwlxpzxService.deleteLogic(scYwlxpzx);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 删除时，判断配置项是否已经有签章管理业务
	 * @param ids
	 * @param request
	 * @return
	 */
	private List<String> doCheck(String busType){
		List<String> list = new ArrayList<String>();
		String hql0 = "from ScQzhglEntity where 1 = 1 AND delflag='0' AND busType = ? ";
		//List<ScQzhglEntity> scQzhglEntityList = (List<ScQzhglEntity>) systemService.findByProperty(ScQzhglEntity.class,"busType",busType);
		List<ScQzhglEntity> scQzhglEntityList = systemService.findHql(hql0, busType);
		if(null != scQzhglEntityList && scQzhglEntityList.size() > 0){
			list.add(busType);
		}		
		return list;
	}
	/**
	 * 删除时，判断配置项是否已经有签章管理业务
	 * @param ids
	 * @param request
	 * @return
	 */
	private List<Object> doCheckBatch(String ids){
		List<Object> scQzhglEntityList = new ArrayList<Object>();
		String hql0 = "from ScQzhglEntity where 1 = 1 AND delflag='0' AND busType = ? ";
		for(String id:ids.split(",")){
			ScYwlxpzxEntity scYwlxpzx = systemService.getEntity(ScYwlxpzxEntity.class,id);
			//scQzhglEntityList.addAll((List<ScQzhglEntity>) systemService.findByProperty(ScQzhglEntity.class,"busType",scYwlxpzx.getBusType()));
			scQzhglEntityList.addAll(systemService.findHql(hql0, scYwlxpzx.getBusType()));
		}
			
		return scQzhglEntityList;
	}
	/**
	 * 批量删除业务类型配置项
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "删除成功";
		List<Object> scQzhglEntityList = this.doCheckBatch(ids);
		if(null != scQzhglEntityList && scQzhglEntityList.size() > 0){
			message = "签章配置项有签章业务记录，不允许删除";
			j.setSuccess(false);
			j.setMsg(message);
			return j;
		}
		try{
			for(String id:ids.split(",")){
				ScYwlxpzxEntity scYwlxpzx = systemService.getEntity(ScYwlxpzxEntity.class,
				id
				);
				scYwlxpzxService.delMain(scYwlxpzx);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加业务类型配置项
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScYwlxpzxEntity scYwlxpzx,ScYwlxpzxPage scYwlxpzxPage, HttpServletRequest request) {
		List<ScYwlxpzxsubEntity> scYwlxpzxsubList =  scYwlxpzxPage.getScYwlxpzxsubList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			scYwlxpzxService.addMain(scYwlxpzx, scYwlxpzxsubList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "业务类型配置项添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新业务类型配置项
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScYwlxpzxEntity scYwlxpzx,ScYwlxpzxPage scYwlxpzxPage, HttpServletRequest request) {
		List<ScYwlxpzxsubEntity> scYwlxpzxsubList =  scYwlxpzxPage.getScYwlxpzxsubList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			scYwlxpzxService.updateMain(scYwlxpzx, scYwlxpzxsubList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新业务类型配置项失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 业务类型配置项新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScYwlxpzxEntity scYwlxpzx, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scYwlxpzx.getId())) {
			scYwlxpzx = scYwlxpzxService.getEntity(ScYwlxpzxEntity.class, scYwlxpzx.getId());
			req.setAttribute("scYwlxpzxPage", scYwlxpzx);
		}
		return new ModelAndView("com/chrhc/project/sc/qzhgl/scYwlxpzx-add");
	}
	
	/**
	 * 业务类型配置项编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScYwlxpzxEntity scYwlxpzx, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scYwlxpzx.getId())) {
			scYwlxpzx = scYwlxpzxService.getEntity(ScYwlxpzxEntity.class, scYwlxpzx.getId());
			req.setAttribute("scYwlxpzxPage", scYwlxpzx);
		}
		return new ModelAndView("com/chrhc/project/sc/qzhgl/scYwlxpzx-update");
	}
	
	
	/**
	 * 加载明细列表[业务类型配置项]
	 * 
	 * @return
	 */
	@RequestMapping(params = "scYwlxpzxsubList")
	public ModelAndView scYwlxpzxsubList(ScYwlxpzxEntity scYwlxpzx, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = scYwlxpzx.getId();
		//===================================================================================
		//查询-业务类型配置项
	    String hql0 = "from ScYwlxpzxsubEntity where 1 = 1 AND pZX_ID = ? ";
	    try{
	    	List<ScYwlxpzxsubEntity> scYwlxpzxsubEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("scYwlxpzxsubList", scYwlxpzxsubEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/chrhc/project/sc/qzhgl/scYwlxpzxsubList");
	}
	
}
