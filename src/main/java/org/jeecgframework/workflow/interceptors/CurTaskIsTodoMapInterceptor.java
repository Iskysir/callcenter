package org.jeecgframework.workflow.interceptors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.log4j.Logger;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * <mvc:interceptor>
 *			<mvc:mapping path="/activitiController.do" />
 *			<mvc:mapping path="/taskController.do" />
 *			<bean class="org.jeecgframework.workflow.interceptors.CurTaskIsTodoMapInterceptor">
 *				<property name="includeUrls">
 *					<list>
 *						<value>activitiController.do?processComplete</value>
 *						<value>taskController.do?goTaskTab</value>
 *					</list>
 *				</property>
 *			</bean>			
 *		</mvc:interceptor>
 */
/**
 * 流程处理和提交之前判断该任务是否仍在该用户名下
 * @author fangxu-wang
 * 2015-10-21
 *
 */
public class CurTaskIsTodoMapInterceptor  implements HandlerInterceptor {
	private List<String> includeUrls;
	
	private static final Logger logger = Logger.getLogger(SynUserInterceptor.class);
	
	@Autowired
	private ProcessEngine processEngine;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private SystemService systemService;
	private String message = null;
	/**
	 * 拦截的方法执行之前执行该方法
	 */
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		TSUser user = ResourceUtil.getSessionUserName();
		
		String requestPath = ResourceUtil.getRequestPath(arg0);// 用户访问的资源地址
		if(!includeUrls.contains(requestPath)){
			return true;
		}else{
			//任务ID
			String taskId = arg0.getParameter("taskId");
			logger.info("*********当前的任务id为："+taskId);
						
			TaskService taskService = processEngine.getTaskService();
			
			HistoricTaskInstance historicTaskInstance = processEngine.getHistoryService().createHistoricTaskInstanceQuery().taskId(taskId).orderByProcessInstanceId().desc().singleResult();
			ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(historicTaskInstance.getProcessDefinitionId()).singleResult();
			logger.info("*********当前的流程为："+processDefinition.getName());
			
			//获得处理人任务
			TaskQuery tq1 = taskService.createTaskQuery().taskAssignee(user.getUserName()).orderByProcessInstanceId().desc().orderByTaskCreateTime().desc();
			//tq1 = tq1.processDefinitionId(processDefinitionId);
			List<Task> assignList = tq1.list();
			
			
			//获得候选人任务
			TaskQuery tq2 = taskService.createTaskQuery().taskCandidateUser(user.getUserName());
			//tq2 = tq2.processDefinitionId(processDefinitionId);
			List<Task> candidateUserList = tq2.list();
			
			//根据当前人所在的组查询
			String userRole = userService.getUserRole(user);
			TaskQuery tq3 = taskService.createTaskQuery().taskCandidateGroupIn(Arrays.asList(userRole.split(","))).orderByProcessInstanceId().desc().orderByTaskCreateTime().desc();
			//tq3 = tq3.processDefinitionId(processDefinitionId);
			List<Task> groupInList = tq3.list();
			
			List<Task> allTaskList = new ArrayList<Task>();
			allTaskList.addAll(assignList);
			allTaskList.addAll(candidateUserList);
			allTaskList.addAll(groupInList);
			
			logger.info("*********获得到的该用户所有的待办任务数量为："+allTaskList.size());
			
			if(null != allTaskList && allTaskList.size() > 0){
				for(Task task : allTaskList){
					if(task.getId().equals(taskId)){
						return true;
					}
				}
				message = "当前流程实例: " + processDefinition.getName() + "已不在待办任务之内";
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				return false;
			}
		}
		return true;
	}
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {		
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}
	public List<String> getIncludeUrls() {
		return includeUrls;
	}

	public void setIncludeUrls(List<String> includeUrls) {
		this.includeUrls = includeUrls;
	}

}
