package com.chrhc.project.sc.endowlogout.entity;

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
 * @Description: 养老保险注销记录
 * @author onlineGenerator
 * @date 2015-08-20 09:32:56
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_endowlogout", schema = "")
@SuppressWarnings("serial")
public class ScEndowlogoutEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
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
	/**组织编号*/
	private java.lang.String sysOrgCode;
	/**删除标志*/
	private java.lang.String delflag;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新日期*/
	private java.util.Date updateDate;
	/**参保编号*/
	@Excel(name="参保编号")
	private java.lang.String code;
	/**参保人姓名*/
	@Excel(name="参保人姓名")
	private java.lang.String name;
	/**身份证号*/
	@Excel(name="身份证号")
	private java.lang.String pidcard;
	/**注销原因*/
	@Excel(name="注销原因")
	private java.lang.String cancellreason;
	/**注销日期*/
	@Excel(name="注销日期")
	private java.lang.String cancelldate;
	/**受益人姓名*/
	@Excel(name="受益人姓名")
	private java.lang.String beniname;
	/**受益人性别*/
	@Excel(name="受益人性别")
	private java.lang.String benisex;
	/**受益人出生日期*/
	@Excel(name="受益人出生日期")
	private java.lang.String benibirthday;
	/**受益人身份证号*/
	@Excel(name="受益人身份证号")
	private java.lang.String beniidcard;
	/**与参保人关系*/
	@Excel(name="与参保人关系")
	private java.lang.String relation;
	/**户籍所在地*/
	@Excel(name="户籍所在地")
	private java.lang.String paddress;
	/**联系人电话*/
	@Excel(name="联系人电话")
	private java.lang.String telphone;
	/**居住地址*/
	@Excel(name="居住地址")
	private java.lang.String address;
	/**领取个人账户余额的指定银行*/
	@Excel(name="领取个人账户余额的指定银行")
	private java.lang.String nominatedbank;
	/**银行账号*/
	@Excel(name="银行账号")
	private java.lang.String bankcode;
	/**人口信息ID外键*/
	private java.lang.String rkId;
	/**养老保险id*/
	private java.lang.String endowid;
	
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
	 *@return: java.lang.String  参保编号
	 */
	@Column(name ="CODE",nullable=false,length=50)
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参保编号
	 */
	public void setCode(java.lang.String code){
		this.code = code;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参保人姓名
	 */
	@Column(name ="NAME",nullable=false,length=50)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参保人姓名
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  身份证号
	 */
	@Column(name ="PIDCARD",nullable=false,length=50)
	public java.lang.String getPidcard(){
		return this.pidcard;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  身份证号
	 */
	public void setPidcard(java.lang.String pidcard){
		this.pidcard = pidcard;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  注销原因
	 */
	@Column(name ="CANCELLREASON",nullable=false,length=50)
	public java.lang.String getCancellreason(){
		return this.cancellreason;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  注销原因
	 */
	public void setCancellreason(java.lang.String cancellreason){
		this.cancellreason = cancellreason;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  注销日期
	 */
	@Column(name ="CANCELLDATE",nullable=false,length=50)
	public java.lang.String getCancelldate(){
		return this.cancelldate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  注销日期
	 */
	public void setCancelldate(java.lang.String cancelldate){
		this.cancelldate = cancelldate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  受益人姓名
	 */
	@Column(name ="BENINAME",nullable=false,length=50)
	public java.lang.String getBeniname(){
		return this.beniname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  受益人姓名
	 */
	public void setBeniname(java.lang.String beniname){
		this.beniname = beniname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  受益人性别
	 */
	@Column(name ="BENISEX",nullable=true,length=50)
	public java.lang.String getBenisex(){
		return this.benisex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  受益人性别
	 */
	public void setBenisex(java.lang.String benisex){
		this.benisex = benisex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  受益人出生日期
	 */
	@Column(name ="BENIBIRTHDAY",nullable=true,length=50)
	public java.lang.String getBenibirthday(){
		return this.benibirthday;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  受益人出生日期
	 */
	public void setBenibirthday(java.lang.String benibirthday){
		this.benibirthday = benibirthday;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  受益人身份证号
	 */
	@Column(name ="BENIIDCARD",nullable=false,length=50)
	public java.lang.String getBeniidcard(){
		return this.beniidcard;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  受益人身份证号
	 */
	public void setBeniidcard(java.lang.String beniidcard){
		this.beniidcard = beniidcard;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  与参保人关系
	 */
	@Column(name ="RELATION",nullable=true,length=50)
	public java.lang.String getRelation(){
		return this.relation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  与参保人关系
	 */
	public void setRelation(java.lang.String relation){
		this.relation = relation;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  户籍所在地
	 */
	@Column(name ="PADDRESS",nullable=true,length=50)
	public java.lang.String getPaddress(){
		return this.paddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  户籍所在地
	 */
	public void setPaddress(java.lang.String paddress){
		this.paddress = paddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人电话
	 */
	@Column(name ="TELPHONE",nullable=true,length=50)
	public java.lang.String getTelphone(){
		return this.telphone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人电话
	 */
	public void setTelphone(java.lang.String telphone){
		this.telphone = telphone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  居住地址
	 */
	@Column(name ="ADDRESS",nullable=true,length=50)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  居住地址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  领取个人账户余额的指定银行
	 */
	@Column(name ="NOMINATEDBANK",nullable=true,length=50)
	public java.lang.String getNominatedbank(){
		return this.nominatedbank;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  领取个人账户余额的指定银行
	 */
	public void setNominatedbank(java.lang.String nominatedbank){
		this.nominatedbank = nominatedbank;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  银行账号
	 */
	@Column(name ="BANKCODE",nullable=true,length=50)
	public java.lang.String getBankcode(){
		return this.bankcode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  银行账号
	 */
	public void setBankcode(java.lang.String bankcode){
		this.bankcode = bankcode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  人口信息ID外键
	 */
	@Column(name ="RK_ID",nullable=true,length=50)
	public java.lang.String getRkId(){
		return this.rkId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  人口信息ID外键
	 */
	public void setRkId(java.lang.String rkId){
		this.rkId = rkId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  养老保险id
	 */
	@Column(name ="ENDOWID",nullable=false,length=36)
	public java.lang.String getEndowid(){
		return this.endowid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  养老保险id
	 */
	public void setEndowid(java.lang.String endowid){
		this.endowid = endowid;
	}
}
