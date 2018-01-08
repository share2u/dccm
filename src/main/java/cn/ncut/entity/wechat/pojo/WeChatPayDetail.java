package cn.ncut.entity.wechat.pojo;

/**
 * 支付明细类
 * */
public class WeChatPayDetail {
	private String payDetailId;
	private int uId;
	private String orderId;
	// 应收金额
	private String orderMoney;
	// 实收金额
	private String payMoney;
	// 支付方式
	private int payMethod;
	// 支付时间
	private String payTime;
	private String remark;
	public String getPayDetailId() {
		return payDetailId;
	}
	public void setPayDetailId(String payDetailId) {
		this.payDetailId = payDetailId;
	}
	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}
	public String getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}
	public int getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(int payMethod) {
		this.payMethod = payMethod;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "WeChatPayDetail [payDetailId=" + payDetailId + ", uId=" + uId
				+ ", orderId=" + orderId + ", orderMoney=" + orderMoney
				+ ", payMoney=" + payMoney + ", payMethod=" + payMethod
				+ ", payTime=" + payTime + ", remark=" + remark + "]";
	}
}
