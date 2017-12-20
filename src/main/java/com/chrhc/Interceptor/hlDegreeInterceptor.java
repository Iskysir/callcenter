package com.chrhc.Interceptor;

import com.chrhc.framework.cgfrom.engine.ChrhcFormInterceptor;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/18.
 */
public class hlDegreeInterceptor implements ChrhcFormInterceptor {
    @Override
    public void beforeAdd(Map data, HttpServletRequest request) {

    }

    @Override
    public void afterAdd(Map data, HttpServletRequest request) {

    }

    @Override
    public void beforeUpdate(Map data, HttpServletRequest request) {

    }

    @Override
    public void afterUpdate(Map data, HttpServletRequest request) {

        JdbcTemplate jdbcTemplate =  (JdbcTemplate)request.getAttribute("jdbcTemplate");
        String bus_type = String.valueOf(data.get("bus_type"));
        String bus_id = String.valueOf(data.get("bus_id"));
        String st_state = String.valueOf(data.get("st_state"));
        String sfhfName = "SFHF";
        String sfhf = "N";//默认未回访
        if("Y".equals(st_state)){
            sfhf = "Y";
        }
        if("hl_repair".equalsIgnoreCase(bus_type)){
            sfhfName = "SFHF";
        }else if("hl_consultation".equalsIgnoreCase(bus_type)){
            sfhfName = "SFHF";
        }else  if("HL_COMPLAINT".equalsIgnoreCase(bus_type)){
            sfhfName = "IS_VISIT";
        }else if("hl_BuAlliance".equalsIgnoreCase(bus_type)){
            sfhfName = "SFHF";
        }
        String sql = "update "+bus_type+" t set t."+sfhfName+" = ? where t.id=?";
        //修改业务表单回访状态
        int i =  jdbcTemplate.update(sql,sfhf,bus_id);

    }

    @Override
    public void beforeDelete(String id, HttpServletRequest request) {

    }

    @Override
    public void afterDelete(String id, HttpServletRequest request) {

    }
}
