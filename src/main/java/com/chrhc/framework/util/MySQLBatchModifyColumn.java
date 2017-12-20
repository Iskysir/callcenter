package com.chrhc.framework.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.util.UUIDGenerator;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MySQLBatchModifyColumn {
	static ApplicationContext  context = new ClassPathXmlApplicationContext(  
            new String[] {"classpath:spring_test.xml"});  
	
	
	/**Mysql 按类型批量修改字段类型
	 * @param schema schema
	 * @param table_prefix 表前缀
	 * @param dataType 字段类型
	 * @param destDataType 目标字段类型
	 */
	public static void  modifyColumnByDataTypeForMysql(String schema,String table_prefix,String dataType,String destDataType)
	{
		
		 BeanFactory factory = (BeanFactory) context;  
		 org.springframework.jdbc.core.JdbcTemplate templateObj=(org.springframework.jdbc.core.JdbcTemplate)factory.getBean("jdbcTemplate");
		    List<Map<String,Object>> listFields=templateObj.queryForList("select * from INFORMATION_SCHEMA.columns where DATA_TYPE='"+dataType+"' and TABLE_SCHEMA='"+schema+"' and table_name like '"+table_prefix+"%'");
		    List<String> sqlList=new ArrayList<String>();
		    long begin=System.currentTimeMillis();
		    for(Map<String,Object> field:listFields)
		    {
		    	String tableName=field.get("table_name").toString();
		    	String columnName=field.get("column_name").toString();
		    	String sql="alter table " +tableName +" MODIFY  column " +columnName+" "+destDataType;
		    	//single execute
		    	//templateObj.execute(sql);
		    	
		    	//batch execute
		    	sqlList.add(sql);
		    }
		    
		    
		    //Batch excute:
		    if(sqlList.size()>0)
		    	templateObj.batchUpdate(sqlList.toArray(new String[0]));
		    System.out.println("time expand:"+(System.currentTimeMillis()-begin));
	}
	
	public static void deleteColumnForMysql(String schema,String table_prefix,String cloumnName)
	{
		 BeanFactory factory = (BeanFactory) context;  
		 org.springframework.jdbc.core.JdbcTemplate templateObj=(org.springframework.jdbc.core.JdbcTemplate)factory.getBean("jdbcTemplate");
		 String sqlTable="select table_name from INFORMATION_SCHEMA.tables where  TABLE_SCHEMA='"+schema+"' and table_name like '"+schema+"_%' and table_name  in ( select DISTINCT TABLE_NAME from INFORMATION_SCHEMA.columns where TABLE_SCHEMA='"+schema+"' and table_name like '"+schema+"_%' and  COLUMN_NAME ='"+cloumnName+"')";  
		 List<Map<String,Object>> listFields=templateObj.queryForList(sqlTable);
		    List<String> sqlList=new ArrayList<String>();
		    long begin=System.currentTimeMillis();
		    for(Map<String,Object> field:listFields)
		    {
		    	String tableName=field.get("table_name").toString();
		    	String sql=" alter table " +tableName +" drop " + cloumnName +" ";
		    	System.out.println(sql + ";");
		    	sqlList.add(sql);
		    }
		    
		    
		    //Batch excute:
		    if(sqlList.size()>0)
		    	templateObj.batchUpdate(sqlList.toArray(new String[0]));
		    System.out.println("time expand:"+(System.currentTimeMillis()-begin));
	}
 
	public static void  addColumnByDataTypeForMysql(String schema,String table_prefix,String cloumnName,String destDataType)
	{
		
		 BeanFactory factory = (BeanFactory) context;  
		 org.springframework.jdbc.core.JdbcTemplate templateObj=(org.springframework.jdbc.core.JdbcTemplate)factory.getBean("jdbcTemplate");
		 String sqlTable="select table_name from INFORMATION_SCHEMA.tables where  TABLE_SCHEMA='"+schema+"' and table_name like '"+schema+"_%' and table_name not in ( select DISTINCT TABLE_NAME from INFORMATION_SCHEMA.columns where TABLE_SCHEMA='"+schema+"' and table_name like '"+schema+"_%' and  COLUMN_NAME ='"+cloumnName+"')";  
		 List<Map<String,Object>> listFields=templateObj.queryForList(sqlTable);
		    List<String> sqlList=new ArrayList<String>();
		    long begin=System.currentTimeMillis();
		    for(Map<String,Object> field:listFields)
		    {
		    	String tableName=field.get("table_name").toString();
		    	String sql=" alter table " +tableName +" add " + cloumnName +" "+destDataType;
		    	System.out.println(sql + ";");
		    	sqlList.add(sql);
		    }
		    
		    
		    //Batch excute:
		    if(sqlList.size()>0)
		    	templateObj.batchUpdate(sqlList.toArray(new String[0]));
		    System.out.println("time expand:"+(System.currentTimeMillis()-begin));
	}
	
	public static void addOperationAll_without信息模板()
	{
		 BeanFactory factory = (BeanFactory) context;  
		 org.springframework.jdbc.core.JdbcTemplate templateObj=(org.springframework.jdbc.core.JdbcTemplate)factory.getBean("jdbcTemplate");
		 String functionSql="select * from t_s_function where t_s_function.functionname!='信息模板'";
		 List<Map<String,Object>> listFields=templateObj.queryForList(functionSql);
		 List<String> sqlList=new ArrayList<String>();
		 for(Map<String,Object> obj:listFields)
		 {
			 String functionId=obj.get("id").toString();
			 String operationSqlAdd="insert into t_s_operation values('"+UUIDGenerator.generate()+"','add','','新增','0','"+functionId+"','8a8ab0b246dc81120146dc8180460000','0') ";
			 String operationSqlUpdate="insert into t_s_operation values('"+UUIDGenerator.generate()+"','update','','修改','0','"+functionId+"','8a8ab0b246dc81120146dc8180460000','0') ";
			 String operationSqlDelete="insert into t_s_operation values('"+UUIDGenerator.generate()+"','delete','','删除','0','"+functionId+"','8a8ab0b246dc81120146dc8180460000','0') ";
			 String operationSqlExport="insert into t_s_operation values('"+UUIDGenerator.generate()+"','excel','','导出Excel','0','"+functionId+"','8a8ab0b246dc81120146dc8180460000','0') ";
			 sqlList.add(operationSqlAdd);
			 sqlList.add(operationSqlUpdate);
			 sqlList.add(operationSqlDelete);
			 sqlList.add(operationSqlExport);
			 
		 }
		 templateObj.batchUpdate(sqlList.toArray(new String[0]));
	}
	
	
	public static void  addCgFormFiled(String schema,String table_prefix,String cloumnName,String chineseName,String length)
	{
		
		 BeanFactory factory = (BeanFactory) context;  
		 org.springframework.jdbc.core.JdbcTemplate templateObj=(org.springframework.jdbc.core.JdbcTemplate)factory.getBean("jdbcTemplate");
		    List<Map<String,Object>> listFields=templateObj.queryForList("select DISTINCT table_name from INFORMATION_SCHEMA.columns where TABLE_SCHEMA = '"+schema+"' and table_name like '"+table_prefix+"_%'");
		    List<String> sqlList=new ArrayList<String>();
		    sqlList.add("delete from cgform_field where field_name='"+cloumnName+"'");
		    long begin=System.currentTimeMillis();
		    for(Map<String,Object> field:listFields){
		    	String tableName = field.get("table_name").toString();
		    	
		    	List<Map<String,Object>> temp =templateObj.queryForList("select * from cgform_head t1 where t1.table_name = '" + tableName +"'"); 
		    	
		    	if(temp != null && !temp.isEmpty()){
		    		Map obj = temp.get(0);
		    		String sql ="INSERT INTO `cgform_field` VALUES ('"+ UUIDGenerator.generate()+"', '"+chineseName+"', '"+obj.get("create_by")+"', '2015-07-21 10:41:04', '"+obj.get("create_by")+"', '', '', '', '0', '', '120', '"+cloumnName+"', '', 'N', 'Y', 'N', 'N', 'N', '"+length+"', '', '', null, '"+cloumnName+"', '24', '0', 'single', 'text', 'string', null, null, null, '"+obj.get("id")+"', '', '', '');" ;
		    		/*"INSERT INTO `cgform_field` VALUES ('" + UUIDGenerator.generate() +"', '"+chineseName+"', null, null, null, '', '', '', '', '', '120', '"+cloumnName+"', '', 'N', 'Y', 'N', 'N', 'N', '"+length+"', '', '',null,'create_name', '6', '0', 'single', 'text', 'string', " +
		    				"'" + obj.get("create_by") + "', null, '" + obj.get("create_name") +"', '" +  obj.get("id") +"', '', '')";*/
		    		sqlList.add(sql);
		    		System.out.println(sql);
		    	}
			   
			    
		    }
		    if(sqlList.size()>0)
		    	templateObj.batchUpdate(sqlList.toArray(new String[0]));
		    System.out.println("time expand:"+(System.currentTimeMillis()-begin));
	}
	
	public static void main(String[] args)
	{
		
		//modifyColumnByDataTypeForMysql("sc","sc_","double","double(14,4)");
		//addColumnByDataTypeForMysql("sc","sc_","sys_org_code","varchar(50)");
		
		//deleteColumnForMysql("sc","sc_","delflag");
		//addColumnByDataTypeForMysql("sc","sc_","delflag","varchar(1) default '0' ");
		//addCgFormFiled("sc","sc_","delflag","删除标志","1");
		//addOperationAll_without信息模板();
	}
}
