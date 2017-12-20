package com.chrhc.project.sc.qrcode.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.FtpUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.web.cgform.entity.upload.CgUploadEntity;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.jeecgframework.web.system.pojo.base.TSAttachment;

import com.chrhc.common.UtilCryptosExtend;
import com.chrhc.project.sc.qrcode.service.ScEwmLogServiceI;

/**
 * 有关二维码解析模块的工具类（
 * 根据文件id获取附件的路径，
 * 根据文件路径获取ftp文件，
 * 图片截取，
 * 根据路径获得文件的扩展名）
 * @author zwt
 *
 */
public class DeEwmUtils {
	//文件路径KEY(用于向map保存“ftp获取文件路径”)
	public static final String FILEPATH="PATH";
	//文件名KEY
	public static  final String FILENAME="NAME";
	//jpg图片格式
	public static  final String EXTENSION_JPG="jpg";
	//png图片格式
	public static  final String EXTENSION_PNG="png";
	//JPEG图片格式
	public static  final String EXTENSION_JPEG="jpeg";
	//gif图片格式
	public static  final String EXTENSION_GIF="gif";
	
	/**
	 * 从ftp获得文件路径与文件名
	 * @param cgFormFieldService
	 * @param cgformid  主文件id
	 * @param fileid    附件文件id
	 * @return List<String>
	 */
     public static  List<String> getPathFromFtp(CgFormFieldServiceI cgFormFieldService,String cgformid){
	 //List<Map<String,String>> reList= new ArrayList<Map<String,String>>(); 
     List<String> reList= new ArrayList<String>(); 
	 
	 List<CgUploadEntity> uploadBeans = cgFormFieldService.findByProperty(CgUploadEntity.class, "cgformId", cgformid);
		
		for(CgUploadEntity b:uploadBeans){
			//Map<String,String> pathMap = new HashMap<String,String>();
			//String title = b.getAttachmenttitle();//附件名
			//String fileKey = b.getId();//附件主键
			String path = b.getRealpath();//附件路径
			//System.out.println(s.lastIndexOf(File.separator));  取得默认文件分割路径（不合适）
			//int index =path.lastIndexOf("/")+1;
			//String filepath =path.substring(0, index);
			//String filename =path.substring(index);
			System.out.println(path);
			//pathMap.put(FILEPATH, filepath);
			//pathMap.put(FILENAME, filename);
			reList.add(path);
		}
	 //返回结果	 
	 return  reList;
	 
     }
	
     /**
 	 * 从ftp获得文件路径(路径+文件名)
 	 * 附件id没传入或者是空串时返回第一个附件path
 	 * @param cgFormFieldService
 	 * @param cgformid  主文件id(可以赋空)
 	 * @param fileid    附件文件id
 	 * @return path  
 	 */
      public static  String getPathFromFtp_all(CgFormFieldServiceI cgFormFieldService,String cgformid,String fileid){
 	 
    	  if(fileid==null||fileid.equals("")){
    		  return "";
    	  }
    	  //cgformid(业务数据id存在情况下) 
    	  if(cgformid!=null&&!cgformid.equals("")){
    		  List<CgUploadEntity> uploadBeans = cgFormFieldService.findByProperty(CgUploadEntity.class, "cgformId", cgformid);
    	 		for(CgUploadEntity b:uploadBeans){
    	 			String path = b.getRealpath();//附件路径
    	 			
    	 			if(fileid==null||"".equals(fileid.trim())){
    	 			    // 如果没有传输附件id则返回第一个附件path
    	 				return  path;	
    	 			}else if(fileid.equals(b.getId())){
    	 			    // 符合条件附件path
    	 				return  path;
    	 			}
    	 		}  
    	  }else{
	    	  List<TSAttachment> uploadBeans = cgFormFieldService.findByProperty(TSAttachment.class, "id", fileid);
	    	  if(uploadBeans==null){
	    		  return "";
	    	  }
	    	  for(TSAttachment b:uploadBeans){
	    	  String path = b.getRealpath();//附件路径
	    	  // 符合条件附件path
	    	  return path;
	    	  }
    	  }
    	  return "";
    	  
      }	
   
