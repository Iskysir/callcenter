package com.chrhc.project.sc.reader.controller;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Scope("prototype")
@Controller
@RequestMapping("/scReaderController")
public class ScReaderController extends BaseController {

	/**
	 * @param args
	 */

	@RequestMapping(params = "scReader")
	public ModelAndView scCerTemplate(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/reader/test");
	}
	
	@RequestMapping(params = "preFP")
	public ModelAndView preFP(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + contextPath;
		return new ModelAndView("com/chrhc/project/sc/reader/pre_fp");
	}
	
	
}
