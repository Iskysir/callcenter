package com.chrhc.project.sc.message.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeecgframework.core.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.yun.PushMsgToAll;
import com.chrhc.project.sc.message.service.ScMessageServiceI;

@Service("msgSendJob")
public class MsgSendJob {

 
	@Autowired
	private ScMessageServiceI scMessageService;
	
//	@Autowired
//	private ScMessageEntity scMessage;
	
	PropertiesUtil util = new PropertiesUtil("sysConfig.properties");
	
    /**
     * 每5分钟执行一次
     * */
    public void sendMsg(){
		System.out.println("---------" + scMessageService );
		
		List<ScMsgRecordEntity> unSendRecords = scMessageService.findHql(" from ScMsgRecordEntity where status = ? or status = ?", MsgConstants.Msg_Not_Send,MsgConstants.Msg_Send_Fail); 
		Set<String> ids = new HashSet<String>();
		if(unSendRecords != null && !unSendRecords.isEmpty()){
			for (int i = 0; i < unSendRecords.size(); i++) {
				String id = unSendRecords.get(i).getMessageId();
				final ScMsgRecordEntity entity = unSendRecords.get(i);
				new Runnable(){
					public void run() {
						scMessageService.send(entity,util.readProperty("mail.smtpHost"),util.readProperty("mail.sender"),util.readProperty("mail.user"),util.readProperty("mail.pwd"));
					}
				}.run();
				ids.add(id);
			}
		}
		
		
		List<ScMessageEntity> unSendMsg =  scMessageService.findHql(" from ScMessageEntity where status = ? and  MType like ? ", MsgConstants.Msg_Not_Send, "%"+MsgConstants.Msg_Type_Baidu+"%" ); 
		if(unSendMsg != null && !unSendMsg.isEmpty()){
			for (int i = 0; i < unSendMsg.size(); i++) {
				final ScMessageEntity entity = unSendMsg.get(i);
				new Runnable(){
					public void run() {
						 PushMsgToAll.PushMsg(entity.getId(), 2, entity.getReceiver(), 0);
						 entity.setStatus(MsgConstants.Msg_Send);
						 scMessageService.updateEntitie(entity);
					}
				}.run();
			}
		}
		for(String id:ids){
			
			List<ScMessageEntity> reStatus = scMessageService.findHql(" from ScMessageEntity where id = ?",id);
			reStatus.get(0).setStatus(MsgConstants.Msg_Send);
			scMessageService.updateEntitie(reStatus.get(0));
		}
	}
}