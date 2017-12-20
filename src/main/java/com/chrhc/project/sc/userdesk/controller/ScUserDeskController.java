package com.chrhc.project.sc.userdesk.controller;
import com.chrhc.project.sc.message.service.ScMessageServiceI;
import com.chrhc.project.sc.mostmenu.entity.ScMostMenuEntity;
import com.chrhc.project.sc.userdesk.entity.ScUserDeskEntity;
import com.chrhc.project.sc.userdesk.page.ScUserDeskPage;
import com.chrhc.Interceptor.InterceptorException;

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
import org.jeecgframework.core.constant.DataBaseConstant;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSUser;
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
 * @Description: 自定义桌面
 * @author onlineGenerator
 * @date 2015-08-04 15:33:42
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scUserDeskController")
public class ScUserDeskController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScUserDeskController.class);
	
	@Autowired
	private ScMessageServiceI scMessageService;

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
	 * 自定义桌面列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scUserDesk")
	public ModelAndView scUserDesk(HttpServletRequest request) {
		
		TSUser user = ResourceUtil.getSessionUserName();
		String code = ResourceUtil.getUserSystemData(DataBaseConstant.SYS_ORG_CODE);
		
		List<String> functionid = systemService.findListbySql("select functionid from t_s_role_function where roleid in (select roleid from t_s_role_user where userid='"+user.getId()+"')");

		List<ScUserDeskEntity> desk =  systemService.findHql(" from ScUserDeskEntity where userId = '"+user.getId()+"'"); 
		for(int i=0; i<desk.size();i++){
			ScUserDeskEntity uDesk = desk.get(i);
			String id = uDesk.getMenuId();
			if(!functionid.contains(id)){
				systemService.delete(uDesk);
			}
		}
		
		List<ScUserDeskEntity> list = systemService.findHql("from ScUserDeskEntity where userId = ?",user.getId());
		
		List<ScUserDeskPage> userDesks = new ArrayList<ScUserDeskPage>();
        for(int i=0;i<list.size();i++){
        	ScUserDeskPage deskPage = new ScUserDeskPage();
        	deskPage.setUserDeskEntity(list.get(i));
        	deskPage.setFunction(systemService.get(TSFunction.class, list.get(i).getMenuId()));
        	userDesks.add(deskPage);
        }
        
        request.setAttribute("userDesk", userDesks);
		
		return new ModelAndView("com/chrhc/project/sc/userdesk/scUserDeskList");
	}

 
	/**
	 * 删除自定义桌面
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScUserDeskEntity scUserDesk, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		scUserDesk = systemService.getEntity(ScUserDeskEntity.class, scUserDesk.getId());
		message = "自定义桌面删除成功";
		try{
			systemService.delete(scUserDesk);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "自定义桌面删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除自定义桌面
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "自定义桌面删除成功";
		try{
			for(String id:ids.split(",")){
				ScUserDeskEntity scUserDesk = systemService.getEntity(ScUserDeskEntity.class, 
				id
				);
				systemService.delete(scUserDesk);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "自定义桌面删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加自定义桌面
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScUserDeskEntity scUserDesk, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "自定义桌面添加成功";
		TSUser user = ResourceUtil.getSessionUserName();
		try{
			List<ScUserDeskEntity> list = systemService.findHql("from ScUserDeskEntity where userId = ?",user.getId());
			List<ScUserDeskEntity> arr = new ArrayList<ScUserDeskEntity>();
			for(int i=0;i<list.size();i++){
				ScUserDeskEntity scUserDesk1 = list.get(i);
				if(scUserDesk1.getMenuId().equals(request.getParameter("menuId"))){
					arr.add(scUserDesk1);
					break;
				}else{
					
				}
			}
		if(arr.isEmpty()){
			scUserDesk.setUserId( ResourceUtil.getSessionUserName().getId());
			scUserDesk.setMenuId(request.getParameter("menuId"));
			scUserDesk.setMenuName(request.getParameter("menuName"));
			scUserDesk.setDelflag("0");
			systemService.save(scUserDesk);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		}catch(Exception e){
			e.printStackTrace();
			message = "自定义桌面添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新自定义桌面
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScUserDeskEntity scUserDesk, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "自定义桌面更新成功";
		ScUserDeskEntity t = systemService.get(ScUserDeskEntity.class, scUserDesk.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scUserDesk, t);
			systemService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
		   if(e instanceof InterceptorException){
				throw new InterceptorException("");
			}else{
				e.printStackTrace();
				message = "自定义桌面更新失败";
				throw new BusinessException(e.getMessage());
			}				
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 自定义桌面新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScUserDeskEntity scUserDesk, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scUserDesk.getId())) {
			scUserDesk = systemService.getEntity(ScUserDeskEntity.class, scUserDesk.getId());
			req.setAttribute("scUserDeskPage", scUserDesk);
		}
		return new ModelAndView("com/chrhc/project/sc/userdesk/scUserDesk-add");
	}
	/**
	 * 自定义桌面编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScUserDeskEntity scUserDesk, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scUserDesk.getId())) {
			scUserDesk = systemService.getEntity(ScUserDeskEntity.class, scUserDesk.getId());
			req.setAttribute("scUserDeskPage", scUserDesk);
		}
		return new ModelAndView("com/chrhc/project/sc/userdesk/scUserDesk-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/userdesk/scUserDeskUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScUserDeskEntity scUserDesk,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScUserDeskEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scUserDesk, request.getParameterMap());
		List<ScUserDeskEntity> scUserDesks = this.systemService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"自定义桌面");
		modelMap.put(NormalExcelConstants.CLASS,ScUserDeskEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("自定义桌面列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scUserDesks);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScUserDeskEntity scUserDesk,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "自定义桌面");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScUserDeskEntity.class);
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
				List<ScUserDeskEntity> listScUserDeskEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScUserDeskEntity.class,params);
				for (ScUserDeskEntity scUserDesk : listScUserDeskEntitys) {
					systemService.save(scUserDesk);
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
