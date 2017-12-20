package com.chrhc.project.sc.wmly.entity;

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
 * @Description: 文明楼院
 * @author onlineGenerator
 * @date 2015-08-25 13:08:19
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_wmly", schema = "")
@SuppressWarnings("serial")
public class ScWmlyEntity implements java.io.Serializable {
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
	/**版本号*/
	private java.lang.Integer versionNum;
	/**部门名称*/
	private java.lang.String sysOrgCode;
	/**更新日期*/
	private java.util.Date updateDate;
	/**门牌号*/
	@Excel(name="门牌号")
	private java.lang.String houseNo;
	/**楼院地址*/
	@Excel(name="楼院地址")
	private java.lang.String address;
	/**房屋类型*/
	@Excel(name="房屋类型")
	private java.lang.String type;
	/**楼院面积(㎡)*/
	@Excel(name="楼院面积(㎡)")
	private java.lang.Double area;
	/**层数(层)*/
	@Excel(name="层数(层)")
	private java.lang.Integer floor;
	/**楼门数(个)*/
	@Excel(name="楼门数(个)")
	private java.lang.Integer doorNum;
	/**楼院户数(户)*/
	@Excel(name="楼院户数(户)")
	private java.lang.Integer houseNum;
	/**楼门院长*/
	@Excel(name="楼门院长")
	private java.lang.String dean;
	/**评选时间*/
	@Excel(name="评选时间")
	private java.util.Date selectDatetime;
	/**产权单位*/
	@Excel(name="产权单位")
	private java.lang.String ownerUnit;
	/**评选级别*/
	@Excel(name="评选级别")
	private java.lang.String selectLevel;
	/**小区名称*/
	@Excel(name="小区名称")
	private java.lang.String name;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String remark;
	/**地理信息*/
	@Excel(name="地理信息")
	private java.lang.String gisxy;
	/**删除标志*/
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
	 *@return: java.lang.String  门牌号
	 */
	@Column(name ="HOUSE_NO",nullable=false,length=20)
	public java.lang.String getHouseNo(){
		return this.houseNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  门牌号
	 */
	public void setHouseNo(java.lang.String houseNo){
		this.houseNo = houseNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  楼院地址
	 */
	@Column(name ="ADDRESS",nullable=false,length=100)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  楼院地址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  房屋类型
	 */
	@Column(name ="TYPE",nullable=true,length=10)
	public java.lang.String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  房屋类型
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  楼院面积(㎡)
	 */
	@Column(name ="AREA",nullable=true,scale=4,length=14)
	public java.lang.Double getArea(){
		return this.area;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  楼院面积(㎡)
	 */
	public void setArea(java.lang.Double area){
		this.area = area;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  层数(层)
	 */
	@Column(name ="FLOOR",nullable=true,length=9)
	public java.lang.Integer getFloor(){
		return this.floor;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  层数(层)
	 */
	public void setFloor(java.lang.Integer floor){
		this.floor = floor;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  楼门数(个)
	 */
	@Column(name ="DOOR_NUM",nullable=true,length=9)
	public java.lang.Integer getDoorNum(){
		return this.doorNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  楼门数(个)
	 */
	public void setDoorNum(java.lang.Integer doorNum){
		this.doorNum = doorNum;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  楼院户数(户)
	 */
	@Column(name ="HOUSE_NUM",nullable=false,length=9)
	public java.lang.Integer getHouseNum(){
		return this.houseNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  楼院户数(户)
	 */
	public void setHouseNum(java.lang.Integer houseNum){
		this.houseNum = houseNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  楼门院长
	 */
	@Column(name ="DEAN",nullable=true,length=50)
	public java.lang.String getDean(){
		return this.dean;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  楼门院长
	 */
	public void setDean(java.lang.String dean){
		this.dean = dean;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  评选时间
	 */
	@Column(name ="SELECT_DATETIME",nullable=false,length=20)
	public java.util.Date getSelectDatetime(){
		return this.selectDatetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  评选时间
	 */
	public void setSelectDatetime(java.util.Date selectDatetime){
		this.selectDatetime = selectDatetime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  产权单位
	 */
	@Column(name ="OWNER_UNIT",nullable=true,length=300)
	public java.lang.String getOwnerUnit(){
		return this.ownerUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产权单位
	 */
	public void setOwnerUnit(java.lang.String ownerUnit){
		this.ownerUnit = ownerUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  评选级别
	 */
	@Column(name ="SELECT_LEVEL",nullable=true,length=10)
	public java.lang.String getSelectLevel(){
		return this.selectLevel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  评选级别
	 */
	public void setSelectLevel(java.lang.String selectLevel){
		this.selectLevel = selectLevel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  小区名称
	 */
	@Column(name ="NAME",nullable=false,length=50)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  小区名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=500)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地理信息
	 */
	@Column(name ="GISXY",nullable=true,length=200)
	public java.lang.String getGisxy(){
		return this.gisxy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地理信息
	 */
	public void setGisxy(java.lang.String gisxy){
		this.gisxy = gisxy;
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
}
