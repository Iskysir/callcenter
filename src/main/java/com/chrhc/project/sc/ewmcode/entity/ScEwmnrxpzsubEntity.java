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
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 二维码返回信息
 * @author onlineGenerator
 * @date 2015-05-12 15:51:43
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_ewmnrxpzsub", schema = "")
@SuppressWarnings("serial")
public class ScEwmnrxpzsubEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**内容项代码*/
	@Excel(name="内容项代码")
	private java.lang.String nrxcode;
	/**内容项名称*/
	@Excel(name="内容项名称")
	private java.lang.String nrxname;

	/**配置主表ID*/
	private java.lang.String pzid;
	/**删除标记*/
	private java.lang.String delflag;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String remark;
	/**版本信息*/
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
	 *@return: java.lang.String  内容项代码
	 */
	@Column(name ="NRXCODE",nullable=true,length=36)
	public java.lang.String getNrxcode(){
		return this.nrxcode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容项代码
	 */
	public void setNrxcode(java.lang.String nrxcode){
		this.nrxcode = nrxcode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容项名称
	 */
	@Column(name ="NRXNAME",nullable=true,length=100)
	public java.lang.String getNrxname(){
		return this.nrxname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容项名称
	 */
	public void setNrxname(java.lang.String nrxname){
		this.nrxname = nrxname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  配置主表ID
	 */
	@Column(name ="PZID",nullable=false,length=36)
	public java.lang.String getPzid(){
		return this.pzid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  配置主表ID
	 */
	public void setPzid(java.lang.String pzid){
		this.pzid = pzid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  删除标记
	 */
	@Column(name ="DELFLAG",nullable=true,length=2)
	public java.lang.String getDelflag(){
		return this.delflag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  删除标记
	 */
	public void setDelflag(java.lang.String delflag){
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
}
