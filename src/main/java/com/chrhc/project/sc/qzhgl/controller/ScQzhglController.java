package com.chrhc.project.sc.qzhgl.controller;
import com.chrhc.project.sc.qzhgl.entity.ScQzhglEntity;
import com.chrhc.project.sc.qzhgl.entity.ScYwlxpzxsubEntity;
import com.chrhc.project.sc.qzhgl.service.ScQzhglServiceI;
import com.chrhc.project.sc.qzhgl.page.ScQzhglPage;
import com.chrhc.project.sc.qzhgl.entity.ScQzhglsubEntity;

import java.util.Collections;
import java.util.Comparator;
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
 * @Description: 签章管理
 * @author onlineGenerator
 * @date 2015-05-14 09:36:58
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scQzhglController")
public class ScQzhglController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScQzhglController.class);

	@Autowired
	private ScQzhglServiceI scQzhglService;
	@Autowired
	private SystemService systemService;


	/**
	 * 签章管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scQzhgl")
	public ModelAndView scQzhgl(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/qzhgl/scQzhglList");
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
	public void datagrid(ScQzhglEntity scQzhgl,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScQzhglEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scQzhgl);
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scQzhglService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 根据id获得签章管理子表数据
	 * @param scQzhgl
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getQzhglsub")
	@ResponseBody
	public AjaxJson getQzhglsub(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "获得签章数据成功";
		//List<ScQzhglsubEntity> subList = systemService.findByProperty(ScQzhglsubEntity.class, "belongId", id);
		String hql =  "from ScQzhglsubEntity where 1 = 1 AND delflag='0' AND belongId = ?  order by sortNo";
		List<ScQzhglsubEntity> subList = systemService.findHql(hql, id);
		/*if (null != subList && subList.size() > 0) {
			Collections.sort(subList, new Comparator(){
				@Override
				public int compare(Object o1, Object o2) {	
					Integer no1 = ((ScQzhglsubEntity)o1).getSortNo();
					Integer no2 = ((ScQzhglsubEntity)o2).getSortNo();
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
		if(null != subList && subList.size() > 0){
			j.setObj(subList);
			j.setSuccess(true);
			j.setMsg(message);
		}else{
			message = "获得签章数据失败";
			j.setSuccess(false);
			j.setMsg(message);
		}
		return j;
	}
	/**
	 * 根据人口库数据的id获得信息,判断是否需要回写用户信息，若需要，则回写。
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "doRkxxById")
	@ResponseBody
	public AjaxJson doRkxxById(String id,String name,String cert_id,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		StringBuffer sb = new StringBuffer("");
		sb.append(" select * FROM sc_rkjbxxnew where 1=1 ");
		sb.append(" and id='"+id+"'");
		sb.append(" and xm='"+name+"'");
		sb.append(" and sfzh='"+cert_id+"'");
		
		List<Object> list= systemService.findListbySql(sb.toString());
		if(null != list && list.size() > 0){
			//说明读取的信息和库中的信息一致，不用回写处理
		}else{	
			StringBuffer updateSb = new StringBuffer("");
			updateSb.append("update sc_rkjbxxnew set ");
			updateSb.append(" xm='"+name+"',");
			updateSb.append(" sfzh='"+cert_id+"'");
			updateSb.append(" where id='"+id+"'");
			int n = systemService.updateBySqlString(updateSb.toString());
			if(n ==1){	
				j.setSuccess(true);
			}else{
				j.setSuccess(false);
			}
		}
		return j;
	}
	
	/**
	 * 删除签章管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScQzhglEntity scQzhgl, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scQzhgl = systemService.getEntity(ScQzhglEntity.class, scQzhgl.getId());
		String message = "签章管理删除成功";
		try{
			scQzhglService.delMain(scQzhgl);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "签章管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除签章管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "签章管理删除成功";
		try{
			for(String id:ids.split(",")){
				ScQzhglEntity scQzhgl = systemService.getEntity(ScQzhglEntity.class,
				id
				);
				scQzhglService.delMain(scQzhgl);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "签章管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加签章管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScQzhglEntity scQzhgl,ScQzhglPage scQzhglPage, HttpServletRequest request) {
		List<ScQzhglsubEntity> scQzhglsubList =  scQzhglPage.getScQzhglsubList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			scQzhglService.addMain(scQzhgl, scQzhglsubList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "签章管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新签章管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScQzhglEntity scQzhgl,ScQzhglPage scQzhglPage, HttpServletRequest request) {
		List<ScQzhglsubEntity> scQzhglsubList =  scQzhglPage.getScQzhglsubList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			scQzhglService.updateMain(scQzhgl, scQzhglsubList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新签章管理失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 签章管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScQzhglEntity scQzhgl, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scQzhgl.getId())) {
			scQzhgl = scQzhglService.getEntity(ScQzhglEntity.class, scQzhgl.getId());
			req.setAttribute("scQzhglPage", scQzhgl);
		}
		return new ModelAndView("com/chrhc/project/sc/qzhgl/scQzhgl-add");
	}
	
	/**
	 * 签章管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScQzhglEntity scQzhgl, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scQzhgl.getId())) {
			scQzhgl = scQzhglService.getEntity(ScQzhglEntity.class, scQzhgl.getId());
			req.setAttribute("scQzhglPage", scQzhgl);
		}
		return new ModelAndView("com/chrhc/project/sc/qzhgl/scQzhgl-update");
	}
	
	
	/**
	 * 加载明细列表[签章管理]
	 * 
	 * @return
	 */
	@RequestMapping(params = "scQzhglsubList")
	public ModelAndView scQzhglsubList(ScQzhglEntity scQzhgl, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = scQzhgl.getId();
		//===================================================================================
		//查询-签章管理
	    String hql0 = "from ScQzhglsubEntity where 1 = 1 AND bELONG_ID = ? ";
	    try{
	    	List<ScQzhglsubEntity> scQzhglsubEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("scQzhglsubList", scQzhglsubEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/chrhc/project/sc/qzhgl/scQzhglsubList");
	}
	
}
