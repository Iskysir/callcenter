package com.chrhc.project.sc.scwt.entity;

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
 * @Description: 文体队伍
 * @author onlineGenerator
 * @date 2015-08-25 13:12:11
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_wtdw", schema = "")
@SuppressWarnings("serial")
public class ScWtdwEntity implements java.io.Serializable {
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
	/**单位名称*/
	@Excel(name="单位名称")
	private java.lang.String unitName;
	/**队伍名称*/
	@Excel(name="队伍名称")
	private java.lang.String teamName;
	/**负责人姓名*/
	@Excel(name="负责人姓名")
	private java.lang.String dutyName;
	/**联系电话*/
	@Excel(name="联系电话")
	private java.lang.String contactTele;
	/**成立时间*/
	@Excel(name="成立时间")
	private java.util.Date foundDate;
	/**队伍类型*/
	@Excel(name="队伍类型")
	private java.lang.String teamType;
	/**评定等级*/
	@Excel(name="评定等级")
	private java.lang.String level;
	/**活动地点*/
	@Excel(name="活动地点")
	private java.lang.String actAddress;
	/**主要活动*/
	@Excel(name="主要活动")
	private java.lang.String mainAct;
	/**业务范围*/
	@Excel(name="业务范围")
	private java.lang.String busiScope;
	/**活动面积(㎡)*/
	@Excel(name="活动面积(㎡)")
	private java.lang.Double actArea;
	/**设备总值(万元)*/
	@Excel(name="设备总值(万元)")
	private java.lang.Double equipSum;
	/**活动项目*/
	@Excel(name="活动项目")
	private java.lang.String actProject;
	/**人数(人)*/
	@Excel(name="人数(人)")
	private java.lang.Integer num;
	/**删除标志*/
	private java.lang.String delflag;
	/**支数(支)*/
	@Excel(name="支数(支)")
	private java.lang.Integer subNum;
	/**队伍介绍*/
	@Excel(name="队伍介绍")
	private java.lang.String teamIntro;
	/**住址*/
	@Excel(name="住址")
	private java.lang.String address;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String remark;
	/**地理信息*/
	private java.lang.String gisxy;
	
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
	 *@return: java.lang.String  单位名称
	 */
	@Column(name ="UNIT_NAME",nullable=false,length=50)
	public java.lang.String getUnitName(){
		return this.unitName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位名称
	 */
	public void setUnitName(java.lang.String unitName){
		this.unitName = unitName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  队伍名称
	 */
	@Column(name ="TEAM_NAME",nullable=false,length=50)
	public java.lang.String getTeamName(){
		return this.teamName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  队伍名称
	 */
	public void setTeamName(java.lang.String teamName){
		this.teamName = teamName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  负责人姓名
	 */
	@Column(name ="DUTY_NAME",nullable=false,length=50)
	public java.lang.String getDutyName(){
		return this.dutyName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  负责人姓名
	 */
	public void setDutyName(java.lang.String dutyName){
		this.dutyName = dutyName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系电话
	 */
	@Column(name ="CONTACT_TELE",nullable=false,length=20)
	public java.lang.String getContactTele(){
		return this.contactTele;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系电话
	 */
	public void setContactTele(java.lang.String contactTele){
		this.contactTele = contactTele;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  成立时间
	 */
	@Column(name ="FOUND_DATE",nullable=false,length=20)
	public java.util.Date getFoundDate(){
		return this.foundDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  成立时间
	 */
	public void setFoundDate(java.util.Date foundDate){
		this.foundDate = foundDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  队伍类型
	 */
	@Column(name ="TEAM_TYPE",nullable=true,length=10)
	public java.lang.String getTeamType(){
		return this.teamType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  队伍类型
	 */
	public void setTeamType(java.lang.String teamType){
		this.teamType = teamType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  评定等级
	 */
	@Column(name ="LEVEL",nullable=true,length=10)
	public java.lang.String getLevel(){
		return this.level;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  评定等级
	 */
	public void setLevel(java.lang.String level){
		this.level = level;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动地点
	 */
	@Column(name ="ACT_ADDRESS",nullable=true,length=100)
	public java.lang.String getActAddress(){
		return this.actAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动地点
	 */
	public void setActAddress(java.lang.String actAddress){
		this.actAddress = actAddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主要活动
	 */
	@Column(name ="MAIN_ACT",nullable=true,length=300)
	public java.lang.String getMainAct(){
		return this.mainAct;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主要活动
	 */
	public void setMainAct(java.lang.String mainAct){
		this.mainAct = mainAct;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务范围
	 */
	@Column(name ="BUSI_SCOPE",nullable=true,length=200)
	public java.lang.String getBusiScope(){
		return this.busiScope;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务范围
	 */
	public void setBusiScope(java.lang.String busiScope){
		this.busiScope = busiScope;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  活动面积(㎡)
	 */
	@Column(name ="ACT_AREA",nullable=true,scale=4,length=14)
	public java.lang.Double getActArea(){
		return this.actArea;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  活动面积(㎡)
	 */
	public void setActArea(java.lang.Double actArea){
		this.actArea = actArea;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  设备总值(万元)
	 */
	@Column(name ="EQUIP_SUM",nullable=true,scale=4,length=14)
	public java.lang.Double getEquipSum(){
		return this.equipSum;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  设备总值(万元)
	 */
	public void setEquipSum(java.lang.Double equipSum){
		this.equipSum = equipSum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动项目
	 */
	@Column(name ="ACT_PROJECT",nullable=true,length=300)
	public java.lang.String getActProject(){
		return this.actProject;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动项目
	 */
	public void setActProject(java.lang.String actProject){
		this.actProject = actProject;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  人数(人)
	 */
	@Column(name ="NUM",nullable=true,length=9)
	public java.lang.Integer getNum(){
		return this.num;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  人数(人)
	 */
	public void setNum(java.lang.Integer num){
		this.num = num;
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
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  支数(支)
	 */
	@Column(name ="SUB_NUM",nullable=true,length=9)
	public java.lang.Integer getSubNum(){
		return this.subNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  支数(支)
	 */
	public void setSubNum(java.lang.Integer subNum){
		this.subNum = subNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  队伍介绍
	 */
	@Column(name ="TEAM_INTRO",nullable=true,length=300)
	public java.lang.String getTeamIntro(){
		return this.teamIntro;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  队伍介绍
	 */
	public void setTeamIntro(java.lang.String teamIntro){
		this.teamIntro = teamIntro;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  住址
	 */
	@Column(name ="ADDRESS",nullable=true,length=100)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  住址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
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
}
