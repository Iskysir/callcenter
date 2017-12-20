package com.chrhc.project.sc.zzgl.controller;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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
import org.jeecgframework.core.util.ResourceUtil;
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

import com.chrhc.Interceptor.InterceptorException;
import com.chrhc.project.sc.zzgl.entity.ScZzyzEntity;
import com.chrhc.project.sc.zzgl.service.ScZzyzServiceI;



/**   
 * @Title: Controller
 * @Description: 证照验真
 * @author onlineGenerator
 * @date 2015-06-11 14:23:32
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scZzyzController")
public class ScZzyzController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScZzyzController.class);

	@Autowired
	private ScZzyzServiceI scZzyzService;
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
	 * 证照验真列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scZzyz")
	public ModelAndView scZzyz(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/zzgl/scZzyzList");
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
	public void datagrid(ScZzyzEntity scZzyz,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScZzyzEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scZzyz, request.getParameterMap());
		try{
		//自定义追加查询条件
		String query_createDate_begin = request.getParameter("createDate_begin");
		String query_createDate_end = request.getParameter("createDate_end");
		if(StringUtil.isNotEmpty(query_createDate_begin)){
			cq.ge("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_createDate_begin));
		}
		if(StringUtil.isNotEmpty(query_createDate_end)){
			cq.le("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_createDate_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scZzyzService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除证照验真
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScZzyzEntity scZzyz, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scZzyz = systemService.getEntity(ScZzyzEntity.class, scZzyz.getId());
		message = "删除成功";
		try{
			scZzyzService.delete(scZzyz);
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
	 * 批量删除证照验真
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "删除成功";
		try{
			for(String id:ids.split(",")){
				ScZzyzEntity scZzyz = systemService.getEntity(ScZzyzEntity.class, 
				id
				);
				scZzyzService.delete(scZzyz);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加证照验真
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScZzyzEntity scZzyz, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "添加成功";
		try{
			scZzyzService.save(scZzyz);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setObj(scZzyz);
		return j;
	}
	
	/**
	 * 更新证照验真
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScZzyzEntity scZzyz, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "更新成功";
		ScZzyzEntity t = scZzyzService.get(ScZzyzEntity.class, scZzyz.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scZzyz, t);
			scZzyzService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
		   if(e instanceof InterceptorException){
				throw new InterceptorException("");
			}else{
				e.printStackTrace();
				message = "更新失败";
				throw new BusinessException(e.getMessage());
			}				
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 证照验真新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScZzyzEntity scZzyz, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scZzyz.getId())) {
			scZzyz = scZzyzService.getEntity(ScZzyzEntity.class, scZzyz.getId());
			req.setAttribute("scZzyzPage", scZzyz);
		}
		return new ModelAndView("com/chrhc/project/sc/zzgl/scZzyz-add");
	}
	/**
	 * 证照验真编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScZzyzEntity scZzyz, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scZzyz.getId())) {
			scZzyz = scZzyzService.getEntity(ScZzyzEntity.class, scZzyz.getId());
			req.setAttribute("scZzyzPage", scZzyz);
		}
		return new ModelAndView("com/chrhc/project/sc/zzgl/scZzyz-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/zzgl/scZzyzUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScZzyzEntity scZzyz,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScZzyzEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scZzyz, request.getParameterMap());
		List<ScZzyzEntity> scZzyzs = this.scZzyzService.getListByCriteriaQuery(cq,false);
		
		String file_Name="证照验真_sc_zzyz";
		//response.setContentType(arg0);
		try {
			file_Name= URLEncoder.encode(file_Name, "UTF-8");
			file_Name = new String(file_Name.getBytes("gbk"),"ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*try {
			//file_Name = new String("证照验真_sc_zzyz".getBytes(),"UTF-8");
			//file_Name = URLEncoder.encode(file_Name);
			String userAgent = request.getHeader("User-Agent");
		    byte[] bytes = userAgent.contains("MSIE") ? file_Name.getBytes()
		            : file_Name.getBytes("UTF-8"); // fileName.getBytes("UTF-8")处理safari的乱码问题
		    file_Name = new String(bytes, "ISO-8859-1"); // 各浏览器基本都支持ISO编码
			
		} catch (Exception e) {
			file_Name ="sc_zzyz";
			e.printStackTrace();
		}*/
		modelMap.put(NormalExcelConstants.FILE_NAME,file_Name);
		modelMap.put(NormalExcelConstants.CLASS,ScZzyzEntity.class);
		//modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("证照验真列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),	"导出信息"));
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams(null, null,null));
		
		modelMap.put(NormalExcelConstants.DATA_LIST,scZzyzs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScZzyzEntity scZzyz,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "证照验真");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScZzyzEntity.class);
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
				List<ScZzyzEntity> listScZzyzEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScZzyzEntity.class,params);
				for (ScZzyzEntity scZzyz : listScZzyzEntitys) {
					scZzyzService.save(scZzyz);
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
