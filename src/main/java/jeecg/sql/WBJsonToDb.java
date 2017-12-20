package jeecg.sql;

import org.codehaus.groovy.runtime.StringBufferWriter;

import java.io.*;
import java.lang.reflect.Array;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Class.forName;

/**
 * Created by Administrator on 2016/11/13.
 */
public class WBJsonToDb {
    /**
     * 读取txt文件的内容
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
    public static String replaceStringDate(String sqlInsert)
    {
        String sqlInsertReturn=sqlInsert;
        int indexOfBegin=0;
        while(true)
        {
            indexOfBegin=sqlInsertReturn.indexOf("'2016-",indexOfBegin+10);
            if(indexOfBegin<0)
            {
                break;
            }

            String dateString=sqlInsertReturn.substring(indexOfBegin+1,indexOfBegin+22);
            String dateStringTime="";
            dateStringTime="to_date('"+dateString.substring(0,(dateString.length()-2))+"','yyyy-mm-dd hh24:mi:ss')";
            sqlInsertReturn=sqlInsertReturn.replaceAll("'"+dateString+"'",dateStringTime)+"";
        }
        return sqlInsertReturn;
    }
    public static void main(String[] args)
    {
        try {
            Class driverClass = forName("oracle.jdbc.driver.OracleDriver");
            String url="jdbc:oracle:thin:@127.0.0.1:1521:XE";
            java.sql.Connection conn= DriverManager.getConnection(url,"hotline","hotline1");
            File file =new File("G:\\yitong\\易通\\data\\hl_call_business.txt");
            String tableName=file.getName().substring(0,file.getName().indexOf(".txt"));
            String insertStart="insert into "+tableName+" ";
            String fileRaw=txt2String(file);
            String insertField="("+fileRaw.substring(fileRaw.indexOf("[")+2,fileRaw.indexOf("]"))+")";
            String valuesRaw=fileRaw.substring(fileRaw.indexOf("]")+2,fileRaw.length()-2);
            String[] valueData=valuesRaw.split("],");
            String [] sqlExecute=new String[valueData.length];
           Statement st= conn.createStatement();
            st.addBatch("delete from " +tableName);
            //st.executeBatch();
            //conn.setAutoCommit(false);
            for(int i=0;i<valueData.length;i++)
            {
                //st= conn.createStatement();
                String sqlInsert=insertStart+(insertField+" values"+valueData[i].replaceAll("\"","'")).replace("[","(").replace("]",")")+")";
                String sqlstr=replaceStringDate(sqlInsert);
               /* String dateString=sqlInsert.substring(sqlInsert.indexOf("2016-"),(sqlInsert.indexOf("2016-")+21));
                String dateStringTime="";
                dateStringTime="to_date('"+dateString.substring(0,(dateString.length()-2))+"','yyyy-mm-dd hh24:mi:ss')";
                sqlInsert=sqlInsert.replaceAll("'"+dateString+"'",dateStringTime)+"";*/

                sqlExecute[i]=sqlstr;
                System.out.println(sqlExecute[i]+";");
                //st.execute(sqlstr);
                st.addBatch(sqlExecute[i]);
                //System.out.println(sqlExecute[i]);
            }
           //int result[]= conn.prepareStatement(Arrays.toString(sqlExecute)).executeBatch();
            int result[]=st.executeBatch();
            conn.commit();
            conn.close();
           // System.out.println(Arrays.toString(sqlExecute));
            //System.out.println(insertField);
            //BufferedReader
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
