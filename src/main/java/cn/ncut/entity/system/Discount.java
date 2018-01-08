package cn.ncut.entity.system;


/**
 * 
*
*<p>Title:Staff</p>
*<p>Description:优惠券po</p>
*<p>Company:ncut</p> 
* @author xth 
* 
*
 */
public class Discount {
	private String DISCOUNT_ID;		//优惠券id
	private String DISCOUNT_NAME;	//优惠券名称
	private Double DISCOUNT_AMOUNT;//优惠券金额
	private Integer NUMBER;	//总数量
	private Integer A_NUMBER;	//已发数量
	private String STARTTIME; 	//开始时间
	private String ENDTIME;//结束时间
	private String CREATETIME;//创建时间
	private String STAFF_ID;//员工编号
	private String PROJECT_IDS;//可用编号
	private Integer STATUS;//状态
	public String getDISCOUNT_ID() {
		return DISCOUNT_ID;
	}
	public void setDISCOUNT_ID(String dISCOUNT_ID) {
		DISCOUNT_ID = dISCOUNT_ID;
	}
	public String getDISCOUNT_NAME() {
		return DISCOUNT_NAME;
	}
	public void setDISCOUNT_NAME(String dISCOUNT_NAME) {
		DISCOUNT_NAME = dISCOUNT_NAME;
	}
	public Double getDISCOUNT_AMOUNT() {
		return DISCOUNT_AMOUNT;
	}
	public void setDISCOUNT_AMOUNT(Double dISCOUNT_AMOUNT) {
		DISCOUNT_AMOUNT = dISCOUNT_AMOUNT;
	}
	public Integer getNUMBER() {
		return NUMBER;
	}
	public void setNUMBER(Integer nUMBER) {
		NUMBER = nUMBER;
	}
	public Integer getA_NUMBER() {
		return A_NUMBER;
	}
	public void setA_NUMBER(Integer a_NUMBER) {
		A_NUMBER = a_NUMBER;
	}
	public String getSTARTTIME() {
		return STARTTIME;
	}
	public void setSTARTTIME(String sTARTTIME) {
		STARTTIME = sTARTTIME;
	}
	public String getENDTIME() {
		return ENDTIME;
	}
	public void setENDTIME(String eNDTIME) {
		ENDTIME = eNDTIME;
	}
	public String getCREATETIME() {
		return CREATETIME;
	}
	public void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}
	public String getSTAFF_ID() {
		return STAFF_ID;
	}
	public void setSTAFF_ID(String sTAFF_ID) {
		STAFF_ID = sTAFF_ID;
	}
	public String getPROJECT_IDS() {
		return PROJECT_IDS;
	}
	public void setPROJECT_IDS(String pROJECT_IDS) {
		PROJECT_IDS = pROJECT_IDS;
	}
	public Integer getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(Integer sTATUS) {
		STATUS = sTATUS;
	}
	
}