      /**
       * 从服务器上下载文件 获得文件
  	   * 将outputsteam转化成inputstream
       * @param path  包含文件名的路径
       * @return  InputStream
       * @throws 
       */
	  public static InputStream  downloadftp(String path){

		//path="upload/files/test3/ftptest_test.jpg"; 
  		ByteArrayInputStream inputstream=null;
  		ByteArrayOutputStream outputStream= new ByteArrayOutputStream ();
  		// ftp工具类
  		FtpUtils ftputil = new FtpUtils();
  		try {
  			int index =path.lastIndexOf("/")+1;
  		    //System.out.println(s.lastIndexOf(File.separator));  取得默认文件分割路径（不合适）
			String directory =path.substring(0, index);
			String fileName =path.substring(index);
			
	  		//调用ftp dowload 方法
	  		ftputil.download(outputStream, directory, fileName);
	  		byte[] outbytes=outputStream.toByteArray();
	  		inputstream =new ByteArrayInputStream(outbytes);
	  		outputStream.flush();
	  	    
	  	    System.out.println("文件字节数:"+outbytes.length);;
	  	    System.out.println("ftp 测试读取完成！！！！");
  	   
  		} catch (FileNotFoundException e) {
  			System.out.println(e.getMessage());
  			e.printStackTrace();
  		}catch(Exception e){
  			System.out.println(e.getMessage());
  			e.printStackTrace();
  			
  		}finally{
  			//关闭输出流
  			if(outputStream!=null){
  				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
  			}
  		}
  		//返回输入流
  		return inputstream;	
  	}
	
     /**
     * 读取图像文件并截取图像文件的区域块(截取左上角 1/4图片大小的 区域)
     * 用 ImageReader
     * @param InputStream 图片输入流
     * @param extension 图片格式
     * @return  BufferedImage 
     */

     public static BufferedImage cutImage(InputStream source,String extension){

    	   BufferedImage bi  =null;
    	   ImageInputStream iis=null;
    	   try{
           // 取得图片读入器
           Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(extension);
           ImageReader reader = (ImageReader)readers.next();
           System.out.println(reader);
           
           // 取得图片读入流
           iis = ImageIO.createImageInputStream(source);
           reader.setInput(iis, true);
           // 图片参数
           ImageReadParam param = reader.getDefaultReadParam();
           int imageIndex = 0;
           int half_width = reader.getWidth(imageIndex)/2;
           int half_height = reader.getHeight(imageIndex)/2;
           
           //截取左上角1/4 区域
	       Rectangle rect = new Rectangle(0, 0, half_width, half_height);
           // Rectangle rect = new Rectangle(40, 40, 600, 600);
           param.setSourceRegion(rect);
           bi = reader.read(0,param); 
           
    	   }catch(IOException e){
    		   e.printStackTrace();
    		   //如果发生异常则返回整个图片的BufferedImage
    		   try {
				bi=ImageIO.read(source);
    		   } catch (IOException e1) {
    			   bi=null;
				e1.printStackTrace();
    		   }
    		   
    	   }finally{
    		   //关闭资源
    		   if(source!=null){
    			   try {
					source.close();
    			   } catch (IOException e) {
					e.printStackTrace();
    			   } 
    		   }
    		   
    		   if(iis!=null){
    			   try {
					iis.close();
				   } catch (IOException e) {
					e.printStackTrace();
				   } 
    		   }
    	   }
    	  //返回
          return bi;
    }
     /**
      * 读取图像文件并截取图像文件的区域块(截取右上角 1/4图片大小的 区域)
      * 用 ImageReader
      * @param InputStream 图片输入流
      * @param extension 图片格式
      * @return  BufferedImage 
      */

      public static BufferedImage cutImage_ys(InputStream source,String extension){

     	   BufferedImage bi  =null;
     	   ImageInputStream iis=null;
     	   try{
            // 取得图片读入器
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(extension);
            ImageReader reader = (ImageReader)readers.next();
            System.out.println(reader);
            
            // 取得图片读入流
            iis = ImageIO.createImageInputStream(source);
            reader.setInput(iis, true);
            // 图片参数
            ImageReadParam param = reader.getDefaultReadParam();
            int imageIndex = 0;
            int half_width = reader.getWidth(imageIndex)/2;
            int half_height = reader.getHeight(imageIndex)/2;
            
            //截取左上角1/4 区域
 	       Rectangle rect = new Rectangle(half_width, 0, half_width, half_height);
            // Rectangle rect = new Rectangle(40, 40, 600, 600);
            param.setSourceRegion(rect);
            bi = reader.read(0,param); 
            
     	   }catch(IOException e){
     		   e.printStackTrace();
     		   //如果发生异常则返回整个图片的BufferedImage
     		   try {
 				bi=ImageIO.read(source);
     		   } catch (IOException e1) {
     			   bi=null;
 				e1.printStackTrace();
     		   }
     		   
     	   }finally{
     		   //关闭资源
     		   if(source!=null){
     			   try {
 					source.close();
     			   } catch (IOException e) {
 					e.printStackTrace();
     			   } 
     		   }
     		   
     		   if(iis!=null){
     			   try {
 					iis.close();
 				   } catch (IOException e) {
 					e.printStackTrace();
 				   } 
     		   }
     	   }
     	  //返回
           return bi;
     }
/************               **************************** 20151214     star          ************************************/
      
