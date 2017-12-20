package com.chrhc.Interceptor;

import com.chrhc.framework.cgfrom.engine.ChrhcFormInterceptor;
import com.chrhc.project.hl.sms.IBaseService;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/22.
 *商户加盟模块拦截器
 */
public class hLSendMsgInterceptor implements ChrhcFormInterceptor{
    @Override
    public void beforeAdd(Map data, HttpServletRequest request) {

    }

    @Override
    public void afterAdd(Map data, HttpServletRequest request) {
        //保存后发送短信
        IBaseService baseService = (IBaseService)request.getAttribute("baseService");
        String tel = String.valueOf(data.get("tel"));
        String content = String.valueOf(data.get("content"));
        String id = String.valueOf(data.get("id"));
        String res = baseService.sendMessage(tel,content);
        String[] value = res.split("\\s+");
        String send_status = "99";
        if(value.length > 2){
            send_status = value[1];
        }
        JdbcTemplate jdbcTemplate =  (JdbcTemplate)request.getAttribute("jdbcTemplate");
        String sql = "update sms_send set send_status = ? where id = ?";
        int i = jdbcTemplate.update(sql,send_status,id);
        //System.out.print(data+"测试");
    }

    @Override
    public void beforeUpdate(Map data, HttpServletRequest request) {

    }

    @Override
    public void afterUpdate(Map data, HttpServletRequest request) {

    }

    @Override
    public void beforeDelete(String id, HttpServletRequest request) {

    }

    @Override
    public void afterDelete(String id, HttpServletRequest request) {

    }
}
