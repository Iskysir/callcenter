package com.chrhc.project.sc.gis.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import org.jeecgframework.core.util.JSONHelper;
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
import org.jeecgframework.web.cgform.service.build.DataBaseService;
import org.jeecgframework.web.cgform.service.impl.build.DataBaseServiceImpl;
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

import com.chrhc.project.sc.gis.entity.ScGislayerEntity;
import com.chrhc.project.sc.gis.service.ScGislayerServiceI;



/**   
 * @Title: Controller
 * @Description: 图层信息
 * @author onlineGenerator
 * @date 2015-05-07 18:49:28
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scGislayerController")
public class ScGislayerController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScGislayerController.class);

	@Autowired
	private ScGislayerServiceI scGislayerService;
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private DataBaseService dataBaseService;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 图层信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scGislayer")
	public ModelAndView scGislayer(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/gis/scGislayerList");
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
	public void datagrid(ScGislayerEntity scGislayer,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScGislayerEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scGislayer, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scGislayerService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "getData")
	public  void getLayerData(ScGislayerEntity scGislayer, HttpServletRequest request, HttpServletResponse response)
	{
		ScGislayerEntity scGislayerEntity=scGislayerService.getEntity(ScGislayerEntity.class, scGislayer.getId());
		
		String sql=dataBaseService.replaceExtendSqlSysVar(scGislayerEntity.getDatasql()+" and sys_org_code like '{sys_org_code}%'");
		//sql=sql+" where sys_org_code={sys_org_code}";
		System.out.println("sql:"+sql);
		List<Map<String, Object>> listData=systemService.findForJdbc(scGislayerEntity.getDatasql());
		String callback=request.getParameter("callback");
		StringBuffer sb=new StringBuffer();
		sb.append("[{layerKey:\""+scGislayer.getId()+"\",dataSource:");
		sb.append(JSONHelper.toJSONString(listData));
		sb.append("}]");
	/*	sb.append("[{layerKey:\"test_0\",dataSource:[");
				sb.append("{");
				sb.append("name:\"测试1\",");
				sb.append("id:\"1\",");
				sb.append("coords : '8024,8156,7704,8526,7884,8968,8742,9138,9046,8938,8986,8262,8144,7958',");
				sb.append("style:{");
				sb.append("Size:5, ");
				sb.append("Color:'#ff3300',");
				sb.append("Opacity:0.5,");
				sb.append("Dashstyle:\"dot\"");
				sb.append("}");
				sb.append("}]");
				sb.append("}]");*/
		try {
			PrintWriter pw=response.getWriter();
			if(StringUtil.isEmpty(callback))
			{
				pw.write(sb.toString());//JSONHelper.toJSONString(listData));
				
			}
			else
			{
				pw.write(callback+"("+sb.toString()+")");
			}
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 删除图层信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScGislayerEntity scGislayer, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scGislayer = systemService.getEntity(ScGislayerEntity.class, scGislayer.getId());
		message = "图层信息删除成功";
		try{
			scGislayerService.delete(scGislayer);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "图层信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除图层信息
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "图层信息删除成功";
		try{
			for(String id:ids.split(",")){
				ScGislayerEntity scGislayer = systemService.getEntity(ScGislayerEntity.class, 
				id
				);
				scGislayerService.delete(scGislayer);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "图层信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加图层信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScGislayerEntity scGislayer, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "图层信息添加成功";
		try{
			scGislayerService.save(scGislayer);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "图层信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新图层信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScGislayerEntity scGislayer, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "图层信息更新成功";
		ScGislayerEntity t = scGislayerService.get(ScGislayerEntity.class, scGislayer.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scGislayer, t);
			scGislayerService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "图层信息更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 图层信息新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScGislayerEntity scGislayer, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scGislayer.getId())) {
			scGislayer = scGislayerService.getEntity(ScGislayerEntity.class, scGislayer.getId());
			req.setAttribute("scGislayerPage", scGislayer);
		}
		return new ModelAndView("com/chrhc/project/sc/gis/scGislayer-add");
	}
	/**
	 * 图层信息编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScGislayerEntity scGislayer, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scGislayer.getId())) {
			scGislayer = scGislayerService.getEntity(ScGislayerEntity.class, scGislayer.getId());
			req.setAttribute("scGislayerPage", scGislayer);
		}
		return new ModelAndView("com/chrhc/project/sc/gis/scGislayer-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/gis/scGislayerUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScGislayerEntity scGislayer,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScGislayerEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scGislayer, request.getParameterMap());
		List<ScGislayerEntity> scGislayers = this.scGislayerService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"图层信息");
		modelMap.put(NormalExcelConstants.CLASS,ScGislayerEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("图层信息列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scGislayers);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScGislayerEntity scGislayer,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "图层信息");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScGislayerEntity.class);
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
				List<ScGislayerEntity> listScGislayerEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScGislayerEntity.class,params);
				for (ScGislayerEntity scGislayer : listScGislayerEntitys) {
					scGislayerService.save(scGislayer);
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
	
	/***
	 * 查询页面
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goView")
	public ModelAndView goView(HttpServletRequest request){
		request.setAttribute("curZoom", request.getParameter("curZoom"));
		request.setAttribute("centerX", request.getParameter("centerX"));
		request.setAttribute("centerY", request.getParameter("centerY"));
		return  new ModelAndView("system/gis/gismap");
	}
}
