package com.chrhc.project.sc.jcql.controller;
import com.chrhc.project.sc.jcql.entity.ScJcqlEntity;
import com.chrhc.project.sc.jsjg.entity.ScJsjgEntity;
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
 * @Description: 基层侨联
 * @author onlineGenerator
 * @date 2015-10-23 11:29:41
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scJcqlController")
public class ScJcqlController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScJcqlController.class);

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
	 * 基层侨联列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scJcql")
	public ModelAndView scJcql(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/jcql/scJcqlList");
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
	public void datagrid(ScJcqlEntity scJcql,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScJcqlEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scJcql, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除基层侨联
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScJcqlEntity scJcql, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scJcql = systemService.getEntity(ScJcqlEntity.class, scJcql.getId());
		message = "基层侨联删除成功";
		try{
			systemService.deleteLogic(scJcql);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "基层侨联删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除基层侨联
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "基层侨联删除成功";
		try{
			List<String> jcqlgzjl = this.doCheckBatchJcqlgzjl(ids);
			if(null == jcqlgzjl || jcqlgzjl.size() == 0){
				for(String id:ids.split(",")){
					ScJcqlEntity scJcql = systemService.getEntity(ScJcqlEntity.class, 
					id
					);
					systemService.deleteLogic(scJcql);
					systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
				}
			}else{
				if(null != jcqlgzjl && jcqlgzjl.size() > 0){
					message = "基层侨联工作记录有基层侨联，不允许删除";
					j.setSuccess(false);
			}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "基层侨联删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	 /**
	  * 批量删除时，判断基层侨联工作记录是否有基层侨联
	  * @param ids
	  * @return
	  */
	 private List<String> doCheckBatchJcqlgzjl(String ids){
		 List<String> list = new ArrayList<String>();
		 
		 String hql0 = "from ScJcqlhdjlEntity where 1 = 1 AND delflag='0' AND partId = ? ";
		 for(String id:ids.split(",")){
			//判断该志愿队是否有志愿者成员
			List<ScJcqlEntity> scZhyfwzhList = systemService.findHql(hql0,id);;			
			if(null != scZhyfwzhList && scZhyfwzhList.size() > 0){
				list.add(id);
			}
		}
		 return list;
	 }

	/**
	 * 添加基层侨联
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScJcqlEntity scJcql, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "基层侨联添加成功";
		try{
			systemService.save(scJcql);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "基层侨联添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新基层侨联
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScJcqlEntity scJcql, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "基层侨联更新成功";
		ScJcqlEntity t = systemService.get(ScJcqlEntity.class, scJcql.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scJcql, t);
			systemService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
		   if(e instanceof InterceptorException){
				throw new InterceptorException("");
			}else{
				e.printStackTrace();
				message = "基层侨联更新失败";
				throw new BusinessException(e.getMessage());
			}				
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 基层侨联新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScJcqlEntity scJcql, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scJcql.getId())) {
			scJcql = systemService.getEntity(ScJcqlEntity.class, scJcql.getId());
			req.setAttribute("scJcqlPage", scJcql);
		}
		return new ModelAndView("com/chrhc/project/sc/jcql/scJcql-add");
	}
	/**
	 * 基层侨联编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScJcqlEntity scJcql, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scJcql.getId())) {
			scJcql = systemService.getEntity(ScJcqlEntity.class, scJcql.getId());
			req.setAttribute("scJcqlPage", scJcql);
		}
		return new ModelAndView("com/chrhc/project/sc/jcql/scJcql-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/jcql/scJcqlUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScJcqlEntity scJcql,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScJcqlEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scJcql, request.getParameterMap());
		List<ScJcqlEntity> scJcqls = this.systemService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"基层侨联");
		modelMap.put(NormalExcelConstants.CLASS,ScJcqlEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("基层侨联列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scJcqls);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScJcqlEntity scJcql,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "基层侨联");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScJcqlEntity.class);
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
				List<ScJcqlEntity> listScJcqlEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScJcqlEntity.class,params);
				for (ScJcqlEntity scJcql : listScJcqlEntitys) {
					systemService.save(scJcql);
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
