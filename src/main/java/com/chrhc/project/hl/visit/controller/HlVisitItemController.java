package com.chrhc.project.hl.visit.controller;
import com.chrhc.project.hl.visit.entity.HlBusVisitEntity;
import com.chrhc.project.hl.visit.entity.HlVisitItemEntity;
import com.chrhc.project.hl.visit.service.HlBusVisitServiceI;
import com.chrhc.project.hl.visit.service.HlVisitItemServiceI;
import com.chrhc.Interceptor.InterceptorException;

import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.runtime.directive.Foreach;
import org.jeecgframework.core.constant.DataBaseConstant;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.cgform.service.build.DataBaseService;
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
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;

import java.io.OutputStream;

import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 回访题库
 * @author onlineGenerator
 * @date 2016-07-20 10:50:55
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/hlVisitItemController")
public class HlVisitItemController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HlVisitItemController.class);

	@Autowired
	private HlBusVisitServiceI hlBusVisitService;
	@Autowired
	private HlVisitItemServiceI hlVisitItemService;
	@Autowired
	private SystemService systemService;
	private String message;
	@Autowired
	private DataBaseService dataBaseService;
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 *公告阅读后配置为只阅读
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "queryZdh")
	@ResponseBody
	public AjaxJson queryZdh(HttpServletRequest request){
		AjaxJson json = new AjaxJson();
		String zdh = request.getParameter("zdh");
		TSUser user = ResourceUtil.getSessionUserName();
		String usernae = user.getUserName();
		String sql = "SELECT DEVID devid,地市 ds,商户名 shm,商户号 shh,装机地址 zjdz,厂商 cs,机型 jx, 终端号 zdh,序列号 xlh, contact contact,tel tel,ADDRESS address,收单机器名称 sdjqmc,拓展人 tzr from V_BUCUSNAME_INFO where  终端号 is not null and 终端号 like '%"+zdh+"%'";
		List<Map<String, Object>> list = systemService.findForJdbc(sql);
		if(list!=null && list.size() > 0){
			json.setObj(list);
		}else{
			json.setSuccess(false);
		}
		//String listjosn =JSONHelper .array2json(list);

		return json;
	}
	/**
	 *取消收藏
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "deletesc")
	@ResponseBody
	public AjaxJson deletesc(HttpServletRequest request){
		AjaxJson json = new AjaxJson();
		String usercode = request.getParameter("usercode");
		TSUser user = ResourceUtil.getSessionUserName();
		String sql = "delete from hl_user_collection t where t.usercode='"+usercode+"' and t.create_by='"+user.getUserName()+"'";
		long count = systemService.executeSql(sql);
		return json;
	}
	/**
	 *添加收藏
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addsc")
	@ResponseBody
	public AjaxJson addsc(HttpServletRequest request){
		AjaxJson json = new AjaxJson();
		String usercode = request.getParameter("usercode");
		TSUser user = ResourceUtil.getSessionUserName();
		String sql = "select count(id) from hl_user_collection  t where t.usercode='"+usercode+"' and t.create_by='"+user.getUserName()+"'";
		long count = systemService.getCountForJdbc(sql);
		if(count==0){
			String pkId = UUIDGenerator.generate();
			Map<String,Object> map = new HashMap<>();
			map.put("id",pkId);
			map.put(DataBaseConstant.CREATE_DATE_TABLE, DateUtils.formatTime());
			map.put(DataBaseConstant.CREATE_BY_TABLE, ResourceUtil.getUserSystemData(DataBaseConstant.SYS_USER_CODE));
			map.put(DataBaseConstant.CREATE_NAME_TABLE, ResourceUtil.getUserSystemData(DataBaseConstant.SYS_USER_NAME));
			map.put(DataBaseConstant.SYS_ORG_CODE_TABLE, ResourceUtil.getUserSystemData(DataBaseConstant.SYS_ORG_CODE));
			map.put("usercode",usercode);

			int i = dataBaseService.insertTable("hl_user_collection",map);
		}


		return json;
	}
	/**
	 *公告阅读后配置为只阅读
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateZxjd")
	@ResponseBody
	public AjaxJson updateZxjd(HttpServletRequest request){
		AjaxJson json = new AjaxJson();
		String id = request.getParameter("id");
		TSUser user = ResourceUtil.getSessionUserName();
		String usernae = user.getUserName();
		String sql = "update hl_repair set pgzt='3' where id=?";
		int i = systemService.executeSql(sql,id);
		return json;
	}
	/**
	 *公告阅读后配置为只阅读
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getCountIsread")
	@ResponseBody
	public AjaxJson getCountIsread(HttpServletRequest request){
		AjaxJson json = new AjaxJson();
		String id = request.getParameter("id");
		TSUser user = ResourceUtil.getSessionUserName();
		String usernae = user.getUserName();
		String sql = "SELECT count(id) from IO_NOTICE   t where t.ISPUBLISH = 'Y' and t.TOUSERID like '%"+usernae+"%' ";

		Long a = systemService.getCountForJdbc(sql);

		String sqla = "SELECT count(*) from IO_NOTICE t  INNER JOIN HL_IONOTICEREADINFO b on t.id = b.IO_NOTICE_ID where b.userid = '"+usernae+"' and t.ispublish='Y'";
		Long b = systemService.getCountForJdbc(sqla);
		int c = 0;
        if(a >= b){
			c = (int) (a - b);
		}

		json.setObj(c);
		return json;
	}
	/**
	 *公告阅读后配置为只阅读
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateIsread")
	@ResponseBody
	public AjaxJson updateIsread(HttpServletRequest request){
		AjaxJson json = new AjaxJson();
		String id = request.getParameter("id");
		TSUser user = ResourceUtil.getSessionUserName();
		String usernae = user.getUserName();
		String sql = "SELECT count(id) from HL_IONOTICEREADINFO where IO_NOTICE_ID = '"+id+"' and USERID = '"+usernae+"'";
		Long a = systemService.getCountForJdbc(sql);

		if (a == 0) {
			//未阅读可新增
			String pkId = UUIDGenerator.generate();
			Map<String,Object> map = new HashMap<>();
			map.put("id",pkId);
			map.put(DataBaseConstant.CREATE_DATE_TABLE, DateUtils.formatTime());
			map.put(DataBaseConstant.CREATE_BY_TABLE, ResourceUtil.getUserSystemData(DataBaseConstant.SYS_USER_CODE));
			map.put(DataBaseConstant.CREATE_NAME_TABLE, ResourceUtil.getUserSystemData(DataBaseConstant.SYS_USER_NAME));
			map.put(DataBaseConstant.SYS_ORG_CODE_TABLE, ResourceUtil.getUserSystemData(DataBaseConstant.SYS_ORG_CODE));
			map.put("io_notice_id",id);
			map.put("userid",usernae);

			int i = dataBaseService.insertTable("hl_ionoticereadinfo",map);
		}
		json.setObj(a);
		return json;
	}
	/**
	 *保存客户信息时，检查本次来电记录是否存在客户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkhlcalls")
	@ResponseBody
	public AjaxJson checkhlcalls(HttpServletRequest request){
		AjaxJson json = new AjaxJson();

		String id = request.getParameter("history_call_id");
		String sql = "select customer_id from hl_history_calls where id = ?";

		List<Map<String,Object>> list = systemService.findForJdbc(sql,id);
		if(list != null && list.size() > 0){
			String customer_id = String.valueOf(list.get(0).get("CUSTOMER_ID"));
			if(StringUtil.isNotEmpty(customer_id) && !"null".equals(customer_id)){
				json.setSuccess(false);
			}
		}
		return json;
	}
	/**
	 *保存客户信息时，更新本次通话记录中的客户信息
	 * @param request
	 * @return
     */
	@RequestMapping(params = "updateHlcalls")
	@ResponseBody
	public AjaxJson updateHlcalls(HttpServletRequest request){
		AjaxJson json = new AjaxJson();
		String id = request.getParameter("history_call_id");
		String customer_id = request.getParameter("id");
		String cm_code = request.getParameter("cm_code");
		String cm_name = request.getParameter("cm_name");
		String cm_tel = request.getParameter("cm_tel");
		String sql = "update hl_history_calls  set customer_code=?,customer_name=?,customer_tel=?,customer_id=? where id=?";
		int a = systemService.executeSql(sql,cm_code,cm_name,cm_tel,customer_id,id);
		json.setObj(a);
		return json;
	}
	/**
	 * 查询展示题目内容
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goManual")
	public ModelAndView goManual(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("com/chrhc/project/hl/visit/hlRepairManual");
		return mv;
	}
	/**
	 * 查询展示题目内容
	 * @param request
	 * @return
     */
	@RequestMapping(params = "goHlVisitItem")
	public ModelAndView goHlVisitItem(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		String bus_id = request.getParameter("bus_id");
		List<HlBusVisitEntity> lista = hlBusVisitService.findByProperty(HlBusVisitEntity.class,"busId",bus_id);

		//List<HlVisitItemEntity> list = hlVisitItemService.getList(HlVisitItemEntity.class);
		//查询题库开启的题目
		List<HlVisitItemEntity> list = hlVisitItemService.findByProperty(HlVisitItemEntity.class,"sfkq","Y");
		for(HlBusVisitEntity n : lista){
			String a = n.getVisitId();
			String b = n.getVisitResult();
			for (HlVisitItemEntity t:list) {
				String c = t.getId();
				if(a.equals(c)){
					t.setBza(b);
					break;
				}
			}
		}

		mv.addObject("visitlist",list);
		String listjosn =JSONHelper .array2json(list);
		mv.addObject("visitlistjson",listjosn);
		mv.setViewName("com/chrhc/project/hl/visit/visitlist");
		return mv;
	}

	/**
	 * 保存题库与业务关联
	 * @param request
	 * @return
     */
	@RequestMapping(params = "savebusvisit")
	@ResponseBody
	public AjaxJson savebusvisit(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String visitlist =  request.getParameter("visitlist");
		String busId = request.getParameter("busId");
		List<HlBusVisitEntity> lista = hlBusVisitService.findByProperty(HlBusVisitEntity.class,"busId",busId);
		//删除busid的记录
		hlBusVisitService.deleteAllEntitie(lista);
		//重新加入记录
		List<HlBusVisitEntity> list = JSONHelper.toList(visitlist, HlBusVisitEntity.class);
		hlBusVisitService.batchSave(list);
		//Map<String,String[]> dd = request.getParameterMap();
		return j;
	}
	/**
	 * 回访题库列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "hlVisitItem")
	public ModelAndView hlVisitItem(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/hl/visit/hlVisitItemList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(HlVisitItemEntity hlVisitItem,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(HlVisitItemEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, hlVisitItem, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.hlVisitItemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除回访题库
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(HlVisitItemEntity hlVisitItem, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		hlVisitItem = systemService.getEntity(HlVisitItemEntity.class, hlVisitItem.getId());
		message = "回访题库删除成功";
		try{
			hlVisitItemService.deleteLogic(hlVisitItem);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "回访题库删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除回访题库
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "回访题库删除成功";
		try{
			for(String id:ids.split(",")){
				HlVisitItemEntity hlVisitItem = systemService.getEntity(HlVisitItemEntity.class, 
				id
				);
				hlVisitItemService.deleteLogic(hlVisitItem);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "回访题库删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加回访题库
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(HlVisitItemEntity hlVisitItem, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "回访题库添加成功";
		try{
			hlVisitItemService.save(hlVisitItem);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "回访题库添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新回访题库
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(HlVisitItemEntity hlVisitItem, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "回访题库更新成功";
		HlVisitItemEntity t = hlVisitItemService.get(HlVisitItemEntity.class, hlVisitItem.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(hlVisitItem, t);
			hlVisitItemService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
		   if(e instanceof InterceptorException){
				throw new InterceptorException("");
			}else{
				e.printStackTrace();
				message = "回访题库更新失败";
				throw new BusinessException(e.getMessage());
			}				
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 回访题库新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(HlVisitItemEntity hlVisitItem, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hlVisitItem.getId())) {
			hlVisitItem = hlVisitItemService.getEntity(HlVisitItemEntity.class, hlVisitItem.getId());
			req.setAttribute("hlVisitItemPage", hlVisitItem);
		}
		return new ModelAndView("com/chrhc/project/hl/visit/hlVisitItem-add");
	}
	/**
	 * 回访题库编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(HlVisitItemEntity hlVisitItem, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hlVisitItem.getId())) {
			hlVisitItem = hlVisitItemService.getEntity(HlVisitItemEntity.class, hlVisitItem.getId());
			req.setAttribute("hlVisitItemPage", hlVisitItem);
		}
		return new ModelAndView("com/chrhc/project/hl/visit/hlVisitItem-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/hl/visit/hlVisitItemUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(HlVisitItemEntity hlVisitItem,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(HlVisitItemEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, hlVisitItem, request.getParameterMap());
		List<HlVisitItemEntity> hlVisitItems = this.hlVisitItemService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"回访题库");
		modelMap.put(NormalExcelConstants.CLASS,HlVisitItemEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("回访题库列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,hlVisitItems);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(HlVisitItemEntity hlVisitItem,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "回访题库");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,HlVisitItemEntity.class);
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
				List<HlVisitItemEntity> listHlVisitItemEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),HlVisitItemEntity.class,params);
				for (HlVisitItemEntity hlVisitItem : listHlVisitItemEntitys) {
					hlVisitItemService.save(hlVisitItem);
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
