package com.chrhc.project.sc.message.entity;
 
public class MsgConstants {
		/**
		 * 未发送
		 */
		public static final String Msg_Not_Send = "1";
		/**
		 * 已发送
		 */
		public static final String Msg_Send = "2";
		/**
		 * 发送失败
		 */
		public static final String Msg_Send_Fail = "3";
		 
		
		
		/**
		 * 短信
		 */
		public static final String Msg_Type_Sms = "sms";
		/**
		 * 邮件
		 */
		public static final String Msg_Type_Email = "email";
		/**
		 * 微信
		 */
		public static final String Msg_Type_Wechat = "wechat";
		/**
		 *百度推送
		 */
		public static final String Msg_Type_Baidu = "baidu";
		
		public static String type(String mType){
			if(mType.endsWith(MsgConstants.Msg_Type_Sms)){
				mType = "短信";
			}
			if(mType.endsWith(MsgConstants.Msg_Type_Email)){
				mType = "邮件";
			}
			if(mType.endsWith(MsgConstants.Msg_Type_Wechat)){
				mType = "微信";
			}
			if(mType.endsWith(MsgConstants.Msg_Type_Baidu)){
				mType = "手机端推送";
			}
			return mType;
		}
		
}
