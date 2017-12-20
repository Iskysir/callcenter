package com.chrhc.project.sc.qrcode.entity;

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
 * @Description: 二维码比较日志
 * @author onlineGenerator
 * @date 2015-05-15 15:57:55
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_ewm_log", schema = "")
@SuppressWarnings("serial")
public class ScEwmLogEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
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
	/**业务id <%--更改为附件文件的id--%>*/
	@Excel(name="业务id")
	private java.lang.String  ywId;
	/**业务类型*/
	@Excel(name="业务类型")
	private java.lang.String ywType;
	/**业务表单*/
	@Excel(name="业务表单")
	private java.lang.String ywTable;
	/**比较次数*/
	@Excel(name="比较次数")
	private java.lang.String operTimes = "0";
	/**当事人姓名*/
	@Excel(name="当事人姓名")
	private java.lang.String name;
	/**当事人证件号码*/
	@Excel(name="当事人证件号码")
	private java.lang.String cardid;
	
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务id
	 */
	@Column(name =" YW_ID",nullable=true,length=36)
	public java.lang.String getYwId(){
		return this. ywId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务id
	 */
	public void setYwId(java.lang.String  ywId){
		this. ywId =  ywId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务类型
	 */
	@Column(name ="YW_TYPE",nullable=true,length=32)
	public java.lang.String getYwType(){
		return this.ywType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务类型
	 */
	public void setYwType(java.lang.String ywType){
		this.ywType = ywType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务表单
	 */
	@Column(name ="YW_TABLE",nullable=true,length=32)
	public java.lang.String getYwTable(){
		return this.ywTable;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务表单
	 */
	public void setYwTable(java.lang.String ywTable){
		this.ywTable = ywTable;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  比较次数
	 */
	@Column(name ="OPER_TIMES",nullable=true,length=32)
	public java.lang.String getOperTimes(){
		return this.operTimes;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  比较次数
	 */
	public void setOperTimes(java.lang.String operTimes){
		this.operTimes = operTimes;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  当事人姓名
	 */
	@Column(name ="NAME",nullable=true,length=32)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  当事人姓名
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  当事人证件号码
	 */
	@Column(name ="CARDID",nullable=true,length=32)
	public java.lang.String getCardid(){
		return this.cardid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  当事人证件号码
	 */
	public void setCardid(java.lang.String cardid){
		this.cardid = cardid;
	}
}
