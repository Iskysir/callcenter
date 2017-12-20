package com.chrhc.project.hl.qc.web;

import com.chrhc.project.hl.qc.service.HlqcServiceI;
import org.jasig.cas.client.util.CommonUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2016/7/14.
 */
@Scope("prototype")
@Controller
@RequestMapping("/HlqcController")
public class HlqcController extends BaseController {

    @Autowired
    private SystemService service;
    @Autowired
    private HlqcServiceI hlqcServiceI;

    /**
     * 质检任务执行
     * @param req
     * @return
     */
    @RequestMapping(params = "implement")
    @ResponseBody
    public AjaxJson implement(HttpServletRequest req){

        AjaxJson j = new AjaxJson();
        TSUser user = ResourceUtil.getSessionUserName();
        //存储符合条件的业务工单
        List<Map<String,Object>> orderIds = new ArrayList<>();
        String id = req.getParameter("id");
        String sql = "select * from hl_qc_task where id = ?";
        List<Map<String,Object>> list =  service.findForJdbc(sql,id);
        if(list != null && list.size() > 0){
            Map<String,Object> map = list.get(0);
            String taskId = String.valueOf(map.get("ID"));
            Date startTime = (Date)map.get("STARTTIME");//开始时间
            Date endTime = (Date)map.get("ENDTIME");//结束时间
            String SCOREPERSON = String.valueOf(map.get("SCOREPERSON"));//评分人员
            String STRATEGYID = String.valueOf(map.get("STRATEGYID"));//质检策略
            String TARGRET = String.valueOf(map.get("TARGRET"));//质检对象
            String HL_BUS_TYPE = String.valueOf(map.get("HL_BUS_TYPE"));//业务类型

            Map<String, String> param = new HashMap<>();

            param.put("startTime", DataUtils.date2Str(startTime,DataUtils.date_sdf));
            param.put("endTime",  DataUtils.date2Str(endTime,DataUtils.date_sdf));
            param.put("target", TARGRET);
            param.put("bus_type", HL_BUS_TYPE);
            List<Map<String,Object>> list1 =hlqcServiceI.selOrders(param);


            List<Map<String,Object>> list2 =  hlqcServiceI.getStrateGy(STRATEGYID);
            if(list2 != null && list2.size() > 0){
                Map<String,Object> map2 = list2.get(0);
                String SAMPLESEL = String.valueOf(map2.get("SAMPLESEL"));
                String SAMPLENUM = String.valueOf(map2.get("SAMPLENUM"));
                if ("all".equals(SAMPLESEL) || list1.size() <= Integer.parseInt(SAMPLENUM)) {
                    orderIds = list1;
                } else {
                    orderIds = selSample(list1, Integer.parseInt(SAMPLENUM));
                }
                hlqcServiceI.saveQcTaskItem(taskId,orderIds,HL_BUS_TYPE);
                String updatesql = "update hl_qc_task set STATE = '2' where ID = ?";
                service.executeSql(updatesql,id);
            }else{
                j.setMsg("质检策略不存在");
                j.setSuccess(false);
            }
        }

        return j;
    }
    private List<Map<String,Object>> selSample(List<Map<String,Object>> list, int num) {
        List<Map<String,Object>> selIds = new ArrayList<>();
        int size = list.size();
        for (int i = size; i > size - num; i--) {
            int index = (int) (Math.random() * i);
            selIds.add(list.get(index));
            list.remove(index);
        }

        return selIds;
    }
}
