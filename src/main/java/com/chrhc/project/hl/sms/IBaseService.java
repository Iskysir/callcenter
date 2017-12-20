package com.chrhc.project.hl.sms;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/4.
 */
public interface IBaseService {
    public String sendMessage(String mobile,String msg);
    public void  saveInfoAndsendMs(Map data, HttpServletRequest request);
    public void  saveInfoAndsendMsRepair(Map data, HttpServletRequest request);
}
