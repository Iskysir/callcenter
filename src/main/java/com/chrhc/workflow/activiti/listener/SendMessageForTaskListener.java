package com.chrhc.workflow.activiti.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.IdentityLink;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.ListUtils;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.chrhc.common.http.SmsHttpService;

/**
 * 监听任务到达下一节点，发短信提醒
 * @author fangxu-wang
 *
 */
@Component
@Transactional
public class SendMessageForTaskListener implements TaskListener{

	private static final Logger logger = Logger.getLogger(SendMessageForTaskListener.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String SMS_SQBH = "0";//德州市的组织编码
	
	

	public void notify(DelegateTask delegateTask) {
		
		
		ApplicationContext context = ApplicationContextUtil.getContext();
		JdbcTemplate templateObj = (JdbcTemplate) context.getBean("jdbcTemplate");
		
		List<Map<String,Object>> smsList= new ArrayList<Map<String,Object>>();
		//任务处理人
		String assignee = delegateTask.getAssignee();
		Set<IdentityLink> candidates = delegateTask.getCandidates();
		
		Map<String,Object> vMap = delegateTask.getVariables();
		String bizCode = vMap.get("code")+"";
		StringBuffer msgSb = new StringBuffer("社区管理平台提醒您，工单编号：");
		msgSb.append(bizCode);
		msgSb.append(" 需要您审批。");
		
		if(!StringUtil.isEmpty(assignee) && !"null".equals(assignee)){
			smsList.add(this.sendMessageByUser(templateObj, assignee,msgSb.toString()));
		}else{
			Iterator<IdentityLink> ii = candidates.iterator();
			while(ii.hasNext()){
				IdentityLink link = ii.next();
				String user = link.getUserId();
				String group = link.getGroupId();
				
				if(!StringUtil.isEmpty(user) && !"null".equals(user)){
					String[] userSplit = user.split(",");
					for(int i=0;i<userSplit.length;i++){
						smsList.add(this.sendMessageByUser(templateObj, userSplit[i],msgSb.toString()));
					}
				}else if(!StringUtil.isEmpty(group) && !"null".equals(group)){
					String[] groupSplit = group.split(",");
					for(int i=0;i<groupSplit.length;i++){
						smsList.add(this.sendMessageByRole(templateObj, groupSplit[i],msgSb.toString()));
					}
				}
			}
		}
		logger.info("smsList:"+smsList);
	}
	/**
	 * 
	 * @param templateObj
	 * @param username
	 * @param msg
	 * @return
	 */
	public Map<String,Object> sendMessageByUser(JdbcTemplate templateObj,String username,String msg){
		SmsHttpService smsHttpService = ApplicationContextUtil.getContext().getBean(SmsHttpService.class);
		Map<String,Object> map = new HashMap<String,Object>();
		
			//指定处理人情况
			StringBuffer sbui = new StringBuffer("");
			sbui.append(" SELECT u.id,u.email,u.mobilePhone,u.officePhone,base.username,base.realname,d.departname,d.org_code ");
			sbui.append(" from t_s_user u,t_s_base_user base,t_s_user_org t,t_s_depart d ");
			sbui.append(" where 1=1 ");
			sbui.append(" and base.username = '");
			sbui.append(username);
			sbui.append("' ");
			sbui.append(" and u.id = base.id ");
			sbui.append(" and base.id = t.user_id ");
			sbui.append(" and t.org_id = d.id ");
			logger.info("下一步处理人sql:"+sbui.toString());
			List<Map<String,Object>> uiList= new ArrayList<Map<String,Object>>();
			uiList = templateObj.queryForList(sbui.toString());
			if(!ListUtils.isNullOrEmpty(uiList)){
				for(int i=0;i<uiList.size();i++){
					Map<String,Object> uiMap = uiList.get(0);
					String sysOrgcode = uiMap.get("org_code")+"";
					String dstNumber = uiMap.get("mobilePhone")+"";
					if(!StringUtil.isEmpty(sysOrgcode) && !"null".equals(sysOrgcode) && !StringUtil.isEmpty(dstNumber) && !"null".equals(dstNumber)){
						map = smsHttpService.SendMessage(SMS_SQBH, dstNumber, msg, "", "", "");	
						//map = smsHttpService.SendMessage(sysOrgcode, dstNumber, msg, "", "", "");					
					}
				}
			}
		return map;
	}
	/**
	 * 
	 * @param templateObj
	 * @param roleCode
	 * @param msg
	 * @return
	 */
	public Map<String,Object> sendMessageByRole(JdbcTemplate templateObj,String roleCode,String msg){
		SmsHttpService smsHttpService = ApplicationContextUtil.getContext().getBean(SmsHttpService.class);
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(!StringUtil.isEmpty(roleCode) && !"null".equals(roleCode)){
			//根据角色，指定处理人情况
			StringBuffer sbui = new StringBuffer("");
			sbui.append(" SELECT u.id,u.email,u.mobilePhone,u.officePhone,base.username,base.realname,d.departname,d.org_code,o.rolecode,o.rolename  ");
			sbui.append(" from t_s_user u,t_s_base_user base,t_s_user_org t,t_s_depart d,t_s_role_user r,t_s_role o ");
			sbui.append(" where 1=1 ");
			sbui.append(" and o.rolecode ='");
			sbui.append(roleCode);
			sbui.append("' ");
			sbui.append(" and r.roleid = o.id ");
			sbui.append(" and r.userid = u.id ");	
			sbui.append(" and u.id = base.id ");
			sbui.append(" and base.id = t.user_id ");
			sbui.append(" and t.org_id = d.id ");
			logger.info("下一步处理人sql:"+sbui.toString());
			List<Map<String,Object>> uiList= new ArrayList<Map<String,Object>>();
			uiList = templateObj.queryForList(sbui.toString());
			if(!ListUtils.isNullOrEmpty(uiList)){
				for(int i=0;i<uiList.size();i++){
					Map<String,Object> uiMap = uiList.get(0);
					String sysOrgcode = uiMap.get("org_code")+"";
					String dstNumber = uiMap.get("mobilePhone")+"";
					if(!StringUtil.isEmpty(sysOrgcode) && !"null".equals(sysOrgcode) && !StringUtil.isEmpty(dstNumber) && !"null".equals(dstNumber)){
						//map = smsHttpService.SendMessage(sysOrgcode, dstNumber, msg, "", "", "");	
						map = smsHttpService.SendMessage(SMS_SQBH, dstNumber, msg, "", "", "");
					}
				}
			}
		}
		return map;
	}
}
