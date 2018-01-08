package cn.ncut.entity.wechat.pojo;

import java.util.List;

/**
 * 订单类
 * */
public class WeChatOrder {
	private String orderId;
	private String orderMoney;
	private int uId;
	// 门店id
	private String storeId;
	// 医生id
	private String staffId;
	// 服务收费id
	private int serviceCostId;
	// 客服id
	private String serviceStaffId;
	// 客服创建订单时间
	private String createTime;
	// 订单状态
	private int orderStatus;
	private String remark;
	// 发送给用户的支付链接
	private String url;
	// 预约时间
	private String recommendTime;
	// 过期时间
	private String expireTime;
	// 实付金额
	private String payMoney;
	// 折扣
	private String proportion;
	// 优惠券使用信息
	private String discountId;
	// 积分
	private int credit;
	// 预存
	private String refund;
	/**
	 * 关联的对象对象信息
	 * */
	private WeChatStore store;
	private WeChatStaff staff;
	private WeChatServiceProject serviceProject;
	private WeChatAppoint appoint;
	private List<WeChatPayDetail> payDetails;
	private WeChatServiceCost serviceCost;
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
	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public int getServiceCostId() {
		return serviceCostId;
	}
	public void setServiceCostId(int serviceCostId) {
		this.serviceCostId = serviceCostId;
	}
	public String getServiceStaffId() {
		return serviceStaffId;
	}
	public void setServiceStaffId(String serviceStaffId) {
		this.serviceStaffId = serviceStaffId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRecommendTime() {
		return recommendTime;
	}
	public void setRecommendTime(String recommendTime) {
		this.recommendTime = recommendTime;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public String getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}
	public String getProportion() {
		return proportion;
	}
	public void setProportion(String proportion) {
		this.proportion = proportion;
	}
	public String getDiscountId() {
		return discountId;
	}
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String getRefund() {
		return refund;
	}
	public void setRefund(String refund) {
		this.refund = refund;
	}
	public WeChatStore getStore() {
		return store;
	}
	public void setStore(WeChatStore store) {
		this.store = store;
	}
	public WeChatStaff getStaff() {
		return staff;
	}
	public void setStaff(WeChatStaff staff) {
		this.staff = staff;
	}
	public WeChatServiceProject getServiceProject() {
		return serviceProject;
	}
	public void setServiceProject(WeChatServiceProject serviceProject) {
		this.serviceProject = serviceProject;
	}
	public WeChatAppoint getAppoint() {
		return appoint;
	}
	public void setAppoint(WeChatAppoint appoint) {
		this.appoint = appoint;
	}
	public List<WeChatPayDetail> getPayDetails() {
		return payDetails;
	}
	public void setPayDetails(List<WeChatPayDetail> payDetails) {
		this.payDetails = payDetails;
	}
	public WeChatServiceCost getServiceCost() {
		return serviceCost;
	}
	public void setServiceCost(WeChatServiceCost serviceCost) {
		this.serviceCost = serviceCost;
	}
	@Override
	public String toString() {
		return "WeChatOrder [orderId=" + orderId + ", orderMoney=" + orderMoney
				+ ", uId=" + uId + ", storeId=" + storeId + ", staffId="
				+ staffId + ", serviceCostId=" + serviceCostId
				+ ", serviceStaffId=" + serviceStaffId + ", createTime="
				+ createTime + ", orderStatus=" + orderStatus + ", remark="
				+ remark + ", url=" + url + ", recommendTime=" + recommendTime
				+ ", expireTime=" + expireTime + ", payMoney=" + payMoney
				+ ", proportion=" + proportion + ", discountId=" + discountId
				+ ", credit=" + credit + ", refund=" + refund + ", store="
				+ store + ", staff=" + staff + ", serviceProject="
				+ serviceProject + ", appoint=" + appoint + ", payDetails="
				+ payDetails + ", serviceCost=" + serviceCost + "]";
	}
}
