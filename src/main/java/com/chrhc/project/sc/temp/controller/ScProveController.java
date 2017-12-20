package com.chrhc.project.sc.temp.controller;
import com.chrhc.project.sc.common.RequestUtil;
import com.chrhc.project.sc.temp.entity.ScProveEntity;
import com.chrhc.project.sc.temp.service.ScProveServiceI;
import com.chrhc.project.sc.temp.service.ScProveServiceUtil;
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
 * @Description: 独生子女证补证证明
 * @author onlineGenerator
 * @date 2015-11-02 10:45:37
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scProveController")
public class ScProveController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScProveController.class);

	@Autowired
	private ScProveServiceI scProveService;
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
	 * 独生子女证补证证明列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scProve")
	public ModelAndView scProve(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/temp/scProveList");
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
	public void datagrid(ScProveEntity scProve,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScProveEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scProve, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scProveService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除独生子女证补证证明
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScProveEntity scProve, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scProve = systemService.getEntity(ScProveEntity.class, scProve.getId());
		message = "独生子女证补证证明删除成功";
		try{
			scProveService.deleteLogic(scProve);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "独生子女证补证证明删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除独生子女证补证证明
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "独生子女证补证证明删除成功";
		try{
			for(String id:ids.split(",")){
				ScProveEntity scProve = systemService.getEntity(ScProveEntity.class, 
				id
				);
				scProveService.deleteLogic(scProve);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "独生子女证补证证明删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加独生子女证补证证明
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScProveEntity scProve, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "独生子女证补证证明添加成功";
		try{
			scProveService.save(scProve);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "独生子女证补证证明添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新独生子女证补证证明
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScProveEntity scProve, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "独生子女证补证证明更新成功";
		ScProveEntity t = scProveService.get(ScProveEntity.class, scProve.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scProve, t);
			scProveService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
		   if(e instanceof InterceptorException){
				throw new InterceptorException("");
			}else{
				e.printStackTrace();
				message = "独生子女证补证证明更新失败";
				throw new BusinessException(e.getMessage());
			}				
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 独生子女证补证证明新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScProveEntity scProve, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scProve.getId())) {
			scProve = scProveService.getEntity(ScProveEntity.class, scProve.getId());
			req.setAttribute("scProvePage", scProve);
		}
		return new ModelAndView("com/chrhc/project/sc/temp/scProve-add");
	}
	/**
	 * 独生子女证补证证明编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScProveEntity scProve, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scProve.getId())) {
			scProve = scProveService.getEntity(ScProveEntity.class, scProve.getId());
			req.setAttribute("scProvePage", scProve);
		}
		return new ModelAndView("com/chrhc/project/sc/temp/scProve-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/temp/scProveUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScProveEntity scProve,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScProveEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scProve, request.getParameterMap());
		List<ScProveEntity> scProves = this.scProveService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"独生子女证补证证明");
		modelMap.put(NormalExcelConstants.CLASS,ScProveEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("独生子女证补证证明列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scProves);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScProveEntity scProve,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "独生子女证补证证明");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScProveEntity.class);
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
				List<ScProveEntity> listScProveEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScProveEntity.class,params);
				for (ScProveEntity scProve : listScProveEntitys) {
					scProveService.save(scProve);
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
	
	@Autowired
	private ScProveServiceUtil scProveServiceUtil;
	/**
	 * 提交之前，验证办理该证明人提供的配偶信息与孩子信息是否正确
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "beforeSubmit")
	@ResponseBody
	public AjaxJson beforeSubmit(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		
		Map<String,Object> parameters =  new HashMap<String,Object>();
		parameters = RequestUtil.getParametersStartingWith(request, "");
		
		if(parameters.containsKey("marry_date")){
			parameters.put("marryday", parameters.get("marry_date"));
		}
		if(parameters.containsKey("child_birthday")){
			parameters.put("birth_date", parameters.get("child_birthday"));
		}
		
		j = scProveServiceUtil.checkQsgxByRkId(parameters);
		return j;
	}
}
