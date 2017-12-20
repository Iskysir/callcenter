package com.chrhc.project.sc.jsjg.controller;
import com.chrhc.project.sc.jsjg.entity.ScJsjgEntity;
import com.chrhc.project.sc.wsjg.entity.ScWsjgEntity;
import com.chrhc.Interceptor.InterceptorException;

import java.util.ArrayList;
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
 * @Description: 计生机构
 * @author onlineGenerator
 * @date 2015-10-21 14:43:19
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scJsjgController")
public class ScJsjgController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScJsjgController.class);

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
	 * 计生机构列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scJsjg")
	public ModelAndView scJsjg(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/jsjg/scJsjgList");
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
	public void datagrid(ScJsjgEntity scJsjg,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScJsjgEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scJsjg, request.getParameterMap());
		try{
		//自定义追加查询条件
		String query_clsj_begin = request.getParameter("clsj_begin");
		String query_clsj_end = request.getParameter("clsj_end");
		if(StringUtil.isNotEmpty(query_clsj_begin)){
			cq.ge("clsj", new SimpleDateFormat("yyyy-MM-dd").parse(query_clsj_begin));
		}
		if(StringUtil.isNotEmpty(query_clsj_end)){
			cq.le("clsj", new SimpleDateFormat("yyyy-MM-dd").parse(query_clsj_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除计生机构
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScJsjgEntity scJsjg, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scJsjg = systemService.getEntity(ScJsjgEntity.class, scJsjg.getId());
		message = "计生机构删除成功";
		try{
			systemService.deleteLogic(scJsjg);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "计生机构删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除计生机构
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "计生机构删除成功";
		try{
			List<String> jsgzjl = this.doCheckBatchJsgzjl(ids);
			if(null == jsgzjl || jsgzjl.size() == 0){
				for(String id:ids.split(",")){
					ScJsjgEntity scJsjg = systemService.getEntity(ScJsjgEntity.class, 
					id
					);
					systemService.deleteLogic(scJsjg);
					systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
				}
			}else{
				if(null != jsgzjl && jsgzjl.size() > 0){
					message = "计生工作记录有计生机构，不允许删除";
					j.setSuccess(false);
			}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "计生机构删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	 /**
	  * 批量删除时，判断卫生工作记录是否有卫生机构
	  * @param ids
	  * @return
	  */
	 private List<String> doCheckBatchJsgzjl(String ids){
		 List<String> list = new ArrayList<String>();
		 
		 String hql0 = "from ScJsgzjlEntity where 1 = 1 AND delflag='0' AND partId = ? ";
		 for(String id:ids.split(",")){
			//判断该志愿队是否有志愿者成员
			List<ScJsjgEntity> scZhyfwzhList = systemService.findHql(hql0,id);;			
			if(null != scZhyfwzhList && scZhyfwzhList.size() > 0){
				list.add(id);
			}
		}
		 return list;
	 }
	

	/**
	 * 添加计生机构
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScJsjgEntity scJsjg, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "计生机构添加成功";
		try{
			systemService.save(scJsjg);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "计生机构添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新计生机构
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScJsjgEntity scJsjg, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "计生机构更新成功";
		ScJsjgEntity t = systemService.get(ScJsjgEntity.class, scJsjg.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scJsjg, t);
			systemService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
		   if(e instanceof InterceptorException){
				throw new InterceptorException("");
			}else{
				e.printStackTrace();
				message = "计生机构更新失败";
				throw new BusinessException(e.getMessage());
			}				
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 计生机构新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScJsjgEntity scJsjg, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scJsjg.getId())) {
			scJsjg = systemService.getEntity(ScJsjgEntity.class, scJsjg.getId());
			req.setAttribute("scJsjgPage", scJsjg);
		}
		return new ModelAndView("com/chrhc/project/sc/jsjg/scJsjg-add");
	}
	/**
	 * 计生机构编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScJsjgEntity scJsjg, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scJsjg.getId())) {
			scJsjg = systemService.getEntity(ScJsjgEntity.class, scJsjg.getId());
			req.setAttribute("scJsjgPage", scJsjg);
		}
		return new ModelAndView("com/chrhc/project/sc/jsjg/scJsjg-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/jsjg/scJsjgUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScJsjgEntity scJsjg,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScJsjgEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scJsjg, request.getParameterMap());
		List<ScJsjgEntity> scJsjgs = this.systemService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"计生机构");
		modelMap.put(NormalExcelConstants.CLASS,ScJsjgEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("计生机构列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scJsjgs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScJsjgEntity scJsjg,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "计生机构");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScJsjgEntity.class);
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
				List<ScJsjgEntity> listScJsjgEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScJsjgEntity.class,params);
				for (ScJsjgEntity scJsjg : listScJsjgEntitys) {
					systemService.save(scJsjg);
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
