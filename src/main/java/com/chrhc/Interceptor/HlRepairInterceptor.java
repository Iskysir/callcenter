package com.chrhc.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.chrhc.framework.cgfrom.engine.ChrhcFormInterceptor;
import com.chrhc.project.hl.sms.IBaseService;
import oracle.jdbc.driver.OracleTypes;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.HttpUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/24.
 */
@Service("HlRepairInterceptorService")
@Transactional
public class HlRepairInterceptor implements ChrhcFormInterceptor {
  private static Logger logger = Logger.getLogger(HlRepairInterceptor.class);

  /*  @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplatea;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SystemService systemService;
    @Resource(name="fileManageDaoImplBySFTP")
    private FileManageDao fileManageDao;*/

  /**
   * 修改派工状态
   * @param data
   * @param jdbcTemplate
     */
  private  void updatePgzt(Map data,JdbcTemplate jdbcTemplate){
    String id = String.valueOf(data.get("id"));//主键ID
    //报修派工后，修改派工状态为已生成派工
    String sqla= "update hl_repair  t set t.pgzt = ? where t.id = ? ";
    jdbcTemplate.update(sqla,"2",id);
  }

  private void ncDispatching(Map data,JdbcTemplate jdbcTemplate){
    String devid = String.valueOf(data.get("devid"));//设备id
   // devid = "1001A110000000006TP5";
    String pwnrs = String.valueOf(data.get("whlxnew"));//维护类型
   // pwnrs = "001";
    String rwms = String.valueOf(data.get("gzms"));//表单故障表述
    String zprys = String.valueOf(data.get("userid"));//表单转派人员id
    String userid = ResourceUtil.getSessionUserName().getId();//登录人id
    logger.info("useridnc=="+userid);
    String isBack = String.valueOf(data.get("sfxyhf"));//是否需要回访
    if(StringUtil.isNotEmpty(isBack)){
      if("Y".equals(isBack)){
        isBack = "1";
      }else {
        isBack = "0";
      }
    }else{
      isBack = "1";
    }
    logger.info("派工参数==设备id="+devid+"维护类型="+pwnrs+"表单转派人员id="+zprys+"登录人id="+userid+"isBack="+isBack);
    String result= HttpUtils.ncDispatching(devid,pwnrs,rwms,zprys,userid,isBack);
    JSONObject json=JSONObject.parseObject(result);
    logger.info("ncpgjson=="+json);
    String flag = String.valueOf(json.get("FLAG"));
    if("Y".equals(flag)){
      String ncPgId = String.valueOf(json.get("PK_HQTASKALLOT"));
      String id = String.valueOf(data.get("id"));//主键ID
      //报修派工后，增加nc系统工单主键
      String sqla= "update hl_repair  t set t.ncpgid = ? where t.id = ? ";
      jdbcTemplate.update(sqla,ncPgId,id);
    }
  }
  /**
   *总部派工调用存储过程
   * @param data
   * @param jdbcTemplate
     */
    private void dispatching(Map data,JdbcTemplate jdbcTemplate){
      String devid = String.valueOf(data.get("devid"))+",";//设备id

      String pwnrs = String.valueOf(data.get("whlxnew"));//维护类型
      if(StringUtil.isEmpty(pwnrs) || "null".equals(pwnrs)){
         pwnrs = "675606f9-d029-407a-7e44-c5bec232aa14";//默认手工派工
      }
      pwnrs = pwnrs+",";
      String rwms = String.valueOf(data.get("gzms"))+"^";//表单故障表述
      String zprys = String.valueOf(data.get("userid"))+",";//表单转派人员id
      String userid = ResourceUtil.getSessionUserName().getId();//登录人id
      DataSource dataSource =  jdbcTemplate.getDataSource();
      Connection conn = null;
      ResultSet rsa = null;
      CallableStatement cs = null;
      try {
        conn = dataSource.getConnection();
        cs = conn.prepareCall("{Call LC00019999.ERP_ECTON_TASKBILL.ZBPG(?,?,?,?,?,?)}");
        cs.setString(1, devid);// 设置输入参数的值
        cs.setString(2, pwnrs);
        cs.setString(3, rwms);
        cs.setString(4, zprys);
        cs.setString(5, userid);
        cs.registerOutParameter(6, OracleTypes.CURSOR);// 注册输出参数的类型
        cs.execute();
        //cs.close();
        rsa = (ResultSet) cs.getObject(6);
        //ResultSet rs = cs.getResultSet();
        if(rsa != null){
          while (rsa.next()) {// 转换每行的返回值到Map中
            Map rowMap = new HashMap();
            String aa = rsa.getString(1);
            //String bb = rsa.getString(2);
          }
        }
        //   rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }finally {
        //关闭连接
        try {
          if(rsa != null){
            rsa.close();
          }
          if(cs != null){
            cs.close();
          }
          if(conn != null){
            conn.close();
          }
        }catch (SQLException e) {
          e.printStackTrace();
        }

      }
    }
    @Override
    public void beforeAdd(Map data, HttpServletRequest request) {

    }

