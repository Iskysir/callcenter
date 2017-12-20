package com.chrhc.project.sc.common;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sclient")
public class SclientController extends BaseController {

	@RequestMapping("/download")
	public void downloadClient(HttpServletRequest request,
			HttpServletResponse response)
	{
		String sclientStr="sclient-32.msi";
		Enumeration e = request.getHeaderNames();
		 while (e.hasMoreElements()) {
		 String name = (String)e.nextElement();
		 String value = request.getHeader(name);
		 if("user-agent".equalsIgnoreCase(name))
		 {
			 if(value.toUpperCase().contains("WOW64"))
			 {
				 sclientStr="sclient-64.msi";
			 }
		 }
		 System.out.println(name + " = " + value);
		 }
		try {
			String realpath=request.getSession().getServletContext().getRealPath("/")+"/downfiles/"+sclientStr;
			OutputStream os = response.getOutputStream();   
		    try {   
		    	response.reset();   
		    	response.setHeader("Content-Disposition", "attachment; filename="+sclientStr);   
		    	response.setContentType("application/octet-stream; charset=utf-8");   
		        os.write(FileUtils.readFileToByteArray(new File(realpath)));   
		        os.flush();   
		    } finally {   
		        if (os != null) {   
		            os.close();   
		        }   
		    }   
		} catch ( IOException exp) {
			// TODO Auto-generated catch block
			exp.printStackTrace();
		}
	}
}
