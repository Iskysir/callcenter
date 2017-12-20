package com.chrhc.project.sc.kinship.util;

public enum ScJzwPersonEnum {

	
	JIATING("家庭","jt","sc_jtxxnew","id","","icon-jt","3")
	,
	YOUFUDUIXIANG("优抚对象","yfdx","sc_rkjbxxnew","yf","Y","icon-yfdx","1")
	,
	KUNNANHU("困难户","knh","sc_rkjbxxnew","knh","Y","icon-knh","1")
	,
	ZUZHUHU("租住户","zzh","sc_jtxxnew","fwcq","4","icon-zzh","3")
	,
	ZHUANYEJUNREN("转业军人","zyjr","sc_rkjbxxnew","byzk","3","icon-zyjr","1")
	,
	JIANGUOQIANLAODANGYUAN("建国前老党员","jgqldy","sc_rkjbxxnew","zzlb","dy","icon-jgqldy","2")
	,
	WENMINGJIATING("文明家庭","wmjt","sc_jtxxnew","xjwmjt","","icon-wmjt","3")
	,
	XUESHENG("学生","xs","sc_rkjbxxnew","sfxs","Y","icon-xs","1")
	,
	WUBAOHU("五保户","wbh","sc_rkjbxxnew","wbh","Y","icon-wbh","1")
	,
	CANJIREN("残疾人","cjr","sc_rkjbxxnew","cjzk","Y","icon-cjr","1")
	,
	DIBAOHU("低保户","dbh","sc_rkjbxxnew","dblb","Y","icon-dbh","1")
	,
	DAIYERENYUAN("待业人员","dyry","sc_rkjbxxnew","gzlb","6","icon-dyry","1")
	,
	SHIYERENYUAN("失业人员","syry","sc_rkjbxxnew","syry","Y","icon-syry","1")
	,
	ZHIANRENYUAN("治安人员","zary","sc_rkjbxxnew","zary","Y","icon-zary","1")
	,
	KONGCHAOLAOREN("空巢老人","kclr","sc_rkjbxxnew","lnr","Y","icon-kclr","2")
	,
	ZHIYUANZHE("志愿者","zyz","sc_rkjbxxnew","zyz","Y","icon-zyz","1")
	,
	LOUZHANG("楼长","lz","sc_rkjbxxnew","lz","Y","icon-lz","1")
	,
	LAONIANREN("老年人","lnr","sc_rkjbxxnew","lnr","Y","icon-lnr","2")
	,
	TUANYUAN("团员","ty","sc_rkjbxxnew","zzlb","ty","icon-ty","1")
	,
	DANGYUAN("党员","dy","sc_rkjbxxnew","zzlb","dy","icon-dy","2")
	,
	TUIWUJUNREN("退伍军人","twjr","sc_rkjbxxnew","byzk","1","icon-twjr","1")
	,
	ZHONGDIANRENYUAN("重点人员","zdry","sc_rkjbxxnew","sfzdwkrq","Y","icon-zdry","1")
	,
	DUJUNLAOREN("独居老人","djlr","sc_rkjbxxnew","lnr","Y","icon-djlr","2")
	,
	YULINGFUNV("育龄妇女","ylfn","sc_rkjbxxnew","ylfn","Y","icon-ylfn","1")
	,
	QISHIYETUIXIU("企事业退休","qsytx","sc_rkjbxxnew","qytxry","Y","icon-qsytx","1")
	;
	//图标名字描述
	private String name;
	private String code;
	//获取信息表名
	private String table;
	//获取信息字段
	private String filed;
	//获取信息字段值
	private String value;
	//图标显示样式名
	private String cssName;
	//数据处理方式1:直接读取2：特殊处理3:家庭属性处理
	private String type;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getFiled() {
		return filed;
	}
	public void setFiled(String filed) {
		this.filed = filed;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getCssName() {
		return cssName;
	}
	public void setCssName(String cssName) {
		this.cssName = cssName;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private ScJzwPersonEnum(String name,String code,String table,String filed,String value,String cssName,String type){		
		this.name = name;
		this.code = code;
		this.table = table;
		this.filed = filed;
		this.value = value;
		this.cssName = cssName;
		this.type = type;
	}
}
