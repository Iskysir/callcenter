package com.chrhc.project.sc.qzhgl.entity;
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

/**   
 * @Title: Entity
 * @Description: 签章管理
 * @author onlineGenerator
 * @date 2015-08-25 13:13:35
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_qzhgl", schema = "")
@SuppressWarnings("serial")
public class ScQzhglEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**创建日期*/
	private java.util.Date createDate;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**版本号*/
	private java.lang.Integer versionNum;
	/**部门名称*/
	private java.lang.String sysOrgCode;
	/**更新日期*/
	private java.util.Date updateDate;
	/**业务类型*/
	private java.lang.String busType;
	/**人口库id*/
	private java.lang.String rkkId;
	/**姓名*/
	private java.lang.String name;
	/**身份证号*/
	private java.lang.String certId;
	/**联系电话*/
	private java.lang.String telephone;
	/**文档名称*/
	private java.lang.String docName;
	/**文档类型名*/
	private java.lang.String docTypename;
	/**文档类型*/
	private java.lang.String docType;
	/**文档id*/
	private java.lang.String docId;
	/**办理日期*/
	private java.util.Date dealDatetime;
	/**预留字段a*/
	private java.lang.String obligatea;
	/**预留字段b*/
	private java.lang.String obligateb;
	/**预留字段c*/
	private java.lang.String obligatec;
	/**删除标志*/
	private java.lang.String delflag;
	/**预留字段d*/
	private java.lang.String obligated;
	/**预留字段e*/
	private java.lang.String obligatee;
	/**预留字段f*/
	private java.lang.String obligatef;
	/**预留字段g*/
	private java.lang.String obligateg;
	/**预留字段h*/
	private java.lang.String obligateh;
	/**预留字段i*/
	private java.lang.String obligatei;
	/**预留字段j*/
	private java.lang.String obligatej;
	
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本号
	 */
	
	@Column(name ="VERSION_NUM",nullable=true,length=16)
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
	 *@return: java.lang.String  业务类型
	 */
	
	@Column(name ="BUS_TYPE",nullable=false,length=50)
	public java.lang.String getBusType(){
		return this.busType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务类型
	 */
	public void setBusType(java.lang.String busType){
		this.busType = busType;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  人口库id
	 */
	
	@Column(name ="RKK_ID",nullable=true,length=36)
	public java.lang.String getRkkId(){
		return this.rkkId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  人口库id
	 */
	public void setRkkId(java.lang.String rkkId){
		this.rkkId = rkkId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  姓名
	 */
	
	@Column(name ="NAME",nullable=false,length=50)
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
	 *@return: java.lang.String  身份证号
	 */
	
	@Column(name ="CERT_ID",nullable=false,length=20)
	public java.lang.String getCertId(){
		return this.certId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  身份证号
	 */
	public void setCertId(java.lang.String certId){
		this.certId = certId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系电话
	 */
	
	@Column(name ="TELEPHONE",nullable=true,length=20)
	public java.lang.String getTelephone(){
		return this.telephone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系电话
	 */
	public void setTelephone(java.lang.String telephone){
		this.telephone = telephone;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文档名称
	 */
	
	@Column(name ="DOC_NAME",nullable=true,length=50)
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
	 *@return: java.lang.String  文档类型名
	 */
	
	@Column(name ="DOC_TYPENAME",nullable=true,length=50)
	public java.lang.String getDocTypename(){
		return this.docTypename;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文档类型名
	 */
	public void setDocTypename(java.lang.String docTypename){
		this.docTypename = docTypename;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文档类型
	 */
	
	@Column(name ="DOC_TYPE",nullable=true,length=20)
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
	 *@return: java.lang.String  文档id
	 */
	
	@Column(name ="DOC_ID",nullable=true,length=36)
	public java.lang.String getDocId(){
		return this.docId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文档id
	 */
	public void setDocId(java.lang.String docId){
		this.docId = docId;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  办理日期
	 */
	
	@Column(name ="DEAL_DATETIME",nullable=true,length=20)
	public java.util.Date getDealDatetime(){
		return this.dealDatetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  办理日期
	 */
	public void setDealDatetime(java.util.Date dealDatetime){
		this.dealDatetime = dealDatetime;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预留字段a
	 */
	
	@Column(name ="OBLIGATEA",nullable=true,length=200)
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
	
	@Column(name ="OBLIGATEB",nullable=true,length=200)
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
	
	@Column(name ="OBLIGATEC",nullable=true,length=200)
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
	 *@return: java.lang.String  预留字段d
	 */
	
	@Column(name ="OBLIGATED",nullable=true,length=200)
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
	
	@Column(name ="OBLIGATEE",nullable=true,length=200)
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
	 *@return: java.lang.String  预留字段f
	 */
	
	@Column(name ="OBLIGATEF",nullable=true,length=200)
	public java.lang.String getObligatef(){
		return this.obligatef;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预留字段f
	 */
	public void setObligatef(java.lang.String obligatef){
		this.obligatef = obligatef;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预留字段g
	 */
	
	@Column(name ="OBLIGATEG",nullable=true,length=200)
	public java.lang.String getObligateg(){
		return this.obligateg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预留字段g
	 */
	public void setObligateg(java.lang.String obligateg){
		this.obligateg = obligateg;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预留字段h
	 */
	
	@Column(name ="OBLIGATEH",nullable=true,length=200)
	public java.lang.String getObligateh(){
		return this.obligateh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预留字段h
	 */
	public void setObligateh(java.lang.String obligateh){
		this.obligateh = obligateh;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预留字段i
	 */
	
	@Column(name ="OBLIGATEI",nullable=true,length=200)
	public java.lang.String getObligatei(){
		return this.obligatei;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预留字段i
	 */
	public void setObligatei(java.lang.String obligatei){
		this.obligatei = obligatei;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预留字段j
	 */
	
	@Column(name ="OBLIGATEJ",nullable=true,length=200)
	public java.lang.String getObligatej(){
		return this.obligatej;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预留字段j
	 */
	public void setObligatej(java.lang.String obligatej){
		this.obligatej = obligatej;
	}
	
}
