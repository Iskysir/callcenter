package com.chrhc.project.sc.zhyzh.controller;
import com.chrhc.project.sc.zhyzh.entity.ScZhyzhfwjlEntity;
import com.chrhc.project.sc.zhyzh.service.ScZhyzhfwjlServiceI;
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
 * @Description: 志愿服务记录
 * @author onlineGenerator
 * @date 2015-05-08 13:28:33
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scZhyzhfwjlController")
public class ScZhyzhfwjlController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScZhyzhfwjlController.class);

	@Autowired
	private ScZhyzhfwjlServiceI scZhyzhfwjlService;
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
	 * 志愿服务记录列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scZhyzhfwjl")
	public ModelAndView scZhyzhfwjl(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/zhyzh/scZhyzhfwjlList");
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
	public void datagrid(ScZhyzhfwjlEntity scZhyzhfwjl,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScZhyzhfwjlEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scZhyzhfwjl, request.getParameterMap());
		try{
		//自定义追加查询条件
		String query_servDatetime_begin = request.getParameter("servDatetime_begin");
		String query_servDatetime_end = request.getParameter("servDatetime_end");
		if(StringUtil.isNotEmpty(query_servDatetime_begin)){
			cq.ge("servDatetime", new SimpleDateFormat("yyyy-MM-dd").parse(query_servDatetime_begin));
		}
		if(StringUtil.isNotEmpty(query_servDatetime_end)){
			cq.le("servDatetime", new SimpleDateFormat("yyyy-MM-dd").parse(query_servDatetime_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scZhyzhfwjlService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除志愿服务记录
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScZhyzhfwjlEntity scZhyzhfwjl, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scZhyzhfwjl = systemService.getEntity(ScZhyzhfwjlEntity.class, scZhyzhfwjl.getId());
		message = "删除成功";
		try{
			scZhyzhfwjlService.delete(scZhyzhfwjl);
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
	 * 批量删除志愿服务记录
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "批量删除成功";
		try{
			for(String id:ids.split(",")){
				ScZhyzhfwjlEntity scZhyzhfwjl = systemService.getEntity(ScZhyzhfwjlEntity.class, 
				id
				);
				scZhyzhfwjlService.delete(scZhyzhfwjl);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
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
	 * 添加志愿服务记录
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScZhyzhfwjlEntity scZhyzhfwjl, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "志愿服务记录添加成功";
		try{
			scZhyzhfwjlService.save(scZhyzhfwjl);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "志愿服务记录添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新志愿服务记录
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScZhyzhfwjlEntity scZhyzhfwjl, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "志愿服务记录更新成功";
		ScZhyzhfwjlEntity t = scZhyzhfwjlService.get(ScZhyzhfwjlEntity.class, scZhyzhfwjl.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scZhyzhfwjl, t);
			scZhyzhfwjlService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "志愿服务记录更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 志愿服务记录新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScZhyzhfwjlEntity scZhyzhfwjl, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scZhyzhfwjl.getId())) {
			scZhyzhfwjl = scZhyzhfwjlService.getEntity(ScZhyzhfwjlEntity.class, scZhyzhfwjl.getId());
			req.setAttribute("scZhyzhfwjlPage", scZhyzhfwjl);
		}
		return new ModelAndView("com/chrhc/project/sc/zhyzh/scZhyzhfwjl-add");
	}
	/**
	 * 志愿服务记录编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScZhyzhfwjlEntity scZhyzhfwjl, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scZhyzhfwjl.getId())) {
			scZhyzhfwjl = scZhyzhfwjlService.getEntity(ScZhyzhfwjlEntity.class, scZhyzhfwjl.getId());
			req.setAttribute("scZhyzhfwjlPage", scZhyzhfwjl);
		}
		return new ModelAndView("com/chrhc/project/sc/zhyzh/scZhyzhfwjl-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/zhyzh/scZhyzhfwjlUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScZhyzhfwjlEntity scZhyzhfwjl,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScZhyzhfwjlEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scZhyzhfwjl, request.getParameterMap());
		List<ScZhyzhfwjlEntity> scZhyzhfwjls = this.scZhyzhfwjlService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"志愿服务记录");
		modelMap.put(NormalExcelConstants.CLASS,ScZhyzhfwjlEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("志愿服务记录列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scZhyzhfwjls);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScZhyzhfwjlEntity scZhyzhfwjl,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "志愿服务记录");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScZhyzhfwjlEntity.class);
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
				List<ScZhyzhfwjlEntity> listScZhyzhfwjlEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScZhyzhfwjlEntity.class,params);
				for (ScZhyzhfwjlEntity scZhyzhfwjl : listScZhyzhfwjlEntitys) {
					scZhyzhfwjlService.save(scZhyzhfwjl);
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
