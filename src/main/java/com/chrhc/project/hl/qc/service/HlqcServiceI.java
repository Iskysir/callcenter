package com.chrhc.project.hl.qc.service;

import com.fr.report.script.function.MAP;
import org.jeecgframework.core.common.service.CommonService;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/15.
 */
public interface HlqcServiceI extends CommonService {

    /**
     * 查询符合条件的工单
     * @param param
     * @return
     */
    public List<Map<String,Object>> selOrders(Map<String, String> param);
    /**
     * 根据ID查询质检策略
     * @param id
     * @return
     */
    public List<Map<String,Object>> getStrateGy(String id);

    /**
     * 批量保存质检考评项
     * @param taskId
     * @param list
     */
    public void  saveQcTaskItem(String taskId,List<Map<String,Object>> list,String bus_type);
}