      /**
       * 读取图像文件并截取图像文件的区域块(截取右上角 1/4图片大小的 区域)
       * 用 ImageReader
       * @param InputStream 图片输入流
       * @param extension 图片格式
       * @param flag 截取位置flag（1：右上角；2：左上角；3：左下角；4：右下角）
       * @return  BufferedImage 
       */

       public static BufferedImage cutImage_ys(InputStream source,String extension,String flag){

      	   BufferedImage bi  =null;
      	   ImageInputStream iis=null;
      	   try{
             // 取得图片读入器
             Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(extension);
             ImageReader reader = (ImageReader)readers.next();
             System.out.println(reader);
             
             // 取得图片读入流
             iis = ImageIO.createImageInputStream(source);
             reader.setInput(iis, true);
             // 图片参数
             ImageReadParam param = reader.getDefaultReadParam();
             int imageIndex = 0;
             int half_width = reader.getWidth(imageIndex)/2;
             int half_height = reader.getHeight(imageIndex)/2;
             
             Rectangle rect =null;
             //截取左上角1/4 区域
             if(flag.equals("2")){
            	 rect = new Rectangle(0, 0, half_width, half_height);
            	//截取右下角1/4 区域  
             }else if(flag.equals("4")){
            	 rect = new Rectangle(half_width, half_height, half_width, half_height);
            	//截取右上角1/4 区域 
             }else if(flag.equals("1")){
            	 rect = new Rectangle(half_width, 0, half_width, half_height);
            	//截取左下角1/4 区域 
             }else if(flag.equals("3")){
            	 rect = new Rectangle(0, half_height, half_width, half_height);
             }
            
             // Rectangle rect = new Rectangle(40, 40, 600, 600);
             param.setSourceRegion(rect);
             bi = reader.read(0,param); 
             
      	   }catch(IOException e){
      		   e.printStackTrace();
      		   //如果发生异常则返回整个图片的BufferedImage
      		   try {
  				bi=ImageIO.read(source);
      		   } catch (IOException e1) {
      			   bi=null;
  				e1.printStackTrace();
      		   }
      		   
      	   }finally{
      		   //关闭资源
      		   if(source!=null){
      			   try {
  					source.close();
      			   } catch (IOException e) {
  					e.printStackTrace();
      			   } 
      		   }
      		   
      		   if(iis!=null){
      			   try {
  					iis.close();
  				   } catch (IOException e) {
  					e.printStackTrace();
  				   } 
      		   }
      	   }
      	  //返回
            return bi;
      }      
      
