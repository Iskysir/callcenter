package com.chrhc.project.sc.temp.entity;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 户口迁入证明
 * @author onlineGenerator
 * @date 2015-11-04 14:55:20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_move_into", schema = "")
@SuppressWarnings("serial")
public class ScMoveIntoEntity implements java.io.Serializable {
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
	/**版本号*/
	private java.lang.Integer versionNum;
	/**部门名称*/
	private java.lang.String sysOrgCode;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	/**编号*/
	@Excel(name="编号")
	private java.lang.String code;
	/**迁入者姓名*/
	@Excel(name="迁入者姓名")
	private java.lang.String moveName;
	/**迁入者身份证号*/
	@Excel(name="迁入者身份证号")
	private java.lang.String moveIdcard;
	/**小区名称*/
	@Excel(name="小区名称")
	private java.lang.String zone;
	/**楼号*/
	@Excel(name="楼号")
	private java.lang.String buildNum;
	/**单元号*/
	@Excel(name="单元号")
	private java.lang.String unitNum;
	/**房间号*/
	@Excel(name="房间号")
	private java.lang.String floorNum;
	/**社区名称*/
	@Excel(name="社区名称")
	private java.lang.String communityName;
	/**填写时间*/
	@Excel(name="填写时间")
	private java.util.Date fillInDate;
	/**电子资料*/
	@Excel(name="电子资料")
	private java.lang.String docTypename;
	/**出证状态*/
	private java.lang.String printStatus;
	/**出证次数*/
	private java.lang.Integer printNum;
	/**预留A*/
	private java.lang.String obligatea;
	/**预留B*/
	private java.lang.String obligateb;
	/**删除标志*/
	private java.lang.String delflag;
	/**预留C*/
	private java.lang.String obligatec;
	/**预留D*/
	private java.lang.String obligated;
	/**预留E*/
	private java.lang.String obligatee;
	/**人口id*/
	private java.lang.String rkId;
	/**文档id*/
	private java.lang.String docId;
	
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
	 *@return: java.lang.String  编号
	 */
	@Column(name ="CODE",nullable=true,length=32)
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  编号
	 */
	public void setCode(java.lang.String code){
		this.code = code;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  迁入者姓名
	 */
	@Column(name ="MOVE_NAME",nullable=false,length=32)
	public java.lang.String getMoveName(){
		return this.moveName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  迁入者姓名
	 */
	public void setMoveName(java.lang.String moveName){
		this.moveName = moveName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  迁入者身份证号
	 */
	@Column(name ="MOVE_IDCARD",nullable=false,length=32)
	public java.lang.String getMoveIdcard(){
		return this.moveIdcard;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  迁入者身份证号
	 */
	public void setMoveIdcard(java.lang.String moveIdcard){
		this.moveIdcard = moveIdcard;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  小区名称
	 */
	@Column(name ="ZONE",nullable=false,length=32)
	public java.lang.String getZone(){
		return this.zone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  小区名称
	 */
	public void setZone(java.lang.String zone){
		this.zone = zone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  楼号
	 */
	@Column(name ="BUILD_NUM",nullable=true,length=32)
	public java.lang.String getBuildNum(){
		return this.buildNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  楼号
	 */
	public void setBuildNum(java.lang.String buildNum){
		this.buildNum = buildNum;
	}
	@Transient
	public java.lang.String getBuildNumText(){
			if(StringUtil.isNotEmpty(buildNum)){
				return  "小区"+buildNum  + "楼";
			}
			
			return "";
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单元号
	 */
	@Column(name ="UNIT_NUM",nullable=false,length=32)
	public java.lang.String getUnitNum(){
		return this.unitNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单元号
	 */
	public void setUnitNum(java.lang.String unitNum){
		this.unitNum = unitNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  房间号
	 */
	@Column(name ="FLOOR_NUM",nullable=false,length=32)
	public java.lang.String getFloorNum(){
		return this.floorNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  房间号
	 */
	public void setFloorNum(java.lang.String floorNum){
		this.floorNum = floorNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  社区名称
	 */
	@Column(name ="COMMUNITY_NAME",nullable=false,length=32)
	public java.lang.String getCommunityName(){
		return this.communityName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  社区名称
	 */
	public void setCommunityName(java.lang.String communityName){
		this.communityName = communityName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  填写时间
	 */
	@Column(name ="FILL_IN_DATE",nullable=false,length=32)
	public java.util.Date getFillInDate(){
		return this.fillInDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  填写时间
	 */
	public void setFillInDate(java.util.Date fillInDate){
		this.fillInDate = fillInDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电子资料
	 */
	@Column(name ="DOC_TYPENAME",nullable=false,length=500)
	public java.lang.String getDocTypename(){
		return this.docTypename;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电子资料
	 */
	public void setDocTypename(java.lang.String docTypename){
		this.docTypename = docTypename;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出证状态
	 */
	@Column(name ="PRINT_STATUS",nullable=true,length=10)
	public java.lang.String getPrintStatus(){
		return this.printStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出证状态
	 */
	public void setPrintStatus(java.lang.String printStatus){
		this.printStatus = printStatus;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  出证次数
	 */
	@Column(name ="PRINT_NUM",nullable=true,length=32)
	public java.lang.Integer getPrintNum(){
		return this.printNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  出证次数
	 */
	public void setPrintNum(java.lang.Integer printNum){
		this.printNum = printNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预留A
	 */
	@Column(name ="OBLIGATEA",nullable=true,length=32)
	public java.lang.String getObligatea(){
		return this.obligatea;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预留A
	 */
	public void setObligatea(java.lang.String obligatea){
		this.obligatea = obligatea;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预留B
	 */
	@Column(name ="OBLIGATEB",nullable=true,length=32)
	public java.lang.String getObligateb(){
		return this.obligateb;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预留B
	 */
	public void setObligateb(java.lang.String obligateb){
		this.obligateb = obligateb;
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
	 *@return: java.lang.String  预留C
	 */
	@Column(name ="OBLIGATEC",nullable=true,length=32)
	public java.lang.String getObligatec(){
		return this.obligatec;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预留C
	 */
	public void setObligatec(java.lang.String obligatec){
		this.obligatec = obligatec;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预留D
	 */
	@Column(name ="OBLIGATED",nullable=true,length=32)
	public java.lang.String getObligated(){
		return this.obligated;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预留D
	 */
	public void setObligated(java.lang.String obligated){
		this.obligated = obligated;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预留E
	 */
	@Column(name ="OBLIGATEE",nullable=true,length=32)
	public java.lang.String getObligatee(){
		return this.obligatee;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预留E
	 */
	public void setObligatee(java.lang.String obligatee){
		this.obligatee = obligatee;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  人口id
	 */
	@Column(name ="RK_ID",nullable=true,length=50)
	public java.lang.String getRkId(){
		return this.rkId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  人口id
	 */
	public void setRkId(java.lang.String rkId){
		this.rkId = rkId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文档id
	 */
	@Column(name ="DOC_ID",nullable=true,length=50)
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
}
