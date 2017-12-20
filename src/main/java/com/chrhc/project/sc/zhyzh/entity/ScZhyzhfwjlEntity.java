package com.chrhc.project.sc.zhyzh.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 志愿服务记录
 * @author onlineGenerator
 * @date 2015-09-23 10:47:15
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_zhyzhfwjl", schema = "")
@SuppressWarnings("serial")
public class ScZhyzhfwjlEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**版本号*/
	private java.lang.Integer versionNum;
	/**部门名称*/
	private java.lang.String sysOrgCode;
	/**更新日期*/
	private java.util.Date updateDate;
	/**服务主体类型*/
	@Excel(name="服务主体类型")
	private java.lang.String servObjtype;
	/**服务队*/
	private java.lang.String isTeam;
	/**服务时间*/
	@Excel(name="服务时间")
	private java.util.Date servDatetime;
	/**服务队名称*/
	@Excel(name="服务队名称")
	private java.lang.String partName;
	/**服务队ID*/
	private java.lang.String partId;
	/**服务者名称*/
	@Excel(name="服务者名称")
	private java.lang.String memname;
	/**服务者ID*/
	private java.lang.String memId;
	/**队员人数(人)*/
	@Excel(name="队员人数(人)")
	private java.lang.Integer num;
	/**工时(小时)*/
	@Excel(name="工时(小时)")
	private java.lang.Double hours;
	/**服务地点*/
	@Excel(name="服务地点")
	private java.lang.String servAddr;
	/**服务内容*/
	@Excel(name="服务内容")
	private java.lang.String servContent;
	/**确认人/部门*/
	@Excel(name="确认人/部门")
	private java.lang.String confBody;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String remark;
	/**地理信息*/
	@Excel(name="地理信息")
	private java.lang.String gisxy;
	/**删除标志*/
	private java.lang.String delflag;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本号
	 */
	@Column(name ="VERSION_NUM",nullable=true,length=16)
	public java.lang.Integer getVersionNum(){
		return this.versionNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  版本号
	 */
	public void setVersionNum(java.lang.Integer versionNum){
		this.versionNum = versionNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门名称
	 */
	@Column(name ="SYS_ORG_CODE",nullable=true,length=50)
	public java.lang.String getSysOrgCode(){
		return this.sysOrgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门名称
	 */
	public void setSysOrgCode(java.lang.String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  服务主体类型
	 */
	@Column(name ="SERV_OBJTYPE",nullable=true,length=20)
	public java.lang.String getServObjtype(){
		return this.servObjtype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  服务主体类型
	 */
	public void setServObjtype(java.lang.String servObjtype){
		this.servObjtype = servObjtype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  服务队
	 */
	@Column(name ="IS_TEAM",nullable=true,length=10)
	public java.lang.String getIsTeam(){
		return this.isTeam;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  服务队
	 */
	public void setIsTeam(java.lang.String isTeam){
		this.isTeam = isTeam;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  服务时间
	 */
	@Column(name ="SERV_DATETIME",nullable=false,length=20)
	public java.util.Date getServDatetime(){
		return this.servDatetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  服务时间
	 */
	public void setServDatetime(java.util.Date servDatetime){
		this.servDatetime = servDatetime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  服务队名称
	 */
	@Column(name ="PART_NAME",nullable=true,length=50)
	public java.lang.String getPartName(){
		return this.partName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  服务队名称
	 */
	public void setPartName(java.lang.String partName){
		this.partName = partName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  服务队ID
	 */
	@Column(name ="PART_ID",nullable=true,length=36)
	public java.lang.String getPartId(){
		return this.partId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  服务队ID
	 */
	public void setPartId(java.lang.String partId){
		this.partId = partId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  服务者名称
	 */
	@Column(name ="MEMNAME",nullable=true,length=50)
	public java.lang.String getMemname(){
		return this.memname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  服务者名称
	 */
	public void setMemname(java.lang.String memname){
		this.memname = memname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  服务者ID
	 */
	@Column(name ="MEM_ID",nullable=true,length=36)
	public java.lang.String getMemId(){
		return this.memId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  服务者ID
	 */
	public void setMemId(java.lang.String memId){
		this.memId = memId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  队员人数(人)
	 */
	@Column(name ="NUM",nullable=true,length=9)
	public java.lang.Integer getNum(){
		return this.num;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  队员人数(人)
	 */
	public void setNum(java.lang.Integer num){
		this.num = num;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  工时(小时)
	 */
	@Column(name ="HOURS",nullable=false,scale=4,length=14)
	public java.lang.Double getHours(){
		return this.hours;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  工时(小时)
	 */
	public void setHours(java.lang.Double hours){
		this.hours = hours;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  服务地点
	 */
	@Column(name ="SERV_ADDR",nullable=false,length=200)
	public java.lang.String getServAddr(){
		return this.servAddr;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  服务地点
	 */
	public void setServAddr(java.lang.String servAddr){
		this.servAddr = servAddr;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  服务内容
	 */
	@Column(name ="SERV_CONTENT",nullable=false,length=300)
	public java.lang.String getServContent(){
		return this.servContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  服务内容
	 */
	public void setServContent(java.lang.String servContent){
		this.servContent = servContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  确认人/部门
	 */
	@Column(name ="CONF_BODY",nullable=true,length=50)
	public java.lang.String getConfBody(){
		return this.confBody;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  确认人/部门
	 */
	public void setConfBody(java.lang.String confBody){
		this.confBody = confBody;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=500)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地理信息
	 */
	@Column(name ="GISXY",nullable=true,length=200)
	public java.lang.String getGisxy(){
		return this.gisxy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地理信息
	 */
	public void setGisxy(java.lang.String gisxy){
		this.gisxy = gisxy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  删除标志
	 */
	@Column(name ="DELFLAG",nullable=true,length=1)
	public java.lang.String getDelflag(){
		return this.delflag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  删除标志
	 */
	public void setDelflag(java.lang.String delflag){
		this.delflag = delflag;
	}
}
