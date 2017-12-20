package com.chrhc.project.sc.scperm.controller;

import com.chrhc.project.sc.scperm.entity.ScReportPermEntity;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.context.annotation.Scope;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.web.cgform.common.CommUtils;
import org.jeecgframework.web.system.service.SystemService;

import java.util.Map;

/**
 * @Title: Controller
 * @Description: 自定义报表权限表
 * @author onlineGenerator
 * @date 2015-06-09 10:32:01
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/scReportPermController")
public class ScReportPermController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ScReportPermController.class);

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
	 * 自定义报表权限表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scReportPerm")
	public ModelAndView scReportPerm(HttpServletRequest request) {
		// DataGrid dataGrid = new DataGrid();
		try {
			// 自定义追加查询条件
			String strPermType = request.getParameter("permType");
			String strPermId = request.getParameter("permId");

			String sql0 = "select scrp.*, jch.NAME as reportname from sc_report_perm scrp "
					+ "LEFT OUTER JOIN jform_cgreport_head jch on scrp.report_code = jch.CODE where 1=1"
					+ " and scrp.perm_id = ? and scrp.perm_type = ?";

			List<Map<String, Object>> lstMap = systemService.findForJdbc(sql0,
					strPermId, strPermType);

			// ScReportPermEntity scReportPerm = new ScReportPermEntity();
			// scReportPerm.setPermType(strPermType);
			// scReportPerm.setPermId(strPermId);
			//
			// CriteriaQuery cq = new CriteriaQuery(ScReportPermEntity.class,
			// dataGrid);
			// // 查询条件组装器
			// org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil
			// .installHql(cq, scReportPerm);
			// cq.add();
			// systemService.getDataGridReturn(cq, true);

			request.setAttribute("permType", strPermType);
			request.setAttribute("permId", strPermId);
			request.setAttribute("scReportPermListMap", lstMap);
			// request.setAttribute("scReportPermList", dataGrid.getResults());
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

		return new ModelAndView("com/chrhc/project/sc/scperm/scReportPerm");
	}

	/**
	 * 批量删除自定义报表权限表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "自定义报表权限表删除成功";
		try {
			for (String id : ids.split(",")) {
				if(StringUtils.isNotEmpty(id))
				{
					ScReportPermEntity scReportPerm = systemService.getEntity(
							ScReportPermEntity.class, id);
					systemService.delete(scReportPerm);
					systemService.addLog(message, Globals.Log_Type_DEL,
							Globals.Log_Leavel_INFO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "自定义报表权限表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加自定义报表权限表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "自定义报表权限表保存成功";
		try {

			Map data = request.getParameterMap();
			if (data != null) {
				data = CommUtils.mapConvert(data);
				String tableName = "sc_report_perm";
				Map<String, List<Map<String, Object>>> mapMore = CommUtils
						.mapConvertMore(data, tableName);
				try {

					if (mapMore.containsKey("sc_report_perm")) {

						List<Map<String, Object>> lstMap = (List<Map<String, Object>>) mapMore
								.get("sc_report_perm");
						// List<ScReportPermEntity> entitys = new
						// java.util.ArrayList<ScReportPermEntity>();

						for (int i = 0; i < lstMap.size(); i++) {
							Map<String, Object> map = (Map<String, Object>) lstMap
									.get(i);
							ScReportPermEntity entity = new ScReportPermEntity();
							if (map.containsKey("permType")
									&& map.containsKey("permId")
									&& map.containsKey("reportCode")
									&& map.get("permType") != null
									&& map.get("permId") != null
									&& map.get("reportCode") != null
									&& String.valueOf(map.get("permType"))
											.length() > 0
									&& String.valueOf(map.get("permId"))
											.length() > 0
									&& String.valueOf(map.get("reportCode"))
											.length() > 0) {
								entity.setId(String.valueOf(map.get("id")));
								if (entity.getId() != null
										&& entity.getId().length() > 0) {
									entity = systemService.get(
											ScReportPermEntity.class,
											entity.getId());
								}
								entity.setPermType(String.valueOf(map
										.get("permType")));
								entity.setPermId(String.valueOf(map
										.get("permId")));
								entity.setReportCode(String.valueOf(map
										.get("reportCode")));
								entity.setDataIds(String.valueOf(map
										.get("dataIds")));
								// entitys.add(entity);
								if (entity.getId() != null
										&& entity.getId().length() > 0) {
									systemService.updateEntitie(entity);
								} else {
									systemService.save(entity);
								}
							}
						}

						// systemService.batchSave(entitys);
					}

					j.setSuccess(true);
				} catch (BusinessException e) {
					e.printStackTrace();
					j.setSuccess(false);
					message = e.getMessage();
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "自定义报表权限表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

}
