package com.chrhc.project.sc.kinship.util;

public enum KinshipEnum {

	/**
	 * 之父
	 */
	FATHER("父亲","1","Y","1"),
	/**
	 * 之母
	 */
	MOTHER("母亲","2","Y","2"),
	/**
	 * 之子
	 */
	SON("儿子","3","Y","1"),
	/**
	 * 之女
	 */
	DAUGHTER("女儿","4","Y","2"),
	/**
	 * 之夫
	 */
	HUSBAND("丈夫","5","Y","1"),
	/**
	 * 之妻
	 */
	WIFE("妻子","6","Y","2"),
	/**
	 * 之儿媳
	 */
	SON_WIFE("儿媳","7","Y","2"),
	/**
	 * 之女婿
	 */
	DAUGHTER_HUSBAND("女婿","8","Y","1"),
	/**
	 * 叔叔,伯父
	 */
	UNCLE("叔叔,伯父","51","Y","1"),
	/**
	 * 姑妈
	 */
	AUNT("姑妈","55","Y","2"),
	/**
	 * 之祖父
	 */
	GRANDFATHER("祖父","9","Y","1"),
	/**
	 * 之祖母
	 */
	GRANDMOTHER("祖母","10","Y","2"),
	/**
	 * 之舅舅
	 */
	JIUJIU("舅舅","59","Y","1"),
	/**
	 * 之姨妈
	 */
	YIMA("姨妈","63","Y","2"),
	/**
	 * 之外祖父
	 */
	OUTGRANDFATHER("外祖父","11","Y","1"),
	/**
	 * 之外祖母
	 */
	OUTGRANDMOTHER("外祖母","12","Y","2"),
	/**
	 * 之孙
	 */
	GRANDSON("孙子","13","Y","1"),
	/**
	 * 之孙女
	 */
	GRANDDAUGHTER("孙女","14","Y","2"),
	/**
	 * 之外孙
	 */
	OUTGRANDSON("外孙","15","Y","1"),
	/**
	 * 之外孙女
	 */
	OUTGRANDDAUGHTER("外孙女","16","Y","2"),
	/**
	 * 之兄
	 */
	ELDER_BROTHER("哥哥","17","Y","1"),
	/**
	 * 之弟
	 */
	YOUNGER_BROTHER("弟弟","18","Y","1"),
	/**
	 *之姐
	 */
	ELDER_SISTER("姐姐","19","Y","2"),
	/**
	 * 之妹
	 */
	YOUNGER_SISTER("妹妹","20","Y","2"),
	/**
	 * 之岳父
	 */
	FATHER_IN_LAW("岳父","21","N","1"),
	/**
	 * 之岳母
	 */
	MOTHER_IN_LAW("岳母","22","N","2"),
	/**
	 * 之哥哥弟弟
	 */
	ELDER_YOUNGER_BROTHER("哥哥、弟弟","67","Y","1"),
	/**
	 * 之姐姐妹妹
	 */
	ELDER_YOUNGER_SISTER("姐姐、妹妹","68","Y","2"),
	/**
	 * 之侄
	 */
	NEPHEW("侄子","23","Y","1"),
	/**
	 * 之侄女
	 */
	NIECE("侄女","24","Y","2"),
	/**
	 * 之外甥
	 */
	OUT_NEPHEW("外甥","28","Y","1"),
	/**
	 * 之外甥女
	 */
	OUT_NIECE("外甥女","29","Y","2"),
	/**
	 * 之妹夫
	 */
	YOUNGER_SISTER_HUSBAND("妹夫","30","Y","1"),
	/**
	 * 之姐夫
	 */
	ELDER_SISTER_HUSBAND("姐夫","31","Y","1"),
	/**
	 * 之姐妹夫
	 */
	SISTER_HUSBAND("姐夫、妹夫","69","Y","1"),
	/**
	 * 之嫂嫂弟媳
	 */
	BROTHER_WIFE("嫂嫂,弟媳","50","Y","2"),
	/**
	 * 之婶婶伯母
	 */
	UNCLE_WIFE("婶婶,伯母","52","Y","2"),
	/**
	 * 之堂兄弟
	 */
	UNCLE_SON("堂兄弟","53","Y","1"),
	/**
	 * 之堂姐妹
	 */
	UNCLE_DAUGHTER("堂姐妹","54","Y","2"),
	/**
	 * 之姑丈
	 */
	AUNT_HUSBAND("姑丈","56","Y","1"),
	/**
	 * 【姑妈】表兄弟
	 */
	AUNT_SON("【姑妈】表兄弟","57","Y","1"),
	/**
	 * 【姑妈】表姐妹
	 */
	AUNT_DAUGHTER("【姑妈】表姐妹","58","Y","2"),
	/**
	 * 舅妈
	 */
	JIUJIU_WIFE("舅妈","60","Y","2"),
	/**
	 * 【舅舅】表兄弟
	 */
	JIUJIU_SON("【舅舅】表兄弟","61","Y","1"),
	/**
	 * 【舅舅】表姐妹
	 */
	JIUJIU_DAUGHTERN("【舅舅】表姐妹","62","Y","2"),
	/**
	 * 姨丈
	 */
	YIMA_HUSBAND("姨丈","64","Y","1"),
	/**
	 *【姨妈】表兄弟
	 */
	YIMA_SON("【姨妈】表兄弟","65","Y","1"),
	/**
	 *【姨妈】表姐妹
	 */
	YIMA_DAUGHTERN("【姨妈】表姐妹","66","Y","2"),
	
	;
	/**
	 * 关系类型名称
	 */
	private String name;
	/**
	 * 关系类型编码
	 */
	private String code;
	/**
	 * 是否是亲属关系图展示
	 */
	private String flag;
	/**
	 * 关系类型性别1：男 2：女
	 */
	private String sex;
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 
	 * @param name
	 * @param code
	 * @param flag
	 */
	private KinshipEnum(String name, String code,String flag,String sex) {
		this.name = name;
		this.code = code;
		this.flag = flag;
		this.sex = sex;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
