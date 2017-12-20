package com.chrhc.project.sc.jcql.entity;

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
 * @Description: 基层侨联活动记录
 * @author onlineGenerator
 * @date 2015-10-23 11:17:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_jcqlhdjl", schema = "")
@SuppressWarnings("serial")
public class ScJcqlhdjlEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**小组名称*/
	@Excel(name="小组名称")
	private java.lang.String teamName1;
	/**活动时间*/
	@Excel(name="活动时间")
	private java.util.Date actionTime;
	/**活动地点*/
	@Excel(name="活动地点")
	private java.lang.String actionPlace;
	/**参与人数(人)*/
	@Excel(name="参与人数(人)")
	private java.lang.Integer participateNum;
	/**版本号*/
	private java.lang.Integer versionNum;
	/**部门名称*/
	private java.lang.String sysOrgCode;
	/**活动形式*/
	@Excel(name="活动形式")
	private java.lang.String activeType;
	/**活动主题*/
	@Excel(name="活动主题")
	private java.lang.String activeTitle;
	/**活动内容*/
	@Excel(name="活动内容")
	private java.lang.String activeContent;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**创建日期*/
	private java.lang.String createDate;
	/**更新日期*/
	private java.lang.String updateDate;
	/**备注信息*/
	@Excel(name="备注信息")
	private java.lang.String remark;
	/**删除标志*/
	private java.lang.String delflag;
	/**基层侨联id*/
	private java.lang.String partId;
	
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
	 *@return: java.lang.String  小组名称
	 */
	@Column(name ="TEAM_NAME1",nullable=false,length=32)
	public java.lang.String getTeamName1(){
		return this.teamName1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  小组名称
	 */
	public void setTeamName1(java.lang.String teamName1){
		this.teamName1 = teamName1;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  活动时间
	 */
	@Column(name ="ACTION_TIME",nullable=false,length=32)
	public java.util.Date getActionTime(){
		return this.actionTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  活动时间
	 */
	public void setActionTime(java.util.Date actionTime){
		this.actionTime = actionTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动地点
	 */
	@Column(name ="ACTION_PLACE",nullable=false,length=32)
	public java.lang.String getActionPlace(){
		return this.actionPlace;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动地点
	 */
	public void setActionPlace(java.lang.String actionPlace){
		this.actionPlace = actionPlace;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  参与人数(人)
	 */
	@Column(name ="PARTICIPATE_NUM",nullable=true,length=32)
	public java.lang.Integer getParticipateNum(){
		return this.participateNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  参与人数(人)
	 */
	public void setParticipateNum(java.lang.Integer participateNum){
		this.participateNum = participateNum;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动形式
	 */
	@Column(name ="ACTIVE_TYPE",nullable=false,length=32)
	public java.lang.String getActiveType(){
		return this.activeType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动形式
	 */
	public void setActiveType(java.lang.String activeType){
		this.activeType = activeType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动主题
	 */
	@Column(name ="ACTIVE_TITLE",nullable=false,length=32)
	public java.lang.String getActiveTitle(){
		return this.activeTitle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动主题
	 */
	public void setActiveTitle(java.lang.String activeTitle){
		this.activeTitle = activeTitle;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动内容
	 */
	@Column(name ="ACTIVE_CONTENT",nullable=false,length=32)
	public java.lang.String getActiveContent(){
		return this.activeContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动内容
	 */
	public void setActiveContent(java.lang.String activeContent){
		this.activeContent = activeContent;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=50)
	public java.lang.String getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建日期
	 */
	public void setCreateDate(java.lang.String createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true,length=50)
	public java.lang.String getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新日期
	 */
	public void setUpdateDate(java.lang.String updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注信息
	 */
	@Column(name ="REMARK",nullable=true,length=200)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注信息
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基层侨联id
	 */
	@Column(name ="PART_ID",nullable=true,length=50)
	public java.lang.String getPartId(){
		return this.partId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基层侨联id
	 */
	public void setPartId(java.lang.String partId){
		this.partId = partId;
	}
}
