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
 * @Description: 计生机构
 * @author onlineGenerator
 * @date 2015-10-22 18:34:27
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_jsjg", schema = "")
@SuppressWarnings("serial")
public class ScJsjgEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**机构名称*/
	@Excel(name="机构名称")
	private java.lang.String jgmc;
	/**成立时间*/
	@Excel(name="成立时间")
	private java.util.Date clsj;
	/**机构主营*/
	@Excel(name="机构主营")
	private java.lang.String jgzy;
	/**机构地址*/
	@Excel(name="机构地址")
	private java.lang.String jgdz;
	/**版本号*/
	private java.lang.Integer versionNum;
	/**部门名称*/
	private java.lang.String sysOrgCode;
	/**负责人*/
	@Excel(name="负责人")
	private java.lang.String fzr;
	/**联系电话*/
	@Excel(name="联系电话")
	private java.lang.String lxdh;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/** 更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**创建日期*/
	private java.util.Date createDatetime;
	/**更新日期*/
	private java.util.Date updateDate;
	/**备注信息*/
	@Excel(name="备注信息")
	private java.lang.String remark;
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
	 *@return: java.lang.String  机构名称
	 */
	@Column(name ="JGMC",nullable=false,length=32)
	public java.lang.String getJgmc(){
		return this.jgmc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机构名称
	 */
	public void setJgmc(java.lang.String jgmc){
		this.jgmc = jgmc;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  成立时间
	 */
	@Column(name ="CLSJ",nullable=false,length=32)
	public java.util.Date getClsj(){
		return this.clsj;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  成立时间
	 */
	public void setClsj(java.util.Date clsj){
		this.clsj = clsj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机构主营
	 */
	@Column(name ="JGZY",nullable=true,length=32)
	public java.lang.String getJgzy(){
		return this.jgzy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机构主营
	 */
	public void setJgzy(java.lang.String jgzy){
		this.jgzy = jgzy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机构地址
	 */
	@Column(name ="JGDZ",nullable=true,length=32)
	public java.lang.String getJgdz(){
		return this.jgdz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机构地址
	 */
	public void setJgdz(java.lang.String jgdz){
		this.jgdz = jgdz;
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
	 *@return: java.lang.String  负责人
	 */
	@Column(name ="FZR",nullable=false,length=32)
	public java.lang.String getFzr(){
		return this.fzr;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  负责人
	 */
	public void setFzr(java.lang.String fzr){
		this.fzr = fzr;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系电话
	 */
	@Column(name ="LXDH",nullable=false,length=32)
	public java.lang.String getLxdh(){
		return this.lxdh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系电话
	 */
	public void setLxdh(java.lang.String lxdh){
		this.lxdh = lxdh;
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
	 *@return: java.lang.String   更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String   更新人名称
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
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATETIME",nullable=true,length=50)
	public java.util.Date getCreateDatetime(){
		return this.createDatetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDatetime(java.util.Date createDatetime){
		this.createDatetime = createDatetime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true,length=50)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
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
