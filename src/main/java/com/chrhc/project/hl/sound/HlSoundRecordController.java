package com.chrhc.project.hl.sound;

import com.chrhc.project.hl.sound.dao.FileManageDao;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.controller.core.UserController;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/4.
 */
@Scope("prototype")
@Controller
@RequestMapping("/HlSoundRecordController")
public class HlSoundRecordController extends BaseController {
    @Autowired
    private  SystemService service;
    @Resource(name="fileManageDaoImplBySFTP")
    private FileManageDao fileManageDao;
    @Autowired
    @Qualifier("ctiJdbcTemplate")
    private JdbcTemplate ctiJdbcTemplate;

    /**
     * 跳转至音频播放页面
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "zixundetail")
    public ModelAndView zixundetail( HttpServletRequest request){

        ModelAndView mv = new ModelAndView();
        //String value = request.getParameter("value");
        String id = request.getParameter("id");
        String sql = "select BZ from HL_CONSULTING where id=?";
        String value = "";
        List<Map<String,Object>> list = service.findForJdbc(sql,id);
        if(list != null && list.size() > 0){
            value = String.valueOf(list.get(0).get("BZ"));
        }
        mv.addObject("value",value);
        mv.setViewName("com/chrhc/project/hl/sound/zixundetail");
        return mv;
    }
    /**
     * 跳转至音频播放页面
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "soundRecord")
    public ModelAndView soundRecord( HttpServletRequest request){

        ModelAndView mv = new ModelAndView();
        String path = request.getParameter("path");
        mv.addObject("path",path);
        mv.setViewName("com/chrhc/project/hl/sound/sound_record");
        return mv;
    }

    /**
     * 返回当前用户信息
     * @param req
     * @return
     */
    @RequestMapping(params = "getUserInfo")
    @ResponseBody
    public AjaxJson getUserInfo(HttpServletRequest req){
        AjaxJson j = new AjaxJson();
        HttpSession session = ContextHolderUtils.getSession();

        Map<String,Object> map = new HashMap<String, Object>();
        TSUser user = ResourceUtil.getSessionUserName();
        String username = user.getUserName();
        map.put("username",username);
        map.put("realname",user.getRealName());
        if(ClientManager.getInstance().getClient(session.getId())!=null){
            Client client = ClientManager.getInstance().getClient(session.getId());
            String pwd = client.getSingleloginpwd();
            map.put("pwd",pwd);
            String serviceurl= ResourceUtil.getConfigByName("singlelogin.service.url");
            serviceurl=String.format(serviceurl,username,pwd);
            map.put("serviceurl",serviceurl);//业务联络单url
            String repairqueryurl= ResourceUtil.getConfigByName("singlelogin.repairquery.url");
            repairqueryurl=String.format(repairqueryurl,username,pwd);
            map.put("repairqueryurl",repairqueryurl);//报修单状态查询url
        }

        j.setAttributes(map);
        return j;
    }

    /**
     * 获取录音记录表
     * @param req
     * @return
     */
    @RequestMapping(params = "getHlOrderReord")
    @ResponseBody
    public AjaxJson getHlOrderReord(HttpServletRequest req){
        AjaxJson j = new AjaxJson();
        String bussinessId = req.getParameter("businessId");
        //String sql = "SELECT t.recordpath,t.recordcode,t.recordtype from HL_ORDERRECORD  t INNER JOIN HL_HISTORY_CALLS n  ON  t.\"ID\" = n.ORDERRECORDID where n.\"ID\" IN(SELECT HISTORY_CALL_ID from HL_CALL_BUSINESS   where  BUSINESS_ID = ?)";
        String bizSql="select ORDERRECORDID from HL_HISTORY_CALLS,HL_CALL_BUSINESS where HL_HISTORY_CALLS.ID=HL_CALL_BUSINESS.HISTORY_CALL_ID and BUSINESS_ID = ?";
        List<Map<String,Object>> bizList = service.findForJdbc(bizSql,bussinessId);
        String callIds="";
        if(bizList.size()>0) {
            for(int i=0;i<bizList.size();i++)
            {
                Map<String,Object> one= bizList.get(i);
                String oneId=one.get("ORDERRECORDID").toString();
                callIds=callIds+"'"+oneId+"',";
            }
            if(callIds.lastIndexOf(",")>0)
            {
                callIds=callIds.substring(0,callIds.length()-1);
            }
            String recordSql = "SELECT call_uuid,start_time recordcode,'ChannelTypeAudio' recordtype,call_lasts_time,(SELECT record_file_name FROM cti_record WHERE customer_uuid=cti_cdr_call.call_uuid LIMIT 1) recordpath FROM cti_cdr_call where call_lasts_time>0 and call_uuid in ("+callIds+")";
            List<Map<String,Object>> objList = ctiJdbcTemplate.queryForList(recordSql);
            j.setObj(objList);
        }
        return j;
    }

    /**
     * 直接播放录音
     * @param req
     * @return
     */
    @RequestMapping(params = "download")
    public void download(HttpServletRequest req, HttpServletResponse response){
        String path = req.getParameter("path");

        try {
            OutputStream os = response.getOutputStream();
            fileManageDao.download(os,path);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    /**
     * 直接播放录音
     * @param req
     * @return
     */
    @RequestMapping(params = "downloadFile")
    public void downloadFile(HttpServletRequest req, HttpServletResponse response){
        String path = req.getParameter("path");
        path = URLDecoder.decode(path);

        try {
            int m = path.lastIndexOf("/");
            String recordName = path.substring(m+1);
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName="
                    + new String(recordName.getBytes("GBK"), "iso8859-1"));
            OutputStream os = response.getOutputStream();
            fileManageDao.download(os,path);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
