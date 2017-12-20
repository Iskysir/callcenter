
package com.chrhc.project.sc.qzhgl.page;
import com.chrhc.project.sc.qzhgl.entity.ScYwlxpzxEntity;
import com.chrhc.project.sc.qzhgl.entity.ScYwlxpzxsubEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

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
 * @Description: 签章配置项
 * @author onlineGenerator
 * @date 2015-08-25 13:17:30
 * @version V1.0   
 *
 */
public class ScYwlxpzxPage implements java.io.Serializable {
	/**保存-签章配置项子项*/
	private List<ScYwlxpzxsubEntity> scYwlxpzxsubList = new ArrayList<ScYwlxpzxsubEntity>();
	public List<ScYwlxpzxsubEntity> getScYwlxpzxsubList() {
		return scYwlxpzxsubList;
	}
	public void setScYwlxpzxsubList(List<ScYwlxpzxsubEntity> scYwlxpzxsubList) {
		this.scYwlxpzxsubList = scYwlxpzxsubList;
	}

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
	/**业务编号*/
	private java.lang.String busType;
	/**业务名称*/
	private java.lang.String busName;
	/**备注*/
	private java.lang.String remark;
	/**排序号*/
	private java.lang.Integer sortNo;
	/**预留字段a*/
	private java.lang.String obligatea;
	/**预留字段b*/
	private java.lang.String obligateb;
	/**预留字段c*/
	private java.lang.String obligatec;
	/**预留字段d*/
	private java.lang.String obligated;
	/**预留字段e*/
	private java.lang.String obligatee;
	/**删除标志*/
	private java.lang.String delflag;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
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
	 *@return: java.lang.String  业务编号
	 */
	public java.lang.String getBusType(){
		return this.busType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务编号
	 */
	public void setBusType(java.lang.String busType){
		this.busType = busType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务名称
	 */
	public java.lang.String getBusName(){
		return this.busName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务名称
	 */
	public void setBusName(java.lang.String busName){
		this.busName = busName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序号
	 */
	public java.lang.Integer getSortNo(){
		return this.sortNo;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序号
	 */
	public void setSortNo(java.lang.Integer sortNo){
		this.sortNo = sortNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预留字段a
	 */
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
	 *@return: java.lang.String  预留字段d
	 */
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
	 *@return: java.lang.String  删除标志
	 */
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
