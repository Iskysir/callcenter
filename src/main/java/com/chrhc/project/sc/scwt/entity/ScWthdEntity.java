package com.chrhc.project.sc.scwt.entity;

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
 * @Description: 文体活动
 * @author onlineGenerator
 * @date 2015-09-21 14:29:26
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_wthd", schema = "")
@SuppressWarnings("serial")
public class ScWthdEntity implements java.io.Serializable {
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
	/**文体队伍*/
	private java.lang.String teamId;
	/**文体队伍*/
	@Excel(name="文体队伍")
	private java.lang.String teamName;
	/**活动时间*/
	@Excel(name="活动时间")
	private java.util.Date actDatetime;
	/**主办单位*/
	@Excel(name="主办单位")
	private java.lang.String hostUnit;
	/**承办单位*/
	@Excel(name="承办单位")
	private java.lang.String orgUnit;
	/**活动地点*/
	@Excel(name="活动地点")
	private java.lang.String actAddress;
	/**活动范围*/
	@Excel(name="活动范围")
	private java.lang.String actScope;
	/**活动主题*/
	@Excel(name="活动主题")
	private java.lang.String actTitle;
	/**照片数(张)*/
	@Excel(name="照片数(张)")
	private java.lang.Integer picNum;
	/**活动形式*/
	@Excel(name="活动形式")
	private java.lang.String actForm;
	/**活动结果*/
	@Excel(name="活动结果")
	private java.lang.String actResult;
	/**经费投入(元)*/
	@Excel(name="经费投入(元)")
	private java.lang.Double funds;
	/**参加单位*/
	@Excel(name="参加单位")
	private java.lang.String joinUnit;
	/**活动内容*/
	@Excel(name="活动内容")
	private java.lang.String actContent;
	/**参加人数(人)*/
	@Excel(name="参加人数(人)")
	private java.lang.Integer joinNum;
	/**删除标志*/
	private java.lang.String delflag;
	/**观众人数(人)*/
	@Excel(name="观众人数(人)")
	private java.lang.Integer viewerNum;
	/**活动场次(次)*/
	@Excel(name="活动场次(次)")
	private java.lang.Integer actNum;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String remark;
	/**地理信息*/
	@Excel(name="地理信息")
	private java.lang.String gisxy;
	
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
	 *@return: java.lang.String  文体队伍
	 */
	@Column(name ="TEAM_ID",nullable=true,length=36)
	public java.lang.String getTeamId(){
		return this.teamId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文体队伍
	 */
	public void setTeamId(java.lang.String teamId){
		this.teamId = teamId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文体队伍
	 */
	@Column(name ="TEAM_NAME",nullable=false,length=50)
	public java.lang.String getTeamName(){
		return this.teamName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文体队伍
	 */
	public void setTeamName(java.lang.String teamName){
		this.teamName = teamName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  活动时间
	 */
	@Column(name ="ACT_DATETIME",nullable=false,length=20)
	public java.util.Date getActDatetime(){
		return this.actDatetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  活动时间
	 */
	public void setActDatetime(java.util.Date actDatetime){
		this.actDatetime = actDatetime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主办单位
	 */
	@Column(name ="HOST_UNIT",nullable=false,length=50)
	public java.lang.String getHostUnit(){
		return this.hostUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主办单位
	 */
	public void setHostUnit(java.lang.String hostUnit){
		this.hostUnit = hostUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  承办单位
	 */
	@Column(name ="ORG_UNIT",nullable=false,length=50)
	public java.lang.String getOrgUnit(){
		return this.orgUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  承办单位
	 */
	public void setOrgUnit(java.lang.String orgUnit){
		this.orgUnit = orgUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动地点
	 */
	@Column(name ="ACT_ADDRESS",nullable=false,length=100)
	public java.lang.String getActAddress(){
		return this.actAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动地点
	 */
	public void setActAddress(java.lang.String actAddress){
		this.actAddress = actAddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动范围
	 */
	@Column(name ="ACT_SCOPE",nullable=true,length=100)
	public java.lang.String getActScope(){
		return this.actScope;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动范围
	 */
	public void setActScope(java.lang.String actScope){
		this.actScope = actScope;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动主题
	 */
	@Column(name ="ACT_TITLE",nullable=false,length=100)
	public java.lang.String getActTitle(){
		return this.actTitle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动主题
	 */
	public void setActTitle(java.lang.String actTitle){
		this.actTitle = actTitle;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  照片数(张)
	 */
	@Column(name ="PIC_NUM",nullable=true,length=9)
	public java.lang.Integer getPicNum(){
		return this.picNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  照片数(张)
	 */
	public void setPicNum(java.lang.Integer picNum){
		this.picNum = picNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动形式
	 */
	@Column(name ="ACT_FORM",nullable=true,length=10)
	public java.lang.String getActForm(){
		return this.actForm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动形式
	 */
	public void setActForm(java.lang.String actForm){
		this.actForm = actForm;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动结果
	 */
	@Column(name ="ACT_RESULT",nullable=true,length=10)
	public java.lang.String getActResult(){
		return this.actResult;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动结果
	 */
	public void setActResult(java.lang.String actResult){
		this.actResult = actResult;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  经费投入(元)
	 */
	@Column(name ="FUNDS",nullable=true,scale=4,length=14)
	public java.lang.Double getFunds(){
		return this.funds;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  经费投入(元)
	 */
	public void setFunds(java.lang.Double funds){
		this.funds = funds;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参加单位
	 */
	@Column(name ="JOIN_UNIT",nullable=true,length=200)
	public java.lang.String getJoinUnit(){
		return this.joinUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参加单位
	 */
	public void setJoinUnit(java.lang.String joinUnit){
		this.joinUnit = joinUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动内容
	 */
	@Column(name ="ACT_CONTENT",nullable=true,length=500)
	public java.lang.String getActContent(){
		return this.actContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动内容
	 */
	public void setActContent(java.lang.String actContent){
		this.actContent = actContent;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  参加人数(人)
	 */
	@Column(name ="JOIN_NUM",nullable=true,length=9)
	public java.lang.Integer getJoinNum(){
		return this.joinNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  参加人数(人)
	 */
	public void setJoinNum(java.lang.Integer joinNum){
		this.joinNum = joinNum;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  观众人数(人)
	 */
	@Column(name ="VIEWER_NUM",nullable=true,length=9)
	public java.lang.Integer getViewerNum(){
		return this.viewerNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  观众人数(人)
	 */
	public void setViewerNum(java.lang.Integer viewerNum){
		this.viewerNum = viewerNum;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  活动场次(次)
	 */
	@Column(name ="ACT_NUM",nullable=true,length=9)
	public java.lang.Integer getActNum(){
		return this.actNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  活动场次(次)
	 */
	public void setActNum(java.lang.Integer actNum){
		this.actNum = actNum;
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
}
