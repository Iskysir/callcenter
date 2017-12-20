package com.chrhc.project.sc.certemp.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.SeriNumGenerateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.chrhc.project.sc.certemp.entity.ScCerTemplateEntity;
import com.chrhc.project.sc.certemp.service.ScCerTemplateServiceI;
import com.chrhc.project.sc.common.ConvertUtil;
import com.chrhc.project.sc.temp.entity.ScRelationsEntity;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;



/**   
 * @Title: Controller
 * @Description: 证照模板
 * @date 2015-05-08 10:21:53
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scCerTemplateController")
public class ScCerTemplateController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScCerTemplateController.class);

	@Autowired
	private ScCerTemplateServiceI scCerTemplateService;
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
	 * 证照模板列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scCerTemplate")
	public ModelAndView scCerTemplate(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/certemp/scCerTemplateList");
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
	public void datagrid(ScCerTemplateEntity scCerTemplate,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScCerTemplateEntity.class, dataGrid);
		//查询条件组装器
		try{
			String tempName = request.getParameter("tempName");
			if(StringUtil.isNotEmpty(tempName)){
				cq.like("tempName",  "%" + tempName + "%");
			}
			
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scCerTemplateService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除证照模板
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScCerTemplateEntity scCerTemplate, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scCerTemplate = systemService.getEntity(ScCerTemplateEntity.class, scCerTemplate.getId());
		message = "证照模板删除成功";
		try{
			scCerTemplateService.delete(scCerTemplate);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "证照模板删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除证照模板
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "证照模板删除成功";
		try{
			for(String id:ids.split(",")){
				ScCerTemplateEntity scCerTemplate = systemService.getEntity(ScCerTemplateEntity.class, 
				id
				);
				scCerTemplateService.delete(scCerTemplate);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "证照模板删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加证照模板
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScCerTemplateEntity scCerTemplate, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "证照模板添加成功";
		try{
			String tempName = request.getParameter("tempName").trim();
			String tableName = request.getParameter("tableName").trim();
			scCerTemplate.setTempName(tempName);
			scCerTemplate.setTableName(tableName);
			scCerTemplateService.save(scCerTemplate);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "证照模板添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新证照模板
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScCerTemplateEntity scCerTemplate, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "证照模板更新成功";
		ScCerTemplateEntity t = scCerTemplateService.get(ScCerTemplateEntity.class, scCerTemplate.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scCerTemplate, t);
			scCerTemplateService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "证照模板更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 证照模板新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScCerTemplateEntity scCerTemplate, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scCerTemplate.getId())) {
			scCerTemplate = scCerTemplateService.getEntity(ScCerTemplateEntity.class, scCerTemplate.getId());
			req.setAttribute("scCerTemplatePage", scCerTemplate);
		}
		return new ModelAndView("com/chrhc/project/sc/certemp/scCerTemplate-add");
	}
	/**
	 * 证照模板编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "preview")
	public ModelAndView preview(ScCerTemplateEntity scCerTemplate, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scCerTemplate.getId())) {
			scCerTemplate = scCerTemplateService.getEntity(ScCerTemplateEntity.class, scCerTemplate.getId());
			req.setAttribute("scCerTemplatePage", scCerTemplate);
			
			Configuration cfg = new Configuration();
			StringTemplateLoader stringLoader = new StringTemplateLoader();
			stringLoader.putTemplate("myTemplate",scCerTemplate.getTemplate().replaceAll("&quot;", "\""));
			cfg.setTemplateLoader(stringLoader);
			
			ScRelationsEntity scRelationsEntity = scCerTemplateService.getEntity(ScRelationsEntity.class, "402881e84d5529c5014d5529c5740000");
			
			Map<String, Object> paras = new HashMap<String, Object>();
			MyBeanUtils.copyBean2Map(paras, scRelationsEntity);
 
			try {
				StringWriter swriter = new StringWriter();
				Template template = cfg.getTemplate("myTemplate","utf-8");
				template.process(paras, swriter);
				req.setAttribute("content", swriter.toString());
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return new ModelAndView("com/chrhc/project/sc/certemp/preview");
	}
	
	
	/**
	 * 证照模板编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tempPrint")
	public ModelAndView tempPrint(HttpServletRequest req) {
		
		String dataId = req.getParameter("id");
		String tableName = req.getParameter("tableName");
		String clazzName = ConvertUtil.tableName2EntityName(tableName);
		 //
		try {
			Object data = scCerTemplateService.getEntity(Class.forName("com.chrhc.project.sc.temp.entity." + clazzName), dataId);
			ScCerTemplateEntity scCerTemplate = (ScCerTemplateEntity) scCerTemplateService.findHql("from ScCerTemplateEntity where tableName like '%" + tableName +"%'").get(0);
			
			Configuration cfg = new Configuration();
			StringTemplateLoader stringLoader = new StringTemplateLoader();
			stringLoader.putTemplate("myTemplate",scCerTemplate.getTemplate().replaceAll("&quot;", "\""));
			cfg.setTemplateLoader(stringLoader);
			
//			String code = req.getParameter("code").substring(0, 2);
			
			
			Map<String, Object> paras = new HashMap<String, Object>();
			MyBeanUtils.copyBean2Map(paras, data);
 
			StringWriter swriter = new StringWriter();
			Template template = cfg.getTemplate("myTemplate","utf-8");
			template.process(paras, swriter);
			req.setAttribute("content", swriter.toString());
			
			req.setAttribute("id", paras.get("id"));
			req.setAttribute("qrcode",  scCerTemplate.getQrcode());
			req.setAttribute("tableName", tableName);
			
			
		} catch (Exception e) {
		 System.out.println(e.toString());
		}
		return new ModelAndView("com/chrhc/project/sc/certemp/preview");
	}
	/**
	 * 打印次数和打印状态更新
	 * @param req
	 */
	@RequestMapping(params = "printStatusAndNum" )
	@ResponseBody
	public AjaxJson  printStatusAndNum(HttpServletRequest req){
		String dataId = req.getParameter("id");
		String tableName = req.getParameter("tableName");
		
		String clazzName = ConvertUtil.tableName2EntityName(tableName);
		 //
		try {
			Object data = scCerTemplateService.getEntity(Class.forName("com.chrhc.project.sc.temp.entity." + clazzName), dataId);
			ReflectHelper helper = new ReflectHelper(data) ;
			helper.setMethodValue("printStatus", "已打印") ;
			int curCount =  Integer.valueOf(helper.getMethodValue("printNum")+"");
			helper.setMethodValue("printNum", curCount + 1) ;
			
			scCerTemplateService.save(data);
			message = "成功";
	}catch(Exception e){
		message = "失败";
			e.printStackTrace();
		}
		AjaxJson j = new AjaxJson();	
		j.setMsg(message);
		return  j;
	}

