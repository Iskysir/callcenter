package com.chrhc.project.sc.ewmcode.entity;
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

/**   
 * @Title: Entity
 * @Description: 二维码内容项配置
 * @author onlineGenerator
 * @date 2015-05-12 15:51:44
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_ewmnrxpz", schema = "")
@SuppressWarnings("serial")
public class ScEwmnrxpzEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**业务编号*/
	private java.lang.String ywbh;
	/**业务名称*/
	private java.lang.String ywmc;
	/**前端传输条件*/
	private java.lang.String frontfield;
	/**数据来源表*/
	private java.lang.String sourcetable;
	/**是否已经使用*/
	private java.lang.String sfyy;
	/**删除标记*/
	private java.lang.String delflag;
	/**备注*/
	private java.lang.String remark;
	/**版本信息*/
	private java.lang.Integer versionNum;
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
	 *@return: java.lang.String  业务编号
	 */
	
	@Column(name ="YWBH",nullable=false,length=36)
	public java.lang.String getYwbh(){
		return this.ywbh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务编号
	 */
	public void setYwbh(java.lang.String ywbh){
		this.ywbh = ywbh;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务名称
	 */
	
	@Column(name ="YWMC",nullable=true,length=100)
	public java.lang.String getYwmc(){
		return this.ywmc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务名称
	 */
	public void setYwmc(java.lang.String ywmc){
		this.ywmc = ywmc;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  前端传输条件
	 */
	
	@Column(name ="FRONTFIELD",nullable=false,length=36)
	public java.lang.String getFrontfield(){
		return this.frontfield;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  前端传输条件
	 */
	public void setFrontfield(java.lang.String frontfield){
		this.frontfield = frontfield;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数据来源表
	 */
	
	@Column(name ="SOURCETABLE",nullable=false,length=32)
	public java.lang.String getSourcetable(){
		return this.sourcetable;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数据来源表
	 */
	public void setSourcetable(java.lang.String sourcetable){
		this.sourcetable = sourcetable;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否已经使用
	 */
	
	@Column(name ="SFYY",nullable=false,length=36)
	public java.lang.String getSfyy(){
		return this.sfyy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否已经使用
	 */
	public void setSfyy(java.lang.String sfyy){
		this.sfyy = sfyy;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  删除标记
	 */
	
	@Column(name ="DELFLAG",nullable=true,length=10)
	public java.lang.String getDelflag(){
		return this.delflag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  删除标记
	 */
	public void setDelflag(java.lang.String delflag) {
		this.delflag = delflag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	
	@Column(name ="REMARK",nullable=true,length=300)
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
	 *@return: java.lang.String  版本信息
	 */
	@Column(name ="version_num",nullable=true,length=16)
	public java.lang.Integer getVersionNum() {
		return versionNum;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  版本信息
	 */
	public void setVersionNum(java.lang.Integer versionNum) {
		this.versionNum = versionNum;
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
}
