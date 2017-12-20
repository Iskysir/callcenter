package org.jeecgframework.core.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fr.report.script.function.COUNT;
import org.jeecgframework.core.constant.DataBaseConstant;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chrhc.project.sc.common.SeriNum;


public class SeriNumGenerateUtils {

    private static WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private static HashMap<String, Integer> map = new HashMap<String, Integer>();

    /**
     * 根据模块ID 返回序列号ID,序列号ID由数据库中配置的前缀+时间（YYYYMMDD）+序号+后缀组成
     * 根据给的参数返回ID
     *
     * @return
     */
    public static synchronized String getId(String Type) {
       String code = "";
  /*       if (Type.equals("CALLCENTER")) {
            String date = sdf.format(new Date());
            SystemService service = wac.getBean(SystemService.class);

            String[] strArr = new String[3];
            try {
                List<SeriNum> list = service.findHql("from SeriNum where idtype = ? and count_date = ? ", Type.toUpperCase(), sdf.parse(date));
                if (list != null && list.size() > 0) {
                    SeriNum seriNum = list.get(0);
                    String n = seriNum.getCurid();
                    String newnum = Integer.parseInt(n) + 1 + "";
                    String prefix = seriNum.getPrefix();
                    String suffix = seriNum.getSuffix();
                    seriNum.setCurid(newnum);
                    service.save(seriNum);
                    strArr[0] = prefix == null ? "" : prefix;
                    strArr[1] = newnum;
                    strArr[2] = suffix == null ? "" : suffix;
                } else {
                    SeriNum srNum = new SeriNum();
                    srNum.setIdtype(Type.toUpperCase());
                    srNum.setCurid("1");
                    srNum.setCount_date(sdf.parse(date));
                    service.save(srNum);
                    String prefix = srNum.getPrefix();
                    String suffix = srNum.getSuffix();
                    strArr[0] = prefix == null ? "" : prefix;
                    strArr[1] = "1";
                    strArr[2] = suffix == null ? "" : suffix;
                }
                DecimalFormat decimalFormat = new DecimalFormat("0000");
                String num = decimalFormat.format(Integer.valueOf(strArr[1]));
                TSUser tsuer = ResourceUtil.getSessionUserName();
                String zxh = tsuer.getUserName();
                code = sdf.format(new Date()) + zxh + num;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            String[] strArr = num(Type);
            code = strArr[0].trim() + sdf.format(new Date()) + fomatnum(Integer.parseInt(strArr[1])) + strArr[2].trim();
        }*/
        String[] strArr = num(Type);
        if("#remark#".equals(strArr[0])){
            code = strArr[1].trim();
        }else{
            code = strArr[0].trim() + sdf.format(new Date()) + fomatnum(Integer.parseInt(strArr[1])) + strArr[2].trim();
        }
        return code;
    }

    /**
     * 对数据进行格式化，默认为六位的数字，从000001开始
     *
     * @param i
     * @return
     */
    private static String fomatnum(int i) {
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        String num = decimalFormat.format(Integer.valueOf(i));
        return num;
    }


    /**
     * 根据模块ID 返回序列号ID,序列号ID由数据库中配置的前缀+序号+后缀组成
     * 根据给的参数返回ID
     *
     * @return
     */
    public static synchronized String getIdNotTime(String Type) {
        String[] strArr = num(Type);
        return strArr[0].trim() + fomatnum(Integer.parseInt(strArr[1])) + strArr[2].trim();
    }


    /**
     * 该方法为强制指定前缀和后缀，不适用默认的数据库中配置的前缀和后缀
     *
     * @param Type
     * @param prefix 编码前缀
     * @param suffix 编码后缀
     * @return
     */
    public static synchronized String getId(String Type, String prefix, String suffix) {
        String[] strArr = num(Type);

        String str = prefix + sdf.format(new Date())
                + fomatnum(Integer.parseInt(strArr[1]))
                + suffix;
        return str;
    }


