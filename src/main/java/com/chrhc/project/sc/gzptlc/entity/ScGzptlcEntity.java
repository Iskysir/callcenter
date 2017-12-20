package com.chrhc.project.sc.gzptlc.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 网上办事
 * @author onlineGenerator
 * @date 2016-04-10 14:05:04
 * @version V1.0   
 *
 */
//@Entity
//@Table(name = "sc_gzptlc", schema = "")
//@SuppressWarnings("serial")
public class ScGzptlcEntity implements java.io.Serializable {
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
	/**姓名*/
	@Excel(name="姓名")
	private java.lang.String declare_per_name;
	public java.lang.String getDeclare_per_name() {
		return declare_per_name;
	}

	public void setDeclare_per_name(java.lang.String declare_per_name) {
		this.declare_per_name = declare_per_name;
	}

	public java.lang.String getDeclare_id_num() {
		return declare_id_num;
	}

	public void setDeclare_id_num(java.lang.String declare_id_num) {
		this.declare_id_num = declare_id_num;
	}

	/**性别*/
	@Excel(name="性别")
	private java.lang.String xb;
	/**身份证号*/
	@Excel(name="身份证号")
	private java.lang.String declare_id_num;
	/**出生年月*/
	@Excel(name="出生年月")
	private java.lang.String csrq;
	private java.lang.String submit_time;
	/**办理类型*/
	@Excel(name="办理类型")
	private java.lang.String biz_type;
	/**办理状态*/
	@Excel(name="办理状态")
	private java.lang.String department_approve_state;
	/**审批意见*/
	@Excel(name="审批意见")
	private java.lang.String spyj;
	/**电话*/
	private String declare_phone;
	/**邮箱*/
	private String declare_email;
	/**地址*/
	private String address;
	/**受理区域*/
	private String slqy;
	/**受理部门*/
	private String depart;
	/**附件信息*/
	private List<Map<String,String>> fj;
	/**流程实例ID*/
	private String instanceID;
	/**办理名称*/
	@Excel(name="办理名称")
	private java.lang.String biz_name;
	/**受理号码*/
	private String acceptcode;
	public String getAcceptcode() {
		return acceptcode;
	}

	public void setAcceptcode(String acceptcode) {
		this.acceptcode = acceptcode;
	}

	/**审批流程URL 审批提交时用到*/
	private java.lang.String url;
	
	public java.lang.String getUrl() {
		return url;
	}

	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	/*public java.lang.String getBizname() {
		return bizname;
	}

	public void setBizname(java.lang.String bizname) {
		this.bizname = bizname;
	}*/

	public String getInstanceID() {
		return instanceID;
	}

	public void setInstanceID(String instanceID) {
		this.instanceID = instanceID;
	}

	/*public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getYx() {
		return yx;
	}

	public void setYx(String yx) {
		this.yx = yx;
	}
*/
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSlqy() {
		return slqy;
	}

	public void setSlqy(String slqy) {
		this.slqy = slqy;
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public List<Map<String, String>> getFj() {
		return fj;
	}

	public void setFj(List<Map<String, String>> fj) {
		this.fj = fj;
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
	 *@return: java.lang.String  姓名
	 */
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  性别
	 */
	@Column(name ="XB",nullable=true,length=50)
	public java.lang.String getXb(){
		return this.xb;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  性别
	 */
	public void setXb(java.lang.String xb){
		this.xb = xb;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出生年月
	 */
	@Column(name ="CSRQ",nullable=true,length=50)
	public java.lang.String getCsrq(){
		return this.csrq;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出生年月
	 */
	public void setCsrq(java.lang.String csrq){
		this.csrq = csrq;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  办理类型
	 */
	/*@Column(name ="BIZ_TYPE",nullable=true,length=50)
	public java.lang.String getBizType(){
		return this.bizType;
	}*/

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  办理类型
	 *//*
	public void setBizType(java.lang.String bizType){
		this.bizType = bizType;
	}
	*//**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  办理状态
	 *//*
	@Column(name ="STATUS",nullable=true,length=50)
	public java.lang.String getStatus(){
		return this.status;
	}

	*//**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  办理状态
	 *//*
	public void setStatus(java.lang.String status){
		this.status = status;
	}*/
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审批意见
	 */
	@Column(name ="SPYJ",nullable=true,length=70)
	public java.lang.String getSpyj(){
		return this.spyj;
	}

	public java.lang.String getSubmit_time() {
		return submit_time;
	}

	public void setSubmit_time(java.lang.String submit_time) {
		this.submit_time = submit_time;
	}

	public java.lang.String getBiz_type() {
		return biz_type;
	}

	public void setBiz_type(java.lang.String biz_type) {
		this.biz_type = biz_type;
	}

	public java.lang.String getDepartment_approve_state() {
		return department_approve_state;
	}

	public void setDepartment_approve_state(
			java.lang.String department_approve_state) {
		this.department_approve_state = department_approve_state;
	}

	public String getDeclare_phone() {
		return declare_phone;
	}

	public void setDeclare_phone(String declare_phone) {
		this.declare_phone = declare_phone;
	}

	public String getDeclare_email() {
		return declare_email;
	}

	public void setDeclare_email(String declare_email) {
		this.declare_email = declare_email;
	}

	public java.lang.String getBiz_name() {
		return biz_name;
	}

	public void setBiz_name(java.lang.String biz_name) {
		this.biz_name = biz_name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审批意见
	 */
	public void setSpyj(java.lang.String spyj){
		this.spyj = spyj;
	}
}
