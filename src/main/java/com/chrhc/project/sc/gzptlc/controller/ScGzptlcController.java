package com.chrhc.project.sc.gzptlc.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TSUserOrg;
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
import com.chrhc.common.http.SmsHttpService;
import com.chrhc.project.sc.gzptlc.entity.ScGzptlcEntity;
import com.chrhc.project.sc.gzptlc.service.ScGzptlcServiceI;



/**   
 * @Title: Controller
 * @Description: 网上办事
 * @author onlineGenerator
 * @date 2016-04-10 14:05:04
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scGzptlcController")
public class ScGzptlcController extends BaseController {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScGzptlcController.class);

	@Autowired
	private ScGzptlcServiceI scGzptlcService;
	@Autowired
	private SystemService systemService;
	@Autowired
	SmsHttpService smsservice;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 网上办事列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scGzptlc")
	public ModelAndView scGzptlc(HttpServletRequest request) {
		ModelAndView mv =	new ModelAndView("com/chrhc/project/sc/gzptlc/scGzptlcList");
		String idcard=request.getParameter("idcard");
		request.getSession().setAttribute("idcard", idcard);
		return mv;
	}
	/**
	 * 网上办事列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scGzptlc_end")
	public ModelAndView scGzptlcend(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/gzptlc/scGzptlcListend");
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
	public void datagrid(ScGzptlcEntity scGzptlc,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScGzptlcEntity.class, dataGrid);
		//查询条件组装器
		//org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scGzptlc, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		//cq.add();
		//this.scGzptlcService.getDataGridReturn(cq, true);
		//TagUtil.datagrid(response, dataGrid);
		
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		String name= request.getParameter("declare_per_name");
		System.out.println("%%%%%%%%%%%%%"+name+"_________________");
		//url="http://10.61.160.7:8280/cpub-back-web/api/app/netservice/declare/tasklist";
		/*ApplicationContext context = ApplicationContextUtil.getContext();
		String sql ="select * from testa";
		List<Map<String,Object>> baseList = jdbcTemplateA.queryForList(sql);*/
		//mv.addObject("list",baseList);
		//getdataByhttp("");
		String url= scGzptlc.getUrl();
		JSONObject object =updataByhttp(url,"",dataGrid,scGzptlc.getDeclare_per_name(),scGzptlc.getDeclare_id_num());
		try {
			PrintWriter pw=response.getWriter();
			if(object==null||object.isEmpty()){
				pw.write("{\"total\":0,\"rows\":[]}");
				return;
			}
				pw.write(object.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 删除网上办事
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScGzptlcEntity scGzptlc, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scGzptlc = systemService.getEntity(ScGzptlcEntity.class, scGzptlc.getId());
		message = "网上办事删除成功";
		try{
			scGzptlcService.deleteLogic(scGzptlc);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "网上办事删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除网上办事
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "网上办事删除成功";
		try{
			for(String id:ids.split(",")){
				ScGzptlcEntity scGzptlc = systemService.getEntity(ScGzptlcEntity.class, 
				id
				);
				scGzptlcService.deleteLogic(scGzptlc);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "网上办事删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加网上办事
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScGzptlcEntity scGzptlc, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "网上办事添加成功";
		try{
			scGzptlcService.save(scGzptlc);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "网上办事添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新网上办事
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScGzptlcEntity scGzptlc, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "网上办事审批成功";
		String flag =scGzptlc.getDepartment_approve_state();
		if("1".equals(flag)){
			j.setObj("审批通过");
		}else{
			j.setObj("审批未通过");
		}
		
		//ScGzptlcEntity t = scGzptlcService.get(ScGzptlcEntity.class, scGzptlc.getId());
		try {
			tj(scGzptlc);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
		   if(e instanceof InterceptorException){
				throw new InterceptorException("");
			}else{
				e.printStackTrace();
				message = "网上办事审批失败";
				throw new BusinessException(e.getMessage());
			}				
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 网上办事新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScGzptlcEntity scGzptlc, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scGzptlc.getId())) {
			scGzptlc = scGzptlcService.getEntity(ScGzptlcEntity.class, scGzptlc.getId());
			req.setAttribute("scGzptlcPage", scGzptlc);
		}
		return new ModelAndView("com/chrhc/project/sc/gzptlc/scGzptlc-add");
	}
	/**
	 * 网上办事编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScGzptlcEntity scGzptlc, HttpServletRequest req,String url) {
		if (StringUtil.isNotEmpty(scGzptlc.getId())) {
			//scGzptlc = scGzptlcService.getEntity(ScGzptlcEntity.class, scGzptlc.getId());
			req.setAttribute("scGzptlcPage", scGzptlc);
			url=url+scGzptlc.getId();
			//url="http://10.61.160.7:8280/cpub-back-web/api/app/netservice/declare/get/67a79961d3654a77bc1375008fbe9e5d";
			System.out.println(url);
			JSONObject rejson = getinfoByhttp(url,scGzptlc);
			req.setAttribute("rejson", rejson);
			req.setAttribute("flag", req.getParameter("flag"));
		}
		
		
		
		
		
//		return new ModelAndView("com/chrhc/project/sc/gzptlc/scGzptlc-update");
		return new ModelAndView("com/chrhc/project/sc/gzptlc/scGzptupdate");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/gzptlc/scGzptlcUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScGzptlcEntity scGzptlc,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScGzptlcEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scGzptlc, request.getParameterMap());
		List<ScGzptlcEntity> scGzptlcs = this.scGzptlcService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"网上办事");
		modelMap.put(NormalExcelConstants.CLASS,ScGzptlcEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("网上办事列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,scGzptlcs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ScGzptlcEntity scGzptlc,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "网上办事");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,ScGzptlcEntity.class);
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
				List<ScGzptlcEntity> listScGzptlcEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),ScGzptlcEntity.class,params);
				for (ScGzptlcEntity scGzptlc : listScGzptlcEntitys) {
					scGzptlcService.save(scGzptlc);
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
	 * 获取审批列表信息
	 * @param url
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "getdatas")
	public   void  getgzptdatas(ScGzptlcEntity scGzptlc,HttpServletRequest request, HttpServletResponse response,DataGrid dataGrid){
		//ModelAndView mv  = new ModelAndView("com/chrhc/project/sc/gzpt/scGzptList");
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		String name= request.getParameter("declare_per_name");
		String idcard= request.getParameter("declare_id_num");
		String idcard_= (String) request.getSession().getAttribute("idcard");
		if(idcard_!=null&&idcard_.length()>5){
			idcard=idcard_;
			request.getSession().removeAttribute("idcard");
		}
		//new String(name.getBytes(),"utf-8");
		System.out.println("%%%%%%%%%%%%%"+name+"_________________");
		//url="http://10.61.160.7:8280/cpub-back-web/api/app/netservice/declare/tasklist";
		/*ApplicationContext context = ApplicationContextUtil.getContext();
		String sql ="select * from testa";
		List<Map<String,Object>> baseList = jdbcTemplateA.queryForList(sql);*/
		//mv.addObject("list",baseList);
		//getdataByhttp("");
		String url= scGzptlc.getUrl();
		if(name==null)name="";
		if(idcard==null)idcard="";
	
		JSONObject object =updataByhttp(url,"",dataGrid,name,idcard);
		
		
		try {
			PrintWriter pw=response.getWriter();
			if(object==null||object.isEmpty()){
				pw.write("{\"total\":0}");
				return;
			}
				pw.write(object.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*JSONArray jsonArray  = object.getJSONArray("rows");
		List listre = JSONArray.toList(jsonArray);
		dataGrid.setResults(listre);
		dataGrid.setRows(100);
		dataGrid.setTotal(105);
		dataGrid.setField("create_time,submit_time,declare_id_num,biz_type,declare_approve_state,biz_name,declare_phone,activity_code,declare_org_name,declare_per_name,instance_id,accept_code,id,declare_email");
		TagUtil.datagrid(response, dataGrid);*/
		//return mv;
	}
	/**
	 * httpclient 获取列表
	 * @param url
	 * @param flag 暂时未用
	 * @return
	 */
	private JSONObject  updataByhttp(String url,String flag,DataGrid dataGrid,String name,String idcard) {
		JSONObject rejson = new JSONObject();
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setAuthenticationPreemptive(true);
		// 创建POST方法的实例
		PostMethod postMethod = new PostMethod(url);
		// 使用系统提供的默认的恢复策略
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,	new DefaultHttpMethodRetryHandler());
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		//取得用户
		TSUser user = ResourceUtil.getSessionUserName();
		postMethod.addParameter("userLoginName", user.getUserName());
		//postMethod.addParameter("orgCode", user.getOrgCodes());
		//postMethod.addParameter("roles", user.getRoleName());
		//postMethod.addParameter("startTime", user.getOrgCodes());
		//postMethod.addParameter("endTime", user.getOrgCodes());
		postMethod.addParameter("page", dataGrid.getPage()+"");
		postMethod.addParameter("pagesize", dataGrid.getRows()+"");
		//String tname="葛莲莲";
		//String tnum ="372925198711141319";
		postMethod.addParameter("declareIdNum",idcard);
		postMethod.addParameter("declarePerName",name);
		/*if(false){
			postMethod.addParameter("declareIdNum","");
		}
		if(false){
			postMethod.addParameter("declarePerName","");
		}*/
		String result = null;// 初始化返回结果（String类型）
		byte[] responseBody = null;// 初始化返回结果（byte[]类型）
		//boolean  responseBody = null;// 初始化返回结果（byte[]类型）
		// 执行getMethod
		int statusCode=0;
		try {
			statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "+ postMethod.getStatusLine());
				return rejson;
			}
			// 返回结果（byte[]类型）
			responseBody = postMethod.getResponseBody();
			// 返回结果（String类型，GBK格式）
			result = new String(responseBody, "utf-8");
			JSONObject jObject = JSONObject.fromObject(result);
			JSONObject jObjectcontent=   jObject.getJSONObject("content");
			JSONArray jsonArray =  jObjectcontent.getJSONArray("content");
			int allsize = (int) jObjectcontent.get("totalElements");
			rejson.element("rows",jsonArray);
			rejson.element("total",allsize);
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			// 释放连接
			postMethod.releaseConnection();
		}
		return rejson;
	}
	/**
	 * httpclient 获取详细列表
	 * @param url
	 * @param flag 暂时未用
	 * @return
	 */
	private JSONObject getinfoByhttp(String url,ScGzptlcEntity scGzptlc) {
		JSONObject rejson=null;
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setAuthenticationPreemptive(true);
		// 创建POST方法的实例
		GetMethod getMethod = new GetMethod(url);
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		
		//取得用户
		//TSUser user = ResourceUtil.getSessionUserName();
		String result = null;// 初始化返回结果（String类型）
		byte[] responseBody = null;// 初始化返回结果（byte[]类型）
		//boolean  responseBody = null;// 初始化返回结果（byte[]类型）
		// 执行getMethod
		int statusCode=0;
		try {
			statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "+ getMethod.getStatusLine());
			}
			// 返回结果（byte[]类型）
			responseBody = getMethod.getResponseBody();
			// 返回结果（String类型，GBK格式）
			result = new String(responseBody, "utf-8");
			JSONObject jObject = JSONObject.fromObject(result);
			rejson=jObject;
			if(jObject==null||jObject.isEmpty()){
				return null;
			}
			//boolean  success=  jObject.getJSONObject("success");
			//List<Boolean> truelist =(List<Boolean>) success.values();
			
			JSONObject jObjectcontent=   jObject.getJSONObject("content");
			Object jsonobject = (Object) jObjectcontent.get("stufList");
			JSONArray stufjsonArray=null;
			
			if(jsonobject==null||jsonobject instanceof JSONObject){
				 stufjsonArray = new JSONArray();
			}else{
				 stufjsonArray =  jObjectcontent.getJSONArray("stufList");
			}
			if(!stufjsonArray.isEmpty()&&stufjsonArray.isArray()){
				
				List<Map<String,String> > stuflist = new ArrayList<Map<String,String>>();
				   for (int i = 0; i < stufjsonArray.size(); i++) {
					   Map<String,String> stufMap = new HashMap<String,String>();
			            JSONObject jo = (JSONObject) stufjsonArray.get(i);
			          
			            stufMap.put("stuffName",(String) jo.get("stuffName") );
			            int flag = (Integer) jo.get("stuffDelivery");
			            stufMap.put("stuffDelivery",flag+"" );
			            if(1==flag){
			            	 stufMap.put("attachmentRealPath",(String)jo.get("attachmentRealPath"));
			            }
			            stuflist.add(stufMap);
			        }
				   scGzptlc.setFj(stuflist);	
				
			}
			JSONObject taskjsonobj =  jObjectcontent.getJSONObject("task");
			if(taskjsonobj!=null&&!taskjsonobj.isNullObject()&&!taskjsonobj.isEmpty()){
				JSONObject flowjsonobj = taskjsonobj.getJSONObject("flow_3");
				if(flowjsonobj!=null&&!flowjsonobj.isNullObject()&&!flowjsonobj.isEmpty()){
					Object objval = flowjsonobj.get("taskResult");
					if(objval==null||objval instanceof JSONObject){
						objval="";
					}
					scGzptlc.setDepartment_approve_state(objval+"");
					
					Object taskrepval = flowjsonobj.get("taskReply");
					if(taskrepval==null||taskrepval instanceof JSONObject){
						taskrepval="";
					}
					scGzptlc.setSpyj(taskrepval+"");
				}
				
			}
		
			
			   JSONObject vojsonobj =  jObjectcontent.getJSONObject("vo");
			   if(vojsonobj!=null&&!vojsonobj.isEmpty()){
				   //姓名
					  String name =  (String) vojsonobj.get("declarePerName");
					  scGzptlc.setDeclare_per_name(name);
					   //证件号码
					  String IDcard =  (String) vojsonobj.get("declareIdNum");
					  scGzptlc.setDeclare_id_num(IDcard);
					  //电话号码
					  String declarePhone =  (String) vojsonobj.get("declarePhone");
					  scGzptlc.setDeclare_phone(declarePhone);
					  //性别 kong
					  Object  declarePerSex =  vojsonobj.get("declarePerSex");
					 
					  scGzptlc.setXb(declarePerSex+"");
					  //邮箱
					  String declareEmail =  (String) vojsonobj.get("declareEmail");
					  scGzptlc.setDeclare_email(declareEmail);
					  //地址
					  String declareAddress =  (String) vojsonobj.get("declareAddress");
					  scGzptlc.setAddress(declareAddress);
					  //区域名称
					  String declareAreaName =  (String) vojsonobj.get("declareAreaName");
					  scGzptlc.setSlqy(declareAreaName);
					  //部门名称
					  String declareOrgName =  (String) vojsonobj.get("declareOrgName");
					  scGzptlc.setDepart(declareOrgName);
					  //流程实例ID
					  String instanID =  (String) vojsonobj.get("instanceId");
					  scGzptlc.setInstanceID(instanID);  
					  //流程实例ID
					  String acpcod =  (String) vojsonobj.get("acceptCode");
					  scGzptlc.setAcceptcode(acpcod);  
				   
			   }
			  
			  JSONObject infojsonobj =  jObjectcontent.getJSONObject("info");
			  if(infojsonobj!=null&&!infojsonobj.isEmpty()){
				  //业务名称
				  String bizName =  (String) infojsonobj.get("bizName");
				  scGzptlc.setBiz_name(bizName);
				  //业务类型
				  String bizType =  (String) infojsonobj.get("bizType");
				  scGzptlc.setBiz_type(bizType);
			  }
			 
			  
			  
			//rejson = new JSONObject();
			//rejson.element("rows",stufjsonArray);
			//rejson.element("total",stufjsonArray.size());
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			// 释放连接
			getMethod.releaseConnection();
		}
		return rejson;
	}
	
	
	/**
	 * httpclient 提交审批
	 * @param url
	 * @param flag 暂时未用
	 * @return
	 */
	private boolean  tj(ScGzptlcEntity scGzptlc) {
		boolean re = true;
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setAuthenticationPreemptive(true);
		// 创建POST方法的实例
		PostMethod postMethod = new PostMethod(scGzptlc.getUrl());
		// 使用系统提供的默认的恢复策略
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,	new DefaultHttpMethodRetryHandler());
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		//取得用户
		TSUser user = ResourceUtil.getSessionUserName();
		postMethod.addParameter("userLoginName", user.getUserName());
		postMethod.addParameter("userName", user.getRealName());
		postMethod.addParameter("instanceId",scGzptlc.getInstanceID() );
		postMethod.addParameter("taskResult", scGzptlc.getDepartment_approve_state());
		postMethod.addParameter("taskReply", scGzptlc.getSpyj());
		
		String result = null;// 初始化返回结果（String类型）
		byte[] responseBody = null;// 初始化返回结果（byte[]类型）
		//boolean  responseBody = null;// 初始化返回结果（byte[]类型）
		// 执行getMethod
		int statusCode=0;
		try {
			statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "+ postMethod.getStatusLine());
				return false;
			}
			// 返回结果（byte[]类型）
			responseBody = postMethod.getResponseBody();
			// 返回结果（String类型，GBK格式）
			result = new String(responseBody, "utf-8");
			
			JSONObject jObject = JSONObject.fromObject(result);
			JSONObject jObjectcontent=   jObject.getJSONObject("content");
			boolean  rs =  (boolean) jObjectcontent.get("rs");
			re=rs;
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			// 释放连接
			postMethod.releaseConnection();
		}
		return re;
	}
	/***
	 * 根据条件查询是存在待处理的条数
	 * @param url
	 * @param name
	 * @param idcard
	 * @param pagesize
	 * @return
	 */
	@RequestMapping(params = "checkwsbs")
	@ResponseBody
	public String checkwsbs(String url,String name,String idcard,String pagesize){
	if(pagesize==null||pagesize.isEmpty()){
		pagesize="100";
	}
	JSONObject jsobj = getdataBynameandid(url,name,idcard,pagesize);
	int  total = (int) jsobj.get("total");
	if(total<1){
		
	}
	return total+"";
	}
	
	/**
	 * httpclient 获取列表 根据条件获取
	 * @param url
	 * @param flag 暂时未用
	 * @return
	 */
	private JSONObject  getdataBynameandid(String url,String name,String idcard,String pagesize) {
		JSONObject rejson = new JSONObject();
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setAuthenticationPreemptive(true);
		// 创建POST方法的实例
		PostMethod postMethod = new PostMethod(url);
		// 使用系统提供的默认的恢复策略
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
		new DefaultHttpMethodRetryHandler());
		//取得用户
		TSUser user = ResourceUtil.getSessionUserName();
		postMethod.addParameter("userLoginName", user.getUserName());
		//postMethod.addParameter("orgCode", user.getOrgCodes());
		//postMethod.addParameter("roles", user.getRoleName());
		//postMethod.addParameter("startTime", user.getOrgCodes());
		//postMethod.addParameter("endTime", user.getOrgCodes());
		//postMethod.addParameter("page", page);
		postMethod.addParameter("pagesize", pagesize);
		if(name==null){
			name="";
		}
		if(idcard==null)idcard="";
		postMethod.addParameter("declareIdNum",idcard);
		postMethod.addParameter("declarePerName",name);
		String result = null;// 初始化返回结果（String类型）
		byte[] responseBody = null;// 初始化返回结果（byte[]类型）
		//boolean  responseBody = null;// 初始化返回结果（byte[]类型）
		// 执行getMethod
		int statusCode=0;
		try {
			statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "+ postMethod.getStatusLine());
				return rejson;
			}
			// 返回结果（byte[]类型）
			responseBody = postMethod.getResponseBody();
			// 返回结果（String类型，GBK格式）
			result = new String(responseBody, "utf-8");
			JSONObject jObject = JSONObject.fromObject(result);
			JSONObject jObjectcontent=   jObject.getJSONObject("content");
			JSONArray jsonArray =  jObjectcontent.getJSONArray("content");
			int allsize = (int) jObjectcontent.get("totalElements");
			rejson.element("rows",jsonArray);
			rejson.element("total",allsize);
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			// 释放连接
			postMethod.releaseConnection();
		}
		return rejson;
	}
	/***
	 * 根据身份证号码与flag 取得待审列表的附件
	 * @param url 接口地址
	 * @param idcard 身份证
	 * @param flag   区分类型标识
	 * @return
	 */
	@RequestMapping(params = "fjzl")
	@ResponseBody
	public JSONArray getfjxx(String url,String idcard,String flag,String pagesize){
		JSONArray  jsonarray=null;
		JSONObject jsonlist = new JSONObject();
		JSONObject rejson = new JSONObject();
		jsonlist =getdataBynameandid(url, null, idcard, pagesize);
		if(jsonlist==null||jsonlist.isNullObject()||jsonlist.isEmpty()){
			return null;
		}
		String total=jsonlist.getString("total");
		Object rows=jsonlist.get("rows");
		JSONArray rowsjsonArray =null;
		if(total==null||total.equalsIgnoreCase("0")||rows==null||rows instanceof JSONObject){
			return null;
		}else{
			rowsjsonArray =  jsonlist.getJSONArray("rows");
		}
		String id="";
		for(int i=0;i<rowsjsonArray.size();i++){
			JSONObject jsonobjrow =rowsjsonArray.getJSONObject(i);
			String biznamerow="";
			if(jsonobjrow==null||jsonobjrow.isNullObject()){
				continue;
			}
			biznamerow = jsonobjrow.getString("biz_name");
			if(biznamerow!=null&&biznamerow.equals(flag)){
				id=jsonobjrow.getString("id");
				break;
			}
		}
		//url=url+scGzptlc.getId();
		if(id!=null&&!"".equals(id)){
		String urlinfo="http://10.61.160.7:8280/cpub-back-web/api/app/netservice/declare/get/"+id;
		  jsonarray = getfjinfo(urlinfo);
		}
		return jsonarray;
	}
	
	/***
	 * 发送消息
	 * @param msg  消息
	 * @param telnum 号码
	 * @return
	 */
	@RequestMapping(params = "sendmsg")
	@ResponseBody
	public String  sendmsg(String msg ,String telnum){
		TSUser user = ResourceUtil.getSessionUserName();
		String query = "select d.org_code from t_s_base_user u join t_s_depart d ON u.departid=d.id where u.username= '"+user.getUserName()+"'"; 
		List<String > orgcodelist = systemService.findListbySql(query);
		String orgcode="";
		if(orgcodelist!=null&&orgcodelist.size()>0){
			orgcode =orgcodelist.get(0);
		}else{
			return "0";
		}
		try{
		smsservice.SendMessage(orgcode, telnum, msg, null, null, null);
		}catch(Exception e){
			e.printStackTrace();
			return "0";
		}
		return "1";
	}
	/**
	 * 获取附件资料
	 * @param url
	 * @return
	 */
	private JSONArray getfjinfo(String url) {
		JSONArray stufjsonArray=null;
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setAuthenticationPreemptive(true);
		// 创建POST方法的实例
		GetMethod getMethod = new GetMethod(url);
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		
		//取得用户
		//TSUser user = ResourceUtil.getSessionUserName();
		String result = null;// 初始化返回结果（String类型）
		byte[] responseBody = null;// 初始化返回结果（byte[]类型）
		//boolean  responseBody = null;// 初始化返回结果（byte[]类型）
		// 执行getMethod
		int statusCode=0;
		try {
			statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "+ getMethod.getStatusLine());
			}
			// 返回结果（byte[]类型）
			responseBody = getMethod.getResponseBody();
			// 返回结果（String类型，GBK格式）
			result = new String(responseBody, "utf-8");
			JSONObject jObject = JSONObject.fromObject(result);
			
			if(jObject==null||jObject.isEmpty()){
				return null;
			}
			//boolean  success=  jObject.getJSONObject("success");
			//List<Boolean> truelist =(List<Boolean>) success.values();
			
			JSONObject jObjectcontent=   jObject.getJSONObject("content");
			Object jsonobject = (Object) jObjectcontent.get("stufList");
			
			
			if(jsonobject==null||jsonobject instanceof JSONObject){
				 stufjsonArray = new JSONArray();
			}else{
				 stufjsonArray =  jObjectcontent.getJSONArray("stufList");
			}
			/*if(!stufjsonArray.isEmpty()&&stufjsonArray.isArray()){
				
				List<Map<String,String> > stuflist = new ArrayList<Map<String,String>>();
				   for (int i = 0; i < stufjsonArray.size(); i++) {
					   Map<String,String> stufMap = new HashMap<String,String>();
			            JSONObject jo = (JSONObject) stufjsonArray.get(i);
			          
			            stufMap.put("stuffName",(String) jo.get("stuffName") );
			            int flag = (Integer) jo.get("stuffDelivery");
			            stufMap.put("stuffDelivery",flag+"" );
			            if(1==flag){
			            	 stufMap.put("attachmentRealPath",(String)jo.get("attachmentRealPath"));
			            }
			            stuflist.add(stufMap);
			        }
				  // scGzptlc.setFj(stuflist);	
				
			}*/
			
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			// 释放连接
			getMethod.releaseConnection();
		}
		return stufjsonArray;
	}
}
