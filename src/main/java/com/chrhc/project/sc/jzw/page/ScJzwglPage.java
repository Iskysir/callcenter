
package com.chrhc.project.sc.jzw.page;
import com.chrhc.project.sc.jzw.entity.ScJzwglEntity;
import com.chrhc.project.sc.jzw.entity.ScJzwdyglEntity;

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
 * @Description: 建筑物管理
 * @author onlineGenerator
 * @date 2015-05-11 18:38:16
 * @version V1.0   
 *
 */
public class ScJzwglPage implements java.io.Serializable {
	/**保存-建筑物单元管理*/
	private List<ScJzwdyglEntity> scJzwdyglList = new ArrayList<ScJzwdyglEntity>();
	public List<ScJzwdyglEntity> getScJzwdyglList() {
		return scJzwdyglList;
	}
	public void setScJzwdyglList(List<ScJzwdyglEntity> scJzwdyglList) {
		this.scJzwdyglList = scJzwdyglList;
	}

	/**主键*/
	private java.lang.String id;
	/**建筑物编号*/
	private java.lang.String number;
	/**地理位置*/
	private java.lang.String gisxy;
	/**建筑物名称*/
	private java.lang.String name;
	/**单元数*/
	private java.lang.Integer units;
	/**建筑分类*/
	private java.lang.String type;
	/**建筑用途*/
	private java.lang.String usadge;
	/**建筑结构*/
	private java.lang.String struct;
	/**建筑年份*/
	private java.lang.String buildYear;
	/**产权年限*/
	private java.lang.Integer propertyLimit;
	/**产权单位*/
	private java.lang.String propertyUnit;
	/**地址*/
	private java.lang.String addr;
	/**GIS图形ID*/
	private java.lang.String gisId;
	/**备注*/
	private java.lang.String remark;
	/**房间生成标识*/
	private java.lang.String autoFlag;
	/**生成标识隐藏用*/
	private java.lang.String autoCode;
	/**删除标志*/
	private java.lang.Integer delFlag;
	/**删除日期*/
	private java.util.Date delDate;
	/**备用a*/
	private java.lang.String obligatea;
	/**备用b*/
	private java.lang.String obligateb;
	/**备用c*/
	private java.lang.String obligatec;
	/**备用d*/
	private java.lang.String obligated;
	/**备用e*/
	private java.lang.String obligatee;
	/**备用f*/
	private java.lang.String obligatef;
	/**备用g*/
	private java.lang.String obligateg;
	/**创建者登录名称*/
	private java.lang.String createBy;
	/**创建者名称*/
	private java.lang.String createName;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新者登录名称*/
	private java.lang.String updateBy;
	/**更新者名称*/
	private java.lang.String updateName;
	/**更新日期*/
	private java.util.Date updateDate;
	
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
	 *@return: java.lang.String  建筑物编号
	 */
	public java.lang.String getNumber(){
		return this.number;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  建筑物编号
	 */
	public void setNumber(java.lang.String number){
		this.number = number;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地理位置
	 */
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
	 *@return: java.lang.String  建筑物名称
	 */
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  建筑物名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  单元数
	 */
	public java.lang.Integer getUnits(){
		return this.units;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  单元数
	 */
	public void setUnits(java.lang.Integer units){
		this.units = units;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  建筑分类
	 */
	public java.lang.String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  建筑分类
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  建筑用途
	 */
	public java.lang.String getUsadge(){
		return this.usadge;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  建筑用途
	 */
	public void setUsadge(java.lang.String usadge){
		this.usadge = usadge;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  建筑结构
	 */
	public java.lang.String getStruct(){
		return this.struct;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  建筑结构
	 */
	public void setStruct(java.lang.String struct){
		this.struct = struct;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  建筑年份
	 */
	public java.lang.String getBuildYear(){
		return this.buildYear;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  建筑年份
	 */
	public void setBuildYear(java.lang.String buildYear){
		this.buildYear = buildYear;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  产权年限
	 */
	public java.lang.Integer getPropertyLimit(){
		return this.propertyLimit;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  产权年限
	 */
	public void setPropertyLimit(java.lang.Integer propertyLimit){
		this.propertyLimit = propertyLimit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  产权单位
	 */
	public java.lang.String getPropertyUnit(){
		return this.propertyUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产权单位
	 */
	public void setPropertyUnit(java.lang.String propertyUnit){
		this.propertyUnit = propertyUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地址
	 */
	public java.lang.String getAddr(){
		return this.addr;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地址
	 */
	public void setAddr(java.lang.String addr){
		this.addr = addr;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  GIS图形ID
	 */
	public java.lang.String getGisId(){
		return this.gisId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  GIS图形ID
	 */
	public void setGisId(java.lang.String gisId){
		this.gisId = gisId;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  房间生成标识
	 */
	public java.lang.String getAutoFlag(){
		return this.autoFlag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  房间生成标识
	 */
	public void setAutoFlag(java.lang.String autoFlag){
		this.autoFlag = autoFlag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  生成标识隐藏用
	 */
	public java.lang.String getAutoCode(){
		return this.autoCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  生成标识隐藏用
	 */
	public void setAutoCode(java.lang.String autoCode){
		this.autoCode = autoCode;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  删除标志
	 */
	public java.lang.Integer getDelFlag(){
		return this.delFlag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  删除标志
	 */
	public void setDelFlag(java.lang.Integer delFlag){
		this.delFlag = delFlag;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  删除日期
	 */
	public java.util.Date getDelDate(){
		return this.delDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  删除日期
	 */
	public void setDelDate(java.util.Date delDate){
		this.delDate = delDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用a
	 */
	public java.lang.String getObligatea(){
		return this.obligatea;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用a
	 */
	public void setObligatea(java.lang.String obligatea){
		this.obligatea = obligatea;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用b
	 */
	public java.lang.String getObligateb(){
		return this.obligateb;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用b
	 */
	public void setObligateb(java.lang.String obligateb){
		this.obligateb = obligateb;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用c
	 */
	public java.lang.String getObligatec(){
		return this.obligatec;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用c
	 */
	public void setObligatec(java.lang.String obligatec){
		this.obligatec = obligatec;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用d
	 */
	public java.lang.String getObligated(){
		return this.obligated;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用d
	 */
	public void setObligated(java.lang.String obligated){
		this.obligated = obligated;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用e
	 */
	public java.lang.String getObligatee(){
		return this.obligatee;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用e
	 */
	public void setObligatee(java.lang.String obligatee){
		this.obligatee = obligatee;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用f
	 */
	public java.lang.String getObligatef(){
		return this.obligatef;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用f
	 */
	public void setObligatef(java.lang.String obligatef){
		this.obligatef = obligatef;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用g
	 */
	public java.lang.String getObligateg(){
		return this.obligateg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用g
	 */
	public void setObligateg(java.lang.String obligateg){
		this.obligateg = obligateg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建者登录名称
	 */
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建者登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建者名称
	 */
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建者名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
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
	 *@return: java.lang.String  更新者登录名称
	 */
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新者登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新者名称
	 */
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新者名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
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
}
