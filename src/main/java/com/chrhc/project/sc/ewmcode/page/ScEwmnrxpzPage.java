
package com.chrhc.project.sc.ewmcode.page;
import com.chrhc.project.sc.ewmcode.entity.ScEwmnrxpzEntity;
import com.chrhc.project.sc.ewmcode.entity.ScEwmnrxpzsubEntity;

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
 * @Description: 二维码内容项配置
 * @author onlineGenerator
 * @date 2015-05-12 15:51:44
 * @version V1.0   
 *
 */
public class ScEwmnrxpzPage implements java.io.Serializable {
	/**保存-二维码返回信息*/
	private List<ScEwmnrxpzsubEntity> scEwmnrxpzsubList = new ArrayList<ScEwmnrxpzsubEntity>();
	public List<ScEwmnrxpzsubEntity> getScEwmnrxpzsubList() {
		return scEwmnrxpzsubList;
	}
	public void setScEwmnrxpzsubList(List<ScEwmnrxpzsubEntity> scEwmnrxpzsubList) {
		this.scEwmnrxpzsubList = scEwmnrxpzsubList;
	}

	/**主键*/
	private java.lang.String id;
	/**业务编号*/
	private java.lang.String ywbh;
	/**业务名称*/
	private java.lang.String ywmc;
	/**前端传输条件*/
	private java.lang.String frontfield;
	/**数据来源表*/
	private java.lang.String sourcetable;
	/**是否已经使用*/
	private java.lang.String sfyy;
	/**删除标记*/
	private java.lang.Integer delflag;
	/**备注*/
	private java.lang.String remark;
	
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
	 *@return: java.lang.String  业务编号
	 */
	public java.lang.String getYwbh(){
		return this.ywbh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务编号
	 */
	public void setYwbh(java.lang.String ywbh){
		this.ywbh = ywbh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务名称
	 */
	public java.lang.String getYwmc(){
		return this.ywmc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务名称
	 */
	public void setYwmc(java.lang.String ywmc){
		this.ywmc = ywmc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  前端传输条件
	 */
	public java.lang.String getFrontfield(){
		return this.frontfield;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  前端传输条件
	 */
	public void setFrontfield(java.lang.String frontfield){
		this.frontfield = frontfield;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数据来源表
	 */
	public java.lang.String getSourcetable(){
		return this.sourcetable;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数据来源表
	 */
	public void setSourcetable(java.lang.String sourcetable){
		this.sourcetable = sourcetable;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否已经使用
	 */
	public java.lang.String getSfyy(){
		return this.sfyy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否已经使用
	 */
	public void setSfyy(java.lang.String sfyy){
		this.sfyy = sfyy;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  删除标记
	 */
	public java.lang.Integer getDelflag(){
		return this.delflag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  删除标记
	 */
	public void setDelflag(java.lang.Integer delflag){
		this.delflag = delflag;
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
}
