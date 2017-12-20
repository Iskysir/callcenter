package org.jeecgframework.web.cgform.service.impl.upload;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.FtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.jeecgframework.web.cgform.dao.upload.CgFormUploadDao;
import org.jeecgframework.web.cgform.entity.upload.CgUploadEntity;
import org.jeecgframework.web.cgform.service.upload.CgUploadServiceI;

@Service("cgUploadService")
@Transactional
public class CgUploadServiceImpl extends CommonServiceImpl implements CgUploadServiceI {
	@Autowired
	private CgFormUploadDao cgFormUploadDao;

	/********************** 文件删除Start yangf **********************/
//	public void deleteFile(CgUploadEntity file) {
//		//step.1 删除附件
//		String sql = "select * from t_s_attachment where id = ?";
//		Map<String, Object> attachmentMap = commonDao.findOneForJdbc(sql, file.getId());
//		//附件相对路径
//		String realpath = (String) attachmentMap.get("realpath");
//		String fileName = FileUtils.getFilePrefix2(realpath);
//		
//		//获取部署项目绝对地址
//		String realPath = ContextHolderUtils.getSession().getServletContext().getRealPath("/");
//		FileUtils.delete(realPath+realpath);
//		FileUtils.delete(realPath+fileName+".pdf");
//		FileUtils.delete(realPath+fileName+".swf");
//		//step.2 删除数据
//		commonDao.delete(file);
//	}

	public void deleteFile(CgUploadEntity file) {
		//step.1 删除附件
		String sql = "select * from t_s_attachment where id = ?";
		Map<String, Object> attachmentMap = commonDao.findOneForJdbc(sql, file.getId());
		//附件相对路径
		String realpath = (String) attachmentMap.get("realpath");
		String directory = realpath.substring(0,realpath.lastIndexOf("/"));
		String filename = realpath.substring(realpath.lastIndexOf("/") + 1 );
		FtpUtils ftp = new FtpUtils();
		try {
			ftp.delete(directory, filename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//step.2 删除数据
		commonDao.delete(file);
	}

	public void deleteFileByCgformld(String cgformld) {
		// step.1 根据业务表名、主键、空间名获取所有附件信息
		String hql1 = "from CgUploadEntity where CGFORM_ID = ? ";
		List<CgUploadEntity> uploadLst = commonDao.findHql(hql1, cgformld);
		if (uploadLst != null && uploadLst.size() > 0){
			for(int i = 0; i < uploadLst.size(); i++){
				CgUploadEntity file = uploadLst.get(i);
				//step.1 删除附件
				this.deleteFile(file);
			}
		}
	}
	/********************** 文件删除End yangf **********************/
	
	public void writeBack(String cgFormId,String cgFormName,String cgFormField,String fileId,String fileUrl) {
		try{
			cgFormUploadDao.updateBackFileInfo(cgFormId, cgFormName, cgFormField, fileId, fileUrl);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("文件上传失败："+e.getMessage());
		}
	}

}
