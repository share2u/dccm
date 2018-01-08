package cn.ncut.entity.wechat.pojo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 优惠券类
 * */
public class WeChatDiscount {
	private String discountId;
	private String discountName;
	// 优惠券金额
	private BigDecimal discountAmount;
	private int number;
	// 优惠券开始时间
	private String startTime;
	// 优惠券结束时间
	private String endTime;
	private String staffId;
	// 项目编号
	private String projectIds;
	private int status;
	// 服务项目
	private List<WeChatServiceProject> serviceProjects;
	public String getDiscountId() {
		return discountId;
	}
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}
	public String getDiscountName() {
		return discountName;
	}
	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
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
	public String getProjectIds() {
		return projectIds;
	}
	public void setProjectIds(String projectIds) {
		this.projectIds = projectIds;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<WeChatServiceProject> getServiceProjects() {
		return serviceProjects;
	}
	public void setServiceProjects(List<WeChatServiceProject> serviceProjects) {
		this.serviceProjects = serviceProjects;
	}
	@Override
	public String toString() {
		return "WeChatDiscount [discountId=" + discountId + ", discountName="
				+ discountName + ", discountAmount=" + discountAmount
				+ ", number=" + number + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", staffId=" + staffId
				+ ", projectIds=" + projectIds + ", status=" + status
				+ ", serviceProjects=" + serviceProjects + "]";
	}
}