    @Override
    public void afterAdd( Map data, HttpServletRequest request) {

      //总部派单调用存储过程
      JdbcTemplate jdbcTemplate =  (JdbcTemplate)request.getAttribute("jdbcTemplate");
       // ApplicationContext app = ApplicationContextUtil.getContext();
       // String[] ss =  app.getBeanDefinitionNames();
       // JdbcTemplate JdbcTemplate =  (JdbcTemplate)app.getBean("JdbcTemplate");

      //  String aa = fileManageDao.toString();
        //DataSource dataSource =  jdbcTemplate.getDataSource();
        //报修单新增后调用业务存储过程

      this.ncDispatching(data,jdbcTemplate);
      //修改派工状态为已派工
      this.updatePgzt(data,jdbcTemplate);
      //保存后发送短信
      IBaseService baseService = (IBaseService)request.getAttribute("baseService");
      baseService.saveInfoAndsendMs(data,request);
      baseService.saveInfoAndsendMsRepair(data,request);
       /* List resultList = (List) jdbcTemplate.execute(
                new CallableStatementCreator() {
                    public CallableStatement createCallableStatement(Connection con) throws SQLException {
                        String storedProc = "{call LC000019999.ERP_ECTON_TASKBILL.ZBPG(?,?,?,?,?,?}";// 调用的sql
                        CallableStatement cs = con.prepareCall(storedProc);
                        cs.setString(1, devid);// 设置输入参数的值
                        cs.setString(2, pwnrs);
                        cs.setString(3, rwms);
                        cs.setString(4, zprys);
                        cs.setString(5, userid);
                        cs.registerOutParameter(6, OracleTypes.CURSOR);// 注册输出参数的类型
                        return cs;
                    }
                }, new CallableStatementCallback() {
                    public Object doInCallableStatement(CallableStatement cs) throws SQLException,DataAccessException {
                        List resultsMap = new ArrayList();
                        cs.execute();
                        ResultSet rs = (ResultSet) cs.getObject(6);// 获取游标一行的值
                        while (rs.next()) {// 转换每行的返回值到Map中
                            Map rowMap = new HashMap();

                        }
                        rs.close();
                        return resultsMap;
                    }
                });*/


    }

    @Override
    public void beforeUpdate(Map data, HttpServletRequest request) {

    }

    @Override
    public void afterUpdate(Map data, HttpServletRequest request) {
      JdbcTemplate jdbcTemplate =  (JdbcTemplate)request.getAttribute("jdbcTemplate");
      String pgzt = String.valueOf(data.get("pgzt"));//派工状态
      String bx_type = String.valueOf(data.get("bx_type"));//报修种类
      if("1".equals(bx_type)){
        if("1".equals(pgzt)){
          this.ncDispatching(data,jdbcTemplate);
          //修改派工状态为已派工
          this.updatePgzt(data,jdbcTemplate);
        }
      }
    }

    @Override
    public void beforeDelete(String id, HttpServletRequest request) {

    }

    @Override
    public void afterDelete(String id, HttpServletRequest request) {

    }
}
