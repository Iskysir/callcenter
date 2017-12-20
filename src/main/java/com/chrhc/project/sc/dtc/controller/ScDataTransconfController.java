package com.chrhc.project.sc.dtc.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.SeriNumGenerateUtils;
import org.jeecgframework.web.cgform.common.CommUtils;
import org.jeecgframework.web.cgform.entity.config.CgFormHeadEntity;
import org.jeecgframework.web.cgform.service.build.DataBaseService;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chrhc.project.sc.dtc.entity.ScDataTransconfEntity;

/**   
 * @Title: Controller
 * @Description:  
 * @author 
 * @date 2013-09-15 22:08:12
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/scDataTransconfController")
public class ScDataTransconfController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private DataBaseService dataBaseService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 数据转换
	 * 
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@RequestMapping(params = "transData")
	@ResponseBody
	public AjaxJson transData(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		
		Map param = CommUtils.mapConvert(request.getParameterMap()); ;
		//获取配置参数
		String dtKey = param.get("dtKey").toString()  ;
		String rk_id = param.get("id").toString()  ;
		param.remove("dtKey");
		
		CriteriaQuery cq = new CriteriaQuery(ScDataTransconfEntity.class);
		cq.eq("dtKey", dtKey);
		cq.add();
		List<ScDataTransconfEntity> stes = systemService.getListByCriteriaQuery(cq ,false);
		
		if(!stes.isEmpty()){
			ScDataTransconfEntity ste = stes.get(0);
			String sql = ste.getSqlStr();
			
			NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
			Map data = npJdbcTemplate.queryForMap(sql, param);
			//List<Map> data = npJdbcTemplate.queryForList(sql, param);
	    	Object pkValue = dataBaseService.getPkValue(ste.getTableName());
	    	data.put("id", pkValue);
	    	//增加 创建 时间 人 等信息zwt	
	   	 	data.put("create_date",DateUtils.getDate());
	   	 	TSUser user = ResourceUtil.getSessionUserName();
		 
	   	 	data.put("create_by",user.getUserName());
	   	 	data.put("create_name",user.getRealName());
	    	  //获取版本号
	        //String version = cgFormFieldService.getCgFormVersionByTableName(ste.getTableName());
			CgFormHeadEntity head = cgFormFieldService.getCgFormHeadByCode(ste.getTableName());
			
			
	    	data.put("code", SeriNumGenerateUtils.getId(head.getIdkey()));
	    	data.put("rk_id",rk_id);
			int num = dataBaseService.insertTable(ste.getTableName(), data);
			Map map = new HashMap();
			map.put("id", pkValue);
			map.put("url", "chrhcFormBuildController.do?ftlForm&id=" + pkValue + "&tableName=" + ste.getTableName() + "&print=1");
			
			
			j.setAttributes(map);
		}
		
		message = "保存成功";
		j.setMsg(message);
		return j;
	}


	/**
	 * 数据传递
	 * 
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@RequestMapping(params = "tiaozhuan")
	
	public ModelAndView tiaozhuan(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("rk_id");
		String dtKey = request.getParameter("dtKey");
		String tableName = request.getParameter("tableName");
		request.setAttribute("id", id);
		request.setAttribute("dtKey", dtKey);
		request.setAttribute("tableName", tableName);
		
		return new ModelAndView("com/chrhc/project/sc/kinship/tiaozhuan");
	}
}
