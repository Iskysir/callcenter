package com.chrhc.project.sc.temp.controller;
import com.chrhc.project.sc.temp.entity.ScStayEntity;
import com.chrhc.project.sc.temp.service.ScStayServiceI;
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
 * @Description: 居住证明
 * @author onlineGenerator
 * @date 2015-10-28 13:32:00
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scStayController")
public class ScStayController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScStayController.class);

	@Autowired
	private ScStayServiceI scStayService;
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
	 * 居住证明列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scStay")
	public ModelAndView scStay(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/temp/scStayList");
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
	public void datagrid(ScStayEntity scStay,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScStayEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scStay, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scStayService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除居住证明
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScStayEntity scStay, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scStay = systemService.getEntity(ScStayEntity.class, scStay.getId());
		message = "居住证明删除成功";
		try{
			scStayService.deleteLogic(scStay);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "居住证明删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除居住证明
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "居住证明删除成功";
		try{
			for(String id:ids.split(",")){
				ScStayEntity scStay = systemService.getEntity(ScStayEntity.class, 
				id
				);
				scStayService.deleteLogic(scStay);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "居住证明删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加居住证明
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScStayEntity scStay, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "居住证明添加成功";
		try{
			scStayService.save(scStay);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "居住证明添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新居住证明
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScStayEntity scStay, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "居住证明更新成功";
		ScStayEntity t = scStayService.get(ScStayEntity.class, scStay.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scStay, t);
			scStayService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
		   if(e instanceof InterceptorException){
				throw new InterceptorException("");
			}else{
				e.printStackTrace();
				message = "居住证明更新失败";
				throw new BusinessException(e.getMessage());
			}				
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 居住证明新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScStayEntity scStay, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scStay.getId())) {
			scStay = scStayService.getEntity(ScStayEntity.class, scStay.getId());
			req.setAttribute("scStayPage", scStay);
		}
		return new ModelAndView("com/chrhc/project/sc/temp/scStay-add");
	}
	/**
	 * 居住证明编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScStayEntity scStay, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scStay.getId())) {
			scStay = scStayService.getEntity(ScStayEntity.class, scStay.getId());
			req.setAttribute("scStayPage", scStay);
		}
		return new ModelAndView("com/chrhc/project/sc/temp/scStay-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/temp/scStayUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScStayEntity scStay,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScStayEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scStay, request.getParameterMap());
		List<ScStayEntity> scStays = this.scStayService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"居住证明");
		modelMap.put(NormalExcelConstants.CLASS,ScStayEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("居住证明列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scStays);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScStayEntity scStay,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "居住证明");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScStayEntity.class);
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
				List<ScStayEntity> listScStayEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScStayEntity.class,params);
				for (ScStayEntity scStay : listScStayEntitys) {
					scStayService.save(scStay);
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
