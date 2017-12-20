package com.chrhc.projects.hotline.softphone.web;


import com.alibaba.fastjson.JSON;
import com.chrhc.projects.hotline.softphone.service.AgentService;
import org.apache.commons.logging.LogFactory;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.SeriNumGenerateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.util.QueryParamUtil;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "")
public class SoftphoneController {

    private static org.apache.commons.logging.Log log4j = LogFactory.getLog(SoftphoneController.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private static String[] statusStr=new String[2];
    @Autowired
    private SystemService systemService;
    @Autowired
    @Qualifier("ctiJdbcTemplate")
    private JdbcTemplate ctiJdbcTemplate;
    @Autowired
    private AgentService agentService;
    /*
    @Autowired
	private CagentService cagentService;

	@Autowired
	private AgentService agentService;

	@Autowired
	private LogService logService ;
	
	@Autowired
	private CallLogService callLogService ;
	
	@Autowired
	private ChannelRecordService channelRecordService;
	*/


    @RequestMapping(value = "/softphone/index")
    public String index(
            @RequestParam(value = "debug", required = false) String debug,
            Model model) {
        model.addAttribute("debug", debug);
        return "/softphone/samwoophone_new";
    }

    @RequestMapping(value = "/softphone/test")
    public String test() {
        return "/softphone/agentocx";
    }

    @RequestMapping(value = "/softphone/rest/agent/status")
    @ResponseBody
    public String agentStatus() {
        //List<HashMap> list = cagentService.findAgentStatus();
        //return JsonMapper.nonDefaultMapper().toJson(list);
        return "";
        // return "/softphone/agentocx";
    }

    /**
     * 座席员实时监控
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/softphone/agentMonitor",params = "refresh")
    public String agentMonitor(Model model, ServletRequest request) {
        System.out.println("/softphone/agentMonitor");
        List<Map<String,Object>> agentList=systemService.findForJdbc("select DISTINCT user_name, agentId from HL_AGENTCONF");
        List<Map<String, Object>> statuslist=ctiJdbcTemplate.queryForList("select c.agentnum bind_exten,a.agent_status,b.status_name from " +
                "cti_agent_status_reserve a join cti_agent c on " +
                "a.agentid=c.id ,cti_agent_status_define b where a.agent_status=b.status_id and a.agent_status!='-1'");
        List<Map<String, Object>> statusCountlist=ctiJdbcTemplate.queryForList("select count(c.agentnum) y,max(b.status_name) name from " +
                "cti_agent_status_reserve a join cti_agent c on a.agentid=c.id ,cti_agent_status_define b where a.agent_status=b.status_id " +
                "and a.agent_status!='-1' group by a.agent_status");
        for(int i = 0; i < statuslist.size(); i++) {
            Map<String,Object> statusmap=statuslist.get(i);
            for(int j = 0; j < agentList.size(); j++) {
                Map<String,Object> agentmap=agentList.get(j);
                if(String.valueOf(statusmap.get("bind_exten")).equals(String.valueOf(agentmap.get("AGENTID"))))
                {
                    statusmap.put("displayName",agentmap.get("USER_NAME"));
                }
            }
        }
        model.addAttribute("statuslist", statuslist);
        model.addAttribute("statusCountlist", JSON.toJSONString(statusCountlist));
        return "/softphone/agentcount";
    }

    /**
     * IVR实时监控
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/rlmonitor/ivrMonitor")
    public String ivrMonitor(Model model, ServletRequest request) {

		/*
		String starttime = request.getParameter("startime");
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
//		calendar.set(Calendar.HOUR_OF_DAY, 0);
		//calendar.add(Calendar.DAY_OF_MONTH, -7);

		Date datestart = null;
		Date dateEnd =  new Date();
		
		if(starttime==null){
			datestart = calendar.getTime();
			
			String starttimeM = sdf.format(datestart);
			model.addAttribute("starttimeM", starttimeM);
			
		}else {
			try {
				datestart = sdf.parse(starttime);
				model.addAttribute("starttimeM", starttime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		
		Double talktime =  this.callLogService.getTalkTime(datestart,dateEnd);
		
		model.addAttribute("starttime", sdf.format(dateEnd));

		List<Map<String, Object>> seri =  new ArrayList<Map<String,Object>>();
		
		
		List<Map<String, Object>> list = callLogService.getData(datestart, dateEnd);
		
		Map<String, Double> map  = new HashMap<String, Double>();
		
		map.put("talkTime", talktime);

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map<String, Object> mapdataR = (Map<String, Object>) iterator.next();
			
			Map<String,Object> mapData = new HashMap<String, Object>();
			mapData.put("name", mapdataR.get("name"));
			mapData.put("data", mapdataR.get("data"));
			seri.add(mapData);
		}
		
		String seriJson  = JsonMapper.nonDefaultMapper().toJson(seri);
		
		log4j.info(seriJson);
		model.addAttribute("seri", seriJson);
		
		model.addAttribute("seriList", seri);
		
		model.addAttribute("datestart", datestart);
		model.addAttribute("dateEnd", dateEnd);
		
*/
        return "/softphone/ivrstatus";
    }


