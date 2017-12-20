package com.chrhc.project.sc.docfile.entity;

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
 * @Description: 附件表
 * @author onlineGenerator
 * @date 2015-05-23 15:02:40
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_file", schema = "")
@SuppressWarnings("serial")
public class ScFileEntity implements java.io.Serializable {
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
	/**部门名称*/
	private java.lang.String sysOrgCode;
	/**更新日期*/
	private java.util.Date updateDate;
	/**姓名*/
	@Excel(name="姓名")
	private java.lang.String name;
	/**身份证号*/
	@Excel(name="身份证号")
	private java.lang.String idcardNum;
	/**性别*/
	@Excel(name="性别")
	private java.lang.String sex;
	/**出生日期*/
	@Excel(name="出生日期")
	private java.lang.String birthday;
	/**民族*/
	@Excel(name="民族")
	private java.lang.String nation;
	/**住址*/
	@Excel(name="住址")
	private java.lang.String address;
	/**身份证照片*/
	@Excel(name="身份证照片")
	private java.lang.String idcardPhoto;
	/**图片*/
	@Excel(name="图片")
	private java.lang.String photo;
	/**视频*/
	@Excel(name="视频")
	private java.lang.String video;
	/**音频*/
	@Excel(name="音频")
	private java.lang.String mp3;
	/**文档 标记图片类型 不可删标记*/
	@Excel(name="文档")
	private java.lang.String file;
	/**指纹  */
	@Excel(name="指纹")
	private java.lang.String fingerprint;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String remark;
	/**文档库外键*/
	private java.lang.String docForeignId;
	/**删除标记*/
	private java.lang.String delflag;
	/**版本控制*/
	@Column(name ="version_num",nullable=true,length=16)
	private java.lang.Integer versionNum;
	
	/**身份证有效期开始*/
	@Column(name ="yxqstar",nullable=true,length=32)
	private java.lang.String yxqstar;
	/**身份证有效期结束*/
	@Column(name ="yxqend",nullable=true,length=32)
	private java.lang.String yxqend;
	
	/**签发机关*/
	@Column(name ="qfjg",nullable=true,length=32)
	private java.lang.String qfjg;
	public java.lang.String getQfjg() {
		return qfjg;
	}
	public void setQfjg(java.lang.String qfjg) {
		this.qfjg = qfjg;
	}
	
	public java.lang.String getYxqstar() {
		return yxqstar;
	}

	public void setYxqstar(java.lang.String yxqstar) {
		this.yxqstar = yxqstar;
	}

	public java.lang.String getYxqend() {
		return yxqend;
	}

	public void setYxqend(java.lang.String yxqend) {
		this.yxqend = yxqend;
	}
	
	
	public java.lang.Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(java.lang.Integer versionnum) {
		this.versionNum = versionnum;
	}
	
	
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
	 *@return: java.lang.String  姓名
	 */
	@Column(name ="NAME",nullable=true,length=32)
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
	@Column(name ="IDCARD_NUM",nullable=true,length=32)
	public java.lang.String getIdcardNum(){
		return this.idcardNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  身份证号
	 */
	public void setIdcardNum(java.lang.String idcardNum){
		this.idcardNum = idcardNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  性别
	 */
	@Column(name ="SEX",nullable=true,length=32)
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
	 *@return: java.lang.String  出生日期
	 */
	@Column(name ="BIRTHDAY",nullable=true,length=32)
	public java.lang.String getBirthday(){
		return this.birthday;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出生日期
	 */
	public void setBirthday(java.lang.String birthday){
		this.birthday = birthday;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  民族
	 */
	@Column(name ="NATION",nullable=true,length=32)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  住址
	 */
	@Column(name ="ADDRESS",nullable=true,length=32)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  住址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  身份证照片
	 */
	@Column(name ="IDCARD_PHOTO",nullable=true,length=200)
	public java.lang.String getIdcardPhoto(){
		return this.idcardPhoto;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  身份证照片
	 */
	public void setIdcardPhoto(java.lang.String idcardPhoto){
		this.idcardPhoto = idcardPhoto;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片
	 */
	@Column(name ="PHOTO",nullable=true,length=500)
	public java.lang.String getPhoto(){
		return this.photo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片
	 */
	public void setPhoto(java.lang.String photo){
		this.photo = photo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  视频
	 */
	@Column(name ="VIDEO",nullable=true,length=2000)
	public java.lang.String getVideo(){
		return this.video;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  视频
	 */
	public void setVideo(java.lang.String video){
		this.video = video;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  音频
	 */
	@Column(name ="MP3",nullable=true,length=1000)
	public java.lang.String getMp3(){
		return this.mp3;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  音频
	 */
	public void setMp3(java.lang.String mp3){
		this.mp3 = mp3;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文档
	 */
	@Column(name ="FILE",nullable=true,length=1000)
	public java.lang.String getFile(){
		return this.file;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文档
	 */
	public void setFile(java.lang.String file){
		this.file = file;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  指纹
	 */
	@Column(name ="FINGERPRINT",nullable=true,length=100)
	public java.lang.String getFingerprint(){
		return this.fingerprint;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  指纹
	 */
	public void setFingerprint(java.lang.String fingerprint){
		this.fingerprint = fingerprint;
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
	 *@return: java.lang.String  文档库外键
	 */
	@Column(name ="DOC_FOREIGN_ID",nullable=true,length=32)
	public java.lang.String getDocForeignId(){
		return this.docForeignId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文档库外键
	 */
	public void setDocForeignId(java.lang.String docForeignId){
		this.docForeignId = docForeignId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  删除标记
	 */
	@Column(name ="DELFLAG",nullable=true,length=32)
	public java.lang.String getDelflag(){
		return this.delflag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  删除标记
	 */
	public void setDelflag(java.lang.String delflag){
		if(delflag==null||delflag.equals("")){
			this.delflag = "0";
		}else{
		this.delflag = delflag;
		}
	}
}
