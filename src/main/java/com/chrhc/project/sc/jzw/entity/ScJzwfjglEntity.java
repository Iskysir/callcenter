package com.chrhc.project.sc.jzw.entity;

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
 * @Description: 建筑物房间管理
 * @author onlineGenerator
 * @date 2015-05-22 09:43:01
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_jzwfjgl", schema = "")
@SuppressWarnings("serial")
public class ScJzwfjglEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**建筑物ID*/
	private java.lang.String jzwId;
	/**单元ID*/
	private java.lang.String dyId;
	/**单元号*/
	@Excel(name="单元号")
	private java.lang.Integer unit;
	/**楼层数*/
	@Excel(name="楼层数")
	private java.lang.Integer floors;
	/**房间号*/
	@Excel(name="房间号")
	private java.lang.Integer number;
	/**部门名称*/
	private java.lang.String sysOrgCode;
	/**房间名*/
	@Excel(name="房间名")
	private java.lang.String room;
	/**GIS信息*/
	private java.lang.String gisxy;
	/**GIS图形ID*/
	private java.lang.String gisId;
	/**删除标识*/
	private java.lang.String delflag;
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
	 *@return: java.lang.String  建筑物ID
	 */
	@Column(name ="JZW_ID",nullable=false,length=36)
	public java.lang.String getJzwId(){
		return this.jzwId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  建筑物ID
	 */
	public void setJzwId(java.lang.String jzwId){
		this.jzwId = jzwId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单元ID
	 */
	@Column(name ="DY_ID",nullable=false,length=36)
	public java.lang.String getDyId(){
		return this.dyId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单元ID
	 */
	public void setDyId(java.lang.String dyId){
		this.dyId = dyId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  单元号
	 */
	@Column(name ="UNIT",nullable=false,length=2)
	public java.lang.Integer getUnit(){
		return this.unit;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  单元号
	 */
	public void setUnit(java.lang.Integer unit){
		this.unit = unit;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  楼层数
	 */
	@Column(name ="FLOORS",nullable=false,length=2)
	public java.lang.Integer getFloors(){
		return this.floors;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  楼层数
	 */
	public void setFloors(java.lang.Integer floors){
		this.floors = floors;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  房间号
	 */
	@Column(name ="NUMBER",nullable=false,length=2)
	public java.lang.Integer getNumber(){
		return this.number;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  房间号
	 */
	public void setNumber(java.lang.Integer number){
		this.number = number;
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
	 *@return: java.lang.String  房间名
	 */
	@Column(name ="ROOM",nullable=false,length=4)
	public java.lang.String getRoom(){
		return this.room;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  房间名
	 */
	public void setRoom(java.lang.String room){
		this.room = room;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  GIS信息
	 */
	@Column(name ="GISXY",nullable=false,length=400)
	public java.lang.String getGisxy(){
		return this.gisxy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  GIS信息
	 */
	public void setGisxy(java.lang.String gisxy){
		this.gisxy = gisxy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  GIS图形ID
	 */
	@Column(name ="GIS_ID",nullable=false,length=36)
	public java.lang.String getGisId(){
		return this.gisId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  GIS图形ID
	 */
	public void setGisId(java.lang.String gisId){
		this.gisId = gisId;
	}
}
