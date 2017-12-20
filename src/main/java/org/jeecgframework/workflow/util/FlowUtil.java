package org.jeecgframework.workflow.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.ListUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.workflow.dao.ActivitiDao;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.util.StringUtils;
/**
 * 
 * 类名：FlowUtil
 * 功能：流程实例运行中辅助类
 * 详细：此类暴露给流程，可在流程定义使用表达式来使用此类的方法，必须由Spring创建才有效
 * 
 * 对外暴露的名称 flowUtil
 * 
 * 作者：jeecg
 * 版本：1.0
 * 日期：2013-8-10 下午4:28:40
 *
 */
public class FlowUtil {

	private static final Logger logger = Logger.getLogger(FlowUtil.class);
	
	public List stringToList(String content){
		String[] s = content.split(",");
		return Arrays.asList(s);
	}
	
	/**
	 * 获取某个人的上级领导（单个）
	 * @param applyUserId
	 * @return
	 */
	public String getDeptHeadId(String applyUserId){
		//通过组织机构，获取该人的上级领导
		List<Map<String,Object>> list = this.getDeptHeadLeaderSql(applyUserId);
		Map<String,Object> map = null;
		String username = "";
		if(list.size()>0){
			map = list.get(0);
			username = map.get("username").toString();
		}
		return username ;
	}
	
	/**
	 *  获取某个人的上级领导（多个领导）
	 * @param applyUserId
	 * @return
	 */
	public String getDeptHeadIds(String applyUserId){
		//通过组织机构，获取该人的上级领导
		List<Map<String,Object>> list = this.getDeptHeadLeaderSql(applyUserId);
		Map<String,Object> map = null;
		StringBuffer sf = new StringBuffer();
		for(int i=0;i<list.size();i++){
			map = list.get(i);
			sf.append(map.get("username"));
			if(i < list.size()-1){
				sf.append(",");
			}
		}
		return sf.toString() ;
	}
	
	/**
	 * 获取某人的上级领导（sql查询）
	 * @param applyUserId
	 * @return
	 */
	private List<Map<String, Object>> getDeptHeadLeaderSql(String username){
		//通过组织机构，获取该人的上级领导
		ActivitiDao activitiDao=ApplicationContextUtil.getContext().getBean(ActivitiDao.class);
		List<Map<String,Object>> list = activitiDao.getDeptHeadLeader(username);
		return list;
	}
	/**
	 * 通过用户角色，获得前一部执行人父级部门的想要角色的人员
	 * @param roleCode
	 * @return
	 */
	public String getUserByRoleCode(String roleCode) {
		ApplicationContext context = ApplicationContextUtil.getContext();
		JdbcTemplate templateObj = (JdbcTemplate) context.getBean("jdbcTemplate");
				
		//获得当前用户部门的id
		TSUser tsUser = ResourceUtil.getSessionUserName();			
		String sql=" SELECT * from t_s_base_user where id='"+tsUser.getId()+"'";
		logger.info(" :sql="+sql);
		List<Map<String,Object>> baseList= new ArrayList<Map<String,Object>>();
		baseList = templateObj.queryForList(sql);
		String curOrgId = "";
		if(!ListUtils.isNullOrEmpty(baseList)){
			curOrgId = baseList.get(0).get("departid")+"";
			if(StringUtils.isEmpty(curOrgId) || "null".equals(curOrgId)){
				return "";
			}
		}
		//获得当前用户部门父级部门的id
		String parentOrgId="";
		String departStr = " SELECT * from t_s_depart where 1 = 1 AND id = '"+curOrgId+"'";
		logger.info(" :departStr="+departStr);
		List<Map<String,Object>> departsList= new ArrayList<Map<String,Object>>();
		departsList = templateObj.queryForList(departStr);
		if(!ListUtils.isNullOrEmpty(departsList)){
			parentOrgId = departsList.get(0).get("parentdepartid")+"";	
		}
		
		//拼接查询sql
		StringBuffer sb = new StringBuffer("");
		
		sb.append("SELECT u.id,u.realname,u.username,o.org_id,'");
		sb.append(roleCode).append("' as roleCode");
		sb.append(" from t_s_base_user u,t_s_role_user r,t_s_user_org o");
		sb.append(" where 1=1");
		sb.append(" and r.userid = u.id ");
		sb.append(" and o.user_id = u.id ");
		sb.append(" and r.roleid in(SELECT id from t_s_role where rolecode = '");
		sb.append(roleCode);
		sb.append("')");
		if(!StringUtils.isEmpty(parentOrgId) && "null".equals(parentOrgId)){
			sb.append(" and o.org_id='");
			sb.append(parentOrgId);
			sb.append("'");
		}
		logger.info(" :sb="+sb.toString());
		
		StringBuffer sf = new StringBuffer("");
		List<Map<String, Object>> list = templateObj.queryForList(sb.toString()
				.toString());
		if (!ListUtils.isNullOrEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> userMap = list.get(i);
				if (userMap.containsKey("username")
						&& null != userMap.get("username")) {
					sf.append(userMap.get("username") + "");
				}
				if (i < list.size()-1) {
					sf.append(",");
				}
			}

		}
		logger.info("备选用户为："+sf.toString());
		return sf.toString();
	}
	/**
	 * 通过用户角色和部门编码，获得符合条件的处理人员
	 * @param roleCode
	 * @param orgCode
	 * @return
	 */
	public String getUserByRoleCodeAndOrgCode(String roleCode,String orgCode) {
		ApplicationContext context = ApplicationContextUtil.getContext();
		JdbcTemplate templateObj = (JdbcTemplate) context.getBean("jdbcTemplate");				
		
		//获得指定部门的id
		String orgId="";
		String departStr = " SELECT * from t_s_depart where 1 = 1 AND org_code = '"+orgCode+"'";
		logger.info(" :departStr="+departStr);
		List<Map<String,Object>> departsList= new ArrayList<Map<String,Object>>();
		departsList = templateObj.queryForList(departStr);
		if(!ListUtils.isNullOrEmpty(departsList)){
			orgId = departsList.get(0).get("id")+"";	
			if(StringUtils.isEmpty(orgId) || "null".equals(orgId)){
				return "";
			}
		}
		
		//拼接查询sql
		StringBuffer sb = new StringBuffer("");
		
		sb.append("SELECT u.id,u.realname,u.username,o.org_id,'");
		sb.append(roleCode).append("' as roleCode");
		sb.append(" from t_s_base_user u,t_s_role_user r,t_s_user_org o");
		sb.append(" where 1=1");
		sb.append(" and r.userid = u.id ");
		sb.append(" and o.user_id = u.id ");
		sb.append(" and r.roleid in(SELECT id from t_s_role where rolecode = '");
		sb.append(roleCode);
		sb.append("')");
		sb.append(" and o.org_id='");
		sb.append(orgId);
		sb.append("'");
		
		logger.info(" :sb="+sb.toString());
		
		StringBuffer sf = new StringBuffer("");
		List<Map<String, Object>> list = templateObj.queryForList(sb.toString()
				.toString());
		if (!ListUtils.isNullOrEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> userMap = list.get(i);
				if (userMap.containsKey("username")
						&& null != userMap.get("username")) {
					sf.append(userMap.get("username") + "");
				}
				if (i < list.size()-1) {
					sf.append(",");
				}
			}

		}
		logger.info("备选用户为："+sf.toString());
		return sf.toString();
	}
}
