package com.chrhc.project.sc.endow.entity;

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
 * @Description: 养老保险参保记录
 * @author onlineGenerator
 * @date 2015-08-19 14:11:18
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_endowmentins", schema = "")
@SuppressWarnings("serial")
public class ScEndowmentinsEntity implements java.io.Serializable {
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
	/**创建日期*/
	private java.util.Date createDate;
	/**更新日期*/
	private java.util.Date updateDate;
	/**养老保险编号*/
	@Excel(name="养老保险编号")
	private java.lang.String code;
	/**姓名*/
	@Excel(name="姓名")
	private java.lang.String name;
	/**身份证号*/
	@Excel(name="身份证号")
	private java.lang.String idcard;
	/**参保日期*/
	@Excel(name="参保日期")
	private java.util.Date insureddate;
	/**参保状况*/
	@Excel(name="参保状况")
	private java.lang.String insuredstate;
	/**性别*/
	@Excel(name="性别")
	private java.lang.String sex;
	/**民族*/
	@Excel(name="民族")
	private java.lang.String nation;
	/**出生日期*/
	@Excel(name="出生日期")
	private java.util.Date birthday;
	/**联系电话*/
	@Excel(name="联系电话")
	private java.lang.String phone;
	/**邮编*/
	@Excel(name="邮编")
	private java.lang.String postcode;
	/**户籍地址*/
	@Excel(name="户籍地址")
	private java.lang.String paddress;
	/**居住地址*/
	@Excel(name="居住地址")
	private java.lang.String address;
	/**户籍性质*/
	@Excel(name="户籍性质")
	private java.lang.String holdnation;
	/**个人缴费额*/
	@Excel(name="个人缴费额")
	private java.lang.String insamount;
	/**企业职工基本养老保险*/
	@Excel(name="企业职工基本养老保险")
	private java.lang.String enterpriseins;
	/**企业养老保险起始时间*/
	@Excel(name="企业养老保险起始时间")
	private java.util.Date enterprisedate;
	/**被征地农民社会保障*/
	@Excel(name="被征地农民社会保障")
	private java.lang.String landlessins;
	/**被征地保障起始时间*/
	@Excel(name="被征地保障起始时间")
	private java.util.Date landlessinsdate;
	/**老农保*/
	@Excel(name="老农保")
	private java.lang.String oldins;
	/**老农保起始时间*/
	@Excel(name="老农保起始时间")
	private java.util.Date oldinsdate;
	/**其他保险*/
	@Excel(name="其他保险")
	private java.lang.String otherins;
	/**其他起始时间*/
	@Excel(name="其他起始时间")
	private java.lang.String otherinsdate;
	/**地理位置*/
	private java.lang.String gisxy;
	/**特殊参保群体*/
	@Excel(name="特殊参保群体")
	private java.lang.String specialgroup;
	/**人口信息ID外键*/
	private java.lang.String rkId;
	/**部门名称*/
	private java.lang.String sysOrgCode;
	/**删除标记*/
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
	 *@return: java.lang.String  养老保险编号
	 */
	@Column(name ="CODE",nullable=false,length=50)
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  养老保险编号
	 */
	public void setCode(java.lang.String code){
		this.code = code;
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
	@Column(name ="IDCARD",nullable=false,length=20)
	public java.lang.String getIdcard(){
		return this.idcard;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  身份证号
	 */
	public void setIdcard(java.lang.String idcard){
		this.idcard = idcard;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  参保日期
	 */
	@Column(name ="INSUREDDATE",nullable=false,length=32)
	public java.util.Date getInsureddate(){
		return this.insureddate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  参保日期
	 */
	public void setInsureddate(java.util.Date insureddate){
		this.insureddate = insureddate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参保状况
	 */
	@Column(name ="INSUREDSTATE",nullable=false,length=50)
	public java.lang.String getInsuredstate(){
		return this.insuredstate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参保状况
	 */
	public void setInsuredstate(java.lang.String insuredstate){
		this.insuredstate = insuredstate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  性别
	 */
	@Column(name ="SEX",nullable=false,length=36)
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
	 *@return: java.lang.String  民族
	 */
	@Column(name ="NATION",nullable=true,length=36)
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  出生日期
	 */
	@Column(name ="BIRTHDAY",nullable=true,length=32)
	public java.util.Date getBirthday(){
		return this.birthday;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  出生日期
	 */
	public void setBirthday(java.util.Date birthday){
		this.birthday = birthday;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系电话
	 */
	@Column(name ="PHONE",nullable=true,length=20)
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系电话
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮编
	 */
	@Column(name ="POSTCODE",nullable=true,length=10)
	public java.lang.String getPostcode(){
		return this.postcode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮编
	 */
	public void setPostcode(java.lang.String postcode){
		this.postcode = postcode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  户籍地址
	 */
	@Column(name ="PADDRESS",nullable=true,length=50)
	public java.lang.String getPaddress(){
		return this.paddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  户籍地址
	 */
	public void setPaddress(java.lang.String paddress){
		this.paddress = paddress;
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
	 *@return: java.lang.String  户籍性质
	 */
	@Column(name ="HOLDNATION",nullable=false,length=50)
	public java.lang.String getHoldnation(){
		return this.holdnation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  户籍性质
	 */
	public void setHoldnation(java.lang.String holdnation){
		this.holdnation = holdnation;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  个人缴费额
	 */
	@Column(name ="INSAMOUNT",nullable=true,length=10)
	public java.lang.String getInsamount(){
		return this.insamount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  个人缴费额
	 */
	public void setInsamount(java.lang.String insamount){
		this.insamount = insamount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  企业职工基本养老保险
	 */
	@Column(name ="ENTERPRISEINS",nullable=true,length=50)
	public java.lang.String getEnterpriseins(){
		return this.enterpriseins;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  企业职工基本养老保险
	 */
	public void setEnterpriseins(java.lang.String enterpriseins){
		this.enterpriseins = enterpriseins;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  企业养老保险起始时间
	 */
	@Column(name ="ENTERPRISEDATE",nullable=true,length=32)
	public java.util.Date getEnterprisedate(){
		return this.enterprisedate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  企业养老保险起始时间
	 */
	public void setEnterprisedate(java.util.Date enterprisedate){
		this.enterprisedate = enterprisedate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  被征地农民社会保障
	 */
	@Column(name ="LANDLESSINS",nullable=true,length=50)
	public java.lang.String getLandlessins(){
		return this.landlessins;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  被征地农民社会保障
	 */
	public void setLandlessins(java.lang.String landlessins){
		this.landlessins = landlessins;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  被征地保障起始时间
	 */
	@Column(name ="LANDLESSINSDATE",nullable=true,length=32)
	public java.util.Date getLandlessinsdate(){
		return this.landlessinsdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  被征地保障起始时间
	 */
	public void setLandlessinsdate(java.util.Date landlessinsdate){
		this.landlessinsdate = landlessinsdate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  老农保
	 */
	@Column(name ="OLDINS",nullable=true,length=50)
	public java.lang.String getOldins(){
		return this.oldins;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  老农保
	 */
	public void setOldins(java.lang.String oldins){
		this.oldins = oldins;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  老农保起始时间
	 */
	@Column(name ="OLDINSDATE",nullable=true,length=32)
	public java.util.Date getOldinsdate(){
		return this.oldinsdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  老农保起始时间
	 */
	public void setOldinsdate(java.util.Date oldinsdate){
		this.oldinsdate = oldinsdate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  其他保险
	 */
	@Column(name ="OTHERINS",nullable=true,length=50)
	public java.lang.String getOtherins(){
		return this.otherins;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  其他保险
	 */
	public void setOtherins(java.lang.String otherins){
		this.otherins = otherins;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  其他起始时间
	 */
	@Column(name ="OTHERINSDATE",nullable=true,length=32)
	public java.lang.String getOtherinsdate(){
		return this.otherinsdate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  其他起始时间
	 */
	public void setOtherinsdate(java.lang.String otherinsdate){
		this.otherinsdate = otherinsdate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地理位置
	 */
	@Column(name ="GISXY",nullable=true,length=200)
	public java.lang.String getGisxy(){
		return this.gisxy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地理位置
	 */
	public void setGisxy(java.lang.String gisxy){
		this.gisxy = gisxy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  特殊参保群体
	 */
	@Column(name ="SPECIALGROUP",nullable=true,length=50)
	public java.lang.String getSpecialgroup(){
		return this.specialgroup;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  特殊参保群体
	 */
	public void setSpecialgroup(java.lang.String specialgroup){
		this.specialgroup = specialgroup;
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
	 *@return: java.lang.String  删除标记
	 */
	@Column(name ="DELFLAG",nullable=true,length=1)
	public java.lang.String getDelflag(){
		return this.delflag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  删除标记
	 */
	public void setDelflag(java.lang.String delflag){
		this.delflag = delflag;
	}
}
