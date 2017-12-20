package com.chrhc.framework.web.cgform.controller.autolist;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.JeecgDataAutorUtils;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.cgform.common.CgAutoListConstant;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;
import org.jeecgframework.web.cgform.entity.upload.CgUploadEntity;
import org.jeecgframework.web.cgform.service.autolist.CgTableServiceI;
import org.jeecgframework.web.cgform.service.autolist.ConfigServiceI;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.jeecgframework.web.cgform.util.QueryParamUtil;
import org.jeecgframework.web.system.pojo.base.DictEntity;
import org.jeecgframework.web.system.pojo.base.TSOperation;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chrhc.framework.cgfrom.engine.ChrhcFreemarkerHelper;
import com.chrhc.framework.cgfrom.engine.RequestUtil;

/**
 * 
 * @Title:CgAutoListController
 * @description:动态列表控制器[根据表名读取配置文件，进行动态数据展现]
 * @author 赵俊夫
 * @date Jul 5, 2013 2:55:36 PM
 * @version V1.0
 */
@Controller
@RequestMapping("/chrhcGisListController")
public class ChrhcGisListController extends BaseController{
	@Autowired
	private ChrhcAutoListController chrhcAutoListController;

	private static Logger log = Logger.getLogger(ChrhcGisListController.class);
	/**
	 * 动态列表展现入口
	 * @param id 动态配置ID
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(params = "list")
	public void list(String id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		chrhcAutoListController.list(id, request, response);
	}

	/**
	 * 动态列表数据查询
	 * @param configId 配置id 修正使用id会造成主键查询的冲突
	 * @param page 分页页面
	 * @param rows 分页大小
	 * @param request 
	 * @param response
	 * @param dataGrid
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagrid")
	public void datagrid(String configId,String page,String field,String rows,String sort,String order, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		chrhcAutoListController.datagrid(configId, page, field, rows, sort, order, request, response, dataGrid);
	}
	
	


	/**
	 * 删除动态表
	 * @param configId 配置id
	 * @param id 主键
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(String configId,String id,
			HttpServletRequest request) {
		return chrhcAutoListController.del(configId, id, request);
	}
	/**
	 * 删除动态表-批量
	 * @param configId 配置id
	 * @param id 主键
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delBatch")
	@ResponseBody
	public AjaxJson delBatch(String configId,String ids,
			HttpServletRequest request) {
		return chrhcAutoListController.delBatch(configId, ids, request);
	}
}
