package com.chrhc.project.sc.gis.entity;

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
 * @Description: 图层信息
 * @author onlineGenerator
 * @date 2015-05-07 18:49:28
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_gislayer", schema = "")
@SuppressWarnings("serial")
public class ScGislayerEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**图层名称*/
	@Excel(name="图层名称")
	private java.lang.String name;
	/**图层类型*/
	@Excel(name="图层类型")
	private java.lang.String layertype;
	/**是否有效*/
	@Excel(name="是否有效")
	private java.lang.String status;
	/**是否删除*/
	private java.lang.String deleted;
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
	/**取图层SQL*/
	@Excel(name="取图层SQL")
	private java.lang.String datasql;
	/**点击展示url*/
	@Excel(name="点击展示url")
	private java.lang.String clickurl;
	/**悬浮展示url*/
	@Excel(name="悬浮展示url")
	private java.lang.String hoverurl;
	/**编辑url*/
	@Excel(name="编辑url")
	private java.lang.String editurl;
	
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
	 *@return: java.lang.String  图层名称
	 */
	@Column(name ="NAME",nullable=false,length=50)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图层名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图层类型
	 */
	@Column(name ="LAYERTYPE",nullable=false,length=36)
	public java.lang.String getLayertype(){
		return this.layertype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图层类型
	 */
	public void setLayertype(java.lang.String layertype){
		this.layertype = layertype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否有效
	 */
	@Column(name ="STATUS",nullable=false,length=1)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否有效
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否删除
	 */
	@Column(name ="DELETED",nullable=false,length=1)
	public java.lang.String getDeleted(){
		return this.deleted;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否删除
	 */
	public void setDeleted(java.lang.String deleted){
		this.deleted = deleted;
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
	@Column(name ="CREATE_DATE",nullable=true,length=50)
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
	@Column(name ="UPDATE_DATE",nullable=true,length=50)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  取图层SQL
	 */
	@Column(name ="DATASQL",nullable=false,length=500)
	public java.lang.String getDatasql(){
		return this.datasql;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  取图层SQL
	 */
	public void setDatasql(java.lang.String datasql){
		this.datasql = datasql;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  点击展示url
	 */
	@Column(name ="CLICKURL",nullable=true,length=500)
	public java.lang.String getClickurl(){
		return this.clickurl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  点击展示url
	 */
	public void setClickurl(java.lang.String clickurl){
		this.clickurl = clickurl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  悬浮展示url
	 */
	@Column(name ="HOVERURL",nullable=true,length=500)
	public java.lang.String getHoverurl(){
		return this.hoverurl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  悬浮展示url
	 */
	public void setHoverurl(java.lang.String hoverurl){
		this.hoverurl = hoverurl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  编辑url
	 */
	@Column(name ="EDITURL",nullable=true,length=500)
	public java.lang.String getEditurl(){
		return this.editurl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  编辑url
	 */
	public void setEditurl(java.lang.String editurl){
		this.editurl = editurl;
	}
}
