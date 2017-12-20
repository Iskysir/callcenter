package org.jeecgframework.web.system.controller.core;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Servlet implementation class Validateaccount
 */
@WebServlet("/validateaccount")
public class Validateaccount extends HttpServlet {
	
	
	@Autowired
	private UserService userService;

	
	private static final long serialVersionUID = 1L;
       
    @Override
	public void init(ServletConfig config) throws ServletException {  
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,  
                config.getServletContext()); 
	}

	/**
     * @see HttpServlet#HttpServlet()
     */
    public Validateaccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username") ;
		String password = request.getParameter("password");
		String jsoncallback = request.getParameter("jsoncallback");

		TSUser user = null;
		if(!"".equals(username)&&!"".equals(password)){
			user = new TSUser();
			user.setUserName(username);
			user.setPassword(password);
			user = userService.checkUserExits(user);
		}

		response.setContentType("application/json"); 
		String jsonStr="";
		if(null != user){
			jsonStr= "{\"result\":\"success\"}";
		}else{
			jsonStr="{\"result\":\"error\"}";   
		}
			response.getWriter().write(jsoncallback+"("+jsonStr+")");  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
