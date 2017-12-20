package com.chrhc.project.sc.message.entity;

 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 消息记录
 * @author onlineGenerator
 * @date 2015-08-07 11:32:05
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_msg_record", schema = "")
@SuppressWarnings("serial")
public class ScMsgRecordEntity implements java.io.Serializable {
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
	/**版本号*/
	private java.lang.Integer versionNum;
	/**组织编号*/
	private java.lang.String sysOrgCode;
	/**删除标志*/
	private java.lang.String delflag;
	/**消息id*/
	@Excel(name="消息id")
	private java.lang.String messageId;
	/**信息类型*/
	@Excel(name="信息类型")
	private java.lang.String mType;
	/**消息标题*/
	@Excel(name="消息标题")
	private java.lang.String title;
	/**内容*/
	@Excel(name="内容")
	private java.lang.String content;
	/**发送人*/
	@Excel(name="发送人")
	private java.lang.String publisher;
	/**接收人*/
	@Excel(name="接收人")
	private java.lang.String receiver;
	/**发送时间*/
	@Excel(name="发送时间")
	private java.lang.String time;
	/**状态类别*/
	@Excel(name="状态类别")
	private java.lang.String statusType;
	/**状态*/
	@Excel(name="状态")
	private java.lang.String status;
	/**电话或邮箱*/
	@Excel(name="电话或邮箱")
	private java.lang.String telEm;
	/**模板类型*/
	@Excel(name="模板类型")
	private java.lang.String modelType;
	
	public ScMsgRecordEntity(){
		delflag = "0";
	}
	
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本号
	 */
	@Column(name ="VERSION_NUM",nullable=true,length=1)
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
	 *@return: java.lang.String  组织编号
	 */
	@Column(name ="SYS_ORG_CODE",nullable=true,length=50)
	public java.lang.String getSysOrgCode(){
		return this.sysOrgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组织编号
	 */
	public void setSysOrgCode(java.lang.String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
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
	 *@return: java.lang.String  消息id
	 */
	@Column(name ="MESSAGE_ID",nullable=true,length=50)
	public java.lang.String getMessageId(){
		return this.messageId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  消息id
	 */
	public void setMessageId(java.lang.String messageId){
		this.messageId = messageId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  信息类型
	 */
	@Column(name ="M_TYPE",nullable=true,length=50)
	public java.lang.String getMType(){
		return this.mType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  信息类型
	 */
	public void setMType(java.lang.String mType){
		this.mType = mType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  消息标题
	 */
	@Column(name ="TITLE",nullable=true,length=50)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  消息标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容
	 */
	@Column(name ="CONTENT",nullable=true,length=50)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发送人
	 */
	@Column(name ="PUBLISHER",nullable=true,length=50)
	public java.lang.String getPublisher(){
		return this.publisher;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送人
	 */
	public void setPublisher(java.lang.String publisher){
		this.publisher = publisher;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  接收人
	 */
	@Column(name ="RECEIVER",nullable=true,length=50)
	public java.lang.String getReceiver(){
		return this.receiver;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接收人
	 */
	public void setReceiver(java.lang.String receiver){
		this.receiver = receiver;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发送时间
	 */
	@Column(name ="TIME",nullable=true,length=50)
	public java.lang.String getTime(){
		return this.time;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送时间
	 */
	public void setTime(java.lang.String time){
		this.time = time;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态类别
	 */
	@Column(name ="STATUS_TYPE",nullable=true,length=50)
	public java.lang.String getStatusType(){
		return this.statusType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态类别
	 */
	public void setStatusType(java.lang.String statusType){
		this.statusType = statusType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="STATUS",nullable=true,length=50)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话或邮箱
	 */
	@Column(name ="TEL_EM",nullable=true,length=50)
	public java.lang.String getTelEm(){
		return this.telEm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话或邮箱
	 */
	public void setTelEm(java.lang.String telEm){
		this.telEm = telEm;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  模板类型
	 */
	@Column(name ="MODEL_TYPE",nullable=true,length=50)
	public java.lang.String getModelType(){
		return this.modelType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模板类型
	 */
	public void setModelType(java.lang.String modelType){
		this.modelType = modelType;
	}
}
