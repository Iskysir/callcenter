package com.chrhc.project.sc.zhyzh.controller;
import com.chrhc.project.sc.qzhgl.entity.ScQzhglsubEntity;
import com.chrhc.project.sc.zhyzh.entity.ScZhyfwzhEntity;
import com.chrhc.project.sc.zhyzh.entity.ScZhyzhfwdEntity;
import com.chrhc.project.sc.zhyzh.entity.ScZhyzhfwjlEntity;
import com.chrhc.project.sc.zhyzh.service.ScZhyzhfwdServiceI;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.context.annotation.Scope;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import org.jeecgframework.core.util.ExceptionUtil;



/**   
 * @Title: Controller
 * @Description: 志愿者服务队
 * @author onlineGenerator
 * @date 2015-05-08 13:24:19
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scZhyzhfwdController")
public class ScZhyzhfwdController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScZhyzhfwdController.class);

	@Autowired
	private ScZhyzhfwdServiceI scZhyzhfwdService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 志愿者服务队列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scZhyzhfwd")
	public ModelAndView scZhyzhfwd(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/zhyzh/scZhyzhfwdList");
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
	public void datagrid(ScZhyzhfwdEntity scZhyzhfwd,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScZhyzhfwdEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scZhyzhfwd, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scZhyzhfwdService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除志愿者服务队
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScZhyzhfwdEntity scZhyzhfwd, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scZhyzhfwd = systemService.getEntity(ScZhyzhfwdEntity.class, scZhyzhfwd.getId());
		message = "删除成功";
		try{
			//scZhyzhfwdService.delete(scZhyzhfwd);
			scZhyzhfwdService.deleteLogic(scZhyzhfwd);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 判断该志愿队是否有志愿者成员
	 * @param scZhyzhfwd
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "doCheckZhyzh")
	@ResponseBody
	public AjaxJson doCheckZhyzh(ScZhyzhfwdEntity scZhyzhfwd, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			//判断该志愿队是否有志愿者成员
			String hql0 = "from ScZhyfwzhEntity where 1 = 1 AND delflag='0' AND belongId = ? ";
			List<ScZhyfwzhEntity> scZhyfwzhList = systemService.findHql(hql0, scZhyzhfwd.getId());			
			if(null != scZhyfwzhList && scZhyfwzhList.size() > 0){
				message = "该志愿队有志愿者成员，不允许删除！";
				j.setSuccess(false);
				j.setMsg(message);
				return j;
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "判断该志愿队是否有志愿者成员失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 判断该志愿队是否有志愿服务记录
	 * @param scZhyzhfwd
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "doCheckZhyfwjl")
	@ResponseBody
	public AjaxJson doCheckZhyfwjl(ScZhyzhfwdEntity scZhyzhfwd, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			//判断该志愿队是否有志愿服务记录
			String hql0 = "from ScZhyzhfwjlEntity where 1 = 1 AND delflag='0' AND partId = ? ";
			List<ScZhyzhfwjlEntity> ScZhyzhfwjlList = systemService.findHql(hql0, scZhyzhfwd.getId());
			if(null != ScZhyzhfwjlList && ScZhyzhfwjlList.size() > 0){
				message = "该志愿队有志愿服务记录，不允许删除";
				j.setSuccess(false);
				j.setMsg(message);
				return j;
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "判断该志愿队是否有志愿服务记录失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 批量删除志愿者服务队
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "批量删除成功";
		try{
			List<String> zhyzhList = this.doCheckBatchZhyzh(ids);
			List<String> fwjlList = this.doCheckBatchZhyfwjl(ids);
			
			if((null == zhyzhList || zhyzhList.size() == 0) && (null == fwjlList || fwjlList.size() == 0)){
				//若志愿服务队没有志愿者成员，且没有服务记录，允许删除
				for(String id:ids.split(",")){
					ScZhyzhfwdEntity scZhyzhfwd = systemService.getEntity(ScZhyzhfwdEntity.class, 
					id
					);
					//scZhyzhfwdService.delete(scZhyzhfwd);
					scZhyzhfwdService.deleteLogic(scZhyzhfwd);
					systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
				}
			}else{
				if(null != zhyzhList && zhyzhList.size() > 0){
					message = "志愿服务队有志愿者成员，不允许删除";
					j.setSuccess(false);
				}else if(null != fwjlList || fwjlList.size() > 0){
					message = "志愿服务队有志愿服务记录，不允许删除";
					j.setSuccess(false);
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
			message = "批量删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	 /**
	  * 批量删除时，判断志愿服务队是否有志愿者成员
	  * @param ids
	  * @return
	  */
	 private List<String> doCheckBatchZhyzh(String ids){
		 List<String> list = new ArrayList<String>();
		 
		 String hql0 = "from ScZhyfwzhEntity where 1 = 1 AND delflag='0' AND belongId = ? ";
		 for(String id:ids.split(",")){
			//判断该志愿队是否有志愿者成员
			List<ScZhyfwzhEntity> scZhyfwzhList = systemService.findHql(hql0,id);;			
			if(null != scZhyfwzhList && scZhyfwzhList.size() > 0){
				list.add(id);
			}
		}
		 return list;
	 }
	/**
	 * 批量删除时，判断志愿服务队是否有服务记录
	 * @param ids
	 * @return
	 */
	private List<String> doCheckBatchZhyfwjl(String ids) {
		List<String> list = new ArrayList<String>();
		
		String hql0 = "from ScZhyzhfwjlEntity where 1 = 1 AND delflag='0' AND partId = ? ";
		for (String id : ids.split(",")) {			
		    List<ScZhyzhfwjlEntity> ScZhyzhfwjlList = systemService.findHql(hql0,id);		
			if (null != ScZhyzhfwjlList && ScZhyzhfwjlList.size() > 0) {
				list.add(id);
			}
		}
		return list;
	}
	/**
	 * 添加志愿者服务队
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScZhyzhfwdEntity scZhyzhfwd, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "志愿者服务队添加成功";
		try{
			scZhyzhfwdService.save(scZhyzhfwd);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "志愿者服务队添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新志愿者服务队
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScZhyzhfwdEntity scZhyzhfwd, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "志愿者服务队更新成功";
		ScZhyzhfwdEntity t = scZhyzhfwdService.get(ScZhyzhfwdEntity.class, scZhyzhfwd.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scZhyzhfwd, t);
			scZhyzhfwdService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "志愿者服务队更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 志愿者服务队新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScZhyzhfwdEntity scZhyzhfwd, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scZhyzhfwd.getId())) {
			scZhyzhfwd = scZhyzhfwdService.getEntity(ScZhyzhfwdEntity.class, scZhyzhfwd.getId());
			req.setAttribute("scZhyzhfwdPage", scZhyzhfwd);
		}
		return new ModelAndView("com/chrhc/project/sc/zhyzh/scZhyzhfwd-add");
	}
	/**
	 * 志愿者服务队编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScZhyzhfwdEntity scZhyzhfwd, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scZhyzhfwd.getId())) {
			scZhyzhfwd = scZhyzhfwdService.getEntity(ScZhyzhfwdEntity.class, scZhyzhfwd.getId());
			req.setAttribute("scZhyzhfwdPage", scZhyzhfwd);
		}
		return new ModelAndView("com/chrhc/project/sc/zhyzh/scZhyzhfwd-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/zhyzh/scZhyzhfwdUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScZhyzhfwdEntity scZhyzhfwd,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScZhyzhfwdEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scZhyzhfwd, request.getParameterMap());
		List<ScZhyzhfwdEntity> scZhyzhfwds = this.scZhyzhfwdService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"志愿者服务队");
		modelMap.put(NormalExcelConstants.CLASS,ScZhyzhfwdEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("志愿者服务队列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scZhyzhfwds);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScZhyzhfwdEntity scZhyzhfwd,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "志愿者服务队");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScZhyzhfwdEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA,null);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<ScZhyzhfwdEntity> listScZhyzhfwdEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScZhyzhfwdEntity.class,params);
				for (ScZhyzhfwdEntity scZhyzhfwd : listScZhyzhfwdEntitys) {
					scZhyzhfwdService.save(scZhyzhfwd);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
}
