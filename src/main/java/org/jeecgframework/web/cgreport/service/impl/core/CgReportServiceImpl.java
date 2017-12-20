package org.jeecgframework.web.cgreport.service.impl.core;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.extend.hqlsearch.SysContextSqlConvert;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.cgreport.common.CgReportConstant;
import org.jeecgframework.web.cgreport.dao.core.CgReportDao;
import org.jeecgframework.web.cgreport.service.core.CgReportServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(value="cgReportService")
@Transactional
public class CgReportServiceImpl extends CommonServiceImpl implements
		CgReportServiceI {
	@Autowired
	private JdbcDao jdbcDao;
	@Autowired
	private CgReportDao cgReportDao;

	private static Logger log = Logger.getLogger(CgReportServiceImpl.class);
	public Map<String, Object> queryCgReportConfig(String reportId) {
		Map<String,Object> cgReportM = new HashMap<String, Object>(0);
		Map<String,Object> mainM = queryCgReportMainConfig(reportId);
		List<Map<String,Object>> itemsM = queryCgReportItems(reportId);
		cgReportM.put(CgReportConstant.MAIN, mainM);
		cgReportM.put(CgReportConstant.ITEMS, itemsM);
		return cgReportM;
	}

	public Map<String,Object> queryCgReportMainConfig(String reportId){
//		String sql = JeecgSqlUtil.getMethodSql(JeecgSqlUtil.getMethodUrl());
//		Map<String,Object> parameters = new LinkedHashMap<String,Object>();
//		parameters.put("id", reportId);
//		Map mainM = jdbcDao.findForMap(sql, parameters);

		//采用MiniDao实现方式
		return cgReportDao.queryCgReportMainConfig(reportId);
	}

	public List<Map<String,Object>> queryCgReportItems(String reportId){
//		String sql = JeecgSqlUtil.getMethodSql(JeecgSqlUtil.getMethodUrl());
//		Map<String,Object> parameters = new LinkedHashMap<String,Object>();
//		parameters.put("configId", reportId);
//		List<Map<String,Object>> items = jdbcDao.findForListMap(sql, parameters);

		//采用MiniDao实现方式
		return cgReportDao.queryCgReportItems(reportId);
	}
	@SuppressWarnings("unchecked")

	public List<Map<String, Object>> queryByCgReportSql(String sql, Map params,
														int page, int rows) {
		String querySql = getFullSql(sql,params);
		log.info("querysqlbbbb="+querySql);
		System.out.println("querysqlbbbb="+querySql);
		List<Map<String,Object>> result = null;
		if(page==-1 && rows==-1){
			result = jdbcDao.findForJdbc(querySql);
		}else{
			//result = jdbcDao.findForJdbc(querySql, page, rows);
			result = jdbcDao.findForJdbc(querySql, page, rows,"oracle");
		}
		return result;
	}
	/**
	 * 获取拼装查询条件之后的sql
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getFullSql(String sql,Map params){
		//处理sql支持表达式
		sql = SysContextSqlConvert.setSqlModel(sql);
		StringBuilder sqlB =  new StringBuilder();
		sqlB.append("SELECT t.* FROM ( ");
		sqlB.append(sql+" ");
		sqlB.append(") t ");
		if (params.size() >= 1) {
			sqlB.append("WHERE 1=1  ");
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
		//sql优化查询 wxch 413
		/*sql = sql.toLowerCase();
		sqlB.append(sql+" ");
		
		if (params.size() >= 1) {
			StringBuilder sqlA =  new StringBuilder();
			Iterator it = params.keySet().iterator();
			while (it.hasNext()) {
				String key = String.valueOf(it.next());
				String value = String.valueOf(params.get(key));
				if (!StringUtil.isEmpty(value) && !"null".equals(value)) {
						
						sqlA.append(" " + key +  value );
				}
				if(!it.hasNext()){
					sqlA.append(" ");
				}else{
					sqlA.append(" AND ");
				}
				
			}
			
			int a = sqlB.lastIndexOf("order");
			int b = sqlB.lastIndexOf("where");
			if(a > 0 && a>b){
				//sqlB.append(" having( "+sqlA+" )");
				sqlB = sqlB.insert(a, " having( "+sqlA+" )");
			}else{
				sqlB.append(" having( "+sqlA+" )");
			}
			
			
		}*/
		return sqlB.toString();
	}
	@SuppressWarnings("unchecked")

	public long countQueryByCgReportSql(String sql, Map params) {
		String querySql = getFullSql(sql,params);
		querySql = "SELECT COUNT(*) FROM ("+querySql+") t2";
		long result = jdbcDao.findForLong(querySql,new HashMap(0));
		return result;
	}
	@SuppressWarnings( "unchecked" )

	public List<String> getSqlFields(String sql) {
		if(oConvertUtils.isEmpty(sql)){
			return null;
		}
		List<Map<String, Object>> result = jdbcDao.findForJdbc(sql, 1, 1);
		if(result.size()<1){
			throw new BusinessException("该报表sql没有数据");
		}
		Set fieldsSet= result.get(0).keySet();
		List<String> fileds = new ArrayList<String>(fieldsSet);
		return fileds;
	}
}
