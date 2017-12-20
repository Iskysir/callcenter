package com.chrhc.project.sc.deleteconfig;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



/**   
 * @Title: Controller
 * @Description: 消息
 * @author onlineGenerator
 * @date 2015-08-06 13:42:35
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scDeleteconfigsController")
public class ScDeleteconfigsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScDeleteconfigsController.class);

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
	 * 批量删除消息
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "表单删除成功";
		try{
			String id = request.getParameter("id");
			List<Map<String, Object>> lists = systemService.findForJdbc("SELECT tab_name,del_where FROM sc_deleteconfigs where wj = '"+id+"' order by del_order");
			if(lists != null && !lists.isEmpty()){
				for(int i = 0;i<lists.size();i++){ 
					Map<String, Object> map = (Map<String, Object>)lists.get(i);
					String table = map.get("tab_name").toString();
					Object condition = map.get("del_where");
				 
					if(StringUtil.isNotEmpty(condition)){
						systemService.executeSql("delete from "+table+" "+ condition);
					}else {
						systemService.executeSql("Truncate Table " + table);
					}
					
				}
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
			

		}catch(Exception e){
			e.printStackTrace();
			message = "表单删除失败,请检查表是否存在或删除条件是否正确";
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
//			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	
}
