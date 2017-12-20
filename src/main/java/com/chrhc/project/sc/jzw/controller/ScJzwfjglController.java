package com.chrhc.project.sc.jzw.controller;
import com.chrhc.project.sc.jzw.entity.ScJzwfjglEntity;
import com.chrhc.project.sc.jzw.service.ScJzwfjglServiceI;
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
 * @Description: 建筑物房间管理
 * @author onlineGenerator
 * @date 2015-04-29 20:50:38
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scJzwfjglController")
public class ScJzwfjglController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScJzwfjglController.class);

	@Autowired
	private ScJzwfjglServiceI scJzwfjglService;
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
	 * 建筑物房间管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scJzwfjgl")
	public ModelAndView scJzwfjgl(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/jzw/scJzwfjglList");
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
	public void datagrid(ScJzwfjglEntity scJzwfjgl,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScJzwfjglEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scJzwfjgl, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scJzwfjglService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除建筑物房间管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScJzwfjglEntity scJzwfjgl, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scJzwfjgl = systemService.getEntity(ScJzwfjglEntity.class, scJzwfjgl.getId());
		message = "建筑物房间管理删除成功";
		try{
			scJzwfjglService.delete(scJzwfjgl);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "建筑物房间管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除建筑物房间管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "建筑物房间管理删除成功";
		try{
			for(String id:ids.split(",")){
				ScJzwfjglEntity scJzwfjgl = systemService.getEntity(ScJzwfjglEntity.class, 
				id
				);
				scJzwfjglService.delete(scJzwfjgl);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "建筑物房间管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加建筑物房间管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScJzwfjglEntity scJzwfjgl, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "建筑物房间管理添加成功";
		try{
			scJzwfjglService.save(scJzwfjgl);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "建筑物房间管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新建筑物房间管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScJzwfjglEntity scJzwfjgl, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "建筑物房间管理更新成功";
		ScJzwfjglEntity t = scJzwfjglService.get(ScJzwfjglEntity.class, scJzwfjgl.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scJzwfjgl, t);
			scJzwfjglService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "建筑物房间管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 建筑物房间管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScJzwfjglEntity scJzwfjgl, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scJzwfjgl.getId())) {
			scJzwfjgl = scJzwfjglService.getEntity(ScJzwfjglEntity.class, scJzwfjgl.getId());
			req.setAttribute("scJzwfjglPage", scJzwfjgl);
		}
		return new ModelAndView("com/chrhc/project/sc/jzw/scJzwfjgl-add");
	}
	/**
	 * 建筑物房间管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScJzwfjglEntity scJzwfjgl, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scJzwfjgl.getId())) {
			scJzwfjgl = scJzwfjglService.getEntity(ScJzwfjglEntity.class, scJzwfjgl.getId());
			req.setAttribute("scJzwfjglPage", scJzwfjgl);
		}
		return new ModelAndView("com/chrhc/project/sc/jzw/scJzwfjgl-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/jzw/scJzwfjglUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScJzwfjglEntity scJzwfjgl,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScJzwfjglEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scJzwfjgl, request.getParameterMap());
		List<ScJzwfjglEntity> scJzwfjgls = this.scJzwfjglService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"建筑物房间管理");
		modelMap.put(NormalExcelConstants.CLASS,ScJzwfjglEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("建筑物房间管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scJzwfjgls);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScJzwfjglEntity scJzwfjgl,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "建筑物房间管理");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScJzwfjglEntity.class);
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
				List<ScJzwfjglEntity> listScJzwfjglEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScJzwfjglEntity.class,params);
				for (ScJzwfjglEntity scJzwfjgl : listScJzwfjglEntitys) {
					scJzwfjglService.save(scJzwfjgl);
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
