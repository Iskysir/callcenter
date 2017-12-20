package com.chrhc.project.hl.sms;

import com.chrhc.project.hl.util.StringUtilLength;
import com.huateng.utils.HexUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.constant.DataBaseConstant;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.cgform.common.CommUtils;
import org.jeecgframework.web.cgform.service.build.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*import com.sun.xml.internal.ws.resources.SenderMessages;*/

/**
 *
 * <br>创建日期：2012-8-13
 * <br><b>Copyright 2012 上海华腾软件系统有限公司　All Rights Reserved</b>
 * @author wmchen
 * @since 2012-8-13
 * @version 1.0
 */
@Service("baseService")
public class BaseServiceImpl implements IBaseService{

	private static Logger logger = Logger.getLogger(BaseServiceImpl.class);

	/** 短信发送服务 */
	@Autowired
	private ISMS smsTCP;
	@Autowired
	private DataBaseService dataBaseService;


	/* (non-Javadoc)
	 * @see com.ecton.business.common.service.IBaseService#sendMessage(java.lang.String, java.lang.String, java.lang.String[])
	 */
	/*public void sendMessage(String mobile,String[] validateString,String type,String code){
		try{
		*//**获取动态通知格式信息*//*
		String formatMessage = this.getNoFromWebParam(type,code);
		*//**替换占位符*//*
		formatMessage = BeanUtils.fillStringByArgs(formatMessage, validateString );
		logger.info("发送短信内容："+formatMessage);
		SmsReqMsg smsReqMsg = new SmsReqMsg();
		smsReqMsg.setIntTxnSeq(IFunnc.FUNC_SMS_INTTXNSEQ);
		smsReqMsg.setIntTxnTm(DateEctonUtils.getDateByFormat("yyyyMMdd"));
		smsReqMsg.setSmsContent(HexUtils.toHex(formatMessage.getBytes("utf-8")));
		smsReqMsg.setSmsNumber(mobile);
		smsReqMsg.setTxnCode(IFunnc.FUNC_SMS);
		smsTCP.doSendMessage(smsReqMsg);
		}catch(Exception e){
			logger.error("短信发送异常：mobile="+mobile+",message="+e.getMessage());
		}
	}*/

