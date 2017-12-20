package com.chrhc.project.sc.certemp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 证照模板
 * @author onlineGenerator
 * @date 2015-05-18 11:39:43
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_cer_template", schema = "")
@SuppressWarnings("serial")
public class ScCerTemplateEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**模板名称*/
	@Excel(name="模板名称")
	private java.lang.String tempName;
	/**模板类型*/
	@Excel(name="模板类型")
	private java.lang.String tempType;
	/**文件模板*/
	@Excel(name="文件模板")
	private java.lang.String template;
	/**二维码模板*/
	@Excel(name="二维码模板")
	private java.lang.String qrcode;
	public java.lang.String getQrcode() {
		return qrcode;
	}

	public void setQrcode(java.lang.String qrcode) {
		this.qrcode = qrcode;
	}

	/**表名*/
	@Excel(name="表名")
	private java.lang.String tableName;
	
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
	 *@return: java.lang.String  模板名称
	 */
	@Column(name ="TEMP_NAME",nullable=true,length=50)
	public java.lang.String getTempName(){
		return this.tempName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模板名称
	 */
	public void setTempName(java.lang.String tempName){
		this.tempName = tempName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  模板类型
	 */
	@Column(name ="TEMP_TYPE",nullable=true,length=36)
	public java.lang.String getTempType(){
		return this.tempType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模板类型
	 */
	public void setTempType(java.lang.String tempType){
		this.tempType = tempType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文件模板
	 */
	@Column(name ="TEMPLATE",nullable=true)
	public java.lang.String getTemplate(){
		return this.template;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文件模板
	 */
	public void setTemplate(java.lang.String template){
		this.template = template;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  表名
	 */
	@Column(name ="TABLE_NAME",nullable=true,length=200)
	public java.lang.String getTableName(){
		return this.tableName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  表名
	 */
	public void setTableName(java.lang.String tableName){
		this.tableName = tableName;
	}
}
