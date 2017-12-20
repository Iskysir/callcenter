package com.chrhc.workflow.activiti.listener;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.repository.ProcessDefinition;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.pojo.base.TPBpmLog;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 第一次提交流程时，默认执行第一个用户任务
 * @author Administrator
 *
 */
@Component
@Transactional
public class AutoSubmitListener implements TaskListener{

	private static final String FIRST_START = "true";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void notify(DelegateTask delegateTask) {
		if(delegateTask.getVariable(FIRST_START) == null ){
			TaskService taskService = ApplicationContextUtil.getContext().getBean(TaskService.class);
			Map<String,Object> map = new HashMap<>();
			map.put(FIRST_START, "true");
			taskService.complete(delegateTask.getId(),map);
			
			//自动提交的任务节点，向流程审批日志中添加审批意见
			TPBpmLog tpbpmLog = new TPBpmLog();
			TSUser user = ResourceUtil.getSessionUserName();
			tpbpmLog.setBpm_id(delegateTask.getExecutionId());
			
			RepositoryService repositoryService = ApplicationContextUtil.getContext().getBean(RepositoryService.class);
			ProcessDefinition processDefinition = repositoryService.getProcessDefinition(delegateTask.getProcessDefinitionId());
			tpbpmLog.setTask_name(processDefinition.getName());
			tpbpmLog.setTask_node(delegateTask.getName());
			tpbpmLog.setOp_code(user.getUserKey());
			tpbpmLog.setOp_type("1");
			tpbpmLog.setOp_name(user.getRealName());
			tpbpmLog.setOp_time(DataUtils.gettimestamp());
			tpbpmLog.setMemo("同意");
			
			String businessKey = delegateTask.getExecution().getProcessBusinessKey();
			tpbpmLog.setBizid(businessKey);						
			SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
			//BPM_FORM_KEY
			String srcTable = delegateTask.getVariable("BPM_FORM_KEY")+"";
			tpbpmLog.setSrcTable(srcTable);
			tpbpmLog.setTaskid(delegateTask.getId());
			
			systemService.save(tpbpmLog);
		}
	}
}
