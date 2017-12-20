package org.jeecgframework.web.cgform.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jeecgframework.core.util.DBTypeUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.common.CgAutoListConstant;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;

/**
 * 
 * @Title:QueryParamUtil
 * @description:列表查询条件处理工具
 * @author 赵俊夫
 * @date Jul 5, 2013 10:22:31 PM
 * @version V1.0
 */
public class QueryParamUtil {
	/**
	 * 组装查询参数
	 * @param request 请求(查询值从此处取)
	 * @param b CgFormFieldEntity实体
	 * @param params 参数存放
	 */
	@SuppressWarnings("unchecked")
	public static void loadQueryParams(HttpServletRequest request, CgFormFieldEntity b, Map params) {
		if("single".equals(b.getQueryMode())){
			//单条件组装方式
			String value = request.getParameter(b.getFieldName());
			if(StringUtil.isEmpty(value)){
				return;
			}
			/*try {
				if(StringUtil.isEmpty(value)){
					return;
				}
				String uri = request.getQueryString();
				if(uri.contains(b.getFieldName()+"=")){
					String contiansChinesevalue = new String(value.getBytes("ISO-8859-1"), "UTF-8");
					value = contiansChinesevalue;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return;
			} */
			value=value.trim();
			sql_inj_throw(value);
			value = applyType(b.getType(),value);
			if(!StringUtil.isEmpty(value)){
				if(value.contains("*")){
					//模糊查询
					value = value.replaceAll("\\*", "%");
					params.put(b.getFieldName(), CgAutoListConstant.OP_LIKE+value);
				}else{
					String gisAttr = b.getGisAttr();
					if(StringUtil.isNotEmpty(gisAttr)){
						Map jsonConfig=JSONHelper.fromJsonToObject(b.getGisAttr(), Map.class);
						if(jsonConfig != null ){
							if(jsonConfig.containsKey("queryType"))
							{
								String queryType=(String)jsonConfig.get("queryType");
								if(queryType.equals("*"))
								{
									value="'"+value.replaceAll("'", "")+"%'";
								}
								if(queryType.equals("**"))
								{
									value="'%"+value.replaceAll("'", "")+"%'";
								}
								params.put(b.getFieldName(), CgAutoListConstant.OP_LIKE+value);
							}
							else
							{
								params.put(b.getFieldName(), CgAutoListConstant.OP_EQ+value);
							}	
						}
					}
					else
					{
						params.put(b.getFieldName(), CgAutoListConstant.OP_EQ+value);
					}
				
					
				}
			}
		}else if("group".equals(b.getQueryMode())){
			//范围查询组装
			String begin = request.getParameter(b.getFieldName()+"_begin");
			String end = request.getParameter(b.getFieldName()+"_end");
			String begin_ = begin ,end_ = end;
			if("date".equalsIgnoreCase(b.getType())){
				if("date".equalsIgnoreCase(b.getShowType())){
					begin_ = begin + " 00:00:00";
					end_ = end + " 23:59:59";
				}
			}
	 
			sql_inj_throw(begin);
			sql_inj_throw(end);
			begin_ = applyType(b.getType(),begin_);
			end_ = applyType(b.getType(),end_);
			
			if(!StringUtil.isEmpty(begin)){
				String re = CgAutoListConstant.OP_RQ + begin_;
				if(!StringUtil.isEmpty(end)){
					re +=" AND "+b.getFieldName()+ CgAutoListConstant.OP_LQ + end_;
				}
				params.put(b.getFieldName(), re);
			}else if(!StringUtil.isEmpty(end)){
				String re = CgAutoListConstant.OP_LQ + end_;
				params.put(b.getFieldName(), re);
			}
		}
	}
	/**
	 * 根据字段类型 进行处理
	 * @param fieldType 字段类型
	 * @param value 值
	 * @return
	 */
	public static String applyType(String fieldType, String value) {
		if(!StringUtil.isEmpty(value)){
			String result = "";
			if(CgAutoListConstant.TYPE_STRING.equalsIgnoreCase(fieldType)){
				result = "'" +value+ "'";
			}else if(CgAutoListConstant.TYPE_DATE.equalsIgnoreCase(fieldType)){
				result = getDateFunction(value, "yyyy-MM-dd");
			}else if(CgAutoListConstant.TYPE_DOUBLE.equalsIgnoreCase(fieldType)){
				result = value;
			}else if(CgAutoListConstant.TYPE_INTEGER.equalsIgnoreCase(fieldType)){
				//result = value;
				//处理int 查询输入字母报错问题，mysql拼接单引号没有问题
				result = "'" +value+ "'";
			}else{
				result = "'" +value+ "'";
			}
			return result;
		}else{
			return "";
		}
	}
	
