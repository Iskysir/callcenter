package org.jeecgframework.tag.core.easyui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.pojo.base.TSOperation;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 
 * @Title:AuthFilterTag
 * @description:列表按钮权限过滤
 * @author 赵俊夫
 * @date Aug 24, 2013 7:46:57 PM
 * @version V1.0
 */
public class OptAuthFilterTag extends TagSupport{
	/**列表容器的ID*/
	protected String name;
	protected String defaultValue;
	@Autowired
	private static SystemService systemService;
	public int doStartTag() throws JspException {
		
		if(!getAuthFilter()){
			try {
				if(defaultValue != null ){
					JspWriter out = this.pageContext.getOut();
					out.print(defaultValue);
					out.flush();					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return SKIP_BODY;
		}
		 	
		return EVAL_PAGE;
	}
	
	public int doEndTag() throws JspException {
		return  EVAL_PAGE;
		
	}
 
	/**
	 * 获取隐藏按钮的JS代码
	 * @param out
	 */
	@SuppressWarnings("unchecked")
	protected boolean getAuthFilter() {
		boolean isHasOpt = false ;
		if( !Globals.BUTTON_AUTHORITY_CHECK || (ResourceUtil.getSessionUserName()!= null && ResourceUtil.getSessionUserName().getUserName().equals("admin"))){
			return true ;
		}else{
			Set<String> operationCodes = (Set<String>) super.pageContext.getRequest().getAttribute(Globals.OPERATIONCODES);
			if (null!=operationCodes) {
				for (String MyoperationCode : operationCodes) {
					if (oConvertUtils.isEmpty(MyoperationCode))
						break;
					systemService = ApplicationContextUtil.getContext().getBean( SystemService.class);
					TSOperation operation = systemService.getEntity(TSOperation.class, MyoperationCode);
					if(getName().equals(operation.getOperationcode())){
						isHasOpt = true ;
						break;
					}
				}
			}
			
		}
		return isHasOpt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
}
