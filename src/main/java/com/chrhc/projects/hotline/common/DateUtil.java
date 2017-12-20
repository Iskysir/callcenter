package com.chrhc.projects.hotline.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 常见的对日期进行处理的函数
 * 
 */
public class DateUtil {

	/**
	 * 将指定格式日期形式的字符串转换成日期类型
	 * 
	 * <pre>
	 *   常用日期格式有:精确到秒的形式 "yyyy-MM-dd HH:mm:ss",精确到日的形式 "yyyy-MM-dd"
	 * 
	 *   例如，将字符串"2009-12-24 12:09:35"转换成日期类型，则需要将参数strFormat置为
	 * "yyyy-MM-dd HH:mm:ss"形式，这样就能将其转换为日期类型的了。
	 * </pre>
	 * 
	 * @param strDate
	 *            - 需要转换的日期(字符串)
	 * @param strFormat
	 *            - 需要格式化日期(字符串)的格式
	 * @return - 日期
	 */
	public static Date string2Date(String strDate, String strFormat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
			Date date = sdf.parse(strDate);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 将"yyyy-MM-dd"格式的日期字符串转换为日期类型
	 * 
	 * @param strDate
	 *            - 需要转换的日期字符串
	 * @return - 日期类型
	 */
	public static Date string2Date(String strDate) {
		return string2Date(strDate, "yyyy-MM-dd");
	}

	/**
	 * 将日期转换成指定格式的字符串类型. 常用日期格式有:精确到秒的形式 "yyyy-MM-dd HH:mm:ss",精确到日的形式
	 * "yyyy-MM-dd"
	 * 
	 * @param date
	 *            - 需要转换的日期
	 * @param format
	 *            - 日期格式
	 * @return - 转换成的字符串
	 */
	public static final String date2String(Date date, String format) {
		if (date == null || format == null)
			return "";

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String result = sdf.format(date);
		return result;

	}

	/**
	 * 将日期转换成指定格式( "yyyy-MM-dd")的字符串类型.
	 * 
	 * @param date
	 *            - 需要转换的日期
	 * @return - 转换成的字符串
	 */
	public static final String date2String(Date date) {
		return date2String(date, "yyyy-MM-dd");
	}
	
	public static final Date string2DateUS(String usString)
	{
		Date sendTime=new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);//MMM dd hh:mm:ss Z yyyy
	        try {
	        		sendTime=sdf.parse(usString);
	            //System.out.println(sdf.parse(createTime));
	          
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	        }
	     return sendTime;
	}
}
