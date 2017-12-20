package org.jeecgframework.core.aop;

import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * 文件名称为：URLAvailability.java
 * 
 * 文件功能简述： 描述一个URL或图片地址是否有效
 * 
 * @author ChenTao
 * @version
 * @time 2008-5-29 上午10:00:35
 * @copyright
 */
@SuppressWarnings("unused")
public class URLAvailability {
	private static Logger logger = Logger.getLogger(URLAvailability.class);
	private static URL urlStr;
	private static HttpURLConnection connection;
	private static int state = -1;

	/**
	 * 功能描述 : 检测当前URL是否可连接或是否有效, 最多连接网络 5 次, 如果 5 次都不成功说明该地址不存在或视为无效地址.
	 * 
	 * @param url
	 *            指定URL网络地址
	 * 
	 * @return String
	 */
	public static synchronized boolean isConnect(String url) {
		int counts = 0;
		if (url == null || url.length() <= 0) {
			return false;
		}
		while (counts < 5) {
			try {
				urlStr = new URL(url);
				connection = (HttpURLConnection) urlStr.openConnection();
				state = connection.getResponseCode();
				if (state == 200) {
					return true;
				}
				break;
			} catch (Exception ex) {
				counts++;
				//logger.info("loop :" + counts);
				continue;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(new URLAvailability().isConnect("http://www.jeewx.com/jeewx/licController.do"));
	}
}
