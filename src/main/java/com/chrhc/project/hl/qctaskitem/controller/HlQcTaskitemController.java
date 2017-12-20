package com.chrhc.project.hl.qctaskitem.controller;
import com.chrhc.project.hl.qctaskitem.entity.HlQcTaskitemEntity;
import com.chrhc.project.hl.qctaskitem.service.HlQcTaskitemServiceI;
import com.chrhc.Interceptor.InterceptorException;

import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import org.jeecgframework.web.system.pojo.base.TSUser;
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
import org.springframework.web.servlet.tags.Param;


/**
 * @Title: Controller
 * @Description: 质检考评
 * @author onlineGenerator
 * @date 2016-07-15 02:31:51
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/hlQcTaskitemController")
public class HlQcTaskitemController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HlQcTaskitemController.class);

	@Autowired
	private HlQcTaskitemServiceI hlQcTaskitemService;
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
	 * 质检考评列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "hlQcTaskitem")
	public ModelAndView hlQcTaskitem(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/hl/qctaskitem/hlQcTaskitemList");
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
	public void datagrid(HlQcTaskitemEntity hlQcTaskitem,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(HlQcTaskitemEntity.class, dataGrid);
		//查询条件组装器
		//org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, hlQcTaskitem, request.getParameterMap());
		try{
		//自定义追加查询条件
			String task_name = request.getParameter("taskname");
			String sql = "SELECT t.id,t.orderid,t.taskid,t.ordertype,t.avsorce,q.task_name  taskname,q.scorepersonname,q.scoreperson from HL_QC_TASKITEM  t  LEFT  JOIN HL_QC_TASK q on  t.TASKID = q.id ";
			Map params =  new HashMap<String,Object>();
			TSUser user = ResourceUtil.getSessionUserName();
			if(StringUtils.isNotEmpty(user.getUserName())){
				params.put("q.scoreperson"," like '%"+user.getUserName()+"%'");
			}
			if(StringUtils.isNotEmpty(task_name)){
				params.put("q.task_name"," like '%"+task_name+"%'");
			}
			params.put("t.delflag","='0'");

			String sort = dataGrid.getSort();
			String order = dataGrid.getOrder().toString();
			int page = dataGrid.getPage();
			int rows = dataGrid.getRows();
			List<Map<String, Object>> list = hlQcTaskitemService.querySingle(sql,params,sort,order,page,rows);
			dataGrid.setTotal(list.size());
			dataGrid.setResults(list);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		//cq.add();
		//this.hlQcTaskitemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 获取工单编号
	 * @param hlQcTaskitem
	 * @param request
     * @return
     */
	@RequestMapping(params = "getOrderCode")
	@ResponseBody
	public AjaxJson getOrderCode(HlQcTaskitemEntity hlQcTaskitem, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		String bus_type = request.getParameter("bus_type");
		try{
			String sql = "select code from "+bus_type+ " where id=?";
			List<Map<String,Object>> list = systemService.findForJdbc(sql,id);
			if(list != null && list.size() > 0){
				String code = String.valueOf(list.get(0).get("CODE"));
				j.setObj(code);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 跳转质检打分页面
	 * @param hlQcTaskitem
	 * @param req
     * @return
     */
	@RequestMapping(params = "goScore")
	public ModelAndView goScore(HlQcTaskitemEntity hlQcTaskitem, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hlQcTaskitem.getId())) {
			hlQcTaskitem = hlQcTaskitemService.getEntity(HlQcTaskitemEntity.class, hlQcTaskitem.getId());
			req.setAttribute("hlQcTaskitemPage", hlQcTaskitem);
		}
		return new ModelAndView("com/chrhc/project/hl/qctaskitem/hlQcTaskitem-score");
	}

	/**
	 * 跳转至评分项打分页面
	 * @param hlQcTaskitem
	 * @param req
     * @return
     */
	@RequestMapping(params = "goScoreOne")
	public ModelAndView goScoreOne(HlQcTaskitemEntity hlQcTaskitem,HttpServletRequest req){
		req.setAttribute("hlQcTaskitemPage", hlQcTaskitem);
		return new ModelAndView("com/chrhc/project/hl/qctaskitem/score_include");
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagridScore")
	public void datagridScore(HlQcTaskitemEntity hlQcTaskitem,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){

	}
	/**
	 * 删除质检考评
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(HlQcTaskitemEntity hlQcTaskitem, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		hlQcTaskitem = systemService.getEntity(HlQcTaskitemEntity.class, hlQcTaskitem.getId());
		message = "质检考评删除成功";
		try{
			hlQcTaskitemService.deleteLogic(hlQcTaskitem);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "质检考评删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除质检考评
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "质检考评删除成功";
		try{
			for(String id:ids.split(",")){
				HlQcTaskitemEntity hlQcTaskitem = systemService.getEntity(HlQcTaskitemEntity.class, 
				id
				);
				hlQcTaskitemService.deleteLogic(hlQcTaskitem);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "质检考评删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加质检考评
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(HlQcTaskitemEntity hlQcTaskitem, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "质检考评添加成功";
		try{
			hlQcTaskitemService.save(hlQcTaskitem);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "质检考评添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新质检考评
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(HlQcTaskitemEntity hlQcTaskitem, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "质检考评更新成功";
		HlQcTaskitemEntity t = hlQcTaskitemService.get(HlQcTaskitemEntity.class, hlQcTaskitem.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(hlQcTaskitem, t);
			hlQcTaskitemService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
		   if(e instanceof InterceptorException){
				throw new InterceptorException("");
			}else{
				e.printStackTrace();
				message = "质检考评更新失败";
				throw new BusinessException(e.getMessage());
			}				
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 质检考评新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(HlQcTaskitemEntity hlQcTaskitem, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hlQcTaskitem.getId())) {
			hlQcTaskitem = hlQcTaskitemService.getEntity(HlQcTaskitemEntity.class, hlQcTaskitem.getId());
			req.setAttribute("hlQcTaskitemPage", hlQcTaskitem);
		}
		return new ModelAndView("com/chrhc/project/hl/qctaskitem/hlQcTaskitem-add");
	}
	/**
	 * 质检考评编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(HlQcTaskitemEntity hlQcTaskitem, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hlQcTaskitem.getId())) {
			hlQcTaskitem = hlQcTaskitemService.getEntity(HlQcTaskitemEntity.class, hlQcTaskitem.getId());
			req.setAttribute("hlQcTaskitemPage", hlQcTaskitem);
		}
		return new ModelAndView("com/chrhc/project/hl/qctaskitem/hlQcTaskitem-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/hl/qctaskitem/hlQcTaskitemUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(HlQcTaskitemEntity hlQcTaskitem,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(HlQcTaskitemEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, hlQcTaskitem, request.getParameterMap());
		List<HlQcTaskitemEntity> hlQcTaskitems = this.hlQcTaskitemService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"质检考评");
		modelMap.put(NormalExcelConstants.CLASS,HlQcTaskitemEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("质检考评列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,hlQcTaskitems);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(HlQcTaskitemEntity hlQcTaskitem,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "质检考评");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,HlQcTaskitemEntity.class);
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
				List<HlQcTaskitemEntity> listHlQcTaskitemEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),HlQcTaskitemEntity.class,params);
				for (HlQcTaskitemEntity hlQcTaskitem : listHlQcTaskitemEntitys) {
					hlQcTaskitemService.save(hlQcTaskitem);
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