	/* (non-Javadoc)
	 * @see com.ecton.business.common.service.IBaseService#sendMessage(java.lang.String, java.lang.String, java.lang.String[])
	 */
	/*public void sendMessage(String mobile,Object obj,String type,String code){
		try{
		*//**获取动态通知格式信息*//*
		String formatMessage = this.getNoFromWebParam(type,code);
		*//**替换占位符*//*
		formatMessage = BeanUtils.replaceMacro(formatMessage, obj);
		if(formatMessage.length()>140){
			List<String> message = new ArrayList<String>();
			message.add(formatMessage.substring(0,139));
			message.add(formatMessage.substring(140,formatMessage.length()));
			for(String meg:message){
				SmsReqMsg smsReqMsg = new SmsReqMsg();
				smsReqMsg.setIntTxnSeq(IFunnc.FUNC_SMS_INTTXNSEQ);
				smsReqMsg.setIntTxnTm(DateEctonUtils.getDateByFormat("yyyyMMdd"));
				smsReqMsg.setSmsContent(HexUtils.toHex(meg.getBytes("utf-8")));
				smsReqMsg.setSmsNumber(mobile);
				smsReqMsg.setTxnCode(IFunnc.FUNC_SMS);
				smsTCP.doSendMessage(smsReqMsg);
			}
			return;
		}
		SmsReqMsg smsReqMsg = new SmsReqMsg();
		smsReqMsg.setIntTxnSeq(IFunnc.FUNC_SMS_INTTXNSEQ);
		smsReqMsg.setIntTxnTm(DateEctonUtils.getDateByFormat("yyyyMMdd"));
		smsReqMsg.setSmsContent(HexUtils.toHex(formatMessage.getBytes("utf-8")));
		smsReqMsg.setSmsNumber(mobile);
		smsReqMsg.setTxnCode(IFunnc.FUNC_SMS);
		smsTCP.doSendMessage(smsReqMsg);
		}catch(Exception e){
			logger.error("短信发送异常：mobile="+mobile+",message="+e.getMessage());
		}
	}
*/
	@Override
	public void  saveInfoAndsendMsRepair(Map data, HttpServletRequest request){
		String isOpenRepair = ResourceUtil.getConfigByName("isOpenRepair");
		if("true".equals(isOpenRepair)){
			JdbcTemplate jdbcTemplate =  (JdbcTemplate)request.getAttribute("jdbcTemplate");
			String history_call_id = String.valueOf(data.get("history_call_id"));
			//history_call_id = "a3836bd7-eb1f-481d-a526-9a9a907baf57";
			String msgworker = ResourceUtil.getConfigByName("smsworker");
			String smscustomer = ResourceUtil.getConfigByName("smscustomer");
			String gzms = String.valueOf(data.get("gzms"));
			String zpry = String.valueOf(data.get("zpry"));
			//增加所属公司判断，短信抬头问题
			String hl_company = String.valueOf(data.get("hl_company"));//1 一卡通  2  易通
			String usercode = "";
			String telworker = "";
			String userid = String.valueOf(data.get("userid"));
			logger.info("userid="+userid+"history_call_id="+history_call_id);

			String worktelsql = "SELECT mobilephone,usercode  from lc00019999.gspuserexd where USERID = ?";
				List<Map<String,Object>> listwork = jdbcTemplate.queryForList(worktelsql,userid);
				logger.info("listwork="+listwork);

				try{
					msgworker = new String(msgworker.getBytes("ISO-8859-1"),"GBK");
					msgworker=String.format(msgworker,gzms);
					//所属公司判断
					if("1".equals(hl_company)) {//一卡通
						smscustomer = ResourceUtil.getConfigByName("smscustomeryikatong");
					}
					smscustomer = new String(smscustomer.getBytes("ISO-8859-1"),"GBK");
				if(listwork != null && listwork.size() > 0){
					usercode = String.valueOf(listwork.get(0).get("usercode"));
					telworker = String.valueOf(listwork.get(0).get("mobilephone"));
					logger.info("telworkeraaa="+telworker);
					if(StringUtil.isNotEmpty(telworker)){//电话号码为空不发送短信
						String resworker = this.sendMessageCompany(telworker,msgworker,hl_company);
						logger.info("resworkeraaa="+resworker);
						String[] valueworker = resworker.split("\\s+");
						String send_status_worker = "99";
						if(valueworker.length > 2) {
							send_status_worker = valueworker[1];
						}
						this.insertHlSmsBus(send_status_worker,telworker,msgworker,data,request);
					}

				}

			} catch (Exception ex) {

			}
			if(StringUtil.isNotEmpty(history_call_id) && !"null".equals(history_call_id)){
				String sql = "select customer_tel from hl_history_calls where id = ?";
				List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,history_call_id);
				if(list != null && list.size() > 0){
					String tel = String.valueOf(list.get(0).get("customer_tel"));
					//tel = "13583162671";
					//验证手机号码
					smscustomer=String.format(smscustomer,zpry,usercode,telworker);
					//String rescustomer = this.sendMessage(tel,smscustomer);
					logger.info("tel="+tel);
					if(StringUtil.isNotEmpty(tel)){
						String rescustomer = this.sendMessageCompany(tel,smscustomer,hl_company);
						String[] valuecustomer = rescustomer.split("\\s+");
						logger.info("valuecustomer="+rescustomer);
						String send_status_customer = "99";
						if(valuecustomer.length > 2) {
							send_status_customer = valuecustomer[1];
						}
						this.insertHlSmsBus(send_status_customer,tel,smscustomer,data,request);
					}
				}
			}
		}


	}
	private int insertHlSmsBus(String send_status,String tel,String msg,Map data, HttpServletRequest request){
		Object ob = CommUtils.mapConvert(request.getParameterMap()).get("tableName");
		String tableName = String.valueOf(ob);
		String bus_id = String.valueOf(data.get("id"));
		String pkId = UUIDGenerator.generate();
		Map<String,Object> map = new HashMap<>();
		map.put("id",pkId);
		map.put(DataBaseConstant.CREATE_DATE_TABLE, DateUtils.formatTime());
		map.put(DataBaseConstant.CREATE_BY_TABLE, ResourceUtil.getUserSystemData(DataBaseConstant.SYS_USER_CODE));
		map.put(DataBaseConstant.CREATE_NAME_TABLE, ResourceUtil.getUserSystemData(DataBaseConstant.SYS_USER_NAME));
		map.put(DataBaseConstant.SYS_ORG_CODE_TABLE, ResourceUtil.getUserSystemData(DataBaseConstant.SYS_ORG_CODE));
		map.put("busid",bus_id);
		map.put("bustype",tableName);
		map.put("send_tel",tel);
		map.put("send_content",msg);
		map.put("send_status",send_status);
		int i = dataBaseService.insertTable("hl_sms_bus",map);
		return i;

	}
	@Override
	public void  saveInfoAndsendMs(Map data, HttpServletRequest request){
		String isOpen = ResourceUtil.getConfigByName("isOpen");
		if("true".equals(isOpen)){
			JdbcTemplate jdbcTemplate =  (JdbcTemplate)request.getAttribute("jdbcTemplate");
			String history_call_id = String.valueOf(data.get("history_call_id"));
			String msg = ResourceUtil.getConfigByName("smscontent");
			try{
				msg = new String(msg.getBytes("ISO-8859-1"),"GBK");
			} catch (Exception ex) {

			}
			if(StringUtil.isNotEmpty(history_call_id) && !"null".equals(history_call_id)){
				String sql = "select customer_tel from hl_history_calls where id = ?";
				List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,history_call_id);
				if(list != null && list.size() > 0){
					String tel = String.valueOf(list.get(0).get("customer_tel"));
					//验证手机号码
					String res = this.sendMessage(tel,msg);
					String[] value = res.split("\\s+");
					String send_status = "99";
					if(value.length > 2){
						send_status = value[1];
					}
					//	Object ob =  request.getParameterMap().get("tableName");
					Object ob = CommUtils.mapConvert(request.getParameterMap()).get("tableName");
					String tableName = String.valueOf(ob);
					String bus_id = String.valueOf(data.get("id"));
					String pkId = UUIDGenerator.generate();
					Map<String,Object> map = new HashMap<>();
					map.put("id",pkId);
					map.put(DataBaseConstant.CREATE_DATE_TABLE, DateUtils.formatTime());
					map.put(DataBaseConstant.CREATE_BY_TABLE, ResourceUtil.getUserSystemData(DataBaseConstant.SYS_USER_CODE));
					map.put(DataBaseConstant.CREATE_NAME_TABLE, ResourceUtil.getUserSystemData(DataBaseConstant.SYS_USER_NAME));
					map.put(DataBaseConstant.SYS_ORG_CODE_TABLE, ResourceUtil.getUserSystemData(DataBaseConstant.SYS_ORG_CODE));
					map.put("busid",bus_id);
					map.put("bustype",tableName);
					map.put("send_tel",tel);
					map.put("send_content",msg);
					map.put("send_status",send_status);
					int i = dataBaseService.insertTable("hl_sms_bus",map);

				}
			}
		}


	}
	@Override
	public String sendMessage(String mobile,String msg){
		String res = "";
		try{
			SmsReqMsg smsReqMsg = new SmsReqMsg();
			//smsReqMsg.setIntTxnSeq(IFunnc.FUNC_SMS_INTTXNSEQ);
			//smsReqMsg.setIntTxnTm(DateEctonUtils.getDateByFormat("yyyyMMdd"));
			logger.info("发送内容转换前单独="+msg);
			logger.info("发送内容转换前msg.getBytes单独="+msg.getBytes("utf-8"));
			smsReqMsg.setSmsContent(HexUtils.toHex(msg.getBytes("utf-8")));
			logger.info("发送内容tohex后单独="+HexUtils.toHex(msg.getBytes("utf-8")));
			smsReqMsg.setSmsNumber(mobile);
			smsReqMsg.setIntTxnSeq("hotline");//系统名称
			//smsReqMsg.setTxnCode(IFunnc.FUNC_SMS);
			res = smsTCP.doSendMessage(smsReqMsg);
			logger.info("返回结果单独="+res);
		}catch(Exception e){
			logger.error("短信发送异常：mobile="+mobile+",message="+e.getMessage());
		}
		return  res;
	}
	//根据所属公司发短信
	public String sendMessageCompany(String mobile,String msg,String hl_company){
		String res = "";
		try{
			SmsReqMsg smsReqMsg = new SmsReqMsg();
			if("1".equals(hl_company)){//一卡通
				smsReqMsg.setTxnCode("ECTON");
			}else{
				smsReqMsg.setTxnCode("SMS01");
			}
			smsReqMsg.setSmsNumber(mobile);
			smsReqMsg.setIntTxnSeq("hotline");//系统名称
			//smsReqMsg.setIntTxnSeq(IFunnc.FUNC_SMS_INTTXNSEQ);
			//smsReqMsg.setIntTxnTm(DateEctonUtils.getDateByFormat("yyyyMMdd"));
			logger.info("发送内容转换前="+msg);
			logger.info("发送内容转换前msg.getBytes="+msg.getBytes("utf-8"));
			//短信内容字符超出140，乱码问题处理
			List<String> msgList = StringUtilLength.getStrList(msg,140);
			if(msgList != null  && msgList.size() > 0){
				for(int i = 0;msgList.size() > 0;i++){
					String splitMsg = msgList.get(i);
					smsReqMsg.setSmsContent(HexUtils.toHex(splitMsg.getBytes("utf-8")));
					res = smsTCP.doSendMessage(smsReqMsg);
					logger.info("splitMsg"+i+"="+splitMsg);
					logger.info("res"+i+"="+res);
				}
			}
			//wxch smsReqMsg.setSmsContent(HexUtils.toHex(msg.getBytes("utf-8")));
			//logger.info("发送内容tohex后="+HexUtils.toHex(msg.getBytes("utf-8")));

			//wxch smsReqMsg.setTxnCode(IFunnc.FUNC_SMS);
			//wxch res = smsTCP.doSendMessage(smsReqMsg);
		}catch(Exception e){
			logger.error("短信发送异常：mobile="+mobile+",message="+e.getMessage());
		}
		return  res;
	}
	/**
	 * @param smsTCP the smsTCP to set
	 */
	public void setSmsTCP(ISMS smsTCP) {
		this.smsTCP = smsTCP;
	}
}
