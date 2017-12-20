package com.chrhc.project.sc.mostmenu.controller;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.chrhc.project.sc.certemp.service.ScCerTemplateServiceI;
import com.chrhc.project.sc.mostmenu.entity.ScMostMenuEntity;

/**   
 * @Title: Controller
 * @Description: 常用菜单
 * @author onlineGenerator
 * @date 2015-07-31 13:36:56
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scMostMenuController")
public class ScMostMenuController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScMostMenuController.class);

	@Autowired
	private ScCerTemplateServiceI scCerTemplateService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
 
	/**
	 * 常用菜单列表更新
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@RequestMapping(params = "mostMenuList")
	public void mostMenuList(HttpServletRequest request, HttpServletResponse response) {
		
		String menuId = request.getParameter("menuId");//获取菜单id
		if(StringUtils.isNotEmpty(menuId)){
			TSUser  tsUser = ResourceUtil.getSessionUserName();
			List<ScMostMenuEntity> list =  systemService.findHql(" from ScMostMenuEntity where userId = ? and menuId = ? and delFlag = '0'",tsUser.getId(),menuId);
			
			if(list != null && !list.isEmpty()){
				ScMostMenuEntity mostMenuEntity = list.get(0);
				mostMenuEntity.setCount(mostMenuEntity.getCount() + 1);
				systemService.updateEntitie(mostMenuEntity);
			}else {
				ScMostMenuEntity mostMenuEntity = new ScMostMenuEntity();
				mostMenuEntity.setCount(1);
				mostMenuEntity.setUserId(tsUser.getId());   
				mostMenuEntity.setMenuId(menuId);
				mostMenuEntity.setDelflag("0");
				systemService.save(mostMenuEntity);
			}
		}
	}

}
