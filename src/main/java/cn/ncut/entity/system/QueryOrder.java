package cn.ncut.entity.system;

import java.util.Set;

import cn.ncut.entity.wechat.pojo.WeChatAppoint;
import cn.ncut.entity.wechat.pojo.WeChatDiscount;
import cn.ncut.entity.wechat.pojo.WeChatPayDetail;
import cn.ncut.entity.wechat.pojo.WeChatServiceCost;
import cn.ncut.entity.wechat.pojo.WeChatServiceProject;
import cn.ncut.entity.wechat.pojo.WeChatStaff;
import cn.ncut.entity.wechat.pojo.WeChatStore;
import cn.ncut.entity.wechat.pojo.WeChatUser;

public class QueryOrder {
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
	// 订单来源 0微信下单1，面对面自助下单2.退款手续费
	private String url;
	// 预约时间
	private String recommendTime;
	// 过期时间
	private String expireTime;
	// 实付金额
	private String payMoney;
	// 折扣
	private String proportion;
	// 优惠券使用的总钱
	private String disCountId;
	// 积分
	private int credit;

	private String weChatName;

	private String weChatPhone;

	private Double refund;

	private WeChatUser user;
	private Store store;
	private QueryServiceCost serviceCost;
	private Staff serviceStaff;
	private Staff staff;
	private Set<WeChatPayDetail> payDetails;
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
	public String getDisCountId() {
		return disCountId;
	}
	public void setDisCountId(String disCountId) {
		this.disCountId = disCountId;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String getWeChatName() {
		return weChatName;
	}
	public void setWeChatName(String weChatName) {
		this.weChatName = weChatName;
	}
	public String getWeChatPhone() {
		return weChatPhone;
	}
	public void setWeChatPhone(String weChatPhone) {
		this.weChatPhone = weChatPhone;
	}

	public WeChatUser getUser() {
		return user;
	}
	public void setUser(WeChatUser user) {
		this.user = user;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public QueryServiceCost getServiceCost() {
		return serviceCost;
	}
	public void setServiceCost(QueryServiceCost serviceCost) {
		this.serviceCost = serviceCost;
	}
	public Staff getServiceStaff() {
		return serviceStaff;
	}
	public void setServiceStaff(Staff serviceStaff) {
		this.serviceStaff = serviceStaff;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public Set<WeChatPayDetail> getPayDetails() {
		return payDetails;
	}
	public void setPayDetails(Set<WeChatPayDetail> payDetails) {
		this.payDetails = payDetails;
	}

	public Double getRefund() {
		return refund;
	}
	public void setRefund(Double refund) {
		this.refund = refund;
	}

}
