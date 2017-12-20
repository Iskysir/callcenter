package com.chrhc.project.hl.visit.controller;
import com.chrhc.project.hl.visit.entity.HlSatisfiedDegreeEntity;
import com.chrhc.project.hl.visit.service.HlSatisfiedDegreeServiceI;
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
 * @Description: 回访满意度
 * @author onlineGenerator
 * @date 2016-07-19 21:06:46
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/hlSatisfiedDegreeController")
public class HlSatisfiedDegreeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HlSatisfiedDegreeController.class);

	@Autowired
	private HlSatisfiedDegreeServiceI hlSatisfiedDegreeService;
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
	 * 回访满意度列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "hlSatisfiedDegree")
	public ModelAndView hlSatisfiedDegree(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/hl/visit/hlSatisfiedDegreeList");
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
	public void datagrid(HlSatisfiedDegreeEntity hlSatisfiedDegree,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(HlSatisfiedDegreeEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, hlSatisfiedDegree, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.hlSatisfiedDegreeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 *
	 * @param hlSatisfiedDegree
	 * @param request
     * @return
     */
	@RequestMapping(params = "getDegree")
	@ResponseBody
	public AjaxJson getDegree(HlSatisfiedDegreeEntity hlSatisfiedDegree,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
	//	HlSatisfiedDegreeEntity t = hlSatisfiedDegreeService.get(HlSatisfiedDegreeEntity.class, hlSatisfiedDegree.getId());
		//List<HlSatisfiedDegreeEntity> list =hlSatisfiedDegreeService.getList(HlSatisfiedDegreeEntity.class);
		List<HlSatisfiedDegreeEntity> list = hlSatisfiedDegreeService.findByProperty(HlSatisfiedDegreeEntity.class,"busId",hlSatisfiedDegree.getBusId());
		j.setObj(list);
		return  j;
	}
	/**
	 * 删除回访满意度
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(HlSatisfiedDegreeEntity hlSatisfiedDegree, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		hlSatisfiedDegree = systemService.getEntity(HlSatisfiedDegreeEntity.class, hlSatisfiedDegree.getId());
		message = "回访满意度删除成功";
		try{
			hlSatisfiedDegreeService.deleteLogic(hlSatisfiedDegree);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "回访满意度删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除回访满意度
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "回访满意度删除成功";
		try{
			for(String id:ids.split(",")){
				HlSatisfiedDegreeEntity hlSatisfiedDegree = systemService.getEntity(HlSatisfiedDegreeEntity.class, 
				id
				);
				hlSatisfiedDegreeService.deleteLogic(hlSatisfiedDegree);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "回访满意度删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加回访满意度
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(HlSatisfiedDegreeEntity hlSatisfiedDegree, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "回访满意度添加成功";
		try{
			hlSatisfiedDegreeService.save(hlSatisfiedDegree);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "回访满意度添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新回访满意度
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(HlSatisfiedDegreeEntity hlSatisfiedDegree, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "回访满意度更新成功";
		HlSatisfiedDegreeEntity t = hlSatisfiedDegreeService.get(HlSatisfiedDegreeEntity.class, hlSatisfiedDegree.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(hlSatisfiedDegree, t);
			hlSatisfiedDegreeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
		   if(e instanceof InterceptorException){
				throw new InterceptorException("");
			}else{
				e.printStackTrace();
				message = "回访满意度更新失败";
				throw new BusinessException(e.getMessage());
			}				
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 回访满意度新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(HlSatisfiedDegreeEntity hlSatisfiedDegree, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hlSatisfiedDegree.getId())) {
			hlSatisfiedDegree = hlSatisfiedDegreeService.getEntity(HlSatisfiedDegreeEntity.class, hlSatisfiedDegree.getId());
			req.setAttribute("hlSatisfiedDegreePage", hlSatisfiedDegree);
		}
		return new ModelAndView("com/chrhc/project/hl/visit/hlSatisfiedDegree-add");
	}
	/**
	 * 回访满意度编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(HlSatisfiedDegreeEntity hlSatisfiedDegree, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hlSatisfiedDegree.getId())) {
			hlSatisfiedDegree = hlSatisfiedDegreeService.getEntity(HlSatisfiedDegreeEntity.class, hlSatisfiedDegree.getId());
			req.setAttribute("hlSatisfiedDegreePage", hlSatisfiedDegree);
		}
		return new ModelAndView("com/chrhc/project/hl/visit/hlSatisfiedDegree-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/hl/visit/hlSatisfiedDegreeUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(HlSatisfiedDegreeEntity hlSatisfiedDegree,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(HlSatisfiedDegreeEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, hlSatisfiedDegree, request.getParameterMap());
		List<HlSatisfiedDegreeEntity> hlSatisfiedDegrees = this.hlSatisfiedDegreeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"回访满意度");
		modelMap.put(NormalExcelConstants.CLASS,HlSatisfiedDegreeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("回访满意度列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,hlSatisfiedDegrees);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(HlSatisfiedDegreeEntity hlSatisfiedDegree,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "回访满意度");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,HlSatisfiedDegreeEntity.class);
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
				List<HlSatisfiedDegreeEntity> listHlSatisfiedDegreeEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),HlSatisfiedDegreeEntity.class,params);
				for (HlSatisfiedDegreeEntity hlSatisfiedDegree : listHlSatisfiedDegreeEntitys) {
					hlSatisfiedDegreeService.save(hlSatisfiedDegree);
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
