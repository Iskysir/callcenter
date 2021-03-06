package com.chrhc.frameworkweb.cgform.controller.build;

import com.chrhc.framework.cgfrom.engine.ChrhcFormInterceptor;
import com.chrhc.framework.cgfrom.engine.ChrhcTemplatContext;
import com.chrhc.framework.cgfrom.engine.RequestUtil;
import com.chrhc.project.hl.sms.IBaseService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.SeriNumGenerateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.common.CgAutoListConstant;
import org.jeecgframework.web.cgform.common.CommUtils;
import org.jeecgframework.web.cgform.entity.config.CgFormHeadEntity;
import org.jeecgframework.web.cgform.entity.upload.CgUploadEntity;
import org.jeecgframework.web.cgform.exception.BusinessException;
import org.jeecgframework.web.cgform.service.build.DataBaseService;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.jeecgframework.web.cgreport.common.CgReportConstant;
import org.jeecgframework.web.cgreport.exception.CgReportNotFoundException;
import org.jeecgframework.web.cgreport.service.core.CgReportServiceI;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.Map.Entry;

/**
 * @ClassName: formBuildController
 * @Description: 读取模板生成填报表单（添加、修改）-执行表单数据添加和修改操作
 * @author 周俊峰
 */
@Scope("prototype")
@Controller
@RequestMapping("/chrhcFormBuildController")
public class ChrhcFormBuildController extends BaseController {
	private static final Logger logger = Logger
			.getLogger(ChrhcFormBuildController.class);
	private String message;

