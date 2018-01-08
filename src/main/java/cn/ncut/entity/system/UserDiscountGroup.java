package cn.ncut.entity.system;

import java.util.List;

public class UserDiscountGroup {
	
	private Integer id;
	private Integer uid;
	private String discount_group_id;
	private String discount_group_name;
	private String create_time;
	private String staff_id;
	
	private String GROUP_NAME;
	
	public Integer getSum() {
		return sum;
	}
	public void setSum(Integer sum) {
		this.sum = sum;
	}
	private Integer sum;
	public String getGROUP_NAME() {
		return GROUP_NAME;
	}
	public void setGROUP_NAME(String gROUP_NAME) {
		GROUP_NAME = gROUP_NAME;
	}
	private List<UserDiscount> userDiscounts;
	
	public List<UserDiscount> getUserDiscounts() {
		return userDiscounts;
	}
	public void setUserDiscounts(List<UserDiscount> userDiscounts) {
		this.userDiscounts = userDiscounts;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getDiscount_group_id() {
		return discount_group_id;
	}
	public void setDiscount_group_id(String discount_group_id) {
		this.discount_group_id = discount_group_id;
	}
	public String getDiscount_group_name() {
		return discount_group_name;
	}
	public void setDiscount_group_name(String discount_group_name) {
		this.discount_group_name = discount_group_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}
	
	
}
