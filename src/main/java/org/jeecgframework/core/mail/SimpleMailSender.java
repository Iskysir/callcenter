package org.jeecgframework.core.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date; 
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address; 
import javax.mail.BodyPart; 
import javax.mail.Message; 
import javax.mail.MessagingException; 
import javax.mail.Multipart; 
import javax.mail.Session; 
import javax.mail.Transport; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeBodyPart; 
import javax.mail.internet.MimeMessage; 
import javax.mail.internet.MimeMultipart; 
import javax.mail.internet.MimeUtility;

/** 
* 简单邮件（不带附件的邮件）发送器 
*/ 
public class SimpleMailSender  { 
/** 
  * 以文本格式发送邮件 
  * @param mailInfo 待发送的邮件的信息 
  */ 
	public boolean sendTextMail(MailSenderInfo mailInfo) { 
	  // 判断是否需要身份认证 
	  MyAuthenticator authenticator = null; 
	  Properties pro = mailInfo.getProperties();
	  if (mailInfo.isValidate()) { 
	  // 如果需要身份认证，则创建一个密码验证器 
		authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword()); 
	  }
	  // 根据邮件会话属性和密码验证器构造一个发送邮件的session 
	  Session sendMailSession = Session.getDefaultInstance(pro,authenticator); 
	  try { 
	  // 根据session创建一个邮件消息 
	  Message mailMessage = new MimeMessage(sendMailSession); 
	  // 创建邮件发送者地址 
	  Address from = new InternetAddress(mailInfo.getFromAddress()); 
	  // 设置邮件消息的发送者 
	  mailMessage.setFrom(from); 
	  // 创建邮件的接收者地址，并设置到邮件消息中 
	  Address[] to = new Address[mailInfo.getToAddress().length];
	  for (int i = 0; i < to.length; i++) {
		  to[i] = new InternetAddress(mailInfo.getToAddress()[i]);
	  }
	  mailMessage.setRecipients(Message.RecipientType.TO,to); 
	  //设置抄送收件人
	  //msg.addRecipients(Message.RecipientType.CC,Address coaddress);
	  //设置暗抄送人
	  //msg.addRecipients(Message.RecipientType.BCC,Address bcoaddress);
	  // 设置邮件消息的主题 
	  mailMessage.setSubject(mailInfo.getSubject()); 
	  // 设置邮件消息发送的时间 
	  mailMessage.setSentDate(new Date()); 
	  // 设置邮件消息的主要内容 
	  String mailContent = mailInfo.getContent(); 
	  mailMessage.setText(mailContent); 
	  // 发送邮件 
	  Transport.send(mailMessage);
	  return true; 
	  } catch (MessagingException ex) { 
		  ex.printStackTrace(); 
	  } 
	  return false; 
	} 
	
	/** 
	  * 以HTML格式发送邮件 
	  * @param mailInfo 待发送的邮件信息 
	 * @throws UnsupportedEncodingException 
	  */ 
	public static boolean sendHtmlMail(MailSenderInfo mailInfo)  { 
	  // 判断是否需要身份认证 
	  MyAuthenticator authenticator = null;
	  Properties pro = mailInfo.getProperties();
	  //如果需要身份认证，则创建一个密码验证器  
	  if (mailInfo.isValidate()) { 
		authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
	  } 
	  // 根据邮件会话属性和密码验证器构造一个发送邮件的session 
	  Session sendMailSession = Session.getInstance(pro,authenticator); 
	  try { 
	  // 根据session创建一个邮件消息 
	  Message mailMessage = new MimeMessage(sendMailSession); 
	  // 创建邮件发送者地址 
	  Address from = new InternetAddress(mailInfo.getFromAddress()); 
	  // 设置邮件消息的发送者 
	  mailMessage.setFrom(from); 
	  // 创建邮件的接收者地址，并设置到邮件消息中 
	  Address[] to = new Address[mailInfo.getToAddress().length];
	  for (int i = 0; i < to.length; i++) {
		  to[i] = new InternetAddress(mailInfo.getToAddress()[i]);
	  }
	  // Message.RecipientType.TO属性表示接收者的类型为TO 
	  mailMessage.setRecipients(Message.RecipientType.TO,to); 
	  
	  // 设置邮件消息的主题 
	  mailMessage.setSubject(mailInfo.getSubject()); 
	  // 设置邮件消息发送的时间 
	  mailMessage.setSentDate(new Date()); 
	  // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象 
	  Multipart mainPart = new MimeMultipart(); 
	  // 创建一个包含HTML内容的MimeBodyPart 
	  BodyPart html = new MimeBodyPart(); 
	  // 设置HTML内容 
	  html.setContent(mailInfo.getContent(), "text/html; charset=utf-8"); 
	  mainPart.addBodyPart(html); 
	  
	  
	 /* if( mailInfo.getAttachFileNames() != null && mailInfo.getAttachFileNames().length > 0){//有附件  
		  for (int i = 0; i < mailInfo.getAttachFileNames().length; i++) {
			  BodyPart filepart = new MimeBodyPart(); 
			  FileDataSource fds = new FileDataSource(mailInfo.getAttachFileNames()[i]);
			  filepart.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart  
			  filepart.setFileName(MimeUtility.encodeText(fds.getName(),"utf-8",null));  //得到文件名同样至入BodyPart  
			  mainPart.addBodyPart(filepart);
		}
      }   
	  */
	  if(mailInfo.getAttachFileNames() !=null && !mailInfo.getAttachFileNames().isEmpty()){
		  for (String filePath : mailInfo.getAttachFileNames().keySet()) {
			  BodyPart filepart = new MimeBodyPart(); 
			  FileDataSource fds = new FileDataSource(filePath);
			  filepart.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart  
			  filepart.setFileName(MimeUtility.encodeText(mailInfo.getAttachFileNames().get(filePath),"utf-8",null));  //得到文件名同样至入BodyPart  
			  mainPart.addBodyPart(filepart);
		  }
	  }
	  
	  // 将MiniMultipart对象设置为邮件内容 
	  mailMessage.setContent(mainPart); 
	  
	  // 发送邮件 
	  Transport.send(mailMessage); 
	  return true; 
	  } catch (MessagingException | UnsupportedEncodingException ex) { 
		  ex.printStackTrace(); 
		  return false; 
	  } 
	} 
} 