    private static String[] num(String id) {

        SystemService service = wac.getBean(SystemService.class);

        String[] strArr = new String[3];

        List<SeriNum> ls = service.findHql("from SeriNum where idtype = ?", id.toUpperCase());
        /**添加自定义表达式处理**/
        if(ls != null && ls.size() > 0){
            SeriNum sn =  ls.get(0);
            String remark = sn.getRemark();
            int count = 1;
            if(StringUtil.isNotEmpty(remark)){
                String startId = sn.getStartid();
                DecimalFormat decimalFormat = new DecimalFormat(startId);
                String sql = "select ID,CURRENT_NUMBER  from HL_CODE_COUNT WHERE CM_CODE = ? AND COUNT_DATE = ? ";
                try {
                    List<Map<String, Object>> list =  service.findForJdbc(sql,id,sdf.format(new Date()));
                    if(list != null && list.size() > 0 ) {
                        String keyId = String.valueOf(list.get(0).get("ID"));
                        String num_count = String.valueOf(list.get(0).get("CURRENT_NUMBER"));
                        count = Integer.parseInt(num_count) + 1;
                        String updateSql = "update HL_CODE_COUNT set CURRENT_NUMBER = ?  where ID = ?";
                        service.executeSql(updateSql,String.valueOf(count),keyId);
                    }else{
                         String insertSql = "insert into HL_CODE_COUNT (ID,CM_CODE,CURRENT_NUMBER,COUNT_DATE) values(?,?,?,?) ";
                         service.executeSql(insertSql,UUIDGenerator.generate(),id,count,sdf.format(new Date()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String num = decimalFormat.format(count);
                remark = remark.replace("{"+DataBaseConstant.SYS_USER_CODE_TABLE+"}", ResourceUtil.getUserSystemData(DataBaseConstant.SYS_USER_CODE))
                        .replace("{"+DataBaseConstant.SYS_USER_NAME_TABLE+"}", ResourceUtil.getUserSystemData(DataBaseConstant.SYS_USER_NAME))
                        .replace("{"+DataBaseConstant.SYS_ORG_CODE_TABLE+"}", ResourceUtil.getUserSystemData(DataBaseConstant.SYS_ORG_CODE))
                        .replace("{"+DataBaseConstant.SYS_COMPANY_CODE_TABLE+"}", ResourceUtil.getUserSystemData(DataBaseConstant.SYS_COMPANY_CODE))
                        .replace("{"+DataBaseConstant.SYS_DATE_TABLE+"}",  DateUtils.formatDate("yyyyMMdd"))
                        .replace("{"+DataBaseConstant.SYS_TIME_TABLE+"}",  DateUtils.formatDate("yyyyMMddHHmmss"))
                        .replace("{code}",num);
                strArr[0] = "#remark#";
                strArr[1] = remark;
            }else{
                SeriNum seriNum = (SeriNum) service.findHql("from SeriNum where idtype = ? ", id.toUpperCase()).get(0);
                String n = seriNum.getCurid();
                String newnum = Integer.parseInt(n) + 1 + "";
                String prefix = seriNum.getPrefix();
                String suffix = seriNum.getSuffix();
                seriNum.setCurid(newnum);
                service.save(seriNum);
                strArr[0] = prefix == null ? "" : prefix;
                strArr[1] = n;
                strArr[2] = suffix == null ? "" : suffix;
            }
        }else{
            if (ls == null || ls.size() == 0) {
                SeriNum srNum = new SeriNum();
                srNum.setIdtype(id.toUpperCase());
                srNum.setCurid("1");
                service.save(srNum);
                //seriNum = seriNumDao.getSeriNumByIdType(id.toUpperCase());
            }
            SeriNum seriNum = (SeriNum) service.findHql("from SeriNum where idtype = ? ", id.toUpperCase()).get(0);
            String n = seriNum.getCurid();
            String newnum = Integer.parseInt(n) + 1 + "";
            String prefix = seriNum.getPrefix();
            String suffix = seriNum.getSuffix();
            seriNum.setCurid(newnum);
            service.save(seriNum);
            strArr[0] = prefix == null ? "" : prefix;
            strArr[1] = n;
            strArr[2] = suffix == null ? "" : suffix;
        }
        return strArr;

    }
}
