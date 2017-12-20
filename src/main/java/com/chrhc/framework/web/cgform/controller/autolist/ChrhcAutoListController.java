package com.chrhc.framework.web.cgform.controller.autolist;

import com.chrhc.framework.cgfrom.engine.ChrhcFormInterceptor;
import com.chrhc.framework.cgfrom.engine.ChrhcFreemarkerHelper;
import com.chrhc.framework.cgfrom.engine.RequestUtil;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.extend.hqlsearch.SysContextSqlConvert;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.cgform.common.CgAutoListConstant;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;
import org.jeecgframework.web.cgform.entity.config.CgFormHeadEntity;
import org.jeecgframework.web.cgform.entity.upload.CgUploadEntity;
import org.jeecgframework.web.cgform.service.autolist.CgTableServiceI;
import org.jeecgframework.web.cgform.service.autolist.ConfigServiceI;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.jeecgframework.web.cgform.util.QueryParamUtil;
import org.jeecgframework.web.cgreport.common.CgReportConstant;
import org.jeecgframework.web.cgreport.exception.CgReportNotFoundException;
import org.jeecgframework.web.cgreport.service.core.CgReportServiceI;
import org.jeecgframework.web.system.pojo.base.DictEntity;
import org.jeecgframework.web.system.pojo.base.TSOperation;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * @Title:CgAutoListController
 * @description:动态列表控制器[根据表名读取配置文件，进行动态数据展现]
 * @author 赵俊夫
 * @date Jul 5, 2013 2:55:36 PM
 * @version V1.0
 */
@Controller
@RequestMapping("/chrhcAutoListController")
public class ChrhcAutoListController extends BaseController{
	@Autowired
	private ConfigServiceI configService;
	@Autowired
	private CgTableServiceI cgTableService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	@Autowired
	private CgReportServiceI cgReportService;
	private static Logger log = Logger.getLogger(ChrhcAutoListController.class);
	//private Map<String, Map<String, Object>> cgmap = new HashMap<String, Map<String,Object>>();
	//private Map<String, List<Map<String, Object>>> rscgmap = new HashMap<String, List<Map<String, Object>>>();
	/**
	 * 动态列表展现入口
	 * @param id 动态配置ID
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(params = "list")
	public void list(String id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		long start = System.currentTimeMillis();
		//step.1 根据表名获取该表单的配置参数
		
		//zxy 版本号没有用，不再读取
		//String jversion = cgFormFieldService.getCgFormVersionByTableName(id);
		String jversion="";
		Map<String, Object> configs = null;
		
		//zxy 修改2015年7月8日
		/*String code=request.getParameter("code");
		if(StringUtil.isNotEmpty(code))
		{
			configs =configService.queryConfigsByCode(code, jversion);
		}
		else
		{
			configs =configService.queryConfigs(id,jversion);
		}*/
		configs =configService.queryConfigsByCode(id, jversion);
		//step.2 获取列表ftl模板路径
		ChrhcFreemarkerHelper viewEngine = new ChrhcFreemarkerHelper();
		Map<String, Object> paras = new HashMap<String, Object>();
		//step.3 封装页面数据
		loadVars(configs,paras,request);
		//step.4 组合模板+数据参数，进行页面展现
		//得到頁面的定製模板
		String ftltemplate = "";
		
