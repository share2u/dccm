package cn.ncut.entity.system;

import cn.ncut.entity.wechat.pojo.WeChatUser;

public class QueryUserDiscount {
	private Integer id;
	private Integer uId;//1对1关联用户表tab_user
	private String discountId;//1对1关联优惠券tb_discount
	private Integer isScan;
	private Integer isUsed;
	private String createTime;//发放事件
	private String startTime;
	private String endTime;
	private String staffId;//发放人的员工id
	private String groupId;//1对1关联tb_discount_group
	
	private WeChatUser user;
	private Discount discount;
	private Staff staff;
	private DiscountGroup discountGroup;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getuId() {
		return uId;
	}
	public void setuId(Integer uId) {
		this.uId = uId;
	}
	public String getDiscountId() {
		return discountId;
	}
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public WeChatUser getUser() {
		return user;
	}
	public void setUser(WeChatUser user) {
		this.user = user;
	}
	public Discount getDiscount() {
		return discount;
	}
	public void setDiscount(Discount discount) {
		this.discount = discount;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public DiscountGroup getDiscountGroup() {
		return discountGroup;
	}
	public void setDiscountGroup(DiscountGroup discountGroup) {
		this.discountGroup = discountGroup;
	}
	
	
	
}
