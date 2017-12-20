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
 * @Description: 建筑物单元管理
 * @author onlineGenerator
 * @date 2015-05-11 18:38:15
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_jzwdygl", schema = "")
@SuppressWarnings("serial")
public class ScJzwdyglEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**建筑物ID*/
	private java.lang.String jzwId;
	/**单元号*/
	@Excel(name="单元号")
	private java.lang.Integer unit;
	/**楼层数*/
	@Excel(name="楼层数")
	private java.lang.Integer floors;
	/**房户数*/
	@Excel(name="房户数")
	private java.lang.Integer rooms;
	/**楼梯数*/
	@Excel(name="楼梯数")
	private java.lang.Integer stairs;
	/**备注*/
	private java.lang.String remark;
	
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
	 *@return: java.lang.Integer  房户数
	 */
	@Column(name ="ROOMS",nullable=false,length=2)
	public java.lang.Integer getRooms(){
		return this.rooms;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  房户数
	 */
	public void setRooms(java.lang.Integer rooms){
		this.rooms = rooms;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  楼梯数
	 */
	@Column(name ="STAIRS",nullable=true,length=2)
	public java.lang.Integer getStairs(){
		return this.stairs;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  楼梯数
	 */
	public void setStairs(java.lang.Integer stairs){
		this.stairs = stairs;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=400)
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
}