 /************               **************************** 20151214     end          ************************************/      
     /**
      * 用 ImageReader 读取图像文件并截取图像文件的区域块(截取自定义大小的 区域) 
      * 如果传入e_x ,e_y 是0 则按照图片的长宽的一半赋值
      * @param source  图片输入流
      * @param extension 图片格式
      * @param s_x  起点x做点(不能超过图片终点坐标)
      * @param s_y  起点y做点(不能超过图片终点坐标)
      * @param e_x  截取图片内容宽度
      * @param e_y  截取图片内容高度
      * @return  BufferedImage
      */
      public static BufferedImage cutImage(InputStream source,String extension,int s_x,int s_y,int e_x,int e_y){
       //    	  public static BufferedImage cutImage(InputStream source,String extension,Integer s_x,Integer s_y,Integer e_x,Integer e_y){
     	   BufferedImage bi  =null;
     	   ImageInputStream iis=null;
     	   try{
            // 取得图片读入器
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(extension);
            ImageReader reader = (ImageReader)readers.next();
            System.out.println(reader);
            
            // 取得图片读入流
            iis = ImageIO.createImageInputStream(source);
            reader.setInput(iis, true);
            // 图片参数
            ImageReadParam param = reader.getDefaultReadParam();
            int imageIndex = 0;
            int half_width = reader.getWidth(imageIndex)/2;
            int half_height = reader.getHeight(imageIndex)/2;
            //如果取得截取块大小参数为零则截取上1/4
            if(e_x==0){
            	e_x=half_width;
            }
            if(e_y==0){
            	e_y=half_height;
            }
            //截取左上角1/4 区域
 	       Rectangle rect = new Rectangle(s_x, s_y, e_x, e_y);
            // Rectangle rect = new Rectangle(40, 40, 600, 600);
            param.setSourceRegion(rect);
            bi = reader.read(0,param); 
            
     	   }catch(IOException e){
     		   e.printStackTrace();
     		   //如果发生异常则返回整个图片的BufferedImage
     		   try {
 				bi=ImageIO.read(source);
     		   } catch (IOException e1) {
     			   bi=null;
 				e1.printStackTrace();
     		   }
     	   }finally{
     		   //关闭资源
     		   if(source!=null){
     			   try {
 					source.close();
     			   } catch (IOException e) {
 					e.printStackTrace();
     			   } 
     		   }
     		   if(iis!=null){
     			   try {
 					iis.close();
 				   } catch (IOException e) {
 					e.printStackTrace();
 				   } 
     		   }
     	   }
     	   //返回
           return bi;
     }
      
	/**
	 * 从文件路径中取得文件的扩展后缀
	 * @param path 
	 * @return 文件后缀（例：jpg）
	 */
	public static String getExtension(String path){
		if(path==null||"".equals(path.trim()))return "";
		if(path.toLowerCase().endsWith(EXTENSION_GIF)){
			return EXTENSION_GIF;
		}else if(path.toLowerCase().endsWith(EXTENSION_JPEG)){
			return EXTENSION_JPEG;
		}else if(path.toLowerCase().endsWith(EXTENSION_JPG)){
			return EXTENSION_JPG;
		}else if(path.toLowerCase().endsWith(EXTENSION_PNG)){
			return EXTENSION_PNG;
		}
		return EXTENSION_JPG;
		
	}
	
	/**
	 * 将得到的json数据字符串转化为list<map> 对象
	 * 
	 * @param data （json数据格式的字符串）
	 * @return 
	 */
	public static List<Map<String,Object>> json2list(String data){
		List<Map<String,Object>> list_map =null;
		//String jsonstr="";
		list_map = JSONHelper.toList(data);
		
		return list_map;
	}
	
