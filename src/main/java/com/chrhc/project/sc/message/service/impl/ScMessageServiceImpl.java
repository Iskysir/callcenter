package com.chrhc.project.sc.message.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.sms.util.MailUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chrhc.common.http.SmsHttpService;
import com.chrhc.project.sc.message.entity.MsgConstants;
import com.chrhc.project.sc.message.entity.ScMessageEntity;
import com.chrhc.project.sc.message.entity.ScMsgRecordEntity;
import com.chrhc.project.sc.message.service.ScMessageServiceI;

@Service("scMessageService")
@Transactional
public class ScMessageServiceImpl extends CommonServiceImpl implements
		ScMessageServiceI {

	@Autowired
	private SystemService systemService;
	@Autowired
	private SmsHttpService sms;


	public void saveMessage(ScMessageEntity entity) {
		entity.setStatus(MsgConstants.Msg_Not_Send);
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateTime;
		try {
			dateTime = time.parse(time.format(new Date()));
			entity.setPTime(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.save(entity);
		String types[] = entity.getMType().split(",");
		String receivers[] = entity.getReceiver().split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < receivers.length; i++) {
			sb.append("'").append(receivers[i]).append("'");
			if (i != receivers.length - 1) {
				sb.append(",");
			}
		} 

		String sql = " select id from sc_rkjbxxnew where sys_org_code in (" + sb.toString() + ")";
		List<String> receiverList = this.findListbySql(sql);
		List<ScMsgRecordEntity> records = new ArrayList<ScMsgRecordEntity>();
		ScMsgRecordEntity record;
		for (int j = 0; j < receiverList.size(); j++) {
			for (int i = 0; i < types.length; i++) {
				record = new ScMsgRecordEntity();
				record.setMessageId(entity.getId());
				record.setTitle(entity.getTitle());
				record.setMType(types[i]);
				record.setContent(entity.getContent());
				record.setPublisher(entity.getPublisher());
				record.setReceiver(receiverList.get(j));
				record.setStatus(MsgConstants.Msg_Not_Send);
				SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
				String date = sdf.format(new Date());
				record.setTime(date);
				
				records.add(record);
			}
		}
		
		systemService.batchSave(records);

	}
	
	public void saveOne(ScMessageEntity entity) {
		entity.setStatus(MsgConstants.Msg_Not_Send);
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateTime;
		try {
			dateTime = time.parse(time.format(new Date()));
			entity.setPTime(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.save(entity);
		String types[] = entity.getMType().split(",");
		ScMsgRecordEntity record = null;
			for (int i = 0; i < types.length; i++) {
				record = new ScMsgRecordEntity();
				record.setMessageId(entity.getId());
				record.setTitle(entity.getTitle());
				record.setMType(types[i]);
				record.setContent(entity.getContent());
				record.setPublisher(entity.getPublisher());
				record.setReceiver(entity.getReceiver());
				record.setStatus(MsgConstants.Msg_Not_Send);
				SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
				String date = sdf.format(new Date());
				record.setTime(date);
				
			}
		
		
		systemService.save(record);

	}

	public void send(ScMsgRecordEntity entity,String smtpHost,String sender,String user,String pwd) {
		
			
		if (MsgConstants.Msg_Type_Sms.equals(entity.getMType())) {
			//SmsHttpService sms = new SmsHttpService();
			List<String> tel = this.findListbySql("select lxdh from sc_rkjbxxnew where id='"+entity.getReceiver().toString()+"'");
			String num = tel.get(0);
			
			String content = StringUtil.removeHTMLLable(entity.getContent()) ;
//			content = content.substring(content.indexOf("<body>")+6,content.indexOf("</body>"));
			sms.SendMessage(entity.getSysOrgCode(), num, content, "", "", "");
			
			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			entity.setTime(date);
			entity.setStatus(MsgConstants.Msg_Send);
			this.updateEntitie(entity);
		} else if (MsgConstants.Msg_Type_Email.equals(entity.getMType())) {
			try {
				List<String> emails = this.findListbySql("select yx from sc_rkjbxxnew where id='"+entity.getReceiver().toString()+"'");
				
			
				if(emails != null && !StringUtil.isEmpty(emails.get(0))){
					String email = emails.get(0);
					MailUtil.sendEmail(smtpHost, email, entity.getTitle(), entity.getContent(),  sender, user, pwd);
					SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
					String date = sdf.format(new Date());
					entity.setTime(date);
					
				}
				//所有发送过的人均设置为已发送
				entity.setStatus(MsgConstants.Msg_Send);
				this.updateEntitie(entity);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		} else if (MsgConstants.Msg_Type_Wechat.equals(entity.getModelType())) {

		}  
	}
}