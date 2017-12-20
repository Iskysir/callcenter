package org.jeecgframework.web.cgform.service.impl.autolist;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.JeecgDataAutorUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.common.CommUtils;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;
import org.jeecgframework.web.cgform.entity.config.CgFormHeadEntity;
import org.jeecgframework.web.cgform.service.autolist.CgTableServiceI;
import org.jeecgframework.web.cgform.service.build.DataBaseService;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.jeecgframework.web.cgform.service.upload.CgUploadServiceI;
import org.jeecgframework.web.cgform.util.QueryParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 
 * @Title:CgTableServiceImpl
 * @description:动态表数据服务实现
 * @author 赵俊夫
 * @date Jul 5, 2013 9:34:51 PM
 * @version V1.0
 */
@Service("cgTableService")
@Transactional
public class CgTableServiceImpl extends CommonServiceImpl implements CgTableServiceI {
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private DataBaseService dataBaseService;
	
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	
	@Autowired
	private CgUploadServiceI cgUploadService;

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> querySingle(String table, String field, Map params,
			int page, int rows) {
		StringBuilder sqlB = new StringBuilder();
		dealQuerySql(table,field,params,sqlB);
		List<Map<String, Object>> result = commonService.findForJdbcParam(sqlB
				.toString(), page, rows);
		return result;
	}
	
	public List<Map<String, Object>> querySingle(String table, String field, Map params,
			String sort, String order, int page, int rows) {
		StringBuilder sqlB = new StringBuilder();
		dealQuerySql(table,field,params,sqlB);
		if(!StringUtil.isEmpty(sort)&& !StringUtil.isEmpty(order)){
			sqlB.append(" ORDER BY "+sort+" "+ order);
		}
		List<Map<String, Object>> result = commonService.findForJdbcParam(sqlB
				.toString(), page, rows);
		return result;
	}