/**
 * 通过ID 改变打印状态
 * @param req
 * @param id 证明数据ID
 * @return
 */
	@RequestMapping(params = "printStatusAndNumbyID" )
	@ResponseBody
	public void printStatusAndNum_id(HttpServletRequest req,String id,HttpServletResponse rep){
		//String dataId = id;
		try {
			String sqls = "select tablename from sc_provetables ";
			List<String> tableList = scCerTemplateService.findListbySql(sqls);
			for (String tablename:tableList){
				try {
				String upsql ="update "+tablename+" set print_status='已打印',print_num =(print_num+1) where id='"+id+"';";
				int upnum =scCerTemplateService.updateBySqlString(upsql);
				System.out.println(tablename+upnum);
				}catch (Exception e) {
					e.printStackTrace();
					System.out.println("erro"+tablename);
				}
			}
			message = "成功";
		}catch(Exception e){
		message = "失败";
			e.printStackTrace();
		}
		//AjaxJson j = new AjaxJson();	
		//j.setMsg(message);
		rep.setContentType("text/html");
		rep.setCharacterEncoding("utf-8");
		try {
			PrintWriter out = rep.getWriter();
			out.print("datas({\"a\":1})");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return  null;
		}
		
		//return  null;
	}
	
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScCerTemplateEntity scCerTemplate, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scCerTemplate.getId())) {
			scCerTemplate = scCerTemplateService.getEntity(ScCerTemplateEntity.class, scCerTemplate.getId());
			req.setAttribute("scCerTemplatePage", scCerTemplate);
		}
		return new ModelAndView("com/chrhc/project/sc/certemp/scCerTemplate-update");
	}
	
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/certemp/scCerTemplateUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScCerTemplateEntity scCerTemplate,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScCerTemplateEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scCerTemplate, request.getParameterMap());
		List<ScCerTemplateEntity> scCerTemplates = this.scCerTemplateService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"证照模板");
		modelMap.put(NormalExcelConstants.CLASS,ScCerTemplateEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("证照模板列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scCerTemplates);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScCerTemplateEntity scCerTemplate,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "证照模板");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScCerTemplateEntity.class);
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
				List<ScCerTemplateEntity> listScCerTemplateEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScCerTemplateEntity.class,params);
				for (ScCerTemplateEntity scCerTemplate : listScCerTemplateEntitys) {
					scCerTemplateService.save(scCerTemplate);
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
	
	
	
	@RequestMapping(params = "getNum" ,method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson getNum(HttpServletRequest request,HttpServletResponse response){
		AjaxJson j = new AjaxJson();
		
		j.setMsg(SeriNumGenerateUtils.getId(request.getParameter("type")));
 
		return j;
	}
	
	
}
