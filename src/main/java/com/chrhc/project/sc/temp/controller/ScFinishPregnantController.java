package com.chrhc.project.sc.temp.controller;
import com.chrhc.project.sc.common.RequestUtil;
import com.chrhc.project.sc.temp.entity.ScFinishPregnantEntity;
import com.chrhc.project.sc.temp.service.ScFinishPregnantServiceI;
import com.chrhc.project.sc.temp.service.ScProveServiceUtil;
import com.chrhc.Interceptor.InterceptorException;

import java.util.HashMap;
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
 * @Description: 政策外怀孕人工终止妊娠证明
 * @author onlineGenerator
 * @date 2015-10-29 11:36:05
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scFinishPregnantController")
public class ScFinishPregnantController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScFinishPregnantController.class);

	@Autowired
	private ScFinishPregnantServiceI scFinishPregnantService;
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
	 * 政策外怀孕人工终止妊娠证明列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scFinishPregnant")
	public ModelAndView scFinishPregnant(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/temp/scFinishPregnantList");
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
	public void datagrid(ScFinishPregnantEntity scFinishPregnant,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScFinishPregnantEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scFinishPregnant, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scFinishPregnantService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除政策外怀孕人工终止妊娠证明
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScFinishPregnantEntity scFinishPregnant, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scFinishPregnant = systemService.getEntity(ScFinishPregnantEntity.class, scFinishPregnant.getId());
		message = "政策外怀孕人工终止妊娠证明删除成功";
		try{
			scFinishPregnantService.deleteLogic(scFinishPregnant);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "政策外怀孕人工终止妊娠证明删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除政策外怀孕人工终止妊娠证明
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "政策外怀孕人工终止妊娠证明删除成功";
		try{
			for(String id:ids.split(",")){
				ScFinishPregnantEntity scFinishPregnant = systemService.getEntity(ScFinishPregnantEntity.class, 
				id
				);
				scFinishPregnantService.deleteLogic(scFinishPregnant);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "政策外怀孕人工终止妊娠证明删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加政策外怀孕人工终止妊娠证明
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScFinishPregnantEntity scFinishPregnant, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "政策外怀孕人工终止妊娠证明添加成功";
		try{
			scFinishPregnantService.save(scFinishPregnant);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "政策外怀孕人工终止妊娠证明添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新政策外怀孕人工终止妊娠证明
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScFinishPregnantEntity scFinishPregnant, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "政策外怀孕人工终止妊娠证明更新成功";
		ScFinishPregnantEntity t = scFinishPregnantService.get(ScFinishPregnantEntity.class, scFinishPregnant.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scFinishPregnant, t);
			scFinishPregnantService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
		   if(e instanceof InterceptorException){
				throw new InterceptorException("");
			}else{
				e.printStackTrace();
				message = "政策外怀孕人工终止妊娠证明更新失败";
				throw new BusinessException(e.getMessage());
			}				
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 政策外怀孕人工终止妊娠证明新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScFinishPregnantEntity scFinishPregnant, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scFinishPregnant.getId())) {
			scFinishPregnant = scFinishPregnantService.getEntity(ScFinishPregnantEntity.class, scFinishPregnant.getId());
			req.setAttribute("scFinishPregnantPage", scFinishPregnant);
		}
		return new ModelAndView("com/chrhc/project/sc/temp/scFinishPregnant-add");
	}
	/**
	 * 政策外怀孕人工终止妊娠证明编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScFinishPregnantEntity scFinishPregnant, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scFinishPregnant.getId())) {
			scFinishPregnant = scFinishPregnantService.getEntity(ScFinishPregnantEntity.class, scFinishPregnant.getId());
			req.setAttribute("scFinishPregnantPage", scFinishPregnant);
		}
		return new ModelAndView("com/chrhc/project/sc/temp/scFinishPregnant-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/temp/scFinishPregnantUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScFinishPregnantEntity scFinishPregnant,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScFinishPregnantEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scFinishPregnant, request.getParameterMap());
		List<ScFinishPregnantEntity> scFinishPregnants = this.scFinishPregnantService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"政策外怀孕人工终止妊娠证明");
		modelMap.put(NormalExcelConstants.CLASS,ScFinishPregnantEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("政策外怀孕人工终止妊娠证明列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scFinishPregnants);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScFinishPregnantEntity scFinishPregnant,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "政策外怀孕人工终止妊娠证明");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScFinishPregnantEntity.class);
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
				List<ScFinishPregnantEntity> listScFinishPregnantEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScFinishPregnantEntity.class,params);
				for (ScFinishPregnantEntity scFinishPregnant : listScFinishPregnantEntitys) {
					scFinishPregnantService.save(scFinishPregnant);
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
		
		Map<String,Object> parameters =  new HashMap<String,Object>();
		parameters = RequestUtil.getParametersStartingWith(request, "");
		
		String rk_id = parameters.get("rk_id")+"";
		String m_rk_id = parameters.get("m_rk_id")+"";
		
		boolean oo = scProveServiceUtil.checkWifeAndHusbandByRkId(rk_id,m_rk_id);
		
		if(!oo){
			j.setSuccess(false);
			j.setMsg("配偶信息不符合");
		}
		return j;
	}
}
