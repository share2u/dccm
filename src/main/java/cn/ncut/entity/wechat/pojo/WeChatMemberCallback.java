package cn.ncut.entity.wechat.pojo;

/**
 * 客户回电
 * */
public class WeChatMemberCallback {
	private int memberCallbackId;
	private int uId;
	private String name;
	private String storeId;
	private String staffId;
	private String phone;
	private int status;
	private String createTime;
	public int getMemberCallbackId() {
		return memberCallbackId;
	}
	public void setMemberCallbackId(int memberCallbackId) {
		this.memberCallbackId = memberCallbackId;
	}
	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