	/**
	 * 数据验真 ，根据得到的数据到数据库中比对，
	 * 验证得到的数据是否是真实的数据
	 * @param data （json数据格式的字符串）
	 * @return 
	 */
	public static String verification(String data){
		
		
		
		return "";
	}
/***
 *  解析二维码验真(zwt20151215)
 * @param path
 * @return
 * @throws IOException 
 */
	 public static AjaxJson decodeMethod(String path,ScEwmLogServiceI scEwmLogService,String flag) throws IOException{//Map<String, Object> map,
		Map<String, Object> map= new HashMap<String,Object>();
		String re=null;
		AjaxJson j= new AjaxJson();
		
		//从ftp上取得输入流
		InputStream in= DeEwmUtils.downloadftp(path);
		if(in==null){
			j.setMsg("无法识别！");
			j.setSuccess(false);
			return j;
		}
	
		//in= new FileInputStream("F://123.png");
		//截取图片
		BufferedImage bufimag = DeEwmUtils.cutImage_ys(in, DeEwmUtils.getExtension(path),flag);
		//BufferedImage bufimag = DeEwmUtils.cutImage(in, DeEwmUtils.getExtension(path),0,0,0,0);
		
		if(bufimag==null){
			//return "截取图片后 BufferedImage 空！！！";
			j.setMsg("无法识别！");
			j.setSuccess(false);
			return j;
		}
		
		if(in!=null)in.close();
		bufimag.flush();
		long star =System.currentTimeMillis();
		///// TODO 
		//ImageIO.write(bufimag, "jpg", new File("f:/xfhtestcut1.jpg"));
		//
		String content = QRDecode.de_content(bufimag);
		long end =System.currentTimeMillis();
		System.out.println("解析用时："+(end-star)+"ms  解密之前内容："+content);
		if(content==null||"".equals(content)){
			//return "二维码内容空！！！";
			j.setMsg("无法识别！");
			j.setSuccess(false);
			return j;
		}
		
		//解密（）
	    re = UtilCryptosExtend.decryptString(content);
	    
		System.out.println("解密之后："+re);
		
		if(re==null||"".equals(re.trim())){
			//return " 解密后内容空！！！";
			j.setMsg("无法识别！");
			j.setSuccess(false);
			return j;
		}
		//转化成list对象
		List<Map<String,Object>>  list_map = DeEwmUtils.json2list(re);
		if(list_map==null||list_map.size()<1){
			//return " json转化后无数据！！！";
			j.setMsg("无法识别！");
			j.setSuccess(false);
			return j;
		}
		//遍历数据  并且拼接查询sql串
		 StringBuffer sql_head=new StringBuffer("select ");
		 StringBuffer sql_end=new StringBuffer(" from ");
		Map<String,Object> map_obj = list_map.get(0);
		// 数据表名
		 String tableName=(String) map_obj.get("tablename");
		 String keycode=(String) map_obj.get("keycode");
		 String data_id=(String) map_obj.get("id");
		 String code=(String) map_obj.get("code");
		 map.put("code", code);
		 map.put("data_id", data_id);
		 map.put("tableName", tableName);
		 /*******************                            返回数据添加 zwt 20151215                         ***************/
		 j.setAttributes(map);
		 //表名  (key设置为 tableName)
		 if(tableName==null&&"".equals(tableName.trim())){
			//return "表名空！！！"; 
			j.setMsg("无法识别！");
			j.setSuccess(false);
			return j;
		 }
		 sql_end.append(tableName);
		 sql_end.append(" where ");
		 String id=null;//业务数据id
		 // String id_templet=null;//   以后只需要业务数据id不需要模板id   要删除
		 
		 for(String key:map_obj.keySet()){
		
			 if(key==null||"keycode".equals(key.trim())||"tablename".equals(key.trim())){
				 continue;
			 }
			 //展示字段
			 sql_head.append(key+" , ");
			/* //如果是id 加入 where条件
			 if("id".equals(key.trim())){
				 id =(String) map_obj.get(key);
				 sql_end.append(key+"='"+map_obj.get(key)+"' and ") ;
				 continue;
			 }
			 //查询条件
			 sql_end.append(key+"='"+map_obj.get(key)+"' and ") ;*/
			 if("id".equals(key.trim())){
				 id =(String) map_obj.get(key);
				 sql_end.append(key+"='"+id+"'") ;
				 continue;
			 } 
			 
		 }
		 String  sql_head_ = sql_head.substring(0, sql_head.lastIndexOf(","));
		 int andindex =sql_end.lastIndexOf("and");
		 String  sql_end_ ="";
		 if(andindex>0){
			 sql_end_ =sql_end.substring(0, andindex);
		 }else{ sql_end_ =sql_end.substring(0);}
		//根据取得数据查询数据库验证
		 List list= scEwmLogService.GeneralQuerry(sql_head_+sql_end_);
		  if(list==null||list.size()<1){
			 // return "伪数据！！！";
			 j.setMsg("伪数据！");
			 j.setSuccess(true);
			 return j;
		 }
		return j;
	}
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path="";
		try {
			//downloadftp(path);
			//testcutimg();
			String data ="[{\"name\":\"周雨柔\",\"idcard\":\"371402010929124\"}]";
			data="[{'keycode':'jzzm001001','id':'402881d74d4beb84014d4beb846b0000','tablename':'sc_stay',\"name\":\"周雨柔\",\"idcard\":\"371402010929124\", }]";
			List<Map<String,Object>>  list= json2list(data);
			for(Map<String,Object> map:list){
				//遍历map
				for(String key:map.keySet()){
				System.out.println(	"key:"+key+"  value:"+map.get(key));
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testcutimg() throws FileNotFoundException, IOException {
		InputStream in= new FileInputStream("F://xx.jpg");
		BufferedImage bi =cutImage(in,"jpg");
		ImageIO.write(bi, "jpg", new File("f:/b.jpg")); 
		in.close();
	}

}
