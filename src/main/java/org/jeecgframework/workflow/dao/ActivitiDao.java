package org.jeecgframework.workflow.dao;

import java.util.List;
import java.util.Map;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.minidao.annotation.Sql;

@MiniDao
public interface ActivitiDao {
	
	@Sql("insert into t_s_basebus(id,userid,prjstateid,busconfigid) values('${mp.id}','${mp.userid}','${mp.prjstateid}','${mp.busconfigid}')")
	@Arguments("mp")
	int insert(Map mp);
	
	/**
	 * 根据历史执行ID，查询变量内容
	 * @param EXECUTION_ID_
	 * @param NAME_
	 * @return
	 */
	@Sql("select TEXT_ from act_hi_varinst where NAME_=:name and EXECUTION_ID_=:proid")
	@Arguments({"name","proid"})
	String getHisVarinst(String name,String proid);
	
	/**
	 * 获取流程的启动和结束时间
	 * @param name
	 * @param proid
	 * @return
	 */
	 @Sql("select * from act_hi_actinst where EXECUTION_ID_=:proid and ACT_TYPE_ in ('startEvent','endEvent')")
	 @Arguments({"proid"})
	 List<Map<String,Object>> getActHiActinst(String proid);
	 
	 
	 /**
	  * 通过用户名，获取上级领导用户名
	  * @param user_name
	  * @return
	  */
	 @Arguments({"username"})
	 List<Map<String,Object>> getDeptHeadLeader(String username);
	 
	 /**
	  * 指定下一步操作人
	  * @param proInsId
	  * @param taskDefKey
	  * @param nextUser
	  */
	 @Arguments({"proInsId","taskDefKey", "page", "rows"})
	 String getTaskIdByProins(String proInsId,String taskDefKey, int page, int rows);
	 
	 /**
	  *  查询流程的历史任务节点
	  * @param proceInsId
	  * @return
	  */
	 @Arguments({"proceInsId"})
	 List getHistTaskNodeList(String proceInsId);
	 
	 
	 /**
	  *  修改自定义表单业务数据状态
	  * @param proceInsId
	  * @return
	  */
	 @Arguments({"id","bpm_form_key"})
	 @Sql("update ${bpm_form_key} set bpm_status = 3 where id = :id")
	 void updateFormDataStatus(String id,String bpm_form_key);
}
