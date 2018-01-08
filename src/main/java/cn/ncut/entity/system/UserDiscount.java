package cn.ncut.entity.system;

import java.util.List;

public class UserDiscount {
	
	private Integer id;
	private Integer uid;
	private String discount_id;
	private String discount_name;
	private String discount_amount;
	private Integer isScan;
	
	private Integer isUsed;
	private String create_time;
	private String start_time;
	private String end_time;
	private String staff_id;
	private String group_id;
	
	private Integer countOfGroup;
	
	// 某用户拥有的属于同一个group的不同优惠券的数量
	private Integer count;  
	
	
	public Integer getCountOfGroup() {
		return countOfGroup;
	}
	public void setCountOfGroup(Integer countOfGroup) {
		this.countOfGroup = countOfGroup;
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
	public String getDiscount_id() {
		return discount_id;
	}
	public void setDiscount_id(String discount_id) {
		this.discount_id = discount_id;
	}
	
	public String getDiscount_name() {
		return discount_name;
	}
	public void setDiscount_name(String discount_name) {
		this.discount_name = discount_name;
	}
	public Integer getIsScan() {
		return isScan;
	}
	public void setIsScan(Integer isScan) {
		this.isScan = isScan;
	}
	public Integer getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

	public String getDiscount_amount() {
		return discount_amount;
	}
	public void setDiscount_amount(String discount_amount) {
		this.discount_amount = discount_amount;
	}
	

}
