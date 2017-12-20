package org.jeecgframework.web.cgreport.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jeecgframework.web.cgreport.common.CgReportConstant;

import org.jeecgframework.core.util.StringUtil;

/**
 * 
 * @Title:QueryParamUtil
 * @description:动态报表查询条件处理工具
 * @author 赵俊夫
 * @date Jul 5, 2013 10:22:31 PM
 * @version V1.0
 */
public class CgReportQueryParamUtil extends  org.jeecgframework.web.cgform.util.QueryParamUtil

{
	private static Logger log = Logger.getLogger(CgReportQueryParamUtil.class);
	/**
	 * 组装查询参数
	 * @param request 请求(查询值从此处取)
	 * @param item 动态报表字段配置
	 * @param params 参数存放
	 */
	@SuppressWarnings("unchecked")
	public static void loadQueryParams(HttpServletRequest request, Map item, Map params) {
		String filedName = (String) item.get(CgReportConstant.ITEM_FIELDNAME);
		// wxch将参数名称转换为小写，解决oracle数据库字段名称都为大写，导致前台页面参数获取不到问题
		filedName = filedName.toLowerCase();
		//end
		String queryMode = (String) item.get(CgReportConstant.ITEM_QUERYMODE);
		String filedType = (String) item.get(CgReportConstant.ITEM_FIELDTYPE);
		if("single".equals(queryMode)){
			//单条件组装方式
			String value =request.getParameter(filedName);
			try {
				if(StringUtil.isEmpty(value)){
					return;
				}
				String uri = request.getQueryString();
				if(uri.contains(filedName+"=")){
					String contiansChinesevalue = new String(value.getBytes("ISO-8859-1"), "UTF-8");
					value = contiansChinesevalue;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return;
			}
			//单独处理卡号信息查询，不能like，否侧查询速度太慢
			if(!"CRDPACCNO".equalsIgnoreCase(filedName) && !"DTLPOSID".equalsIgnoreCase(filedName) && !"DTLTXCODE".equalsIgnoreCase(filedName) && !"UNITID".equalsIgnoreCase(filedName)){
				value = "%" + value + "%";
			}
			sql_inj_throw(value);
			value = applyType(filedType,value);
			if(!StringUtil.isEmpty(value)){
				/*if(value.contains("*")){
					//模糊查询
					value = value.replaceAll("\\*", "%");
					params.put(filedName, CgReportConstant.OP_LIKE+value);
				}else{
					params.put(filedName, CgReportConstant.OP_EQ+value);
				}*/
				if("CRDPACCNO".equalsIgnoreCase(filedName) || "DTLPOSID".equalsIgnoreCase(filedName) || "DTLTXCODE".equalsIgnoreCase(filedName) || "UNITID".equalsIgnoreCase(filedName)){
					params.put(filedName, CgReportConstant.OP_EQ+value);
				}else {
					params.put(filedName, CgReportConstant.OP_LIKE + value  );
				}

			}
		}else if("group".equals(queryMode)){
			//范围查询组装
			String begin = request.getParameter(filedName+"_begin");
			sql_inj_throw(begin);
			begin= applyType(filedType,begin);
			String end = request.getParameter(filedName+"_end");
			sql_inj_throw(end);
			end= applyType(filedType,end);
			if(!StringUtil.isEmpty(begin)){
				String re = CgReportConstant.OP_RQ+begin;
				if(!StringUtil.isEmpty(end)){
					re +=" AND "+filedName+CgReportConstant.OP_LQ+end;
				}
				params.put(filedName, re);
			}else if(!StringUtil.isEmpty(end)){
				String re = CgReportConstant.OP_LQ+end;
				params.put(filedName, re);
			}
		}
	}
	
}
