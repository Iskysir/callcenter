package com.chrhc.project.sc.jzw.controller;

import com.chrhc.project.sc.jzw.entity.ScJzwfjglEntity;
import com.chrhc.project.sc.jzw.entity.ScJzwglEntity;
import com.chrhc.project.sc.jzw.service.ScJzwfjglServiceI;
import com.chrhc.project.sc.jzw.service.ScJzwglServiceI;
import com.chrhc.project.sc.jzw.page.ScJzwglPage;
import com.chrhc.project.sc.jzw.entity.ScJzwdyglEntity;
import com.chrhc.project.sc.kinship.util.ScJzwPersonEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.DataBaseConstant;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.joda.time.chrono.IslamicChronology;

/**
 * @Title: Controller
 * @Description: 建筑物管理
 * @author onlineGenerator
 * @date 2015-04-29 09:30:45
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/scJzwglController")
public class ScJzwglController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ScJzwglController.class);

	@Autowired
	private ScJzwglServiceI scJzwglService;
	@Autowired
	private SystemService systemService;

	@Autowired
	private ScJzwfjglServiceI scJzwfjglService;

	private static final String ATUOCODE_N = "0";
	private static final String ATUOCODE_Y = "1";
	private static final String ATUOCODE_R = "2";

	/**
	 * 建筑物管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "scJzwgl")
	public ModelAndView scJzwgl(HttpServletRequest request) {
		return new ModelAndView("com/chrhc/project/sc/jzw/scJzwglList");
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
	public void datagrid(ScJzwglEntity scJzwgl, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ScJzwglEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				scJzwgl);
		try {
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.scJzwglService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除建筑物管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "建筑物管理删除成功";
		try {
			ScJzwglEntity scJzwgl = systemService.getEntity(
					ScJzwglEntity.class, id);
			// 判断当前建筑物是否已生成建筑房间信息
			if (scJzwgl != null && (scJzwgl.getAutoFlag().equals(ATUOCODE_N) == false)) {
				message = "该建筑物已生成房间信息,不能删除";
				j.setMsg(message);
				j.setSuccess(true);
				return j;
			} else if (scJzwgl != null) {
				scJzwglService.delMain(scJzwgl);
				systemService.addLog(message, Globals.Log_Type_DEL,
						Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "建筑物管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setSuccess(true);
		return j;
	}

	/**
	 * 批量删除建筑物管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "建筑物管理删除成功";
		try {
			// ===================================================================================
			// 查询-已生成建筑物信息判断
			String hql0 = "from ScJzwglEntity where 1 = 1 AND id in (?) AND autoFlag <> " + ATUOCODE_N;
			// ===================================================================================
			// 变量声明
			List<ScJzwglEntity> scJzwglEntityLst = new ArrayList<ScJzwglEntity>();
			scJzwglEntityLst = systemService.findHql(hql0, ids);
			if (scJzwglEntityLst != null && scJzwglEntityLst.size() > 0) {
				message = "存在已生成房间信息的建筑物,不能删除";
				j.setSuccess(false);
				j.setMsg(message);
				return j;
			}
			for (String id : ids.split(",")) {
				ScJzwglEntity scJzwgl = systemService.getEntity(
						ScJzwglEntity.class, id);
				if (scJzwgl != null) {
					scJzwglService.delMain(scJzwgl);
					systemService.addLog(message, Globals.Log_Type_DEL,
							Globals.Log_Leavel_INFO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "建筑物管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setSuccess(true);
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加建筑物管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ScJzwglEntity scJzwgl, ScJzwglPage scJzwglPage,
			HttpServletRequest request) {
		List<ScJzwdyglEntity> scJzwdyglList = scJzwglPage.getScJzwdyglList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try {
			scJzwglService.addMain(scJzwgl, scJzwdyglList);
			systemService.addLog(message, Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "建筑物管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新建筑物管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ScJzwglEntity scJzwgl, ScJzwglPage scJzwglPage,
			HttpServletRequest request) {
		List<ScJzwdyglEntity> scJzwdyglList = scJzwglPage.getScJzwdyglList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try {
			scJzwglService.updateMain(scJzwgl, scJzwdyglList);
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "更新建筑物管理失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 建筑物管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ScJzwglEntity scJzwgl, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scJzwgl.getId())) {
			scJzwgl = scJzwglService.getEntity(ScJzwglEntity.class,
					scJzwgl.getId());
			req.setAttribute("scJzwglPage", scJzwgl);
		}
		return new ModelAndView("com/chrhc/project/sc/jzw/scJzwgl-add");
	}

	/**
	 * 建筑物管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ScJzwglEntity scJzwgl, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(scJzwgl.getId())) {
			scJzwgl = scJzwglService.getEntity(ScJzwglEntity.class,
					scJzwgl.getId());
			req.setAttribute("scJzwglPage", scJzwgl);
		}
		return new ModelAndView("com/chrhc/project/sc/jzw/scJzwgl-update");
	}

	/**
	 * 加载明细列表[建筑物单元管理]
	 * 
	 * @return
	 */
	@RequestMapping(params = "scJzwdyglList")
	public ModelAndView scJzwdyglList(ScJzwglEntity scJzwgl,
			HttpServletRequest req) {

		// ===================================================================================
		// 获取参数
		Object id0 = scJzwgl.getId();
		// ===================================================================================
		// 查询-建筑物单元管理
		String hql0 = "from ScJzwdyglEntity where 1 = 1 AND jZW_ID = ? ";
		try {
			List<ScJzwdyglEntity> scJzwdyglEntityList = systemService.findHql(
					hql0, id0);
			req.setAttribute("scJzwdyglList", scJzwdyglEntityList);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/chrhc/project/sc/jzw/scJzwdyglList");
	}

	/**
	 * 建筑物管理-检查是否生成建筑物房间信息
	 * 
	 * @return 1:已生成 0:未生成 9:不存在3:没有权限查看
	 */
	@RequestMapping(params = "dochkJzwfj")
	@ResponseBody
	public AjaxJson dochkJzwfj(HttpServletRequest request) {
		//取得登录人所属机构code
		String sysOrgCode = ResourceUtil.getUserSystemData(DataBaseConstant.SYS_ORG_CODE);
		boolean flag = false;
		
		AjaxJson j = new AjaxJson();
		String msg = ATUOCODE_Y;

		List<ScJzwglEntity> lstScJzw = new ArrayList<ScJzwglEntity>();
		try {

			String id = request.getParameter("id");
			String gisId = request.getParameter("gisId");

			ScJzwglEntity scJzwgl = new ScJzwglEntity();
			if(id != null && id.length() > 0)
			{
				// 判断当前建筑物是否已生成建筑房间信息
				scJzwgl = scJzwglService.getEntity(ScJzwglEntity.class, id);
				msg = scJzwgl.getAutoFlag();
			}else if(gisId != null && gisId.length() > 0){
				// 获取建筑物信息
				String hql1 = "from ScJzwglEntity where 1 = 1 AND GIS_ID = ?";
				lstScJzw = scJzwglService.findHql(hql1, gisId);
				if (lstScJzw != null && lstScJzw.size() > 0) {
					scJzwgl = lstScJzw.get(0);	
					//kaishi
					String autoFlag = scJzwgl.getAutoFlag();
					msg = autoFlag;
					if("1".equals(autoFlag)){
						//人员权限
						String hql2 = "from ScJzwglEntity where 1 = 1 AND GIS_ID = ? AND SYS_ORG_CODE LIKE ?";
						List<ScJzwglEntity> qxls = scJzwglService.findHql(hql2, gisId,sysOrgCode+"%");
						if(qxls != null && qxls.size() > 0){
							flag = true;
						}else{
							msg = "3";
						}	
					}
					
				} else {
					scJzwgl = null;
					
				}
			}
			if(scJzwgl == null) {
				msg = "9";
			}else{				
				j.setObj(scJzwgl.getName());	
			}
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(msg);
		j.setSuccess(flag);
		return j;
	}

	/**
	 * 建筑物管理-检查建筑物的信息是否已存在
	 * 
	 * @return true:存在 false:不存在
	 */
	@RequestMapping(params = "dochkJzwGis")
	@ResponseBody
	public AjaxJson dochkJzwGis(String id, String gisid,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		j.setSuccess(true);
		try {
			// 查询-建筑物单元管理
			String sql = "SELECT COUNT(1) FROM sc_jzwgl WHERE 1 = 1 ";
			if (id != null && id.length() > 0) {
				sql += " AND id <> '" + id + "'";
			}
			if (gisid != null && gisid.length() > 0) {
				sql += " AND gis_id = '" + gisid + "'";
			}
			Long cnt = systemService.getCountForJdbc(sql);
			if (cnt != null && cnt > 0) {
				j.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		return j;
	}

	/**
	 * 建筑物管理-批量生成建筑物房间信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchJzwfj")
	@ResponseBody
	public AjaxJson doBatchJzwfj(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "生成建筑物房间信息成功";
		List<ScJzwdyglEntity> lstScDyEntity = new ArrayList<ScJzwdyglEntity>();
		try {
			// 判断当前建筑物是否已生成建筑房间信息
			ScJzwglEntity scJzwgl = new ScJzwglEntity();
			scJzwgl = scJzwglService.getEntity(ScJzwglEntity.class, id);
			if (scJzwgl == null) {
				j.setSuccess(true);
				j.setMsg("该建筑物信息已不存在");
				return j;
			} else if (scJzwgl.getAutoFlag().equals(ATUOCODE_Y)) {
				j.setSuccess(true);
				j.setMsg("该建筑物已生成房间信息");
				return j;
			}

			// 重新生成房间信息时，删除已生成的房间信息
			
			/*if(scJzwgl.getAutoCode().equals(ATUOCODE_N) == false){
				String delSql = "delete from sc_jzwfjgl where jZW_ID = ?";
				systemService.executeSql(delSql, id);		
			}*/

			// 获取建筑物单元信息,生成建筑物房间信息
			lstScDyEntity = getScJzwdyglList(id);
			List<ScJzwfjglEntity> entitys = new ArrayList<ScJzwfjglEntity>();
			for (ScJzwdyglEntity scDyEntity : lstScDyEntity) {
				// 楼层数
				for (int iFl = 1; iFl <= scDyEntity.getFloors(); iFl++) {
					for (int iRm = 1; iRm <= scDyEntity.getRooms(); iRm++) {
						ScJzwfjglEntity scJzwfjgl = new ScJzwfjglEntity();
						scJzwfjgl.setJzwId(id);
						scJzwfjgl.setDyId(scDyEntity.getId());
						scJzwfjgl.setUnit(scDyEntity.getUnit());
						scJzwfjgl.setFloors(iFl);
						scJzwfjgl.setNumber(iRm);
						scJzwfjgl.setRoom(String.valueOf(iFl)
								+ String.format("%02d", iRm));
						scJzwfjgl.setGisxy(scJzwgl.getGisxy());
						scJzwfjgl.setGisId(scJzwgl.getGisId());
						scJzwfjgl.setDelflag("0");
						entitys.add(scJzwfjgl);
					}
				}
			}
			//房间生成重新处理开始
			List<ScJzwfjglEntity>  oldfjglEn = scJzwfjglService.findByProperty(ScJzwfjglEntity.class, "jzwId", id);
			List<ScJzwfjglEntity> newlist = new ArrayList<ScJzwfjglEntity>();
			List<ScJzwfjglEntity> oldlist = new ArrayList<ScJzwfjglEntity>();
			if(entitys != null && entitys.size() > 0){
				
				for(ScJzwfjglEntity newfjglEn : entitys){
					String dyId = newfjglEn.getDyId();//new 单元ID
					String room = newfjglEn.getRoom();//new 房间号
					if(oldfjglEn != null && oldfjglEn.size() > 0){
						for(ScJzwfjglEntity old : oldfjglEn){
							String oldDyId = old.getDyId();//old单元ID
							String oldRoom = old.getRoom();//old房间号
							if(dyId.equals(oldDyId)){
								if(room.equals(oldRoom)){
									//需修改旧的房间信息，并在新房间list中清除此房间信息保证新增，老房间list中也要清除保证不被删除								
									oldlist.add(old);									
									newlist.add(newfjglEn);
									MyBeanUtils.copyBeanNotNull2Bean(newfjglEn,old);
									scJzwfjglService.saveOrUpdate(old);
								}
							}
							
						}
					}
				}	
			}
			oldfjglEn.removeAll(oldlist);
			entitys.removeAll(newlist);
			if(oldfjglEn != null && oldfjglEn.size() > 0){
				for(ScJzwfjglEntity en : oldfjglEn){
					en.setDelflag("1");
					scJzwfjglService.saveOrUpdate(en);
				}
			}
			//scJzwfjglService.deleteAllEntitie(oldfjglEn);
			//房间生成重新处理结束
			// 保存房间信息
			scJzwfjglService.batchSave(entitys);
			// 更新生成标识
			scJzwgl = scJzwglService.getEntity(ScJzwglEntity.class, id);
			scJzwgl.setAutoFlag(ATUOCODE_Y);
			scJzwgl.setAutoCode(ATUOCODE_Y);
			scJzwglService.saveOrUpdate(scJzwgl);

			systemService.addLog(message, Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "生成建筑物房间信息失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setSuccess(true);
		j.setMsg(message);
		return j;
	}

	
	/**
	 * 建筑物管理-查看建筑物房间信息
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "findFjByJzwId")
	public ModelAndView findFjByJzwId(String id, HttpServletRequest request,
			HttpServletResponse response) {
		// ===================================================================================
		// 变量定义
		ScJzwglEntity scJzw = new ScJzwglEntity();
		try {
			// 获取建筑物信息
			scJzw = scJzwglService.get(ScJzwglEntity.class, id);
			if(scJzw == null){
				// GISID未关联建筑物信息
				request.setAttribute("errFlag", "NULL");
				return new ModelAndView("com/chrhc/project/sc/jzw/scJzwfjglNull");
			}else if(scJzw.getAutoCode().equals(ATUOCODE_Y) == false){
				// 建筑物信息未生成房间信息或需重新生成
				request.setAttribute("errFlag", "AUTOFALSE");
				request.setAttribute("name", scJzw.getName());
				return new ModelAndView("com/chrhc/project/sc/jzw/scJzwfjglNull");
			}

			// 获取房间信息
			this.getFjxxByJzw(scJzw, request);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return new ModelAndView("com/chrhc/project/sc/jzw/scJzwfjglList");
	}

	/**
	 * 建筑物管理-查看建筑物房间信息
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "findFjByGisId")
	public ModelAndView findFjByGisId(String id, HttpServletRequest request,
			HttpServletResponse response) {
		// ===================================================================================
		// 变量定义
		List<ScJzwglEntity> lstScJzw = new ArrayList<ScJzwglEntity>();
		ScJzwglEntity scJzw = new ScJzwglEntity();

		// 查询-建筑物管理
		String hql1 = "from ScJzwglEntity where 1 = 1 AND GIS_ID = ?";

		try {
			// 获取建筑物信息
			lstScJzw = scJzwglService.findHql(hql1, id);
			if (lstScJzw != null && lstScJzw.size() > 0) {
				scJzw = lstScJzw.get(0);
			} else {
				// GISID未关联建筑物信息
				request.setAttribute("errFlag", "NULL");
				return new ModelAndView("com/chrhc/project/sc/jzw/scJzwfjglNull");
			}
			if(scJzw != null && scJzw.getAutoCode().equals(ATUOCODE_Y) == false){
				// 建筑物信息未生成房间信息或需重新生成
				request.setAttribute("errFlag", "AUTOFALSE");
				request.setAttribute("name", scJzw.getName());
				return new ModelAndView("com/chrhc/project/sc/jzw/scJzwfjglNull");
			}

			// 获取房间信息
			this.getFjxxByJzw(scJzw, request);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return new ModelAndView("com/chrhc/project/sc/jzw/scJzwfjglList");
	}

	/**
	 * 
	 * @param scJzw
	 * @param request
	 */
	private void getFjxxByJzw(ScJzwglEntity scJzw, HttpServletRequest request) {

		// ===================================================================================
		// 变量定义
		List<ScJzwfjglEntity> lstScFjEntity = new ArrayList<ScJzwfjglEntity>();
		List<ScJzwdyglEntity> lstScDyEntity = new ArrayList<ScJzwdyglEntity>();
		Map<String, String> map = new HashMap<String, String>();
		List lstFj = new ArrayList(); // 房间信息
		// ===================================================================================
		// 查询-建筑物房间管理
		String hql0 = "from ScJzwfjglEntity where 1 = 1 AND jzwId = ? AND delflag='0' order by floors desc, unit asc, number asc";

		// 查询-家庭信息
		String sql = "select fj.id as jfid , jt.id as jtid ,jt.hzxm as hzxm ,jt.fwcq,jt.xjwmjt from sc_jzwfjgl fj , sc_jtxxnew jt "
				+ "where fj.id = jt.sqly_id and fj.jzw_id = ? and jt.delflag = '0' order by fj.floors desc, fj.unit asc, fj.number asc,jt.hzxm";
		try {
			String jzwId = scJzw.getId(); // 建筑物Id

			// 判断当前建筑物是否已生成建筑房间信息
			lstScDyEntity = getScJzwdyglList(jzwId);

			// 房间信息列表
			lstScFjEntity = systemService.findHql(hql0, jzwId);

			// 家庭信息
			List<Map<String, Object>> lstJt = systemService.findForJdbc(sql,
					jzwId);

			// 房间信息处理
			if (lstScFjEntity != null && lstScFjEntity.size() > 0) {
				int iFl = 1;
				lstFj = new ArrayList();
				List lstMap = new ArrayList();

				// 单元ID
				String strDyId = lstScFjEntity.get(0).getDyId();

				for (int iRow = 0; iRow < lstScFjEntity.size(); iRow++) {
					ScJzwfjglEntity scFj = lstScFjEntity.get(iRow);
					if (iRow == 0) {
						iFl = scFj.getFloors(); // 楼层数
					}

					// 切换下一楼层
					if (iFl != scFj.getFloors()) {
						iFl = scFj.getFloors();
						lstFj.add(lstMap);
						lstMap = new ArrayList();
					}

					// 房间号
					map = new HashMap<String, String>();
					map.put("id", scFj.getId());
					map.put("unit", scFj.getDyId());
					map.put("room", scFj.getRoom());
					map.put("gisxy", scFj.getGisxy());
					map.put("gisid", scFj.getGisId());
					map.put("jtxx", this.getJtxxByFj(scFj.getId(), lstJt));
					lstMap.add(map);
				}
				// 最后一条记录
				lstFj.add(lstMap);
			}

			// 统计房间数
			int fRooms = 0;
			if (lstScDyEntity != null && lstScDyEntity.size() > 0) {
				for (ScJzwdyglEntity scJzwdy : lstScDyEntity) {
					fRooms += scJzwdy.getRooms();
				}
			}

			request.setAttribute("scJzwgl", scJzw);
			request.setAttribute("lstFj", lstFj);
			request.setAttribute("lstFjEntity", lstScFjEntity);
			request.setAttribute("lstDy", lstScDyEntity);
			request.setAttribute("fRooms", fRooms);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	/**
	 * 加载明细列表[建筑物单元管理]
	 * 
	 * @return
	 */
	private List<ScJzwdyglEntity> getScJzwdyglList(String id) {
		// ===================================================================================
		// 获取参数
		Object id0 = id;
		// ===================================================================================
		// 查询-建筑物单元管理
		String hql0 = "from ScJzwdyglEntity where 1 = 1 AND jZW_ID = ? order by unit";
		// ===================================================================================
		// 变量声明
		List<ScJzwdyglEntity> scJzwdyglEntityList = new ArrayList<ScJzwdyglEntity>();
		try {
			scJzwdyglEntityList = systemService.findHql(hql0, id0);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return scJzwdyglEntityList;
	}

	/**
	 * 根据房间ID获取家庭信息ID和户主姓名
	 * 
	 * @param fjId
	 *            房间ID
	 * @param lst
	 *            家庭信息List
	 * @return
	 */
	private String getJtxxByFj(String fjId, List<Map<String, Object>> lst) {
		String strRst = "";

		List<Map<String, Object>> lstNew = new ArrayList<Map<String, Object>>();
		if (lst != null && lst.size() > 0) {
			for (Map<String, Object> map : lst) {
				if (map.get("jfid").equals(fjId)) {
					String xjwmjt = String.valueOf(map.get("xjwmjt"));
					String fwcq = String.valueOf(map.get("fwcq"));
					if(StringUtils.isNotBlank(xjwmjt) && !"null".equals(xjwmjt)){//此家庭属于文明家庭
						map.put("name", ScJzwPersonEnum.WENMINGJIATING.getName());
						map.put("cssname", ScJzwPersonEnum.WENMINGJIATING.getCssName());
					}else if("4".equals(fwcq)){//此家庭属于租住户
						map.put("name", ScJzwPersonEnum.ZUZHUHU.getName());
						map.put("cssname", ScJzwPersonEnum.ZUZHUHU.getCssName());
					}else{//普通家庭
						map.put("name", ScJzwPersonEnum.JIATING.getName());
						map.put("cssname", ScJzwPersonEnum.JIATING.getCssName());
					}
					lstNew.add(map);
				}
			}
			strRst = JSONHelper.array2json(lstNew);
		}
		return strRst;
	}
}
