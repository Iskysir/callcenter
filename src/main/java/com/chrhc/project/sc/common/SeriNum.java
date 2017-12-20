package com.chrhc.project.sc.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.Date;

/**   
 * @Title: Entity
 * @Description: sc_csn
 * @author onlineGenerator
 * @date 2015-05-14 11:40:31
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_csn", schema = "")
@SuppressWarnings("serial")
public class SeriNum  implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**idtype*/
	@Excel(name="idtype")
	private java.lang.String idtype;
	/**curid*/
	@Excel(name="curid")
	private java.lang.String curid;
	/**startid*/
	@Excel(name="startid")
	private java.lang.String startid;
	/**endid*/
	@Excel(name="endid")
	private java.lang.String endid;
	/**progid*/
	@Excel(name="progid")
	private java.lang.String progid;
	/**remark*/
	@Excel(name="remark")
	private java.lang.String remark;
	/**prefix*/
	@Excel(name="prefix")
	private java.lang.String prefix;
	/**suffix*/
	@Excel(name="suffix")
	private java.lang.String suffix;
	/**计数日期**/
	private java.util.Date count_date;

	@Column(name ="COUNT_DATE",nullable=true,length=20)
	public Date getCount_date() {
		return count_date;
	}

	public void setCount_date(Date count_date) {
		this.count_date = count_date;
	}

	/**
	 *方法: 取得java.lang.String

	 *@return: java.lang.String  id
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=50)
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
	 *@return: java.lang.String  idtype
	 */
	@Column(name ="IDTYPE",nullable=true,length=20)
	public java.lang.String getIdtype(){
		return this.idtype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  idtype
	 */
	public void setIdtype(java.lang.String idtype){
		this.idtype = idtype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  curid
	 */
	@Column(name ="CURID",nullable=true,length=10)
	public java.lang.String getCurid(){
		return this.curid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  curid
	 */
	public void setCurid(java.lang.String curid){
		this.curid = curid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  startid
	 */
	@Column(name ="STARTID",nullable=true,length=10)
	public java.lang.String getStartid(){
		return this.startid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  startid
	 */
	public void setStartid(java.lang.String startid){
		this.startid = startid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  endid
	 */
	@Column(name ="ENDID",nullable=true,length=10)
	public java.lang.String getEndid(){
		return this.endid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  endid
	 */
	public void setEndid(java.lang.String endid){
		this.endid = endid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  progid
	 */
	@Column(name ="PROGID",nullable=true,length=10)
	public java.lang.String getProgid(){
		return this.progid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  progid
	 */
	public void setProgid(java.lang.String progid){
		this.progid = progid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  remark
	 */
	@Column(name ="REMARK",nullable=true,length=128)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  remark
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  prefix
	 */
	@Column(name ="PREFIX",nullable=true,length=200)
	public java.lang.String getPrefix(){
		return this.prefix;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  prefix
	 */
	public void setPrefix(java.lang.String prefix){
		this.prefix = prefix;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  suffix
	 */
	@Column(name ="SUFFIX",nullable=true,length=200)
	public java.lang.String getSuffix(){
		return this.suffix;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  suffix
	 */
	public void setSuffix(java.lang.String suffix){
		this.suffix = suffix;
	}
}