	/**
	 * 防止sql注入
	 * @param str 输入sql
	 * @return 是否存在注入关键字
	 */
	public static boolean sql_inj(String str) {
		if(StringUtil.isEmpty(str)){
			return false;
		}
		String inj_str = "'|and|exec|insert|select|delete|update|count|chr|mid|master|truncate|char|declare|;|or|+|,";
//		String inj_str = "'|and|exec|insert|select|delete|update|count|chr|mid|master|truncate|char|declare|;|or|-|+|,";
		String inj_stra[] = inj_str.split("\\|");
		for (int i = 0; i < inj_stra.length; i++) {
			if (str.indexOf(" "+inj_stra[i]+" ") >= 0) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 当存在sql注入时抛异常
	 * @param str 输入sql
	 */
	public static void sql_inj_throw(String str){
		if(sql_inj(str)){
			throw new RuntimeException("请注意,填入的参数可能存在SQL注入!");
		}
	}
	/**
	 * 获取数据库类型
	 * @return
	 */
	public static String getDBType(){
		return DBTypeUtil.getDBType();
	}
	/**
	 * 跨数据库返回日期函数
	 * @param dateStr 日期值
	 * @param dateFormat 日期格式
	 * @return 日期函数
	 */
	public static String getDateFunction(String dateStr,String dateFormat){
		String dbType = getDBType();
		String dateFunction = "";
		if("mysql".equalsIgnoreCase(dbType)){
			//mysql日期函数
			dateFunction = "'"+dateStr+"'";
		}else if("oracle".equalsIgnoreCase(dbType)){
			//oracle日期函数
			//dateFunction = "TO_DATE('"+dateStr+"','"+dateFormat+"')";
			//160818修改oracle 日期函数出现日期格式图片在转换整个输入字符串之前结束问题
			dateFunction = "TO_DATE('"+dateStr+"','"+dateFormat+" HH24:MI:SS')";
		}else if("sqlserver".equalsIgnoreCase(dbType)){
			//sqlserver日期函数
			dateFunction = "(CONVERT(VARCHAR,'"+dateStr+"') as DATETIME)";
		}else if("postgres".equalsIgnoreCase(dbType)){
			//postgres日期函数
			dateFunction = "'"+dateStr+"'::date ";
		}else{
			dateFunction = dateStr;
		}
		return dateFunction;
	}
	/**
	 * 将结果集转化为列表json格式
	 * @param result 结果集
	 * @param size 总大小
	 * @return 处理好的json格式
	 */
	@SuppressWarnings("unchecked")
	public static String getJson(List<Map<String, Object>> result,Long size){
		JSONObject main = new JSONObject();
		JSONArray rows = new JSONArray();
		main.put("total",size );
		for(Map m:result){
			JSONObject item = new JSONObject();
			Iterator  it =m.keySet().iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				String value =String.valueOf(m.get(key));
				Object rawValue=m.get(key);
				if(m.get(key) instanceof Double)
				{
					DecimalFormat format = new DecimalFormat("############.#####");
					value = format.format(rawValue);
				}
				
				key = key.toLowerCase();
				if(StringUtil.isNotEmpty(value)&&(value.length()==10||value.length()==19||value.length()==21))
				{
					if((key.contains("time")||key.contains("date"))){
						value = DateUtils.datatimeFormatCanNoTime(value);
					}
					else
					{
						if(DateUtils.isDateString(value))
						{
							value = DateUtils.datatimeFormatCanNoTime(value);
						}
					}
				}
				if("null".equals(value))
				{
					value="";
				}
				
				item.put(key,value );
			}
			rows.add(item);
		}
		main.put("rows", rows);
		return main.toString();
	}

	
	
}
