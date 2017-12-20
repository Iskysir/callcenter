package com.chrhc.project.sc.wmly.controller;
import com.chrhc.project.sc.wmly.entity.ScWmlyEntity;
import com.chrhc.project.sc.wmly.service.ScWmlyServiceI;
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
 * @Description: 文明楼院
 * @author onlineGenerator
 * @date 2015-05-14 16:01:00
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scWmlyController")
public class ScWmlyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScWmlyController.class);

	@Autowired
	private ScWmlyServiceI scWmlyService;
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
	 * 文明楼院列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scWmly")
	public ModelAndView scWmly(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/wmly/scWmlyList");
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
	public void datagrid(ScWmlyEntity scWmly,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScWmlyEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scWmly, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scWmlyService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 根据门牌号和楼院地址组合判断数据库是否唯一
	 * @param houseNo
	 * @param address
	 * @return
	 */
	@RequestMapping(params = "doCheckCom")
	@ResponseBody
    public AjaxJson doCheckCom(String id,String houseNo,String address,HttpServletRequest request){
    	AjaxJson j = new AjaxJson();
    	message = "";
    	
    	if(null != id && !"".equals(id) && !"null".equals(id)){
    		//编辑页面
    		StringBuffer sb = new StringBuffer("from ScWmlyEntity where 1=1 and house_no=? and address=?");
    		List<ScWmlyEntity> hqlList = systemService.findHql(sb.toString(),houseNo,address);
    		if(null != hqlList && hqlList.size() > 0){
        		ScWmlyEntity e = hqlList.get(0);
        		if(id.equals(e.getId())){
        			j.setSuccess(true);
        		}else{
        			message = "已存在相同楼院地址和门牌号，请重新输入";
    	    		j.setMsg(message);
    	    		j.setSuccess(false);
        		}
    		}
    	}else{
    		//新增页面，id为空
    		StringBuffer sb = new StringBuffer("select * from sc_wmly where 1=1");
        	if(null != houseNo && !"".equals(houseNo) && null != address && !"".equals(address)){
        		sb.append(" and house_no = '"+houseNo+"'");
        		sb.append(" and address = '"+address+"'");
        	}
        	List<Object> list = systemService.findListbySql(sb.toString());
        	if(null != list && list.size() > 0){
        		message = "已存在相同楼院地址和门牌号，请重新输入";
	    		j.setMsg(message);
	    		j.setSuccess(false);
        	}else{
        		j.setSuccess(true);
        	}
    	}
    	
    	return j;
    }
	/**
	 * 删除文明楼院
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScWmlyEntity scWmly, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scWmly = systemService.getEntity(ScWmlyEntity.class, scWmly.getId());
		message = "文明楼院删除成功";
		try{
			scWmlyService.delete(scWmly);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "文明楼院删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除文明楼院
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "文明楼院删除成功";
		try{
			for(String id:ids.split(",")){
				ScWmlyEntity scWmly = systemService.getEntity(ScWmlyEntity.class, 
				id
				);
				scWmlyService.delete(scWmly);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "文明楼院删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加文明楼院
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScWmlyEntity scWmly, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "文明楼院添加成功";
		try{
			scWmlyService.save(scWmly);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "文明楼院添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新文明楼院
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScWmlyEntity scWmly, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "文明楼院更新成功";
		ScWmlyEntity t = scWmlyService.get(ScWmlyEntity.class, scWmly.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scWmly, t);
			scWmlyService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "文明楼院更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 文明楼院新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScWmlyEntity scWmly, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scWmly.getId())) {
			scWmly = scWmlyService.getEntity(ScWmlyEntity.class, scWmly.getId());
			req.setAttribute("scWmlyPage", scWmly);
		}
		return new ModelAndView("com/chrhc/project/sc/wmly/scWmly-add");
	}
	/**
	 * 文明楼院编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScWmlyEntity scWmly, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scWmly.getId())) {
			scWmly = scWmlyService.getEntity(ScWmlyEntity.class, scWmly.getId());
			req.setAttribute("scWmlyPage", scWmly);
		}
		return new ModelAndView("com/chrhc/project/sc/wmly/scWmly-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/wmly/scWmlyUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScWmlyEntity scWmly,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScWmlyEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scWmly, request.getParameterMap());
		List<ScWmlyEntity> scWmlys = this.scWmlyService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"文明楼院");
		modelMap.put(NormalExcelConstants.CLASS,ScWmlyEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("文明楼院列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scWmlys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScWmlyEntity scWmly,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "文明楼院");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScWmlyEntity.class);
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
				List<ScWmlyEntity> listScWmlyEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScWmlyEntity.class,params);
				for (ScWmlyEntity scWmly : listScWmlyEntitys) {
					scWmlyService.save(scWmly);
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
