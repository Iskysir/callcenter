package com.chrhc.project.hl.visit.entity;

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
 * @Description: 回访满意度
 * @author onlineGenerator
 * @date 2016-07-19 21:06:46
 * @version V1.0   
 *
 */
@Entity
@Table(name = "hl_satisfied_degree", schema = "")
@SuppressWarnings("serial")
public class HlSatisfiedDegreeEntity implements java.io.Serializable {
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
	/**更新日期*/
	private java.util.Date updateDate;
	/**版本号*/
	private java.lang.Integer versionNum;
	/**组织编号*/
	private java.lang.String sysOrgCode;
	/**删除标志*/
	private java.lang.String delflag;
	/**满意度*/
	@Excel(name="满意度")
	private java.lang.String degeree;
	/**回访状态*/
	@Excel(name="回访状态")
	private java.lang.String stState;
	/**有效工作量*/
	@Excel(name="有效工作量")
	private java.lang.String yxgzl;
	/**无效工作量*/
	@Excel(name="无效工作量")
	private java.lang.String wxgzl;
	/**失效工作量*/
	@Excel(name="失效工作量")
	private java.lang.String sxgzl;
	/**回访人*/
	@Excel(name="回访人")
	private java.lang.String visitPerson;
	/**回访时间*/
	@Excel(name="回访时间")
	private java.util.Date visitTime;
	/**客户录入*/
	@Excel(name="客户录入")
	private java.lang.String visitInput;
	/**录入时间*/
	@Excel(name="录入时间")
	private java.util.Date inputTime;
	/**业务类型*/
	private java.lang.String busType;
	/**业务主键*/
	private java.lang.String busId;
	/**预留字段a*/
	private java.lang.String bza;
	/**预留字段b*/
	private java.lang.String bzb;
	/**回访意见*/
	@Excel(name="回访意见")
	private java.lang.String visitOption;
	
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本号
	 */
	@Column(name ="VERSION_NUM",nullable=true,length=1)
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
	 *@return: java.lang.String  组织编号
	 */
	@Column(name ="SYS_ORG_CODE",nullable=true,length=50)
	public java.lang.String getSysOrgCode(){
		return this.sysOrgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组织编号
	 */
	public void setSysOrgCode(java.lang.String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
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
	 *@return: java.lang.String  满意度
	 */
	@Column(name ="DEGEREE",nullable=true,length=50)
	public java.lang.String getDegeree(){
		return this.degeree;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  满意度
	 */
	public void setDegeree(java.lang.String degeree){
		this.degeree = degeree;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  回访状态
	 */
	@Column(name ="ST_STATE",nullable=true,length=50)
	public java.lang.String getStState(){
		return this.stState;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  回访状态
	 */
	public void setStState(java.lang.String stState){
		this.stState = stState;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  有效工作量
	 */
	@Column(name ="YXGZL",nullable=true,length=50)
	public java.lang.String getYxgzl(){
		return this.yxgzl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  有效工作量
	 */
	public void setYxgzl(java.lang.String yxgzl){
		this.yxgzl = yxgzl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  无效工作量
	 */
	@Column(name ="WXGZL",nullable=true,length=50)
	public java.lang.String getWxgzl(){
		return this.wxgzl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  无效工作量
	 */
	public void setWxgzl(java.lang.String wxgzl){
		this.wxgzl = wxgzl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  失效工作量
	 */
	@Column(name ="SXGZL",nullable=true,length=50)
	public java.lang.String getSxgzl(){
		return this.sxgzl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  失效工作量
	 */
	public void setSxgzl(java.lang.String sxgzl){
		this.sxgzl = sxgzl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  回访人
	 */
	@Column(name ="VISIT_PERSON",nullable=true,length=50)
	public java.lang.String getVisitPerson(){
		return this.visitPerson;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  回访人
	 */
	public void setVisitPerson(java.lang.String visitPerson){
		this.visitPerson = visitPerson;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  回访时间
	 */
	@Column(name ="VISIT_TIME",nullable=true,length=50)
	public java.util.Date getVisitTime(){
		return this.visitTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  回访时间
	 */
	public void setVisitTime(java.util.Date visitTime){
		this.visitTime = visitTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户录入
	 */
	@Column(name ="VISIT_INPUT",nullable=true,length=50)
	public java.lang.String getVisitInput(){
		return this.visitInput;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户录入
	 */
	public void setVisitInput(java.lang.String visitInput){
		this.visitInput = visitInput;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  录入时间
	 */
	@Column(name ="INPUT_TIME",nullable=true,length=50)
	public java.util.Date getInputTime(){
		return this.inputTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  录入时间
	 */
	public void setInputTime(java.util.Date inputTime){
		this.inputTime = inputTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务类型
	 */
	@Column(name ="BUS_TYPE",nullable=true,length=50)
	public java.lang.String getBusType(){
		return this.busType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务类型
	 */
	public void setBusType(java.lang.String busType){
		this.busType = busType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务主键
	 */
	@Column(name ="BUS_ID",nullable=true,length=50)
	public java.lang.String getBusId(){
		return this.busId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务主键
	 */
	public void setBusId(java.lang.String busId){
		this.busId = busId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预留字段a
	 */
	@Column(name ="BZA",nullable=true,length=200)
	public java.lang.String getBza(){
		return this.bza;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预留字段a
	 */
	public void setBza(java.lang.String bza){
		this.bza = bza;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预留字段b
	 */
	@Column(name ="BZB",nullable=true,length=200)
	public java.lang.String getBzb(){
		return this.bzb;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预留字段b
	 */
	public void setBzb(java.lang.String bzb){
		this.bzb = bzb;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  回访意见
	 */
	@Column(name ="VISIT_OPTION",nullable=true,length=2000)
	public java.lang.String getVisitOption(){
		return this.visitOption;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  回访意见
	 */
	public void setVisitOption(java.lang.String visitOption){
		this.visitOption = visitOption;
	}
}
