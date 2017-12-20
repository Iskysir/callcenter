package com.chrhc.project.sc.docfile.controller;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.FtpUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.cgform.dao.rkxx.ScRkjbxxDaoI;
import org.jeecgframework.web.cgform.entity.upload.CgUploadEntity;
import org.jeecgframework.web.cgform.service.upload.CgUploadServiceI;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.chrhc.project.sc.docfile.entity.ScDocWarEntity;
import com.chrhc.project.sc.docfile.entity.ScFileEntity;
import com.chrhc.project.sc.docfile.page.ScDocWarPage;
import com.chrhc.project.sc.docfile.service.ScDocWarServiceI;
import com.chrhc.project.sc.rkyw.entity.ScRkYwConfigEntity;


/**   
 * @Title: Controller
 * @Description: 电子文档库
 * @author onlineGenerator
 * @date 2015-05-23 15:02:40
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scDocWarController")
public class ScDocWarController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScDocWarController.class);

	@Autowired
	private ScDocWarServiceI scDocWarService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private CgUploadServiceI cgUploadService;
	@Autowired
	private ScRkjbxxDaoI scRkjbxxDao;
	/**
	 * 获取文档库实例
	 * @param docWarId
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getDetailDocWar")
	@ResponseBody
	public AjaxJson getDetailDocWar(String docWarId,HttpServletRequest req){
		AjaxJson  j = new AjaxJson();
		if(StringUtil.isNotEmpty(docWarId)){
			ScDocWarEntity scDocWar = scDocWarService.getEntity(ScDocWarEntity.class, docWarId);
			//获取参数
			Object id0 = scDocWar.getId();
			//===================================================================================
			//1.查询出数据库的明细数据-附件表
		    String hql0 = "from ScFileEntity where 1 = 1 AND dOC_FOREIGN_ID = ? ";
		    List<ScFileEntity> scFileOldList = scDocWarService.findHql(hql0,id0);
		    if(scFileOldList != null && scFileOldList.size() > 0){
		    	ScFileEntity scFile  = scFileOldList.get(0);
		    	scDocWar.setDocFile(scFile.getPhoto());
		    }
			j.setObj(scDocWar);
			//String json = JSONHelper.bean2json(scDocWar);
		}
		return j;
	}
	@RequestMapping(params = "saverkxxgrzp")
	@ResponseBody
	public AjaxJson saverkxxgrzp(String docfile,HttpServletRequest req){
		AjaxJson  j = new AjaxJson();
		String cgformId = req.getParameter("cgformId");
		String cgformName = req.getParameter("cgformName");
		String cgformField = req.getParameter("cgformField");
		if(StringUtil.isNotEmpty(docfile)){
			CgUploadEntity cgformObjnew  = new CgUploadEntity(); 
			CgUploadEntity cgformObj = scDocWarService.getEntity(CgUploadEntity.class, docfile);
			if(cgformObj!=null){
				try {
					MyBeanUtils.copyBeanNotNull2Bean(cgformObj,cgformObjnew);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//cgformObjnew.setId("");
				cgformObjnew.setCgformField(cgformField);
				cgformObjnew.setCgformName(cgformName);
				cgformObjnew.setCgformId(cgformId);
				
				scDocWarService.saveOrUpdate(cgformObjnew);
				cgUploadService.writeBack(cgformId, cgformName, cgformField, "",
						cgformObjnew.getRealpath());
			}
		}
		return j;
	}
	/**
	 * 电子文档库列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scDocWar")
	public ModelAndView scDocWar(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/docfile/scDocWarList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(ScDocWarEntity scDocWar,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScDocWarEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scDocWar,request.getParameterMap());
		try{
			//自定义追加查询条件
			String query_createDate_begin = request.getParameter("createDate_begin");
			String query_createDate_end = request.getParameter("createDate_end");
			if(StringUtil.isNotEmpty(query_createDate_begin)){
				cq.ge("createDate", new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(query_createDate_begin));
			}
			if(StringUtil.isNotEmpty(query_createDate_end)){
				cq.le("createDate", new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(query_createDate_end));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scDocWarService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除电子文档库
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ScDocWarEntity scDocWar, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		scDocWar = systemService.getEntity(ScDocWarEntity.class, scDocWar.getId());
		String message = "删除成功";
		try{
			scDocWarService.deleteLogic(scDocWar);
			//scDocWarService.del_pic(scDocWar.getDocFile(),scDocWar.getId(),"Z");  // 删除ftp图片信息
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除电子文档库
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "删除成功";
		try{
			for(String id:ids.split(",")){
				ScDocWarEntity scDocWar = systemService.getEntity(ScDocWarEntity.class,
				id
				);
				scDocWarService.deleteLogic(scDocWar);
				//scDocWarService.del_pic(scDocWar.getDocFile(),scDocWar.getId(),"Z");
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加电子文档库
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScDocWarEntity scDocWar,@ModelAttribute ScDocWarPage scDocWarPage, HttpServletRequest request) {
		List<ScFileEntity> scFileList =  scDocWarPage.getScFileList();
		AjaxJson j = new AjaxJson();

		String message = "添加成功";
//		CgUploadEntity cgform = new CgUploadEntity();
		
		
		try{
			String form_id = "";
/* 快捷业务，电子文档库业务运行库中有重复的身份证等文档的录入，故该段暂时隐去
 * 			String idcardnum = scDocWar.getIdcardnum();
			if(null != idcardnum && !"".equals(idcardnum)){
				//快捷业务使用，当身份证信息存在与人口库且不一致时，更新电子文档库及人口库，而不用新增 add by fangxu-wang 20150813
				List<ScDocWarEntity> docList = scDocWarService.findByProperty(ScDocWarEntity.class,"idcardnum",idcardnum);
				if(null != docList && docList.size() > 0){
					scDocWar.setId(docList.get(0).getId());
					//return doUpdate(scDocWar, scDocWarPage, request);
					scDocWarService.updateMain(scDocWar, scFileList);
					form_id = scDocWar.getId();
				}else{				
					//保存主信息
					form_id =(String) scDocWarService.save(scDocWar);
				}
			}else{
				message = "添加失败";
				j.setMsg(message);
				j.setObj(scDocWar);
				return j;
			}*/

			
			//保存主信息
			form_id =(String) scDocWarService.save(scDocWar);
			//String sql ="insert INTO cgform_uploadfiles (id,CGFORM_ID,CGFORM_NAME,CGFORM_FIELD)VALUES ('"+scDocWar.getDocFile()+"','"+form_id+"','doc_file_z','sc_doc_war') ";//CGFORM_ID, CGFORM_NAME,CGFORM_FIELD
			//String sql ="insert INTO cgform_uploadfiles (id,CGFORM_ID,CGFORM_NAME,CGFORM_FIELD)VALUES (?,?,'sc_doc_war','doc_file') ";//CGFORM_ID, CGFORM_NAME,CGFORM_FIELD
			
			
			//存储cgformfiles关联表
			//CgUploadEntity CgUploadEntity = commonDao.getEntity(CgUploadEntity.class, fileid);
			String file_id=scDocWar.getDocFile();
			if(file_id!=null&&!"".equals(file_id)){
				//int i_=scDocWarService.executeSql(sql, new Object[]{file_id,form_id});
				CgUploadEntity cgformObj = scDocWarService.getEntity(CgUploadEntity.class, file_id);
				if(cgformObj!=null){
					cgformObj.setCgformField("doc_file");
					cgformObj.setCgformName("sc_doc_war");
					cgformObj.setCgformId(form_id);
					scDocWarService.saveOrUpdate(cgformObj);
					System.out.println("主表cgform更新");
				}
					
			}
			/**保存-附件表*/
			for(ScFileEntity scFile:scFileList){
				//if((scFile.getName()==null||"".equals(scFile.getName()))&&(scFile.getPhoto()==null||"".equals(scFile.getPhoto()))){
				if(scFile.getPhoto()==null||"".equals(scFile.getPhoto())){
					System.out.println("空数据，跳过！！");
					continue;
				}
				//外键设置
				scFile.setDocForeignId(scDocWar.getId());
				String  scfile_id = (String) scDocWarService.save(scFile);
				String sql_ ="insert INTO cgform_uploadfiles (id,CGFORM_ID,CGFORM_NAME,CGFORM_FIELD)VALUES (?,?,'sc_file','Photo') ";
				String photoid=scFile.getPhoto();
				if(photoid!=null&&!"".equals(photoid)){
					//int i_f=scDocWarService.executeSql(sql_, new Object[]{photoid,scfile_id});
					CgUploadEntity cgformObj = scDocWarService.getEntity(CgUploadEntity.class, photoid);
					if(cgformObj!=null){
						cgformObj.setCgformField("Photo");
						cgformObj.setCgformName("sc_file");
						cgformObj.setCgformId(scfile_id);
						scDocWarService.saveOrUpdate(cgformObj);
						System.out.println("付表cgform插入");
					}
					
				}
				
			}
		
			
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setObj(scDocWar);
		return j;
	}
	
	
	/**
	 * 增加电子文档的图片（文件）异步提交
	 * @param 
	 * @return  AjaxJson
	 * @throws IOException 
	 */
	@RequestMapping(params = "addfiles")
	@ResponseBody
	public Map<String, Object> addnew(ScDocWarEntity scDocWar,ScDocWarPage scDocWarPage, HttpServletRequest request,HttpServletResponse respons, String flag) throws IOException {
		List<ScFileEntity> scFileList =  scDocWarPage.getScFileList();
		//respons.setContentType("text/html;charset=utf-8");
		respons.setCharacterEncoding("utf-8");
		//返回结果
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("success", true);
		
		j.setAttributes(attributes);
		FtpUtils ftputil = new FtpUtils();
		//1 代表上传成功  0代表失败
		String message = "1";
		MultipartRequest mureq =null;
		String directory ="upload/files/";
		
		try{
		
		if(!(request instanceof MultipartRequest)){
			System.out.println("RequestFacade 类型不对跳过！！！！");
			return null;
		}
		
		//		RequestFacade
		mureq = (MultipartRequest)request;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		TSUser user= ResourceUtil.getSessionUserName();
		Map<String, MultipartFile> fileMap = mureq.getFileMap();
		InputStream in=null;
		
		try{
	    //遍历文件
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()){
			 MultipartFile mf = entity.getValue();
			String  filerealName = mf.getOriginalFilename();// 获取文件名
			String datestr = DateUtils. getDate("yyyyMMddHHmmss") ;
			String extend = FileUtils.getExtend(filerealName);// 获取文件扩展名
			String ftppath="";
			//路径
			if(filerealName!=null&&filerealName.startsWith("F")){
				ftppath = directory+"sc_doc_war/";
			}else if(filerealName!=null&&filerealName.startsWith("Z")){
				ftppath = directory+"sc_doc_war/";
			}
			else
			{
				ftppath = directory+"sc_doc_war/";
			}
			FTPClient ftpClient=ftputil.getFtpClient(ftppath);
			//ftp 的文件名
			String ftpfilename=datestr+StringUtil.random(8)+"."+extend;
			in=mf.getInputStream();
			//上传到ftp服务器
			try {
				ftputil.upload(in, ftppath, ftpfilename, filerealName, ftpClient);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("单个文件上传出错！！！");
				continue;
			}
			if(in!=null){
				in.close();
			}
			//关闭ftp
			ftputil.close(ftpClient); 
			/**取得主要信息附件文件 上传的到t_s_attachment 保存（返回主键id）*/
			//TSAttachment attachment = new TSAttachment();
			CgUploadEntity attachment= new CgUploadEntity();
			attachment.setRealpath(ftppath+ftpfilename);
			
			//时间处理
			Calendar ca=  Calendar.getInstance();
			Timestamp time = new Timestamp(ca.getTimeInMillis());
			attachment.setCreatedate(time);
			attachment .setExtend(extend);
			attachment.setAttachmenttitle(filerealName);
			attachment.setTSUser(user);
			//cgform neirong 
			
			attachment.setCgformId("0");
			attachment.setCgformField("0");
			attachment.setCgformName("0");
			//保存数据t_s_attachment
			String cgformid = (String) scDocWarService.save_general(attachment);
			
			if(filerealName.startsWith("F")){
				String index= filerealName.substring(1,filerealName.indexOf("_"));
				attributes.put("F"+index, cgformid);
			}else if(filerealName.startsWith("Z")){
				attributes.put("Z", cgformid);
			}
			else
			{
				attributes.put(filerealName, cgformid);
			}
		    System.out.println(filerealName+"：ok");

		 }
		 
		}catch(Exception e){
			j.setSuccess(false);
			attributes.put("success", false);
			System.out.println("-- upload 中途 erro--");
		}
		 
		//保存该业务数据（sc_doc_war（主表）   sc_file(附表)）取得sc_doc_war的主键id（）
		
		
		//出入t_s_attachment的id 与sc_doc_war 的id到cgform_uploadfiles
		
		//j.setMsg(message);
//		return j.getJsonStr();
		return attributes;
	}
	/*public AjaxJson addnew(ScDocWarEntity scDocWar,ScDocWarPage scDocWarPage, HttpServletRequest request,HttpServletResponse respons, String flag) throws IOException {
		List<ScFileEntity> scFileList =  scDocWarPage.getScFileList();
		respons.setCharacterEncoding("utf-8");
		//respons.setContentType("text/html;charset=utf-8");
		//AjaxJson j = new AjaxJson();
		//String directory = "upload/files/doc/"; 
		FtpUtils ftputil = new FtpUtils();
		//String message = "添加成功";
		MultipartRequest mureq =null;
		
		try{
		
		if(!(request instanceof MultipartRequest)){
			System.out.println("RequestFacade 类型不对跳过！！！！");
			return null;
		}
		
//		RequestFacade
		mureq = (MultipartRequest)request;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		Map<String, MultipartFile> fileMap = mureq.getFileMap();
		InputStream in=null;
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()){
			 MultipartFile mf = entity.getValue();
			String  fileName = mf.getOriginalFilename();// 获取文件名
			String datestr = DateUtils. getDate("yyyyMMddHHmmss") ;
			String ftpfilename=datestr+fileName;
			 in =mf.getInputStream();
			 int i=in.read();
			 System.out.println(i+"@@@@@@@@@@@@@@@@@");
			 //ftputil.upload(in , directory, ftpfilename, fileName, ftpClient);
			//attr.put("fileName", fileName);
			 
		 }
		System.out.println("进入添加后台");
		if(true){
			 AjaxJson j =new AjaxJson();
			 j.setMsg("返回成功");
			 Map<String, Object> attributes = new HashMap<String, Object>();
			 attributes.put("value1", "1234567891");
			 attributes.put("value2", "1234567892");
			 j.setAttributes(attributes);
			 System.out.println(JSONHelper.toJSONString(j));
			return j;
		}
		
		
	    //首先生成一条记录
	    //scDocWar = (ScDocWarEntity) scDocWarService.save(scDocWar);
	    String cgformid = (String) scDocWarService.save(scDocWar);//主表id用于返回前台& 文件保存时也用 
	    CgUploadController cgupload = new CgUploadController();
	    CgUploadEntity cgUploadEntity =new CgUploadEntity();//初始化一个cgformuploadfile对象
	    主表附件设置
	    if("Z".equals(flag)){
	    	cgUploadEntity.setCgformId(cgformid);
	    	cgUploadEntity.setCgformName("sc_doc_war");
	    	cgUploadEntity.setCgformField("docFile");//附件字段
	    	 从表附件设置	
	    }else if("F".equals(flag)){
	    	cgUploadEntity.setCgformId(cgformid);
	    	cgUploadEntity.setCgformName("sc_file");
	    	//TODO   字段名称待定
//	    	cgUploadEntity.setCgformField("docFile");//附件字段
	    }
	    //在返回值中存储了存在cgform的id（根据此id可以取得附件的ftp目录）
	    AjaxJson ajaxjson = cgupload.saveFiles(request, null, cgUploadEntity);
		
		*//** 文件上传（自己处理的 不用）*//*
		FTPClient ftpClient=null;
		InputStream in=null;
		try {
		MultipartRequest mureq = (MultipartRequest)request;
		 //List<MultipartFile> list_files =mureq.getFiles("Filedata");
		 ftpClient=ftputil.getFtpClient(directory);
		 Map<String, Object> attr=new HashMap<String,Object>();
				 
		 Map<String, MultipartFile> fileMap = mureq.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()){
			 MultipartFile mf = entity.getValue();
			String  fileName = mf.getOriginalFilename();// 获取文件名
			String datestr = DateUtils. getDate("yyyyMMddHHmmss") ;
			String ftpfilename=datestr+fileName;
			 in =mf.getInputStream();
			ftputil.upload(in , directory, ftpfilename, fileName, ftpClient);
			attr.put("fileName", fileName);
			 
		 }
		
		}catch (Exception e){
			e.printStackTrace();
			message="--erro--";
		}finally{
			ftputil.close(ftpClient);
			if(in!=null){
		         try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		//取得主要信息附件文件 上传的到t_s_attachment 保存（返回主键id）
		
		
		//保存该业务数据（sc_doc_war（主表）   sc_file(附表)）取得sc_doc_war的主键id（）
		
		
		//出入t_s_attachment的id 与sc_doc_war 的id到cgform_uploadfiles
		
		
		
		
		
		
		
		
		
		//j.setMsg(message);
		return ajaxjson;
	}*/
	
	
	
	/**
	 * 更新电子文档库
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScDocWarEntity scDocWar,ScDocWarPage scDocWarPage, HttpServletRequest request) {
		List<ScFileEntity> scFileList =  scDocWarPage.getScFileList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			ScDocWarEntity scDocWar_old = systemService.getEntity(ScDocWarEntity.class, scDocWar.getId());
			//删除旧的图片信息
			if(scDocWar_old!=null&&!( scDocWar_old.getDocFile().equals(scDocWar.getDocFile()) )){
				scDocWarService.del_pic(scDocWar_old.getDocFile(),scDocWar_old.getId(),"Z");	
			}
			scDocWarService.updateMain(scDocWar, scFileList);
			
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 电子文档库新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScDocWarEntity scDocWar, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scDocWar.getId())) {
			scDocWar = scDocWarService.getEntity(ScDocWarEntity.class, scDocWar.getId());
			req.setAttribute("scDocWarPage", scDocWar);
		
		}else{
		req.setAttribute("tablecode", req.getParameter("tablecode"));
		req.setAttribute("doctype", req.getParameter("doctype"));
		}
		return new ModelAndView("com/chrhc/project/sc/docfile/scDocWar-add");
	}
	
	/**
	 * 高拍仪dialog
	 * 
	 * @return
	 */
	@RequestMapping(params = "gaydialog")
	public ModelAndView gayDialog( HttpServletRequest req) {
		
		return new ModelAndView("com/chrhc/project/sc/docfile/scDocWar-add_dialog");
	}
	
	
	/**
	 * 电子文档库编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScDocWarEntity scDocWar, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scDocWar.getId())) {
			scDocWar = scDocWarService.getEntity(ScDocWarEntity.class, scDocWar.getId());
			req.setAttribute("scDocWarPage", scDocWar);
			if(scDocWar==null){
				req.setAttribute("tablecode", req.getParameter("tablecode"));
				req.setAttribute("doctype", req.getParameter("doctype"));
				return new ModelAndView("com/chrhc/project/sc/docfile/scDocWar-add");
			}
			
			//获取参数  *************zwt star********
			Object id0 = scDocWar.getId();
			//查询-附件表
		    String hql0 = "from ScFileEntity where 1 = 1 AND dOC_FOREIGN_ID = ? ";
			List<ScFileEntity> scFileEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("scFileList", scFileEntityList);
			//flag： up 更新  否则  查看 
			req.setAttribute("flag", req.getParameter("flag"));
			
			// *************zwt end********
		}
		return new ModelAndView("com/chrhc/project/sc/docfile/scDocWar-update");
	}
	
	
	/**
	 * 加载明细列表[附件表]
	 * 
	 * @return
	 */
	@RequestMapping(params = "scFileList")
	public ModelAndView scFileList(ScDocWarEntity scDocWar, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = scDocWar.getId();
		//===================================================================================
		//查询-附件表
	    String hql0 = "from ScFileEntity where 1 = 1 AND dOC_FOREIGN_ID = ? ";
	    try{
	    	List<ScFileEntity> scFileEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("scFileList", scFileEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/chrhc/project/sc/docfile/scFileList");
	}
	/**
	 * 删除 图片
	 * @param fileid（附件id）
	 * @param dataid（业务id）
	 * @return
	 */
	@RequestMapping(params="delphoto")
	@ResponseBody
	public AjaxJson delphoto(String fileid,String dataid){
		
		AjaxJson j= new AjaxJson();
		String mes="删除成功";
		//fileid="402881cd4d9ab84c014d9abb07e40001";
		//dataid ="402881cd4d851895014d852b6f0c0009";
		try{
			//执行附件删除
			scDocWarService.del_pic(fileid, dataid, null);
		}catch(Exception e){
			e.printStackTrace();
			mes="删除失败！";
			j.setSuccess(false);
		}
		j.setMsg(mes);
		return j;
	}
	/**
	 * 根据字典code 与分类code取得字典text
	 * @param tableClass 字典类的全名  例如：org.jeecgframework.web.system.pojo.base.TSTypegroup
	 * @param datacode  字典code
	 * @param groupcode 字典分类code 
	 * @return
	 */
	@RequestMapping(params="getajaxcode")
	@ResponseBody
	public String zidian(String tableClass,String data,String groupcode){
		
		String re ="";
		String mes="删除成功";
		Class myclass =null;
		String groupCodeID="";
		
		try{
			//String data1=new String(data.getBytes("utf-8"),"GB2312");
			String data1=URLDecoder.decode(data,"UTF-8");
			//大类code
			if(groupcode!=null&&!groupcode.equals("")){
				//scDocWarService.findByProperty(myclass, "propertyName", groupcode);
				TSTypegroup typegroup = scDocWarService.findUniqueByProperty(TSTypegroup.class, "typegroupcode", groupcode);
				//List<TSType> types = this.commonDao.findByProperty(TSType.class, "TSTypegroup.id", tsTypegroup.getId());
				//TSTypegroup tsTypegroup = commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode", typegroupCode);
				groupCodeID = typegroup.getId();
			}
			
			
			if(tableClass!=null&&!tableClass.equals("")){
				
			 myclass = Class.forName(tableClass);
			}else{
				 myclass = TSType.class;
			}
			//执行查询字典值
			List<TSType> types = scDocWarService.findByProperty(TSType.class, "TSTypegroup.id",groupCodeID);
			for(TSType ty:types){
				if(ty.getTypename().equals(data)){
					return  ty.getTypecode();
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
			
			
		}
	
		return "";
	}
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ScDocWarEntity scZzyz,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ScDocWarEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, scZzyz, request.getParameterMap());
		List<ScDocWarEntity> scZzyzs = this.scDocWarService.getListByCriteriaQuery(cq,false);
		 List<TSType> listtype=null;
		 //List<ScDocWarEntity> scZzyzsData= new ArrayList<ScDocWarEntity>();
		// HibernateUtils.getSessionFactory().getCurrentSession(); 
		 org.hibernate.Session session =  scDocWarService. getSession();
		 //字典转换
		 for(ScDocWarEntity scdaocwar :scZzyzs){
			 session.evict(scdaocwar);
			 listtype=null;
			 String groupid="mz";
			String nation_ = getDictionary(groupid,scdaocwar.getNation());
			if(nation_!=null){
				scdaocwar.setNation(nation_);
			}
			 groupid="sex";
			 String sex_ = getDictionary(groupid,scdaocwar.getSex());
			 if(sex_!=null){
					scdaocwar.setSex(sex_);
				}
			 groupid="doctype";
			 String doctype_ = getDictionary(groupid,scdaocwar.getDocType());
			 if(doctype_!=null){
					scdaocwar.setDocType(doctype_);
				}
			 
			 
		 }
		
		String file_Name="电子文档_sc_doc_war";//+scZzyz.getVersionNum()
		//response.setContentType(arg0);
		try {
			file_Name= URLEncoder.encode(file_Name, "UTF-8");
			file_Name = new String(file_Name.getBytes("gbk"),"ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*try {
			//file_Name = new String("证照验真_sc_zzyz".getBytes(),"UTF-8");
			//file_Name = URLEncoder.encode(file_Name);
			String userAgent = request.getHeader("User-Agent");
		    byte[] bytes = userAgent.contains("MSIE") ? file_Name.getBytes()
		            : file_Name.getBytes("UTF-8"); // fileName.getBytes("UTF-8")处理safari的乱码问题
		    file_Name = new String(bytes, "ISO-8859-1"); // 各浏览器基本都支持ISO编码
			
		} catch (Exception e) {
			file_Name ="sc_zzyz";
			e.printStackTrace();
		}*/
		modelMap.put(NormalExcelConstants.FILE_NAME,file_Name);
		modelMap.put(NormalExcelConstants.CLASS,ScDocWarEntity.class);
		//modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("电子文档列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
		//	"导出信息"));
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams(null, null,null));
		modelMap.put(NormalExcelConstants.DATA_LIST,scZzyzs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/** 字典查询方法   参数1：groupid 字典所属大类code ；参数2：code 字典code*/
	@RequestMapping(params = "getDictionary")
	@ResponseBody
	String getDictionary(String groupcode,String code){
		String re =null;
		try{
			 List< TSTypegroup > listGroup =scDocWarService.findByProperty(TSTypegroup.class, "typegroupcode", groupcode);
			 if(listGroup!=null&&listGroup.size()>0){
				 String hql="from TSType where typecode='"+code+"' and TSTypegroup='"+listGroup.get(0).getId()+"'";
				 List<TSType> listtype =  scDocWarService.findByQueryString(hql);
				 if(listtype!=null&&listtype.size()>0){
					re = listtype.get(0).getTypename();	 
					return re;
				 }
			 }	
		}catch(Exception e){
			e.printStackTrace();
			re=null;
		}finally{
			
		}
		return re ;	
		 
	}
	
	/**
	 * 查询字典表（）
	 * @param tablename  表名
	 * @param codevalue  code值
	 * @param label      标签字段名
	 * @param code       code字段名
	 * @return
	 */
	 
	@RequestMapping(params = "getselfDictionary")
	@ResponseBody
	String getselfDictionary(String tablename,String codevalue,String label,String code){
		String re =null;
		try{
			String  sql="select  "+label +"  from "+tablename+" where "+code+"= ?";//+codevalue+"' ";
			String  sql_="select  "+label +" from "+tablename+" where "+code+"= '"+codevalue+"' ";
			List list_Obj = scDocWarService.findListbySql(sql_);//返回值是list《object》
			//List list_map = scDocWarService.findForJdbc( sql, new String[]{codevalue});//返回值是list<map>
			System.out.println(list_Obj);
			//System.out.println(list_map);
			if(list_Obj!=null&&list_Obj.size()>0){
					re = (String) list_Obj.get(0);
//				if(res!=null&&res.length>0){
//					re=res[0];
//				}
			}
		}catch(Exception e){
			e.printStackTrace();
			re=null;
		}finally{
			
		}
		return re ;	
		 
	}
	
	/**
	 * 根据表code取得 取得该业务类型 需要多少图片证明材料
	 * @param tablecode 表code
	 * @return 图片类型
	 */
	 
	@RequestMapping(params = "getphototype")
	@ResponseBody
	public String getphototype(String tablecode){
		String tablcode_="";
		try {
			tablcode_=URLDecoder.decode(tablecode,"UTF-8");
			tablecode =tablcode_;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String re="";
		/**根据tablecode 取得人口回写配置表  如果存在数据则进行下一步操作*/
        List<ScRkYwConfigEntity>  configlist = systemService.findByProperty(ScRkYwConfigEntity.class, "businesscode", tablecode);
        if(configlist==null||configlist.size()==0){
        	return "";
        }
        ScRkYwConfigEntity cfg = configlist.get(0);
        String types = cfg.getDocphototype();
        if(types==null){return "";}
        //docPhotoType
        String[] arr_type =types.split(",");
        for(String item:arr_type){
        	//取得字典label值
        	re +=getDictionary("docPhotoType",item)+",";
        	
        }
		if(re.endsWith(",")){
			re=re.substring(0, re.length()-1);
		}
		return re;
		
	}
	/**
	 * 根据id取的 人口基本信息
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "getrkxx")
	@ResponseBody
	public AjaxJson getRKxx(String id){
		AjaxJson rejson  =  new AjaxJson();
		try{
		List<Map> listmap = scRkjbxxDao.getrkxx(id);
		if(listmap==null||listmap.size()<1){
			return rejson;
		}
		Map map = listmap.get(0);
		//String photopath = (String) map.get("grzp");
		rejson.setAttributes(map);
		}catch(Exception e){
			e.printStackTrace();
			rejson.setSuccess(false);
		}
		return rejson;
	}
}
