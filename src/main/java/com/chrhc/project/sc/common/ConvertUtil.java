package com.chrhc.project.sc.common;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import jodd.util.ContextUtil;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * 
 */

public class ConvertUtil {

	 
	/**
	 * 增加中心坐标点
	 * @param map
	 */
	public static void addCenterxy(Map map){
		if(map.containsKey("centerxy")){
			if(map.containsKey("gisxy")){
				String gisxy = String.valueOf(map.get("gisxy"));
				String centerxy = ConvertUtil.getCenterxy(gisxy);
				map.put("centerxy", centerxy);
			}
		}
	}
	
	/**
	 * 
	 * 将字符串数组转换成字符串
	 * 
	 * @param str
	 * 
	 
	 * @return
	 */

	public static String tableName2EntityName(String tableName) {
		StringBuilder sb = new StringBuilder();
		String[] str2 = tableName.split("_");
		
		for(int i = 0;i < str2.length;i++){
			str2[i] = str2[i].replaceFirst(str2[i].substring(0,1), str2[i].substring(0,1).toUpperCase());
			sb.append(str2[i])  ;
		}
		sb.append("Entity")  ;
		return sb.toString();
	}

	/**
	 * 获取中心点坐标
	 * @param gisxy
	 * @return
	 */
	public static String getCenterxy(String gisxy){

		String centerxy = "";
		
		if(StringUtil.isNotEmpty(gisxy)){
			String[] gisxyarray = gisxy.split(",");
			int length = gisxyarray.length;
			if(length > 0){
				if(length==2){
					centerxy = gisxy;
				}else{
					if(gisxyarray[0].equals(gisxyarray[length-2]) && gisxyarray[1].equals(gisxyarray[length-1])){
						 java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(",");   
					        java.util.regex.Matcher matcher = pattern.matcher(gisxy);   
					        StringBuffer sb = new StringBuffer(gisxy.length());   
					        for(int i=1; matcher.find(); i++){
					               
					            if(i%2==0) {
					            	
					            }else{
					            	matcher.appendReplacement(sb, " "); 
					            }
					                  
					        }   
					        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
					       // SystemService systemService = wac.getBean(SystemService.class);
					        JdbcTemplate jdbcTemplate = wac.getBean(JdbcTemplate.class);
					       // System.out.println(sb);
					        matcher.appendTail(sb);
					       // System.out.println(sb); 
					        String sql = "select CONCAT((X(Centroid(GeomFromText('Polygon(("+sb.toString()+"))')))) ,',', (Y(Centroid(GeomFromText('Polygon(("+sb.toString()+"))'))))) as gisxycenter";
					        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
					       // String sql  = "select CONCAT((X(Centroid(GeomFromText('Polygon((?))')))) ,',', (Y(Centroid(GeomFromText('Polygon((?))'))))) as gisxycenter";
					       // List<Map<String, Object>> list = systemService.findForJdbc(sql, sb.toString(),sb.toString());
					        if(list != null && list.size() > 0){
					        	centerxy = String.valueOf(list.get(0).get("gisxycenter"));
					        }
					       

					}else{
						centerxy = gisxy;
					}
				}
			}
			
		}else{
			centerxy = gisxy;
		}
		return centerxy;
	}
	/*public static void main(String[] args) {
		String gisxy = "6352,8120,7024,9024,9936,7808,9128,6872,7536,7608,6352,8120";
		//String gisxy = "6352,8120";
		//String gisxy = "6352,8120,7024,9024";
		ConvertUtil.getCenterxy(gisxy);
	}*/

}