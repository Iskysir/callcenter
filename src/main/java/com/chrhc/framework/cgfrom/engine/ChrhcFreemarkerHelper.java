package com.chrhc.framework.cgfrom.engine;

import java.io.StringWriter;
import java.util.Map;

import javax.servlet.ServletContext;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * @Title:FreemarkerHelper
 * @description:Freemarker引擎协助类
 * @author 赵俊夫
 * @date Jul 5, 2013 2:58:29 PM
 * @version V1.0
 */
public class ChrhcFreemarkerHelper {


	/**
	 * 解析ftl
	 * 
	 * @param tplName
	 *            模板名
	 * @param encoding
	 *            编码
	 * @param paras
	 *            参数
	 * @return
	 */
	public String parseTemplate(ServletContext servletContext, String tplName,
			String encoding, Map<String, Object> paras) {
		try {
			Configuration _tplConfig = new Configuration();
			_tplConfig.setServletContextForTemplateLoading(servletContext,
					"template");

			StringWriter swriter = new StringWriter();
			Template mytpl = null;
			mytpl = _tplConfig.getTemplate(tplName, encoding);
			mytpl.process(paras, swriter);
			return swriter.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}

	}

	public String parseTemplate(ServletContext servletContext, String tplName,
			Map<String, Object> paras) {
		return this.parseTemplate(servletContext, tplName, "utf-8", paras);
	}
}