		String typeValue=request.getParameter("bizType");
		if(StringUtil.isNotEmpty(typeValue)){
			//paras.put("bizTypeText", new String(request.getParameter("ptitle").getBytes("iso8859-1"),"utf-8"));
			TSType tsType = systemService.getType(typeValue, null, null);
			if(tsType != null){
				paras.put("bizTypeText", tsType.getTypename());
			}
			
		}
		
		
		if(StringUtil.isNotEmpty(request.getParameter("ftl"))){
			ftltemplate = request.getParameter("ftl").toString();
		} else {
			ftltemplate = "baseautolist";
		}
		Object initQuery=paras.get("initquery");
		//放入工程根路径
		paras.put("basePath", request.getContextPath()); 
		String html = viewEngine.parseTemplate(request.getSession().getServletContext(), ftltemplate + ".ftl", paras);
		try {
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "no-store");
			PrintWriter writer = response.getWriter();
			writer.println(html);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		log.debug("动态列表生成耗时："+(end-start)+" ms");
	}

	/**
	 * 动态列表数据查询
	 * @param configId 配置id 修正使用id会造成主键查询的冲突
	 * @param page 分页页面
	 * @param rows 分页大小
	 * @param request 
	 * @param response
	 * @param dataGrid
	 */
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "gisdatagrid")
	public void gisdatagrid(String configId,String page,String field,String rows,String sort,String order, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		this.datagrid(configId, page, field, rows, sort, order, request, response, dataGrid);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagrid2")
	public void datagrid2(String configId,String page,String field,String rows,String sort,String order, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		this.datagrid(configId, page, field, rows, sort, order, request, response, dataGrid);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagrid")
	public void datagrid(String configId,String page,String field,String rows,String sort,String order, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		Object dataRuleSql =JeecgDataAutorUtils.loadDataSearchConditonSQLString(); //request.getAttribute(Globals.MENU_DATA_AUTHOR_RULE_SQL);
		long start = System.currentTimeMillis();
		//step.1 获取动态配置
		//zxy jversion暂无用
		//String jversion = cgFormFieldService.getCgFormVersionByTableName(configId);
		String jversion = "";
		Map<String, Object>  configs = configService.queryConfigsByCode(configId,jversion);
		String table = (String) configs.get(CgAutoListConstant.TABLENAME);
		Map params =  new HashMap<String,Object>();
		//begin zxy 2015年5月29日16:15:51 添加对_gisQuery的处理实现or查询 begin
		dealGisQuery(request,configId,params);
		//end
		
		//step.2 获取查询条件以及值
		List<CgFormFieldEntity> beans = (List<CgFormFieldEntity>) configs.get(CgAutoListConstant.FILEDS);
		for(CgFormFieldEntity b:beans){
//			if(CgAutoListConstant.BOOL_TRUE.equals(b.getIsQuery())){
				QueryParamUtil.loadQueryParams(request,b,params);
//			}
		}
		int p = page==null?1:Integer.parseInt(page);
		int r = rows==null?99999:Integer.parseInt(rows);
		//step.3 进行查询返回结果
		
		//zxy 逻辑删除添加过滤数据
		params.put("delflag", "='0'");
		List<Map<String, Object>> result=cgTableService.querySingle(table, field.toString(), params,sort,order, p,r );
		
		log.info("sql查询耗时："+(System.currentTimeMillis()-start)+" ms");
		//处理页面中若存在checkbox只能显示code值而不能显示text值问题
		Map<String, Object> dicMap = new HashMap<String, Object>();
		for(CgFormFieldEntity b:beans){
			loadDic(dicMap, b);
			List<DictEntity> dicList = (List<DictEntity>)dicMap.get(CgAutoListConstant.FIELD_DICTLIST);
			if(dicList.size() > 0){
				for(Map<String, Object> resultMap:result){
					StringBuffer sb = new StringBuffer();
					String value = (String)resultMap.get(b.getFieldName());
					if(oConvertUtils.isEmpty(value)){continue;}
					String[] arrayVal = value.split(",");
					if(arrayVal.length > 1){
						for(String val:arrayVal){
							for(DictEntity dictEntity:dicList){
								if(val.equals(dictEntity.getTypecode())){
									sb.append(dictEntity.getTypename());
									sb.append(",");
								}
								
							}
						}
						resultMap.put(b.getFieldName()+"__codevalue", value);
						resultMap.put(b.getFieldName(), sb.toString().substring(0, sb.toString().length()-1));
					}
					else
					{
						/*for(DictEntity dictEntity:dicList){
							if(value.equals(dictEntity.getTypecode())){
								sb.append(dictEntity.getTypename());
								//sb.append(",");
							}
							
						}
						resultMap.put(b.getFieldName(), sb.toString());*/
					}
					
				}
			}
		}
		Long size = cgTableService.getQuerySingleSize(table, field, params);
		log.info("dict耗时："+(System.currentTimeMillis()-start)+" ms");
		
		//zxy 2015年6月1日16:32:45 处理附件问题 begin
		
		this.dealFile(result, beans);
		log.info("file耗时："+(System.currentTimeMillis()-start)+" ms");
		//end
		//wxch 处理popup显示问题
		this.dealPopup(result, beans);
		log.info("popup耗时："+(System.currentTimeMillis()-start)+" ms");
		this.dealDic(result,beans);
		log.info("国际化耗时："+(System.currentTimeMillis()-start)+" ms");
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.println(QueryParamUtil.getJson(result,size));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		log.info("动态列表查询耗时："+(end-start)+" ms");
	}
	
	private void dealGisQuery(HttpServletRequest request,String tableName,Map params)
	{
		String gisQueryValue=request.getParameter("gisQuery");
		if(StringUtil.isNotEmpty(gisQueryValue))
		{
			/*List<Map<String, Object>> list=systemService.findForJdbc("select 1 from cgform_head where table_name=?", tableName);
			if(list.size()>0)
			{*/
			List<Map<String, Object>> gisqueryCfglist=systemService.findForJdbc("SELECT * FROM sc_gisquery where table_name=?", tableName);
			if(gisqueryCfglist.size()>0)
			{
				Map<String, Object> cfg=gisqueryCfglist.get(0);
				HashMap gisQueryCfg=new HashMap();
				gisQueryCfg.put("gisQueryConfig", cfg.get("searchfield"));
				gisQueryCfg.put("gisQueryValue", gisQueryValue);
				params.put("gisQuery", gisQueryCfg);
			}
				
			/*}*/
		}
		
	}
	
	
	
	/**
	 * 处理数据字典
	 * @param result 查询的结果集
	 * @param beans 字段配置
	 */
	@SuppressWarnings("unchecked")
	private void dealDic(List<Map<String, Object>> result,
			List<CgFormFieldEntity> beans) {
		for(CgFormFieldEntity bean:beans){
			String dicTable = bean.getDictTable();//字典Table
			String dicCode = bean.getDictField();//字典Code
			String dicText= bean.getDictText();//字典text
			if(StringUtil.isEmpty(dicTable)&& StringUtil.isEmpty(dicCode)){
				//不需要处理字典
				continue;
			}else{
				if(!bean.getShowType().equals("popup")){
					List<DictEntity> dicDataList = queryDic(dicTable, dicCode,dicText);
					log.info("result=="+result);
					for(Map r:result){
						String value = String.valueOf(r.get(bean.getFieldName()));
						log.info("resultvalue=="+value);
//						for(Map m:dicDatas){
//							String typecode = String.valueOf(m.get("typecode"));
//							String typename = String.valueOf(m.get("typename"));
//							if(value.equalsIgnoreCase(typecode)){
//								r.put(bean.getFieldName(),typename);
//							}
//						}
						for(DictEntity dictEntity:dicDataList){
							log.info("dictEntity=="+dictEntity.getTypecode());
							if(value.equalsIgnoreCase(dictEntity.getTypecode())){
								log.info("getTypename=="+MutiLangUtil.getMutiLangInstance().getLang(dictEntity.getTypename()));
								//------------------update-begin------for:-国际化处理-----------------------author:zhagndaihao------------
								r.put(bean.getFieldName(),MutiLangUtil.getMutiLangInstance().getLang(dictEntity.getTypename()));
								//------------------update-end-----for:-国际化处理----------------------------author:zhagndaihao---------
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 处理File附件url 字段名__athurl
	 * @param result 查询的结果集
	 * @param beans 字段配置
	 * zxy 2015年6月1日15:47:26
	 */
	private void dealFile(List<Map<String, Object>> result,
			List<CgFormFieldEntity> beans)
	{
		if(result.size()>0)
		{
			for(CgFormFieldEntity bean:beans){
				String showType=bean.getShowType();
				String fieldName=bean.getFieldName();
				if("file".equals(showType))
				{
					for(Map r:result){
						String value=(String)r.get("id");
						String athUrl=getAthUrl(value);
						r.put(fieldName, athUrl);
					}
				}
			}
		}
	}
	
	/**
	 * 处理popup查询列表更新显示问题
	 * @param result
	 * @param beans
	 */
	private void dealPopup(List<Map<String, Object>> result,
			List<CgFormFieldEntity> beans){
		 Map<String, StringBuffer> sbIds = new HashMap<String, StringBuffer>();
		 Map<String, List<Map<String, Object>>> rscgmap = new HashMap<String, List<Map<String, Object>>>();
		 Map<String, Map<String, Object>> cgmap = new HashMap<String, Map<String,Object>>();
		if(result.size()>0)
		{
			
					for(Map<String, Object> r:result){
						this.dealPopupDataIds(beans, r,sbIds);	
					}
					for(Map<String, Object> r:result){
						this.dealPopupData(beans, r,sbIds,rscgmap,cgmap);
					}
					
			
		}
	}
	private void dealPopupDataIds(List<CgFormFieldEntity> popupList,
			Map<String, Object> dataForm,Map<String, StringBuffer> sbIds) {
		StringBuffer sb = new StringBuffer();
		for (CgFormFieldEntity fieldMap : popupList) {
		
			String showType=fieldMap.getShowType();
			if("popup".equals(showType)||"idcardread".equals(showType))
			{
				String dictTable =  fieldMap.getDictTable();
				String validateAttr = fieldMap.getPopupAttr();
				String fieldName = fieldMap.getFieldName();
				String gisAttr = fieldMap.getGisAttr();
				if(StringUtil.isEmpty(dictTable))
				{
					dictTable="idcard";
				}
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
				
						String[] idFieldPair = idField.split("=");							
						String isValue = String.valueOf(dataForm.get(idFieldPair[0]));
						if(StringUtil.isNotEmpty(isValue) && !"null".equals(isValue)){
							String aa = dataForm.get(idFieldPair[0]).toString();
							if(sbIds.containsKey(dictTable)){
								sb = sbIds.get(dictTable);
							}else{
								sbIds.put(dictTable, sb);
							}
							sb.append("'"+aa+"'").append(",");
						}			
					}
				}				
			}
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
	private void dealPopupData(List<CgFormFieldEntity> popupList,
			Map<String, Object> dataForm,Map<String, StringBuffer> sbIds,Map<String, List<Map<String, Object>>> rscgmap,Map<String, Map<String, Object>> cgmap) {
		
		/*获取所有需要处理的diabled readonly 字段 zxy 2015年8月11日*/
		StringBuilder needDealFields=new StringBuilder();
		for (CgFormFieldEntity fieldMap : popupList) {
			String validateAttr = fieldMap.getValidateAttr()!=null?fieldMap.getValidateAttr().toLowerCase():"";
			String showType = fieldMap.getShowType();
			String fieldName = fieldMap.getFieldName();
			if(validateAttr.indexOf("disabled")>-1||validateAttr.indexOf("readonly")>-1 || "popup".equals(showType))
			{
				needDealFields.append(fieldMap.getFieldName());
				needDealFields.append(",");
			}
		}
		/*获取所有需要处理的diabled readonly 字段 zxy 2015年8月11日*/
		
		
		for (CgFormFieldEntity fieldMap : popupList) {
			long start=System.currentTimeMillis();
			String showType=fieldMap.getShowType();
			if("popup".equals(showType)||"idcardread".equals(showType))
			{
				String dictTable =  fieldMap.getDictTable();
				String validateAttr = fieldMap.getPopupAttr();
				String fieldName = fieldMap.getFieldName();
				String gisAttr = fieldMap.getGisAttr();
				if(StringUtil.isEmpty(dictTable))
				{
					dictTable="idcard";
				}
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
							if(cgmap.containsKey(dictTable)){
								cgReportMap = cgmap.get(dictTable);
							}else{
								cgReportMap = cgReportService.queryCgReportConfig(dictTable);
								cgmap.put(dictTable, cgReportMap);
							}							
							log.info("cgReportService config："+(System.currentTimeMillis()-start)+" ms :"+fieldMap.getFieldName());
							if(cgReportMap.size()<=0){
								throw new CgReportNotFoundException("动态报表配置不存在!");
							}
						}catch (Exception e) {
							throw new CgReportNotFoundException("查找动态报表配置失败!"+e.getMessage());
						}
						//step.2 获取该配置的查询SQL
						Map configM = (Map) cgReportMap.get(CgReportConstant.MAIN);
						String querySql = (String) configM.get(CgReportConstant.CONFIG_SQL);
						Map queryparams =  new LinkedHashMap<String,Object>();
						String isValue = String.valueOf(dataForm.get(idFieldPair[0]));
						if(StringUtil.isNotEmpty(isValue) && !"null".equals(isValue)){
							StringBuffer sb = new StringBuffer();
							String parms = "";
							if(sbIds.containsKey(dictTable)){
								sb = sbIds.get(dictTable);
							}
							if(StringUtil.isEmpty(sb.toString())){
								parms = dataForm.get(idFieldPair[0]).toString();
								queryparams.put(idFieldPair[1], CgReportConstant.OP_EQ+"'"+dataForm.get(idFieldPair[0])+"'");
							}else{
								int i = sb.lastIndexOf(",");
								parms = sb.substring(0, i);
								queryparams.put(idFieldPair[1], CgReportConstant.OP_IN+"("+parms+")");
							}
							
							//step.4 进行查询返回结果
							int p = -1;
							int r = -1;
							//log.info("cgReportService begin data："+(System.currentTimeMillis()-start)+" ms :"+fieldMap.getFieldName());
							
							
							//wxch929修改开始
							Map queryparamsnew =  new LinkedHashMap<String,Object>();
							List<Map<String, Object>> result = null;
							String dictTableId = dictTable+parms;
							if(rscgmap.containsKey(dictTableId)){
								result = rscgmap.get(dictTableId);
							}else{
								result= cgReportService.queryByCgReportSql(querySql, queryparams, p, r); 								
								rscgmap.put(dictTableId, result);			
						      
							}
							
							if (result.size() > 0) {
								boolean flag = false;
								for(int i = 0;i<result.size();i++){
									Map<String, Object> oneRow = result.get(i);
									try {
										Object newkey = dataForm.get(idFieldPair[0]);
										Object newvalue = oneRow.get(idFieldPair[1]);
										if(newkey.equals(newvalue)){
											flag = true;
											Iterator<Entry<String, Object>> entrys = oneRow
													.entrySet().iterator();

											while (entrys.hasNext()) {
												Entry<String, Object> entry = entrys.next();
												String key = pairsMap.get(entry.getKey().toLowerCase());
												if(StringUtil.isNotEmpty(key))
												{
													//zxy 需要处理字段
													if(needDealFields.indexOf(key)>-1)
														dataForm.put(key, entry.getValue());
												}
											}
											break;
										}
									} catch (Exception e) {
										System.out.println(e.toString());
									}
								}
								if(!flag){
									//没有数据时将值值为空
									for (Map.Entry<String, String> entry : pairsMap.entrySet()) {								  
										   String key = entry.getValue();
										   dataForm.put(key, "");
									}
								}
							}else{
									//没有数据时将值值为空
								for (Map.Entry<String, String> entry : pairsMap.entrySet()) {								  
									   String key = entry.getValue();
									   dataForm.put(key, "");
								}
							}
							//wxch929修改结束
							
							
						/*	List<Map<String, Object>> result= cgReportService.queryByCgReportSql(querySql, queryparams, p, r);
							//log.info("cgReportService end data："+(System.currentTimeMillis()-start)+" ms :"+fieldMap.getFieldName());
					
							if (result.size() > 0) {
								Map<String, Object> oneRow = result.get(0);
								Iterator<Entry<String, Object>> entrys = oneRow
										.entrySet().iterator();

								while (entrys.hasNext()) {
									Entry<String, Object> entry = entrys.next();
									String key = pairsMap.get(entry.getKey());
									if(StringUtil.isNotEmpty(key))
									{
										//zxy 需要处理字段
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
							}*/
						}else{
							//没有数据时将值值为空
							for (Map.Entry<String, String> entry : pairsMap.entrySet()) {								  
								   String key = entry.getValue();
								   dataForm.put(key, "");
							}
						}
				

					}
				}
				
			}
			//log.info("popup data deal："+(System.currentTimeMillis()-start)+" ms :"+fieldMap.getFieldName());
		}
	}
	/**
	 * @param ywid 业务id
	 * @return
	 */
	private String getAthUrl(String ywid)
	{
		StringBuffer result=new StringBuffer("");
		List<CgUploadEntity> uploadBeans = cgFormFieldService.findByProperty(CgUploadEntity.class, "cgformId", ywid);
		for(CgUploadEntity upload:uploadBeans)
		{
			result.append("commonController.do?viewFile&subclassname=c&fileid=");
			result.append(upload.getId());
			result.append(",");
		}
		if(result.length()>0)
		{
			result=result.deleteCharAt(result.length()-1);
		}
		return result.toString();
	}

	/**
	 * 删除动态表
	 * @param configId 配置id
	 * @param id 主键
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(String configId,String id,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		CgFormHeadEntity head = cgFormFieldService.getCgFormHeadByCode(configId);
		String table = head.getTableName();
		String code = head.getCode();
		ChrhcFormInterceptor interceptor = cgFormFieldService.getInterceptorByTableName(code);
		
		if(interceptor != null ){
			interceptor.beforeDelete(id, request);
		}
		
		cgTableService.delete(head,table, id);
		
		if(interceptor != null ){
			interceptor.afterDelete(id, request);
		}
		
		
		String message = "删除成功";
		systemService.addLog(message, Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}
	/**
	 * 删除动态表-批量
	 * @param configId 配置id
	 * @param ids 主键
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delBatch")
	@ResponseBody
	public AjaxJson delBatch(String configId,String ids,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		CgFormHeadEntity head = cgFormFieldService.getCgFormHeadByCode(configId);
		String table = head.getTableName();
		String code = head.getCode();
		
		String message = "删除成功";
		try {
			String[] id = ids.split(",");
			
			ChrhcFormInterceptor interceptor = cgFormFieldService.getInterceptorByTableName(code);
			
			if(interceptor != null ){
				interceptor.beforeDelete(ids, request);
			}
			
			cgTableService.deleteBatch(head,table, id);
			
			if(interceptor != null ){
				interceptor.afterDelete(ids, request);
			}
			
		} catch (Exception e) {
			message = e.getMessage();
		}
		systemService.addLog(message, Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 装载要传入到ftl中的变量
	 * @param configs 从数据库中取出来的配置
	 * @param paras 要传入ftl的参数（需要对configs进行一些改造）
	 * @param request 
	 * @return 要传入ftl的参数（该方法直接操作paras容器，当然可以使用此返回值）
	 */
	@SuppressWarnings("unchecked")
	private Map loadVars(Map<String, Object> configs,Map<String, Object> paras, HttpServletRequest request) {
		paras.putAll(configs);
		List<Map> fieldList = new ArrayList<Map>();
		List<Map> queryList = new ArrayList<Map>();
		StringBuilder fileds = new StringBuilder();
		StringBuilder initQuery = new StringBuilder();
		
		//zxy 添加所有request变量 begin 2015年4月27日13:35:27
		RequestUtil.setAllRequestParam(paras, request);
		//zxy 添加所有request变量 end 2015年4月27日13:35:32
		
		//------------------update-begin-------2014年9月3日----author:JueYue------for:-列表数据隐藏权限------------
		Set<String> operationCodes = (Set<String>) request.getAttribute(Globals.OPERATIONCODES);
		Map<String,TSOperation> operationCodesMap = new HashMap<String, TSOperation>();
		if(operationCodes != null){
			TSOperation tsOperation;
			for (String id : operationCodes) {
				tsOperation = systemService.getEntity(TSOperation.class, id);
				if(tsOperation != null && tsOperation.getOperationType() == 0 && tsOperation.getStatus() == 0){
					operationCodesMap.put(tsOperation.getOperationcode(), tsOperation);
				}
			}
		}
		for (CgFormFieldEntity bean : (List<CgFormFieldEntity>) configs.get(CgAutoListConstant.FILEDS)) {
			if(operationCodesMap.containsKey(bean.getFieldName())) {
				continue;
			}
			//------------------update-end---2014年9月3日----author:JueYue------for:-列表数据隐藏权限------------
			Map fm = new HashMap<String, Object>();
			fm.put(CgAutoListConstant.FILED_ID, bean.getFieldName());
			fm.put(CgAutoListConstant.FIELD_TITLE, bean.getContent());
			String isShowList = bean.getIsShowList();
			if(StringUtil.isEmpty(isShowList)){
				if("id".equalsIgnoreCase(bean.getFieldName())){
					isShowList = CgAutoListConstant.BOOL_FALSE;
				}else{
					isShowList = CgAutoListConstant.BOOL_TRUE;
				}
			}
			fm.put(CgAutoListConstant.FIELD_ISSHOW, isShowList);
			fm.put(CgAutoListConstant.FIELD_QUERYMODE, bean.getQueryMode());
			fm.put(CgAutoListConstant.FIELD_SHOWTYPE, bean.getShowType());
			fm.put(CgAutoListConstant.FIELD_TYPE, bean.getType());
			fm.put(CgAutoListConstant.FIELD_LENGTH, bean.getFieldLength()==null?"120":bean.getFieldLength());
			fm.put(CgAutoListConstant.FIELD_HREF, bean.getFieldHref()==null?"":bean.getFieldHref());
			loadDic(fm,bean);
			fieldList.add(fm);
			if (CgAutoListConstant.BOOL_TRUE.equals(bean.getIsQuery())) {
				Map fmq = new HashMap<String, Object>();
				fmq.put(CgAutoListConstant.FILED_ID, bean.getFieldName());
				fmq.put(CgAutoListConstant.FIELD_TITLE, bean.getContent());
				fmq.put(CgAutoListConstant.FIELD_QUERYMODE, bean.getQueryMode());
				fmq.put(CgAutoListConstant.FIELD_TYPE, bean.getType());
				fmq.put(CgAutoListConstant.FIELD_SHOWTYPE, bean.getShowType());
				fmq.put(CgAutoListConstant.FIELD_DICTFIELD, bean.getDictField());
				fmq.put(CgAutoListConstant.FIELD_DICTTABLE, bean.getDictTable());
				fmq.put(CgAutoListConstant.FIELD_ISQUERY,"Y");
				fmq.put(CgAutoListConstant.FIELD_MAINQUERY,bean.getMainQuery());
				fmq.put(CgAutoListConstant.GISATTR, bean.getGisAttr());
				fmq.put(CgAutoListConstant.VALIDATEATTR, bean.getValidateAttr());
				fmq.put(CgAutoListConstant.POPUPATTR, bean.getPopupAttr());
				loadDefaultValue(fmq,bean,request);
				loadDic(fmq,bean);
				queryList.add(fmq);
			}
			loadUrlDataFilter(queryList,bean,request);
			loadInitQuery(initQuery,bean,request);
			fileds.append(bean.getFieldName()).append(",");
		}
		loadAuth(paras, request);
		loadIframeConfig(paras, request);
		paras.put(CgAutoListConstant.CONFIG_FIELDLIST, fieldList);
		paras.put(CgAutoListConstant.CONFIG_QUERYLIST, queryList);
		paras.put(CgAutoListConstant.FILEDS, fileds);
		paras.put(CgAutoListConstant.INITQUERY, initQuery+"&dataRule="+request.getParameter("dataRule"));
		return paras;
	}
	/**
	 * 加载iframe设置
	 * @param paras
	 * @param request
	 */
	private void loadIframeConfig(Map<String, Object> paras,
			HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		String lang = (String)session.getAttribute("lang");
		
		//如果列表以iframe形式的话，需要加入样式文件
		StringBuilder sb= new StringBuilder("");
		if(!request.getQueryString().contains("isHref")){
			sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery/jquery-1.8.3.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/dataformat.js\"></script>");
			sb.append("<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\"plug-in/easyui/themes/default/easyui.css\" type=\"text/css\"></link>");
			sb.append("<link rel=\"stylesheet\" href=\"plug-in/easyui/themes/icon.css\" type=\"text/css\"></link>");
			sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"plug-in/accordion/css/accordion.css\">");
			sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"plug-in/accordion/css/icons.css\">");
			sb.append("<script type=\"text/javascript\" src=\"plug-in/easyui/jquery.easyui.min.1.3.2.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"plug-in/easyui/locale/zh-cn.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/syUtil.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"plug-in/My97DatePicker/WdatePicker.js\"></script>");
			sb.append("<link rel=\"stylesheet\" href=\"plug-in/tools/css/common.css\" type=\"text/css\"></link>");
			sb.append("<script type=\"text/javascript\" src=\"plug-in/lhgDialog/lhgdialog.min.js\"></script>");
			
			sb.append(StringUtil.replace("<script type=\"text/javascript\" src=\"plug-in/tools/curdtools_{0}.js\"></script>", "{0}",  StringUtil.isEmpty(lang)?"zh-cn":lang));
			
			sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/easyuiextend.js\"></script>");
		}else{
		}
		paras.put(CgAutoListConstant.CONFIG_IFRAME, sb.toString());
	}
	/**
	 * 加载按钮权限
	 * @param paras
	 * @param request
	 */
	private void loadAuth(Map<String, Object> paras, HttpServletRequest request) {
		List<TSOperation>  nolist = (List<TSOperation>) request.getAttribute(Globals.NOAUTO_OPERATIONCODES);
		if(ResourceUtil.getSessionUserName().getUserName().equals("admin")|| !Globals.BUTTON_AUTHORITY_CHECK){
			nolist = null;
		}
		String nolistStr = "";
		if(nolist!=null){
			for(TSOperation operation:nolist){
				nolistStr+=operation.getOperationcode();
				nolistStr+=",";
			}
		}
		paras.put(CgAutoListConstant.CONFIG_NOLIST, nolist==null?new ArrayList<String>(0):nolist);
		paras.put(CgAutoListConstant.CONFIG_NOLISTSTR, nolistStr==null?"":nolistStr);
	}
	/**
	 * 加载列表初始查询条件-
	 * @param initQuery
	 * @param bean
	 * @param request
	 */
	private void loadInitQuery(StringBuilder initQuery, CgFormFieldEntity bean,
			HttpServletRequest request) {
		if(bean.getFieldName().equalsIgnoreCase("id")){
			return;
		}
		String paramV = request.getParameter(bean.getFieldName());
		String paramVbegin = request.getParameter(bean.getFieldName()+"_begin");
		String paramVend = request.getParameter(bean.getFieldName()+"_end");
		paramV = getSystemValue(paramV);
		if(!StringUtil.isEmpty(paramV)){
			initQuery.append("&"+bean.getFieldName()+"="+paramV);
		}
		if(!StringUtil.isEmpty(paramVbegin)){
			initQuery.append("&"+bean.getFieldName()+"_begin="+paramVbegin);
		}
		if(!StringUtil.isEmpty(paramVend)){
			initQuery.append("&"+bean.getFieldName()+"_end="+paramVend);
		}
	}

	/**
	 * 加载URL中的过滤参数[在未配置查询字段的情况下，作为hidden控件使用]
	 * @param queryList
	 * @param bean
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	private void loadUrlDataFilter(List<Map> queryList, CgFormFieldEntity bean,
			HttpServletRequest request) {
		String paramV = request.getParameter(bean.getFieldName());
		String paramVbegin = request.getParameter(bean.getFieldName()+"_begin");
		String paramVend = request.getParameter(bean.getFieldName()+"_end");
		if(bean.getFieldName().equalsIgnoreCase("id")){
			return;
		}
		for(Map mq:queryList){
			if(mq.containsValue(bean.getFieldName())){
				return;
			}
		}
		if(!StringUtil.isEmpty(paramV) || !StringUtil.isEmpty(paramVbegin) ||!StringUtil.isEmpty(paramVend)){
			Map fmq = new HashMap<String, Object>();
			fmq.put(CgAutoListConstant.FILED_ID, bean.getFieldName());
			fmq.put(CgAutoListConstant.FIELD_TITLE, bean.getContent());
			fmq.put(CgAutoListConstant.FIELD_QUERYMODE, bean.getQueryMode());
			fmq.put(CgAutoListConstant.FIELD_TYPE, bean.getType());
			fmq.put(CgAutoListConstant.FIELD_ISQUERY,"N");
			paramV = getSystemValue(paramV);
			fmq.put(CgAutoListConstant.FIELD_VALUE, paramV);
			paramVend = getSystemValue(paramVend);
			fmq.put(CgAutoListConstant.FIELD_VALUE_BEGIN, StringUtil.isEmpty(paramVbegin)?"":paramVbegin);
			fmq.put(CgAutoListConstant.FIELD_VALUE_END, StringUtil.isEmpty(paramVend)?"":paramVend);
			queryList.add(fmq);
		}
	}

	/**
	 * 加载URL中的过滤参数[在已配置查询字段的情况下，给该查询字段加上默认值]
	 * @param fmq
	 * @param bean
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	private void loadDefaultValue(Map fmq, CgFormFieldEntity bean,
			HttpServletRequest request) {
		if(bean.getQueryMode().equalsIgnoreCase("single")){
			String paramV = request.getParameter(bean.getFieldName());
			if(!StringUtil.isEmpty(paramV)){
				paramV = getSystemValue(paramV);
				fmq.put(CgAutoListConstant.FIELD_VALUE, paramV);
			}
		}else if(bean.getQueryMode().equalsIgnoreCase("group")){
			String paramVbegin = request.getParameter(bean.getFieldName()+"_begin");
			String paramVend = request.getParameter(bean.getFieldName()+"_end");
			fmq.put(CgAutoListConstant.FIELD_VALUE_BEGIN, StringUtil.isEmpty(paramVbegin)?"":paramVbegin);
			fmq.put(CgAutoListConstant.FIELD_VALUE_END, StringUtil.isEmpty(paramVend)?"":paramVend);
		}
	}

	/**
	 * 装载数据字典
	 * @param m	要放入freemarker的数据
	 * @param bean 读取出来的动态配置数据
	 */
	@SuppressWarnings("unchecked")
	private void loadDic(Map m, CgFormFieldEntity bean) {
		String dicT = bean.getDictTable();//字典Table
		String dicF = bean.getDictField();//字典Code
		String dicText = bean.getDictText();//字典Text
		String gisAttr = bean.getGisAttr();
		if(StringUtil.isEmpty(dicT)&& StringUtil.isEmpty(dicF)){
			//如果这两个字段都为空，则没有数据字典
			m.put(CgAutoListConstant.FIELD_DICTLIST, new ArrayList(0));
			return;
		}
		if(!bean.getShowType().equals("popup")){
			//if(!StringUtil.isEmpty(gisAttr)) {
				gisAttr = String.valueOf(gisAttr);
				String whereStr = SysContextSqlConvert.setSqlModel(gisAttr);
				List<DictEntity> dicDatas = systemService.queryDic(dicT, dicF, dicText, whereStr);
				m.put(CgAutoListConstant.FIELD_DICTLIST, dicDatas);
		//	}
		}else{
			m.put(CgAutoListConstant.FIELD_DICTLIST, new ArrayList(0));
		}
	}
	/**
	 * 查询数据字典
	 * @param dicTable 数据字典表
	 * @param dicCode	数据字典字段
	 * @param dicText 数据字典显示文本
	 * @return
	 */
	private List<DictEntity> queryDic(String dicTable, String dicCode,String dicText) {
//		StringBuilder dicSql = new StringBuilder();
//		if(StringUtil.isEmpty(dicTable)){//step.1 如果没有字典表则使用系统字典表
//			dicTable = CgAutoListConstant.SYS_DIC;
//			dicSql.append(" SELECT TYPECODE,TYPENAME FROM");
//			dicSql.append(" "+dicTable);
//			dicSql.append(" "+"WHERE TYPEGROUPID = ");
//			dicSql.append(" "+"(SELECT ID FROM "+CgAutoListConstant.SYS_DICGROUP+" WHERE TYPEGROUPCODE = '"+dicCode+"' )");
//		}else{//step.2 如果给定了字典表则使用该字典表，这个功能需要在表单配置追加字段
//			//table表查询
//			dicSql.append("SELECT DISTINCT ").append(dicCode).append(" as typecode, ");
//			if(dicText!=null&&dicText.length()>0){
//				dicSql.append(dicText).append(" as typename ");
//			}else{
//				dicSql.append(dicCode).append(" as typename ");
//			}
//			dicSql.append(" FROM ").append(dicTable);
//			dicSql.append(" ORDER BY ").append(dicCode);
//		}
//		//step.3 字典数据
//		List<Map<String, Object>> dicDatas = systemService.findForJdbc(dicSql.toString());
		return systemService.queryDict(dicTable, dicCode, dicText);
	}
	
	private String getSystemValue(String sysVarName) {
		if(StringUtil.isEmpty(sysVarName)){
			return sysVarName;
		}
		if(sysVarName.contains("{") && sysVarName.contains("}")){
			sysVarName = sysVarName.replaceAll("\\{", "");
			sysVarName = sysVarName.replaceAll("\\}", "");
			sysVarName =sysVarName.replace("sys.", "");
			return ResourceUtil.getUserSystemData(sysVarName);
		}else{
			return sysVarName;
		}
	}
	
 
	/**
	 * 查询上一条或者下一条数据
	 * @param configId tableId
	 * @param request 
	 * @param field
	 * @param stepNum  1，待办下一条，-1代表上一条
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "nextRecord")
	@ResponseBody
	public AjaxJson nextRecord(String configId,String field,String sort,String order, HttpServletRequest request, int stepNum) {
		//step.1 获取动态配置
		String jversion = cgFormFieldService.getCgFormVersionByTableName(configId);
		Map<String, Object>  configs = configService.queryConfigs(configId,jversion);
		String table = (String) configs.get(CgAutoListConstant.TABLENAME);
		Map params =  new HashMap<String,Object>(); 
		List<CgFormFieldEntity> beans = (List<CgFormFieldEntity>) configs.get(CgAutoListConstant.FILEDS);
		for(CgFormFieldEntity b:beans){
			QueryParamUtil.loadQueryParams(request,b,params);
		}
		params.remove("id");
		
		String id = request.getParameter("id");
		
 		String nextId = cgTableService.queryNextId(table,  params,sort,order, id ,stepNum);
		
 		AjaxJson j = new AjaxJson();
 		j.setMsg(nextId);
 		return j;
		 
	}
	
}
