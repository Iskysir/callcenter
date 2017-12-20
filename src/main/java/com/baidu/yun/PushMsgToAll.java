package com.baidu.yun;

import java.text.MessageFormat;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;

public class PushMsgToAll {  
	static String apiKey = "kHG4rrCFRykDWsxB5a5zScHW";
	static String secretKey = "7an5vfeNyflj7CtwGW3zXDq5bzlSGQVC";
	static String message = "'{'title:\"{0}\",description:'{'pull:{1},data:\"{2}\",type:{3}'}}'";
    public static void main(String[] args) {
    	//PushMsg();
    	System.out.println(MessageFormat.format(message,"id",2,"101",0));
    }
    
    public static void PushMsg(String msgId ,int pull,String data , int type)  {
    	 // 1. get apiKey and secretKey from developer console
        PushKeyPair pair = new PushKeyPair(apiKey, secretKey);
        // 2. build a BaidupushClient object to access released interfaces
        BaiduPushClient pushClient = new BaiduPushClient(pair,  BaiduPushConstants.CHANNEL_REST_URL);

        // 3. register a YunLogHandler to get detail interacting information
        // in this request.
        pushClient.setChannelLogHandler(new YunLogHandler() {
            @Override
            public void onHandle(YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });

        try {
            // 4. specify request arguments
            PushMsgToAllRequest request = new PushMsgToAllRequest()
                    .addMsgExpires(new Integer(86400 * 7)).addMessageType(0)
                    .addMessage(MessageFormat.format(message , msgId, pull, data, type))
                    // 设置定时推送时间，必需超过当前时间一分钟，单位秒.实例120秒后推送
                    .addSendTime(System.currentTimeMillis() / 1000 + 120).
                    addDeviceType(3);
            // 5. http request
            PushMsgToAllResponse response = pushClient.pushMsgToAll(request);
            // Http请求返回值解析
            System.out.println("msgId: " + response.getMsgId() + ",sendTime: "     + response.getSendTime() + ",timerId: "  + response.getTimerId());
        } catch (PushClientException e) {
        	 e.printStackTrace();
        } catch (PushServerException e) {
        	 System.out.println(String.format( "requestId: %d, errorCode: %d, errorMsg: %s",   e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
        	 e.printStackTrace();
        }
    }
    
}