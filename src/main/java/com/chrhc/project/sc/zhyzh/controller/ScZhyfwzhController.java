package com.chrhc.project.sc.zhyzh.controller;
import com.chrhc.project.sc.zhyzh.entity.ScZhyfwzhEntity;
import com.chrhc.project.sc.zhyzh.entity.ScZhyzhfwdEntity;
import com.chrhc.project.sc.zhyzh.entity.ScZhyzhfwjlEntity;
import com.chrhc.project.sc.zhyzh.service.ScZhyfwzhServiceI;

import java.util.ArrayList;
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
 * @Description: 志愿服务者
 * @author onlineGenerator
 * @date 2015-05-08 13:28:56
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scZhyfwzhController")
public class ScZhyfwzhController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScZhyfwzhController.class);

	@Autowired
	private ScZhyfwzhServiceI scZhyfwzhService;
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
	 * 志愿服务者列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scZhyfwzh")
	public ModelAndView scZhyfwzh(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/zhyzh/scZhyfwzhList");
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
	public void datagrid(ScZhyfwzhEntity scZhyfwzh,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScZhyfwzhEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scZhyfwzh, request.getParameterMap());
		try{
		//自定义追加查询条件
		String query_code_begin = request.getParameter("code_begin");
		String query_code_end = request.getParameter("code_end");
		if(StringUtil.isNotEmpty(query_code_begin)){
			cq.ge("code", Integer.parseInt(query_code_begin));
		}
		if(StringUtil.isNotEmpty(query_code_end)){
			cq.le("code", Integer.parseInt(query_code_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scZhyfwzhService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除志愿服务者
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScZhyfwzhEntity scZhyfwzh, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scZhyfwzh = systemService.getEntity(ScZhyfwzhEntity.class, scZhyfwzh.getId());
		message = "删除成功";
		try{
			//scZhyfwzhService.delete(scZhyfwzh);
			scZhyfwzhService.deleteLogic(scZhyfwzh);
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
	 * 判断该志愿队是否有志愿服务记录
	 * @param scZhyzhfwd
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "doCheckZhyfwjl")
	@ResponseBody
	public AjaxJson doCheckZhyfwjl(ScZhyzhfwdEntity scZhyzhfwd, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			//判断该志愿队是否有志愿服务记录
			String hql0 = "from ScZhyzhfwjlEntity where 1 = 1 AND delflag='0' AND memId = ? ";
			List<ScZhyzhfwjlEntity> ScZhyzhfwjlList = systemService.findHql(hql0, scZhyzhfwd.getId());
			if(null != ScZhyzhfwjlList && ScZhyzhfwjlList.size() > 0){
				message = "该志愿队有志愿服务记录，不允许删除";
				j.setSuccess(false);
				j.setMsg(message);
				return j;
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "判断该志愿队是否有志愿服务记录失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 批量删除志愿服务者
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "批量删除成功";
		try{
			List<String> list = this.doCheckBatchZhyfwjl(ids);
			if(null != list && list.size() > 0){
				message = "志愿服务者有志愿服务记录，不允许删除";
				j.setSuccess(false);
			}else{
				for(String id:ids.split(",")){
					ScZhyfwzhEntity scZhyfwzh = systemService.getEntity(ScZhyfwzhEntity.class, 
					id
					);
					//scZhyfwzhService.delete(scZhyfwzh);
					scZhyfwzhService.deleteLogic(scZhyfwzh);
					systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
				}
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
	 * 批量删除时，判断志愿服务者是否有服务记录
	 * @param ids
	 * @return
	 */
	private List<String> doCheckBatchZhyfwjl(String ids) {
		List<String> list = new ArrayList<String>();
		String hql0 = "from ScZhyzhfwjlEntity where 1 = 1 AND delflag='0' AND memId = ? ";
		for (String id : ids.split(",")) {
			List<ScZhyzhfwjlEntity> ScZhyzhfwjlList = systemService.findHql(hql0, id);
			if(null != ScZhyzhfwjlList && ScZhyzhfwjlList.size() > 0){
				list.add(id);
			}
		}
		return list;
	}

	/**
	 * 添加志愿服务者
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScZhyfwzhEntity scZhyfwzh, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "志愿服务者添加成功";
		try{
			scZhyfwzhService.save(scZhyfwzh);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "志愿服务者添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新志愿服务者
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScZhyfwzhEntity scZhyfwzh, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "志愿服务者更新成功";
		ScZhyfwzhEntity t = scZhyfwzhService.get(ScZhyfwzhEntity.class, scZhyfwzh.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scZhyfwzh, t);
			scZhyfwzhService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "志愿服务者更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 志愿服务者新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScZhyfwzhEntity scZhyfwzh, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scZhyfwzh.getId())) {
			scZhyfwzh = scZhyfwzhService.getEntity(ScZhyfwzhEntity.class, scZhyfwzh.getId());
			req.setAttribute("scZhyfwzhPage", scZhyfwzh);
		}
		return new ModelAndView("com/chrhc/project/sc/zhyzh/scZhyfwzh-add");
	}
	/**
	 * 志愿服务者编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScZhyfwzhEntity scZhyfwzh, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scZhyfwzh.getId())) {
			scZhyfwzh = scZhyfwzhService.getEntity(ScZhyfwzhEntity.class, scZhyfwzh.getId());
			req.setAttribute("scZhyfwzhPage", scZhyfwzh);
		}
		return new ModelAndView("com/chrhc/project/sc/zhyzh/scZhyfwzh-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/zhyzh/scZhyfwzhUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScZhyfwzhEntity scZhyfwzh,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScZhyfwzhEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scZhyfwzh, request.getParameterMap());
		List<ScZhyfwzhEntity> scZhyfwzhs = this.scZhyfwzhService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"志愿服务者");
		modelMap.put(NormalExcelConstants.CLASS,ScZhyfwzhEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("志愿服务者列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scZhyfwzhs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScZhyfwzhEntity scZhyfwzh,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "志愿服务者");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScZhyfwzhEntity.class);
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
				List<ScZhyfwzhEntity> listScZhyfwzhEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScZhyfwzhEntity.class,params);
				for (ScZhyfwzhEntity scZhyfwzh : listScZhyfwzhEntitys) {
					scZhyfwzhService.save(scZhyfwzh);
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
