package com.chrhc.framework.cgfrom.engine;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveModel;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.jeecgframework.core.util.PropertiesUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.common.CgAutoListConstant;
import org.jeecgframework.web.cgform.engine.DBTempletLoader;
import org.jeecgframework.web.cgform.engine.FreemarkerHelper;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

@Component("chrhctempletContext")
public class ChrhcTemplatContext {

	@Resource(name = "chrhcfreemarker")
	private Configuration freemarker;
	
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;

	private Map<String, TemplateDirectiveModel> tags;
	
	private static final String ENCODING = "UTF-8";

	private static Cache ehCache;//ehcache
	/**
	 * 系统模式：
	 * PUB-生产（使用ehcache）
	 * DEV-开发
	 */
	private static String _sysMode = null;
	static{
		PropertiesUtil util = new PropertiesUtil("sysConfig.properties");
		_sysMode = util.readProperty(CgAutoListConstant.SYS_MODE_KEY);
		if(CgAutoListConstant.SYS_MODE_PUB.equalsIgnoreCase(_sysMode)){
			ehCache = CacheManager.getInstance().getCache("dictCache");//永久缓存块
		}
	}

	@PostConstruct
	public void init() {
		if (tags == null)
			return;
		for (String key : tags.keySet()) {
			freemarker.setSharedVariable(key, tags.get(key));
		}
	}

	public Locale getLocale() {
		return freemarker.getLocale();
	}

	public Template getTemplate(String tableName, String ftlVersion,String templet ,String templet_one_many) {
		
		ChrhcDBTempletLoader dbTempletLoader =  (ChrhcDBTempletLoader) freemarker.getTemplateLoader();
		if(StringUtil.isNotEmpty(templet)) dbTempletLoader.TEMPLET = templet; 
		if(StringUtil.isNotEmpty(templet_one_many)) dbTempletLoader.TEMPLET_ONE_MANY = templet_one_many;
		
		
		Template template = null;
		if (tableName == null) {
			return null;
		}
//        update-start--Author:zhangguoming  Date:20140922 for：根据ftlVersion动态读取模板
        if (ftlVersion != null && ftlVersion.length() > 0) {
            tableName = tableName + "&ftlVersion=" + ftlVersion;
        }
//        update-end--Author:zhangguoming  Date:20140922 for：根据ftlVersion动态读取模板
        try {
			if(CgAutoListConstant.SYS_MODE_DEV.equalsIgnoreCase(_sysMode)){//开发模式
				//freemarker.setCacheStorage(new freemarker.cache.MruCacheStorage(0, 0));
				freemarker.setTemplateUpdateDelay(0);
				template = freemarker.getTemplate(tableName,freemarker.getLocale(), ENCODING);
			}else if(CgAutoListConstant.SYS_MODE_PUB.equalsIgnoreCase(_sysMode)){//生产模式（缓存）
				//获取版本号
		    	String version = cgFormFieldService.getCgFormVersionByTableName(tableName);
				template = getTemplateFromCache(tableName, ENCODING,version);
			}else{
				throw new RuntimeException("sysConfig.properties的freeMarkerMode配置错误：(PUB:生产模式，DEV:开发模式)");
			}
			return template;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * 从缓存中读取ftl模板
	 * @param template
	 * @param encoding
	 * @return
	 */
	public Template getTemplateFromCache(String tableName,String encoding,String version){
		Template template =  null;
		try {
			//cache的键：类名.方法名.参数名
			String cacheKey = FreemarkerHelper.class.getName()+".getTemplateFormCache."+tableName+"."+version;
			Element element = ehCache.get(cacheKey);
			if(element==null){
				template = freemarker.getTemplate(tableName,freemarker.getLocale(), ENCODING);
				element = new Element(cacheKey,  template);
				ehCache.put(element);
			}else{
				template = (Template)element.getObjectValue();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return template;
	}

	public Configuration getFreemarker() {
		return freemarker;
	}

	public void setFreemarker(Configuration freemarker) {
		this.freemarker = freemarker;
	}

	public Map<String, TemplateDirectiveModel> getTags() {
		return tags;
	}

	public void setTags(Map<String, TemplateDirectiveModel> tags) {
		this.tags = tags;
	}

	
}
