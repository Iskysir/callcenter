package org.jeecgframework.web.system.service;

import com.alibaba.fastjson.JSONObject;
import org.jeecgframework.core.common.service.CommonService;

import org.jeecgframework.web.system.pojo.base.TSUser;

import java.io.IOException;

/**
 * 
 * @author  chrc
 *
 */
public interface UserService extends CommonService{

	public TSUser checkUserExits(TSUser user);
	
	/**
	 * 确认用户存在，CAS登陆用
	 * @param doukai
	 * @return
	 */
	public TSUser checkUserExitsForCas(TSUser user);
	
	public String getUserRole(TSUser user);
	public void pwdInit(TSUser user, String newPwd);
	/**
	 * 判断这个角色是不是还有用户使用
	 *@Author JueYue
	 *@date   2013-11-12
	 *@param id
	 *@return
	 */
	public int getUsersOfThisRole(String id);
	JSONObject ectonSingleLogin(TSUser user) throws IOException;
	public JSONObject ectonNcSingleLogin (TSUser user);
}
