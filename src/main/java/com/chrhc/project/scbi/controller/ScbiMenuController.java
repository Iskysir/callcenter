package com.chrhc.project.scbi.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ListtoMenu;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.controller.core.SystemController;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.chrhc.project.scbi.utils.ReportUtils;

@Scope("prototype")
@Controller
@RequestMapping("/scbiMenuController")
public class ScbiMenuController  extends BaseController{
	private static final Logger logger = Logger.getLogger(SystemController.class);
	@Autowired
	private SystemService systemService;
	
	@RequestMapping(params = "subMenu")
	public ModelAndView scUserDesk(HttpServletRequest request) {
		String parentMenuId=request.getParameter("_scbi_parentId");
		request.setAttribute("parentMenuId", parentMenuId);
		return new ModelAndView("com/chrhc/project/scbi/scbi_submenu");
	}
	
	@RequestMapping(params = "userMenu")
	public void scUserMenu(HttpServletRequest request, HttpServletResponse response) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		ModelAndView modelAndView = new ModelAndView();
		String key = request.getParameter("key");
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			modelAndView.setView(new RedirectView("loginController.do?login"));
		} else {
			String sql = "";
			List<Map<String, Object>> list = null;
			sql = "SELECT n.parentfunctionid as pId,n.functionname as name,n.ID as id ,n.functionurl,b.iconclas,b.path from t_s_function as n INNER JOIN t_s_icon as b ON n.desk_iconid = b.ID where functionname like ? and EXISTS (SELECT distinct l.functionid from t_s_role_function as l WHERE n.ID = l.functionid AND l.roleid in ( SELECT t.roleid from t_s_role_user as t INNER JOIN t_s_base_user as m where t.userid = m.ID and m.ID = ?)) and n.functiontype='0' AND  n.id not in (select DISTINCT parentfunctionid from t_s_function where parentfunctionid is not null) ORDER BY CAST(n.functionorder AS SIGNED)  ASC";
			
			list = systemService.findForJdbc(sql, "%"+key+"%",user.getId());
			String ztreemenu = JSONHelper.array2json(list);
			ztreemenu=ReportUtils.replaceUrl(request, ztreemenu);
			// request.setAttribute("ztreemenu", ztreemenu);
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");

			try {
				PrintWriter pw = response.getWriter();

				pw.write(ztreemenu);
				pw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
