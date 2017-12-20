package com.chrhc.project.sc.endowlogout.controller;
import com.chrhc.project.sc.endow.entity.ScEndowmentinsEntity;
import com.chrhc.project.sc.endow.service.ScEndowmentinsServiceI;
import com.chrhc.project.sc.endowlogout.entity.ScEndowlogoutEntity;
import com.chrhc.project.sc.endowlogout.service.ScEndowlogoutServiceI;
import com.chrhc.Interceptor.InterceptorException;

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
 * @Description: 养老保险注销记录
 * @author onlineGenerator
 * @date 2015-08-20 09:32:56
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scEndowlogoutController")
public class ScEndowlogoutController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScEndowlogoutController.class);

	@Autowired
	private ScEndowlogoutServiceI scEndowlogoutService;
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
	 * 养老保险注销记录列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scEndowlogout")
	public ModelAndView scEndowlogout(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/endowlogout/scEndowlogoutList");
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
	public void datagrid(ScEndowlogoutEntity scEndowlogout,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScEndowlogoutEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scEndowlogout, request.getParameterMap());
		try{
		//自定义追加查询条件
		String query_cancelldate_begin = request.getParameter("cancelldate_begin");
		String query_cancelldate_end = request.getParameter("cancelldate_end");
		if(StringUtil.isNotEmpty(query_cancelldate_begin)){
			cq.ge("cancelldate", Integer.parseInt(query_cancelldate_begin));
		}
		if(StringUtil.isNotEmpty(query_cancelldate_end)){
			cq.le("cancelldate", Integer.parseInt(query_cancelldate_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scEndowlogoutService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除养老保险注销记录
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScEndowlogoutEntity scEndowlogout, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scEndowlogout = systemService.getEntity(ScEndowlogoutEntity.class, scEndowlogout.getId());
		message = "养老保险注销记录删除成功";
		try{
			scEndowlogoutService.deleteLogic(scEndowlogout);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "养老保险注销记录删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除养老保险注销记录
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "养老保险注销记录删除成功";
		try{
			for(String id:ids.split(",")){
				ScEndowlogoutEntity scEndowlogout = systemService.getEntity(ScEndowlogoutEntity.class, 
				id
				);
				scEndowlogoutService.deleteLogic(scEndowlogout);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "养老保险注销记录删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加养老保险注销记录
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScEndowlogoutEntity scEndowlogout, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "养老保险注销记录添加成功";
		try{
			if(scEndowlogout.getEndowid() != null && scEndowlogout.getEndowid() != ""){
				ScEndowmentinsEntity t = scEndowmentinsService.get(ScEndowmentinsEntity.class, scEndowlogout.getEndowid());
				
				t.setInsuredstate("cancelled");
				
				scEndowmentinsService.saveOrUpdate(t);
			}
			scEndowlogoutService.save(scEndowlogout);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "养老保险注销记录添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setObj(scEndowlogout);
		return j;
	}
	
	/**
	 * 更新养老保险注销记录
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScEndowlogoutEntity scEndowlogout, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "养老保险注销记录更新成功";
		ScEndowlogoutEntity t = scEndowlogoutService.get(ScEndowlogoutEntity.class, scEndowlogout.getId());
		try {
			if(scEndowlogout.getEndowid() != null && scEndowlogout.getEndowid() != ""){
				ScEndowmentinsEntity endowins = scEndowmentinsService.get(ScEndowmentinsEntity.class, scEndowlogout.getEndowid());
				
				endowins.setInsuredstate("cancelled");
				
				scEndowmentinsService.saveOrUpdate(endowins);
			}
			MyBeanUtils.copyBeanNotNull2Bean(scEndowlogout, t);
			scEndowlogoutService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
		   if(e instanceof InterceptorException){
				throw new InterceptorException("");
			}else{
				e.printStackTrace();
				message = "养老保险注销记录更新失败";
				throw new BusinessException(e.getMessage());
			}				
		}
		j.setMsg(message);
		j.setObj(scEndowlogout);
		return j;
	}
	

	/**
	 * 养老保险注销记录新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScEndowlogoutEntity scEndowlogout, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scEndowlogout.getId())) {
			scEndowlogout = scEndowlogoutService.getEntity(ScEndowlogoutEntity.class, scEndowlogout.getId());
			req.setAttribute("scEndowlogoutPage", scEndowlogout);
		}
		return new ModelAndView("com/chrhc/project/sc/endowlogout/scEndowlogout-add");
	}
	
	/**
	 * 注销养老保险 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "cancel")
	public ModelAndView cancel(HttpServletRequest request) {
		String id = request.getParameter("endowid");
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String pidcard = request.getParameter("pidcard");
		String flag = request.getParameter("flag");
		request.setAttribute("endowid", id);
		request.setAttribute("code", code);
		request.setAttribute("name", name);
		request.setAttribute("pidcard", pidcard);
		request.setAttribute("flag", flag);
		
		ScEndowlogoutEntity scEndowlogout = new ScEndowlogoutEntity();
		scEndowlogout.setEndowid(id);
		scEndowlogout.setCode(code);
		scEndowlogout.setName(name);
		scEndowlogout.setPidcard(pidcard);
		
		request.setAttribute("scEndowlogoutPage", scEndowlogout);
		
		return new ModelAndView("com/chrhc/project/sc/endowlogout/scEndowlogout-add");
	}
	
	/**
	 * 养老保险注销记录编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScEndowlogoutEntity scEndowlogout, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scEndowlogout.getId())) {
			scEndowlogout = scEndowlogoutService.getEntity(ScEndowlogoutEntity.class, scEndowlogout.getId());
			req.setAttribute("scEndowlogoutPage", scEndowlogout);
		}
		return new ModelAndView("com/chrhc/project/sc/endowlogout/scEndowlogout-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/endowlogout/scEndowlogoutUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScEndowlogoutEntity scEndowlogout,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScEndowlogoutEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scEndowlogout, request.getParameterMap());
		List<ScEndowlogoutEntity> scEndowlogouts = this.scEndowlogoutService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"养老保险注销记录");
		modelMap.put(NormalExcelConstants.CLASS,ScEndowlogoutEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("养老保险注销记录列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scEndowlogouts);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScEndowlogoutEntity scEndowlogout,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "养老保险注销记录");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScEndowlogoutEntity.class);
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
				List<ScEndowlogoutEntity> listScEndowlogoutEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScEndowlogoutEntity.class,params);
				for (ScEndowlogoutEntity scEndowlogout : listScEndowlogoutEntitys) {
					scEndowlogoutService.save(scEndowlogout);
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
