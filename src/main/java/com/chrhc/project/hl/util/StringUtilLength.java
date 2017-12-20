package com.chrhc.project.hl.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */
public class StringUtilLength {
    /**
     * 把原始字符串分割成指定长度的字符串列表
     *
     * @param inputString
     *            原始字符串
     * @param length
     *            指定长度
     * @return
     */
    public static List<String> getStrList(String inputString, int length) {
        int size = inputString.length() / length;
        if (inputString.length() % length != 0) {
            size += 1;
        }
        return getStrList(inputString, length, size);
    }

    /**
     * 把原始字符串分割成指定长度的字符串列表
     *
     * @param inputString
     *            原始字符串
     * @param length
     *            指定长度
     * @param size
     *            指定列表大小
     * @return
     */
    public static List<String> getStrList(String inputString, int length,
                                          int size) {
        List<String> list = new ArrayList<String>();
        for (int index = 0; index < size; index++) {
            String childStr = substring(inputString, index * length,
                    (index + 1) * length);
            list.add(childStr);
        }
        return list;
    }

    /**
     * 分割字符串，如果开始位置大于字符串长度，返回空
     *
     * @param str
     *            原始字符串
     * @param f
     *            开始位置
     * @param t
     *            结束位置
     * @return
     */
    public static String substring(String str, int f, int t) {
        if (f > str.length())
            return null;
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }
    public static  void main(String[] args){
        String  coutent = "你好，收到商户维护请求，故障描述【2017年07月02日，德州市德城区靓车港汽车装具美容护理中心,消费终端:71010135上传了3笔305元的逾期超过设定值消费可疑帐。该商户已经有42天未进行结算，因此，需要立即进行维护。维护时，务必告知商户不及时结算的后果。必要时，可联系其管理方（如店经理、财务或总店）由其安排及时结算。PS:商户不及时结算的后果：1、钱不能及时到账，2、若POS机意外损坏（如不小心摔到地上或泼了一杯水），则钱可能没法到账；3、根据合作协议，未能在7日内结算的交易，特定情况下可以不予划款。】请在2小时内与商户联系沟通维护事宜。";
        List<String> list = StringUtilLength.getStrList(coutent,140);
        for(int i = 0 ;i<list.size();i++){
            System.out.println(i+"="+list.get(i).toString());
        }

    }
}
