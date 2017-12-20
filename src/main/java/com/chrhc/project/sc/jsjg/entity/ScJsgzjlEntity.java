package com.chrhc.project.sc.jsjg.entity;

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
 * @Description: 计生工作记录
 * @author onlineGenerator
 * @date 2015-10-21 14:47:57
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_jsgzjl", schema = "")
@SuppressWarnings("serial")
public class ScJsgzjlEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**计生机构*/
	@Excel(name="计生机构")
	private java.lang.String jsjg;
	/**时间*/
	@Excel(name="时间")
	private java.util.Date sj;
	/**工作主题*/
	@Excel(name="工作主题")
	private java.lang.String gzzt;
	/**工作内容*/
	@Excel(name="工作内容")
	private java.lang.String gznr;
	/**版本号*/
	private java.lang.Integer versionNum;
	/**部门名称*/
	private java.lang.String sysOrgCode;
	/**备注信息*/
	@Excel(name="备注信息")
	private java.lang.String bzxx;
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
	/**删除标志*/
	private java.lang.String delflag;
	/**计生机构id*/
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
	 *@return: java.lang.String  计生机构
	 */
	@Column(name ="JSJG",nullable=false,length=32)
	public java.lang.String getJsjg(){
		return this.jsjg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计生机构
	 */
	public void setJsjg(java.lang.String jsjg){
		this.jsjg = jsjg;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  时间
	 */
	@Column(name ="SJ",nullable=false,length=32)
	public java.util.Date getSj(){
		return this.sj;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  时间
	 */
	public void setSj(java.util.Date sj){
		this.sj = sj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  工作主题
	 */
	@Column(name ="GZZT",nullable=false,length=32)
	public java.lang.String getGzzt(){
		return this.gzzt;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  工作主题
	 */
	public void setGzzt(java.lang.String gzzt){
		this.gzzt = gzzt;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  工作内容
	 */
	@Column(name ="GZNR",nullable=false,length=32)
	public java.lang.String getGznr(){
		return this.gznr;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  工作内容
	 */
	public void setGznr(java.lang.String gznr){
		this.gznr = gznr;
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
	 *@return: java.lang.String  备注信息
	 */
	@Column(name ="BZXX",nullable=true,length=200)
	public java.lang.String getBzxx(){
		return this.bzxx;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注信息
	 */
	public void setBzxx(java.lang.String bzxx){
		this.bzxx = bzxx;
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
	 *@return: java.lang.String  计生机构id
	 */
	@Column(name ="PART_ID",nullable=true,length=50)
	public java.lang.String getPartId(){
		return this.partId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计生机构id
	 */
	public void setPartId(java.lang.String partId){
		this.partId = partId;
	}
}
