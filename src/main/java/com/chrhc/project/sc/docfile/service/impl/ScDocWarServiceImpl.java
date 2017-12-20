package com.chrhc.project.sc.docfile.service.impl;
import com.chrhc.project.sc.docfile.service.ScDocWarServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.chrhc.project.sc.docfile.entity.ScDocWarEntity;
import com.chrhc.project.sc.docfile.entity.ScFileEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import org.jeecgframework.core.common.dao.ICommonDao;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.FtpUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.cgform.entity.upload.CgUploadEntity;
import org.jeecgframework.web.system.pojo.base.TSAttachment;

import java.util.ArrayList;
import java.util.UUID;
import java.io.Serializable;

import javax.annotation.Resource;


@Service("scDocWarService")
@Transactional
public class ScDocWarServiceImpl extends CommonServiceImpl implements ScDocWarServiceI {
	
	
 	public <T> void delete(T entity) {
 		
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((ScDocWarEntity)entity);
 	}
	
	public void addMain(ScDocWarEntity scDocWar,
	        List<ScFileEntity> scFileList){
			//保存主信息
		this.save(scDocWar);
		
			/**保存-附件表*/
			for(ScFileEntity scFile:scFileList){
				//外键设置
				scFile.setDocForeignId(scDocWar.getId());
				this.save(scFile);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(scDocWar);
	}
	/**
	 * 通用的保存方法
	 */
	public <T> Serializable save_general(T entity){
		
		return commonDao.save(entity);
	}
	

	
	public void updateMain(ScDocWarEntity scDocWar,
	        List<ScFileEntity> scFileList) {
		//保存主表信息
		this.saveOrUpdate(scDocWar);
		
		String file_id=scDocWar.getDocFile();
		if(file_id!=null&&!"".equals(file_id)){
			//int i_=scDocWarService.executeSql(sql, new Object[]{file_id,form_id});
			CgUploadEntity cgformObj = commonDao.getEntity(CgUploadEntity.class, file_id);
			String cgformid = cgformObj.getCgformId();
			if(cgformObj!=null&&cgformid!=null&&cgformid.length()<3){
				cgformObj.setCgformField("doc_file");
				cgformObj.setCgformName("sc_doc_war");
				cgformObj.setCgformId(scDocWar.getId());
				commonDao.saveOrUpdate(cgformObj);
				System.out.println("主表cgform更新");
			}
				
		}
		
		//===================================================================================
		//获取参数
		Object id0 = scDocWar.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-附件表
	    String hql0 = "from ScFileEntity where 1 = 1 AND dOC_FOREIGN_ID = ? ";
	    List<ScFileEntity> scFileOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-附件表
		for(ScFileEntity oldE:scFileOldList){
			boolean isUpdate = false;
				for(ScFileEntity sendE:scFileList){
					//需要更新的明细数据-附件表
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							this.saveOrUpdate(oldE);
							
						} catch (Exception e) {
							e.printStackTrace();
							throw new BusinessException(e.getMessage());
						}
						isUpdate= true;
		    			break;
		    		}
		    	}
	    		if(!isUpdate){
		    		//如果数据库存在的明细，前台没有传递过来则是删除-附件表
		    		super.delete(oldE);
		    		try {
						del_pic(oldE.getPhoto(), oldE.getId(), null);
					} catch (Exception e) {
						e.printStackTrace();
					}
	    		}
	    		
			}
			//3.持久化新增的数据-附件表
			for(ScFileEntity scFile:scFileList){
				if(oConvertUtils.isEmpty(scFile.getId())){
					if(oConvertUtils.isEmpty(scFile.getPhoto()))continue;//||scFile.getPhoto().contains("docbackground.png")
					//外键设置
					scFile.setDocForeignId(scDocWar.getId());
					String scfile_id = (String) this.save(scFile);
					String photoid=scFile.getPhoto();
					if(photoid!=null&&!"".equals(photoid)){//&&!photoid.contains("docbackground.png")
						//int i_f=scDocWarService.executeSql(sql_, new Object[]{photoid,scfile_id});
						CgUploadEntity cgformObj = commonDao.getEntity(CgUploadEntity.class, photoid);
						if(cgformObj!=null){
							cgformObj.setCgformField("Photo");
							cgformObj.setCgformName("sc_file");
							cgformObj.setCgformId(scfile_id);
							commonDao.saveOrUpdate(cgformObj);
							System.out.println("付表cgform插入");
						}
						
					}
					
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(scDocWar);
	}

	
	public void delMain(ScDocWarEntity scDocWar) throws Exception {
		//删除主表信息
		this.delete(scDocWar);
		//===================================================================================
		//获取参数
		Object id0 = scDocWar.getId();
		String fileid=scDocWar.getDocFile();
		//删除附件关联的数据（cgform  、attachment ftp 附件）star
		if(fileid!=null&&!"".equals(fileid.trim())){
			del_pic(fileid,id0.toString(),null);
		}
		//删除附件关联的数据（cgform  、attachment ftp 附件）end
		//===================================================================================
		//删除-附件表
	    String hql0 = "from ScFileEntity where 1 = 1 AND dOC_FOREIGN_ID = ? ";
	    List<ScFileEntity> scFileOldList = this.findHql(hql0,id0);
	   //删除附件关联的数据（cgform  、attachment ftp 附件）star
		for(ScFileEntity scfile :scFileOldList){
			String sc_photo=  scfile.getPhoto(); 
			String sc_fileid=  scfile.getPhoto(); 
			if(sc_photo!=null&&!"".equals(sc_photo.trim())){
	  			del_pic(sc_photo,sc_fileid,null);
	  		}
		}
	   //删除附件关联的数据（cgform  、attachment ftp 附件）end
	    
	  
		this.deleteAllEntitie(scFileOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(ScDocWarEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(ScDocWarEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(ScDocWarEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,ScDocWarEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{doc_type}",String.valueOf(t.getDocType()));
 		sql  = sql.replace("#{doc_name}",String.valueOf(t.getDocName()));
 		sql  = sql.replace("#{doc_url}",String.valueOf(t.getDocUrl()));
 		sql  = sql.replace("#{ctreate_time}",String.valueOf(t.getCtreateTime()));
 		sql  = sql.replace("#{doc_file}",String.valueOf(t.getDocFile()));
 		sql  = sql.replace("#{delflag}",String.valueOf(t.getDelflag()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{sex}",String.valueOf(t.getSex()));
 		sql  = sql.replace("#{birthday}",String.valueOf(t.getBirthday()));
 		sql  = sql.replace("#{idcardnum}",String.valueOf(t.getIdcardnum()));
 		sql  = sql.replace("#{nation}",String.valueOf(t.getNation()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	/**
 	 * 删除附件
 	 * @param fileid cgformupload的id
 	 * @param dataid cgform的业务数据  id 
 	 * @param flag   null 删除 附件数据，其他则不操作
 	 * @throws Exception 
 	 */
 	public  void del_pic(String fileid,String dataid,String flag) throws Exception{
	  //取得附件表与中间关联表 实体
	  TSAttachment attachment = commonDao.getEntity(TSAttachment.class, fileid);
	  if(attachment==null||attachment.getRealpath()==null){
		  
		  
		return; 
	  }
	  String ftppath=  attachment.getRealpath();
	  String ftppath_ = ftppath.substring(0, ftppath.lastIndexOf("/")+1);
	  String filename= ftppath.substring(ftppath.lastIndexOf("/")+1);
	  CgUploadEntity CgUploadEntity = commonDao.getEntity(CgUploadEntity.class, fileid);
	  if(CgUploadEntity==null||CgUploadEntity!=null){//||CgUploadEntity.getCgformId().equals(dataid)
		  //删除
		  if(CgUploadEntity!=null){
			  
			  commonDao.delete(CgUploadEntity);//删除CgUploadEntity 就不用删除attachment了
		  }else{
			  
			  //删除attachment
			  commonDao.delete(attachment);
		  }
		  FtpUtils ftputil = new FtpUtils();
		  try{
		  ftputil.delete(ftppath_, filename);
		  }catch(Exception e ){
			e.printStackTrace();  
		  }
		  //如果null 删除附表数据
		  if(null==flag){
			  //删除附表
			 ScFileEntity scfile = commonDao.getEntity(ScFileEntity.class, dataid);
			 if(scfile!=null){
				 if(StringUtil.isNotEmpty(scfile.getFile())){
					 scfile.setPhoto("");
					 scfile.setName("");
					 scfile.setIdcardNum("");
					 commonDao.updateEntitie(scfile); 
				 }else{
				 commonDao.delete(scfile); 
				 } 
			 }
		}
	  }
    }
 
 	/**
 	 * 删除主 业务数据  暂时不用（与上边重复）
 	 * @param fileid  附件id
 	 * @param dataid  业务数据id
 	 * @throws Exception
 	 */
 	public  void del_pic_Z(String fileid,String dataid) throws Exception{
 		  // String hql="select CgUploadEntity where id='"+fileid+"' and ";
 		  //取得附件表与中间关联表 实体
 		  TSAttachment attachment = commonDao.getEntity(TSAttachment.class, fileid);
 		  if(attachment==null||attachment.getRealpath()==null){
 			 return; 
 		  }
 		  String ftppath=  attachment.getRealpath();
 		  String ftppath_ = ftppath.substring(0, ftppath.lastIndexOf("/")+1);
 		  String filename= ftppath.substring(ftppath.lastIndexOf("/")+1);
 		  CgUploadEntity CgUploadEntity = commonDao.getEntity(CgUploadEntity.class, fileid);
 		  if(CgUploadEntity==null||CgUploadEntity.getCgformId().equals(dataid)){
 			  //删除
 			  if(CgUploadEntity!=null){
 				  
 				  commonDao.delete(CgUploadEntity);
 			  }
 			  commonDao.delete(attachment);
 			  FtpUtils ftputil = new FtpUtils();
 			  ftputil.delete(ftppath_, filename);
 			  //更新数据表     附件字段(设置空)
 				/*if("Z".equals(flag)){
 					ScDocWarEntity scdoc = commonDao.getEntity(ScDocWarEntity.class, fileid);
 					scdoc.setDocFile("");
 					commonDao.updateEntitie(scdoc);
 				}else if("F".equals(flag)){
 					ScFileEntity scfile = commonDao.getEntity(ScFileEntity.class, fileid);
 					//scfile.setPhoto("");
 					//commonDao.updateEntitie(scfile);
 					commonDao.delete(scfile);
 				}*/
 			  //删除附表
 			 ScFileEntity scfile = commonDao.getEntity(ScFileEntity.class, dataid);
 			 commonDao.delete(scfile);
 		  }
 		 //String hql_Attachment="TSAttachment";
 		// commonDao.executeHql(hql_Attachment);
 	   }
 	
 	
 	
 	
}