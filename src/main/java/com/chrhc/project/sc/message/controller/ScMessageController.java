package com.chrhc.project.sc.message.controller;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
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
import com.chrhc.project.sc.message.entity.MsgConstants;
import com.chrhc.project.sc.message.entity.ScMessageEntity;
import com.chrhc.project.sc.message.entity.ScMsgRecordEntity;
import com.chrhc.project.sc.message.service.ScMessageServiceI;



/**   
 * @Title: Controller
 * @Description: 消息
 * @author onlineGenerator
 * @date 2015-08-06 13:42:35
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scMessageController")
public class ScMessageController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScMessageController.class);

	@Autowired
	private SystemService systemService;
	@Autowired
	private ScMessageServiceI scMessageService;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 消息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "sendOne")
	public ModelAndView scMessage(HttpServletRequest request) {
		
		request.setAttribute("receiver", request.getParameter("id"));
		
		return new ModelAndView("com/chrhc/project/sc/message/sendOne");
	}

	/**
	 * 给单个人发送
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "addOne")
	@ResponseBody
	public AjaxJson addOne(ScMessageEntity scMessage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "消息添加成功";
		TSUser tsUser = ResourceUtil.getSessionUserName();
		try{
			scMessage.setPublisher(tsUser.getId());
				String id = request.getParameter("receiver");
				String modelName = request.getParameter("modelName");
				scMessage.setModelType(modelName);
				
				scMessage.setReceiver(id);
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date date = sdf.parse(sdf.format(new Date()));
//			scMessage.setPTime(date);
				String title = request.getParameter("title");
				scMessage.setTitle(title);
			scMessageService.saveOne(scMessage);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "消息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 消息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scMessageList")
	public ModelAndView scMessageList(HttpServletRequest request) {
		
		return new ModelAndView("com/chrhc/project/sc/message/scMessageList");
	}
	
	/**
	 * 详情页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "look")
	public ModelAndView detail(ScMessageEntity scMessageEntity,HttpServletRequest request) {
//		TSUser user = ResourceUtil.getSessionUserName();
		String id = request.getParameter("id");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		List<ScMessageEntity> list = systemService.findHql("from ScMessageEntity where id = ?",id);
		scMessageEntity = list.get(0);
//		绑定信息类型
		String mType = scMessageEntity.getMType();
//		String[] mTypes = mType.split(",");
		request.setAttribute("mTypes", mType);
		
//		过滤内容
		String content = StringUtil.removeHTMLLable(scMessageEntity.getContent()) ;
		
		request.setAttribute("content", content);
		
//		绑定发送日期
		Date date = scMessageEntity.getPTime();
		String str=sdf.format(date);
		request.setAttribute("pTime", str);
//		绑定发送状态
		int sendFail=0;
		int send=0;
		List<Map<String, Object>> lists = systemService.findForJdbc("SELECT xm ,receiver,status,m_type FROM sc_rkjbxxnew AS rk JOIN  sc_msg_record AS msg ON rk.id = msg.receiver WHERE message_id =  '"+id+"'");
//		Map<String, String> suc = new HashMap<String, String>();
		StringBuffer success1 =new StringBuffer(); 
		StringBuffer failer =new StringBuffer(); 
		if(lists != null && !lists.isEmpty()){
			for(int i = 0;i<lists.size();i++){
				Map<String, Object> map = (Map<String, Object>)lists.get(i);
				String status = map.get("status").toString();
				String receiver = map.get("receiver").toString();
				String type = MsgConstants.type(map.get("m_type").toString());
				if(status.equals(MsgConstants.Msg_Send)){
					send++;
					String xm = map.get("xm").toString();
					xm = xm + "(" + type + ")";
					success1.append(xm + " ");

				}else if(status.equals(MsgConstants.Msg_Send_Fail)){
					String xm = map.get("xm").toString();

					xm = xm + "(" + type + ")";
					sendFail++;
					failer.append(xm + " ");
				}
			}
		}
		
//		绑定发送成功的人名
		request.setAttribute("receivers", success1);
		request.setAttribute("receiversFail", failer);
//		绑定发送成功的条数
		request.setAttribute("sendFail", sendFail);
		request.setAttribute("send", send);
//		绑定实体
		request.setAttribute("scMessageEntity", scMessageEntity);
		
		request.setAttribute("id", id);
		ModelAndView mav = new ModelAndView("com/chrhc/project/sc/message/scMessageDetail");
		mav.addObject("scMessageEntity",scMessageEntity );
		return mav;
		
//		return new ModelAndView("com/chrhc/project/sc/message/scMessageDetail");
	}
	
	/**
	 * 加载iframe
	 */
	@RequestMapping(params = "right")
	public ModelAndView iframe(HttpServletRequest request) {
		
		return new ModelAndView("com/chrhc/project/sc/message/right");
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
	public void datagrid(ScMessageEntity scMessage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScMessageEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scMessage, request.getParameterMap());
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
	 * 删除消息
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScMessageEntity scMessage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scMessage = systemService.getEntity(ScMessageEntity.class, scMessage.getId());
		
		
		message = "消息删除成功";
		try{
			systemService.deleteLogic(scMessage);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "消息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除消息
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "消息删除成功";
		try{
			for(String id:ids.split(",")){
				ScMessageEntity scMessage = systemService.getEntity(ScMessageEntity.class, id);
				//删除消息列表中数据时，同时删除消息记录表中的数据
				String msgId = scMessage.getId();
				List<ScMsgRecordEntity> records = systemService.findHql("from ScMsgRecordEntity where messageId = ?", msgId);
				for(int i=0;i<records.size();i++){
					ScMsgRecordEntity record = records.get(i);
					systemService.delete(record);
				}
				systemService.deleteLogic(scMessage);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "消息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加消息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScMessageEntity scMessage, HttpServletRequest request,HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		message = "消息添加成功";
		TSUser tsUser = ResourceUtil.getSessionUserName();
		try{
			scMessage.setPublisher(tsUser.getId());
			String title = request.getParameter("title").trim();
			String modelName = request.getParameter("modelName");
			scMessage.setModelType(modelName);
			scMessage.setTitle(title);
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date date = sdf.parse(sdf.format(new Date()));
//			scMessage.setPTime(date);
			scMessageService.saveMessage(scMessage);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
//			request.getRequestDispatcher("scMessageController.do?goAdd").forward(request, response);
//			goAdd(scMessage, request);
		}catch(Exception e){
			e.printStackTrace();
			message = "消息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新消息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScMessageEntity scMessage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "消息更新成功";
		ScMessageEntity t = systemService.get(ScMessageEntity.class, scMessage.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(scMessage, t);
			systemService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
		   if(e instanceof InterceptorException){
				throw new InterceptorException("");
			}else{
				e.printStackTrace();
				message = "消息更新失败";
				throw new BusinessException(e.getMessage());
			}				
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 消息新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScMessageEntity scMessage, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scMessage.getId())) {
			scMessage = systemService.getEntity(ScMessageEntity.class, scMessage.getId());
			req.setAttribute("scMessagePage", scMessage);
		}
		return new ModelAndView("com/chrhc/project/sc/message/scMessage-add");
	}
	/**
	 * 消息编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScMessageEntity scMessage, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scMessage.getId())) {
			scMessage = systemService.getEntity(ScMessageEntity.class, scMessage.getId());
			req.setAttribute("scMessagePage", scMessage);
		}
		return new ModelAndView("com/chrhc/project/sc/message/scMessage-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/message/scMessageUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScMessageEntity scMessage,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScMessageEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scMessage, request.getParameterMap());
		List<ScMessageEntity> scMessages = this.systemService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"消息");
		modelMap.put(NormalExcelConstants.CLASS,ScMessageEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("消息列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scMessages);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScMessageEntity scMessage,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "消息");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScMessageEntity.class);
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
				List<ScMessageEntity> listScMessageEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScMessageEntity.class,params);
				for (ScMessageEntity scMessage : listScMessageEntitys) {
					systemService.save(scMessage);
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
