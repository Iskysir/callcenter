package com.chrhc.project.sc.docfile.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;

/**   
 * @Title: Entity
 * @Description: 电子文档库
 * @author onlineGenerator
 * @date 2015-05-23 15:02:40
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_doc_war", schema = "")
@SuppressWarnings("serial")
public class ScDocWarEntity implements java.io.Serializable {
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
	/**部门名称*/
	private java.lang.String sysOrgCode;
	
	private TSDepart tsDepart;
    
	/**更新日期*/
	private java.util.Date updateDate;
	/**文档类型*/
	@Excel(name="文档类型", width=30)
	private java.lang.String docType;
	/**文档名称*/
	private java.lang.String docName;
	/**文档地址*/
	private java.lang.String docUrl;
	/**创建时间*/
	private java.util.Date ctreateTime;
	/**附件*/
	//@Excel(name="附件", width=30,orderNum="12")
	private java.lang.String docFile;
	/**删除标记*/
	private java.lang.String delflag;
	/**姓名*/
	@Excel(name="姓名", width=30)
	private java.lang.String name;
	/**性别*/
	@Excel(name="性别", width=30)
	private java.lang.String sex;
	/**出生日期*/
	@Excel(name="出生日期", width=30)
	private java.lang.String birthday;
	/**身份证号*/
	@Excel(name="身份证号", width=30)
	private java.lang.String idcardnum;
	/**民族*/
	@Excel(name="民族", width=30)
	private java.lang.String nation;
	/**住址*/
	@Excel(name="住址", width=30)
	private java.lang.String address;
	/**身份证有效期开始*/
	@Excel(name="身份证有效期开始", width=30)
	@Column(name ="yxqstar",nullable=true,length=32)
	private java.lang.String yxqstar;
	/**身份证有效期结束*/
	@Excel(name="身份证有效期结束", width=30)
	@Column(name ="yxqend",nullable=true,length=32)
	private java.lang.String yxqend;
	
	/**签发机关*/
	@Excel(name="签发机关", width=30)
	@Column(name ="qfjg",nullable=true,length=32)
	private java.lang.String qfjg;
	
	/**备注信息*/
	@Excel(name="备注", width=30)
	@Column(name ="bz",nullable=true,length=300)
	private java.lang.String bz;
	

	public java.lang.String getBz() {
		return bz;
	}

	public void setBz(java.lang.String bz) {
		this.bz = bz;
	}

	/**版本控制*/
	
	private java.lang.Integer versionNum;
	@Column(name ="version_num",nullable=true,length=16)
	public java.lang.Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(java.lang.Integer versionnum) {
		this.versionNum = versionnum;
	}

	public java.lang.String getQfjg() {
		return qfjg;
	}

	public void setQfjg(java.lang.String qfjg) {
		this.qfjg = qfjg;
	}

	public java.lang.String getYxqstar() {
		return yxqstar;
	}

	public void setYxqstar(java.lang.String yxqstar) {
		this.yxqstar = yxqstar;
	}

	public java.lang.String getYxqend() {
		return yxqend;
	}

	public void setYxqend(java.lang.String yxqend) {
		this.yxqend = yxqend;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门名称
	 */
	
	@JsonIgnore
	@ManyToOne(fetch =FetchType.LAZY)
	//@OneToOne
	@JoinColumn(name = "SYS_ORG_CODE", referencedColumnName = "org_code",insertable=false,updatable=false) //
    public TSDepart getTsDepart() {
        return tsDepart;
    }
    @Column(name ="SYS_ORG_CODE",nullable=true,length=50)
	public java.lang.String getSysOrgCode(){
		return this.sysOrgCode;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门名称
	 */
	public void setTsDepart(TSDepart tsDepart){
		this.tsDepart = tsDepart;
	}
	public void setSysOrgCode(java.lang.String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
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
	 *@return: java.lang.String  文档类型
	 */
	
	@Column(name ="DOC_TYPE",nullable=true,length=32)
	public java.lang.String getDocType(){
		return this.docType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文档类型
	 */
	public void setDocType(java.lang.String docType){
		this.docType = docType;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文档名称
	 */
	
	@Column(name ="DOC_NAME",nullable=true,length=32)
	public java.lang.String getDocName(){
		return this.docName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文档名称
	 */
	public void setDocName(java.lang.String docName){
		this.docName = docName;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文档地址
	 */
	
	@Column(name ="DOC_URL",nullable=true,length=32)
	public java.lang.String getDocUrl(){
		return this.docUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文档地址
	 */
	public void setDocUrl(java.lang.String docUrl){
		this.docUrl = docUrl;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	
	@Column(name ="CTREATE_TIME",nullable=true,length=32)
	public java.util.Date getCtreateTime(){
		return this.ctreateTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCtreateTime(java.util.Date ctreateTime){
		this.ctreateTime = ctreateTime;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附件
	 */
	
	@Column(name ="DOC_FILE",nullable=true,length=500)
	public java.lang.String getDocFile(){
		return this.docFile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附件
	 */
	public void setDocFile(java.lang.String docFile){
		this.docFile = docFile;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  删除标记
	 */
	
	@Column(name ="DELFLAG",nullable=true,length=1)
	public java.lang.String getDelflag(){
		return this.delflag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  删除标记
	 */
	public void setDelflag(java.lang.String delflag){
		if(delflag==null||delflag.equals("")){
			this.delflag = "0";
		}else{
		this.delflag = delflag;
		}
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  姓名
	 */
	
	@Column(name ="NAME",nullable=true,length=32)
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
	 *@return: java.lang.String  性别
	 */
	
	@Column(name ="SEX",nullable=true,length=5)
	public java.lang.String getSex(){
		return this.sex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  性别
	 */
	public void setSex(java.lang.String sex){
		this.sex = sex;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出生日期
	 */
	
	@Column(name ="BIRTHDAY",nullable=true,length=20)
	public java.lang.String getBirthday(){
		return this.birthday;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出生日期
	 */
	public void setBirthday(java.lang.String birthday){
		this.birthday = birthday;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  身份证号
	 */
	
	@Column(name ="IDCARDNUM",nullable=true,length=32)
	public java.lang.String getIdcardnum(){
		return this.idcardnum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  身份证号
	 */
	public void setIdcardnum(java.lang.String idcardnum){
		this.idcardnum = idcardnum;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  民族
	 */
	
	@Column(name ="NATION",nullable=true,length=10)
	public java.lang.String getNation(){
		return this.nation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  民族
	 */
	public void setNation(java.lang.String nation){
		this.nation = nation;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  住址
	 */
	
	@Column(name ="ADDRESS",nullable=true,length=50)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  住址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	
}
