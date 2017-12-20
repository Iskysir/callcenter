package com.chrhc.project.sc.message.service;

import org.jeecgframework.core.common.service.CommonService;
import com.chrhc.project.sc.message.entity.ScMessageEntity;
import com.chrhc.project.sc.message.entity.ScMsgRecordEntity;

public interface ScMessageServiceI extends CommonService{

	void saveMessage(ScMessageEntity entity);
	
	void saveOne(ScMessageEntity entity);

	void send(ScMsgRecordEntity scMsgRecordEntity,String smtpHost,String sender,String user,String pwd);
	 
}
