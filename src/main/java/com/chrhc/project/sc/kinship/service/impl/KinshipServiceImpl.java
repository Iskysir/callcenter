package com.chrhc.project.sc.kinship.service.impl;
import com.chrhc.project.sc.kinship.service.KinshipServiceI;
import com.chrhc.project.sc.kinship.util.CurdTypeEnum;
import com.chrhc.project.sc.kinship.util.KinshipEnum;
import com.chrhc.project.sc.kinship.util.ScJzwPersonEnum;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.extend.datasource.DataSourceType;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.PropertiesUtil;

import com.chrhc.project.sc.kinship.entity.KinshipEntity;
import com.chrhc.project.sc.kinship.entity.PersonRecord;
import com.chrhc.project.sc.kinship.entity.SortByTime;
import com.fr.report.script.function.ADD2ARRAY;
import com.sun.org.apache.bcel.internal.generic.SIPUSH;
import com.sun.star.form.DataSelectionType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.Socket;

@Service("kinshipService")
@Transactional
public class KinshipServiceImpl extends CommonServiceImpl implements KinshipServiceI {
	private static PropertiesUtil util  = new PropertiesUtil("sysConfig.properties");
	private static String sparkIp =  util.readProperty("sparkIp");
	private static String sparkPort = util.readProperty("sparkPort");
	enum curd{
		ADD,UPDATE,DELETE
	}
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((KinshipEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((KinshipEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((KinshipEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(KinshipEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(KinshipEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(KinshipEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,KinshipEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{gxlx}",String.valueOf(t.getGxlx()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{bz}",String.valueOf(t.getBz()));
 		sql  = sql.replace("#{ry_id}",String.valueOf(t.getRyId()));
 		sql  = sql.replace("#{delflag}",String.valueOf(t.getDelflag()));
 		sql  = sql.replace("#{del_date}",String.valueOf(t.getDelDate()));
 		sql  = sql.replace("#{obligatea}",String.valueOf(t.getObligatea()));
 		sql  = sql.replace("#{obligateb}",String.valueOf(t.getObligateb()));
 		sql  = sql.replace("#{obligatec}",String.valueOf(t.getObligatec()));
 		sql  = sql.replace("#{obligated}",String.valueOf(t.getObligated()));
 		sql  = sql.replace("#{obligatee}",String.valueOf(t.getObligatee()));
 		sql  = sql.replace("#{qs_id}",String.valueOf(t.getQsId()));
 		sql  = sql.replace("#{ry_name}",String.valueOf(t.getRyName()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public KinshipEnum getKinShip(KinshipEntity entity) {
		String id = entity.getRyId();
		String code = entity.getGxlx();
		String xb = "";
		String sql = "SELECT XB FROM SC_RKJBXXNEW AS T WHERE T.ID = ? and delflag='0'";
		List<Map<String, Object>>  list = this.findForJdbc(sql, id);
		if(list != null && list.size() > 0){
			Map<String, Object> map = list.get(0);
			xb = (String)map.get("xb");
		}
		
		KinshipEnum kinship = null;
		for(KinshipEnum c :KinshipEnum.values()){
			if(code.equals(c.getCode())){
				kinship = c;
			}
		}
		if(kinship != null){
		switch (kinship) {
		case FATHER:
			kinship = KinshipEnum.SON;
			if("2".equals(xb)){
				kinship = KinshipEnum.DAUGHTER;
			}
			break;
		case MOTHER:
			kinship = KinshipEnum.SON;
			if("2".equals(xb)){
				kinship = KinshipEnum.DAUGHTER;
			}
			break;
		case SON:
			kinship = KinshipEnum.FATHER;
			if("2".equals(xb)){
				kinship = KinshipEnum.MOTHER;
			}
			break;
		case DAUGHTER:
			kinship = KinshipEnum.FATHER;
			if("2".equals(xb)){
				kinship = KinshipEnum.MOTHER;
			}
			break;
		case HUSBAND:
			kinship = KinshipEnum.WIFE;
			break;
		case WIFE:
			kinship = KinshipEnum.HUSBAND;
			break;
		case SON_WIFE:
			kinship = null;
			break;
		case DAUGHTER_HUSBAND:
			kinship = null;
			break;
			
		case GRANDFATHER:
			kinship = KinshipEnum.GRANDSON;
			if("2".equals(xb)){
				kinship = KinshipEnum.GRANDDAUGHTER;
			}
			break;
		case GRANDMOTHER:
			kinship = KinshipEnum.GRANDSON;
			if("2".equals(xb)){
				kinship = KinshipEnum.GRANDDAUGHTER;
			}
			break;
			
		case OUTGRANDFATHER:
			kinship = KinshipEnum.OUTGRANDSON;
			if("2".equals(xb)){
				kinship = KinshipEnum.OUTGRANDDAUGHTER;
			}
			break;
			
		case OUTGRANDMOTHER:
			kinship = KinshipEnum.OUTGRANDSON;
			if("2".equals(xb)){
				kinship = KinshipEnum.OUTGRANDDAUGHTER;
			}
			break;
		case GRANDSON:
			kinship = KinshipEnum.GRANDFATHER;
			if("2".equals(xb)){
				kinship = KinshipEnum.GRANDMOTHER;
			}
			break;
			
		case GRANDDAUGHTER:
			kinship = KinshipEnum.GRANDFATHER;
			if("2".equals(xb)){
				kinship = KinshipEnum.GRANDMOTHER;
			}
			break;
		case OUTGRANDSON:
			kinship = KinshipEnum.OUTGRANDFATHER;
			if("2".equals(xb)){
				kinship = KinshipEnum.OUTGRANDMOTHER;
			}
			break;
		case OUTGRANDDAUGHTER:
			kinship = KinshipEnum.OUTGRANDFATHER;
			if("2".equals(xb)){
				kinship = KinshipEnum.OUTGRANDMOTHER;
			}
			break;
		case ELDER_BROTHER:
			kinship = KinshipEnum.YOUNGER_BROTHER;
			if("2".equals(xb)){
				kinship = KinshipEnum.YOUNGER_SISTER;
			}
			break;
		case YOUNGER_BROTHER:
			kinship = KinshipEnum.ELDER_BROTHER;
			if("2".equals(xb)){
				kinship = KinshipEnum.ELDER_SISTER;
			}
			break;
		case ELDER_SISTER:
			kinship = KinshipEnum.YOUNGER_BROTHER;
			if("2".equals(xb)){
				kinship = KinshipEnum.YOUNGER_SISTER;
			}
			break;
		case YOUNGER_SISTER:
			kinship = KinshipEnum.ELDER_BROTHER;
			if("2".equals(xb)){
				kinship = KinshipEnum.ELDER_SISTER;
			}
			break;
		case FATHER_IN_LAW:
			kinship = null;
			break;
		case NEPHEW:
			kinship = null;
			break;
		case NIECE:
			kinship = null;
			break;
		case OUT_NEPHEW:
			kinship = null;
			break;
		case OUT_NIECE:
			kinship = null;
			break;
		case YOUNGER_SISTER_HUSBAND:
			kinship = null;
			break;
		case ELDER_SISTER_HUSBAND:
			kinship = null;
			break;
			
		default:
			kinship = null;
			break;
		}
		}
		return kinship;
	}

	@Override
	public void addReverseKinship(KinshipEntity kinship,CurdTypeEnum enumType) {
		String ryName = kinship.getRyName();
		String ryId = kinship.getRyId();
		String qsId = kinship.getQsId();
		String name = kinship.getName();
		String gxlx = kinship.getGxlx();
		KinshipEnum kinEnum = this.getKinShip(kinship);
		
			if(enumType != null){
				switch (enumType) {
				case ADD:
					if(kinEnum != null){
						Map<String,KinshipEntity> map = this.getFlagAddKinship(kinEnum.getCode(), qsId, ryId);
						KinshipEntity kin = map.get("flag");
						if(kin == null){
							KinshipEntity reverseKinship = new KinshipEntity();
							reverseKinship.setName(ryName);
							reverseKinship.setQsId(ryId);
							reverseKinship.setRyId(qsId);
							reverseKinship.setRyName(name);
							reverseKinship.setGxlx(kinEnum.getCode());
							reverseKinship.setDelflag("0");
							this.save(reverseKinship);	
						}
					}
					break;
				case UPDATE:
					KinshipEntity oldKinship = this.getEntity(KinshipEntity.class, kinship.getId());
					String oldQsId = oldKinship.getQsId();
					if(!oldQsId.equals(qsId)){
						if(kinEnum != null){
							Map<String,KinshipEntity> map = this.getFlagAddKinship(kinEnum.getCode(), oldQsId, ryId);
							KinshipEntity kin = map.get("flag");
							if(kin != null){
								this.delete(kin);
							}
							Map<String,KinshipEntity> mapa = this.getFlagAddKinship(kinEnum.getCode(), qsId, ryId);
							KinshipEntity kina = mapa.get("flag");
							if(kina == null){
								KinshipEntity reverseKinship = new KinshipEntity();
								reverseKinship.setName(ryName);
								reverseKinship.setQsId(ryId);
								reverseKinship.setRyId(qsId);
								reverseKinship.setRyName(name);
								reverseKinship.setGxlx(kinEnum.getCode());
								reverseKinship.setDelflag("0");
								this.save(reverseKinship);	
							}
						}
					}
					break;
				case DELETE:
					if(kinEnum != null){
						Map<String,KinshipEntity> map = this.getFlagAddKinship(kinEnum.getCode(), qsId, ryId);
						KinshipEntity kin = map.get("flag");
						if(kin != null){
							this.delete(kin);
						}
					}
					break;
				default:
					break;
				}
			}
			
			
			
		
		
	}

	@Override
	public Map<String,KinshipEntity> getFlagAddKinship(String gxlx, String ry_id, String qs_id) {
		Map<String,KinshipEntity> map = new HashMap<String, KinshipEntity>();
		KinshipEntity kinship = null;
		//boolean flag = false;
		String sql = "select id from sc_qsgx as t where t.gxlx = ? and t.ry_id = ? and t.qs_id = ? and delflag='0'";
		List<Map<String, Object>> list = this.findForJdbc(sql, gxlx,ry_id,qs_id);
		if(list != null && list.size() > 0){
			String id =String.valueOf(list.get(0).get("id")) ;
			 kinship = this.getEntity(KinshipEntity.class, id);
			 //flag = true;			
		}
		map.put("flag", kinship);
		return map;
	}

	@Override
	public List<Map<String, Object>> getRkjbxx(String id) {
		
		String sql = "SELECT ID,XB,XM,graphid FROM SC_RKJBXXNEW AS T WHERE T.ID = ? and delflag='0'";
		List<Map<String, Object>>  list = this.findForJdbc(sql, id);
		return list;
	}

	@Override
	public String getQsGxById(String id) {
		Map<String, List<Map<String,String>>> map = new HashMap<String, List<Map<String,String>>>();//存放人员关系图数据
		List<KinshipEntity> list = this.findByProperty(KinshipEntity.class, "ryId", id);//首先遍历已存在现有关系存入map，如何没有的关系，则根据具体逻辑查找
		if(list != null && list.size() > 0){
			for(KinshipEntity t :list){
				String qsgx = t.getGxlx();
				String name = t.getName();
				if(map.containsKey(qsgx)){
					List<Map<String,String>> listkin =map.get(qsgx);
					Map<String,String> mapa = new HashMap<String, String>();
					mapa.put("id", t.getQsId());
					mapa.put("name", name);
					listkin.add(mapa);
				}else{
					List<Map<String,String>> listkin = new ArrayList<Map<String,String>>();
					Map<String,String> mapa = new HashMap<String, String>();
					mapa.put("id", t.getQsId());
					mapa.put("name", name);
					listkin.add(mapa);
					map.put(qsgx, listkin);
				}
				
			}
		}
		
		for(KinshipEnum ksen :KinshipEnum.values()){
			String flag = ksen.getFlag();
			if("Y".equals(flag)){
				String code = ksen.getCode();
				//if(!map.containsKey(code)){
					
					switch (ksen) {
					case FATHER:
						//if(!map.containsKey(ksen.getCode())){
							//通过兄弟姐妹来查找父亲
							String byQsId = "";
							StringBuffer sb = new StringBuffer();
							this.appendRyId(map, sb, KinshipEnum.ELDER_BROTHER);
							this.appendRyId(map, sb, KinshipEnum.ELDER_SISTER);
							this.appendRyId(map, sb, KinshipEnum.ELDER_YOUNGER_BROTHER);
							this.appendRyId(map, sb, KinshipEnum.ELDER_YOUNGER_SISTER);
							this.appendRyId(map, sb, KinshipEnum.YOUNGER_BROTHER);
							this.appendRyId(map, sb, KinshipEnum.YOUNGER_SISTER);
							int b = sb.lastIndexOf(",");
							if(b != -1){
								byQsId = sb.substring(0, b);
								this.qsgxAddMap(map, byQsId, KinshipEnum.FATHER.getCode(), ksen,id);
							}
						//}
						//if(!map.containsKey(ksen.getCode())){

							//通过母亲来查找父亲
							String bmId="";
							StringBuffer sbm = new StringBuffer();
							this.appendRyId(map, sbm, KinshipEnum.MOTHER);
							int bm = sbm.lastIndexOf(",");
							if(bm != -1){
								bmId = sbm.substring(0, bm);
								this.qsgxAddMap(map, bmId, KinshipEnum.HUSBAND.getCode(), ksen,id);
							}
						
						//}
						break;
					case MOTHER:
						//if(!map.containsKey(ksen.getCode())){
							//通过兄弟姐妹来查找母亲
							String motherId = "";
							StringBuffer msb = new StringBuffer();
							this.appendRyId(map, msb, KinshipEnum.ELDER_BROTHER);
							this.appendRyId(map, msb, KinshipEnum.ELDER_SISTER);
							this.appendRyId(map, msb, KinshipEnum.ELDER_YOUNGER_BROTHER);
							this.appendRyId(map, msb, KinshipEnum.ELDER_YOUNGER_SISTER);
							this.appendRyId(map, msb, KinshipEnum.YOUNGER_BROTHER);
							this.appendRyId(map, msb, KinshipEnum.YOUNGER_SISTER);
							int mb = msb.lastIndexOf(",");
							if(mb != -1){
								motherId = msb.substring(0, mb);
								this.qsgxAddMap(map, motherId, KinshipEnum.MOTHER.getCode(), ksen,id);
							}
						//}
						//if(!map.containsKey(ksen.getCode())){
							//通过父亲来查找母亲
							String mbmId="";
							StringBuffer msbm = new StringBuffer();
							this.appendRyId(map, msbm, KinshipEnum.FATHER);
							int mbm = msbm.lastIndexOf(",");
							if(mbm != -1){
								mbmId = msbm.substring(0, mbm);
								this.qsgxAddMap(map, mbmId, KinshipEnum.WIFE.getCode(), ksen,id);
							}
						//}
						break;
					case SON:
						//通过女儿儿子找哥弟
						String sonId = "";
						StringBuffer sonsb = new StringBuffer();
						this.appendRyId(map, sonsb, KinshipEnum.DAUGHTER);
						this.appendRyId(map, sonsb, KinshipEnum.SON);
						int sonb = sonsb.lastIndexOf(",");
						if(sonb != -1){
							sonId = sonsb.substring(0, sonb);
							this.qsgxAddMap(map, sonId, "'"+KinshipEnum.YOUNGER_BROTHER.getCode()+"','"+KinshipEnum.ELDER_BROTHER.getCode()+"',"
									+ "'"+KinshipEnum.ELDER_YOUNGER_BROTHER.getCode()+"'", ksen,id);
						}
						
						//通过丈夫、妻子来找儿子
						String sonIda = "";
						StringBuffer sonsba = new StringBuffer();
						this.appendRyId(map, sonsba, KinshipEnum.HUSBAND);
						this.appendRyId(map, sonsba, KinshipEnum.WIFE);
						int sonba = sonsba.lastIndexOf(",");
						if(sonba != -1){
							sonIda = sonsba.substring(0, sonba);
							this.qsgxAddMap(map, sonIda, KinshipEnum.SON.getCode(), ksen,id);
						}
						
						break;
					case DAUGHTER:
						//通过儿子,女儿找女儿
						String daughterId = "";
						StringBuffer daughtersb = new StringBuffer();
						this.appendRyId(map, daughtersb, KinshipEnum.SON);
						this.appendRyId(map, daughtersb, KinshipEnum.DAUGHTER);
						int  daughterb = daughtersb.lastIndexOf(",");
						if(daughterb != -1){
							daughterId = daughtersb.substring(0, daughterb);
							this.qsgxAddMap(map, daughterId,"'"+KinshipEnum.YOUNGER_SISTER.getCode()+"','"+KinshipEnum.ELDER_SISTER.getCode()+"',"
									+ "'"+KinshipEnum.ELDER_YOUNGER_SISTER.getCode()+"'", ksen,id);
						}
						
						//通过丈夫、妻子来找女儿
						String sonIdab = "";
						StringBuffer sonsbab = new StringBuffer();
						this.appendRyId(map, sonsbab, KinshipEnum.HUSBAND);
						this.appendRyId(map, sonsbab, KinshipEnum.WIFE);
						int sonbab = sonsbab.lastIndexOf(",");
						if(sonbab != -1){
							sonIdab = sonsbab.substring(0, sonbab);
							this.qsgxAddMap(map, sonIdab, KinshipEnum.DAUGHTER.getCode(), ksen,id);
						}
						
						break;
					case HUSBAND:
						//通过儿女找丈夫
						String husbandId = "";
						StringBuffer husbandsb = new StringBuffer();
						this.appendRyId(map, husbandsb, KinshipEnum.SON);
						this.appendRyId(map, husbandsb, KinshipEnum.DAUGHTER);
						int  husbandb = husbandsb.lastIndexOf(",");
						if(husbandb != -1){
							husbandId = husbandsb.substring(0, husbandb);
							this.qsgxAddMap(map, husbandId,KinshipEnum.FATHER.getCode(), ksen,id);
						}
						break;
					case WIFE:
						//通过儿女找妻子
						String wifeId = "";
						StringBuffer wifeIdsb = new StringBuffer();
						this.appendRyId(map, wifeIdsb, KinshipEnum.SON);
						this.appendRyId(map, wifeIdsb, KinshipEnum.DAUGHTER);
						int  wifeb = wifeIdsb.lastIndexOf(",");
						if(wifeb != -1){
							wifeId = wifeIdsb.substring(0, wifeb);
							this.qsgxAddMap(map, wifeId, KinshipEnum.MOTHER.getCode(), ksen,id);
						}
						break;
					case SON_WIFE:
						//通过儿子找儿媳
						String sonWifeId = "";
						StringBuffer sonWifesb = new StringBuffer();
						this.appendRyId(map, sonWifesb, KinshipEnum.SON);
						int  sonWifeb = sonWifesb.lastIndexOf(",");
						if(sonWifeb != -1){
							sonWifeId = sonWifesb.substring(0, sonWifeb);
							this.qsgxAddMap(map, sonWifeId,KinshipEnum.WIFE.getCode(), ksen,id);
						}
						break;
					case DAUGHTER_HUSBAND:
						//通过女儿找女婿
						String daughterHusbandId = "";
						StringBuffer daughterHusbandsb = new StringBuffer();
						this.appendRyId(map, daughterHusbandsb, KinshipEnum.DAUGHTER);
						int daughterHusbandb = daughterHusbandsb.lastIndexOf(",");
						if(daughterHusbandb != -1){
							daughterHusbandId = daughterHusbandsb.substring(0, daughterHusbandb);
							this.qsgxAddMap(map, daughterHusbandId,KinshipEnum.HUSBAND.getCode(), ksen,id);
						}
						break;
					case UNCLE:
						
						//通过父亲找叔叔
						String uncleId = "";
						StringBuffer unclesb = new StringBuffer();
						this.appendRyId(map, unclesb, KinshipEnum.FATHER);
						int uncleb = unclesb.lastIndexOf(",");
						if(uncleb != -1){
							uncleId = unclesb.substring(0, uncleb);
							this.qsgxAddMap(map, uncleId,"'"+KinshipEnum.YOUNGER_BROTHER.getCode()+"','"+KinshipEnum.ELDER_BROTHER.getCode()+"'", ksen,id);
						}
						/*//通过祖父祖母找叔叔
						String uncleIda = "";
						StringBuffer unclesba = new StringBuffer();
						this.appendRyId(map, unclesba, KinshipEnum.GRANDFATHER);
						this.appendRyId(map, unclesba, KinshipEnum.GRANDMOTHER);
						int uncleba = unclesba.lastIndexOf(",");
						if(uncleba != -1){
							uncleIda = unclesba.substring(0, uncleba);
							this.qsgxAddMap(map, uncleIda,KinshipEnum.SON.getCode(), ksen,id);
						}*/
						break;
					case GRANDFATHER:
						
						//if(!map.containsKey(ksen.getCode())){
							//通过姑妈、叔叔、伯父、父亲找爷爷
							String grandfatherId = "";
							StringBuffer grandfathersb = new StringBuffer();
							this.appendRyId(map, grandfathersb, KinshipEnum.AUNT);
							this.appendRyId(map, grandfathersb, KinshipEnum.UNCLE);
							this.appendRyId(map, grandfathersb, KinshipEnum.FATHER);
							int grandfatherb = grandfathersb.lastIndexOf(",");
							if(grandfatherb != -1){
								grandfatherId = grandfathersb.substring(0, grandfatherb);
								this.qsgxAddMap(map, grandfatherId,KinshipEnum.FATHER.getCode(), ksen,id);
							}
						//}
						//if(!map.containsKey(ksen.getCode())){
							//通过祖母找祖父
							String tgrandfatherId = "";
							StringBuffer tgrandfathersb = new StringBuffer();
							this.appendRyId(map, tgrandfathersb, KinshipEnum.GRANDMOTHER);
							
							int tgrandfatherb = tgrandfathersb.lastIndexOf(",");
							if(tgrandfatherb != -1){
								tgrandfatherId = tgrandfathersb.substring(0, tgrandfatherb);
								this.qsgxAddMap(map, tgrandfatherId,KinshipEnum.HUSBAND.getCode(), ksen,id);
							}
						//}
						
						break;
					case GRANDMOTHER:
						//if(!map.containsKey(ksen.getCode())){
							//通过姑妈、叔叔、伯父、父亲找祖母
							String mgrandfatherId = "";
							StringBuffer mgrandfathersb = new StringBuffer();
							this.appendRyId(map, mgrandfathersb, KinshipEnum.AUNT);
							this.appendRyId(map, mgrandfathersb, KinshipEnum.UNCLE);
							this.appendRyId(map, mgrandfathersb, KinshipEnum.FATHER);
							int mgrandfatherb = mgrandfathersb.lastIndexOf(",");
							if(mgrandfatherb != -1){
								mgrandfatherId = mgrandfathersb.substring(0, mgrandfatherb);
								this.qsgxAddMap(map, mgrandfatherId,KinshipEnum.MOTHER.getCode(), ksen,id);
							}
						//}
					//	if(!map.containsKey(ksen.getCode())){
							//通过祖父找祖母
							String ograndfatherId = "";
							StringBuffer ograndfathersb = new StringBuffer();
							this.appendRyId(map, ograndfathersb, KinshipEnum.GRANDFATHER);
							
							int ograndfatherb = ograndfathersb.lastIndexOf(",");
							if(ograndfatherb != -1){
								ograndfatherId = ograndfathersb.substring(0, ograndfatherb);
								this.qsgxAddMap(map, ograndfatherId,KinshipEnum.WIFE.getCode(), ksen,id);
							}
						//}
						break;
						
					case OUTGRANDFATHER:
						//if(!map.containsKey(ksen.getCode())){
							//通过母亲、舅舅、姨妈找外祖父
							String grandfatherIda = "";
							StringBuffer grandfathersba = new StringBuffer();
							this.appendRyId(map, grandfathersba, KinshipEnum.MOTHER);
							this.appendRyId(map, grandfathersba, KinshipEnum.JIUJIU);
							this.appendRyId(map, grandfathersba, KinshipEnum.YIMA);
							int grandfatherba = grandfathersba.lastIndexOf(",");
							if(grandfatherba != -1){
								grandfatherIda = grandfathersba.substring(0, grandfatherba);
								this.qsgxAddMap(map, grandfatherIda,KinshipEnum.FATHER.getCode(), ksen,id);
							}
						//}
						//if(!map.containsKey(ksen.getCode())){
							//通过外祖母找外祖父
							String grandfatherIdb = "";
							StringBuffer grandfathersbb = new StringBuffer();
							this.appendRyId(map, grandfathersbb, KinshipEnum.OUTGRANDMOTHER);
							
							int grandfatherbb = grandfathersbb.lastIndexOf(",");
							if(grandfatherbb != -1){
								grandfatherIdb = grandfathersbb.substring(0, grandfatherbb);
								this.qsgxAddMap(map, grandfatherIdb,KinshipEnum.HUSBAND.getCode(), ksen,id);
							}
						//}
						break;
						
					case OUTGRANDMOTHER:
						//if(!map.containsKey(ksen.getCode())){
							//通过母亲、舅舅、姨妈找外祖母
							String grandfatherIdc = "";
							StringBuffer grandfathersbc = new StringBuffer();
							this.appendRyId(map, grandfathersbc, KinshipEnum.MOTHER);
							this.appendRyId(map, grandfathersbc, KinshipEnum.JIUJIU);
							this.appendRyId(map, grandfathersbc, KinshipEnum.YIMA);
							int grandfatherbc = grandfathersbc.lastIndexOf(",");
							if(grandfatherbc != -1){
								grandfatherIdc = grandfathersbc.substring(0, grandfatherbc);
								this.qsgxAddMap(map, grandfatherIdc,KinshipEnum.MOTHER.getCode(), ksen,id);
							}
						//}
						//if(!map.containsKey(ksen.getCode())){
							//通过外祖父找外祖母
							String grandfatherIdd = "";
							StringBuffer grandfathersbd = new StringBuffer();
							this.appendRyId(map, grandfathersbd, KinshipEnum.OUTGRANDFATHER);
							
							int grandfatherbd = grandfathersbd.lastIndexOf(",");
							if(grandfatherbd != -1){
								grandfatherIdd = grandfathersbd.substring(0, grandfatherbd);
								this.qsgxAddMap(map, grandfatherIdd,KinshipEnum.WIFE.getCode(), ksen,id);
							}
						//}
						break;
					case GRANDSON:
						//if(!map.containsKey(ksen.getCode())){
							//通过儿子媳妇找孙子
							String grandfatherIde = "";
							StringBuffer grandfathersbe = new StringBuffer();
							this.appendRyId(map, grandfathersbe, KinshipEnum.SON);
							this.appendRyId(map, grandfathersbe, KinshipEnum.SON_WIFE);
							int grandfatherbe = grandfathersbe.lastIndexOf(",");
							if(grandfatherbe != -1){
								grandfatherIde = grandfathersbe.substring(0, grandfatherbe);
								this.qsgxAddMap(map, grandfatherIde,KinshipEnum.SON.getCode(), ksen,id);
							}
						//}
						break;
						
					case GRANDDAUGHTER:
						//if(!map.containsKey(ksen.getCode())){
							//通过儿子媳妇找孙女
							String grandfatherIdf = "";
							StringBuffer grandfathersbf = new StringBuffer();
							this.appendRyId(map, grandfathersbf, KinshipEnum.SON);
							this.appendRyId(map, grandfathersbf, KinshipEnum.SON_WIFE);
							int grandfatherbf = grandfathersbf.lastIndexOf(",");
							if(grandfatherbf != -1){
								grandfatherIdf = grandfathersbf.substring(0, grandfatherbf);
								this.qsgxAddMap(map, grandfatherIdf,KinshipEnum.DAUGHTER.getCode(), ksen,id);
							}
						//}
						break;
					case OUTGRANDSON:
						//if(!map.containsKey(ksen.getCode())){
							//通过女儿女婿找外孙
							String grandfatherIdg = "";
							StringBuffer grandfathersbg = new StringBuffer();
							this.appendRyId(map, grandfathersbg, KinshipEnum.DAUGHTER);
							this.appendRyId(map, grandfathersbg, KinshipEnum.DAUGHTER_HUSBAND);
							int grandfatherbg = grandfathersbg.lastIndexOf(",");
							if(grandfatherbg != -1){
								grandfatherIdg = grandfathersbg.substring(0, grandfatherbg);
								this.qsgxAddMap(map, grandfatherIdg,KinshipEnum.SON.getCode(), ksen,id);
							}
						//}
						break;
					case OUTGRANDDAUGHTER:
						//if(!map.containsKey(ksen.getCode())){
							//通过女儿女婿找外孙女
							String grandfatherIdh = "";
							StringBuffer grandfathersbh = new StringBuffer();
							this.appendRyId(map, grandfathersbh, KinshipEnum.DAUGHTER);
							this.appendRyId(map, grandfathersbh, KinshipEnum.DAUGHTER_HUSBAND);
							int grandfatherbh = grandfathersbh.lastIndexOf(",");
							if(grandfatherbh != -1){
								grandfatherIdh = grandfathersbh.substring(0, grandfatherbh);
								this.qsgxAddMap(map, grandfatherIdh,KinshipEnum.DAUGHTER.getCode(), ksen,id);
							}
						//}
						
						break;
					case ELDER_BROTHER:
						
						break;
					case YOUNGER_BROTHER:
						
						break;
					case ELDER_SISTER:
						
						break;
					case YOUNGER_SISTER:
						
						break;
					case FATHER_IN_LAW:
					
						break;
					case ELDER_YOUNGER_BROTHER:
						//if(!map.containsKey(ksen.getCode())){
							//通过父母找哥哥弟弟
							String grandfatherIdi = "";
							StringBuffer grandfathersbi = new StringBuffer();
							this.appendRyId(map, grandfathersbi, KinshipEnum.FATHER);
							this.appendRyId(map, grandfathersbi, KinshipEnum.MOTHER);	
							int grandfatherbi = grandfathersbi.lastIndexOf(",");
							if(grandfatherbi != -1){
								grandfatherIdi = grandfathersbi.substring(0, grandfatherbi);
								this.qsgxAddMap(map, grandfatherIdi,KinshipEnum.SON.getCode(), ksen,id);
							}
							//通过哥哥弟弟姐姐妹妹找哥哥弟弟
							String uncleIdc = "";
							StringBuffer unclesbc = new StringBuffer();
							this.appendRyId(map, unclesbc, KinshipEnum.ELDER_BROTHER);
							this.appendRyId(map, unclesbc, KinshipEnum.ELDER_YOUNGER_BROTHER);
							this.appendRyId(map, unclesbc, KinshipEnum.YOUNGER_BROTHER);
							this.appendRyId(map, unclesbc, KinshipEnum.ELDER_SISTER);
							this.appendRyId(map, unclesbc, KinshipEnum.YOUNGER_SISTER);
							this.appendRyId(map, unclesbc, KinshipEnum.ELDER_YOUNGER_SISTER);
							int unclebc = unclesbc.lastIndexOf(",");
							if(unclebc != -1){
								uncleIdc = unclesbc.substring(0, unclebc);
								this.qsgxAddMap(map, uncleIdc,"'"+KinshipEnum.YOUNGER_BROTHER.getCode()+"','"+KinshipEnum.ELDER_BROTHER.getCode()+"',"
										+ "'"+KinshipEnum.ELDER_YOUNGER_BROTHER.getCode()+"'", ksen,id);
							}
						//}
						break;
					case ELDER_YOUNGER_SISTER:
						//if(!map.containsKey(ksen.getCode())){
							//通过父母找姐姐妹妹
							String grandfatherIdj = "";
							StringBuffer grandfathersbj = new StringBuffer();
							this.appendRyId(map, grandfathersbj, KinshipEnum.FATHER);
							this.appendRyId(map, grandfathersbj, KinshipEnum.MOTHER);	
							int grandfatherbj = grandfathersbj.lastIndexOf(",");
							if(grandfatherbj != -1){
								grandfatherIdj = grandfathersbj.substring(0, grandfatherbj);
								this.qsgxAddMap(map, grandfatherIdj,KinshipEnum.DAUGHTER.getCode(), ksen,id);
							}
							//通过哥哥弟弟姐姐妹妹找姐姐妹妹
							String uncleIdca = "";
							StringBuffer unclesbca = new StringBuffer();
							this.appendRyId(map, unclesbca, KinshipEnum.ELDER_BROTHER);
							this.appendRyId(map, unclesbca, KinshipEnum.ELDER_YOUNGER_BROTHER);
							this.appendRyId(map, unclesbca, KinshipEnum.YOUNGER_BROTHER);
							this.appendRyId(map, unclesbca, KinshipEnum.ELDER_SISTER);
							this.appendRyId(map, unclesbca, KinshipEnum.YOUNGER_SISTER);
							this.appendRyId(map, unclesbca, KinshipEnum.ELDER_YOUNGER_SISTER);
							int unclebca = unclesbca.lastIndexOf(",");
							if(unclebca != -1){
								uncleIdca = unclesbca.substring(0, unclebca);
								this.qsgxAddMap(map, uncleIdca,"'"+KinshipEnum.YOUNGER_SISTER.getCode()+"','"+KinshipEnum.ELDER_SISTER.getCode()+"',"
										+ "'"+KinshipEnum.ELDER_YOUNGER_SISTER.getCode()+"'", ksen,id);
							}
						//}
						break;
					case NEPHEW:
						//if(!map.containsKey(ksen.getCode())){
							//通过哥哥弟弟嫂嫂弟媳找侄子
							String grandfatherIdk = "";
							StringBuffer grandfathersbk = new StringBuffer();
							this.appendRyId(map, grandfathersbk, KinshipEnum.ELDER_BROTHER);
							this.appendRyId(map, grandfathersbk, KinshipEnum.YOUNGER_BROTHER);	
							this.appendRyId(map, grandfathersbk, KinshipEnum.ELDER_YOUNGER_BROTHER);	
							int grandfatherbk = grandfathersbk.lastIndexOf(",");
							if(grandfatherbk != -1){
								grandfatherIdk = grandfathersbk.substring(0, grandfatherbk);
								this.qsgxAddMap(map, grandfatherIdk,KinshipEnum.SON.getCode(), ksen,id);
							}
						//}
						break;
					case NIECE:
						//if(!map.containsKey(ksen.getCode())){
							//通过哥哥弟弟嫂嫂弟媳找侄女
							String grandfatherIdl = "";
							StringBuffer grandfathersbl = new StringBuffer();
							this.appendRyId(map, grandfathersbl, KinshipEnum.ELDER_BROTHER);
							this.appendRyId(map, grandfathersbl, KinshipEnum.YOUNGER_BROTHER);	
							this.appendRyId(map, grandfathersbl, KinshipEnum.ELDER_YOUNGER_BROTHER);	
							int grandfatherbl = grandfathersbl.lastIndexOf(",");
							if(grandfatherbl != -1){
								grandfatherIdl = grandfathersbl.substring(0, grandfatherbl);
								this.qsgxAddMap(map, grandfatherIdl,KinshipEnum.DAUGHTER.getCode(), ksen,id);
							}
						//}
						break;
					case OUT_NEPHEW:
						//if(!map.containsKey(ksen.getCode())){
							//通过姐姐妹妹找
							String grandfatherIdm = "";
							StringBuffer grandfathersbm = new StringBuffer();
							this.appendRyId(map, grandfathersbm, KinshipEnum.ELDER_YOUNGER_SISTER);
							this.appendRyId(map, grandfathersbm, KinshipEnum.YOUNGER_SISTER);	
							this.appendRyId(map, grandfathersbm, KinshipEnum.ELDER_SISTER);
							int grandfatherbm = grandfathersbm.lastIndexOf(",");
							if(grandfatherbm != -1){
								grandfatherIdm = grandfathersbm.substring(0, grandfatherbm);
								this.qsgxAddMap(map, grandfatherIdm,KinshipEnum.SON.getCode(), ksen,id);
							}
						//}
						break;
					case OUT_NIECE:
						//if(!map.containsKey(ksen.getCode())){
							//通过姐姐妹妹找
							String grandfatherIdn = "";
							StringBuffer grandfathersbn = new StringBuffer();
							this.appendRyId(map, grandfathersbn, KinshipEnum.ELDER_YOUNGER_SISTER);
							this.appendRyId(map, grandfathersbn, KinshipEnum.YOUNGER_SISTER);	
							this.appendRyId(map, grandfathersbn, KinshipEnum.ELDER_SISTER);
							int grandfatherbn = grandfathersbn.lastIndexOf(",");
							if(grandfatherbn != -1){
								grandfatherIdn = grandfathersbn.substring(0, grandfatherbn);
								this.qsgxAddMap(map, grandfatherIdn,KinshipEnum.DAUGHTER.getCode(), ksen,id);
							}
						//}
						break;
					case YOUNGER_SISTER_HUSBAND:
						//if(!map.containsKey(ksen.getCode())){
							//通过妹妹找妹夫
							String grandfatherIdo = "";
							StringBuffer grandfathersbo = new StringBuffer();
							this.appendRyId(map, grandfathersbo, KinshipEnum.YOUNGER_SISTER);
							
							int grandfatherbo = grandfathersbo.lastIndexOf(",");
							if(grandfatherbo != -1){
								grandfatherIdo = grandfathersbo.substring(0, grandfatherbo);
								this.qsgxAddMap(map, grandfatherIdo,KinshipEnum.HUSBAND.getCode(), ksen,id);
							}
						//}
						break;
					case ELDER_SISTER_HUSBAND:
						//if(!map.containsKey(ksen.getCode())){
							//通过姐姐找姐夫
							String grandfatherIdp = "";
							StringBuffer grandfathersbp = new StringBuffer();
							this.appendRyId(map, grandfathersbp, KinshipEnum.ELDER_SISTER);
							
							int grandfatherbp = grandfathersbp.lastIndexOf(",");
							if(grandfatherbp != -1){
								grandfatherIdp = grandfathersbp.substring(0, grandfatherbp);
								this.qsgxAddMap(map, grandfatherIdp,KinshipEnum.HUSBAND.getCode(), ksen,id);
							}
						//}
						break;
					case SISTER_HUSBAND:
						//if(!map.containsKey(ksen.getCode())){
							//通过姐姐妹妹找姐夫妹夫
							String grandfatherIdq = "";
							StringBuffer grandfathersbq = new StringBuffer();
							this.appendRyId(map, grandfathersbq, KinshipEnum.ELDER_YOUNGER_SISTER);
							
							int grandfatherbq = grandfathersbq.lastIndexOf(",");
							if(grandfatherbq != -1){
								grandfatherIdq = grandfathersbq.substring(0, grandfatherbq);
								this.qsgxAddMap(map, grandfatherIdq,KinshipEnum.HUSBAND.getCode(), ksen,id);
							}
						//}
						break;
					case BROTHER_WIFE:
						//if(!map.containsKey(ksen.getCode())){
							//通过哥哥弟弟找嫂嫂弟媳
							String grandfatherIdr = "";
							StringBuffer grandfathersbr = new StringBuffer();
							this.appendRyId(map, grandfathersbr, KinshipEnum.YOUNGER_BROTHER);
							this.appendRyId(map, grandfathersbr, KinshipEnum.ELDER_BROTHER);
							this.appendRyId(map, grandfathersbr, KinshipEnum.ELDER_YOUNGER_BROTHER);
							int grandfatherbr = grandfathersbr.lastIndexOf(",");
							if(grandfatherbr != -1){
								grandfatherIdr = grandfathersbr.substring(0, grandfatherbr);
								this.qsgxAddMap(map, grandfatherIdr,KinshipEnum.WIFE.getCode(), ksen,id);
							}
						//}
						break;
					
					case UNCLE_WIFE:
						//if(!map.containsKey(ksen.getCode())){
							//通过叔叔伯父找婶婶伯母
							String grandfatherIds = "";
							StringBuffer grandfathersbs = new StringBuffer();
							this.appendRyId(map, grandfathersbs, KinshipEnum.UNCLE);
							int grandfatherbs = grandfathersbs.lastIndexOf(",");
							if(grandfatherbs != -1){
								grandfatherIds = grandfathersbs.substring(0, grandfatherbs);
								this.qsgxAddMap(map, grandfatherIds,KinshipEnum.WIFE.getCode(), ksen,id);
							}
						//}
						break;
					case UNCLE_SON:
						//if(!map.containsKey(ksen.getCode())){
							//通过叔叔伯父找堂兄弟
							String grandfatherIdt = "";
							StringBuffer grandfathersbt = new StringBuffer();
							this.appendRyId(map, grandfathersbt, KinshipEnum.UNCLE);
							int grandfatherbt = grandfathersbt.lastIndexOf(",");
							if(grandfatherbt != -1){
								grandfatherIdt = grandfathersbt.substring(0, grandfatherbt);
								this.qsgxAddMap(map, grandfatherIdt,KinshipEnum.SON.getCode(), ksen,id);
							}
						//}
						break;
					case UNCLE_DAUGHTER:
						//if(!map.containsKey(ksen.getCode())){
							//通过叔叔伯父找堂兄弟
							String grandfatherIdu = "";
							StringBuffer grandfathersbu = new StringBuffer();
							this.appendRyId(map, grandfathersbu, KinshipEnum.UNCLE);
							int grandfatherbu = grandfathersbu.lastIndexOf(",");
							if(grandfatherbu != -1){
								grandfatherIdu = grandfathersbu.substring(0, grandfatherbu);
								this.qsgxAddMap(map, grandfatherIdu,KinshipEnum.DAUGHTER.getCode(), ksen,id);
							}
						//}
						break;
					case AUNT:
						//通过父亲、叔叔、伯父找姑妈
						String auntId = "";
						StringBuffer auntsb = new StringBuffer();
						this.appendRyId(map, auntsb, KinshipEnum.FATHER);
						this.appendRyId(map, auntsb, KinshipEnum.UNCLE);
						int auntb = auntsb.lastIndexOf(",");
						if(auntb != -1){
							auntId = auntsb.substring(0, auntb);
							this.qsgxAddMap(map, auntId,"'"+KinshipEnum.YOUNGER_SISTER.getCode()+"','"+KinshipEnum.ELDER_SISTER.getCode()+"'", ksen,id);
						}
						break;
					case AUNT_HUSBAND:
						if(!map.containsKey(ksen.getCode())){
							//通过姑妈找姑丈
							String grandfatherIdv = "";
							StringBuffer grandfathersbv = new StringBuffer();
							this.appendRyId(map, grandfathersbv, KinshipEnum.AUNT);
							int grandfatherbv = grandfathersbv.lastIndexOf(",");
							if(grandfatherbv != -1){
								grandfatherIdv = grandfathersbv.substring(0, grandfatherbv);
								this.qsgxAddMap(map, grandfatherIdv,KinshipEnum.HUSBAND.getCode(), ksen,id);
							}
						}
						break;
					case AUNT_SON:
						//if(!map.containsKey(ksen.getCode())){
							//通过姑妈找[姑妈表兄弟]
							String grandfatherIdw = "";
							StringBuffer grandfathersbw = new StringBuffer();
							this.appendRyId(map, grandfathersbw, KinshipEnum.AUNT);
							int grandfatherbw = grandfathersbw.lastIndexOf(",");
							if(grandfatherbw != -1){
								grandfatherIdw = grandfathersbw.substring(0, grandfatherbw);
								this.qsgxAddMap(map, grandfatherIdw,KinshipEnum.SON.getCode(), ksen,id);
							}
						//}
						break;
					case AUNT_DAUGHTER:
						//if(!map.containsKey(ksen.getCode())){
							//通过姑妈找[姑妈表姐妹]
							String grandfatherIdx = "";
							StringBuffer grandfathersbx = new StringBuffer();
							this.appendRyId(map, grandfathersbx, KinshipEnum.AUNT);
							int grandfatherbx = grandfathersbx.lastIndexOf(",");
							if(grandfatherbx != -1){
								grandfatherIdx = grandfathersbx.substring(0, grandfatherbx);
								this.qsgxAddMap(map, grandfatherIdx,KinshipEnum.DAUGHTER.getCode(), ksen,id);
							}
						//}
						break;
					case JIUJIU:
						//if(!map.containsKey(ksen.getCode())){
							//通过母亲、姨妈找舅舅
							String jiujiuId = "";
							StringBuffer jiujiusb = new StringBuffer();
							this.appendRyId(map, jiujiusb, KinshipEnum.MOTHER);
							this.appendRyId(map, jiujiusb, KinshipEnum.YIMA);
							int jiujiub = jiujiusb.lastIndexOf(",");
							if(jiujiub != -1){
								jiujiuId = jiujiusb.substring(0, jiujiub);
								this.qsgxAddMap(map, jiujiuId,"'"+KinshipEnum.YOUNGER_BROTHER.getCode()+"','"+KinshipEnum.ELDER_BROTHER.getCode()+"'", ksen,id);
							}
						//}
						//if(!map.containsKey(ksen.getCode())){
							//通过外祖父、外祖母找舅舅
							String jiujiuIda = "";
							StringBuffer jiujiusba = new StringBuffer();
							this.appendRyId(map, jiujiusba, KinshipEnum.OUTGRANDFATHER);
							this.appendRyId(map, jiujiusba, KinshipEnum.OUTGRANDMOTHER);
							int jiujiuba = jiujiusba.lastIndexOf(",");
							if(jiujiuba != -1){
								jiujiuIda = jiujiusba.substring(0, jiujiuba);
								this.qsgxAddMap(map, jiujiuIda,KinshipEnum.SON.getCode(), ksen,id);
							}
						//}
					
						break;
					case JIUJIU_WIFE:
						//if(!map.containsKey(ksen.getCode())){
							//通过舅舅找舅妈
							String grandfatherIdy = "";
							StringBuffer grandfathersby = new StringBuffer();
							this.appendRyId(map, grandfathersby, KinshipEnum.JIUJIU);
							int grandfatherby = grandfathersby.lastIndexOf(",");
							if(grandfatherby != -1){
								grandfatherIdy = grandfathersby.substring(0, grandfatherby);
								this.qsgxAddMap(map, grandfatherIdy,KinshipEnum.WIFE.getCode(), ksen,id);
							}
						//}
						break;
					case JIUJIU_SON:
						//if(!map.containsKey(ksen.getCode())){
							//通过舅舅[舅舅表兄弟]
							String grandfatherIdz = "";
							StringBuffer grandfathersbz = new StringBuffer();
							this.appendRyId(map, grandfathersbz, KinshipEnum.JIUJIU);
							int grandfatherbz = grandfathersbz.lastIndexOf(",");
							if(grandfatherbz != -1){
								grandfatherIdz = grandfathersbz.substring(0, grandfatherbz);
								this.qsgxAddMap(map, grandfatherIdz,KinshipEnum.SON.getCode(), ksen,id);
							}
						//}
						break;
					case JIUJIU_DAUGHTERN:
						//if(!map.containsKey(ksen.getCode())){
							//通过舅舅[舅舅表姐妹]
							String grandfatherIdaa = "";
							StringBuffer grandfathersbaa = new StringBuffer();
							this.appendRyId(map, grandfathersbaa, KinshipEnum.JIUJIU);
							int grandfatherbaa = grandfathersbaa.lastIndexOf(",");
							if(grandfatherbaa != -1){
								grandfatherIdaa = grandfathersbaa.substring(0, grandfatherbaa);
								this.qsgxAddMap(map, grandfatherIdaa,KinshipEnum.DAUGHTER.getCode(), ksen,id);
							}
						//}
						break;
					case YIMA:
						//if(!map.containsKey(ksen.getCode())){
							//通过母亲找姨妈
							String jiujiuIdb = "";
							StringBuffer jiujiusbb = new StringBuffer();
							this.appendRyId(map, jiujiusbb, KinshipEnum.MOTHER);
							int jiujiubb = jiujiusbb.lastIndexOf(",");
							if(jiujiubb != -1){
								jiujiuIdb = jiujiusbb.substring(0, jiujiubb);
								this.qsgxAddMap(map, jiujiuIdb,"'"+KinshipEnum.YOUNGER_SISTER.getCode()+"','"+KinshipEnum.ELDER_SISTER.getCode()+"'", ksen,id);
							}
						//}
						//if(!map.containsKey(ksen.getCode())){
							//通过外祖父、外祖母找姨妈
							String jiujiuIdc = "";
							StringBuffer jiujiusbc = new StringBuffer();
							this.appendRyId(map, jiujiusbc, KinshipEnum.OUTGRANDFATHER);
							this.appendRyId(map, jiujiusbc, KinshipEnum.OUTGRANDMOTHER);
							int jiujiubc = jiujiusbc.lastIndexOf(",");
							if(jiujiubc != -1){
								jiujiuIdc= jiujiusbc.substring(0, jiujiubc);
								this.qsgxAddMap(map, jiujiuIdc,KinshipEnum.DAUGHTER.getCode(), ksen,id);
							}
						//}
					
						break;
					case YIMA_HUSBAND:
						//if(!map.containsKey(ksen.getCode())){
							//通过姨妈找姨丈
							String grandfatherIdbb = "";
							StringBuffer grandfathersbbb = new StringBuffer();
							this.appendRyId(map, grandfathersbbb, KinshipEnum.YIMA);
							int grandfatherbbb = grandfathersbbb.lastIndexOf(",");
							if(grandfatherbbb != -1){
								grandfatherIdbb = grandfathersbbb.substring(0, grandfatherbbb);
								this.qsgxAddMap(map, grandfatherIdbb,KinshipEnum.HUSBAND.getCode(), ksen,id);
							}
						//}
						break;
					case YIMA_SON:
						//if(!map.containsKey(ksen.getCode())){
							//通过姨妈找[姨妈表兄弟]
							String grandfatherIdcc = "";
							StringBuffer grandfathersbcc = new StringBuffer();
							this.appendRyId(map, grandfathersbcc, KinshipEnum.YIMA);
							int grandfatherbcc = grandfathersbcc.lastIndexOf(",");
							if(grandfatherbcc != -1){
								grandfatherIdcc = grandfathersbcc.substring(0, grandfatherbcc);
								this.qsgxAddMap(map, grandfatherIdcc,KinshipEnum.SON.getCode(), ksen,id);
							}
						//}
						break;
					case YIMA_DAUGHTERN:
						//if(!map.containsKey(ksen.getCode())){
							//通过姨妈找【姨妈表姐妹】
							String grandfatherIddd = "";
							StringBuffer grandfathersbdd = new StringBuffer();
							this.appendRyId(map, grandfathersbdd, KinshipEnum.YIMA);
							int grandfatherbdd = grandfathersbdd.lastIndexOf(",");
							if(grandfatherbdd != -1){
								grandfatherIddd = grandfathersbdd.substring(0, grandfatherbdd);
								this.qsgxAddMap(map, grandfatherIddd,KinshipEnum.DAUGHTER.getCode(), ksen,id);
							}
						//}
						break;
						
					default:
						break;
					}
					
					
					
					
				//}
			}
		}
		String json = JSONHelper.map2json(map);
		//System.out.println(json);
		return json;
	}

	public void qsgxAddMap(Map<String, List<Map<String,String>>> map,String qsId,String qsgx,KinshipEnum ksen,String ry_id){
		List<Object[]> listMap = this.getQsGxByCode(qsId,qsgx);
		
		if(listMap !=null && listMap.size() > 0){
			List<Map<String,String>> listkin = new ArrayList<Map<String,String>>();
			//Object[] ob = listMap.get(0);
			// List<Map<String,String>> list = null;
			 if(map.containsKey(ksen.getCode())){
				 listkin = map.get(ksen.getCode());
			 }
			for(Object[] t :listMap){
				
				String id = String.valueOf(t[0].toString());
				if(!ry_id.equals(id)){
					Map<String,String> mapa = new HashMap<String, String>();				
					mapa.put("id", String.valueOf(t[0].toString()));
					mapa.put("name", String.valueOf(t[1].toString()));
					//如何集合中存在则不增加
					boolean flag = listkin.contains(mapa);
					if(!flag){
						listkin.add(mapa);
					}
					
				}
				
				
			}
			if(listkin != null && listkin.size() > 0){
				if(!map.containsKey(ksen.getCode())){
					map.put(ksen.getCode(), listkin);
				}
				
			}
			
		}
	}
	@Override
	public void appendRyId(Map<String, List<Map<String,String>>> map, StringBuffer sb,KinshipEnum enumkinship) {
		if(map.containsKey(enumkinship.getCode())){
			List<Map<String,String>> listId = map.get(enumkinship.getCode());
			if(listId != null && listId.size() >0){
				for(Map<String,String> t : listId){
					sb.append("'"+t.get("id")+"',");
				}	
			}
			
		}
		
	}

	@Override
	public List<Object[]> getQsGxByCode(String qsGxId, String qsgx) {
		//String sql = "SELECT QS_ID,NAME FROM SC_QSGX AS T WHERE T.RY_ID IN ("+qsGxId+") AND T.GXLX = "+qsgx;
		String sql = "SELECT DISTINCT QS_ID,NAME FROM SC_QSGX AS T WHERE T.RY_ID IN ("+qsGxId+") AND T.GXLX IN ("+qsgx+") and delflag='0'" ;
		List<Object[]> list=this.findListbySql(sql);
		//List<Map<String, Object>> list = this.findForJdbc(sql, qsGxId,qsgx);
		
		return list;
	}

	@Override
	public List<Map<String, Object>>  getGlcheck(String mainTable, String zTable,
			String wjIdName,String id) {
		String sql = "SELECT t.id from "+mainTable+" as t  INNER JOIN  "+zTable+"  as m ON  t.id = m."+wjIdName+" where t.id = ?  and m.delflag = '0' " ;
		List<Map<String, Object>> list = this.findForJdbc(sql, id);
		return list;
	}

	@Override
	public Map<String, KinshipEntity> getFlagAddKinship(String gxlx,
			String ry_id, String qs_id, String idnew) {
		Map<String,KinshipEntity> map = new HashMap<String, KinshipEntity>();
		KinshipEntity kinship = null;
		//boolean flag = false;
		String sql = "select id from sc_qsgx as t where t.gxlx = ? and t.ry_id = ? and t.qs_id = ? and id != ? and delflag='0'";
		List<Map<String, Object>> list = this.findForJdbc(sql, gxlx,ry_id,qs_id,idnew);
		if(list != null && list.size() > 0){
			String id =String.valueOf(list.get(0).get("id")) ;
			 kinship = this.getEntity(KinshipEntity.class, id);
			 //flag = true;			
		}
		map.put("flag", kinship);
		return map;
	}

	@Override
	public void getjtrksx(String jtId,List<Map<String, String>> sxlist) {
		//List<Map<String, String>> sxlist = new ArrayList<Map<String,String>>();
		String sql = "select * from sc_rkjbxxnew as t where t.ssjt_id = ? and t.delflag = '0'";
		List<Map<String, Object>> list =  this.findForJdbc(sql, jtId);
		if(list != null && list.size() > 0){
			for(int i = 0;i<list.size();i++){
				Map<String, Object> map = list.get(i);
				String id = String.valueOf(map.get("id"));
				String xm = String.valueOf(map.get("xm"));
				ScJzwPersonEnum[] scjzw = ScJzwPersonEnum.values();				
				for(ScJzwPersonEnum scjzwp : scjzw){
					boolean dyflag = false;//党员处理标识
					String type = scjzwp.getType();
					String filed = scjzwp.getFiled();
					String value = scjzwp.getValue();
					String cssName = scjzwp.getCssName();
					String code = scjzwp.getCode();
					String name = scjzwp.getName();
					String dbvalue =String.valueOf(map.get(filed)) ;
					
					if("1".equals(type)){
						
						if(value.equals(dbvalue)){
							Map<String, String> sxmap = new HashMap<String, String>();
							sxmap.put("id", id);
							sxmap.put("xm", xm);
							sxmap.put("cssname", cssName);
							sxmap.put("code", code);
							sxmap.put("name", name);
							sxlist.add(sxmap);
						}
					}else if("2".equals(type)){
						 
						if(ScJzwPersonEnum.JIANGUOQIANLAODANGYUAN.getCode().equals(code)){//建国前老党员处理
							dyflag = true;
							if(value.equals(dbvalue)){//首先必须是党员
								Map<String, String> sxmap = new HashMap<String, String>();
								//查询是否属于建国前老党员
								String dysql = "select * from sc_dyinfo as t where t.rk_id = ? and t.jointime <= '1949-10-01 00:00:00' and t.delflag = '0'";
								List<Map<String, Object>> dylist =  this.findForJdbc(dysql, id);
								if(dylist != null && dylist.size() > 0){//属于建国前老党员
									sxmap.put("id", id);
									sxmap.put("xm", xm);
									sxmap.put("cssname", cssName);
									sxmap.put("code", code);
									sxmap.put("name", name);
								}else{//不属于建国前老党员 但是属于党员
									sxmap.put("id", id);
									sxmap.put("xm", xm);
									sxmap.put("cssname", ScJzwPersonEnum.DANGYUAN.getCssName());
									sxmap.put("code", ScJzwPersonEnum.DANGYUAN.getCode());
									sxmap.put("name", ScJzwPersonEnum.DANGYUAN.getName());
								}
								sxlist.add(sxmap);
							}
						}else if(ScJzwPersonEnum.KONGCHAOLAOREN.getCode().equals(code)){
							if(value.equals(dbvalue)){//首先必须属于老年人
								Map<String, String> sxmap = new HashMap<String, String>();
								//查询老年人表的居住情况
								String lnrsql = "select * from sc_agedinfo as t where t.rk_id = ? and t.delflag = '0'";
								List<Map<String, Object>> lnrlist =  this.findForJdbc(lnrsql, id);
								if(lnrlist != null && lnrlist.size() > 0){
									String inhaitinfo = String.valueOf(lnrlist.get(0).get("inhaitinfo"));
									if("kclnr".equals(inhaitinfo)){//属于空巢老年人
										sxmap.put("id", id);
										sxmap.put("xm", xm);
										sxmap.put("cssname", cssName);
										sxmap.put("code", code);
										sxmap.put("name", name);
									}else if("djlnren".equals(inhaitinfo)){//属于独居老年人
										sxmap.put("id", id);
										sxmap.put("xm", xm);
										sxmap.put("cssname", ScJzwPersonEnum.DUJUNLAOREN.getCssName());
										sxmap.put("code", ScJzwPersonEnum.DUJUNLAOREN.getCode());
										sxmap.put("name", ScJzwPersonEnum.DUJUNLAOREN.getName());
									}
									
								}else{//属于老年人
									sxmap.put("id", id);
									sxmap.put("xm", xm);
									sxmap.put("cssname", ScJzwPersonEnum.LAONIANREN.getCssName());
									sxmap.put("code", ScJzwPersonEnum.LAONIANREN.getCode());
									sxmap.put("name", ScJzwPersonEnum.LAONIANREN.getName());
								}
								sxlist.add(sxmap);
							}
						}
					}
					
				}
			}
		}
	}

	@Override
	public void getPersonRecord(String rk_id,List<PersonRecord> listpr) {
		String sql = "SELECT k.bz as llxx,k.rktime,k.businesscode,k.rkxx_field,t.business_id from sc_rk_busines as t INNER JOIN"
				+ " sc_rk_yw_config as k  ON k.businesscode = t.businesscode  where k.rktime is NOT NULL and t.rkxx_id = ?";
		List<Map<String, Object>> list = this.findForJdbc(sql, rk_id);
		
		String sqlpr = "select n.xm,n.create_date,n.sfsw,n.csrq  from sc_rkjbxxnew as n where n.id = ?";
		List<Map<String, Object>> listrk = this.findForJdbc(sqlpr, rk_id);
		if(listrk != null && listrk.size() > 0){	
			Map<String, Object> mappr = listrk.get(0);			
			String csrq = String.valueOf(mappr.get("csrq"));
			String create_date = String.valueOf(mappr.get("create_date"));		
			String sfsw = String.valueOf(mappr.get("sfsw"));
			//出生日期组装数据
			if(StringUtils.isNotBlank(csrq) && !"null".equals(csrq)){
				String recordNamecsrq = "出生";
				PersonRecord prcsrq = new PersonRecord();
				prcsrq.setRecordName(recordNamecsrq);
				this.dealTime(csrq, prcsrq);
				listpr.add(prcsrq);
			}
			//社区人员档案建立日期组装数据
			if(StringUtils.isNotBlank(create_date) && !"null".equals(create_date)){
				String recordNamecr = "社区人员档案建立";
				PersonRecord prcscr = new PersonRecord();
				prcscr.setRecordName(recordNamecr);
				this.dealTime(create_date, prcscr);
				listpr.add(prcscr);
			}	
			//社区人员死亡日期数据组装
			if(StringUtils.isNotBlank(sfsw) && !"null".equals(sfsw)){
				String recordNamesw = "死亡";
				PersonRecord prcssw = new PersonRecord();
				prcssw.setRecordName(recordNamesw);
				this.dealTime(sfsw, prcssw);
				listpr.add(prcssw);
			}	
			
		}		
		if(list != null && list.size() > 0){	
			for(Map<String, Object> map : list){
				PersonRecord pr = new PersonRecord();
				String llxx = String.valueOf(map.get("llxx"));
				String rktime = String.valueOf(map.get("rktime"));
				String businesscode = String.valueOf(map.get("businesscode"));
				String business_id = String.valueOf(map.get("business_id"));
				String rktimeValue = this.getRkTime(rktime, businesscode, business_id);
				pr.setRecordName(llxx);
				this.dealTime(rktimeValue, pr);
				listpr.add(pr);
			}
		}
		//对list根据日期排序
		Collections.sort(listpr, new SortByTime());		
	}

	/**
	 * 获取业务时间
	 * @param field
	 * @param tableName
	 * @param bizId
	 * @return
	 */
	private String getRkTime (String field,String tableName,String bizId){
		String rktime = "";
		String sql = "select "+field+" from "+tableName+" where id = ?";
		try {
			List<Map<String, Object>> list = this.findForJdbc(sql, bizId);
			if(list != null && list.size() > 0){
				Map<String, Object> map = list.get(0);
				rktime = String.valueOf(map.get(field));
			
			}
		} catch (Exception e) {
			
		}
		
		return rktime;	
	}
	/**
	 * 业务时间处理
	 * @param time
	 * @param pr
	 */
    private void dealTime(String time,PersonRecord pr){
    	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
    	if(StringUtils.isNotBlank(time)){
    		try {
				Date  date = sd.parse(time);
				String fortime = sd.format(date);
				
				Calendar c1 = Calendar.getInstance();
				c1.setTime(date);
				int year = c1.get(Calendar.YEAR);
				
				int month = c1.get(Calendar.MONTH)+1;
				
				int day = c1.get(Calendar.DATE);
				pr.setCreateTime(fortime);
				pr.setYear(year);
				pr.setMonth(month);
				pr.setDay(day);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    }

	@Override
	public String getSparkQsgx(int ry_graphid) {
		String qsgxjson = "{}";
		Socket client = null;
		
		try {
		
		client  =  new Socket(sparkIp,Integer.parseInt(sparkPort));//指定连接主机及端口
	
		BufferedReader buf = null;//接收服务端传过来的信息
		PrintStream out = null;//输出流，向服务端发送信息
		//BufferedReader input = null;//
		//input = new BufferedReader(new InputStreamReader(System.in));//从键盘接收信息
		out  = new PrintStream(client.getOutputStream());//向服务端发送数据
		buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));
		
			StringBuffer sbquery = new StringBuffer();
			sbquery.append("{");
			sbquery.append(" \"opt\":\"queryVE\", ");
			sbquery.append(" \"ry_graphid\":\""+ry_graphid+"\"} ");
			
			out.println(sbquery);
			qsgxjson = buf.readLine();
			//System.out.println(qsgxjson);
			buf.close();
			out.flush();
			out.close();
			client.close();
		} catch (Exception e) {
			
		}
		
		
	return qsgxjson;
	}

	@Override
	public String addSparkQsgx(int ry_graphid, String ry_Id, int qs_graphid,
			String qs_Id, String qsgx, String ry_xb, String qs_xb) {
		String qsgxjson = "{}";
		Socket client = null;
		
		try {
		
		client  =  new Socket(sparkIp,Integer.parseInt(sparkPort));//指定连接主机及端口
	
		BufferedReader buf = null;//接收服务端传过来的信息
		PrintStream out = null;//输出流，向服务端发送信息
		//BufferedReader input = null;//
		//input = new BufferedReader(new InputStreamReader(System.in));//从键盘接收信息
		out  = new PrintStream(client.getOutputStream());//向服务端发送数据
		buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));
		
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append(" \"opt\":\"addVE\", ");
			sb.append(" \"ry_graphid\":\""+ry_graphid+"\", ");
			sb.append(" \"ry_Id\":\""+ry_Id+"\", ");
			sb.append(" \"ry_xb\":\""+ry_xb+"\", ");
			sb.append(" \"qs_graphid\":\""+qs_graphid+"\", ");
			sb.append(" \"qs_Id\":\""+qs_Id+"\", ");
			sb.append(" \"qs_xb\":\""+qs_xb+"\", ");
			sb.append(" \"qsgx\":\""+qsgx+"\" } ");
			
			out.println(sb);
			qsgxjson = buf.readLine();
			//System.out.println(qsgxjson);
			buf.close();
			out.flush();
			out.close();
			client.close();
		} catch (Exception e) {
			
		}
		
		return qsgxjson;
	}

	@Override
	public String deleteSparkQsgx(int ry_graphid, String ry_Id, int qs_graphid,
			String qs_Id, String qsgx) {
		
		String qsgxjson = "{}";
		Socket client = null;
		
		try {
		
		client  =  new Socket(sparkIp,Integer.parseInt(sparkPort));//指定连接主机及端口
	
		BufferedReader buf = null;//接收服务端传过来的信息
		PrintStream out = null;//输出流，向服务端发送信息
		//BufferedReader input = null;//
		//input = new BufferedReader(new InputStreamReader(System.in));//从键盘接收信息
		out  = new PrintStream(client.getOutputStream());//向服务端发送数据
		buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));
		
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append(" \"opt\":\"addVE\", ");
			sb.append(" \"ry_graphid\":\""+ry_graphid+"\", ");
			sb.append(" \"ry_Id\":\""+ry_Id+"\", ");		
			sb.append(" \"qs_graphid\":\""+qs_graphid+"\", ");
			sb.append(" \"qs_Id\":\""+qs_Id+"\", ");		
			sb.append(" \"qsgx\":\""+qsgx+"\" } ");
			
			out.println(sb);
			qsgxjson = buf.readLine();
			//System.out.println(qsgxjson);
			buf.close();
			out.flush();
			out.close();
			client.close();
		} catch (Exception e) {
			
		}
		
		return qsgxjson;
	}
}