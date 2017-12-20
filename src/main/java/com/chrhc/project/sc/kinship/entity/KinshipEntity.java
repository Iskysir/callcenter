package com.chrhc.project.sc.kinship.entity;

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
 * @Description: 亲属关系表
 * @author onlineGenerator
 * @date 2015-05-06 11:53:31
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_qsgx", schema = "")
@SuppressWarnings("serial")
public class KinshipEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**关系类型*/
	@Excel(name="关系类型")
	private java.lang.String gxlx;
	/**姓名*/
	@Excel(name="姓名")
	private java.lang.String name;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String bz;
	/**人员id*/
	private java.lang.String ryId;
	/**删除标识*/
	private java.lang.String delflag;
	/**删除时间*/
	private java.lang.String delDate;
	/**预留字段a*/
	private java.lang.String obligatea;
	/**预留字段b*/
	private java.lang.String obligateb;
	/**预留字段c*/
	private java.lang.String obligatec;
	/**预留字段d*/
	private java.lang.String obligated;
	/**预留字段e*/
	private java.lang.String obligatee;
	/**亲属姓名id*/
	private java.lang.String qsId;
	/**人员名字*/
	private java.lang.String ryName;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关系类型
	 */
	@Column(name ="GXLX",nullable=true,length=20)
	public java.lang.String getGxlx(){
		return this.gxlx;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关系类型
	 */
	public void setGxlx(java.lang.String gxlx){
		this.gxlx = gxlx;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  姓名
	 */
	@Column(name ="NAME",nullable=true,length=20)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  姓名
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="BZ",nullable=true,length=200)
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
	 *@return: java.lang.String  人员id
	 */
	@Column(name ="RY_ID",nullable=true,length=50)
	public java.lang.String getRyId(){
		return this.ryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  人员id
	 */
	public void setRyId(java.lang.String ryId){
		this.ryId = ryId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  删除标识
	 */
	@Column(name ="DELFLAG",nullable=true,length=2)
	public java.lang.String getDelflag(){
		return this.delflag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  删除标识
	 */
	public void setDelflag(java.lang.String delflag){
		this.delflag = delflag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  删除时间
	 */
	@Column(name ="DEL_DATE",nullable=true,length=20)
	public java.lang.String getDelDate(){
		return this.delDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  删除时间
	 */
	public void setDelDate(java.lang.String delDate){
		this.delDate = delDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预留字段a
	 */
	@Column(name ="OBLIGATEA",nullable=true,length=100)
	public java.lang.String getObligatea(){
		return this.obligatea;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预留字段a
	 */
	public void setObligatea(java.lang.String obligatea){
		this.obligatea = obligatea;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预留字段b
	 */
	@Column(name ="OBLIGATEB",nullable=true,length=100)
	public java.lang.String getObligateb(){
		return this.obligateb;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预留字段b
	 */
	public void setObligateb(java.lang.String obligateb){
		this.obligateb = obligateb;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预留字段c
	 */
	@Column(name ="OBLIGATEC",nullable=true,length=100)
	public java.lang.String getObligatec(){
		return this.obligatec;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预留字段c
	 */
	public void setObligatec(java.lang.String obligatec){
		this.obligatec = obligatec;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预留字段d
	 */
	@Column(name ="OBLIGATED",nullable=true,length=100)
	public java.lang.String getObligated(){
		return this.obligated;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预留字段d
	 */
	public void setObligated(java.lang.String obligated){
		this.obligated = obligated;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预留字段e
	 */
	@Column(name ="OBLIGATEE",nullable=true,length=100)
	public java.lang.String getObligatee(){
		return this.obligatee;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预留字段e
	 */
	public void setObligatee(java.lang.String obligatee){
		this.obligatee = obligatee;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  亲属姓名id
	 */
	@Column(name ="QS_ID",nullable=true,length=50)
	public java.lang.String getQsId(){
		return this.qsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  亲属姓名id
	 */
	public void setQsId(java.lang.String qsId){
		this.qsId = qsId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  人员名字
	 */
	@Column(name ="RY_NAME",nullable=true,length=100)
	public java.lang.String getRyName(){
		return this.ryName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  人员名字
	 */
	public void setRyName(java.lang.String ryName){
		this.ryName = ryName;
	}
}
