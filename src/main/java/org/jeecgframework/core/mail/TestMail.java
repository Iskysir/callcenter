package org.jeecgframework.core.mail;

import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import java.util.UUID;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.MimeMessage;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
 

/** 
 * 发送邮件需要使用的基本信息 
 */

public class TestMail {
	public static void main(String[] args) throws Exception {
		/*Properties prop = new Properties();
		String path = TestMail.class.getClassLoader().getResource("").toURI().getPath();    
		System.out.println(path);
		
		FileInputStream fis = new FileInputStream(new File(path + "mailSetting.properties"));    
		prop.load(fis);
		System.out.println(prop.getProperty("mail.username"));*/
		//sendMail();
		recciveMail();
		
		Queue<String> queue = new LinkedList<String>();  
        queue.offer("Hello");  
        queue.offer("World!");  
        queue.offer("你好！");  
        System.out.println(queue.size());  
        String str;  
        while((str=queue.poll())!=null){  
            System.out.println(str);  
        }  
        System.out.println();  
        System.out.println(queue.size()); 
	
	}

	private static void recciveMail() throws Exception {
		//读取配置文件
		Properties props = new Properties(); 
		props.put("mail.smtp.host", "smtp.163.com"); 
        props.put("mail.smtp.auth", "true");  
        Session session = Session.getDefaultInstance(props,null); 
        URLName urln = new URLName("pop3", "pop.163.com", 110, null, "chrhchotline@163.com", "chrhc2014");  
        Store store = session.getStore(urln);
        store.connect();  
        Folder folder = store.getFolder("INBOX");  
        folder.open(Folder.READ_WRITE);  
        
        Message[] messages = folder.getMessages();
        ReciveOneMail pmm = null;
        for (int i = 0; i < 5&&messages!=null&&i<messages.length; i++) {
        	pmm = new ReciveOneMail((MimeMessage) messages[i]); 
        	  System.out.println(pmm.getSubject());      
             // System.out.println(pmm.saveAttachMent((Part) messages[i]));
        	//messages[i].setFlag(Flags.Flag.DELETED, true);
        }
        
        folder.close(true);
        store.close();
	}

	private static void sendMail(){
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("chrhchotline@163.com");
		mailInfo.setPassword("chrhc2014");// 您的邮箱密码
		mailInfo.setFromAddress("chrhchotline@163.com"); 
		mailInfo.setToAddress(new String[]{"65753119@qq.com"});
		mailInfo.setSubject("关于新的问题");
		mailInfo.setContent("新的"); 
		
		String[] attachFileNames = new String[]{"E:\\新建文本文档.txt"};
		//mailInfo.setAttachFileNames(attachFileNames);
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		//sms.sendTextMail(mailInfo);// 发送文体格式
	
		sms.sendHtmlMail(mailInfo);// 发送html格式
	}
}