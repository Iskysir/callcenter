package com.chrhc.Interceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jeecgframework.core.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Aspect
@Component
public class InterceptorForSave extends HandlerInterceptorAdapter {

	@Autowired
	CommonService commonService;

	// @Pointcut("execution(* com.chrhc.projects.hotline.business.service..*.save*(..)) ")
	/*@Pointcut("execution(* com.chrhc.project.sc.*.service.impl.*ServiceImpl.updateMain(..)) ")
	public void updateMain() {

	}

	@Around(value = "updateMain()")
	public Object executesave(ProceedingJoinPoint pjp) throws InterceptorException {
		Object result = null;
		Object[] args = pjp.getArgs();

		Class objClass = args[0].getClass();
		String objType = objClass.getSimpleName();

		Integer versionNum = 0;//实体类的version_num
		String id = "";//实体类的id
		try {
			Method m1 = (Method) objClass.getMethod("getVersionNum");
			versionNum = (Integer) m1.invoke(args[0]);

			Method m2 = (Method) objClass.getMethod("getId");
			id = (String) m2.invoke(args[0]);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		if (null != id && !"".equals(id) && !"null".equals(id)) {
			//获得数据库中该条数据的version_num
			StringBuffer sqlBuffer = new StringBuffer("from " + objType + " ab where 1=1 ");
			sqlBuffer.append(" and ab.id = ? ");
			List<Object> listDb = commonService.findHql(sqlBuffer.toString(), id);

			if (null == listDb || listDb.size() == 0) {
				//说明目前数据库中没有该条数据
				try {
					if ("null".equals(versionNum) || "".equals(versionNum) || null == versionNum) {
						Method m3 = (Method) objClass.getMethod("setVersionNum", Integer.class);
						m3.invoke(args[0], 0);
					}
					result = pjp.proceed(args);
				} catch (Throwable e) {
					e.printStackTrace();
				}
				return result;
			}
			int numDb = 0;//数据库中，该条数据的version_num
			if (null != listDb && listDb.size() > 0) {
				//当数据库中有该条数据时
				Method m4;
				try {
					m4 = (Method) objClass.getMethod("getVersionNum");
					numDb = (Integer) m4.invoke(listDb.get(0));
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

			}
			if (versionNum == numDb) {
				//提交的实体类的version_num与数据库中的version_num相等
				try {
					Method m5 = (Method) objClass.getMethod("setVersionNum", Integer.class);
					m5.invoke(args[0], versionNum + 1);
					result = pjp.proceed(args);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			} else {
				//若不相等，则抛出定义的异常
				throw new InterceptorException("");
			}
		}
		return result;
	}
*/
}
