package com.chrhc.project.sc.qrcode.controller;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chrhc.common.UtilCryptosExtend;
import com.chrhc.project.sc.qrcode.entity.ScEwmLogEntity;
import com.chrhc.project.sc.qrcode.service.ScEwmLogServiceI;
import com.chrhc.project.sc.qrcode.util.DeEwmUtils;
import com.chrhc.project.sc.qrcode.util.QRDecode;

/**   
 * @Title: Controller
 * @Description: 二维码解析class
 * @author 
 * @date 2015-05-06
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/dec_wmController")
public class DecodEWMController extends BaseController {
	
	
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DecodEWMController.class);
	@Autowired
	private ScEwmLogServiceI scEwmLogService;
	//@Autowired
	//private SystemService systemService;
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	/**
	 * 二维码解析
	 * 
	 * @return  re
	 */
	@RequestMapping(params = "decode")
	@ResponseBody
	public String dec_ewm(HttpServletRequest request,HttpServletResponse respons,String ipath) {  //ModelAndView
		if(ipath==null||"".equals(ipath.trim())){
			return "";
		}
		respons.setCharacterEncoding("utf-8");
		String re = null;
		try {
			//解析二维码内容
			re =QRDecode.de_content(ipath);
			//取得图片的高宽
			String content1 =QRDecode.de_heightAndWidth(ipath);
			re = re+": "+content1;
			//test t = new test();
			//t.test(request);
			//respons.getWriter().write(re);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}
	
	/**
	 *  二维码解析页面跳转 
	 * @param request
	 * @param respons
	 * @param ipath  文件路径（绝对路径）
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(params = "decodeindex")//, method = RequestMethod.POST
	public ModelAndView dec_ewmindex(HttpServletRequest request,HttpServletResponse respons, @RequestParam File  tFile,String fileID) throws IOException {  //ModelAndView
		//respons.setCharacterEncoding("utf-8");
		respons.setContentType("text/html;charset=utf-8");
		ModelAndView mv =new ModelAndView("com/chrhc/project/sc/qrcode/decewm");
		System.out.println(fileID);
		System.out.println(tFile);
		if(tFile==null||"".equals(tFile.getPath())){
			return mv;
		}
//		if(ipath==null||"".equals(ipath)){
//			return mv;
//		}
		System.out.println("AbsolutePath: "+tFile.getAbsolutePath());
		System.out.println("CanonicalPath:"+tFile.getCanonicalPath());
		System.out.println("FreeSpace:"+tFile.getFreeSpace());
		System.out.println("Name:"+tFile.getName());
		System.out.println("Path:"+tFile.getPath());
		System.out.println("ParentFile:"+tFile.getParentFile());
		String ipath =tFile.getAbsolutePath();
		System.out.println(tFile.getPath());
		
		String re = null;
		try {
			//地址中文乱码
			//ipath =new String(ipath.getBytes("iso8859-1"),"utf-8");//(在tomcat中配置了get方式提交编码utf-8，此处省略)
			re =QRDecode.de_content(ipath);
			String content1 =QRDecode.de_heightAndWidth(ipath);
			re = re+": "+content1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("content", re);
		return mv;
	}

	/**
	 * 二维码解析
	 * 
	 * @return  re
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@RequestMapping(params = "ftptest")
	//@ResponseBody
	public ModelAndView  dec_ftptest(HttpServletRequest request,HttpServletResponse respons,String ipath) throws ServletException, IOException {  //ModelAndView
		
		respons.setCharacterEncoding("utf-8");
		String id="402881f44d46c702014d46cbabcd006";
		id="402881f34d372672014d372672ea0000";
		String re = null;
		ModelAndView mv = new ModelAndView();
		try {
			mv.setViewName("redirect:/dec_wmController.do?decodeindex&tFile");
			//request.getRequestDispatcher("/dec_wmController.do?decodeindex&tFile").forward(request, respons);
			/*List<CgUploadEntity> uploadBeans = cgFormFieldService.findByProperty(CgUploadEntity.class, "cgformId", id);
			List<Map<String,Object>> files = new ArrayList<Map<String,Object>>(0);
			for(CgUploadEntity b:uploadBeans){
				String title = b.getAttachmenttitle();//附件名
				String fileKey = b.getId();//附件主键
				String path = b.getRealpath();//附件路径
				
				String field = b.getCgformField();//表单中作为附件控件的字段
				System.out.println(title+":"+fileKey+":"+path);
				Map<String, Object> file = new HashMap<String, Object>();
				file.put("title", title);
				file.put("fileKey", fileKey);
				file.put("path", path);
				file.put("field", field==null?"":field);
				files.add(file);
			}*/
//			DeEwmUtils.getPathFromFtp(cgFormFieldService, id);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return mv;
	}
	
	/**
	 * 二维码解析(根据ftppath取得图片解析)
	 * 
	 * @return  re
	 */
	@RequestMapping(params = "decodeByftpPath")
	@ResponseBody
	public String dec_ewmByFtpPath(HttpServletRequest request,HttpServletResponse respons,String path) {  //ModelAndView
		String re=null;
		path="upload/files/test3/ftptest_test.jpg";//测试使用，在正式环境中删除
		path="upload/files/test3/xfhtest6.jpg";//测试使用，在正式环境中删除
		if(path==null||"".equals(path.trim())){
			return "";
		}
		respons.setCharacterEncoding("utf-8");
		
		try {
			//从ftp上取得输入流
			InputStream in= DeEwmUtils.downloadftp(path);
			if(in==null){
				return "输入流空！！！";
			}
			
			//截取图片
			BufferedImage bufimag = DeEwmUtils.cutImage_ys(in, DeEwmUtils.getExtension(path));
			//BufferedImage bufimag = DeEwmUtils.cutImage(in, DeEwmUtils.getExtension(path),0,0,0,0);
			
			if(bufimag==null){
				return "截取图片后 BufferedImage 空！！！";
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
				return "二维码内容空！！！";
			}
			
			//解密（）
		    re = UtilCryptosExtend.decryptString(content);
			System.out.println("解密之后："+re);
			
			if(re==null||"".equals(re.trim())){
				return " 解密后内容空！！！";
			}
			//转化成list对象
			List<Map<String,Object>>  list_map = DeEwmUtils.json2list(re);
			if(list_map==null||list_map.size()<1){
				return " json转化后无数据！！！";
			}
			//遍历数据  并且拼接查询sql串
			 StringBuffer sql_head=new StringBuffer("select ");
			 StringBuffer sql_end=new StringBuffer(" from ");
			Map<String,Object> map_obj = list_map.get(0);
			// 数据表名
			 String tableName=(String) map_obj.get("tablename");
			 //表名  (key设置为 tableName)
			 if(tableName==null&&"".equals(tableName.trim())){
				return "表名空！！！"; 
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
				 //如果是id 加入 where条件
				 if("id".equals(key.trim())){
					 id =(String) map_obj.get(key);
					 sql_end.append(key+"='"+map_obj.get(key)+"' and ") ;
					 continue;
				 }
				 //查询条件
				 sql_end.append(key+"='"+map_obj.get(key)+"' and ") ;
			 }
			 String  sql_head_ = sql_head.substring(0, sql_head.lastIndexOf(","));
			 String  sql_end_ =sql_end.substring(0, sql_end.lastIndexOf("and"));
			//根据取得数据查询数据库验证
			 List list= scEwmLogService.GeneralQuerry(sql_head_+sql_end_);
			  if(list==null||list.size()<1){
				 return "伪数据！！！";
			 }
			 //增加解析日志
			  ScEwmLogEntity ewmlog =new ScEwmLogEntity();
			 
			  List<ScEwmLogEntity> list_entity = scEwmLogService.findByProperty(ScEwmLogEntity.class, "ywId", id);
			  if(list_entity==null||list_entity.size()<1){
				  ewmlog.setOperTimes("1");
				  ewmlog.setCardid("");
				  ewmlog.setYwId(id);
				  ewmlog.setYwTable(tableName);
				  ewmlog.setYwType("备注");
				  //添加比较日志
				  scEwmLogService.save(ewmlog);
			  }else{
				  ewmlog =  list_entity.get(0);
				  ewmlog.setYwType("备注:");
				String countstr =  ewmlog.getOperTimes();
				int count_int  =0;
				try{
				   count_int = Integer.parseInt(countstr)+1;
				   ewmlog.setOperTimes(count_int+"");
				}catch(NumberFormatException e){
					e.printStackTrace();
					count_int=0;
				}
				//更新比较日志
				scEwmLogService.saveOrUpdate(ewmlog);
				System.out.println("更新日志ok！！！");
				
			  }
			  
			  
			 System.out.println(sql_head_+sql_end_);
			 System.out.println(list.size());
			 //取得url地址
			 String path_url = request.getContextPath();
			 String basePath_url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path_url+"/";
			 // request.getRequestDispatcher("/dec_wmController.do?decodeindex&tFile").forward(request, respons);
			 respons.sendRedirect(basePath_url+"scCerTemplateController.do?tempPrint&id="+id+"&tableName="+tableName);  //重定向可以URIEncoding="UTF-8"URIEncoding="UTF-8"useBodyEncodingForURI="true"
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}
	
	/**
	 * 根据ftp文件id与附件id取得图片解析
	 * @param request
	 * @param respons
	 * @param cgFormId  主文件id
	 * @param fileId    附件id
	 * @return
	 */
	@RequestMapping(params = "decodeByftpid")
	@ResponseBody
	public AjaxJson dec_ewmByFtpId(HttpServletRequest request,HttpServletResponse respons,String cgFormId,String fileId) {  //ModelAndView
		
		//取得url地址
		 String path_url = request.getContextPath();
		 String basePath_url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path_url+"/";
		AjaxJson j = new AjaxJson();
		j.setMsg("验证通过");
		Map<String, Object> map= new HashMap<String, Object>();
		j.setAttributes(map);
		String re=null;
		respons.setCharacterEncoding("utf-8");
		if(fileId==null||"".equals(fileId.trim())){
			j.setMsg("无法识别！");
			j.setSuccess(false);
			return j;
		}
		
		try {
		//文件路径
	    String path = DeEwmUtils.getPathFromFtp_all(cgFormFieldService, cgFormId, fileId);
		if(path==null||"".equals(path.trim())){
			j.setMsg("无法识别！");
			j.setSuccess(false);
			return j;
		}   

		/******************************封装方法   star************************/	
		 j= DeEwmUtils.decodeMethod(path,scEwmLogService,"2");
		 if(!j.isSuccess()){
			 j= DeEwmUtils.decodeMethod(path,scEwmLogService,"4");
			 
			 if(!j.isSuccess()){
				 
				 j= DeEwmUtils.decodeMethod(path,scEwmLogService,"1");
				 
				 if(!j.isSuccess()){
					 j= DeEwmUtils.decodeMethod(path,scEwmLogService,"3"); 
				 }
			 }
			 
			 
		 }
		
		 Map<String, Object> remap = j.getAttributes();
		 String id=(String) remap.get("data_id");
		 String tableName=(String) remap.get("tableName");
		 
		 
	
		  /******************************封装方法   end************************/
		 //增加解析日志 useBodyEncodingForURI="true" useBodyEncodingForURI="true"
		  ScEwmLogEntity ewmlog =new ScEwmLogEntity();
		 
		  List<ScEwmLogEntity> list_entity = scEwmLogService.findByProperty(ScEwmLogEntity.class, "ywId", id);
		  //List<ScEwmLogEntity> list_entity = scEwmLogService.findByProperty(ScEwmLogEntity.class, "ywId", fileId);
		  if(list_entity==null||list_entity.size()<1){
			  ewmlog.setOperTimes("1");
			  ewmlog.setCardid("");
			  ewmlog.setYwId(id);
			  ewmlog.setYwTable(tableName);
			  ewmlog.setYwType("备注");
			  //添加比较日志
			  scEwmLogService.save(ewmlog);
		  }else{
			  ewmlog =  list_entity.get(0);
			  ewmlog.setYwType("备注:");
			String countstr =  ewmlog.getOperTimes();
			int count_int  =0;
			try{
			   count_int = Integer.parseInt(countstr)+1;
			   ewmlog.setOperTimes(count_int+"");
			}catch(NumberFormatException e){
				e.printStackTrace();
				count_int=0;
			}
			//更新比较日志
			scEwmLogService.saveOrUpdate(ewmlog);
			System.out.println("更新日志ok！！！");
			
		  }
		  
		  
		// System.out.println(sql_head_+sql_end_);
		 //System.out.println(list.size());
		 
		 // request.getRequestDispatcher("/dec_wmController.do?decodeindex&tFile").forward(request, respons);
		 // respons.sendRedirect(basePath_url+"scCerTemplateController.do?tempPrint&id="+id+"&tableName="+tableName);  //重定向可以URIEncoding="UTF-8"URIEncoding="UTF-8"useBodyEncodingForURI="true"
	
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg("无法识别！！！");
			 j.setSuccess(false);
		}
		return j;
	}
}
