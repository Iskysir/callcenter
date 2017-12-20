package com.chrhc.project.sc.rkyw.entity;

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
 * @Description: 人口回写配置表
 * @author onlineGenerator
 * @date 2015-08-03 15:49:20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_rk_yw_config", schema = "")
@SuppressWarnings("serial")
public class ScRkYwConfigEntity implements java.io.Serializable {
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
	/**人口信息ID字段*/
	@Excel(name="人口信息ID字段")
	private java.lang.String rkxxFiled;
	/**业务表code*/
	@Excel(name="业务表code")
	private java.lang.String businesscode;
	/**业务类型*/
	@Excel(name="业务类型")
	private java.lang.String businesstype;
	/**业务模块*/
	@Excel(name="业务模块")
	private java.lang.String modeltype;
	/**改写字段*/
	@Excel(name="改写字段")
	private java.lang.String reaptfiled;
	/**重写字段的表达式*/
	@Excel(name="重写字段的表达式")
	private java.lang.String expression;
	/**电子文档证明材料类型*/
	@Excel(name="电子文档证明材料类型")
	private java.lang.String docphototype;
	/**业务描述*/
	@Excel(name="业务描述")
	private java.lang.String businessdes;
	/**重点服务人群*/
	@Excel(name="重点服务人群")
	private java.lang.String sfzdfwrq;
	/**重点稳控人群*/
	@Excel(name="重点稳控人群")
	private java.lang.String sfzdwkrq;
	/**低保*/
	@Excel(name="低保")
	private java.lang.String dblb;
	/**五保户*/
	@Excel(name="五保户")
	private java.lang.String wbh;
	/**残疾状况*/
	@Excel(name="残疾状况")
	private java.lang.String cjzk;
	/**困难户*/
	@Excel(name="困难户")
	private java.lang.String kdh;
	/**失业人员*/
	@Excel(name="失业人员")
	private java.lang.String syry;
	/**退休人员*/
	@Excel(name="退休人员")
	private java.lang.String qytxry;
	/**老年人*/
	@Excel(name="老年人")
	private java.lang.String lnr;
	/**优抚*/
	@Excel(name="优抚")
	private java.lang.String yf;
	/**育龄妇女*/
	@Excel(name="育龄妇女")
	private java.lang.String ylfn;
	/**志愿者*/
	@Excel(name="志愿者")
	private java.lang.String zyz;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String bz;
	
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
	 *@return: java.lang.String  人口信息ID字段
	 */
	@Column(name ="RKXX_FIElD",nullable=true,length=50)
	public java.lang.String getRkxxFiled(){
		return this.rkxxFiled;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  人口信息ID字段
	 */
	public void setRkxxFiled(java.lang.String rkxxFiled){
		this.rkxxFiled = rkxxFiled;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务表code
	 */
	@Column(name ="BUSINESSCODE",nullable=true,length=50)
	public java.lang.String getBusinesscode(){
		return this.businesscode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务表code
	 */
	public void setBusinesscode(java.lang.String businesscode){
		this.businesscode = businesscode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务类型
	 */
	@Column(name ="BUSINESSTYPE",nullable=true,length=50)
	public java.lang.String getBusinesstype(){
		return this.businesstype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务类型
	 */
	public void setBusinesstype(java.lang.String businesstype){
		this.businesstype = businesstype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务模块
	 */
	@Column(name ="MODELTYPE",nullable=true,length=50)
	public java.lang.String getModeltype(){
		return this.modeltype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务模块
	 */
	public void setModeltype(java.lang.String modeltype){
		this.modeltype = modeltype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  改写字段
	 */
	@Column(name ="REAPTFILED",nullable=true,length=50)
	public java.lang.String getReaptfiled(){
		return this.reaptfiled;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  改写字段
	 */
	public void setReaptfiled(java.lang.String reaptfiled){
		this.reaptfiled = reaptfiled;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  重写字段的表达式
	 */
	@Column(name ="EXPRESSION",nullable=true,length=50)
	public java.lang.String getExpression(){
		return this.expression;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  重写字段的表达式
	 */
	public void setExpression(java.lang.String expression){
		this.expression = expression;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电子文档证明材料类型
	 */
	@Column(name ="DOCPHOTOTYPE",nullable=true,length=50)
	public java.lang.String getDocphototype(){
		return this.docphototype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电子文档证明材料类型
	 */
	public void setDocphototype(java.lang.String docphototype){
		this.docphototype = docphototype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务描述
	 */
	@Column(name ="BUSINESSDES",nullable=true,length=50)
	public java.lang.String getBusinessdes(){
		return this.businessdes;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务描述
	 */
	public void setBusinessdes(java.lang.String businessdes){
		this.businessdes = businessdes;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  重点服务人群
	 */
	@Column(name ="SFZDFWRQ",nullable=true,length=50)
	public java.lang.String getSfzdfwrq(){
		return this.sfzdfwrq;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  重点服务人群
	 */
	public void setSfzdfwrq(java.lang.String sfzdfwrq){
		this.sfzdfwrq = sfzdfwrq;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  重点稳控人群
	 */
	@Column(name ="SFZDWKRQ",nullable=true,length=50)
	public java.lang.String getSfzdwkrq(){
		return this.sfzdwkrq;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  重点稳控人群
	 */
	public void setSfzdwkrq(java.lang.String sfzdwkrq){
		this.sfzdwkrq = sfzdwkrq;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  低保
	 */
	@Column(name ="DBLB",nullable=true,length=50)
	public java.lang.String getDblb(){
		return this.dblb;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  低保
	 */
	public void setDblb(java.lang.String dblb){
		this.dblb = dblb;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  五保户
	 */
	@Column(name ="WBH",nullable=true,length=50)
	public java.lang.String getWbh(){
		return this.wbh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  五保户
	 */
	public void setWbh(java.lang.String wbh){
		this.wbh = wbh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  残疾状况
	 */
	@Column(name ="CJZK",nullable=true,length=50)
	public java.lang.String getCjzk(){
		return this.cjzk;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  残疾状况
	 */
	public void setCjzk(java.lang.String cjzk){
		this.cjzk = cjzk;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  困难户
	 */
	@Column(name ="KDH",nullable=true,length=50)
	public java.lang.String getKdh(){
		return this.kdh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  困难户
	 */
	public void setKdh(java.lang.String kdh){
		this.kdh = kdh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  失业人员
	 */
	@Column(name ="SYRY",nullable=true,length=50)
	public java.lang.String getSyry(){
		return this.syry;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  失业人员
	 */
	public void setSyry(java.lang.String syry){
		this.syry = syry;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  退休人员
	 */
	@Column(name ="QYTXRY",nullable=true,length=50)
	public java.lang.String getQytxry(){
		return this.qytxry;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  退休人员
	 */
	public void setQytxry(java.lang.String qytxry){
		this.qytxry = qytxry;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  老年人
	 */
	@Column(name ="LNR",nullable=true,length=50)
	public java.lang.String getLnr(){
		return this.lnr;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  老年人
	 */
	public void setLnr(java.lang.String lnr){
		this.lnr = lnr;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  优抚
	 */
	@Column(name ="YF",nullable=true,length=50)
	public java.lang.String getYf(){
		return this.yf;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  优抚
	 */
	public void setYf(java.lang.String yf){
		this.yf = yf;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  育龄妇女
	 */
	@Column(name ="YLFN",nullable=true,length=50)
	public java.lang.String getYlfn(){
		return this.ylfn;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  育龄妇女
	 */
	public void setYlfn(java.lang.String ylfn){
		this.ylfn = ylfn;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  志愿者
	 */
	@Column(name ="ZYZ",nullable=true,length=50)
	public java.lang.String getZyz(){
		return this.zyz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  志愿者
	 */
	public void setZyz(java.lang.String zyz){
		this.zyz = zyz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="BZ",nullable=true,length=50)
	public java.lang.String getBz(){
		return this.bz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setBz(java.lang.String bz){
		this.bz = bz;
	}
}