    /**
     * 设置座席状态 undo 未保存入数据库中
     *
     * @param status  状态编号
     * @param seconds 持续时间
     * @param model   mvc 显示视图
     * @param request httprequest
     * @return 返回成功页面
     */
    @RequestMapping(value = "/softphone/rest/agent/status/set")
    public String setAgentStatus(@RequestParam("status") String status,
                                 @RequestParam("seconds") String seconds, Model model,
                                 ServletRequest request) {
        System.out.println("workmode:" + status + ",seconds:" + seconds);

        return "hotline/success";
    }

    /**
     * 显示座席状态界面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/softphone/agentlist")
    public String agentlist(Model model, ServletRequest request) {
        List agentList = agentService.getAgentStatus();
        model.addAttribute("showType", request.getParameter("showType"));
        model.addAttribute("agentList", agentList);
        return "/softphone/agentlist";
    }

    /**
     * 保存来电记录
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/softphone/saveHisCall")
    @ResponseBody
    public String saveHisCall(Model model, HttpServletRequest request) {
        String uuid = request.getParameter("uuidA");
        String bizId = request.getParameter("uuidBiz");
        String uuidB = request.getParameter("uuidB");
        String calledNum = request.getParameter("calledNum");
        String business_id = request.getParameter("business_id");
        String business_code = request.getParameter("business_code");
        String yw_code = request.getParameter("yw_code");
        String callType = request.getParameter("callType");
        String cm_id = "";
        String cm_code ="";
        String cm_name ="";
        String cm_tel = request.getParameter("cm_tel");
        List<Map<String, Object>> custList=systemService.findForJdbc("SELECT id,cm_name,cm_code from  HL_CUSTOMER where CM_TEL like '%"+cm_tel+"%' order by create_date desc",1,1);
       if(custList.size()>0)
       {
           cm_id= String.valueOf(custList.get(0).get("ID"));
           cm_code= String.valueOf(custList.get(0).get("CM_CODE"));
           cm_name= String.valueOf(custList.get(0).get("CM_NAME"));
       }
        /*HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("uuid",uuid);
        result.put("uuidB",uuidB);
        result.put("cm_id",cm_id);
        result.put("cm_code",cm_code);
        result.put("cm_name",cm_name);
        result.put("cm_tel",cm_tel);*/
        Object obj = request.getSession().getAttribute("agentMap");
        int res = -1;
        if (obj != null) {

            Map<String, Object> agentMap = (HashMap<String, Object>) obj;
            Client client = ClientManager.getInstance().getClient(request.getSession().getId());
            //client.getUser().getRealName();
            String sql = "INSERT INTO HL_HISTORY_CALLS " +
                    "(ID,CREATE_NAME,CREATE_BY,CREATE_DATE,UPDATE_NAME,UPDATE_BY,UPDATE_DATE," +
                    "VERSION_NUM,SYS_ORG_CODE,DELFLAG,CUSTOMER_CODE,CUSTOMER_NAME,CUSTOMER_TEL," +
                    "CALL_DATE,ZXH,ZX_NAME,CUSTOMER_ID,HL_COMPANY,ORDERRECORDID,CALLED_NUM,CALLTYPE) VALUES " +
                    "('" + bizId + "','" + client.getUser().getRealName() + "','" + client.getUser().getUserName()
                    + "',sysdate,null,null,null,'0','','0','" + cm_code + "','" + cm_name + "','"+ cm_tel + "','','" + agentMap.get("AGENTID").toString() + "','"
                    + client.getUser().getRealName() + "','" + cm_id + "','','" + uuid+ "','" + calledNum+ "','" + callType + "')";
            res = systemService.executeSql(sql);
            if(StringUtil.isNotEmpty(business_id))
            {
                String sqlCallBiz="INSERT INTO HL_CALL_BUSINESS (ID, DELFLAG, HISTORY_CALL_ID, BUSINESS_CODE, BUSINESS_ID, YW_CODE) VALUES ('"+ UUID.randomUUID().toString()+"','0', '"+bizId+"', '"+business_code+"', '"+business_id+"', '"+yw_code+"')";
                systemService.executeSql(sqlCallBiz);
            }
        }
        return "{result:'" + res + "'}";
    }

    @RequestMapping(value = "/softphone/queryCallBiz")
    @ResponseBody
    public Object queryCallBiz(Model model, HttpServletRequest request)
    {
        AjaxJson j = new AjaxJson();
        String callId=request.getParameter("callId");
        List<Map<String,Object>> list=systemService.findForJdbc("select yw_code,business_id,business_code,content from HL_CALL_BUSINESS,CGFORM_HEAD where HL_CALL_BUSINESS.BUSINESS_CODE=CGFORM_HEAD.CODE and HISTORY_CALL_ID='"+callId+"'",null);
        j.setObj(list);
        return j;
    }

    @RequestMapping(value = "/softphone/queryQueue")
    public void queryQueue(Model model, HttpServletRequest request, HttpServletResponse response)
    {
        String result="";
        //AjaxJson j = new AjaxJson();
        String sql="select a.id, FROM_UNIXTIME(a.queue_enter_time,'%Y-%m-%d %T') queue_enter_time, a.callerid,a.calleeid,case when cust_status='Trying' then '转接中' else '等待中'end statusname,b.queuename,a.serving_agent from cti_customer_in_queue a,cti_queue b where a.queueid=b.id order by a.queue_enter_time asc";
        List<Map<String,Object>> queuelist= ctiJdbcTemplate.queryForList(sql);
        for(int i=0;i<queuelist.size();i++)
        {
            Object calledObj=queuelist.get(i).get("callerid");
            if(calledObj!=null&& StringUtil.isNotEmpty(calledObj))
            {
                String callerId=calledObj.toString();
                String clientSql="select cm_name from HL_CUSTOMER where cm_tel like '"+callerId+"%' order by create_date desc";
                List<Map<String,Object>> clientList=systemService.findForJdbc(clientSql,1,1);
                if(clientList.size()>0)
                {
                    Object cmObj=clientList.get(0).get("CM_NAME");
                    if(StringUtil.isNotEmpty(cmObj))
                    {
                        queuelist.get(i).put("cm_name",cmObj);
                    }
                }
            }

        }
        result=QueryParamUtil.getJson(queuelist, (long) queuelist.size());
        try {
            response.getWriter().println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //j.setObj(queuelist);
        //return result;
    }

    /**
     * 座席外拨 转接界面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/softphone/agentdial")
    public String agentdial(Model model, ServletRequest request) {
        model.addAttribute("showType", request.getParameter("showType"));
        return "/softphone/agentdial";
    }

    /**
     * 座席状态设置界面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/softphone/agentstatus")
    public String agentstatus(Model model, ServletRequest request) {

        //model.addAttribute("statusList", statusList);
        System.out.println("123");

        return "/softphone/agentstatus";
    }

    /**
     * IVR 转接界面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/softphone/agentivr")
    public String agentivr(Model model, ServletRequest request) {

		/*List ivrList = this.agentService.getIvrList();
		model.addAttribute("ivrList", ivrList);*/
        List ivrList = ctiJdbcTemplate.queryForList("select ivr_num code,ivr_name name,'1' status from cti_ivr");
        //List ivrList=systemService.findForJdbc("select ivrcode code,ivrname name,IVRSTATE status  from HL_IVR");
        model.addAttribute("ivrList", ivrList);
        return "/softphone/agentivr";
    }


    @RequestMapping(value = "/softphone/calltransfer")
    public String calltransfer(Model model,
                               @RequestParam(value = "userName", required = false) String userName,
                               @RequestParam(value = "ani", required = false) String ani,
                               @RequestParam(value = "dnis", required = false) String dsni,
                               @RequestParam(value = "bizId", required = false) String bizId,
                               @RequestParam(value = "data", required = false) String data,
                               ServletRequest request) {

        String pajs = "comet.callTransfer('" + ani + "','" + dsni + "','" + bizId + "','" + data + "');";
        try {
            //ChatServlet.sendJs(userName, pajs);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "/hotline/success";
    }

    @RequestMapping(value = "/softphone/selfApply")
    public String selfApply(
            Model model,
            HttpServletRequest request) {
        String result="0";
        String telephone=request.getParameter("tel");
        String devNo=request.getParameter("devNo");
        String callId=request.getParameter("call_id");
        List<Map<String,Object>> listRes=systemService.findForJdbc("SELECT DEVID devid,地市 ds,商户名 shm,商户号 shh,装机地址 zjdz,厂商 cs,机型 jx, 终端号 zdh,序列号 xlh, contact contact,tel tel,ADDRESS address,收单机器名称 sdjqmc,拓展人 tzr from V_BUCUSNAME_INFO where 终端号 like '%"+devNo+"%'");
        if(listRes.size()==1)
        {
            Map<String,Object> map=listRes.get(0);
            String devNoStrS=map.get("ZDH").toString();
            String[] devNoStrArray=devNoStrS.split(",");
            boolean hasCode=false;
            for(int i=0;i<devNoStrArray.length;i++)
            {
               String devNoOne= devNoStrArray[i];
                if(devNo.equals(devNoOne))
                {
                    hasCode=true;
                    break;
                }
            }
            if(hasCode) {
                String code = SeriNumGenerateUtils.getId("BXD");
                String id = java.util.UUID.randomUUID().toString();
                String insertBxdSql = "INSERT INTO HL_REPAIR (ID, CREATE_NAME, CREATE_BY, CREATE_DATE, VERSION_NUM, SYS_ORG_CODE, DELFLAG, CODE, ZDH, SHH, SHMC, LXR,ldhm, ZJDZ, ZDJTAZDZ, SDJQMC, DS, ZDSL, CJ, TZR, JX, HISTORY_CALL_ID, SOUND_RECORD, DEVID, PGZT, BX_TYPE) VALUES (";
                insertBxdSql = insertBxdSql + "'" + id + "', 'admin', 'admin', sysdate, 0, NULL, '0', '" + code + "', '" + devNo + "', '" + String.valueOf(map.get("SHH")) + "', '" + String.valueOf(map.get("SHM")) + "', ";
                insertBxdSql = insertBxdSql + "'" + String.valueOf(map.get("CONTACT")) + "', '" + telephone + "', '" + String.valueOf(map.get("ZJDZ")) + "', ";
                insertBxdSql = insertBxdSql + "'" + String.valueOf(map.get("ADDRESS")) + "', '" + String.valueOf(map.get("SDJQMC")) + "', '" + String.valueOf(map.get("DS")) + "', '1', ";
                insertBxdSql = insertBxdSql + "'" + String.valueOf(map.get("CS")) + "', '" + String.valueOf(map.get("TZR")) + "', '" + String.valueOf(map.get("JX")) + "', '" + callId + "', '" + callId + "', '" + String.valueOf(map.get("DEVID")) + "','1', '1')";
                systemService.executeSql(insertBxdSql);
                result = "1";
            }
        }
        else
        {
            result="0";
        }
        model.addAttribute("result", result);
        return "/softphone/selfapply";
    }

    @RequestMapping(value = "/softphone/satisfy")
    public String satisfy(
            Model model,
            HttpServletRequest request) {
        String result="";
        String callId=request.getParameter("call_id");
        String satisy=request.getParameter("satisy");
        String sql="update HL_HISTORY_CALLS set SATISFY='"+satisy+"' where ORDERRECORDID='"+callId+"'";
        systemService.executeSql(sql);
        return "/softphone/selfapply";
    }


    @RequestMapping(value = "/softphone/ivrselect",params = "save")
    @ResponseBody
    public String saveIvrSelect(Model model, HttpServletRequest request) {
        String callId=request.getParameter("call_id");
        String ivrselect=request.getParameter("ivrselect");
       String id = java.util.UUID.randomUUID().toString();

            String sql = "INSERT INTO HL_IVRSELECT( id,callId,ivrSelect,CREATE_DATE)" +

                    " VALUES " +
                    "('" + id + "','" + callId + "','" + ivrselect
                    + "',sysdate)";
            int res = systemService.executeSql(sql);

        return "{result:'" + res + "'}";
    }

    @RequestMapping(value = "/softphone/ivrselect",params = "check")
    @ResponseBody
    public String checkIvrSelect(Model model, HttpServletRequest request) {
        String callId = request.getParameter("uuidA");
        String ivrselect = "";
        String sql = "select * from HL_IVRSELECT where callId='" + callId + "' order by CREATE_DATE desc";
        List<Map<String, Object>> list = systemService.findForJdbc(sql);
        if (list.size() > 0) {
            ivrselect = String.valueOf(list.get(0).get("IVRSELECT"));
        }
        return "{result:'" + ivrselect + "'}";


    }

    @RequestMapping(value = "/softphone/queueStatus",params = "check")
    @ResponseBody
    public String queueStatus(Model model, HttpServletRequest request) {
        long now=System.currentTimeMillis();
        if(StringUtil.isNotEmpty(statusStr[0]))
        {
          long before=  Long.parseLong(statusStr[0]);
            if(now-before<=2000)
            {
                return statusStr[1];
            }
        }
        String waitNum="";
        /*String sql = "" +
                "SELECT COUNT(1) num FROM cti_cdr_call_event a where not exists " +
                "(select 1 from cti_cdr_call_event b where b.event_leg_uuid=a.event_leg_uuid and b.event_time>a.event_time)" +
                " and a.event_params  like '%ivr_num=1477467426505' and event_time>=date_add(NOW(), interval -5 MINUTE)" +
                " and event_time <=NOW() and  not EXISTS (select 1 from cti_cdr_call_event c where a.event_leg_uuid=c.event_leg_uuid and c.event_type='3')";*/
        String sql ="select COUNT(1) num from cti_customer_in_queue";
        List<Map<String, Object>> Queuelist = ctiJdbcTemplate.queryForList(sql);
        if (Queuelist.size() > 0) {
            waitNum = String.valueOf(Queuelist.get(0).get("num"));
        }
        String statusSql="select count(c.agentnum) y,max(b.status_name) name from " +
                "                cti_agent_status_reserve a join cti_agent c on a.agentid=c.id ,cti_agent_status_define b where a.agent_status=b.status_id \n" +
                "                 and a.agent_status!='-1' group by a.agent_status";
        List<Map<String, Object>> statusCountlist=ctiJdbcTemplate.queryForList(statusSql);
        Map result=new HashMap();
        result.put("waitNum",waitNum);
        result.put("agentStatus",statusCountlist);

        StringBuilder statusBuilder=new StringBuilder("[");
        StringBuilder statusString=new StringBuilder();
        for(int i=0;i<statusCountlist.size();i++)
        {
            Map<String, Object> map=statusCountlist.get(i);
            String name=String.valueOf(map.get("name"));
            String y=String.valueOf(map.get("y"));
            statusBuilder.append("{name:"+name+",");
            statusBuilder.append("y:"+y+"},");
            statusString.append(","+name+":"+y+"");
        }

        //String statusString=statusBuilder.substring(0,statusBuilder.length()-1)+"]";
        String resultString="等待人数："+waitNum+statusString;
        statusStr[0]=String.valueOf(now);
        statusStr[1]=resultString;
        //statusBuilder.append("]");
       //resultString=JSONHelper.map2json(result);
        return resultString;


    }


    @RequestMapping(value = "/softphone/phone",params = "check")
    @ResponseBody
    public String phoneCheck(Model model, HttpServletRequest request) {
        String result="山东济南";
        String phone=request.getParameter("phone");
        if(phone!=null)
        {
            phone=phone.trim();
        }
        if(phone.length()>8)
        {
           String begin= phone.substring(0,1);
            String begin3=phone.substring(0,3);
            int begin3Num=Integer.parseInt(begin3);
            if("0".equals(begin)&&(begin3Num<11||begin3Num>20))
            {
                String begin4=phone.substring(0,4);

                String sql1="select province,city from phone where phone.city_code like '"+begin4+"%' " ;
                String sql2=" select   province,city from phone where phone.city_code  like '"+begin3+"%'";
                List<Map<String,Object>> list=ctiJdbcTemplate.queryForList(sql1);
                if(list.size()>0)
                {
                    result=String.valueOf(list.get(0).get("province"))+String.valueOf(list.get(0).get("city"));
                }
                else
                {
                    list=ctiJdbcTemplate.queryForList(sql2);
                    if(list.size()>0) {
                        result = String.valueOf(list.get(0).get("province")) + String.valueOf(list.get(0).get("city"));
                    }
                }
            }
            else
            {
                String begin7=phone.substring(0,7);
                if("0".equals(phone.substring(0,1)))
                {
                    begin7=phone.substring(1,8);
                }

                String sql="select province,city from phone where phone.phone like '"+begin7+"%'";
                List<Map<String,Object>> list=ctiJdbcTemplate.queryForList(sql);
                if(list.size()>0)
                {
                    result=String.valueOf(list.get(0).get("province"))+String.valueOf(list.get(0).get("city"));
                }
            }

        }

        return result;
    }


}
