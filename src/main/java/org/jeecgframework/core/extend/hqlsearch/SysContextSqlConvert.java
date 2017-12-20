package org.jeecgframework.core.extend.hqlsearch;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jeecgframework.core.extend.hqlsearch.parse.vo.HqlRuleEnum;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSDataRule;

/**
 * 数据库列表序列化转换sql
 * 
 * @ClassName: SqlJsonConvert
 * @Description: TODO
 * @author Comsys-skyCc cmzcheng@gmail.com
 * @date 2014-8-25 下午7:12:41
 * 
 */
public class SysContextSqlConvert {

	enum Signal {
		GREEN, YELLOW, RED
	}

	/**
	 * 
	 * setSqlModel sql行列转换
	 * 
	 * @Title: setSqlModel
	 * @Description: TODO
	 * @param @param dataRule
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String setSqlModel(TSDataRule dataRule){
		if(dataRule == null) 
		return "";
		String sqlValue="";
		HqlRuleEnum ruleEnum=HqlRuleEnum.getByValue(dataRule.getRuleConditions());
		String tempValue = null;
		if(ruleEnum!=HqlRuleEnum.CUSTOME)
		{
		//-----------------------------------------------------------------------
		//#{sys_user_code}%
		String ValueTemp = dataRule.getRuleValue();
		String moshi = "";
		if(ValueTemp.indexOf("}")!=-1){
			 moshi = ValueTemp.substring(ValueTemp.indexOf("}")+1);
		}
		String returnValue = null;
		//针对特殊标示处理#{sysOrgCode}，判断替换
		if (ValueTemp.contains("#{")) {
			ValueTemp = ValueTemp.substring(2,ValueTemp.indexOf("}"));
		} else {
			ValueTemp = ValueTemp;
		}
		//-----------------------------------------------------------------------
		
		tempValue = ResourceUtil.getUserSystemData(ValueTemp);
		
		if(tempValue!=null){
			tempValue = tempValue + moshi;
		}else{
			tempValue = ValueTemp;
		}
		}
		switch (ruleEnum) {
		case GT:
			sqlValue+=" and "+dataRule.getRuleColumn()+" <'"+tempValue+"'";
			break;
		case GE:
			sqlValue+=" and "+dataRule.getRuleColumn()+" >='"+tempValue+"'";
			break;
		case LT:
			sqlValue+=" and "+dataRule.getRuleColumn()+" <'"+tempValue+"'";
			break;
		case LE:
			sqlValue+=" and "+dataRule.getRuleColumn()+" =>'"+tempValue+"'";
			break;
		case  EQ:
			sqlValue+=" and "+dataRule.getRuleColumn()+" ='"+tempValue+"'";
			break;
		case LIKE:
			sqlValue+=" and "+dataRule.getRuleColumn()+" like '"+tempValue+"'";
			break;
		case NE:
			sqlValue+=" and "+dataRule.getRuleColumn()+" !='"+tempValue+"'";
			break;
		case IN:
			sqlValue+=" and "+dataRule.getRuleColumn()+" IN('"+tempValue+"')";
			break;
		case RIGHT_LIKE:
			sqlValue+=" and "+dataRule.getRuleColumn()+" like '"+tempValue+"%'";
			break;
		case CUSTOME:
			sqlValue+=" and ("+SysContextSqlConvert.setSqlModel(dataRule.getRuleValue())+")";
			break;
		default:
			break;
		}
		
		
		return sqlValue;
	}
	
	/**
	 * 替换sql中#{var}格式的变量
	 * 
	 *  
	 * 
	 */
	public static String setSqlModel(String sql){
 
		 	//取得#{XXX}格式的正则表达式
	        Pattern p = Pattern.compile("\\#\\{[\\w\\!\\s]+\\}");
	        Matcher m = p.matcher(sql);
 
	        while (m.find())
	        {
	        	String s = m.group();
	        	String name = s.substring(2,s.indexOf("}"));
	        	String value = ResourceUtil.getUserSystemData(name);
	        	sql = sql.replace(s, value);
	        } 
	        
		return sql;
	}
	// /**
	// *
	// * setSqlIn sql为in的方法
	// *
	// * @Title: setSqlIn
	// * @Description: TODO
	// * @param @param dataRule
	// * @param @param sqlValue
	// * @param @return 设定文件
	// * @return String 返回类型
	// * @throws
	// */
	// public static String setSqlIn(List<DSDataRule>T dataRule,String
	// sqlValue){
	// sqlValue+="'"+dataRule.getRuleValue()+"',";
	// return sqlValue;
	// }
}
