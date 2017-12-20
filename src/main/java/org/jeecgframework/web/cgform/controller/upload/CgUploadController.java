package org.jeecgframework.web.cgform.controller.upload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.web.cgform.entity.upload.CgUploadEntity;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.jeecgframework.web.cgform.service.upload.CgUploadServiceI;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
import org.jeecgframework.web.system.service.SystemService;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @Title:CgUploadController
 * @description:智能表单文件上传控制器
 * @author 赵俊夫
 * @date Jul 24, 2013 9:10:44 PM
 * @version V1.0
 */
@Scope("prototype")
@Controller
@RequestMapping("/cgUploadController")
public class CgUploadController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CgUploadController.class);
	@Autowired
	private SystemService systemService;
	@Autowired
	private CgUploadServiceI cgUploadService;
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 保存文件
	 * 
	 * @param request
	 * @param response
	 * @param cgUploadEntity
	 *            智能表单文件上传实体
	 * @return
	 */
	@RequestMapping(params = "saveFiles", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson saveFiles(HttpServletRequest request,
			HttpServletResponse response, CgUploadEntity cgUploadEntity) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributes = new HashMap<String, Object>();
		try {
			String fileKey = oConvertUtils.getString(request
					.getParameter("fileKey"));// 文件ID
			String id = oConvertUtils.getString(request
					.getParameter("cgFormId"));// 动态表主键ID
			String tableName = oConvertUtils.getString(request
					.getParameter("cgFormName"));// 动态表名
			String cgField = oConvertUtils.getString(request
					.getParameter("cgFormField"));// 动态表上传控件字段
			if (!StringUtil.isEmpty(id)) {
				cgUploadEntity.setCgformId(id);
				cgUploadEntity.setCgformName(tableName);
				cgUploadEntity.setCgformField(cgField);
			}
			if (StringUtil.isNotEmpty(fileKey)) {
				cgUploadEntity.setId(fileKey);
				cgUploadEntity = systemService.getEntity(CgUploadEntity.class,
						fileKey);
			}
			UploadFile uploadFile = new UploadFile(request, cgUploadEntity);
			uploadFile.setCusPath("files");
			uploadFile.setSwfpath("swfpath");
			uploadFile.setByteField(null);// 不存二进制内容
			cgUploadEntity = systemService.uploadFile(uploadFile);
			cgUploadService.writeBack(id, tableName, cgField, fileKey,
					cgUploadEntity.getRealpath());
			attributes.put("fileKey", cgUploadEntity.getId());
			attributes.put(
					"viewhref",
					"commonController.do?objfileList&fileKey="
							+ cgUploadEntity.getId());
			attributes.put("delurl", "cgUploadController.do?delFile&id="
					+ cgUploadEntity.getId());

		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg("操作失败");
			throw new BusinessException(e.getMessage());
		}
		j.setMsg("操作成功");
		j.setAttributes(attributes);
		return j;
	}

	/**
	 * 删除文件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delFile")
	@ResponseBody
	public AjaxJson delFile(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		CgUploadEntity file = systemService.getEntity(CgUploadEntity.class, id);
		message = "" + file.getAttachmenttitle() + "被删除成功";
		cgUploadService.deleteFile(file);
		systemService.addLog(message, Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);
		j.setSuccess(true);
		j.setMsg(message);
		return j;
	}

	/**
	 * 根据记录ID获取附件JSON串
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getAttachFilesJson")
	@ResponseBody
	private AjaxJson getAttachFilesJson(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		// check
		if(id == null || id == "" || id.length() <=0 ){
			j.setSuccess(false);
			return j;
		}
		List<CgUploadEntity> uploadBeans = cgFormFieldService.findByProperty(CgUploadEntity.class, "cgformId", id);
		
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		for(CgUploadEntity b:uploadBeans){
			String title = b.getAttachmenttitle();//附件名
			String fileKey = b.getId();//附件主键
			String path = b.getRealpath();//附件路径
			String field = b.getCgformId();//表单中作为附件控件的字段
			Map<String, Object> file = new HashMap<String, Object>();
			file.put("title", title);
			file.put("fileKey", fileKey);
			file.put("path", path);
			file.put("field", field==null?"":field);
			files.add(file);
		}
		j.setSuccess(true);
		j.setMsg(JSONHelper.array2json(files));
		return j;
	}
}
