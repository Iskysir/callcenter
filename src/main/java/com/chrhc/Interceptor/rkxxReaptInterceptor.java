package com.chrhc.Interceptor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.jeecgframework.core.groovy.GroovyScriptEngine;
import org.jeecgframework.web.cgform.dao.rkxx.ScRkjbxxDaoI;
import org.jeecgframework.web.cgform.service.build.DataBaseService;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chrhc.project.sc.rkyw.entity.ScRkBusinesEntity;
import com.chrhc.project.sc.rkyw.entity.ScRkYwConfigEntity;

 


@Component
//@Controller
//注释掉aop
//@Aspect
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class rkxxReaptInterceptor  {
	@Autowired
	private DataBaseService dataBaseService;
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ScRkjbxxDaoI scRkjbxxDao;
	
	/* (non-Javadoc)
	 * @see org.jeecgframework.core.interceptors.rkxxReaptInterceptorInterface#beforeMethod(org.aspectj.lang.JoinPoint)
	 */
	// @Pointcut("execution(* com.chrhc.frameworkweb.cgform.controller.build.ChrhcFormBuildController.saveOrUpdate(..))")
	/* @Pointcut("execution(* org.jeecgframework.web.cgform.service.impl.build.DataBaseServiceImpl.insertTable(..))")
	 public void anyMethod() {
		 System.out.println("……………………切点………………………………");
	    }*/
	/*@Before("execution(* org.jeecgframework.web.cgform.service.impl.build.DataBaseServiceImpl.insertTable(..))")
	public void doBefore(JoinPoint point) throws Exception {
		System.out.println("before ……………前…………………  asp");
		System.out.println("@Before：模拟权限检查...");
        System.out.println("@Before：目标方法为：" + 
                point.getSignature().getDeclaringTypeName() + 
                "." + point.getSignature().getName());
        System.out.println("@Before：参数为：" + Arrays.toString(point.getArgs()));
        System.out.println("@Before：被织入的目标对象为：" + point.getTarget());
	}*/
	/* (non-Javadoc)
	 * @see org.jeecgframework.core.interceptors.rkxxReaptInterceptorInterface#afterMethod(org.aspectj.lang.JoinPoint)
	 */
	
	@AfterThrowing("execution(* org.jeecgframework.web.cgform.service.impl.build.DataBaseServiceImpl.insertTable*(..))")
	public void  throwException (JoinPoint point) throws Exception {
		System.out.println("出错了还要继续执行吗？");
	}
	/**
	 * 在插入业务信息时  插入业务关联表，
	 * 重写人口基本信息表
	 * @param point
	 * @throws Exception
	 */
	@Around("execution(* org.jeecgframework.web.cgform.service.impl.build.DataBaseServiceImpl.insertTable(..)) || execution(* org.jeecgframework.web.cgform.service.impl.build.DataBaseServiceImpl.insertTableMore(..))")
	
	public Object  afterInsertMethod(ProceedingJoinPoint  point) throws Exception {
		System.out.println("before ……………钱 啊…………………  新增的aop");
		Object   revl=null;
		 Object[] paramaters=null;
		try {	
			 /**取得拦截的参数  执行拦截的目标方法*/
	        paramaters = point.getArgs();
			revl = point.proceed(paramaters);
			//System.out.println((int)revl==0);
			//解析参数
			 String tablename ="";
		        Map<String, Object> data =null;
		        for(Object obj:paramaters){
		        	if(obj instanceof String){
		        		tablename=(String) obj;
		        	}else if(obj instanceof Map){
		        		data =(Map<String, Object>) obj;
		        	}
		        }
		    //判断 返回值是否执行成功
			if(revl instanceof Integer){
				int s = (int)revl;
				if(s==0){
					return 0;
				}
			}else if(revl instanceof Map){
				//List<Map<String, Object>>  list_para  =  ((List<Map<String, Object>>)data.get(tablename));
				data = (Map<String, Object>) revl;
			}
			
	        /**根据tablecode 取得人口回写配置表  如果存在数据则进行下一步操作*/
	        List<ScRkYwConfigEntity>  configlist = systemService.findByProperty(ScRkYwConfigEntity.class, "businesscode", tablename);
	        if(configlist==null||configlist.size()==0){
	        	return revl;
	        }
	        ScRkYwConfigEntity scrkconfigentity = configlist.get(0);
	        
	        /**insert 人口业务关联表*/
	        ScRkBusinesEntity scrkbusiness = new ScRkBusinesEntity();
	        scrkbusiness.setBusinesscode(tablename);
	        scrkbusiness.setDelflag("0");
	        scrkbusiness.setBusinessId((String)data.get("id"));
	        String rkxxid =(String)data.get(scrkconfigentity.getRkxxFiled());
	        scrkbusiness.setRkxxId(rkxxid);  //(String)  data.get(scrkconfigentity.getRkxxFiled())
	        systemService.save(scrkbusiness);
	        
	        /**取得业务类型 如果是人口类型 重写 基本信息表*/
	        String businesstype = scrkconfigentity.getBusinesstype();
	      //取得重写依据 的表达式
	        String  expression = scrkconfigentity.getExpression();
	        if("1".equals(businesstype)&&expression!=null&&!"".equals(expression.trim())){
	        	
	    		//执行java表达式 
	    		GroovyScriptEngine groovyScriptEngine = new GroovyScriptEngine();
	    		Object reaptValue = groovyScriptEngine.executeObject(expression, data);
	    		Map<String, Object> redata = new HashMap<String, Object>();
	    		redata = dataBaseService.findOneForJdbc("sc_rkjbxxnew", rkxxid);
	    		redata.put(scrkconfigentity.getReaptfiled(), reaptValue);
	    		dataBaseService.updateTable("sc_rkjbxxnew", rkxxid, redata);
	        	
	        }
			
		} catch (Throwable e1) {
		//  Auto-generated catch block
		System.out.println("run  ……………erro …………………  新增的aop");
		 e1.printStackTrace();
		throw new RuntimeException("业务提交失败 ！");
		//return 0;
		}
	      
	  return revl;      
	}
	
	
	/**
	 * 在更新业务信息时
	 * 重写人口基本信息表（不再操作业务关联表）
	 * @param point
	 * @throws Exception
	 */
	@Around("execution(* org.jeecgframework.web.cgform.service.impl.build.DataBaseServiceImpl.updateTable(..)) || execution(* org.jeecgframework.web.cgform.service.impl.build.DataBaseServiceImpl.updateTableMore(..))")
	
	public Object  afterupdateMethod(ProceedingJoinPoint  point) throws Exception {
		System.out.println("before ……………钱 啊…………………  新增的aop");
		Object   revl=null;
		 Object[] paramaters=null;
		 Map<String, Object> data =null;
		 String tablename ="";
		 String dataid="";
		try {	
			 /**取得拦截的参数  执行拦截的目标方法*/
	        paramaters = point.getArgs();
	        if(paramaters==null||paramaters.length<1){
	        	throw new RuntimeException("参数获取失败！");	
	        }
	        //单表update
	        if(paramaters.length>2){
	        	tablename =(String) paramaters[0];
	        	dataid =(String) paramaters[1];
	        	data =(Map<String, Object>) paramaters[2];
	        	
	        	
	        //一对多 update (mainmap )
	        }else if(paramaters.length>1){
	        	for(Object obj:paramaters){
		        	if(obj instanceof String){
		        		tablename=(String) obj;
		        	}
		        }
	        	for(Object obj:paramaters){
		        	 if(obj instanceof Map){
		        		Map<String, List<Map<String, Object>>> mapMore =(Map<String, List<Map<String, Object>>>) obj;
		        		Map<String, Object> mainMap = mapMore.get(tablename).get(0);
		        		data =(Map<String, Object>) deepClone(mainMap);
		        	}
		        }
	        	
	        }
	        //执行函数体
			revl = point.proceed(paramaters);
			//System.out.println((int)revl==0);
			
		    //判断 返回值是否执行成功
			if(revl instanceof Integer){
				//int s = (int)revl;
				/*if(s==0){
					return 0;
				}*/
			}else if(revl instanceof Boolean){
				//List<Map<String, Object>>  list_para  =  ((List<Map<String, Object>>)data.get(tablename));
				revl=true;
			}
			
	        /**根据tablecode 取得人口回写配置表  如果存在数据则进行下一步操作*/
	        List<ScRkYwConfigEntity>  configlist = systemService.findByProperty(ScRkYwConfigEntity.class, "businesscode", tablename);
	        if(configlist==null||configlist.size()==0){
	        	return revl;
	        }
	        ScRkYwConfigEntity scrkconfigentity = configlist.get(0);
	        String rkxxid =(String)data.get(scrkconfigentity.getRkxxFiled());
	        
	        /**update 时 人口业务关联表不做操作*/
	       /* ScRkBusinesEntity scrkbusiness = new ScRkBusinesEntity();
	        scrkbusiness.setBusinesscode(tablename);
	        scrkbusiness.setDelflag("0");
	        scrkbusiness.setBusinessId((String)data.get("id"));
	        
	        scrkbusiness.setRkxxId(rkxxid);  //(String)  data.get(scrkconfigentity.getRkxxFiled())
	        systemService.save(scrkbusiness);*/
	        
	        /**取得业务类型 如果是人口类型 重写 基本信息表*/
	        String businesstype = scrkconfigentity.getBusinesstype();
	      //取得重写依据 的表达式
	        String  expression = scrkconfigentity.getExpression();
	        if("1".equals(businesstype)&&expression!=null&&!"".equals(expression.trim())){
	        	
	    		//执行java表达式 
	    		GroovyScriptEngine groovyScriptEngine = new GroovyScriptEngine();
	    		Object reaptValue = groovyScriptEngine.executeObject(expression, data);
	    		Map<String, Object> redata = new HashMap<String, Object>();
	    		redata = dataBaseService.findOneForJdbc("sc_rkjbxxnew", rkxxid);
	    		redata.put(scrkconfigentity.getReaptfiled(), reaptValue);
	    		dataBaseService.updateTable("sc_rkjbxxnew", rkxxid, redata);
	        	
	        }
			
		} catch (Throwable e1) {
		//  Auto-generated catch block
		System.out.println("run  ……………erro …………………  新增的aop");
		 e1.printStackTrace();
		throw new RuntimeException("业务提交失败 ！");
		//return 0;
		}
	      
	  return revl;      
	}
	
	
	
	
	//@AfterReturning("execution(* org.jeecgframework.web.cgform.service.impl.build.DataBaseServiceImpl.updateTable(..))")
	//@Around("execution(* org.jeecgframework.web.cgform.service.impl.build.DataBaseServiceImpl.updateTable(..))")
	public void afterUpdateMethod(JoinPoint point) throws Exception {
		System.out.println("after ……………后…………………  更新的aop");
		System.out.println("@AfterReturning：模拟日志记录功能...");
	    System.out.println("@AfterReturning：目标方法为：" + 
	                point.getSignature().getDeclaringTypeName() + 
	                "." + point.getSignature().getName());
	    System.out.println("@AfterReturning：参数为：" +   Arrays.toString(point.getArgs()));
	    //System.out.println("@AfterReturning：返回值为：" );
	    System.out.println("@AfterReturning：被织入的目标对象为：" + point.getTarget());
		
		//		throw new RuntimeException("运行时异常！！！！");
		//		throw new IOException("运行时异常！！！！");
		//		throw new Error("错误啊");
		//	 	throw new Exception("我们的错");
	}
	
	/**
	 * 业务数据单条删除后执行
	 * 删除关联信息（逻辑删除）
	 * 重写个人基本信息
	 * @param point
	 * @return
	 * @throws Exception
	 */
	@Around("execution(* org.jeecgframework.web.cgform.service.impl.autolist.CgTableServiceImpl.delete(..))")
	//@AfterReturning("execution(* org.jeecgframework.web.cgform.service.impl.autolist.CgTableServiceImpl.delete(..))")
	public boolean deletOneMethod(ProceedingJoinPoint point) throws Exception {
		 Object[] paramaters=null;
			try {	
				 /**取得拦截的参数  执行拦截的目标方法*/
		        paramaters = point.getArgs();
				Object   revl = point.proceed(paramaters);
				
				//判断 返回值是否执行成功
				if(!(boolean)revl){
					return false;
				}
				 /** 执行成功之后进行  根据 tablecode  进行人口基本信息回写 */
		        String tablename ="";
		        
		        //CgFormHeadEntity head,String table, Object id
		        tablename =(String) paramaters[1];
		        String data_id =(String) paramaters[2];
		        Map<String,Object> data  = dataBaseService.findOneForJdbc(tablename, data_id);
		        /**根据tablecode 取得人口回写配置表  如果存在数据则进行下一步操作*/
		        List<ScRkYwConfigEntity>  configlist = systemService.findByProperty(ScRkYwConfigEntity.class, "businesscode", tablename);
		        if(configlist==null||configlist.size()==0){
		        	return true;
		        }
		        ScRkYwConfigEntity scrkconfigentity = configlist.get(0);
		        
		        /**删除 人口业务关联表 (逻辑删除)*/     //systemService.getEntity(ScRkBusinesEntity.class,data_id);
		        String hql="from ScRkBusinesEntity where rkxxId=? and businessId=? ";
		        String rkxxid =(String)data.get(scrkconfigentity.getRkxxFiled());
		        String[] param ={rkxxid,data_id};
		        List<ScRkBusinesEntity> listrkbusiness = systemService.findHql(hql, param);
		        if(listrkbusiness!=null&&listrkbusiness.size()>0){
		        	
		        	ScRkBusinesEntity  scrkbusiness =listrkbusiness.get(0);
		        	scrkbusiness.setDelflag("1");
		        	systemService.updateEntitie(scrkbusiness);
		        	//systemService.deleteEntityById(ScRkBusinesEntity.class, (String)data.get("id")); 物理删除
		        }
		        
		        /**取得业务类型 如果是人口类型 重写 基本信息表*/
		        String businesstype = scrkconfigentity.getBusinesstype();
		        //取得重写依据 的表达式
		        String  expression = scrkconfigentity.getExpression();
		        if("1".equals(businesstype)&&expression!=null&&!"".equals(expression.trim())){
		        	//执行java表达式 
		    		GroovyScriptEngine groovyScriptEngine = new GroovyScriptEngine();
		    		Object reaptValue = groovyScriptEngine.executeObject(expression, data);
		    		Map<String, Object> redata = new HashMap<String, Object>();
		    		redata = dataBaseService.findOneForJdbc("sc_rkjbxxnew", rkxxid);
		    		redata.put(scrkconfigentity.getReaptfiled(), reaptValue);
		    		dataBaseService.updateTable("sc_rkjbxxnew", rkxxid, redata);
//		    		将结果 放入reaptEntity中以便更新基本信息表
		    		//RkreaptEntity rkEntity =new RkreaptEntity();
		    		//rkEntity.setId((String)data.get(scrkconfigentity.getRkxxFiled()));
		    		/*Class reaptclass = Class.forName("org.jeecgframework.web.cgform.dao.rkxx.RkreaptEntity");
			        //test = (RkreaptEntity) reaptclass.newInstance();
			        Field rpFiled = reaptclass.getField(scrkconfigentity.getReaptfiled());
			        rpFiled.setAccessible(true);
			        rpFiled.set(rkEntity, (String)reaptValue);*/
		    		//rkEntity.setField(scrkconfigentity.getReaptfiled());
		    		//System.out.println(reaptValue.getClass().getName());
		    		//SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
		    		//String sdate = formatter.format(reaptValue);
		    		//System.out.println(sdate);
		    		/*Object reobj =  new SimpleDateFormat("yyyy-MM-dd").parse(sdate);// HH:mm:ss	
		    		Date dd= (Date)reaptValue;*/
		    		//Timestamp reobj = new Timestamp(dd.getTime());
		    		/*String datastr = "str_to_date('"+sdate+"', '%Y-%m-%d')";
		    		if(reaptValue instanceof String){
		    			
		    			rkEntity.setFieldvalue("'"+reaptValue+"'");
		    		}else{
		    			//rkEntity.setFieldvalue(reobj);
		    			rkEntity.setFieldvalue(datastr);
		    		}*/
			       // scRkjbxxDao.updateRkjbxx(rkEntity);
		        }
				
			} catch (Throwable e1) {
			System.out.println("run  ……………erro …………………  删除的aop");
			e1.printStackTrace();
			throw new RuntimeException("删除失败 ！");
			}
		  return true; 
	}
	
	/**
	 * 批量删除数据
	 * 删除关联信息（逻辑删除）
	 * 重写个人基本信息
	 * @param point
	 * @return
	 * @throws Exception   com.chrhc.project.sc.zhyzh.controller
	 */
	@Around("execution(* org.jeecgframework.web.cgform.service.impl.autolist.CgTableServiceImpl.deleteBatch(..)) ")//||execution(* org.jeecgframework.core.common.service.impl.CommonServiceImpl.deleteLogic(..))
	//@AfterReturning("execution(* org.jeecgframework.web.cgform.service.impl.autolist.CgTableServiceImpl.deleteBatch(..))")
	public boolean afterdeleteBatchMethod(ProceedingJoinPoint point) throws Exception {
		Object[] paramaters=null;
		try {	
			 /**取得拦截的参数  执行拦截的目标方法*/
	        paramaters = point.getArgs();
			Object   revl = point.proceed(paramaters);
			//判断 返回值是否执行成功
			
			
			if(revl instanceof Boolean  ){
				if(!(boolean)revl){
					return false;
				}
			}
			 /** 执行成功之后进行  根据 tablecode  进行人口基本信息回写 */
	        String tablename ="";
	        
	        //CgFormHeadEntity head,String table, String[] ids
	        String[] data_ids=null;
	        tablename =(String) paramaters[1];
		    data_ids =(String[]) paramaters[2];
		    
		    /**根据tablecode 取得人口回写配置表  如果存在数据则进行下一步操作,如果没有则不进行下面操作  */
	        List<ScRkYwConfigEntity>  configlist = systemService.findByProperty(ScRkYwConfigEntity.class, "businesscode", tablename);
	        if(configlist==null||configlist.size()==0){
	        	return true;
	        }
	        ScRkYwConfigEntity scrkconfigentity = configlist.get(0);
	        if(scrkconfigentity==null){
	        	return true;
	        }
	        for(String data_id:data_ids){
	        Map<String,Object> data  = dataBaseService.findOneForJdbc(tablename, data_id);
	        
	        /**删除 人口业务关联表 (逻辑删除)*/     //systemService.getEntity(ScRkBusinesEntity.class,data_id);
	        String hql="from ScRkBusinesEntity where rkxxId=? and businessId=? ";
	        String rkxxid =(String)data.get(scrkconfigentity.getRkxxFiled());
	        if(rkxxid==null||rkxxid.trim().equals("")){
	        	return true;
	        }
	        String[] param ={rkxxid,data_id};
	        List<ScRkBusinesEntity> listrkbusiness = systemService.findHql(hql, param);
	        if(listrkbusiness!=null&&listrkbusiness.size()>0){
	        	
	        	ScRkBusinesEntity  scrkbusiness =listrkbusiness.get(0);
	        	scrkbusiness.setDelflag("1");
	        	systemService.updateEntitie(scrkbusiness);
	        	//systemService.deleteEntityById(ScRkBusinesEntity.class, (String)data.get("id")); 物理删除
	        }
	        
	        /**取得业务类型 如果是人口类型 重写 基本信息表*/
	        String businesstype = scrkconfigentity.getBusinesstype();
	      //取得重写依据 的表达式
	        String  expression = scrkconfigentity.getExpression();
	        if("1".equals(businesstype)&&expression!=null&&!"".equals(expression.trim())){
	        	
	    		//执行java表达式 
	    		GroovyScriptEngine groovyScriptEngine = new GroovyScriptEngine();
	    		Object reaptValue = groovyScriptEngine.executeObject(expression, data);
	    		//System.out.println(reaptValue.getClass().getName());
	    		Map<String, Object> redata = new HashMap<String, Object>();
	    		redata = dataBaseService.findOneForJdbc("sc_rkjbxxnew", rkxxid);
	    		if(redata==null||redata.isEmpty()){
	    			return true;
	    		}
	    		redata.put(scrkconfigentity.getReaptfiled(), reaptValue);
	    		dataBaseService.updateTable("sc_rkjbxxnew", rkxxid, redata);
	        	
	        }
	       }
	        
		} catch (Throwable e1) {
		//  Auto-generated catch block
		System.out.println("run  ……………erro …………………  批量删除的aop");
		e1.printStackTrace();
		throw new RuntimeException("删除失败 ！");
		//return 0;
		}
	      
	  return true; 
	}
	
	
	/**
	 * 批量删除数据(对生成代码部分拦截)
	 * 删除关联信息（逻辑删除）
	 * 重写个人基本信息
	 * @param point
	 * @return
	 * @throws Exception   com.chrhc.project.sc.zhyzh.controller
	 */
	@Around("execution(* org.jeecgframework.core.common.service.impl.CommonServiceImpl.deleteLogic(..)) ")
	//@AfterReturning("execution(* org.jeecgframework.web.cgform.service.impl.autolist.CgTableServiceImpl.deleteBatch(..))")
	public boolean afterdeleteBatchMethod_coding(ProceedingJoinPoint point) throws Exception {
		Object[] paramaters=null;
		try {	
			 /**取得拦截的参数  执行拦截的目标方法*/
	        paramaters = point.getArgs();
			Object   revl = point.proceed(paramaters);
			//判断 返回值是否执行成功
			
			 /** 执行成功之后进行  根据 tablecode  进行人口基本信息回写 */
	        String tablename ="";
	        Object dataobj=paramaters[0];
	        tablename = gettableNameByentity(dataobj);
	        //CgFormHeadEntity head,String table, String[] ids
		    String data_id ="";
		    data_id =(String) PropertyUtils.getProperty(dataobj, "id");
		   // PropertyUtils.setProperty(dataobj, "delflag", "1");
		    if(data_id==null||data_id.trim().equals("")){
		    	return false;
		    }
		    /**根据tablecode 取得人口回写配置表  如果存在数据则进行下一步操作,如果没有则不进行下面操作  */
	        List<ScRkYwConfigEntity>  configlist = systemService.findByProperty(ScRkYwConfigEntity.class, "businesscode", tablename);
	        if(configlist==null||configlist.size()==0){
	        	return true;
	        }
	        ScRkYwConfigEntity scrkconfigentity = configlist.get(0);
	        if(scrkconfigentity==null){
	        	return true;
	        }
	      
	        Map<String,Object> data  = dataBaseService.findOneForJdbc(tablename, data_id);
	        
	        /**删除 人口业务关联表 (逻辑删除)*/     //systemService.getEntity(ScRkBusinesEntity.class,data_id);
	        String hql="from ScRkBusinesEntity where rkxxId=? and businessId=? ";
	        String rkxxid =(String)data.get(scrkconfigentity.getRkxxFiled());
	        if(rkxxid==null||rkxxid.trim().equals("")){
	        	return true;
	        }
	        String[] param ={rkxxid,data_id};
	        List<ScRkBusinesEntity> listrkbusiness = systemService.findHql(hql, param);
	        if(listrkbusiness!=null&&listrkbusiness.size()>0){
	        	
	        	ScRkBusinesEntity  scrkbusiness =listrkbusiness.get(0);
	        	scrkbusiness.setDelflag("1");
	        	systemService.updateEntitie(scrkbusiness);
	        	//systemService.deleteEntityById(ScRkBusinesEntity.class, (String)data.get("id")); 物理删除
	        }
	        
	        /**取得业务类型 如果是人口类型 重写 基本信息表*/
	        String businesstype = scrkconfigentity.getBusinesstype();
	      //取得重写依据 的表达式
	        String  expression = scrkconfigentity.getExpression();
	        if("1".equals(businesstype)&&expression!=null&&!"".equals(expression.trim())){
	        	
	    		//执行java表达式 
	    		GroovyScriptEngine groovyScriptEngine = new GroovyScriptEngine();
	    		Object reaptValue = groovyScriptEngine.executeObject(expression, data);
	    		//System.out.println(reaptValue.getClass().getName());
	    		Map<String, Object> redata = new HashMap<String, Object>();
	    		redata = dataBaseService.findOneForJdbc("sc_rkjbxxnew", rkxxid);
	    		if(redata==null||redata.isEmpty()){
	    			return true;
	    		}
	    		redata.put(scrkconfigentity.getReaptfiled(), reaptValue);
	    		dataBaseService.updateTable("sc_rkjbxxnew", rkxxid, redata);
	        	
	        }
	       
	        
		} catch (Throwable e1) {
		//  Auto-generated catch block
		System.out.println("run  ……………erro …………………  批量删除的aop");
		e1.printStackTrace();
		throw new RuntimeException("删除失败 ！");
		//return 0;
		}
	      
	  return true; 
	}
	
	
	/**
	 * 数据参数转换 暂时没用
	 * @param obj
	 * @return
	 */
	public Object type_trans(Object obj){
		Object reobj=null;
		//"datetime"
		if(obj instanceof java.util.Date){
			try {
				reobj =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(obj));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//"date"//
		}else if(obj instanceof java.util.Date){
			if(obj!=null&&obj.toString().length()>10)
			{
				try {
					reobj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(obj));
				} catch (ParseException e) {
					System.out.println("");
					e.printStackTrace();
				}
			}
			else
			{
				try {
					reobj = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(obj));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}else if("int".equalsIgnoreCase("")){
				
		}else if("double".equalsIgnoreCase("")){
				//double->java.lang.Double
				Object newV = new Double(0);
				try{
					newV = Double.parseDouble(String.valueOf(obj));
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		return obj;
	} 
/***
 * 根据对象取得所相应的table表名
 * @param obj
 * @return
 */
public  String gettableNameByentity(Object obj){
	String tablename="";
	if(obj==null){
		return tablename;
	}
	try{
	Session sessin = systemService.getSession();
	SessionFactory  sessionfactory  = sessin.getSessionFactory();
	AbstractEntityPersister claametdata = (AbstractEntityPersister) sessionfactory.getClassMetadata(obj.getClass());//getClassMapping(obj.getClass().getName());
	tablename =claametdata.getTableName();
	}catch(Exception e){
		e.printStackTrace();
		return tablename;
	}
	return tablename;
}

/***
 * 深度克隆
 * @param obj
 * @return
 * @throws IOException
 * @throws ClassNotFoundException
 */
public Object deepClone(Object obj) throws IOException, ClassNotFoundException{
	
  /* 写入当前对象的二进制流 */  
  ByteArrayOutputStream bos = new ByteArrayOutputStream();  
  ObjectOutputStream oos = new ObjectOutputStream(bos);  
  oos.writeObject(obj);  

  /* 读出二进制流产生的新对象 */  
  ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());  
  ObjectInputStream ois = new ObjectInputStream(bis);  
  return ois.readObject();  
	
}	
}
