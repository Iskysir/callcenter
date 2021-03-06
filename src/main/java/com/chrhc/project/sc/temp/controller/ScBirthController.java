package com.chrhc.project.sc.temp.controller;
import com.chrhc.project.sc.common.RequestUtil;
import com.chrhc.project.sc.temp.entity.ScBirthEntity;
import com.chrhc.project.sc.temp.service.ScBirthServiceI;
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
 * @Description: 婚育证明
 * @author onlineGenerator
 * @date 2015-11-02 11:03:03
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scBirthController")
public class ScBirthController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScBirthController.class);

	@Autowired
	private ScBirthServiceI scBirthService;
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
	 * 婚育证明列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scBirth")
	public ModelAndView scBirth(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/temp/scBirthList");
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
	public void datagrid(ScBirthEntity scBirth,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScBirthEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scBirth, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scBirthService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除婚育证明
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScBirthEntity scBirth, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scBirth = systemService.getEntity(ScBirthEntity.class, scBirth.getId());
		message = "婚育证明删除成功";
		try{
			scBirthService.deleteLogic(scBirth);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "婚育证明删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除婚育证明
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "婚育证明删除成功";
		try{
			for(String id:ids.split(",")){
				ScBirthEntity scBirth = systemService.getEntity(ScBirthEntity.class, 
				id
				);
				scBirthService.deleteLogic(scBirth);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "婚育证明删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加婚育证明
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScBirthEntity scBirth, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "婚育证明添加成功";
		try{
			scBirthService.save(scBirth);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "婚育证明添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新婚育证明
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScBirthEntity scBirth, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "婚育证明更新成功";
		ScBirthEntity t = scBirthService.get(ScBirthEntity.class, scBirth.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scBirth, t);
			scBirthService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
		   if(e instanceof InterceptorException){
				throw new InterceptorException("");
			}else{
				e.printStackTrace();
				message = "婚育证明更新失败";
				throw new BusinessException(e.getMessage());
			}				
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 婚育证明新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScBirthEntity scBirth, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scBirth.getId())) {
			scBirth = scBirthService.getEntity(ScBirthEntity.class, scBirth.getId());
			req.setAttribute("scBirthPage", scBirth);
		}
		return new ModelAndView("com/chrhc/project/sc/temp/scBirth-add");
	}
	/**
	 * 婚育证明编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScBirthEntity scBirth, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scBirth.getId())) {
			scBirth = scBirthService.getEntity(ScBirthEntity.class, scBirth.getId());
			req.setAttribute("scBirthPage", scBirth);
		}
		return new ModelAndView("com/chrhc/project/sc/temp/scBirth-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/temp/scBirthUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScBirthEntity scBirth,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScBirthEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scBirth, request.getParameterMap());
		List<ScBirthEntity> scBirths = this.scBirthService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"婚育证明");
		modelMap.put(NormalExcelConstants.CLASS,ScBirthEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("婚育证明列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scBirths);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScBirthEntity scBirth,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "婚育证明");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScBirthEntity.class);
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
				List<ScBirthEntity> listScBirthEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScBirthEntity.class,params);
				for (ScBirthEntity scBirth : listScBirthEntitys) {
					scBirthService.save(scBirth);
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
	
	/**
	 * 保存提交之前验证，验证办理该证明人提供的配偶信息与孩子信息是否正确
	 * @param scBirth
	 * @param request
	 * @return
	 */
	@Autowired
	private ScProveServiceUtil scProveServiceUtil;
	@RequestMapping(params = "beforeSubmit")
	@ResponseBody
	public AjaxJson beforeSubmit(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "验证成功";
		
		Map<String,Object> parameters =  new HashMap<String,Object>();
		parameters = RequestUtil.getParametersStartingWith(request, "");
		
		j = scProveServiceUtil.checkQsgxByRkId(parameters);
		
		return j;
	}
}
