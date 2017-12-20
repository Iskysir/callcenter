package com.chrhc.project.sc.endow.controller;
import com.chrhc.project.sc.endow.entity.ScEndowmentinsEntity;
import com.chrhc.project.sc.endow.service.ScEndowmentinsServiceI;
import com.chrhc.Interceptor.InterceptorException;

import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
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
import org.jeecgframework.web.cgform.common.CgAutoListConstant;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;
import org.jeecgframework.web.cgform.util.QueryParamUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import org.jeecgframework.core.util.ExceptionUtil;



/**   
 * @Title: Controller
 * @Description: 养老保险参保记录
 * @author onlineGenerator
 * @date 2015-08-19 14:11:18
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scEndowmentinsController")
public class ScEndowmentinsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScEndowmentinsController.class);

	@Autowired
	private ScEndowmentinsServiceI scEndowmentinsService;
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
	 * 养老保险参保记录列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scEndowmentins")
	public ModelAndView scEndowmentins(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/endow/scEndowmentinsList");
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
	public void datagrid(ScEndowmentinsEntity scEndowmentins,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScEndowmentinsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scEndowmentins, request.getParameterMap());
		try{
		//自定义追加查询条件
		String query_insureddate_begin = request.getParameter("insureddate_begin");
		String query_insureddate_end = request.getParameter("insureddate_end");
		if(StringUtil.isNotEmpty(query_insureddate_begin)){
			cq.ge("insureddate", new SimpleDateFormat("yyyy-MM-dd").parse(query_insureddate_begin));
		}
		if(StringUtil.isNotEmpty(query_insureddate_end)){
			cq.le("insureddate", new SimpleDateFormat("yyyy-MM-dd").parse(query_insureddate_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scEndowmentinsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除养老保险参保记录
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScEndowmentinsEntity scEndowmentins, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scEndowmentins = systemService.getEntity(ScEndowmentinsEntity.class, scEndowmentins.getId());
		message = "养老保险参保记录删除成功";
		try{
			scEndowmentinsService.deleteLogic(scEndowmentins);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "养老保险参保记录删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除养老保险参保记录
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "养老保险参保记录删除成功";
		try{
			for(String id:ids.split(",")){
				ScEndowmentinsEntity scEndowmentins = systemService.getEntity(ScEndowmentinsEntity.class, 
				id
				);
				scEndowmentinsService.deleteLogic(scEndowmentins);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "养老保险参保记录删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加养老保险参保记录
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScEndowmentinsEntity scEndowmentins, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "养老保险参保记录添加成功";
		try{
			scEndowmentinsService.save(scEndowmentins);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "养老保险参保记录添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setObj(scEndowmentins);
		return j;
	}
	
	/**
	 * 更新养老保险参保记录
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScEndowmentinsEntity scEndowmentins, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "养老保险参保记录更新成功";
		ScEndowmentinsEntity t = scEndowmentinsService.get(ScEndowmentinsEntity.class, scEndowmentins.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scEndowmentins, t);
			scEndowmentinsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
		   if(e instanceof InterceptorException){
				throw new InterceptorException("");
			}else{
				e.printStackTrace();
				message = "养老保险参保记录更新失败";
				throw new BusinessException(e.getMessage());
			}				
		}
		j.setMsg(message);
		j.setObj(scEndowmentins);
		return j;
	}
	

	/**
	 * 养老保险参保记录新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScEndowmentinsEntity scEndowmentins, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scEndowmentins.getId())) {
			scEndowmentins = scEndowmentinsService.getEntity(ScEndowmentinsEntity.class, scEndowmentins.getId());
			req.setAttribute("scEndowmentinsPage", scEndowmentins);
		}
		return new ModelAndView("com/chrhc/project/sc/endow/scEndowmentins-add");
	}
	/**
	 * 养老保险参保记录编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScEndowmentinsEntity scEndowmentins, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scEndowmentins.getId())) {
			scEndowmentins = scEndowmentinsService.getEntity(ScEndowmentinsEntity.class, scEndowmentins.getId());
			req.setAttribute("scEndowmentinsPage", scEndowmentins);
		}
		return new ModelAndView("com/chrhc/project/sc/endow/scEndowmentins-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/endow/scEndowmentinsUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScEndowmentinsEntity scEndowmentins,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScEndowmentinsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scEndowmentins, request.getParameterMap());
		List<ScEndowmentinsEntity> scEndowmentinss = this.scEndowmentinsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"养老保险参保记录");
		modelMap.put(NormalExcelConstants.CLASS,ScEndowmentinsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("养老保险参保记录列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scEndowmentinss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScEndowmentinsEntity scEndowmentins,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "养老保险参保记录");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScEndowmentinsEntity.class);
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
				List<ScEndowmentinsEntity> listScEndowmentinsEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScEndowmentinsEntity.class,params);
				for (ScEndowmentinsEntity scEndowmentins : listScEndowmentinsEntitys) {
					scEndowmentinsService.save(scEndowmentins);
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
