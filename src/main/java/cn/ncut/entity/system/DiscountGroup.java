package cn.ncut.entity.system;

import java.util.List;


/**
 * 
*
*<p>Title:Staff</p>
*<p>Description:优惠券组po</p>
*<p>Company:ncut</p> 
* @author xth 
* 
*
 */
public class DiscountGroup {
	private Integer DISCOUNTGROUP_ID;		//主键
	private String GROUP_ID;	//组id
	private String GROUP_NAME;//组名
	private String DISCOUNT_ID;	//优惠券id
	private String DISCOUNT_NUMBER;	//组中该优惠券数量
	private String CREATE_TIME; 	//创建时间
	private String STAFF_ID;//员工编号
	private String REMARK;//备注
	private List<Discount> Discountlist;//优惠券list
	public Integer getDISCOUNTGROUP_ID() {
		return DISCOUNTGROUP_ID;
	}
	public void setDISCOUNTGROUP_ID(Integer dISCOUNTGROUP_ID) {
		DISCOUNTGROUP_ID = dISCOUNTGROUP_ID;
	}
	public String getGROUP_ID() {
		return GROUP_ID;
	}
	public void setGROUP_ID(String gROUP_ID) {
		GROUP_ID = gROUP_ID;
	}
	public String getGROUP_NAME() {
		return GROUP_NAME;
	}
	public void setGROUP_NAME(String gROUP_NAME) {
		GROUP_NAME = gROUP_NAME;
	}
	public String getDISCOUNT_ID() {
		return DISCOUNT_ID;
	}
	public void setDISCOUNT_ID(String dISCOUNT_ID) {
		DISCOUNT_ID = dISCOUNT_ID;
	}
	public String getDISCOUNT_NUMBER() {
		return DISCOUNT_NUMBER;
	}
	public void setDISCOUNT_NUMBER(String dISCOUNT_NUMBER) {
		DISCOUNT_NUMBER = dISCOUNT_NUMBER;
	}
	public String getCREATE_TIME() {
		return CREATE_TIME;
	}
	public void setCREATE_TIME(String cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
	public String getSTAFF_ID() {
		return STAFF_ID;
	}
	public void setSTAFF_ID(String sTAFF_ID) {
		STAFF_ID = sTAFF_ID;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public List<Discount> getDiscountlist() {
		return Discountlist;
	}
	public void setDiscountlist(List<Discount> discountlist) {
		Discountlist = discountlist;
	}
	
	
}
