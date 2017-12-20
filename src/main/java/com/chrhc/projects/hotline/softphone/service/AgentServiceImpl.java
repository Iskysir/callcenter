package com.chrhc.projects.hotline.softphone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Component
@Transactional(readOnly = true)
public class AgentServiceImpl implements AgentService {
	@Autowired
	@Qualifier("ctiJdbcTemplate")
	private JdbcTemplate ctiJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	/*@Autowired
	private CagentService cagentService;
	@Autowired
	private AgentUserService agentUserService;
	
	private static HashMap<String,String> agentNotAval=new HashMap<String,String>();
	
	@Autowired
	private AgentDao agentDao;
	
	@Autowired
	private SkillChannelDao skillChannelDao;
	
	@Autowired
	private DictionaryService dictService;
	
	@Autowired
	private UserService userService;*/
	
	
/*	@Autowired
	private HotlineAcdService hotlineAcdService;*/
	
	@Override
	public List<Map<String,Object>> getAgentStatus() {
		// TODO Auto-generated method stub
		//List<HashMap> resultList=new ArrayList<HashMap>();
		List<Map<String,Object>> agentStatusList=ctiJdbcTemplate.queryForList("select agentname,agent_status from cti_agent_status_reserve,cti_agent where cti_agent.id=cti_agent_status_reserve.agentid");
		List<Map<String,Object>> agentList=jdbcTemplate.queryForList("select USER_ID,USERNAME,REALNAME,SKILL_ID,AGENTID,EXTENSION from  HL_AGENTCONF LEFT JOIN t_s_base_user on HL_AGENTCONF.USER_ID=t_s_base_user.ID");
		for (Map<String,Object> agentStatusMap:agentStatusList)
		{
			for(Map<String,Object> user :agentList)
			{
				if(user.get("AGENTID").toString().equalsIgnoreCase(agentStatusMap.get("agentname").toString()))
				{
					agentStatusMap.put("id", user.get("USER_ID"));
					agentStatusMap.put("userName", user.get("USERNAME"));
					agentStatusMap.put("displayName", user.get("REALNAME"));
					agentStatusMap.put("skill", user.get("SKILL_ID"));
					agentStatusMap.put("agentId", user.get("AGENTID"));
					agentStatusMap.put("agentPhone", user.get("EXTENSION"));

					if("-1".equals(agentStatusMap.get("agent_status").toString()))
					{
						agentStatusMap.put("userStatus", "离线");
					}
					if("0".equals(agentStatusMap.get("agent_status").toString()))
					{
						agentStatusMap.put("userStatus", "空闲");
					}
					if("1".equals(agentStatusMap.get("agent_status").toString()))
					{
						agentStatusMap.put("userStatus", "振铃");
					}
					if("2".equals(agentStatusMap.get("agent_status").toString()))
					{
						agentStatusMap.put("userStatus", "通话");
					}
					if("3".equals(agentStatusMap.get("agent_status").toString()))
					{
						agentStatusMap.put("userStatus", "保持");
					}
					if("4".equals(agentStatusMap.get("agent_status").toString()))
					{
						agentStatusMap.put("userStatus", "话后");
					}
					if("5".equals(agentStatusMap.get("agent_status").toString()))
					{
						agentStatusMap.put("userStatus", "被占用");
					}
					if("6".equals(agentStatusMap.get("agent_status").toString()))
					{
						agentStatusMap.put("userStatus", "忙碌");
					}
					if("7".equals(agentStatusMap.get("agent_status").toString()))
					{
						agentStatusMap.put("userStatus", "小休");
					}
					if("8".equals(agentStatusMap.get("agent_status").toString()))
					{
						agentStatusMap.put("userStatus", "仅呼出");
					}
					
					//agentStatusMap.put("", "");
				}
				/*HashMap agentStatusMap=new HashMap();
				agentStatusMap.put("id", user.getId());
				agentStatusMap.put("displayName", user.getDisplayName());
				agentStatusMap.put("userName", user.getUserName());
				agentStatusMap.put("officePhone", user.getOfficePhone());*/
				/*String jsonStr=JsonMapper.nonDefaultMapper().toJson(user);
				HashMap agentStatusMap=JsonMapper.nonDefaultMapper().fromJson(jsonStr, HashMap.class);*/
				//resultList.add(agentStatusMap);
			}
		}
		return agentStatusList;
	}
	


}
