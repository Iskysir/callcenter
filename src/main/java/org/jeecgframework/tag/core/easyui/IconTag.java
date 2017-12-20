package org.jeecgframework.tag.core.easyui;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSIcon;
import org.jeecgframework.web.system.service.MutiLangServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;

public class IconTag extends TagSupport {
	@Autowired
	private SystemService systemService;
	public final static String MENU_SHOW="show";
	public final static String MENU_HIDE="hide";
	public final static String MENU_MOST="most";
	public final static String MENU_DESK="desk";
	public final static String MENU_BIZSHOW="bizshow";
	public final static String MENU_BIZHIDE="bizhide";
	private String url;
	private String iconId;
	private String functionId;
	private String type;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIconId() {
		return iconId;
	}
	public void setIconId(String iconId) {
		this.iconId = iconId;
	}
	public String getMenuId() {
		return functionId;
	}
	public void setMenuId(String menuId) {
		this.functionId = menuId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int doEndTag() throws JspTagException {
		if (this.systemService == null)
		{
			this.systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);	
		}
		try {
			StringBuffer result=new StringBuffer();
			if(org.apache.commons.lang.StringUtils.isNotEmpty(this.url)&&org.apache.commons.lang.StringUtils.isEmpty(iconId)&&org.apache.commons.lang.StringUtils.isEmpty(this.functionId))
			{
				result.append(getUrl(this.url,this.type));
			}
			if(org.apache.commons.lang.StringUtils.isNotEmpty(this.iconId)&&org.apache.commons.lang.StringUtils.isEmpty(this.functionId)&&org.apache.commons.lang.StringUtils.isEmpty(this.url))
			{
				TSIcon icon=systemService.getEntity(TSIcon.class, this.iconId);
				result.append(getUrl(icon.getIconPath(),this.type));
			}
			if(org.apache.commons.lang.StringUtils.isNotEmpty(this.functionId)&&org.apache.commons.lang.StringUtils.isEmpty(this.iconId)&&org.apache.commons.lang.StringUtils.isEmpty(this.url))
			{
				TSFunction function=systemService.getEntity(TSFunction.class, this.functionId);
				result.append(getUrl(function.getTSIconDesk().getIconPath(),this.type));
			}
			JspWriter out = this.pageContext.getOut();
			out.print(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	private String getUrl(String url,String type)
	{
		StringBuffer result=new StringBuffer();
		int index=url.lastIndexOf(".png");
		if(index>0)
		{
			String urlWithOutExtName=url.substring(0, index);
			int _index=urlWithOutExtName.lastIndexOf("_");
			if(_index>0)
			{
				String urlWithOut_=urlWithOutExtName.substring(0, _index);
				result.append(urlWithOut_);
				result.append("_");
				result.append(type);
			}
			else
			{
				result.append(urlWithOutExtName);
				result.append("_");
				result.append(type);
			}
			result.append(".png");
		}
		return result.toString();
	}
}
