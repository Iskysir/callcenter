package com.chrhc.project.hl.qc.service.impl;

import com.chrhc.project.hl.qc.service.HlqcServiceI;
import com.chrhc.project.hl.qctaskitem.entity.HlQcTaskitemEntity;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/15.
 */
@Service("HlqcService")
@Transactional
public class HlqcServiceImpl extends CommonServiceImpl implements HlqcServiceI {
    @Override
    public List<Map<String,Object>> selOrders(Map<String, String> param) {
        String startTime = param.get("startTime");
        String endTime = param.get("endTime");
        String target = param.get("target");
        String bus_type = param.get("bus_type");
        StringBuffer sb = new StringBuffer();
        sb.append("select id from ").append(bus_type).append(" where 1=1  ");
        if(StringUtil.isNotEmpty(startTime)){
            sb.append(" and CREATE_DATE >= ?");
        }

        if(StringUtil.isNotEmpty(endTime)){
            sb.append(" and CREATE_DATE <= ?");
        }
        if(StringUtil.isNotEmpty(target)){
            String[] ids = target.split(",");
            StringBuffer ids_ = new StringBuffer();
            for (int i = 0; i < ids.length; i++) {
                if(i == ids.length -1 ){
                    ids_.append("'" + ids[i] + "'");
                }else{
                    ids_.append("'" + ids[i] + "',");
                }
            }
            sb.append(" and CREATE_BY in (" + ids_+ ") ");
        }
        Date srdate = DataUtils.str2Date(param.get("startTime") + " 00:00:00 ",DataUtils.datetimeFormat);
        Date enddate = DataUtils.str2Date(param.get("endTime") + " 23:59:59 ",DataUtils.datetimeFormat);
        List<Map<String,Object>> list = this.findForJdbc(sb.toString(),srdate,enddate);
        return list;
    }

    @Override
    public List<Map<String, Object>> getStrateGy(String id) {
        String sql = "select * from HL_QC_STRATEGY where id = ?";
        List<Map<String,Object>> list =  this.findForJdbc(sql,id);
        return list;
    }

    @Override
    public void saveQcTaskItem(String taskId, List<Map<String, Object>> orderIds,String bus_type) {
        List<HlQcTaskitemEntity> list = new ArrayList<>();
        HlQcTaskitemEntity item ;
        for (int i = 0; i < orderIds.size(); i++) {
            Map<String, Object> map = orderIds.get(i);
            String orderid = String.valueOf(map.get("ID"));
            item = new HlQcTaskitemEntity();
            item.setOrderid(orderid);
            item.setTaskid(taskId);
            item.setOrdertype(bus_type);
            item.setDelflag("0");
            list.add(item);
        }
        this.batchSave(list);
    }
}
