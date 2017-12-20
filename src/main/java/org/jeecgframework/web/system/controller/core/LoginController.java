package org.jeecgframework.web.system.controller.core;

import com.alibaba.fastjson.JSONObject;
import com.chrhc.project.sc.message.service.ScMessageServiceI;
import com.chrhc.project.sc.mostmenu.entity.ScMostMenuEntity;
import com.chrhc.project.scbi.utils.ReportUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.extend.datasource.DataSourceContextHolder;
import org.jeecgframework.core.extend.datasource.DataSourceType;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.MutiLangServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 登陆初始化控制器
 * 
 * @author zxy
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/loginController")
public class LoginController extends BaseController {
	private Logger log = Logger.getLogger(LoginController.class);
	private SystemService systemService;
	private UserService userService;
	private String message = null;

	@Autowired
	private MutiLangServiceI mutiLangService;

	@Autowired
	private ScMessageServiceI scMessageService;
	
	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {

		this.userService = userService;
	}

	@RequestMapping(params = "goPwdInit")
	public String goPwdInit() {
		return "login/pwd_init";
	}

	/**
	 * admin账户密码初始化
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "pwdInit")
	public ModelAndView pwdInit(HttpServletRequest request) {
		ModelAndView modelAndView = null;
		TSUser user = new TSUser();
		user.setUserName("admin");
		String newPwd = "111111";
		userService.pwdInit(user, newPwd);
		modelAndView = new ModelAndView(new RedirectView(
				"loginController.do?login"));
		return modelAndView;
	}

	/**
	 * 检查用户名称
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "checkuser")
	@ResponseBody
	public AjaxJson checkuser(TSUser user, HttpServletRequest req) throws IOException{
		HttpSession session = ContextHolderUtils.getSession();
		String extension=req.getParameter("extension");
		DataSourceContextHolder
				.setDataSourceType(DataSourceType.dataSource_jeecg);
		AjaxJson j = new AjaxJson();
		// update-begin--Author:ken Date:20140629 for：添加语言选择
		if (req.getParameter("langCode") != null) {
			req.getSession().setAttribute("lang", req.getParameter("langCode"));
		}

		// update-end--Author:zhangguoming Date:20140226 for：添加验证码
		//int users = userService.getList(TSUser.class).size();
		long users = userService.getCountForJdbc("select count(1) from t_s_base_user");

		if (users == 0) {
			j.setMsg("a");
			j.setSuccess(false);
		} else {
			//简单单点验证
			JSONObject jresult=userService.ectonNcSingleLogin(user);
			String flag = String.valueOf(jresult.get("FLAG"));
			log.info("ncjresult="+jresult);
			if(!"Y".equals(flag)){
				j.setSuccess(false);
				j.setMsg("登录失败,请检查NC接口");
				return j;
			}
			/*
			JSONObject jresult=userService.ectonSingleLogin(user);
			if(!jresult.getBoolean("result")){
				j.setSuccess(false);
				j.setMsg(jresult.getString("message"));
				return j;
			}*/
			TSUser u = userService.checkUserExits(user);

