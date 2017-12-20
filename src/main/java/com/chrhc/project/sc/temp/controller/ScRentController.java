package com.chrhc.project.sc.temp.controller;
import com.chrhc.project.sc.common.RequestUtil;
import com.chrhc.project.sc.temp.entity.ScRentEntity;
import com.chrhc.project.sc.temp.service.ScRentServiceI;
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
import org.jeecgframework.web.cgform.common.CgAutoListConstant;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;
import org.jeecgframework.web.cgform.service.autolist.CgTableServiceI;
import org.jeecgframework.web.cgform.service.autolist.ConfigServiceI;
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
 * @Description: 出租户证明
 * @author onlineGenerator
 * @date 2015-11-13 11:48:54
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scRentController")
public class ScRentController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScRentController.class);

	@Autowired
	private ScRentServiceI scRentService;
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
	 * 出租户证明列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scRent")
	public ModelAndView scRent(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/temp/scRentList");
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
	public void datagrid(ScRentEntity scRent,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScRentEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scRent, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scRentService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除出租户证明
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScRentEntity scRent, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scRent = systemService.getEntity(ScRentEntity.class, scRent.getId());
		message = "出租户证明删除成功";
		try{
			scRentService.deleteLogic(scRent);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "出租户证明删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除出租户证明
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "出租户证明删除成功";
		try{
			for(String id:ids.split(",")){
				ScRentEntity scRent = systemService.getEntity(ScRentEntity.class, 
				id
				);
				scRentService.deleteLogic(scRent);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "出租户证明删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加出租户证明
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScRentEntity scRent, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "出租户证明添加成功";
		try{
			scRentService.save(scRent);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "出租户证明添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新出租户证明
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScRentEntity scRent, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "出租户证明更新成功";
		ScRentEntity t = scRentService.get(ScRentEntity.class, scRent.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scRent, t);
			scRentService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
		   if(e instanceof InterceptorException){
				throw new InterceptorException("");
			}else{
				e.printStackTrace();
				message = "出租户证明更新失败";
				throw new BusinessException(e.getMessage());
			}				
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 出租户证明新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScRentEntity scRent, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scRent.getId())) {
			scRent = scRentService.getEntity(ScRentEntity.class, scRent.getId());
			req.setAttribute("scRentPage", scRent);
		}
		return new ModelAndView("com/chrhc/project/sc/temp/scRent-add");
	}
	/**
	 * 出租户证明编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScRentEntity scRent, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scRent.getId())) {
			scRent = scRentService.getEntity(ScRentEntity.class, scRent.getId());
			req.setAttribute("scRentPage", scRent);
		}
		return new ModelAndView("com/chrhc/project/sc/temp/scRent-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/temp/scRentUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScRentEntity scRent,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScRentEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scRent, request.getParameterMap());
		List<ScRentEntity> scRents = this.scRentService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"出租户证明");
		modelMap.put(NormalExcelConstants.CLASS,ScRentEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("出租户证明列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scRents);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScRentEntity scRent,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "出租户证明");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScRentEntity.class);
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
				List<ScRentEntity> listScRentEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScRentEntity.class,params);
				for (ScRentEntity scRent : listScRentEntitys) {
					scRentService.save(scRent);
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
	 * 出租证明提交之前验证
	 * @param scRent
	 * @param request
	 * @return
	 */
	@Autowired
	private ConfigServiceI configService;
	@Autowired
	private CgTableServiceI cgTableService;
	@RequestMapping(params = "beforeSubmit")
	@ResponseBody
	public AjaxJson beforeSubmit(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "验证成功";
		Map<String,Object> parameters =  new HashMap<String,Object>();
		parameters = RequestUtil.getParametersStartingWith(request, "");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String houseId = parameters.get("obligatec")+"";
		String rentName = parameters.get("rentName")+"";
		String rentIdcard = parameters.get("rentIdcard")+"";
		
		//String config = "sc_czfwgl";
		String config = "sc_fwczjl";
		Map<String, Object> configs = configService.queryConfigsByCode(config, "");
		String table = (String) configs.get(CgAutoListConstant.TABLENAME);
		List<CgFormFieldEntity> beans = (List<CgFormFieldEntity>) configs.get(CgAutoListConstant.FILEDS);
		StringBuffer fields = new StringBuffer("");
		if(null != beans && beans.size() > 0){
			for(int i= 0;i<beans.size();i++){
				if(i == beans.size() - 1){
					fields.append(beans.get(i).getFieldName());
				}else{
					fields.append(beans.get(i).getFieldName()).append(",");
				}
			}				
		}
		Map<String,Object> params =  new HashMap<String,Object>();
		if(null != houseId && !"".equals(houseId)){
			params.put("house_id", "='"+houseId+"'");			
		}
		if(null != rentName && !"".equals(rentName)){
			params.put("name", "='"+rentName+"'");			
		}
		if(null != rentIdcard && !"".equals(rentIdcard)){
			params.put("cert_id", "='"+rentIdcard+"'");			
		}
		params.put("delflag", "='0'");
		List<Map<String, Object>> list=cgTableService.querySingle(table, fields.toString(), params,"in_datatime","DESC", 1,99999);
		if(null != list && list.size() > 0){
			map = list.get(0);
		}
		logger.info("*****map="+map);
		if(null == list || list.size() == 0){
			message = "该出租房屋的入住者信息与租着信息不符";
			j.setMsg(message);
			j.setSuccess(false);
			j.setAttributes(map);
		}
		return j;
	}
}
