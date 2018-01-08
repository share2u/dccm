package cn.ncut.entity.wechat.pojo;

/**
 * 用户支付历史,主要用于微信端支付核对用户支付情况
 * */
public class WeChatPayHistory {
	private int payHistoryId;
	private String openId;
	private String parentOrderId;
	private String childOrderId;
	private int status;
	private String payMoney;
	private String payTime;
	private int payMethod;
	private String discountId;
	public int getPayHistoryId() {
		return payHistoryId;
	}
	public void setPayHistoryId(int payHistoryId) {
		this.payHistoryId = payHistoryId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getParentOrderId() {
		return parentOrderId;
	}
	public void setParentOrderId(String parentOrderId) {
		this.parentOrderId = parentOrderId;
	}
	public String getChildOrderId() {
		return childOrderId;
	}
	public void setChildOrderId(String childOrderId) {
		this.childOrderId = childOrderId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public int getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(int payMethod) {
		this.payMethod = payMethod;
	}
	public String getDiscountId() {
		return discountId;
	}
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}
	@Override
	public String toString() {
		return "WeChatPayHistory [payHistoryId=" + payHistoryId + ", openId="
				+ openId + ", parentOrderId=" + parentOrderId
				+ ", childOrderId=" + childOrderId + ", status=" + status
				+ ", payMoney=" + payMoney + ", payTime=" + payTime
				+ ", payMethod=" + payMethod + ", discountId=" + discountId
				+ "]";
	}
}