	@SuppressWarnings("unchecked")
	public boolean delete(CgFormHeadEntity head,String table, Object id) {
		try{
			/*CgFormHeadEntity head = cgFormFieldService.getCgFormHeadByCode(table);*/
			Map<String,Object> data  = dataBaseService.findOneForJdbc(table, id.toString());
			if(data!=null){
				//打印测试
			    Iterator it=data.entrySet().iterator();
			    while(it.hasNext()){
			    	Map.Entry entry=(Map.Entry)it.next();
			        Object ok=entry.getKey();
			        Object ov=entry.getValue()==null?"":entry.getValue();
			        org.jeecgframework.core.util.LogUtil.info("name:"+ok.toString()+";value:"+ov.toString());
			    }
				data = CommUtils.mapConvert(data);
				dataBaseService.executeSqlExtend(head.getId(), "delete", data);
			}
			//step.1 删除表
			StringBuilder deleteSql = new StringBuilder();
			//zxy 逻辑删除
			deleteSql.append("UPDATE "+table+" SET DELFLAG='1' WHERE id = ?");
			
			//deleteSql.append("DELETE FROM "+table+" WHERE id = ?"); zxy 逻辑删除
			
			
			
			
			if(!QueryParamUtil.sql_inj(id.toString())){
				commonService.executeSql(deleteSql.toString(), id);
			}
			//zxy 逻辑删除
			
			//step.2 判断是否有明细表,进行连带删除
			String[] subTables = head.getSubTableStr()==null?new String[0]:head.getSubTableStr().split(",");
			String[] codes = head.getSubCode()==null?new String[0]:head.getSubCode().split(",");
			int i=0;
			for(String code:codes){
				String subTable=subTables[i];
				Map<String, CgFormFieldEntity>  fields = cgFormFieldService.getAllCgFormFieldByCode(code);
				String subFkField = null;
				Iterator it = fields.keySet().iterator();
				for(;it.hasNext();){
					String fieldName  = (String) it.next();
					CgFormFieldEntity fieldc = fields.get(fieldName);
					if(StringUtil.isNotEmpty(fieldc.getMainTable())){
						if(table.equalsIgnoreCase(fieldc.getMainTable())){
							subFkField = fieldName;
						}
					}
				}
				if(StringUtil.isNotEmpty(subFkField)){
					//String dsql = "delete from "+subTable+" "+"where "+subFkField+" = ? ";
					String dsql = "update "+subTable+" "+"set delflag='1' where "+subFkField+" = ? ";
					this.executeSql(dsql,id);
				}
				i++;
			}
			//step.3 删除附件信息--通用 zxy 逻辑删除去除 不再删除File表
			/*if(!QueryParamUtil.sql_inj(id.toString())){
				cgUploadService.deleteFileByCgformld(String.valueOf(id));
			}*/
			
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	private void dealQuerySql(String table, String field, Map params,StringBuilder sqlB){
		sqlB.append(" SELECT ");
		for (String f : field.split(",")) {
			sqlB.append(f);
			sqlB.append(",");
		}
		sqlB.deleteCharAt(sqlB.length() - 1);
		sqlB.append(" FROM " + table);
		if (params.size() >= 1) {
			sqlB.append(" WHERE 1=1 and delflag = '0'  ");
			Iterator it = params.keySet().iterator();
			while (it.hasNext()) {
				String key = String.valueOf(it.next());
				if(!"gisQuery".equals(key))//zxy 2015年5月29日16:39:48 gisQuery或者处理添加
				{
					String value = String.valueOf(params.get(key));
					if (!StringUtil.isEmpty(value) && !"null".equals(value)&&!" LIKE '%%'".equals(value)) {
							sqlB.append(" AND ");
							sqlB.append(" " + key +  value );
					}
				}
			}
		}
		//update-begin--Author:JueYue  Date:20140831 for：onlinecoding 数据权限
		Object dataRuleSql = JeecgDataAutorUtils.loadDataSearchConditonSQLString();//ContextHolderUtils.getRequest().getAttribute(Globals.MENU_DATA_AUTHOR_RULE_SQL);
		if(dataRuleSql != null && !dataRuleSql.equals("")){
			if(params.size() == 0) {
				sqlB.append(" WHERE 1=1 and delflag = '0'  ");
			}
			sqlB.append(dataRuleSql);
		}
		//update-end--Author:JueYue  Date:20140831 for：onlinecoding 数据权限
		
		
		//zxy 2015年5月29日16:39:48 gisQuery或者处理添加  begin
		if(params.size() >= 1&&params.containsKey("gisQuery"))
		{
			HashMap map=(HashMap)params.get("gisQuery");
			String gisQueryConfig=(String)map.get("gisQueryConfig");
			String gisQueryValue=(String)map.get("gisQueryValue");
			if(StringUtil.isNotEmpty(gisQueryConfig)&&StringUtil.isNotEmpty(gisQueryValue))
			{
				String[] fieldArray=gisQueryConfig.split(",");
				if(fieldArray.length>=1)
				{
					/*if(params.size() == 1) {
						sqlB.append(" WHERE 1=1 ");
					}*/
					sqlB.append(" AND (");
					for(String gisfield:fieldArray)
					{
						sqlB.append(gisfield+" like '%"+gisQueryValue+"%' OR ");
					}
					sqlB.delete(sqlB.length()-3, sqlB.length());
					sqlB.append(" )");
				}
			}
		}
		
		//zxy 2015年5月29日16:39:48 gisQuery或者处理添加  end
		
	}


	@SuppressWarnings("unchecked")
	
	public Long getQuerySingleSize(String table, String field, Map params) {
		StringBuilder sqlB = new StringBuilder();
		dealQuerySql(table,"count(*) as query_size,",params,sqlB);
		List<Map<String, Object>> result = commonService.findForJdbc(sqlB.toString());
		if(result.size()>=1){
			return Long.parseLong(String.valueOf(result.get(0).get("query_size")));
		}else{
			return 0L;
		}
	}
	
	public boolean deleteBatch(CgFormHeadEntity head,String table, String[] ids) {
		try{
			for(String id:ids){
				delete(head,table, id);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return true;
	}


	public String queryNextId(String table, Map params, String sort, String order, String id,int steptNum) {
		
		StringBuilder sqlB = new StringBuilder();
		sqlB.append(" SELECT @rownum := 0, t.id FROM ").append(table).append(" t where delflag = '0' ");
		if (params.size() >= 1) {
			//sqlB.append(" WHERE 1=1 and delflag = '0' ");
			Iterator it = params.keySet().iterator();
			while (it.hasNext()) {
				String key = String.valueOf(it.next());
				String value = String.valueOf(params.get(key));
				if (!StringUtil.isEmpty(value) && !"null".equals(value)) {
						sqlB.append(" AND ");
						sqlB.append(" " + key +  value );
				}
			}
		}
		Object dataRuleSql = JeecgDataAutorUtils.loadDataSearchConditonSQLString();
		if(dataRuleSql != null && !dataRuleSql.equals("")){
			if(params.size() == 0) {
				//sqlB.append(" WHERE 1=1 and delflag = '0' ");
			}
			sqlB.append(dataRuleSql);
		}
		
		if(!StringUtil.isEmpty(sort)&& !StringUtil.isEmpty(order)){
			sqlB.append(" ORDER BY "+sort+" "+ order);
		}
		
		StringBuilder sqlA = new StringBuilder("SELECT @rownum :=@rownum + 1 rownum, id FROM ( ");
		sqlA.append(sqlB).append(" ) t2");
		
		StringBuilder sqlC = new StringBuilder(" SELECT * FROM ( ");
		sqlC.append(sqlA).append(" ) t3");
		
		
		String sql1  = sqlC.toString() + " where id = '" + id + "'";
		Map map = commonService.findOneForJdbc(sql1);
		
		int rowNum = (int) (Double.parseDouble(map.get("rownum")+"") + steptNum);
		
		String sql2  = sqlC.toString() + " where rownum = " + rowNum + "";
		map = commonService.findOneForJdbc(sql2);
		
		if(map != null){
			return map.get("id") + "";
		}
		return  "";
	}
}