	@Autowired
	private ChrhcTemplatContext templetContext;
	@Autowired
	private DataBaseService dataBaseService;
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private CgReportServiceI cgReportService;
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private IBaseService baseService;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * form表单页面跳转
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "ftlForm")
	public void ftlForm(HttpServletRequest request, HttpServletResponse response) {
		try {
			long start = System.currentTimeMillis();
			String tableName = request.getParameter("tableName");
			// update-start--Author:zhangguoming Date:20140922
			// for：根据ftlVersion动态读取模板
			String ftlVersion = request.getParameter("ftlVersion");

			String templet = request.getParameter("ftl");
			String templet_one_many = request.getParameter("ftlm");

			Template template = templetContext.getTemplate(tableName,
					ftlVersion, templet, templet_one_many);
			// update-end--Author:zhangguoming Date:20140922
			// for：根据ftlVersion动态读取模板
			StringWriter stringWriter = new StringWriter();
			BufferedWriter writer = new BufferedWriter(stringWriter);
			Map<String, Object> data = new HashMap<String, Object>();
			String id = request.getParameter("id");
			// 获取版本号
			String version = cgFormFieldService
					.getCgFormVersionByTableName(tableName);
			// 装载表单配置
			Map configData = cgFormFieldService.getFtlFormConfig(tableName,
					version);
			data = new HashMap(configData);

			// zxy 添加所有request变量 begin 2015年4月27日13:35:27
			RequestUtil.setAllRequestParam(data, request);
			// zxy 添加所有request变量 end 2015年4月27日13:35:32

			// 如果该表是主表查出关联的附表
			CgFormHeadEntity head = (CgFormHeadEntity) data.get("head");
			Map<String, Object> dataForm = new HashMap<String, Object>();

			// zxy 添加所有request变量 begin 2015年5月15日13:35:27
			RequestUtil.setAllRequestParam(dataForm, request);
			// zxy 添加所有request变量 end 2015年5月15日13:35:32

			if (StringUtils.isNotEmpty(id)) {
				dataForm = dataBaseService.findOneForJdbc(head.getTableName(), id);
				// zxy 处理popup相关数据一致性 2015年6月25日17:57:08
				List popupList = (ArrayList) configData.get("popupList");
				dealPopupData(data,popupList, dataForm);
				// zxy 处理popup相关数据一致性2015年6月25日17:57:12
				
				//zxy 统一处理时间 date datetime类型 2015年7月8日18:23:35
				List dateList = (ArrayList) configData.get("dateList");
				dealDateData(dateList,dataForm);
				//zxy 统一处理时间 date datetime类型 2015年7月8日18:24:27
			} else {
				// zxy 处理popup相关数据一致性 2015年6月25日17:57:08
				List popupList = (ArrayList) configData.get("popupList");
				dealPopupData(data,popupList, dataForm);
				// zxy 处理popup相关数据一致性2015年6月25日17:57:12
				
				//zxy 统一处理时间 date datetime类型 2015年7月8日18:23:35
				List dateList = (ArrayList) configData.get("dateList");
				dealDateData(dateList,dataForm);
				//zxy 统一处理时间 date datetime类型 2015年7月8日18:24:27
				
				// 新建给编号赋值
				if (StringUtil.isNotEmpty(head.getIdkey())) {
					String[] idkeyArr = head.getIdkey().split(";");
					String codeValue = SeriNumGenerateUtils.getId(idkeyArr[0]);
					if (idkeyArr.length > 1) {
						dataForm.put(idkeyArr[1], codeValue);
					} else {
						dataForm.put("code", codeValue);
					}
				}
			}

			Iterator it = dataForm.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String ok = (String) entry.getKey();
				Object ov = entry.getValue();
				data.put(ok, ov);
			}
			Map<String, Object> tableData = new HashMap<String, Object>();
			// 获取主表或单表表单数据
			tableData.put(tableName, dataForm);
			// 获取附表表表单数据
			if (StringUtils.isNotEmpty(id)) {
				if (head.getJformType() == CgAutoListConstant.JFORM_TYPE_MAIN_TALBE) {
					String subTableStr = head.getSubTableStr();
					String subCode=head.getSubCode();
					if (StringUtils.isNotEmpty(subTableStr)) {
						String[] subTables = subTableStr.split(",");
						String[] subCodes=subCode.split(",");
						List<Map<String, Object>> subTableData = new ArrayList<Map<String, Object>>();
						for(int i=0;i<subTables.length;i++)
						{
							String subTable=subTables[i];
							subTableData = cgFormFieldService.getSubTableData(
									head.getTableName(), subTable, id);
							tableData.put(subCodes[i], subTableData);
						}
						/*for (String subTable : subTables) {
							subTableData = cgFormFieldService.getSubTableData(
									head.getTableName(), subTable, id);
							tableData.put(subTable, subTableData);
						}*/
					}
				}
			}
			// 装载单表/(主表和附表)表单数据
			data.put("data", tableData);
			data.put("id", id);
			// 装载附件信息数据
			pushFiles(data, id);
			template.process(data, writer);
			String content = stringWriter.toString();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(content);
			long end = System.currentTimeMillis();
			logger.debug("自定义表单生成耗时：" + (end - start) + " ms");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 如果表单带有附件，则查询出来传递到页面
	 * 
	 * @param data
	 *            传往页面的数据容器
	 * @param id
	 *            表单主键，用户查找附件数据
	 */
	private void pushFiles(Map<String, Object> data, String id) {
		List<CgUploadEntity> uploadBeans = cgFormFieldService.findByProperty(
				CgUploadEntity.class, "cgformId", id);
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>(0);
		for (CgUploadEntity b : uploadBeans) {
			String title = b.getAttachmenttitle();// 附件名
			String fileKey = b.getId();// 附件主键
			String path = b.getRealpath();// 附件路径
			String field = b.getCgformField();// 表单中作为附件控件的字段
			Map<String, Object> file = new HashMap<String, Object>();
			file.put("title", title);
			file.put("fileKey", fileKey);
			file.put("path", path);
			file.put("field", field == null ? "" : field);
			files.add(file);
		}
		data.put("filesList", files);
	}
	
	
	private void dealDateData(List<Map<String, Object>> dateList,
			Map<String, Object> dataForm)
	{
		for (Map<String, Object> fieldMap : dateList) {
			String fieldName=(String)fieldMap.get("field_name");
			Object value=dataForm.get(fieldName);
			if(value!=null)
			{
				String dateStr=value.toString();
				value=DateUtils.datatimeFormatCanNoTime(dateStr);
			}
			dataForm.put(fieldName, value);
		}
	}
	

	/**
	 * 处理popup 数据,以第一列作为唯一标示 按照对应关系取数据赋值
	 * 
	 * @param popupList
	 *            popup idcardreader 弹出字段
	 * @param dataForm
	 *            数据
	 */
	private void dealPopupData(Map<String, Object> data,List<Map<String, Object>> popupList,
			Map<String, Object> dataForm) {
		/*获取所有需要处理的diabled readonly 字段 zxy 2015年8月11日*/
		StringBuilder needDealFields=new StringBuilder();
		List<Map<String, Object>> columnList=(List<Map<String, Object>>)data.get("columns");
		for(Map<String,Object> map:columnList)
		{
			String validateAttr=map.get("validate_attr")!=null?((String)map.get("validate_attr")).toLowerCase():"";
			String showType = map.get("show_type")!=null?(String)map.get("show_type"):"";
			String fieldName=map.get("field_name")!=null?(String)map.get("field_name"):"";
			if(validateAttr.indexOf("disabled")>-1||validateAttr.indexOf("readonly")>-1 || "popup".equals(showType))
			{
				needDealFields.append(fieldName);
				needDealFields.append(",");
			}
		}
		/*获取所有需要处理的diabled readonly 字段 zxy 2015年8月11日*/
		for (Map<String, Object> fieldMap : popupList) {
			String dictTable = (String) fieldMap.get("dict_Table");
			if(StringUtil.isEmpty(dictTable))
			{
				dictTable="idcard";
			}
			String validateAttr = (String) fieldMap.get("popup_Attr");
			String fieldName = (String) fieldMap.get("field_Name");
			String gisAttr = (String) fieldMap.get("gis_Attr");
			if (StringUtil.isNotEmpty(validateAttr)) {
				String[] validateCfg = validateAttr.split(";");
				String popGisCfn = "";
				if (validateCfg.length == 1) {
					if (!validateCfg[0].contains("validType")) {
						popGisCfn = validateCfg[0];
					}
				} else {
					popGisCfn = validateCfg[1];
				}
				if (StringUtil.isNotEmpty(popGisCfn)) {
					String[] fields = popGisCfn.split(",");
					String idField = fields[0];
					StringBuffer srcSqlFields = new StringBuffer();
					// StringBuffer destSqlFields=new StringBuffer();
					Map<String, String> pairsMap = new HashMap<String, String>();
					for (String Onefield : fields) {
						String[] pairs = Onefield.split("=");
						pairsMap.put(pairs[1], pairs[0]);
						srcSqlFields.append(pairs[1]);
						srcSqlFields.append(",");
					}
					String[] idFieldPair = idField.split("=");
					//step.1 根据id获取该动态报表的配置参数
					Map<String, Object>  cgReportMap = null;
					try{
						cgReportMap = cgReportService.queryCgReportConfig(dictTable);
						if(cgReportMap.size()<=0){
							throw new CgReportNotFoundException("动态报表配置不存在!");
						}
					}catch (Exception e) {
						throw new CgReportNotFoundException("查找动态报表配置失败!"+e.getMessage());
					}
					//step.2 获取该配置的查询SQL
					Map configM = (Map) cgReportMap.get(CgReportConstant.MAIN);
					String querySql = (String) configM.get(CgReportConstant.CONFIG_SQL);
					//动态报表sql处理，跨社区查询问题开始
					
					//querySql = querySql.replaceAll("(?i)sys_org_code\\s+like\\s+'\\#\\{sys_org_code\\}%'", "sys_org_code like '%'");
					
					//动态报表sql处理，跨社区查询问题结束
					Map queryparams =  new LinkedHashMap<String,Object>();
					String isValue = String.valueOf(dataForm.get(idFieldPair[0]));
					/**所属社区表单显示处理**/
					TSUser user = ResourceUtil.getSessionUserName();
					String sys_org_code = user.getCurrentDepart().getOrgCode();
					String orgLength = ResourceUtil.getConfigByName("sqSysOrgCodeLength");
					if("sys_org_code".equals(idFieldPair[0]) && String.valueOf(sys_org_code.length()).equals(orgLength)){
						if(StringUtils.isEmpty(isValue) || "null".equals(isValue)){
							isValue = sys_org_code;
							dataForm.put(idFieldPair[0], isValue);
						}
						
					}
					/**所属社区表单显示处理**/
					if(StringUtil.isNotEmpty(isValue)){
						if(StringUtil.isNotEmpty(gisAttr)){
							JSONObject jsonObject = JSONObject.fromObject(gisAttr);
							Map map = new HashMap();
							for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
								String key = (String) iter.next();
								map.put(key, jsonObject.get(key));
							}
							if(map.containsKey("singleSelect")){
								String flag = String.valueOf(map.get("singleSelect"));
								if("false".equals(flag)){
									String sqlvalue = isValue.replaceAll(",", "\',\'");
									queryparams.put(idFieldPair[1], CgReportConstant.OP_IN+"('"+sqlvalue+"')");
								}else{
									queryparams.put(idFieldPair[1], CgReportConstant.OP_EQ+"'"+isValue+"'");
								}
							}else{
								queryparams.put(idFieldPair[1], CgReportConstant.OP_EQ+"'"+isValue+"'");
							}
						}else{
							queryparams.put(idFieldPair[1], CgReportConstant.OP_EQ+"'"+isValue+"'");
						}


						//step.4 进行查询返回结果
						int p = 1;
						int r = 1;
						List<Map<String, Object>> result= cgReportService.queryByCgReportSql(querySql, queryparams, p, r);
						//logger.info("querySql="+querySql);
						//logger.info("queryparams="+queryparams);
						//logger.info("result="+result);
						if (result.size() > 0) {
							Map<String, Object> oneRow = result.get(0);
							Iterator<Entry<String, Object>> entrys = oneRow
									.entrySet().iterator();
							//logger.info("entrys="+entrys);
							while (entrys.hasNext()) {
								Entry<String, Object> entry = entrys.next();
								String key = pairsMap.get(entry.getKey().toLowerCase());
								//logger.info("entry="+entry);
								//logger.info("pairsMap="+pairsMap);
								//logger.info("key="+key);
								if(StringUtil.isNotEmpty(key))
								{
									if(needDealFields.indexOf(key)>-1)
										dataForm.put(key, entry.getValue());
								}
							}
						}else{
								//没有数据时将值值为空
							for (Map.Entry<String, String> entry : pairsMap.entrySet()) {								  
								   String key = entry.getValue();
								   dataForm.put(key, "");
							}
						}
						//logger.info("dataForm="+dataForm);
					}
				}
			}
		}
	}

	/**
	 * 保存或更新
	 * 
	 * @param
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "saveOrUpdate")
	@ResponseBody
	public AjaxJson saveOrUpdate(HttpServletRequest request) throws Exception {
		request.setAttribute("jdbcTemplate",jdbcTemplate);
		request.setAttribute("baseService",baseService);
		AjaxJson j = new AjaxJson();
		Map data = request.getParameterMap();
		if (data != null) {

			data = CommUtils.mapConvert(data);
			// ConvertUtil.addCenterxy(data);
			String tableName = (String) data.get("tableName");
			String id = (String) data.get("id");
			// 打印测试
			Iterator it = data.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				Object ok = entry.getKey();
				Object ov = entry.getValue() == null ? "" : entry.getValue();
				logger.debug("name:" + ok.toString() + ";value:"
						+ ov.toString());
			}
			
			ChrhcFormInterceptor interceptor = cgFormFieldService.getInterceptorByTableName(tableName);
			//移动端离线判断
			String offline = request.getParameter("offline");
			if (StringUtils.isEmpty(id)) {
				// 消除不是表的字段
				String[] filterName = { "tableName", "saveOrUpdate" };
				data = CommUtils.attributeMapFilter(data, filterName);
				// 保存数据库
				try {
					Object pkValue = null;
					pkValue = dataBaseService.getPkValue(tableName);
					data.put("id", pkValue);
					
			    	if(interceptor != null){
			    		interceptor.beforeAdd(data, request);
			    	}
			    	
					int num = dataBaseService.insertTable(tableName, data);
					
					if(interceptor != null){
						interceptor.afterAdd(data, request);
			    	}
					
					if (num > 0) {
						j.setSuccess(true);
						message = "业务提交成功";
					} else {
						j.setSuccess(false);
						message = "业务提交失败";
					}
				} catch (Exception e) {
					e.printStackTrace();
					j.setSuccess(false);
					message = e.getMessage();
				}
			} else if(StringUtil.isNotEmpty(offline)){
				
				if("true".equals(offline)){
					String offlineId = request.getParameter("id");
					if(StringUtil.isNotEmpty(offlineId)){
						// 消除不是表的字段
						String[] filterName = { "tableName", "saveOrUpdate" };
						data = CommUtils.attributeMapFilter(data, filterName);
						// 保存数据库
						try {
							//Object pkValue = null;
							//pkValue = dataBaseService.getPkValue(tableName);
							data.put("id", offlineId);
							
					    	if(interceptor != null){
					    		interceptor.beforeAdd(data, request);
					    	}
					    	
							int num = dataBaseService.insertTable(tableName, data);
							
							if(interceptor != null){
								interceptor.afterAdd(data, request);
					    	}
							
							if (num > 0) {
								j.setSuccess(true);
								message = "业务提交成功";
							} else {
								j.setSuccess(false);
								message = "业务提交失败";
							}
						} catch (Exception e) {
							e.printStackTrace();
							j.setSuccess(false);
							message = e.getMessage();
						}
					}
					
				}
				
			
			}else {
				// 消除不是表的字段
				String[] filterName = { "tableName", "saveOrUpdate", "id" };
				data = CommUtils.attributeMapFilter(data, filterName);
				// 更新数据库
				try {

			    	if(interceptor != null){
			    		interceptor.beforeUpdate(data, request);
			    	}
			    	
					int num = dataBaseService.updateTable(tableName, id, data);
					
					if(interceptor != null){
			    		interceptor.afterUpdate(data, request);
			    	}
					
					if (num > 0) {
						j.setSuccess(true);
						message = "业务更新成功";
					} else {
						if (num == -9999) {
							j.setSuccess(false);
							message = "该条信息已经被修改，请关闭后重新打开";
						} else {
							j.setSuccess(false);
							message = "业务更新失败";
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					j.setSuccess(false);
					message = e.getMessage();
				}
			}
		}
		j.setMsg(message);
		j.setObj(data);
		return j;
	}

	/**
	 * 保存或更新
	 * 
	 * @param
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "saveOrUpdateMore")
	@ResponseBody
	public AjaxJson saveOrUpdateMore(HttpServletRequest request)
			throws Exception {
		AjaxJson j = new AjaxJson();
		Map data = request.getParameterMap();
		if (data != null) {
			data = CommUtils.mapConvert(data);
			// ConvertUtil.addCenterxy(data);
			String tableName = (String) data.get("tableName");
			CgFormHeadEntity head=this.cgFormFieldService.getCgFormHeadByCode(tableName);
			//tableName=head.getTableName();
			String id = (String) data.get("id");
			// 打印测试
			Iterator it = data.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				Object ok = entry.getKey();
				Object ov = entry.getValue() == null ? "" : entry.getValue();
				logger.debug("name:" + ok.toString() + ";value:"
						+ ov.toString());
			}
			Map<String, List<Map<String, Object>>> mapMore = CommUtils
					.mapConvertMore(data, tableName);
			
			ChrhcFormInterceptor interceptor = cgFormFieldService.getInterceptorByTableName(tableName);
			
			if (StringUtils.isEmpty(id)) {
				logger.info("一对多添加!!!!!");
				try {
					
			    	if(interceptor != null){
			    		interceptor.beforeAdd(data, request);
			    	}
			    	
					Map<String, Object> result = dataBaseService .insertTableMore(mapMore, tableName);
					
					if(interceptor != null){
						interceptor.afterAdd(data, request);
			    	}
					
					data.put("id", result.get("id"));
					j.setSuccess(true);
					message = "业务提交成功";
				} catch (BusinessException e) {
					e.printStackTrace();
					j.setSuccess(false);
					message = e.getMessage();
				}

			} else {
				logger.info("一对多修改!!!!!");
				try {
			    	if(interceptor != null){
			    		interceptor.beforeUpdate(data, request);
			    	}
					
					dataBaseService.updateTableMore(mapMore, tableName);
					
					if(interceptor != null){
			    		interceptor.afterUpdate(data, request);
			    	}
					j.setSuccess(true);
					message = "业务更新成功";
				} catch (BusinessException e) {
					e.printStackTrace();
					j.setSuccess(false);
					message = e.getMessage();
				}
			}
		}
		j.setMsg(message);
		j.setObj(data);
		return j;
	}

	/**
	 * 自定义按钮（触发对应的后台方法）
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "doButton")
	@ResponseBody
	public AjaxJson doButton(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			String formId = request.getParameter("formId");
			String buttonCode = request.getParameter("buttonCode");
			String tableName = request.getParameter("tableName");
			String id = request.getParameter("id");
			Map<String, Object> data = dataBaseService.findOneForJdbc(
					tableName, id);
			if (data != null) {
				// 打印测试
				Iterator it = data.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry entry = (Map.Entry) it.next();
					Object ok = entry.getKey();
					Object ov = entry.getValue() == null ? "" : entry
							.getValue();
					logger.debug("name:" + ok.toString() + ";value:"
							+ ov.toString());
				}
				data = CommUtils.mapConvert(data);
				dataBaseService.executeSqlExtend(formId, buttonCode, data);
			}
			j.setSuccess(true);
			message = "操作成功";
		} catch (Exception e) {
			e.printStackTrace();
			message = "操作失败";
		}
		j.setMsg(message);
		return j;
	}

}
