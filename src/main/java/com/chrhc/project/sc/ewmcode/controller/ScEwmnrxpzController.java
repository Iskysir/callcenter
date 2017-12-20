package com.chrhc.project.sc.ewmcode.controller;
import com.chrhc.Interceptor.InterceptorException;
import com.chrhc.project.sc.ewmcode.entity.ScEwmnrxpzEntity;
import com.chrhc.project.sc.ewmcode.service.ScEwmnrxpzServiceI;
import com.chrhc.project.sc.ewmcode.page.ScEwmnrxpzPage;
import com.chrhc.project.sc.ewmcode.entity.ScEwmnrxpzsubEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;


/**   
 * @Title: Controller
 * @Description: 二维码内容项配置
 * @author onlineGenerator
 * @date 2015-05-12 15:51:44
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scEwmnrxpzController")
public class ScEwmnrxpzController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScEwmnrxpzController.class);

	@Autowired
	private ScEwmnrxpzServiceI scEwmnrxpzService;
	@Autowired
	private SystemService systemService;


	/**
	 * 二维码内容项配置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scEwmnrxpz")
	public ModelAndView scEwmnrxpz(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/ewmcode/scEwmnrxpzList");
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
	public void datagrid(ScEwmnrxpzEntity scEwmnrxpz,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScEwmnrxpzEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scEwmnrxpz);
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scEwmnrxpzService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除二维码内容项配置
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScEwmnrxpzEntity scEwmnrxpz, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scEwmnrxpz = systemService.getEntity(ScEwmnrxpzEntity.class, scEwmnrxpz.getId());
		String message = "二维码内容项配置删除成功";
		try{
			scEwmnrxpzService.deleteLogic(scEwmnrxpz);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "二维码内容项配置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除二维码内容项配置
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "二维码内容项配置删除成功";
		try{
			for(String id:ids.split(",")){
				ScEwmnrxpzEntity scEwmnrxpz = systemService.getEntity(ScEwmnrxpzEntity.class,
				id
				);
				scEwmnrxpzService.deleteLogic(scEwmnrxpz);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "二维码内容项配置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加二维码内容项配置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScEwmnrxpzEntity scEwmnrxpz,ScEwmnrxpzPage scEwmnrxpzPage, HttpServletRequest request) {
		List<ScEwmnrxpzsubEntity> scEwmnrxpzsubList =  scEwmnrxpzPage.getScEwmnrxpzsubList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			scEwmnrxpzService.addMain(scEwmnrxpz, scEwmnrxpzsubList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			
			e.printStackTrace();
			message = "二维码内容项配置添加失败";
			throw new BusinessException(e.getMessage()); 

		}
		j.setMsg(message);
		j.setObj(scEwmnrxpz);
		return j;
	}
	/**
	 * 更新二维码内容项配置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScEwmnrxpzEntity scEwmnrxpz,ScEwmnrxpzPage scEwmnrxpzPage, HttpServletRequest request) {
		List<ScEwmnrxpzsubEntity> scEwmnrxpzsubList =  scEwmnrxpzPage.getScEwmnrxpzsubList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			scEwmnrxpzService.updateMain(scEwmnrxpz, scEwmnrxpzsubList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			if (e instanceof InterceptorException){
                //类似此处的处理
				throw new InterceptorException("");
			} else {
				e.printStackTrace();
				message = "更新二维码内容项配置失败";
				throw new BusinessException(e.getMessage());
			}
			
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 二维码内容项配置新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScEwmnrxpzEntity scEwmnrxpz, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scEwmnrxpz.getId())) {
			scEwmnrxpz = scEwmnrxpzService.getEntity(ScEwmnrxpzEntity.class, scEwmnrxpz.getId());
			req.setAttribute("scEwmnrxpzPage", scEwmnrxpz);
			
			List<Map<String, Object>> fieldList = scEwmnrxpzService.getTableField(scEwmnrxpz.getSourcetable());
			req.setAttribute("fieldlist", fieldList);
		}
		
		return new ModelAndView("com/chrhc/project/sc/ewmcode/scEwmnrxpz-add");
	}
	
	/**
	 * 二维码内容项配置编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScEwmnrxpzEntity scEwmnrxpz, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scEwmnrxpz.getId())) {
			scEwmnrxpz = scEwmnrxpzService.getEntity(ScEwmnrxpzEntity.class, scEwmnrxpz.getId());
			req.setAttribute("scEwmnrxpzPage", scEwmnrxpz);
			
			List<Map<String, Object>> fieldList = scEwmnrxpzService.getTableField(scEwmnrxpz.getSourcetable());
			req.setAttribute("fieldlist", fieldList);
			
			
			//===================================================================================
			//获取参数
			Object id0 = scEwmnrxpz.getId();
			String tablename = scEwmnrxpz.getSourcetable();

			//===================================================================================
			//查询-二维码返回信息
		    String hql0 = "from ScEwmnrxpzsubEntity where 1 = 1 AND pZID = ? ";
		    try{
		    	List<ScEwmnrxpzsubEntity> scEwmnrxpzsubEntityList = systemService.findHql(hql0,id0);
				req.setAttribute("scEwmnrxpzsubList", scEwmnrxpzsubEntityList);
				
		    }catch(Exception e){
				logger.info(e.getMessage());
		    }
		}
		return new ModelAndView("com/chrhc/project/sc/ewmcode/scEwmnrxpz-update");
	}
	
	/**
	 * 二维码内容项配置查看页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goDetail")
	public ModelAndView goDetail(ScEwmnrxpzEntity scEwmnrxpz, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scEwmnrxpz.getId())) {
			scEwmnrxpz = scEwmnrxpzService.getEntity(ScEwmnrxpzEntity.class, scEwmnrxpz.getId());
			req.setAttribute("scEwmnrxpzPage", scEwmnrxpz);
			
			List<Map<String, Object>> fieldList = scEwmnrxpzService.getTableField(scEwmnrxpz.getSourcetable());
			req.setAttribute("fieldlist", fieldList);
		}
		return new ModelAndView("com/chrhc/project/sc/ewmcode/scEwmnrxpz-detail");
	}
	
	/**
	 * 加载明细列表[二维码返回信息]
	 * 
	 * @return
	 */
	@RequestMapping(params = "scEwmnrxpzsubList")
	public ModelAndView scEwmnrxpzsubList(ScEwmnrxpzEntity scEwmnrxpz, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = scEwmnrxpz.getId();
		String tablename = scEwmnrxpz.getSourcetable();

		//===================================================================================
		//查询-二维码返回信息
	    String hql0 = "from ScEwmnrxpzsubEntity where 1 = 1 AND pZID = ? ";
	    try{
	    	List<ScEwmnrxpzsubEntity> scEwmnrxpzsubEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("scEwmnrxpzsubList", scEwmnrxpzsubEntityList);
			
			//取得模板表field
			if(id0 != null && !id0.equals("")){
				Map<String, Object> scEwmnrxpzTemp = scEwmnrxpzService.findOneForJdbc("select * from sc_ewmnrxpz where id = '" + id0 + "'");
				
				tablename = scEwmnrxpzTemp.get("sourcetable").toString();
				
				List<Map<String, Object>> fieldList = scEwmnrxpzService.getTableField(tablename);
				req.setAttribute("fieldlist", fieldList);
			}
			
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/chrhc/project/sc/ewmcode/scEwmnrxpzsubList");
	}
	
	/**
	 * 加载数据来源表字段
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getTableField")
	@ResponseBody
	public AjaxJson getTableField(String tblname, HttpServletRequest req, HttpServletResponse response){
		
		AjaxJson j = new AjaxJson();
		String tablename = req.getParameter("tablename").toString();
		
		if(!StringUtil.isEmpty(tablename)){
			List<Map<String, Object>> fieldList = scEwmnrxpzService.getTableField(tablename);
			
			j.setObj(fieldList);
			
		}
		return j;
		
	}
	
	
	/**
	 * 判断关联信息
	 * 
	 */
	@RequestMapping(params = "checkdel")
	@ResponseBody
	public AjaxJson checkdel(HttpServletRequest req){
		AjaxJson j = new AjaxJson();
		Boolean flag = true;
		String message = "";
		String mainTable = req.getParameter("mainTable");
		String zTable = req.getParameter("zTable");
		String wjIdName = req.getParameter("wjIdName");
		String id = req.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(id)){
				String [] ids = id.split(",");
				for(String zjid:ids){
					List<Map<String, Object>> list = scEwmnrxpzService.getGlcheck(mainTable, zTable, wjIdName, zjid);
					if(list != null && list.size() > 0){
						message = "存在关联信息记录不能删除！";
						flag = false;
						break;
					}
				}
				
			}
			
		} catch (Exception e) {
			flag = false;
			message = "删除失败，请联系管理员！"+e.toString();
		}
		j.setSuccess(flag);
		j.setMsg(message);
		return j;
	}
}
