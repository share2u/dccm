package cn.ncut.entity.wechat.pojo;

/**
 * 客户预约表
 * */
public class WeChatAppoint {
	private String customAppointId;
	private String orderId;
	private int uId;
	private String appointTime;
	private String serviceStaffId;
	private int appointCode;
	private String expireTime;
	public String getCustomAppointId() {
		return customAppointId;
	}
	public void setCustomAppointId(String customAppointId) {
		this.customAppointId = customAppointId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public String getAppointTime() {
		return appointTime;
	}
	public void setAppointTime(String appointTime) {
		this.appointTime = appointTime;
	}
	public String getServiceStaffId() {
		return serviceStaffId;
	}
	public void setServiceStaffId(String serviceStaffId) {
		this.serviceStaffId = serviceStaffId;
	}
	public int getAppointCode() {
		return appointCode;
	}
	public void setAppointCode(int appointCode) {
		this.appointCode = appointCode;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	@Override
	public String toString() {
		return "WeChatAppoint [customAppointId=" + customAppointId
				+ ", orderId=" + orderId + ", uId=" + uId + ", appointTime="
				+ appointTime + ", serviceStaffId=" + serviceStaffId
				+ ", appointCode=" + appointCode + ", expireTime=" + expireTime
				+ "]";
	}
}
