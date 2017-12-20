package com.chrhc.project.sc.scperm.entity;

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
 * @Description: 自定义报表权限表
 * @author onlineGenerator
 * @date 2015-06-11 16:53:58
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_report_perm", schema = "")
@SuppressWarnings("serial")
public class ScReportPermEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**关联类型*/
	private java.lang.String permType;
	/**关联ID*/
	private java.lang.String permId;
	/**自定义报表编码*/
	@Excel(name="自定义报表编码")
	private java.lang.String reportCode;
	/**数据id*/
	@Excel(name="数据id")
	private java.lang.String dataIds;
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
	 *@return: java.lang.String  关联类型
	 */
	@Column(name ="PERM_TYPE",nullable=true,length=50)
	public java.lang.String getPermType(){
		return this.permType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联类型
	 */
	public void setPermType(java.lang.String permType){
		this.permType = permType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关联ID
	 */
	@Column(name ="PERM_ID",nullable=true,length=36)
	public java.lang.String getPermId(){
		return this.permId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联ID
	 */
	public void setPermId(java.lang.String permId){
		this.permId = permId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  自定义报表编码
	 */
	@Column(name ="REPORT_CODE",nullable=true,length=36)
	public java.lang.String getReportCode(){
		return this.reportCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  自定义报表编码
	 */
	public void setReportCode(java.lang.String reportCode){
		this.reportCode = reportCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数据id
	 */
	@Column(name ="DATA_IDS",nullable=true,length=9999)
	public java.lang.String getDataIds(){
		return this.dataIds;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数据id
	 */
	public void setDataIds(java.lang.String dataIds){
		this.dataIds = dataIds;
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
}
