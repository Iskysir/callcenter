package com.chrhc.project.sc.kinship.controller;
import com.chrhc.project.sc.kinship.entity.KinshipEntity;
import com.chrhc.project.sc.kinship.entity.PersonRecord;
import com.chrhc.project.sc.kinship.service.KinshipServiceI;
import com.chrhc.project.sc.kinship.util.CurdTypeEnum;
import com.chrhc.project.sc.kinship.util.KinshipEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.DataGridReturn;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;

import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

import org.jeecgframework.core.util.ExceptionUtil;



/**   
 * @Title: Controller
 * @Description: 亲属关系表
 * @author onlineGenerator
 * @date 2015-05-06 11:53:31
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/kinshipController")
public class KinshipController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(KinshipController.class);

	@Autowired
	private KinshipServiceI kinshipService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private com.chrhc.common.http.SmsHttpService SmsHttpService;
	@Autowired
	private JdbcTemplate jdbcTemplateXg;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 查看人员履历图
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "personrecord")
	public ModelAndView personrecord(@RequestParam(value="id",required=true) String id,HttpServletRequest request){
		
		ModelAndView mv = new ModelAndView();
		//获取人员履历时间抽信息开始
		List<PersonRecord> listpr = new ArrayList<PersonRecord>();
		this.kinshipService.getPersonRecord(id, listpr);
		//对listpr数组进行分组排序
		Map<String, List<PersonRecord> > map = new LinkedHashMap<String, List<PersonRecord>>();
		for(PersonRecord pr :listpr){
			String year =String.valueOf(pr.getYear()) ;
			if(map.containsKey(year)){
				List<PersonRecord> list = map.get(year);
				list.add(pr);
			}else{
				List<PersonRecord> listnew = new ArrayList<PersonRecord>();
				listnew.add(pr);
				map.put(year, listnew);
			}
		}
		mv.addObject("prmap", map);
		mv.addObject("listpr",listpr);
		//获取人员履历时间抽信息结束
		
		//获取人员基本信息开始
		String sql = "select * from sc_rkjbxxnew as t where t.id = ?";
		List<Map<String, Object>> listry = this.kinshipService.findForJdbc(sql, id);
		mv.addObject("rkjbxx", listry);
		//获取人员基本信息结束
		mv.setViewName("com/chrhc/project/sc/kinship/person_record");
		return mv;
	}

	@RequestMapping(params = "getjtrksx")
	@ResponseBody
	public AjaxJson getjtrksx(HttpServletRequest req){
		AjaxJson j = new AjaxJson();
		String jtId = req.getParameter("jtId");
		List<Map<String, String>> sxlist = new ArrayList<Map<String,String>>();
		if(StringUtil.isNotEmpty(jtId)){
			kinshipService.getjtrksx(jtId, sxlist);
		}
		//String json = JSONHelper.map2json(map);
		j.setObj(sxlist);
		return j;
	}
	@RequestMapping(params = "getQslxSex")
	@ResponseBody
	public AjaxJson getQslxSex(HttpServletRequest req){
		AjaxJson j = new AjaxJson();
		Map<String, Object> map = new HashMap<String, Object>();
		for(KinshipEnum ksen :KinshipEnum.values()){
			String code = ksen.getCode();
			Map<String, String> mapa = new HashMap<String, String>();
			
			String sex = ksen.getSex();
			String name = ksen.getName();
			String gxlx = ksen.getCode();
			mapa.put("name", name);
			mapa.put("sex", sex);
			
			map.put(code, mapa);
		}
		//String json = JSONHelper.map2json(map);
		j.setAttributes(map);
		return j;
	}
	/**
	 * 判断是否是户主
	 * 
	 */
	@RequestMapping(params = "checkhz")
	@ResponseBody
	public AjaxJson checkhz(HttpServletRequest req){
		AjaxJson j = new AjaxJson();
		Boolean flag = false;
		String message = "";
	
		String id = req.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(id)){
				String sql = "SELECT sfhz FROM SC_RKJBXXNEW AS T WHERE T.ID = ? and delflag='0'";
				List<Map<String, Object>> list =  kinshipService.findForJdbc(sql, id);
				if(list !=null && list.size() > 0){
					String sfhz = list.get(0).get("sfhz").toString();
					if("Y".equals(sfhz)){
						flag = true;
					}else{
						message = "该户主已被更改为非户主，请重新选择新户主!";
					}
					
				}else{
					message = "该户主已被删除，请重新选择新户主!";
				}
				
			}
			
		} catch (Exception e) {
			flag = false;
			message = "出错了，请联系管理员！"+e.toString();
		}
		j.setSuccess(flag);
		j.setMsg(message);
		return j;
	}
	/**
	 * 判断关联信息
	 * 
	 */
	@RequestMapping(params = "checkperson")
	@ResponseBody
	public AjaxJson checkperson(HttpServletRequest req){
		AjaxJson j = new AjaxJson();
		Boolean flag = false;
		String message = "";
	
		String id = req.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(id)){
				String sql = "SELECT ID FROM SC_RKJBXXNEW AS T WHERE T.ID = ? and delflag='0'";
				List<Map<String, Object>> list =  kinshipService.findForJdbc(sql, id);
				if(list !=null && list.size() > 0){
					flag = true;
				}
				
			}
			
		} catch (Exception e) {
			flag = false;
			message = "出错了，请联系管理员！"+e.toString();
		}
		j.setSuccess(flag);
		j.setMsg(message);
		return j;
	}
	/**
	 * 判断关联信息
	 * 
	 */
	@RequestMapping(params = "checkdel")
	@ResponseBody
	public AjaxJson checkdel(HttpServletRequest req){
		AjaxJson j = new AjaxJson();
		Boolean flag = true;
		String message = "";
		String mainTable = req.getParameter("mainTable");
		String zTable = req.getParameter("zTable");
		String wjIdName = req.getParameter("wjIdName");
		String id = req.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(id)){
				String [] ids = id.split(",");
				for(String zjid:ids){
					List<Map<String, Object>> list = kinshipService.getGlcheck(mainTable, zTable, wjIdName, zjid);
					if(list != null && list.size() > 0){
						message = "存在关联信息记录不能删除！";
						flag = false;
						break;
					}
				}
				
			}
			
		} catch (Exception e) {
			flag = false;
			message = "删除失败，请联系管理员！"+e.toString();
		}
		j.setSuccess(flag);
		j.setMsg(message);
		return j;
	}
	/**
	 * 判断关联信息家庭信息专用
	 * 
	 */
	@RequestMapping(params = "checkdeljt")
	@ResponseBody
	public AjaxJson checkdeljt(HttpServletRequest req){
		AjaxJson j = new AjaxJson();
		Boolean flag = true;
		String message = "";
		String mainTable = req.getParameter("mainTable");
		String zTable = req.getParameter("zTable");
		String wjIdName = req.getParameter("wjIdName");
		String id = req.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(id)){
				String [] ids = id.split(",");
				for(String zjid:ids){
					List<Map<String, Object>> list = kinshipService.getGlcheck(mainTable, zTable, wjIdName, zjid);
					if(list != null && list.size() > 1){
						message = "存在关联信息记录不能删除！";
						flag = false;
						break;
					}
				}
				
			}
			
		} catch (Exception e) {
			flag = false;
			message = "删除失败，请联系管理员！"+e.toString();
		}
		j.setSuccess(flag);
		j.setMsg(message);
		return j;
	}
	/**
	 * 判断是否同一关系同一人不能新增
	 * 
	 */
	@RequestMapping(params = "checkadd")
	@ResponseBody
	public AjaxJson checkadd(HttpServletRequest req,KinshipEntity kinship){
		AjaxJson j = new AjaxJson();
		String gxlx = kinship.getGxlx();
		String ry_id = kinship.getRyId();
		String qs_id = kinship.getQsId();
		String id = kinship.getId();
		//String message = "";
		Map<String, KinshipEntity> map = kinshipService.getFlagAddKinship(gxlx, ry_id, qs_id,id);
		KinshipEntity kin = map.get("flag");
		if(kin != null){
			j.setSuccess(false);
			j.setMsg(kin.getName());
		}else{
			j.setSuccess(true);
		}
		//String json = JSONHelper.map2json(map);
		return j;
	}
	@RequestMapping(params = "getjtxx")
	@ResponseBody
	public AjaxJson getjtxx(HttpServletRequest req){
		AjaxJson j = new AjaxJson();
		String id = req.getParameter("ssjt_id");
		if(StringUtil.isNotEmpty(id)){
			String sql = "select * from sc_jtxxnew where id = ? and delflag='0'";
			List<Map<String, Object>> list = 	kinshipService.findForJdbc(sql, id);
			j.setObj(list);
		//	String json = JSONHelper.array2json(list);
			//j.setObj(json);
		}
		return j;
	}
	/**
	 * 清除亲属关系
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "dodelrk")
	@ResponseBody
	public AjaxJson dodelrk(KinshipEntity kinship, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "亲属关系表删除成功";
		try{
			String rkid = request.getParameter("rkid");
			String hz_idnew = request.getParameter("hz_id");
			String qsgxnew = request.getParameter("qsgx");
			if(StringUtil.isNotEmpty(rkid)){
				String sql = "SELECT m.hz_id,t.yhzgx from sc_rkjbxxnew as t INNER JOIN sc_jtxxnew as m ON t.ssjt_id = m.id  where t.id = ?";
				List<Map<String, Object>> list = kinshipService.findForJdbc(sql, rkid);
				if(list != null && list.size() > 0){
					String hz_id = list.get(0).get("hz_id").toString();
					String qsgx = list.get(0).get("yhzgx").toString();
					this.clearqsgx(hz_id, rkid, qsgx);//清除原有亲属关系
				
				}
			}
			this.clearqsgx(hz_idnew, rkid, qsgxnew);//判断最新修改关系，亲属关系中是否存在，如果存在则先清空关系
		}catch(Exception e){
			e.printStackTrace();
			message = "亲属关系表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setObj(kinship);
		return j;
	}
	private void clearqsgx(String hz_id,String rkid,String qsgx){
		if(StringUtil.isNotEmpty(hz_id) && StringUtil.isNotEmpty(rkid) && StringUtil.isNotEmpty(qsgx)){
			String qsgxsql = "select * from sc_qsgx where gxlx =? and ry_id=? and qs_id = ? and delflag='0'";
			List<Map<String, Object>> lista = kinshipService.findForJdbc(qsgxsql, qsgx,hz_id,rkid);
			if(lista != null && lista.size() > 0){
				String id = lista.get(0).get("id").toString();
				KinshipEntity bean = kinshipService.getEntity(KinshipEntity.class, id);
				kinshipService.delete(bean);
				kinshipService.addReverseKinship(bean, CurdTypeEnum.DELETE);
			}
		}
	}
	/**
	 * 添加亲属关系表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAddrk")
	@ResponseBody
	public AjaxJson doAddrk(KinshipEntity kinship, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "亲属关系表添加成功";
		try{
			String qsid = kinship.getQsId();
			String ryid = kinship.getRyId();
			kinshipService.save(kinship);
			
			//关系映射操作
			kinshipService.addReverseKinship(kinship,CurdTypeEnum.ADD);
			
			
			
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "亲属关系表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setObj(kinship);
		return j;
	}
	/**
	 * 查看亲属关系图
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "diagram")
	public ModelAndView diagram(@RequestParam(value="id",required=true) String id,HttpServletRequest request){
		
		
		//SmsHttpService.SendMessage("33", "13583162671", "413测试短信", "", "", "");
		ModelAndView mv = new ModelAndView();
		String json = kinshipService.getQsGxById(id);
		//request.setAttribute("qsgxjson", json);
		
		//String sql = "select xb from sc_rkjbxxnew where id = ?";
		String sex = "1";
		//List<Map<String, Object>> list =  kinshipService.findForJdbc(sql, id);
		List<Map<String, Object>> list =  kinshipService.getRkjbxx(id);
		int ry_graphid = 0;
		if(list != null && list.size() > 0){
			sex = list.get(0).get("xb").toString();
			String graphida = list.get(0).get("graphid").toString();
			ry_graphid = Integer.parseInt(graphida);
		}
		//查询spark亲属关系图测试
		//String sparkjson = kinshipService.getSparkQsgx(ry_graphid);
		mv.addObject("qsgxjson", json);
		mv.addObject("sex", sex);
		mv.setViewName("com/chrhc/project/sc/kinship/kinship_diagram");
		return mv;
	}
	/**
	 * 亲属关系表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "kinship")
	public ModelAndView kinship(HttpServletRequest request) {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		request.setAttribute("ry_id", id);
		request.setAttribute("ry_name", name);
		return new ModelAndView("com/chrhc/project/sc/kinship/kinshipList");
	}
	
	/**
	 * 巡更系统查询页面跳转
	 */
	@RequestMapping(params = "xgView")
	public ModelAndView xgView(HttpServletRequest request) {
		/*String id = request.getParameter("id");
		String name = request.getParameter("name");
		request.setAttribute("ry_id", id);
		request.setAttribute("ry_name", name);*/
		return new ModelAndView("com/chrhc/project/sc/kinship/xg_view_list");
	}
	/**
	 * 巡更系统请求数据
	 */
	@RequestMapping(params = "xgViewDatagrid")
	public void xgViewDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String xld = String.valueOf(request.getParameter("xld"));
		String idcard =String.valueOf(request.getParameter("idcard"));
		String sqname = String.valueOf(request.getParameter("sqname"));
		//String sql = "select * from BC_user";
		//String sql = "SELECT top 500 '准时' as clzt,'班组1' as xly,t.CardName as kh,t.TermId as xjqmc, t.TermId as xjq,t.RecordId as jlh ,t.CheckDateTime as rq ,b.Name as xld,b.Location as azwz from BC_GPSpatrolrecord  as t INNER JOIN BC_patrolrecord as a on t.TermId = a.TermId INNER JOIN  BC_place as b on a.AreaId = b.AreaId ORDER BY t.CheckDateTime DESC;";
				
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT '准时' as clzt,'班组1' as xly,t.CardName as kh,t.TermId as xjq,t.TermId as xjqmc, t.RecordId as jh,t.CheckDateTime as rq ,b.Name as xld,b.Location as azwz from BC_patrolrecord as t  LEFT JOIN BC_place as b on t.PlaceId = b.ID  where 1=1 ")	;
	if(StringUtils.isNotEmpty(xld)&& !"null".equals(xld)){
		sql.append(" and b.Name like '"+xld+"%'");
	}
	sql.append(" ORDER BY t.CheckDateTime DESC");
	List list = jdbcTemplateXg.queryForList(sql.toString());
		
		
		dataGrid.setResults(list);
		dataGrid.setTotal(list.size());
		dataGrid.setRows(100);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 证明合并页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "proveView")
	public ModelAndView proveView(HttpServletRequest request) {
		/*String id = request.getParameter("id");
		String name = request.getParameter("name");
		request.setAttribute("ry_id", id);
		request.setAttribute("ry_name", name);*/
		return new ModelAndView("com/chrhc/project/sc/kinship/prove_view_list");
	}
	/**
	 * easyui 证明合并请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "proveViewDatagrid")
	public void proveViewDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String name = String.valueOf(request.getParameter("name"));
		String idcard =String.valueOf(request.getParameter("idcard"));
		String sqname = String.valueOf(request.getParameter("sqname"));
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT * from (");
		sb.append(" SELECT t.code as code ,t.name as name ,t.idcard as idcard,t.community_name as sqname,'居住证明' as type,t.create_date as create_date from sc_stay as t ");
		//sb.append(" UNION ALL");
		//sb.append(" SELECT r.code as code ,r.master_name as name ,r.obligatea as idcard,r.community_name as sqname,'出租户证明' as type ,r.create_date as create_date from sc_rent as r ");
		sb.append(" UNION ALL");
		sb.append(" SELECT t.code as code ,t.move_name as name ,t.move_idcard as idcard ,t.community_name as sqname, '户口迁入证明' as type, t.create_date as create_date from sc_move_into as t ");
		sb.append(" ) as m   ORDER BY m.create_date DESC");	
		List<Map<String, Object>> list =  kinshipService.findForJdbc(sb.toString());
		dataGrid.setResults(list);
		dataGrid.setTotal(list.size());
		dataGrid.setRows(100);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 证明类型选择 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "prove")
	public ModelAndView prove(HttpServletRequest request) {
	
		return new ModelAndView("com/chrhc/project/sc/kinship/prove");
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
	public void datagrid(KinshipEntity kinship,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(KinshipEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, kinship, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		DataGridReturn ss = this.kinshipService.getDataGridReturn(cq, true);
		
		List<KinshipEntity> list = dataGrid.getResults();
		if(list != null && list.size() > 0){
			 for(KinshipEntity t : list){
				 String qsid = t.getQsId();
				 String sql = "select sfzh from sc_rkjbxxnew as t where t.id = ?";
				List<Map<String, Object>> 	listm =   kinshipService.findForJdbc(sql, qsid);
				if(listm != null && listm.size() > 0){
					String sfzh = listm.get(0).get("sfzh").toString();
					t.setObligatea(sfzh);
				}
			 }
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除亲属关系表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(KinshipEntity kinship, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		kinship = systemService.getEntity(KinshipEntity.class, kinship.getId());
		message = "亲属关系表删除成功";
		try{
			kinshipService.delete(kinship);
			kinshipService.addReverseKinship(kinship, CurdTypeEnum.DELETE);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "亲属关系表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除亲属关系表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "亲属关系表删除成功";
		try{
			for(String id:ids.split(",")){
				KinshipEntity kinship = systemService.getEntity(KinshipEntity.class, 
				id
				);
				kinshipService.delete(kinship);
				kinshipService.addReverseKinship(kinship, CurdTypeEnum.DELETE);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "亲属关系表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加亲属关系表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(KinshipEntity kinship, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "亲属关系表添加成功";
		try{
			kinship.setDelflag("0");
			kinshipService.save(kinship);
			
			//关系映射操作
			kinshipService.addReverseKinship(kinship,CurdTypeEnum.ADD);
			
			
			
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "亲属关系表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setObj(kinship);
		return j;
	}
	
	/**
	 * 更新亲属关系表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(KinshipEntity kinship, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "亲属关系表更新成功";
		KinshipEntity t = kinshipService.get(KinshipEntity.class, kinship.getId());
		
		
		
		try {
			kinshipService.addReverseKinship(kinship,CurdTypeEnum.UPDATE);
			MyBeanUtils.copyBeanNotNull2Bean(kinship, t);
			kinshipService.saveOrUpdate(t);
			
			
			
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "亲属关系表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 亲属关系表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(KinshipEntity kinship, HttpServletRequest req) {
		
		if (StringUtil.isNotEmpty(kinship.getId())) {
			kinship = kinshipService.getEntity(KinshipEntity.class, kinship.getId());	
		}else{
			String ryId = kinship.getRyId();
			String ryName = kinship.getRyName();
			if(StringUtils.isNotEmpty(ryId)){
				kinship.setRyId(ryId);
			}
			if(StringUtils.isNotEmpty(ryName)){
				kinship.setRyName(ryName);
			}
		}
		
		req.setAttribute("kinshipPage", kinship);
		return new ModelAndView("com/chrhc/project/sc/kinship/kinship-add");
	}
	/**
	 * 亲属关系表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(KinshipEntity kinship, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(kinship.getId())) {
			kinship = kinshipService.getEntity(KinshipEntity.class, kinship.getId());
			req.setAttribute("kinshipPage", kinship);
		}
		return new ModelAndView("com/chrhc/project/sc/kinship/kinship-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/chrhc/project/sc/kinship/kinshipUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(KinshipEntity kinship,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(KinshipEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, kinship, request.getParameterMap());
		List<KinshipEntity> kinships = this.kinshipService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"亲属关系表");
		modelMap.put(NormalExcelConstants.CLASS,KinshipEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("亲属关系表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,kinships);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(KinshipEntity kinship,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "亲属关系表");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,KinshipEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA,null);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<KinshipEntity> listKinshipEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),KinshipEntity.class,params);
				for (KinshipEntity kinship : listKinshipEntitys) {
					kinshipService.save(kinship);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
}