			// update-begin--Author:zhangguoming Date:20140617 for：空指针bug
			if (u == null) {
				j.setMsg(mutiLangService
						.getLang("common.username.or.password.error"));
				j.setSuccess(false);
				return j;
			} else {
				// update-begin--Author:zhangguoming Date:20140226 for：添加验证码

				// update-end--Author:ken Date:20140629 for：添加语言选择
				// update-begin--Author:zhangguoming Date:20140226 for：添加验证码
				
				  String randCode = req.getParameter("randCode"); if
				 (StringUtils.isEmpty(randCode)) {
				  j.setMsg(mutiLangService.getLang("common.enter.verifycode"));
				 j.setSuccess(false); return j; } else if
				 (!randCode.equalsIgnoreCase
				 (String.valueOf(session.getAttribute("randCode")))) { 
					// todo "randCode"和验证码servlet中该变量一样，通过统一的系统常量配置比较好，暂时不知道系统常量放在什么地方合适
				 j.setMsg(mutiLangService.getLang("common.verifycode.error"));
				  j.setSuccess(false); return j; }
				 
				// update-end--Author:zhangguoming Date:20140226 for：添加验证码
			}
			// update-end--Author:zhangguoming Date:20140617 for：空指针bug
			TSUser u2 = userService.getEntity(TSUser.class, u.getId());
			if (u != null && u2.getStatus() != Globals.User_Forbidden) {
				// if (user.getUserKey().equals(u.getUserKey())) {

				if (true) {
					// update-start-Author:zhangguoming Date:20140825
					// for：处理用户有多个组织机构的情况，以弹出框的形式让用户选择
					Map<String, Object> attrMap = new HashMap<String, Object>();
					j.setAttributes(attrMap);

					String orgId = req.getParameter("orgId");
					if (oConvertUtils.isEmpty(orgId)) { // 没有传组织机构参数，则获取当前用户的组织机构
						Long orgNum = systemService
								.getCountForJdbc("select count(1) from t_s_user_org where user_id = '"
										+ u.getId() + "'");
						if (orgNum > 1) {
							attrMap.put("orgNum", orgNum);
							attrMap.put("user", u2);
						} else {
							Map<String, Object> userOrgMap = systemService
									.findOneForJdbc(
											"select org_id as orgId from t_s_user_org where user_id=?",
											u2.getId());
							saveLoginSuccessInfo(req, u2,
									(String) userOrgMap.get("orgId"),extension);
						}
					} else {
						attrMap.put("orgNum", 1);

						saveLoginSuccessInfo(req, u2, orgId,extension);
					}
					// update-end-Author:zhangguoming Date:20140825
					// for：处理用户有多个组织机构的情况，以弹出框的形式让用户选择
				} else {
					j.setMsg(mutiLangService.getLang("common.check.shield"));
					j.setSuccess(false);
				}
			} else {
				if (u2.getStatus() == Globals.User_Forbidden) {
					j.setMsg(mutiLangService.getLang("common.user.lock.error"));
					j.setSuccess(false);
				} else {
					j.setMsg(mutiLangService
							.getLang("common.username.or.password.error"));
					j.setSuccess(false);
				}
			}
		}

		return j;
	}

	// update-start-Author:zhangguoming Date:20140825 for：记录用户登录的相关信息
	/**
	 * 保存用户登录的信息，并将当前登录用户的组织机构赋值到用户实体中；
	 * 
	 * @param req
	 *            request
	 * @param user
	 *            当前登录用户
	 * @param orgId
	 *            组织主键
	 */
	private void saveLoginSuccessInfo(HttpServletRequest req, TSUser user,
			String orgId,String extension) {
		if(StringUtil.isEmpty(orgId)){
			orgId = "dept20141023insertAAATFaAAFAAAguWAAa";//如果orgid为null，则设置为运营中心
		}
		TSDepart currentDepart = systemService.get(TSDepart.class, orgId);
		user.setCurrentDepart(currentDepart);
		HttpSession session = ContextHolderUtils.getSession();
		message = mutiLangService.getLang("common.user") + ": "
				+ user.getUserName() + "[" + currentDepart.getDepartname()
				+ "]" + mutiLangService.getLang("common.login.success");
		List<Map<String,Object>> agentStatusList=this.systemService.findForJdbc("select * from HL_AGENTCONF where USER_ID='"+user.getId()+"'");
		if(agentStatusList.size()>0)
		{
			Map<String,Object> map=agentStatusList.get(0);
			if(map!=null&&StringUtil.isNotEmpty(extension)&&extension.length()==4)
			{
				//map.put("AGENTID",extension);
				map.put("EXTENSION",extension);
			}
			session.setAttribute("agentMap",map);
		}
		Client client = new Client();
		client.setIp(IpUtil.getIpAddr(req));
		client.setLogindatetime(new Date());
		String singlelogpwd = req.getParameter("password");
		client.setSingleloginpwd(singlelogpwd);
		client.setUser(user);
		ClientManager.getInstance().addClinet(session.getId(), client);
		/*List<Map<String,Object>> list= systemService.findForJdbc("select gisxy from sc_sqjj where sys_org_code = '"+ currentDepart.getOrgCode() + "'", null);
		 if(list.size()>0)
		 {
			 Map<String,Object> currentGisxy=list.get(0);
			 String gisxy=String.valueOf(currentGisxy.get("gisxy"));
			 session.setAttribute("currentGisxy", gisxy);
		 }*/
		// 添加登陆日志
		systemService.addLog(message, Globals.Log_Type_LOGIN,
				Globals.Log_Leavel_INFO);
	}

	// update-end-Author:zhangguoming Date:20140825 for：记录用户登录的相关信息

	/**
	 * 获取sessionId
	 */
	@RequestMapping(params = "getSession")
	@ResponseBody
	public AjaxJson getSession(ModelMap modelMap, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		DataSourceContextHolder
				.setDataSourceType(DataSourceType.dataSource_jeecg);
		String userName = req.getParameter("username");
		String passWord = req.getParameter("password");
		// System.out.println("userName = " + userName + "password = " +
		// passWord);
		TSUser user1 = new TSUser();
		user1.setUserName(userName);
		user1.setPassword(passWord);
		TSUser u = userService.checkUserExits(user1);
		if (u != null) {
			if (true) {
				TSUser u2 = userService.getEntity(TSUser.class, u.getId());
				TSUser cfe=new TSUser();
				cfe.setId(u2.getId());
				cfe.setUserName(u2.getUserName());
				cfe.setRealName(u2.getRealName());
				cfe.setEmail(u2.getEmail());
				cfe.setMobilePhone(u2.getMobilePhone());
				cfe.setOfficePhone(u2.getOfficePhone());
				cfe.setActivitiSync(u2.getActivitiSync());
				cfe.setStatus(u2.getStatus());
				 List<TSRoleUser> roleUser = systemService.findByProperty(TSRoleUser.class, "TSUser.id", u2.getId());
                 if (roleUser.size() > 0) {
                     String roleName = "";
                     String roleIds="";
                     for (TSRoleUser ru : roleUser) {
                         roleName += ru.getTSRole().getRoleName() + ",";
                         roleIds+=ru.getTSRole().getId()+",";
                     }
                     roleName = roleName.substring(0, roleName.length() - 1);
                     roleIds=roleIds.substring(0, roleIds.length() - 1);
                     cfe.setRoleid(roleIds);
                     cfe.setRoleName(roleName);
                     cfe.setUserKey(roleName);
                 }
                 if(u2.getUserOrgList().size()>0)
                 {
                	 String orgListIds="";
                     String orgListCodes="";
                     for (TSUserOrg ru : u2.getUserOrgList()) {
                     	orgListIds += ru.getTsDepart().getId() + ",";
                     	orgListCodes+=ru.getTsDepart().getOrgCode()+",";
                     }
                     orgListIds = orgListIds.substring(0, orgListIds.length() - 1);
                     orgListCodes=orgListCodes.substring(0, orgListCodes.length() - 1);
                     cfe.setOrgIds(orgListIds);
                     cfe.setOrgCodes(orgListCodes);
                 }
				
				List<Map<String,Object>> functionList = null;
				Map<String, Object> attrMap = new HashMap<String, Object>();
				attrMap.put("user", cfe);
				j.setAttributes(attrMap);
				//attrMap.put("user", u);
				Enumeration e = req.getHeaderNames();
			
				while (e.hasMoreElements()) {
					String name = (String) e.nextElement();
					String value = req.getHeader(name);
					if ("user-agent".equalsIgnoreCase(name)) {
						/*if (value.toUpperCase().contains("ANDROID")
								|| value.toUpperCase().contains("MOBILE")) {*/
						if(true){
							String getMobilePrvl = "select * from t_s_function where parentfunctionid='40280e814f4990e8014f4a0d958c0011' and id in (select t_s_role_function.functionid from  t_s_role_function where roleid in (select t_s_role_user.roleid from t_s_role_user where t_s_role_user.userid='"
									+ u.getId() + "'))  order by functionorder asc";
							functionList = this.systemService.findForJdbc(getMobilePrvl, null);
							attrMap.put("prvls", functionList);
							//j.setObj(functionList);
						}
					}
					//System.out.println(name + " = " + value);
				}
				String orgId = req.getParameter("orgId");
				String sessionId = "";
				if (oConvertUtils.isEmpty(orgId)) { // 没有传组织机构参数，则获取当前用户的组织机构
					/*
					 * Long orgNum = systemService.getCountForJdbc(
					 * "select count(1) from t_s_user_org where user_id = '" +
					 * u.getId() + "'"); if (orgNum > 1) { attrMap.put("orgNum",
					 * orgNum); attrMap.put("user", u2); } else { Map<String,
					 * Object> userOrgMap = systemService.findOneForJdbc(
					 * "select org_id as orgId from t_s_user_org where user_id=?"
					 * , u2.getId()); sessionId =
					 * saveLoginSuccessInfoMobile(req, u2, (String)
					 * userOrgMap.get("orgId")); }
					 */
					List<Map<String, Object>> userOrgMapList = systemService
							.findForJdbc(
									"select   org_id as orgId from t_s_user_org where user_id=?",
									u2.getId());
					if (userOrgMapList != null && userOrgMapList.size() > 0) {
						String orgIda = String.valueOf(userOrgMapList.get(0)
								.get("orgId"));
						sessionId = saveLoginSuccessInfoMobile(req, u2, orgIda);

					}

				} else {
					attrMap.put("orgNum", 1);

					sessionId = this.saveLoginSuccessInfoMobile(req, u2, orgId);
				}
				attrMap.put("sessionId", sessionId);
			} else {
				j.setMsg(mutiLangService.getLang("common.check.shield"));
				j.setSuccess(false);
			}
		} else {
			j.setMsg(mutiLangService
					.getLang("common.username.or.password.error"));
			j.setSuccess(false);
		}

		return j;
	}

	/**
	 * 保存用户登录的信息，并将当前登录用户的组织机构赋值到用户实体中； 手机端使用
	 * 
	 * @param req
	 *            request
	 * @param user
	 *            当前登录用户
	 * @param orgId
	 *            组织主键
	 */
	private String saveLoginSuccessInfoMobile(HttpServletRequest req,
			TSUser user, String orgId) {
		TSDepart currentDepart = systemService.get(TSDepart.class, orgId);
		user.setCurrentDepart(currentDepart);

		HttpSession session = req.getSession();
		message = mutiLangService.getLang("common.user") + ": "
				+ user.getUserName() + "[" + currentDepart.getDepartname()
				+ "]" + mutiLangService.getLang("common.login.success");

		Client client = new Client();
		client.setIp(IpUtil.getIpAddr(req));
		client.setLogindatetime(new Date());
		client.setUser(user);
		ClientManager.getInstance().addClinet(session.getId(), client);
		// 添加登陆日志
		systemService.addLog(message, Globals.Log_Type_MobileLOGIN,
				Globals.Log_Leavel_INFO);
		return session.getId();
	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "login")
	public String login(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException  {
		DataSourceContextHolder
				.setDataSourceType(DataSourceType.dataSource_jeecg);
		//System.out.println("123123");
		// CAS登陆验证 doukai add start
	  /*  Map<String, Object> params = AssertionHolder.getAssertion()
	    	      .getPrincipal().getAttributes();
	    	    
	    	    TSUser userFromCas = new TSUser();
	    	    
	    	    String alias = (String)params.get("alias");
	    	    String username = AssertionHolder.getAssertion().getPrincipal().getName();
	    	    
	    	    boolean hasNotBind = true;
	    	    if (StringUtils.isNotBlank(alias))
	    	    {
	    	      String[] aliasArray = alias.split(",");
	    	      String[] arrayOfString1;
	    	      int j = (arrayOfString1 = aliasArray).length;
	    	      for (int i = 0; i < j; i++)
	    	      {
	    	        String alia = arrayOfString1[i];
	    	        if (!StringUtils.isBlank(alia))
	    	        {
	    	          userFromCas.setUserName(alia);
	    	          userFromCas = this.userService.checkUserExitsForCas(userFromCas);
	    	          if (userFromCas != null)
	    	          {
	    	        	  Map<String, Object> userOrgMap = systemService
									.findOneForJdbc(
											"select org_id as orgId from t_s_user_org where user_id=?",
											userFromCas.getId());
	    	        	  TSDepart currentDepart = systemService.get(TSDepart.class, userOrgMap.get("orgId").toString());
	    	        	  userFromCas.setCurrentDepart(currentDepart);
	    	            hasNotBind = false;
	    	            break;
	    	          }
	    	        }
	    	      }
	    	    }
	    	    ServletContext application = request.getSession().getServletContext();
	    	    
	    	    String uiapServer = application.getInitParameter("uiapServer");
	    	    String bizcode = application.getInitParameter("bizcode");
	    	    if (hasNotBind)
	    	    {
	    	      response.sendRedirect(uiapServer + "security/profile/setalias/" + bizcode + "/" + username);
	    	      return null;
	    	    }
	    	    HttpSession session = ContextHolderUtils.getSession();
	    	    
	    	    Client client = new Client();
	    	    client.setIp(IpUtil.getIpAddr(request));
	    	    client.setLogindatetime(new Date());
	    	    client.setUser(userFromCas);
	    	    ClientManager.getInstance().addClinet(session.getId(), client);
		
	    	 // CAS登陆验证 doukai add end*/
		
		
		
		TSUser user = ResourceUtil.getSessionUserName();
//		String code = ResourceUtil.getUserSystemData(DataBaseConstant.SYS_ORG_CODE);
		String roles = "";
		if (user != null) {
			List<TSRoleUser> rUsers = systemService.findByProperty(
					TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				roles += role.getRoleName() + ",";
			}
			if (roles.length() > 0) {
				roles = roles.substring(0, roles.length() - 1);
			}
			modelMap.put("roleName", roles);
			modelMap.put("userName", user.getUserName());
			modelMap.put("realName", user.getRealName());
			// update-start-Author:zhangguoming Date:20140914 for：获取当前登录用户的组织机构
			modelMap.put("currentOrgName", ClientManager.getInstance()
					.getClient().getUser().getCurrentDepart().getDepartname());
			// update-end-Author:zhangguoming Date:20140914 for：获取当前登录用户的组织机构
			request.getSession().setAttribute("CKFinder_UserRole", "admin");

			// request.getSession().setAttribute("lang", "en");

			// 默认风格
			String indexStyle = "default";
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
					continue;
				}
				if (cookie.getName().equalsIgnoreCase("JEECGINDEXSTYLE")) {
					indexStyle = cookie.getValue();
				}
			}
			// 要添加自己的风格，复制下面三行即可
			if (StringUtils.isNotEmpty(indexStyle)
					&& indexStyle.equalsIgnoreCase("bootstrap")) {
				return "main/bootstrap_main";
			}
			if (StringUtils.isNotEmpty(indexStyle)
					&& indexStyle.equalsIgnoreCase("shortcut")) {
				return "main/shortcut_main";
			}

			// update-start--Author:gaofeng Date:2014-01-24 for:新增首页风格按钮选项
			if (StringUtils.isNotEmpty(indexStyle)
					&& indexStyle.equalsIgnoreCase("sliding")) {
				return "main/sliding_main";
			}
			// update-start--Author:gaofeng Date:2014-01-24 for:新增首页风格按钮选项
			
//			String code = ResourceUtil.getUserSystemData(DataBaseConstant.SYS_ORG_CODE);
			

			List<String> functionid = systemService.findListbySql("select functionid from t_s_role_function where roleid in (select roleid from t_s_role_user where userid='"+user.getId()+"')"); 
//			List<String> ids = scMessageService.findListbySql("select menu_id from sc_most_menu where sys_org_code = '"+code+"'");
			List<ScMostMenuEntity> mosts =  systemService.findHql(" from ScMostMenuEntity where userId = '"+user.getId()+"'" ); 
			for(int i=0; i<mosts.size();i++){
				ScMostMenuEntity most = mosts.get(i);
				String id = most.getMenuId();
				if(!functionid.contains(id)){
					most.setDelflag("1");
//					systemService.delete(most);
				}else{
					most.setDelflag("0");
				}
			}
			
			List<ScMostMenuEntity> list = new ArrayList<>();
			List<TSFunction> tsfList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				TSFunction tsf = systemService.get(TSFunction.class, list
						.get(i).getMenuId());
				if (tsf != null) {
					tsfList.add(tsf);
				}
			}

			request.setAttribute("mostMenuList", tsfList);
			
			//zxy 2015年10月22日 获取系统字典sysdict中的信息，根据index值跳转 begin
			String mainJsp="main/sc_main";
			List<DictEntity> listDict=this.systemService.getSystemDict("sysdict");
			for(int i=0;listDict!=null&&i<listDict.size();i++)
			{
				DictEntity oneEntity=listDict.get(i);
				if(oneEntity.getTypecode().equals("index"))
				{
					mainJsp=oneEntity.getTypename();
				}
				ContextHolderUtils.getSession().setAttribute(oneEntity.getTypecode(), oneEntity.getTypename());
			}
			String _index=request.getParameter("_index");
			if(StringUtils.isNotEmpty(_index))
			{
				mainJsp=_index;
			}
			//zxy end
			List<Map<String,Object>> listDept= systemService.findForJdbc("select gisxy from sc_sqjj where sqbh = '"+ ClientManager.getInstance()
					.getClient().getUser().getCurrentDepart().getOrgCode() + "'", null);
			 if(listDept.size()>0)
			 {
				 Map<String,Object> currentGisxy=listDept.get(0);
				 String gisxy=String.valueOf(currentGisxy.get("gisxy"));
				 request.getSession().setAttribute("currentGisxy", gisxy);
			 }
			return "main/hotline_main";
		} else {
			return "login/login";
		}

	}

	/**
	 * 退出系统
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response)
		    throws IOException{
		HttpSession session = ContextHolderUtils.getSession();
		TSUser user = ResourceUtil.getSessionUserName();
		systemService.addLog("用户" + user.getUserName() + "已退出",
				Globals.Log_Type_EXIT, Globals.Log_Leavel_INFO);
		ClientManager.getInstance().removeClinet(session.getId());
		ModelAndView modelAndView = new ModelAndView(new RedirectView(
				"loginController.do?login"));
		//session.invalidate();
		request.getSession().invalidate();
		return modelAndView;
		
	   /* HttpSession session = ContextHolderUtils.getSession();
	    TSUser user = ResourceUtil.getSessionUserName();
	    this.systemService.addLog("用户" + user.getUserName() + "已退出", 
	      Globals.Log_Type_EXIT, Globals.Log_Leavel_INFO);
	    ClientManager.getInstance().removeClinet(session.getId());
	    
	    request.getSession().invalidate();
	    
	    ServletContext application = request.getSession().getServletContext();
	    response.sendRedirect(application.getInitParameter("casServerLogoutUrl") + 
	      "?service=" + application.getInitParameter("serverName"));
	    
	    return null;*/
	}

	/**
	 * 菜单跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "left")
	public ModelAndView left(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		ModelAndView modelAndView = new ModelAndView();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			modelAndView.setView(new RedirectView("loginController.do?login"));
		} else {
			List<TSConfig> configs = userService.loadAll(TSConfig.class);
			for (TSConfig tsConfig : configs) {
				request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
			}
			modelAndView.setViewName("main/left");

			// end

			request.setAttribute("menuMap", getFunctionMap(user));
		}
		return modelAndView;
	}

	/**
	 * 菜单跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "leftnew")
	public ModelAndView leftnew(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		ModelAndView modelAndView = new ModelAndView();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			modelAndView.setView(new RedirectView("loginController.do?login"));
		} else {
			List<TSConfig> configs = userService.loadAll(TSConfig.class);
			for (TSConfig tsConfig : configs) {
				request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
			}

			List<ScMostMenuEntity> list = new ArrayList<>();
			List<TSFunction> tsfList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				TSFunction tsf = systemService.get(TSFunction.class, list
						.get(i).getMenuId());
				if (tsf != null) {
					tsfList.add(tsf);
				}
			}

			request.setAttribute("mostMenuList", tsfList);

			modelAndView.setViewName("main/leftnew");

		}
		return modelAndView;
	}

	@RequestMapping(params = "getTree")
	public void getTree(HttpServletRequest request, HttpServletResponse response) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		ModelAndView modelAndView = new ModelAndView();
		String id = request.getParameter("id");
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			modelAndView.setView(new RedirectView("loginController.do?login"));
		} else {
			// 添加ztree显示菜单
			String sql = "";
			List<Map<String, Object>> list = null;
			if (StringUtil.isEmpty(id)) {
				sql = "SELECT n.parentfunctionid as pId,n.functionname as name,n.ID as id ,n.functionurl,b.iconclas,b.path from t_s_function  n INNER JOIN t_s_icon  b ON n.desk_iconid = b.ID where EXISTS (SELECT distinct l.functionid from t_s_role_function l WHERE n.ID = l.functionid AND l.roleid in ( SELECT t.roleid from t_s_role_user  t INNER JOIN t_s_base_user  m on t.userid = m.ID where  m.ID = ?)) and n.functiontype='0' AND  n.parentfunctionid is null ORDER BY To_number(n.functionorder,'999999999') ASC";
				list = systemService.findForJdbc(sql, user.getId());
			} else {
				sql = "SELECT n.parentfunctionid as pId,n.functionname as name,n.ID  as id,n.functionurl,b.iconclas,b.path from  t_s_function  n INNER JOIN t_s_icon  b ON n.desk_iconid = b.ID where EXISTS (SELECT distinct l.functionid from t_s_role_function l WHERE n.ID = l.functionid AND l.roleid in ( SELECT t.roleid from t_s_role_user t INNER JOIN t_s_base_user  m on t.userid = m.ID where m.ID = ?)) and n.functiontype='0' AND  n.parentfunctionid = ? ORDER BY To_number(n.functionorder,'999999999')  ASC";
				list = systemService.findForJdbc(sql, user.getId(), id);
			}
			String sqlcount = "select n.id from t_s_function n where n.parentfunctionid = ?";

			for (Map<String, Object> map : list) {
				String fuid = String.valueOf(map.get("id"));
				String name = String.valueOf(map.get("name"));
				// String iconclas = String.valueOf(map.get("iconclas"));
				name = ListtoMenu.getMutiLang(name);
				map.put("name", name);
				List<Map<String, Object>> listfu = systemService.findForJdbc(
						sqlcount, fuid);
				;
				if (listfu != null && listfu.size() > 0) {
					map.put("isParent", true);
				}
				// map.put("iconSkin", iconclas);自定义图标未换

			}
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

	/**
	 * 获取权限的map
	 * 
	 * @param user
	 * @return
	 */
	private Map<Integer, List<TSFunction>> getFunctionMap(TSUser user) {
		Map<Integer, List<TSFunction>> functionMap = new HashMap<Integer, List<TSFunction>>();
		Map<String, TSFunction> loginActionlist = getUserFunction(user);
		if (loginActionlist.size() > 0) {
			Collection<TSFunction> allFunctions = loginActionlist.values();
			for (TSFunction function : allFunctions) {
				// update-begin--Author:anchao Date:20140913
				// for：菜单过滤--------------------
				if (function.getFunctionType().intValue() == Globals.Function_TYPE_FROM
						.intValue()) {
					// 如果为表单或者弹出 不显示在系统菜单里面
					continue;
				}
				// update-end--Author:anchao Date:20140913
				// for：菜单过滤--------------------
				if (!functionMap.containsKey(function.getFunctionLevel() + 0)) {
					functionMap.put(function.getFunctionLevel() + 0,
							new ArrayList<TSFunction>());
				}
				functionMap.get(function.getFunctionLevel() + 0).add(function);
			}
			// 菜单栏排序
			Collection<List<TSFunction>> c = functionMap.values();
			for (List<TSFunction> list : c) {
				Collections.sort(list, new NumberComparator());
			}
		}
		return functionMap;
	}

	/**
	 * 获取用户菜单列表
	 * 
	 * @param user
	 * @return
	 */
	private Map<String, TSFunction> getUserFunction(TSUser user) {
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		// update-start--Author:JueYue Date:2014-5-28 for:风格切换,菜单懒加载失效的问题
		if (client.getFunctions() == null || client.getFunctions().size() == 0) {
			// update-end--Author:JueYue Date:2014-5-28 for:风格切换,菜单懒加载失效的问题
			Map<String, TSFunction> loginActionlist = new HashMap<String, TSFunction>();
			List<TSRoleUser> rUsers = systemService.findByProperty(
					TSRoleUser.class, "TSUser.id", user.getId());
			// update-begin--Author:zhangguoming Date:20140821
			// for：重构方法体，并加载用户组织机构下角色所拥有的权限
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				assembleFunctionsByRole(loginActionlist, role);
			}
			// update-start-Author:zhangguoming Date:20140825
			// for：获取当前登录用户的组织机构主键
			String orgId = client.getUser().getCurrentDepart().getId();
			// update-end-Author:zhangguoming Date:20140825 for：获取当前登录用户的组织机构主键
			List<TSRoleOrg> orgRoleList = systemService.findByProperty(
					TSRoleOrg.class, "tsDepart.id", orgId);
			for (TSRoleOrg roleOrg : orgRoleList) {
				TSRole role = roleOrg.getTsRole();
				assembleFunctionsByRole(loginActionlist, role);
			}
			// update-end--Author:zhangguoming Date:20140821
			// for：重构方法体，并加载用户组织机构下角色所拥有的权限
			client.setFunctions(loginActionlist);
		}
		return client.getFunctions();
	}

	// update-begin--Author:zhangguoming Date:20140821 for：抽取方法，获取角色下的权限列表
	/**
	 * 根据 角色实体 组装 用户权限列表
	 * 
	 * @param loginActionlist
	 *            登录用户的权限列表
	 * @param role
	 *            角色实体
	 */
	private void assembleFunctionsByRole(
			Map<String, TSFunction> loginActionlist, TSRole role) {
		List<TSRoleFunction> roleFunctionList = systemService.findByProperty(
				TSRoleFunction.class, "TSRole.id", role.getId());
		for (TSRoleFunction roleFunction : roleFunctionList) {
			TSFunction function = roleFunction.getTSFunction();
			// update-begin--Author:anchao Date:20140822
			// for：[bugfree号]字段级权限（表单，列表）--------------------
			if (function.getFunctionType().intValue() == Globals.Function_TYPE_FROM
					.intValue()) {
				// 如果为表单或者弹出 不显示在系统菜单里面
				continue;
			}
			// update-end--Author:anchao Date:20140822
			// for：[bugfree号]字段级权限（表单，列表）--------------------
			loginActionlist.put(function.getId(), function);
		}
	}

	// update-end--Author:zhangguoming Date:20140821 for：抽取方法，获取角色下的权限列表

	/**
	 * 首页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "home")
	public ModelAndView home(HttpServletRequest request) {
		return new ModelAndView("main/home");
	}

	/**
	 * 无权限页面提示跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "noAuth")
	public ModelAndView noAuth(HttpServletRequest request) {
		return new ModelAndView("common/noAuth");
	}

	/**
	 * @Title: top
	 * @Description: bootstrap头部菜单请求
	 * @param request
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(params = "top")
	public ModelAndView top(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(
					new RedirectView("loginController.do?login"));
		}
		request.setAttribute("menuMap", getFunctionMap(user));
		List<TSConfig> configs = userService.loadAll(TSConfig.class);
		for (TSConfig tsConfig : configs) {
			request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
		}
		return new ModelAndView("main/bootstrap_top");
	}

	/**
	 * @Title: top
	 * @author gaofeng
	 * @Description: shortcut头部菜单请求
	 * @param request
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(params = "shortcut_top")
	public ModelAndView shortcut_top(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(
					new RedirectView("loginController.do?login"));
		}
		request.setAttribute("menuMap", getFunctionMap(user));
		List<TSConfig> configs = userService.loadAll(TSConfig.class);
		for (TSConfig tsConfig : configs) {
			request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
		}
		return new ModelAndView("main/shortcut_top");
	}

	/**
	 * @Title: top
	 * @author:gaofeng
	 * @Description: shortcut头部菜单一级菜单列表，并将其用ajax传到页面，实现动态控制一级菜单列表
	 * @return AjaxJson
	 * @throws
	 */
	@RequestMapping(params = "primaryMenu")
	@ResponseBody
	public String getPrimaryMenu() {
		List<TSFunction> primaryMenu = getFunctionMap(
				ResourceUtil.getSessionUserName()).get(0);
		String floor = "";
		// update-start--Author:zhangguoming Date:20140923
		// for：用户没有任何权限，首页没有退出按钮的bug
		if (primaryMenu == null) {
			return floor;
		}
		// update-end--Author:zhangguoming Date:20140923
		// for：用户没有任何权限，首页没有退出按钮的bug
		for (TSFunction function : primaryMenu) {
			if (function.getFunctionLevel() == 0) {

				String lang_key = function.getFunctionName();
				String lang_context = mutiLangService.getLang(lang_key);

				if ("Online 开发".equals(lang_context)) {

					floor += " <li><img class='imag1' src='plug-in/login/images/online.png' /> "
							+ " <img class='imag2' src='plug-in/login/images/online_up.png' style='display: none;' />"
							+ " </li> ";
				} else if ("统计查询".equals(lang_context)) {

					floor += " <li><img class='imag1' src='plug-in/login/images/guanli.png' /> "
							+ " <img class='imag2' src='plug-in/login/images/guanli_up.png' style='display: none;' />"
							+ " </li> ";
				} else if ("系统管理".equals(lang_context)) {

					floor += " <li><img class='imag1' src='plug-in/login/images/xtgl.png' /> "
							+ " <img class='imag2' src='plug-in/login/images/xtgl_up.png' style='display: none;' />"
							+ " </li> ";
				} else if ("常用示例".equals(lang_context)) {

					floor += " <li><img class='imag1' src='plug-in/login/images/cysl.png' /> "
							+ " <img class='imag2' src='plug-in/login/images/cysl_up.png' style='display: none;' />"
							+ " </li> ";
				} else if ("系统监控".equals(lang_context)) {

					floor += " <li><img class='imag1' src='plug-in/login/images/xtjk.png' /> "
							+ " <img class='imag2' src='plug-in/login/images/xtjk_up.png' style='display: none;' />"
							+ " </li> ";
				} else if (lang_context.contains("消息推送")) {
					String s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'>消息推送</div>";
					floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/msg.png' /> "
							+ " <img class='imag2' src='plug-in/login/images/msg_up.png' style='display: none;' />"
							+ s + "</li> ";
				} else {
					// 其他的为默认通用的图片模式
					String s = "";
					if (lang_context.length() >= 5 && lang_context.length() < 7) {
						s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'><span style='letter-spacing:-1px;'>"
								+ lang_context + "</span></div>";
					} else if (lang_context.length() < 5) {
						s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'>"
								+ lang_context + "</div>";
					} else if (lang_context.length() >= 7) {
						s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'><span style='letter-spacing:-1px;'>"
								+ lang_context.substring(0, 6)
								+ "</span></div>";
					}
					floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/default.png' /> "
							+ " <img class='imag2' src='plug-in/login/images/default_up.png' style='display: none;' />"
							+ s + "</li> ";
				}
			}
		}

		return floor;
	}

	/**
	 * 返回数据
	 */
	@RequestMapping(params = "getPrimaryMenuForWebos")
	@ResponseBody
	public AjaxJson getPrimaryMenuForWebos() {
		AjaxJson j = new AjaxJson();
		// 将菜单加载到Session，用户只在登录的时候加载一次
		Object getPrimaryMenuForWebos = ContextHolderUtils.getSession()
				.getAttribute("getPrimaryMenuForWebos");
		if (oConvertUtils.isNotEmpty(getPrimaryMenuForWebos)) {
			j.setMsg(getPrimaryMenuForWebos.toString());
		} else {
			String PMenu = ListtoMenu.getWebosMenu(getFunctionMap(ResourceUtil
					.getSessionUserName()));
			ContextHolderUtils.getSession().setAttribute(
					"getPrimaryMenuForWebos", PMenu);
			j.setMsg(PMenu);
		}
		return j;
	}
}