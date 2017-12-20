package com.chrhc.project.sc.zhyzh.entity;

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
 * @Description: 志愿服务者
 * @author onlineGenerator
 * @date 2015-09-23 10:47:02
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sc_zhyfwzh", schema = "")
@SuppressWarnings("serial")
public class ScZhyfwzhEntity implements java.io.Serializable {
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
	/**姓名*/
	@Excel(name="姓名")
	private java.lang.String name;
	/**性别*/
	@Excel(name="性别")
	private java.lang.String gender;
	/**身份证号*/
	@Excel(name="身份证号")
	private java.lang.String certId;
	/**民族*/
	@Excel(name="民族")
	private java.lang.String nation;
	/**志愿者编号*/
	@Excel(name="志愿者编号")
	private java.lang.String code;
	/**手机*/
	@Excel(name="手机")
	private java.lang.String mobilePhone;
	/**注册时间*/
	@Excel(name="注册时间")
	private java.util.Date regDatetime;
	/**所属服务队*/
	private java.lang.String belongId;
	/**所属服务队*/
	@Excel(name="所属服务队")
	private java.lang.String belongName;
	/**文化程度*/
	@Excel(name="文化程度")
	private java.lang.String eduDegree;
	/**工作单位*/
	@Excel(name="工作单位")
	private java.lang.String workUnit;
	/**政治面貌*/
	@Excel(name="政治面貌")
	private java.lang.String political;
	/**职务*/
	@Excel(name="职务")
	private java.lang.String position;
	/**通讯地址*/
	@Excel(name="通讯地址")
	private java.lang.String address;
	/**办公电话*/
	@Excel(name="办公电话")
	private java.lang.String workTele;
	/**删除标志*/
	private java.lang.String delflag;
	/**家庭电话*/
	@Excel(name="家庭电话")
	private java.lang.String homeTele;
	/**电子信箱*/
	@Excel(name="电子信箱")
	private java.lang.String mail;
	/**服务统计(次)*/
	private java.lang.Integer servStat;
	/**伤残病历*/
	@Excel(name="伤残病历")
	private java.lang.String isMedical;
	/**志愿经验*/
	@Excel(name="志愿经验")
	private java.lang.String isExpe;
	/**爱好特长*/
	@Excel(name="爱好特长")
	private java.lang.String hobbies;
	/**个人简历*/
	@Excel(name="个人简历")
	private java.lang.String resume;
	/**培训记录*/
	@Excel(name="培训记录")
	private java.lang.String trainRecord;
	/**服务时间*/
	@Excel(name="服务时间")
	private java.lang.String servTime;
	/**服务时段*/
	@Excel(name="服务时段")
	private java.lang.String servPeriod;
	/**一助一长期结对服务*/
	@Excel(name="一助一长期结对服务")
	private java.lang.String servPair;
	/**社区教育科普文化*/
	@Excel(name="社区教育科普文化")
	private java.lang.String servEdu;
	/**健康救助*/
	@Excel(name="健康救助")
	private java.lang.String servHealth;
	/**抢险救灾*/
	@Excel(name="抢险救灾")
	private java.lang.String servRelief;
	/**环保活动*/
	@Excel(name="环保活动")
	private java.lang.String servEnviron;
	/**社区治安*/
	@Excel(name="社区治安")
	private java.lang.String servPolice;
	/**大型赛会服务*/
	@Excel(name="大型赛会服务")
	private java.lang.String servEvent;
	/**志愿者组织管理*/
	@Excel(name="志愿者组织管理")
	private java.lang.String servOrg;
	/**家政服务*/
	@Excel(name="家政服务")
	private java.lang.String servHouse;
	/**维权服务*/
	@Excel(name="维权服务")
	private java.lang.String servRight;
	/**其它服务项目*/
	@Excel(name="其它服务项目")
	private java.lang.String servOther;
	/**个人照片*/
	@Excel(name="个人照片")
	private java.lang.String picId;
	/**申请社区志愿者级别*/
	@Excel(name="申请社区志愿者级别")
	private java.lang.String level;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String remark;
	/**地理信息*/
	private java.lang.String gisxy;
	/**人口信息id*/
	private java.lang.String rkId;
	
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
	 *@return: java.lang.String  性别
	 */
	@Column(name ="GENDER",nullable=false,length=2)
	public java.lang.String getGender(){
		return this.gender;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  性别
	 */
	public void setGender(java.lang.String gender){
		this.gender = gender;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  身份证号
	 */
	@Column(name ="CERT_ID",nullable=false,length=20)
	public java.lang.String getCertId(){
		return this.certId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  身份证号
	 */
	public void setCertId(java.lang.String certId){
		this.certId = certId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  民族
	 */
	@Column(name ="NATION",nullable=false,length=10)
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
	 *@return: java.lang.String  志愿者编号
	 */
	@Column(name ="CODE",nullable=false,length=20)
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  志愿者编号
	 */
	public void setCode(java.lang.String code){
		this.code = code;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机
	 */
	@Column(name ="MOBILE_PHONE",nullable=false,length=30)
	public java.lang.String getMobilePhone(){
		return this.mobilePhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机
	 */
	public void setMobilePhone(java.lang.String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  注册时间
	 */
	@Column(name ="REG_DATETIME",nullable=false,length=20)
	public java.util.Date getRegDatetime(){
		return this.regDatetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  注册时间
	 */
	public void setRegDatetime(java.util.Date regDatetime){
		this.regDatetime = regDatetime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属服务队
	 */
	@Column(name ="BELONG_ID",nullable=true,length=36)
	public java.lang.String getBelongId(){
		return this.belongId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属服务队
	 */
	public void setBelongId(java.lang.String belongId){
		this.belongId = belongId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属服务队
	 */
	@Column(name ="BELONG_NAME",nullable=true,length=50)
	public java.lang.String getBelongName(){
		return this.belongName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属服务队
	 */
	public void setBelongName(java.lang.String belongName){
		this.belongName = belongName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文化程度
	 */
	@Column(name ="EDU_DEGREE",nullable=true,length=20)
	public java.lang.String getEduDegree(){
		return this.eduDegree;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文化程度
	 */
	public void setEduDegree(java.lang.String eduDegree){
		this.eduDegree = eduDegree;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  工作单位
	 */
	@Column(name ="WORK_UNIT",nullable=true,length=50)
	public java.lang.String getWorkUnit(){
		return this.workUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  工作单位
	 */
	public void setWorkUnit(java.lang.String workUnit){
		this.workUnit = workUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  政治面貌
	 */
	@Column(name ="POLITICAL",nullable=true,length=20)
	public java.lang.String getPolitical(){
		return this.political;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  政治面貌
	 */
	public void setPolitical(java.lang.String political){
		this.political = political;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  职务
	 */
	@Column(name ="POSITION",nullable=true,length=20)
	public java.lang.String getPosition(){
		return this.position;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职务
	 */
	public void setPosition(java.lang.String position){
		this.position = position;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  通讯地址
	 */
	@Column(name ="ADDRESS",nullable=true,length=200)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通讯地址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  办公电话
	 */
	@Column(name ="WORK_TELE",nullable=true,length=30)
	public java.lang.String getWorkTele(){
		return this.workTele;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  办公电话
	 */
	public void setWorkTele(java.lang.String workTele){
		this.workTele = workTele;
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
	 *@return: java.lang.String  家庭电话
	 */
	@Column(name ="HOME_TELE",nullable=true,length=30)
	public java.lang.String getHomeTele(){
		return this.homeTele;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  家庭电话
	 */
	public void setHomeTele(java.lang.String homeTele){
		this.homeTele = homeTele;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电子信箱
	 */
	@Column(name ="MAIL",nullable=true,length=50)
	public java.lang.String getMail(){
		return this.mail;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电子信箱
	 */
	public void setMail(java.lang.String mail){
		this.mail = mail;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  服务统计(次)
	 */
	@Column(name ="SERV_STAT",nullable=true,length=9)
	public java.lang.Integer getServStat(){
		return this.servStat;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  服务统计(次)
	 */
	public void setServStat(java.lang.Integer servStat){
		this.servStat = servStat;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  伤残病历
	 */
	@Column(name ="IS_MEDICAL",nullable=true,length=20)
	public java.lang.String getIsMedical(){
		return this.isMedical;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  伤残病历
	 */
	public void setIsMedical(java.lang.String isMedical){
		this.isMedical = isMedical;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  志愿经验
	 */
	@Column(name ="IS_EXPE",nullable=true,length=20)
	public java.lang.String getIsExpe(){
		return this.isExpe;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  志愿经验
	 */
	public void setIsExpe(java.lang.String isExpe){
		this.isExpe = isExpe;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  爱好特长
	 */
	@Column(name ="HOBBIES",nullable=true,length=500)
	public java.lang.String getHobbies(){
		return this.hobbies;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  爱好特长
	 */
	public void setHobbies(java.lang.String hobbies){
		this.hobbies = hobbies;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  个人简历
	 */
	@Column(name ="RESUME",nullable=true,length=500)
	public java.lang.String getResume(){
		return this.resume;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  个人简历
	 */
	public void setResume(java.lang.String resume){
		this.resume = resume;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  培训记录
	 */
	@Column(name ="TRAIN_RECORD",nullable=true,length=500)
	public java.lang.String getTrainRecord(){
		return this.trainRecord;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  培训记录
	 */
	public void setTrainRecord(java.lang.String trainRecord){
		this.trainRecord = trainRecord;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  服务时间
	 */
	@Column(name ="SERV_TIME",nullable=true,length=100)
	public java.lang.String getServTime(){
		return this.servTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  服务时间
	 */
	public void setServTime(java.lang.String servTime){
		this.servTime = servTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  服务时段
	 */
	@Column(name ="SERV_PERIOD",nullable=true,length=100)
	public java.lang.String getServPeriod(){
		return this.servPeriod;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  服务时段
	 */
	public void setServPeriod(java.lang.String servPeriod){
		this.servPeriod = servPeriod;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  一助一长期结对服务
	 */
	@Column(name ="SERV_PAIR",nullable=true,length=100)
	public java.lang.String getServPair(){
		return this.servPair;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  一助一长期结对服务
	 */
	public void setServPair(java.lang.String servPair){
		this.servPair = servPair;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  社区教育科普文化
	 */
	@Column(name ="SERV_EDU",nullable=true,length=100)
	public java.lang.String getServEdu(){
		return this.servEdu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  社区教育科普文化
	 */
	public void setServEdu(java.lang.String servEdu){
		this.servEdu = servEdu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  健康救助
	 */
	@Column(name ="SERV_HEALTH",nullable=true,length=100)
	public java.lang.String getServHealth(){
		return this.servHealth;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  健康救助
	 */
	public void setServHealth(java.lang.String servHealth){
		this.servHealth = servHealth;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  抢险救灾
	 */
	@Column(name ="SERV_RELIEF",nullable=true,length=100)
	public java.lang.String getServRelief(){
		return this.servRelief;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  抢险救灾
	 */
	public void setServRelief(java.lang.String servRelief){
		this.servRelief = servRelief;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  环保活动
	 */
	@Column(name ="SERV_ENVIRON",nullable=true,length=100)
	public java.lang.String getServEnviron(){
		return this.servEnviron;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  环保活动
	 */
	public void setServEnviron(java.lang.String servEnviron){
		this.servEnviron = servEnviron;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  社区治安
	 */
	@Column(name ="SERV_POLICE",nullable=true,length=100)
	public java.lang.String getServPolice(){
		return this.servPolice;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  社区治安
	 */
	public void setServPolice(java.lang.String servPolice){
		this.servPolice = servPolice;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  大型赛会服务
	 */
	@Column(name ="SERV_EVENT",nullable=true,length=100)
	public java.lang.String getServEvent(){
		return this.servEvent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  大型赛会服务
	 */
	public void setServEvent(java.lang.String servEvent){
		this.servEvent = servEvent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  志愿者组织管理
	 */
	@Column(name ="SERV_ORG",nullable=true,length=100)
	public java.lang.String getServOrg(){
		return this.servOrg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  志愿者组织管理
	 */
	public void setServOrg(java.lang.String servOrg){
		this.servOrg = servOrg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  家政服务
	 */
	@Column(name ="SERV_HOUSE",nullable=true,length=100)
	public java.lang.String getServHouse(){
		return this.servHouse;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  家政服务
	 */
	public void setServHouse(java.lang.String servHouse){
		this.servHouse = servHouse;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  维权服务
	 */
	@Column(name ="SERV_RIGHT",nullable=true,length=100)
	public java.lang.String getServRight(){
		return this.servRight;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  维权服务
	 */
	public void setServRight(java.lang.String servRight){
		this.servRight = servRight;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  其它服务项目
	 */
	@Column(name ="SERV_OTHER",nullable=true,length=50)
	public java.lang.String getServOther(){
		return this.servOther;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  其它服务项目
	 */
	public void setServOther(java.lang.String servOther){
		this.servOther = servOther;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  个人照片
	 */
	@Column(name ="PIC_ID",nullable=true,length=500)
	public java.lang.String getPicId(){
		return this.picId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  个人照片
	 */
	public void setPicId(java.lang.String picId){
		this.picId = picId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  申请社区志愿者级别
	 */
	@Column(name ="LEVEL",nullable=true,length=20)
	public java.lang.String getLevel(){
		return this.level;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  申请社区志愿者级别
	 */
	public void setLevel(java.lang.String level){
		this.level = level;
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
	 *@return: java.lang.String  人口信息id
	 */
	@Column(name ="RK_ID",nullable=true,length=36)
	public java.lang.String getRkId(){
		return this.rkId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  人口信息id
	 */
	public void setRkId(java.lang.String rkId){
		this.rkId = rkId;
	}
}
