package com.chrhc.project.scbi.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.PropertiesUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.TSUser;

public class ReportUtils {
	private static PropertiesUtil util = null;
	public static String replaceUrl(HttpServletRequest request,String url)
	{
		if(util==null)
			util = new PropertiesUtil("sysConfig.properties");
		//Object obj=request.getSession().getAttribute("reportContext");
		Object obj= util.readProperty("reportContext");
		String reportContext=obj!=null?obj.toString():"http://localhost:8075/WebReport";
		String result=url;
		result=url.replace("${reportContext}", reportContext);
		result=result.replace("${sys_org_code}", ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode());
		//报修单查询url
		HttpSession session = ContextHolderUtils.getSession();
		TSUser user = ResourceUtil.getSessionUserName();
		String username = user.getUserName();
		if(ClientManager.getInstance().getClient(session.getId())!=null){
			Client client = ClientManager.getInstance().getClient(session.getId());
			String pwd = client.getSingleloginpwd();
			//报修单状态查询url
			String repairqueryurl= ResourceUtil.getConfigByName("singlelogin.repairquery.url");
			repairqueryurl=String.format(repairqueryurl,username,pwd);
			result=result.replace("${singlelogin.repairquery.url}", repairqueryurl);
			//总部任务派发
			String zbrwpfurl= ResourceUtil.getConfigByName("singlelogin.zbrwpf.url");
			zbrwpfurl=String.format(zbrwpfurl,username,pwd);
			result=result.replace("${singlelogin.zbrwpf.url}", zbrwpfurl);
			//地方分公司派工
			String dffgspgurl= ResourceUtil.getConfigByName("singlelogin.dffgspg.url");
			dffgspgurl=String.format(dffgspgurl,username,pwd);
			result=result.replace("${singlelogin.dffgspg.url}", dffgspgurl);
			//任务报工
			String rwbgurl= ResourceUtil.getConfigByName("singlelogin.rwbg.url");
			rwbgurl=String.format(rwbgurl,username,pwd);
			result=result.replace("${singlelogin.rwbg.url}", rwbgurl);
			//报工单
			String bgdurl= ResourceUtil.getConfigByName("singlelogin.bgd.url");
			bgdurl=String.format(bgdurl,username,pwd);
			result=result.replace("${singlelogin.bgd.url}", bgdurl);
			//报工评价
			String bgpjurl= ResourceUtil.getConfigByName("singlelogin.bgpj.url");
			bgpjurl=String.format(bgpjurl,username,pwd);
			result=result.replace("${singlelogin.bgpj.url}", bgpjurl);
			//零活任务
			String lhrwurl= ResourceUtil.getConfigByName("singlelogin.lhrw.url");
			lhrwurl=String.format(lhrwurl,username,pwd);
			result=result.replace("${singlelogin.lhrw.url}", lhrwurl);
			//零活报工
			String lhbgurl= ResourceUtil.getConfigByName("singlelogin.lhbg.url");
			lhbgurl=String.format(lhbgurl,username,pwd);
			result=result.replace("${singlelogin.lhbg.url}", lhbgurl);
			//密码修改
			String updatepasswordurl= ResourceUtil.getConfigByName("singlelogin.updatepassword.url");
			updatepasswordurl=String.format(updatepasswordurl,username,pwd);
			result=result.replace("${singlelogin.updatepassword.url}", updatepasswordurl);
			//地市内勤统计表
			String dsnqtjburl= ResourceUtil.getConfigByName("singlelogin.dsnqtjb.url");
			dsnqtjburl=String.format(dsnqtjburl,username,pwd);
			result=result.replace("${singlelogin.dsnqtjb.url}", dsnqtjburl);
			//客服中心坐席派单
			String kfzxzxpdurl= ResourceUtil.getConfigByName("singlelogin.kfzxzxpd.url");
			kfzxzxpdurl=String.format(kfzxzxpdurl,username,pwd);
			result=result.replace("${singlelogin.kfzxzxpd.url}", kfzxzxpdurl);
			//地市分公司派工一卡通
			String dsfgspgykturl= ResourceUtil.getConfigByName("singlelogin.dsfgspgykt.url");
			dsfgspgykturl=String.format(dsfgspgykturl,username,pwd);
			result=result.replace("${singlelogin.dsfgspgykt.url}", dsfgspgykturl);
			//总部派工任务查询
			String zbpgrwcxurl= ResourceUtil.getConfigByName("singlelogin.zbpgrwcx.url");
			zbpgrwcxurl=String.format(zbpgrwcxurl,username,pwd);
			result=result.replace("${singlelogin.zbpgrwcx.url}", zbpgrwcxurl);
		}
		return result;
	}
}
