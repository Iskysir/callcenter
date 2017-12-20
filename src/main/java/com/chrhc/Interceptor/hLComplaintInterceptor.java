package com.chrhc.Interceptor;

import com.chrhc.framework.cgfrom.engine.ChrhcFormInterceptor;
import com.chrhc.project.hl.sms.IBaseService;
import com.fr.third.org.apache.poi.util.SystemOutLogger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/22.
 *投诉咨询模块拦截器
 */
public class hLComplaintInterceptor implements ChrhcFormInterceptor{
    @Override
    public void beforeAdd(Map data, HttpServletRequest request) {

    }

    @Override
    public void afterAdd(Map data, HttpServletRequest request) {
        //保存后发送短信
        IBaseService baseService = (IBaseService)request.getAttribute("baseService");
        baseService.saveInfoAndsendMs(data,request);
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
