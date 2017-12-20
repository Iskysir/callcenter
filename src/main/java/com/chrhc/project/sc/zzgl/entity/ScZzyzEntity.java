package com.chrhc.project.sc.zzgl.entity;

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
 * @Description: 证照验真
 * @author onlineGenerator
 * @date 2015-06-11 14:23:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_zzyz", schema = "")
@SuppressWarnings("serial")
public class ScZzyzEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**证照编号*/
	@Excel(name="证照编号", width=30 )
	private java.lang.String zzbh;
	/**验证结果*/
	@Excel(name="验证结果", width=30)
	private java.lang.String yzjg;
	/**验证时间*/
	private java.util.Date yzsj;
	/**删除标记*/
	private java.lang.String scbj;
	/**版本号*/
	private java.lang.Integer versionNum;
	/**部门名称*/
	private java.lang.String sysOrgCode;
	/**备注*/
	@Excel(name="备注", width=30)
	private java.lang.String bz;
	/**证照打印ID*/
	private java.lang.String zzdyid;
	/**创建时间*/
	@Excel(name="创建时间", width=30)
	private java.util.Date createDate;
	
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
	 *@return: java.lang.String  证照编号
	 */
	@Column(name ="ZZBH",nullable=true,length=36)
	public java.lang.String getZzbh(){
		return this.zzbh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  证照编号
	 */
	public void setZzbh(java.lang.String zzbh){
		this.zzbh = zzbh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  验证结果
	 */
	@Column(name ="YZJG",nullable=true,length=255)
	public java.lang.String getYzjg(){
		return this.yzjg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  验证结果
	 */
	public void setYzjg(java.lang.String yzjg){
		this.yzjg = yzjg;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  验证时间
	 */
	@Column(name ="YZSJ",nullable=true,length=20)
	public java.util.Date getYzsj(){
		return this.yzsj;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  验证时间
	 */
	public void setYzsj(java.util.Date yzsj){
		this.yzsj = yzsj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  删除标记
	 */
	@Column(name ="SCBJ",nullable=true,length=2)
	public java.lang.String getScbj(){
		return this.scbj;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  删除标记
	 */
	public void setScbj(java.lang.String scbj){
		this.scbj = scbj;
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
	 *@return: java.lang.String  备注
	 */
	@Column(name ="BZ",nullable=true,length=300)
	public java.lang.String getBz(){
		return this.bz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setBz(java.lang.String bz){
		this.bz = bz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  证照打印ID
	 */
	@Column(name ="ZZDYID",nullable=true,length=36)
	public java.lang.String getZzdyid(){
		return this.zzdyid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  证照打印ID
	 */
	public void setZzdyid(java.lang.String zzdyid){
		this.zzdyid = zzdyid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=50)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
}
